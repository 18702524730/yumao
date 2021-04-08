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
<title>我的存证 - 预猫</title>
<link href='${EXTERNAL_URL }favicon.png' rel='shortcut icon' type='image/png'>
<link href="${EXTERNAL_URL }assets/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href='${EXTERNAL_URL }css/main.css' rel='stylesheet' type='text/css'>
<link href='${EXTERNAL_URL }css/pages3/save.css' rel='stylesheet' type='text/css'>


<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src='${EXTERNAL_URL }assets/html5shiv/3.7.3/html5shiv.min.js' type='text/javascript'></script>
  <script src='${EXTERNAL_URL }assets/respond.js/1.4.2/respond.min.js' type='text/javascript'></script>
<![endif]-->
<script src='${EXTERNAL_URL }assets/jquery/1.12.4/jquery.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/bootstrap/3.3.7/js/bootstrap.min.js' type='text/javascript'></script>
	
	
			<style type="text/css">
				ul.nav-tabs >li>div {
					background:transparent!important;
				}
				ul.nav-tabs >li.active>div {
					background:#4D72FE!important;
				}
				ul.nav-tabs >li>a>b{
					color:#979797;
				}
				ul.nav-tabs >li.active>a>b{
					color:#000;
				}
				
			</style>
	
	
	
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
	

	
	
	<div class="container" style="height:885px;border:1px solid #eee;margin-top:30px;padding:0px;">
		<div class="left-nav">
			<div class="l-n-user text-center">
				<div style="width:70px;height:70px;background:#fff;border-radius: 50%;margin:16px 0  10px 60px; overflow: hidden">
					<div style="width:66px;height:66px; margin:2px 0 0 2px">
						<img src="${EXTERNAL_URL }images/account/head.png" alt="">
					</div>
				</div>
				
				<b style="font-size:16px;color:#fff;">我是个boss</b>
				<p style="font-size:12px;color:#fff;margin-top:10px">杭州信拾贝网络科技有限公司</p>
				<div style="width:91px; height:25px;border-radius: 13px;background:#fff;text-align: center;margin-top:16px;margin-left:50px">
					<a href="${EXTERNAL_URL }alipay/submit.html?orderType=2" style="display: inline-block;width:100%;height: 100%;font-size:12px;color:#444444;line-height: 24px;cursor: pointer;">立即续费</a>
				
				</div>
			</div>
			
			
			<div class="l-n-service">
				<div class="account" >
					<div style="float:left;margin-left:40px;">
						<img src="${EXTERNAL_URL }images/account/account-h.png" alt="">
					</div>
					<a style="font-size:16px;color:#333333;margin-left:15px;">账号管理</a >
				
				</div>
				
				<div class="save" >
					<div style="float:left;margin-left:40px;">
						<img src="${EXTERNAL_URL }images/account/save.svg" alt="">
					</div>
					<a style="font-size:16px;color:#333333;margin-left:15px;">我的存证</a>
				
				</div>
				<div class="chain" style="	border-left:6px solid #4D72FE;">
					<div style="float:left;margin-left:40px;">
						<img src="${EXTERNAL_URL }images/account/chain-b.png" alt="">
					</div>
					<a style="font-size:16px;color:#4D72FE;margin-left:15px;">我的证据链</a >
				
				</div>
				<div class="protect">
					<div style="float:left;margin-left:40px;">
						<img src="${EXTERNAL_URL }images/account/protect.svg" alt="">
					</div>
					<a style="font-size:16px;color:#333333;margin-left:15px;">我的维权</a>
				
				</div>
			
			
			
			</div>
			
			
			
		
		
		</div>
		
		<div class="right-content" >
			
			<h1 style="margin:38px 0 0 48px"><b style="color:#040404;font-size:20px">我的证据链</b></h1>
			<div style="margin-top:44px" >
			
				<button style="width:140px;height45px;background:rgba(77, 114, 254, 0.8);line-height: 45px ;text-align: center;float:left;margin-left:48px;color:#fff">添加证据链</button>
			
				<div class="search" style="float:right;margin-right:30px;">
					<p style="display: inline-block;height:45px;line-height: 45px;font-size:16px;color:#979797">证据链名称：</p>
					<input type="text" name="" id="" style="height:45px;width:266px;border:1px solid #EEEEEE;border-radius: 4px;display: inline-block">
					
					<img src="${EXTERNAL_URL }images/account/search.png" alt=""  style="position:relative;left:-40px">
				</div>
			
			</div>
			
			<p style="margin:100px 0 20px 48px;color:#979797;font-size: 14px">管理列表中的内容，将享受确权存证、侵权检测、维权索赔等原创保护服务</p>
			

			<table class="table" style="margin:20px 0 0 48px;width:894px;text-align: center;">
				<tr  class="top"style="height:50px;background:#F5F6FA;" >
					<td >证据链名称</td>
					<td>分类</td>
					<td>创建时间</td>
					<td>完整度</td>
					<td>作品包</td>
					<td>保管涵</td>
					<td>操作</td>
				</tr>
				
				
				<tr >
					<td >奔跑的小猪</td>
					<td>服装</td>
					<td>2016-08-31</td>
					<td>100%</td>
					<td><a href="" style="font-size:14px;color:#4D72FE">下载</a></td>
					<td><a href="" style="font-size:14px;color:#4D72FE">下载</a></td>
					<td><a href="" style="font-size:14px;color:#333">查看</a></td>
				</tr>
				
				<tr >
					<td >奔跑的小猪</td>
					<td>服装</td>
					<td>2016-08-31</td>
					<td>100%</td>
					<td><a href="" style="font-size:14px;color:#4D72FE">下载</a></td>
					<td><a href="" style="font-size:14px;color:#4D72FE">下载</a></td>
					<td><a href="" style="font-size:14px;color:#333">查看</a></td>
				</tr>
				
				
				<tr >
					<td >奔跑的小猪</td>
					<td>服装</td>
					<td>2016-08-31</td>
					<td>100%</td>
					<td><a href="" style="font-size:14px;color:#4D72FE">下载</a></td>
					<td><a href="" style="font-size:14px;color:#4D72FE">下载</a></td>
					<td><a href="" style="font-size:14px;color:#333">查看</a></td>
				</tr>
				
				
				<tr >
					<td >奔跑的小猪</td>
					<td>服装</td>
					<td>2016-08-31</td>
					<td>100%</td>
					<td><a href="" style="font-size:14px;color:#4D72FE">下载</a></td>
					<td><a href="" style="font-size:14px;color:#4D72FE">下载</a></td>
					<td><a href="" style="font-size:14px;color:#333">查看</a></td>
				</tr>
				
				<tr >
					<td >奔跑的小猪</td>
					<td>服装</td>
					<td>2016-08-31</td>
					<td>100%</td>
					<td><a href="" style="font-size:14px;color:#4D72FE">下载</a></td>
					<td><a href="" style="font-size:14px;color:#4D72FE">下载</a></td>
					<td><a href="" style="font-size:14px;color:#333">查看</a></td>
				</tr>
				
				<tr >
					<td >奔跑的小猪</td>
					<td>服装</td>
					<td>2016-08-31</td>
					<td>100%</td>
					<td><a href="" style="font-size:14px;color:#4D72FE">下载</a></td>
					<td><a href="" style="font-size:14px;color:#4D72FE">下载</a></td>
					<td><a href="" style="font-size:14px;color:#333">查看</a></td>
				</tr>
				
								<tr >
					<td >奔跑的小猪</td>
					<td>服装</td>
					<td>2016-08-31</td>
					<td>100%</td>
					<td><a href="" style="font-size:14px;color:#4D72FE">下载</a></td>
					<td><a href="" style="font-size:14px;color:#4D72FE">下载</a></td>
					<td><a href="" style="font-size:14px;color:#333">查看</a></td>
				</tr>
				<tr >
					<td >奔跑的小猪</td>
					<td>服装</td>
					<td>2016-08-31</td>
					<td>100%</td>
					<td><a href="" style="font-size:14px;color:#4D72FE">下载</a></td>
					<td><a href="" style="font-size:14px;color:#4D72FE">下载</a></td>
					<td><a href="" style="font-size:14px;color:#333">查看</a></td>
				</tr>
				<tr >
					<td >奔跑的小猪</td>
					<td>服装</td>
					<td>2016-08-31</td>
					<td>100%</td>
					<td><a href="" style="font-size:14px;color:#4D72FE">下载</a></td>
					<td><a href="" style="font-size:14px;color:#4D72FE">下载</a></td>
					<td><a href="" style="font-size:14px;color:#333">查看</a></td>
				</tr>
				<tr >
					<td >奔跑的小猪</td>
					<td>服装</td>
					<td>2016-08-31</td>
					<td>0%</td>
					<td><a href="" style="font-size:14px;color:#979797">下载</a></td>
					<td><a href="" style="font-size:14px;color:#979797">下载</a></td>
					<td><a href="" style="font-size:14px;color:#4D72FE">完善</a><span style="margin:0 5px;">|</span><a href="" style="font-size:14px;color:#FF5169">删除</a></td>
				</tr>
				
				
			</table>

			
			
			
			
			
		
		
		</div>
	
	
	
	
	
	
	
	
	
	</div>
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	<div class="foot" style="width:100%; height:240px;	background:#FCFCFC; margin-top:130px;">
  		<jsp:include page="../widget/footer-bottom.jsp" />	

	</div>
	
	
	
	
	
</body>
</html>