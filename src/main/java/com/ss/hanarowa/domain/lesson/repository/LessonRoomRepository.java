package com.ss.hanarowa.domain.lesson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.domain.lesson.entity.LessonRoom;

public interface LessonRoomRepository extends JpaRepository<LessonRoom, Long> {
	List<LessonRoom> findByBranchId(Long branchId);
}