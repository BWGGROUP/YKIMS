/**
 * Created by shbs-tp001 on 15-9-10.
 */
var optactive=0;
var menuactive=2;
var outtime=300;//搜索文本框变化时的延时时间
var timefunction;
var doajax="";//判断上一次请求是否完全返回
var searchtype="";//搜索按钮事件判别提交参数
var submittype="";//跳转详情返回类型
var company_search=sessionStorage.getItem("company_search");
$(function () {

    $("ul[ro]").each(function () {
        if( $(this)[0].scrollHeight>$(this).parents(".shgroup").height()){
            $(this).parents(".shgroup").addClass("havemore");
        }

    });

    click_lisetn();
    search_organization_tip.config();

    if(backtype!=""){
        company_search=eval("("+company_search+")");
        if(backtype=="searchcompany"){
            if(company_search.baskList!=null&&company_search.baskList!=""){
                for(var i= 0;i<company_search.baskList.length;i++){
                   $("li[cl="+company_search.baskList[i]+"]").click();
                }

            }
            if(company_search.induList!=null&& company_search.induList!=""){
                for(var i= 0;i<company_search.induList.length;i++){
                    $("li[cl="+company_search.induList[i]+"]").click();
                }
            }
            if(company_search.stageList!=null && company_search.stageList!=""){
                for(var i= 0;i<company_search.stageList.length;i++){
                    $("li[cl="+company_search.stageList[i]+"]").click();
                }
            }
            selecttable.data.baskList=company_search.baskList;
            selecttable.data.induList=company_search.induList;
            selecttable.data.stageList=company_search.stageList;
            selecttable.data.page=company_search.page;
            selecttable.select();
        }else if(backtype=="searchcname"){
            $(".search-input").val(company_search.searchname);
            searchcompany_byname.data.searchname=company_search.searchname;
            searchcompany_byname.data.page=company_search.page;
            searchcompany_byname.select();
        }
    }

});
function click_lisetn() {
    $(".havemore .more").click(function () {
        showmore($(this));
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
            var html=template.compile(model_data.condition)(data);
            $("#condition").append(html);
            $(this).attr("active","active");
            delcondition();
        }else{
            $(this).attr("active","");
            $(this).removeClass("active");
            $(".condition span[ro='"+$(this).parents("ul").attr("ro")+"'][cl='"+$(this).attr("cl")+"']").parents(".condition").remove();
        }

    });
    $(".screen").click(function () {
    	searchtype="search";
        $(".search-input").val("");
        selecttable.config_data();
    });
    $(".search-btn").bind("click", function () {
    	searchtype="search";
        searchcompany_byname.config();
    });

}

//数据模板
var model_data={
    condition:
        '<div class="condition"><span><%=info.dev%>：</span><span class="tip"><%=info.tip%></span><span class="del" ro=<%=info.ro%> cl=<%=info.cl%> ><span class="glyphicon glyphicon-remove"></span></span></div>',
    tiplist:'<%for(var i=0;i<list.length;i++){%>' +
        '<li data-code="<%=list[i].base_comp_code%>"><b>公司</b><span><%=list[i].base_comp_name%></span></li>' +
        '<%}%>',
    cooklist:'<%for(var i=0;i<list.length;i++){%>' +
        '<li data-code="<%=list[i].code%>"><b><%=list[i].type%></b><span><%=list[i].name%></span></li>' +
        '<%}%>',
    table:'<% for (var i=0;i<list.length;i++ ){%>' +
        '<tr>' +
        '<td ><a data-code="<%=list[i].base_comp_code%>" class="companyname"><%=list[i].base_comp_name%></a></td>' +
        '<td>' +
        '<% for (var j=0;j<list[i].view_comp_baskcont.length;j++ ){%>' +
        '<span class="comp"><%=list[i].view_comp_baskcont[j].name%></span>' +
        '<%}%>' +
        '</td>' +
        '<td>' +
        '<% for (var j=0;j<list[i].view_comp_inducont.length;j++ ){%>' +
        '<span class="comp"><%=list[i].view_comp_inducont[j].name%></span>' +
        '<%}%>' +
        '</td>' +
        '<td><%=list[i].base_comp_stagecont%></td>' +
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
        $("ul[ro='"+ro+"'] li[cl='"+cl+"']").attr("active","");
        $(this).parents(".condition").remove();
    });
}


//公司搜模块索提示
var search_organization_tip=(function(){

    var config=function(){
        $(".search-input").val("");
        $(".search-input").bind("input propertychange",function(){
            if ($(this).val().trim() == "") {
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
            tipclick();
        });
        $(".search-input").blur(function () {
            setTimeout(function () {
                $(".search_b_list").slideUp(200);
            },100);
        });
        $(".search-input").focus(function () {
            if ($(this).val().length > 0&& $(".search_b_list ul li").length > 0) {
                $(".search_b_list").slideDown(200);
            }else{
                cookSearch();
            }
        });
    };
    var  tipclick=function(){
        $(".search_b_list ul li").unbind().bind("click",function(){
            $(".search-input").val($(this).children("span").html());
            saveCookie({name:$(this).children("span").html(),type:$(this).children("b").html(),code:$(this).attr("data-code")});

            window.location.href="findCompanyDeatilByCode.html?code="+$(this).attr("data-code")
            +"&type=search&logintype="+cookieopt.getlogintype();
        });
    };
    var selecttip = function() {

        if ($(".search-input").val().trim() != "") {
            $.showloading();

            doajax = $.ajax({
                async : true,
                dataType : 'json',
                type : 'post',
                data : {
                    name :$(".search-input").val(),
                    pageCount:1,
                    logintype : cookieopt.getlogintype()
                },
                url : "company_searchlistbyname.html",
                success : function(data) {
                    $.hideloading();
                    if(data.message=="success"){
                        var html=template.compile(model_data.tiplist)(data);
                        $(".search_b_list ul").html(html);
                        if(data.list.length!=0){
                            $(".search_b_list").slideDown(200);
                        }
                        inputkeybod();
                    }else{
                        $(".search_b_list").slideUp(200);
                    }
                    tipclick();
                    doajax = "";
                }

            });
        }


    };


    //cookie存储搜索历史
    var cookSearch=function(){
        var searchList=unescape(cookieopt.getcookie("companyList"));
        if(searchList!=null&& searchList!="null"&&searchList!=""){
            var data={list:eval(searchList)};
            var html = template.compile(model_data.cooklist)(data);
            $(".search_b_list ul").html(html);
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
            var type=$(this).children("b").html();
            if(type=="搜索") {
                searchcompany_byname.config();
            }else{
                window.location.href="findCompanyDeatilByCode.html?code="+$(this).attr("data-code")+"&logintype="+cookieopt.getlogintype();
            }

        });
    };
    return {
        config:config
    };
})();
//公司筛选模块
var selecttable=(function () {
    var data={
        induList:[],
        stageList:[],
        baskList:[]
    };
    var tabledata={};
    function config_data(){
    	data.induList=[];
        data.baskList=[];
        data.stageList=[];
        //筐
        $("span[ro='0']").each(function () {
            data.baskList.push($(this).attr("cl"));
        });
        //行业
        $("span[ro='1']").each(function () {
            data.induList.push($(this).attr("cl"));
        });
        //阶段
        $("span[ro='2']").each(function () {
            data.stageList.push($(this).attr("cl"));
        });
        data.page={
            pageCount:1
        };
        select();
    };
    function select(){
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "company_searchlist.html",
            data: {
                induList:JSON.stringify(data.induList),
                stageList:JSON.stringify(data.stageList),
                baskList:JSON.stringify(data.baskList),
                pageCount:data.page.pageCount,
                type:searchtype,
                logintype: cookieopt.getlogintype()
            },
            success: function (json) {
                $.hideloading();
                searchtype="";
                tabledata=json;
                data.page=json.page;
                if(json.message=="success"){
                    var html=template.compile(model_data.table)(json);
                    $("#tablecontent").html(html);
                    $("#trcontent").show();
                    $("#pagination1").show();
                    if(data.page.totalCount>0){
                    	$(".totalSize").show();
                    	$(".totalSize").html("<li>共"+data.page.totalCount+"条</li>");
                    }else{
                    	$(".totalSize").hide();
                    }
                    submittype="searchcompany";//条件筛选
                    jumpclick();
                }else if(json.message=="nomore"){
                    var html='<tr><td  colspan="4" style="width:100%;">暂无数据</td></tr>';
                    $("#tablecontent").html(html);
                    $("#trcontent").show();
                    $("#pagination1").hide();
                    $(".totalSize").hide();
                }
                setTimeout(function () {
                    $('body').scrollTop( $('body')[0].scrollHeight );
                },50);
                jqPagination();
            }
        });
    }
    function jqPagination() {
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
    }

    return{
        config_data:config_data,
        select:select,
        data:data
    };
})();
//公司按名字搜索
var searchcompany_byname=(function(){
    var data={};
    var tabledata={};
    function config() {
        $(".del").click();
        data.page={
            pageCount:1
        };
        data.searchname=$(".search-input").val().trim();
        if($(".search-input").val().trim()) {
            saveCookie({name: data.searchname, type: "搜索", code: ""});
        }
        select();
    }

    function select() {
        $.showloading();
        $.ajax({
            async : true,
            dataType : 'json',
            type : 'post',
            data : {
                name : data.searchname,
                type : searchtype,
                pageCount:data.page.pageCount,
                logintype : cookieopt.getlogintype()
            },
            url : "company_searchlistbyname.html",
            success : function(json) {
                $.hideloading();
                searchtype="";
                tabledata=json;
                data.page=json.page;
                if(json.message=="success"){
                    var html=template.compile(model_data.table)(json);
                    $("#tablecontent").html(html);
                    $("#trcontent").show();
                    $("#pagination1").show();
                    setTimeout(function () {
                        $('body').scrollTop( $('body')[0].scrollHeight );
                    },50);
                    if(data.page.totalCount>0){
                    	$(".totalSize").show();
                    	$(".totalSize").html("<li>共"+data.page.totalCount+"条</li>");
                    }else{
                    	$(".totalSize").hide();
                    }
                    submittype="searchcname";//名称筛选
                    jumpclick();
                }else if(json.message=="nomore"){
                    var html='<tr><td  colspan="4" style="width:100%;">暂无数据</td></tr>';
                    $("#tablecontent").html(html);
                    $("#trcontent").show();
                    $("#pagination1").hide();
                    $(".totalSize").hide();
                }

                jqPagination();
            }

        });
    }
    function jqPagination() {
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
    }

    return{
        config:config,
        select:select,
        data:data
    };
})();


//保存搜索历史记录
function saveCookie(cook){
    var cookdata = [];
    var searchList=unescape(cookieopt.getcookie("companyList"));
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
            cookieopt.setcookie("companyList",JSON.stringify(cookdata));
        }
    }else{
        cookdata.push(cook);
        cookieopt.setcookie("companyList",JSON.stringify(cookdata));
    }

}


function jumpclick(){
    $(".companyname").unbind().bind("click", function () {
        var type="hidden";
        var w = document.createElement("form");
        document.body.appendChild(w);
        var a= document.createElement("input");
        var b= document.createElement("input");
        var c= document.createElement("input");

        a.type= b.type= c.type=type;
        a.name="logintype";
        a.value=cookieopt.getlogintype();
        b.name="code";
        b.value=$(this).attr("data-code");
        c.name="backtype";
        c.value=submittype;

        w.appendChild(a);
        w.appendChild(b);
        w.appendChild(c);

        w.action = "findCompanyDeatilByCode.html";
        w.method="post";
        if(submittype=="searchcompany"){
            sessionStorage.setItem("company_search",JSON.stringify(selecttable.data));
        }else if(submittype=="searchcname"){
            var data={};
            data.searchname=searchcompany_byname.data.searchname;
            data.page=searchcompany_byname.data.page;
            sessionStorage.setItem("company_search",JSON.stringify(data));
        }


        w.submit();


//        $.openLink("findCompanyDeatilByCode.html?code="+$(this).attr("data-code")+"&logintype="+cookieopt.getlogintype());
    });
}

function inputkeybod(){
    var zonghe=$(".search_b_list ul li").length;
    var dangqian=$(".search_b_list ul li.keybod").index();


    $(".search-input").unbind("keydown");
    $(".search-input").keydown(function(e){

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

     if(event.which==13&&dangqian!=-1) {
            var hh= $(".search_b_list ul li").eq(dangqian).click();
        }
    });
}