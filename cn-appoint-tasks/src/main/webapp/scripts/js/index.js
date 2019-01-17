$(function() {
    $("#job-status").click(function() {
        $("#content").load("/worker/jobs");
    });
    $("#server-status").click(function() {
        $("#content").load("/worker/servers");
    });
    switchLanguage();
    //初始化显示语言
    initLanguage();
});
