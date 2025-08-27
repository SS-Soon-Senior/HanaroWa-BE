package com.ss.hanarowa.domain.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.domain.lesson.entity.RoomTime;

public interface RoomTimeRepository extends JpaRepository<RoomTime, Long> {
}