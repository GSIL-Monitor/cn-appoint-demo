$(function() {
    $("[data-toggle='tooltip']").tooltip()
});

function showDialog(msg, timeout) {
    $("#message-info").text(msg);
    $("#message-dialog").modal("show");
    if(null !== timeout) {
        setTimeout('$("#message-dialog").modal("hide")', timeout);
    }
}

function showSuccessDialog() {
    showInfoDialog("操作成功！");
}

function showInfoDialog(msg) {
    showDialog(msg, 2000);
}

function showFailureDialog(msg) {
    showDialog(msg, null);
}


function showShutdownConfirmModal() {
    $("#confirm-info").text("确认要关闭吗？");
    $("#confirm-dialog").modal({backdrop: 'static', keyboard: true});
}

function showDeleteConfirmModal() {
    $("#confirm-info").text("确认要删除吗？");
    $("#confirm-dialog").modal({backdrop: 'static', keyboard: true});
}


/*加载本地资源*/
function doLocale() {
    if ($("#content").hasClass("lang-en")) {
        i18n("en");
    } else {
        i18n("zh");
    }
}

function i18n(lang) {
    jQuery.i18n.properties({
        name : 'message',
        path : '/i18n/',
        mode : 'map',
        language : lang,
        cache: true,
        encoding: 'UTF-8',
        callback : function() {
            for (var i in $.i18n.map) {
                $('[data-lang="'+i+'"]').html($.i18n.prop(i));
            }
        }
    });
}


function switchLanguage() {
    $("#lang-zh").click(function() {
        $("#content").removeClass("lang-en").addClass("lang-zh");
        doLocale();
    });
    $("#lang-en").click(function() {
        $("#content").removeClass("lang-zh").addClass("lang-en");
        doLocale();
    });
}

/**
 * 根据浏览器语言初始化显示语言
 */
function initLanguage() {
    //获取浏览器语言
    var lan = (navigator.language || navigator.browserLanguage);
    if (lan && lan.toLowerCase().indexOf('zh') > -1) {
        //切换成中文
        $("#lang-zh").click();
    }
}