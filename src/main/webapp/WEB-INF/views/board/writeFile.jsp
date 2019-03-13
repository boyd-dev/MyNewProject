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
<script src="<c:url value='/resources/js/filepond/filepond.min.js'/>"></script>
<script src="<c:url value='/resources/js/validation/formCheck.js'/>"></script>

<title>Write</title>
</head>
<body>

<div style="width: 50%;">
<p>
<font color="blue">You are about to edit...</font>
</p>
<form:form modelAttribute="postVO" name="frm" method="post">
    <form:input type="text" path="cnttTitle" cssClass="input_title" placeholder="제목을 입력하십시오." required="required" alt="제목"/>

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
    <!-- 첨부파일의 갯수는 최대 3개로 하고 각 파일ID를 저장하기 위해 만든다. -->
    <input type="hidden" name="filepondFileId" />
    <input type="hidden" name="filepondFileId" />
    <input type="hidden" name="filepondFileId" />
</form:form>

<!-- filepond에서 사용하는 file type으로 form 태그와는 별도로 사용된다. -->
<input type="file"/>

</div>
<div style="width: 50%; text-align: center; margin-top: 10px;">
    <span><input type="button" value="목록" title="목록" onclick="javascript:fn_list();return false;"/></span>
    <span><input type="button" value="취소" title="취소" onclick="javascript:fn_reset();return false;"/></span>
    <span><input type="button" value="저장" title="저장" onclick="javascript:fn_save();return false;"/></span>
</div>


<script type="text/javascript">

    //filepond//////////////////////////////////////////////////////////////
    //https://pqina.nl/filepond/docs/
    const f = document.querySelector('input[type="file"]');
    const pond = FilePond.create(f, { maxFiles: 3,
    	                              allowMultiple: true,
    	                              server: { url: "<c:url value='/board'/>",
    	                            	        process: {url: "/boardSaveFile.do?_csrf=" + "${_csrf.token}" },
    	                            	        revert: function (fileId, load, error) { fn_revertFile(fileId); load(); }
    	                              }
    	                              //instantUpload: false
    	                            }
    );

    /*
    pond.on('addfile', function(e, f) {
        if (e) {
            return;
        }
        console.log('File added', f.fileSize);
    });
    */

    //업로드가 모두 처리된 후 호출되는 callback
    //서버로 부터 받은 파일 키를 배열에 저장하고 필요할 때 사용한다.
    var uploadedfiles = [];
    pond.on('processfile', function (e, f) {
        console.log(f.serverId);
        uploadedfiles.push(f.serverId);
    });


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

	function fn_revertFile(fileId) {

		let p = uploadedfiles.indexOf(fileId);
		uploadedfiles.splice(p,1);

		//고전적인 AJAX
		if (window.XMLHttpRequest) {
		    x = new XMLHttpRequest();
			x.open("POST", "<c:url value='/board/boardDeleteFile.do?_csrf='/>" + "${_csrf.token}", true); //async
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
		gfn_csrf_submit('${_csrf.parameterName}', '${_csrf.token}');
	}

	function fn_save() {

		uploadedfiles.forEach(function(id, index) {
			document.frm.filepondFileId[index].value = id;
		});

		var data = CKEDITOR.instances.editor1.getData();
		document.frm.cnttPost.value = data;
		document.frm.action = "<c:url value='/board/boardSaveAttchFile.do'/>";

		if (gfn_checkRequired()) {
			gfn_csrf_submit('${_csrf.parameterName}', '${_csrf.token}');
		}

	}

</script>

</body>
</html>