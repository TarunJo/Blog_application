package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.Comment;
import com.mountblue.spring.blogApplication.entity.Post;
import com.mountblue.spring.blogApplication.entity.Tag;
import com.mountblue.spring.blogApplication.repository.CommentRepository;
import com.mountblue.spring.blogApplication.repository.PostRepository;
import com.mountblue.spring.blogApplication.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostServicesImpl implements PostServices {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private TagsRepository tagsRepository;
    @Autowired
    public PostServicesImpl(CommentRepository commentRepository,
                            PostRepository postRepository, TagsRepository tagsRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.tagsRepository = tagsRepository;
    }

    @Override
    public void addPost(Post post, Tag tag) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(authentication);

        Post thePost = new Post(post.getTitle().trim(),
                post.getExcerpt().trim(),
                post.getContent().trim(), authentication.getName());
        List<Tag> tags = tagsRepository.findAll();
        List<String> tagName = new ArrayList<>();

        for(Tag tempTag: tags) {
            tagName.add(tempTag.getName());
        }

        String[] givenTags = tag.getName().split(",");
        for(String tempTag: givenTags) {
            tempTag = tempTag.trim();
            if(tagName.contains(tempTag)) {
                for(Tag theTag: tags) {
                    if(theTag.getName().equals(tempTag))
                    {
                        System.out.println("2nd layer");
                        thePost.addTags(theTag);
                        break;
                    }
                }
            }
            else {
                Tag createTag = new Tag(tempTag);
                tagsRepository.save(createTag);
                thePost.addTags(createTag);
            }
        }

        postRepository.save(thePost);
    }

    @Override
    public void updatePost(Post post, String tags) {
        Post newPost = new Post(post.getTitle().trim(),
                post.getExcerpt().trim(),
                post.getContent().trim(),
                post.getAuthor().trim());
        newPost.setId(post.getId());

        List<Tag> tag = tagsRepository.findAll();
        List<String> tagName = new ArrayList<>();

        for(Tag tempTag: tag) {
            tagName.add(tempTag.getName());
        }

        String[] givenTags = tags.split(",");
        for(String tempTag: givenTags) {
            tempTag = tempTag.trim();
            if(tagName.contains(tempTag)) {
                for(Tag theTag: tag) {
                    if(theTag.getName().equals(tempTag))
                    {
                        newPost.addTags(theTag);
                        break;
                    }
                }
            }
            else {
                Tag createTag = new Tag(tempTag);
                tagsRepository.save(createTag);
                newPost.addTags(createTag);
            }
        }

        postRepository.save(newPost);
    }

    @Override
    public void createPost(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("tag", new Tag());
    }

    @Override
    public void editPost(Model model, int postId) {
        Post post = postRepository.findById(postId).get();
        model.addAttribute("post", post);

        List<Tag> tags = post.getTags();
        String theTags = "";

        for(Tag tag: tags) {
            theTags += tag.getName()+", ";
        }

        if(!theTags.isEmpty()) {
            theTags = theTags.substring(0, theTags.length()-2);
        }

        model.addAttribute("theTags", theTags);
    }

    @Override
    public void deletePost(int postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public void findAllPost(Model model,
                            String directionOption,
                            String fieldOption,
                            Integer page,
                            String author,
                            String tags,
                            String searchValue
    ) {

        author = (author == "" ? null : author);
        tags = (tags == "" ? null : tags);
        directionOption = (directionOption == null || directionOption.equals("asc") ? null : directionOption);
        fieldOption = (fieldOption == null || fieldOption.equals("published") ? null : fieldOption);

        Pageable pageable = PageRequest.of(page, 6);
        List<String> stringTag = new ArrayList<>();

        if(tags == null) {
            stringTag = null;
        }
        else {
            for(String tempTag: tags.split(","))
            {
                stringTag.add(tempTag.trim());
            }
        }

        Page<Post> postPage;

        if(searchValue == null)
            postPage = postRepository.findAllCustom( author, stringTag, directionOption, fieldOption, pageable);
        else
            postPage = postRepository.searchByValue(searchValue, pageable);

        model.addAttribute("posts", postPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());
        model.addAttribute("author", author);
        model.addAttribute("tagList", tags);
        model.addAttribute("searchValue", searchValue);



        if(directionOption == null) {
            model.addAttribute("directionOption", "asc");
        }
        else {
            model.addAttribute("directionOption", directionOption);
        }

        if(fieldOption == null) {
            model.addAttribute("fieldOption", "published");
        }
        else {
            model.addAttribute("fieldOption", fieldOption);
        }
    }

    @Override
    public void getPostById(int theId, Model model) {
        model.addAttribute("editComment", new Comment());
        model.addAttribute("post", postRepository.findById(theId).get());
    }
}
