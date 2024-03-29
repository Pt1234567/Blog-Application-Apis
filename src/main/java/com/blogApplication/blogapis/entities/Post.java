package com.blogApplication.blogapis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    private String postTitle;

    private String postContent;

    private String postImage;

    private Date addedDate;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;
}
