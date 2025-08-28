package com.ss.hanarowa.domain.lesson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ss.hanarowa.domain.lesson.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
	List<Lesson> findByBranchIdOrderByIdDesc(Long branchId);

	// 전체 강좌 최신순 조회
	List<Lesson> findAllByOrderByIdDesc();

	// 강좌명 OR 강사명 으로 검색 (최신순)
	@Query("SELECT l FROM Lesson l WHERE l.lessonName LIKE %:query% OR l.instructor like %:query% ORDER BY l.id DESC")
	List<Lesson> findByLessonNameContainingOrderByIdDesc(@Param("query") String query);
}
