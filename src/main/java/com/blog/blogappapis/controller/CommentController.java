package com.blog.blogappapis.controller;

import com.blog.blogappapis.payloads.ApiResponse;
import com.blog.blogappapis.payloads.CommentDTO;
import com.blog.blogappapis.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @PathVariable Integer postId,
                                                    @PathVariable Integer userId){
        CommentDTO createdCommentDto=this.commentService.createComment(commentDTO,postId,userId);
        return new ResponseEntity<CommentDTO>(createdCommentDto, HttpStatus.CREATED);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment"+commentId+" deleted successfully",true),HttpStatus.OK);

    }
}
