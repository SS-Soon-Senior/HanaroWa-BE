package com.ss.hanarowa.domain.facility.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.hanarowa.domain.facility.entity.FacilityImage;

@Repository
public interface FacilityImageRepository extends JpaRepository<FacilityImage,Long> {
}