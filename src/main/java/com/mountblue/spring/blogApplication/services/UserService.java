package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.User;

public interface UserService {
    boolean addUser(String username, String email, String password);
    User authenticate(String username, String password);
}
