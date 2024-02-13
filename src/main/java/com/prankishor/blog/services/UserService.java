package com.prankishor.blog.services;

import com.prankishor.blog.payloads.UserDto;
import com.prankishor.blog.payloads.UserResponse;

public interface UserService {
	
	UserDto registerNewUser(UserDto user);
	
    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Integer userId);

    UserDto getUserById(Integer userId);

    UserResponse getAllUsers(Integer pageNumber, Integer pageSize);

    void deleteUser(Integer userId);
}
