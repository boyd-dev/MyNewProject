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
	<script src="<c:url value='/resources/js/validation/formCheck.js'/>"></script>
	<title>Sign-Up</title>
</head>

<body>

<div>
     <section class="signup">
    	<div>
		<form name="signupForm" method="post">
			<div class="inputbox"><label for="id">User ID</label><input type="text" name="mberId" id="id" class="input_text" tabindex="1" maxlength="8" /></div>
			<div class="inputpwbox"><label for="pw">Password</label><input type="password" name="passwd" id="pw" class="input_text" tabindex="2" maxlength="7" /></div>
			<div class="inputpwbox"><label for="cpw">Confirm password</label><input type="password" name="cpasswd" id="cpw" class="input_text" tabindex="3" maxlength="7" /></div>
			<input type="button" id="btnSignup" value="Sign-up"/>
			<input type="button" id="btnReset" value="Reset"/>
		</form>
		</div>
	 </section>
	 <input type="hidden" id="message" value="<c:out value='${_message}'/>" />

</div>

<script type="text/javascript">

    var bDup = false;

	$(function(){

		fnShowMessage();

		$('#btnSignup').click(function(){
			fn_signup();
		});

		$('#btnReset').click(function(){
            $('input[type!=button]').val('');
		});


		function fn_signup() {

			let id = $('#id').val().trim();
			let pw = $('#pw').val().trim();
			let cpw = $('#cpw').val().trim();

		    if (id === "") {
		        alert("아이디를 입력하십시오.");
		        document.signupForm.id.focus();
		    } else if (pw === "") {
		        alert("비밀번호를 입력하십시오.");
		        document.signupForm.passwd.focus();
		    } else {

		    	if (gfn_pwdValidation(pw)) {

		    		if (cpw === "" || (cpw !== pw)) {
		    	        alert("비밀번호를 확인하십시오.");
		 		        document.signupForm.cpasswd.focus();
		    		} else {
		    			if (!bDup) {
		    				document.signupForm.action="<c:url value='/subscrb/userSignup.do'/>";
				            document.signupForm.submit();
		    			} else {
		    				alert("이미 존재하는 아이디입니다. 다른 아이디를 입력하십시오.");
		    			}
		    		}
		    	} else {
		    		alert("비밀번호는 알파벳으로 시작하는 4-8자 이내의 알파벳과 숫자입니다.");
		    		$('#pw').val('');
					$('#cpw').val('');
		    	}
		    }
		};


		var fn_checkDuplication = function (){
		    var mberId = $('#id').val().trim();
		    var url = "<c:url value='/subscrb/'/>" + mberId;

		    if (mberId.length > 0) {
		    	$.ajax(url, {
					  type : 'GET',
					  contentType: 'application/json',
					  cache: false,
				      success: function(data, status, xhr) {
				    	  //console.log(data);

				    	  if (parseInt(data) > 0) {
				    		  bDup = true;
					    	  alert("이미 존재하는 아이디입니다. 다른 아이디를 입력하십시오.");
					    	  $('#id').focus();
				    	  } else {
				    		  bDup = false;
				    	  }

				      },
				      error: function(data, status, xhr) {
				          console.log('FAIL! ' + status + ':' + xhr);
				      }
				});
		    }
		}

		$('#id').on("change", function(){
			fn_checkDuplication();
		});

	});


	var fnShowMessage = function() {
		if ($.trim($('#message').val()) != '') {
		     alert($('#message').val());
		}
	};

</script>

</body>
</html>