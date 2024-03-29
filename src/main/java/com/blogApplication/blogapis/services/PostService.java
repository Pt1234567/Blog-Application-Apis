package com.blogApplication.blogapis.services;

import com.blogApplication.blogapis.entities.Post;
import com.blogApplication.blogapis.payloads.PostDto;

import java.util.List;

public interface PostService {

    //create post
    PostDto createPost(PostDto postDto,int user_id,int category_id);

    //update post
    Post updatePost(int id,PostDto postDto);

    //delete post
    void deletePost(int id);

    //get all posts
    List<Post> getAllPosts();

    //get post by id
    Post getPostById(int id);

    //get post by user
    List<Post> getPostByUser(int user_id);

    //get post By category
    List<Post> getPostByCategory(int category_id);

    //searchPost
    List<Post> searchPost(String keyword);
}
