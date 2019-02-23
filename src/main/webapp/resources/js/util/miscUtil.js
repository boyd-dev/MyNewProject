/**
 * 기타 유틸리티성 자바스크립트
 *
 */

// 날짜를 8자리로 받고 포맷 f를 "YYYY/MM/DD" 형태를 적용하여 다시 리턴한다.
// fn_util_dateFormatter("20190101", "YYYY.MM.DD") = 2019.01.01
function fn_util_dateFormatter(d,f){

	if (d == null || d.length != 8) return "";

	let fmt = f.toUpperCase() || "YYYY-MM-DD";

    let yyyy = d.substring(0,4);
	let mm = d.substring(4,6);
	let dd = d.substring(6);

	let rtnVal = "";
	rtnVal = fmt.replace("YYYY", yyyy);
	rtnVal = rtnVal.replace("MM", mm);
	rtnVal = rtnVal.replace("DD", dd);

	return rtnVal;
}


// 숫자-금액
// 3171030 = 3,171,030
function fn_util_numComma(n) {

	if (n == null || n.length == 0 || n == undefined) {
		n = 0;
	}

	const regExpr = /(^[+-]?\d+)(\d{3})/;
	n += '';
	while (regExpr.test(n)){
		n = n.replace(reg, '$1' + ',' + '$2');
	}
	return n;
}
