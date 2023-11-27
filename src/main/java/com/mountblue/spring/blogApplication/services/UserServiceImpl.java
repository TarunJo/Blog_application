package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.Authorities;
import com.mountblue.spring.blogApplication.entity.User;
import com.mountblue.spring.blogApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean addUser(String username, String email, String password) {
        User userByName = userRepository.findByUserName(username);
        User userByEmail = userRepository.findByEmail(email);

        if(userByEmail == null && userByName == null) {
            String encodedPassword = passwordEncoder.encode(password);
            User user = new User(username, email, encodedPassword);
            Authorities authorities = new Authorities( username, "ROLE_author");
            user.addAuthorities(authorities);

            userRepository.save(user);

            return true;
        }
        else {
            return false;
        }
    }
}
