package com.foo.myapp.user.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foo.myapp.user.persistence.User;
import com.foo.myapp.user.service.IUserDao;
import com.foo.myapp.user.service.IUserServiceJpa;
import com.foo.myapp.user.service.UserVO;
import com.foo.myapp.utils.Encrypt;
import com.foo.myapp.utils.TestNameList;

/**
 *
 * @author Kim.S.W
 *
 * 클래스명이 *ServiceImpl.패턴이 아니므로 AOP로 설정한 트랜잭션이 적용되지 않지만
 * 대신에 @Transactional을 사용하도록 설정
 *
 */
@Service(value="userServiceJpa")
public class UserServiceJpaImpl implements IUserServiceJpa {

	@Resource
	private IUserDao userDao;

	//메소드 레벨에서 트랜잭션 annotaton을 적용
	@Override
	@Transactional
	public int saveNewUser(UserVO vo) throws Exception {

		String enpassword = Encrypt.encryptPassword(vo.getPasswd(), vo.getMberId());

		//entity클래스를 JPA DAO에 전달한다.
		User user = new User();
		user.setMberId(vo.getMberId());
		user.setMberNm(TestNameList.getRandomName());
		user.setPasswd(enpassword);
		user.setInstId(vo.getMberId());
		user.setUpdtId(vo.getMberId());
		Date now = new Date();
		user.setInstTm(now);
		user.setUpdtTm(now);

		userDao.insertUser(user);

		//if (userDao.findUser(vo.getMberId()) != null) {
		if (userDao.selectCountByMberId(vo.getMberId()) == 1 ) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public long selectCountByMberId(User vo) throws Exception {
		return userDao.selectCountByMberId(vo.getMberId());
	}



}
