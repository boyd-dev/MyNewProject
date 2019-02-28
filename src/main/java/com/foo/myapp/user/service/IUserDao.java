package com.foo.myapp.user.service;

import com.foo.myapp.user.persistence.User;

/**
 *
 * DAO for JPA
 * @author Kim.S.W.
 *
 */
public interface IUserDao {

	public void insertUser(User vo) throws Exception;

	public User findUser(String mberId) throws Exception;

	public long selectCountByMberId(String mberId) throws Exception; //JPA COUNT는 long으로 리턴된다.


}
