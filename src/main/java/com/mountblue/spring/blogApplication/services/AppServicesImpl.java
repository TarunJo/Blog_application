package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.Post;
import com.mountblue.spring.blogApplication.entity.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Post> findAllPost() {
        TypedQuery<Post> query = entityManager.createQuery("from Post", Post.class);

        return query.getResultList();
    }

    @Override
    public Post getPostById(int theId) {
        return entityManager.find(Post.class, theId);
    }
}
