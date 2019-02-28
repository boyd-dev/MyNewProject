package com.foo.myapp.user.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.foo.myapp.user.persistence.User;
import com.foo.myapp.user.service.IUserDao;

/**
 *
 * DAO for JPA
 * @author Kim.S.W.
 *
 */
@Repository
public class UserDao implements IUserDao {

	//context-persistence에서 JPA Annotation에 의해 EntityManager가 Spring 컨테이너에 의해 자동으로 생성된다.
	//type의 디폴트는 TRANSACTION이므로 생략해도 된다.
	@PersistenceContext(unitName="MySQLJPA", type=PersistenceContextType.TRANSACTION)
	private EntityManager em;

	@Override
	public void insertUser(User vo) throws Exception {
		em.persist(vo);
	}

	@Override
	public User findUser(String mberId) throws Exception {
	    return (User) em.find(User.class, mberId);
	}

	@Override
	public long selectCountByMberId(String mberId) throws Exception {

		String jpql = "SELECT COUNT(U) FROM User U WHERE U.mberId = :mberId";
		Long count = em.createQuery(jpql, Long.class).setParameter("mberId", mberId).getSingleResult();
		return count.longValue();
	}

	//하이버네이트의 세션을 가져온다. 이것으로부터 DB커넥션을 가져올 수 있다.
	//When to use?
	protected Session getCurrentSession() {
		return (em == null)?null : (Session) em.getDelegate();
	}



}
