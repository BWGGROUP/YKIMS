/**
 * Created by shbs-tp001 on 15-9-18.
 */
$(function () {
    congfig.init();
    addnote.btnclick();
    
    if(backtype!=""){
    	$(".goback").show();
    	$(".goback").unbind().bind("click",function(){
    		if(backtype=="searchmeeting"){
        		window.location.href="meeting_search.html?logintype="
        			+cookieopt.getlogintype()+"&backtype="+backtype;
        	}else if(backtype=="searchfoot"){
        		window.location.href="myfoot_search.html?logintype="
                    +cookieopt.getlogintype()+"&backtype="+backtype;
        	}
    	});
    }
});

var rows=1;//根据文本框初始值设置。
var cols=10;//根据文本框初始值设置。
var num=0;
function changerow(){
	num++;
	if(num==rows*cols-1){
	rows=rows+2;
	document.getElementById("meetingcontentid").rows=rows;
	}
}

var congfig=(function () {
    var data={
        mplist:meetingpeople.split(","),
        meetingcont:meetingcont,
        noteconfignum:CommonVariable().NOTECONFIGNUM,
        meetingcode:meetingcode,
        zhangkai:false,
        sharetype:"",
        sharewadJson:null,
        meetingtypeJson:null,
        SysWadList:null,
        YKuserList:null,
        meetingtypeList:null,
        meetingnote:null,
        version:"",
        fileListInfo:null
    };

    //初始加载会议信息
    var initLoadMeeting=function(){

        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "initloadmeetinginfo.html",
            data: {
                meetingcode: data.meetingcode,
                logintype: cookieopt.getlogintype()
            },
            success: function (vdata) {
                $.hideloading();
                if(vdata.message=="success"){
                    data.SysWadList=vdata.SysWadList;
                    data.sharewadJson=vdata.UserShareList;
                    data.YKuserList=vdata.YKuserList;
                    data.meetingnote=vdata.baseMeetingNoteInfos;
                    data.version=vdata.version;
                    data.fileListInfo=vdata.meetingFile;
                    data.meetingtypeList=vdata.typeLabelList;
                    init();
                }else{
                    $.showtip("请求失败","error");

                }

            }
        });

    };

    var init=function(){
    	data.sharetype=meeting_object.base_meeting_sharetype;
        joinmeetingUser.data.joinuser=dicJoinMeetConfig(meeting_object.view_meeting_usercode,meeting_object.view_meeting_usercodename);
        var peoplehtml=template.compile(data_model.people)(data);
        var meetingnote=template.compile(data_model.noteall)(data);
        if(peoplehtml=="<li></li>"){
            peoplehtml="<li>暂无数据</li>";
        }
        if(meetingnote==""){
            meetingnote="<tr><td style='text-align: center'>暂无数据</td></tr>";
        }
        $(".meetpeo").html(peoplehtml);
        if(meeting_invicont!=null && meeting_invicont!=""){
        	InvestmentInfoconfig.data.invicont = eval(meeting_invicont);	
        }else{
        	InvestmentInfoconfig.data.invicont = [];
        }
        InvestmentInfoconfig.InvestmentInfoLoad();//渲染相关机构
        
        if(meeting_compcont!=null && meeting_compcont!=""){
        	CompInfoconfig.data.companycont = eval(meeting_compcont);
        }else{
        	CompInfoconfig.data.companycont = [];
        }
        
        
        CompInfoconfig.CompInfoLoad();//渲染相关公司
        
        meetingfile.rendingFile();//

        if(meeting_object.base_meeting_typecode!=null
        		&&meeting_object.base_meeting_typecode!="" ){
            var map={};
            data.meetingtypeJson=[];
            map.code=meeting_object.base_meeting_typecode;
            map.name=meeting_object.base_meeting_typename;
            data.meetingtypeJson.push(map);

        }
        meetingtypeRend();
        
        //dwj 渲染分享范围
	    if(meeting_object.userstatus=="1"){
	    	$(".contentDiv").html("<textarea class=\"txt_redord meetingconttxt\" placeholder=\"会议内容...\" onkeyup=\"changerow()\"  maxlength=\"3000\" id=\"meetingcontentid\">"
	                +data.meetingcont+"</textarea>");
	    	
		   	$(".share,.edit-btn,.addbtn,.meetcontbtn").removeClass("hidden");

            //参会人编辑
            joinmeetingUser.editClick();

            //添加相关机构
            InvestmentInfoconfig.InvestmentAddClick();

            //添加相关公司
            CompInfoconfig.CompAddClick();
            
            //保存会议内容
            meetcontConfig.saveClick();
            
            //渲染分享范围
		   	initShareWad();
		   	
		   	//dwj 分享事件
		   	shareclick();
		   	
		   	//会议类型编辑
		   	meetingtypeClick();
	    }else{
	    	$(".contentDiv").html("<pre class=\"meeting-conten\">"+data.meetingcont+"</pre>");
	    }
        
        
        
        $(".meeting-conten").html(data.meetingcont);
        $(".notetable").html(meetingnote);
        if(data.noteconfignum<data.meetingnote.length){
            $(".closeshearch").show();
            $("[nodemore]").hide();
        }
        click();
    };

  //dwj 2016-3-9 初始加载分享范围
    var initShareWad = function() {
    	var html = "";
    	if (congfig.data.sharewadJson != null && congfig.data.sharewadJson != "" ) {
    		var data = {
    			list : congfig.data.sharewadJson
    		};
    		html = template.compile(data_model.share)(data);
    	} else {
    		html = "<li>暂无数据</li>";
    	}
    	$(".sharecontent").html(html);
    };
    
  //dwj 会议类型
    var meetingtypeRend=function(){

        var html = "";
        if (data.meetingtypeJson != null && data.meetingtypeJson != "" ) {
            var ndata = {
                list : data.meetingtypeJson
            };
            html = template.compile(table_data.labelInfoList)(ndata);
        } else {
            html = table_data.labelNull;
        }
        $(".typecontent").html(html);

    };
    
  //dwj 会议类型编辑
    var meetingtypeClick=function(){
        $(".meetingtype").unbind().bind("click",function(){
            var $this=$(this);
            var sharelist=labelListConfig(false, data.meetingtypeList, data.meetingtypeJson);
            listefit.config({
 				title : "分享范围",
 				list : sharelist,
 				radio : true,
 				besure : function() {
 					data.meetingtypeJson = eval(choiceContent());
 					updateMeetingType();
 				}
 			});
        });

    };
    
    //修改会议类型
    var updateMeetingType=function(){
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "updateMeetingType.html",
            data: {
                version:data.version,
                typelabel:oldContent(data.meetingtypeJson),
                meetcode: meeting_object.base_meeting_code,
                logintype: cookieopt.getlogintype()
            },
            success: function (vdata) {
                if(vdata.message=="success"){
                    $.showtip("保存成功");
                    data.version=vdata.version;
                    data.meetingtypeJson=vdata.meetingTypeJson;
                    meetingtypeRend();
                }else if(vdata.message=="noroot"){
                    $.showtip("无修改权限");
                }else if(vdata.message=="nomeeting"){
                    $.showtip("会议不存在");
                }else if(vdata.message=="nouser"){
                    $.showtip("未登录用户");
                }else if(vdata.message=="upfail"){
                    $.showtip("数据已被修改");
                }else{
                    $.showtip("修改失败");
                }
                setTimeout(function () {
                    $.hidetip();
                },2000);
                $.hideloading();
            }
        });

    };
    //分享编辑事件
    var shareclick=function(){
    	var sharewadshowlist = dicListConfig(true, data.SysWadList, data.sharewadJson);
    	$(".share").unbind().bind("click", function() {
    		var $this = $(this);
    		var htmltxt='<div class="sharefrom"><ul class="inputshare">' +
            '<li><span class=""><input id="ra1" name="sharetype" type="radio" value="1" /></span><span class="">分享给筐</span></li>' +
            '<li><span class=""><input id="ra2" name="sharetype" type="radio" value="2" /></span><span class="">分享给人</span></li>' +
            '</ul></div>';
    		
    		 inputlsit_edit.config({
                 title:"提示",//弹层标题
                 html:true,//是否以html显示
                 besurebtn:"确定",//确定按钮文字
                 cancle:"取消",//取消按钮文字 （如果不写，则不会显示该按钮）
                 htmltext:htmltxt,
                 submit: function () {
                	 data.sharetype=$('.inputshare input[name="sharetype"]:checked').val();
                     inputlsit_edit.close();
                     var sharelist;
         			if(data.sharetype=="1"){
         				sharelist=dicListConfig(true, data.SysWadList, data.sharewadJson);
         			}else if(data.sharetype=="2"){
         				sharelist=dicListConfig(true, data.YKuserList, data.sharewadJson);
         			}
                     
         			setTimeout(function(){
         				listefit.config({
             				title : "分享范围",
             				list : sharelist,
             				radio : false,
             				besure : function() {
             					data.sharewadJson = eval(choiceContent());
             					updateShare();
             				}
             			});
         			}, 500);
                     
                     
                 },//点击确定按钮回调方法
                 canmit: function () {
                     inputlsit_edit.close();
                 }//点击取消按钮回调方法
             });
    		 $('#ra'+data.sharetype).attr("checked",true);
    	});
};
    
    var click= function () {
        $(".closeshearch").unbind().bind("click", function () {
           if(!$(this).hasClass("active")){
               $("[nodemore]").show();
               $(this).html('收起<span class="glyphicon glyphicon-chevron-up"></span>').addClass("active");
               click();
               data.zhangkai=true;
           }else{
               $("[nodemore]").hide();
               $(this).html('更多<span class="glyphicon glyphicon-chevron-down"></span>').removeClass("active");
               data.zhangkai=false;
           }
        });
        $("[del-code]").unbind().bind("click", function () {
            $this=$(this);
            inputlsit_edit.config({
                title:"提示",//弹层标题
                html:true,//是否以html显示
                besurebtn:"确定",//确定按钮文字
                cancle:"取消",//取消按钮文字 （如果不写，则不会显示该按钮）
                htmltext:"<div style='text-align: center;font-size: 15px;'>您确定要删除该条记录吗？</div>",
                submit: function () {
                    deletnote($this.attr("del-code"));
                    inputlsit_edit.close();
                },//点击确定按钮回调方法
                canmit: function () {
                    inputlsit_edit.close();
                }//点击取消按钮回调方法
            });
        });
    };
    var deletnote=function(notecode){
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "meetingnote_delet.html",
            data: {
                notecode: notecode,
                logintype: cookieopt.getlogintype()
            },
            success: function (data) {
                if(data.message=="success"){
                    $.showtip("删除成功");
                    noteconfig(notecode);
                }else if(data.message=="failure"){
                    $.showtip("数据已被删除");
                    noteconfig(notecode);
                }else{
                    $.showtip("请求失败");

                }
                setTimeout(function () {
                    $.hidetip();
                },2000);
                $.hideloading();
            }
        });
    };
var noteconfig= function (notecode) {
    if(notecode){
        data.meetingnote.baoremove(Number($("[del-code='"+notecode+"']").attr("i")));
    }

    var meetingnote=template.compile(data_model.noteall)(data);
    if(meetingnote==""){
        meetingnote="<tr><td style='text-align: center'>暂无数据</td></tr>";
    }
    $(".notetable").html(meetingnote);
    if(data.noteconfignum>= data.meetingnote.length){

        $(".closeshearch").html('更多<span class="glyphicon glyphicon-chevron-down"></span>').removeClass("active").hide();
        data.zhangkai=false;
    }else{
        if(!$(".closeshearch").hasClass("active")){
            $(".closeshearch").html('更多<span class="glyphicon glyphicon-chevron-down"></span>').show();
        }

    }
    if(!data.zhangkai){
        $("[nodemore]").hide();
    }

    click();
};

	var updateShare=function(){
		 $.showloading();
		 $.ajax({
	           async: true,
	           dataType: 'json',
	           type: 'post',
	           url: "updateMeetingInfo.html",
	           data: {
	           	meetcode: meeting_object.base_meeting_code,
	           	sharetype: data.sharetype,
	           	version:data.version,
	           	newbask:oldContent(data.sharewadJson),
	            logintype: cookieopt.getlogintype()
	           },
	           success: function (vdata) {
	           	if(vdata.message=="success"){
	           		$.showtip("保存成功","success");
	           		data.sharewadJson=vdata.userShareList;
	           		data.version=vdata.version;
	           		initShareWad();
	           	}else if(vdata.message=="noroot"){
	           		$.showtip("无修改权限","error");
	           	}else if(vdata.message=="nomeeting"){
	           		$.showtip("会议不存在","error");
	           	}else if(vdata.message=="nouser"){
	           		$.showtip("未登录用户","error");
	           	}else if(vdata.message == "upfail"){
	           		$.showtip("数据已被修改","error");
	           	}else{
	           		$.showtip("修改失败","error");
	           	}
	           	

	           	$.hideloading();
	            setTimeout(function () {
	                  $.hidetip();
	              },2000);
	           }
	     });
	
	};
	
	//dwj 标签弹出层集合
	var dicListConfig=function(vDel,vList,vObj){
	   var list=[];
	   var map={};
	   for(var i=0;i<vList.length;i++){
	       map={};
			map.name = vList[i].name;
			map.id = vList[i].code;
	       if(vObj!=null){
	           for ( var j = 0; j < vObj.length; j++) {
	               //判断标签是否被选中,存在则加上选中标识
	               if(vObj[j]!=null && vList[i].code==vObj[j].code){
	                   map.select=true;
	               }
	           }
	       }
	       list.push(map);
	   }
	   return list;
	};

	//dwj 标签弹出层集合
    var labelListConfig=function(vDel,vList,vObj){
        var list=[];
        var map={};
        for(var i=0;i<vList.length;i++){
            map={};
            map.name = vList[i].sys_labelelement_name;
            map.id = vList[i].sys_labelelement_code;
            if(vObj!=null){
                for ( var j = 0; j < vObj.length; j++) {
                    //判断标签是否被选中,存在则加上选中标识
                    if(vObj[j]!=null && vList[i].sys_labelelement_code==vObj[j].code){
                        map.select=true;
                    }
                }
            }
            list.push(map);
        }
        return list;
    };
    
    //dwj 参会人信息转为json格式
    var dicJoinMeetConfig=function(vCode,vName){
        var list=[];
        var map;
        var vcode=vCode.split(",");
        var vname=vName.split(",");
        for(var i=0;i<vcode.length;i++){
            map={};
            map.code=vcode[i];
            map.name=vname[i];
            list.push(map);
        }
        return list;
    };
	
	//dwj 2016-3-4 弹层选择筐选择值,返回[{code:'',name:''}]
	function choiceContent(){
	   var list="[";
	   var num=0;
	   var newNum=0;
	   $(".list li[class='active']").each(function(){
	       num++;
	   });
	   $(".list li[class='active']").each(function(){
	       newNum++;
	       list+="{code:'"+$(this).attr("tip-l-i")+"',name:'"+$(this).text()+"'}";
	       if(newNum<num){
	           list+=",";
	       }
	   });
	   list+="]";
	   return list;
	}
	//dwj 2016-3-9 转变对象格式为json字符串
	var oldContent=function (vObj){
	   var list="[";
	   if(vObj!=null){
	       for ( var i = 0; i < vObj.length; i++) {
	           list+="{code:'"+vObj[i].code+"',name:'"+vObj[i].name+"'}";
	           if(i<vObj.length-1){
	               list+=",";
	           }
	       }
	   }
	   list+="]";
	   return list;
	};
    return{
        init:initLoadMeeting,
        data:data,
        noteconfig:noteconfig,
        dicListConfig:dicListConfig,
        choiceContent:choiceContent,
        oldContent:oldContent


    };
})();

var data_model={
	share:'<% for (var i=0;i<list.length;i++ ){%>' +
        '<li><%=list[i].name%></li>' +
        '<%}%>',
    people:'<% for (var i=0;i<mplist.length;i++ ){%>' +
        '<li><%=mplist[i]%></li>' +
        '<%}%>',
    editPeople:'<% for (var i=0;i<list.length;i++ ){%>' +
        '<li><%=list[i].name%></li>' +
        '<%}%>',
    invicont:'<% for (var i=0;i<invicont.length;i++ ){%>' +
        '  <li p-index="<%=i%>"><span class="comp"><%=invicont[i].name%></span> <span class="comp"><%=invicont[i].personname%></span><span class="comp"><%=invicont[i].personpo%></span></li>' +
        '<%}%>',
    company:'<%for (var i=0;i<companycont.length;i++ ){%>' +
        '  <li  p-index="<%=i%>"><span class="comp"><%=companycont[i].name%></span> <span class="comp"><%=companycont[i].personname%></span><span class="comp"><%=companycont[i].personpo%></span></li>' +
        '<%}%>',
    noteall:'<% for (var i=0;i<meetingnote.length;i++ ){ %>' +
        '<tr <%= nodermore(i,noteconfignum)%> >' +
        '<td><%=dateFormat(meetingnote[i].createtime,"yyyy-MM-dd")%></td>' +
        '<td><%=meetingnote[i].sys_user_name%></td>' +
        '<td><pre><%=meetingnote[i].base_invesnote_content%></pre></td>' +
        '<td><button class="btn btn-default smart" i="<%=i%>" del-code="<%=meetingnote[i].base_meetingnote_code%>">删除</button></td>' +
        '</tr>'+
        '<%}%>'
};


var table_data = {
    investmentInfoList : '<%for(var i=0;i<list.length;i++){%>'
        + '<li id="<%=list[i].base_investment_code%>" p-index="<%=i%>">'
        + '<span class="comp"><%=list[i].base_investment_name%></span>'
        + '<span class="comp"><%=list[i].base_investor_name%></span>'
        + '<span class="comp"><%=list[i].base_investor_posiname%></span>'
        + '</li>'
        + '<%}%>',
    compInfoList : '<%for(var i=0;i<list.length;i++){%>'
        + '<li id="<%=list[i].base_comp_code%>" p-index="<%=i%>">'
        + '<span class="comp"><%=list[i].base_comp_name%></span>'
        + '<span class="comp"><%=list[i].base_entrepreneur_name%></span>'
        + '<span class="comp"><%=list[i].base_entrepreneur_posiname%></span>'
        + '</li>'
        + '<%}%>',
    companyselectlist : '<% for (var i=0;i<list.length;i++ ){%>'
        + '<div data-com-code="<%=list[i].base_comp_code%>" '
        +'class="item"><%=list[i].base_comp_name %></div>'
        + '<%}%>',
    investmentselectlist:'<% for (var i=0;i<list.length;i++ ){%>' +
        '<div data-org-code="<%=list[i].base_investment_code%>" class="item"><%=list[i].base_investment_name %></div>' +
        '<%}%>',
    investorSelectList:'<% for (var i=0;i<list.length;i++ ){%>'
        +'<div data-investor-code="<%=list[i].base_investor_code%>" '
        +' base_investor_posiname="<%=list[i].base_investor_posiname%>" '
        +'class="item"><%=list[i].base_investor_name %></div>' +
        '<%}%>',
    entrepreneurSelectList:'<% for (var i=0;i<list.length;i++ ){%>'
        +'<div base_entrepreneur_code="<%=list[i].base_entrepreneur_code%>" '
        +' base_entrepreneur_posiname="<%=list[i].base_entrepreneur_posiname%>" '
        +'class="item"><%=list[i].base_entrepreneur_name %></div>' +
        '<%}%>',
    investorSelectListNull:'<option id="">暂无数据</option>',
    addcomp:	'<div class="shgroup" style="margin-top:10px;">'
        +' <div class="title no-border"><%=name%>:</div>'
        +' <div class="tiplist no-border" style="padding-right:10px;">'
        +' <input type="text" class="inputdef inputdef_l addcompinvestname" maxlength="20">'
        +'</div>'
        +'<div style="display:table-cell">'
        +'<button class="btn btn-default smart" id="addcompinvet" >'
        +'确定</button></div></div>',
    labelInfoList : '<%for(var i=0;i<list.length;i++){%>'
        + '<li data-i="<%=i%>"><%=list[i].name%></li>' + '<%}%>',
    labelNull : '<li>暂无数据</li>'
};

template.helper("nodermore", function (a,b) {
    if(a<b){
        return "";
    }else{
        return "nodemore";
    }
});


var addnote=(function () {
    var btnclick= function () {
      $(".addnote").unbind().bind("click", function () {
        var text=$(".notetext").val().trim();
          if(text!=""){
              ajaxaddnote($(".notetext").val());
          }else{
              $.showtip("备注信息不能为空");
              setTimeout(function () {
                  $.hidetip();
              },2000);
          }
      });
    };
    var ajaxaddnote= function (text) {
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "meetingnote_add.html",
            data: {
                meetingcode: congfig.data.meetingcode,
                text:text,
                logintype: cookieopt.getlogintype()
            },
            success: function (data) {
                if(data.message=="success"){
                    congfig.data.meetingnote.unshift(data.MeetingNote);
                    congfig.noteconfig();
                    $(".notetext").val("");
                    $.showtip("保存成功");
                }else if(data.message=="error"){
                    $.showtip("保存失败");
                }else{
                    $.showtip("请求失败");
                }

                $.hideloading();
                setTimeout(function () {
                    $.hidetip();
                },2000);
            }
        });
    };
    return{
        btnclick:btnclick
    };
})();


//参会人
var joinmeetingUser=(function(){
    var data={
        joinuser:null
    };

    // 初始加载易凯参会人
    var initYKparp = function() {
        var html = "";
        if (data.joinuser != null && data.joinuser != "" ) {
            var vdata = {
                list : data.joinuser
            };
            html = template.compile(data_model.editPeople)(vdata);
        } else {
            html = "<li>暂无数据</li>";
        }
        $(".meetpeo").html(html);
        YKuserClick();
    };
    // 参会人员事件
    var YKuserClick = function() {
        var ykusershowlist = congfig.dicListConfig(true, congfig.data.YKuserList, data.joinuser);
        $(".people").unbind().bind("click", function() {
            var $this = $(this);
            listefit.config({
                title : "参会人",
                list : ykusershowlist,
                radio : false,
                besure : function() {
                    updateJoinmeeting(eval(congfig.choiceContent()));
                }
            });
        });
    };


    //修改会议参会人请求
    var updateJoinmeeting=function(vObj){
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "updateMeetingJoinPeople.html",
            data: {
                version: congfig.data.version,
                meetcode: meeting_object.base_meeting_code,
                newjoinpeople: congfig.oldContent(vObj),
                logintype: cookieopt.getlogintype()
            },
            success: function (vdata) {
                if(vdata.message=="success"){
                    $.showtip("保存成功","success");
                    data.joinuser=vObj;
                    congfig.data.version=vdata.version;
                    initYKparp();
                }else if(vdata.message=="noroot"){
                    $.showtip("无修改权限","error");
                }else if(vdata.message=="nomeeting"){
                    $.showtip("会议不存在","error");
                }else if(vdata.message=="nouser"){
                    $.showtip("未登录用户","error");
                }else if(vdata.message=="upfail"){
                    $.showtip("数据已被修改","error");
                }else{
                    $.showtip("修改失败","error");
                }

                $.hideloading();

                setTimeout(function () {
                    $.hidetip();
                },2000);
            }
        });

    };


    return {
        data:data,
        editClick:YKuserClick
    };
})();


//选择机构
var InvestmentInfoconfig = (function() {
    var data={
        invicont:null
    };

    //机构打印
    var initInvestment = function() {
        var invicont=template.compile(data_model.invicont)(data);
        if(invicont==""){
            invicont="<li>暂无数据</li>";
        }
        $(".invicont").html(invicont);
        if(meeting_object.userstatus=="1") {
            invesclick();
        }

    };

    // 机构信息选中行事件
    var invesclick = function() {
        $(".invicont li").unbind().bind("click", function() {
            var id = $(this).attr("p-index");
            inputlsit_edit.config({
                title:"修改相关机构",
                list:[
                    {id:"investment",lable:"投资机构",type:"text",readonly:"readonly"},
                    {id:"investor",lable:"投资人",type:"text",readonly:"readonly"},
                    {id:"investorposiname",lable:"职位",type:"text",maxlength:"20"}
                ],
                besurebtn:"关闭",
                cancle:"删除",
                complete: function () {
                    $("#investorposiname").attr('readonlyflag',"readonlyflag");
                    //机构code
                    $("#investment").attr("investmentcode",data.invicont[id].code);
                    //机构name
                    $("#investment").val(data.invicont[id].name);
                    //机构添加类型
                    $("#investment").attr("operatype",data.invicont[id].investmenttype);
                    //投资人code
                    $("#investor").attr("investorcode",data.invicont[id].personcode);
                    //投资人name
                    $("#investor").val(data.invicont[id].personname);
                    //投资人类型
                    $("#investor").attr("operatype",data.invicont[id].investortype);
                    //投资人职位
                    $("#investorposiname").val(data.invicont[id].personpo);
                    
                    $("#investorposiname").attr('readonly',true);
                },
                submit: function () {
                    inputlsit_edit.close();
                },
                canmit: function () {
                    inputlsit_edit.config({
                        title:"提示",//弹层标题
                        html:true,//是否以html显示
                        besurebtn:"确定",//确定按钮文字
                        cancle:"取消",//取消按钮文字 （如果不写，则不会显示该按钮）
                        htmltext:"<div style='text-align: center;font-size: 15px;'>您确定要删除该条记录吗？</div>",
                        submit: function () {
                            inputlsit_edit.close();
                            $.showloading();
                            $.ajax({
                                async : true,
                                dataType : 'json',
                                type : 'post',
                                url : "delMeetingOrg.html",
                                data : {
                                    meetcode : meeting_object.base_meeting_code,
                                    version : congfig.data.version,
                                    orgcode : data.invicont[id].code,
                                    investorcode : data.invicont[id].personcode,
                                    logintype : cookieopt.getlogintype()
                                },
                                success : function(vdata) {
                                    if (vdata.message == "success") {
                                        $.showtip("删除成功");
                                        congfig.data.version=vdata.version;
                                        data.invicont.baoremove(id);
                                        initInvestment();
                                    }else if(vdata.message == "change"){
                                        //会议已被修改
                                        $.showtip("会议已被修改");
                                    }else if(vdata.message == "noroot"){
                                        //无权限修改
                                        $.showtip("无修改权限");
                                    }else if(vdata.message == "nomeeting"){
                                        //会议不存在
                                        $.showtip("会议不存在");
                                    }else if(vdata.message == "nouser"){
                                        //未登录用户
                                        $.showtip("未登录用户");
                                    }else if(vdata.message == "fail"){
                                        //删除失败
                                        $.showtip("删除失败");
                                    }else if(vdata.message == "error"){
                                        $.showtip("系统异常");
                                    }else {
                                        $.showtip("异常消息");
                                    }
                                    $.hideloading();
                                    setTimeout(function () {$.hidetip();},2000);
                                }
                            });


                        },//点击确定按钮回调方法
                        canmit: function () {
                            inputlsit_edit.close();
                        }//点击取消按钮回调方法
                    });

                }
            });

        });
    };

    //添加相关机构图标事件
    var addinvenstmentClick=function(){
        $(".jigou .addbtn").unbind().bind("click",function(e){
            inputlsit_edit.config({
                title:"添加相关机构",
                list:[
                    {id:"investment",lable:"投资机构",type:"text",readonly:"readonly"},
                    {id:"investor",lable:"投资人",type:"text",readonly:"readonly"},
                    {id:"investorposiname",lable:"职位",type:"text",readonly:"readonly",maxlength:"20"}
                ],
                complete: function () {
                    $("#investorposiname").attr('readonlyflag',"readonlyflag");
                    investmentClick();
                    investorClick();
                },
                submit: function () {
                    var bool=0;
                    if(data.invicont.length>0 && $("#investment").attr("investmentcode").trim()!=""){
                        for ( var i = 0; i < data.invicont.length; i++) {
                            if(data.invicont[i].name==$("#investment").val()
                                && data.invicont[i].personname==$("#investor").val()){
                                bool=1;
                                break;
                            }
                        }
                    }
                    if(bool==1){
                        $.showtip("当前投资机构投资人已选择");
                        setTimeout(function () {$.hidetip();},2000);
                    }else if($("#investment").val().trim()==""){
                        $.showtip("投资机构不能为空");
                        setTimeout(function () {$.hidetip();},2000);
                    }else{
                        var investmentinfo=new Object();
                        //机构code
                        investmentinfo.code=$("#investment").attr("investmentcode");
                        //机构name
                        investmentinfo.name=$("#investment").val();
                        //机构添加类型
                        investmentinfo.investmenttype=$("#investment").attr("operatype");
                        //投资人code
                        investmentinfo.personcode=$("#investor").attr("investorcode");
                        //投资人name
                        investmentinfo.personname=$("#investor").val();
                        //投资人类型
                        investmentinfo.investortype=$("#investor").attr("operatype");
                        //投资人职位
                        investmentinfo.personpo=$("#investorposiname").val();
                        inputlsit_edit.close();
                        updateOrgRequest(investmentinfo);
                    }
                }
            });


        });
    };


    //添加相关机构请求
    var updateOrgRequest=function(vObj){
        $.showloading();
        $.ajax({
            async : true,
            dataType : 'json',
            type : 'post',
            url : "updateMeetingOrg.html",
            data : {
                meetcode: meeting_object.base_meeting_code,
                version: congfig.data.version,
                neworg : JSON.stringify(vObj),
                logintype : cookieopt.getlogintype()
            },
            success : function(vdata) {
                if (vdata.message == "success") {
                    $.showtip("修改成功");
                    selectinvestment.searchdata.searchorg="";
                    selectinvestor.searchdata.searchinv="";
                    if(vObj.investmenttype=="add"){
                        vObj.code=vdata.orgcode;
                    }
                    data.invicont.push(vObj);
                    congfig.data.version=vdata.version;
                    initInvestment();
                }else if(vdata.message == "change"){
                    //会议已被修改
                    $.showtip("会议已被修改");
                }else if(vdata.message == "orgexsit"){
                    //机构简称已存在
                    $.showtip("机构简称已存在");
                }else if(vdata.message == "noroot"){
                    //无权限修改
                    $.showtip("无修改权限");
                }else if(vdata.message == "nomeeting"){
                    //会议不存在
                    $.showtip("会议不存在");
                }else if(vdata.message == "nouser"){
                    //未登录用户
                    $.showtip("未登录用户");
                }else if(vdata.message == "nullorg"){
                    //机构信息存在空项
                    $.showtip("机构信息存在空项");
                }else if(vdata.message == "nullinvestor"){
                    //投资人信息存在空项
                    $.showtip("投资人信息存在空项");
                }else if(vdata.message == "error"){
                    $.showtip("系统异常");
                }else {
                    $.showtip("异常消息");
                }
                $.hideloading();
                setTimeout(function () {$.hidetip();},2000);
            }
        });

    };



    //相关机构选择
    var investmentClick=function(){
        $("#investment").unbind().bind("click",function(e){
            selectinvestment.config();
        });
    };
    //投资人选择
    var investorClick=function(){
        $("#investor").unbind().bind("click",function(e){
            if($("#investment").attr("operatype")=="add" ||
                ($("#investment").attr("operatype")=="select"
                    && $("#investment").attr("investmentcode")!=""
                    && $("#investment").attr("investmentcode")!=null)){
                selectinvestor.config();
            }
            else{
                $.showtip("请选择机构");
                setTimeout(function () {$.hidetip();},2000);
            }
        });
    };



    return {
        data:data,
        InvestmentInfoLoad : initInvestment,
        InvestmentAddClick : addinvenstmentClick
    };
})();

//机构选择页面
var selectinvestment=(function () {
    var opt={investmentcode:""};
    var savedata={
        searchorg:""
    };
    var dataconfig= function () {
        opt.page={
            pageCount:1,
            pageSize:new CommonVariable().PAGESIZE,
            totalCount:2
        };
        opt.firstload=true;
        $(".inner").html("<div class='lists'></div>");
    };
    var config= function () {

        WCsearch_list.config({
            allowadd:true,
            searchclick: function () {
                dataconfig();
                if(opt.dropload){
                    opt.dropload.Destroy();
                };
                $.showloading();
                investment_ajax();
                opt.dropload= WCsearch_list.dropload({
                    loadDownFn: function (me) {
                        if(Number(opt.page.pageCount)<Number(opt.page.totalPage)){
                            opt.page.pageCount=opt.page.pageCount+1;
                            investment_ajax();
                        }else{
                            $.showtip("暂无更多数据");
                            setTimeout(function () {$.hidetip();},2000);
                            opt.dropload.resetload();
                            opt.dropload.lock();
                        }
                    }
                });
            },
            addclick: function () {
                var vdata = {name: '机构简称'};
                var addcomphtml=template.compile(table_data.addcomp)(vdata);
                $(".inner").html("<div class='lists'></div>");
                $(".lists").html(addcomphtml);
                $(".addcompinvestname").val(savedata.searchorg);
                $("#addcompinvet").unbind().bind("click", function() {
                    if(""!=$(".addcompinvestname").val().trim()){
//                        var bool=0;
//                        if(InvestmentInfoconfig.data.invicont.length>0){
//                            for ( var i = 0; i < InvestmentInfoconfig.data.invicont.length; i++) {
//                                if(InvestmentInfoconfig.data.invicont[i].name==$(".addcompinvestname").val().trim() ){
//                                    bool=1;
//                                    break;
//                                }
//                            }
//                        }
//                        if(bool==1){
//                            $.showtip("当前添加的机构已存在");
//                            setTimeout(function () {$.hidetip();},2000);
//                        }else{
//                            invementadd_ajax();
//                        }
                        invementadd_ajax();
                    }else{
                        WCsearch_list.closepage();
                    }

                });
            }
        });
        dataconfig();
    };
    var investment_ajax= function () {
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "findInvestmentNameListByName.html",
            data: {
                type:1,
                name:$(".select-page-input").val(),
                pageSize:opt.page.pageSize,
                pageCount:opt.page.pageCount,
                logintype:cookieopt.getlogintype()
            },
            success: function (data) {
                $.hideloading();
                opt.page=data.page;
                if(data.message=="success"){
                    savedata.searchorg=$(".select-page-input").val().trim();

                    var html=table_data.investmentlistNull;
                    if(data.list.length>0){
                        html=template.compile(table_data.investmentselectlist)(data);
                        $(".lists").append(html);
                        opt.dropload.resetload();
                        $(".item").unbind().bind("click",function () {
                            savedata.searchorg="";
                            $("#investment").val($(this).html());
                            opt.investmentcode=$(this).attr('data-org-code');
                            $("#investment").attr("operatype",'select');
                            if($("#investment").attr("investmentcode")!=$(this).attr('data-org-code')){
                                $("#investor").val("");
                                $("#investor").attr("operatype",'no');
                                $("#investor").attr("investorcode","");
                                $("#investorposiname").val("");
                                $("#investorposiname").attr('readonly',true);
                            }
                            $("#investment").attr("investmentcode",$(this).attr('data-org-code'));
                            WCsearch_list.closepage();
                        });
                    }else{
                        $.showtip("暂无数据");
                        setTimeout(function() {$.hidetip();}, 2000);
                    }

                }else if(data.message == "nomore"){
                    $.showtip("暂无数据");
                    setTimeout(function() {$.hidetip();}, 2000);

                }else {
                    $.showtip("查询异常");
                    setTimeout(function() {$.hidetip();}, 2000);
                }
            }
        });
    };
    var invementadd_ajax = function() {
        $.showloading();
        $.ajax({
            async : true,
            dataType : 'json',
            type : 'post',
            url : "queryInvestmentOnlyNamefottrade.html",
            data : {
                name : $(".addcompinvestname").val().trim(),
                logintype : cookieopt.getlogintype()
            },
            success : function(data) {
                $.hideloading();
                $("#investment").val($(".addcompinvestname").val().trim());
                if (data.message == "nodata") {
                    opt.investmentcode="";
                    $("#investment").attr("operatype",'add');
                    $("#investment").attr("investmentcode","");
                    if($("#investor").val()!=null
                        && $("#investor").val()!=""){
                        $("#investorposiname").val("");
                        $("#investorposiname").attr('readonly',false);
                    }
                    else{
                        $("#investorposiname").val("");
                        $("#investorposiname").attr('readonly',true);
                    }
                    WCsearch_list.closepage();
                }else if(data.message == "exsit"){
                    $.showtip("机构简称已存在");
                    setTimeout(function() {$.hidetip();}, 2000);

                }else {
                    $.showtip("查询异常");
                    setTimeout(function() {$.hidetip();}, 2000);
                }
            }
        });
    };
    return {
        config:config,
        data:opt,
        searchdata:savedata
    };
})();

//投资人选择页面
var selectinvestor = (function() {
    var opt = {
        investorcode : ""
    };
    var savedata={
        searchinv:""
    };
    var dataconfig = function() {
        opt.page = {
            pageCount : 1,
            pageSize:new CommonVariable().PAGESIZE,
            totalCount : 2
        };
        opt.firstload = true;
        $(".inner").html("<div class='lists'></div>");
    };
    var config = function() {

        WCsearch_list.config({
            allowadd : true,
            searchclick : function() {
                dataconfig();
                if (opt.dropload) {
                    opt.dropload.Destroy();
                };
                $.showloading();
                investor_ajax();
                opt.dropload = WCsearch_list
                    .dropload({
                        loadDownFn : function(me) {
                            if (Number(opt.page.pageCount) < Number(opt.page.totalPage)) {
                                opt.page.pageCount = opt.page.pageCount + 1;
                                investor_ajax();
                            } else {
                                $.showtip("暂无更多数据");
                                setTimeout(function() {$.hidetip();}, 2000);
                                opt.dropload.resetload();
                                opt.dropload.lock();
                            }
                        }
                    });
            },
            addclick: function () {
                var data = {name: '投资人姓名'};
                var addcomphtml=template.compile(table_data.addcomp)(data);
                $(".inner").html("<div class='lists'></div>");
                $(".lists").html(addcomphtml);
                $(".addcompinvestname").val(savedata.searchinv);
                $("#addcompinvet").unbind().bind("click", function() {
                    if(""!=$(".addcompinvestname").val().trim()){

                        if("select"==$("#investment").attr("operatype")){
                            var invesbool=addinvestor_ajax();
                            if(invesbool==false){
                                $.showtip("当前添加的机构投资人已存在");
                                setTimeout(function () {$.hidetip();},2000);
                                return;
                            }
                        }

                        $("#investor").val($(".addcompinvestname").val().trim());
                        opt.investorcode="";
                        $("#investor").attr("operatype",'add');
                        $("#investor").attr("investorcode","");
                        $("#investorposiname").val("");
                        $("#investorposiname").attr('readonly',false);
                    }
                    WCsearch_list.closepage();
                });
            }
        });
        dataconfig();
    };
    var investor_ajax = function() {
        $.showloading();
        var invementcode=null;
        if($("#investment").attr("investmentcode")!=undefined ){
            invementcode=$("#investment").attr("investmentcode");
        }
        $.ajax({
            async : true,
            dataType : 'json',
            type : 'post',
            url : "queryinvestorlistByinvementcode.html",
            data : {
                name : $(".select-page-input").val(),
                invementcode:invementcode,
                pageSize : opt.page.pageSize,
                pageCount : opt.page.pageCount,
                logintype : cookieopt.getlogintype()
            },
            success : function(data) {
                $.hideloading();
                opt.page = data.page;
                savedata.searchinv=$(".select-page-input").val().trim();
                if (data.message == "success") {
                    var html=table_data.investorSelectListNull;
                    if(data.list.length>0){
                        html=template.compile(table_data.investorSelectList)(data);

                        $(".lists").append(html);

                        opt.dropload.resetload();
                        $(".item").unbind().bind("click", function() {
                            savedata.searchinv="";
                            $("#investor").val($(this).html());
                            opt.investorcode=$(this).attr('data-investor-code');
                            $("#investor").attr("operatype",'select');
                            $("#investor").attr("investorcode",$(this).attr('data-investor-code'));
                            if("select"==$("#investment").attr("operatype")){
                                $("#investorposiname").val($(this).attr('base_investor_posiname'));
                                $("#investorposiname").attr('readonly',true);
                            }
                            else if("add"==$("#investment").attr("operatype")){
                                $("#investorposiname").val("");
                                $("#investorposiname").attr('readonly',false);
                            }
                            WCsearch_list.closepage();
                        });
                    }
                }
                else if(data.message == "nomore"){
                    $.showtip("暂无数据");
                    setTimeout(function() {$.hidetip();}, 2000);

                }else {
                    $.showtip("查询异常");
                    setTimeout(function() {$.hidetip();}, 2000);
                }
            }
        });
    };

    var addinvestor_ajax=function() {
        var addinvesbool=true;
        $.showloading();
        $.ajax({
            async : false,
            dataType : 'json',
            type : 'post',
            url : "queryOrgInvestorOnlyNamefottrade.html",
            data : {
                orgcode: $("#investment").attr("investmentcode").trim(),
                name : $(".addcompinvestname").val().trim(),
                logintype : cookieopt.getlogintype()
            },
            success : function(data) {
                $.hideloading();
                if (data.message == "nodata") {
                    addinvesbool= true;
                }else if(data.message == "exsit"){
                    addinvesbool= false;

                }else {
                    addinvesbool= false;
                }
            }
        });

        return addinvesbool;
    };

    return {
        config : config,
        data : opt,
        searchdata:savedata
    };
})();




//选择公司
var CompInfoconfig = (function() {
    var data={
        companycont:null
    };

    //公司打印
    var initComp = function() {

        var html = "";
        if(data.companycont!=null && data.companycont[0]!=null){
            html=template.compile(data_model.company)(data);
        }
        if(html==""){
            html="<li>暂无数据</li>";
        }

        $(".companycont").html(html);
        if(meeting_object.userstatus=="1") {
            compeditclick();// 编辑公司选中行事件
        }
    };

    // 公司信息选中行事件
    var compeditclick = function() {
        $(".companycont li").unbind().bind("click", function() {
            var id = $(this).attr("p-index");
            inputlsit_edit.config({
                title:"修改相关公司",
                list:[
                    {id:"compid",lable:"公司",type:"text",readonly:"readonly"},
                    {id:"entrepreneur",lable:"联系人",type:"text",readonly:"readonly"},
                    {id:"entrepreneurposiname",lable:"职位",type:"text",readonly:"readonly"}
                ],
                besurebtn:"关闭",
                cancle:"删除",
                complete: function () {
                    $("#entrepreneurposiname").attr('readonlyflag',"readonlyflag");
                    //公司code
//                    $("#compid").attr("base_comp_code",data.companycont[id].code);
                    //公司name
                    $("#compid").val(data.companycont[id].name);
                    //公司添加类型
//                    $("#compid").attr("operatype",data.companycont[id].comptype);
                    //公司联系人code
//                    $("#entrepreneur").attr("base_entrepreneur_code",data.companycont[id].personcode);
                    //公司联系人name
                    $("#entrepreneur").val(data.companycont[id].personname);
                    //公司联系人类型
//                    $("#entrepreneur").attr("operatype",data.companycont[id].entrepreneurtype);
                    //公司联系人职位
                    $("#entrepreneurposiname").val(data.companycont[id].personpo);

                    $("#entrepreneurposiname").attr('readonly',true);

                },
                submit: function () {
                    inputlsit_edit.close();
                },
                canmit: function () {
                    inputlsit_edit.config({
                        title:"提示",//弹层标题
                        html:true,//是否以html显示
                        besurebtn:"确定",//确定按钮文字
                        cancle:"取消",//取消按钮文字 （如果不写，则不会显示该按钮）
                        htmltext:"<div style='text-align: center;font-size: 15px;'>您确定要删除该条记录吗？</div>",
                        submit: function () {
                            inputlsit_edit.close();
                            $.showloading();
                            $.ajax({
                                async : true,
                                dataType : 'json',
                                type : 'post',
                                url : "delMeetingCompany.html",
                                data : {
                                    meetcode : meeting_object.base_meeting_code,
                                    version : congfig.data.version,
                                    companycode : data.companycont[id].code,
                                    relepcode : data.companycont[id].personcode,
                                    logintype : cookieopt.getlogintype()
                                },
                                success : function(vdata) {
                                    if (vdata.message == "success") {
                                        $.showtip("删除成功");
                                        congfig.data.version=vdata.version;
                                        data.companycont.baoremove(Number(id));
                                        initComp();

                                    }else if(vdata.message == "change"){
                                        //会议已被修改
                                        $.showtip("会议已被修改");
                                    }else if(vdata.message == "noroot"){
                                        //无权限修改
                                        $.showtip("无修改权限");
                                    }else if(vdata.message == "nomeeting"){
                                        //会议不存在
                                        $.showtip("会议不存在");
                                    }else if(vdata.message == "nouser"){
                                        //未登录用户
                                        $.showtip("未登录用户");
                                    }else if(vdata.message == "fail"){
                                        //删除失败
                                        $.showtip("删除失败");
                                    }else if(vdata.message == "error"){
                                        $.showtip("系统异常");
                                    }else {
                                        $.showtip("异常消息");
                                    }
                                    $.hideloading();

                                    setTimeout(function () {$.hidetip();},2000);
                                }
                            });


                        },//点击确定按钮回调方法
                        canmit: function () {
                            inputlsit_edit.close();
                        }//点击取消按钮回调方法
                    });


                }
            });

        });
    };



    //添加公司图标事件
    var addcompClick=function(){
        $(".gongsi .addbtn").unbind().bind("click",function(e){
            inputlsit_edit.config({
                title:"添加相关公司",
                list:[
                    {id:"compid",lable:"公司",type:"text",readonly:"readonly"},
                    {id:"entrepreneur",lable:"联系人",type:"text",readonly:"readonly"},
                    {id:"entrepreneurposiname",lable:"职位",type:"text",readonly:"readonly",maxlength:"20"},
                    {id: "planshow", lable: "创建融资计划",type:"checkbox"},
                    {id: "plantime", lable: "计划时间",type:"date"},
                    {id: "emailtime", lable: "提醒时间",type:"date"},
                    {id: "palntext", lable: "计划内容",type:"textarea",maxlength:"100"}
                ],
                complete: function () {
                    $("#entrepreneurposiname").attr('readonlyflag',"readonlyflag");
                    $("#planshow").parents("ul[class='inputlist']").children().eq(4).css("display","none");
                    $("#planshow").parents("ul[class='inputlist']").children().eq(5).css("display","none");
                    $("#planshow").parents("ul[class='inputlist']").children().eq(6).css("display","none");
                    //选择公司
                    compselectClick();
                    //选择公司联系人
                    entrepreneurselectClick();
                    //融资计划选择
                    compplanClick();
                },
                submit: function () {
                    var bool=0;
                    if(data.companycont.length>0){
                        for ( var i = 0; i < data.companycont.length; i++) {
                            if(data.companycont[i].code==$("#compid").attr("base_comp_code")){
                                bool=1;
                                break;
                            }
                        }
                    }
                    if(bool==1){
                        $.showtip("当前公司已选择");
                        setTimeout(function () {$.hidetip();},2000);
                        return;
                    }else if($("#compid").val().trim()==""){
                        $.showtip("公司不能为空");
                        setTimeout(function () {$.hidetip();},2000);
                        return;
                    }else if(
                        $("#planshow").is(':checked')&& ($("#plantime").val().trim()==""
                        || isNaN(new Date($("#plantime").val()).getTime()))){
                        $.showtip("融资计划的计划时间不可为空");
                        setTimeout(function () {$.hidetip();},2000);
                        return;
                    }
                    else if(
                        $("#planshow").is(':checked')
                        && ($("#emailtime").val().trim()==""
                        || isNaN(new Date($("#emailtime").val()).getTime()))
                        ){
                        $.showtip("融资计划的提醒时间不可为空");
                        setTimeout(function () {$.hidetip();},2000);
                        return;
                    }
                    else if(
                        $("#planshow").is(':checked')&& $("#palntext").val().trim()==""){
                        $.showtip("融资计划的计划内容不可为空");
                        setTimeout(function () {$.hidetip();},2000);
                        return;
                    }
                    else{
                        plantime = $("#plantime").val();
                        emailtime = $("#emailtime").val();

                        if ($("#planshow").is(':checked')) {

                            var thisnowdate=new Date(nowdate.substring(0,10).replace(/-/g,"/"));
                            emailtime=new Date(emailtime.replace(/-/g,"/").replace("年","/").replace("月","/").replace("日","/"));
                            plantime=new Date(plantime.replace(/-/g,"/").replace("年","/").replace("月","/").replace("日","/"));
                            if(thisnowdate>plantime){
                                $.showtip("融资计划的计划时间需大于当前日期");
                                setTimeout(function () {$.hidetip();},2000);
                                return;
                            }
                            if(thisnowdate>emailtime ){
                                $.showtip("融资计划的提醒时间需大于当前日期");
                                setTimeout(function () {$.hidetip();},2000);
                                return;
                            }
                        }
                        var compinfo=new Object();
                        //公司code
                        compinfo.code=$("#compid").attr("base_comp_code");
                        //公司name
                        compinfo.name=$("#compid").val();
                        //公司添加类型
                        compinfo.comptype=$("#compid").attr("operatype");
                        //公司联系人code
                        compinfo.personcode=$("#entrepreneur").attr("base_entrepreneur_code");
                        //公司联系人name
                        compinfo.personname=$("#entrepreneur").val();
                        //公司联系人类型
                        compinfo.entrepreneurtype=$("#entrepreneur").attr("operatype");
                        //公司联系人职位
                        compinfo.personpo=$("#entrepreneurposiname").val();

                        //是否创建公司融资计划
                        compinfo.financplanflag=$("#planshow").is(':checked');
                        //公司融资计划计划日期
                        compinfo.plandate=$("#plantime").val();
                        //公司融资计划提醒日期
                        compinfo.remindate=$("#emailtime").val();
                        //公司融资计划内容
                        compinfo.plancont=$("#palntext").val();

                        inputlsit_edit.close();

                        updcompanyClick(compinfo);

                    }
                    
                }
            });


        });
    };


    //添加相关公司请求
    var updcompanyClick=function(vObj){
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "updateMeetingCompany.html",
            data: {
                meetcode: meeting_object.base_meeting_code,
                version: congfig.data.version,
                newcompany: JSON.stringify(vObj),
                logintype: cookieopt.getlogintype()
            },
            success: function (vdata) {

                $.hideloading();
                if(vdata.message=="success"){
                    $.showtip("保存成功");
                    congfig.data.version=vdata.version;

                    selectcompany.searchdata.searchcom="";
                    selectentrepreneur.searchdata.searchinv="";

                    if(vObj.comptype=="add"){
                        vObj.code=vdata.companycode;
                    }
                    data.companycont.push(vObj);
                    initComp();

                }else if(vdata.message == "change"){
                    //会议已被修改
                    $.showtip("会议已被修改");
                }else if(vdata.message == "compnameexsit"){
                    //机构简称已存在
                    $.showtip("公司简称已存在");
                }else if(vdata.message == "noroot"){
                    //无权限修改
                    $.showtip("无修改权限");
                }else if(vdata.message == "nomeeting"){
                    //会议不存在
                    $.showtip("会议不存在");
                }else if(vdata.message == "nouser"){
                    //未登录用户
                    $.showtip("未登录用户");
                }else if(vdata.message == "nullcompany"){
                    //公司信息存在空项
                    $.showtip("公司信息存在空项");
                }else if(vdata.message == "nulllinkuser"){
                    //联系人信息存在空项
                    $.showtip("联系人信息存在空项");
                }else if(vdata.message == "error"){
                    $.showtip("系统异常");
                }else {
                    $.showtip("异常消息");
                }
                $.hideloading();
                setTimeout(function () {$.hidetip();},2000);
            }
        });
    };

    //相关公司选择
    var compselectClick=function(){
        $("#compid").unbind().bind("click",function(e){
            selectcompany.config();
        });
    };
    //公司联系人选择
    var entrepreneurselectClick=function(){
        $("#entrepreneur").unbind().bind("click",function(e){
            if($("#compid").attr("operatype")=="add" ||
                ($("#compid").attr("operatype")=="select"
                    && $("#compid").attr("base_comp_code")!=""
                    && $("#compid").attr("base_comp_code")!=null)){
                selectentrepreneur.config();
            }
            else{
                $.showtip("请选择公司");
                setTimeout(function () {$.hidetip();},2000);
            }
        });
    };

    //融资计划选择
    var compplanClick=function(){
        $("#planshow").unbind().bind("click",function(e){
            if($("#planshow").is(':checked')){
                $("#planshow").parents("ul[class='inputlist']").children().eq(4).removeAttr("style");
                $("#planshow").parents("ul[class='inputlist']").children().eq(5).removeAttr("style");
                $("#planshow").parents("ul[class='inputlist']").children().eq(6).removeAttr("style");
            }
            else{
                //公司融资计划计划日期
                $("#plantime").val("");
                //公司融资计划提醒日期
                $("#emailtime").val("");
                //公司融资计划内容
                $("#palntext").val("");
                $("#planshow").parents("ul[class='inputlist']").children().eq(4).css("display","none");
                $("#planshow").parents("ul[class='inputlist']").children().eq(5).css("display","none");
                $("#planshow").parents("ul[class='inputlist']").children().eq(6).css("display","none");
            }
        });
    };
    return {
        data:data,
        CompInfoLoad : initComp,
        CompAddClick : addcompClick
    };
})();


//公司选择页面
var selectcompany = (function() {
    var opt = {
        companycode : ""
    };
    var savedata={
        searchcom:""
    };
    var dataconfig = function() {
        opt.page = {
            pageCount : 1,
            pageSize:new CommonVariable().PAGESIZE,
            totalCount : 2
        };
        opt.firstload = true;
        $(".inner").html("<div class='lists'></div>");
    };
    var config = function() {

        WCsearch_list.config({
            allowadd : true,
            searchclick : function() {
                dataconfig();
                if (opt.dropload) {
                    opt.dropload.Destroy();
                }
                $.showloading();
                company_ajax();
                opt.dropload = WCsearch_list.dropload({
                    loadDownFn : function(me) {
                        if (Number(opt.page.pageCount) < Number(opt.page.totalPage)) {
                            opt.page.pageCount = opt.page.pageCount + 1;
                            company_ajax();
                        } else {
                            $.showtip("暂无更多数据");
                            setTimeout(function() {$.hidetip();}, 2000);
                            opt.dropload.resetload();
                            opt.dropload.lock();
                        }
                    }
                });
            },
            addclick: function () {
                var vdata = {name: '公司简称'};
                var addcomphtml=template.compile(table_data.addcomp)(vdata);
                $(".inner").html("<div class='lists'></div>");
                $(".lists").html(addcomphtml);
                $(".addcompinvestname").val(savedata.searchcom);
                $("#addcompinvet").unbind().bind("click", function() {
                    if(""!=$(".addcompinvestname").val().trim()){
                        var bool=0;
                        if(CompInfoconfig.data.companycont.length>0){
                            for ( var i = 0; i < CompInfoconfig.data.companycont.length; i++) {
                                if(CompInfoconfig.data.companycont[i].name==$(".addcompinvestname").val().trim() ){
                                    bool=1;
                                    break;
                                }
                            }
                        }
                        if(bool==1){
                            $.showtip("当前添加的公司已存在");
                            setTimeout(function () {$.hidetip();},2000);
                        }else{
                            companyadd_ajax();
                        }
                    }
                    else{
                        WCsearch_list.closepage();
                    }

                });
            }
        });
        dataconfig();
    };
    var company_ajax = function() {
        $.showloading();
        $.ajax({
            async : true,
            dataType : 'json',
            type : 'post',
            url : "querycompanylistByname.html",
            data : {
                name : $(".select-page-input").val(),
                pageSize : opt.page.pageSize,
                pageCount : opt.page.pageCount,
                logintype : cookieopt.getlogintype()
            },
            success : function(data) {
                $.hideloading();
                savedata.searchcom=$(".select-page-input").val().trim();
                opt.page = data.page;
                if (data.message == "success") {
                    var html = template.compile(table_data.companyselectlist)(data);
                    $(".lists").append(html);
                    opt.dropload.resetload();
                    $(".item").unbind().bind("click", function() {
                        savedata.searchcom="";
                        $("#compid").val($(this).html());
                        opt.companycode = $(this).attr('data-com-code');
                        $("#compid").attr("operatype",'select');
                        if($("#compid").attr("base_comp_code")!=$(this).attr('data-org-code')){
                            $("#entrepreneur").val("");
                            $("#entrepreneur").attr("operatype",'no');
                            $("#entrepreneur").attr("base_entrepreneur_code","");
                            $("#entrepreneurposiname").val("");
                            $("#entrepreneurposiname").attr('readonly',true);
                        }
                        $("#compid").attr("base_comp_code",$(this).attr('data-com-code'));
                        WCsearch_list.closepage();
                    });
                }
                else if(data.message == "nomore"){
                    $.showtip("暂无数据");
                    setTimeout(function() {$.hidetip();}, 2000);

                }else {
                    $.showtip("查询异常");
                    setTimeout(function() {$.hidetip();}, 2000);
                }
            }
        });
    };
    var companyadd_ajax = function() {
        $.showloading();
        $.ajax({
            async : true,
            dataType : 'json',
            type : 'post',
            url : "queryCompOnlyNamefortrade.html",
            data : {
                name : $(".addcompinvestname").val().trim(),
                logintype : cookieopt.getlogintype()
            },
            success : function(data) {
                $.hideloading();
                if (data.message == "nodata") {
                    $("#compid").val($(".addcompinvestname").val().trim());
                    opt.companycode="";
                    $("#compid").attr("operatype",'add');
                    $("#compid").attr("base_comp_code","");
                    if($("#entrepreneur").val()!=null
                        && $("#entrepreneur").val()!="" ){
                        $("#base_entrepreneur_posiname").val("");
                        $("#base_entrepreneur_posiname").attr('readonly',false);
                    }
                    else{
                        $("#base_entrepreneur_posiname").val("");
                        $("#base_entrepreneur_posiname").attr('readonly',true);
                    }
                    WCsearch_list.closepage();
                }
                else if(data.message == "exsit"){
                    $.showtip("公司简称已存在");
                    setTimeout(function() {$.hidetip();}, 2000);

                }else {
                    $.showtip("查询异常");
                    setTimeout(function() {$.hidetip();}, 2000);
                }
            }
        });
    };
    return {
        config : config,
        data : opt,
        searchdata:savedata
    };
})();

//公司联系人选择页面
var selectentrepreneur=(function () {
    var opt={entrepreneurcode:""};
    var savedata={
        searchinv:""
    };
    var dataconfig= function () {
        opt.page={
            pageCount:1,
            pageSize:new CommonVariable().PAGESIZE,
            totalCount:2
        };
        opt.firstload = true;
        $(".inner").html("<div class='lists'></div>");
    };
    var config=function(){

        WCsearch_list.config({
            allowadd:true,
            searchclick: function () {
                dataconfig();
                if(opt.dropload){
                    opt.dropload.Destroy();
                };
                $.showloading();
                entrepreneur_ajax();
                opt.dropload= WCsearch_list.dropload({
                    loadDownFn: function (me) {
                        if(Number(opt.page.pageCount)<Number(opt.page.totalPage)){
                            opt.page.pageCount=opt.page.pageCount+1;
                            entrepreneur_ajax();
                        }else{
                            $.showtip("暂无更多数据");
                            setTimeout(function () {$.hidetip(); },2000);
                            opt.dropload.resetload();
                            opt.dropload.lock();
                        }
                    }
                });
            },
            addclick: function () {
                var data = {name: '联系人姓名'};
                var addcomphtml=template.compile(table_data.addcomp)(data);
                $(".inner").html("<div class='lists'></div>");
                $(".lists").html(addcomphtml);
                $(".addcompinvestname").val(savedata.searchinv);
                $("#addcompinvet").unbind().bind("click", function() {
                    if(""!=$(".addcompinvestname").val().trim()){
                        $("#entrepreneur").val($(".addcompinvestname").val().trim());
                        opt.entrepreneurcode="";
                        $("#entrepreneur").attr("operatype",'add');
                        $("#entrepreneur").attr("base_entrepreneur_code","");
                        $("#entrepreneurposiname").val("");
                        $("#entrepreneurposiname").attr('readonly',false);

                    }
                    WCsearch_list.closepage();
                });
            }
        });
        dataconfig();
    };
    var entrepreneur_ajax=function(){
        $.showloading();
        var compcode=null;
        if($("#compid").attr("base_comp_code")!=undefined ){
            compcode=$("#compid").attr("base_comp_code");
        }

        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url:"queryentrepreneurlistBycompcode.html",
            data:{
                name:$(".select-page-input").val(),
                compcode:compcode,
                pageSize:opt.page.pageSize,
                pageCount:opt.page.pageCount,
                logintype:cookieopt.getlogintype()
            },
            success: function (data) {
                $.hideloading();
                opt.page=data.page;
                savedata.searchinv=$(".select-page-input").val().trim();
                if(data.message=="success"){
                    var html=table_data.investorSelectListNull;
                    if(data.list.length>0){
                        html=template.compile(table_data.entrepreneurSelectList)(data);

                        $(".lists").append(html);
                        opt.dropload.resetload();
                        $(".item").unbind().bind("click",function () {
                            savedata.searchinv="";
                            $("#entrepreneur").val($(this).html());
                            opt.entrepreneurcode=$(this).attr('base_entrepreneur_code');
                            $("#entrepreneur").attr("operatype",'select');
                            $("#entrepreneur").attr("base_entrepreneur_code",$(this).attr('base_entrepreneur_code'));
                            if("select"==$("#compid").attr("operatype")){
                                $("#entrepreneurposiname").val($(this).attr('base_entrepreneur_posiname'));
                                $("#entrepreneurposiname").attr('readonly',true);
                            }
                            else{
                                $("#entrepreneurposiname").val("");
                                $("#entrepreneurposiname").attr('readonly',false);
                            }
                            WCsearch_list.closepage();
                        });
                    }
                }else if(data.message == "nomore"){
                    $.showtip("暂无数据");
                    setTimeout(function() {$.hidetip();}, 2000);

                }else {
                    $.showtip("查询异常");
                    setTimeout(function() {$.hidetip();}, 2000);
                }
            }
        });
    };
    return{
        config:config,
        data:opt,
        searchdata:savedata
    };

})();

//保存会议内容
var meetcontConfig=(function(){
    var saveClick=function(){
        $(".saveMeetCont").unbind().bind("click",function(){
            var cont=$("#meetingcontentid").val();
            if(cont.trim()=="" ){
                $.showtip("会议内容不能为空");
                setTimeout(function() {$.hidetip();}, 2000);
            }else if(cont==meetingcont){
                $.showtip("会议内容未改变");
                setTimeout(function() {$.hidetip();}, 2000);
            }else{
                saveRequest(cont);
            }

        });
    };

    var saveRequest=function(val){
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "updateMeetingContent.html",
            data: {
                meetcode: meeting_object.base_meeting_code,
                version: congfig.data.version,
                content: val,
                logintype: cookieopt.getlogintype()
            },
            success: function (vdata) {

                if(vdata.message=="success"){
                    $.showtip("保存成功");
                    congfig.data.version=vdata.version;
                    meetingcont=val;
                }else if(vdata.message == "change"){
                    //会议已被修改
                    $.showtip("会议已被修改");
                }else if(vdata.message == "noroot"){
                    //无权限修改
                    $.showtip("无修改权限");
                }else if(vdata.message == "nomeeting"){
                    //会议不存在
                    $.showtip("会议不存在");
                }else if(vdata.message == "nouser"){
                    //未登录用户
                    $.showtip("未登录用户");
                }else if(vdata.message == "nullpar"){
                    //会议内容为空
                    $.showtip("内容为空");
                }else if(vdata.message == "error"){
                    $.showtip("系统异常");
                }else {
                    $.showtip("异常消息");
                }
                $.hideloading();

                setTimeout(function() {$.hidetip();}, 2000);

            }
        });

    };


    return {
        saveClick:saveClick
    };
})();

var meetingfile=(function(){
	var rend=function(){
		var html="";
		$(".meetingfileul").html(html);
		
		if(congfig.data.fileListInfo!=null&&congfig.data.fileListInfo.length>0){
			for(var i=0;i<congfig.data.fileListInfo.length;i++){
				html="<li><a href=\""+congfig.data.fileListInfo[i].base_meeting_src+"\" target=\"newWindows\"  class=\"filename\">"+congfig.data.fileListInfo[i].base_meeting_filename+"</a>"+
				"<span del=\""+congfig.data.fileListInfo[i].base_file_code+"\" class=\"glyphicon glyphicon-remove fileDelClick hidden\"></span></li>";
				$(".meetingfileul").append(html);
			}
			if(meeting_object.userstatus=="1"){
				$(".fileDelClick").removeClass("hidden");
				deleteclick();
			}
		}
		
	};
	
	var deleteclick=function(){
		$(".fileDelClick").unbind().bind("click",function(){
			var code=$(this).attr("del");
			inputlsit_edit.config({
                title:"提示",//弹层标题
                html:true,//是否以html显示
                besurebtn:"确定",//确定按钮文字
                cancle:"取消",//取消按钮文字 （如果不写，则不会显示该按钮）
                htmltext:"<div style='text-align: center;font-size: 15px;'>您确定要删除该条记录吗？</div>",
                submit: function () {
                	delajax(code);
                    inputlsit_edit.close();
                },//点击确定按钮回调方法
                canmit: function () {
                    inputlsit_edit.close();
                }//点击取消按钮回调方法
            });
		});
	};
	
	var delajax=function(vCode){

        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "delMeetingFile.html",
            data: {
            	filecode: vCode,
                logintype: cookieopt.getlogintype()
            },
            success: function (vdata) {
                if(vdata.message=="success"){
                    $.showtip("删除成功");
                    congfig.data.fileListInfo=vdata.fileinfo;
                    rend();
                }else if(vdata.message =="unexist"){
                	$.showtip("附件信息已被删除，请刷新页面");
                }else if(vdata.message =="unfile"){
                	$.showtip("附件信息不存在");
                }else if(vdata.message == "error"){
                    $.showtip("系统异常");
                }else if(vdata.message =="nouser"){
                	$.showtip("未登录");
                }else {
                    $.showtip("异常消息");
                }
                $.hideloading();
                setTimeout(function() {$.hidetip();}, 2000);
            }
        });
		
	};
	
	return{
		rendingFile:rend
	};
	
})();

