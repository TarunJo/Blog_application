package com.mountblue.spring.blogApplication.controller;

import com.mountblue.spring.blogApplication.entity.Post;
import com.mountblue.spring.blogApplication.entity.Tag;
import com.mountblue.spring.blogApplication.services.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {
    private PostServices postServices;

    @Autowired
    public PostController(PostServices postServices) {
        this.postServices = postServices;
    }

    @GetMapping("/")
    public String blogPage(Model model,
                           @RequestParam(name = "directionOption", required = false) String directionOption,
                           @RequestParam(name = "fieldOption", required = false) String fieldOption,
                           @RequestParam(name = "page", defaultValue = "0") Integer page,
                           @RequestParam(name = "author", required = false) String author,
                           @RequestParam(name = "tagList", required = false) String tags,
                           @RequestParam(name = "searchValue",required = false) String searchValue) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        postServices.getAllPost(model, directionOption, fieldOption, page, author, tags, searchValue);

        return "blog-page";
    }

    @GetMapping("/post{postId}")
    public String viewPost(@PathVariable("postId") Integer postId, Model model) {
        if(postServices.getPostById(postId) == null) return "error";
        postServices.getPostById(postId, model);

        return "view-post";
    }

    @GetMapping("/createPost")
    public String createPost(Model model) {
        postServices.createPost(model);

        return "new-post";
    }

    @PostMapping("/addPost")
    public String addPost(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag) {
        postServices.addPost(post, tag);

        return "redirect:/";
    }

    @GetMapping("/editPost{postId}")
    public String editPost(@PathVariable("postId") Integer postId, Model model) {
        if(postServices.getPostById(postId) == null) return "error";
        postServices.editPost(model, postId);

        return "edit-post";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute("post") Post post, @ModelAttribute("tg") String tags) {
        postServices.updatePost(post, tags);

        return "redirect:/";
    }

    @GetMapping("/deletePost{postId}")
    public String deletePost(@PathVariable("postId") Integer postId) {
        postServices.deletePost(postId);

        return "redirect:/";
    }
}
