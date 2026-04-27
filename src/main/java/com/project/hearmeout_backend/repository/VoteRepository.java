package com.project.hearmeout_backend.repository;

import com.project.hearmeout_backend.model.Post;
import com.project.hearmeout_backend.model.Vote;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@NullMarked
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByPostIdAndUserId(Long postId, Long userId);

    Optional<Vote> findByPostIdAndUserId(Long postId, Long userId);

    void removeVoteByPostIdAndUserId(Long postId, Long userId);

    List<Vote> post(Post post);

    List<Vote> findAllByPostId(Long postId);
}