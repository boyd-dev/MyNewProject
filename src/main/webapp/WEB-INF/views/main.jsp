<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"  %>


<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="<c:url value='/resources/js/jquery/jquery-1.11.3.min.js'/>"></script>
    <link rel="icon" type="image/x-icon" href="resources/favicon.ico"/>
	<link type="text/css" rel="stylesheet" href="<c:url value='/resources/css/style.css'/>" />
	<script src="<c:url value='/resources/js/validation/formCheck.js'/>"></script>

	<title>Main</title>
</head>

<body>

<div>
    Welcome <b><sec:authentication property="name" /></b>!
    <br/>
    <!-- 간단한 메뉴를 구성하기로 한다. -->
    <ul>
        <li><a href="<c:url value='/board/boardList.do?boardId=BOARD_0001'/>">게시판</a></li>
        <li><a href="<c:url value='/board/boardListFile.do?boardId=BOARD_0002'/>">첨부파일 게시판</a></li>
        <li><a href="<c:url value='/board/boardListGrid.do?boardId=BOARD_0003'/>">그리드 게시판</a></li>

    </ul>

    <form name="frm" method="post">
        <span><input type="button" value="Sign-out" title="Sign-out" onclick="javascript:fn_signOut();return false;"/></span>
    </form>
</div>

<script type="text/javascript">

	function fn_signOut() {
		document.frm.action = "<c:url value='/logout'/>";
		//document.frm.submit();
		gfn_csrf_submit('${_csrf.parameterName}', '${_csrf.token}');
	}

</script>

</body>
</html>