/**
 * Created by shbs-tp001 on 15-9-18.
 */
var searchtype="";
$(function () {

    click_listen();
});
//公司选择页面
var selectcompany=(function () {
    var opt={companycode:""};
    var dataconfig= function () {
        opt.page={
            pageCount:1,
            pageSize:new CommonVariable().PAGESIZE,
            totalCount:2
        };
        $(".inner").html("<div class='lists'><div class='itemnocare'  style='text-align: center;color:#AAA'>不限</div></div>");
        $(".itemnocare").unbind().bind("click",function () {
            $(".cmopany").val("");
            opt.companycode="";
            WCsearch_list.closepage();
        });
    };
    var config= function () {

        WCsearch_list.config({
            allowadd:false,
            searchclick: function () {
                dataconfig();
                if(opt.dropload){
                    opt.dropload.Destroy();
                };
                company_ajax();
                opt.dropload= WCsearch_list.dropload({
                    loadDownFn: function (me) {
                        if(Number(opt.page.pageCount)<Number(opt.page.totalPage)){
                            opt.page.pageCount=opt.page.pageCount+1;
                            company_ajax();
                        }else{
                            $.showtip("暂无更多数据");
                            setTimeout(function () {
                                $.hidetip();
                            },2000);
                            opt.dropload.resetload();
                            opt.dropload.lock();
                        }
                    }
                });

            }
        });
        dataconfig();
    };
    var company_ajax= function () {
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "companylistbyname.html",
            data: {
                name:$(".select-page-input").val(),
                pageSize:opt.page.pageSize,
                pageCount:opt.page.pageCount,
                logintype:cookieopt.getlogintype()
            },
            success: function (data) {
                opt.page=data.page;
                if(data.message=="success"){
                    var html=template.compile(data_model.companylist)(data);
                        $(".lists").append(html);

                    opt.dropload.resetload();
                    $(".item").unbind().bind("click",function () {
                        $(".cmopany").val($(this).html());
                        opt.companycode=$(this).attr('data-com-code');
                        WCsearch_list.closepage();
                    });
                }else if(data.message=="nomore"){
                    $.showtip("无结果数据");
                    setTimeout(function () {
                        $.hidetip();
                    },2000);
                }else{
                    $.showtip("查询异常");
                    setTimeout(function () {
                        $.hidetip();
                    },2000);
                }
            }
        });
    };
    return {
        config:config,
        data:opt
    };
})();
//数据模板
var data_model={
    companylist:'<% for (var i=0;i<list.length;i++ ){%>' +
        '<div data-com-code="<%=list[i].base_comp_code%>" class="item"><%=Formatname(list[i]) %></div>' +
        '<%}%>',
    tablelist:  '<% for (var i=0;i<list.length;i++ ){%>'+
        '<tr>' +
        '<td><%=dateFormat(list[i].base_financplan_date,"yyyy-MM-dd")%></td>' +
        '<td><%=Formatname(list[i])%></td>' +
        '<td><%=list[i].base_financplan_cont%></td>'+
        '</tr>'+
    '<%}%>'

};
function click_listen(){
    $('.cmopany').bind("click",function(){
        selectcompany.config();
    });
    $('.btn-rongz').bind("click",function(){
    	searchtype="search";
        selectfinancing.config();
    });
}
var selectfinancing=(function () {
    var page={};
    var timestamp="";
    var firstload=true;
    var config=function(){
        page.pageCount=1;
        firstload=true;
        if($("#datePicker").val()!=""){
            var date=new Date($("#datePicker").val());
            timestamp=date.getTime();
        }else{
            timestamp="";
        }
        select();
    };
    var select=function(){
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url:"financingsearch.html",
            data:{
                companycode:selectcompany.data.companycode,
                timestamp:timestamp,
                type:searchtype,
                pageCount:page.pageCount,
                logintype:cookieopt.getlogintype()
            },
            success: function (data) {
                $.hideloading();
                searchtype="";
                if(data.message=="success"){
                    var html=template.compile(data_model.tablelist)(data);
                    $("#tablecontent").html(html);
                    $("#trcontent").show();
                    page=data.page;
                    $(".totalSize").html("共"+data.page.totalCount+"条");
                    if(firstload){
                        jqPagination();
                    }
                    firstload=false;
                }else if(data.message=="nomore"){
                    var html='<tr><td  colspan="3" style="width:100%;">暂无数据</td></tr>';
                    $("#tablecontent").html(html);
                    $("#trcontent").show();
                    page=data.page;
                    if(firstload){
                        jqPagination();
                    }
                    firstload=false;
                }else{
                    $.showtip("查询出错");
                    setTimeout(function () {
                        $.hidetip();
                    },1500);
                }

            }
        });
    };
    var jqPagination= function () {
        if(Number(page.totalPage)>0){
            $('.pagination').jqPagination({
                link_string	: '/?page={page_number}',
                max_page	:page.totalPage,
                current_page:page.pageCount,
                paged		: function(a) {
                    if(a!=page.pageCount){
                        page.pageCount=a;
                        select();
                    }
                }
            });
            $('.pageaction').show();
        }else{
            $('.pageaction').hide();
        }
    };
    return{
        config:config,
    page:page
    };
})();

template.helper('Formatname', function (data) {
var name;
    if(data.base_comp_name!=""){
    name=data.base_comp_name;
    }else if(data.base_comp_name==""&&data.base_comp_fullname!=""){
        name=data.base_comp_fullname;
    }else{
        name=data.base_comp_ename;
    }
return name;
});