package org.funcode.portal.server.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@ConfigurationPropertiesScan(basePackages = {"org.funcode.portal.server.*"})
@SpringBootApplication(scanBasePackages = {"org.funcode.portal.server.*"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"org.funcode.portal.server.*"})
@ServletComponentScan(basePackages = {"org.funcode.portal.server.*"})
public class PortalServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortalServerApplication.class, args);
    }

}
