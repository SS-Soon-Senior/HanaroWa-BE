package com.ss.hanarowa.global.security;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.entity.Role;
import com.ss.hanarowa.domain.member.repository.MemberRepository;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final MemberRepository memberRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		String provider = userRequest.getClientRegistration().getRegistrationId();

		String providerId = null;
		String email = null;
		String name = null;

		if ("google".equals(provider)) {
			// Google
			providerId = oAuth2User.getAttribute("sub");
			email = oAuth2User.getAttribute("email");
			name = oAuth2User.getAttribute("name");

		} else if ("naver".equals(provider)) {
			// Naver
			Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttribute("response");
			if (response != null) {
				providerId = (String) response.get("id");
				email = (String) response.get("email");
				name = (String) response.get("name");
			}

		} else if ("kakao".equals(provider)) {
			// Kakao
			Object kakaoId = oAuth2User.getAttribute("id");
			providerId = kakaoId != null ? String.valueOf(kakaoId) : null;

			Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttribute("kakao_account");
			if (kakaoAccount != null) {
				email = (String) kakaoAccount.get("email");

				Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
				if (profile != null) {
					name = (String) profile.get("nickname");
				} else {
					name = (String) kakaoAccount.get("name");
				}
			}
		} else {
			throw new GeneralException(ErrorStatus.PROVIDER_NOT_USED);
		}

		// DB 조회 및 신규 회원 저장
		final String finalProviderId = providerId;
		final String finalEmail = email;
		final String finalName = name;

		Member member = memberRepository.findByProviderAndProviderId(provider, providerId)
										.orElseGet(() -> {
											return memberRepository.save(Member.builder()
																			   .email(finalEmail)
																			   .name(finalName)
																			   .provider(provider)
																			   .providerId(finalProviderId)
																			   .role(Role.USERS)
																			   .build());
										});

		return new CustomOAuth2User(member, oAuth2User.getAttributes());
	}
}
