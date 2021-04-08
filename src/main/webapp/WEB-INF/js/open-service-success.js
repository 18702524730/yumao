$(document).ready(function() {
    var externalUrl = $("#external-url").val();
    var count = 3;
    setInterval(function() {
        $(".second-down").text(count);
        count--;
        if (count < 1) {
            window.location.href = externalUrl + "user/account.html";
        }
    }, 1000);
});