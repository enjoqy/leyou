package org.junhi.user.api;

import org.junhi.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 提供给别人进行远程调用的
 * @author junhi
 * @date 2019/8/4 22:18
 */
public interface UserApi {

    /**
     * 查询用户是否存在，如果存在返回用户信息（不含密码）
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    User queryUser(@RequestParam("username")String username, @RequestParam("password")String password);

}
