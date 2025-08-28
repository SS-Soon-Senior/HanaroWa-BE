package com.ss.hanarowa.domain.lesson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.domain.lesson.entity.Lesson;
import com.ss.hanarowa.domain.member.entity.MyLesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
	List<Lesson> findAllByMemberId(Long memberId);
}
