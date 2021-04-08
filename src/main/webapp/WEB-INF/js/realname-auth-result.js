$(document).ready(function() {
    var externalUrl = $('#external-url').val();
    var returnMapping = $('#return-mapping').val();

    $('.realname-auth-result-frame .go-index').click(function() {
        location.href = externalUrl + "index.html";
    });
    
    $('.realname-auth-result-frame .submit-realname-auth').click(function() {
        location.href = externalUrl + "realname-auth.html?return_mapping=" + encodeURIComponent(encodeURIComponent(returnMapping)) + "&re_auth=true";
    });
});