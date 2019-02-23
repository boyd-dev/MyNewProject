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
<link type="text/css" rel="stylesheet" href="<c:url value='/resources/js/filepond/filepond.css'/>" >
<script src="<c:url value='/resources/ckeditor/ckeditor.js'/>"></script>
<script src="<c:url value='/resources/js/filepond/filepond.foo.min.js'/>"></script><!-- 한글 문제 때문에 수정한 스크립트를 사용 -->
<script src="<c:url value='/resources/js/validation/formCheck.js'/>"></script>

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
    <form:input type="text" path="cnttTitle" cssClass="input_title" value="${postVO.cnttTitle}" required="required" alt="제목"/>

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

    <!-- 첨부파일의 갯수는 최대 3개로 하고 각 파일ID를 저장하기 위해 만든다. -->
    <!-- 3개 미만인 경우에는 3개로 맞추어 주기 위해 비어 있는 필드를 생성한다. -->
    <c:set var="fileListSize" value="${fn:length(fileList)}"/>
    <c:forEach var="result" items="${fileList}" varStatus="status">
        <input type="hidden" name="filepondFileId" value="${result.fileId}" />
    </c:forEach>
    <c:if test="${fileListSize lt 3}">
        <c:forEach begin="0" end="${2-fileListSize}">
            <input type="hidden" name="filepondFileId"/>
        </c:forEach>
    </c:if>

</form:form>

<!-- filepond에서 사용하는 file type으로 form 태그와는 별도로 사용된다. -->
<input type="file"/>

</div>
<div style="width: 50%; text-align: center; margin-top: 10px;">
    <span><input type="button" value="목록" title="목록" onclick="javascript:fn_list();return false;"/></span>
    <span><input type="button" value="취소" title="취소" onclick="javascript:fn_reset();return false;"/></span>
    <span><input type="button" value="저장" title="저장" onclick="javascript:fn_save();return false;"/></span>
    <span><input type="button" value="삭제" title="삭제" onclick="javascript:fn_delete();return false;"/></span>
    <!-- submit할 때 전달할 필요 없는 값 -->
    <input type="hidden" id="message" value="<c:out value='${_message}'/>" />
</div>


<script type="text/javascript">

	//filepond//////////////////////////////////////////////////////////////
	//업로드된 파일들을 filepond에 보여주기 위하여 배열을 만든다.
    var ufiles = [];
    var ufileIds = document.getElementsByName("filepondFileId");
    for (let i=0; i<ufileIds.length; i++) {
    	if (ufileIds[i].value != '') {
    		ufiles.push({source: ufileIds[i].value, options:{type:"limbo"}}); //restore를 사용할 때는 limbo 타입이다.
    	}
    }

	const f = document.querySelector('input[type="file"]');
	const pond = FilePond.create(f, { maxFiles: 3,
		                              allowMultiple: true,
		                              server: { url: "<c:url value='/board'/>",
		                            	        process: {url: "/boardSaveFile.do" },
		                            	        revert: function (fileId, load, error) { fn_revertFile(fileId); load(); },
		                            	        restore: {url: "/fileList.do?fileId=" }
		                              },
		                              files: ufiles
		                              //instantUpload: false
		                            }
	);

	//업로드가 모두 처리된 후 호출되는 callback
    //filepond에 파일이 표시 되면 이 함수가 호출된다. 결과적으로 신규등록과 같은 상태가 된다.
    var uploadedfiles = [];
    pond.on('processfile', function (e, f) {
        uploadedfiles.push(f.serverId); //저장할 때 전달해야 하므로 배열에 저장해둔다.
    });

    //파일 삭제는 AJAX로 처리하여 화면이 다시 로드되지 않도록 한다.
    var x = new XMLHttpRequest();
	var handleStateChange = function () {
		/*
        0: request not initialized
        1: server connection established
        2: request received
        3: processing request
        4: request finished and response is ready
        */
		if(x.readyState == 4 && x.status == 200){ //정상처리
		    console.log(x.responseText);
		} else {
		    console.log(x.readyState);
		}
	};

	//filepond의 삭제를 클릭했을 때 호출되는 함수
	function fn_revertFile(fileId) {

		//배열에서 해당 파일ID를 제거한다.
		let p = uploadedfiles.indexOf(fileId);
		uploadedfiles.splice(p,1);

		//파일 삭제 요청을 전송한다.
		if (window.XMLHttpRequest) {
		    x = new XMLHttpRequest();
			x.open("POST", "<c:url value='/board/boardDeleteFile.do'/>", true); //async
			x.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			x.onreadystatechange = handleStateChange;
			x.send("fileId=" + fileId); //POST방식일때 send()를 사용하여 querystring형태로 전달한다.
	   		//str = x.responseText;	//sync일때는 여기서 결과를 받을 수 있다.
			//x=null;
		}
	}

	////////////////////////////////////////////////////////////////////////

	function fn_list(){
		document.frm.action = "<c:url value='/board/boardListFile.do'/>";
		document.frm.submit();
	}

	function fn_save() {

		uploadedfiles.forEach(function(id, index) {
			document.frm.filepondFileId[index].value = id;
		});

		var data = CKEDITOR.instances.editor1.getData();
		//console.log(data);
		document.frm.cnttPost.value = data;
		document.frm.action = "<c:url value='/board/boardUpdateFile.do'/>";

		if (fn_checkRequired()) {
			document.frm.submit();
		}
	}

	function fn_delete() {
		document.frm.action = "<c:url value='/board/boardDeleteWithFiles.do'/>";
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