
var port = "8080";

function getStateUrl(){
	return "http://www.hkjf.com:" + port + "/loginController.do?getState";
}

function getSyncUrl(){
	return "http://www.hkjf.com:" + port + "/loginController.do?syncState";
}


function getSyncUrl2(){
	return "http://www.hkjf.com:" + port + "/syncState.html";
}

function getSyncUrl3(){
	return "http://www.qsh.com:" + port + "/loginController.do?syncState";
}

function getPassport(){
	return "http://www.hkjf.com:" + port + "/passport.html?target=";
}

function getTarget(){
	return "http://www.qsh.com:" + port + "/qshIndex.html"
}

function getCookie(name) {
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
}

function setCookie(name,value) {
	var Days = 30;
	var exp = new Date();
	exp.setTime(exp.getTime() + Days*24*60*60*1000);
	document.cookie = name + "=" + escape (value) + ";expires=" + exp.toGMTString();
}

function delCookie(name) {
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval=getCookie(name);
	if(cval != null){
		document.cookie= name + "=" + escape (cval) + ";expires="+exp.toGMTString();
	}
}

function getUrlParam(name) {
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null){
		return  unescape(r[2]); 
	}
	return null;
}
