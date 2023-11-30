package com.mountblue.spring.blogApplication.rest;

import com.mountblue.spring.blogApplication.entity.User;
import com.mountblue.spring.blogApplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/authenticate")
    public ResponseEntity<?> authentication(@RequestParam("username") String username,
                                            @RequestParam("password") String password) {
        User user = userService.authenticate(username, password);

        if(user == null)
            return new ResponseEntity<>("UnAuthorized: Authentication Failed", HttpStatus.UNAUTHORIZED);
        else{
            user.setPost(null);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }
}
