<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>
<div class="modal fade" id="warnAddOriginalModal" tabindex="-1" role="dialog" aria-labelledby="warnAddOriginalModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content" style="border-radius: 0px; height: 360px; width: 604px">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title" id="myModalLabel">
          <b>警告</b>
        </h4>
      </div>
      <div class="modal-body">
        <h1 style="text-align: center; margin-top: 45px">
          <b style="font-size: 18px; color: #FF1313;">请按节点顺序添加作品</b>
        </h1>

        <h2 style="margin-top: 30px; text-align: center;">
          <b style="font-size: 16px; color: #333333"><span class="warn-node-name" style="font-size: 16px; color: #333333"></span>节点还未添加作品，若选择继续上传，上述节点将无法再添加作品</b>
        </h2>

      </div>
      <div class="modal-footer" style="margin-top: 40px">
        <button type="button" class="btn btn-primary confirm-warn-add-original">
          <b>确认</b>
        </button>
      </div>
    </div>
  </div>
</div>