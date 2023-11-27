package com.mountblue.spring.blogApplication.controller;

import com.mountblue.spring.blogApplication.entity.Post;
import com.mountblue.spring.blogApplication.entity.Tag;
import com.mountblue.spring.blogApplication.services.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {
    @Autowired
    PostServices postServices;

    @GetMapping("/")
    public String blogPage(Model model,
                           @RequestParam(name = "directionOption", required = false) String directionOption,
                           @RequestParam(name = "fieldOption", required = false) String fieldOption,
                           @RequestParam(name = "page", defaultValue = "0") Integer page,
                           @RequestParam(name = "author", required = false) String author,
                           @RequestParam(name = "tagList", required = false) String tags,
                           @RequestParam(name = "searchValue",required = false) String searchValue
    ) {
        postServices.findAllPost(model, directionOption, fieldOption, page, author, tags, searchValue);

        return "blog-page";
    }

    @GetMapping("/post{postId}")
    public String viewPost(@PathVariable Integer postId, Model model) {
        postServices.getPostById(postId, model);

        return "view-post";
    }

    @GetMapping("/createpost")
    public String createPost(Model model) {
        postServices.createPost(model);

        return "new-post";
    }

    @PostMapping("/addpost")
    public String addPost(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag) {
        postServices.addPost(post, tag);

        return "redirect:/";
    }

    @GetMapping("/editpost{postId}")
    public String editPost(@PathVariable Integer postId, Model model) {
        postServices.editPost(model, postId);

        return "edit-post";
    }

    @PostMapping("/updatepost")
    public String updatePost(@ModelAttribute("post") Post post, @ModelAttribute("tg") String tags) {
        postServices.updatePost(post, tags);

        return "redirect:/";
    }

    @GetMapping("/deletepost{postId}")
    public String deletePost(@PathVariable Integer postId) {
        postServices.deletePost(postId);

        return "redirect:/";
    }
}
