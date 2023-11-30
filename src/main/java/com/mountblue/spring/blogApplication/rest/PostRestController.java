package com.mountblue.spring.blogApplication.rest;

import com.mountblue.spring.blogApplication.entity.Post;
import com.mountblue.spring.blogApplication.entity.Tag;
import com.mountblue.spring.blogApplication.services.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostRestController {
    private PostServices postServices;

    @Autowired
    public PostRestController(PostServices postServices) {
        this.postServices = postServices;
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPost() {
        List<Post> allPost = postServices.getALlPost();

        if(allPost.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(allPost, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable("postId") Integer id) {
        Post post = postServices.getPostById(id);

        if (post == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> deletePostById(@PathVariable("postId") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(postServices.getPostById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else if(!postServices.getPostById(id).getAuthor().equals(authentication.getName()) &&
                !authentication.getAuthorities().toString().contains("ROLE_admin")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        else {
            postServices.deletePost(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable("postId") Integer id, @RequestBody Post post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(postServices.getPostById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else if(!postServices.getPostById(id).getAuthor().equals(authentication.getName()) &&
                !authentication.getAuthorities().toString().contains("ROLE_admin")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        else {
            String tags = "";
            for(Tag tempTag: post.getTags()) {
                tags += tempTag.getName()+",";
            }
            tags = tags.substring(0, tags.length()-1);
            post.setId(id);

            postServices.updatePost(post, tags);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        List<Tag> tags = post.getTags();
        Tag tag = new Tag("");
        for( Tag tempTag: tags) {
            tag.setName(tag.getName()+","+tempTag.getName());
        }
        tag.setName(tag.getName().substring(1, tag.getName().length()));
        postServices.addPost(post, tag);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/customView")
    public ResponseEntity<?> customView(@RequestParam(value = "direction", defaultValue = "") String direction,
                                        @RequestParam(value = "field", defaultValue = "") String field,
                                        @RequestParam(value = "author", defaultValue = "") String author,
                                        @RequestParam(value = "tags", defaultValue = "") String tags,
                                        @RequestParam(value = "searchValue", defaultValue = "") String searchValue,
                                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
        Page<Post> posts = postServices.getAllPost(
                direction, field, page, author,
                tags, searchValue, pageSize
        );

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
