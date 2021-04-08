$(document).ready(function() {
    var externalUrl = $('#external-url').val();
    var name = $('#name').val();
    var nameField = $('.add-original-works-dialog .add-original-works-name');
    var categoryField = $('.add-original-works-dialog .add-original-works-category');
    var creationTimeField = $('.add-original-works-dialog .add-original-works-creation-time');
    var originalFileField = $('.add-original-works-dialog .add-original-works-original-file');

    creationTimeField.datetimepicker({
        format : 'yyyy-mm-dd',
        todayBtn : true,
        language : 'zh-CN',
        minView : 2,
        maxView : 4,
        autoclose : true,
        endDate : new Date()
    });

    originalFileField.fileinput({
        minFileSize : 1,
        maxFileSize : 10240,
        showUpload : false,
        showCaption : true,
        language : 'zh',
        showPreview : false,
        layoutTemplates : {
            actionUpload : ''
        },
        dropZoneEnabled : false,
        uploadUrl : externalUrl + "original-works/add.json",
        allowedFileTypes : [ 'image', 'html', 'text', 'video', 'audio', 'flash', 'object' ],
        allowedFileExtensions : [ "png", "jpg", "jpeg", "gif", "text", "pdf", "docx", "doc", "xls", "xlsx", "ppt", "mp4", "avi", "mp3", "wma" ],
        elErrorContainer : '#kartik-file-errors',
        uploadExtraData : function() {
            return {
                name : nameField.val(),
                category : categoryField.val(),
                creation_time : creationTimeField.val()
            };
        }
    }).on('fileuploaded', function(event, data, previewId, index) {
        var responseData = data.response;
        if (responseData.code === 0) {
            // 关闭弹出层
            $('#add-original-works-modal .close').click();
            $('#original-works-table').bootstrapTable('refresh');
            // 上传成功后弹出‘成功对话框’
            var nameFieldVal = nameField.val();
            var type = $("#dialog-add-original-works-category option:selected").text();
            addDialogSuccess(nameFieldVal, type);
            return false;
        }
        commonModal("上传失败", responseData.message);
        return false;
    }).on('filebatchuploadsuccess', function(event, data) {
        var responseData = data.response;
        if (responseData.code != 0) {
            commonModal("上传失败", responseData.message);
        }
    });

    $('.add-original-works-dialog .submit-save').click(function() {
        // loading
        var $btn = $(this).button('loading');
        // var $btn = $(this).button('')
        // var _this = $(this);
        var category = categoryField.val();
        if (nameField.val() === "" || nameField.val() === null) {
            commonModal("上传失败", "请输入作品名称");
            $btn.button('reset');
            return false;
        }

        if ('-1' === category) {
            commonModal("上传失败", "请选择作品类别");
            $btn.button('reset');
            return false;
        }

        if (creationTimeField.val() === "" || creationTimeField.val() === null) {
            commonModal("上传失败", "请选择日期");
            $btn.button('reset');
            return false;
        }

        if (originalFileField.fileinput('getFilesCount') === 0) {
            commonModal("上传失败", "请选择文件");
            $btn.button('reset');
            return false;
        }

        // 上传方法
        originalFileField.fileinput('upload');
        originalFileField.fileinput('disable');
        $btn.button('reset');

        return false;
    });

    function addDialogSuccess(nameFieldVal, type) {
        var mydate = new Date();
        var str = "" + mydate.getFullYear() + "-";
        str += (mydate.getMonth() + 1) + "-";
        str += mydate.getDate() + " ";
        str += mydate.getHours() + ":";
        str += mydate.getMinutes() + ":";
        str += mydate.getSeconds();
        $(".custody-date").text(str);
        $(".works-name").text(nameFieldVal);
        $(".works-type").text(type);
        $(".work-person").text(name);
        $('#add-original-works-modal').modal('hide');
        $('#add-original-success-modal').modal('show');
    }

});