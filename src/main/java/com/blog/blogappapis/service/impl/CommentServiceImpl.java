package com.blog.blogappapis.service.impl;

import com.blog.blogappapis.entities.Comment;
import com.blog.blogappapis.entities.Post;
import com.blog.blogappapis.entities.User;
import com.blog.blogappapis.exceptions.ResourceNotFoundException;
import com.blog.blogappapis.payloads.CommentDTO;
import com.blog.blogappapis.repository.CommentRepo;
import com.blog.blogappapis.repository.PostRepo;
import com.blog.blogappapis.repository.UserRepo;
import com.blog.blogappapis.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CommentRepo commentRepo;
    /*@Autowired
    private UserRoleRepo userRoleRepo;
    @Autowired
    private RoleRepo roleRepo;*/
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId,Integer userId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post Id",postId));
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","user Id",userId));
        System.out.println("Creating comment");
        /*List<UserRole> userRole=  new ArrayList<>();
        userRole.add(this.userRoleRepo.findById(user.getId())
                .orElseThrow(()->new ResourceNotFoundException("UserRole","user Id",userId)));
        List<Role> roles=userRole.stream().map(uRole->this.roleRepo.findById(uRole.getRoleId()).get()).collect(Collectors.toList());
        roles.forEach(System.out::println);
        user.setRoles(roles);*/
        Comment comment=dtoToEntity(commentDTO);
        comment.setPost(post);
        comment.setUser(user);
        Comment updatedComment=this.commentRepo.save(comment);
        return this.entityToDto(updatedComment);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment"," id",commentId));
        this.commentRepo.delete(comment);
    }
    private Comment dtoToEntity(CommentDTO commentDTO){
        return this.modelMapper.map(commentDTO,Comment.class);
    }
    private CommentDTO entityToDto(Comment comment){
        return this.modelMapper.map(comment,CommentDTO.class);
    }
}
