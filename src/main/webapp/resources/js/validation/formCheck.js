/**
 * 공통적으로 사용할 수 있는 자바스크립트 함수들을 제공합니다.
 * form submit 전에 수행할 입력 검사
 *
 */

//게시판 제목과 내용 입력 여부 검사
//첫 번째 form에 대하여 적용함
//input text에 required 라는 속성을 부여해야 함
//CKEDITOR를 사용하고 그 내용을 cnttPost라는 이름으로 전달하는 경우에만 사용
function gfn_checkRequired(){

	var elements = document.forms[0].elements;
	for (k=0; k<elements.length; k++){
		if ((elements[k].type == "text") && elements[k].getAttribute("required") != null && elements[k].value.trim() == ""){
			alert(elements[k].alt + "을 입력하십시오.");
			elements[k].focus();
			return false;
		}
	}

	if (document.forms[0].cnttPost.value.trim() == "") {
		alert("내용을 입력하십시오.");
		CKEDITOR.instances.editor1.focus();
		return false;
	}

	return true;
}


//패스워드 유효성 검사
//영문자,숫자로 구성되는 4자리-8자리 글자, 시작은 영문자
function gfn_pwdValidation(str) {

	const regExpr = /^[A-Za-z]\w{3,7}$/;

	if (str.match(regExpr)) {
		return true;
	} else {
		return false;
	}
}


//Spring Security CSRF방지를 위한 토큰 포함 전송
//화면마다 폼 필드를 추가하지 않고 공통의 submit 함수에서 token 필드를 추가
//파라미터가 2개가 아니면 일반적인 submit으로 처리
function gfn_csrf_submit(...args) {

	const form = document.forms[0];

	if (args.length == 2) {
		const _csrf  = args[0];
		const _token = args[1];

		if (_csrf != null && _csrf != '' && _token != null  && _token != '') {
			console.log(_token);
			const hiddenField = document.createElement("INPUT");
			hiddenField.setAttribute("type", "hidden");
			hiddenField.setAttribute("name", _csrf);
			hiddenField.setAttribute("value", _token);

			form.appendChild(hiddenField);
		}
	}
    form.submit();
}




