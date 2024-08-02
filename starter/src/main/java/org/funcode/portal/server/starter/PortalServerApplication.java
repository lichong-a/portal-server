package org.funcode.portal.server.starter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

@Slf4j
@ConfigurationPropertiesScan(basePackages = {"org.funcode.portal.server.*"})
@SpringBootApplication(scanBasePackages = {"org.funcode.portal.server.*"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"org.funcode.portal.server.*"})
@ServletComponentScan(basePackages = {"org.funcode.portal.server.*"})
public class PortalServerApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(PortalServerApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = "/".equals(env.getProperty("server.servlet.context-path")) ? "" : env.getProperty("server.servlet.context-path");
        String infoMessage = "\n----------------------------------------------------------\n" +
                "Application is running! Access URLs:\n" +
                "Local: \t\t\thttp://localhost:" + port + path + "\n" +
                "External: \t\thttp://" + ip + ":" + port + path + "\n";
        boolean swaggerEnabled = "true".equals(env.getProperty("springdoc.swagger-ui.enabled"));
        if (swaggerEnabled) {
            String swaggerPath = Objects.requireNonNull(env.getProperty("springdoc.swagger-ui.path")).substring(1);
            infoMessage += "Swagger Doc: \thttp://" + ip + ":" + port + path + "/" + swaggerPath + "\n";
            infoMessage += "Knife4j Doc: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                    "----------------------------------------------------------";
        } else {
            infoMessage += "----------------------------------------------------------";
        }
        log.info(infoMessage);
    }

}
