package com.adobe.demo;

import com.adobe.demo.service.AppService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);

        String[] names = ctx.getBeanDefinitionNames(); // all beans in Spring Container
        for(String name: names) {
            System.out.println(name);
        }

        System.out.println("*****");
        AppService service = ctx.getBean("appService", AppService.class);
        service.insert();
    }

}
