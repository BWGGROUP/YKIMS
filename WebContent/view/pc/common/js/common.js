/**
 * Created by shbs-tp001 on 15-9-8.
 */
//全局静态变量
var CommonVariable=(function () {
    var CommonVariable= function () {
        this.NOTECONFIGNUM=2;//备注信息初始显示条数
        this.SELSECLIMIT=100;//下拉选择列表显示条数
        this.SUPERKCODE="SUPERADMIN";//超级筐code
        this.SUPERMEET="PUBLICADMIN";//会议超级筐code
        this.SHOWPAGESIZE=5;//页面交易，基金，融资信息的显示条数
    };
    return function(){
        return new CommonVariable();
    };
})();

$(function () {
    $('.loguserbtn').click(function(e){
        if(!$(this).parents(".loginuser").hasClass("active")){
            $(this).parents(".loginuser").addClass("active");
            $(document).bind("click", function () {
                $('.loguserbtn').click();
            });
            e.stopPropagation();
        }else{
            $(this).parents(".loginuser").removeClass("active");
            $(document).unbind("click");
        }
        $(".loginuser").bind("click", function (e) {
            e.stopPropagation();
        });
    });
    $("a[menu-u-href]").click(function(){
        window.location.href=$(this).attr("menu-u-href")+"?logintype="+cookieopt.getlogintype();
    });
    config_function.addleftmenu();
    config_function.autoline();
   window.onresize=function() {
      config_function.autoline();
   };
//集成loading效果
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
        tiptimeout:"",
        showtip:function(text,type,num){
             num=num||2000;
            type=type||"normal";
            var html='<div class="tip-box '+type+'"><i><span class="glyphicon '+choose_class()+'" ></span></i>'+text+'</div>';

            if($(".tip-box").length>0){
                $(".tip-box").remove();
                clearTimeout($.tiptimeout);
            }
            $("body").append(html);
            $(".tip-box").animate({top:"0px"},300,function(){
                $.tiptimeout= setTimeout(function(){
                    $.hidetip();
                },num);
            });
            function choose_class(){
                var a="";
                switch (type){
                    case "normal":
                        a="glyphicon-info-sign";
                        break;
                    case "success":
                        a="glyphicon-ok-circle";
                        break;
                    case "error":
                        a="glyphicon-exclamation-sign";
                        break;

                }
                return a;
            }
        },
        hidetip: function () {
            $(".tip-box").animate({top:"-41px"},300, function () {
                $(this).remove();
            });
        },
        openLink: function (url) {
           $("body").append("<a id='openLink' style='display: none' target='_blank'></a>");
           var openLink= $("#openLink").attr('href', url);
            openLink[0].click();
            openLink.remove();
        }
    });

    $(window).ajaxError(function errorPrompt(event,XMLHttpRequest,ajaxOptions,thrownError){
        $.hideloading();
        if(XMLHttpRequest.responseText=="SESSIONTIMEOUT"){
            alert("由于长时间未操作页面，页面数据已过期");
            location.reload();
        }
    });
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
        var list=["addFinancplan.html","addFinancplan.html","initTradeDetialInfo.html"];
        for(var i= 0;i<list.length;i++){
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
});

//弹层页面 用于展示表格更多
$(function () {

    var alertpagedef={};
    $.fn.alertpage= function (opt) {
        alertpagedef= $.extend(alertpagedef,opt,{});
    };

});
var config_function=(function () {
    //动态加载左侧菜单
    addleftmenu=function(){
        try{ changemenu();}catch (e){}
        click();
    };
var click= function () {
    $(".left-menu-body").find("a[menu-href]").bind("click",function(){
        window.location.href=$(this).attr("menu-href")+"?logintype="+cookieopt.getlogintype();
    });
};
    autoline=function(){
        try{
            $(".line1").css("left",$(".main-content-box-left").offset().left+$(".main-content-box-left").width());
            $(".line2").css("left",$(".main-content-box-left").offset().left+$(".main-content-box-left").width()+9);
            $(".line3").css("right",$(".main-content-box-right").offset().left);
        }catch (e){}

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
                $(this).css("display","none");
            });
            $($(".optbtn")[activenow]).addClass("active");
            try{$($(".menu-list li")[optactive*5+menuactive]).addClass("active");}catch (e){}

            var active=".search";
            if(activenow==1){
                active=".add";
            }
//            $(active).addClass('flipInX animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
//                $(this).removeClass('flipInX animated');
//            });
            $(active).css("display","block");
        }
        $(".optbtn").unbind().bind("click", function () {
            var index=$(this).attr("index");
            if(index==activenow){
                return;
            }else{
                activenow=index;
                change();
            }
        });
        
    };
    return{
        addleftmenu:addleftmenu,
        autoline:autoline
    };
})();

//此方法已作废
var tip_edit1=(function () {

  var modl_list={
      nom:'<div class="edit-tip-box">' +
          '<ul>' +
          '<% for (var i=0;i<list.length;i++ ){%>'+
          '<li>' +
          '<div class="tipg">' +
          '<span class="tip-detail">' +
          '<span class="tipg-name"><%=list[i].name%></span>' +
          '<div class="group-edit hidden">' +
          '<input type="text" name="groupedit" value="<%=list[i].name%>" class="fn-input">' +
          '<button class="btn btn-llt" btndata-i="<%=list[i].id%>">OK</button></div>' +
          '</span>' +
          '<span class="action">' +
          '<a class="edit"   >' +
          '<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>' +
          '</a><a class="delete" >' +
          '<span class="glyphicon glyphicon-remove" btndata-i="<%=list[i].id%>" aria-hidden="true"></span>' +
          '</a></span>' +
          '</div>' +
          '</li>'+
          '<%}%>'+
          '</ul>' +
          '<% if (allowadd=="true"){%>'+
          '<div class="creat-group">' +
          '<div class="creat-group-name">添加</div>' +
          '<div class="creat-group-action hidden">' +
          '<input type="text" name="groupcreat" class="fn-input ">' +
          '<button class="btn bt-creat">添加</button>' +
          '<button class="btn bt-close">取消</button>' +
          '</div>' +
          '</div>' +'' +
          '<div class="edit-tip-box-remove"><span class="glyphicon glyphicon-remove" ></span></div>' +
          ''+
          '<%}%>'+
          '</div>',
      delbsure:'<div class="bdel-box">' +
          '<div class="bdel-box-body">' +
          '确定要删除“<%=name%>”吗？' +
    '</div>' +
    '<div class="bdel-box-foot">' +
          '<button class="btn bt-creat" id="suredel" >确定</button><button class="btn bt-close" id="delcl">取消</button></div>' +
          '</div></div>',
      tipmoal:'<div class="bdel-box">' +
          '<div class="bdel-box-body">' +
          '<%=text%>' +
          '</div>' +
          '<div class="bdel-box-foot">' +
          '<button class="btn bt-creat" id="tipmodel" >确定</button></div>' +
          '</div></div>',
      addlist:'<li>' +
          '<div class="tipg">' +
          '<span class="tip-detail">' +
          '<span class="tipg-name"><%=name%></span>' +
          '<div class="group-edit hidden">' +
          '<input type="text" name="groupedit" value="<%=name%>" class="fn-input">' +
          '<button class="btn btn-llt" btndata-i="<%=id%>">OK</button></div>' +
          '</span>' +
          '<span class="action">' +
          '<a class="edit"   >' +
          '<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>' +
          '</a><a class="delete" >' +
          '<span class="glyphicon glyphicon-remove" btndata-i="<%=id%>" aria-hidden="true"></span>' +
          '</a></span>' +
          '</div>' +
          '</li>'
  };

    var def={
        eidtbtn:function(){

        },
        eidtsuccess:function(){},
        delbtn: function () {

        },
        delsuccess:function(){},
        creatbtn:function(){},
        creatsuccess:function(){},
        $this:""
    };

   var config_function= function (opt) {
       if($(".edit-tip-box").length>0){
           return;
       }
       def= $.extend(def,opt);
       $(def.$this).addClass("active");
       var $group=$(def.$this).parents(".shgroup");
       var allowedit=($group.attr("allowedit")=="false")?"false":"true";
       var allowdelet=($group.attr("allowdelet")=="false")?"false":"true";
       var allowadd=($group.attr("allowadd")=="false")?"false":"true";
       var listhtml=$group.children(".tiplist").children("ul");
       var data=new Object();
       data.alloweeeedit=allowedit;
       data.allowdelet=allowdelet;
       data.allowadd=allowadd;
       var list=[];
       listhtml.children("li").each(function () {
           var info=new Object();
           info.name=$(this).html();
           info.id=$(this).attr("data-i");
            list.push(info);
       });
       data.list=list;

    var html=template.compile(modl_list.nom)(data);
       $("body").append(html);
        $(".edit-tip-box").css({top:$(def.$this).offset().top+23,left:$(def.$this).offset().left});
       $(".edit-tip-box-remove").click(function () {
           $(".edit-tip-box").remove();
           $(def.$this).removeClass("active");
       });

       $(".edit").click(function () {
                $(".tipg .action").addClass("hidden");
           var actiongroup=$(this).parents(".tipg").children(".tip-detail").children(".group-edit");
           var nametext=$(this).parents(".tipg").children(".tip-detail").children(".tipg-name");
           nametext.addClass("hidden");
           actiongroup.removeClass("hidden");
           var ortex=actiongroup.children("[name='groupedit']").val();
           actiongroup.children(".btn-llt").click(function () {
                if(actiongroup.children("[name='groupedit']").val()==ortex){
                    hidegruoaction(nametext,actiongroup);
                    return;
                }else{
                    def.eidtbtn(actiongroup.children("[name='groupedit']").val(),actiongroup.children("button").attr("btndata-i"));
                    editsuccess_f();
                }
           });
       });
       $(".delete").click(function () {
           hideedit();
           var nametext=$(this).parents(".tipg").children(".tip-detail").children(".tipg-name");
           var d=new Object();
           d.name=nametext.html();
           $(".edit-tip-box").append(template.compile(modl_list.delbsure)(d));
           $(this).parents("li");

           $(".bdel-box").css("top",$(this).parents("li")[0].offsetTop-$(".bdel-box").height());
           var delid=$(this).attr("btndata-i");
           $("#suredel").unbind().bind("click",function () {
               def.delbtn(delid);
               removedelbsure();
           });
       });
       if(allowadd=="true"){
           $(".creat-group-name").click(function () {
               $(this).addClass("hidden");
               $(this).next(".creat-group-action").removeClass("hidden");
               var $this=$(this);
               $(".bt-creat").click(function(){
                   if($("[name='groupcreat']").val()==""){
                       showtipmodel("请填写正确信息",$this);
                   }else{
                       def.creatbtn($("[name='groupcreat']").val());
                       cre_succ_action();
                   }
               });
               $(".bt-close").click(function(){
                   $(".creat-group-action").addClass("hidden");
                   $(".creat-group-name").removeClass("hidden");
                   $("[name='groupcreat']").val("");
               });
           });

       }

   };
    var hidegruoaction=function(showtex,hideaction){
        showedit();
        $(hideaction).addClass("hidden");
        $(showtex).removeClass("hidden");
    };
    var editsuccess_f=function(){
        var edittext=$(".tipg .tip-detail .tipg-name.hidden");
        var editaction=$(".tipg .tip-detail .tipg-name.hidden").next(".group-edit");
        edittext.html(editaction.children("[name='groupedit']").val());

        edittext.removeClass("hidden");
        editaction.addClass("hidden");
        $("[data-i='"+editaction.children("button").attr("btndata-i")+"']").html(editaction.children("[name='groupedit']").val());
        showedit();
    };
    var removedelbsure=function(){
        $(".bdel-box").remove();
        showedit();
    };
    var showtipmodel=function(text,$this){
        $(".bdel-box").remove();
        var data=new Object();
        data.text=text;
//        console.log($($this));
        $(".edit-tip-box").append(template.compile(modl_list.tipmoal)(data));
        hideedit();
        $(".bdel-box").css("bottom","30px");
        $("#tipmodel").click(function () {
            $(".bdel-box").remove();
            showedit();
        });
    };
    var hideedit=function(){
        $(".tipg .action").addClass("hidden");
    };
    var showedit= function () {
        $(".tipg .action").removeClass("hidden");
    };
    var cre_succ_action= function (json) {
        var data=new Object();
        data.name=$("[name='groupcreat']").val();
        data.id=Math.ceil(Math.random()*100);
        $(".edit-tip-box ul").append(template.compile(modl_list.addlist)(data));
        $(def.$this).parents(".shgroup").children().find("ul").append('<li  data-i="'+data.id+'">'+data.name+'</li>');
        $(".creat-group-action").addClass("hidden");
        $(".creat-group-name").removeClass("hidden");
        $("[name='groupcreat']").val("");
    };
    return{
        config_function:config_function,
        editsuccess:editsuccess_f
    };
})();
 var tip_edit=(function () {
     var def;
     var html_model='<div class="edit-tip-box">' +
         '<ul class="list">' +
         '<% for(var i=0;i<list.length;i++){ ' +
         'list[i].select=list[i].select||false;' +
         'list[i].no_delete=list[i].no_delete||"";' +
         '%>' +
         '<%if(list[i].select){%>' +
         '<li tip-l-i="<%=list[i].id %>" class="active" no_delete="<%print(list[i].no_delete) %>" ><%=list[i].name %></li>' +
         '<%}else{ %>' +
         '<li tip-l-i="<%=list[i].id %>" ><%=list[i].name %></li>' +
         '<%} %>' +
         '<%}%>' +
         '</ul>' +
         '<% if (allowadd){%>'+
         '<div class="creat-group">' +
         '<div class="creat-group-name">添加</div>' +
         '<div class="creat-group-action hidden">' +
         '<input type="text" name="groupcreat" class="fn-input ">' +
         '<button class="btn bt-creat">添加</button>' +
         '<button class="btn bt-close">取消</button>' +
         '</div>' +
         '</div>' +
         '<%}%>'+
         '<div class="b_opt"><button class="btn bt-close" id="delcl">取消</button><button class="btn  bt-creat" id="submit">保存</button></div>' +
         '</div>';
     var tipmoal='<div class="bdel-box">' +
     '<div class="bdel-box-body">' +
     '<%=text%>' +
     '</div>' +
     '<div class="bdel-box-foot">' +
     '<button class="btn bt-creat" id="tipmodel" >确定</button></div>' +
     '</div></div>';
     var config= function (opt) {
         if($(".edit-tip-box").length>0){
             return;
         }
        def={
            allowadd:false,
            list:[],
            $this:"",
            radio:false,//是否单选
            besure: function () {},
            complete: function () {},
            add: function () {}
        };
         def= $.extend({},def,opt);
         var html=template.compile(html_model)(def);
         $("body").append(html);
         $(def.$this).addClass("active");
         $(".edit-tip-box").css({top:$(def.$this).offset().top+25,left:$(def.$this).offset().left});
         click();
     };
     var click= function () {

        $(".list li").click(function () {
            if($(this).hasClass("active")&&$(this).attr("no_delete")!="true"){
                $(this).removeClass("active");
            }else{
                if(def.radio){
                    $(".list li").removeClass("active");
                }
                $(this).addClass("active");
            }
        });
         $("#submit").click(function () {
             def.besure();

         });
         $("#delcl").click(function () {
             close();
         });
         if(def.allowadd){
             $(".creat-group-name").click(function () {
                 $(this).addClass("hidden");
                 $(this).next(".creat-group-action").removeClass("hidden");
                 var $this=$(this);
                 $(".bt-creat").click(function(){
                     if($("[name='groupcreat']").val()==""){
                         showtipmodel("请填写正确信息",$this);
                     }else{
                         def.add();
                         closeadd();
                     }
                 });
                 $(".bt-close").click(function(){
                     closeadd();
                 });

                 var closeadd= function () {
                     $(".creat-group-action").addClass("hidden");
                     $(".creat-group-name").removeClass("hidden");
                     $("[name='groupcreat']").val("");
                 };
             });

         }

         $(".edit-tip-box").bind("click",function (e) {
             e.stopPropagation();
         });
         setTimeout(function () {
             $(window).bind("click", function () {
                 close();
             });
         },500);

     };
     var close= function () {
         $(def.$this).removeClass("active");
         $(".edit-tip-box").remove();
         $(window).unbind("click");
     };
     var showtipmodel=function(text,$this){
         $(".bdel-box").remove();
         var data=new Object();
         data.text=text;
         $(".edit-tip-box").append(template.compile(tipmoal)(data));
         $(".bdel-box").css("top",$($this).offset().top-88);
         $("#tipmodel").click(function () {
             $(".bdel-box").remove();
         });
     };
     return{
         config:config,
         close:close
     };
 })();

//详情信息项点击显示编辑框
var alone_edit=(function () {
    var config_function= function () {
        $(".canEdit").unbind().bind("click",function(){
            if($(this).next(".txtEdit").hasClass("lable_hidden")){
                var $this=$(this);
                $(this).addClass("lable_hidden");
                $(this).next(".txtEdit").removeClass("lable_hidden").addClass("lable_block");
                setTimeout(function(){
                    hide_editinput($this);
                },100);

                $(".txtInfo,.txtSave").click(function(e){
                    e.stopPropagation();
                });
                $("select").click(function(e){
                    e.stopPropagation();
                });

            }
        });
    };
    var hide_editinput= function (a) {
        $("body").unbind().bind("click",function(){
            close(a);
        });
    };
    function close(a) {
        $(a).next(".txtEdit").removeClass("lable_block").addClass("lable_hidden");
        $(a).removeClass("lable_hidden");
        $("body").unbind();
    }
    return{
        config_function:config_function,
        close:close
    };

})();
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
    };
    var getlogintype= function () {
        var logintype=getcookie("logintype");
        if(logintype==null||logintype=="null"){
            logintype="PC";
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
//数组移除某个元素
Array.prototype.baoremove = function(dx)
{
    if(isNaN(dx)||dx>this.length){return false;}
    this.splice(dx,1);
};
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
                date= new Date(a);
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