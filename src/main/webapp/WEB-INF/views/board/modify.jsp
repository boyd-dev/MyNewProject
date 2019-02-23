<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" type="image/x-icon" href="<c:url value='/resources/favicon.ico'/>"/>
<link type="text/css" rel="stylesheet" href="<c:url value='/resources/css/style.css'/>" />
<script src="<c:url value='/resources/ckeditor/ckeditor.js'/>"></script>
<title>Write</title>
</head>
<body onload="fnShowMessage()">

<div style="width: 50%;">
<p>
ID: <b><c:out value="${result.cnttId}"/></b>
<br/>
<font color="red">You are about to edit...</font>
</p>
<form:form modelAttribute="postVO" name="frm" method="post">
    <form:input type="text" path="cnttTitle" cssClass="input_title" value="${postVO.cnttTitle}" />

    <textarea name="editor1" id="editor1" rows="10" cols="80">
        <c:out value="${result.cnttPost}"/>
    </textarea>
    <script>
        // Replace the <textarea id="editor1"> with a CKEditor
        // instance, using default configuration.
        CKEDITOR.replace( 'editor1' );

    </script>
    <form:input path="boardId"  type="hidden" value="${postVO.boardId}"/>
    <form:input path="cnttId"   type="hidden" value="${postVO.cnttId}"/>
    <form:input path="authorId" type="hidden" value="${postVO.authorId}"/>
    <form:input path="cnttPost" type="hidden" name="cnttPost" />
    <input type="hidden" name="pageNo" value="${param.pageNo}" />
    <span class="error_msg">
        <form:errors path="cnttTitle"/>
        <br/>
        <form:errors path="cnttPost" />
    </span>
</form:form>

</div>
<div style="width: 50%; text-align: center; margin-top: 10px;">
    <span><input type="button" value="목록" title="목록" onclick="javascript:fn_list();return false;"/></span>
    <span><input type="button" value="취소" title="취소" onclick="javascript:fn_reset();return false;"/></span>
    <span><input type="button" value="저장" title="저장" onclick="javascript:fn_save();return false;"/></span>
    <span><input type="button" value="삭제" title="삭제" onclick="javascript:fn_delete();return false;"/></span>
    <!-- submit할 때 전달하지 않는다. -->
    <input type="hidden" id="message" value="<c:out value='${_message}'/>" />
</div>


<script type="text/javascript">
	function fn_list(){
		document.frm.action = "<c:url value='/board/boardList.do'/>";
		document.frm.submit();
	}

	function fn_save() {
		var data = CKEDITOR.instances.editor1.getData();
		console.log(data);
		document.frm.cnttPost.value = data;
		document.frm.action = "<c:url value='/board/boardUpdate.do'/>";
		document.frm.submit();
	}

	function fn_delete() {
		document.frm.action = "<c:url value='/board/boardDelete.do'/>";
		document.frm.submit();
	}

	var fnShowMessage = function() {
		var msg = document.getElementById("message").value;
		if (msg != "") {
		    alert(msg);
		}
	};

</script>

</body>
</html>