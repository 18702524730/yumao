<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>

<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="modal fade" id="open-service-payment" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title" id="myModalLabel">续费</h4>
      </div>
      <div class="modal-body">
        <div class="container-fluid1 page-content">
          <div class="container1 open-service-frame">
            <div class="row container-page-content">
              <div class="col-lg-12 container-page-content-column">
                <div class="tab-content mt-30">
                  <div role="tabpanel" class="tab-pane" id="payment">
                    <div class="row">
                      <div class="col-lg-12 text-center">
                        <table id="service-table"></table>
                      </div>
                    </div>
                    <div class="row mt-50">
                      <div class="col-lg-8 col-lg-offset-2 text-center">
                        <span>总计：<label id="all-price">0.00</label> 元
                        </span>
                      </div>
                      <div class="col-lg-8 col-lg-offset-2 text-center">
                        <span>支付方式：支付宝</span>
                      </div>
                    </div>
                    <div class="row mt-30">
                      <div class="col-lg-12 text-center">
                        <span><input class="payment-agreement" type="checkbox" value="1" aria-label="..."> 我已阅读并同意<a class="color-link" href="javascript:void(0);"
                          data-toggle="modal" data-target="#payment-agreement-modal">《预猫年度服务协议》</a></span>
                      </div>
                    </div>
                  </div>
                  <div role="tabpanel" class="tab-pane" id="payment-result">
                    <div class="row mt-80">
                      <div class="col-lg-12 text-center">
                        <img alt="" src="${EXTERNAL_URL }images/right.png" style="width: 100px;">
                      </div>
                    </div>
                    <div class="row title mt-30">
                      <div class="col-lg-12 text-center">
                        <span>支付成功</span>
                      </div>
                    </div>
                    <div class="row content mt-10">
                      <div class="col-lg-12 text-center">
                        <span>恭喜您成功开通原创保镖服务，请尽快将您需要保护的原创内容，上传至系统中，我们会第一时间为您提供服务。</span>
                      </div>
                    </div>
                    <div class="row mt-100">
                      <div class="col-lg-12 text-center">
                        <button type="button" class="btn btn-link color-btn btn-step2">管理原创内容</button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-link color-btn btn-step1">确认支付</button>
      </div>
    </div>
  </div>
</div>