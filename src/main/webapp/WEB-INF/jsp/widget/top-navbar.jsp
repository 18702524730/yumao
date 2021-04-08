<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>

<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../widget/register-dialog.jsp" />
<jsp:include page="../widget/login-dialog.jsp" />

<c:choose>
    <c:when test="${true eq param.top_layer }">
        <nav class="navbar top-navbar col-lg-12" style="position: absolute; top: 0px; z-index: 1000;">
    </c:when>
    <c:otherwise>
        <nav class="navbar top-navbar col-lg-12" style="background-color: white;">
    </c:otherwise>
</c:choose>

<div class="container">
    <div class="navbar-header">
        <button type="button" class="btn btn-link no-color-link navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
            <span class="sr-only">切换导航</span> <span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>
        </button>
        <a class="btn btn-link no-color-link hidden-xs" href="${EXTERNAL_URL }index.html"> <c:choose>
                <c:when test="${param.top_layer }">
                    <img alt='' style="width: 100%;" src='${EXTERNAL_URL }images/logo_title_white.png'>
                </c:when>
                <c:otherwise>
                    <img alt='' style="width: 100%;" src='${EXTERNAL_URL }images/logo_title.png'>
                </c:otherwise>
            </c:choose>


        </a>
    </div>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav mt-20">
            <%--                 <li class="active"><a class="btn btn-link no-color-link" href="${EXTERNAL_URL }index.html">首页</a></li> --%>
            <%--                 <li><a class="btn btn-link no-color-link" href="${EXTERNAL_URL }index.html">预猫</a></li> --%>
            <!--                 <li class="dropdown"><a class="btn btn-link no-color-link dropdown-toggle" href="#" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">存证产品&nbsp;<span class="caret"></span></a> -->
            <!--                     <ul class="dropdown-menu"> -->
            <!--                         <li><a class="btn btn-link no-color-link" target="_blank" href="https://cz.ipsebe.com/notarization/pages/webNotarize.html?source=1&czPurpose=1#/appNew">网页取证</a></li> -->
            <!--                         <li><a class="btn btn-link no-color-link" target="_blank" href="javascript:void(0);">手机取证</a></li> -->
            <!--                         <li><a class="btn btn-link no-color-link" target="_blank" href="javascript:void(0);">见证实录</a></li> -->
            <!--                                                 <li role="separator" class="divider"></li> -->
            <!--                     </ul></li> -->
            <!--                 <li><a class="btn btn-link no-color-link" target="_blank" href="https://www.ipnotary.com/czcx.htm">存证查验</a></li> -->
            <%--                 <li><a class="btn btn-link no-color-link" href="${EXTERNAL_URL }about-us.html">关于我们</a></li> --%>
        </ul>
        <ul class="nav navbar-nav navbar-right" style="margin-top:12px;">
            <c:choose>
                <c:when test="${param.top_layer }">
                    <li><shiro:authenticated>
                            <span style="color: #FFFFFF;"> <a class="btn" style="color: #FFFFFF;" href="javascript:void(0);"><shiro:principal property="username" /></a> <a class="btn btn-link" style="color: #FFFFFF;" href="${EXTERNAL_URL }original-works/index.html">管理控制台</a> <a class="btn btn-link" style="color: #FFFFFF;" href="${EXTERNAL_URL }logout.html">退出登录</a></span>
                        </shiro:authenticated> <shiro:notAuthenticated>
                            <c:choose>
                                <c:when test="${empty param.no_display_login || false eq param.no_display_login }">
                                    <span><a class="btn btn-link no-color-link" style="color: #FFFFFF;" href="${EXTERNAL_URL }login.html">登录</a></span>
                                    <span><a class="btn btn-link no-color-link" style="color: #FFFFFF;" href="${EXTERNAL_URL }register.html">注册</a></span>
                                </c:when>
                                <c:otherwise>
                                    <span> <a class="btn btn-link no-color-link" style="color: #FFFFFF;" href="${EXTERNAL_URL }index.html">首页</a>
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </shiro:notAuthenticated></li>
                </c:when>
                <c:otherwise>
                    <li><shiro:authenticated>
                            <span> <a class="btn" style="color: #000000;" href="javascript:void(0);"><shiro:principal property="username" /></a> <a class="btn btn-link" href="${EXTERNAL_URL }original-works/index.html">管理控制台</a> <a class="btn btn-link" href="${EXTERNAL_URL }logout.html">退出登录</a></span>
                        </shiro:authenticated> <shiro:notAuthenticated>
                            <c:choose>
                                <c:when test="${empty param.no_display_login || false eq param.no_display_login }">
                                    <span><a class="btn btn-link no-color-link" type="button" class="btn btn-link" href="${EXTERNAL_URL }login.html">登录</a></span>
                                    <span><a class="btn btn-link no-color-link" type="button" class="btn btn-link" href="${EXTERNAL_URL }register.html">注册</a></span>
                                </c:when>
                                <c:otherwise>
                                    <span> <a class="btn btn-link no-color-link" type="button" class="btn btn-link" href="${EXTERNAL_URL }index.html">首页</a>
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </shiro:notAuthenticated></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
    <!-- /.navbar-collapse -->
</div>
<!-- /.container-fluid -->
</nav>
<div style='background-color: #969594; height: 1px; border: none;'></div>
