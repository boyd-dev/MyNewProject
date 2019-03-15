기존 게시판 구현예제의 인증 처리를 Spring Security 4.0.3을 적용하여 구현하였습니다. CSRF필터링을 위해 화면 submit이 변경되었습니다. 
로그인/아웃 화면은 기본 제공 화면으로, 인증 provider는 UserDetailsService 인터페이스의 구현체를 사용한 JDBC방식으로 처리합니다. 전자정부프레임워크의 구현 방식을 일부 참조하였으나 전자정부프레임워크의 Security 설정 간소화(?)를 사용하지 않고 Spring Security 그 자체의 기능들을 사용합니다.

* XML설정을 사용합니다(context-security.xml 참조).

* Spring Security의 기본 필터(CSRF필터 포함)들이 모두 적용됩니다.

* 권한 관련 테이블이 다수 추가되었습니다(dbscript/db.sql 참조). 실제 사용되는 권한은 ROLE_USER로 사용자 등록시 부여 받습니다.



 
