package com.project.hearmeout_backend.repository;

import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.enums.PostType;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepository extends JpaRepository<@NonNull Post,@NonNull  Long> {

    List<Post> findByAuthor_IdAndPostType(Long userId, PostType postType);

    List<Post> findByPostType(PostType postType, Pageable pageable);
}