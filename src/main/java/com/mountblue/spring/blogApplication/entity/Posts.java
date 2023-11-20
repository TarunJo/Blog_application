package com.mountblue.spring.blogApplication.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
public class Posts {
    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "excerpt")
    private String excerpt;
    @Column(name = "content")
    private String Content;
    @Column(name = "author")
    private String author;
    @Column(name = "published_at")
    private Date publishedAt;
    @Column(name = "is_published")
    private boolean isPublished;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "posts",
            cascade = CascadeType.ALL)
    private List<Comments> comments;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"), // field from current class
            inverseJoinColumns=@JoinColumn(name = "tag_id") // field from other class
    )
    private List<Tags> tags;

    // Constructors.
    public Posts() {}

    public Posts(String title, String excerpt, String content, String author, boolean isPublished) {
        this.title = title;
        this.excerpt = excerpt;
        Content = content;
        this.author = author;
        this.publishedAt = new Date();
        this.isPublished = isPublished;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt() {
        this.updatedAt = new Date();
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }
    // add a convenience method
    public void addTags(Tags theTag) {
        if(tags == null) {
            tags = new ArrayList<>();
        }

        tags.add(theTag);
    }

    public void  addComment(Comments comment) {
        if(comments == null) {
            comments = new ArrayList<>();
        }

        comments.add(comment);
        comment.setPosts(this);
    }

    // toString Method
    @Override
    public String toString() {
        return "Posts{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", excerpt='" + excerpt + '\'' +
                ", Content='" + Content + '\'' +
                ", author='" + author + '\'' +
                ", publishedAt=" + publishedAt +
                ", isPublished=" + isPublished +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
