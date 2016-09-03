/**
 * Created by shbs-tp004 on 15-9-9.
 */
var optactive=0;
var menuactive=4;
var searchtype="";
$(function () {
    click_lisetn();

    choosecompany.select2();
});

function click_lisetn(){
    $('#dp1').datepicker();



    $('#btn_search').click(function(){
    	searchtype="search";
        selcttable.config();
        if($('.search_planContent').hasClass('lable_hidden')){
            $('.search_planContent').removeClass("lable_hidden").addClass("lable_block");
        }
    });
}
//select2选择公司
var choosecompany=(function () {
    var opt={};

    function select2() {
        opt.$eventSelect=$("#chosecompany").select2({
            placeholder:"请选择公司",//文本框的提示信息
            minimumInputLength:0,   //至少输入n个字符，才去加载数据
            allowClear: true,  //是否允许用户清除文本信息
            ajax:{
                url:"companylistbyname.html",
                dataType:"json",
                cache: true,
                type:"post",
                delay: 250,//加载延迟
                data: function (params) {
                    return{
                        name:(params.term||"").replace(/(^\s*)|(\s*$)/g,''),
                        pageSize:CommonVariable().SELSECLIMIT,
                        pageCount:"1",
                        logintype:cookieopt.getlogintype()
                    }
                },
                processResults: function (data, page) {
                    for(var i=0;i<data.list.length;i++){
                        data.list[i].id=data.list[i].base_comp_code;
                        data.list[i].text=data.list[i].base_comp_name;
                    }
                    return {
                        results: data.list//返回结构list值
                    };
                }

            },
            escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
            templateResult: formatRepo // 格式化返回值 显示再下拉类表中
        });
    }

    function selectdata() {
        var l=opt.$eventSelect.select2("data");
        var list=[];
       for(var i=0;i< l.length;i++){
           if(!l[i].selected){
              list.push(l[i]);
           }
       }
    return list;
    }
    function formatRepo (repo) {
        return repo.text;
    }
    return{
        selectdata:selectdata,
        select2:select2,
        opt:opt
    };
})();
//查询表格
var selcttable=(function(){
    var opt={};

    function opt_config() {
        opt.page={};
        opt.page.pageCount=1;
        if($("#dp1").val()!=""){
            var date=new Date($("#dp1").val());
            opt.timestamp=date.getTime();
        }else{
            opt.timestamp="";
        }
        var comlist=choosecompany.selectdata();
        if(comlist.length>0){
            opt.companycode=comlist[0].id;
        }else{
            opt.companycode="";
        }
        select();
    }
    function select(){
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url:"financingsearch.html",
            data:{
                companycode:opt.companycode,
                timestamp:opt.timestamp,
                type:searchtype,
                pageCount:opt.page.pageCount,
                logintype:cookieopt.getlogintype()
            },
            success: function (data) {
                $.hideloading();
                searchtype="";
                if(data.message=="success"){
                    var html=template.compile(data_model.tablelist)(data);
                    $("#tablecontent").html(html);

                    opt.page=data.page;
                    if(data.page.totalCount>0){
                		$(".totalSize").show();
                    	$(".totalSize").html("<li>共"+data.page.totalCount+"条</li>");
                    }else{
                    	$(".totalSize").hide();
                    }
                    $("#pagination1").show();
                }else if(data.message=="nomore"){
                    var html='<tr><td  colspan="3" style="width:100%;">暂无数据</td></tr>';
                    $("#tablecontent").html(html);
                    opt.page=data.page;
                    $(".totalSize").hide();
                    $("#pagination1").hide();
                }else{
                    $.showtip("查询出错");
                    setTimeout(function () {
                        $.hidetip();
                    },1500);
                    $("#pagination1").hide();
                }
                jqPaginator();
            }
        });
    }

    function jqPaginator(){
        $.jqPaginator('#pagination1', {
            totalPages: opt.page.totalPage,
            visiblePages: 5,
            currentPage: opt.page.pageCount,
            onPageChange: function (num, type) {
                if(opt.page.pageCount!=num){
                    opt.page.pageCount=num;
                    select();
                }

            }
        });
    }
    return {
        config:opt_config
    };
})();

var data_model={
    tablelist:  '<% for (var i=0;i<list.length;i++ ){%>'+
        '<tr>' +
        '<td><%=dateFormat(list[i].base_financplan_date,"yyyy-MM-dd")%></td>' +
        '<td><%=Formatname(list[i])%></td>' +
        '<td><pre><%=list[i].base_financplan_cont%></pre></td>'+
        '</tr>'+
        '<%}%>'
};
template.helper('Formatname', function (data) {
    var name;
    if(data.base_comp_name!=""){
        name=data.base_comp_name;
    }else if(data.base_comp_name==""&&data.base_comp_fullname!=""){
        name=data.base_comp_fullname;
    }else{
        name=data.base_comp_ename;
    }
    return name
});