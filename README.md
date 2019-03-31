기존 게시판 구현예제의 인증 처리를 Spring Security OAuth2 2.0.12을 적용하여 구현하였습니다. 게시판 사용을 위한 <b>클라이언트</b> 인증 예제가 되겠습니다. Google을 Authentication Provider로 사용하였으므로 [Developer console](https://console.developers.google.com)에서 client_id, client_secret을 발급받아서 global.properties에 설정해야 합니다.

* redirecton URI는 	http://localhost:8080/.../oauth2/commence 으로 설정하였습니다. 이를 바꾸는 경우 global.properties의 내용도 변경하십시오. 

* Spring Security OAuth2 설정은 XML 방식으로 합니다(context-security.xml 참조).

* 이메일과 프로파일을 scope로 하여 이메일을 사용자 아이디로 사용하고 DB에 저장하지는 않습니다. 토큰이나 기타 다른 정보를 저장하지 않고 인증 용도로만 사용합니다.



 
