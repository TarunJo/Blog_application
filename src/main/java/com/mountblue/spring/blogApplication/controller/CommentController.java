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
    @Autowired
    CommentServices commentServices;
    @Autowired
    PostServices postServices;

    @PostMapping("/createComment{postId}")
    public String createComment(@PathVariable Integer postId, @ModelAttribute("editComment") Comment comment) {
        commentServices.createComment(postId, comment);
        System.out.println("hello");

        return "redirect:/post{postId}";
    }

    @GetMapping("/deletecomment/{postId}/{commentId}")
    public String deleteComment(@PathVariable Integer postId, @PathVariable Integer commentId) {
        commentServices.deleteComment(commentId);

        return "redirect:/post{postId}";
    }

    @GetMapping("/editcomment/{postId}/{commentId}")
    public String editComment(@PathVariable Integer postId, @PathVariable Integer commentId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(postServices.getPostById(postId) == null ||
                commentServices.getCommentByCommentId(commentId) == null) {
            return "access-denied";
        }
        else if(!commentServices.getCommentByCommentId(commentId).getName()
                .equals(authentication.getName().toUpperCase())
                && !authentication.getAuthorities().toString().contains("ROLE_admin")
        ) {
            return "access-denied";
        }
        commentServices.editComment(postId, commentId, model);

        return "view-post";
    }

    @PostMapping("/updatecomment/{postId}/{commentId}")
    public String updateComment(@PathVariable Integer postId,
                                @PathVariable Integer commentId,
                                @ModelAttribute("editComment") String comment,
                                @ModelAttribute("comment") Comment cmt
    ) {
        commentServices.updateComment(commentId, comment);

        return "redirect:/post{postId}";
    }
}
