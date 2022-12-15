package com.blog.blogappapis.service.impl;

import com.blog.blogappapis.config.AppConstants;
import com.blog.blogappapis.entities.Role;
import com.blog.blogappapis.entities.User;
import com.blog.blogappapis.exceptions.ResourceNotFoundException;
import com.blog.blogappapis.payloads.UserDTO;
import com.blog.blogappapis.repository.RoleRepo;
import com.blog.blogappapis.repository.UserRepo;
import com.blog.blogappapis.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerNewUser(UserDTO userDTO) {
        User user=this.modelMapper.map(userDTO,User.class);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Role role=this.roleRepo.findById(AppConstants.NORMAL_USER).get();
        //List<Role> roles=user.getRoles();

        user.getRoles().add(role);
        User registeredUser=this.userRepo.save(user);
        return this.userToDTO(registeredUser);
    }

    @Override
    public UserDTO createUser(UserDTO userDto) {
        User user = this.userRepo.save(this.dtoToUser(userDto));
        return this.userToDTO(user);
    }

    @Override
    public UserDTO updateUser(UserDTO userDto, Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," id",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updatedUser=this.userRepo.save(user);
        return this.userToDTO(updatedUser);
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," id",userId));
        /*List<UserRole> userRole=  new ArrayList<>();
        userRole.add(this.userRoleRepo.findById(user.getId())
                .orElseThrow(()->new ResourceNotFoundException("UserRole","user Id",userId)));
        List<Role> roles=userRole.stream().map(uRole->this.roleRepo.findById(uRole.getRoleId()).get()).collect(Collectors.toList());
        roles.forEach(System.out::println);
        user.setRoles(roles);*/
        return this.userToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> userList=this.userRepo.findAll();
        List<UserDTO> userDTOList=userList.stream().map(user->this.userToDTO(user)).collect(Collectors.toList());
        return userDTOList;
    }

    @Override
    public void deleteUser(Integer userId) {
    User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," id",userId));
    this.userRepo.delete(user);
    }
    private User dtoToUser(UserDTO userDTO){
    User user=this.modelMapper.map(userDTO,User.class);
/*    user.setId(userDTO.getId());
    user.setName(userDTO.getName());
    user.setPassword(userDTO.getPassword());
    user.setEmail(userDTO.getEmail());
    user.setAbout(userDTO.getAbout());*/
    return user;
    }
    private UserDTO userToDTO(User user){
        UserDTO userDTO=this.modelMapper.map(user,UserDTO.class);
        //userDTO.setRole(user.getRoles());
        /*userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setAbout(user.getAbout());*/
        return userDTO;
    }
}
