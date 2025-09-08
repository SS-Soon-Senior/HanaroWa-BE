package com.ss.hanarowa.domain.facility.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.domain.facility.entity.Facility;
import com.ss.hanarowa.domain.facility.entity.FacilityTime;

public interface FacilityTimeRepository extends JpaRepository<FacilityTime, Long> {
	List<FacilityTime> findFacilityTimesByFacilityAndStartedAtBetween(Facility facility, LocalDateTime startedAtAfter, LocalDateTime startedAtBefore);
	
	List<FacilityTime> findAllByOrderByIdDesc();

	List<FacilityTime> findAllByMemberId(Long memberId);
}
