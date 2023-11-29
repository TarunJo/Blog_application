package com.mountblue.spring.blogApplication.services;

import com.mountblue.spring.blogApplication.entity.Tag;
import com.mountblue.spring.blogApplication.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServicesImpl implements TagServices{
    private TagsRepository tagsRepository;

    @Autowired
    public TagServicesImpl(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    @Override
    public List<Tag> getAllTags() {
        return tagsRepository.findAll();
    }

    @Override
    public Tag getTagByTagId(Integer tagId) {
        Optional<Tag> byId = tagsRepository.findById(tagId);

        if(byId.isEmpty()) return null;
        return tagsRepository.findById(tagId).get();
    }

    @Override
    public void updateTagById(Integer tagId, Tag tag) {
        Tag oldTag = tagsRepository.findById(tagId).get();
        oldTag.setName(tag.getName());

        tagsRepository.save(oldTag);
    }

    @Override
    public void deleteTag(Integer tagId) {
        tagsRepository.deleteById(tagId);
    }
}
