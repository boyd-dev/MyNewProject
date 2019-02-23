<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" type="image/x-icon" href="<c:url value='/resources/favicon.ico'/>"/>
<link type="text/css" rel="stylesheet" href="<c:url value='/resources/css/style.css'/>" />
<script src="<c:url value='/resources/js/jquery/jquery-1.11.3.min.js'/>"></script>
<script src="<c:url value='/resources/js/util/miscUtil.js'/>"></script>

<!-- agGrid https://www.ag-grid.com/ -->
<script src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
<link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
<link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-balham.css">


<title>List(ag-Grid)</title>
</head>
<body>

<a href="https://www.ag-grid.com/" target="_blank"><img alt="duke" src="<c:url value='/resources/images/ag-grid-logo.jpg'/>" style="width: 70px; height: 70px;"/></a>

<div style="width: 50%;">
<form name="frm" method="post">
	<div align="left" style="float: left;">총 <span id="txtTotalCount">0</span> 건</div>
	<div align="right" style="padding-bottom: 5px">
		<span>
		    <input type="text" id="searchText" placeholder="Filter..." />
		    <input type="button" id="btnSearch" value="초기화" title="Reset" />
		</span>
		<span><input type="button" id="btnPost" value="쓰기" title="쓰기"/></span>
		<input type="hidden" name="boardId" id="boardId" value="BOARD_0003" />
		<input type="hidden" name="cnttId" value="0"/><!-- cnttId가 long형이므로 처음에는 0을 전달해야 함 -->
	</div>
    <!-- agGrid -->
    <div id="myGrid" style="height: 288px" class="ag-theme-balham"></div>

    <br/>
    <span><input type="button" id="btnSignOut" value="Sign-out" title="Sign-out" /></span>
    <span><input type="button" id="btnMain" value="Main" title="Main" /></span>
</form>
</div>


<script type="text/javascript">


    //ag-Grid
	var columnDefs = [
	    {headerName: "번호",  width: 70,  field: "cnttId"},
	    {headerName: "제목",  width: 220, field: "cnttTitle"},
	    {headerName: "작성자", width: 70, field: "authorId"},
	    {headerName: "작성일", width: 70, field: "updtTm", valueFormatter: dateFormatter},
	    {headerName: "조회수", width: 70, field: "cnttHit", type: "numericColumn"}
	];


	var gridOptions = {
	    columnDefs: columnDefs,
	    defaultColDef:{
	        sortable:true,
	        resizable: true
	    },
	    rowData: [],
	    rowSelection: 'single',
	    pagination: true,
	    paginationPageSize: 10,
	    onGridReady: function (params) {
	        params.api.sizeColumnsToFit();
	    }
	};

	var eGridDiv = document.querySelector("#myGrid");
	new agGrid.Grid(eGridDiv, gridOptions); //그리드 생성


	$(function(){

		fn_totalCount(); //화면이 열리면 즉시 조회한다.

		//두 번 클릭할 때 게시글로 이동한다.
		gridOptions.onRowDoubleClicked = function(params) {
			var selectedNodes = gridOptions.api.getSelectedNodes();
		    var selectedData = selectedNodes.map(function(node) { return node.data });
			//console.log(selectedData);

			fn_getPost(selectedData[0].cnttId);//단일선택이므로 항상 하나만 나온다.
		};

		//조회 기능 대신 클라이언트 측에서 필터링하도록 한다.
		//Quick filter
		$('#searchText').on("input", function(){
			gridOptions.api.setQuickFilter($(this).val());
		})


		//초기화
		$('#btnSearch').click(function(){
			$('#searchText').val('');
			$('#searchText').trigger('input');
	    });

		$("#btnPost").on("click", function(){
			document.frm.action = "<c:url value='/board/addPostingGrid.do'/>";
			document.frm.submit();
		});

		$("#btnSignOut").on("click", function(){
			document.frm.action = "<c:url value='/actionLogout.do'/>";
			document.frm.submit();
		});

		$("#btnMain").on("click", function(){
			document.frm.action = "<c:url value='/main.do'/>";
			document.frm.submit();
		});
	});


	var fn_totalCount = function () {

		var boardId = $("#boardId").val();
		var url = "<c:url value='/board/'/>" + boardId;
	    $.ajax(url, {
			  type : 'GET',
			  contentType: 'application/json',
			  cache: false,
		      success: function(data, status, xhr) {
		    	  $('#txtTotalCount').text(data);

		    	  //전체를 가져온다.
		    	  fn_getList();
		      },
		      error: function(data, status, xhr) {
		          console.log('FAIL! ' + status + ':' + xhr);
		      }
		});
	}

	var fn_getPost = function (v) {
		document.frm.cnttId.value = v;
		document.frm.action = "<c:url value='/board/boardCnttGrid.do'/>";
		document.frm.submit();
	}


	function fn_boardCntt(v){
		document.frm.cnttId.value = v;
		document.frm.action = "<c:url value='/board/boardCntt.do'/>";
		document.frm.submit();
	}


	var fn_getList = function () {

		var url = "<c:url value='/board/boardGrid.do'/>";
	    var boardId = $("#boardId").val();
	    //한 번에 전부 가져와서 클라이언트 측에서 그리드 페이징한다.
	    //TODO 건수가 많아지면 ag-Grid의 server-side row model을 고려한다.
	    var recordCountPerPage = $('#txtTotalCount').text();
	    var searchCond = {'boardId': boardId, 'recordCountPerPage': recordCountPerPage};

	    $.ajax(url, {
			  type : 'POST',
			  contentType: 'application/json',
			  data: JSON.stringify(searchCond),
			  dataType: 'json',
			  cache: false,
		      success: function(data, status, xhr) {
		    	  //console.log('SUCCESS!');
		    	  fn_callbackSuccess(data);
		      },
		      error: function(data, status, xhr) {
		          console.log('FAIL! ' + status + ':' + xhr);
		      }
		});
	}

	var fn_callbackSuccess = function (data) {
		//console.log(JSON.stringify(data));
		gridOptions.api.setRowData(data); //그리드에 데이터를 로드한다.
		gridOptions.api.sizeColumnsToFit();
	}

    function dateFormatter(params) {
		return fn_util_dateFormatter(params.value, "YYYY/MM/DD");
	}

</script>
</body>
</html>