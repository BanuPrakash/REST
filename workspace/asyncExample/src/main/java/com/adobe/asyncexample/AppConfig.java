package com.adobe.asyncexample;

import com.adobe.asyncexample.service.PostService;
import com.adobe.asyncexample.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig {

    // thread pool for hotels / users
    // https://jsonplaceholder.typicode.com/users
    @Bean(name="users-pool")
    public Executor usersPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("USER-THREAD-POOL");
        executor.initialize();
        return executor;
//        Executors.newFixedThreadPool(100);
    }

    // thread pool for flights / posts
    // https://jsonplaceholder.typicode.com/posts
    @Bean(name="posts-pool")
    public Executor postsPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("POST-THREAD-POOL");
        executor.initialize();
        return executor;
//        Executors.newFixedThreadPool(100);
    }

    @Bean
    UserService jsonUserService() {
        RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com/");
        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return  factory.createClient(UserService.class);
    }

    @Bean
    PostService jsonPostService() {
        RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com/");
        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return  factory.createClient(PostService.class);
    }
}
