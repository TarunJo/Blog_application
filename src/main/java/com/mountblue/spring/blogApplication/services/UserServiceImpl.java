package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.Authorities;
import com.mountblue.spring.blogApplication.entity.Tag;
import com.mountblue.spring.blogApplication.entity.User;
import com.mountblue.spring.blogApplication.repository.AuthoritiesRepository;
import com.mountblue.spring.blogApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private AuthoritiesRepository authoritiesRepository;
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthoritiesRepository authoritiesRepository) {
        this.userRepository = userRepository;
        this.authoritiesRepository = authoritiesRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean addUser(String username, String email, String password) {
        User userByName = userRepository.findByUserName(username);
        User userByEmail = userRepository.findByEmail(email);

        if(userByEmail == null && userByName == null) {
            String encodedPassword = passwordEncoder.encode(password);
            User user = new User(username, email, encodedPassword);

            List<Authorities> authoritiesList = authoritiesRepository.findAll();
            List<String> authoritiesName = new ArrayList<>();

            for(Authorities tempAuthorities: authoritiesList) {
                authoritiesName.add(tempAuthorities.getRole());
            }

            String givenAuthorities = "ROLE_author";

            if(authoritiesName.contains(givenAuthorities)) {
                for(Authorities theAuthorities: authoritiesList) {
                    if(theAuthorities.getRole().equals(givenAuthorities))
                    {
                        user.addAuthorities(theAuthorities);
                        break;
                    }
                }
            }
            else {
                Authorities newAuthorities = new Authorities(givenAuthorities);
//                Authorities admin = new Authorities("ROLE_admin");
                authoritiesRepository.save(newAuthorities);
//                authoritiesRepository.save(admin);
                user.addAuthorities(newAuthorities);
//                user.addAuthorities(admin);
            }

            userRepository.save(user);

            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public User authenticate(String username, String password) {
        User user = userRepository.findByUserName(username);

        if(user == null) return null;
        else if(passwordEncoder.matches(password, user.getPassword())) return user;
        else return null;
    }
}
