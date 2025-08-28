package com.ss.hanarowa.domain.facility.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.domain.facility.entity.Facility;
import com.ss.hanarowa.domain.facility.entity.FacilityTime;

public interface FacilityTimeRepository extends JpaRepository<FacilityTime, Long> {
	List<FacilityTime> findFacilityTimesByFacilityAndStartedAtBetween(Facility facility, LocalDateTime startedAtAfter, LocalDateTime startedAtBefore);
	
	// 모든 예약 내역을 최신순으로 조회 (ID 기준)
	List<FacilityTime> findAllByOrderByIdDesc();

	List<FacilityTime> findAllByMemberId(Long memberId);
}
