package com.project.hearmeout_backend.repository;
import com.project.hearmeout_backend.dto.response.comment_response.CommentResponseDTO;
import com.project.hearmeout_backend.dto.response.user_response.UserCommentResponseDTO;
import com.project.hearmeout_backend.model.Comment;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@NullMarked
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("""
            SELECT new com.project.hearmeout_backend.dto.response.user_response.UserCommentResponseDTO(
                    c.body, COALESCE(p.id, p.parent.id), p.body, c.updatedAt
                    )
            FROM Comment c
            JOIN c.post p
            WHERE c.author.id = :userId
            """)
    List<UserCommentResponseDTO> findUserCommentsByUsername(@Param("username") String username);

    @Query("""
        SELECT new com.project.hearmeout_backend.dto.response.comment_response.CommentResponseDTO(
            c.id, c.body, c.author.username, p.id, c.updatedAt
        )
        FROM Comment c
        JOIN c.post p
        WHERE c.post.id = :postId
    """)
    List<CommentResponseDTO> findCommentsDTOByPostId(@Param("postId") Long postId);
}