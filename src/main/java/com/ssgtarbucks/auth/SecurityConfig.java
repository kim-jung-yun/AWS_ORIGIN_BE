package com.ssgtarbucks.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.CookieRequestCache;
import org.springframework.web.filter.CorsFilter;

import com.ssgtarbucks.jwt.JwtFilter;
import com.ssgtarbucks.util.SendMessage;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtFilter jwtFilter;
	
	// jwt 를 쿠키로 저장할때 쿠키의 이름
	@Value("${jwt.name}")
	private String jwtName;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, AuthSuccessHandler successHandler,
			AuthFailHandler failHandler, CookieRequestCache cookCache, CorsFilter corsFilter) throws Exception {
		System.out.println("SecurityConfig.filterChain");
		// 권한에 따라 허용하는 url 설정
		http.csrf().disable().addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests().antMatchers("/api/v1/**", // 모두허용
						"/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**") // swagger 툴
				.permitAll()
				//.antMatchers("/api/v1/admin/**").hasRole("ADMIN") // 관리자만 가능
				//.antMatchers("/api/v1/**").hasRole("MANAGER") // 이외 점주
				.anyRequest().authenticated()
				//.and()
				//.csrf().ignoringAntMatchers("/api/v1/find")
				;

		// 세션을 사용하지 않도록 설정한다.
		http.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// 토큰을 검사하는 필터를 UsernamePasswordAuthenticationFilter 가 동작하기 이전에 동작하도록 설정 한다.
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				
				// 세션을 사용할수 없기때문에 쿠키케시를 사용하도록 설정한다.
				.requestCache(config -> config.requestCache(cookCache));

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
			UserDetailsService userDetailService) throws Exception {
		 System.out.println("0.SecurityConfig.authenticationManager>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailService)
				.passwordEncoder(bCryptPasswordEncoder).and().build();
	}

	@Bean
	public CookieRequestCache getCookieRequestCache() {
		 System.out.println("0. SecurityConfig.getCookieRequestCache>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
		return new CookieRequestCache();
	}

}