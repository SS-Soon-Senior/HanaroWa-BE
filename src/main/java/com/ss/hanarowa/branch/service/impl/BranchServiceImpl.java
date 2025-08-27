package com.ss.hanarowa.branch.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.hanarowa.branch.repository.BranchRepository;
import com.ss.hanarowa.branch.service.BranchService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BranchServiceImpl implements BranchService {

	private final BranchRepository branchRepository;


}
