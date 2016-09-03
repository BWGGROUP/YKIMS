var optactive=0;
var menuactive=5;
var footsearch=sessionStorage.getItem("myfoot_search");
$(function(){
	$(".totalSize").hide();
	$(".selecttype").on('change',function(){
		if($(this).val()=='4'){
			$('.handtypediv').html('<select id="select_con" class="inputconf handtype">'+
					'<option value="YK-CRE">创建</option></select>');
		}else if($(this).val()=='3'){
			$('.handtypediv').html('<select id="select_con" class="inputconf handtype">'+
					'<option value="YK-JOIN">参与</option>'+
					'<option value="YK-CRE">创建</option></select>');
		}else{
			$('.handtypediv').html('<select id="select_con" class="inputconf handtype">'+
					'<option value="YK-CRE">创建</option>'+
					'<option value="YK-UPD">修改</option>'+
					'<option value="YK-ADD">添加</option></select>');
		}
		
		$("#select_con").select2({
	    	minimumResultsForSearch: Infinity
	    });
	});
	$("#select_type").select2({
    	minimumResultsForSearch: Infinity
    });
	$("#select_con").select2({
    	minimumResultsForSearch: Infinity
    });

    searchConfig.searchClick();

    if(backtype!=""&&
    		(backtype=="myfoot"
    			||backtype=="searchfoot")){
        footsearch=eval("(" + footsearch + ")");
        $("#select_type").val(footsearch.logtype);

        $("#select_type").select2({
        	minimumResultsForSearch: Infinity
        });
        if(footsearch.logtype=='4'){
            $('.handtypediv').html('<select id="select_con" class="inputconf handtype">'+
                '<option value="YK-CRE">创建</option></select>');
        }else if(footsearch.logtype=='3'){
            $('.handtypediv').html('<select id="select_con" class="inputconf handtype">'+
                '<option value="YK-JOIN">参与</option>'+
                '<option value="YK-CRE">创建</option></select>');
        }else{
            $('.handtypediv').html('<select id="select_con" class="inputconf handtype">'+
                '<option value="YK-CRE">创建</option>'+
                '<option value="YK-UPD">修改</option>'+
                '<option value="YK-ADD">添加</option></select>');
        }
        $("#select_con").val(footsearch.opertype);
        $("#select_con").select2({
            minimumResultsForSearch: Infinity
        });

        searchConfig.data.logtype=footsearch.logtype;
        searchConfig.data.opertype=footsearch.opertype;
        searchConfig.data.page.pageCount=footsearch.pageCount;
        searchConfig.searchRequest();
        
    }


});

var searchConfig=(function(){
	var vdata={
			list:null,
            page:{pageCount:1},
            opertype:'',
            logtype:''
	};
	//机构记录
	var orgRend=function(){
		var html=data_modle.nullbody3;
		$(".tablehead").html(data_modle.orghead.toString());
		if(vdata.list!=null&&vdata.list.length>0){
			html=template.compile(data_modle.orgbody)(vdata);
		}
		$(".tablecontent").html(html);
        if(vdata.list!=null&&vdata.list.length>0){
        	lineClick();
            $("#pagination1").show();
            jqPaginator();
        }else{
            $("#pagination1").hide();
        }
	};
	//交易记录
	var tradeRend=function(){
        var html=data_modle.nullbody6;
        $(".tablehead").html(data_modle.tradehead.toString());
        if(vdata.list!=null&&vdata.list.length>0){
            html=template.compile(data_modle.tradebody)(vdata);
        }
        $(".tablecontent").html(html);
        if(vdata.list!=null&&vdata.list.length>0){
        	lineClick();
            $("#pagination1").show();
            jqPaginator();
        }else{
            $("#pagination1").hide();
        }
	};
	//公司记录
	var companyRend=function(){
		var html=data_modle.nullbody4;
        $(".tablehead").html(data_modle.companyhead.toString());
        if(vdata.list!=null&&vdata.list.length>0){
            html=template.compile(data_modle.companybody)(vdata);
        }
        $(".tablecontent").html(html);
        if(vdata.list!=null&&vdata.list.length>0){
        	lineClick();
            $("#pagination1").show();
            jqPaginator();
        }else{
            $("#pagination1").hide();
        }
	};
	//会议记录
	var meetingRend=function(){
        var html=data_modle.nullbody3;
        $(".tablehead").html(data_modle.meetinghead.toString());
        if(vdata.list!=null&&vdata.list.length>0){
            html=template.compile(data_modle.meetingbody)(vdata);
        }
        $(".tablecontent").html(html);
        if(vdata.list!=null&&vdata.list.length>0){
            lineClick();
            $("#pagination1").show();
            jqPaginator();
        }else{
            $("#pagination1").hide();
        }
	};
	//融资计划
	var fincRend=function(){
        var html=data_modle.nullbody3;
        $(".tablehead").html(data_modle.financinghead.toString());
        if(vdata.list!=null&&vdata.list.length>0){
            html=template.compile(data_modle.financingbody)(vdata);
        }
        $(".tablecontent").html(html);
        if(vdata.list!=null&&vdata.list.length>0){
            $("#pagination1").show();
            jqPaginator();
        }else{
            $("#pagination1").hide();
        }
	};
	
	
	var searchClick=function(){
		$(".searchClick").unbind().bind("click",function(){
            vdata.page={pageCount:1};
			vdata.logtype=$("#select_type").val();
			vdata.opertype=$("#select_con").val();
            ajaxRequest();
	    
		});
	};

    var lineClick=function(){
        $(".tablecontent tr").each(function(index,element){

            $(this).unbind().bind("click",function(){

                var data={};
                data.opertype=vdata.opertype;
                data.logtype=vdata.logtype;
                data.pageCount=vdata.page.pageCount;
                sessionStorage.setItem("myfoot_search",JSON.stringify(data));
                var type="hidden";
                if(vdata.logtype=='0'){//跳转到机构详情


                    var w = document.createElement("form");
                    document.body.appendChild(w);
                    var a= document.createElement("input");
                    var b= document.createElement("input");
                    var c= document.createElement("input");

                    a.type= b.type= c.type= type;
                    a.name="logintype";
                    a.value=cookieopt.getlogintype();
                    b.name="id";
                    b.value=vdata.list[index].code;
                    c.name="backtype";
                    c.value='searchfoot';

                    w.appendChild(a);
                    w.appendChild(b);
                    w.appendChild(c);

                    w.action = "findInvestmentById.html";
                    w.method="post";

                    w.submit();

//                    $.openLink("findInvestmentById.html?id="+vdata.list[index].code+"&logintype="+cookieopt.getlogintype());

                }else if(vdata.logtype=='1'){//跳转到交易详情
                    var w = document.createElement("form");
                    document.body.appendChild(w);
                    var a= document.createElement("input");
                    var b= document.createElement("input");
                    var c= document.createElement("input");

                    a.type= b.type= c.type= type;
                    a.name="logintype";
                    a.value=cookieopt.getlogintype();
                    b.name="tradeCode";
                    b.value=vdata.list[index].base_trade_code;
                    c.name="backherf";
                    c.value='searchfoot';

                    w.appendChild(a);
                    w.appendChild(b);
                    w.appendChild(c);

                    w.action = "findTradeDetialInfo.html";
                    w.method="post";

                    w.submit();
//                    $.openLink("findTradeDetialInfo.html?logintype="+cookieopt.getlogintype()+"&tradeCode="+vdata.list[index].base_trade_code);
                }else if(vdata.logtype=='2'){//跳转到公司详情
                    var w = document.createElement("form");
                    document.body.appendChild(w);
                    var a= document.createElement("input");
                    var b= document.createElement("input");
                    var c= document.createElement("input");

                    a.type= b.type= c.type= type;
                    a.name="logintype";
                    a.value=cookieopt.getlogintype();
                    b.name="code";
                    b.value=vdata.list[index].base_comp_code;
                    c.name="backtype";
                    c.value='searchfoot';

                    w.appendChild(a);
                    w.appendChild(b);
                    w.appendChild(c);

                    w.action = "findCompanyDeatilByCode.html";
                    w.method="post";

                    w.submit();
//                    $.openLink("findCompanyDeatilByCode.html?code="+vdata.list[index].base_comp_code+"&logintype="+cookieopt.getlogintype());
                }else if(vdata.logtype=='3'){//跳转到会议详情
                    var w = document.createElement("form");
                    document.body.appendChild(w);
                    var a= document.createElement("input");
                    var b= document.createElement("input");
                    var c= document.createElement("input");

                    a.type= b.type= c.type= type;
                    a.name="logintype";
                    a.value=cookieopt.getlogintype();
                    b.name="meetingcode";
                    b.value=vdata.list[index].base_meeting_code;
                    c.name="backtype";
                    c.value='searchfoot';

                    w.appendChild(a);
                    w.appendChild(b);
                    w.appendChild(c);

                    w.action = "meeting_info.html";
                    w.method="post";

                    w.submit();
//                    $.openLink("meeting_info.html?meetingcode="+vdata.list[index].base_meeting_code+"&logintype="+cookieopt.getlogintype());
                }

            });

            if(vdata.logtype=='1'){
                $(this).children("td").children(".companyname").bind("click", function (e) {
                    var data={};
                    data.opertype=vdata.opertype;
                    data.logtype=vdata.logtype;
                    data.pageCount=vdata.page.pageCount;
                    sessionStorage.setItem("myfoot_search",JSON.stringify(data));
                    var type="hidden";
                    var w = document.createElement("form");
                    document.body.appendChild(w);
                    var a= document.createElement("input");
                    var b= document.createElement("input");
                    var c= document.createElement("input");

                    a.type= b.type= c.type= type;
                    a.name="logintype";
                    a.value=cookieopt.getlogintype();
                    b.name="code";
                    b.value=vdata.list[index].base_comp_code;
                    c.name="backtype";
                    c.value='searchfoot';

                    w.appendChild(a);
                    w.appendChild(b);
                    w.appendChild(c);

                    w.action = "findCompanyDeatilByCode.html";
                    w.method="post";

                    w.submit();
//                    $.openLink("findCompanyDeatilByCode.html?logintype="+cookieopt.getlogintype()+"&code="+vdata.list[index].base_comp_code);//跳转到公司详情
                    e.stopPropagation();
                });
            }
            
        });
    };



    var ajaxRequest=function(){
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "findUserFoot.html",
            data: {
                opertype: vdata.opertype,
                logtype: vdata.logtype,
                pageCount: vdata.page.pageCount,
                logintype: cookieopt.getlogintype()
            },
            success: function (data) {
                $.hideloading();
                if(data.message=="success"){
                    vdata.page=data.page;
                    vdata.list=data.list;
                    if(data.page.totalCount>0){
                		$(".totalSize").show();
                    	$(".totalSize").html("<li>共"+data.page.totalCount+"条</li>");
                    }else{
                    	$(".totalSize").hide();
                    }
                    
                    if(vdata.logtype=='0'){
                        orgRend();
                    }else if(vdata.logtype=='1'){
                        tradeRend();
                    }else if(vdata.logtype=='2'){
                        companyRend();
                    }else if(vdata.logtype=='3'){
                        meetingRend();
                    }else if(vdata.logtype=='4'){
                        fincRend();
                    }

                }else{
                    $.showtip("请求失败","error");

                }

            }
        });
    };
	function jqPaginator(){
        $.jqPaginator('#pagination1', {
            totalPages: vdata.page.totalPage,
            visiblePages: 5,
            currentPage: vdata.page.pageCount,
            onPageChange: function (num, type) {
                if(vdata.page.pageCount!=num){
                    vdata.page.pageCount=num;
                    ajaxRequest();
                }

            }
        });
    };
    return {
        searchClick:searchClick,
        searchRequest:ajaxRequest,
        data:vdata
    };
})();



var data_modle={
		orghead:'<tr><th class="td_one">投资机构</th>'+
				'<th class="td_two">类型</th>'+
				'<th class="td_four">相关行业近期交易</th></tr>',
	    orgbody:'<%for(var i=0;i<list.length;i++){%>'+
		        '<tr code="<%=list[i].code%>"><td><a class="companyname"><%=list[i].name%></a></td>' +
		        '<td><%=list[i].typename%></td>'+
		        '<td><%=list[i].companyName%></td>' +
		        '</tr><%}%>',
	    tradehead:'<tr><th style="width: 15%;">时间</th>'+
				'<th style="width: 15%;">公司</th>'+
				'<th style="width: 15%;">阶段</th>'+
				'<th style="width: 15%;">金额</th>'+
				'<th style="width: 15%;">行业</th>'+
				'<th style="width: 20%;">投资者</th></tr>',
	    tradebody:'<% for (var i=0;i<list.length;i++ ){%>' +
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
		        '<%}%>',
	    companyhead:'<tr><th style="width:30%;">公司</th>'+
	            '<th style="width:25%;">关注筐</th>'+
	            '<th style="width:25%;">行业</th>'+
	            '<th style="width:20%;">阶段</th></tr>',
	    companybody:'<% for (var i=0;i<list.length;i++ ){%>' +
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
		        '<%}%>',
	    meetinghead:'<tr><th width=\"20%\">时间</th>'+
				'<th width=\"30%\">记录人</th>'+
				'<th width=\"50%\">公司/机构</th></tr>',
		meetingbody:'<% for (var i=0;i<list.length;i++ ){%>'+
		        '<tr>' +
		        '<td><%=list[i].base_meeting_time%></td>' +
		        '<td><%=substring(list[i].createName,"40")%></td>' +
		        '<td><%=substring(list[i].base_meeting_compcont+" / "+list[i].base_meeting_invicont,"40")%></td>'+
		        '</tr>'+
		        '<%}%>',
	    financinghead:'<tr><th width="20%">计划融资日期</th>'+
				'<th width="30%">公司名称</th>'+
				'<th>内容</th></tr>',
	    financingbody:'<% for (var i=0;i<list.length;i++ ){%>'+
		        '<tr>' +
		        '<td><%=dateFormat(list[i].base_financplan_date,"yyyy-MM-dd")%></td>' +
		        '<td><%=Formatname(list[i])%></td>' +
		        '<td><pre><%=list[i].base_financplan_cont%></pre></td>'+
		        '</tr>'+
		        '<%}%>',
	    nullbody3:'<tr><td colspan="3">暂无数据</td></tr>',
	    nullbody4:'<tr><td colspan="4">暂无数据</td></tr>',
        nullbody5:'<tr><td colspan="5">暂无数据</td></tr>',
        nullbody6:'<tr><td colspan="6">暂无数据</td></tr>'
	};

template.helper("substr", function (a,b) {
    a= a.substring(0,b);
    return a;
});
//应用模板 截取字符串
template.helper("substring", function (str,long) {
    str= str.replace(/<br\/>/g,"");
    if(str.length>long){
        str= str.substring(0,long)+"...";
    }
    return str;
});
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