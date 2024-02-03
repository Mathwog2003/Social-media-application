package com.social.demo.service;

import com.social.demo.entity.Post;


import java.util.List;

public interface PostServiceInterface {

    Post createNewPost(Integer post, Integer userId) throws Exception;
    String deletePost(Integer postId,Integer userId) throws Exception;
    List<Post> findPostByUserId(Integer userId);
    Post findPostByPostId(Integer postId) throws Exception;
    List<Post> findAllPost();
    Post savedPost(Integer postId, Integer userId) throws Exception;
    Post likePost(Integer postId, Integer userId) throws Exception;

}
