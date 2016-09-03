/**
 * Created by shbs-tp001 on 15-9-10.
 */
var optactive=0;
var menuactive=1;
var searchtype="";
var tradesearch=sessionStorage.getItem("trade_search");

$(function () {
    $("ul[ro]").each(function () {
        if( $(this)[0].scrollHeight-5>$(this).parents(".shgroup").height()){
            $(this).parents(".shgroup").addClass("havemore");
        }

    });
    click_lisetn();

    if(backtype!=null){
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

});
function click_lisetn() {


    $(".havemore .more").click(function () {
        showmore($(this));
    });
    $(".tiplist li").click(function () {

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
    $(".screen").click(function () {
    	searchtype="search";
        listsearch.congfig_data();
    });

    $('#dp1').datepicker({
        format: 'yyyy-mm',
        viewMode:1,
        minViewMode:1
    });
    $('#dp2').datepicker({
        format: 'yyyy-mm',
        viewMode:1,
        minViewMode:1
    });
}

//数据模板
var model_data={
    condition:
        '<div class="condition"><span><%=info.dev%>：</span><span class="tip"><%=info.tip%></span><span class="del" ro=<%=info.ro%> cl=<%=info.cl%> ><span class="glyphicon glyphicon-remove"></span></span></div>',
    tablelist:'<% for (var i=0;i<list.length;i++ ){%>' +
        ' <tr class="tr_detail">' +
        '<td><%= substr(list[i].base_trade_date,7)%></td>' +
        '<td> <a class="companyname"><%= list[i].base_comp_name%></a></td>' +
        '<td><%= list[i].base_trade_stagecont%></td>' +
        '<td><%= list[i].base_trade_money%></td>' +
        '<td>' +
        '<% for (var j=0;j<list[i].view_trade_inducont.length;j++ ){%>' +
        '<span class="comp"><%= list[i].view_trade_inducont[j].name%></span>' +
        '<%}%>' +
        '</td>' +
        '<td>' +
        '<% for (var j=0;j<list[i].view_investment_cont.length;j++ ){%>' +
        '<span class="comp"><%= list[i].view_investment_cont[j].name%></span>' +
        '<%}%>' +
        '</td>' +
        '</tr>' +
        '<%}%>'

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
//删除筛选条件框
function delcondition(){
    $(".condition .del").unbind().bind("click",function () {
        var ro=$(this).attr("ro");
        var cl=$(this).attr("cl");
        $("ul[ro='"+ro+"'] li[cl='"+cl+"']").click();
        $(this).parents(".condition").remove();
    });
}



 function clearDate(){
     $("#dp1").val("");
     $("#dp2").val("");
 }
var listsearch=(function(){
    var data={
    };
    var tabledata={};
    var congfig_data=function(){
        if($(".starttime").val()!=""){
            var starttime=new Date($(".starttime").val().replace(/-/g,"/"));
            if(!isNaN(starttime.getTime())){
                starttime=starttime.format(starttime,"yyyy-MM-dd");
            }else{
                starttime="";

            }

        }else{
            starttime="";
        }
        if($(".endtime").val()!=""){
            var endtime=new Date($(".endtime").val().replace(/-/g,"/"));

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
                    $("#trcontent").show();
                    $("#pagination1").show();
                    click();
                    setTimeout(function () {
                        $('body').scrollTop( $('body')[0].scrollHeight );
                    },50);
                    if(data.page.totalCount>0){
                    	$(".totalSize").show();
                    	$(".totalSize").html("<li>共"+data.page.totalCount+"条</li>");
                    }else{
                    	$(".totalSize").hide();
                    }
                }else if(json.message=="nomore"){
                    var html='<tr><td  colspan="6" style="width:100%;">暂无数据</td></tr>';
                    $("#tablecontent").html(html);
                    $("#trcontent").show();
                    $("#pagination1").hide();
                    $(".totalSize").hide();
                }
                jqPagination();
                
            }
        });
    };
    var jqPagination= function () {

            $.jqPaginator('#pagination1', {
                totalPages: data.page.totalPage,
                visiblePages: 5,
                currentPage: data.page.pageCount,
                onPageChange: function (num, type) {
                    if(data.page.pageCount!=num){
                        data.page.pageCount=num;
                        select();
                    }

                }
            });
            

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

//                $.openLink("findTradeDetialInfo.html?logintype="+cookieopt.getlogintype()+"&tradeCode="+tabledata.list[index].base_trade_code);//跳转到交易详情

            });

            $(this).children("td").children(".companyname").bind("click", function (e) {
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

//                $.openLink("findCompanyDeatilByCode.html?logintype="+cookieopt.getlogintype()
//                    +"&code="+tabledata.list[index].base_comp_code);//跳转到公司详情
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
template.helper("substr", function (a,b) {
    a= a.substring(0,b);
    return a;
});