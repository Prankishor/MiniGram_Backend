package com.prankishor.blog.services;

import java.util.List;
import com.prankishor.blog.payloads.PostDto;
import com.prankishor.blog.payloads.PostResponse;

public interface PostService {
	
	//C
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//U
	PostDto updatePost(PostDto postDto, Integer postId);	
	
	//D
	void deletePost(Integer postId);
	
	//R
	//All Post
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy);
	
	//Single Post
	PostDto getPostById(Integer postId);
	
	//All post by Category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	//All post by User
	List<PostDto> getPostsByUser(Integer userId);
	
	//Search Posts by keyword
	List<PostDto> searchPosts(String keyword);
	
}
