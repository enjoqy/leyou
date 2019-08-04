package org.junhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * @author junhi
 * @date 2019/7/22 9:13
 */
@SpringBootApplication
@EnableDiscoveryClient  // 启用注册中心
@MapperScan("org.junhi.item.mapper")
public class LeyouItemApplication {

    public static void main(String[] args) {

//        //运行nginx脚本
//        try {
//            String path = LeyouItemApplication.class.getClassLoader().getResource("nginx.bat").getPath();
//            String decodePath = URLDecoder.decode(path, "utf-8");
//            Runtime.getRuntime().exec(decodePath);
//            System.out.println("nginx服务启动");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        SpringApplication.run(LeyouItemApplication.class);
    }

}
