package org.fbs.r34;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "org.fbs.r34.handler",
                "org.fbs.r34.service",
                "org.fbs.r34.rule",
                "org.fbs.r34.repository",
                "org.fbs.r34.aspect",
                "org.fbs.r34.provider"
                //"org.fbs.r34.interceptor"
        }
)
public class R34Application {

    public static void main(String[] args) {
        SpringApplication.run(R34Application.class, args);
    }

}
