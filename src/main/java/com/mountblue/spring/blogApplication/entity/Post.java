package com.mountblue.spring.blogApplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "excerpt",length = 1000)
    private String excerpt;
    @Column(name = "content",columnDefinition = "TEXT")
    private String content;
    @Column(name = "author")
    private String author;
    @Column(name = "published_at", updatable = false)
    @CreationTimestamp
    private Date publishedAt;
    @Column(name = "is_published", updatable = false)
    private boolean isPublished;
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post",
            cascade = CascadeType.ALL)
    private List<Comment> comments;
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User userPost;
    @ManyToMany()
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"), // field from current class
            inverseJoinColumns=@JoinColumn(name = "tag_id") // field from other class
    )
    private List<Tag> tags;

    public Post() {}

    public Post(String title, String excerpt, String content, String author) {
        this.title = title;
        this.excerpt = excerpt;
        this.content = content;
        this.author = author;
        this.publishedAt = new Date();
        this.isPublished = true;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public User getUserPost() {
        return userPost;
    }

    public void setUserPost(User userPost) {
        this.userPost = userPost;
    }

    public void addTags(Tag theTag) {
        if(tags == null) {
            tags = new ArrayList<>();
        }

        tags.add(theTag);
    }

    public void  addComment(Comment comment) {
        if(comments == null) {
            comments = new ArrayList<>();
        }

        comments.add(comment);
        comment.setPost(this);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", excerpt='" + excerpt + '\'' +
                ", Content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", publishedAt=" + publishedAt +
                ", isPublished=" + isPublished +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
