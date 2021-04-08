//-- 生成声明函对话框开始
function statementAdd(externalUrl, on_result) {
    $.ajax({
        url : externalUrl + "statement/add.json",
        type : 'POST',
        data : JSON.stringify({
            stop_name : $('#create-statement-step-1 #stop-name').val(),
            shop_url : $('#create-statement-step-1 #shop-url').val(),
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

    $('#create-statement-step-1').on('show.bs.modal', function() {
        $('#create-statement-step-1 #stop-name').val('');
    }).on('shown.bs.modal', function() {
        $('#create-statement-step-1 #stop-name').focus();
    }).on('hiden.bs.modal', function() {
    });

    $('#create-statement-step-1 .btn-go-next').click(function() {
        statementAdd(externalUrl, function(data) {
            if (data.code === 0) {
                $('#create-statement-step-1').modal('hide');
                $('#create-statement-step-2').modal('show');
                $('#create-statement-step-2 .user-id').val($('#create-statement-step-1 .user-id').val());
                return false;
            }

            if (data.code === -2) {
                // location.href = externalUrl + "login.html?return_mapping=" + encodeURIComponent(encodeURIComponent("market.html"));
                location.href = externalUrl + "login.html";
                return false;
            }

            if (data.code === -3) {
                // /service/open.html?return_mapping=" + URLDeEncodeUtility.encode("original-works/index.html", CharEncoding.UTF_8) + "&step=1
                location.href = externalUrl + "service/open.html";
                return false;
            }

            if (data.code === -4) {
                // /service/open.html?return_mapping=" + URLDeEncodeUtility.encode("original-works/index.html", CharEncoding.UTF_8) + "&step=1
                location.href = externalUrl + "/realname-auth.html";
                return false;
            }

            alert(data.message);
        });
    });

    $('#create-statement-step-2').on('shown.bs.modal', function() {
    }).on('hiden.bs.modal', function() {
    });

    $('#create-statement-step-2 .btn-go-download').click(function() {
        $('#create-statement-step-2').modal('hide');

        location.href = externalUrl + "statement/download.html";
        // location.href = externalUrl + "market.html";
    });

    $('#create-statement').click(function() {
        $('#create-statement-step-1').modal('show');
        return false;
    });
    // -- 生成声明函对话框结束
    
    $("#update-shop-url").click(function (){
        $.ajax({
            url : externalUrl + "statement/update-url.json",
            type : 'POST',
            data : JSON.stringify({
                shop_url : $('#shop-url-input').val(),
            }),
            dataType : "json",
            contentType : 'application/json;charset=utf-8',
            cache : false,
            success : function(data) {
                if(data.code == 0){
                    $("#for-edit").removeClass("active");
                    $("#for-display").addClass("active");
                    $("#shop-url-display").text($('#shop-url-input').val());
                    return false;
                }
                alert(data.message);
            }
        })
        return false;
    });
});
