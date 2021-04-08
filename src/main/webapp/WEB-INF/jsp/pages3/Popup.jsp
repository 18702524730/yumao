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
<title>开通成功 - 预猫</title>
<link href='${EXTERNAL_URL }favicon.png' rel='shortcut icon' type='image/png'>
<link href="${EXTERNAL_URL }assets/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href='${EXTERNAL_URL }css/main.css' rel='stylesheet' type='text/css'>
<link href='${EXTERNAL_URL }css/pages3/popup.css' rel='stylesheet' type='text/css'>


<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src='${EXTERNAL_URL }assets/html5shiv/3.7.3/html5shiv.min.js' type='text/javascript'></script>
  <script src='${EXTERNAL_URL }assets/respond.js/1.4.2/respond.min.js' type='text/javascript'></script>
<![endif]-->
<script src='${EXTERNAL_URL }assets/jquery/1.12.4/jquery.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/bootstrap/3.3.7/js/bootstrap.min.js' type='text/javascript'></script>
</head>
<body>
<!--	1-->

	<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">修改手机号码</button>

    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content" style="border-radius:0px;height:394px;width:604px">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel"><b>修改手机号<b></h4>
                </div>
                <div class="modal-body">
				
								
					<form class="form-horizontal text-center" style="margin-top:40px ">
					  <div class="form-group" style="margin-bottom:30px ">
						<label for="inputEmail3" class="col-xs-4 control-label">原手机号码：</label>
						<div class="col-xs-6  real-pad" >
							 <p class="form-control-static" style="float:left; margin-right:30px;">133******1241</p>
							<button style="background:#F7F7F7;font-size:14px;color:#4D72FE;width:153px;height:35px;border-radius: 18px;text-align: center;line-height: 35px">点击发送验证码</button>
						</div>
					  </div>
					  <div class="form-group" style="margin-bottom:30px ">
						<label for="inputPassword3" class="col-xs-4 control-label">验证码：</label>
						<div class="col-xs-6 real-pad">
						  <input type="text" class="form-control" id="inputPassword3" placeholder="请输入手机验证码">
						</div>
					  </div>

					</form>			

				
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary"><b>下一步</b></button>
                </div>
            </div>
        </div>
    </div>
	
			
			
			
	
	<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#tell">修改手机号码</button>

    <div class="modal fade" id="tell" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content" style="border-radius:0px;height:394px;width:604px">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel"><b>修改手机号<b></h4>
                </div>
                <div class="modal-body">
				
								
					<form class="form-horizontal text-center" style="margin-top:40px ">
					  <div class="form-group" style="margin-bottom:30px ">
						<label for="inputEmail3" class="col-xs-4 control-label">新手机号码：</label>
						<div class="col-xs-6  real-pad" >
							 <input type="text" class="form-control" id="inputPassword3" placeholder="请输入新手机号码">

						</div>
					  </div>
					  <div class="form-group" style="margin-bottom:30px ">
						<label for="inputPassword3" class="col-xs-4 control-label">验证码：</label>
						<div class="col-xs-6 real-pad">
						  <input type="text" class="form-control" id="inputPassword3" placeholder="请输入手机验证码" style="float:left;width:170px">
							<button style="position:relative;width:130px;height:40px;color:#fff;background:#7FC4FD">发送验证码</button>
						</div>
					  </div>

					</form>			

				
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary mybluebtn"><b>提交</b></button>
                </div>
            </div>
        </div>
    </div>
	
	
<!--	2-->
	
				<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#add">添加原创作品</button>

				<div class="modal fade" id="add" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog" role="document">
						<div class="modal-content" style="border-radius:0px;height:629px;width:640px">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="myModalLabel"><b>添加原创作品<b></h4>
							</div>
							<div class="modal-body">

										
									<form class="form-horizontal text-center" style="margin-top:40px ">
									  <div class="form-group" style="margin-bottom:30px ">
										<label for="inputEmail3" class="col-xs-4 control-label">作品名称：</label>
										<div class="col-xs-6  real-pad">
										  <input type="email" class="form-control" id="inputEmail3" placeholder="请输入作品名称">
										</div>
									  </div>

									  <div class="form-group" style="margin-bottom:30px ">
										<label for="inputEmail3" class="col-xs-4 control-label">作品种类：</label>
										<div class="col-xs-6  real-pad">
										  <select class="form-control" style="height:40px; border-radius: 0px">
											  <option>1</option>
											  <option>2</option>
											  <option>3</option>
											  <option>4</option>
											  <option>5</option>
										  </select>
										</div>
									  </div>



									  <div class="form-group" style="margin-bottom:30px ">
										<label for="inputPassword3" class="col-xs-4 control-label">创作完成日期：</label>
										<div class="col-xs-6 real-pad">
										  <input type="datetime" class="form-control" id="inputPassword3" placeholder="请输入创作日期">
										</div>
									  </div>

					
									 <div class="form-group" style="margin-bottom:30px ">
										<label for="inputPassword3" class="col-xs-4 control-label">作品文件：</label>
										<div class="col-xs-6 real-pad">
						 					 <input type="text" class="form-control" id="inputPassword3" placeholder="请输入手机验证码" style="float:left;width:203px">
											<button style="position:relative;width:130px;height:40px;color:#fff;background:#7FC4FD">选择文件</button>

											<p style="font-size:12px; color:#979797;text-align: left;margin-top:10px;">上传文件大小不得超过10MB；支持格式</p>
											<p style="font-size:12px; color:#979797;text-align: left;margin-top:10px;">png/jpg/jpeg/gif/text/pdf/doc/xls/ppt/mp4/avi/mp3/wma</p>

										</div>
									  </div>


									</form>		
								
								


							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-primary mybluebtn"><b>提交</b></button>
								<p style="margin-top:50px;color:#979797;font-size:12px;text-align: center;">上传的版权归用户自己所有，未经用户授权，平台绝对不会私自使用用户上传的作品</p>
							</div>
						</div>
					</div>
				</div>
			
			
			
			
			
<!--			3-->
			
			
			
					<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#chain">添加证据链</button>

					<div class="modal fade" id="chain" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
						<div class="modal-dialog" role="document">
							<div class="modal-content" style="border-radius:0px;height:370px;width:604px">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="myModalLabel"><b>添加证据链<b></h4>
								</div>
								<div class="modal-body">


									<form class="form-horizontal text-center" style="margin-top:40px ">
									  <div class="form-group" style="margin-bottom:30px ">
										<label for="inputEmail3" class="col-xs-4 control-label">证据链名称：</label>
										<div class="col-xs-6  real-pad" >
	  											<input type="text" class="form-control" id="inputPassword3" placeholder="请输入验证码名称">					
										  </div>
									  </div>
									  <div class="form-group" style="margin-bottom:30px ">
										<label for="inputPassword3" class="col-xs-4 control-label">证据链分类：</label>
										<div class="col-xs-6 real-pad">
									
										 <p class="form-control-static" style="float:left; margin-right:30px;">服装</p>

										</div>
									  </div>

									</form>			


								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-primary"><b>确认添加</b></button>
								</div>
							</div>
						</div>
					</div>

			
			
			<!--			4-->
			
			
			
					<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#warn">警告</button>

					<div class="modal fade" id="warn" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
						<div class="modal-dialog" role="document">
							<div class="modal-content" style="border-radius:0px;height:360px;width:604px">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="myModalLabel"><b>警告<b></h4>
								</div>
								<div class="modal-body">
									<h1 style="text-align: center;margin-top:45px"><b style="font-size:18px;color:#FF1313;">请按节点顺序添加作品</b></h1>

									<h2 style="margin-top:30px;text-align: center;"><b style="font-size:16px;color:#333333">***节点还未添加作品，若选择继续上传，该节点将无法再添加作品</b></h2>

								</div>
								<div class="modal-footer" style="margin-top:40px">
									<button type="button" class="btn btn-primary"><b>确认</b></button>
								</div>
							</div>
						</div>
					</div>


			
					<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#warning">警告</button>

					<div class="modal fade" id="warning" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
						<div class="modal-dialog" role="document">
							<div class="modal-content" style="border-radius:0px;height:360px;width:604px">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="myModalLabel"><b>警告<b></h4>
								</div>
								<div class="modal-body">
									<h1 style="text-align: center;margin-top:45px"><b style="font-size:18px;color:#333333;">取消关联后，作品将移至我的存证。</b></h1>

									<h2 style="margin-top:30px;text-align: center;"><b style="font-size:16px;color:#333333">是否确认取消？</b></h2>

								</div>
								<div class="modal-footer" style="margin-top:40px">
									<button type="button" class="btn btn-primary"><b>确认</b></button>
								</div>
							</div>
						</div>
					</div>

			
			
			
			
			
			
						<!--			5-->
			
			
			
					<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#propect">添加维权</button>

					<div class="modal fade" id="propect" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
						<div class="modal-dialog" role="document">
							<div class="modal-content" style="border-radius:0px;width:640px">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="myModalLabel"><b>添加维权<b></h4>
								</div>
								<div class="modal-body">
									<div  style="margin-top:40px;margin-left:69px;">
										
										<input type="text" class="form-control" id="inputPassword3" placeholder="请选择被侵权相关作品" style="width:384px;height:50px;float:left;border-radius: 0px">					

										<button style="width:118px;height:50px;line-height: 50px;align-content: center;background:#7FC4FD;font-size:16px;color:#fff">搜索</button>
									</div>

				

									
									
									<table class="table popup-table" style="width:502px;margin:20px 0 0 69px;text-align: center;">
										<tr  class="top"style="background:#F5F6FA;" >
											<td >
												<div class="checkbox">
												  <label>
													<input type="checkbox" id="blankCheckbox" value="option1" aria-label="...">
												  </label>
												</div>
											
											
											</td>
											<td>作品名称</td>
											<td>作品种类</td>
											<td>创作时间</td>
											<td>上传时间</td>
										</tr>

										<tr>
											<td>
												<div class="checkbox">
												  <label>
													<input type="checkbox" id="blankCheckbox" value="option1" aria-label="...">
												  </label>
												</div>
											</td>
											<td>飞翔的荷兰猪</td>
											<td>美术作品</td>
											<td>2016-08-31</td>
											<td>2018-03-31</td>

										</tr>
										<tr>
											<td>
												<div class="checkbox">
												  <label>
													<input type="checkbox" id="blankCheckbox" value="option1" aria-label="...">
												  </label>
												</div>
											</td>
											<td>飞翔的荷兰猪</td>
											<td>美术作品</td>
											<td>2016-08-31</td>
											<td>2018-03-31</td>

										</tr>
										<tr>
											<td>
												<div class="checkbox">
												  <label>
													<input type="checkbox" id="blankCheckbox" value="option1" aria-label="...">
												  </label>
												</div>
											</td>
											<td>飞翔的荷兰猪</td>
											<td>美术作品</td>
											<td>2016-08-31</td>
											<td>2018-03-31</td>

										</tr>
										<tr>
											<td>
												<div class="checkbox">
												  <label>
													<input type="checkbox" id="blankCheckbox" value="option1" aria-label="...">
												  </label>
												</div>
											</td>
											<td>飞翔的荷兰猪</td>
											<td>美术作品</td>
											<td>2016-08-31</td>
											<td>2018-03-31</td>

										</tr>
										<tr>
											<td>
												<div class="checkbox">
												  <label>
													<input type="checkbox" id="blankCheckbox" value="option1" aria-label="...">
												  </label>
												</div>
											</td>
											<td>飞翔的荷兰猪</td>
											<td>美术作品</td>
											<td>2016-08-31</td>
											<td>2018-03-31</td>

										</tr>

									</table>
									
									
									
									
									
									
									
									
									
									
								</div>
								<div class="modal-footer" style="margin-top:40px;margin-bottom:70px">
									<button type="button" class="btn btn-primary"><b>下一步</b></button>
								</div>
							</div>
						</div>
					</div>

		
					<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#propect-two">添加维权</button>

					<div class="modal fade" id="propect-two" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
						<div class="modal-dialog" role="document">
							<div class="modal-content" style="border-radius:0px;width:640px">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="myModalLabel"><b>添加维权<b></h4>
								</div>
								<div class="modal-body">
									<div  style="margin-top:40px;margin-left:69px;">
										<h1 style="margin-bottom:20px;font-size:16px;color:#333333;">侵权链接</h1>
										
										<input type="text" class="form-control" id="inputPassword3" placeholder="请输入侵权商品链接" style="width:502px;height:50px;border-radius: 0px">					

								
									</div>

									<h1 style="margin-top:30px;font-size:16px;color:#333333;margin-left:69px">相关作品</h1>

					
									<table class="table popup-table" style="width:502px;margin:20px 0 0 69px;text-align: center;">
										<tr  class="top"style="background:#F5F6FA;" >
											<td>作品名称</td>
											<td>作品种类</td>
											<td>创作时间</td>
											<td>上传时间</td>
										</tr>

										<tr>
											<td>飞翔的荷兰猪</td>
											<td>美术作品</td>
											<td>2016-08-31</td>
											<td>2018-03-31</td>

										</tr>
		

									</table>
									
									
									
									
									
									
									
									
									
									
									
									
								</div>
								<div class="modal-footer" style="margin-top:40px;text-align: center;margin-bottom:70px;bor">
									<button type="button" class="btn btn-primary mybluebtn" ><b>提交</b></button>
									<button style="display: block;background:none;color:#A3A6B4;font-size:14px;margin-top:20px;margin-left:268px">返回上一步</button>
								</div>
							</div>
						</div>
					</div>
			


			
			
			
			
			
	
	
	
</body>
</html>