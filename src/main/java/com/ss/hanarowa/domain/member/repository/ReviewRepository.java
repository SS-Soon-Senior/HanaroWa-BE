package com.ss.hanarowa.domain.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.domain.lesson.entity.LessonGisu;
import com.ss.hanarowa.domain.lesson.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	List<Review> findByLessonGisu(LessonGisu lessonGisu);
	List<Review> findByLessonGisuId(Long lessonGisuId);
}