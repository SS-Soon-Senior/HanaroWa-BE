package com.ss.hanarowa.domain.branch.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.hanarowa.domain.branch.dto.response.BranchResponseDTO;
import com.ss.hanarowa.domain.branch.repository.BranchRepository;
import com.ss.hanarowa.domain.branch.service.BranchService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BranchServiceImpl implements BranchService {

	private final BranchRepository branchRepository;

	@Override
	public List<BranchResponseDTO> getAllBranches() {
		return branchRepository.findAll().stream()
			.map(branch -> BranchResponseDTO.builder()
				.branchId(branch.getId())
				.locationName(branch.getLocation().getName())
				.branchName(branch.getName())
				.build())
			.collect(Collectors.toList());
	}
}