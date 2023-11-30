package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.Comment;
import com.mountblue.spring.blogApplication.entity.Post;
import com.mountblue.spring.blogApplication.entity.Tag;
import com.mountblue.spring.blogApplication.entity.User;
import com.mountblue.spring.blogApplication.repository.CommentRepository;
import com.mountblue.spring.blogApplication.repository.PostRepository;
import com.mountblue.spring.blogApplication.repository.TagsRepository;
import com.mountblue.spring.blogApplication.repository.UserRepository;
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
import java.util.Optional;

@Service
public class PostServicesImpl implements PostServices {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private TagsRepository tagsRepository;
    private UserRepository userRepository;

    @Autowired
    public PostServicesImpl(CommentRepository commentRepository,
                            PostRepository postRepository,
                            TagsRepository tagsRepository,
                            UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.tagsRepository = tagsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addPost(Post post, Tag tag) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(authentication.getName());
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

        thePost.setUserPost(user);
        postRepository.save(thePost);
    }

    @Override
    public void updatePost(Post post, String tags) {
        Post oldPost = postRepository.getById(post.getId());
        Post newPost = new Post(post.getTitle().trim(),
                post.getExcerpt().trim(),
                post.getContent().trim(),
                oldPost.getAuthor().trim());
        newPost.setId(post.getId());
        User user = userRepository.findByUserName(oldPost.getAuthor().trim());
        newPost.setUserPost(user);

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
    public void editPost(int postId, Tag tag) {
        Post post = postRepository.getReferenceById(postId);
        Tag newTag = new Tag(tag.getName());

        if(tagsRepository.findByName(newTag.getName()) == null)
            tagsRepository.save(newTag);

        post.addTags(newTag);
        postRepository.save(post);
    }

    @Override
    public void deletePost(int postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getName().equals(postRepository.findById(postId).get().getAuthor())
                || authentication.getName().equals("admin"))
            postRepository.deleteById(postId);
    }

    @Override
    public List<Post> getALlPost() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(Integer id) {
        Optional<Post> byId = postRepository.findById(id);

        if(byId.isEmpty())
            return null;

        return postRepository.findById(id).get();
    }

    @Override
    public void getAllPost(Model model,
                           String directionOption,
                           String fieldOption,
                           Integer page,
                           String author,
                           String tags,
                           String searchValue) {
        Page<Post> postPage;
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
    public Page<Post> getAllPost(String directionOption,
                                 String fieldOption,
                                 Integer page,
                                 String author,
                                 String tags,
                                 String searchValue,
                                 Integer pageSize) {
        author = (author == "" ? null : author);
        tags = (tags == "" ? null : tags);
        directionOption = (directionOption == null || directionOption.equals("asc") ? null : directionOption);
        fieldOption = (fieldOption == null || fieldOption.equals("published") ? null : fieldOption);
        searchValue = searchValue == null || searchValue.equals("") ? null :searchValue;
        Pageable pageable = PageRequest.of(page, pageSize);
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

        if(searchValue == null)
            return postRepository.findAllCustom( author, stringTag, directionOption, fieldOption, pageable);
        else
            return postRepository.searchByValue(searchValue, pageable);
    }

    @Override
    public void getPostById(int theId, Model model) {
        model.addAttribute("editComment", new Comment());
        model.addAttribute("post", postRepository.findById(theId).get());
    }
}
