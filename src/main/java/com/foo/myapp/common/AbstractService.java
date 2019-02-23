package com.foo.myapp.common;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;

import com.foo.myapp.common.exception.BizException;

/**
 * 비지니스 로직과 관련된 서비스에서 발생하는 예외처리를 위한 기능을 제공한다.
 * 모든 비지니스 서비스 클래스는 이 클래스를 상속받도록 한다.
 *
 */

public class AbstractService {

	//예외 또는 오류 메시지는 메시지 properties에서 관리하기로 한다.
    @Resource(name="messageSource")
    private MessageSource messageSource;

	protected Exception processException(final String msg) {
		return new BizException(msg);
	}

	protected Exception processException(final String msgKey, final String[] msgArgs, final Locale locale) {
		return new BizException(messageSource, msgKey, msgArgs, locale);
	}

}
