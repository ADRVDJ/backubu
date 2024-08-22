package com.example.demo;

import com.example.demo.models.ERole;
import com.example.demo.models.RoleEntity;
import com.example.demo.models.UserEntity;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import java.util.Set;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Autowired
	private UserRepository userRepository;
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	@Bean
	CommandLineRunner init() {
		return args -> {
			UserEntity userEntity = UserEntity.builder()
					.email("admin@gmail.com")
					.username("admin")
					.password("admin")
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.ADMIN)
							.build()))
					.build();

			userRepository.save(userEntity);

		};
	}
}
