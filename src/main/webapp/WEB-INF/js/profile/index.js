$(document).ready(function() {
    var externalUrl = $('#external-url').val();
    var returnMapping = $('#return-mapping').val();

    $('.to-open-service').click(function() {
        window.location.href = externalUrl + "service/open.html";
        return false;
    });
    
    $(".my-save").click(function(){
        window.location.href = externalUrl + "original-works/my-save.html";
        return false;
    });
    
    $(".my-tort").click(function(){
        window.location.href = externalUrl + "tort/my-tort.html";
        return false;
    });
    $(".my-evid-chain").click(function (){
        window.location.href = externalUrl + "evid-chain/my-chain.html";
        return false;
    })
});
