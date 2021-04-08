<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8' %>

<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang='zh'>
<head>
    <meta charset='UTF-8'>
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1'/>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <meta name='renderer' content='webkit'>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <meta name="keywords" content="预猫,信拾贝,怕侵权,原创保护,被侵权,电商反侵权,电商维权,知识产权严密保护">
    <meta name="description" content="预猫是一个致力于原创知识产权严密保护、侵权监测和维权诉讼的互联网平台，服务于电商、设计、文创等行业权利人；怕被侵权，就上预猫，享受省心又严密的知识产权保护。">
    <title>预猫 - 保！我省心</title>

    <link href='${EXTERNAL_URL }favicon.png' rel='shortcut icon' type='image/png'>
    <link href="${EXTERNAL_URL }assets/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href='${EXTERNAL_URL }css/main.css' rel='stylesheet' type='text/css'>
    <link href='${EXTERNAL_URL }css/market.css' rel='stylesheet' type='text/css'>

    <jsp:include page="/WEB-INF/jsp/widget/embedded/baidu-tongji-script.jsp"/>
    <script src='${EXTERNAL_URL }assets/html5shiv/3.7.3/html5shiv.min.js' type='text/javascript'></script>
    <script src='${EXTERNAL_URL }assets/respond.js/1.4.2/respond.min.js' type='text/javascript'></script>

</head>
<body>
<input id="external-url" type="hidden" value="${EXTERNAL_URL }">

<div class="evidenceTitle">
    <div class="header">
        <div class="logoWrap">
            <a href="${EXTERNAL_URL }index.html"><img src="${EXTERNAL_URL }images/evidence/logo.png" alt=""></a>
        </div>
        <div class="loginWrap">
            <a href="${EXTERNAL_URL }index.html">首页</a> <a
                href="${EXTERNAL_URL }evidence-wang.html">证据王</a> <%-- <a class="current" href="javascript:viod(0);">营销保</a> --%>
            <shiro:notAuthenticated>
                <a class="btn" href="${EXTERNAL_URL }login.html">登录</a>
                <a class="btn" href="${EXTERNAL_URL }register.html">注册</a>
            </shiro:notAuthenticated>
            <shiro:authenticated>
                <span><a class="" style="color: #FFFFFF;" href="${EXTERNAL_URL }user/account.html"> <%--<shiro:principal property="username" />--%>我的预猫</a> <a
                        class="btn btn-link hide" style="color: #FFFFFF;"
                        href="${EXTERNAL_URL }original-works/index.html">管理控制台</a> <a class="btn btn-link"
                                                                                      style="color: #FFFFFF;"
                                                                                      href="${EXTERNAL_URL }logout.html">退出登录</a></span>
            </shiro:authenticated>
            <a class="help" href="${EXTERNAL_URL }faq/index.html"> <img src="${EXTERNAL_URL }images/evidence/help.png"
                                                                        alt=""></a>
        </div>
    </div>
</div>
<div class="marketMain">
    <div class="marketMainContainer">
        <h2>
            <img src="${EXTERNAL_URL }images/market/textImg.png"/>
        </h2>
        <div class="des">
            每个做原创的人，都担心产品被抄袭<br> 一旦被抄袭，经济损失，品牌损失都很大<br> 相比能维权成功，我知道，你更希望不被抄袭
        </div>
        <img src="${EXTERNAL_URL }images/market/mainImg.jpg" alt="">
        <h3 class="caasa">中国反侵权联盟标识</h3>
        <p class="caasaDes">
            在商品的预览图上，打上中国反侵权联盟标识；<br> 让盗图，盗版者望而却步！
        </p>
        <h3 class="detailH3">详情页加上原创认证声明图</h3>
        <p class="detailP">
            在商品的详情页上，添加中国反侵权联盟认证声明图；<br> 让盗图，盗版者望而却步；<br> 让购买者明辨真假！
        </p>
        <h2 class="btn-title">“预猫因你而生，如你所愿”</h2>
        <shiro:authenticated>
            <%--<c:choose>--%>
            <%--<c:when test="${hasStatement }">--%>
            <%--<a id="download-statement" class="btn" href="${EXTERNAL_URL }statement/download.html" type="button">下载声明函</a>--%>
            <%--</c:when>--%>
            <%--<c:otherwise>--%>
            <%--<a id="create-statement" class="btn" href="javascript:void(0);" type="button">立即生成声明函</a>--%>
            <%--</c:otherwise>--%>
            <%--</c:choose>--%>
            <c:choose>
                <c:when test="${hasServiceOpened }">
                    <c:choose>
                        <c:when test="${hasStatement }">
                            <a id="download-statement" class="btn" href="${EXTERNAL_URL }statement/download.html"
                               type="button">下载声明函</a>
                        </c:when>
                        <c:otherwise>
                            <a id="create-statement" class="btn" href="javascript:void(0)" type="button">立即生成声明函</a>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <%--<a href="${EXTERNAL_URL }service/open.html" style="font-size: 12px; color: #444444; line-height: 24px;">立即开通服务</a>--%>
                    <a href="${EXTERNAL_URL }service/open.html" class="btn" type="button">立即生成声明函</a>
                </c:otherwise>
            </c:choose>
        </shiro:authenticated>
        <shiro:notAuthenticated>
            <a class="btn" href="${EXTERNAL_URL }login.html" type="button">立即生成声明函</a>
        </shiro:notAuthenticated>
    </div>
</div>
<footer class="index_footer">
    <div class="content">
        <div class="img_box">
            <img src="${EXTERNAL_URL }images/index/erweima.png" alt="">
            <p class="li">网页取证</p>
            <p class="li">手机APP</p>
            <p class="li">见证实录</p>
        </div>
        <div class="entry">
            <%--<p class="tit">中国知识产权公证服务平台查验入口</p>--%>
            <%--<a href="https://www.ipnotary.com/czcx.htm" target="_black" class="link">输入公证保管函编号，即可查询</a>--%>
        </div>
        <div class="kefu_box">
            <p class="li">客服：<br>
                <a target="_blank"
                   href="https://awp.taobao.com/bs/wwlight.html?spm=a1z13.2196529.0.0.71015acaqVKdr6&ver=3&touid=%E4%BF%A1%E6%8B%BE%E8%B4%9D&siteid=cntaobao&status=2&charset=utf-8"><img
                        src="${EXTERNAL_URL }images/icon_ww.gif"/></a>咨询客服
                <a target="_blank"
                   href="https://awp.taobao.com/bs/wwlight.html?spm=a1z13.2196529.0.0.71015acaqVKdr6&ver=3&touid=%E4%BF%A1%E6%8B%BE%E8%B4%9D&siteid=cntaobao&status=2&charset=utf-8"><img
                        src="${EXTERNAL_URL }images/icon_ww.gif"/></a>使用咨询
            </p>
            <p class="li">周一到周五8:30-18:00</p>
        </div>
        <p class="copy_info">
            <span class="icp"><a href="//beian.miit.gov.cn" target="_blank" style="color: #fff;">浙 ICP备 18028232号</a></span> <img src="${EXTERNAL_URL }images/index/beian.png" alt=""
                                                             class="icon"> <span class="right"><a style="color: #fff;" target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=33010402003546">浙公网安备 33010402003546号</a> ©2018 信拾贝官网 All Rights Reserved.</span>
        </p>
    </div>
</footer>

<jsp:include page="/WEB-INF/jsp/market/dialog/create-statement.jsp"/>
</body>

<script src='${EXTERNAL_URL }assets/jquery/1.12.4/jquery.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/bootstrap/3.3.7/js/bootstrap.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/common.js' type='text/javascript'></script>
<script type="text/javascript">
    window.onload = function () {
        // init();
    }

    window.onresize = throttle(windResize, 200);

    function init() {
        windResize();
    }

    function windResize() {
        var winWid = document.documentElement.clientWidth;
        console.log(winWid)
        if (winWid > 1200) {
            $('.evidenceTitle').height(winWid * 799 / 1920);
        } else {
            $('.evidenceTitle').height(1200 * 799 / 1920);
        }
    }

    function throttle(methods, delay) {
        var timer, start;
        return function loop() {
            var self = this, now = new Date(), arg = [].slice.call(arguments);
            if (!start) {
                start = now
            }
            if (timer) {
                clearTimeout(timer)
            }
            if (now - start >= delay) {
                methods.apply(self, arg)
                start = now
            } else {
                timer = setTimeout(function () {
                    loop.apply(self, arg)
                }, delay)
            }
        }
    }

    //-- 生成声明函对话框开始

    function statementAdd(externalUrl, on_result) {
        $.ajax({
            url: externalUrl + "statement/add.json",
            type: 'POST',
            data: JSON.stringify({
                stop_name: $('#create-statement-step-1 #stop-name').val(),
                shop_url: $('#create-statement-step-1 #shop-url').val(),
            }),
            dataType: "json",
            contentType: 'application/json;charset=utf-8',
            cache: false,
            success: function (data) {
                return on_result(data);
            }
        });

        return false;
    }

    $('#create-statement-step-1').on('show.bs.modal', function () {
        $('#create-statement-step-1 #stop-name').val('');
    }).on('shown.bs.modal', function () {
        $('#create-statement-step-1 #stop-name').focus();
    }).on('hiden.bs.modal', function () {
    });

    $('#create-statement-step-1 .btn-go-next').click(function () {
        statementAdd("${EXTERNAL_URL }", function (data) {
            if (data.code === 0) {
                $('#create-statement-step-1').modal('hide');
                $('#create-statement-step-2').modal('show');
                $('#create-statement-step-2 .user-id').val($('#create-statement-step-1 .user-id').val());
                return false;
            }

            if (data.code === -2) {
                location.href = "${EXTERNAL_URL }login.html?return_mapping=" + encodeURIComponent(encodeURIComponent("market.html"));
                return false;
            }
            alert(data.message);
        });
    });

    $('#create-statement-step-2').on('shown.bs.modal', function () {
    }).on('hiden.bs.modal', function () {
    });

    $('#create-statement-step-2 .btn-go-download').click(function () {
        $('#create-statement-step-2').modal('hide');

        location.href = "${EXTERNAL_URL }statement/download.html";

        location.href = "${EXTERNAL_URL }market.html";
    });

    $('#create-statement').click(function () {
        $('#create-statement-step-1').modal('show');
    });

    //-- 生成声明函对话框结束
</script>
</html>
