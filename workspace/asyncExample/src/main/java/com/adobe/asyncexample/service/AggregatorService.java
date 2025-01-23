package com.adobe.asyncexample.service;

import com.adobe.asyncexample.dto.Post;
import com.adobe.asyncexample.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AggregatorService {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Async("users-pool")
    public CompletableFuture<List<User>> getUsers() {
        System.out.println(Thread.currentThread() + " getting users");
        return CompletableFuture.completedFuture(userService.getUsers());
    }


    @Async("posts-pool")
    public CompletableFuture<List<Post>> getPosts() {
        System.out.println(Thread.currentThread() + " getting Posts");
        return CompletableFuture.completedFuture(postService.getPosts());
    }
}
