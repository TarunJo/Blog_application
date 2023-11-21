package com.mountblue.spring.blogApplication;

import com.mountblue.spring.blogApplication.dao.AppDao;
import com.mountblue.spring.blogApplication.dao.AppDaoImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDao appDao) {
		return runner -> {
//			createPost(appDao);
		};
	}

	private void createPost(AppDao appDao) {
		appDao.addPost();
	}
}
