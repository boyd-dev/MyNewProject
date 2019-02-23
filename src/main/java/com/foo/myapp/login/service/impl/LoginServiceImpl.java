package com.foo.myapp.login.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.foo.myapp.common.AbstractService;
import com.foo.myapp.login.service.ILoginService;
import com.foo.myapp.login.service.LoginVO;
import com.foo.myapp.utils.Encrypt;

@Service("loginService")
public class LoginServiceImpl extends AbstractService implements ILoginService {

    @Resource(name="loginDAO")
    private LoginDAO loginDAO;


    @Override
	public LoginVO actionLogin(LoginVO vo) throws Exception {

    	// 1. 입력한 비밀번호를 암호화한다.
		String enpassword = Encrypt.encryptPassword(vo.getPasswd(), vo.getMberId());
    	vo.setPasswd(enpassword);

    	// 2. 아이디와 암호화된 비밀번호가 DB와 일치하는지 확인한다.
    	LoginVO loginVO = loginDAO.actionLogin(vo);

    	// 3. 결과를 리턴한다.
    	if (loginVO != null && !loginVO.getMberId().equals("") && !loginVO.getPasswd().equals("")) {
    		return loginVO;
    	} else {
    		loginVO = new LoginVO();
    	}

    	return loginVO;
    }

}
