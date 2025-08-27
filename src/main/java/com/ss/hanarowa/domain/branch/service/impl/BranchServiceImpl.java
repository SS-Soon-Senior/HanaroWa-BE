package com.ss.hanarowa.domain.branch.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.hanarowa.domain.branch.repository.BranchRepository;
import com.ss.hanarowa.domain.branch.service.BranchService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BranchServiceImpl implements BranchService {

	private final BranchRepository branchRepository;


}