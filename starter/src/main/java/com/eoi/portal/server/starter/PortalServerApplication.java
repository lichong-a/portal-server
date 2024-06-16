package com.eoi.portal.server.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.eoi.portal.server.*"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.eoi.portal.server.*"})
@ServletComponentScan(basePackages = {"com.eoi.portal.server.*"})
public class PortalServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortalServerApplication.class, args);
    }

}
