package com.project.hearmeout_backend.repository;

import com.project.hearmeout_backend.model.Vote;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<@NonNull Vote, @NonNull Long> {
    boolean existsByPostIdAndUserId(Long postId, Long userId);

    Optional<Vote> findByPostIdAndUserId(Long postId, Long userId);

    void removeVoteByPostIdAndUserId(Long postId, Long userId);
}