package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.Comment;
import com.mountblue.spring.blogApplication.entity.Post;
import com.mountblue.spring.blogApplication.repository.CommentRepository;
import com.mountblue.spring.blogApplication.repository.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.print.attribute.standard.PageRanges;
import java.util.List;

@Service
public class CommentServicesImpl implements CommentServices {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    @Autowired
    public CommentServicesImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void createComment(int postId, Comment comment) {
        Post post = postRepository.findById(postId).get();
        Comment theComment =  new Comment("Tarun Joshi", "tarun@gmail.com", comment.getComment());
        post.addComment(theComment);

        postRepository.save(post);
    }

    @Override
    public void deleteComment(int commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public void editComment(int theId, int commentId, Model model) {
        model.addAttribute("editComment", new Comment());
        model.addAttribute("commentId", commentId);
        model.addAttribute("post", postRepository.findById(theId).get());
    }

    @Override
    public void updateComment(int commentId, String commentString) {
        Comment comment = commentRepository.findById(commentId).get();
        comment.setComment(commentString);

        commentRepository.save(comment) ;
    }
}
