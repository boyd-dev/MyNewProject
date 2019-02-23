package com.foo.myapp.login.service.impl;

import org.springframework.stereotype.Repository;
import com.foo.myapp.common.AbstractDao;
import com.foo.myapp.login.service.LoginVO;


@Repository("loginDAO")
public class LoginDAO extends AbstractDao {

    public LoginVO actionLogin(LoginVO vo) throws Exception {
    	return select("Login.actionLogin", vo);
    }


}
