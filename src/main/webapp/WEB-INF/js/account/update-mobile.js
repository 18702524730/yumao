function getConfirmVerification(externalUrl, newMobile, on_result) {
    $.ajax({
        url : externalUrl + "user/send-update-mobile-code.json",
        type : 'POST',
        data : JSON.stringify({
            new_mobile : newMobile,
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

function toUpdateMobile(externalUrl, verifycode, mobile, on_result) {
    $.ajax({
        url : externalUrl + "user/check-update-mobile-code.json",
        type : 'POST',
        data : JSON.stringify({
            verifycode : verifycode,
            mobile : mobile,
        }),
        dataType : 'json',
        contentType : 'application/json;charset=utf-8',
        cache : false,
        success : function(data) {
            return on_result(data);
        }

    });
}

function updateMobile(externalUrl, newMobile, on_result) {
    $.ajax({
        url : externalUrl + "user/update-mobile.json",
        type : 'POST',
        data : JSON.stringify({
            mobile : newMobile,
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
            var confirmMobileVerifycodeField = $("#confirm-mobile-verifycode");
            var userTypeField = $("#user-type");
            var newMobile = $("#update-new-mobile");
            var updateMobileVerifycodeField = $("#update-mobile-verifycode");

            $(".to-update-mobile").click(function() {
                $("#confirmMobileModal").modal("show");
                return false;
            });

            /*
             * 原手机号获取验证码
             */
            $(".confrim-mobile-div .get-verifycode").click(function() {
                // 点击按钮后锁定
                // var $btn = $(this).button('loading');
                // var $btn = $(this).button('');
                var _this = $(this);
                return getVerification(externalUrl, accountField.val(), userTypeField.val(), function(data) {
                    if (data.code === 0) {
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
             * 校验原手机号
             */
            $(".next-btn").click(function() {
                // 点击按钮后锁定
                var $btn = $(this).button('loading');

                var account = accountField.val();
                var verifycode = confirmMobileVerifycodeField.val();
                toUpdatePassword(externalUrl, account, verifycode, userTypeField.val(), function(data) {
                    if (data.code === 0) {
                        $("#confirmMobileModal").modal("hide");
                        $("#updateMobilModal").modal("show");
                        $btn.button('reset');
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

            /**
             * 新手机号获取验证码
             */
            $(".to-update-mobile-div .get-verifycode").click(function() {
                // 点击按钮后锁定
                // var $btn = $(this).button('loading');
                // var $btn = $(this).button('');
                var _this = $(this);
                return getConfirmVerification(externalUrl, newMobile.val(), function(data) {
                    if (data.code === 0) {
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
            });

            /**
             * 验证新手机号,提交修改申请
             */
            $(".submit-update-mobile").click(
                    function() {
                        // 点击按钮后锁定
                        var $btn = $(this).button('loading');

                        toUpdateMobile(externalUrl, updateMobileVerifycodeField.val(), newMobile.val(), function(data) {
                            if (data.code === 0) {
                                // 修改手机号
                                updateMobile(externalUrl, newMobile.val(), function(data) {
                                    if (data.code === 0) {
                                        // 弹出层
                                        $("#updatePasswordResultModal").modal("show");
                                        $("#updatePasswordResultModal .modal-title").text("修改成功");
                                        $("#updatePasswordResultModal #update-message").html(
                                                "<span class='col-lg-12 mt-10' style='font-size: 16px;'><span class='waiting-seconds'>5</span>秒后刷新。如没有跳转<a class='btn btn-link color-link' style='color: #528CC2;' href="
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
                                        //
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
        });