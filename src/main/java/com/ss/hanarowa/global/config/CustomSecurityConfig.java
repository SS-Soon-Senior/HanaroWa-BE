package com.ss.hanarowa.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ss.hanarowa.global.security.CustomAuthenticationEntryPoint;
import com.ss.hanarowa.global.security.CustomOAuth2UserService;
import com.ss.hanarowa.global.security.JwtAuthenticationFilter;
import com.ss.hanarowa.global.security.handler.CustomAccessDeniedHandler;
import com.ss.hanarowa.global.security.handler.CustomSuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class CustomSecurityConfig {
	private final CustomOAuth2UserService customOAuth2UserService;
	private final CustomSuccessHandler customSuccessHandler;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http,
		CustomAuthenticationEntryPoint customAuthenticationEntryPoint) throws Exception {
		http
			// .httpBasic(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.cors(config -> config.configurationSource(corsConfigurationSource()))
			.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(
					"/api/auth/signup",
					"/api/auth/reissue",
					"/api/auth/signin",
					"/api/auth/signin/**",
					"/api/oauth2/**",
					"/api/login/**",
					"/favicon.png",
					"/actuator/**",
					"/swagger-ui/**",
					"/api/v3/api-docs/**",
					"/hanarowa/api-docs/**",
					"/v3/api-docs/**",
					"/broadcast/**",
					"/swagger.html",
					"/api/member/refresh",
					"/api/auth/logout",
					"/api/branch",
					"/ws/**"
				).permitAll()
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.anyRequest().authenticated()
			)
			.exceptionHandling(config -> {
				config.accessDeniedHandler(customAccessDeniedHandler);
				config.authenticationEntryPoint(customAuthenticationEntryPoint);
			})

			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
			http.oauth2Login(oauth2 -> oauth2
				.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
				.successHandler(customSuccessHandler)
			);
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager
		(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	private CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOriginPatterns(List.of("*"));
		config.setAllowedMethods(List.of(
			HttpMethod.GET.name(),
			HttpMethod.POST.name(),
			HttpMethod.PATCH.name(),
			HttpMethod.OPTIONS.name(),
			HttpMethod.DELETE.name()));
		config.setAllowedHeaders(List.of(
			HttpHeaders.AUTHORIZATION,
			HttpHeaders.CACHE_CONTROL,
			HttpHeaders.CONTENT_TYPE));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
