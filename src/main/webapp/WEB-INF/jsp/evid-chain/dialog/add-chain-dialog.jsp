<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>
<div class="modal fade" id="addChainModal" tabindex="-1" role="dialog" aria-labelledby="addChainModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content" style="border-radius: 0px; height: 300px; width: 604px">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title" id="myModalTitleLabel">
          <b>添加证据链</b>
        </h4>
      </div>
      <div class="modal-body">
        <div class="text-center" style="margin-top: 40px">
          <div class="form-group" style="margin-bottom: 30px">
            <label for="add-chain-name" class="col-xs-4 control-label">证据链名称：</label>
            <div class="col-xs-6  real-pad">
              <input type="text" class="form-control" id="add-chain-name" placeholder="请输入证据链名称">
            </div>
          </div>
          <div class="form-group" style="margin-bottom: 30px">
            <label for="inputPassword3" class="col-xs-4 control-label">证据链分类：</label>
            <div class="col-xs-6 real-pad">
              <p class="form-control-static" style="float: left; margin-right: 30px;">服装</p>
            </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary add-evid-chain">
          <b>确认添加</b>
        </button>
      </div>
    </div>
  </div>
</div>