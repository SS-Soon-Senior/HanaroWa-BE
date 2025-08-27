package com.ss.hanarowa.domain.lesson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.domain.lesson.entity.LessonGisu;

public interface LessonGisuRepository extends JpaRepository<LessonGisu, Long> {
	List<LessonGisu> findByLessonId(Long lessonId);
}