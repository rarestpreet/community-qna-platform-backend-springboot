package com.project.hearmeout_backend.repository;

import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.enums.PostType;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@NullMarked
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByAuthor_IdAndPostType(Long userId, PostType postType);

    List<Post> findByPostTypeAndAuthorIdNot(PostType postType, Pageable pageable, Long userId);
    List<Post> findByPostType(PostType postType, Pageable pageable);
}