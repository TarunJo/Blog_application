package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.Comment;
import com.mountblue.spring.blogApplication.entity.Post;
import com.mountblue.spring.blogApplication.entity.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppServicesImpl implements AppServices {
    private EntityManager entityManager;

    @Autowired
    public AppServicesImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void addPost(Post post, Tag tag) {
        Post thePost = new Post(post.getTitle(), post.getExcerpt(), post.getContent(), post.getAuthor());
        TypedQuery<Tag> query = entityManager.createQuery("from Tag", Tag.class);
        List<Tag> tags = query.getResultList();
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
                entityManager.persist(createTag);
                thePost.addTags(createTag);
            }
        }

        entityManager.persist(thePost);
    }

    @Override
    @Transactional
    public void updatePost(Post post, String tags) {
        Post newPost = new Post(post.getTitle(), post.getExcerpt(), post.getContent(), post.getAuthor());
        newPost.setId(post.getId());

        TypedQuery<Tag> query = entityManager.createQuery("from Tag", Tag.class);
        List<Tag> tag = query.getResultList();
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
                        System.out.println("2nd layer");
                        newPost.addTags(theTag);
                        break;
                    }
                }
            }
            else {
                Tag createTag = new Tag(tempTag);
                entityManager.persist(createTag);
                newPost.addTags(createTag);
            }
        }

        entityManager.merge(newPost);
    }

    @Override
    public void createModels(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("tag", new Tag());
    }

    @Override
    public void editPost(Model model, int postId) {
        Post post = entityManager.find(Post.class, postId);
        model.addAttribute("post", post);

        List<Tag> tags = post.getTags();
        String theTags = "";

        for(Tag tag: tags) {
            theTags += tag.getName()+", ";
        }

        if(!theTags.isEmpty()) {
            theTags = theTags.substring(0, theTags.length()-2);
        }

        model.addAttribute("tags", theTags);
    }

    @Override
    @Transactional
    public void deletePost(int postId) {
        Post post = entityManager.find(Post.class, postId);
        entityManager.remove(post);
    }

    @Override
    @Transactional
    public void createComment(int postId, Comment comment) {
        Post post = entityManager.find(Post.class, postId);
        Comment theComment =  new Comment("Tarun Joshi", "tarun@gmail.com", comment.getComment());

        post.addComment(theComment);

        entityManager.merge(post);
    }

    @Override
    public void findAllPost(Model model) {
        TypedQuery<Post> query = entityManager.createQuery("from Post", Post.class);
        model.addAttribute("posts", query.getResultList());
    }

    @Override
    public void getPostById(int theId, Model model) {
        model.addAttribute("comment", new Comment());
        model.addAttribute("post", entityManager.find(Post.class, theId));
    }
}
