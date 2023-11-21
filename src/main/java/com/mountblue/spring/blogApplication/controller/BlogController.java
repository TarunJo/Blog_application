package com.mountblue.spring.blogApplication.controller;

import com.mountblue.spring.blogApplication.entity.Comment;
import com.mountblue.spring.blogApplication.entity.Tag;
import com.mountblue.spring.blogApplication.services.AppServices;
import com.mountblue.spring.blogApplication.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BlogController {
    @Autowired
    AppServices appServices;

    @GetMapping("/")
    public String blogPage(Model model) {
        appServices.findAllPost(model);

        return "blog-page";
    }

    @GetMapping("/post{postId}")
    public String viewPost(@PathVariable int postId, Model model) {
        appServices.getPostById(postId , model);

        return "view-post";
    }

    @GetMapping("/newpost")
    public String createPost(Model model) {
        appServices.createModels(model);

        return "new-post";
    }

    @PostMapping("/createpost")
    public String postCreation(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag) {
        appServices.addPost(post, tag);

        return "redirect:/";
    }

    @GetMapping("/editpost{postId}")
    public String editPost(@PathVariable int postId, Model model) {
        appServices.editPost(model, postId);

        return "edit-post";
    }

    @PostMapping("/updatepost")
    public String updatePost(@ModelAttribute("post") Post post, @ModelAttribute("tags") String tags) {
        appServices.updatePost(post, tags);

        return "redirect:/";
    }

    @GetMapping("/deletepost{postId}")
    public String deletePost(@PathVariable int postId) {
        appServices.deletePost(postId);

        return "redirect:/";
    }

    @PostMapping("/createComment{postId}")
    public String createComment(@PathVariable int postId, @ModelAttribute("comment") Comment comment) {
        appServices.createComment(postId, comment);

        return "redirect:/post{postId}";
    }
}
