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
<jsp:include page="widget/meta-extends.jsp" />
<title>用户注册 - 预猫</title>
<link href='${EXTERNAL_URL }favicon.png' rel='shortcut icon' type='image/png'>
<link href="${EXTERNAL_URL }assets/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href='${EXTERNAL_URL }css/main.css' rel='stylesheet' type='text/css'>
<link href='${EXTERNAL_URL }css/regLogin.css' rel='stylesheet' type='text/css'>

<jsp:include page="/WEB-INF/jsp/widget/embedded/baidu-tongji-script.jsp" />
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src='${EXTERNAL_URL }assets/html5shiv/3.7.3/html5shiv.min.js' type='text/javascript'></script>
  <script src='${EXTERNAL_URL }assets/respond.js/1.4.2/respond.min.js' type='text/javascript'></script>
<![endif]-->
<script src='${EXTERNAL_URL }assets/html5shiv/3.7.3/html5shiv.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/respond.js/1.4.2/respond.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/jquery/1.12.4/jquery.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/bootstrap/3.3.7/js/bootstrap.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/common.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/register.js' type='text/javascript'></script>
<style>
.reglogin_page .agreement .agreement_con {
		top: 58%;
		width: 728px;
		height: 700px;
		left: 54%;
}

.reg_content>img {
		margin: 30px 0 0 10%;
}

.reg_content_in .form_box_in .tab_label {
		padding-top: 48px;
}

.reg_content_in .form_box_in .tab-content {
		padding-top: 6px;
}

.reg_content_in .form_box {
		width: 586px;
}

.reg_content_in .form_box_in .register-dialog {
		padding-bottom: 30px;
}

.reglogin_page .agreement .agreement_con_in {
		height: 528px;
}

.reg_content_in .form_box_in .tab_label {
		height: 116px;
}

.reg_content_in .form_box_in .tab-content .tab-panel .form-group {
		margin-bottom: 20px;
}

.reg_content_in .form_box_in .form-footer {
		margin: 44px auto 0;
}

.reg_content_in .form-control {
		border: 1px solid #ccc;
}
</style>
</head>
<body class="reglogin_page">
  <input id="external-url" type="hidden" value="${EXTERNAL_URL }">
  <input id="return-mapping" type="hidden" value="${return_mapping }">
  <input id="user-type" type="hidden" value="1">

  <div class="reg_content">
    <img class="logo" src="${EXTERNAL_URL }images/evidence/logo.png">
    <div class="reg_content_in">
      <div class="form_container">
        <div class="form_box">
          <div class="form_box_in">
            <form class="register-dialog pb-20 register-frame">
              <div style="padding: 30px 0px 0px 98px;">账号下所存文件，所有权归个人，选个人注册，所有权归企业，选企业注册。</div>
              <div class="tab_label">
                <label class="cur" id="1">个人用户<span></span></label> <label id="2">企业用户<span></span></label>
              </div>
              <div class="tab-content">
                <div role="tabpanel" class="tab-panel active" id="dialog-grpart">
                  <div class="form-group">
                    <label for="dialog-register-gr-username" class="col-lg-3 control-label">用户名：</label>
                    <div class="my-col pr-20">
                      <input id="dialog-register-gr-username" type="text" class="form-control register-username" placeholder="请填写用户名" autocomplete="off">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="dialog-register-gr-login-password" class="col-lg-3 control-label">登录密码：</label>
                    <div class="my-col">
                      <input id="dialog-register-gr-login-password" type="password" class="form-control register-login-password" placeholder="请填写登录密码" autocomplete="off">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="dialog-register-gr-affirm-login-password" class="col-lg-3 control-label">确认密码：</label>
                    <div class="my-col">
                      <input id="dialog-register-gr-affirm-login-password" type="password" class="form-control register-affirm-login-password" placeholder="请再次填写登录密码"
                        autocomplete="off">
                    </div>
                  </div>
                  <div class="form-group phone-group">
                    <label for="dialog-register-gr-mobile" class="col-lg-3 control-label">手机号码：</label>
                    <div class="my-col">
                      <span>+86</span><input id="dialog-register-gr-mobile" type="text" class="form-control register-mobile" placeholder="请填写手机号码" autocomplete="off">
                    </div>
                  </div>
                  <div class="form-group has-feedback">
                    <label for="dialog-register-gr-mobile-verifycode" class="col-lg-3 control-label">验证码：</label>
                    <div class="my-col pr-20">
                      <div class="input-group">
                        <input id="dialog-register-gr-mobile-verifycode" type="text" class="form-control register-mobile-verifycode" placeholder="请填写验证码" autocomplete="off">
                        <button class="code-btn get-verifycode" type="button">获取验证码</button>
                      </div>
                    </div>
                  </div>
                </div>
                <div role="tabpanel" class="tab-panel" id="dialog-qypart">
                  <div class="form-group">
                    <label for="dialog-register-qy-username" class="col-lg-3 control-label">用户名：</label>
                    <div class="my-col pr-20">
                      <input id="dialog-register-qy-username" type="text" class="form-control register-username" placeholder="请填写用户名" autocomplete="off">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="dialog-register-qy-login-password" class="col-lg-3 control-label">登录密码：</label>
                    <div class="my-col">
                      <input id="dialog-register-qy-login-password" type="password" class="form-control register-login-password" placeholder="请填写登录密码" autocomplete="off">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="dialog-register-qy-affirm-login-password" class="col-lg-3 control-label">确认密码：</label>
                    <div class="my-col">
                      <input id="dialog-register-qy-affirm-login-password" type="password" class="form-control register-affirm-login-password" placeholder="请再次填写登录密码"
                        autocomplete="off">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="dialog-register-qy-ent-uni-credit-code" class="col-lg-3 control-label">统一社会信用代码：</label>
                    <div class="my-col pr-20">
                      <input id="dialog-register-qy-ent-uni-credit-code" type="text" class="form-control register-ent-uni-credit-code" placeholder="请填写统一社会信用代码" autocomplete="off">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="dialog-register-qy-ent-org-code" class="col-lg-3 control-label">组织机构代码：</label>
                    <div class="my-col pr-20">
                      <input id="dialog-register-qy-ent-org-code" type="text" class="form-control register-ent-org-code" placeholder="请填写组织机构代码" autocomplete="off">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="dialog-register-qy-ent-contacts-realname" class="col-lg-3 control-label">企业联系人真实姓名：</label>
                    <div class="my-col pr-20">
                      <input id="dialog-register-qy-ent-contacts-realname" type="text" class="form-control register-ent-contacts-realname" placeholder="请填写企业联系人真实姓名"
                        autocomplete="off">
                    </div>
                  </div>
                  <div class="form-group phone-group">
                    <label for="dialog-register-qy-ent-contacts-mobile" class="col-lg-3 control-label">企业联系人手机号码：</label>
                    <div class="my-col pr-20">
                      <span>+86</span><input id="dialog-register-qy-ent-contacts-mobile" type="text" class="form-control register-ent-contacts-mobile" placeholder="请填写企业联系人手机号码"
                        autocomplete="off">
                    </div>
                  </div>
                  <div class="form-group has-feedback">
                    <label for="dialog-register-qy-mobile-verifycode" class="col-lg-3 control-label">验证码：</label>
                    <div class="my-col pr-20">
                      <div class="input-group">
                        <input id="dialog-register-qy-mobile-verifycode" type="text" class="form-control register-mobile-verifycode" placeholder="请填写验证码" autocomplete="off">
                        <button class="code-btn get-verifycode" type="button">获取验证码</button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="form-footer mt-40">
                <%--<button type="button" class="submit-register">完成</button>--%>
                  <p style="color: red;font-size: 14px;">因公司战略调整，预猫业务将进行升级，如您有存证需求，请移步至拾贝网：<a href="https://www.ipsebe.com/cms/service/rightoriginal.htm" style="font-size: 14px;text-decoration: underline"> 点击前往</a></p>
                <div class="col-lg-12 text-center">
                  <span>已有账号？</span><a class="im" href="${EXTERNAL_URL }login.html">登录</a>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="agreement">
    <div class="mask"></div>
    <div class="agreement_con">
      <h3>网站服务协议</h3>
      <div class="agreement_con_in">
        <p>本协议是本网站（网址：http://www.xsebe.com/）的所有者杭州信拾贝网络科技有限公司（以下简称“信拾贝”）与用户（即购买本网站相关服务的购买者）订立的契约，“用户”在本协议中也简称为“您”。本协议适用于您在本网站下单购买的相关服务。</p>
        <p>在接受本协议前，请您务必审慎阅读，充分理解各条款内容，关于本协议，提示您特别关注风险条款、免责条款，以及信拾贝对您违规、违约行为的认定处理条款等。如您不同意本协议的任何内容或无法准确理解信拾贝对条款的解释，请不要同意本协议或购买使用本协议项下的服务，否则我们将视为您已接受本协议所有条款内容，同意接受本协议约束。届时您不得以未阅读本协议的内容或未获得信拾贝对您疑问条款的解答等理由，主张本协议无效或要求撤销本协议。</p>
        <p>1.服务类目（包括但不限于以下内容）</p>
        <p>1.1商标方面：国内、外商标注册；商标变更、转让、续展、许可、交易、异议申请、无效宣告、撤三、各种复审及答辩、维权、诉讼等服务。</p>
        <p>1.2专利方面：发明、实用新型、外观设计专利申请服务及其他疑难专利案件等服务。</p>
        <p>1.3著作权方面：著作权登记、变更、转让、许可、著作权纠纷及游戏版号等服务。</p>
        <p>1.4科技项目方面：国家级高新技术企业的认定、科技项目服务、网络文化经营资质办理等服务。</p>
        <p>1.5原创保护方面：电子存证、侵权监测、协助维权等服务。</p>
        <p>2.用户权利与义务</p>
        <p>2.1您购买本网站提供的相关服务时应遵循诚信原则，应通过合规方式购买使用。</p>
        <p>2.2您确保您向信拾贝提供或通过本网站上传的所涉资料真实、合法、正确、有效的，包括但不限于姓名、有效证件号码（身份证、公司注册证、护照等）、邮箱地址、联系电话、联系地址、邮编等信息。信息如有变更应及时书面通知到信拾贝，如因您注册或提供的信息不真实而产生的一切法律后果及责任，由您承担。</p>
        <p>2.3您应按本网站的具体流程及费用办理相关服务，否则信拾贝有权终止该服务。</p>
        <p>2.4在您购买本网站相关服务后，您有权知悉该服务的任何阶段工作进展情况。</p>
        <p>2.5您在本网站实施的所有行为均应遵守国家相关的法律、法规规定、本协议约定和本网站各项规则要求，不违背社会功德良俗及公共利益，不损害他人的合法权益。如您违反前述的，一切责任后果由您承担。</p>
        <p>3.信拾贝的权利与义务</p>
        <p>3.1信拾贝尽力在现有技术上维护本网站的正常运行，并努力提升和优化技术，以便给用户提供更优质的用网体验。</p>
        <p>3.2信拾贝应在收到用户支付的款项及提供合规的相应文件后，及时向用户提供专业服务。</p>
        <p>3.3信拾贝将积极维护您的合法权益，未经您的事先同意，不得更改您委托办理的服务事项。</p>
        <p>3.4信拾贝有义务对您的个人信息以及相关资料保密。</p>
        <p>3.5若信拾贝向您提供相关服务并收到国家或地方官方机构下发的相关文件时，信拾贝应及时通知您或将文件邮寄给您。</p>
        <p>4.服务使用、变更、中断或终止</p>
        <p>4.1 信拾贝为您提供的各项服务，您应正当、合理、善意地使用。如发生下列任何一种情形，信拾贝有权不通知您而中断或终止向您提供服务：</p>
        <p>4.1.1您提供的个人资料不真实、或未按相关规则填写信息又未能提供合理证明；</p>
        <p>4.1.2您违反相关法律法规或本协议的约定；</p>
        <p>4.1.3信拾贝出于安全原因或其他必要情形的考虑。</p>
        <p>4.2如因本网站系统维护或升级的需要而需暂停服务，信拾贝将尽可能事先进行通知，但由于不可预计的、临时性的调整维护或升级导致需要暂停服务的，信拾贝不承担任何责任。</p>
        <p>4.3信拾贝会尽力维护本网站的正常运行，但由于信拾贝无法预测或无法控制的因素导致本网站发生技术故障而造成你购买的服务产生错误或其他情况的，届时按实际而非技术错误时的情况处理。</p>
        <p>5.协议的订立、生效和服务期限</p>
        <p>5.1用户通过在本网站确认本协议或以其他方式确认本协议，包括但不限于未点击确认本协议而购买了本网站相关服务的，均表示用户与信拾贝已就本协议达成一致并同意接受本协议的全部约定内容。</p>
        <p>5.2本协议自用户确认之时即刻生效。</p>
        <p>5.3本协议服务期限至信拾贝向用户提供的服务结束之日止，如用户与信拾贝另行签订了有关本网站服务的协议，且另行签订的协议与本协议有冲突的，以另行签订的协议为准。</p>
        <p>6.管辖及法律适用</p>
        <p>6.1本协议签订地为中华人民共和国杭州市江干区。</p>
        <p>6.2本协议的成立、生效、履行、解释及纠纷解决，适用中华人民共和国法律。</p>
        <p>6.3若您和信拾贝之间发生任何纠纷或争议，您可以与信拾贝友好协商解决；协商不成的，您同意将纠纷或争议提交本协议签订地有管辖权的人民法院管辖。</p>
        <p>6.4本协议所有条款的标题仅为阅读方便，本身并无实际涵义，不能作为本协议涵义解释的依据。</p>
        <p>6.5本协议条款无论因何种原因部分无效或不可执行，其余条款仍有效，对双方具有约束力。</p>
        <p>7.合理免责条款</p>
        <p>7.1商标</p>
        <p>7.1.1国内商标注册申请存在3至6个月的盲查期，该期间内您的商标有可能与他人享有在先权的商标相同或构成近似从而被驳回；</p>
        <p>7.1.2当您申请的国内商标通过商标局实质审查会有三个月公告期，在此期间您的商标有可能会被第三人提出异议；</p>
        <p>7.1.3您申请的国际商标有可能因不符合相关国家的法律法规规定或其他不可控制的因素而被驳回等；</p>
        <p>7.1.4信拾贝向您提供的国内外商标查询及建议等仅供参考，不具有法律效力。</p>
        <p>7.1.5商标转让、交易业务中的出让方要保证出让的商标不存在隐瞒信息、权利瑕疵及与第三方的纠纷，否则拾贝免责。</p>
        <p>7.2专利与版权</p>
        <p>7.2.1因您对技术资料的未完全交底或因您提供费减材料不充分（若存在费减）或未及时提供导致无法进行费减造成的损失，或因技术内容缺乏新颖性、创造性、公开不充分或已有在先申请等不符合专利申请条件都可能给您造成损失；</p>
        <p>7.2.2若您未在法定期限内缴纳您的专利申请过程中和授权后所有发生的官费，有可能会造成您权利的丧失及其他损失；</p>
        <p>7.2.3因您提供的软件鉴别资料/美术或文字作品证明材料不充分或不符合相关法律的规定有可能导致您的软件著作权/作品著作权登记申请失败或造成其他损失。</p>
        <p>7.3其它服务</p>
        <p>7.3.1项目申报、游戏版号、游戏资质办理本身均存在一定的风险，有可能因为政府、政策等原因导致办理失败。</p>
        <p>7.3.2若您提供的维权/电子存证等其他服务所需的相关证明文件存在瑕疵或您未按照本网站相关页面操作要求（例：所需存证的网页应在非登录状态下进行电子存证）进行操作，有可能导致您选择的服务办理失败。</p>
        <p>7.4您应对信拾贝提供的各种服务相关法律文书及时做出回复，如因您的过错（超过期限、证据不充分等）有可能导致服务费用增加、权利丧失等。</p>
        <p>7.5服务相关法律文书的下发时间以国家/国外官方机构实际发文为准，如遇政治、战争、不可抗力或官方机构自身原因有可能造成您不能及时收到文件或导致您产生其他损失。</p>
        <p>7.6信拾贝对出现上述7.1-7.5中的任一情形不承担任何责任。</p>
        <p>8.费用支付及退款</p>
        <p>8.1您理解并同意委托信拾贝办理本网站相关服务，并承担服务所产生的相关费用。</p>
        <p>8.2信拾贝收取相关服务的费用仅指该服务顺利情况下办理完成的费用，如发生非顺利状态下程序（包含但不限于重新注册、变更、转让、许可、续展、驳回复审、补充证据、审查意见、异议、争议、答辩或其它诉讼程序等），费用另计。</p>
        <p>8.3您可以随时提出终止在本网站购买的服务，但因信拾贝提供服务已产生的费用不予退还。</p>
        <p>9.关于优惠活动</p>
        <p>9.1如遇优惠活动，具体规则请以本网站优惠信息为准。</p>
        <p>10.附则</p>
        <p>本协议内容包括以上条款及信拾贝已经发布的或将来可能发布的各类协议。视为本协议不可分割的一部分，具有同等法律效力。在您使用该服务时，已经阅读、理解并接受本协议的全部条款及各类规则，并承诺遵守中国的各类法律规定，如有违反而导致任何法律责任，您将以自己的名义独立承担相应的法律责任。</p>
      </div>
      <div class="agree-btn">我已阅读并同意《网站服务协议》</div>
    </div>
  </div>
  <footer class="footer footer-bottom mt-20 pb-40">
    <div class='container'>
      <div class="row">
        <div class="col-lg-12 text-center">
          <p>杭州信拾贝网络科技有限公司为中国反侵权假冒创新战略联盟副理事长单位</p>
          <p class="copyright"><a style="color: #000;" href="//beian.miit.gov.cn" target="_blank">浙 ICP备 18028232号</a></p>
        </div>
      </div>
    </div>
  </footer>
</body>
</html>
