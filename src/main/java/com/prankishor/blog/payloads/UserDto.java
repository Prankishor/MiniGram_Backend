package com.prankishor.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.prankishor.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
    
    @NotEmpty
    @Size(min=3, message="Username must contain atleast 3 letters")
    private String name;
    
    @Email(message = "Email address is not valid !!")
    @NotEmpty(message = "Email is required !!")
    private String email;
    
    @NotEmpty
    @Size(min=6, max=12, message="Password must be of length between 6 to 12 characters")
    private String password;
    @NotEmpty
    private String about;
    
    private Set<RoleDto> roles = new HashSet<>();
    
    @JsonIgnore
    public String getPassword() {
    	return this.password;
    }
    
    @JsonProperty
    public String setPassword(String password) {
    	return this.password = password;
    }
}
