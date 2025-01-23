package com.adobe.asyncexample.api;

import com.adobe.asyncexample.dto.Post;
import com.adobe.asyncexample.dto.User;
import com.adobe.asyncexample.service.AggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/posts")
public class PostController {
    @Autowired
    private AggregatorService service;

    record PostsDTO(String title, String username){}

    @GetMapping
    public List<PostsDTO> getPosts() {
        CompletableFuture<List<Post>> posts = service.getPosts(); // non-blocking
        CompletableFuture<List<User>> users = service.getUsers(); // non-blocking

        // barrier; blocking
        List<Post> postList = posts.join(); // caller thread has to wait
        List<User> userList = users.join();

        return  postList.stream().map(post -> {
            String name = userList.stream().filter(user -> user.id() == post.userId()).findFirst().get().name();
            return  new PostsDTO(post.title(), name);
        }).collect(Collectors.toList());
    }
}
