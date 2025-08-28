package com.ss.hanarowa.domain.member.service;

import java.util.List;

import com.ss.hanarowa.domain.member.dto.response.MemberListResponseDTO;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.entity.Role;

public interface AdminMemberService {

	List<MemberListResponseDTO> getAllMembers();
}
