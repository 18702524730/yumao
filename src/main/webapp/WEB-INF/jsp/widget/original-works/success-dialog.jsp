<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>

<div class="modal fade" id="add-original-success-modal" tabindex="-1" role="dialog" data-keyboard="false" data-backdrop="static" aria-labelledby="login-modal-label">
    <div class="modal-dialog" role="document">
        <div class="modal-content add-original-works-dialog">
            <div class="modal-header modal-top-title">
                <button type="button" class="btn btn-link no-color-link close" data-dismiss="modal" aria-label="Close" style="opacity: 1">
<!--                     <span aria-hidden="true">&times;</span> -->
<img alt="" src="${EXTERNAL_URL }images/close.png" style="cursor: pointer;" data-dismiss="modal">
                </button>
                <h4 class="modal-title" id="login-modal-label">添加原创内容</h4>
            </div>
            <div class="modal-body">
<%--                <div class="row">--%>
<%--                    <div class="col-md-8 col-md-offset-2" style="margin-top:20px;">--%>
<%--                        <div style="width: 370px; border-radius:10px; color: #666; line-height: 42px; letter-spacing: 2px;background-color: #F8F8F8;text-align: right;font-size: 14px;" >--%>
<%--                            <p style="margin:0px;"><span style="display: inline-block;width:113px;text-align: center;border-right: 2px solid #fff;">提交时间</span><span class="custody-date" style="display:inline-block;width:255px;text-align: center"></span></p>--%>
<%--                            <p style="margin:0px; background-color: #E7E7E7"><span style="display: inline-block;width:113px;text-align: center;border-right: 2px solid #fff;">作品名称</span><span class="works-name" style="display:inline-block;width:255px;text-align: center"></span></p>--%>
<%--                            <p style="margin:0px;"><span style="display: inline-block;width:113px;text-align: center;border-right: 2px solid #fff;">作品类型</span><span class="works-type" style="display:inline-block;width:255px;text-align: center"></span></p>--%>
<%--                        </div>--%>
<%--                    </div>--%>

<%--                </div>--%>
                <div class="row" style="margin-top:16px; margin-bottom: 32px" >
                    <div class="col-md-8 col-md-offset-2" align="center" style="" >
                        <div class="col-md-12" style="margin: auto;">
                            <img src="${EXTERNAL_URL }images/tort-info-success.png" style="width:86px;height: 86px;margin-bottom: 27px;">
                            <p style="font-size: 16px;color:#333;">
                                <span style="margin-bottom: 10px;font-size: 17px;">文件提交成功</span><br>
                                <span style="font-size: 17px;">您的作品已被预猫保护</span>
                            </p>
                        </div>
                        <!-- <div class="col-md-8" align="center" style="width: auto;height: 46px;">
                            
                        </div> -->
                    </div>
                    
                </div>


                <div class="row"  style="margin-bottom: 25px">
                    <div style="text-align: center;">
<%--                        <button type="button" class="btn btn-primary myclosebtn" style="padding: 7px 25px;" data-dismiss="modal">关闭</button>--%>
                        <input type="button" value="继续添加" class="mybluebtn add-original-works" data-dismiss="modal">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-8 col-md-offset-2" align="center" style="font-weight: normal;letter-spacing: 0;color: #909090;">
                        <p style="font-size:14px; margin-bottom: 12px;">后续作品如若被侵权，预猫将替您维权！</p>
                        <p style="font-size: 12px;">怕被侵权，就上预猫！</p>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
