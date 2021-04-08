<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>

<div class="modal fade" id="create-statement-step-1" tabindex="-1" role="dialog" data-keyboard="false" data-backdrop="static" aria-labelledby="register-agreement-modal-label">
    <div class="modal-dialog" style="max-width: 800px;" role="document">
        <div class="modal-content">
            <div class="modal-header" style="background-color: #4F70A6;">
                <button type="button" class="btn btn-link no-color-link close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" style="color: #ffffff;">生成声明函</h4>
            </div>
            <div class="modal-body" style="background-color: #ffffff; padding-left: 30px; padding-right: 30px;">
                <form role="form">
                    <div class="form-group mt-50">
                        <span>请填写准确的店铺名称</span>
                    </div>
                    <div class="form-group mt-20">
                        <input type="text" class="form-control" id="stop-name" name="stop-name" placeholder="请填写准确的店铺名称（最多10个字）" maxlength="10">
                    </div>
                    <div class="form-group mt-50">
                        <span>请填写准确的店铺链接</span>
                    </div>
                    <div class="form-group mt-20">
                        <input type="text" class="form-control" id="shop-url" name="shop-url" placeholder="请填写准确的店铺链接">
                    </div>
                    <div class="form-group mt-20">
                        <span>原创保护公证保管声明函只可生成一次，请您确保您填写的店铺名称和链接的准确性。</span>
                    </div>
                    <div class="form-group mt-50 text-center" style="margin-bottom: 40px;">
                        <button type="button" class="btn btn-default btn-go-next">生成声明函</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="create-statement-step-2" tabindex="-1" role="dialog" data-keyboard="false" data-backdrop="static" aria-labelledby="register-agreement-modal-label">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header" style="background-color: #4F70A6;">
                <button type="button" class="btn btn-link no-color-link close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" style="color: #ffffff;">生成声明函</h4>
            </div>
            <div class="modal-body" style="background-color: #ffffff; padding-left: 30px; padding-right: 30px;">
                <form role="form">
                    <div class="form-group mt-30 text-center">
                        <img src="${EXTERNAL_URL }images/tort-info-success.png">
                    </div>
                    <div class="form-group text-center">
                        <span style="font-size: 40px;"><strong>生成成功</strong></span>
                    </div>
                    <div class="form-group mt-40 text-center" style="margin-bottom: 40px;">
                        <a id="download-statement" class="btn" href="${EXTERNAL_URL }statement/download.html" type="button" style="">下载声明函</a>
<%--                        <button type="button" class="btn btn-default btn-go-download">下载声明函</button>--%>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

