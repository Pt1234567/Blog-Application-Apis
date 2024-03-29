package com.blogApplication.blogapis.repositories;

import com.blogApplication.blogapis.entities.Category;
import com.blogApplication.blogapis.entities.Post;
import com.blogApplication.blogapis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    //custom finder methods
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

}
