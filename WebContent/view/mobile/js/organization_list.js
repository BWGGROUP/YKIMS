var organization_search=sessionStorage.getItem("organization_search");
organization_search=eval("(" + organization_search + ")");
$(function () {
       $(".company").bind("touchstart", function () {
           $(this).addClass("hover");
           $this= $(this);
           $(document).bind("touchmove", function () {
               $this.removeClass("hover");
               $(document).unbind();
           });
       });


    page();
  //导出表格按钮点击
	$("#exprtExcel").click(function(){

        inputlsit_edit.config({
            title:"提示",//弹层标题
            html:true,//是否以html显示
            besurebtn:"确定",//确定按钮文字
            cancle:"取消",//取消按钮文字 （如果不写，则不会显示该按钮）
            htmltext:"<div style='text-align: center;font-size: 15px;'>您确定要导出表格吗？</div>",
            submit: function () {
                if($("[noexcel]").length>0){
                    $.showtip("无数据需要下载");
                }else{
                    subpost();
                    $.showtip("表格已发送到你的邮箱");
                }
                inputlsit_edit.close();
            },//点击确定按钮回调方法
            canmit: function () {
                inputlsit_edit.close();
            }//点击取消按钮回调方法
        });


   setTimeout(function(){
            $.hidetip();
        },2000);
	});
	//　重新搜索按钮点击
	$("#goback").click(function(){
        goback();
	});
    click();
});
function click(){
    $(".company").unbind().bind("click",function () {
        var code=$(this).attr("code");
        organization_search.pageCount=pagenow;
        sessionStorage.setItem("organization_search",JSON.stringify(organization_search));
        
    	if(organization_search.type=="text"){
    		window.location.href="findInvestmentById.html?id="
            	+code+"&logintype="+cookieopt.getlogintype()
            	+"&backtype=searchtext";
    	}else{
    		window.location.href="findInvestmentById.html?id="
            	+code+"&logintype="+cookieopt.getlogintype()
            	+"&backtype=searchscre";
    	}
        
        
        
    });
}
function page(){
    if(!$("[noexcel]").length){
		 $('.pagination').jqPagination({
		        link_string	: '/?page={page_number}',
		        max_page	: mainpage,
		        current_page: pagenow,
		        paged		: function(page) {
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
        $(".pageaction").hide();
    }
	 function selectmore_text(){
		 $.showloading();
		        $.ajax({
		            async: true,
		            dataType: 'json',
		            type: 'post',
		            data:{
		            	pageCount:pagenow,
		            	name:organization_search.searchtext,
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
		            		$(".pageaction").hide();
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
                deleteflag:a.deleteflag,
                invtype:a.invtype,
                logintype:cookieopt.getlogintype()
            },
            url:"searchMoreConditionByPage.html",
            success: function (data) {
            	$.hideloading();
                if(data.list.length>0){
                    var html=template.compile(data_modle.tablelist)(data);
                    $("#datalist").html(html);
                }else{
                    $(".pageaction").hide();
                }
                click();
            }
        });
    }
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
        bground:search.bground,
        labletype:search.invtype,
        competition: search.competition,
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
//重新搜索返回上一页面
function goback(){
	if(organization_search.type!=null
			&&organization_search.type=="text"){
		window.location.href="gotoSearchMain.html?logintype="+cookieopt.getlogintype();
	}else{
		window.location.href="initInvestmentSearch.html?logintype="+cookieopt.getlogintype();
	}
	
//	history.go(-1);
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