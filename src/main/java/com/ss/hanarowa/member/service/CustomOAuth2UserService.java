package com.ss.hanarowa.member.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ss.hanarowa.member.entity.Member;
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
		String providerId = oAuth2User.getAttribute("sub"); // 구글= sub, 카카오= id, 네이버= response.id
		String email = oAuth2User.getAttribute("email");
		String name = oAuth2User.getAttribute("name");

		Member member = memberRepository.findByProviderAndProviderId(provider, providerId)
										.orElseGet(() -> {
											return memberRepository.save(Member.builder()
																			   .email(email)
																			   .name(name)
																			   .provider(provider)
																			   .providerId(providerId)
																			   .build());
										});

		return new CustomOAuth2User(member, oAuth2User.getAttributes());
	}
}

