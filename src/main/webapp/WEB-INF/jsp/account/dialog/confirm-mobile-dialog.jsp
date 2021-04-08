<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>
<div class="modal fade" id="confirmMobileModal" tabindex="-1" role="dialog" aria-labelledby="confirmMobileModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content" style="border-radius: 0px; height: 350px; width: 604px">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title" id="myModalLabel">
          <b>修改手机号</b>
        </h4>
      </div>
      <div class="modal-body">
        <div class="text-center" style="margin-top: 40px">
          <div class="form-group col-sm-12" style="margin-bottom: 30px">
            <label for="inputEmail3" class="col-sm-4 control-label">原手机号码：</label>
            <div class="col-sm-6 real-pad confrim-mobile-div">
              <p class="form-control-static" style="float: left; margin-right: 30px;">${user.mobileForDisplay }</p>
                <button class="get-verifycode" style="background: #F7F7F7; font-size: 14px; color: #4D72FE; width: 153px; height: 35px; border-radius: 18px; text-align: center; line-height: 35px">点击发送验证码</button>
<%--              <div class="get-verifycode" style="display:inline-block;cursor:pointer;background: #F7F7F7; font-size: 14px; color: #4D72FE; width: 153px; height: 35px; border-radius: 18px; text-align: center; line-height: 35px">点击发送验证码</div>--%>
            </div>
          </div>
          <div class="form-group col-sm-12" style="margin-bottom: 30px">
            <label for="confirm-mobile-verifycode" class="col-sm-4 control-label">验证码：</label>
            <div class="col-sm-6 real-pad">
              <input type="text" class="form-control" id="confirm-mobile-verifycode" placeholder="请输入手机验证码">
            </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary next-btn" style="border: none" >
          <b>下一步</b>
        </button>
<%--        <div type="button" class="btn btn-primary next-btn" style="display: inline-block;cursor: pointer;line-height: 48px; font-size: 16px;">--%>
<%--          <b>下一步</b>--%>
<%--        </div>--%>
      </div>
    </div>
  </div>
</div>