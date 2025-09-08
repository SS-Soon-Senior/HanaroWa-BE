package com.ss.hanarowa.domain.branch.service;

import java.util.List;

import com.ss.hanarowa.domain.branch.dto.response.BranchResponseDTO;

public interface BranchService {
	List<BranchResponseDTO> getAllBranches();
}