package com.foo.myapp.common;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractDao extends SqlSessionDaoSupport {

	//@Resource(name="sqlSessionTemplate")
	/**
	 * context-datasource.xml에서 인젝션된다.
	 */
	@Autowired
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

	public int insert(String queryId, Object params){
		return getSqlSession().insert(queryId, params);
	}

    //Generic 메소드
	//이 메소드의 리턴 타입은 하나의 타입으로 고정되는 것이 아니므로 제너릭 메소드를 정의한다.
	//제너릭 메소드는 리턴 타입 앞에 제너릭 타입 파라미터를 <>으로 선언한다. 여기서는 List의 element이므로 E라는 컨벤션으로 표기한다.
	public <E> List<E> selectList(String queryId, Object parameterObject) {
		return getSqlSession().selectList(queryId, parameterObject);
	}

	//T는 type을 나타내는 컨벤션
	public <T> T select(String queryId, Object parameterObject) {
		return getSqlSession().selectOne(queryId, parameterObject);
	}

	public int update(String queryId, Object params){
		return getSqlSession().update(queryId, params);
	}

	public int delete(String queryId, Object params){
		return getSqlSession().delete(queryId, params);
	}



}
