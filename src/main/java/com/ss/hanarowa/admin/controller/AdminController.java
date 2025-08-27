package com.ss.hanarowa.admin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.hanarowa.admin.dto.response.AdminLessonListResponseDTO;
import com.ss.hanarowa.admin.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name="[관리자]")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	private final AdminService adminService;

	// @PreAuthorize("hasRole('ADMIN')")
	@Operation(summary ="강좌 개설 신청 내역")
	@GetMapping
	public ResponseEntity<List<AdminLessonListResponseDTO>> getAdminAllLessons(){
		log.info("[관리자] Controller : 강좌 목록 전체 가져오기");
		List<AdminLessonListResponseDTO> list = adminService.getAllLessons();
		return ResponseEntity.ok(list);
	}
}
