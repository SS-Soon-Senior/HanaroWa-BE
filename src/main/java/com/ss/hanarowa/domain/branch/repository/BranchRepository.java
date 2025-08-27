package com.ss.hanarowa.domain.branch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ss.hanarowa.domain.branch.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {

}