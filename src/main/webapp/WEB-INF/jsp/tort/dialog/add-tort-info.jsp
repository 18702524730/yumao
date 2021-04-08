<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>

<div class="modal fade" id="add-tort-info-modal" tabindex="-1" role="dialog" data-keyboard="false" data-backdrop="static" aria-labelledby="login-modal-label">
  <div class="modal-dialog" role="document" style="width:900px;">
    <div class="modal-content add-tort-info-dialog" style="width:900px;">
      <input type="hidden" id="tort-info-id">
      <div class="modal-header modal-top-title" style="background-color: #f7f7f7;">
        <div  class=" no-color-link" data-dismiss="modal" aria-label="Close" style="float: right;margin-top: 7px;">
          <!--                     <span aria-hidden="true">&times;</span> -->
          <img alt="" src="${EXTERNAL_URL }images/close.png" style="cursor: pointer; display: block" data-dismiss="modal" >
        </div>
        <h4 class="modal-title" id="login-modal-label" style="color: #333; font-weight: 400; font-size: 18px;">关联作品</h4>
      </div>
      <div class="add-tort-info-one" style="margin: 0px 100px 0px 100px;padding-bottom: 50px;">
        <form class="form-horizontal pb-20">
          <div class="form-group mt-30" style="margin-top: 20px;">
            <h4>请选择被侵权相关作品：</h4>
          </div>
          <div class="form-group mt-30" style="margin-top: 20px;">
            <div class="input-group">
              <input name="link" type="text" class="form-control link-input" placeholder="请输入作品名称" maxlength="200" id="search-original-works-name"> <span
                class="input-group-btn">
                <div class="btn search-original-works-btn"  style="background-color: #7FC4FD;width: 118px;display: inline-block;cursor: pointer;color: #fff;font-family:'Microsoft YaHei';">搜索</div>
              </span>
            </div>
          </div>
          <div class="form-group mt-30 myselftable">
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
              <button type="button" class="next-btn mybluebtn">下一步</button>
<%--              <div  class=" next-btn" style="display: inline-block;cursor: pointer; line-height: 50px; text-align: center;font-size:16px;font-family:'Microsoft YaHei';font-weight:bold; width:217px;height:50px;background:rgba(255,255,255,1);border:1px solid rgba(112,112,112,1);opacity:1;border-radius:25px;">下一步</div>--%>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-body add-tort-info-two" style="margin: 0px 100px 0px 100px; display: none;">
        <form class="" >
          <div class="form-group mt-30">
            <h5 style="width: 100px; float: left;">商品链接：</h5>
            <input class="form-control" type="text" id="goods-url" placeholder="请输入侵权商品链接">
          </div>
          <div class="form-group mt-30 myselftable">
            <h5>相关作品：</h5>
            <table id="tort-info-table-2"></table>
          </div>
          <div class="row" style="padding-bottom: 40px;">

            <div class="col-xs-12" align="center">
              <button type="button" class=" btn-submit-tort mybluebtn" style="width: 150px">提交</button>
<%--              <div  class="btn-submit-tort" style="width: 217px;height:50px;border-radius: 25px;line-height: 50px;text-align: center;font-size: 16px;font-weight: bold;cursor:pointer;color:#333;font-family: 'Microsoft YaHei'; border: 1px solid #707070">提交</div>--%>
              <div class="col-xs-12" align="center">
<%--                <button type="button" class="btn btn-default btn-last-step" style="width: 150px">返回上一步</button>--%>
                <div  class="btn-last-step" style="width: 150px;font-size: 14px;color: #A3A6B4;margin-top: 20px;cursor: pointer">返回上一步</div>
              </div>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-body add-tort-info-success" style="margin: 0px 100px 0px 100px;" align="center">
        <div class="row" style="margin-top: 20px;">
          <img src="${EXTERNAL_URL }images/tort-info-success.png">
        </div>
        <div class="row" style="margin-top: 30px;">
          <p>
            <b style="font-size: 17px;font-weight:400;">维权提交成功</b>
          </p>
        </div>
        <div class="row" style="margin-top: 50px;padding-bottom: 20px;">
<%--          <div  class="btn" data-dismiss="modal" aria-label="Close" style="width: 100px;margin-right: 200px;cursor: pointer;background-color: #005e9e;color: #fff">关闭</div>--%>
          <div  class="btn  go-on-btn" style="width: 200px;height: 50px;background-color: #3B86FF; cursor: pointer;color: #fff;border-radius: 50px;text-align: center;padding-top: 13px;font-size: 16px;">继续添加</div>
        </div>
          <div class="row" style="font-weight: normal;margin-bottom: 30px;color:#909090;">
              <p style="font-size:14px;">预猫禁止恶意投诉他人的行为</p>
              <p style="font-size:14px;">如若发现，将保留追究法律责任的权利.</p>
              <span></span>
          </div>
      </div>
    </div>
  </div>
</div>
