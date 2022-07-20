package com.shohag.Backend.repositories;

import com.shohag.Backend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    Category findByCategoryId(Long categoryId);
}
