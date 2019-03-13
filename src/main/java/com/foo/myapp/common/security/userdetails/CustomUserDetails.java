package com.foo.myapp.common.security.userdetails;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.foo.myapp.login.service.LoginVO;

public class CustomUserDetails extends User {

	/**
	 *
	 */
	private static final long serialVersionUID = 1098856718663319093L;

	//사용자가 로그인 후에 유지하는 정보
	private LoginVO loginVO;

	/**
     * User 클래스의 생성자 Override
     * @param username 사용자계정
     * @param password 사용자 패스워드
     * @param enabled 사용자계정 사용여부
     * @param accountNonExpired
     * @param credentialsNonExpired
     * @param accountNonLocked
     * @param authorities
     * @param loginVO User에서 제공하는 속성 외에 추가된 사용자 정보
     * @throws IllegalArgumentException
     */
	public CustomUserDetails(String username,
			                 String password,
			                 boolean enabled,
			                 boolean accountNonExpired,
			                 boolean credentialsNonExpired,
			                 boolean accountNonLocked,
			                 Collection<? extends GrantedAuthority> authorities,
			                 LoginVO loginVO) {

		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

		this.loginVO = loginVO;
	}

    public CustomUserDetails(String username, String password, LoginVO loginVO) throws IllegalArgumentException {

    	this(username,
    	     password,
    	     true,
    	     true,
    	     true,
    	     true,
    		 Arrays.asList(new GrantedAuthority[] {new SimpleGrantedAuthority("HOLDER")}),
    		 loginVO);
    }

	public LoginVO getLoginVO() {
		return loginVO;
	}

}
