<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>

<!-- 弹出层 -->
  <div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" >
  <!-- <div class="modal-dialog modal-sm" role="document" style="position: relative;top:230px;left:270px;"> -->
  <div class="modal-dialog modal-sm modal-win" role="document" style="width:400px;">
      <div class="modal-content">
        <div class="modal-header">
          <a href="javascript;;" class="close" data-dismiss="modal" aria-label="Close"><img src="${EXTERNAL_URL }images/alert-end.png" ></a>
          <%-- <button type="button" class="close" data-dismiss="modal" aria-label="Close"><img src="${EXTERNAL_URL }images/alert-end.png" ></button> --%>
          <h4 class="modal-title" id="mySmallModalLabel"></h4>
        </div>
        <div class="modal-body" id="messageHint"></div>
      </div>
    </div>
</div>