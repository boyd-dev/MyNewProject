<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ui" uri="http://foo.com/tag" %><!-- 페이징 커스텀 태그 -->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" type="image/x-icon" href="<c:url value='/resources/favicon.ico'/>"/>
<link type="text/css" rel="stylesheet" href="<c:url value='/resources/css/style.css'/>" />
<title>List</title>
</head>
<body>

<br/><br/>

<div style="width: 50%;">
<form name="frm" method="post">
    <div align="left" style="float: left;">총 <c:out value="${resultCnt}"/> 건</div>
    <div align="right" style="padding-bottom: 5px">
            <span>
                <select name="searchType">
                    <option value="">선택</option>
                    <option value="TITLE" <c:if test='${searchVO.searchType eq "TITLE"}'>selected</c:if>>제목</option>
                    <option value="AUTHOR" <c:if test='${searchVO.searchType eq "AUTHOR"}'>selected</c:if>>작성자</option>
                </select>
                <input type="text" name="searchKey" value="<c:out value='${searchVO.searchKey}'/>" />
                <input type="button" value="조회" title="조회" onclick="javascript:fn_list();return false;" />
            </span>
            <span><input type="button" value="쓰기" title="쓰기" onclick="javascript:fn_posting();return false;"/></span>
            <input type="hidden" name="boardId"  value="BOARD_0001" />
            <input type="hidden" name="pageNo" value="<c:out value='${searchVO.pageNo}'/>"/>
            <input type="hidden" name="cnttId" value="0"/><!-- cnttId가 long형이므로 처음에는 0을 전달해야 함 -->

    </div>
<table width="100%" cellpadding="5" class="table-line">
     <colgroup>
         <col width="10%"/>
         <col width="*"/>
         <col width="15%"/>
         <col width="15%"/>
         <col width="10%"/>
     </colgroup>
	 <thead>
	  <tr>
	    <th>번호</th>
	    <th>제목</th>
	    <th>작성자</th>
	    <th>작성일</th>
	    <th>조회수</th>
	  </tr>
	 </thead>
	 <tbody>
		 <c:forEach var="result" items="${resultList}" varStatus="status">
		  <tr>
		    <td nowrap align="center">
		        <c:out value="${(searchVO.pageNo-1) * searchVO.recordCountPerPage + status.count}"/>
		        |
		        <c:out value="${resultCnt - ((searchVO.pageNo-1) * searchVO.recordCountPerPage) - status.index}" />
		    </td>
		    <td>
		        <a href="javascript:fn_boardCntt('<c:out value="${result.cnttId}"/>')"><c:out value="${result.cnttTitle}"/></a>
		    </td>
		    <td nowrap align="center">
		        <c:out value="${result.authorId}"/>
		    </td>
		    <td nowrap align="center">
		        <fmt:parseDate var="updtTm" value="${result.updtTm}" pattern="yyyyMMdd" />
		        <fmt:formatDate type="date" value="${updtTm}" pattern="yyyy-MM-dd"/>
		    </td>
		    <td align="right">
		        <c:out value="${result.cnttHit}"/>
		    </td>
		  </tr>
		 </c:forEach>
		 <c:if test="${fn:length(resultList) == 0}">
		    <tr>
		       <td nowrap colspan="5"><spring:message code="common.nodata.msg" /></td>
		    </tr>
		 </c:if>
	 </tbody>
</table>

    <div align="center" class="pagination">
        <ui:pagination paginationInfo="${pagingInfo}" type="image" jsFunction="fn_page"></ui:pagination>
	</div>
	<span><input type="button" value="Sign-out" title="Sign-out" onclick="javascript:fn_signOut();return false;"/></span>
	<span><input type="button" value="Main" title="Main" onclick="javascript:fn_main();return false;"/></span>

</form>
</div>


<script type="text/javascript">
	function fn_list(){
		document.frm.action = "<c:url value='/board/boardList.do'/>";
		document.frm.submit();
	}

	function fn_posting(){
		document.frm.action = "<c:url value='/board/addPosting.do'/>";
		document.frm.submit();
	}

	function fn_page(pageNo){
		document.frm.pageNo.value = pageNo;
		document.frm.action = "<c:url value='/board/boardList.do'/>";
		document.frm.submit();
	}

	function fn_boardCntt(v){
		document.frm.cnttId.value = v;
		document.frm.action = "<c:url value='/board/boardCntt.do'/>";
		document.frm.submit();
	}

	function fn_signOut() {
		document.frm.action = "<c:url value='/actionLogout.do'/>";
		document.frm.submit();
	}

	function fn_main() {
		document.frm.action = "<c:url value='/main.do'/>";
		document.frm.submit();
	}

</script>


</body>
</html>