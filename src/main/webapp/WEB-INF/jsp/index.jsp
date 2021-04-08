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
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<meta name="keywords" content="预猫,信拾贝,怕侵权,原创保护,被侵权,电商反侵权,电商维权,知识产权严密保护">
<meta name="description" content="预猫是一个致力于原创知识产权严密保护、侵权监测和维权诉讼的互联网平台，服务于电商、设计、文创等行业权利人；怕被侵权，就上预猫，享受省心又严密的知识产权保护。">
<title>预猫 - 保！我省心</title>
<link href='${EXTERNAL_URL }favicon.png' rel='shortcut icon' type='image/png'>
<link href="${EXTERNAL_URL }assets/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href='${EXTERNAL_URL }css/main.css' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="${EXTERNAL_URL }css/idangerous.swiper2.7.6.css">
<link href='${EXTERNAL_URL }css/jquery.fullPage.css' rel='stylesheet' type='text/css'>
<link href='${EXTERNAL_URL }css/index.css' rel='stylesheet' type='text/css'>

<jsp:include page="/WEB-INF/jsp/widget/embedded/baidu-tongji-script.jsp" />
<script src='${EXTERNAL_URL }assets/html5shiv/3.7.3/html5shiv.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/respond.js/1.4.2/respond.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/jquery/1.12.4/jquery.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/common.js' type='text/javascript'></script>

  <style>
    #tgBigox{
      position: relative;
      width:1400px; margin:0 auto; height:30px; line-height:30px; padding-right:65px; overflow:hidden;
    }
    #tgGoox{
      color: red;
      position: absolute;
      height:30px; line-height:30px;
      top: 0px;
      left:0;
    }
    #tgGoox a{
      color:red;
      margin-left:8px;
      float:left;
    }
    #tgGoox a:hover{
      color:#57bc54;
    }
    .noteimg{position: fixed;z-index: 10;top:50%;left: 50%;margin-top: -300px;margin-left: -650px;}
    #close{position: absolute;right: 10px;top: 5px;font-size: 20px;}body{background-color: #333;}
  </style>


</head>
<body>
<%--<div style="background:#fff;">
  <div id="tgBigox">
    <div id="tgGoox">
      <a href="https://www.ipsebe.com/cms/service/rightoriginal.htm" target="_blank">各位预猫用户，您好，由于预猫平台战略升级，自2020年4月1日起将无法进行使用，若您有存证需求，请移步至https://www.ipsebe.com/cms/service/rightoriginal.htm ，使用预猫平台的注册手机号进行注册，后续我司会配置相应存证包，使用期限与您预猫到期时间相同，到期后存证包数量清零，如有疑问，可旺旺咨询：信拾贝</a>
    </div>
  </div>
</div>--%>
<div class="mask"></div><div class="noteimg"><span id="close" style="cursor:pointer">关闭</span><a href="https://www.ipsebe.com/cms/service/rightoriginal.htm" target="_blank"><img src="${EXTERNAL_URL }images/attention.png" alt=""></a> </div>
<%--<p >各位预猫用户，您好，由于预猫平台战略升级，自2020年4月1日起将无法进行使用，若您有存证需求，请移步至https://www.ipsebe.com/cms/service/rightoriginal.htm ，使用预猫平台的注册手机号进行注册，后续我司会配置相应存证包，使用期限与您预猫到期时间相同，到期后存证包数量清零，如有疑问，可旺旺咨询：信拾贝</p>--%>
  <input id="external-url" type="hidden" value="${EXTERNAL_URL }">
  <div style="position: absolute; top: 505px; left:93.5%; opacity:0.5; z-index: 999;">
    <img src="${EXTERNAL_URL }images/index-contact-us.png" alt="">
    <a target="_blank"  href="https://awp.taobao.com/bs/wwlight.html?spm=a1z13.2196529.0.0.71015acaqVKdr6&ver=3&touid=%E4%BF%A1%E6%8B%BE%E8%B4%9D&siteid=cntaobao&status=2&charset=utf-8" style="position: absolute;top: 0;left: 0;width: 100%;text-align: center;padding-top: 25px;"><img
          src="${EXTERNAL_URL }images/contact-wangwang.png" alt="" style="margin-bottom: 10px;">
      <span style="position: relative;  color: white; display: block; font-size: 13px; width: 13px;margin-left: auto;margin-right: auto;">联系我们</span></a>
  </div>

  <div class="evidenceTitle index_header">
    <div class="header clearfix">
      <%--     <img src="${EXTERNAL_URL }images/index-contact-us.png" alt="" style="position: absolute; top: 495px;left: 595px;"> --%>
      <!--       <div style="position: absolute; top: 500px; left: 1495px;"> -->
      <div class="logoWrap">
        <a href="${EXTERNAL_URL }index.html"><img src="${EXTERNAL_URL }images/evidence/logo.png" alt=""></a>
      </div>
      <div class="loginWrap">
        <%--         <img src="${EXTERNAL_URL }images/index-contact-us.png" alt="" style="position: absolute; top: 495px;left: 595px;"> --%>
        <a class="current" href="javascript:viod(0);">首页</a> <a class="" href="${EXTERNAL_URL}evidence-wang.html">证据王</a> <%-- <a href="${EXTERNAL_URL }market.html">营销保</a> --%>
        <shiro:notAuthenticated>
          <a class="btn" href="${EXTERNAL_URL }login.html">登录</a>
          <a class="btn" href="${EXTERNAL_URL }register.html">注册</a>
        </shiro:notAuthenticated>
        <shiro:authenticated>
          <span><a class="" style="color: #FFFFFF;" href="${EXTERNAL_URL }user/account.html"> <%--<shiro:principal property="username" />--%>我的预猫</a> <a class="btn btn-link hide" style="color: #FFFFFF;"
            href="${EXTERNAL_URL }original-works/index.html">管理控制台</a> <a class="btn btn-link" style="color: #FFFFFF;" href="${EXTERNAL_URL }logout.html">退出登录</a></span>
        </shiro:authenticated>
        <a class="help" href="${EXTERNAL_URL }faq/index.html"> <img src="${EXTERNAL_URL }images/evidence/help.png" alt=""></a>
      </div>
    </div>
  </div>

  <img src="${EXTERNAL_URL }images/index/next_page.png" alt="" class="next_page" id="moveUp">
  <div id="fullpage">
    <div class="section section_one">
      <div class="wrap ">
        <div class="swiper-container swiper_one" id="swiperOne">
          <div class="swiper-wrapper">
            <div class="swiper-slide">
              <div class="part_lef">
                <div class="tit">
                  <img src="${EXTERNAL_URL }images/index/tit_1_1.png" alt="" class="img">
                </div>
                <p class="binfo">“维权的判定，就是权利在先”</p>
                <p class="des">如果因为上一次犹豫，留下了麻烦，那么这次再犹豫，就只剩下绝望!</p>
                <!-- <a href="login.html" class="btn">立即开通</a> -->
              </div>
              <div class="part_rig">
                <img src="${EXTERNAL_URL }images/index/img_1_1.png" alt="" class="bg">
              </div>
            </div>
            <div class="swiper-slide">
              <div class="part_lef">
                <div class="tit">
                  <img src="${EXTERNAL_URL }images/index/tit_1_2.png" alt="" class="img">
                </div>
                <p class="des">预猫是为图片、产品、设计等原创作品提供公证确权、侵权跟踪、辅助维权的包年服务，当原创作者的权益受到侵犯时，可以维权止损或索赔。</p>
                <!-- <a href="login.html" class="btn">立即开通</a> -->
              </div>
              <div class="part_rig">
                <img src="${EXTERNAL_URL }images/index/img_1_2.png" alt="" class="bg">
              </div>
            </div>
          </div>
        </div>
        <div id="paginationOne" class="pagination"></div>
        <div class="toggle_box">
          <img src="${EXTERNAL_URL }images/index/prev.png" alt="" class="icon prev" id="prevOne"> <span class="index_wrap" id="indexBoxOne"><span class="index">01</span>/
            <span class="total">02</span></span> <img src="${EXTERNAL_URL }images/index/next.png" alt="" class="icon next" id="nextOne">
        </div>
      </div>
    </div>
    <div class="section section_two">
      <div class="wrap">
        <div class="part_lef">
          <div class="tit">
            <img src="${EXTERNAL_URL }images/index/tit_2_1.png" alt="" class="img">
          </div>
          <p class="info">侵权、确权、被盗、维权，无能为力!</p>
          <div class="case_box">
            <div class="item clearfix">
              <p class="tit_line">
                <span class="name">双楼凯歌</span> <span class="time">2019-7-6 14:27:19</span>
              </p>
              <p class="info">我可以申请专利，但我们产品很多，更新比较快，不划算!</p>
            </div>
            <div class="item clearfix">
              <p class="tit_line">
                <span class="name">慕庭旗舰店</span> <span class="time">2019-6-9 15:29:39</span>
              </p>
              <p class="info">侵权的数量，然后外观的专利，还有一个痛就是要做评估报告，这个维权成本及时间又进一步增加!</p>
            </div>
          </div>
        </div>
        <div class="part_rig">
          <img src="${EXTERNAL_URL }images/index/img_2_1.png" alt="" class="bg">
        </div>
      </div>
    </div>
    <div class="section section_three">
      <div class="wrap">
        <div class="part_lef">
          <div class="tit">
            <img src="${EXTERNAL_URL }images/index/tit_3_1.png" alt="" class="img">
          </div>
          <p class="info">三大步，四大优势，让你彻底摆脱维权的“烦心事”</p>
          <div class="step_box">
            <div class="item">
              <div class="icon_box">
                <img src="${EXTERNAL_URL }images/index/icon_1.png" alt="">
                <p class="type_name">10G存储空间</p>
              </div>
              <p class="title">证据上传</p>
              <p class="info">将需保护的文件上传至预猫平台</p>
            </div>
            <div class="item">
              <div class="icon_box">
                <img src="${EXTERNAL_URL }images/index/icon_2.png" alt="">
                <p class="type_name">侵权跟踪处理</p>
              </div>
              <p class="title">侵权跟踪</p>
              <p class="info">根据用户提交的侵权链接，进行侵权处理</p>
            </div>
            <div class="item">
              <div class="icon_box">
                <img src="${EXTERNAL_URL }images/index/icon_3.png" alt="">
                <p class="type_name">专业法律团队</p>
              </div>
              <p class="title">代理维权</p>
              <p class="info">被保护的文件被侵权，预猫将进行代理维权</p>
            </div>
          </div>
        </div>
        <div class="part_rig">
          <div class="img_box">
            <div class="swiper-container swiper_three" id="swiperThree">
              <div class="swiper-wrapper">
                <div class="swiper-slide">
                  <img src="${EXTERNAL_URL }images/index/banner_3_1.png" class="img" alt="">
                </div>
                <div class="swiper-slide">
                  <img src="${EXTERNAL_URL }images/index/banner_3_2.png" class="img" alt="">
                </div>
                <div class="swiper-slide">
                  <img src="${EXTERNAL_URL }images/index/banner_3_3.png" class="img" alt="">
                </div>
                <div class="swiper-slide">
                  <img src="${EXTERNAL_URL }images/index/banner_3_4.png" class="img" alt="">
                </div>
              </div>
            </div>
            <div id="paginationThree" class="pagination"></div>
            <div class="toggle_box">
              <img src="${EXTERNAL_URL }images/index/prev_b.png" alt="" class="icon prev" id="prevThree"> <span class="index_wrap" id="indexBoxThree"><span
                class="index">01</span>/ <span class="total">02</span></span> <img src="${EXTERNAL_URL }images/index/next_b.png" alt="" class="icon next" id="nextThree">
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="section section_four">
      <div class="wrap">
        <div class="part_lef">
          <div class="tit">
            <img src="${EXTERNAL_URL }images/index/tit_4_1.png" alt="" class="img">
          </div>
          <p class="info">电商、文创、设计等等都需要”我“</p>
        </div>
        <div class="part_rig">
          <div class="list_box">
            <div class="item">
              <img src="${EXTERNAL_URL }images/index/img_4_1.png" class="icon" alt="">
              <p class="title">电商行业</p>
              <p class="info">产品主图、详情页，都是电商企业最容易被盗用和模仿的，早保护，早安心。</p>
              <a class="more"> <img src="${EXTERNAL_URL }images/index/img_4_7.png" alt=""> <span class="text">MORE</span>
              </a>
            </div>
            <div class="item">
              <img src="${EXTERNAL_URL }images/index/img_4_3.png" class="icon" alt="">
              <p class="title">文创行业</p>
              <p class="info">文化创意行业是知识产权被侵权的重灾区，一次被侵权，可能直接被拖垮。</p>
              <a class="more"> <img src="${EXTERNAL_URL }images/index/img_4_7.png" alt=""> <span class="text">MORE</span>
              </a>
            </div>
            <div class="item">
              <img src="${EXTERNAL_URL }images/index/img_4_5.png" class="icon" alt="">
              <p class="title">设计行业</p>
              <p class="info">设计之所以能产生价值是因为有独特性，如果满大街都是盗版，那还有什么价值。</p>
              <a class="more"> <img src="${EXTERNAL_URL }images/index/img_4_7.png" alt=""> <span class="text">MORE</span>
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="section section_five">
      <div class="wrap">
        <div class="swiper-container swiper_five" id="swiperFive">
          <div class="swiper-wrapper">
            <div class="swiper-slide">
              <div class="part_lef" style="margin-top: 40px;">
                <div class="tit">
                  <img src="${EXTERNAL_URL }images/index/tit_5_1.png" alt="" class="img">
                </div>
                <p class="binfo">中国知识产权公证服务平台</p>
                <p class="des">在中华人民共和国司法部和中国公证协会的授权及指导下，构建以公证法律服务为核心，着眼于知识产权的权利认定、流转和侵权维权等环节，集知识产权专业服务创作、保护、交易安全、维权服务等于一体的知识产权保护法律体系。</p>
              </div>
              <div class="part_rig">
                <img src="${EXTERNAL_URL }images/index/img_5_1.png" alt="" class="bg">
              </div>
            </div>
            <%--<div class="swiper-slide">--%>
              <%--<div class="part_lef">--%>
                <%--<div class="tit">--%>
                  <%--<img src="${EXTERNAL_URL }images/index/tit_5_1.png" alt="" class="img">--%>
                <%--</div>--%>
                <%--<p class="binfo">中国反侵权假冒创新战略联盟</p>--%>
                <%--<p class="des">在全国双打办的指导和中国产学研合作促进会的支持下，由国家三十多个部委、百余家知名企业，共同发起组建的“中国产学研合作促进会反侵权假冒创新战略联盟”非盈利性组织。</p>--%>
              <%--</div>--%>
              <%--<div class="part_rig">--%>
                <%--<img src="${EXTERNAL_URL }images/index/img_5_2.png" alt="" class="bg">--%>
              <%--</div>--%>
            <%--</div>--%>
            <div class="swiper-slide">
              <div class="part_lef">
                <div class="tit">
                  <img src="${EXTERNAL_URL }images/index/tit_5_1.png" alt="" class="img">
                </div>
                <p class="binfo">电子商务产品打假维权协作网</p>
                <p class="des">由质检总局执法督查司发起，全国“双打办”和中国消费者协会支持，严厉打击互联网领域销售假冒伪劣违法行为，切实维护广大消费者、名优产品生产企业和电商平台企业的合法权益，促进电子商务健康发展的电子商务产品质量执行新平台。</p>
              </div>
              <div class="part_rig">
                <img src="${EXTERNAL_URL }images/index/img_5_3.png" alt="" class="bg">
              </div>
            </div>
          </div>
          <div id="paginationFive" class="pagination"></div>
          <div class="toggle_box">
            <img src="${EXTERNAL_URL }images/index/prev.png" alt="" class="icon prev" id="prevFive"> <span class="index_wrap" id="indexBoxFive"><span class="index">01</span>/
              <span class="total">02</span></span> <img src="${EXTERNAL_URL }images/index/next.png" alt="" class="icon next" id="nextFive">
          </div>
        </div>
      </div>
    </div>
    <div class="section section_six">
      <div class="wrap">
        <div class="part_lef">
          <div class="tit">
            <%--<img src="${EXTERNAL_URL }images/index/tit_6_1.png" alt="" class="img">--%>
            <p style="font-size: 30px;color: #FFF">加入“预猫”，同行都在用</p>
          </div>
          <p class="info">从前忘保护和未来要保护的原创，统统上传到预猫平台，即刻享受保护；只需999，别人享受的你也能享受。</p>
          <div class="btn_box">
            <p class="price">
              999 <span>/年</span>
            </p>
            <a href="${EXTERNAL_URL }service/open.html" class="btn">立即开通</a>
          </div>
        </div>
        <div class="part_rig">
          <img src="${EXTERNAL_URL }images/index/img_6_1.png" alt="" class="bg">
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
              <p class="li">客服：<br> <a target="_blank"
                href="https://awp.taobao.com/bs/wwlight.html?spm=a1z13.2196529.0.0.71015acaqVKdr6&ver=3&touid=%E4%BF%A1%E6%8B%BE%E8%B4%9D&siteid=cntaobao&status=2&charset=utf-8"><img
                    src="${EXTERNAL_URL }images/icon_ww.gif" /></a>咨询客服
              <a target="_blank"
                href="https://awp.taobao.com/bs/wwlight.html?spm=a1z13.2196529.0.0.71015acaqVKdr6&ver=3&touid=%E4%BF%A1%E6%8B%BE%E8%B4%9D&siteid=cntaobao&status=2&charset=utf-8"><img
                src="${EXTERNAL_URL }images/icon_ww.gif" /></a>使用咨询
            </p>
            <p class="li">周一到周五8:30-18:00</p>
          </div>
          <p class="copy_info">
            <span class="icp"><a href="//beian.miit.gov.cn" target="_blank" style="color: #fff;">浙 ICP备 18028232号</a></span> <img src="${EXTERNAL_URL }images/index/beian.png" alt="" class="icon"> <span class="right"><a style="color: #fff;" target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=33010402003546">浙公网安备
              33010402003546号</a> ©2018 信拾贝官网 All Rights Reserved.</span>
          </p>
        </div>
      </footer>
    </div>
  </div>
<script type="text/javascript">
  var cars = $("#tgGoox").width();
  var i =0;
  function start(){
    i--;
    if(i<=-cars){
      i=cars;
      document.getElementById('tgGoox').style.right =-cars+'px';
    }else{
      document.getElementById('tgGoox').style.left =i+'px';
    }
    setTimeout(start,20);
  }
  onload=function(){setTimeout(start,1000)};
</script>
<script>var mask = document.getElementById('close');var img = document.getElementsByTagName('img')[0];mask.onclick=function(){img.style.display = "none";this.style.display = "none";}</script>

  <script src='${EXTERNAL_URL }assets/fullpage.js' type='text/javascript'></script>
  <!-- swiper -->
  <script src="${EXTERNAL_URL }assets/swiper/idangerous.swiper2.7.6.min.js"></script>
  <script src='${EXTERNAL_URL }js/index.js' type='text/javascript'></script>
</body>
</html>
