package com.prankishor.blog.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.prankishor.blog.entities.Category;
import com.prankishor.blog.entities.Post;
import com.prankishor.blog.entities.User;
import com.prankishor.blog.exceptions.ResourceNotFoundException;
import com.prankishor.blog.payloads.PostDto;
import com.prankishor.blog.payloads.PostResponse;
import com.prankishor.blog.repositories.CategoriesRepo;
import com.prankishor.blog.repositories.PostRepo;
import com.prankishor.blog.repositories.UserRepo;
import com.prankishor.blog.services.PostService;


@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoriesRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->
		new ResourceNotFoundException("User", "User id", userId));
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->
		new ResourceNotFoundException("Category", "cateory id", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub
		
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize) {
		Pageable pageMeta = PageRequest.of(pageNumber, pageSize);
		
		Page<Post> pagePost = this.postRepo.findAll(pageMeta);
		
		List<Post> posts = pagePost.getContent();
		
		List<PostDto> postDtos = new ArrayList<>();
		for (int i = 0; i < posts.size(); i++) {
            postDtos.add(this.modelMapper.map(posts.get(i), PostDto.class));
        }
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		
		List<PostDto> postDtos = new ArrayList<>();
		for (int i = 0; i < posts.size(); i++) {
            postDtos.add(this.modelMapper.map(posts.get(i), PostDto.class));
        }
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		
		List<PostDto> postDtos = new ArrayList<>();
		for(int i=0; i<posts.size(); i++)
		{
			postDtos.add(this.modelMapper.map(posts.get(i), PostDto.class));
		}
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> searchedPosts = this.postRepo.findByTitleContaining(keyword);
		
		List<PostDto> postDtos = new ArrayList<>();
		for(int i=0; i<searchedPosts.size(); i++)
		{
			postDtos.add(this.modelMapper.map(searchedPosts.get(i), PostDto.class));
		}
		return postDtos;
	}

}
