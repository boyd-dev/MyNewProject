package com.foo.myapp.user.service;

import java.io.Serializable;

/**
 * 사용자 정보
 * @author Kang
 *
 */
public class UserVO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7394667350539570776L;


	/**
	 * 아이디
	 */
	private String mberId;

	/**
	 * 이름
	 */
	private String mberNm;

	/**
	 * 암호
	 */
	private String passwd;

	/**
	 * 암호힌트
	 */
	private String passwordHint;

	/**
	 * 이메일
	 */
	private String mberEmail;

	/**
	 * 가입일
	 */
	private String subscrDt;

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

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getPasswordHint() {
		return passwordHint;
	}

	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}

	public String getMberEmail() {
		return mberEmail;
	}

	public void setMberEmail(String mberEmail) {
		this.mberEmail = mberEmail;
	}

	public String getSubscrDt() {
		return subscrDt;
	}

	public void setSubscrDt(String subscrDt) {
		this.subscrDt = subscrDt;
	}

}
