/**
 * Created by shbs-tp004 on 15-9-13.
 */
var optactive=0;
var menuactive=3;
var searchtype="";
var meetingsearch=sessionStorage.getItem("meeting_search");
$(function (){
	$(".totalSize").hide();
	if(message=="noroot"){
		$.showtip("您没有查看此会议的权限","error");
	}
	if(backtype!=""){
        if(backtype=="searchmeeting"){
        	meetingsearch=eval("("+meetingsearch+")");
        	$("#select_org").html("<option value='"+meetingsearch.orgaincode+"'>"+meetingsearch.orgaintext+"</option>");
            $("#select_com").html("<option value='"+meetingsearch.companycode+"'>"+meetingsearch.companytext+"</option>");
            $("#select_peo").html("<option value='"+meetingsearch.peoplecode+"'>"+meetingsearch.peopletext+"</option>");
        }
	}
	click_lisetn();
    if(backtype!=""){
        if(backtype=="searchmeeting"){

            tableselect.data.orgaincode=meetingsearch.orgaincode;
            tableselect.data.companycode=meetingsearch.companycode;
            tableselect.data.peoplecode=meetingsearch.peoplecode;
            
            tableselect.data.orgaintext=meetingsearch.orgaintext;
            tableselect.data.companytext=meetingsearch.companytext;
            tableselect.data.peopletext=meetingsearch.peopletext;
            
            tableselect.data.meetingtype=meetingsearch.meetingtype;
            tableselect.data.page=meetingsearch.page;
            
            $("#select_type").val(meetingsearch.meetingtype).trigger("change");
            
            tableselect.select();
        }
    }
    
});

function click_lisetn(){


    conditional_choose.select2();
    
    $(".btn-default-save").click(function () {
    	searchtype="search";
        tableselect.opt_config();
    });
}
//条件选择
var conditional_choose=(function(){
    var opt={};
    function select2() {
        //选择投资机构
        opt.$orgainselect=$("#select_org").select2({
            placeholder:"请选择机构",//文本框的提示信息
            minimumInputLength:0,   //至少输入n个字符，才去加载数据
            allowClear: true,  //是否允许用户清除文本信息
            ajax:{
                url:"findInvestmentNameListByName.html",
                dataType:"json",
                cache: true,
                type:"post",
                delay: 250,//加载延迟
                data: function (params) {
                    return{
                        name:(params.term||"").replace(/(^\s*)|(\s*$)/g,''),
                        pageSize:CommonVariable().SELSECLIMIT,
                        pageCount:"1",
                        logintype:cookieopt.getlogintype(),
                        type:"1"
                    };
                },
                processResults: function (data, page) {
                    for(var i=0;i<data.list.length;i++){
                        data.list[i].id=data.list[i].base_investment_code;
                        data.list[i].text=data.list[i].base_investment_name;
                    }
                    return {
                        results: data.list//返回结构list值
                    };
                }

            },
            escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
            templateResult: formatRepo // 格式化返回值 显示再下拉类表中
        }).change(function(){
        	var o=opt.$orgainselect.select2("data");
        	if(o.length>0){
        		for(var i=0;i< o.length;i++){
                    if(!o[i].selected){
                    	tableselect.data.orgaincode=o[i].id;
                    	tableselect.data.orgaintext=o[i].text;
                    	break;
                    }else{
                		tableselect.data.orgaincode="";
                    	tableselect.data.orgaintext="";
                	}
                }
        	}else{
        		tableselect.data.orgaincode="";
            	tableselect.data.orgaintext="";
        	}
        	
        });
        //选择公司
        opt.$companyselect=$("#select_com").select2({
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
                    };
                },
                processResults: function (data) {
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
        }).change(function(){
        	var c=opt.$companyselect.select2("data");
        	if(c.length>0){
        		for(var i=0;i< c.length;i++){
                    if(!c[i].selected){
                    	tableselect.data.companycode=c[i].id;
                    	tableselect.data.companytext=c[i].text;
                    	break;
                    }else{
                		tableselect.data.companycode="";
                    	tableselect.data.companytext="";
                	}
                }
        	}else{
        		tableselect.data.companycode="";
            	tableselect.data.companytext="";
        	}
        });
        //选择记录人
        opt.$peopleselect=$("#select_peo").select2({
            placeholder:"请选择记录人",//文本框的提示信息
            minimumInputLength:0,   //至少输入n个字符，才去加载数据
            allowClear: true,  //是否允许用户清除文本信息
            ajax:{
                url:"sysuserbyname.html",
                dataType:"json",
                cache: true,
                type:"post",
                delay: 250,//加载延迟
                data: function (params) {
                    return{
                        name:(params.term||"").replace(/(^\s*)|(\s*$)/g,''),
                        logintype:cookieopt.getlogintype()
                    };
                },
                processResults: function (data) {
                    for(var i=0;i<data.list.length;i++){
                        data.list[i].id=data.list[i].sys_user_code;
                        data.list[i].text=data.list[i].sys_user_name;
                    }
                    return {
                        results: data.list//返回结构list值
                    };
                }

            },
            escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
            templateResult: formatRepo // 格式化返回值 显示再下拉类表中
        }).change(function(){
        	var p=opt.$peopleselect.select2("data");
        	if(p.length>0){
        		for(var i=0;i< p.length;i++){
                    if(!p[i].selected){
                    	tableselect.data.peoplecode=p[i].id;
                    	tableselect.data.peopletext=p[i].text;
                    	break;
                    }else{
                		tableselect.data.peoplecode="";
                    	tableselect.data.peopletext="";
                	}
                }
        	}else{
        		tableselect.data.peoplecode="";
            	tableselect.data.peopletext="";
        	}
        	
        });
        
        $("#select_type").select2({
        	minimumResultsForSearch: Infinity
        });
    }
    function formatRepo (repo) {
        return repo.text;
    }
    function selectdata() {
        var map={};
        var o=opt.$orgainselect.select2("data");
        var c=opt.$companyselect.select2("data");
        var p=opt.$peopleselect.select2("data");
        var olist=[];
        var clist=[];
        var plist=[];
        for(var i=0;i< o.length;i++){
            if(!o[i].selected){
                olist.push(o[i]);
            }
        }
        for(var i=0;i< c.length;i++){
            if(!c[i].selected){
                clist.push(c[i]);
            }
        }
        for(var i=0;i< p.length;i++){
            if(!p[i].selected){
                plist.push(p[i]);
            }
        }
        map.olist=olist;
        map.clist=clist;
        map.plist=plist;
        return map;
    }
    return {
        select2:select2,
        selectdata:selectdata
    };
})();

//查询表格
var tableselect=(function () {
    var opt={
    		orgaincode:"",
    		orgaintext:"",
            companycode:"",
            companytext:"",
            peoplecode:"",
            peopletext:""
    };
    var rendMeetList=function(){
    	var html;
    	if(opt.tabledata.roottype=="root"){
    		$(".tablehead").html("<tr><th width=\"15%\">时间</th>"+
					"<th width=\"30%\">记录人</th>"+
					"<th width=\"44%\">公司/机构</th>"+
					"<th width=\"6%\">操作</th>"+
					"</tr>");
			html=template.compile(data_model.tablelistdel)(opt.tabledata);
    	}else{
    		$(".tablehead").html("<tr><th width=\"20%\">时间</th>"+
							"<th width=\"30%\">记录人</th>"+
							"<th width=\"50%\">公司/机构</th>"+
							"</tr>");
    		html=template.compile(data_model.tablelist)(opt.tabledata);
    	}
    	$("#tablecontent").html(html);
    	if(opt.tabledata.roottype=="root"){
    		delclick();
    	}
    };
    
    function delclick(){
    	$("[del-code]").unbind().bind("click",function(e){
    		var $this=$(this);
            layer.confirm('您确定要删除该条会议记录吗？', {
                title:"提示",
                icon: 0,
                btn: ['确定','取消'] //按钮
            }, function(index){
                delRequest($this.attr("del-code"));
                layer.close(index);
            }, function(index){
                layer.close(index);
            });
    		e.stopPropagation();
    	});
    };
    
    var delRequest=function(vCo){
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "deleteMeetingInfo.html",
            data: {
            	meetcode: vCo,
                logintype: cookieopt.getlogintype()
            },
            success: function (data) {
                if(data.message=="success"){
                	select();
                    $.showtip("删除成功","success");
                }else if(data.message=="failure"){
                    $.showtip("数据已被删除","error");
                    noteconfig(notecode);
                }else{
                    $.showtip("请求失败","error");

                }
                $.hideloading();
            }
        });
    };
    
    function opt_config() {
        opt.page={pageCount:1};
//        var comlist=conditional_choose.selectdata().clist;
//        var oralist=conditional_choose.selectdata().olist;
//        var peolist=conditional_choose.selectdata().plist;
//        if(comlist.length>0){
//            opt.companycode=comlist[0].id;
//        }else{
//            opt.companycode="";
//        }
//        if(oralist.length>0){
//            opt.orgaincode=oralist[0].id;
//        }else{
//            opt.orgaincode="";
//        }
//        if(peolist.length>0){
//            opt.peoplecode=peolist[0].id;
//        }else{
//            opt.peoplecode="";
//        }
        opt.meetingtype=$("#select_type").val();
        select();
    }
    function select(){
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "screenmeetinglist.html",
            data: {
                orgaincode: opt.orgaincode,
                companycode:opt.companycode,
                recordcode:opt.peoplecode,
                meetingtype:opt.meetingtype,
                type:searchtype,
                pageCount:opt.page.pageCount,
                logintype: cookieopt.getlogintype()
            },
            success: function (data) {
                $.hideloading();
                searchtype="";
                opt.tabledata=data;
                opt.page=data.page;
                $("#trcontent").show();
                $("#pagination1").show();
                if(data.message=="success"){
                	rendMeetList();
                	if(data.page.totalCount>0){
                		$(".totalSize").show();
                    	$(".totalSize").html("<li>共"+data.page.totalCount+"条</li>");
                    }else{
                    	$(".totalSize").hide();
                    }
                	
                }else if(data.message=="nomore"){
                	var html='<tr nomore><td  colspan="'+$(".tablehead tr").children().length+'" style="width:100%;">暂无数据</td></tr>';
                    $("#tablecontent").html(html);
                    $("#pagination1").hide();
                    $(".totalSize").hide();
                }
                jqPaginator();
                companyclick();
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
    function companyclick() {
        $("#tablecontent tr").each(function(index,element){
            $(this).bind("click", function () {
                if(opt.tabledata.list[index].visible=="1"){//可见详情
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
                    b.value="searchmeeting";
                    c.name="meetingcode";
                    c.value=opt.tabledata.list[index].base_meeting_code;

                    w.appendChild(a);
                    w.appendChild(b);
                    w.appendChild(c);

                    w.action = "meeting_info.html";
                    w.method="post";

                    var data={};
                    data.orgaincode=opt.orgaincode;
                    data.orgaintext=opt.orgaintext;
                    data.companycode=opt.companycode;
                    data.companytext=opt.companytext;
                    data.peoplecode=opt.peoplecode;
                    data.peopletext=opt.peopletext;
                    data.meetingtype=opt.meetingtype;
                    data.page=opt.page;

                    sessionStorage.setItem("meeting_search",JSON.stringify(data));


                    w.submit();



//                    $.openLink("meeting_info.html?meetingcode="
//                        +opt.tabledata.list[index].base_meeting_code
//                        +"&logintype="+cookieopt.getlogintype());
                }else{
                    $.showtip("您没有查看此会议的权限","error");
                }
            });
        });
    }
    
    
    return{
        select:select,
        opt_config:opt_config,
        data:opt
    };
})();
var data_model={
    tablelist:  '<% for (var i=0;i<list.length;i++ ){%>'+
        '<tr>' +
        '<td><%=list[i].base_meeting_time%></td>' +
        '<td><%=substring(list[i].createName,"40")%></td>' +
        '<td><%=substring(list[i].base_meeting_compcont+" / "+list[i].base_meeting_invicont,"40")%></td>'+
        '</tr>'+
        '<%}%>',
        tablelistdel:  '<% for (var i=0;i<list.length;i++ ){%>'+
        '<tr>' +
        '<td><%=list[i].base_meeting_time%></td>' +
        '<td><%=substring(list[i].createName,"40")%></td>' +
        '<td><%=substring(list[i].base_meeting_compcont+" / "+list[i].base_meeting_invicont,"40")%></td>'+
        '<td><button class="btn btn_delete" i="<%=i%>" del-code="<%=list[i].base_meeting_code%>"></button></td>' +
        '</tr>'+
        '<%}%>'
};
//应用模板 截取字符串
template.helper("substring", function (str,long) {
    str= str.replace(/<br\/>/g,"");
    if(str.length>long){
        str= str.substring(0,long)+"...";
    }
    return str;
});