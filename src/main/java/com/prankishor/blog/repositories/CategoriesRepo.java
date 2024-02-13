package com.prankishor.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prankishor.blog.entities.Category;

public interface CategoriesRepo extends JpaRepository<Category, Integer>{

}
