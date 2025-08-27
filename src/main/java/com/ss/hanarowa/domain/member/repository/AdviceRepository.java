package com.ss.hanarowa.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.domain.member.entity.Advice;

public interface AdviceRepository extends JpaRepository<Advice, Long> {
}