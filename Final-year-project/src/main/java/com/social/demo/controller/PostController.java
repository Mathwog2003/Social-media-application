package com.social.demo.controller;

import com.social.demo.entity.Post;
import com.social.demo.service.PostServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    @Autowired
    private PostServiceImplementation postServiceImplementation;

    @PostMapping("/posts/user/{userId}")
    public Post createPost(@RequestBody Post post, @PathVariable Integer userId) throws Exception {
        return postServiceImplementation.createNewPost(post,userId);
    }

    @DeleteMapping("/Posts/{postId}/user/{userId}")
    public String deletePost(Integer postId,Integer userId) throws Exception {
        return postServiceImplementation.deletePost(postId,userId);
    }

    @GetMapping("/posts/{postId}")
    public Post findPostsByPostId(@PathVariable Integer postId) throws Exception {
        return postServiceImplementation.findPostByPostId(postId);
    }

    @GetMapping("/posts/user/{userId}")
    public Optional<Post> findPostsByUserId(@PathVariable Integer userId)
    {
        return postServiceImplementation.findPostByUserId(userId);
    }

    @GetMapping("/posts")
    public List<Post> findAllPost()
    {
        return postServiceImplementation.findAllPost();
    }

    @PutMapping("/posts/{postId}/user/{userId}")
    public Post savePost(@PathVariable Integer postId, @PathVariable Integer userId) throws Exception {
        return postServiceImplementation.savedPost(postId,userId);
    }
    @PutMapping("/posts/like/{postId}/user/{userId}")
    public Post likePost(@PathVariable Integer postId , @PathVariable Integer userId) throws Exception {
        return postServiceImplementation.likePost(postId,userId);
    }
}
