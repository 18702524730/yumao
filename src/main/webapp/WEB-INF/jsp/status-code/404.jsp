<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>

<!DOCTYPE html>
<html lang='zh'>
<head>
<meta charset='UTF-8'>
<meta name='viewport' content='width=device-width, initial-scale=1.0'>
<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1' />
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<meta name='renderer' content='webkit'>
<jsp:include page="../widget/meta-extends.jsp" />
<title>404</title>
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
    <script type="text/javascript">
                    $(document).ready(function() {
                        var waitingSecondsField = $('.waiting-seconds');
                        var intervalID = setInterval(function() {
                            var waitingSeconds = parseInt(waitingSecondsField.text());
                            if (waitingSeconds <= 1) {
                                clearInterval(intervalID);
                                location.href = '${EXTERNAL_URL }index.html';
                                return false;
                            }
                            waitingSecondsField.text(waitingSeconds - 1);
                        }, 1000);
                    });
                </script>


    <div class='content-provider' >
        <jsp:include page="../widget/top-navbar.jsp" />
        <div class="container-fluid"  style="margin-bottom: 100px;">
            <div class="col-sm-12" align="center">
                <div class="col-xs-8 col-sm-6 " style=" position:absolute;left: 25%; top: 26%;">
                    <span class="col-lg-12" style="font-size: 16px;margin-top:150px;">很遗憾，您访问的页面挖不到了...</span> <span class="col-lg-12 mt-10" style="font-size: 16px;"><span class="waiting-seconds">5</span>秒后跳转到首页。如没有跳转<a class="btn btn-link color-link" style="color: #528CC2;" href="${EXTERNAL_URL }index.html">点击这里</a> </span>
                </div>
                <img alt="" src="${EXTERNAL_URL }images/404.png">
            </div>
        </div>
    </div>

    <jsp:include page="../widget/footer-bottom.jsp" />
</body>
</html>