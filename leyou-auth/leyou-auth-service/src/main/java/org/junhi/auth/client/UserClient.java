package org.junhi.auth.client;

import org.junhi.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author junhi
 * @date 2019/8/4 22:22
 */
@FeignClient("user-service")
public interface UserClient extends UserApi {}
