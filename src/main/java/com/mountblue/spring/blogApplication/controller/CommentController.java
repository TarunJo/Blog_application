package com.mountblue.spring.blogApplication.controller;

import com.mountblue.spring.blogApplication.entity.Comment;
import com.mountblue.spring.blogApplication.services.CommentServices;
import org.springframework.beans.factory.annotation.Autowired;
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
