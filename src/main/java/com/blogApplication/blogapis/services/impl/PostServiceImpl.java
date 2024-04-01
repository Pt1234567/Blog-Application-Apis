package com.blogApplication.blogapis.services.impl;

import com.blogApplication.blogapis.entities.Category;
import com.blogApplication.blogapis.entities.Post;
import com.blogApplication.blogapis.entities.User;
import com.blogApplication.blogapis.exception.ResourceNotFoundException;
import com.blogApplication.blogapis.payloads.CommentDto;
import com.blogApplication.blogapis.payloads.PostDto;
import com.blogApplication.blogapis.payloads.PostResponse;
import com.blogApplication.blogapis.repositories.CategoryRepo;
import com.blogApplication.blogapis.repositories.PostRepo;
import com.blogApplication.blogapis.repositories.UserRepo;
import com.blogApplication.blogapis.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        post.setPostImage("");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post saved=this.postRepo.save(post);

        return this.modelMapper.map(saved,PostDto.class);
    }

    @Override
    public PostDto updatePost(int id, PostDto postDto) {

        Post post=this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        post.setPostTitle(postDto.getPostTitle());
        post.setPostContent(postDto.getPostContent());
        post.setPostImage(postDto.getPostImage());

        Post updatedPost=this.postRepo.save(post);
        return this.modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(int id) {
        Post post=this.postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPosts(int pageNumber, int pageSize,String sortBy,String sortDir) {

        Sort sort=null;

        if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }else{
            sort=Sort.by(sortBy).descending();
        }

        Pageable pageable= PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> pagePost=this.postRepo.findAll(pageable);


        List<Post> postList=pagePost.getContent();

        List<PostDto> postDtos=postList.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(int id) {

        Post post=this.postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        // Map the Post entity to PostDto
        PostDto postDto = this.modelMapper.map(post, PostDto.class);

        // Fetch comments associated with the post and map them to CommentDto objects
        List<CommentDto> commentDtoList = post.getComments().stream()
                .map(comment -> this.modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());

        // Set the commentList field in the PostDto
        postDto.setCommentList(commentDtoList);

        return postDto;
    }

    @Override
    public List<PostDto> getPostByUser(int user_id) {
        User user=this.userRepo.findById(user_id).orElseThrow(()->new ResourceNotFoundException("User","id",user_id));

        List<Post> postList=this.postRepo.findByUser(user);

        List<PostDto> postDtos=   postList.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> getPostByCategory(int category_id) {
          List<Post> postList=this.postRepo.findByCategory(this.categoryRepo.findById(category_id).orElseThrow(()->new ResourceNotFoundException("Category","id",category_id)));

          List<PostDto> postDtos=   postList.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

          return postDtos;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> postList=this.postRepo.findByPostTitleContaining(keyword);
        List<PostDto> postDtos=postList.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
         return postDtos;
    }
}
