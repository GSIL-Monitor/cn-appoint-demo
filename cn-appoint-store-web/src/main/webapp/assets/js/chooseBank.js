function openWin(url,t,w,h) {
    jQuery.jdThickBox({title:t,type:'iframe',source:url,width:w,height:h});
}

function showChooseBank(url) {
    $(window).scrollTop(0, 0);
    openWin(url,'查询',860,580);
}

function closeThinkBox(){
    jdThickBoxclose();
}

