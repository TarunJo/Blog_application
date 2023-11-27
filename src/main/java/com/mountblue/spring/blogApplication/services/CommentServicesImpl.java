package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.Comment;
import com.mountblue.spring.blogApplication.entity.Post;
import com.mountblue.spring.blogApplication.entity.User;
import com.mountblue.spring.blogApplication.repository.CommentRepository;
import com.mountblue.spring.blogApplication.repository.PostRepository;
import com.mountblue.spring.blogApplication.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.print.attribute.standard.PageRanges;
import java.util.List;

@Service
public class CommentServicesImpl implements CommentServices {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;
    @Autowired
    public CommentServicesImpl(CommentRepository commentRepository,
                               PostRepository postRepository,
                               UserRepository userRepository
    ) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createComment(int postId, Comment comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Post post = postRepository.findById(postId).get();
        User currentUser = userRepository.findByUserName(authentication.getName());
        Comment theComment =  new Comment(authentication.getName().toUpperCase(),
                currentUser.getEmail(),
                comment.getComment());

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
