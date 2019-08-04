package org.junhi.user.service;

import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang3.StringUtils;
import org.junhi.common.utils.NumberUtils;
import org.junhi.user.mapper.UserMapper;
import org.junhi.user.pojo.User;
import org.junhi.user.utils.CodecUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author junhi
 * @date 2019/8/2 20:35
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 验证码存到redis中的命名前缀
     */
    private static final String KEY_PREFIX = "user:verify:";

    /**
     * 校验数据是否可用
     *
     * @param data
     * @param type
     * @return
     */
    public Boolean checkUser(String data, Integer type) {
        User record = User.builder().build();
        switch (type) {
            case 1:
                record.setUsername(data);
                break;
            case 2:
                record.setPhone(data);
                break;
            default:
                return null;
        }
        return this.userMapper.selectCount(record) == 0;
    }

    /**
     * 接收前端传来的手机号，调用发送手机验证码
     *
     * @param phone
     */
    public void sendVerifyCode(String phone) {
        if (StringUtils.isBlank(phone)) {
            return;
        }
        //生成验证码
        String code = NumberUtils.generateCode(6);

        //发送消息到rabbitMQ
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);
        this.amqpTemplate.convertAndSend("LEYOU.SMS.EXCHANGE", "verifycode.sms", map);

        //把验证码保存到redis中
        this.redisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
    }

    /**
     * 接收前端传来的注册信息，包含手机验证码
     * @param user
     * @param code
     */
    public void register(User user, String code) {

        //从redis中获取验证码
        String redisCode = this.redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());

        //校验验证码
        if(!StringUtils.equals(code, redisCode)){
            return;
        }

        //生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);

        //加盐加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));

        //新增用户，id设置为null防止sql注入
        user.setId(null);
        user.setCreated(new Date());
        this.userMapper.insertSelective(user);

        //删除redis中存的验证码
        this.redisTemplate.delete(KEY_PREFIX + user.getPhone());
    }

    /**
     * 查询用户是否存在，如果存在返回用户信息（不含密码）
     * @param username
     * @param password
     * @return
     */
    public User queryUser(String username, String password) {
        User record = User.builder()
                .username(username)
                .build();
        User user = this.userMapper.selectOne(record);

        //判断user是否为空
        if(user == null){
            return null;
        }

        //获取盐，对用户输入的密码进行加密
        password = CodecUtils.md5Hex(password, user.getSalt());

        //和数据库中的密码进行比较
        if(StringUtils.equals(password, user.getPassword())){
            return user;
        }
        return null;
    }
}
