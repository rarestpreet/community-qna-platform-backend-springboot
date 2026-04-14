package com.project.hearmeout_backend.repository;

import com.project.hearmeout_backend.model.Tag;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;

@NullMarked
public interface TagRepository extends JpaRepository<Tag, Long> {
}