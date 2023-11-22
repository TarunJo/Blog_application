package com.mountblue.spring.blogApplication.controller;

import com.mountblue.spring.blogApplication.entity.Comment;
import com.mountblue.spring.blogApplication.entity.Tag;
import com.mountblue.spring.blogApplication.services.CommentServices;
import com.mountblue.spring.blogApplication.services.PostServices;
import com.mountblue.spring.blogApplication.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlogController {
    @Autowired
    PostServices postServices;

    @Autowired
    CommentServices commentServices;

    @GetMapping("/")
    public String blogPage(Model model) {
        postServices.findAllPost(model);

        return "blog-page";
    }

    @GetMapping("/post{postId}")
    public String viewPost(@PathVariable int postId, Model model) {
        postServices.getPostById(postId , model);

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
    public String updatePost(@ModelAttribute("post") Post post, @ModelAttribute("tags") String tags) {
        postServices.updatePost(post, tags);

        return "redirect:/";
    }

    @GetMapping("/deletepost{postId}")
    public String deletePost(@PathVariable int postId) {
        postServices.deletePost(postId);

        return "redirect:/";
    }

    @PostMapping("/createComment{postId}")
    public String createComment(@PathVariable int postId, @ModelAttribute("comment") Comment comment) {
        commentServices.createComment(postId, comment);

        return "redirect:/post{postId}";
    }

    @GetMapping("/deletecomment/{postId}/{commentId}")
    public String deleteComment(@PathVariable int postId, @PathVariable int commentId) {
        commentServices.deleteComment(commentId);

        return "redirect:/post{postId}";
    }
}
