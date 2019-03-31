package com.foo.myapp.common.auth.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.foo.myapp.common.auth.IUserDetailsService;
import com.foo.myapp.login.service.LoginVO;

public class SecurityOAuthUserDetailsServiceImpl implements IUserDetailsService {

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	public Object getAuthenticatedUser() {

		SecurityContext context = SecurityContextHolder.getContext();
		if (context.getAuthentication().getPrincipal() == null) {
			return null;
		}

		//Google email 주소가 리턴
		//Google email은 6-30자 이내
		String user_name = (String) context.getAuthentication().getPrincipal();

		//이메일에서 @도메인을 제외한 문자열을 아이디로 한다.
		Pattern p = Pattern.compile("([^@]+)");
		Matcher m = p.matcher(user_name);
		String mberId = null;
		if (m.find()) {
			mberId = m.group(0);
		}

		LoginVO loginVO = new LoginVO();
		loginVO.setMberId(mberId);
		loginVO.setMberEmail(user_name);

		return loginVO;
	}

	@Override
	public List<String> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isAuthenticated() {

		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication == null) {
        	LOGGER.debug("Authentication is null");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;

	}

}
