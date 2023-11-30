package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.Comment;
import org.springframework.ui.Model;

import java.util.List;

public interface CommentServices {
    void createComment(int postId, Comment comment);
    void deleteComment(int commentId);
    void editComment(int postId, int commentId, Model model);
    void updateComment(int commentId, String comment);
    List<Comment> getAllComments();
    List<Comment> getAllCommentsByPostId(Integer postId);
    Comment getCommentByCommentId(Integer commentId);
}
