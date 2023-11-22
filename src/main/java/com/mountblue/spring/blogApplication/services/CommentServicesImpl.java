package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.Comment;
import com.mountblue.spring.blogApplication.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class CommentServicesImpl implements CommentServices {
    private EntityManager entityManager;

    @Autowired
    public CommentServicesImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void createComment(int postId, Comment comment) {
        Post post = entityManager.find(Post.class, postId);
        Comment theComment =  new Comment("Tarun Joshi", "tarun@gmail.com", comment.getComment());
        post.addComment(theComment);

        entityManager.merge(post);
    }

    @Override
    @Transactional
    public void deleteComment(int commentId) {
        Comment comment = entityManager.find(Comment.class, commentId);
        entityManager.remove(comment);
    }

    @Override
    public void editComment(int theId, int commentId, Model model) {
        model.addAttribute("comment", new Comment());
        model.addAttribute("commentId", commentId);
        model.addAttribute("post", entityManager.find(Post.class, theId));
    }

    @Override
    @Transactional
    public void updateComment(int commentId, String commentString) {
        Comment comment = entityManager.find(Comment.class, commentId);
        comment.setComment(commentString);

        entityManager.merge(comment);
    }
}
