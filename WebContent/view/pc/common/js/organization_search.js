/**
 * Created by shbs-tp001 on 15-9-7.
 */
var optactive=0;
var menuactive=0;
var competition;
var kuang;
var hangye;
var payatt;//关注
var stage;//阶段
var currency;//币种
var scale;//规模
var feature;//特征
var bground;//背景
var deleteflag;//状态
var invtype;//类型
$(function () {
    $("input").val("");
    $("input[type='hidden']").val("1");
    
    $("ul[ro]").each(function () {
        if($(this)[0].scrollHeight-3>$(this).parents(".shgroup").height()){
            $(this).parents(".shgroup").addClass("havemore");
        }
    });
    click_lisetn();
    search_organization_tip.config();
    search_company_tip.config();

});
//监听事件
function click_lisetn(){
    $(".havemore .more").click(function () {
        showmore($(this));
    });
    $(".closeshearch").click(function () {
        showselist($(this));
    });
    $(".tiplist li").click(function () {
        if($(this).attr("active")!="active"){
            var data=new Object();
            var info=new Object();
            info.dev=$(this).attr("tip");
            info.tip=$(this).html();
            info.ro=$(this).parents("ul").attr("ro");
            info.cl=$(this).attr("cl");
            data.info=info;
            if($(this).attr("gm")!="undefined"){
                if(info.cl.indexOf("usd")>0&&$($("[bz]")[0]).attr("active")!="active"){//美元
                    $($("ul[ro='3'] li")[0]).click();
                }else if(info.cl.indexOf("cnyscale")>0&&$($("[bz]")[1]).attr("active")!="active"){
                    $($("ul[ro='3'] li")[1]).click();
                }
            }
            var html=template.compile(model_data.condition)(data);
            $("#condition").append(html);
            $(this).attr("active","active");
            delcondition();
        }else{
            if($(this).html()=="USD"){
                $("[cl*='usd'][active='active']").click();
            }else if($(this).html()=="CNY"){
                $("[cl*='cnyscale'][active='active']").click();
            }
            $(this).attr("active","");
            $(this).removeClass("active");
            $(".condition span[ro='"+$(this).parents("ul").attr("ro")+"'][cl='"+$(this).attr("cl")+"']").parents(".condition").remove();


        }

    });
    $(".search-btn").click(
        function() {
            $.showloading();
            var name = $("[name='name']").val();
            var data = {};
            
            //保存cookie
            if(name!=""){
            	saveCookie({name:name,type:'搜索',href:"gotoInvestmentList.html?logintype="
                    + cookieopt.getlogintype()});
            }
            
            data.type = "text";
            data.name = name;
            sessionStorage.setItem("organization_search", JSON
                .stringify(data));
            $("#form")[0].action = "gotoInvestmentList.html?logintype="
                + cookieopt.getlogintype();
            $("#form").submit();
            $.hideloading();
        });
    $(".btn_search").click(
        function() {
           searchdatainit();
           subpost();
        });

}
//保存搜索历史记录
function saveCookie(cook){
	var cookdata = [];
    var searchList=unescape(cookieopt.getcookie("searchList"));
    if(searchList!=null&&searchList!="null"&& searchList!=""){
    	cookdata=eval(searchList);
        var bool=true;
        for(var i=0;i<cookdata.length;i++){
        	if(cookdata[i].name==cook.name){
        		bool=false;
        	}
        }
        if(bool){
        	if(cookdata.length>9){
        		cookdata.splice(0,1);
        	}
        	cookdata.push(cook);
        	cookieopt.setcookie("searchList",JSON
                    .stringify(cookdata));
        }
    }else{
    	cookdata.push(cook);
    	cookieopt.setcookie("searchList",JSON
                .stringify(cookdata));
    }
}

//数据模板
var model_data={
    condition:
        '<div class="condition"><span><%=info.dev%>：</span><span class="tip"><%=info.tip%></span><span class="del" ro=<%=info.ro%> cl=<%=info.cl%> ><span class="glyphicon glyphicon-remove"></span></span></div>',
    tiplist: '<ul>' +
        '<%for(var i=0;i<list.length;i++){%>' +
        '<li data-code="<%=list[i].base_comp_code%>"><%=list[i].base_comp_name%></li>' +
        '<%}%>' + '</ul>',
    tiplist_or : '<ul class="searchul">'
        + '<%for(var i=0;i<investmentlist.length;i++){%>'
        + '<li href="findInvestmentById.html?id=<%=investmentlist[i].code%>&logintype=<%=logintype()%>"><b><%=investmentlist[i].nameStr%></b><span><%=investmentlist[i].name%></span></li>'
        + '<%}%>'
        + '<%for(var i=0;i<investorList.length;i++){%>'
        + '<li href="findInvestorDeatilByCode.html?code=<%=investorList[i].code%>&logintype=<%=logintype()%>"><b><%=investorList[i].nameStr%></b><span><%=investorList[i].name%></span></li>'
        + '<%}%>' + '</ul>',
    searchlist:'<ul>'
        + '<%for(var i=0;i<searchList.length;i++){%>'
        + '<li href="<%=searchList[i].href%>"><b><%=searchList[i].type%></b><span><%=searchList[i].name%></span></li>'
        + '<%}%>' + '</ul>'

};
//筛选条件组展示更多
function showmore(a){
    if($(a).parents(".shgroup").hasClass("heightauto")){
        $(a).parents(".shgroup").removeClass("heightauto");
        $(a).html(' 更多<span class="glyphicon glyphicon-chevron-down" ></span>');
    }else{
        $(a).parents(".shgroup").addClass("heightauto");
        $(a).html(' 收起<span class="glyphicon glyphicon-chevron-up" ></span>');

    }
}
//展示全部筛选条件组
function showselist(a){
    $(".groupmore").slideToggle(function(){
        if($(".groupmore")[0].style.display=="none"){
            $(a).html('展开<span class="glyphicon glyphicon-chevron-down" ></span>');
        }else{
            $(a).html('收起<span class="glyphicon glyphicon-chevron-up" ></span>');
        }
    });

}
//删除筛选条件框
function delcondition(){
    $(".condition .del").unbind().bind("click",function () {
        var ro=$(this).attr("ro");
        var cl=$(this).attr("cl");
        $("ul[ro='"+ro+"'] li[cl='"+cl+"']").click();
        $(this).parents(".condition").remove();
    });
}
//投资机构搜模块索提示
var search_organization_tip = (function() {
    var timefunction;
    var outtime=300;//搜索文本框变化时的延时时间
    var doajax="";//判断上一次请求是否完全返回
    var $this="";
    var config = function() {

        $(".search-input").bind("input propertychange", function() {
            if ($(this).val().trim()== "") {
            	cookSearch();
            } else {
                if (timefunction) {
                    clearTimeout(timefunction);
                }
                timefunction = setTimeout(function() {
                    if (doajax != "") {
                        doajax.abort();//上一次请求作废
                    }
                    selecttip();
                    outtime = 1000;
                }, outtime);
            }
        });
        $(".search-input").blur(function() {
            setTimeout(function() {
                $(".search_b_list").slideUp(200);
            }, 100);
        });
        $(".search-input").focus(function() {

            inputkeybod();
            if ($(this).val().length > 0&& $(".search_b_list ul li").length > 0) {
                $(".search_b_list").slideDown(200);
            }else{
                cookSearch();
            }
        });
    };

    //cookie存储搜索历史
    var cookSearch=function(){
        var searchList=unescape(cookieopt.getcookie("searchList"));
        if(searchList!=null&& searchList!="null"&&searchList!=""){
            var data={searchList:eval(searchList)};
            var html = template.compile(model_data.searchlist)(data);
            $(".search_b_list").html(html);
            $(".search_b_list").slideDown(200);
            cookClick();
        }else{
            $(".search_b_list").slideUp(200);//模糊查询下拉框收起
        }
    };

    //cookie数据单击事件
    var cookClick=function(){
        $(".search_b_list ul li").unbind().bind("click", function() {
        	
            $(".search-input").val($(this).children("span").html());
            saveCookie({name:$(this).children("span").html(),type:$(this).children("b").html(),href:$(this).attr("href")});

            var type=$(this).children("b").html();
            if(type=="搜索") {
                var data = {};
                data.type = "text";
                data.name = $(this).children("span").html();
                sessionStorage.setItem("organization_search", JSON
                    .stringify(data));
                $("#form")[0].action = $(this).attr("href");
                $("#form").submit();
                $.hideloading();
            }else{
                window.location.href=$(this).attr("href");
            }




        });
    };

    var selecttip = function() {
        if ($("[name='name']").val() != "") {
            $.showloading();

            doajax = $.ajax({
                async : true,
                dataType : 'json',
                type : 'post',
                data : {
                    name : $("[name='name']").val().trim(),
                    logintype : cookieopt.getlogintype()
                },
                url : "search.html",
                success : function(data) {
                    $.hideloading();
                    if (data.investmentlist.length > 0
                        || data.investorList.length > 0) {
                        var html = template.compile(model_data.tiplist_or)(data);
                        $(".search_b_list").html(html);
                        $(".search_b_list").slideDown(200);

                        tipclick();
                        inputkeybod();
                    } else {
                        $(".search_b_list").hide();
                    }
                    doajax = "";
                }

            });
        }

        var tipclick = function() {
            $(".search_b_list ul li").unbind().bind("click", function() {
                $(".search-input").val($(this).children("span").html());
                saveCookie({name:$(this).children("span").html(),type:$(this).children("b").html(),href:$(this).attr("href")});
                window.location.href=$(this).attr("href")+"&type=search";


            });
        };

    };


    return {
        config : config
    };
})();


function inputkeybod(){
    var zonghe=$(".search_b_list ul li").length;
    var dangqian=$(".search_b_list ul li.keybod").index();


        $("[name='name']").unbind("keydown");
        $("[name='name']").keydown(function(e){

                if(event.which==38){
                    $(".search_b_list ul li.keybod").removeClass("keybod");
                    if(dangqian==0){
                        dangqian=zonghe;
                    }
                    $(".search_b_list ul li").eq(dangqian-1).addClass("keybod");
                    dangqian--;

                }else if (event.which==40){
                    $(".search_b_list ul li.keybod").removeClass("keybod");
                    if(dangqian==zonghe-1){

                        dangqian=-1;
                    }
                    $(".search_b_list ul li").eq(dangqian+1).addClass("keybod");
                    dangqian++;
                }
            if(event.which==13&&dangqian==-1){
                if( $(".search-input").val().length>0){
                    $(".search-btn").click();
                }

            }else if(event.which==13&&dangqian!=-1){
               var hh= $(".search_b_list ul li").eq(dangqian).attr("href");
               if($(".search-input").val().length>0){
            	   	var url=hh+"&type=search";
            	   	$(".search-input").val("");
                    saveCookie({name:$(".search_b_list ul li").eq(dangqian).children("span").html(),
                    	type:$(".search_b_list ul li").eq(dangqian).children("b").html(),
                    	href:hh});
                    window.location.href=url;
               }else{
            	   var type=$(".search_b_list ul li").eq(dangqian).children("b").html();
                   if(type=="搜索") {
                	   $(".search-input").val($(".search_b_list ul li").eq(dangqian).children("span").html());
                       var data = {};
                       data.type = "text";
                       data.name = $(".search_b_list ul li").eq(dangqian).children("span").html();
                       sessionStorage.setItem("organization_search", JSON.stringify(data));
                       $("#form")[0].action = hh;
                       $("#form").submit();
                       $.hideloading();
                   }else{
                	   window.location.href=hh;
                   }
               }

            }
        });



}
function checsubmit(){
	if($(".search-input").val().length>0){
		return true;
	}else{
		return false;
	}
}

var search_company_tip=(function(){
    var timefunction;
    var outtime=300;//搜索文本框变化时的延时时间
    var doajax="";//判断上一次请求是否完全返回
    var $this="";
    var config=function(){
        $(".company1,.company2,.company3").bind("input propertychange",function(){
            $this=$(this);
            if($(this).val().trim()==""){
                $(".companytip").slideUp(200);//模糊查询下拉框收起
            }else{
                if(timefunction){
                    clearTimeout(timefunction);
                }
                timefunction=setTimeout(function(){
                    if(doajax!=""){
                        doajax.abort();//上一次请求作废
                    }
                    selecttip();
                    outtime=1000;
                },outtime);
            }
//            tipclick();
        });
        $(".company1,.company2,.company3").blur(function () {
            var $this= $(this);
            setTimeout(function () {
                $this.parents("span").children(".companytip").slideUp(200);
            },100);

        });
        $(".company1,.company2,.company3").focus(function () {
            if($(this).val().length>0){
                $(this).parents("span").children(".companytip").slideDown(200);
                setTimeout(function () {
                    $(".box-body").scrollTop(10000);
                },200);
            }
        });

    };


    var selecttip=function(){
        if($this.val()!=""){
            doajax=$.ajax({
                async: true,
                dataType: 'json',
                type: 'post',
                data:{
                    name:$this.val().trim(),
                    logintype:cookieopt.getlogintype()
                },
                url:"searchCompany.html",
                success: function (data) {
                    if(data.list.length>0){
                        var html=template.compile(model_data.tiplist)(data);
                        $this.next(".companytip").html(html);
                        $this.next(".companytip").slideDown(200);
                        tipclick();
                    }else{
                        $(".companytip").hide();
                    }
                    doajax="";
                }
            });
        }
        var  tipclick=function(){
            $(".companytip ul li").unbind().bind("click",function(){
                $(this).parents("span").children("input").val($(this).html());
                $(this).parents("ul li").each(function(){
                    $(this).removeAttr("compet-active");
                });
                $(this).attr("compet-active","active");
            });
        };
    };
    return {
        config:config
    };
//    }
})();
template.helper("logintype", function() {
    return cookieopt.getlogintype();
});
function searchdatainit(){
    competition="";
    kuang="";
    hangye="";
    payatt="";//关注
    stage="";//阶段
    currency="";//币种
    scale="";//规模
    feature="";//特征
    bground="";//背景
    deleteflag="";//状态
    invtype="";//类型
    //竞争公司
    $("[compet-active='active']").each(function(){
        if($(this).html()==$(this).parents("span").children("[name='company']").val()){
            if(competition==""){
                competition=($(this).attr("data-code"))+",";
            }else{
                competition+=($(this).attr("data-code"))+",";
            }
        }


    });
    //筐
    $("[ro='0'] li[active='active']").each(function(){
        if(kuang==""){
            kuang=($(this).attr("cl"))+",";
        }else{
            kuang+=($(this).attr("cl"))+",";
        }
    });
    //行业
    $("[ro='1'] li[active='active']").each(function(){
        if(hangye==""){
            hangye=($(this).attr("cl"))+",";
        }else{
            hangye+=($(this).attr("cl"))+",";
        }
    });
    //近期关注
    $("[ro='2'] li[active='active']").each(function(){
        if(payatt==""){
            payatt=($(this).attr("cl"))+",";
        }else{
            payatt+=($(this).attr("cl"))+",";
        }

    });
    //币种
    $("[ro='3'] li[active='active']").each(function(){
        if(currency==""){
            currency=($(this).attr("cl"))+",";
        }else{
            currency+=($(this).attr("cl"))+"";
        }
        //currency=","+($(this).attr("cl"));
    });

    //规模
    $("[ro='4'] li[active='active']").each(function(){
        // scale=","+($(this).attr("cl"));
        if(scale==""){
            scale=($(this).attr("cl"))+",";
        }else{
            scale+=($(this).attr("cl"))+",";
        }
    });
    //阶段
    $("[ro='5'] li[active='active']").each(function(){
        //stage=","+($(this).attr("cl"));
        if(stage==""){
            stage=($(this).attr("cl"))+",";
        }else{
            stage+=($(this).attr("cl"))+",";
        }
    });
    //特征
    $("[ro='6'] li[active='active']").each(function(){
        //feature=","+($(this).attr("cl"));
        if(feature==""){
            feature=($(this).attr("cl"))+",";
        }else{
            feature+=($(this).attr("cl"))+",";
        }
    });
    //背景
    $("[ro='7'] li[active='active']").each(function(){
        if(bground==""){
            bground=($(this).attr("cl"))+",";
        }else{
            bground+=($(this).attr("cl"))+",";
        }
    });
    //状态
    $("[ro='8'] li[active='active']").each(function(){
        if(deleteflag==""){
        	deleteflag=($(this).attr("cl"))+",";
        }else{
        	deleteflag+=($(this).attr("cl"))+",";
        }
    });
  //类型
    $("[ro='9'] li[active='active']").each(function(){
        //feature=","+($(this).attr("cl"));
        if(invtype==""){
        	invtype=($(this).attr("cl"))+",";
        }else{
        	invtype+=($(this).attr("cl"))+",";
        }
    });
}
function subpost(){
    var type="hidden";
    var w = document.createElement("form");
    document.body.appendChild(w);
    var a= document.createElement("input");
    var b= document.createElement("input");
    var c= document.createElement("input");
    var d= document.createElement("input");
    var e= document.createElement("input");
    var f= document.createElement("input");
    var g= document.createElement("input");
    var h= document.createElement("input");
    var k= document.createElement("input");
    var l= document.createElement("input");
    var m= document.createElement("input");//2016-4-21 add 状态
    var n= document.createElement("input");//2016-5-3 add 类型
    var o= document.createElement("input");
    a.type= b.type= c.type= d.type= e.type= f.type= g.type= h.type= k.type= l.type =m.type=n.type=o.type = type;
    a.name="kuang";
    a.value=kuang;
    b.name="hangye";
    b.value=hangye;
    c.name="payatt";
    c.value=payatt;
    d.name="currency";
    d.value=currency;
    e.name="scale";
    e.value=scale;
    f.name="stage";
    f.value=stage;
    g.name="feature";
    g.value=feature;
    h.name="competition";
    l.value=bground;
    l.name="bground";
    h.value=competition;
    k.name="logintype";
    k.value=cookieopt.getlogintype();
    m.name="deleteflag";
    m.value=deleteflag;
    n.name="invtype";
    n.value=invtype;
    o.name="type";
    o.value="search";
    
    w.appendChild(a);
    w.appendChild(b);
    w.appendChild(c);
    w.appendChild(d);
    w.appendChild(e);
    w.appendChild(f);
    w.appendChild(g);
    w.appendChild(h);
    w.appendChild(k);
    w.appendChild(l);
    w.appendChild(m);
    w.appendChild(n);
    w.appendChild(o);
    
    w.action = "searchMoreCondition.html";
    w.method="post";
    var data={};
    data.type="scre";
    data.kuang=kuang;
    data.hangye=hangye;
    data.payatt=payatt;
    data.currency=currency;
    data.scale=scale;
    data.stage=stage;
    data.feature=feature;
    data.competition=competition;
    data.bground=bground;
    data.logintype=cookieopt.getlogintype();
    data.deleteflag=deleteflag;
    data.invtype=invtype;
    sessionStorage.setItem("organization_search",JSON.stringify(data));

    w.submit();
}