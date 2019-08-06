package org.junhi.auth.service;

import org.junhi.auth.client.UserClient;
import org.junhi.auth.config.JwtProperties;
import org.junhi.common.pojo.UserInfo;
import org.junhi.common.utils.JwtUtils;
import org.junhi.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author junhi
 * @date 2019/8/4 21:40
 */
@Service
public class AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 授权，发放token
     * @param username
     * @param password
     * @return
     */
    public String accredit(String username, String password) {
        //1.根据用户名和密码查询
        User user = this.userClient.queryUser(username, password);

        //判断user
        if(user == null){
            return null;
        }

        try {
            //jwtUtils生成jwt类型的token
            UserInfo userInfo = new UserInfo();
            userInfo.setId(user.getId());
            userInfo.setUsername(user.getUsername());
            return JwtUtils.generateToken(userInfo, this.jwtProperties.getPrivateKey(), this.jwtProperties.getExpire());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
