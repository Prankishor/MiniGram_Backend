package com.prankishor.blog.services;

import java.util.List;

import com.prankishor.blog.payloads.CategoryDto;

public interface CategoriesService {

	//Create
	CategoryDto createCategory(CategoryDto categoryDto);
	//Update
	CategoryDto updateCategory(CategoryDto categoryDto, Integer catId);
	//Get
	CategoryDto getCategory(Integer catId);
	//Delete
	void deleteCategory(Integer catId);
	
	//Get all
	List<CategoryDto> getAllCategories();
}
