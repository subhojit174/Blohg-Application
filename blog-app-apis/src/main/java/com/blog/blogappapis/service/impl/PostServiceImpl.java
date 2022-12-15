package com.blog.blogappapis.service.impl;

import com.blog.blogappapis.entities.Category;
import com.blog.blogappapis.entities.Comment;
import com.blog.blogappapis.entities.Post;
import com.blog.blogappapis.entities.User;
import com.blog.blogappapis.exceptions.ResourceNotFoundException;
import com.blog.blogappapis.payloads.PostDTO;
import com.blog.blogappapis.payloads.PostResponse;
import com.blog.blogappapis.payloads.UserDTO;
import com.blog.blogappapis.repository.CategoryRepo;
import com.blog.blogappapis.repository.CommentRepo;
import com.blog.blogappapis.repository.PostRepo;
import com.blog.blogappapis.repository.UserRepo;
import com.blog.blogappapis.service.PostService;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDTO createPost(PostDTO postDTO, Integer categoryId, Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User Id",userId));
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id",categoryId));
        Post post=this.modelMapper.map(postDTO,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost=this.postRepo.save(post);
        return this.modelMapper.map(newPost,PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post"," id",postId));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageName(postDTO.getImageName());
        Post updatedPost=this.postRepo.save(post);
        return this.modelMapper.map(updatedPost,PostDTO.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post"," id",postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer pageSize, Integer pageNumber, String sortBy, String sortOrder) {
        Sort sort=(sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> pagePost=this.postRepo.findAll(pageable);
        List<Post> postList=pagePost.getContent();
        List<PostDTO> postDTOList=postList.stream().map(post->this.modelMapper.map(post,PostDTO.class)).collect(Collectors.toList());
        //return postDTOList;
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDTOList);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDTO getPostById(Integer postId) {
        Post post=this.postRepo.getById(postId);
        PostDTO postDTO=this.modelMapper.map(post,PostDTO.class);
        /*User user=post.getUser();
        System.out.println(user.getRoles());
        UserDTO userDTO=this.modelMapper.map(user, UserDTO.class);
        System.out.println(userDTO.getRoles());
        postDTO.setUser(userDTO);*/
       // postDTO.setUser(p);
        return postDTO;
    }

    @Override
    public List<PostDTO> getPostsByCategory(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","category id ",categoryId));
        List<Post> postList=this.postRepo.findByCategory(category);
        List<PostDTO> postDTOList=postList.stream().map(post->this.modelMapper.map(post,PostDTO.class)).collect(Collectors.toList());
        return postDTOList;
    }

    @Override
    public List<PostDTO> getPostsByUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User Id ",userId));
        List<Post> postList=this.postRepo.findByUser(user);
        List<PostDTO> postDTOList=postList.stream().map(post->this.modelMapper.map(post,PostDTO.class)).collect(Collectors.toList());
        return postDTOList;
    }

    @Override
    public List<PostDTO> searchPosts(String keyWord) {
        List<Post> postList=this.postRepo.findByTitleContaining("%"+keyWord+"%");
        List<PostDTO> postDTOList=postList.stream().map(post->this.modelMapper.map(post,PostDTO.class)).collect(Collectors.toList());
        return postDTOList;    }
}
