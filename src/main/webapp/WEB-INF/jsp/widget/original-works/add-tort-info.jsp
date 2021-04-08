<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>

<div class="modal fade" id="add-tort-info-modal" tabindex="-1" role="dialog" data-keyboard="false" data-backdrop="static" aria-labelledby="login-modal-label">
  <div class="modal-dialog" role="document" style="width: 800px;">
    <div class="modal-content add-tort-info-dialog">
      <input type="hidden" id="tort-info-id">
      <div class="modal-header modal-top-title">
        <button type="button" class="btn btn-link no-color-link close" data-dismiss="modal" aria-label="Close">
          <!--                     <span aria-hidden="true">&times;</span> -->
          <img alt="" src="${EXTERNAL_URL }images/close.png" style="cursor: pointer; height: 40px; padding-bottom: 12px;" data-dismiss="modal">
        </button>
        <h4 class="modal-title" id="login-modal-label">关联作品</h4>
      </div>
      <div class="add-tort-info-one" style="margin: 0px 100px 0px 100px; height: 700px; max-height: 800px;">
        <form class="form-horizontal pb-20">
          <div class="form-group mt-30" style="margin-top: 20px;">
            <h4>请选择被侵权相关作品：</h4>
          </div>
          <div class="form-group mt-30" style="margin-top: 20px;">
            <div class="input-group">
              <input name="link" type="text" class="form-control link-input" placeholder="请输入作品名称" maxlength="200" id="search-original-works-name"> <span
                class="input-group-btn">
                <button class="btn btn-primary search-original-works-btn" type="button">查询</button>
              </span>
            </div>
          </div>
          <div class="form-group mt-30">
            <table id="tort-info-table-1"></table>
          </div>
          <!-- <div class="form-group mt-30">
            <label for="dialog-add-original-works-name" class="col-lg-3 control-label">作品名称：</label>
            <div class="col-lg-9 pr-20">
              <input id="dialog-add-original-works-name" type="text" class="form-control add-original-works-name" placeholder="请填写作品名称">
            </div>
          </div> -->
          <div class="form-group mt-40">
            <div class="col-lg-offset-4 col-lg-4">
              <button type="button" class="btn btn-link color-btn form-control next-btn">下一步</button>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-body add-tort-info-two" style="margin: 0px 100px 0px 100px; display: none;">
        <form class="">
          <div class="form-group mt-30">
            <h5 style="width: 100px; float: left;">商品链接：</h5>
            <input class="form-control" type="text" id="goods-url" placeholder="请输入侵权商品链接">
          </div>
          <div class="form-group mt-30">
            <h5>相关作品：</h5>
            <table id="tort-info-table-2"></table>
          </div>
          <div class="row">
            <div class="col-xs-6" align="center">
              <button type="button" class="btn btn-default btn-last-step" style="width: 150px">上一步</button>
            </div>
            <div class="col-xs-6" align="center">
              <button type="button" class="btn btn-primary btn-submit-tort mybluebtn" style="width: 150px">提交</button>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-body add-tort-info-success" style="margin: 0px 100px 0px 100px;" align="center">
        <div class="row" style="margin-top: 100px;">
          <img src="${EXTERNAL_URL }images/tort-info-success.png">
        </div>
        <div class="row" style="margin-top: 30px;">
          <p>
            <b style="font-size: 15px;">维权提交成功</b>
          </p>
        </div>
        <div class="row" style="font-weight: 100">
          <p>预猫禁止恶意投诉他人的行为</p>
          <p>如若发现，将保留追究法律责任的权利.</p>
          <span></span>
        </div>
        <div class="row" style="margin-top: 50px;">
          <button type="button" class="btn btn-link color-btn form-control go-on-btn" style="width: 260px;">继续添加</button>
        </div>
      </div>
    </div>
  </div>
</div>
