package com.blog.blogappapis;

import com.blog.blogappapis.config.AppConstants;
import com.blog.blogappapis.entities.Role;
import com.blog.blogappapis.repository.RoleRepo;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogAppApisApplication
		implements CommandLineRunner
{

	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		/*System.out.println(passwordEncoder.encode("hvh"));
		System.out.println(passwordEncoder.encode("arjun"));*/
		try {
			Role normalRole = new Role(AppConstants.NORMAL_USER, "ROLE_NORMAL");
			Role adminRole = new Role(AppConstants.ADMIN_USER, "ROLE_ADMIN");
			List<Role> roles = List.of(normalRole, adminRole);
			List<Role> resultRole = this.roleRepo.saveAll(roles);
			resultRole.forEach(System.out::println);

		}
		catch (Exception ex){
			ex.printStackTrace();
		}

	}
}
