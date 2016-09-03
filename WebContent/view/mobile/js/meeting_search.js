var searchtype="";
var meetingsearch=sessionStorage.getItem("meeting_search");
$(function () {
	if(message=="noroot"){
		 $.showtip("您没有查看此会议的权限");
         setTimeout(function () {
             $.hidetip();
         },2000);
	}
    click_listen();
    $(".orgain,.company,.people").val("");
    if(backtype!=""){
        if(backtype=="searchmeeting"){
            meetingsearch=eval("("+meetingsearch+")");
            selecttable.data.page=meetingsearch.page;
            selectorgain.data.orgaincode=meetingsearch.orgaincode;
            selectorgain.data.orgainname=meetingsearch.orgainname;
            selectcompany.data.companycode=meetingsearch.companycode;
            selectcompany.data.companyname=meetingsearch.companyname;
            selectpeople.data.peoplecode=meetingsearch.peoplecode;
            selectpeople.data.peoplename=meetingsearch.peoplename;
            meetingTypeConfig.data.code=meetingsearch.typecode;
            meetingTypeConfig.data.name=meetingsearch.typename;
            $(".orgain").val(meetingsearch.orgainname);
            $(".company").val(meetingsearch.companyname);
            $(".people").val(meetingsearch.peoplename);
            $(".meetingtype").val(meetingsearch.typename);
            selecttable.select();
        }
    }
});
function click_listen() {
    $(".orgain").bind("click", function () {
        selectorgain.config();
    });
    $(".company").bind("click", function () {
        selectcompany.config();
    });
    $(".people").bind("click", function () {
        selectpeople.config();
    });
    meetingTypeConfig.click();
    
    $(".submit").bind("click", function () {
    	searchtype="search";
        selecttable.dataconfig();
        selecttable.select();
    });
}
//投资机构选择页面
var selectorgain=(function () {
    var opt={orgaincode:"",
            orgainname:""
    };
    var dataconfig= function () {
        opt.page={
            pageCount:1,
            pageSize:new CommonVariable().PAGESIZE,
            totalCount:2
        };
        $(".inner").html("<div class='lists'><div class='itemnocare' style='text-align: center;color:#AAA'>不限</div></div>");
        $(".itemnocare").unbind().bind("click",function () {
            $(".orgain").val("");
            opt.orgaincode="";
            opt.orgainname="";
            WCsearch_list.closepage();
        });
    };
    var config=function(){

        WCsearch_list.config({
            allowadd:false,
            searchclick: function () {
                dataconfig();
                if(opt.dropload){
                    opt.dropload.Destroy();
                };
                $.showloading();
                orgain_ajax();
                opt.dropload= WCsearch_list.dropload({
                    loadDownFn: function (me) {
                        if(Number(opt.page.pageCount)<Number(opt.page.totalPage)){
                            opt.page.pageCount=opt.page.pageCount+1;
                            orgain_ajax();
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
    var orgain_ajax=function(){
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url:"findInvestmentNameListByName.html",
            data:{
                name:$(".select-page-input").val().trim(),
                pageSize:opt.page.pageSize,
                pageCount:opt.page.pageCount,
                logintype:cookieopt.getlogintype(),
                type:"1"
            },
            success: function (data) {
                opt.page=data.page;
                if(data.message=="success"){
                    var html=template.compile(data_model.orgainlist)(data);
                    if(data.list.length==0){
                        $.showtip("无结果数据");
                        setTimeout(function () {
                            $.hidetip();
                        },2000);
                    }
                            $(".lists").append(html);

                    opt.dropload.resetload();
                    $(".item").unbind().bind("click",function () {
                        $(".orgain").val($(this).html());
                        opt.orgaincode=$(this).attr('data-org-code');
                        opt.orgainname=$(this).html();
                        WCsearch_list.closepage();
                    });
                }else{
                    $.showtip("查询异常");
                    setTimeout(function () {
                        $.hidetip();
                    },2000);
                }
                $.hideloading();
            }
        });
    };
    return{
        config:config,
        data:opt
    };

})();
//公司选择页面
var selectcompany=(function () {
    var opt={companycode:"",
            companyname:""
    };
    var dataconfig= function () {
        opt.page={
            pageCount:1,
            pageSize:new CommonVariable().PAGESIZE,
            totalCount:2
        };
        opt.firstload=true;
        $(".inner").html("<div class='lists'><div class='itemnocare' style='text-align: center;color:#AAA'>不限</div></div>");
        $(".itemnocare").unbind().bind("click",function () {
            $(".company").val("");
            opt.companycode="";
            opt.companyname="";
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
                $.showloading();
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
                name:$(".select-page-input").val().trim(),
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
                        $(".company").val($(this).html());
                        opt.companycode=$(this).attr('data-com-code');
                        opt.companyname=$(this).html();
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
                $.hideloading();
            }
        });
    };
    return {
        config:config,
        data:opt
    };
})();
//记录人选择页面
var selectpeople=(function () {
    var opt={peoplecode:"",
            peoplename:""
    };
    var dataconfig= function () {
        $(".inner").html("<div class='lists'><div class='itemnocare' style='text-align: center;color:#AAA'>不限</div></div>");
        $(".itemnocare").unbind().bind("click",function () {
            $(".people").val("");
            opt.peoplecode="";
            opt.peoplename="";
            WCsearch_list.closepage();
        });
    };
    var config= function () {

        WCsearch_list.config({
            allowadd:false,
            searchclick: function () {
                dataconfig();
                people_ajax();
            }
        });
        dataconfig();
    };
    var people_ajax= function () {
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "sysuserbyname.html",
            data: {
                name: $(".select-page-input").val().trim(),
                logintype: cookieopt.getlogintype()
            },
            success: function (data) {
                if(data.message=="success"){
                    if(data.list.length==0){
                        $.showtip("无结果数据");
                        setTimeout(function () {
                            $.hidetip();
                        },2000);
                    }
                    var html=template.compile(data_model.peoplelist)(data);

                    $(".lists").append(html);
                    $(".item").unbind().bind("click",function () {
                        $(".people").val($(this).html());
                        opt.peoplecode=$(this).attr('data-peo-code');
                        opt.peoplename=$(this).html();
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
                $.hideloading();
            }
        });
    };
    return{
        config:config,
        data:opt
    };
})();

//会议类型
var meetingTypeConfig=(function(){
	var vdata={};
	
	var click=function(){
		$(".meetingtype").unbind().bind("click",function(){

			WCsearch_list.config({
	            allowadd:false,
	            searchclick: function () {
	            	
	            }
	        });
			var data={
			     list:meetingtypeList
			};
			var html=template.compile(data_model.typelist)(data);
            $(".lists").html(html);

            $(".item").unbind().bind("click",function () {
                $(".meetingtype").val($(this).html());
                vdata.code=$(this).attr('data-type-code');
                vdata.name=$(this).html();
                WCsearch_list.closepage();
            });

		
		});
	};
	
	return {
		click:click,
		data:vdata
	};
})();

var data_model={
    orgainlist:'<% for (var i=0;i<list.length;i++ ){%>' +
        '<div data-org-code="<%=list[i].base_investment_code%>" class="item"><%=list[i].base_investment_name %></div>' +
        '<%}%>',
    companylist:'<% for (var i=0;i<list.length;i++ ){%>' +
        '<div data-com-code="<%=list[i].base_comp_code%>" class="item"><%=list[i].base_comp_name %></div>' +
        '<%}%>',
    peoplelist:'<div class="lists">'+
        '<% for (var i=0;i<list.length;i++ ){%>' +
        '<div data-peo-code="<%=list[i].sys_user_code%>" class="item"><%=list[i].sys_user_name %></div>' +
        '<%}%>' +
        '</div>',
    typelist:'<div class="lists">'+
    	'<div data-peo-code="" class="item">全部</div>' +
        '<% for (var i=0;i<list.length;i++ ){%>' +
        '<div data-type-code="<%=list[i].sys_labelelement_code%>" class="item"><%=list[i].sys_labelelement_name %></div>' +
        '<%}%>' +
        '</div>',
    tablelist:  '<% for (var i=0;i<list.length;i++ ){%>'+
        '<tr>' +
        '<td><%=list[i].base_meeting_time%></td>' +
        '<td><%=substring(list[i].createName,"10")%></td>' +
        '<td><%=substring(list[i].base_meeting_compcont+" / "+list[i].base_meeting_invicont,"10")%></td>'+
        '</tr>'+
        '<%}%>',
    tablelistdel:  '<% for (var i=0;i<list.length;i++ ){%>'+
        '<tr>' +
        '<td><%=list[i].base_meeting_time%></td>' +
        '<td><%=substring(list[i].createName,"10")%></td>' +
        '<td><%=substring(list[i].base_meeting_compcont+" / "+list[i].base_meeting_invicont,"10")%></td>'+
        '<td><button class="btn btn-default smart delClick" del-code="<%=list[i].base_meeting_code%>">删除</button></td>'+
		'</tr>'+
        '<%}%>'

};
//所搜得到会议列表
var  selecttable =(function () {
    var tabledata={};
    var vdata=[];
    var dataconfig= function () {
        tabledata={};
        vdata.page={pageCount:1};
    };
    
    var rendMeetList=function(){
    	var html;
    	if(tabledata.roottype=="root"){
    		$(".tablehead").html("<tr><th width=\"15%\">时间</th>"+
					"<th width=\"20%\">记录人</th>"+
					"<th width=\"37%\">公司/机构</th>"+
					"<th width=\"23%\">操作</th>"+
					"</tr>");
			html=template.compile(data_model.tablelistdel)(tabledata);
    	}else{
    		$(".tablehead").html("<tr><th width=\"20%\">时间</th>"+
							"<th width=\"30%\">记录人</th>"+
							"<th width=\"50%\">公司/机构</th>"+
							"</tr>");
    		html=template.compile(data_model.tablelist)(tabledata);
    	}
    	$("#tablecontent").html(html);
    	if(tabledata.roottype=="root"){
    		delclick();
    	}
    };
    //删除会议事件
    function delclick(){
    	$("[del-code]").unbind().bind("click",function(e){
    		var $this=$(this);
    		inputlsit_edit.config({
                title:"提示",//弹层标题
                html:true,//是否以html显示
                besurebtn:"确定",//确定按钮文字
                cancle:"取消",//取消按钮文字 （如果不写，则不会显示该按钮）
                htmltext:"<div style='text-align: center;font-size: 15px;'>您确定要删除该条会议记录吗？</div>",
                submit: function () {
                    $.showloading();//等待动画
                    delRequest($this.attr("del-code"));
                    inputlsit_edit.close();
                },//点击确定按钮回调方法
                canmit: function () {
                    inputlsit_edit.close();
                }//点击取消按钮回调方法
            });
    		e.stopPropagation();
    	});
    };
    //删除会议请求
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
            	$.hideloading();
                if(data.message=="success"){
                	select();
                    $.showtip("删除成功","success");
                }else if(data.message=="failure"){
                    $.showtip("数据已被删除","error");
                }else{
                    $.showtip("请求失败","error");

                }
                
                setTimeout(function () {
                    $.hidetip();
                },2000);
                
            }
        });
    };
    
    var select=function(){
        $.showloading();
        $.ajax({
            async: false,
            dataType: 'json',
            type: 'post',
            url: "screenmeetinglist.html",
            data: {
                orgaincode: selectorgain.data.orgaincode,
                companycode:selectcompany.data.companycode,
                recordcode:selectpeople.data.peoplecode,
                meetingtype:meetingTypeConfig.data.code,
                type:searchtype,
                pageCount:vdata.page.pageCount,
                logintype: cookieopt.getlogintype()
            },
            success: function (data) {
                $.hideloading();
                searchtype="";
                tabledata=data;
                vdata.page=data.page;
                if(data.message=="success"){
                	rendMeetList();
                	$(".totalSize").html("共"+data.page.totalCount+"条");
                    $("#trcontent").show();
                }else if(data.message=="nomore"){
                    var html='<tr><td  colspan="'+$(".tablehead tr").children().length+'" style="width:100%;">暂无数据</td></tr>';
                    $("#tablecontent").html(html);
                    $("#trcontent").show();
                }
                jqPagination();
                companyclick();
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
                        select();
                    }
                }
            });
            $('.pageaction').show();
        }else{
            $('.pageaction').hide();
        }
    };
    var companyclick= function () {
        $("#tablecontent tr").each(function(index,element){
            $(this).bind("click", function () {
                if(tabledata.list[index].visible=="1"){//可见详情
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
                    c.value=tabledata.list[index].base_meeting_code;

                    w.appendChild(a);
                    w.appendChild(b);
                    w.appendChild(c);

                    w.action = "meeting_info.html";
                    w.method="post";

                    var data={};
                    
                    data.orgaincode=selectorgain.data.orgaincode;
                    data.orgainname=selectorgain.data.orgainname;
                    data.companycode=selectcompany.data.companycode;
                    data.companyname=selectcompany.data.companyname;
                    data.peoplecode=selectpeople.data.peoplecode;
                    data.peoplename=selectpeople.data.peoplename;
                    data.typecode=meetingTypeConfig.data.code;
                    data.typename=meetingTypeConfig.data.name;
                    data.page=vdata.page;

                    sessionStorage.setItem("meeting_search",JSON.stringify(data));


                    w.submit();
                	
//                	window.location.href="meeting_info.html?meetingcode="
//                		+tabledata.list[index].base_meeting_code
//                		+"&logintype="+cookieopt.getlogintype();
                }else{
                    $.showtip("您没有查看此会议的权限");
                    setTimeout(function () {
                        $.hidetip();
                    },2000);
                }
            });
        });
    };
    return{
        select:select,
        dataconfig:dataconfig,
        data:vdata
    };
})();
//应用模板 截取字符串
template.helper("substring", function (str,l) {
    str= str.replace(/<br\/>/g,"");
    if(str.length>l){
        str= str.substring(0,l)+"...";
    }
    return str;
});