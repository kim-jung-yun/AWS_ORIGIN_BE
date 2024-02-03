package com.ssgtarbucks.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.CookieRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.ssgtarbucks.jwt.JwtUtil;


@Component
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Autowired
	private JwtUtil jwtUtil;

	@Value("${jwt.name}")
	private String jwtName;

	@Value("${jwt.cookie.expiration}")
	private int cookieExpiration;

	@Autowired
	private CookieRequestCache requestCache;

	@Autowired
	public AuthSuccessHandler(CookieRequestCache requestCache) {
		super.setRequestCache(requestCache);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		System.out.println("2. AuthSuccessHandler.onAuthenticationSuccess");
		System.out.println("Authentication:>>>>>>>>>>>>>" + authentication);
		System.out.println("3. authentication.getName() >>>>>>>>" + authentication.getName()); 

		User u = (User) authentication.getPrincipal();
		String jwtToken = jwtUtil.generateToken(u.getUsername());
		// JWT를 쿠키에 담아 응답 (쿠키에 공백문자는 에러를 일으키기 때문에 공백대신에 "+" 를 사용함)
		Cookie cookie = new Cookie(jwtName, "Bearer+" + jwtToken);
		cookie.setMaxAge(cookieExpiration); // 쿠키 유지 시간 초 단위로 설정
		cookie.setHttpOnly(true); // 웹브라우저에서 JavaScript에서 접근 불가 하도록 설정
		cookie.setPath("/"); // 모든 경로에서 쿠키를 사용할수 있도록 설정
		response.addCookie(cookie);

		response.addHeader("jwtAuthToken", "Bearer+" + jwtToken);

		SavedRequest cashed = requestCache.getRequest(request, response);
		System.out.println("AuthSuccessHandler.onAuthenticationSuccess.SavedRequest>> " + cashed);
		System.out.println("4. AuthSuccessHandler.onAuthenticationSuccess.SavedRequest>> " + cashed);
		System.out.println("AuthSuccessHandler.onAuthenticationSuccess.SavedRequest>> " + cashed);
		
		if (cashed != null) {
			response.sendRedirect("login_success");
		} else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}
}
