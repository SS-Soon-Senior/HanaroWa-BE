package com.ss.hanarowa.domain.lesson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.domain.lesson.entity.LessonGisu;
import com.ss.hanarowa.domain.lesson.entity.Review;
import com.ss.hanarowa.domain.member.entity.Member;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	List<Review> findByLessonGisu(LessonGisu lessonGisu);
	List<Review> findByLessonGisuId(Long lessonGisuId);

	List<Review> findByMemberAndLessonGisu(Member member, LessonGisu lessonGisu);
}
