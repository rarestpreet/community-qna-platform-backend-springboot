package com.project.hearmeout_backend.repository;

import com.project.hearmeout_backend.dto.response.user_response.UserDetailResponseDTO;
import com.project.hearmeout_backend.model.User;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@NullMarked
public interface UserRepository extends JpaRepository<User, Long> {

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
                AND u.isAccountTerminated = false
            """)
    Optional<UserDetailResponseDTO> findUserForAuth(String email);
}