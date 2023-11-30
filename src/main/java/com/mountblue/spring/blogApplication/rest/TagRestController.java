package com.mountblue.spring.blogApplication.rest;

import com.mountblue.spring.blogApplication.entity.Tag;
import com.mountblue.spring.blogApplication.services.PostServices;
import com.mountblue.spring.blogApplication.services.TagServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TagRestController {
    private TagServices tagServices;
    private PostServices postServices;

    @Autowired
    public TagRestController(TagServices tagServices, PostServices postServices) {
        this.tagServices = tagServices;
        this.postServices = postServices;
    }

    @GetMapping("/tags")
    public ResponseEntity<?> getAllTags() {
        List<Tag> tags = tagServices.getAllTags();

        if(tags.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/tag/{tagId}")
    public ResponseEntity<?> getTagByTagId(@PathVariable("tagId") Integer tagId) {
        Tag tag = tagServices.getTagByTagId(tagId);

        if(tag == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @GetMapping("/tags/{postId}")
    public ResponseEntity<?> getAllTagsByPostId(@PathVariable("postId") Integer postId) {
        if(postServices.getPostById(postId) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<Tag> tags = postServices.getPostById(postId).getTags();

        if(tags.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @PostMapping("/tag")
    public ResponseEntity<?> addTagToPost(@RequestParam(value = "postId", defaultValue = "") Integer postId,
                                          @RequestBody Tag tag){
        if(postId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(postServices.getPostById(postId) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else if(!postServices.getPostById(postId).getAuthor().equals(authentication.getName()) &&
                !authentication.getAuthorities().toString().contains("ROLE_admin")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        postServices.editPost(postId, tag);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/tag")
    public ResponseEntity<?> updateTag(@RequestParam(value = "tagId", defaultValue = "") Integer tagId,
                                       @RequestBody Tag tag){
        if(tagId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!authentication.getAuthorities().toString().contains("ROLE_admin")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if(tagServices.getTagByTagId(tagId) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        tagServices.updateTagById(tagId, tag);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/tag")
    public ResponseEntity<?> DeleteTag(@RequestParam(value = "tagId", defaultValue = "") Integer tagId){
        if(tagId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!authentication.getAuthorities().toString().contains("ROLE_admin")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if(tagServices.getTagByTagId(tagId) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        tagServices.deleteTag(tagId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
