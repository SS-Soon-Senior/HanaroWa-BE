package com.ss.hanarowa.domain.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.hanarowa.domain.lesson.entity.Curriculum;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
}