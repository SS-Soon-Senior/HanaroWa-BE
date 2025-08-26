package com.ss.hanarowa.security;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ss.hanarowa.member.entity.Member;
import com.ss.hanarowa.member.entity.Role;
import com.ss.hanarowa.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final MemberRepository memberRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		String provider = userRequest.getClientRegistration().getRegistrationId();
		String providerId;

		if (provider.equals("google")) {
			providerId = oAuth2User.getAttribute("sub");
		} else if (provider.equals("naver")) {
			Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttribute("response");
			providerId = (String) response.get("id");
		} else if (provider.equals("kakao")) {
			providerId = String.valueOf(oAuth2User.getAttribute("id"));
		} else {
			throw new OAuth2AuthenticationException("지원하지 않는 provider: " + provider);
		}

		String email = oAuth2User.getAttribute("email");
		String name = oAuth2User.getAttribute("name");

		Member member = memberRepository.findByProviderAndProviderId(provider, providerId)
										.orElseGet(() -> {
											return memberRepository.save(Member.builder()
																			   .email(email)
																			   .name(name)
																			   .provider(provider)
																			   .providerId(providerId)
																			   .role(Role.USERS)
																			   .build());
										});

		return new CustomOAuth2User(member, oAuth2User.getAttributes());
	}
}

