package com.blogApplication.blogapis.services.impl;

import com.blogApplication.blogapis.entities.Category;
import com.blogApplication.blogapis.entities.Post;
import com.blogApplication.blogapis.entities.User;
import com.blogApplication.blogapis.exception.ResourceNotFoundException;
import com.blogApplication.blogapis.payloads.PostDto;
import com.blogApplication.blogapis.repositories.CategoryRepo;
import com.blogApplication.blogapis.repositories.PostRepo;
import com.blogApplication.blogapis.repositories.UserRepo;
import com.blogApplication.blogapis.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto,int user_id,int category_id) {

        User user=this.userRepo.findById(user_id).orElseThrow(()->new ResourceNotFoundException("User","id",user_id));
        Category category=this.categoryRepo.findById(category_id).orElseThrow(()->new ResourceNotFoundException("Category","id",category_id));


        Post post=this.modelMapper.map(postDto,Post.class);
        post.setPostImage("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post saved=this.postRepo.save(post);

        return this.modelMapper.map(saved,PostDto.class);
    }

    @Override
    public Post updatePost(int id, PostDto postDto) {
        return null;
    }

    @Override
    public void deletePost(int id) {

    }

    @Override
    public List<Post> getAllPosts() {
        return null;
    }

    @Override
    public Post getPostById(int id) {
        return null;
    }

    @Override
    public List<Post> getPostByUser(int user_id) {
        return null;
    }

    @Override
    public List<Post> getPostByCategory(int category_id) {
        return null;
    }

    @Override
    public List<Post> searchPost(String keyword) {
        return null;
    }
}
