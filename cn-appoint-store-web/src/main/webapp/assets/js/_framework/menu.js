/**
 * Created by liyi on 2017/4/24.
 */
$(function(){
    $(".l-menu .h-menu-list .li-item a").each(function (index) {
        if(window.location.pathname==$(this).attr("href")){
            $(this).parent().addClass("h-item-on");
            $(this).parent().parent().parent().parent("dl.h-menu-box").addClass("h-menu-on");
        }else if(window.location.pathname=='/'){
            $($(".l-menu").children(".h-menu-box").get(0)).addClass("h-menu-on");
            // $(".l-menu .h-menu-list a[href='/menu/store/store-info']").addClass("h-item-on");
            $(".l-menu .li-item:first").addClass("h-item-on");
        }
    });
    $(".l-menu dt.h-menu-hd").each(function (index) {
        $(this).click(function () {
            if ($(this).parent().hasClass("h-menu-on")) {
                $(this).parent().removeClass("h-menu-on");
            } else {
                $(".l-menu dl.h-menu-box").removeClass("h-menu-on");
                $(this).parent().addClass("h-menu-on");
                // 二级菜单展示
                $(".l-menu dd>li.li-item").each(function (idx) {
                    $(this).click(function () {
                        if ($(this).hasClass("h-item-on")) {
//                                $(this).parent("dl.h-menu-box").addClass("h-menu-on");
                        } else {
                            $("li.li-item").removeClass("h-item-on");
                            $(".l-menu dl.h-menu-box").removeClass("h-menu-on");
                            $(this).parent("dl.h-menu-box").addClass("h-menu-on");
                            $(this).addClass("h-item-on");
                        }
                    });
                });
            }
        });
    });
})