package com.ssgtarbucks.jwt;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssgtarbucks.auth.CustomUserDetailsService;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Value("${jwt.name}")
	private String jwtName;

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private CustomUserDetailsService service;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 System.out.println("1. JwtFilter.doFilterInternal>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String jwtToken = "";

		Enumeration<String> names = request.getHeaderNames(); // header subject
		while (names.hasMoreElements()) {
			String headerName = names.nextElement();
			if (jwtName.equals(headerName)) {
				jwtToken = request.getHeader(headerName);
				break;
			}
		}

		//System.out.println(">>>>>jwtToken : " + jwtToken);
		// 사용자명
		String user_id = null;
		// 토큰 Bearer 로 시작 하는지 확인
		if (jwtToken.startsWith("Bearer+")) {
			System.out.println("jwtToken : " + jwtToken);
			jwtToken = jwtToken.substring(7);
			// 유틸을 이용해서 토큰에 저장된 user_id (subject) 얻음
			user_id = jwtUtil.extractEmail(jwtToken);
		}

		System.out.println("1. JwtFilter.user_id>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + user_id);
		System.out.println("1. JwtFilter.authentication>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ SecurityContextHolder.getContext().getAuthentication());
		
		// user_id 이 존재하고 Spring Security 에서 아직 인증을 받지 않은 상태라면
		if (user_id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// 읽어낸 userName 을 이용해서 UserDetails 객체를 얻어낸다
			UserDetails userDetails = service.loadUserByUsername(user_id);
			// token 이 유효한 토큰인지 유틸을 이용해서 알아낸다
			boolean isValid = jwtUtil.validateToken(jwtToken, userDetails);
			if (isValid) {
				// 사용자가 제출한 사용자 이름과 비밀번호와 같은 인증 자격 증명을 저장
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// 보안 컨텍스트 업데이트
				SecurityContextHolder.getContext().setAuthentication(authToken);
				System.out
						.println("1. JwtFilter.UsernamePasswordAuthenticationToken>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
								+ authToken);
			}

			System.out.println("1. JwtFilter.UserDetails>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + userDetails);
		}
		// 다음 필터 chain 진행하기
		filterChain.doFilter(request, response);
	}

}
