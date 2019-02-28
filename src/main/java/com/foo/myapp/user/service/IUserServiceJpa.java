package com.foo.myapp.user.service;

import com.foo.myapp.user.persistence.User;

public interface IUserServiceJpa {


	/**
	 * 신규 회원 등록
	 *
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int saveNewUser(UserVO vo) throws Exception;

	/**
	 * 아이디 중복 검사
	 *
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public long selectCountByMberId(User vo) throws Exception;


}
