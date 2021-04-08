<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>

<div class="modal fade" id="register-modal" tabindex="-1" role="dialog" data-keyboard="false" data-backdrop="static" aria-labelledby="register-modal-label">
    <div class="modal-dialog" role="document">
        <div class="modal-content register-dialog">
            <div class="modal-header" style="background-color: #4F70A6;">
                <button type="button" class="btn btn-link no-color-link close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="register-modal-label" style="color: #ffffff;">用户注册</h4>
            </div>
            <div class="modal-body text-center" style="background-color: #ffffff; padding-left: 30px; padding-right: 30px;">
                <form class="form-horizontal pb-20">
                    <div class="form-group">
                        <div class="radio col-lg-6 text-right">
                            <label> <input id="dialog-register-user-type-personal" name="dialog-register-user-type" type="radio" value="1" checked> 个人用户
                            </label>
                        </div>
                        <div class="radio col-lg-6 text-left">
                            <label> <input id="dialog-register-user-type-enterprise" name="dialog-register-user-type" type="radio" value="2"> 企业用户
                            </label>
                        </div>
                    </div>
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane active" id="dialog-grpart">
                            <div class="form-group mt-30">
                                <label for="dialog-register-gr-username" class="col-lg-3 control-label">用户名：</label>
                                <div class="col-lg-9 pr-20">
                                    <input id="dialog-register-gr-username" type="text" class="form-control register-username" placeholder="请填写用户名" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group mt-30">
                                <label for="dialog-register-gr-login-password" class="col-lg-3 control-label">登录密码：</label>
                                <div class="col-lg-9">
                                    <input id="dialog-register-gr-login-password" type="password" class="form-control register-login-password" placeholder="请填写登录密码" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group mt-30">
                                <label for="dialog-register-gr-affirm-login-password" class="col-lg-3 control-label">确认密码：</label>
                                <div class="col-lg-9">
                                    <input id="dialog-register-gr-affirm-login-password" type="password" class="form-control register-affirm-login-password" placeholder="请再次填写登录密码" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group mt-30">
                                <label for="dialog-register-gr-mobile" class="col-lg-3 control-label">手机号码：</label>
                                <div class="col-lg-9 pr-20">
                                    <input id="dialog-register-gr-mobile" type="text" class="form-control register-mobile" placeholder="请填写手机号码" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group has-feedback mt-30">
                                <label for="dialog-register-gr-mobile-verifycode" class="col-lg-3 control-label">验证码：</label>
                                <div class="col-lg-9 pr-20">
                                    <div class="input-group">
                                        <input id="dialog-register-gr-mobile-verifycode" type="text" class="form-control register-mobile-verifycode" placeholder="请填写验证码" autocomplete="off"> <span class="input-group-btn">
                                            <button class="btn btn-link color-btn" get-verifycode" type="button">获取验证码</button>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div role="tabpanel" class="tab-pane" id="dialog-qypart">
                            <div class="form-group mt-30">
                                <label for="dialog-register-qy-username" class="col-lg-3 control-label">用户名：</label>
                                <div class="col-lg-9 pr-20">
                                    <input id="dialog-register-qy-username" type="text" class="form-control register-username" placeholder="请填写用户名" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group mt-30">
                                <label for="dialog-register-qy-login-password" class="col-lg-3 control-label">登录密码：</label>
                                <div class="col-lg-9">
                                    <input id="dialog-register-qy-login-password" type="password" class="form-control register-login-password" placeholder="请填写登录密码" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group mt-30">
                                <label for="dialog-register-qy-affirm-login-password" class="col-lg-3 control-label">确认密码：</label>
                                <div class="col-lg-9">
                                    <input id="dialog-register-qy-affirm-login-password" type="password" class="form-control register-affirm-login-password" placeholder="请再次填写登录密码" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group mt-30">
                                <label for="dialog-register-qy-ent-uni-credit-code" class="col-lg-3 control-label">统一社会信用代码：</label>
                                <div class="col-lg-9 pr-20">
                                    <input id="dialog-register-qy-ent-uni-credit-code" type="text" class="form-control register-ent-uni-credit-code" placeholder="请填写统一社会信用代码" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group mt-30">
                                <label for="dialog-register-qy-ent-org-code" class="col-lg-3 control-label">组织机构代码：</label>
                                <div class="col-lg-9 pr-20">
                                    <input id="dialog-register-qy-ent-org-code" type="text" class="form-control register-ent-org-code" placeholder="请填写组织机构代码" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group mt-30">
                                <label for="dialog-register-qy-ent-contacts-realname" class="col-lg-3 control-label">企业联系人真实姓名：</label>
                                <div class="col-lg-9 pr-20">
                                    <input id="dialog-register-qy-ent-contacts-realname" type="text" class="form-control register-ent-contacts-realname" placeholder="请填写企业联系人真实姓名" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group mt-30">
                                <label for="dialog-register-qy-ent-contacts-mobile" class="col-lg-3 control-label">企业联系人手机号码：</label>
                                <div class="col-lg-9 pr-20">
                                    <input id="dialog-register-qy-ent-contacts-mobile" type="text" class="form-control register-ent-contacts-mobile" placeholder="请填写企业联系人手机号码" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group has-feedback mt-30">
                                <label for="dialog-register-qy-mobile-verifycode" class="col-lg-3 control-label">验证码：</label>
                                <div class="col-lg-9 pr-20">
                                    <div class="input-group">
                                        <input id="dialog-register-qy-mobile-verifycode" type="text" class="form-control register-mobile-verifycode" placeholder="请填写验证码" autocomplete="off"> <span class="input-group-btn">
                                            <button class="btn btn-link color-btn get-verifycode" type="button">获取验证码</button>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group mt-40">
                        <div class="col-lg-12">
                            <span><input class="register-agreement" type="checkbox" value="1" aria-label="..."> 我已阅读并同意<a class="btn btn-link color-link" target="_blank" href="${EXTERNAL_URL }agreement/1.html">《网站服务协议》</a></span>
                        </div>
                    </div>
                    <div class="form-group mt-40">
                        <button type="button" class="btn btn-link color-btn form-control submit-register">注册</button>
                    </div>
                </form>
            </div>
            <div class="modal-footer" style="background-color: #F4F5F7;">
                <div class="row">
                    <div class="col-lg-12 text-center">
                        <span>已有账号？</span>
                        <button type="button" class="btn btn-link color-btn to-login" style="color: #D9534F;">登录</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
