package com.ss.hanarowa.member.service;

import com.ss.hanarowa.member.dto.MemberInfoDTO;
import com.ss.hanarowa.member.dto.MemberRegistDTO;

public interface MemberService {
	void credentialRegist(MemberRegistDTO memberRegistDTO);
	void infoRegist(MemberInfoDTO memberInfoDTO, long id);
}
