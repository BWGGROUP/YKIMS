var footsearch=sessionStorage.getItem("myfoot_search");
$(function(){
	searchConfig.initClick();
	
	if(backtype!=""&&
    		(backtype=="myfoot"
    			||backtype=="searchfoot")){
        footsearch=eval("(" + footsearch + ")");
        searchConfig.data.logtype=footsearch.logtype;
        searchConfig.data.opertype=footsearch.opertype;
        searchConfig.data.page.pageCount=footsearch.pageCount;
        searchConfig.select();
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
		$(".tablehead").html(data_modle.orgHead);
		if(vdata.list!=null&&vdata.list.length>0){
			html=template.compile(data_modle.orgBody)(vdata);
		}
		$(".tablecontent").html(html);
        if(vdata.list!=null&&vdata.list.length>0){
        	lineClick();
        }
        jqPagination();
	};
	//交易记录
	var tradeRend=function(){
        var html=data_modle.nullbody6;
        $(".tablehead").html(data_modle.tradeHead);
        if(vdata.list!=null&&vdata.list.length>0){
            html=template.compile(data_modle.tradeBody)(vdata);
        }
        $(".tablecontent").html(html);
        if(vdata.list!=null&&vdata.list.length>0){
        	lineClick();
        }
        jqPagination();
	};
	//公司记录
	var companyRend=function(){
		var html=data_modle.nullbody4;
        $(".tablehead").html(data_modle.companyHead);
        if(vdata.list!=null&&vdata.list.length>0){
            html=template.compile(data_modle.companyBody)(vdata);
        }
        $(".tablecontent").html(html);
        if(vdata.list!=null&&vdata.list.length>0){
        	lineClick();
        }
        jqPagination();
	};
	//会议记录
	var meetingRend=function(){
        var html=data_modle.nullbody3;
        $(".tablehead").html(data_modle.meetingHead);
        if(vdata.list!=null&&vdata.list.length>0){
            html=template.compile(data_modle.meetingBody)(vdata);
        }
        $(".tablecontent").html(html);
        if(vdata.list!=null&&vdata.list.length>0){
            lineClick();
        }
        jqPagination();
	};
	//融资计划
	var fincRend=function(){
        var html=data_modle.nullbody3;
        $(".tablehead").html(data_modle.fincHead);
        if(vdata.list!=null&&vdata.list.length>0){
            html=template.compile(data_modle.fincBody)(vdata);
        }
        $(".tablecontent").html(html);
        jqPagination();
	};
	
	var init=function(){
		inputClick();
		searchClick();
	};
	
	var searchClick=function(){
		$(".searchClick").unbind().bind("click",function(){
            vdata.page={pageCount:1};
            if(vdata.logtype!=""&&vdata.opertype!=""){
                ajaxRequest();
            }else{
                $.showtip("搜索条件不能为空");
                setTimeout(function () {
                    $.hidetip();
                },2000);
            }


	    
		});
	};
	
	var inputClick=function(){
		$(".modletype").unbind().bind('click',function(){
			WCsearch_list.config({
	            allowadd:false,
	            searchclick: function () {
	            	
	            }
	        });
            $(".lists").html(data_modle.modelselectList);

            $(".item").unbind().bind("click",function () {
                $(".modletype").val($(this).html());
                vdata.logtype=$(this).attr('code');
                vdata.logtypename=$(this).html();
                $(".handtype").val("");
                vdata.opertype="";
                WCsearch_list.closepage();
            });

		});
        $(".handtype").unbind().bind('click',function(){
            WCsearch_list.config({
                allowadd:false,
                searchclick: function () {

                }
            });
            if(vdata.logtype=='3'){//判断选择会议
                $(".lists").html(data_modle.meetingtypList);
            }else if(vdata.logtype=='4'){
            	$(".lists").html(data_modle.finctypeList);
            }else{
                $(".lists").html(data_modle.handtypeList);
            }

            $(".item").unbind().bind("click",function () {
                $(".handtype").val($(this).html());
                vdata.opertype=$(this).attr('code');
                vdata.opertypename=$(this).html();
                WCsearch_list.closepage();
            });

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
            	
//                if(vdata.logtype=='0'){//跳转到机构详情
//                	window.location.href="findInvestmentById.html?id="+
//                	vdata.list[index].code+"&logintype="+cookieopt.getlogintype();
//                }else if(vdata.logtype=='1'){//跳转到交易详情
//                	window.location.href="findTradeDetialInfo.html?logintype="+
//                	cookieopt.getlogintype()+"&tradeCode="+
//                	vdata.list[index].base_trade_code;
//                }else if(vdata.logtype=='2'){//跳转到公司详情
//                	window.location.href="findCompanyDeatilByCode.html?code="+
//                	vdata.list[index].base_comp_code+"&logintype="+
//                	cookieopt.getlogintype();
//                }else if(vdata.logtype=='3'){//跳转到会议详情
//                	window.location.href="meeting_info.html?meetingcode="+
//                	vdata.list[index].base_meeting_code+"&logintype="+
//                	cookieopt.getlogintype();
//                }
            });

            if(vdata.logtype=='1'){
                $(this).children("td").children(".company").bind("click", function (e) {
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
                    $(".totalSize").html("共"+data.page.totalCount+"条");
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
    
    var jqPagination= function () {
        if(Number(vdata.page.totalPage)>0){
            $('.pagination').jqPagination({
                link_string	: '/?page={page_number}',
                max_page	:vdata.page.totalPage,
                current_page:vdata.page.pageCount,
                paged		: function(a) {
                    if(a!=vdata.page.pageCount){
                        vdata.page.pageCount=a;
                        ajaxRequest();
                    }
                }
            });
            $('.pageaction').show();
        }else{
            $('.pageaction').hide();
        }
    };
    
    return {
        initClick:init,
        select:ajaxRequest,
        data:vdata
    };
})();

var data_modle={
        modelselectList:'<div code="0" class="item">投资机构</div>'+
                '<div code="1" class="item">近期交易</div>'+
                '<div code="2" class="item">公司</div>'+
                '<div code="3" class="item">会议</div>'+
                '<div code="4" class="item">融资计划</div>',
        handtypeList:'<div code="YK-CRE" class="item">创建</div>'+
                '<div code="YK-UPD" class="item">修改</div>'+
                '<div code="YK-ADD" class="item">添加</div>',
        meetingtypList:'<div code="YK-JOIN" class="item">参与</div>'+
                '<div code="YK-CRE" class="item">创建</div>',
        finctypeList:'<div code="YK-CRE" class="item">创建</div>',       
		orgHead:'<tr>'+
				'<th width="40%">投资机构</th>'+
				'<th width="20%">类型</th>'+
				'<th width="40%">相关行业近期交易</th>'+
				'</tr>',
		orgBody:'<%for(var i=0;i<list.length;i++){%>'+
		        '<tr code="<%=list[i].code%>"><td><a class="companyname"><%=list[i].name%></a></td>' +
		        '<td><%=list[i].typename%></td>'+
		        '<td><%=list[i].companyName%></td>' +
		        '</tr><%}%>',
		tradeHead:'<tr>'+
			    '<th width="30%">时间</th>'+
			    '<th width="25%">公司</th>'+
			    '<th width="20%">金额</th>'+
			    '<th width="30%">投资者</th>'+
			    '</tr>',
		tradeBody:'<% for (var i=0;i<list.length;i++ ){%>' +
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
		        '<%}%>',
		companyHead:'<tr>'+
				'<th width="40%">公司</th>'+
				'<th width="30%">行业</th>'+
				'<th width="30%">阶段</th>'+
				'</tr>',
		companyBody:'<% for (var i=0;i<list.length;i++ ){%>' +
		        '<tr>' +
		        '<td ><a data-code="<%=list[i].base_comp_code%>" class="company"><%=list[i].base_comp_name%></a></td>' +
		        '<td>' +
		        '<% for (var j=0;j<list[i].view_comp_inducont.length;j++ ){%>' +
		        '<span class="comp"><%=list[i].view_comp_inducont[j].name%></span>' +
		        '<%}%>' +
		        '</td>' +
		        '<td><%=list[i].base_comp_stagecont%></td>' +
		        '</tr>' +
		        '<%}%>',
		meetingHead:'<tr><th width=\"20%\">时间</th>'+
				'<th width=\"30%\">记录人</th>'+
				'<th width=\"50%\">公司/机构</th>'+
				'</tr>',
        meetingBody:'<% for (var i=0;i<list.length;i++ ){%>'+
		        '<tr>' +
		        '<td><%=list[i].base_meeting_time%></td>' +
		        '<td><%=substring(list[i].createName,"10")%></td>' +
		        '<td><%=substring(list[i].base_meeting_compcont+" / "+list[i].base_meeting_invicont,"10")%></td>'+
		        '</tr>'+
		        '<%}%>',
		fincHead:'<tr>'+
				'<th width="25%">时间</th>'+
				'<th width="25%">公司</th>'+
				'<th width="50%">内容</th>'+
				'</tr>',
        fincBody:'<% for (var i=0;i<list.length;i++ ){%>'+
		        '<tr>' +
		        '<td><%=dateFormat(list[i].base_financplan_date,"yyyy-MM-dd")%></td>' +
		        '<td><%=Formatname(list[i])%></td>' +
		        '<td><%=list[i].base_financplan_cont%></td>'+
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