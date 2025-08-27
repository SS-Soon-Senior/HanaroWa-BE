package com.ss.hanarowa.domain.branch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.domain.branch.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}