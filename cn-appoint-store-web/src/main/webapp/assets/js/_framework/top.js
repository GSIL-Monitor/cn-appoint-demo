$(function(){
    var timer=null;
    $(".e-hd-usermenu2016 .icon-drop,.h-hd-drop").hover(function(){
        clearTimeout(timer);
        $(".e-hd-usermenu2016").addClass("user-menu-on");
    },function(){
        timer=setTimeout(function (){
            $(".e-hd-usermenu2016").removeClass("user-menu-on")
        }, 300);
    })
})