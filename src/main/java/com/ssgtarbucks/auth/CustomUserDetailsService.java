package com.ssgtarbucks.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ssgtarbucks.domain.UserDTO;
import com.ssgtarbucks.service.UserService;


// Spring Security 서비스 구현체
@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService service;

	@Override
	public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {
		System.out.println("3. CustomUserDetailsService.loadUserByUsername: " + user_id);
		System.out.println("user_id >>>>>>>>>>>" + user_id);
		UserDTO userDTO = new UserDTO();
		userDTO = service.selectUserByUserId(user_id);
		
		
		if (userDTO != null) {
			List<SimpleGrantedAuthority> list = new ArrayList<>();
			list.add(new SimpleGrantedAuthority(userDTO.getUser_type()));
			System.out.println("출력해보기 : id )"+ userDTO.getUser_id()+" pw)"+userDTO.getUser_pw());
			UserDetails userDetails = new org.springframework.security.core.userdetails.User(userDTO.getUser_id(),
					userDTO.getUser_pw(), list);
			System.out.println("UserDetails>>>>>>>>>>" + userDetails);
			// org.springframework.security.core.userdetails.User [Username=hong@daum.net,
			// Password=[PROTECTED], Enabled=true, AccountNonExpired=true,
			// credentialsNonExpired=true, AccountNonLocked=true, Granted
			// Authorities=[USER]]
			return userDetails;
		} else {
			throw new UsernameNotFoundException(user_id + " -> 데이터베이스에서 찾을 수 없습니다.");
		}
	}


}
