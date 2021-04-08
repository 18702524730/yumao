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
<title>实名认证个人 - 预猫</title>
<link href='${EXTERNAL_URL }favicon.png' rel='shortcut icon' type='image/png'>
<link href="${EXTERNAL_URL }assets/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href='${EXTERNAL_URL }css/main.css' rel='stylesheet' type='text/css'>
	<link href='${EXTERNAL_URL }css/pages3/Real-name.css' rel='stylesheet' type='text/css'>


<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src='${EXTERNAL_URL }assets/html5shiv/3.7.3/html5shiv.min.js' type='text/javascript'></script>
  <script src='${EXTERNAL_URL }assets/respond.js/1.4.2/respond.min.js' type='text/javascript'></script>
<![endif]-->
<script src='${EXTERNAL_URL }assets/jquery/1.12.4/jquery.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/bootstrap/3.3.7/js/bootstrap.min.js' type='text/javascript'></script>
</head>
<body>
	
<!--    导航-->
	<div class="container-fluid" style="box-shadow: 0 1px 18px 0 rgba(175,175,175,0.1);height:88px;">
  
		<div class="container" >
			<div class="logo">
				<img src="${EXTERNAL_URL }images/logo.png" alt="">
			</div>

			<ul class="top-nav">
				<li><a href="">我的预猫</a></li>
				<li><a href="" style="color:#4d72fe">最终boss</a></li>
				<li style="margin-right:0px;"><a href="">退出登录</a></li>
			</ul>
		
		</div>
	</div>
	
	
	<div class="container"  style="margin-top:30px;">
			
		<div class="top-head">
			<img src="${EXTERNAL_URL }images/open-service/head.png" alt="">
		</div>
		<div class="user">
			<b >我是个boss</b>
			<p >杭州信拾贝网络科技有限公司</p>
		</div>
	</div>
	
	<div class="container text-center" style="margin-top:30px;">
	
		<img src="${EXTERNAL_URL }images/open-service/stepper.png" alt="">
	
	</div>
	
	<div class="container"  style="margin-top:40px">
	
		<div class="name-box" style="height:442px;">
			<h1 class="text-center"><b style="font-size:20px;color:#333333;">确认打款</b></h1>
			
			
			
			<form class="form-horizontal text-center" style="margin-top:40px ">
			  <div class="form-group" style="margin-bottom:30px ">
				<label for="inputEmail3" class="col-xs-4 control-label">汇款金额：</label>
				<div class="col-xs-5  real-pad">
				  <input type="text" class="form-control" id="inputEmail3" placeholder="请输入银行支付业务收款回单上的汇款金额">
				</div>
			  </div>
			  <div class="form-group" style="margin-bottom:30px ">
				<label for="inputPassword3" class="col-xs-4 control-label">验证码：</label>
				<div class="col-xs-5 real-pad">
				  <input type="text" class="form-control" id="inputPassword3" placeholder="请输入银行支付业务收款回单上的验证码">
				</div>
			  </div>
				

				
				
				

			  <div class="form-group " >
				<div class="col-sm-offset-2 col-xs-10 real-up" style="margin:40px 0 0 321px;border:none;">
				  <button type="submit" class="mybluebtn">提交</button>
				</div>
			  </div>
			<p style="font-size:14px;color:#979797;text-align: center;margin-top:30px;">如有问题或异常，请联系客服</p>
			</form>			
		
		
		
		
		
		</div>
	
	
	
	
	
	
	
	
	
	
	
	
	</div>
	
	
	
	
	
	
	
	
	
	<div class="foot" style="width:100%; height:240px;	background:#FCFCFC; margin-top:130px;">
  		<jsp:include page="../widget/footer-bottom.jsp" />	

	</div>
	
	
	
	
	
</body>
</html>