package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.Tag;

import java.util.List;

public interface TagServices {
    List<Tag> getAllTags();
    Tag getTagByTagId(Integer tagId);
    void updateTagById(Integer tagId, Tag tag);
    void deleteTag(Integer tagId);
}
