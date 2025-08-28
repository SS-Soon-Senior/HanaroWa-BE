package com.ss.hanarowa.domain.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.domain.lesson.entity.LessonGisu;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.entity.MyLesson;

public interface MyLessonRepository extends JpaRepository<MyLesson, Long> {
	int countByLessonGisu(LessonGisu lessonGisu);
	int countByLessonGisuId(Long lessonGisuId);
	List<MyLesson> findAllByLessonGisuId(Long lessonGisuId);
	List<MyLesson> findAllByMemberId(Long memberId);
}
