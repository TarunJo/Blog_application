package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.Comment;
import com.mountblue.spring.blogApplication.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
