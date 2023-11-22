package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.Comment;

public interface CommentServices {
    void createComment(int postId, Comment comment);

    void deleteComment(int commentId);
}
