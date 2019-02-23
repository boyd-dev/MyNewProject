package com.foo.myapp.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foo.myapp.user.mapper.IUserMapper;
import com.foo.myapp.user.service.IUserService;
import com.foo.myapp.user.service.UserVO;
import com.foo.myapp.utils.Encrypt;
import com.foo.myapp.utils.TestNameList;

@Service(value="userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserMapper userMapper;

	@Override
	public int saveNewUser(UserVO vo) throws Exception {

		int result = 0;

		//입력한 비밀번호를 암호화한다.
		String enpassword = Encrypt.encryptPassword(vo.getPasswd(), vo.getMberId());
		vo.setPasswd(enpassword);
		vo.setMberNm(TestNameList.getRandomName());

		result = userMapper.insertUser(vo);

		return result;

	}

	@Override
	public int selectCountByMberId(UserVO vo) throws Exception {
		return userMapper.selectCountByMberId(vo);
	}

}
