package com.foo.myapp.user.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.foo.myapp.user.service.IUserService;
import com.foo.myapp.user.service.UserVO;

@RestController
public class UserRestController {

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Resource(name="userService")
	private IUserService userService;

	@RequestMapping(value="/subscrb/{mberId}")
	@ResponseBody
	public String boardList(@PathVariable String mberId) throws Exception {

		LOGGER.debug("NEW USER ID = " + mberId);

		UserVO vo = new UserVO();
		vo.setMberId(mberId);
		int cnt = userService.selectCountByMberId(vo);

		return String.valueOf(cnt);

	}

}
