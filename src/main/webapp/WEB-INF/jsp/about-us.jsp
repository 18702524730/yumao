<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>

<!DOCTYPE html>
<html lang='zh'>
<head>
<meta charset='UTF-8'>
<meta name='viewport' content='width=device-width, initial-scale=1.0'>
<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1' />
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<meta name='renderer' content='webkit'>
<jsp:include page="widget/meta-extends.jsp" />
<title>关于我们 - 预猫</title>
<link href='${EXTERNAL_URL }favicon.png' rel='shortcut icon' type='image/png'>
<link href="${EXTERNAL_URL }assets/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href='${EXTERNAL_URL }css/main.css' rel='stylesheet' type='text/css'>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src='${EXTERNAL_URL }assets/html5shiv/3.7.3/html5shiv.min.js' type='text/javascript'></script>
  <script src='${EXTERNAL_URL }assets/respond.js/1.4.2/respond.min.js' type='text/javascript'></script>
<![endif]-->
<script src='${EXTERNAL_URL }assets/jquery/1.12.4/jquery.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/bootstrap/3.3.7/js/bootstrap.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/common.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/register.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/login.js' type='text/javascript'></script>
</head>
<body>
    <input id="external-url" type="hidden" value="${EXTERNAL_URL }">

    <div class='content-provider'>
        <jsp:include page="widget/top-navbar.jsp" />

        <div class="container-fluid text-center">
            <img alt="" src="${EXTERNAL_URL }images/about-us_banner.png" style="width: 100%;">
        </div>
        <div class="container-fluid text-center">
            <img alt="" src="${EXTERNAL_URL }images/about-us_main.png" style="width: 100%;">
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-6 text-center">
                    <iframe width="604" height="525" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="http://j.map.baidu.com/s/HnpXLV"></iframe>
                </div>
                <div class="col-lg-6">
                    <div class="row contact-us">
                        <div class="col-lg-12 pl-50">
                            <span class="contact-label">联系我们</span>
                        </div>
                        <div class="col-lg-12 row main-background mt-20 pt-20 pb-20">
                            <div class="col-lg-12 pl-50">
                                <span class="contact-company">杭州信拾贝网络科技有限公司</span>
                            </div>
                            <div class="col-lg-12 pl-50">
                                <span class="contact-location">杭州市江干区九环路9号4号楼3楼303室</span>
                            </div>
                        </div>
                    </div>
                    <div class="row qrcode mt-50">
                        <div class="col-lg-5 text-right">
                            <span>用手机扫码关注信拾贝微信服务平台<br />优质知识产权咨询每日推送
                            </span>
                        </div>
                        <div class="col-lg-2 text-center">
                            <img alt="" src="${EXTERNAL_URL }images/qrcode.png">
                        </div>
                        <div class="col-lg-5 text-left">
                            <div class="kefu_box" style="padding: 15px">
                                <p class="li">
                                    客服：<br>
                                    <a target="_blank"
                                                   href="https://awp.taobao.com/bs/wwlight.html?spm=a1z13.2196529.0.0.71015acaqVKdr6&ver=3&touid=%E4%BF%A1%E6%8B%BE%E8%B4%9D&siteid=cntaobao&status=2&charset=utf-8"><img
                                        src="${EXTERNAL_URL }images/icon_ww.gif" /></a>咨询客服
                                    <a target="_blank"
                                       href="https://awp.taobao.com/bs/wwlight.html?spm=a1z13.2196529.0.0.71015acaqVKdr6&ver=3&touid=%E4%BF%A1%E6%8B%BE%E8%B4%9D&siteid=cntaobao&status=2&charset=utf-8"><img
                                            src="${EXTERNAL_URL }images/icon_ww.gif" /></a>使用咨询
                                </p>
                                <p class="li">周一到周五8:30-18:00</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="widget/footer-bottom.jsp" />
</body>
</html>