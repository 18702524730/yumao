function realnameAuth(externalUrl, realname, idcard, bankCardNo, bankReservedMobile, entName, entBankName, entBankNoDictValue, entBankPublicAccountNo, on_result) {
    $.ajax({
        url : externalUrl + "realname-auth.json",
        type : 'POST',
        data : JSON.stringify({
            realname : realname,
            idcard : idcard,
            bank_card_no : bankCardNo,
            bank_reserved_mobile : bankReservedMobile,
            ent_name : entName,
            ent_bank_name : entBankName,
            ent_bank_no_dict_value : entBankNoDictValue,
            ent_bank_public_account_no : entBankPublicAccountNo
        }),
        dataType : "json",
        contentType : 'application/json;charset=utf-8',
        cache : false,
        success : function(data) {
            return on_result(data);
        }
    });

    return false;
}

$(document).ready(function() {
    var externalUrl = $('#external-url').val();
    var returnMapping = $('#return-mapping').val();
    var userType = $('#user-type').val();

    var realnameField = $('.realname-auth-frame .realname-auth-realname');
    var idcardField = $('.realname-auth-frame .realname-auth-idcard-no');
    var bankCardNoField = $('.realname-auth-frame .realname-auth-bank-card-no');
    var bankReservedMobileField = $('.realname-auth-frame .realname-auth-bank-reserved-mobile');
    var entNameField = $('.realname-auth-frame .realname-auth-ent-name');
    var entBankNameField = $('.realname-auth-frame .realname-auth-ent-bank-name');
    var entBankNoDictValueField = $('.realname-auth-frame .realname-auth-ent-bank-no');
    var entBankPublicAccountNoField = $('.realname-auth-frame .realname-auth-ent-bank-public-account-no');
    var idPictureField = $('.realname-auth-frame .realname-auth-id-picture');

    idPictureField.fileinput({
        minFileSize : 0,
        maxFileSize : 0,
        showUpload : false,
        showCaption : true,
        language : 'zh',
        showPreview : false,
        layoutTemplates : {
            actionUpload : ''
        },
        dropZoneEnabled : false,
        uploadUrl : externalUrl + "realname-auth-qy.json",
        allowedFileTypes : [ 'image' ],
        allowedFileExtensions : [ "png", "jpg", "jpeg", "gif", "bmp" ],
        elErrorContainer : '#kartik-file-errors',
        uploadExtraData : function() {
            return {
                ent_name : entNameField.val(),
                ent_bank_name : entBankNameField.val(),
                ent_bank_no_dict_value : entBankNoDictValueField.selectpicker('val'),
                ent_bank_public_account_no : entBankPublicAccountNoField.val()
            };
        }
    }).on('filepreupload', function(event, data, previewId, index) {
        return false;
    }).on('fileuploaded', function(event, data, previewId, index) {
        var responseData = data.response;
        if (responseData.code === 0) {
            location.href = externalUrl + "realname-auth.html?return_mapping=" + encodeURIComponent(encodeURIComponent(returnMapping));
            return false;
        }
        alert(responseData.message);
        return false;
    });

    $('.realname-auth-frame .submit-realname-auth').click(function() {

        // loading
        var $btn = $(this).button('');
        // var $btn = $(this).button('loading');

        var realname = "";
        var idcard = "";
        var bankCardNo = "";
        var bankReservedMobile = "";
        var entName = "";
        var entBankName = "";
        var entBankNoDictValue = "";
        var entBankPublicAccountNo = "";

        if (userType === "1") {
            realname = realnameField.val();
            idcard = idcardField.val();
            bankCardNo = bankCardNoField.val();
            bankReservedMobile = bankReservedMobileField.val();
        } else {
            idPictureField.fileinput('upload');
            idPictureField.fileinput('disable');
            // 解开loading
            $btn.button('reset')
            return false;
        }

        realnameAuth(externalUrl, realname, idcard, bankCardNo, bankReservedMobile, entName, entBankName, entBankNoDictValue, entBankPublicAccountNo, function(data) {
            if (data.code === 0) {
                location.href = externalUrl + "realname-auth.html?return_mapping=" + encodeURIComponent(encodeURIComponent(returnMapping));
                return false;
            }
            alert(data.message);
            // 解开loading
            $btn.button('reset');
            return false;
        });
        return false;
    });
});