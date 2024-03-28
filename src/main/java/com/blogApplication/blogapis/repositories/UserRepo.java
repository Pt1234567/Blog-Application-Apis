package com.blogApplication.blogapis.repositories;

import com.blogApplication.blogapis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

}
