package com.foo.myapp.common.auth.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.foo.myapp.common.auth.IUserDetailsService;


/**
 * 이미 로그인이 되었다면 세션이 사용자 정보를 가지고 있다.
 * 세션에 있는 사용자 정보를 리턴한다.
 *
 * @author foo
 *
 */
public class SessionUserDetailsServiceImpl implements IUserDetailsService {

	/**
	 * RequestContextHolder is a Spring API for setting the values
	 * to any of three scopes request, session or global session.
	 * Note that in some situations, we may not be able to get the actual request
	 * from the request, we can simply use RequestContextHolder to get the request
	 * attributes and set the values.
	 *
	 * RequestContextHolder는 request나 session에 있는 값을 참조할 때 사용할 수 있다.
	 * 보통은 컨트롤러에서 request나 session을 처리하는데 그 외의 영역에서도 참조하는 것이 가능하다.
	 *
	 */

	public Object getAuthenticatedUser() {
		if (RequestContextHolder.getRequestAttributes() == null) {
			return null;
		}

		//인증여부 판단: 세션에 userInfo 속성이 있어야 한다.
		return RequestContextHolder.getRequestAttributes().getAttribute("userInfo", RequestAttributes.SCOPE_SESSION);
	}

	public List<String> getAuthorities() {

		// 권한 설정을 리턴한다.
		List<String> listAuthorization = new ArrayList<String>();

		//TODO
		return listAuthorization;
	}

	public Boolean isAuthenticated() {

		// 인증된 유저인지 확인한다.
		if (RequestContextHolder.getRequestAttributes() == null) {
			return false;
		} else {

			if (RequestContextHolder.getRequestAttributes().getAttribute("userInfo", RequestAttributes.SCOPE_SESSION) == null) {
				return false;
			} else {
				return true;
			}
		}

	}

}
