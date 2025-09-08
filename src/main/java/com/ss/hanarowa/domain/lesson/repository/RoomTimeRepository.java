package com.ss.hanarowa.domain.lesson.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ss.hanarowa.domain.lesson.entity.RoomTime;

public interface RoomTimeRepository extends JpaRepository<RoomTime, Long> {
	
	@Query("SELECT rt FROM RoomTime rt WHERE rt.lessonRoom.id = :roomId " +
		   "AND NOT (rt.endedAt <= :startTime OR rt.startedAt >= :endTime)")
	List<RoomTime> findConflictingRoomTimes(@Param("roomId") Long roomId,
											@Param("startTime") LocalDateTime startTime,
											@Param("endTime") LocalDateTime endTime);
}