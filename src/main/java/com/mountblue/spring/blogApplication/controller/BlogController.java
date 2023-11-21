package com.mountblue.spring.blogApplication.controller;

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
        List<Post> posts = appServices.findAllPost();
        model.addAttribute("posts", posts);

        return "blog-page";
    }

    @GetMapping("/post{postId}")
    public String viewPost(@PathVariable int postId, Model model) {
        Post post = appServices.getPostById(postId);
        model.addAttribute("post", post);

        return "view-post";
    }

    @GetMapping("/newpost")
    public String createPost(Model model) {
        Post post = new Post();
        Tag tag = new Tag();

        model.addAttribute("post", post);
        model.addAttribute("tag", tag);

        return "new-post";
    }

    @PostMapping("/createpost")
    public String postCreation(@ModelAttribute("post") Post post, @ModelAttribute("tag") Tag tag) {
        appServices.addPost(post, tag);
        return "blog-page";
    }

}
