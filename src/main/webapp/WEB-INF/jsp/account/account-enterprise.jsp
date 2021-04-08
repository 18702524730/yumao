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
<title>基本信息 企业 - 预猫</title>
<link href='${EXTERNAL_URL }favicon.png' rel='shortcut icon' type='image/png'>
<link href="${EXTERNAL_URL }assets/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href='${EXTERNAL_URL }css/main.css' rel='stylesheet' type='text/css'>
<link href='${EXTERNAL_URL }css/pages3/account-Basic.css' rel='stylesheet' type='text/css'>
<link href='${EXTERNAL_URL }css/pages3/popup.css' rel='stylesheet' type='text/css'>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src='${EXTERNAL_URL }assets/html5shiv/3.7.3/html5shiv.min.js' type='text/javascript'></script>
  <script src='${EXTERNAL_URL }assets/respond.js/1.4.2/respond.min.js' type='text/javascript'></script>
<![endif]-->
<script src='${EXTERNAL_URL }assets/jquery/1.12.4/jquery.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/bootstrap/3.3.7/js/bootstrap.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/common.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/account/update-password.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/account/update-mobile.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/account/statement.js' type='text/javascript'></script>
<style type="text/css">
ul.nav-tabs>li>div {
  background: transparent !important;
}

ul.nav-tabs>li.active>div {
  background: #4D72FE !important;
}

ul.nav-tabs>li>a>b {
  color: #979797;
}

ul.nav-tabs>li.active>a>b {
  color: #000;
}
</style>
</head>
<body>
  <input id="external-url" type="hidden" value="${EXTERNAL_URL }">
  <input id="return-mapping" type="hidden" value="${return_mapping }">
  <input id="user-type" type="hidden" value="${user.userType }">
  <input id="user-name" type="hidden" value="${user.username }">
  <jsp:include page="dialog/update-password-dialog.jsp"></jsp:include>
  <jsp:include page="dialog/confirm-mobile-dialog.jsp"></jsp:include>
  <jsp:include page="dialog/update-mobile-dialog.jsp"></jsp:include>
  <jsp:include page="../market/dialog/create-statement.jsp"></jsp:include>
  <!--    导航-->
  <div class="container-fluid" style="box-shadow: 0 1px 18px 0 rgba(175, 175, 175, 0.1); height: 88px;">
    <div class="container">
      <div class="logo" >
        <a href="${EXTERNAL_URL }index.html"><img src="${EXTERNAL_URL }images/logo.png" alt=""></a>
      </div>
      <ul class="top-nav">
        <li><a href="${EXTERNAL_URL }index.html">预猫首页</a></li>
        <li><a href="${EXTERNAL_URL }user/account.html" style="color: #4d72fe">我的预猫</a></li>
        <li style="margin-right: 0px;"><a href="${EXTERNAL_URL }logout.html">退出登录</a></li>
      </ul>
    </div>
  </div>
  <div class="container" style="height: 776px; border: 1px solid #eee; margin-top: 30px; padding: 0px;">
    <div class="left-nav">
      <div class="l-n-user text-center">
        <div style="width: 70px; height: 70px; background: #fff; border-radius: 50%; margin: 16px 0 10px 60px; overflow: hidden">
          <div style="width: 66px; height: 66px; margin: 2px 0 0 2px">
            <img src="${EXTERNAL_URL }images/account/head.png" alt="">
          </div>
        </div>
        <b style="font-size: 16px; color: #fff;display: block;padding: 0 5px; overflow: hidden;text-overflow: ellipsis;white-space: nowrap;margin-bottom: 5px;"><shiro:principal property="username" /></b>
        <c:if test="${hasRealName }">
          <p style="font-size: 12px; color: #fff; margin-top: 0px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;padding: 0 5px;margin-bottom: 5px">${entName }</p>
        </c:if>
        <c:choose>
          <c:when test="${hasServiceOpened }">
            <p style="font-size: 12px; color: #fff; margin-top: 0px;margin-bottom: 5px">服务到期：${endTime }</p>
            <div style="width: 91px; height: 25px; border-radius: 13px; background: #fff; text-align: center;  margin-left: 50px">
              <a href="${EXTERNAL_URL }alipay/submit.html?orderType=2" style="display: inline-block;width:100%;height: 100%;font-size: 12px; color: #444444; line-height: 24px;cursor: pointer;">立即续费</a>
          </c:when>
          <c:otherwise>
            <div style="width: 91px; height: 25px; border-radius: 13px; background: #fff; text-align: center; margin-top: 8px; margin-left: 50px">
              <a href="${EXTERNAL_URL }service/open.html" style="display: inline-block;width:100%;height: 100%;font-size: 12px; color: #444444; line-height: 24px;">立即开通服务</a>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
    <div class="l-n-service">
      <div class="account" style="border-left: 6px solid #4D72FE;">
        <div style="float: left; margin-left: 40px;">
          <img src="${EXTERNAL_URL }images/account/account-b.svg" alt="">
        </div>
        <a style="font-size: 16px; color: #4D72FE; margin-left: 15px;" href="${EXTERNAL_URL }user/account.html">账号管理</a>
      </div>
      <div class="save">
        <div style="float: left; margin-left: 40px;">
          <img src="${EXTERNAL_URL }images/account/save.svg" alt="">
        </div>
        <a style="font-size: 16px; color: #333333; margin-left: 15px;" href="${EXTERNAL_URL }original-works/my-save.html">我的原创</a>
      </div>
<%--      <div class="chain">--%>
<%--        <div style="float: left; margin-left: 40px;">--%>
<%--          <img src="${EXTERNAL_URL }images/account/chain.svg" alt="">--%>
<%--        </div>--%>
<%--        <a style="font-size: 16px; color: #999999; margin-left: 15px;" href="#&lt;%&ndash;${EXTERNAL_URL }evid-chain/my-chain.html&ndash;%&gt;">我的证据链(开发中)</a>--%>
<%--      </div>--%>
      <div class="protect">
        <div style="float: left; margin-left: 40px;">
          <img src="${EXTERNAL_URL }images/account/protect.svg" alt="">
        </div>
        <a style="font-size: 16px; color: #333333; margin-left: 15px;" href="${EXTERNAL_URL }tort/my-tort.html">我的维权</a>
      </div>
    </div>
  </div>
  <div class="right-content">
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist" style="margin: 38px 0 0 48px">
      <li role="presentation" class="active "><a href="#home" aria-controls="home" role="tab" data-toggle="tab"><b style="font-size: 20px;">基本信息</b></a>
        <div style="height: 6px; width: 37px; border-radius: 4px; margin-left: 36px; background: transparent;"></div></li>
      <li role="presentation text-center" style="margin-left: 80px;"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab"><b style="font-size: 20px">修改密码</b></a>
        <div style="height: 6px; width: 37px; border-radius: 4px; margin-left: 38px; background: transparent;"></div></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content" style="margin-top: 50px; margin-left: 48px;">
      <div role="tabpanel" class="tab-pane active" id="home">
        <div class="people-information">
          <h1 style="font-size: 16px; color: #333333; font-weight: 600">
            个人信息
            <hr style="position: relative; left: 100px; width: 70%; top: -29px">
          </h1>
          <div class="row">
            <div class="col-xs-6">
              <div class="form-group">
                <label class="col-xs-3 control-label">用户名：</label>
                <div class="col-xs-9">
                  <p class="form-control-static">${user.username }</p>
                </div>
              </div>
            </div>
            <div class="col-xs-6">
              <div class="form-group">
                <label class="col-xs-3 control-label">手机号码：</label>
                <div class="col-xs-7">
                  <p class="form-control-static" style="display: inline-block; margin-right: 20px;">${user.entContactsMobileForDisplay }</p>
                  <a href="javascript:void(0)" style="color: #FF5169;" class="to-update-mobile">修改</a>
                </div>
              </div>
            </div>
          </div>
          <div class="row" style="margin-top: 32px">
            <div class="col-xs-6">
              <div class="form-group">
                <label class="col-xs-3 control-label">企业名称：</label>
                <div class="col-xs-9">
                  <p class="form-control-static">${entName }</p>
                </div>
              </div>
            </div>
            <div class="col-xs-6">
              <div class="form-group">
                <label class="col-xs-3 control-label">统一社会信用代码：</label>
                <div class="col-xs-4">
                  <p class="form-control-static" style="display: inline-block; margin-right: 20px;">${entUniCreditCode }</p>
                </div>
              </div>
            </div>
          </div>
          <div class="row" style="margin-top: 32px">
            <div class="col-xs-6">
              <div class="form-group">
                <label class="col-xs-3 control-label">企业联系人：</label>
                <div class="col-xs-9" style="width: 69%">
                  <p class="form-control-static">${entContactsRealname }</p>
                </div>
              </div>
            </div>
          </div>
          <%--<h1 style="font-size: 16px; color: #333333; font-weight: 600; margin-top: 50px">--%>
            <%--店铺信息--%>
            <%--<hr style="position: relative; left: 100px; width: 70%; top: -29px">--%>
          <%--</h1>--%>
          <%--<div class="row">--%>
            <%--<div class="col-xs-6">--%>
              <%--<div class="form-group">--%>
                <%--<label class="col-xs-3 control-label">店铺名称：</label>--%>
                <%--<div class="col-xs-9">--%>
                  <%--<p class="form-control-static">${statement.stopName }</p>--%>
                <%--</div>--%>
              <%--</div>--%>
            <%--</div>--%>
            <%--<div class="col-xs-6">--%>
              <%--<div class="form-group">--%>
                <%--<label for="inputEmail3" class="col-xs-3 control-label">店铺链接：</label>--%>
                <%--<div class="col-xs-7">--%>
                  <%--<div class="col-xs-10" style="padding: 0px">--%>
                    <%--<div class="tab-content">--%>
                      <%--<div role="tabpanel" class="tab-pane active" id="for-display">--%>
                        <%--<div class="col-sm-8" style="padding-left:0">--%>
<%--&lt;%&ndash;                          <p class="form-control-static" id="shop-url-display">${statement.shopUrl }</p>&ndash;%&gt;--%>
                              <%--<p class="form-control-static" style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" id="shop-url-display">${statement.shopUrl }</p>--%>
                        <%--</div>--%>
                        <%--<div class="col-sm-4" style="padding-left: 0px; padding-top: 7px;">--%>
                          <%--<c:if test="${hasStatement }">--%>
                            <%--<a href="#for-edit" aria-controls="for-display" role="tab" data-toggle="tab"><label style="color: #FF5169">修改</label></a>--%>
                          <%--</c:if>--%>
                        <%--</div>--%>
                      <%--</div>--%>
                      <%--<div role="tabpanel" class="tab-pane" id="for-edit">--%>
                        <%--<input type="text" class="form-control" id="shop-url-input" placeholder="请填写" style="width: 75%; display: inline-block; margin-right: 20px;"--%>
                          <%--value="${statement.shopUrl }"> <a href="javascript:void(0)" id="update-shop-url" aria-controls="for-edit" role="tab" data-toggle="tab"><label--%>
                          <%--style="color: #FF5169">完成</label></a>--%>
                      <%--</div>--%>
                    <%--</div>--%>
                  <%--</div>--%>
                <%--</div>--%>
              <%--</div>--%>
            <%--</div>--%>
          <%--</div>--%>
          <%--<div class="row" style="margin-top: 32px">--%>
            <%--<div class="col-xs-6">--%>
              <%--<div class="form-group">--%>
                <%--<label class="col-xs-5 control-label">原创保护公证保管声明函:</label>--%>
                <%--<div class="col-xs-4" style="width: 24%">--%>
                  <%--<p>--%>
                    <%--<c:choose>--%>
                      <%--<c:when test="${hasServiceOpened }">--%>
                        <%--<c:choose>--%>
                          <%--<c:when test="${hasStatement }">--%>
                            <%--<a id="download-statement" class="btn" href="${EXTERNAL_URL }statement/download.html" type="button" style="color: #4D72FE">下载声明函</a>--%>
                          <%--</c:when>--%>
                          <%--<c:otherwise>--%>
                            <%--<a id="create-statement" class="btn" href="javascript:void(0)" type="button" style="color: #4D72FE">生成声明函</a>--%>
                          <%--</c:otherwise>--%>
                        <%--</c:choose>--%>
                      <%--</c:when>--%>
                      <%--<c:otherwise>--%>
                        <%--&lt;%&ndash;<a href="${EXTERNAL_URL }service/open.html" style="font-size: 12px; color: #444444; line-height: 24px;">立即开通服务</a>&ndash;%&gt;--%>
                        <%--<a href="${EXTERNAL_URL }service/open.html" class="btn" type="button" style="color: #4D72FE">生成声明函</a>--%>
                      <%--</c:otherwise>--%>
                    <%--</c:choose>--%>
                  <%--</p>--%>
                <%--</div>--%>
              <%--</div>--%>
            <%--</div>--%>
          <%--</div>--%>
        </div>
      </div>
      <div role="tabpanel" class="tab-pane" id="profile">
        <div class="form-horizontal text-center" style="margin-top: 40px">
          <fieldset>
            <div id="update-password-div" class="form-group" style="margin-bottom: 30px">
              <label for="inputEmail3" class="col-xs-4 control-label" style="width: 14%;">注册手机号：</label>
              <div class="col-xs-5  real-pad" style="display: inline-block;">
                <input type="text" disabled id="disabledTextInput" class="form-control" placeholder="${user.entContactsMobileForDisplay }">
              </div>
                <button class="get-verifycode" style="position:relative;background: #7FC4FD; font-size: 14px; color: #fff; width: 153px; height: 40px; text-align: center; line-height: 40px;left: -130px">点击发送验证码</button>
<%--                <div class="get-verifycode"--%>
<%--                        style="display:inline-block;cursor:pointer;width: 142px; height: 40px; font-size: 14px; color: #fff; background: #7FC4FD; text-align: center; line-height: 40px; position: relative; left: -135px">发送验证码</div>--%>
            </div>
          </fieldset>
          <div class="input-group" style="margin: 10px 0px 0px 5px;">
            <p class="input-p-display" style="display: none; color: red;margin-left: 118px;">
              已向手机号：<span></span>发送验证码，请注意查收
            </p>
          </div>
          <div class="form-group" style="margin-bottom: 30px">
            <label for="update-password-verifycode" class="col-xs-4 control-label" style="width: 14%;">输入验证码：</label>
            <div class="col-xs-5 real-pad">
              <input type="text" class="form-control" id="update-password-verifycode" placeholder="请输入验证码">
            </div>
          </div>
          <div class="form-group" style="margin-bottom: 30px">
            <label for="update-pass" class="col-xs-4 control-label" style="width: 14%;">新密码：</label>
            <div class="col-xs-5 real-pad">
              <input type="password" class="form-control" id="update-pass" placeholder="请输入新密码">
            </div>
          </div>
          <div class="form-group" style="margin-bottom: 30px">
            <label for="update-affirm-pass" class="col-xs-4 control-label" style="width: 14%;">确认密码：</label>
            <div class="col-xs-5 real-pad">
              <input type="password" class="form-control" id="update-affirm-pass" placeholder="请确认新密码">
            </div>
          </div>
          <div class="form-group ">
            <div class="col-sm-offset-2 col-xs-10 real-up " style="margin: 50px 0 0 220px;cursor:pointer;border:none">
              <button class="to-submit mybluebtn">提交</button>
<%--              <div class="to-submit" style="display:inline-block;cursor:pointer;line-height: 48px;font-size: 16px;">提交</div>--%>
              <input type="hidden" class="submit-update-pass">
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  </div>
  <div class="foot" style="width: 100%; height: 240px; background: #FCFCFC; margin-top: 130px;">
    <jsp:include page="../widget/footer-bottom.jsp" />
  </div>
</body>
</html>