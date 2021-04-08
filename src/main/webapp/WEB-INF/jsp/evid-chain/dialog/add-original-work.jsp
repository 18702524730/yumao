<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>
<div class="modal fade" id="addOriginWorksModal" tabindex="-1" role="dialog" aria-labelledby="addOriginWorksModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content" style="border-radius: 0px;width: 604px">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <div class="modal-title" id="myModalTitleLabel">
          <h4>关联原创内容</h4>
        </div>
      </div>
      <div class="modal-body">
        <div class="row">
          <label for="add-original-works-name" class="col-sm-4 control-label" style="text-align:right;">名称：</label>
          <div class="col-sm-6 real-pad">
            <input type="text" class="form-control" id="add-original-works-name" placeholder="请输入作品名称">
          </div>
          <button class="btn btn-primary search-add-original-works-name" type="button">查询</button>
        </div>
        <table id="add-original-works-table"></table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary add-original-works-submit">
          <b>确认添加</b>
        </button>
      </div>
    </div>
  </div>
</div>