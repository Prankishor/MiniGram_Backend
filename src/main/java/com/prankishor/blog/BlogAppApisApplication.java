package com.prankishor.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.prankishor.blog.entities.Role;
import com.prankishor.blog.repositories.RoleRepo;

import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
	
	   @Override
	   public void run(String... args) throws Exception {
	      
		   try {
			   Role admin = new Role();
			   admin.setId(501);
			   admin.setName("ROLE_ADMIN");
			   
			   Role normal = new Role();
			   normal.setId(502);
			   normal.setName("ROLE_NORMAL");
			   
			   List<Role> roles = List.of(admin, normal);
			   
			   List<Role> result = this.roleRepo.saveAll(roles);
			   
			   result.forEach(r->{
				   System.out.println(r.getName());
			   });
		   }
		   catch(Exception e) {
			   e.printStackTrace();
		   }
	   }

}
