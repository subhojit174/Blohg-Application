package com.blog.blogappapis.controller;

import com.blog.blogappapis.config.AppConstants;
import com.blog.blogappapis.entities.Post;
import com.blog.blogappapis.payloads.ApiResponse;
import com.blog.blogappapis.payloads.PostDTO;
import com.blog.blogappapis.payloads.PostResponse;
import com.blog.blogappapis.service.FileService;
import com.blog.blogappapis.service.PostService;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;
    @PostMapping("/category/{categoryId}/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @PathVariable Integer categoryId, @PathVariable Integer userId){
        PostDTO createdPostDTO=postService.createPost(postDTO,categoryId,userId);
        return new ResponseEntity<PostDTO>(createdPostDTO, HttpStatus.CREATED);
    }
    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Integer postId){
        PostDTO updatedPostDTO=postService.updatePost(postDTO,postId);
        return new ResponseEntity<PostDTO>(updatedPostDTO, HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable Integer userId){
        List<PostDTO> postDTOList=postService.getPostsByUser(userId);
        return ResponseEntity.ok(postDTOList);
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable Integer categoryId){
        List<PostDTO> postDTOList=postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(postDTOList);
    }
    @GetMapping("/")
    public ResponseEntity<PostResponse> getPosts(@RequestParam(required = false,defaultValue = AppConstants.pageSize) Integer pageSize,
                                                 @RequestParam(required = false, defaultValue =AppConstants.pageNumber) Integer pageNumber,
                                                 @RequestParam(required = false, defaultValue = AppConstants.pageSize) String sortBy,
                                                 @RequestParam(required = false,defaultValue = AppConstants.sortBy) String sortOrder){
        PostResponse postResponse=postService.getAllPosts(pageSize,pageNumber,sortBy,sortOrder);
        return ResponseEntity.ok(postResponse);
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post"+postId+" deleted successfully",true),HttpStatus.OK);
    }
    @PreAuthorize("hasRole('NORMAL')")
    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Integer postId){
        PostDTO postDTO=postService.getPostById(postId);
        return ResponseEntity.ok(postDTO);
    }
    @GetMapping("/Search/{keyword}")
    public ResponseEntity<List<PostDTO>> postByTitle(@PathVariable String keyword){
        List<PostDTO> postDTOList=postService.searchPosts(keyword);
        return  ResponseEntity.ok(postDTOList);
    }
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(@RequestParam("image")MultipartFile image,@PathVariable Integer postId)
    throws IOException {
        PostDTO postDTO=this.postService.getPostById(postId);
        String fileName=this.fileService.uploadImage(path,image);
        System.out.println("fileName:"+fileName);
        postDTO.setImageName(fileName);
        PostDTO updatedPostDTO=this.postService.updatePost(postDTO,postId);
        System.out.println("Updated fileName:"+updatedPostDTO.getImageName());
        return new ResponseEntity<PostDTO>(updatedPostDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName, HttpServletResponse response)
        throws IOException{
        InputStream resource=this.fileService.
                getSource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
