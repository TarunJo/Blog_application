package com.mountblue.spring.blogApplication.repository;

import com.mountblue.spring.blogApplication.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByOrderByPublishedAtDesc();

    List<Post> findAllByOrderByPublishedAtAsc();
}
