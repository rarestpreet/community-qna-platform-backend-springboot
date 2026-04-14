package com.project.hearmeout_backend.repository;

import com.project.hearmeout_backend.model.Vote;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@NullMarked
public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByPostIdAndUserId(Long postId, Long userId);

    Optional<Vote> findByPostIdAndUserId(Long postId, Long userId);

    void removeVoteByPostIdAndUserId(Long postId, Long userId);
}