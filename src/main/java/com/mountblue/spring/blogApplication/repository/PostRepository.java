package com.mountblue.spring.blogApplication.repository;

import com.mountblue.spring.blogApplication.entity.Post;
import com.mountblue.spring.blogApplication.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT p FROM Post p " +
            "LEFT JOIN p.tags t ON COALESCE(t.name, '') IN :tagsFilter " +
            "WHERE (COALESCE(:authorFilter, '') = '' OR LOWER(p.author) LIKE CONCAT('%', LOWER(:authorFilter), '%')) AND " +
            "(COALESCE(:tagsFilter, '') = '' OR t.name IN (:tagsFilter)) " +
            "ORDER BY " +
            "CASE " +
            "   WHEN :sortDirection IS NULL AND :sortField = 'author' THEN p.author END ASC, " +
            "CASE " +
            "   WHEN :sortDirection IS NULL AND :sortField = 'title' THEN p.title END ASC, " +
            "CASE " +
            "   WHEN :sortDirection IS NULL AND :sortField IS NULL THEN p.publishedAt END ASC, " +
            "CASE " +
            "   WHEN :sortDirection = 'desc' AND :sortField = 'author' THEN p.author END DESC, " +
            "CASE " +
            "   WHEN :sortDirection = 'desc' AND :sortField = 'title' THEN p.title END DESC, " +
            "CASE " +
            "   WHEN :sortDirection = 'desc' AND :sortField IS NULL THEN p.publishedAt END DESC"
    )
    public Page<Post> findAllCustom(
                                    @Param("authorFilter") String authorFilter,
                                    @Param("tagsFilter") List<String> tagsFilter,
                                    @Param("sortDirection") String sortDirection,
                                    @Param("sortField") String sortField,
                                    Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "LEFT JOIN p.tags t ON COALESCE(t.name, '') IN :searchValue " +
            "WHERE (COALESCE(:searchValue, '') = '' OR " +
            "LOWER(p.author) LIKE CONCAT('%', LOWER(:searchValue), '%') OR " +
            "LOWER(p.title) LIKE CONCAT('%', LOWER(:searchValue), '%') OR " +
            "LOWER(p.content) LIKE CONCAT('%', LOWER(:searchValue), '%'))"
//            "LOWER(t.name) LIKE CONCAT('%', LOWER(:searchValue), '%'))"
    )
    public Page<Post> searchByValue(@Param("searchValue")String searchValue, Pageable pageable);
}
