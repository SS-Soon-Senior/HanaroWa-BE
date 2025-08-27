package com.ss.hanarowa.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.domain.member.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}