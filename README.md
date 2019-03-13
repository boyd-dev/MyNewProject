
Spring Security 4.0.3을 적용하여 인증 처리를 구현하였습니다. 로그인/아웃 화면도 기본화면을, 인증 provider는 UserDetailsService 인터페이스의 구현체를 사용하여 JDBC방식으로 하였습니다. 전자정부프레임워크의 구현 방식을 참조하였으나 전자정부프레임워크의 Spring Security 설정 간소화(?)를 사용하지 않고 Spring Security 그 자체의 구현 예제를 참고하였습니다.

* XML설정을 사용합니다. 

* Spring Security의 기본 필터(CSRF필터 포함)들이 모두 적용됩니다.

* 권한 관련 테이블이 추가되었습니다(dbscript/db.sql 참조)

전자정부프레임워크 [위키참조](http://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte:fdl:server_security:architecture)




 
