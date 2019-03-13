package com.foo.myapp.user.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.foo.myapp.user.service.UserVO;

/**
 * MyBatis의 mapper인터페이스와 annotation으로 DAO를 구현할 수 있다.
 * @Select @Insert 등과 같은 표기로 사용하거나 context-mapper.xml에 설정된 basePackage를 namespace로,
 * 인터페이스 메소드가 query id가 되도록 mapper를 작성할 수 있다.
 *
 * @author foo
 *
 */
public interface IUserMapper {

	@Insert("INSERT INTO T_MEMBER(MBER_ID, PASSWORD, MBER_NM, MBER_EMAIL, INST_TM, INST_ID, UPDT_TM, UPDT_ID) "
			+ "VALUES (#{mberId}, #{passwd}, #{mberNm}, #{mberEmail}, NOW(), #{mberId}, NOW(), #{mberId})")
	public int insertUser(UserVO vo) throws Exception;


	//Spring Security 권한 정보가 필요하다.
	@Insert("INSERT INTO T_ROLE_USER_MAPPING(MBER_ID, ROLE_CODE, INST_TM, UPDT_TM) "
			+ "VALUES (#{mberId}, 'ROLE_USER', NOW(), NOW())")
	public int insertRoleUserMapping(UserVO vo) throws Exception;


	@Select("SELECT COUNT(*) FROM T_MEMBER WHERE MBER_ID = #{mberId}")
	public int selectCountByMberId(UserVO vo) throws Exception;


}
