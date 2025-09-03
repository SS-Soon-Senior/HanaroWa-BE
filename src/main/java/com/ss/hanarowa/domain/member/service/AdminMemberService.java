package com.ss.hanarowa.domain.member.service;

import java.util.List;

import com.ss.hanarowa.domain.member.dto.response.MemberListResponseDTO;

public interface AdminMemberService {

	List<MemberListResponseDTO> getAllMembers();
}
