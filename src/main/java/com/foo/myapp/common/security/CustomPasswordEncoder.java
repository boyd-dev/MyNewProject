package com.foo.myapp.common.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;

import com.foo.myapp.utils.Encrypt;

/**
 *
 * PlaintextPasswordEncoder는 Spring Security 5에서 deprecated
 * 여기서는 salt를 MBER_ID로 전달해야 하기 때문에 계속 사용하기로 한다.
 *
 * @author foo
 *
 */
public class CustomPasswordEncoder extends PlaintextPasswordEncoder {

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {

		try {

			if (salt != null) {
				//당연히 Sing-up에서 비번 저장할 때와 동일한 암호화 방식을 사용해야 한다.
				String encPassFromUser = Encrypt.encryptPassword(rawPass, salt.toString());
			    return super.isPasswordValid(encPass, encPassFromUser, null);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

}