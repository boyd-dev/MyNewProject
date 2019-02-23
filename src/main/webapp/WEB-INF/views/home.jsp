<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="icon" type="image/x-icon" href="resources/favicon.ico"/>
	<link type="text/css" rel="stylesheet" href="<c:url value='/resources/css/style.css'/>" />
	<title>Home - Spring MVC</title>

	<style type="text/css">
	    body {font-family: Consolas;}
	</style>
</head>
<body>
<h1>
	Hello world!
</h1>

<p>  The time on the server is ${serverTime}. </p>

<br/>
<a href="<c:url value='/login.do'/>"><b>[SIGN-IN]</b></a>
<br/><br/>
Want to join? click <a href="<c:url value='/signup.do'/>">[SIGN-UP]</a>


</body>
</html>
