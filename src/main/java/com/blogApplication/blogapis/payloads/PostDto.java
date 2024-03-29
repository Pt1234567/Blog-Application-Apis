package com.blogApplication.blogapis.payloads;

import com.blogApplication.blogapis.entities.Category;
import com.blogApplication.blogapis.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

    private String postTitle;
    private String postContent;
    private String postImage;
    private Date addedDate;
    private UserDto user;
    private CategoryDto category;
}
