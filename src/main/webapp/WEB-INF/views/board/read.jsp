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
ID: <b><c:out value="${result.cnttId}"/></b>
<br/>
<c:out value="${result.updtTm}"/>, <c:out value="${result.authorId}"/> wrote...
</p>
<form name="frm" method="post">
    <input type="text" name="cnttTitle" class="input_title" readonly="readonly" value="<c:out value="${result.cnttTitle}"/>" />

    <textarea name="editor1" id="editor1" rows="10" cols="80" readonly="readonly">
        <c:out value="${result.cnttPost}"/>
    </textarea>
    <script>
        // Replace the <textarea id="editor1"> with a CKEditor
        // instance, using default configuration.
        CKEDITOR.replace( 'editor1' );
    </script>
    <input type="hidden" name="boardId" value="<c:out value="${result.boardId}"/>" />
    <input type="hidden" name="cnttId" value="<c:out value="${result.cnttId}"/>" />
    <input type="hidden" name="authorId" value="<c:out value="${result.authorId}"/>" />
    <input type="hidden" name="pageNo" value="${param.pageNo}" />
    <input type="hidden" name="searchKey" value="${param.searchKey}" />
    <input type="hidden" name="searchType" value="${param.searchType}" />
</form>


</div>
<div style="width: 50%; text-align: center; margin-top: 10px;">
    <span><input type="button" value="목록" title="목록" onclick="javascript:fn_list();return false;"/></span>
    <span><input type="button" value="수정" title="수정" onclick="javascript:fn_modify();return false;"/></span>
</div>


<script type="text/javascript">
	function fn_list(){
		document.frm.action = "<c:url value='/board/boardList.do'/>";
		document.frm.submit();
	}

	function fn_modify() {
		document.frm.action = "<c:url value='/board/boardMod.do'/>";
		document.frm.submit();
	}
</script>

</body>
</html>