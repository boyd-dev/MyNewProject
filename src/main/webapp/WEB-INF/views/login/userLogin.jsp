<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="<c:url value='/resources/js/jquery/jquery-1.11.3.min.js'/>"></script>
    <link rel="icon" type="image/x-icon" href="resources/favicon.ico"/>
	<link type="text/css" rel="stylesheet" href="<c:url value='/resources/css/style.css'/>" />
	<title>Main</title>
</head>

<body>

<div>

    <section class="main">
    	<div>
    	<img src="<c:url value='/resources/images/rocket.png'/>"/>
		<form name="loginForm" method="post">
			<div class="inputbox"><label for="id">User ID</label><input type="text" name="mberId" id="id" class="input_text" tabindex="1" maxlength="12"/></div>
			<div class="inputbox"><label for="pw">Password</label><input type="password" name="passwd" id="pw" class="input_text" tabindex="2" maxlength="8" onkeypress="if (event.keyCode==13) fnLogin();" /></div>
			<div class="inputbox" style="height:20px;">
				<label style="height:20px;"></label><input style="vertical-align:bottom; width:18px; height:18px;" type="checkbox" name="checksaveid" id="checksaveid" value="checkbox" /> Cookie ID
			</div>
			<input type="button" id="btnLogin" value="Sign-in" class="login" />
			<input type="button" id="btnSignup" value="Sign-up" />
		</form>
		</div>
	</section>
	<input type="hidden" id="message" value="<c:out value='${_message}'/>" />

</div>

<script type="text/javascript">
	$(function(){

		fnShowMessage();

		//브라우저의 Cookie에 아이디가 저장되어 있는지 확인
		getCookieId();

		$('#btnLogin').click(function(){
			setCookieId();
			fnLogin();
		});


		$('#btnSignup').click(function(){
			location.href = "<c:url value='/signup.do'/>";
		});

	});



var fnLogin = function() {
    if (document.loginForm.id.value == "") {
        alert("아이디를 입력하십시오.");
        document.loginForm.id.focus();
    } else if (document.loginForm.passwd.value == "") {
        alert("비밀번호를 입력하십시오.");
        document.loginForm.passwd.focus();
    } else {
        document.loginForm.action="<c:url value='/actionLogin.do'/>";
        document.loginForm.submit();
    }
};

var fnShowMessage = function() {
	if ($.trim($('#message').val()) != '') {
	     alert($('#message').val());
	}
};


// Cookie로 아이디 저장하기
function setCookieId() {
	var form = document.loginForm;
	var expdate = new Date();

	if(form.checksaveid){
		if (form.checksaveid.checked){
			expdate.setTime(expdate.getTime() + 1000 * 3600 * 24 * 30); // 30일(밀리세컨드)
		} else {
			expdate.setTime(expdate.getTime() - 1); //쿠키삭제
		}
		setCookie("saveid", form.id.value, expdate);
	}
}

function getCookieId() {
	var form = document.loginForm;
	if(form.checksaveid){
  		form.checksaveid.checked = ((form.id.value = getCookie("saveid")) != "");
  	}
}

function setCookie (name, value, expires) {
    document.cookie = name + "=" + escape (value) + "; path=/; expires=" + expires.toGMTString();
}

function getCookie(name) {
	var search = name + "=";
	if (document.cookie.length > 0) { // 쿠키가 설정되어 있다면
	  offset = document.cookie.indexOf(search);
	  if (offset != -1) { // 쿠키가 존재하면
	    offset += search.length;
	    // set index of beginning of value
	    end = document.cookie.indexOf(";", offset);
	    // 쿠키 값의 마지막 위치 인덱스 번호 설정
	    if (end == -1)
	      end = document.cookie.length;
	    return unescape(document.cookie.substring(offset, end));
	  }
	}
	return "";
}

</script>



</body>
</html>