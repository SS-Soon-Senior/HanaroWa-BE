package com.ss.hanarowa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.ss.hanarowa.security.CustomOAuth2UserService;
import com.ss.hanarowa.security.handler.OAuth2SuccessHandler;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class OAuthSecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService;
	private final OAuth2SuccessHandler oAuth2SuccessHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());

		http.authorizeHttpRequests(auth -> auth
			.requestMatchers("/", "/login/**", "/oauth2/**").permitAll()
			.anyRequest().authenticated()
		);

		http.oauth2Login(oauth2 -> oauth2
			.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
			.successHandler(oAuth2SuccessHandler)
		);

		return http.build();
	}
}
