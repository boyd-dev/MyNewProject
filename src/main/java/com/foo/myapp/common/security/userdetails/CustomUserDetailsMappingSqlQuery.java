package com.foo.myapp.common.security.userdetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import com.foo.myapp.login.service.LoginVO;

public class CustomUserDetailsMappingSqlQuery extends MappingSqlQuery<CustomUserDetails> {

	public CustomUserDetailsMappingSqlQuery(DataSource ds, String usersByUsernameQuery) {
		super(ds, usersByUsernameQuery);
	    declareParameter(new SqlParameter(Types.VARCHAR));
	    compile();
	}


	//결과셋을 사용자 세션 정보에 담는 부분
	//전자정부프레임워크에서는 추상 메소드로 정의하여 상속 클래스에서 구현하도록 되어 있다.
	//protected abstract EgovUserDetails mapRow(ResultSet rs, int rownum) throws SQLException;
	@Override
	protected CustomUserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

		String mberId    = rs.getString("MBER_ID");
        String passwd    = rs.getString("PASSWORD");
        String mberNm    = rs.getString("MBER_NM");
        String email     = rs.getString("MBER_EMAIL");

        //추가적인 사용자 정보
        LoginVO loginVO = new LoginVO();
        loginVO.setMberId(mberId);
        loginVO.setPasswd(passwd);
        loginVO.setMberNm(mberNm);
        loginVO.setMberEmail(email);

        return new CustomUserDetails(mberId, passwd, loginVO);

	}

}
