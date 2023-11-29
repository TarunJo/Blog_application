package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.Post;
import com.mountblue.spring.blogApplication.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.util.List;

public interface PostServices {
    void getAllPost(Model model, String directionOption, String fieldOption, Integer page,
                    String author,
                    String tags, String searchValue);

    Page<Post> getAllPost(String directionOption, String fieldOption, Integer page,
                          String author,
                          String tags, String searchValue, Integer pageSize);

    List<Post> getALlPost();

    Post getPostById(Integer id);

    void getPostById(int theId, Model model);

    void addPost(Post post, Tag tag);

    void updatePost(Post post, String tags);

    void createPost(Model model);

    void editPost(Model model, int postId);
    void editPost(int postId, Tag tag);

    void deletePost(int postId);
}
