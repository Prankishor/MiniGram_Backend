package com.prankishor.blog.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prankishor.blog.entities.Role;
import com.prankishor.blog.entities.User;
import com.prankishor.blog.exceptions.ResourceNotFoundException;
import com.prankishor.blog.payloads.UserDto;
import com.prankishor.blog.payloads.UserResponse;
import com.prankishor.blog.repositories.RoleRepo;
import com.prankishor.blog.repositories.UserRepo;
import com.prankishor.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userdto) {
        User user = this.dtoToUser(userdto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", " id ", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());

        User updatedUser = this.userRepo.save(user);

        return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", " id ", userId));

        return this.userToDto(user);
	}

	@Override
	public UserResponse getAllUsers(Integer pageNumber, Integer pageSize) {
		
		Pageable pageMeta = PageRequest.of(pageNumber, pageSize);
		Page<User> pageUser = this.userRepo.findAll(pageMeta);
		
		
        List<User> users = pageUser.getContent();
        List<UserDto> userDtos = new ArrayList<UserDto>();

        for (int i = 0; i < users.size(); i++) {
            userDtos.add(this.userToDto(users.get(i)));
        }
        
        UserResponse userResponse = new UserResponse();
        userResponse.setUsers(userDtos);
        userResponse.setPageNumber(pageUser.getNumber());
        userResponse.setPageSize(pageUser.getSize());
        userResponse.setTotalElements(pageUser.getTotalElements());
        userResponse.setTotalPages(pageUser.getTotalPages());
        userResponse.setLastPage(pageUser.isLast());
        
        return userResponse;
	}

	@Override
	public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
	}
	
    private User dtoToUser(UserDto userdto) {
    	
    	User user = this.modelMapper.map(userdto, User.class);
        return user;
    }

    private UserDto userToDto(User user) {
    	
    	UserDto userdto = this.modelMapper.map(user, UserDto.class);
        return userdto;
    }

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		Role role = this.roleRepo.findById(502).get();
		
		user.getRoles().add(role);
		User newUser = this.userRepo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}

}
