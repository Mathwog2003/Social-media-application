package com.social.demo.service;

import com.social.demo.entity.Post;
import com.social.demo.entity.User;
import com.social.demo.repositary.PostRepository;
import com.social.demo.repositary.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostServiceInterface{

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    public Post createNewPost(Post post, Integer userId) throws Exception {
    Optional<User> user = userService.findUserById(userId);
    Post newpost = new Post();
    newpost.setCaption(post.getCaption());
    newpost.setImage(post.getImage());
    newpost.setCreatedAt(post.getCreatedAt());
    newpost.setCreatedAt(LocalDateTime.now());
    newpost.setVideo(post.getVideo());
    newpost.setUser(user.get());
    return postRepository.save(newpost);

    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws Exception {
        Optional<User> user = userService.findUserById(userId);
        Post post = findPostByPostId(postId);

        if (post.getUser().getId() != user.get().getId()) {
            throw new Exception("You can't delete another user's post");
        }

        // Remove references to the post in users_saved_post table
        for (User u : userRepository.findAll()) {
            u.getSavedPost().remove(post);
        }

        // Delete the post
        postRepository.delete(post);

        return "Post has been deleted";
    }

    @Override
    public Optional<Post> findPostByUserId(Integer userId) {
        return postRepository.findPostByUserId(Long.valueOf(userId));
    }

    @Override
    public Post findPostByPostId(Integer postId) throws Exception {
        Optional<Post> post = postRepository.findById(Long.valueOf(postId));
        if(post.isEmpty())
        {
            throw  new Exception("Post not found with this id"+postId);
        }
        return post.get();
    }

    @Override
    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    public Post savedPost(Integer postId, Integer userId) throws Exception {
        Post post = findPostByPostId(postId);
        Optional<User> user = userService.findUserById(userId);

        if (user.isPresent()) {
            if (user.get().getSavedPost().contains(post)) {
                // The post is already saved by the user
                user.get().getSavedPost().remove(post);
            } else {
                // Save the post for the user
                user.get().getSavedPost().add(post);
                userRepository.save(user.get());
            }
            return post;
        }else {
            throw new Exception("User not found");
        }
    }



    public Post likePost(Integer postId, Integer userId) throws Exception {
        // Assuming postId is the correct parameter for finding the post
        Post post = findPostByPostId(postId);

        if (post != null) {
            Optional<User> user = userService.findUserById(userId);

            if (user.isPresent()) {
                if (post.getLiked().contains(user.get())) {
                    post.getLiked().remove(user.get());
                } else {
                    post.getLiked().add(user.get());
                }

                return postRepository.save(post);
            } else {
                throw new Exception("User not found");
            }
        } else {
            throw new Exception("Post not found");
        }
    }
}
