package com.mountblue.spring.blogApplication.repository;

import com.mountblue.spring.blogApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String name);
    User findByEmail(String email);
}
