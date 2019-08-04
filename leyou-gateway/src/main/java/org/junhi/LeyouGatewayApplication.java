package org.junhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author junhi
 * @date 2019/7/20 18:08
 */
@SpringBootApplication
@EnableZuulProxy  // 开启zuul网关代理
@EnableDiscoveryClient  // 开启eureka客户端
public class LeyouGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeyouGatewayApplication.class);
    }

}
