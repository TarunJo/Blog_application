package com.mountblue.spring.blogApplication.rest;

import com.mountblue.spring.blogApplication.entity.Comment;
import com.mountblue.spring.blogApplication.services.CommentServices;
import com.mountblue.spring.blogApplication.services.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentRestController {
    CommentServices commentServices;
    PostServices postServices;


    @Autowired
    public CommentRestController(CommentServices commentServices, PostServices postServices) {
        this.commentServices = commentServices;
        this.postServices = postServices;
    }

    @GetMapping("/comments")
    public ResponseEntity<?> getAllComments() {
        List<Comment> allComments = commentServices.getAllComments();

        if(allComments.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(allComments, HttpStatus.OK);
    }

    @GetMapping("/comments/{postId}")
    public ResponseEntity<?> getAllCommentsByPostId(@PathVariable("postId") Integer postId) {
        List<Comment> allComments = commentServices.getAllCommentsByPostId(postId);

        if(allComments.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(allComments, HttpStatus.OK);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<?> getCommentByCommentId(@PathVariable("commentId") Integer commentId) {
        Comment comment = commentServices.getCommentByCommentId(commentId);

        if(comment == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteCommentByCommentId(@PathVariable("commentId") Integer commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(commentServices.getCommentByCommentId(commentId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else if(!commentServices.getCommentByCommentId(commentId).getName().
                equals(authentication.getName().toUpperCase()) &&
                !authentication.getAuthorities().toString().contains("ROLE_admin"))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        commentServices.deleteComment(commentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/comment/{postId}")
    public ResponseEntity<?> addCommentOnPost(@PathVariable("postId") Integer postId,
                                              @RequestBody Comment comment) {
        if(postServices.getPostById(postId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        commentServices.createComment(postId, comment);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<?> updateCommentByCommentId(@PathVariable("commentId") Integer commentId,
                                              @RequestBody Comment comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(commentServices.getCommentByCommentId(commentId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else if(!commentServices.getCommentByCommentId(commentId).getName().
                equals(authentication.getName().toUpperCase()) &&
            !authentication.getAuthorities().toString().contains("ROLE_admin"))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        commentServices.updateComment(commentId, comment.getComment());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
