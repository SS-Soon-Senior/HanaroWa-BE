package com.ss.hanarowa.domain.facility.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ss.hanarowa.domain.facility.entity.Facility;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

	@Query("""
        select distinct f
        from Facility f
        left join fetch f.facilityImages fi
        where f.branch.id = :branchId
        order by f.id asc, fi.id asc
    """)
	List<Facility> findAllByBranchId(long branchId);
}
