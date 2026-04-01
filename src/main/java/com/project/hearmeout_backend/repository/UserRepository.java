package com.project.hearmeout_backend.repository;

import com.project.hearmeout_backend.dto.response.user_response.UserDetailResponseDTO;
import com.project.hearmeout_backend.model.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<@NonNull User,@NonNull  Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("""
                SELECT new com.project.hearmeout_backend.dto.response.user_response.UserDetailResponseDTO(
                    u.id, u.email, u.password
                )
                FROM User u
                WHERE u.email = :email
            """)
    Optional<UserDetailResponseDTO> findUserForAuth(String email);
}