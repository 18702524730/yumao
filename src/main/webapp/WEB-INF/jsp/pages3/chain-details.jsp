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
<link href='${EXTERNAL_URL }css/pages3/chain-details.css' rel='stylesheet' type='text/css'>


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
	

	
	
	<div class="container" style="height:2462px;border:1px solid #eee;margin-top:30px;padding:0px;">
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
			
			<ol class="breadcrumb" style="margin:38px 0 40px 48px;background:none;padding:0px">
				  <li><a href="#" >我的证据链</a></li>
				  <li class="active">奔跑的小猪</li>
			</ol>
			
			<div style="margin-left:48px;height:101px;line-height: 68px">
				<h1 style="float:left; margin-top:8px"><b style="font-size:18px;color:#040404;">奔跑的小猪</b></h1>
				<p style="float:left;margin-left:8px;font-size:16px;color:#040404">(完善度20%)</p>
			</div>
			
			<ul style="margin-left:48px;">
				<li style="height:29px">
					<div style="float:left;margin-left:3px">
						<img src="${EXTERNAL_URL }images/chain-details/premise.png" alt="">
					</div>	
					<b style="float:left;margin-left:10px;font-size:16px;color:#040404;">设计手稿</b>
					<button style="width:29px;height:29px;float:left;margin-left:10px;background:none;margin-top:-3px"><img src="${EXTERNAL_URL }images/chain-details/up.png" alt=""></button>
				
				</li>
				<li  style="margin-top:24px;">
					<button class="left" >
						<img src="${EXTERNAL_URL }images/chain-details/left.png" alt="">
					</button>
					<div class="browse">
						<div>
							<div>
								<p>奔跑的小猪设计手稿01.jpg</p>
								<span>2016-02-31</span>
								<div>
									<a href="">下载</a>
									<a href="">保管涵</a>
									<a href="">取消关联</a>

								</div>
							</div>
						
							<div>
								<p>奔跑的小猪设计手稿01.jpg</p>
								<span>2016-02-31</span>
								<div>
									<a href="">下载</a>
									<a href="">保管涵</a>
									<a href="">取消关联</a>

								</div>
							</div>
						</div>
					
					
					</div>
					
						<button class="right" >
						<img src="${EXTERNAL_URL }images/chain-details/right.png" alt="">
					</button>
					
				
				</li>
			</ul>
			
			
			
			
			<ul style="margin-left:48px; margin-top:260px">
				<li style="height:29px">
					<div style="float:left;margin-left:3px">
						<img src="${EXTERNAL_URL }images/chain-details/premise.png" alt="">
					</div>	
					<b style="float:left;margin-left:10px;font-size:16px;color:#040404;">电子制版图</b>
					<button style="width:29px;height:29px;float:left;margin-left:10px;background:none;margin-top:-3px"><img src="${EXTERNAL_URL }images/chain-details/up.png" alt=""></button>
				
				</li>
				<li  style="margin-top:24px;">
					<button class="left" >
						<img src="${EXTERNAL_URL }images/chain-details/left.png" alt="">
					</button>
					<div class="browse">
						<div>
							<div>
								<p>奔跑的小猪设计手稿01.jpg</p>
								<span>2016-02-31</span>
								<div>
									<a href="">下载</a>
									<a href="">保管涵</a>
									<a href="">取消关联</a>

								</div>
							</div>
						
							<div>
								<p>奔跑的小猪设计手稿01.jpg</p>
								<span>2016-02-31</span>
								<div>
									<a href="">下载</a>
									<a href="">保管涵</a>
									<a href="">取消关联</a>

								</div>
							</div>
						</div>
					
					
					</div>
					
						<button class="right" >
						<img src="${EXTERNAL_URL }images/chain-details/right.png" alt="">
					</button>
					
				
				</li>
			</ul>
			
			<ul style="margin-left:48px; margin-top:260px">
				<li style="height:29px">
					<div style="float:left;margin-left:3px">
						<img src="${EXTERNAL_URL }images/chain-details/premise.png" alt="">
					</div>	
					<b style="float:left;margin-left:10px;font-size:16px;color:#040404;">预计效果图</b>
					<button style="width:29px;height:29px;float:left;margin-left:10px;background:none;margin-top:-3px"><img src="${EXTERNAL_URL }images/chain-details/up.png" alt=""></button>
				
				</li>
				<li  style="margin-top:24px;">
					<button class="left" >
						<img src="${EXTERNAL_URL }images/chain-details/left.png" alt="">
					</button>
					<div class="browse">
						<div>
							<div>
								<p>奔跑的小猪设计手稿01.jpg</p>
								<span>2016-02-31</span>
								<div>
									<a href="">下载</a>
									<a href="">保管涵</a>
									<a href="">取消关联</a>

								</div>
							</div>
						
							<div>
								<p>奔跑的小猪设计手稿01.jpg</p>
								<span>2016-02-31</span>
								<div>
									<a href="">下载</a>
									<a href="">保管涵</a>
									<a href="">取消关联</a>

								</div>
							</div>
						</div>
					
					
					</div>
					
						<button class="right" >
						<img src="${EXTERNAL_URL }images/chain-details/right.png" alt="">
					</button>
					
				
				</li>
			</ul>
			
			<ul style="margin-left:48px; margin-top:260px">
				<li style="height:29px">
					<div style="float:left;margin-left:3px">
						<img src="${EXTERNAL_URL }images/chain-details/premise.png" alt="">
					</div>	
					<b style="float:left;margin-left:10px;font-size:16px;color:#040404;">产品平铺图（正反面）</b>
					<button style="width:29px;height:29px;float:left;margin-left:10px;background:none;margin-top:-3px"><img src="${EXTERNAL_URL }images/chain-details/up.png" alt=""></button>
				
				</li>
				<li  style="margin-top:24px;">
					<button class="left" >
						<img src="${EXTERNAL_URL }images/chain-details/left.png" alt="">
					</button>
					<div class="browse">
						<div>
							<div>
								<p>奔跑的小猪设计手稿01.jpg</p>
								<span>2016-02-31</span>
								<div>
									<a href="">下载</a>
									<a href="">保管涵</a>
									<a href="">取消关联</a>

								</div>
							</div>
						
							<div>
								<p>奔跑的小猪设计手稿01.jpg</p>
								<span>2016-02-31</span>
								<div>
									<a href="">下载</a>
									<a href="">保管涵</a>
									<a href="">取消关联</a>

								</div>
							</div>
						</div>
					
					
					</div>
					
						<button class="right" >
						<img src="${EXTERNAL_URL }images/chain-details/right.png" alt="">
					</button>
					
				
				</li>
			</ul>
			
			
			<ul style="margin-left:48px; margin-top:260px">
				<li style="height:29px">
					<div style="float:left;margin-left:3px">
						<img src="${EXTERNAL_URL }images/chain-details/premise.png" alt="">
					</div>	
					<b style="float:left;margin-left:10px;font-size:16px;color:#040404;">产品模特图</b>
					<button style="width:29px;height:29px;float:left;margin-left:10px;background:none;margin-top:-3px"><img src="${EXTERNAL_URL }images/chain-details/up.png" alt=""></button>
				
				</li>
				<li  style="margin-top:24px;">
					<button class="left" >
						<img src="${EXTERNAL_URL }images/chain-details/left.png" alt="">
					</button>
					<div class="browse">
						<div>
							<div>
								<p>奔跑的小猪设计手稿01.jpg</p>
								<span>2016-02-31</span>
								<div>
									<a href="">下载</a>
									<a href="">保管涵</a>
									<a href="">取消关联</a>

								</div>
							</div>
						
							<div>
								<p>奔跑的小猪设计手稿01.jpg</p>
								<span>2016-02-31</span>
								<div>
									<a href="">下载</a>
									<a href="">保管涵</a>
									<a href="">取消关联</a>

								</div>
							</div>
						</div>
					
					
					</div>
					
						<button class="right" >
						<img src="${EXTERNAL_URL }images/chain-details/right.png" alt="">
					</button>
					
				
				</li>
			</ul>
			
			
			
			<ul style="margin-left:48px; margin-top:260px">
				<li style="height:29px">
					<div style="float:left;margin-left:3px">
						<img src="${EXTERNAL_URL }images/chain-details/premise.png" alt="">
					</div>	
					<b style="float:left;margin-left:10px;font-size:16px;color:#040404;">产品正视图</b>
					<p style="margin-left:5px;float:left;font-size:16px; color:#979797;">（仅可上传一张图片格式文件，可用于生成产品保管函）</p>
					<button style="width:29px;height:29px;float:left;margin-left:10px;background:none;margin-top:-3px"><img src="${EXTERNAL_URL }images/chain-details/up.png" alt=""></button>
					<a href="" style="float:left;font-size:14px;color:#4D72FE;margin-left:20px;">点击生成保管函</a>

				
				</li>
				<li  style="margin-top:24px;">
					<button class="left" >
						<img src="${EXTERNAL_URL }images/chain-details/left.png" alt="">
					</button>
					<div class="browse">
						<div>
							<div>
								<p>奔跑的小猪设计手稿01.jpg</p>
								<span>2016-02-31</span>
								<div>
									<a href="">下载</a>
									<a href="">保管涵</a>
									<a href="">取消关联</a>

								</div>
							</div>
						
							<div>
								<p>奔跑的小猪设计手稿01.jpg</p>
								<span>2016-02-31</span>
								<div>
									<a href="">下载</a>
									<a href="">保管涵</a>
									<a href="">取消关联</a>

								</div>
							</div>
						</div>
					
					
					</div>
					
						<button class="right" >
						<img src="${EXTERNAL_URL }images/chain-details/right.png" alt="">
					</button>
					
				
				</li>
			</ul>
			
			
			
			<ul style="margin-left:48px; margin-top:260px">
				<li style="height:29px">
					<div style="float:left;margin-left:3px">
						<img src="${EXTERNAL_URL }images/chain-details/premise.png" alt="">
					</div>	
					<b style="float:left;margin-left:10px;font-size:16px;color:#040404;">产品详情页</b>
					<button style="width:29px;height:29px;float:left;margin-left:10px;background:none;margin-top:-3px"><img src="${EXTERNAL_URL }images/chain-details/up.png" alt=""></button>
				
				</li>
				<li  style="margin-top:24px;">
					<button class="left" >
						<img src="${EXTERNAL_URL }images/chain-details/left.png" alt="">
					</button>
					<div class="browse">
						<div>
							<div>
								<p>奔跑的小猪设计手稿01.jpg</p>
								<span>2016-02-31</span>
								<div>
									<a href="">下载</a>
									<a href="">保管涵</a>
									<a href="">取消关联</a>

								</div>
							</div>
						
							<div>
								<p>奔跑的小猪设计手稿01.jpg</p>
								<span>2016-02-31</span>
								<div>
									<a href="">下载</a>
									<a href="">保管涵</a>
									<a href="">取消关联</a>

								</div>
							</div>
						</div>
					
					
					</div>
					
						<button class="right" >
						<img src="${EXTERNAL_URL }images/chain-details/right.png" alt="">
					</button>
					
				
				</li>
			</ul>			
			
						
			<ul style="margin-left:48px; margin-top:260px">
				<li style="height:29px">
					<div style="float:left;margin-left:3px">
						<img src="${EXTERNAL_URL }images/chain-details/premise.png" alt="">
					</div>	
					<b style="float:left;margin-left:10px;font-size:16px;color:#040404;">产品发表证明	</b>
					<button style="width:29px;height:29px;float:left;margin-left:10px;background:none;margin-top:-3px"><img src="${EXTERNAL_URL }images/chain-details/up.png" alt=""></button>
				
				</li>
				<li  style="margin-top:24px;">
					<button class="left" >
						<img src="${EXTERNAL_URL }images/chain-details/left.png" alt="">
					</button>
					<div class="browse">
						<div>
							<div>
								<p>奔跑的小猪设计手稿01.jpg</p>
								<span>2016-02-31</span>
								<div>
									<a href="">下载</a>
									<a href="">保管涵</a>
									<a href="">取消关联</a>

								</div>
							</div>
						
							<div>
								<p>奔跑的小猪设计手稿01.jpg</p>
								<span>2016-02-31</span>
								<div>
									<a href="">下载</a>
									<a href="">保管涵</a>
									<a href="">取消关联</a>

								</div>
							</div>
						</div>
					
					
					</div>
					
						<button class="right" >
						<img src="${EXTERNAL_URL }images/chain-details/right.png" alt="">
					</button>
					
				
				</li>
			</ul>	
			
			
			
			
			
			
			
			
			
		
		
		</div>
	
	
	
	
	
	
	
	
	
	</div>
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	<div class="foot" style="width:100%; height:240px;	background:#FCFCFC; margin-top:130px;">
  		<jsp:include page="../widget/footer-bottom.jsp" />	

	</div>
	
	
	
	
	
</body>
</html>