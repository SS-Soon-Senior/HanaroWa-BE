package com.ss.hanarowa.domain.member.service;

import com.ss.hanarowa.domain.member.dto.MemberRegistDTO;
import com.ss.hanarowa.domain.member.dto.MemberInfoDTO;

public interface MemberService {
	void credentialRegist(MemberRegistDTO memberRegistDTO);
	void infoRegist(MemberInfoDTO memberInfoDTO, long id);
	void withdraw(long id);
	void modifyInfo(MemberInfoDTO memberInfoDTO, long id);
}
