/**
 * Created by shbs-tp001 on 15-9-9.
 */
var optactive=0;
var menuactive=0;
var organization_search=sessionStorage.getItem("organization_search");
organization_search=eval("(" + organization_search + ")");
$(function () {
	click();
    page();
    //导出表格按钮点击
    $("#exprtExcel").click(function(){
        layer.confirm('您确定要导出表格吗？', {
            title:"提示",
            icon: 0,
            btn: ['确定','取消'] //按钮
        }, function(index){
            if($("[noexcel]").length>0){
                $.showtip("无数据需要下载");
            }else{
                subpost();
                $.showtip("表格已发送到你的邮箱");
            }
            layer.close(index);
        }, function(index){
            layer.close(index);
        });

    });
    //　重新搜索按钮点击
    $("#goback").click(function(){
    	window.location.href="gotoSearchMain.html?logintype="+cookieopt.getlogintype();
//        history.go(-1);
    });
});


function page(){
    if(!$("[noexcel]").length){
        $.jqPaginator('#pagination1', {
            totalPages: Number(mainpage),
            visiblePages: 5,
            currentPage: Number(pagenow),
            onPageChange: function (page, type) {
                if(page!=pagenow){
                    pagenow=page;
                    if(organization_search.type=="text"){
                        selectmore_text();
                    }else{
                        select_scre();
                    }

                }
            }
        });
    }else{
        $(".pagination").hide();
    }
    function selectmore_text(){
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            data:{
                pageCount:pagenow,
                name:organization_search.name,
                type:1,
                logintype:cookieopt.getlogintype()
            },
            url:"findInvestmentByName.html",
            success: function (data) {
                $.hideloading();
                if(data.list.length>0){
                    var html=template.compile(data_modle.tablelist)(data);
                    $("#datalist").html(html);
                }else{
                    $("#pagination1").hide();
                }
                click();
            }
        });
    }
    function select_scre(){
        $.showloading();
        var a=organization_search;
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            data:{
                pageCount:pagenow,
                type:"1",
                kuang:a.kuang,
                hangye: a.hangye,
                payatt: a.payatt,
                currency: a.currency,
                scale:a.scale,
                stage:a.stage,
                feature:a.feature,
                bground:a.bground,
                competition: a.competition,
                deleteflag: a.deleteflag,
                invtype: a.invtype,
                logintype:cookieopt.getlogintype()
            },
            url:"searchMoreConditionByPage.html",
            success: function (data) {
                $.hideloading();
                if(data.list.length>0){
                    var html=template.compile(data_modle.tablelist)(data);
                    $("#datalist").html(html);
                }else{
                    $("#pagination1").hide();
                }
                click();
            }
        });
    }
}
var data_modle={
    tablelist:'<%if(types=="text"){for( var i=0;i<list.length;i++){%>'+
        '<tr><td><a  code="<%=list[i].base_investment_code%>" class="company"><%=list[i].base_investment_name%></a></td>' +
        '<td><%=list[i].view_investment_typename%></td>'+

        '<td><%=list[i].companyName%></td>' +
        '<td></td>'+
        '</tr>'+
        '<%}}else{for( var i=0;i<list.length;i++){%>'+
        '<tr><td><a code="<%=list[i].base_investment_code%>" class="company"><%=list[i].base_investment_name%></a></td>' +
        '<td><%=list[i].view_investment_typename%></td>'+
//				'<td><%if(list[i].view_investment_compcode==""){%>否<%}else{%>是<%}%></td>'+
        '<td><%=list[i].companyName%></td>' +
        '<td><%=list[i].view_investment_compcode%></td>'+
        '</tr>'+
        '<%}}%>',
    tablelistNull:'<tr><td colspan="4">暂无数据  </td></tr>'
};
template.helper("logintype", function () {
    return cookieopt.getlogintype();
});
function click(){
    $(".company").unbind().bind("click",function () {
        var code=$(this).attr("code");
        organization_search.pageCount=pagenow;
        sessionStorage.setItem("organization_search",JSON.stringify(organization_search));
        
        var type="hidden";
        var w = document.createElement("form");
        document.body.appendChild(w);
        var a= document.createElement("input");
        var b= document.createElement("input");
        var c= document.createElement("input");
        
        a.type= b.type= c.type=type;
        a.name="logintype";
        a.value=cookieopt.getlogintype();
        b.name="id";
        b.value=code;
        c.name="backtype";
        c.value="search"+organization_search.type;
        
        w.appendChild(a);
        w.appendChild(b);
        w.appendChild(c);
        
        w.action = "findInvestmentById.html";
        w.method="post";

        w.submit();
        
        
//        $.openLink("findInvestmentById.html?id="+code+"&backtype=search"+organization_search.type+"&logintype="+cookieopt.getlogintype());
    });
}
//导出表格
function subpost(){
    var hiddenPath = document.getElementById("path").value;
    var search=organization_search;
    if(search.type=="text"){
        var data={
            pageCount:pagenow,
            name:search.searchtext,
            type:"text",
            kuang:"",
            hangye:"",
            payatt:"",
            currency: "",
            scale:"",
            stage:"",
            feature:"",
            competition: "",
            bground:"",
            labletype:"",
            hiddenPath:hiddenPath,
            logintype:cookieopt.getlogintype()
        };
    }else{
        var data={
            pageCount:pagenow,
            name:"",
            type:"scre",
            kuang:search.kuang,
            hangye: search.hangye,
            payatt: search.payatt,
            currency: search.currency,
            scale:search.scale,
            stage:search.stage,
            feature:search.feature,
            competition: search.competition,
            bground:search.bground,
            labletype:search.invtype,
            hiddenPath:hiddenPath,
            logintype:cookieopt.getlogintype()
        };
    }

    $.ajax({
        async: true,
        dataType: 'json',
        type: 'post',
        data:data,
        url:"exportExcelByMoreCon.html",
        success:function(exportdata){
        	var mes = exportdata.message;
        	if(!mes){
        		$.showtip("系统繁忙，请稍后再试！");
        	}
        	
        }
    });
}
