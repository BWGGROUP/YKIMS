/**
 * Created by shbs-tp001 on 15-9-21.
 */
var searchtype="";
var tradesearch=sessionStorage.getItem("trade_search");
$(function(){
    init();//页面初始化

});
function init(){
    $("ul[ro]").each(function () {
        if( $(this)[0].scrollHeight>$(this)[0].clientHeight){
            $(this).parents(".shgroup").addClass("havemore");
        }
    });
    $(".havemore .more").click(function () {
        showmore($(this));
    });
    $(".tiplist li").click(function () {
//&&$(this).offset().top<($(this).parents(".box-body").height()+$(this).parents(".box-body").offset().top)
        if($(this).attr("active")!="active"){
            if($(this).parent("ul").attr('ro')=='2'){
                $("ul[ro='2'] li[active='active']").click();
                $(".starttime,.endtime").val("").attr("disabled","disabled");

            }
            var data=new Object();
            var info=new Object();
            info.dev=$(this).attr("tip");
            info.tip=$(this).html();
            info.ro=$(this).parents("ul").attr("ro");
            info.cl=$(this).attr("cl");
            data.info=info;
            var html=template.compile(model_data.condition)(data);
            $("#condition").append(html);
            $(this).attr("active","active");
            $(this).addClass("active");
            delcondition();
        }else{
            $(this).attr("active","");
            $(this).removeClass("active");
            $(".condition span[ro='"+$(this).parents("ul").attr("ro")+"'][cl='"+$(this).attr("cl")+"']").parents(".condition").remove();
            if(!$("ul[ro='2'] li[active='active']").length){
                $(".starttime,.endtime").removeAttr("disabled");
            }
        }

    });
    $(".submit").bind("click",function(){
    	searchtype="search";
        listsearch.congfig_data();
    });
    
    
    if(backtype!=null&&backtype!=""){
    	if(backtype=="searchtrade"){
            tradesearch=eval("("+tradesearch+")");
            for(var i=0;i<tradesearch.induList.length;i++){
            	$("ul[ro='0'] li[cl='"+tradesearch.induList[i]+"']").click();
            }
            for(var i=0;i<tradesearch.stageList.length;i++){
            	$("ul[ro='1'] li[cl='"+tradesearch.stageList[i]+"']").click();
            }
            if(tradesearch.datearea!="0"){
            	$("ul[ro='2'] li[cl='"+tradesearch.datearea+"']").click();
            }
            if(tradesearch.starttime!=""){
            	$(".starttime").val(tradesearch.starttime.substring(0,7));
            }
            if(tradesearch.endtime!=""){
            	$(".endtime").val(tradesearch.endtime.substring(0,7));
            }
            
            listsearch.data.induList=tradesearch.induList;
            listsearch.data.stageList=tradesearch.stageList;
            listsearch.data.endtime=tradesearch.endtime;
            listsearch.data.starttime=tradesearch.starttime;
            listsearch.data.datearea=tradesearch.datearea;
            listsearch.data.page=tradesearch.page;
            listsearch.select();

        }
    }
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
//筛选条件组展示更多
function showmore(a){
    if($(a).parents(".shgroup").hasClass("heightauto")){
        $(a).parents(".shgroup").removeClass("heightauto");
        $(a).html('<span class="glyphicon glyphicon-chevron-down" ></span>');
    }else{
        $(a).parents(".shgroup").addClass("heightauto");
        $(a).html('<span class="glyphicon glyphicon-chevron-up" ></span>');

    }
}
var listsearch=(function(){
    var data={};
    var tabledata={};
    var congfig_data=function(){
        if($("starttime").val()!=""){
            var starttime=new Date($(".starttime").val());
            if(!isNaN(starttime.getTime())){
                starttime=starttime.format(starttime,"yyyy-MM-dd");
            }else{
                starttime="";
            }

        }else{
            starttime="";
        }
        if($("endtime").val()!=""){
            var endtime=new Date($(".endtime").val());

            if(!isNaN(endtime.getTime())){
                endtime.setMonth(endtime.getMonth()+1);
                endtime.setDate(endtime.getDate()-1);
                endtime=endtime.format(endtime,"yyyy-MM-dd");
            }else{
                endtime="";
            }
        }else{
            endtime="";
        }
        if(starttime>=endtime&&endtime!=""){
            $.showtip("开始时间不得大于截止时间");
            setTimeout(function () {
                $.hidetip();
            },2000);
            return;
        }
        data.induList=[];
        data.stageList=[];
        data.endtime=endtime;
        data.starttime=starttime;
        data.datearea=0;
        //行业
        $("span[ro='0']").each(function () {
            data.induList.push($(this).attr("cl"));
        });
        //阶段
        $("span[ro='1']").each(function () {
            data.stageList.push($(this).attr("cl"));
        });
        data.page={
            pageCount:1
        };
        if($("ul[ro='2'] li[active='active']").length){
            data.datearea=$("ul[ro='2'] li[active='active']").attr("cl");
        }
        select();
    };
    var select=function(){
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "trade_searchlist.html",
            data: {
                starttime: data.starttime,
                endtime:data.endtime,
                induList:JSON.stringify(data.induList),
                stageList:JSON.stringify(data.stageList),
                pageCount:data.page.pageCount,
                datearea:data.datearea,
                type:searchtype,
                logintype: cookieopt.getlogintype()
            },
            success: function (json) {
                $.hideloading();
                searchtype="";
                tabledata=json;
                data.page=json.page;
                if(json.message=="success"){
                    var html=template.compile(model_data.tablelist)(json);
                    $("#tablecontent").html(html);
                    $(".totalSize").html("共"+data.page.totalCount+"条");
                    $("#trcontent").show();
                    click();
                    setTimeout(function () {
                        $('body').scrollTop( $('body')[0].scrollHeight );
                    },50);
                }else if(json.message=="nomore"){
                    var html='<tr><td  colspan="4" style="width:100%;">暂无数据</td></tr>';
                    $("#tablecontent").html(html);
                    $("#trcontent").show();
                }
                jqPagination();

            }
        });
    };
    var jqPagination= function () {
        if(Number(data.page.totalPage)>0){
            $('.pagination').jqPagination({
                link_string	: '/?page={page_number}',
                max_page	:data.page.totalPage,
                current_page:data.page.pageCount,
                paged		: function(a) {
                    if(a!=data.page.pageCount){
                        data.page.pageCount=a;
                        select();
                    }
                }
            });
            $('.pageaction').show();
        }else{
            $('.pageaction').hide();
        }
    };
    var click= function () {
        $("#tablecontent tr").each(function (index,el) {
            $(this).bind("click", function () {
            	var type="hidden";
                var w = document.createElement("form");
                document.body.appendChild(w);
                var a= document.createElement("input");
                var b= document.createElement("input");
                var c= document.createElement("input");

                a.type= b.type= c.type= type;
                a.name="logintype";
                a.value=cookieopt.getlogintype();
                b.name="tradeCode";
                b.value=tabledata.list[index].base_trade_code;
                c.name="backherf";
                c.value='searchtrade';

                w.appendChild(a);
                w.appendChild(b);
                w.appendChild(c);

                w.action = "findTradeDetialInfo.html";
                w.method="post";

                sessionStorage.setItem("trade_search",JSON.stringify(data));

                w.submit();
//                window.location.href="findTradeDetialInfo.html?logintype="+cookieopt.getlogintype()+"&tradeCode="+tabledata.list[index].base_trade_code;//跳转到交易详情
            });

            $(this).children("td").children(".company").bind("click", function (e) {
            	var type="hidden";
                var w = document.createElement("form");
                document.body.appendChild(w);
                var a= document.createElement("input");
                var b= document.createElement("input");
                var c= document.createElement("input");

                a.type= b.type= c.type=type;
                a.name="logintype";
                a.value=cookieopt.getlogintype();
                b.name="backtype";
                b.value="searchtrade";
                c.name="code";
                c.value=tabledata.list[index].base_comp_code;

                w.appendChild(a);
                w.appendChild(b);
                w.appendChild(c);

                w.action = "findCompanyDeatilByCode.html";
                w.method="post";

                sessionStorage.setItem("trade_search",JSON.stringify(data));


                w.submit();
//            	window.location.href="findCompanyDeatilByCode.html?logintype="+cookieopt.getlogintype()+"&code="+tabledata.list[index].base_comp_code;//跳转到公司详情
                e.stopPropagation();
            });
        });
    };
    return{
        select:select,
        congfig_data:congfig_data,
        data:data
    };
})();
//数据模板
var model_data={
    condition:
        '<div class="condition"><span><%=info.dev%>：</span><span class="tip"><%=info.tip%></span><span class="del" ro=<%=info.ro%> cl=<%=info.cl%> ><span class="glyphicon glyphicon-remove"></span></span></div>',
    tablelist:'<% for (var i=0;i<list.length;i++ ){%>' +
        ' <tr>' +
        '<td><%= substr(list[i].base_trade_date,7)%></td>' +
        '<td> <a class="company"><%= list[i].base_comp_name%></a></td>' +
        '<td><%= list[i].base_trade_money%></td>' +
        '<td>' +
        '<% for (var j=0;j<list[i].view_investment_cont.length;j++ ){%>' +
        '<span class="comp"><%= list[i].view_investment_cont[j].name%></span>' +
        '<%}%>' +
        '</td>' +
        '</tr>' +
        '<%}%>'

};
template.helper("substr", function (a,b) {
    a= a.substring(0,b);
    return a;
});