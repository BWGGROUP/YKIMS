/**
 * Created by shbs-tp001 on 15-9-14.
 */
//全局静态变量
var CommonVariable=(function () {
    var CommonVariable= function () {
        this.NOTECONFIGNUM=2;//备注信息初始显示条数
        this.PAGESIZE=20;//一页展示条数
        this.SHOWPAGESIZE=5;//前台交易，融资信息，基金显示的条数
    };
    return function(){
        return new CommonVariable();
    };
})();
$(function () {
    $.extend({
        showloading:function(text){
            if($("#loading-model").length>0){
                $.hideloading();
            }
            text=text||"加载中";
            var html='<div id="loading-model" class="fixed-model"><div class="loading-body"><i class="ui-loading-bright"></i><p>'+text+'...</p></div></div>';
            $("body").append(html);
        },
        hideloading: function () {
            $("#loading-model").remove();
        },
        showtip:function(text){
            if($("#tip").length==0){
                var loadinghtml='<div id="tips"  class="fixed-model"><div id="tip" ><span id="load-more">'+text+'</span></div></div>';
                var H=document.body.offsetHeight;
                var W=document.body.offsetWidth;
                $("body").append(loadinghtml);
                $C=$("#tip");
                $C.css({"left":(W-$C.width())/2-7});
                $C.fadeIn(200);
            }else{
            	$("#tip").remove();
            	$.showtip(text);
            }
        },
        hidetip:function(){
            $C=$("#tips");
            $C.fadeOut(200);
            setTimeout(function(){
                $C.remove();
            },200);
        }
    });

//    left_menu.html.config();
    bottom_menu.config();
    $(document).ajaxError(function errorPrompt(event,XMLHttpRequest,ajaxOptions,thrownError){
        $.hideloading();
        if(XMLHttpRequest.responseText=="SESSIONTIMEOUT"){
           alert("由于长时间未操作页面，页面数据已过期");
            location.reload();
        }
    });

});


var left_menu=(function () {
    var H= $(window).height();
    var config= function () {

        $(".main-content").css("min-height",H);
        $(".left-menu-btn").click(function(){

        });
    };
    var hidemenu=function(){
        $("body").removeClass("showleft-menu").addClass("hideleft");
        setTimeout(function(){ $("html,body").removeClass("overflow_hidden");$("#mm-blocker").remove();},300);
    };
    var showleft_menu= function () {
        $("html,body").addClass("overflow_hidden");
        $("body").removeClass("hideleft").addClass("showleft-menu");
        $("body").append('<div id="mm-blocker" style="min-height: '+H+'px"></div>');
        $("#mm-blocker").bind(" touchmove click",function(e){
            hidemenu();
            e.stopPropagation();
        });
    };
    return{
        config:config,
        showleft:showleft_menu
    };
})();

var tipedit=(function () {
    var data_modle='<div class="tipedit-model"><div class="tipedit-content"><div class="tipedit-body">' +
        '<ul>' +
        '<% for (var i=0;i<list.length;i++ ){%>' +
        '<li>' +
        '<div class="tipg">' +
        '<div class="tipg-name">' +
        '<span class="tipg-name-text"><%=list[i].name%></span>' +
        '<% if(allowdelet){%>' +
        '<button class="btn btn-tip-del" btndata-i="<%=list[i].id%>"><span class="glyphicon glyphicon-remove"></span></button>' +
        '<%}%>' +
        '</div>' +
        '<% if(allowedit){%>' +
        '<div class="group-edit hidden"><input type="text" name="groupedit" value="<%=list[i].name%>" class="fn-input"><button class="btn btn-llt" btndata-i="<%=list[i].id%>">OK</button></div>' +
        '<%}%>' +
        '</div>' +
        '</li>' +
        '<%}%>'+
        '</ul>' +
        '<% if (allowadd){%>'+
        '<div class="creat-group"><div class="creat-group-name ">添加</div><div class="creat-group-action hidden"><input type="text" name="groupcreat" class="fn-input "><button class="btn bt-creat">添加</button><button class="btn bt-close">取消</button></div></div>' +
        '<%}%>'+
        '</div>' +
        '<div class="bottom-opt">' +
        '<div class="close">取消</div><div class="besuer">保存</div>' +
        '</div>' +
        '</div></div>';
    var def={
        allowedit:true,
        allowdelet:true,
        allowadd:true,
        eidtsuccess:function(){},

        $this:"",
        model:"",
        allowdo_other:true
    };
    var congfig= function (opt) {
        def= $.extend(def,opt);
        init();


    };
    var init=function(){
        var data=new Object();
        data.allowadd=def.allowadd;
        data.allowdelet=def.allowdelet;
        data.allowedit=def.allowedit;
        var list=[];
        var html_list=def.$this.children(".tiplist").children("ul").children("li");
        html_list.each(function () {
            var info=new Object;
            info.name=$(this).html();
            info.id=$(this).attr("data-i");
            list.push(info);
        });
        data.list=list;
        var html=template.compile(data_modle)(data);
        $("body").append(html);
        def.model=$(".tipedit-model");
        click();
    };
    var click= function () {
            if(def.allowedit){
                def.model.find("li").unbind().bind("click",function () {
                    if(def.allowdo_other){
                        hide_del();
                        show_edit($(this));
                        def.allowdo_other=false;
                    }
                });
                def.model.find(".btn-llt").unbind().bind("click",function (e) {
                    show_del();
                    hide_edit($(this).parents("li"));
                    def.allowdo_other=true;
                    e.stopPropagation();
                });
            }
        if(def.allowdelet){
            def.model.find(".btn-tip-del").unbind().bind("click",function (e) {
                var id=$(this).attr('btndata-i');
                $(this).parents("li").remove();
                e.stopPropagation();
            });
        }
        if(def.allowadd){
            def.model.find(".creat-group-name").unbind().bind("click",function () {
               show_add();
            });
            def.model.find(".bt-creat").unbind().bind("click",function () {
                hide_add();
            });
            def.model.find(".bt-close").unbind().bind("click",function () {
                hide_add();
            });
        }
        def.model.find(".close").unbind().bind("click",function (e) {
            close();
            e.stopPropagation();
        });
        def.model.find(".besuer").unbind().bind("click",function (e) {
            close();
            e.stopPropagation();
        });
        
    };
    var hide_del= function () {
        if(def.allowdelet){
        def.model.find(".btn-tip-del").addClass("hidden");
        }
    };
    var show_del= function () {
        if(def.allowdelet){
            def.model.find(".btn-tip-del").removeClass("hidden");
        }
    };
    var show_edit= function ($this) {
        if(def.allowedit){
            $this.find(".group-edit").removeClass("hidden");
            hide_tipname($this);
        }
    };
    var show_tipname= function ($this) {
        $this.find(".tipg-name").removeClass("hidden");
    };
    var hide_tipname= function ($this) {
        $this.find(".tipg-name").addClass("hidden");
    };
    var hide_edit= function ($this) {
        if(def.allowedit){
            $this.find(".group-edit").addClass("hidden");
            show_tipname($this);
        }
    };
    var show_add= function () {
        if(def.allowadd){
            def.model.find(".creat-group-action").removeClass("hidden");
            def.model.find(".creat-group-name").addClass("hidden");

        }
    };
    var hide_add= function () {
        if(def.allowadd){
            def.model.find(".creat-group-name").removeClass("hidden");
            def.model.find(".creat-group-action").addClass("hidden");

        }
    };
    var close= function () {
        def.model.remove();
        def.allowdo_other=true;
    };
    return{
        congfig:congfig
    };
})();

var bottom_menu=(function () {
    var config= function () {
        $.ajax({
            async: true,
            dataType: 'html',
            type: 'get',
            url:"view/mobile/common/bottom-menu",
            success: function (data) {
                $("body").append(data);
                click();
                if(cookieopt.getlogintype()=="MB"){
                    $(".unbind").html("退出登录");
                }
            }
        });
    };
    var click= function () {
        $(".menu1 li.ms").click(function (e) {

            if($(this).hasClass("active")){
                closemenu();
            }else{
                closemenu();
                $(this).addClass("active");
                $(this).children("ul").addClass("bottom-menushow");
            }
            e.stopPropagation();
        });
        $(".menu1 li.m").click(function (e) {

            if($(this).hasClass("active")){
                closemenu();
            }else{
                closemenu();
                $(this).addClass("active");
                $(this).children("ul").addClass("bottom-menushow");
            }
            e.stopPropagation();
        });

        $(".menu1 li.mu").unbind().bind("click",function (e) {

                if( $(this).hasClass("active")){
                    closemenu();
                }else{
                    closemenu();
                    $(this).addClass("active");
                    $(this).children("ul").addClass("bottom-menushow");
                }
                e.stopPropagation();
        });
        try{
               if(islogin){
                   $(".menu1 li.mu").unbind();
               }
        }catch (e){}

        $(".menu1 li.me").click(function (e) {
            closemenu();
//            left_menu.html.showleft();
            e.stopPropagation();
        });
        $(".add-menu a,.search-menu a,.usermenu  a").click(function (e) {
            closemenu();
            e.stopPropagation();
        });
        $(".add-menu li,.search-menu li,.usermenu  li").bind("touchstart", function () {
            $(this).addClass("factive");
            var src=$(this).find("img").attr("src");
            src=src.substring(0,src.indexOf(".png"));
            $(this).find("img").attr("src",src+"_h.png");

        });
        $(".add-menu li,.search-menu li,.usermenu  li").bind("touchend", function () {
            $(this).removeClass("factive");
            var src=$(this).find("img").attr("src");
            src=src.substring(0,src.indexOf("_h"));
            $(this).find("img").attr("src",src+".png");
        });

        $("html").click(function (e) {
            closemenu();
            e.stopPropagation();
        });
        $("a[data-href]").click( function (e) {
            window.location.href=$(this).attr("data-href")+"?logintype="+cookieopt.getlogintype();
            e.stopPropagation();
        });
    };
    var closemenu= function () {
        if($(".menu1 li ul.bottom-menushow").length>0){
            $(".menu1 li.ms").removeClass("active");
            $(".menu1 li.m").removeClass("active");
            $(".menu1 li.mu").removeClass("active");
            $(".menu1 li ul.bottom-menushow").removeClass("bottom-menushow").addClass("bottom-menuhide").one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
                $(this).removeClass("bottom-menuhide");
            });
        }
    };
    return{
        config:config
    };
})();
//多选列表效果弹层
var listefit=(function () {
    var html_model;
    var def={
        title:"",//弹层标题
        list:[],//列表数据,
        radio:false,//是否单选
        besurebtn:"保存",//确定按钮文字
        cancle:"",//取消按钮文字 （如果不写，则不会显示该按钮）
        besure: function () {}//点击保存后回调该方法
    };
    //lists数据结构：[{"name":"张颖","id":"1","select":true},{"name":"张颖","id":"1","select":true}]
    //name,id--必填字段，select--选填字段，标示该<li>是否被选中 默认值：false。 选中状态以 class="active"标示，开发者可自行更改此状态
    var config= function (opt) {
        def= $.extend(def,opt);
         html_model='<div class="tipedit-model show">' +
            '<div class="tipedit-content">' +
            '<div class="tipedit-title">' +
            def.title +
            '<span class="close-model"></span></div>' +
            '<div class="tipedit-body">' +
            '<ul class="list">' +
            '<% for(var i=0;i<list.length;i++){ ' +
             'list[i].select=list[i].select||false;' +
             'list[i].no_delete=list[i].no_delete||"";%>' +
             '<%if(list[i].select){%>' +
             '<li tip-l-i="<%=list[i].id %>" class="active" no_delete="<%print(list[i].no_delete) %>" ><%=list[i].name %></li>' +
            '<%}else{ %>' +
            '<li tip-l-i="<%=list[i].id %>" ><%=list[i].name %></li>' +
            '<%} %>' +
            '<%}%>' +
            '</ul>' +
            '</div>' +
            '<div class="bottom-opt">' +
             '<%if(cancle==""){%>' +
             '<div class="besuer" style="width: 100%;border-bottom-left-radius: 6px;"><%=besurebtn %></div>' +
             '<%}else{%>' +
             '<div class="close"><%=cancle%></div>' +
             '<div class="besuer"><%=besurebtn %></div>' +
             '<%}%>' +
            '</div></div></div>';
        init();
    };
    var init= function () {
        var html=template.compile(html_model)(def);
        $("body").append(html);
        $(".tipedit-body").css("overflow","hidden");
        $(".tipedit-model").one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
            $(".tipedit-body").css("overflow","");
            $(this).removeClass("show");
        });
        click();
    };
    var click= function () {

        $(".tipedit-body .list li").each(function (index) {
            $(this).unbind().bind("click", function () {
                if(($(".tipedit-body").height()+$(".tipedit-body")[0].scrollTop)>index*$(this).height()){
                    if($(this).hasClass("active")&&$(this).attr("no_delete")!="true"){
                        $(this).removeClass("active");
                    }else{
                        if(def.radio){
                            $(".tipedit-body .list li").removeClass("active");
                        }
                        $(this).addClass("active");
                    }
                }

            });
        });
        $(".close-model").unbind().bind("click", function () {
            close();
        });
        $(".besuer").unbind().bind("click", function () {
            def.besure();
            close();
        });
        $(".besuer").bind("touchstart",function(){
            $(this).addClass("active");
        });
        $(".besuer").bind("touchend",function(){
            $(this).removeClass("active");
        });
    };
    var close= function () {
        $(".tipedit-content").addClass("hide").one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
            $(".tipedit-model").remove();
        });
    };
    return{
        config:config,
        close:close
    };
})();
//输入框弹层效果
var inputlsit_edit=(function () {
    var html_model='<div class="tipedit-model show"><div class="tipedit-content"><div class="tipedit-title"><%=title%><span class="close-model"></span></div><div class="tipedit-body">' +
        '<ul class="inputlist">' +
        '<%if(html){%>' +
        '<%print(htmltext)%>' +
        '<%}else{%>' +
        '<% for(var i=0;i<list.length;i++){ ' +
        'list[i].type=list[i].type||"text";' +
        'list[i].value=list[i].value||"";' +
        'list[i].readonly=list[i].readonly||"";' +
        'list[i].disabled=list[i].disabled||"";' +
        'list[i].maxlength=list[i].maxlength||"";' +
        '%>' +
        '<li>' +
        	//2015-11-30 TASK078 RQQ ModStart
            '<%if(list[i].type!="select"&&list[i].type!="textarea" &&list[i].type!="span"){%>' +
          //2015-11-30 TASK078 RQQ ModStart
            '<span class="lable"><%=list[i].lable%>:</span><span class="in"><input value="<%=list[i].value %>" <%=list[i].readonly%> <%=list[i].disabled%>  id="<%=list[i].id%>" type="<%=list[i].type %>" class="inputdef"'
            +' maxlength="<%=list[i].maxlength %>"/'
            +' /></span>' +

            '<%}else if(list[i].type=="select"){%>' +
                '<span class="lable"><%=list[i].lable%>:</span><span class="in"><select class="inputdef" id="<%=list[i].id%>">' +
                     '<% for(var j=0;j<list[i].optionlist.length;j++){%> ' +
                             '<option id="<%=list[i].optionlist[j].id%>" value="<%=list[i].optionlist[j].text%>"><%=list[i].optionlist[j].text%></option>' +
                     '<%}%>'+
                    '</select></select></span>' +
                '<%}else if(list[i].type=="textarea"){%>' +
                        '<span class="lable"><%=list[i].lable%>:</span><span class="in"><textarea class="inputdef" id="<%=list[i].id%>" maxlength="<%=list[i].maxlength%>"></textarea>' +
            			//2015-11-30 TASK078 RQQ AddStart
                        '<%}else if(list[i].type=="span"){%>' +
                        '<span class="lable"><%=list[i].lable%>:</span><span class="in" id="<%=list[i].id%>" style="text-align: left;padding-left: 15px;"> <%=list[i].value%>'
                        +' </span>' +
                //2015-11-30 TASK078 RQQ AddEnd
                     '<%}%>' +
        '</li>' +
        '<%}%>' +
        '<%}%>' +
        '</ul>' +
        '</div><div class="bottom-opt">' +
        '' +
        '<%if(cancle==""){%>' +
        '<div class="besuer" style="width: 100%;border-bottom-left-radius: 6px;"><%=besurebtn%></div>' +
        '<%}else{%>' +
        '<div class="close"><%=cancle%></div><div class="besuer"><%=besurebtn%></div>' +
        '<%}%>' +
        '</div></div></div>';
    var def;
    var config= function (opt) {
        def={
            title:"",//弹层标题
            list:[],//输入框列表数组
            html:false,//是否以html显示
            besurebtn:"确定",//确定按钮文字
            cancle:"",//取消按钮文字 （如果不写，则不会显示该按钮）
            htmltext:"",
            submit: function () {},//点击确定按钮回调方法
            canmit: function () {},//点击取消按钮回调方法
            complete: function () {}//效果加载完成回调方法
        };
        def= $.extend(def,opt);
        var html=template.compile(html_model)(def);
        $("body").append(html);
        click();
        def.complete();
        $("input[readonly]").focus(function(){
        if(	$(this).attr("readonlyflag")!="readonlyflag"){
            $(this).blur();
        }
        });
        $(".tipedit-model").one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
            $(this).removeClass("show");
        });
    };
    var click=function(){
        $(".close-model").unbind().bind("click", function () {
            close();
        });
        $(".besuer").unbind().bind("click", function () {
            def.submit();

        });
        $(".besuer").bind("touchstart",function(){
            $(this).addClass("active");
        });
        $(".besuer").bind("touchend",function(){
            $(this).removeClass("active");
        });
        if(def.cancle!=""){
            $(".close").unbind().bind("click", function () {
                def.canmit();

            });
            $(".close").bind("touchstart",function(){
                $(this).addClass("active");
            });
            $(".close").bind("touchend",function(){
                $(this).removeClass("active");
            });
        }
    };
    var close= function () {
        $(".tipedit-content").addClass("hide").one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
            $(".tipedit-model").remove();
        });
    };
return{
    config:config,
    close:close
};
})();
//数组移除某个元素
Array.prototype.baoremove = function(dx)
{
    if(isNaN(dx)||dx>this.length){return false;}
    this.splice(dx,1);
};
var WCsearch_list=(function () {
    var html_model='<div id="select-page" class="select-page show">' +
        '<div class="search-head">' +
        '<div class="serarch-back-btn"></div>' +
        '<div class="center ">' +
        '<input class="select-page-input" placeholder="检索">' +
        '<div class="serarch-btn"></div>' +
        '</div>' +
        '<%if(allowadd){%>' +
        '<div class="serarch-add-btn"></div>' +
        '<%}%>' +
        '</div>' +
        '<div class="inner">' +
        '<div class="lists">' +
        '</div>' +
        '</div></div>';
    var def={
        loadDownFn: function () {},
        searchclick:function(){},
        addclick: function () {},
        allowadd:false
    };
    var config= function (opt) {
        def= $.extend(def,opt);
        var html=template.compile(html_model)(def);
        $("body").append(html);
        $("html,body").css({overflow:"hidden",height:"100%"});
        click();
        $(".inner").height($(window).height()-48);
    };
    var click= function () {
        $(".serarch-btn").click(function () {
            def.searchclick();
        });
        if(def.allowadd){
            $(".serarch-add-btn").click(function () {
                def.addclick();
            });
        }

        $(".serarch-back-btn").click(function () {
            closepage();
        });

    };
    var dropload= function (opt) {
        def= $.extend(def,opt);

            def.dropload= $('.inner').dropload({

                loadDownFn : def.loadDownFn
            });


        return def.dropload;
    };
    var closepage= function () {
        $("#select-page").removeClass("show").addClass("hide").one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
            $(this).remove();
            $("html,body").css({overflow:"auto",height:"auto"});
        });
    };
    var dropload_lock= function () {
        def.dropload.lock();
    };
    var dropload_unlock= function () {
        def.dropload.unlock();
    };
    return {
        config:config,
        dropload:dropload,
        closepage:closepage
    };
})();
/**
 * 数据模板对日期进行格式化，
 * @param date 要格式化的日期
 * @param format 进行格式化的模式字符串
 *     支持的模式字母有：
 *     y:年,
 *     M:年中的月份(1-12),
 *     d:月份中的天(1-31),
 *     h:小时(0-23),
 *     m:分(0-59),
 *     s:秒(0-59),
 *     S:毫秒(0-999),
 *     q:季度(1-4)
 * @return String
 */
try{
    template.helper('dateFormat', function (a,format) {
        var date;
        if(isNaN(Number(a.time))){
            if(isNaN(Number(a))){
                if(a.lastIndexOf(".")>0){
                    date= new Date(a.replace(/-/g,"/").substr(0, a.lastIndexOf(".")));
                }else{
                    date= new Date(a.replace(/-/g,"/"));
                }

            }else{
                date=new Date(Number(a));
            }

        }else{
             date = new Date(Number(a.time));
        }
        // date.setTime()
        var map = {
            "M": date.getMonth() + 1, //月份
            "d": date.getDate(), //日
            "h": date.getHours(), //小时
            "m": date.getMinutes(), //分
            "s": date.getSeconds(), //秒
            "q": Math.floor((date.getMonth() + 3) / 3), //季度
            "S": date.getMilliseconds() //毫秒
        };


        format = format.replace(/([yMdhmsqS])+/g, function(all, t){
            var v = map[t];
            if(v !== undefined){
                if(all.length > 1){
                    v = '0' + v;
                    v = v.substr(v.length-2);
                }
                return v;
            }
            else if(t === 'y'){
                return (date.getFullYear() + '').substr(4 - all.length);
            }
            return all;
        });
        return format;
    });

}catch (e){}

Date.prototype.format=function(date,format){
    var map = {
        "M": date.getMonth() + 1, //月份
        "d": date.getDate(), //日
        "h": date.getHours(), //小时
        "m": date.getMinutes(), //分
        "s": date.getSeconds(), //秒
        "q": Math.floor((date.getMonth() + 3) / 3), //季度
        "S": date.getMilliseconds() //毫秒
    };


    format = format.replace(/([yMdhmsqS])+/g, function(all, t){
        var v = map[t];
        if(v !== undefined){
            if(all.length > 1){
                v = '0' + v;
                v = v.substr(v.length-2);
            }
            return v;
        }
        else if(t === 'y'){
            return (date.getFullYear() + '').substr(4 - all.length);
        }
        return all;
    });
    return format;
};
/**
 * cookie操作
 * @autor zzg
 */
var cookieopt=(function(){
  var getcookie=function(key) {
        if(key){
            var strCookie = document.cookie;
            var arrCookie = strCookie.split(";");
            var cookie = null;
            for (var i = 0; i < arrCookie.length; i++) {
                var arr=arrCookie[i].split("=");
                if(key==arr[0].trim()){
                    cookie=arr[1].trim();
                    break;
                }
            }
            return cookie;
        }else{
            return null;
        }

    };
    var setcookie= function (key,value) {
        var Days = 30; //30天
        var exp = new Date();
        exp.setTime(exp.getTime() + Days*24*60*60*1000);
        document.cookie = key+"="+ escape (value) + ";expires=" + exp.toGMTString();
//        document.cookie=key+"="+value;
    };
    var getlogintype= function () {
        var logintype=getcookie("logintype");
        if(logintype==null||logintype=="null"){
            var wechatInfo = navigator.userAgent.match(/MicroMessenger\/([\d\.]+)/i) ;
            if( wechatInfo ) {
                logintype="WX";
            }else{
                logintype="MB";
            }
            setcookie("logintype",logintype);
            return logintype;
        }
        return logintype;
    };
    return{
        getcookie:getcookie,
        setcookie:setcookie,
        getlogintype:getlogintype
    };
})();
//返回时间string　年月日
function timestampformatdate(timestamp) {
    //年
    var year = 1900 + timestamp.year;
    //月
    var month = timestamp.month + 1 < 10 ? "0" + (timestamp.month + 1) : timestamp.month + 1;
    //日
    var day = timestamp.date < 10 ? "0" + timestamp.date : timestamp.date;
   
    //时间字符串
    var serverTimeStr=year+"-"+month+"-"+day;
    return  serverTimeStr;
}

//返回时间string　年月时分秒
function timestampformat(timestamp) {
    //年
    var year = 1900 + timestamp.year;
    //月
    var month = timestamp.month + 1 < 10 ? "0" + (timestamp.month + 1) : timestamp.month + 1;
    //日
    var day = timestamp.date < 10 ? "0" + timestamp.date : timestamp.date;
    //小时
    var hour=timestamp.hours>9?timestamp.hours:"0"+timestamp.hours;
    //分钟
    var minute=timestamp.minutes==0?"00":timestamp.minutes;
   //秒
    var seconds=timestamp.seconds==0?"00":timestamp.seconds;
    //时间字符串
    var serverTimeStr=year+"-"+month+"-"+day+" "+hour+":"+minute+":"+seconds;
    return  serverTimeStr;
}



/******防止ＡＪＡＸ重复提交*******/
	var pendingRequests = {};
	function generatePendingRequestKey(options){
	return options.url;
	}
	function  storePendingRequest(key,jqXHR){
	pendingRequests[key]=jqXHR;
	jqXHR.pendingRequestKey = key;
	}
   $.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
       //翻页不拦截重复请求
       var list=["addFinancplan.html","addFinancplan.html","findNoteByInvestorCode.html"];
       for(i in list){
           if(options.url.indexOf(list[i])<0){
               return;
           }
       }

        // 不重复发送相同请求
        var key = generatePendingRequestKey(options);
        if (!pendingRequests[key]) {
        	storePendingRequest(key, jqXHR);
        } else {
        	// or do other
        	jqXHR.abort();
        	$.hideloading();
        }
        
		var complete = options.complete;
		var pendingRequestsFun = function(){
			pendingRequests[jqXHR.pendingRequestKey] = null;
			};
		options.complete = function(jqXHR, textStatus) {
			// clear from pending requests
			setTimeout(pendingRequestsFun,2000);
			if ($.isFunction(complete)) {
				complete.apply(this, arguments);
			}
		};
	});


