package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.Comment;
import com.mountblue.spring.blogApplication.entity.Post;
import com.mountblue.spring.blogApplication.entity.Tag;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AppServices {
    void findAllPost(Model model);

    void getPostById(int theId, Model model);

    void addPost(Post post, Tag tag);

    void updatePost(Post post, String tags);

    void createModels(Model model);

    void editPost(Model model, int postId);

    void deletePost(int postId);

    void createComment(int postId, Comment comment);
}
