package com.ss.hanarowa.domain.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.domain.lesson.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}