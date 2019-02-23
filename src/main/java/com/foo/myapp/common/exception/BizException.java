package com.foo.myapp.common.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;


/**
 * 비지니스로직 예외 발생시 예외 객체를 생성하여 리턴한다.
 *
 *
 */
public class BizException extends Exception {

	private static final long serialVersionUID = 1L;

	protected String message = null;
	protected String messageKey = null;
	protected String[] messageParameters = null;
	protected Exception exception = null;


    public String getMessage() {
		return message;
	}

	public BizException() {
		this("BizException: No message"); //다른 생성자를 호출할 때 this()를 사용한다.
	}

	public BizException(String message) {
		this.message = message;
	}


	//여기서 MessageSource 를 DI 하려면 constructor injection 이 되는데 그것은 일반적인 방식은 아닌 것 같다.
	public BizException(MessageSource messageSource, String messageKey, String[] messageParameters, Locale locale) {

		this.messageKey = messageKey;
		this.messageParameters = messageParameters;
		this.message = messageSource.getMessage(messageKey, messageParameters, locale);

	}


}
