package com.blogApplication.blogapis.services.impl;

import com.blogApplication.blogapis.entities.User;
import com.blogApplication.blogapis.exception.ResourceNotFoundException;
import com.blogApplication.blogapis.payloads.CommentDto;
import com.blogApplication.blogapis.payloads.UserDto;
import com.blogApplication.blogapis.repositories.UserRepo;
import com.blogApplication.blogapis.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
           // Convert UserDto to User entity
            User user = dtoToEntity(userDto);

            // Save user entity to database
            User savedUser = userRepo.save(user);

            // Convert User entity to UserDto
            UserDto savedUserDto = EntityTodto(savedUser);

            return savedUserDto;

    }

    @Override
    public UserDto getUserById(int id) {
         User user=this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));

         UserDto userDto=this.modelMapper.map(user,UserDto.class);

         // fetch all comments associated with user
         List<CommentDto> commentDtos=user.getComments().stream().map((comment ->this.modelMapper.map(comment,CommentDto.class) )).collect(Collectors.toList());
         userDto.setCommentList(commentDtos);

         return userDto;
    }

    @Override
    public UserDto updateUser(int id, UserDto userDto) {
        User user=this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatesUser=this.userRepo.save(user);

        return this.EntityTodto(updatesUser);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList=this.userRepo.findAll();
        List<UserDto> userDtoList=userList.stream().map(user->EntityTodto(user)).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public void deleteUser(int id) {
       User user=this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
       this.userRepo.delete(user);
    }

    private User dtoToEntity(UserDto userDto) {
        // Convert UserDto to User entity
        User user = this.modelMapper.map(userDto, User.class);

//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());

        return user;
    }

    private UserDto EntityTodto(User user) {
        // Convert User entity to UserDto
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        return userDto;
    }
}
