package com.prankishor.blog.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prankishor.blog.entities.Category;
import com.prankishor.blog.entities.User;
import com.prankishor.blog.exceptions.ResourceNotFoundException;
import com.prankishor.blog.payloads.CategoryDto;
import com.prankishor.blog.payloads.UserDto;
import com.prankishor.blog.repositories.CategoriesRepo;
import com.prankishor.blog.services.CategoriesService;

@Service
public class CategoriesServiceImpl implements CategoriesService {
	
	@Autowired
	private CategoriesRepo categoriesRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category savedCategory = this.categoriesRepo.save(category);
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer catId) {
		Category category = this.categoriesRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category ", "Category Id", catId));
		category.setCategoryName(categoryDto.getCategoryName());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory = this.categoriesRepo.save(category);
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto getCategory(Integer catId) {
		Category cat = this.categoriesRepo.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", catId));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer catId) {
		Category cat = this.categoriesRepo.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", catId));
		this.categoriesRepo.delete(cat);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> cats = this.categoriesRepo.findAll();

        List<CategoryDto> catdtos = new ArrayList<CategoryDto>();

        for (int i = 0; i < cats.size(); i++) {
            catdtos.add(this.modelMapper.map(cats.get(i), CategoryDto.class));
        }
        return catdtos;
	}

}
