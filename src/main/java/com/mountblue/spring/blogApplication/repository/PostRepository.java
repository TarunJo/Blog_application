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
            "WHERE (:authorFilter IS NULL OR p.author IN :authorFilter) AND " +
            " (:tagsFilter IS NULL OR t.name IN :tagsFilter) " +
//            "AND (:publishedDateFilter IS NULL OR p.publishedAt = :publishedDateFilter) " +
            "ORDER BY " +
            "CASE WHEN :sortDirection = 'asc' THEN p.publishedAt END ASC, " +
            "CASE WHEN :sortDirection = 'desc' THEN p.publishedAt END DESC, " +
            "CASE WHEN :sortDirection IS NULL THEN p.title END"
    )
    public Page<Post> findAllCustom(
                                    @Param("authorFilter") String authorFilter,
//                                    @Param("publishedDateFilter") Date publishedDateFilter,
                                    @Param("tagsFilter") List<String> tagsFilter,
                                    @Param("sortDirection") String sortDirection,
                                    Pageable pageable);

    public Page<Post> findAllByOrderByPublishedAtDesc(Pageable pageable);

    public Page<Post> findAllByOrderByPublishedAtAsc(Pageable pageable);
}
