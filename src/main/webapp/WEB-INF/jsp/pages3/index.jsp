<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>

<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang='zh'>
<head>
<meta charset='UTF-8'>
<meta name='viewport' content='width=device-width, initial-scale=1.0'>
<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1' />
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<meta name='renderer' content='webkit'>

<title>开通服务 - 预猫</title>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src='${EXTERNAL_URL }assets/html5shiv/3.7.3/html5shiv.min.js' type='text/javascript'></script>
  <script src='${EXTERNAL_URL }assets/respond.js/1.4.2/respond.min.js' type='text/javascript'></script>
<![endif]-->
</head>
<body>
    <input id="external-url" type="hidden" value="${EXTERNAL_URL }">
    
    <a href="${EXTERNAL_URL }user/account.html">个人中心首页</a>
    <a href="${EXTERNAL_URL }pages3/dialog.html">弹出框</a>
    <a href="${EXTERNAL_URL }pages3/open-service.html">服务开通</a>
	<a href="${EXTERNAL_URL }pages3/renew-service.html">立即续费</a>
    <a href="${EXTERNAL_URL }pages3/Real-name.html">实名个人</a>
    <a href="${EXTERNAL_URL }pages3/Real-name-enterprise.html">实名企业</a>
	 <a href="${EXTERNAL_URL }pages3/open-success.html">开通成功</a>
		 <a href="${EXTERNAL_URL }pages3/open-warning.html">开通中</a>
		 <a href="${EXTERNAL_URL }pages3/Verification.html">验证</a>

	 <a href="${EXTERNAL_URL }pages3/account-Basic.html">账户信息</a>
	<a href="${EXTERNAL_URL }pages3/account-enterprise.html">账户信息-企业</a>

	 <a href="${EXTERNAL_URL }pages3/save.html">我的存证</a>
	 <a href="${EXTERNAL_URL }pages3/chain.html">我的证据链</a>
	
		 <a href="${EXTERNAL_URL }pages3/chain-details.html">我的证据链-详情</a>

	
	
<a href="${EXTERNAL_URL }pages3/protect.html">我的维权</a>
	
	
<a href="${EXTERNAL_URL }pages3/Popup.html">弹窗</a>

	
	
	
	
</body>
</html>