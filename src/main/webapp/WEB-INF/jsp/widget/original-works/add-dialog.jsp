<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>

<div class="modal fade" id="add-original-works-modal" tabindex="-1" role="dialog" data-keyboard="false" data-backdrop="static" aria-labelledby="login-modal-label">
    <div class="modal-dialog" role="document">
        <div class="modal-content add-original-works-dialog">
            <div class="modal-header modal-top-title" style="background-color: #f7f7f7">
                <div class="no-color-link " data-dismiss="modal" aria-label="Close" style="float: right; margin-top: 7px;">
                    <!--                     <span aria-hidden="true">&times;</span> -->
                    <img alt="" src="${EXTERNAL_URL }images/close.png" style="cursor: pointer;display: block" data-dismiss="modal" >
                </div>
                <h4 class="modal-title" id="login-modal-label" style="color:#333333;font-weight: bold">添加原创作品</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal pb-20" style="padding-right: 45px;">
                    <div class="form-group mt-30">
                        <label for="dialog-add-original-works-name" class="col-lg-3 control-label" style="font-weight: normal;font-size: 14px;padding-right: 0;">作品名称：</label>
                        <div class="col-lg-9 pr-20">
                            <input id="dialog-add-original-works-name" type="text" class="form-control add-original-works-name" placeholder="例：花边设计图（实物图）">
                        </div>
                    </div>
                    <div class="form-group mt-30">
                        <label for="dialog-add-original-works-category" class="col-lg-3 control-label" style="font-weight: normal;font-size: 14px;padding-right: 0;">作品种类：</label>
                        <div class="col-lg-9 pr-20">
                            <select id="dialog-add-original-works-category" class="form-control add-original-works-category" style="color: #979797;">
                                <option value="-1" selected="selected">请选择作品类别</option>
                                <option value="1">口述作品</option>
                                <option value="2">文字作品</option>
                                <option value="3">音乐作品</option>
                                <option value="4">戏剧作品</option>
                                <option value="5">曲艺作品</option>
                                <option value="6">舞蹈作品</option>
                                <option value="7">美术作品</option>
                                <option value="8">摄影作品</option>
                                <option value="9">电影和类似摄制电影方式创作的作品</option>
                                <option value="10">图形</option>
                                <option value="11">建筑作品</option>
                                <option value="12">计算机软件</option>
                                <option value="15">杂技艺术作品</option>
                                <option value="16">模型</option>
                                <option value="13">其他</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group mt-30">
                        <label for="dialog-add-original-works-creation-time" class="col-lg-3 control-label" style="font-weight: normal;font-size: 14px;padding-right: 0;">创作时间：</label>
                        <div class="col-lg-9 pr-20">
                            <input id="dialog-add-original-works-creation-time" type="text" class="form-control form_datetime add-original-works-creation-time" style="background-color: #ffffff;" placeholder="请选择创作时间" readonly>
                        </div>
                    </div>
                    <div class="form-group mt-30">
                        <label for="dialog-add-original-works-original-file" class="col-lg-3 control-label" style="font-weight: normal;font-size: 14px;padding-right: 0;">作品文件：</label>
                        <div class="col-lg-9 pr-20">
                            <div class="filebox">
                            <input id="dialog-add-original-works-original-file" type="file" class="form-control add-original-works-original-file"></div>
                            <div id="kartik-file-errors"></div>
                            <div class="mt-10" style="color: #979797;">上传文件大小不得超过10MB；支持格式 png/jpg/jpeg/gif/text/pdf/docx/doc/xls/xlsx/ppt/mp4/avi/mp3/wma</div>
                        </div>
                    </div>
                    <div class="form-group mt-40">
                        <div class="col-lg-offset-4 col-lg-4">
                            <button type="button" class="submit-save mybluebtn" style="border: none">保存</button>
                        </div>
                    </div>
                    <div style="text-align: center;padding-left: 30px;font-size: 12px;color: #979797;padding-top: 20px;padding-bottom: 50px;">上传的版权归用户自己所有，未经用户授权，平台绝对不会私自使用用户上传的作品</div>
                </form>
            </div>
        </div>
    </div>
</div>
