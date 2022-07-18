package com.shohag.Backend.repositories;

import com.shohag.Backend.entities.Category;
import com.shohag.Backend.entities.Post;
import com.shohag.Backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

    // creating custom finder method in JPA
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    List<Post> findByTitleContaining(String title);
}
