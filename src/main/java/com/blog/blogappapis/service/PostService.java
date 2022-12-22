package com.blog.blogappapis.service;

import com.blog.blogappapis.entities.Post;
import com.blog.blogappapis.payloads.PostDTO;
import com.blog.blogappapis.payloads.PostResponse;
import java.util.List;

public interface PostService {
PostDTO createPost(PostDTO postDTO,Integer categoryId,Integer userId);
PostDTO updatePost(PostDTO postDTO,Integer postId);
void deletePost(Integer postId);
PostResponse getAllPosts(Integer pageSize, Integer pageNumber, String sortBy, String sortOrder);
    PostDTO getPostById(Integer postId);
List<PostDTO> getPostsByCategory(Integer categoryId);
List<PostDTO> getPostsByUser(Integer userId);
List<PostDTO> searchPosts(String keyWord);
}
