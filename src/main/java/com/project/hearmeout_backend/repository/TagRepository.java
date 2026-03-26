package com.project.hearmeout_backend.repository;

import com.project.hearmeout_backend.model.Tag;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<@NonNull Tag,@NonNull Long> {
}