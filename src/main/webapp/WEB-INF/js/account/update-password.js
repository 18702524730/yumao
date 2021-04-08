function getVerification(externalUrl, account, userType, on_result) {
    $.ajax({
        url : externalUrl + "/user/send-ververifycode-state.json",
        type : 'POST',
        data : JSON.stringify({
            account : account,
            userType : userType,
        }),
        dataType : 'json',
        contentType : 'application/json;charset=utf-8',
        cache : false,
        success : function(data) {
            return on_result(data);
        }
    });

    return false;
}

function toUpdatePassword(externalUrl, account, verifycode, userType, on_result) {
    $.ajax({
        url : externalUrl + "/user/forget-toupdate-pass.json",
        type : 'POST',
        data : JSON.stringify({
            account : account,
            verifycode : verifycode,
            user_type : userType
        }),
        dataType : 'json',
        contentType : 'application/json;charset=utf-8',
        cache : false,
        success : function(data) {
            return on_result(data);
        }

    });
}

function updatePass(externalUrl, updatePassword, affirmUpdatePassword, on_result) {
    $.ajax({
        url : externalUrl + '/user/update-password.json',
        type : 'POST',
        data : JSON.stringify({
            updatePassword : updatePassword,
            affirmUpdatePassword : affirmUpdatePassword
        }),
        dataType : 'json',
        contentType : 'application/json;charset=utf-8',
        cache : false,
        success : function(data) {
            return on_result(data);
        }
    });
}

$(document).ready(
        function() {
            var externalUrl = $('#external-url').val();
            var accountField = $("#user-name");
            var verifycodeField = $("#update-password-verifycode");
            var userTypeField = $("#user-type");
            /*
             * 获取验证码
             */
            $("#update-password-div .get-verifycode").click(function() {
                // 点击按钮后锁定
                // var $btn = $(this).button('loading');
                // var $btn = $(this).button('');
                var _this = $(this);
                return getVerification(externalUrl, accountField.val(), userTypeField.val(), function(data) {
                    if (data.code === 0) {
                        // 提示
                        $(".input-p-display").css("display", "block");
                        $(".input-p-display span").text(data.mobileForDisplay);
                        var count = 60;

                        var intervalID = setInterval(function() {
                            _this.text(count + '秒后重新发送').prop('disabled', true);

                            if (count < 0) {
                                clearInterval(intervalID);
                                // 解开loading
                                // $btn.button('reset');
                                _this.text('重新发送').prop('disabled', false);
                            }
                            count--;
                        }, 1000);
                        return false;
                    }
                    // 弹出层
                    $("#updatePasswordResultModal").modal("show");
                    $("#updatePasswordResultModal .modal-title").text("获取验证码失败");
                    $("#updatePasswordResultModal #update-message").html("<span class='col-lg-12 mt-10' style='font-size: 16px;'>" + data.message + "</span>");
                    // 解开loading
                    $btn.button('reset');
                    return false;
                });
            })
            /*
             * 校验验证码
             */
            $(".to-submit").click(function() {
                // 点击按钮后锁定
                var $btn = $(this).button('loading');

                var account = accountField.val();
                var verifycode = verifycodeField.val();
                toUpdatePassword(externalUrl, account, verifycode, userTypeField.val(), function(data) {
                    if (data.code === 0) {
                        $btn.button('reset');
                        $(".submit-update-pass").click();
                        return false;
                    }
                    // 弹出层
                    $("#updatePasswordResultModal").modal("show");
                    $("#updatePasswordResultModal .modal-title").text("验证失败");
                    $("#updatePasswordResultModal #update-message").html("<span class='col-lg-12 mt-10' style='font-size: 16px;'>" + data.message + "</span>");
                    // 解开loading
                    $btn.button('reset');
                    return false;
                });
            });
            /*
             * 修改密码
             */
            $(".submit-update-pass").click(
                    function() {
                        // 点击按钮后锁定
                        var $btn = $(this).button('loading');

                        var updatePasswordField;
                        var affirmUpdatePasswordField;
                        updatePassword = $("#update-pass").val();
                        affirmUpdatePassword = $("#update-affirm-pass").val();
                        updatePass(externalUrl, updatePassword, affirmUpdatePassword, function(data) {
                            if (data.code === 0) {
                                // 弹出层
                                $btn.button('reset');
                                $("#updatePasswordResultModal").modal("show");
                                $("#updatePasswordResultModal .modal-title").text("修改成功");
                                $("#updatePasswordResultModal #update-message").html(
                                        "<span class='col-lg-12 mt-10' style='font-size: 16px;'><span class='waiting-seconds'>5</span>秒后跳转到登陆。如没有跳转<a class='btn btn-link color-link' style='color: #528CC2;' href="
                                                + externalUrl + "login.html>点击这里</a> </span>");
                                var waitingSecondsField = $('.waiting-seconds');
                                var intervalID = setInterval(function() {
                                    var waitingSeconds = parseInt(waitingSecondsField.text());
                                    if (waitingSeconds <= 1) {
                                        clearInterval(intervalID);
                                        location.href = externalUrl + "/login.html";
                                        return false;
                                    }
                                    waitingSecondsField.text(waitingSeconds - 1);
                                }, 1000);
                                $btn.button('reset');
                                return false;
                            }
                            // 弹出层
                            $("#updatePasswordResultModal").modal("show");
                            $("#updatePasswordResultModal .modal-title").text("修改失败");
                            $("#updatePasswordResultModal #update-message").html("<span class='col-lg-12 mt-10' style='font-size: 16px;'>" + data.message + "</span>");
                            // 解开loading
                            $btn.button('reset');
                            return false;
                        });
                    });
        });