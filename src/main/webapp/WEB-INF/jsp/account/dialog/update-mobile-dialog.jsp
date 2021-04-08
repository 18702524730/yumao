<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>
<div class="modal fade" id="updateMobilModal" tabindex="-1" role="dialog" aria-labelledby="updateMobilModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content" style="border-radius: 0px; height: 394px; width: 604px">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true" style="float: left;padding-left:5px ">&times;</span>
        </button>
        <h4 class="modal-title" id="myModalLabel">
          <b>修改手机号</b>
        </h4>
      </div>
      <div class="modal-body">
        <div class="text-center" style="margin-top: 40px">
          <div class="form-group col-sm-12" style="margin-bottom: 30px">
            <label for="update-new-mobile" class="col-sm-4 control-label">新手机号码：</label>
            <div class="col-sm-6  real-pad">
              <input type="text" class="form-control" id="update-new-mobile" placeholder="请输入新手机号码">
            </div>
          </div>
          <div class="form-group col-sm-12" style="margin-bottom: 30px">
            <label for="update-mobile-verifycode" class="col-sm-4 control-label">验证码：</label>
            <div class="col-sm-8 to-update-mobile-div real-pad">
              <input type="text" class="form-control" id="update-mobile-verifycode" placeholder="请输入手机验证码" style="float: left; width: 180px">
              <button class="get-verifycode" style="position: relative; width: 130px; height: 40px; color: #fff; background: #7FC4FD">发送验证码</button>
            </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="submit-update-mobile mybluebtn">
          <b>提交</b>
        </button>
      </div>
    </div>
  </div>
</div>