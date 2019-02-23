package com.foo.myapp.common.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.foo.myapp.common.auth.UserDetailsHelper;
import com.foo.myapp.login.service.LoginVO;

/**
 * Spring 인터셉터는 HandlerInterceptorAdapter를 상속하여 구현
 * 서블릿 필터와 유사하지만 확장 가능성이 높다.
 * 요청의 전 또는 후로 적용이 가능하다.
 * servlet-context.xml에 <mvc:intercepters/>에 설정한다.
 *
 * @author foo
 *
 */
public class AuthenticInterceptor extends HandlerInterceptorAdapter {

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	/**
	 * preHandle은 컨트롤러로 넘어가기 전에 수행된다.
	 * 세션에 계정정보(LoginVO)가 있는지 여부로 인증 여부를 체크한다.
	 * 계정정보(LoginVO)가 없다면, 로그인 페이지로 이동한다.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		boolean isPermittedURL = false;

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();

		if(loginVO != null){
			return true;
		} else if(!isPermittedURL){
			    LOGGER.debug("Empty LoginVO");
				ModelAndView modelAndView = new ModelAndView("redirect:/login.do");
				throw new ModelAndViewDefiningException(modelAndView);
		}else{
			return true;
		}
	}

}
