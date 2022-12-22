package com.blog.blogappapis.service;

import com.blog.blogappapis.entities.User;
import com.blog.blogappapis.payloads.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO registerNewUser(UserDTO user);

    UserDTO createUser(UserDTO user);

    UserDTO updateUser(UserDTO user, Integer userId);

    UserDTO getUserById(Integer userId);

    List<UserDTO> getAllUser();

    void deleteUser(Integer userId);
}
