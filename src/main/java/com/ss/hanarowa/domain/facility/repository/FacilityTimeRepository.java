package com.ss.hanarowa.domain.facility.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.domain.facility.entity.FacilityTime;

public interface FacilityTimeRepository extends JpaRepository<FacilityTime, Long> {
}