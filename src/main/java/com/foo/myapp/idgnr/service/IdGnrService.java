package com.foo.myapp.idgnr.service;

public interface IdGnrService {

	//ID 는 숫자 또는 문자열일 수도 있으므로 리턴 타입을 그냥 객체로 한다.
	Object getNextId() throws Exception;

	//알고리즘 기반의 유일키를 제공 받을 수 있다.
	String getNextStringId() throws Exception;

}
