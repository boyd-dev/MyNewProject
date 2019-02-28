package com.foo.myapp.user.web;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.foo.myapp.user.service.IUserService;
import com.foo.myapp.user.service.IUserServiceJpa;
import com.foo.myapp.user.service.UserVO;

@Controller
public class UserController {

	@Resource(name="userService")
	private IUserService userService;

	@Resource(name="userServiceJpa")
	private IUserServiceJpa userServiceJpa;

	@Resource(name="messageSource")
	private MessageSource messageSource;


	@RequestMapping(value = "/subscrb/userSignup.do")
	public String actionSignup(@ModelAttribute("userVO") UserVO userVO, ModelMap model, RedirectAttributes redirectAttrs) throws Exception {

		//int result = userService.saveNewUser(userVO);

		//plain JPA
		//JPA를 사용하여 처리하는 예제
		int result = userServiceJpa.saveNewUser(userVO);

		if (result > 0) {
			redirectAttrs.addFlashAttribute("_message", messageSource.getMessage("success.common.insert", null, null));
			return "redirect:/login.do";

		} else {
			redirectAttrs.addFlashAttribute("_message", messageSource.getMessage("fail.request.msg", null, null));
			return "redirect:/";
		}
	}


}
