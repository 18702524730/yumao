<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>

<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" data-keyboard="false" data-backdrop="static" aria-labelledby="login-modal-label">
    <div class="modal-dialog" style="max-width: 400px;" role="document">
        <div class="modal-content login-dialog">
            <div class="modal-header">
                <button type="button" class="btn btn-link no-color-link close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="login-modal-label">用户登录</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal pb-20">
                    <div class="form-group mt-30">
                        <div class="radio col-lg-6 text-right">
                            <label> <input id="dialog-login-user-type-personal" name="dialog-login-user-type" type="radio" value="1" checked> 个人用户
                            </label>
                        </div>
                        <div class="radio col-lg-6 text-left">
                            <label> <input id="dialog-login-user-type-enterprise" name="dialog-login-user-type" type="radio" value="2"> 企业用户
                            </label>
                        </div>
                    </div>
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane active" id="dialog-grpart">
                            <div class="form-group mt-30">
                                <label for="dialog-login-gr-account" class="col-lg-3 control-label">账号：</label>
                                <div class="col-lg-9 pr-20">
                                    <input id="dialog-login-gr-account" type="text" class="form-control login-account" placeholder="请填写用户名/手机号码/邮箱地址" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group mt-30">
                                <label for="dialog-login-gr-login-password" class="col-lg-3 control-label">登录密码：</label>
                                <div class="col-lg-9">
                                    <input id="dialog-login-gr-login-password" type="password" class="form-control login-login-password" placeholder="请填写登录密码" autocomplete="off">
                                </div>
                            </div>
                        </div>
                        <div role="tabpanel" class="tab-pane" id="dialog-qypart">
                            <div class="form-group mt-30">
                                <label for="dialog-login-qy-account" class="col-lg-3 control-label">账号：</label>
                                <div class="col-lg-9 pr-20">
                                    <input id="dialog-login-qy-account" type="text" class="form-control login-account" placeholder="请填写用户名/统一社会信用代码/组织机构代码" autocomplete="off">
                                </div>
                            </div>
                            <div class="form-group mt-30">
                                <label for="dialog-login-qy-login-password" class="col-lg-3 control-label">登录密码：</label>
                                <div class="col-lg-9">
                                    <input id="dialog-login-qy-login-password" type="password" class="form-control login-login-password" placeholder="请填写登录密码" autocomplete="off">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group mt-40">
                        <div class="col-lg-12">
                            <button type="button" class="btn btn-link color-btn form-control submit-login">登录</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <div class="row">
                    <div class="col-lg-12 text-center">
                        <span>还没有账号？</span>
                        <button type="button" class="btn btn-link color-link to-register" style="color: #D9534F;">注册</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
