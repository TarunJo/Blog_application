package com.mountblue.spring.blogApplication.repository;

import com.mountblue.spring.blogApplication.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tag, Integer> {
    Tag findByName(String name);
}
