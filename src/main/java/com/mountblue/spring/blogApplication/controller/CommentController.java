package com.mountblue.spring.blogApplication.controller;

import com.mountblue.spring.blogApplication.entity.Comment;
import com.mountblue.spring.blogApplication.services.CommentServices;
import com.mountblue.spring.blogApplication.services.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    private CommentServices commentServices;
    private PostServices postServices;

    @Autowired
    public CommentController(CommentServices commentServices, PostServices postServices) {
        this.commentServices = commentServices;
        this.postServices = postServices;
    }

    @PostMapping("/createComment{postId}")
    public String createComment(@PathVariable("postId") Integer postId,
                                @ModelAttribute("editComment") Comment comment) {
        commentServices.createComment(postId, comment);

        return "redirect:/post{postId}";
    }

    @GetMapping("/deleteComment/{postId}/{commentId}")
    public String deleteComment(@PathVariable("postId") Integer postId,
                                @PathVariable("commentId") Integer commentId) {
        commentServices.deleteComment(commentId);

        return "redirect:/post{postId}";
    }

    @GetMapping("/editComment/{postId}/{commentId}")
    public String editComment(@PathVariable("postId") Integer postId,
                              @PathVariable("commentId") Integer commentId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(postServices.getPostById(postId) == null ||
                commentServices.getCommentByCommentId(commentId) == null) {
            return "error";
        }
        else if(!commentServices.getCommentByCommentId(commentId).getName()
                .equals(authentication.getName().toUpperCase())
                && !authentication.getAuthorities().toString().contains("ROLE_admin")) {
            return "error";
        }
        commentServices.editComment(postId, commentId, model);

        return "view-post";
    }

    @PostMapping("/updateComment/{postId}/{commentId}")
    public String updateComment(@PathVariable("commentId") Integer commentId,
                                @ModelAttribute("editComment") String comment) {
        commentServices.updateComment(commentId, comment);

        return "redirect:/post{postId}";
    }
}
