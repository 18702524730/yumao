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
<title>基本信息 企业   - 预猫</title>
<link href='${EXTERNAL_URL }favicon.png' rel='shortcut icon' type='image/png'>
<link href="${EXTERNAL_URL }assets/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href='${EXTERNAL_URL }css/main.css' rel='stylesheet' type='text/css'>
<link href='${EXTERNAL_URL }css/pages3/account-Basic.css' rel='stylesheet' type='text/css'>


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
	

	
	
	<div class="container" style="height:776px;border:1px solid #eee;margin-top:30px;padding:0px;">
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
				<div class="account" style="	border-left:6px solid #4D72FE;">
					<div style="float:left;margin-left:40px;">
						<img src="${EXTERNAL_URL }images/account/account-b.svg" alt="">
					</div>
					<a style="font-size:16px;color:#4D72FE;margin-left:15px;">账号管理</a >
				
				</div>
				
				<div class="save">
					<div style="float:left;margin-left:40px;">
						<img src="${EXTERNAL_URL }images/account/save.svg" alt="">
					</div>
					<a style="font-size:16px;color:#333333;margin-left:15px;">我的存证</a>
				
				</div>
				<div class="chain">
					<div style="float:left;margin-left:40px;">
						<img src="${EXTERNAL_URL }images/account/chain.svg" alt="">
					</div>
					<a style="font-size:16px;color:#333333;margin-left:15px;">我的证据链</a >
				
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



					  <!-- Nav tabs -->
					  <ul class="nav nav-tabs" role="tablist" style="margin:38px 0 0 48px">
						<li role="presentation" class="active ">
							<a href="#home" aria-controls="home" role="tab" data-toggle="tab"><b style="font-size:20px;">基本信息</b></a>
							<div style="height:6px;width:37px;border-radius: 4px;margin-left:36px; background:transparent;"></div>
						  </li>
						<li role="presentation text-center"style="margin-left:80px;">
							<a href="#profile" aria-controls="profile" role="tab" data-toggle="tab"><b style="font-size:20px">修改密码</b></a>
							<div style="height:6px;width:37px;border-radius: 4px;margin-left:38px; background:transparent;"></div>
						  </li>
					  </ul>

					  <!-- Tab panes -->
					  <div class="tab-content" style="margin-top:50px;margin-left:48px;">
						<div role="tabpanel" class="tab-pane active" id="home">
							
							
							<div class="people-information">
								<h1 style="font-size:16px;color:#333333;font-weight:600"> 个人信息 <hr style="position:relative;left:100px;width:70%;top:-29px	"></h1>
								
								
								
								
								
								<div class="row">
									 <div class="col-xs-6">
										  <div class="form-group">
												<label class="col-xs-3 control-label">用户名：</label>
												<div class="col-xs-9">
												  <p class="form-control-static">我是boss</p>
												</div>
										  </div>

									 </div>
									  <div class="col-xs-6">
										  <div class="form-group">
												<label class="col-xs-3 control-label">手机号码：</label>
												<div class="col-xs-7">
												  <p class="form-control-static" style="display: inline-block;margin-right:20px;">132******4542</p>
												  <a href="" style="color:#FF5169;">修改</a>
												</div>
										  </div>

									 </div>


								</div>


								<div class="row" style="margin-top:32px">
									 <div class="col-xs-6">
										  <div class="form-group">
												<label class="col-xs-3 control-label">企业名称：</label>
												<div class="col-xs-9">
												  <p class="form-control-static">杭州信拾贝网络科技有限公司</p>
												</div>
										  </div>

									 </div>
									  <div class="col-xs-6">
										  <div class="form-group">
												<label class="col-xs-3 control-label">统一社会信用代码：</label>
												<div class="col-xs-9">
												  <p class="form-control-static" style="display: inline-block;margin-right:20px;">3320******1234</p>
												</div>
										  </div>

									 </div>


								</div>		
								
								
							
								<div class="row" style="margin-top:32px">
									 <div class="col-xs-6">
										  <div class="form-group">
												<label class="col-xs-3 control-label">企业联系人：</label>
												<div class="col-xs-9" style="width:69%">
												  <p class="form-control-static">欧阳月</p>
												</div>
										  </div>

									 </div>



								</div>	
								
								
								
								
								
								
								
								
								
								
								
								
								<h1 style="font-size:16px;color:#333333;font-weight:600;margin-top:50px"> 店铺信息 <hr style="position:relative;left:100px;width:70%;top:-29px	"></h1>
								
								
								
								<div class="row">
									 <div class="col-xs-6">
										  <div class="form-group">
												<label class="col-xs-3 control-label">店铺名称：</label>
												<div class="col-xs-9">
												  <p class="form-control-static">我是boss</p>
												</div>
										  </div>

									 </div>
									  <div class="col-xs-6">
										  <div class="form-group">
												<label for="inputEmail3" class="col-xs-3 control-label">店铺链接：</label>
												<div class="col-xs-7">
													<div class="col-xs-10" style="padding:0px">
														<div class="tab-content">
															<div role="tabpanel" class="tab-pane active" id="for-display">
																<p class="form-control-static" style="display: inline-block;margin-right:20px">www.baidu.com</p>
																<a href="#for-edit" aria-controls="for-display" role="tab" data-toggle="tab"><label style="color:#FF5169">修改</label></a>
															</div>
															<div role="tabpanel" class="tab-pane" id="for-edit">
																<input type="email" class="form-control" id="inputEmail3" placeholder="请填写" style="width:75%;display: inline-block;margin-right:20px;">
																<a href="#for-display" aria-controls="for-edit" role="tab" data-toggle="tab"><label style="color:#FF5169">完成</label></a>
															</div>
														</div>
													</div>
												</div>
										  </div>

									 </div>


								</div>

								
								
								<div class="row" style="margin-top:32px">
									 <div class="col-xs-6">
										  <div class="form-group">
												<label class="col-xs-5 control-label">原创保护公证保管声明函:</label>
												<div class="col-xs-4" style="width:24%">
												  <p class="form-control-static"><button style="background:none;color:#4D72FE">生成声明函</button></p>
												</div>
										  </div>

									 </div>
								</div>
								
								
								
								
								
								
								
								
								

							
							</div>
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
						</div>
						<div role="tabpanel" class="tab-pane" id="profile">
								<form class="form-horizontal text-center" style="margin-top:40px ">
									
								<fieldset disabled>
								  <div class="form-group" style="margin-bottom:30px ">
									<label for="inputEmail3" class="col-xs-4 control-label">注册手机号：</label>
									<div class="col-xs-5  real-pad" style="display: inline-block;">
 											<input type="text" id="disabledTextInput" class="form-control" placeholder="132*******1241" >
									  </div>
										<button style="width:142px;height:40px;font-size:14px;color:#fff;background:#7FC4FD;text-align: center;line-height: 40px;position:relative;left:-155px">发送验证码</button>

								  </div>
								</fieldset >
								  <div class="form-group" style="margin-bottom:30px ">
									<label for="inputPassword3" class="col-xs-4 control-label">输入验证码：</label>
									<div class="col-xs-5 real-pad">
									  <input type="text" class="form-control" id="inputPassword3" placeholder="请输入验证码">
									</div>
								  </div>

								 <div class="form-group" style="margin-bottom:30px ">
									<label for="inputPassword3" class="col-xs-4 control-label">新密码：</label>
									<div class="col-xs-5 real-pad">
									  <input type="text" class="form-control" id="inputPassword3" placeholder="请输入新密码">
									</div>
								  </div>
								 <div class="form-group" style="margin-bottom:30px ">
									<label for="inputPassword3" class="col-xs-4 control-label">确认密码：</label>
									<div class="col-xs-5 real-pad">
									  <input type="text" class="form-control" id="inputPassword3" placeholder="请确认新密码">
									</div>
								  </div>






								  <div class="form-group " >
									<div class="col-sm-offset-2 col-xs-10 real-up" style="margin:120px 0 0 180px;border:none">
									  <button type="submit" class="mybluebtn">提交</button>
									</div>
								  </div>
								</form>			

						  
						  
						  
							
							
							
							
						  
						  
						  
						  
						  
						 </div>
					  </div>

				
			
			
			
			
			
			
			
			
			
			
			
		
		
		</div>
	
	
	
	
	
	
	
	
	
	</div>
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	<div class="foot" style="width:100%; height:240px;	background:#FCFCFC; margin-top:130px;">
  		<jsp:include page="../widget/footer-bottom.jsp" />	

	</div>
	
	
	
	
	
</body>
</html>