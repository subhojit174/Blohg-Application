package com.blog.blogappapis.service;

import com.blog.blogappapis.payloads.CommentDTO;

public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO,Integer postId,Integer userId);
    void deleteComment(Integer commentId);
}
