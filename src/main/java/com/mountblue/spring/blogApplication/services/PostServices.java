package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.Post;
import com.mountblue.spring.blogApplication.entity.Tag;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;

public interface PostServices {
    void findAllPost(Model model, String directionOption, String fieldOption, Integer page,
                     String author,
                     String tags, String searchValue);

    void getPostById(int theId, Model model);

    void addPost(Post post, Tag tag);

    void updatePost(Post post, String tags);

    void createModels(Model model);

    void editPost(Model model, int postId);

    void deletePost(int postId);

}
