package com.blog.blogappapis.repository;

import com.blog.blogappapis.entities.Category;
import com.blog.blogappapis.entities.Post;
import com.blog.blogappapis.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    @Query("select p from Post p where p.title like :key")
    List<Post> findByTitleContaining(@Param("key") String title);
}
