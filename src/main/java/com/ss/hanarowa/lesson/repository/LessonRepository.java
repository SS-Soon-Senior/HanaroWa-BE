package com.ss.hanarowa.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.lesson.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
