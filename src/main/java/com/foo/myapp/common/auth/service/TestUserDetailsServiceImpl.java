package com.foo.myapp.common.auth.service;

import java.util.List;

import com.foo.myapp.common.auth.IUserDetailsService;
import com.foo.myapp.login.service.LoginVO;

/**
 * 인터셉터를 우회하기 위한 테스트 사용자
 * @author foo
 *
 */
public class TestUserDetailsServiceImpl implements IUserDetailsService {

	@Override
	public Object getAuthenticatedUser() {

		LoginVO loginVO = new LoginVO();
		loginVO.setMberId("Kate");
		loginVO.setMberNm("Kate Elizabeth Winslet");

		return loginVO;
	}

	@Override
	public List<String> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return null;
	}

}
