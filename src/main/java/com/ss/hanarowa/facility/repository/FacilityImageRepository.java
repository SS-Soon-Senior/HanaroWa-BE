package com.ss.hanarowa.facility.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.hanarowa.facility.entity.FacilityImage;

@Repository
public interface FacilityImageRepository extends JpaRepository<FacilityImage,Long> {
}
