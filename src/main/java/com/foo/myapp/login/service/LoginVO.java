package com.foo.myapp.login.service;

import java.io.Serializable;

public class LoginVO  implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2302934325062581374L;

	/**
	 * 아이디
	 */
	private String mberId;

	/**
	 * 사용자명
	 */
	private String mberNm;

	/**
	 * 비밀번호
	 */
	private String passwd;


	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getMberId() {
		return mberId;
	}

	public void setMberId(String mberId) {
		this.mberId = mberId;
	}

	public String getMberNm() {
		return mberNm;
	}

	public void setMberNm(String mberNm) {
		this.mberNm = mberNm;
	}


}
