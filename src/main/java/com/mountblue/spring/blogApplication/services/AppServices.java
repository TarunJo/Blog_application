package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.Post;
import com.mountblue.spring.blogApplication.entity.Tag;

import java.util.List;

public interface AppServices {
    List<Post> findAllPost();

    Post getPostById(int theId);

    void addPost(Post post, Tag tag);
}
