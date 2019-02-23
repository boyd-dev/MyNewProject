스프링 4 기준으로 레거시 스프링 MVC 구조의 클래식한(?) 학습용 게시판입니다. 🚀

거의 전자정부프레임워크를 참조하였습니다만 패키지명을 변경하고 게시판에 필요한 인터페이스와 
클래스만을 넣었습니다. 
처음 접하는 분들을 위해 각각 소스를 분리(컨트롤러와 화면)하여 복잡하게 만들지 않습니다. 
여러 기능이 있는 게시판이 아니라 세 개의 게시판을 각각 만들었습니다. 예제로 참고하기 바랍니다. 

<b>다음 3개의 게시판이 있습니다. </b>

1) 일반 게시판

그냥 글만 쓰는 게시판입니다. 단순한 형태의 CKEditor를 사용합니다.

2) 첨부파일 게시판 

일반 게시판에 첨부파일(여러 개) 기능을 추가한 게시판입니다.
첨부파일은 <a href="https://github.com/pqina/filepond">filepond</a>라는 자바스크립트 파일첨부 라이브러리를 활용했습니다.
덕분에 구현부가 단순해지고 보기 좋은(?) 첨부파일 기능이 제공됩니다. 😄

3) 그리드 게시판

일반 게시판의 게시판 목록을 자바스크립트 그리드로 바꾼 버전입니다(목록을 두 번 클릭하여 게시물 내용을 봅니다).
그리드의 client-side 페이징을 이용합니다. 그리드 라이브러리는 <a href="https://www.ag-grid.com/">ag-Grid</a>를 사용합니다.

<b>기타사항</b>

0) 페이지 디자인은 관심 밖입니다.

1) 데이터베이스는 MySQL입니다. 테이블은 총 5개입니다. 
/src/main/webapp/resources/dbscript/db.sql에 MySQL용 스크립트가 있습니다.
globals.properties에서 데이터베이스 연결정보를 설정합니다.
MyBatis를 사용하고 mapper인터페이스 구현 예제를 일부 포함시켰습니다.

2) 페이징은 전자정부프레임워크의 페이징 인터페이스와 클래스를 이용합니다.<a href="http://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte:ptl:view:paginationtag&s[]=pagination">[관련 도움말]</a>
ImagePaginationRenderer를 구현예제로 추가했습니다.

3) 유일키는 전자정부프레임워크의 유일키 생성 부분을 이용합니다. <a href="http://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte:fdl:id_generation&s[]=id&s[]=generation">[관련 도움말]</a>
채번 테이블(T_SEQ_MASTER)을 사용하고 10개씩 캐싱합니다.

4) 세션 기반의 인증으로 인터셉터를 사용합니다.

5) 빈 유효성 검사를 단순하게 적용합니다(@Valid). <a href="http://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte2:ptl:validation">[관련 도움말]</a>

6) 스프링 설정은 전자정부프레임워크에서 권장(?)하는 XML로 합니다.

7) 컨텍스트 루트가 /myapp인데 하드코딩된 부분이 있을 수 있습니다.😂

8) 일부 구현되지 않은 기능이나 설정은 각자의 환경에 맞추기 바랍니다.

복잡한 소스를 최대한 줄이고 기본적인 흐름을 이해할 수 있도록 단순하게 구성한 예제이므로 
스프링 MVC를 처음 접하는 분들에게 도움이 되었으면 하는 바람입니다.

STS 4부터는 legacy Spring MVC가 지원되지 않습니다. 
이 예제는 STS 3.8.4과 오라클 JDK 1.8.0_162에서 만들어졌습니다. Servlet 버전은 2.5입니다.

<b>사용법</b>

Sign-up 화면에서 사용자등록 후 로그인해서 게시판에 글을 작성하면 됩니다.

<b>면책조항</b>

학습 목적 외의 용도로 사용하여 발생하는 모든 문제는 책임지지 않습니다.😅

