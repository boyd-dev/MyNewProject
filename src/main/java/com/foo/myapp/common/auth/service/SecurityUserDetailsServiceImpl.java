package com.foo.myapp.common.auth.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.foo.myapp.common.auth.IUserDetailsService;
import com.foo.myapp.common.security.userdetails.CustomUserDetails;

/**
 * Spring Security 인증정보용
 *
 * @author foo
 *
 */
public class SecurityUserDetailsServiceImpl implements IUserDetailsService {

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	public Object getAuthenticatedUser() {

		//SecurityContext is used to store the details of the currently authenticated user, also known as a principle.
		//principal in computer security is an entity that can be authenticated by a computer system or network.
		SecurityContext context = SecurityContextHolder.getContext();
		if (context.getAuthentication().getPrincipal() == null) {
			return null;
		}

		LOGGER.debug(context.getAuthentication().getPrincipal().toString());

		//CutomUserDetails로부터 사용자 인증 정보를 리턴한다.
		CustomUserDetails userInfo = (CustomUserDetails) context.getAuthentication().getPrincipal();
		return userInfo.getLoginVO();
	}

	@Override
	public List<String> getAuthorities() {

		List<String> list = new ArrayList<String>();

		//사용자 권한 정보(ROLE) 조회
		SecurityContext context = SecurityContextHolder.getContext();
		Collection<? extends GrantedAuthority> authorities = context.getAuthentication().getAuthorities();

		authorities.forEach(auth -> {
			list.add(auth.getAuthority());
		});
		return list;
	}

	@Override
	public Boolean isAuthenticated() {

		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication == null) {
        	LOGGER.debug("Authentication is null");
            return Boolean.FALSE;
        }

        String username = authentication.getName();
        if (username.equals("anonymousUser")) {
        	LOGGER.debug("username is {}", username);
            return Boolean.FALSE;
        }

        Object principal = authentication.getPrincipal();

        return (!(principal == null));

	}

}
