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
<body>

<div style="width: 50%;">
<p>
<font color="blue">You are about to edit...</font>
</p>
<form:form modelAttribute="postVO" name="frm" method="post">
    <form:input type="text" path="cnttTitle" cssClass="input_title" placeholder="제목을 입력하십시오."/>

    <textarea name="editor1" id="editor1" rows="10" cols="80">
    </textarea>
    <script>
        // Replace the <textarea id="editor1"> with a CKEditor
        // instance, using default configuration.
        CKEDITOR.replace( 'editor1' );

    </script>
    <form:input path="boardId" type="hidden" />
    <form:input path="cnttPost" type="hidden" name="cnttPost" />
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
</div>


<script type="text/javascript">

	function fn_list(){
		document.frm.action = "<c:url value='/board/boardListGrid.do'/>";
		document.frm.submit();
	}

	function fn_save() {
		var data = CKEDITOR.instances.editor1.getData();
		//console.log(data);
		document.frm.cnttPost.value = data;
		document.frm.action = "<c:url value='/board/boardSaveGrid.do'/>";
		document.frm.submit();
	}
</script>

</body>
</html>