package com.ss.hanarowa.domain.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.domain.member.entity.MyLesson;

public interface MyLessonRepository extends JpaRepository<MyLesson, Long> {
	List<MyLesson> findAllByMemberId(Long memberId);
}
