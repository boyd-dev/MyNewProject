package com.foo.myapp.common.auth;

import java.util.List;


/**
 * helper 클래스는 유틸리티 클래스(static 메소드를 제공)와 유사하다.
 * @author foo
 *
 */
public class UserDetailsHelper {

	static IUserDetailsService userDetailsService;

	public IUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	/**
	 * context-userdetailshelper.xml에서 사용자 인증 수단을 인젝션
	 *
	 * @param userDetailsService
	 */
	public void setUserDetailsService(IUserDetailsService userDetailsService) {
		UserDetailsHelper.userDetailsService = userDetailsService;
	}

	/**
	 * 인증된 사용자객체를 VO형식으로 가져온다.
	 * @return Object - 사용자 ValueObject
	 */
	public static Object getAuthenticatedUser() {
		return userDetailsService.getAuthenticatedUser();
	}

	/**
	 * 인증된 사용자의 권한 정보를 가져온다.
	 *
	 * @return List - 사용자 권한정보 목록
	 */
	public static List<String> getAuthorities() {
		return userDetailsService.getAuthorities();
	}

	/**
	 * 인증된 사용자 여부를 체크한다.
	 * @return Boolean - 인증된 사용자 여부(TRUE / FALSE)
	 */
	public static Boolean isAuthenticated() {
		return userDetailsService.isAuthenticated();
	}
}
