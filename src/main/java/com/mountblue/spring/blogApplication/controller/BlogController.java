package com.mountblue.spring.blogApplication.controller;

import com.mountblue.spring.blogApplication.entity.Comment;
import com.mountblue.spring.blogApplication.entity.Tag;
import com.mountblue.spring.blogApplication.services.CommentServices;
import com.mountblue.spring.blogApplication.services.PostServices;
import com.mountblue.spring.blogApplication.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BlogController {
    @Autowired
    PostServices postServices;

    @Autowired
    CommentServices commentServices;

    @GetMapping("/")
    public String blogPage(Model model, @RequestParam(name = "post", required = false) List<Post> post,
                           @RequestParam(name = "defaultOption", required = false) String defaultOption) {
        postServices.findAllPost(model, post, defaultOption);

        return "blog-page";
    }

    @GetMapping("/post{postId}")
    public String viewPost(@PathVariable int postId, Model model) {
        postServices.getPostById(postId, model);

        return "view-post";
    }

    @GetMapping("/newpost")
    public String createPost(Model model) {
        postServices.createModels(model);

        return "new-post";
    }

    @PostMapping("/createpost")
    public String postCreation(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag) {
        postServices.addPost(post, tag);

        return "redirect:/";
    }

    @GetMapping("/editpost{postId}")
    public String editPost(@PathVariable int postId, Model model) {
        postServices.editPost(model, postId);

        return "edit-post";
    }

    @PostMapping("/updatepost")
    public String updatePost(@ModelAttribute("post") Post post, @ModelAttribute("tg") String tags) {
        postServices.updatePost(post, tags);

        return "redirect:/";
    }

    @GetMapping("/deletepost{postId}")
    public String deletePost(@PathVariable int postId) {
        postServices.deletePost(postId);

        return "redirect:/";
    }

    @PostMapping("/createComment{postId}")
    public String createComment(@PathVariable int postId, @ModelAttribute("editComment") Comment comment) {
        commentServices.createComment(postId, comment);
        System.out.println("hello");

        return "redirect:/post{postId}";
    }

    @GetMapping("/deletecomment/{postId}/{commentId}")
    public String deleteComment(@PathVariable int postId, @PathVariable int commentId) {
        commentServices.deleteComment(commentId);

        return "redirect:/post{postId}";
    }

    @GetMapping("/editcomment/{postId}/{commentId}")
    public String editComment(@PathVariable int postId, @PathVariable int commentId, Model model) {
        commentServices.editComment(postId, commentId, model);

        return "view-post";
    }

    @GetMapping("/updatecomment/{postId}/{commentId}")
    public String updateComment(@PathVariable int postId, @PathVariable int commentId, @ModelAttribute("editComment") String comment, @ModelAttribute("comment") Comment cmt) {
        commentServices.updateComment(commentId, comment);

        return "redirect:/post{postId}";
    }

    @GetMapping("/sorting")
    public String sorting(@ModelAttribute("selectedOption") String option, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("defaultOption", option);
        if(option.equals("PublishedAtDesc")) {
            redirectAttributes.addAttribute("post", postServices.sortByPublishedAtDesc());

            return "redirect:/";
        }
        else if(option.equals("PublishedAtAsc")) {
            redirectAttributes.addAttribute("post", postServices.sortByPublishedAtAsc());
            
            return "redirect:/";
        }
        else {
            return "redirect:/";
        }
    }

}
