function validate(externalUrl, vcode, amount, on_result) {
    $.ajax({
        url : externalUrl + "realname-auth-qy-validate.json",
        type : 'POST',
        data : JSON.stringify({
            vcode : vcode,
            amount : amount
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

    $('.realname-auth-validate-frame .submit-realname-auth-validate').click(function() {
        var vcodeField = $('.realname-auth-validate-frame .realname-auth-validate-vcode');
        var amountField = $('.realname-auth-validate-frame .realname-auth-validate-amount');

        var vcode = vcodeField.val();
        var amount = amountField.val();

        validate(externalUrl, vcode, amount, function(data) {
            if (data.code === 0) {
                location.href = externalUrl + "realname-auth.html?return_mapping=" + encodeURIComponent(encodeURIComponent(returnMapping));
                return false;
            }
            alert(data.message);
            return false;
        });
    });
});