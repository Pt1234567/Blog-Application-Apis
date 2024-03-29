package com.blogApplication.blogapis.controllers;

import com.blogApplication.blogapis.entities.Post;
import com.blogApplication.blogapis.payloads.PostDto;
import com.blogApplication.blogapis.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    //create post
    @PostMapping("users/{userId}/categories/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postdto, @PathVariable int userId, @PathVariable int categoryId){

         PostDto createPost=this.postService.createPost(postdto, userId, categoryId);

         return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
    }
}
