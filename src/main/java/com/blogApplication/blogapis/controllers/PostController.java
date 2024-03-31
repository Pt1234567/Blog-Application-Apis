package com.blogApplication.blogapis.controllers;

import com.blogApplication.blogapis.entities.Post;
import com.blogApplication.blogapis.payloads.ApiResponse;
import com.blogApplication.blogapis.payloads.PostDto;
import com.blogApplication.blogapis.payloads.PostResponse;
import com.blogApplication.blogapis.services.PostService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //get post by category
    @GetMapping("category/{category_Id}/posts")
    public ResponseEntity<List<PostDto>> getByPostByCategoryId(@PathVariable int category_Id){
        List<PostDto> postDtoList=this.postService.getPostByCategory(category_Id);

        return new ResponseEntity<>(postDtoList,HttpStatus.OK);
    }

    //get post by User
    @GetMapping("user/{user_Id}/posts")
    public ResponseEntity<List<PostDto>> getByPostByUserId(@PathVariable int user_Id){
        List<PostDto> postDtoList=this.postService.getPostByUser(user_Id);

        return new ResponseEntity<>(postDtoList,HttpStatus.OK);
    }

    @GetMapping("posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable int postId){
        PostDto postDto=this.postService.getPostById(postId);

        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    @GetMapping("posts/")
    public ResponseEntity<PostResponse> getALLPosts(@RequestParam(value = "pageNumber" ,defaultValue = "0",required = false) int pageNumber,
                                                    @RequestParam(value = "pageSize",defaultValue = "9",required = false) int pageSize,
                                                    @RequestParam(value="sortBy",defaultValue = "postTitle",required = false) String sortBy,
                                                    @RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir){
        PostResponse allPosts=this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);

        return new ResponseEntity<>(allPosts,HttpStatus.OK);
    }

    @DeleteMapping("posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable int postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully",true),HttpStatus.OK);
    }

    //update post
    @PutMapping("posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable int postId,@RequestBody PostDto postDto){
        PostDto updatedPost=this.postService.updatePost(postId,postDto);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }
}
