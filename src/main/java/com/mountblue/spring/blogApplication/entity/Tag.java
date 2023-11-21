package com.mountblue.spring.blogApplication.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tags")
public class Tag {
    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "tag_id"), // field from current class
            inverseJoinColumns=@JoinColumn(name = "post_id") // field from other class
    )
    private List<Post> posts;

    // Constructors
    public Tag() {}

    public Tag(String name) {
        this.name = name;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    // add a convenience method
    public void addPost(Post thePost) {
        if(posts == null) {
            posts = new ArrayList<>();
        }

        posts.add(thePost);
    }

    // toString Method
    @Override
    public String toString() {
        return "Tags{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
