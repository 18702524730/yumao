$(document).ready(function() {
    var externalUrl = $('#external-url').val();
    var returnMapping = $('#return-mapping').val();

    $('.go-index').click(function() {
        location.href = externalUrl + "index.html";
    });
    
    $('.re-real-name').click(function() {
        location.href = externalUrl + "realname-auth.html?return_mapping=" + encodeURIComponent(encodeURIComponent(returnMapping)) + "&re_auth=true";
    });
});