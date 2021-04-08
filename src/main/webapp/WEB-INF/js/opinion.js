function feedback(externalUrl, opinionTitle, opinionContent, opinionEmail, opinionTelphone,on_result) {
    $.ajax({
        url : externalUrl + "addOpinion.json",
        type : 'POST',
        data : JSON.stringify({
            opinionTitle : opinionTitle,
            opinionContent : opinionContent,
            opinionEmail : opinionEmail,
            opinionTelphone : opinionTelphone
        }),
        dataType : 'json',
        contentType : 'application/json;charset=utf-8',
        cache : false,
        success : function(data) {
            return on_result(data);
        },
        /*error : function(jqXHR, textStatus, errorThrown) {
            alert(textStatus);
            alert(errorThrown);
        }*/
    });

};

/* 提交意见反馈 */
$(document).ready(function() {
    var externalUrl = $("#external-url").val();
    var returnMapping = $('#return-mapping').val();
    
    $(".submit-opinion").click(function() {
        //loading
        var $btn = $(this).button('');
        // var $btn = $(this).button('loading');
        // 得到每个对象
        var opinionTitleField = $("#opinion-form .opinion-title");
        var opinionContentField = $("#opinion-form .opinion-content");
        var opinionEmailField = $("#opinion-form .opinion-email");
        var opinionTelphoneField = $("#opinion-form .opinion-telphone");
        // 得到每个对象的值
        var opinionTitle =opinionTitleField.val();
        var opinionContent = opinionContentField.val();
        var opinionEmail =opinionEmailField.val();
        var opinionTelphone =opinionTelphoneField.val();
        
        // 调用ajax提交
        feedback(externalUrl, opinionTitle, opinionContent, opinionEmail, opinionTelphone, function(data) {
            
            if(data.code === 0){
                window.location.href = externalUrl + returnMapping;
                return false;
            }
            commonModal("提交失败",data.message);
            //解除loading
            $btn.button('reset');
            return false;
        });
        
    });
});
