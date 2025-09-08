package com.ss.hanarowa.domain.lesson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ss.hanarowa.domain.lesson.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
	List<Lesson> findByBranchIdOrderByIdDesc(Long branchId);

	List<Lesson> findAllByOrderByIdDesc();

	@Query("SELECT l FROM Lesson l WHERE l.lessonName LIKE %:query% OR l.instructor like %:query% ORDER BY l.id DESC")
	List<Lesson> findByLessonNameContainingOrderByIdDesc(@Param("query") String query);

	List<Lesson> findAllByMemberId(Long memberId);
	
	Lesson findByLessonNameAndInstructor(String lessonName, String instructor);
}
