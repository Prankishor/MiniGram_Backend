package com.prankishor.blog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prankishor.blog.payloads.ApiResponse;
import com.prankishor.blog.payloads.PostResponse;
import com.prankishor.blog.payloads.UserDto;
import com.prankishor.blog.payloads.UserResponse;
import com.prankishor.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
    private UserService userService;
	
	// Post (Create User)
	
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }
    
    //Put (Update User)
    
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, 
    		@PathVariable Integer userId)
    {
    	UserDto updatedUser = this.userService.updateUser(userDto, userId);
    	return ResponseEntity.ok(updatedUser);
    }
    
    //Delete (Delete User)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId)
    {
    	this.userService.deleteUser(userId);
    	//return new ResponseEntity(Map.of("message", "User deleted Successfully"), HttpStatus.OK);
    	return new ResponseEntity<>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
    }
    
    //GET (Get user) 
    
    @GetMapping("/")
    public ResponseEntity<UserResponse> getAllUsers(
    		@RequestParam(value="pageNumber", defaultValue="0", required=false) Integer pageNumber,
    		@RequestParam(value="pageSize", defaultValue="3", required=false) Integer pageSize)
    {
    	UserResponse userResponse = this.userService.getAllUsers(pageNumber, pageSize);
    	return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId)
    {
    	return ResponseEntity.ok(this.userService.getUserById(userId));
    }
}
