/**
 * Created by shbs-tp001 on 15-9-8.
 */
$(function () {
    $('.glyphicon_bottom').click(function(){
        if($('.sys_manage').hasClass('lable_hidden')){
            $(this).removeClass('glyphicon-triangle-bottom').addClass('glyphicon-triangle-top');
            $('.sys_manage').removeClass('lable_hidden').addClass('lable_block');
        }else{
            $(this).removeClass('glyphicon-triangle-top').addClass('glyphicon-triangle-bottom');
            $('.sys_manage').removeClass('lable_block').addClass('lable_hidden');
        }
    });
    config_function.addleftmenu();
    config_function.autoline();
    window.onresize=function(){
       config_function.autoline();
    }
});


var config_function=(function () {
    //动态加载左侧菜单
    addleftmenu=function(){
        changemenu();
    };

    autoline=function(){
        $(".line1").css("left",$(".main-content-box-left").offset().left+$(".main-content-box-left").width());
        $(".line2").css("left",$(".main-content-box-left").offset().left+$(".main-content-box-left").width()+9);
        $(".line3").css("right",$(".main-content-box-right").offset().left);
    };
    //页面自适高度
    autoleftheight= function () {


            $("body").height( $("body").height()-$(".topheader").height()-3);
    };
    //左侧菜单切换
    changemenu=function(){
        var activenow=optactive;
        change();
        function change(){
            $(".optbtn").each(function(){
                $(this).removeClass("active");
            });
            $(".menu-list").each(function(){
                $(this).css("display","none")
            });
            $($(".optbtn")[activenow]).addClass("active");
            $($(".menu-list li")[optactive*5+menuactive]).addClass("active")
            var active=".search";
            if(activenow==1){
                active=".add"
            }
            $(active).addClass('flipInX animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
                $(this).removeClass('flipInX animated');
            });
            $(active).css("display","block");
        }
//        $(".optbtn").unbind().bind("click", function () {
//            var index=$(this).attr("index");
//            if(index==activenow){
//                return;
//            }else{
//                activenow=index;
//                change();
//            }
//        })
        
    };
    return{
        addleftmenu:addleftmenu,
        autoline:autoline
    }
})();





//详情信息项点击显示编辑框
var alone_edit=(function () {
    var config_function= function (callback) {
        $(".canEdit").click(function(){
            if($(this).next(".txtEdit").hasClass("lable_hidden")){
                var $this=$(this);
                $(this).addClass("lable_hidden");
                $(this).next(".txtEdit").removeClass("lable_hidden").addClass("lable_block");
                setTimeout(function(){
                    hide_editinput($this);
                },100);

                $(".txtInfo").click(function(e){
                    e.stopPropagation();
                });
                $(this).next(".txtEdit").children(".txtSave").unbind().bind("click",function(e){
                    callback();
                    e.stopPropagation();
                });
            }
        })
    };
    var hide_editinput= function (a) {
        $("body").unbind().bind("click",function(){
            $(a).next(".txtEdit").removeClass("lable_block").addClass("lable_hidden");
            $(a).removeClass("lable_hidden");
            $("body").unbind();
        });
    };

    return{
        config_function:config_function
    }

})();
