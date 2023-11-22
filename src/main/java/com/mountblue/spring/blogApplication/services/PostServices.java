package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.Post;
import com.mountblue.spring.blogApplication.entity.Tag;
import org.springframework.ui.Model;

public interface PostServices {
    void findAllPost(Model model);

    void getPostById(int theId, Model model);

    void addPost(Post post, Tag tag);

    void updatePost(Post post, String tags);

    void createModels(Model model);

    void editPost(Model model, int postId);

    void deletePost(int postId);
}