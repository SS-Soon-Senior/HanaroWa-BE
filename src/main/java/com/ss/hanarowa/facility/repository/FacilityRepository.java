package com.ss.hanarowa.facility.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.facility.entity.Facility;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
}
