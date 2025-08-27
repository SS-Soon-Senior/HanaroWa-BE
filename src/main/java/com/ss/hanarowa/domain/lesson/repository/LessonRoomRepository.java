package com.ss.hanarowa.domain.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.domain.lesson.entity.LessonRoom;

public interface LessonRoomRepository extends JpaRepository<LessonRoom, Long> {
}