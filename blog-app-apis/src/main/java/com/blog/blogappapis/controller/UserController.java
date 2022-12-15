package com.blog.blogappapis.controller;

import com.blog.blogappapis.payloads.ApiResponse;
import com.blog.blogappapis.payloads.UserDTO;
import com.blog.blogappapis.service.UserService;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser( @RequestBody @Valid UserDTO userDTO){
        UserDTO createUserDTO=this.userService.createUser(userDTO);
        return new ResponseEntity<>(createUserDTO, HttpStatus.CREATED);
    }
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser( @RequestBody @Valid UserDTO userDTO, @PathVariable Integer userId){
        UserDTO updatedUserDTO=this.userService.updateUser(userDTO,userId);
        return  ResponseEntity.ok(updatedUserDTO);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User"+userId+  " deleted successfully",true),HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUser(){
        return new ResponseEntity<>(this.userService.getAllUser(),HttpStatus.OK);
    }
    @GetMapping("/{userId}")

    public ResponseEntity<UserDTO> getUser(@PathVariable Integer userId){
        return new ResponseEntity<>(this.userService.getUserById(userId),HttpStatus.OK);
    }
}
