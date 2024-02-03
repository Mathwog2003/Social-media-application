package com.social.demo.controller;

import com.social.demo.entity.Post;
import com.social.demo.service.PostServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    @Autowired
    private PostServiceImplementation postServiceImplementation;

    @PostMapping("/posts/user/{userId}")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable Integer userId) {
        try {
            Post createdPost = postServiceImplementation.createNewPost(post, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/posts/{postId}/user/{userId}")
    public ResponseEntity<String> deletePost(@PathVariable Integer postId, @PathVariable Integer userId) {
        try {
            postServiceImplementation.deletePost(postId, userId);
            return ResponseEntity.ok("Successfully deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> findPostsByPostId(@PathVariable Integer postId) {
        try {
            Post foundPost = postServiceImplementation.findPostByPostId(postId);
            return ResponseEntity.ok(foundPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/posts/user/{userId}")
    public ResponseEntity<Optional<Post>> findPostsByUserId(@PathVariable Integer userId) {
        Optional<Post> foundPost = postServiceImplementation.findPostByUserId(userId);
        return ResponseEntity.ok(foundPost);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> findAllPost() {
        List<Post> allPosts = postServiceImplementation.findAllPost();
        return ResponseEntity.ok(allPosts);
    }

    @PutMapping("/posts/save/{postId}/user/{userId}")
    public ResponseEntity<Post> savePost(@PathVariable Integer postId, @PathVariable Integer userId) {
        try {
            Post savedPost = postServiceImplementation.savedPost(postId, userId);
            return ResponseEntity.ok(savedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/posts/like/{postId}/user/{userId}")
    public ResponseEntity<Post> likePost(@PathVariable Integer postId, @PathVariable Integer userId) {
        try {
            Post likedPost = postServiceImplementation.likePost(postId, userId);
            return ResponseEntity.ok(likedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
