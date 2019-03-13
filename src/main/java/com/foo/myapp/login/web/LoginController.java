package com.foo.myapp.login.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.foo.myapp.login.service.ILoginService;
import com.foo.myapp.login.service.LoginVO;

/**
 * Spring Security의 필터가 인증 정보를 처리하므로 이 컨트롤러는 사용하지 않는다.
 *
 * @author kim
 *
 */
@Deprecated
@Controller
public class LoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Resource(name = "loginService")
	private ILoginService loginService;

	@Resource(name="messageSource")
	private MessageSource messageSource;


	/* Spring Security를 사용하므로 주석처리함
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String loginUsrView(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/login/userLogin";
	}


	@RequestMapping(value="/actionLogin.do", method=RequestMethod.POST)
	public String actionLogin(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request, RedirectAttributes redirectAttrs) throws Exception {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("--------------------------------------------");
			LOGGER.debug("LOG-IN " + loginVO.getMberId() + ":" + loginVO.getPasswd());
			LOGGER.debug("--------------------------------------------");
		}

		LoginVO resultVO = loginService.actionLogin(loginVO);

		if (resultVO != null && resultVO.getMberId() != null && !resultVO.getMberId().equals("")) {

			//session에 저장
			request.getSession().setAttribute("userInfo", resultVO);

			return "redirect:/main.do"; //메뉴로 이동

		} else {
			//A FlashMap provides a way for one request to store attributes intended for use in another.
			//This is most commonly needed when redirecting from one URL to another -- e.g. the Post/Redirect/Get pattern.
			//A FlashMap is saved before the redirect (typically in the session) and is made available after the redirect and removed immediately.

			redirectAttrs.addFlashAttribute("_message", messageSource.getMessage("fail.common.login", null, null));
			return "redirect:/login.do";

		}
	}


	@RequestMapping(value = "/actionLogout.do")
	public String actionLogout(HttpSession session, ModelMap model) throws Exception {

		session.setAttribute("sessionInfo", null);
		session.invalidate();

		return "redirect:/";
	}
	*/



}
