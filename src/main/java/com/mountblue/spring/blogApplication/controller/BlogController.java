package com.mountblue.spring.blogApplication.controller;

import com.mountblue.spring.blogApplication.entity.Comment;
import com.mountblue.spring.blogApplication.entity.Tag;
import com.mountblue.spring.blogApplication.services.CommentServices;
import com.mountblue.spring.blogApplication.services.PostServices;
import com.mountblue.spring.blogApplication.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class BlogController {
    @Autowired
    PostServices postServices;

    @Autowired
    CommentServices commentServices;

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

    @GetMapping("/filter")
    public String filter() {
        return "filter";
    }
}
