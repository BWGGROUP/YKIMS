/**
 * Created by shbs-tp004 on 15-9-9.
 */
var optactive=0;
var menuactive=3;

var opt={};
var addinvesbool=true;
var addcompbool=true;
var att=".*\.(doc|docx|xls|txt|pdf)$";
$(function () {
	
    congfig.init();
    addnote.btnclick();
    addmeeting.initMeetingClick();
    
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

var congfig=(function () {
    var data={
        mplist:meetingpeople.split(","),
        invicont:meeting_invicont==null?[]:eval(meeting_invicont),
        companycont:meeting_compcont==null?[]:eval(meeting_compcont),
        meetingcont:meetingcont,
        noteconfignum:CommonVariable().NOTECONFIGNUM,
        meetingcode:meetingcode,
        zhangkai:false,
        sharewadJson:null,
        sharetype:null,
        SysWadList:null,
        YKuserList:null,
        meetingtypeList:null,
        meetingtypeJson:null,
        meetingnote:null,
        version:"",
        meetingfile:null
    };
    
    var editdata={
        joinPeopleJson:null
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
                    data.meetingfile=vdata.meetingFile;
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
        editdata.joinPeopleJson=dicJoinMeetConfig(meeting_object.view_meeting_usercode,meeting_object.view_meeting_usercodename);
    	var peoplehtml=template.compile(data_model.people)(data);
        var meetingnote=template.compile(data_model.noteall)(data);
        if(peoplehtml=="<li></li>"){
            peoplehtml="<li>暂无数据</li>";
        }

        $(".meetpeo").html(peoplehtml);
        InvestmentInfoconfig.InvestmentInfoLoad();
        CompInfoconfig.CompInfoLoad();
        //$(".meeting-conten").html(data.meetingcont);

        if(meetingnote==""){
            meetingnote='<tr><td colspan="4" >暂无数据</td></tr>';
        }


        $(".notetable").html(meetingnote);
        if(data.noteconfignum<data.meetingnote.length){
            $(".closeshearch").show();
            $("[nodemore]").hide();
        }

        click();
        
        
        //会议附件下载
        addmeeting.loadlable();
//        if(data.meetingfile!=null&&data.meetingfile.base_meeting_src!=""){
//        	$("#loadmeetingfile").attr("href",data.meetingfile.base_meeting_src);
//        	$(".filename").html(data.meetingfile.base_meeting_filename);
//        	$("#loadmeetingfile").removeClass("hidden");
//        }

        if(meeting_object.base_meeting_typecode!=null
        		&&meeting_object.base_meeting_typecode!="" ){
            var map={};
            data.meetingtypeJson=[];
            map.code=meeting_object.base_meeting_typecode;
            map.name=meeting_object.base_meeting_typename;
            data.meetingtypeJson.push(map);

        }
        meetingtypeRend();

        
        //dwj 会议编辑判断是否为创建人
        if(meeting_object.userstatus=="1"){
        	$(".choicefile,.newfilename").removeClass("hidden");
        	addmeeting.filechange();

        	$(".meetingtypeClick .title .caret").removeClass("hidden");
            //会议类型编辑
            meetingtypeClick();
        	
            $(".contentDiv").html("<textarea class=\"txt_redord\" maxlength=\"3000\" id=\"meetingcontentid\">"
                +data.meetingcont+"</textarea>");
            $(".saveMeetCont").removeClass("hidden");

            meetcontConfig.saveClick();

            $(".shareClick,.joinmeeting,.trade_add_img,.delClick").removeClass("hidden");
            $(".delClick").addClass("glyphicon");
            
            //dwj 编辑参会人事件
            JoinMeetingClick();

            //添加相关机构事件
            InvestmentInfoconfig.InvestmentAddClick();

            //添加相关公司事件
            CompInfoconfig.CompAddClick();

            //加载分享范围
            initShareWad();
            //dwj 分享事件
            ShareWadClick();
        }else{
            $(".contentDiv").html("<pre class=\"meeting-conten\">"+data.meetingcont+"</pre>");

        }

    };
    //渲染参会人信息
    var rendPeople=function(){
    	var pdata={
    			list:editdata.joinPeopleJson
    		};
    	var peoplehtml=template.compile(data_model.editPeople)(pdata);
    	if(peoplehtml=="<li></li>"){
            peoplehtml="<li>暂无数据</li>";
        }
    	$(".meetpeo").html(peoplehtml);
    };
    
  //dwj 2016-3-4 加载分享范围
    var initShareWad = function() {
    	var html = "";
    	if (data.sharewadJson != null && data.sharewadJson != "" ) {
    		var ndata = {
    			list : data.sharewadJson
    		};
    		html = template.compile(data_model.labelInfoList)(ndata);
    	} else {
    		html = data_model.labelNull;
    	}
    	$(".sharerange").html(html);
    };

    //dwj 会议类型
    var meetingtypeRend=function(){

        var html = "";
        if (data.meetingtypeJson != null && data.meetingtypeJson != "" ) {
            var ndata = {
                list : data.meetingtypeJson
            };
            html = template.compile(data_model.labelInfoList)(ndata);
        } else {
            html = data_model.labelNull;
        }
        $(".typerange").html(html);

    };
    //dwj 会议类型编辑
    var meetingtypeClick=function(){
        $(".meetingtypeClick").unbind().bind("click",function(){
            var $this=$(this);
            var sharelist=labelListConfig(false, data.meetingtypeList, data.meetingtypeJson);
            tip_edit.config({
                $this: $this,
                title: "会议类型",
                list: sharelist,
                radio: true,
                besure: function () {
                    data.meetingtypeJson = eval(choiceContent());
                    updateMeetingType();
                    tip_edit.close();
//                    updateShare();
                }
            });
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
            layer.confirm('您确定要删除该条记录吗？', {
                title:"提示",
                icon: 0,
                btn: ['确定','取消'] //按钮
            }, function(index){
                deletnote($this.attr("del-code"));
                layer.close(index);
            }, function(index){
                layer.close(index);
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
                    $.showtip("删除成功","success");
                    noteconfig(notecode);
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
    var noteconfig= function (notecode) {
        if(notecode){
            data.meetingnote.baoremove(Number($("[del-code='"+notecode+"']").attr("i")));
        }

        var meetingnote=template.compile(data_model.noteall)(data);
        if(meetingnote==""){
            meetingnote='<tr><td colspan="4" >暂无数据</td></tr>';
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
    
  //dwj 会议参会人编辑事件
    var JoinMeetingClick=function(){
    	$(".editJoinMeet").unbind().bind("click",function(){
    		var $this = $(this).children("div").children("span");
    		tip_edit.config({
		         $this:$this,
		         title:"参会人员",
	    		 list : dicListConfig(true, data.YKuserList, editdata.joinPeopleJson),
	    		 radio : false,
	    		 besure : function() {
	    			editdata.joinPeopleJson=eval(choiceContent());
	    			tip_edit.close();
                    updateJoinmeeting();

	    		}
    		});
    	
    		
    	});
    };
    
 //dwj 分享范围事件
    var ShareWadClick = function() {
    		$(".shareClick").unbind().bind("click", function() {
    			var $this = $(this).children("div").children("span");
    			layer.open({
    	            title:"选择分享类型",
    	            type: 1,
    	            scrollbar: false,
    	            skin: 'layui-layer-rim', //加上边框
    	            area: ['350px','200px'], //宽高
    	            content: '<div class="sharefrom"><ul class="inputshare">' +
    	                '<li><span class=""><input id="ra1" name="sharetype" type="radio" value="1"/></span><span class="">分享给筐</span></li>' +
    	                '<li><span class=""><input id="ra2" name="sharetype" type="radio" value="2" /></span><span class="">分享给人</span></li>' +
    	                '</ul><div class="btn-box"><button class="btn btn-default getShareVal">确定</button></div></div>'
    	        });
    			$(".getShareVal").unbind().bind("click",function(){
    				data.sharetype=$('.inputshare input[name="sharetype"]:checked').val();
    				layer.closeAll("page");
    				var sharelist;
    				if(data.sharetype=="1"){
    					sharelist=dicListConfig(true, data.SysWadList, data.sharewadJson);
    				}else if(data.sharetype=="2"){
    					sharelist=dicListConfig(true, data.YKuserList, data.sharewadJson);
    				}
    	            tip_edit.config({
	    			$this:$this,
	    			title : "分享范围",
	    			list : sharelist,
	    			radio : false,
	    			besure : function() {
	    				data.sharewadJson = eval(choiceContent());
	    				tip_edit.close();
	    				updateShare();
	    			}
	    		});
    				
    				
    			});
    			$('#ra'+data.sharetype).attr("checked",true);
    			
    		});
    	
    };

    //修改会议参会人请求
    var updateJoinmeeting=function(){
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "updateMeetingJoinPeople.html",
            data: {
                version: data.version,
                meetcode: meeting_object.base_meeting_code,
                newjoinpeople: oldContent(editdata.joinPeopleJson),
                logintype: cookieopt.getlogintype()
            },
            success: function (vdata) {
                if(vdata.message=="success"){
                    $.showtip("保存成功","success");
                    data.version=vdata.version;
                    rendPeople();
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
            }
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
                    $.showtip("保存成功","success");
                    data.version=vdata.version;
                    data.meetingtypeJson=vdata.meetingTypeJson;
                    meetingtypeRend();
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
            }
        });

    };




    //修改会议分享范围
    var updateShare=function(){
		 $.showloading();
		 $.ajax({
	            async: true,
	            dataType: 'json',
	            type: 'post',
	            url: "updateMeetingInfo.html",
	            data: {
	            	version:data.version,
	            	sharetype:data.sharetype,
	            	meetcode: meeting_object.base_meeting_code,
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
	            	}else if(vdata.message=="upfail"){
	            		$.showtip("数据已被修改","error");
	            	}else{
	            		$.showtip("修改失败","error");
	            	}

	            	$.hideloading();
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
        noteconfig:noteconfig
    };
})();

var data_model={
    people:'<% for (var i=0;i<mplist.length;i++ ){%>' +
        '<li><%=mplist[i]%></li>' +
        '<%}%>',
    editPeople:'<% for (var i=0;i<list.length;i++ ){%>' +
	    '<li><%=list[i].name%></li>' +
	    '<%}%>',
    invicont:'<% for (var i=0;i<invicont.length;i++ ){%>' +
        '<li><span class="comp"><%=invicont[i].name%></span> ' +
        '<span class="comp"><%=invicont[i].personname%></span>' +
        '<span class="comp"><%=invicont[i].personpo%></span></li>' +
        '<%}%>',
    invicontupd:
        '<% for (var i=0;i<invicont.length;i++ ){%>' +
        '<li><span class="comp"><%=invicont[i].name%></span> ' +
        '<span class="comp"><%=invicont[i].personname%></span>' +
        '<span class="comp"><%=invicont[i].personpo%></span>' +
        '<span class="comp orremove " orgremove org-code="<%=invicont[i].code%>"'+
        ' inv-code="<%=invicont[i].personcode%>" i="<%=i%>">删除</span></li>'+
        '<%}%>',
    company:'<%for (var i=0;i<companycont.length;i++ ){%>' +
        '<li><span class="comp"><%=companycont[i].name%></span>' +
        '<span class="comp"><%=companycont[i].personname%></span>' +
        '<span class="comp"><%=companycont[i].personpo%></span></li>' +
        '<%}%>',
    companyupd:'<%for (var i=0;i<companycont.length;i++ ){%>' +
        '<li><span class="comp"><%=companycont[i].name%></span>' +
        '<span class="comp"><%=companycont[i].personname%></span>' +
        '<span class="comp"><%=companycont[i].personpo%></span>' +
        '<span class="comp orremove " companyremove company-code="<%=companycont[i].code%>"'+
        ' relepcode="<%=companycont[i].personcode%>" i="<%=i%>">删除</span></li>'+
        '<%}%>',
    noteall:'<% for (var i=0;i<meetingnote.length;i++ ){ %>' +
        '<tr <%= nodermore(i,noteconfignum)%> >' +
        '<td><%=dateFormat(meetingnote[i].createtime,"yyyy-MM-dd")%></td>' +
        '<td><%=meetingnote[i].sys_user_name%></td>' +
        '<td><pre><%=meetingnote[i].base_invesnote_content%></pre></td>' +
        '<td><button class="btn btn_delete" i="<%=i%>" del-code="<%=meetingnote[i].base_meetingnote_code%>"></button></td>' +
        '</tr>'+
        '<%}%>',
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
                $.showtip("备注信息不能为空","error");

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
                    $.showtip("保存成功","success");
                }else if(data.message=="error"){
                    $.showtip("保存失败","error");
                }else{
                    $.showtip("请求失败","error");
                }
                
                $.hideloading();
            }
        });
    };
    return{
        btnclick:btnclick
    };
})();



//选择机构
var InvestmentInfoconfig = (function() {
    var data={
        invicont:(meeting_invicont==null||meeting_invicont=="")?[]:eval(meeting_invicont)
    };
    // 搜索机构公司存储搜索字段
    var savedata={
        searchorg:'',
        searchinv:''
    };



    //机构打印
    var initInvestment = function() {
        var html = "";
        if(data.invicont!=null){
            if(meeting_object.userstatus=="1") {
                html = template.compile(data_model.invicontupd)(data);
            }else{
                html = template.compile(data_model.invicont)(data);
            }


        }
        if(html==""){
           html ="<li>暂无数据</li>";
        }

        $(".invicont").html(html);
        if(meeting_object.userstatus=="1") {
            delorgClick();
        }
    };


    var delorgClick=function(){
        //相关机构移出
        $("[orgremove]").unbind().bind("click", function () {
            var i=$(this).attr("i");
            var code=$(this).attr("org-code");
            var invcode=$(this).attr("inv-code");
            layer.confirm('您确定要删除该条记录吗？', {
                title:"提示",
                icon: 0,
                btn: ['确定','取消'] //按钮
            }, function(index){
                layer.close(index);
                $.showloading();
                $.ajax({
                    async : true,
                    dataType : 'json',
                    type : 'post',
                    url : "delMeetingOrg.html",
                    data : {
                        meetcode: meeting_object.base_meeting_code,
                        version: congfig.data.version,
                        orgcode : code,
                        investorcode: invcode,
                        logintype : cookieopt.getlogintype()
                    },
                    success : function(vdata) {
                        if (vdata.message == "success") {
                            $.showtip("删除成功");
                            congfig.data.version=vdata.version;
                            data.invicont.baoremove(Number(i));
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
                    }
                });
            }, function(index){
                layer.close(index);
            });

        });
    };



    //添加相关机构图标事件
    var addinvenstmentClick=function(){

        //点击添加机构图标
        $(".add_org").unbind().bind("click",function(e){

            //页面层
            layer.open({
                title:"相关机构",
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['500px', '240px'], //宽高
                content: '<div class="addfrom"><ul class="inputlist">'
                    +'<li><span class="lable">投资机构:</span><span class="in">'
                    +'<select id="investment" class=" inputdef"><option></option></select></span>'
                    +'<span class="addbuttonselect">'
                    +'<button id="btn_investment_add" class="btn btn_icon_add"></button></span></li>'
                    +'<li><span class="lable">投资人:</span><span class="in">'
                    +'<select id="investor" class=" inputdef"><option></option></select></span>'
                    +'<span class="addbuttonselect">'
                    +'<button id="btn_investor_add" class="btn btn_icon_add"></button></span></li>'
                    +'<li><span class="lable">职位:</span><span class="in">'
                    +'<input value="" readonly id="investorposiname" type="text" class="inputdef" maxlength="20"></span></li>'
                    +'</ul><div class="btn-box"><button class="btn btn-default saveorgan">保存</button></div></div>'
            });
            investmentselect_ajax();
            investmentadd_ajax();
            investoradd_ajax();
            investorselect_ajax();
            opt.$investornselect.prop("disabled", true);
            addinventsaveclick();


            //相关机构保存
            function addinventsaveclick() {
                $(".saveorgan").unbind().bind("click", function () {
                    if($("#investment").val()==null
                        || $("#investment").val().trim()==""){
                        $.showtip("请选择投资机构");
                        return;
                    }else{
                        var base_investment_code;
                        var base_investment_name;
                        var investmenttype;
                        if($("#btn_investment_add").hasClass("btn_icon_add")){
                            base_investment_code=$("#investment").val()  ;
                            base_investment_name=$("#investment").find("option:selected").text();
                            investmenttype="select";
                        }else{
                            base_investment_code="";
                            base_investment_name=$("#investment").val();
                            investmenttype="add";
                            invementadd_ajax();
                            if(addinvesbool==false){
                                $.showtip("该投资机构已添加");
                                return;
                            }

                        }
                        var base_investor_code;
                        var base_investor_name;
                        var investortype;
                        if($("#btn_investor_add").hasClass("btn_icon_add")){
                            base_investor_code=$("#investor").val()  ;
                            base_investor_name=$("#investor").find("option:selected").text();
                            investortype="select";
                        }else{
                            base_investor_code="";
                            base_investor_name=$("#investor").val();
                            investortype="add";
                            addinvestor_ajax();
                            if(addinvesbool==false){
                                $.showtip("该投资机构投资人已添加");
                                return;
                            }

                        }

                        for(var i=0;i<  data.invicont.length;i++){
                            if(base_investment_name == data.invicont[i].name
                                && base_investor_name == data.invicont[i].personname){
                                $.showtip("该投资机构投资人已存在");
                                return;
                            }
                        }

                        var investmentinfo=new Object();
                        //机构code
                        investmentinfo.code=base_investment_code;
                        //机构name
                        investmentinfo.name=base_investment_name;
                        //机构添加类型
                        investmentinfo.investmenttype=investmenttype;
                        //投资人code
                        investmentinfo.personcode=base_investor_code;
                        //投资人name
                        investmentinfo.personname=base_investor_name;
                        if(base_investor_name==null
                            ||base_investor_name=="" ){
                            $("#investorposiname").val("");
                        }
                        //投资人类型
                        investmentinfo.investortype=investortype;

                        //投资人职位
                        investmentinfo.personpo=$("#investorposiname").val();

                        layer.closeAll('page');
                        updateOrgRequest(investmentinfo);

                    }
                });
            }



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
                    savedata.searchorg="";
                    savedata.searchinv="";
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
            }
        });

    };





    var touzieditclick=function(){
        //相关机构编辑
        $("[touziedit]").unbind().bind("click", function () {
            var i=$(this).attr("i");
            //页面层
            layer.open({
                title:"添加所属投资机构",
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['500px', '240px'], //宽高
                content: '<div class="addfrom"><ul class="inputlist">'
                    +'<li><span class="lable">投资机构:</span><span class="in">'
                    +'<select id="investment" class=" inputdef">'
                    +'<option value="'+investmentInfoList[i].base_investment_code+'" selected="selected">'
                    +investmentInfoList[i].base_investment_name+'</option></select></span>'
                    +'<span class="addbuttonselect">'
                    +'<button id="btn_investment_add" class="btn btn_icon_add"></button></span></li>'
                    +'<li><span class="lable">投资人:</span><span class="in">'
                    +'<select id="investor" class=" inputdef">'
                    +'<option value="'+investmentInfoList[i].base_investor_code+'" selected="selected">'
                    +investmentInfoList[i].base_investor_name+'</option></select></span>'
                    +'<span class="addbuttonselect">'
                    +'<button id="btn_investor_add" class="btn btn_icon_add"></button></span></li>'
                    +'<li><span class="lable">职位:</span><span class="in">'
                    +'<input value="'
                    +investmentInfoList[i].base_investor_posiname+'"  id="investorposiname" type="text" class="inputdef" maxlength="20"></span></li>'
                    +'</ul><div class="btn-box"><button class="btn btn-default editorgan">保存</button></div></div>'
            });


            if(investmentInfoList[i].investmenttype=="select"){
                investmentselect_ajax();
            }
            else if(investmentInfoList[i].investmenttype=="add"){
                $("#investment").parent().html('<input value="'
                    +investmentInfoList[i].base_investment_name
                    +'"id="investment" type="text" class="inputdef" maxlength="20">');
                $("#btn_investment_add").removeClass("btn_icon_add").addClass("btn_icon_search");
            }

            if(investmentInfoList[i].investortype=="select"){
                investorselect_ajax();
            }
            else if(investmentInfoList[i].investortype=="add"){
                $("#investor").parent().html('<input value="'
                    +investmentInfoList[i].base_investor_name
                    +'"id="investor" type="text" class="inputdef" maxlength="20">');
                $("#btn_investor_add").removeClass("btn_icon_add").addClass("btn_icon_search");
            }
            if($("#btn_investment_add").hasClass("btn_icon_add")
                && $("#btn_investor_add").hasClass("btn_icon_add")){
                $("#investorposiname").attr("readonly","readonly");
            }
            else{
                $("#investorposiname").removeAttr("readonly");
            }

            investmentadd_ajax();
            investoradd_ajax();
            editinventsaveclick();

//编辑相关机构保存
            function editinventsaveclick(){

                $(".editorgan").unbind().bind("click", function () {
                    if($("#investment").val()==null
                        || $("#investment").val().trim()==""){
                        $.showtip("请选择投资机构");
                        return;
                    }else{
                        var base_investment_code;
                        var base_investment_name;
                        var investmenttype;
                        if($("#btn_investment_add").hasClass("btn_icon_add")){
                            base_investment_code=$("#investment").val()  ;
                            base_investment_name=$("#investment").find("option:selected").text();
                            investmenttype="select";
                        }else{
                            base_investment_code="";
                            base_investment_name=$("#investment").val();
                            investmenttype="add";
                            invementadd_ajax();
                            if(!addinvesbool){
                                $.showtip("该投资机构已添加");
                                return;
                            }

                        }
                        var base_investor_code;
                        var base_investor_name;
                        var investortype;
                        if($("#btn_investor_add").hasClass("btn_icon_add")){
                            base_investor_code=$("#investor").val()  ;
                            base_investor_name=$("#investor").find("option:selected").text();
                            investortype="select";
                        }else{
                            base_investor_code="";
                            base_investor_name=$("#investor").val();
                            investortype="add";
                        }
                        for(var j=0;j<  investmentInfoList.length;j++){
                            if(base_investment_name==  investmentInfoList[j].base_investment_name
                                && base_investor_name == investmentInfoList[j].base_investor_name
                                && i!=j){
                                $.showtip("该投资机构投资人已添加");
                                return;
                            }
                        }
                        var investmentinfo=new Object();
                        //机构code
                        investmentinfo.base_investment_code=base_investment_code;
                        //机构name
                        investmentinfo.base_investment_name=base_investment_name;
                        //机构添加类型
                        investmentinfo.investmenttype=investmenttype;
                        //投资人code
                        investmentinfo.base_investor_code=base_investor_code;
                        //投资人name
                        investmentinfo.base_investor_name=base_investor_name;

                        if(base_investor_name==null
                            ||base_investor_name=="" ){
                            $("#investorposiname").val("");
                        }
                        //投资人类型
                        investmentinfo.investortype=investortype;

                        //投资人职位
                        investmentinfo.base_investor_posiname=$("#investorposiname").val();
                    }
                    investmentInfoList[i]=investmentinfo;
                    layer.closeAll('page');
                    initInvestment();
                });
            }
        });

//相关机构移出
        $("[touziremove]").unbind().bind("click", function () {
            var i=$(this).attr("i");
            investmentInfoList.baoremove(Number(i));
            initInvestment();
        });
    };

//投资机构选择页面
    function investmentselect_ajax() {
        //选择投资机构
        opt.$orgainselect=$("#investment").select2({
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
                    savedata.searchorg=(params.term || "");
                    return{
                        name:params.term||"",
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
        });

        //机构发生变化清空投资人搜索重置信息
        $("#investment").on("change", function() {
            if($("#btn_investment_add").hasClass("btn_icon_add")){
                //机构发生变化
                //若现在投资人为添加状态，职位可输入
                if($("#btn_investor_add").hasClass("btn_icon_search")){
                    $("#investorposiname").removeAttr("readonly");
                }
                //若现在投资人为搜索状态，初始化搜索，职位不可输入
                else if($("#investment").val()!=null
                    &&$("#investment").val().trim()!="" ){
                    $("#investor").parent().html('<select id="investor" class=" inputdef"><option></option></select>');
                    investorselect_ajax();
                    $("#investorposiname").val("");
                    $("#investorposiname").attr("readonly","readonly");
                }
                else{
                    $("#investor").parent().html('<select id="investor" class=" inputdef" ><option></option></select>');
                    investorselect_ajax();
                    opt.$investornselect.prop("disabled", true);
                    $("#investorposiname").val("");
                    $("#investorposiname").attr("readonly","readonly");
                }
            }
        });
    }
    function  investmentadd_ajax() {

        //添加投资机构点击
        $("#btn_investment_add").unbind().bind("click", function () {
            //现在是搜索切换为添加，职位允许输入
            if($("#btn_investment_add").hasClass("btn_icon_add")){
                $("#investment").parent().html('<input value="" id="investment" type="text" class="inputdef" maxlength="20">');
                $("#investment").val(savedata.searchorg);
                $("#btn_investment_add").removeClass("btn_icon_add").addClass("btn_icon_search");
                $("#investorposiname").removeAttr("readonly");

                if($("#btn_investor_add").hasClass("btn_icon_add")){
                    investorselect_ajax();
                    opt.$investornselect.prop("disabled", false);
                }

            }
            //现在是添加切换为搜索，职位不允许输入，初始化机构筛选和投资人筛选
            else{
                $("#investment").parent().html('<select id="investment" class=" inputdef"><option></option></select>');
                investmentselect_ajax();
                $("#btn_investment_add").removeClass("btn_icon_search").addClass("btn_icon_add");
                $("#investorposiname").val("");
                if($("#btn_investor_add").hasClass("btn_icon_add")){
                    $("#investorposiname").attr("readonly","readonly");
                    opt.$investornselect.val("").trigger("change");
                    investorselect_ajax();
                    opt.$investornselect.prop("disabled", true);
                }
                else{
                    $("#investorposiname").removeAttr("readonly");
                }
            }
        });

    }

//投资人选择页面
    function investorselect_ajax() {
        var invementcode;
        if($("#btn_investment_add").hasClass("btn_icon_add")){
            invementcode=$("#investment").val()  ;
        }
        else{
            invementcode="";
        }
        //选择投资人
        opt.$investornselect=$("#investor").select2({
            placeholder:"请选择投资人",//文本框的提示信息
            minimumInputLength:0,   //至少输入n个字符，才去加载数据
            allowClear: true,  //是否允许用户清除文本信息
            ajax:{
                url:"queryinvestorlistByinvementcode.html",
                dataType:"json",
                cache: true,
                type:"post",
                delay: 250,//加载延迟
                data: function (params) {

                    savedata.searchinv=(params.term || "");
                    return{
                        name:params.term||"",
                        pageSize:CommonVariable().SELSECLIMIT,
                        pageCount:"1",
                        logintype:cookieopt.getlogintype(),
                        invementcode:invementcode,
                        type:"1"
                    };
                },
                processResults: function (data, page) {
                    for(var i=0;i<data.list.length;i++){
                        data.list[i].id=data.list[i].base_investor_code;
                        data.list[i].text=data.list[i].base_investor_name;

                    }
                    return {
                        results: data.list//返回结构list值
                    };
                }

            },
            escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
            templateResult: formatRepo // 格式化返回值 显示再下拉类表中
        });

        //投资人发生变化职位信息信息
        $("#investor").on("change", function() {
            if($("#btn_investor_add").hasClass("btn_icon_add")){
                //职位
                $("#investorposiname").val(opt.$investornselect.select2("data")[0].base_investor_posiname);

            }
        });
    }
    function  investoradd_ajax() {

        //添加投资人点击
        $("#btn_investor_add").unbind().bind("click", function () {
            if($("#btn_investor_add").hasClass("btn_icon_add")){
                $("#investor").parent().html('<input value="" id="investor" type="text" class="inputdef" maxlength="20">');
                $("#investor").val(savedata.searchinv);
                $("#btn_investor_add").removeClass("btn_icon_add").addClass("btn_icon_search");
                $("#investorposiname").val("");
                $("#investorposiname").removeAttr("readonly");
            }
            else{
                $("#investor").parent().html('<select id="investor" class=" inputdef" ><option></option></select>');
                $("#btn_investor_add").removeClass("btn_icon_search").addClass("btn_icon_add");
                investorselect_ajax();
                if($("#investment").val()!=null
                    &&$("#investment").val().trim()!=null ){
                    opt.$investornselect.prop("disabled", false);
                }
                else{
                    opt.$investornselect.prop("disabled", true);
                }
                $("#investorposiname").val("");
                if($("#btn_investment_add").hasClass("btn_icon_add")){
                    $("#investorposiname").attr("readonly","readonly");
                }
                else{
                    $("#investorposiname").removeAttr("readonly");
                }
            }
        });

    }
    function invementadd_ajax() {
        $.showloading();
        $.ajax({
            async : false,
            dataType : 'json',
            type : 'post',
            url : "queryInvestmentOnlyNamefottrade.html",
            data : {
                name : $("#investment").val().trim(),
                logintype : cookieopt.getlogintype()
            },
            success : function(data) {
                $.hideloading();
                if (data.message == "nodata") {
                    addinvesbool= true;
                }
                else if(data.message == "exsit"){
                    addinvesbool= false;

                }else {
                    addinvesbool= false;
                }
            }
        });
    };

    function addinvestor_ajax() {
        $.showloading();
        $.ajax({
            async : false,
            dataType : 'json',
            type : 'post',
            url : "queryOrgInvestorOnlyNamefottrade.html",
            data : {
                orgcode:$("#investment").val().trim() ,
                name : $("#investor").val().trim(),
                logintype : cookieopt.getlogintype()
            },
            success : function(data) {
                $.hideloading();
                if (data.message == "nodata") {
                    addinvesbool= true;
                }
                else if(data.message == "exsit"){
                    addinvesbool= false;

                }else {
                    addinvesbool= false;
                }
            }
        });
    };

    function formatRepo (repo) {
        return repo.text;
    }

    return{
        InvestmentInfoLoad:initInvestment,
        InvestmentAddClick:addinvenstmentClick,
        savedata:savedata

    };
})();


//选择公司
var CompInfoconfig = (function() {
    var data={
        companycont:(meeting_compcont==null||meeting_compcont=="")?[]:eval(meeting_compcont)
    };
    var savedata={
        searchcom:"",
        searchinv:""
    };
    //公司打印
    var initComp = function() {
        var html = "";
        if(data.companycont!=null){
            if(meeting_object.userstatus=="1") {
                html = template.compile(data_model.companyupd)(data);
            }else{
                html = template.compile(data_model.company)(data);
            }

        }
        if(html==""){
            html="<li>暂无数据</li>";
        }
        $(".companycont").html(html);
        if(meeting_object.userstatus=="1") {
            deleteCompClick();
        }
       // compeditclick();// 编辑／删除公司事件
    };

    //删除公司事件
    var deleteCompClick=function(){
        $("[companyremove]").unbind().bind("click",function(){
            var $this=$(this);
            layer.confirm('您确定要删除该条记录吗？', {
                title:"提示",
                icon: 0,
                btn: ['确定','取消'] //按钮
            }, function(index){
                deleteCompRequest($this);
                layer.close(index);
            }, function(index){
                layer.close(index);
            });
        });
    };

    //删除相关公司请求
    var deleteCompRequest=function (val){
        $.showloading();
        $.ajax({
            async : true,
            dataType : 'json',
            type : 'post',
            url : "delMeetingCompany.html",
            data : {
                meetcode : meeting_object.base_meeting_code,
                version : congfig.data.version,
                companycode : val.attr("company-code"),
                relepcode : val.attr("relepcode"),
                logintype : cookieopt.getlogintype()
            },
            success : function(vdata) {
                if (vdata.message == "success") {
                    $.showtip("删除成功");
                    congfig.data.version=vdata.version;
                    data.companycont.baoremove(Number(val.attr("i")));
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
            }
        });
    };

    //添加公司图标事件
    var addcompClick=function(){

        //点击添加公司图标
        $(".add_company").unbind().bind("click",function(e){

            //页面层
            layer.open({
                title:"相关公司",
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['500px'], //宽高
                content: '<div class="addfrom"><ul class="inputlist">'
                    +'<li><span class="lable">公司:</span><span class="in">'
                    +'<select id="compid" class=" inputdef"><option></option></select></span>'
                    +'<span class="addbuttonselect">'
                    +'<button id="btn_comp_add" class="btn btn_icon_add"></button></span></li>'
                    +'<li><span class="lable">联系人:</span><span class="in">'
                    +'<select id="entrepreneur" class=" inputdef"><option></option></select></span>'
                    +'<span class="addbuttonselect">'
                    +'<button id="btn_entrepreneur_add" class="btn btn_icon_add"></button></span>'
                    +'</li>'
                    +'<li><span class="lable">职位:</span><span class="in">'
                    +'<input value="" readonly id="entrepreneurposiname" type="text" class="inputdef" maxlength="20"></span>'
                    +'</li>'
                    +'<li><span class="lable">创建融资计划:</span><span class="in">'
                    +'<input value=""  id="planshow" type="checkbox" ></span>'
                    +'</li>'
                    +'<li style="display:none"><span class="lable">计划时间:</span><span class="in">'
                    +'<input type="text" class="span2" id="plantime" data-date-format="yyyy-mm-dd" readonly></span>'
                    +'</li>'
                    +'<li style="display:none"><span class="lable">提醒时间:</span><span class="in">'
                    +'<input type="text" class="span2" id="emailtime" data-date-format="yyyy-mm-dd" readonly></span>'
                    +'</li>'
                    +'<li style="display:none"><span class="lable">计划内容:</span><span class="in">'
                    +'<textarea class="txt_redord" style="height:80px;"id="palntext" maxlength="100"></textarea>'
                    +'</li>'
                    +'</ul>'
                    +'<div class="btn-box"><button class="btn btn-default savecomp">保存</button></div></div>'
            });
            compinfoselect_ajax();
            compinfoadd_ajax();
            entrepreneuradd_ajax();
            entrepreneurselect_ajax();
            opt.$entrepreneurselect.prop("disabled", true);
            addcompsaveclick();
            //融资计划选择
            compplanClick();

            //相关公司保存
            function addcompsaveclick() {
                $(".savecomp").unbind().bind("click", function () {
                    if($("#compid").val()==null
                        ||$("#compid").val().trim()==""){
                        $.showtip("请选择公司");
                        return;
                    }else{
                        var base_comp_code;
                        var base_comp_name;
                        var comptype;
                        if($("#btn_comp_add").hasClass("btn_icon_add")){
                            base_comp_code=$("#compid").val()  ;
                            base_comp_name=$("#compid").find("option:selected").text();
                            comptype="select";
                        }
                        else{
                            base_comp_code="";
                            base_comp_name=$("#compid").val();
                            comptype="add";
                            compadd_ajax();
                            if(!addcompbool){
                                $.showtip("该公司已添加");
                                return;
                            }

                        }
                        var base_entrepreneur_code;
                        var base_entrepreneur_name;
                        var entrepreneurtype;
                        if($("#btn_entrepreneur_add").hasClass("btn_icon_add")){
                            base_entrepreneur_code=$("#entrepreneur").val()  ;
                            base_entrepreneur_name=$("#entrepreneur").find("option:selected").text();
                            entrepreneurtype="select";
                        }
                        else{
                            base_entrepreneur_code="";
                            base_entrepreneur_name=$("#entrepreneur").val();
                            entrepreneurtype="add";
                        }

                        for(var i=0;i<data.companycont.length;i++){
                            if(base_comp_code==  data.companycont[i].code){
                                $.showtip("已选择该公司");
                                return;
                            }else if(base_comp_name==  data.companycont[i].name){
                                $.showtip("该公司已添加");
                                return;
                            }
                        }

                        if(
                            $("#planshow").is(':checked')&& ($("#plantime").val().trim()==""
                            || isNaN(new Date($("#plantime").val()).getTime()))){
                            $.showtip("融资计划的计划时间不可为空");
                            return;
                        }
                        else if(
                            $("#planshow").is(':checked')
                            && ($("#emailtime").val().trim()==""
                            || isNaN(new Date($("#emailtime").val()).getTime()))
                            ){
                            $.showtip("融资计划的提醒时间不可为空");
                            return;
                        }
                        else if(
                            $("#planshow").is(':checked')&& $("#palntext").val().trim()==""){
                            $.showtip("融资计划的计划内容不可为空");
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
                                    return;
                                }
                                if(thisnowdate>emailtime ){
                                    $.showtip("融资计划的提醒时间需大于当前日期");
                                    return;
                                }
                            }
                            var compinfo=new Object();
                            //公司code
                            compinfo.code=base_comp_code;
                            //公司name
                            compinfo.name=base_comp_name;
                            //公司添加类型
                            compinfo.comptype=comptype;
                            //公司联系人code
                            compinfo.personcode=base_entrepreneur_code;
                            //公司联系人name
                            compinfo.personname=base_entrepreneur_name;
                            if(base_entrepreneur_name==null
                                ||base_entrepreneur_name=="" ){
                                $("#entrepreneurposiname").val("");
                            }
                            //公司联系人类型
                            compinfo.entrepreneurtype=entrepreneurtype;

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
                            layer.closeAll('page');
                            updcompanyClick(compinfo);

                        }
                    }
                });
            }



        });
    };

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
                if(vdata.message=="success"){
                    $.showtip("保存成功");
                    congfig.data.version=vdata.version;
                    savedata.searchcom="";
                    savedata.searchinv="";
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

            }
        });
    };





    var compeditclick=function(){
        //相关公司编辑
        $("[compedit]").unbind().bind("click", function () {
            var i=$(this).attr("i");
            var contentstr="";
            contentstr='<div class="addfrom" ><ul class="inputlist">'
                +'<li><span class="lable">公司:</span><span class="in">'
                +'<select id="compid" class=" inputdef">'
                +'<option value="'+compInfoList[i].base_comp_code+'" selected="selected">'
                +compInfoList[i].base_comp_name+'</option></select></span>'
                +'<span class="addbuttonselect">'
                +'<button id="btn_comp_add" class="btn btn_icon_add"></button></span></li>'
                +'<li><span class="lable">联系人:</span><span class="in">'
                +'<select id="entrepreneur" class=" inputdef">'
                +'<option value="'+compInfoList[i].base_entrepreneur_code+'" selected="selected">'
                +compInfoList[i].base_entrepreneur_name+'</option></select></span>'
                +'<span class="addbuttonselect">'
                +'<button id="btn_entrepreneur_add" class="btn btn_icon_add"></button></span>'
                +'</li>'
                +'<li><span class="lable">职位:</span><span class="in">'
                +'<input value="'
                +compInfoList[i].base_entrepreneur_posiname+'" id="entrepreneurposiname" type="text" class="inputdef" maxlength="20"></span>'
                +'</li>'
                +'<li><span class="lable">创建融资计划:</span><span class="in">';
            var checked="";
            var stylestr="";
            if(compInfoList[i].financplanflag){
                checked="checked";
            }
            else{
                stylestr="style='display:none'";
            }
            contentstr=	contentstr+'<input value=""  id="planshow" type="checkbox"'
                +checked+' ></span>'
                +'</li>'
                +'<li '+stylestr+'><span class="lable">计划时间:</span><span class="in">'
                +'<input type="text" class="span2" id="plantime" data-date-format="yyyy-mm-dd" readonly></span>'
                +'</li>'
                +'<li '+stylestr+'><span class="lable">提醒时间:</span><span class="in">'
                +'<input type="text" class="span2" id="emailtime" data-date-format="yyyy-mm-dd" readonly></span>'
                +'</li>'
                +'<li '+stylestr+'><span class="lable">计划内容:</span><span class="in">'
                +'<textarea class="txt_redord" style="height:80px;"id="palntext" maxlength="100"></textarea>'
                +'</li>'
                +'</ul>'
                +'<div class="btn-box"><button class="btn btn-default editcomp">保存</button></div></div>';
            //页面层
            layer.open({
                title:"相关公司",
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['500px'], //宽高
                content: contentstr
            });

            if(compInfoList[i].comptype=="select"){
                compinfoselect_ajax();
            }
            else if(compInfoList[i].comptype=="add"){
                $("#compid").parent().html('<input value="'
                    +compInfoList[i].base_comp_name
                    +'"id="compid" type="text" class="inputdef" maxlength="20">');
                $("#btn_comp_add").removeClass("btn_icon_add").addClass("btn_icon_search");
            }

            if(compInfoList[i].entrepreneurtype=="select"){
                entrepreneurselect_ajax();
            }
            else if(compInfoList[i].entrepreneurtype=="add"){
                $("#entrepreneur").attr("operatype",'add');
                $("#entrepreneur").parent().html('<input value="'
                    +compInfoList[i].base_entrepreneur_name
                    +'"id="entrepreneur" type="text" class="inputdef" maxlength="20">');
                $("#btn_entrepreneur_add").removeClass("btn_icon_add").addClass("btn_icon_search");
            }
            if($("#btn_comp_add").hasClass("btn_icon_add")
                && $("#btn_entrepreneur_add").hasClass("btn_icon_add")){
                $("#entrepreneurposiname").attr("readonly","readonly");
            }
            else{
                $("#entrepreneurposiname").removeAttr("readonly");
            }
            if(compInfoList[i].financplanflag){
                //融资计划计划时间
                $("#plantime").val(compInfoList[i].base_financplan_date);
                //融资计划提醒时间
                $("#emailtime").val(compInfoList[i].base_financplan_remindate);
                //融资计划计划内容
                $("#palntext").val(compInfoList[i].base_financplan_cont);
                //2015/12/10 BUG297 AddStart
                $('#plantime').datepicker();
                $('#emailtime').datepicker();
                //2015/12/10 BUG297 AddEnd
            }

            compinfoadd_ajax();
            entrepreneuradd_ajax();
            editcompsaveclick();
            //融资计划选择
            compplanClick();

            function editcompsaveclick(){

                $(".editcomp").unbind().bind("click", function () {
                    if($("#compid").val()==null
                        ||$("#compid").val().trim()==""){
                        $.showtip("请选择公司");
                        return;
                    }else{
                        var base_comp_code;
                        var base_comp_name;
                        var comptype;
                        if($("#btn_comp_add").hasClass("btn_icon_add")){
                            base_comp_code=$("#compid").val()  ;
                            base_comp_name=$("#compid").find("option:selected").text();
                            comptype="select";
                        }
                        else{
                            base_comp_code="";
                            base_comp_name=$("#compid").val();
                            comptype="add";
                            compadd_ajax();
                            if(!addcompbool){
                                $.showtip("该公司已添加");
                                return;
                            }

                        }
                        var base_entrepreneur_code;
                        var base_entrepreneur_name;
                        var entrepreneurtype;
                        if($("#btn_entrepreneur_add").hasClass("btn_icon_add")){
                            base_entrepreneur_code=$("#entrepreneur").val()  ;
                            base_entrepreneur_name=$("#entrepreneur").find("option:selected").text();
                            entrepreneurtype="select";
                        }
                        else{
                            base_entrepreneur_code="";
                            base_entrepreneur_name=$("#entrepreneur").val();
                            entrepreneurtype="add";
                        }

                        for(var j=0;j<  compInfoList.length;j++){
                            if(base_comp_code==  compInfoList[j].base_comp_code
                                && "select"==compInfoList[j].base_comp_code
                                && i!=j){
                                $.showtip("已选择该公司");
                                return;
                            }
                            else if(base_comp_name==  compInfoList[j].base_comp_name
                                && i!=j){
                                $.showtip("该公司已添加");
                                return;
                            }
                        }

                        if(
                            $("#planshow").is(':checked')&& ($("#plantime").val().trim()==""
                            || isNaN(new Date($("#plantime").val()).getTime()))){
                            $.showtip("融资计划的计划时间不可为空");
                            return;
                        }
                        else if(
                            $("#planshow").is(':checked')
                            && ($("#emailtime").val().trim()==""
                            || isNaN(new Date($("#emailtime").val()).getTime()))
                            ){
                            $.showtip("融资计划的提醒时间不可为空");
                            return;
                        }
                        else if(
                            $("#planshow").is(':checked')&& $("#palntext").val().trim()==""){
                            $.showtip("融资计划的计划内容不可为空");
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
                                    return;
                                }
                                if(thisnowdate>emailtime ){
                                    $.showtip("融资计划的提醒时间需大于当前日期");
                                    return;
                                }
                            }
                            var compinfo=new Object();
                            //公司code
                            compinfo.base_comp_code=base_comp_code;
                            //公司name
                            compinfo.base_comp_name=base_comp_name;
                            //公司添加类型
                            compinfo.comptype=comptype;
                            //公司联系人code
                            compinfo.base_entrepreneur_code=base_entrepreneur_code;
                            //公司联系人name
                            compinfo.base_entrepreneur_name=base_entrepreneur_name;
                            //公司联系人类型
                            compinfo.entrepreneurtype=entrepreneurtype;
                            if(base_entrepreneur_name==null
                                ||base_entrepreneur_name=="" ){
                                $("#entrepreneurposiname").val("");
                            }
                            //公司联系人职位
                            compinfo.base_entrepreneur_posiname=$("#entrepreneurposiname").val();

                            //是否创建公司融资计划
                            compinfo.financplanflag=$("#planshow").is(':checked');
                            //公司融资计划计划日期
                            compinfo.base_financplan_date=$("#plantime").val();
                            //公司融资计划提醒日期
                            compinfo.base_financplan_remindate=$("#emailtime").val();
                            //公司融资计划内容
                            compinfo.base_financplan_cont=$("#palntext").val();

                            compInfoList[i]=compinfo;
                            layer.closeAll('page');
                            initComp();
                        }
                    }
                });
            }
        });

//相关公司移出
        $("[compremove]").unbind().bind("click", function () {
            var i=$(this).attr("i");
            compInfoList.baoremove(Number(i));
            initComp();
        });
    };
//公司选择页面
    function compinfoselect_ajax() {
        //选择公司
        opt.$compselect=$("#compid").select2({
            placeholder:"请选择公司",//文本框的提示信息
            minimumInputLength:0,   //至少输入n个字符，才去加载数据
            allowClear: true,  //是否允许用户清除文本信息
            ajax:{
                url:"querycompanylistByname.html",
                dataType:"json",
                cache: true,
                type:"post",
                delay: 250,//加载延迟
                data: function (params) {
                    savedata.searchcom = (params.term || "");
                    return{
                        name:params.term||"",
                        pageSize:CommonVariable().SELSECLIMIT,
                        pageCount:"1",
                        logintype:cookieopt.getlogintype(),
                        type:"1"
                    };
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

        //公司发生变化清空联系人信息
        $("#compid").on("change", function() {
            if($("#btn_comp_add").hasClass("btn_icon_add")){
                //公司发生变化
                //若现在联系人为添加状态，职位可输入
                if($("#btn_entrepreneur_add").hasClass("btn_icon_search")){
                    $("#entrepreneurposiname").removeAttr("readonly");
                }
                //若现在联系人为搜索状态，初始化搜索，职位不可输入
                else if($("#compid").val()!=null
                    &&$("#compid").val().trim()!="" ){
                    $("#entrepreneur").parent().html('<select id="entrepreneur" class=" inputdef"><option></option></select>');
                    entrepreneurselect_ajax();
                    $("#entrepreneurposiname").val("");
                    $("#entrepreneurposiname").attr("readonly","readonly");
                }
                else{
                    $("#entrepreneur").parent().html('<select id="entrepreneur" class=" inputdef"><option></option></select>');
                    entrepreneurselect_ajax();
                    opt.$entrepreneurselect.prop("disabled", true);
                    $("#entrepreneurposiname").val("");
                    $("#entrepreneurposiname").attr("readonly","readonly");
                }
            }
        });
    }
    function  compinfoadd_ajax() {

        //添加公司点击
        $("#btn_comp_add").unbind().bind("click", function () {
            //现在是搜索切换为添加，职位允许输入
            if($("#btn_comp_add").hasClass("btn_icon_add")){
                $("#compid").parent().html('<input value="" id="compid" type="text" class="inputdef" maxlength="20">');
                $("#compid").val(savedata.searchcom);
                $("#btn_comp_add").removeClass("btn_icon_add").addClass("btn_icon_search");
                $("#entrepreneurposiname").removeAttr("readonly");
                if($("#btn_entrepreneur_add").hasClass("btn_icon_add")){
                    entrepreneurselect_ajax();
                    opt.$entrepreneurselect.prop("disabled", false);
                }
            }
            //现在是添加切换为搜索，职位不允许输入，初始化公司筛选
            else{
                $("#compid").parent().html('<select id="compid" class=" inputdef" ><option></option></select>');
                $("#btn_comp_add").removeClass("btn_icon_search").addClass("btn_icon_add");
                compinfoselect_ajax();
                $("#entrepreneurposiname").val("");
                //处理联系人
                if($("#btn_entrepreneur_add").hasClass("btn_icon_add")){
                    $("#entrepreneurposiname").attr("readonly","readonly");
                    opt.$entrepreneurselect.val("").trigger("change");
                    entrepreneurselect_ajax();
                    opt.$entrepreneurselect.prop("disabled", true);
                }
                else{
                    $("#entrepreneurposiname").removeAttr("readonly");
                }
            }
        });

    }

//联系人选择页面
    function entrepreneurselect_ajax() {
        var base_comp_code;
        if($("#btn_comp_add").hasClass("btn_icon_add")){
            base_comp_code=$("#compid").val()  ;
        }
        else{
            base_comp_code="";
        }
        //选择联系人
        opt.$entrepreneurselect=$("#entrepreneur").select2({
            placeholder:"请选择联系人",//文本框的提示信息
            minimumInputLength:0,   //至少输入n个字符，才去加载数据
            allowClear: true,  //是否允许用户清除文本信息
            ajax:{
                url:"queryentrepreneurlistBycompcode.html",
                dataType:"json",
                cache: true,
                type:"post",
                delay: 250,//加载延迟
                data: function (params) {
                    savedata.searchinv = params.term||"";
                    return{
                        name:params.term||"",
                        pageSize:CommonVariable().SELSECLIMIT,
                        pageCount:"1",
                        logintype:cookieopt.getlogintype(),
                        compcode:base_comp_code,
                        type:"1"
                    };
                },
                processResults: function (data, page) {
                    for(var i=0;i<data.list.length;i++){
                        data.list[i].id=data.list[i].base_entrepreneur_code;
                        data.list[i].text=data.list[i].base_entrepreneur_name;

                    }
                    return {
                        results: data.list//返回结构list值
                    };
                }

            },
            escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
            templateResult: formatRepo // 格式化返回值 显示再下拉类表中
        });

        //联系人发生变化职位信息信息
        $("#entrepreneur").on("change", function() {
            if($("#btn_entrepreneur_add").hasClass("btn_icon_add")){
                //职位
                $("#entrepreneurposiname").val(opt.$entrepreneurselect.select2("data")[0].base_entrepreneur_posiname);

            }
        });
    }
    function  entrepreneuradd_ajax() {

        //添加联系人点击
        $("#btn_entrepreneur_add").unbind().bind("click", function () {
            if($("#btn_entrepreneur_add").hasClass("btn_icon_add")){
                $("#entrepreneur").parent().html('<input value="" id="entrepreneur" type="text" class="inputdef" maxlength="20">');
                $("#entrepreneur").val(savedata.searchinv);
                $("#btn_entrepreneur_add").removeClass("btn_icon_add").addClass("btn_icon_search");
                $("#entrepreneurposiname").val("");
                $("#entrepreneurposiname").removeAttr("readonly");
            }
            else{
                $("#entrepreneur").parent().html('<select id="entrepreneur" class=" inputdef" ><option></option></select>');
                $("#btn_entrepreneur_add").removeClass("btn_icon_search").addClass("btn_icon_add");
                entrepreneurselect_ajax();
                if($("#compid").val()!=null
                    && $("#compid").val().trim()!=""){
                    opt.$entrepreneurselect.prop("disabled", false);
                }
                else{
                    opt.$entrepreneurselect.prop("disabled", true);
                }
                $("#entrepreneurposiname").val("");
                if($("#btn_comp_add").hasClass("btn_icon_add")){
                    $("#entrepreneurposiname").attr("readonly","readonly");
                }
                else{
                    $("#entrepreneurposiname").removeAttr("readonly");
                }
            }
        });

    }
    function compadd_ajax() {
        $.showloading();
        $.ajax({
            async : true,
            dataType : 'json',
            type : 'post',
            url : "queryCompOnlyNamefortrade.html",
            data : {
                name : $("#compid").val().trim(),
                logintype : cookieopt.getlogintype()
            },
            success : function(data) {
                $.hideloading();
                if (data.message == "nodata") {
                    addcompbool= true;
                }
                else if(data.message == "exsit"){
                    addcompbool= false;

                }else {
                    addcompbool= false;
                }
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
                $('#plantime').datepicker();

                $('#emailtime').datepicker();
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

    function formatRepo (repo) {
        return repo.text;
    }
    return{
        CompInfoLoad:initComp,
        CompAddClick:addcompClick,
        savedata:savedata
    };
})();

//保存会议内容
var meetcontConfig=(function(){
    var saveClick=function(){
        $(".saveMeetClick").unbind().bind("click",function(){
            var cont=$("#meetingcontentid").val();
            if(cont.trim()==""){
            	$.showtip("会议内容不能为空");
            }else if(cont==meetingcont){
                $.showtip("会议内容未改变");
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
                    $.hideloading();
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

            }
        });

    };


    return {
        saveClick:saveClick
    };
})();

var addmeeting=(function(){
	var data={
			filename:""
	};
	var filechange=function(){
		$("#file").on("change",function(e){
			var fileName="";
			if(e.currentTarget.files.length>0){
				fileName=e.currentTarget.files[0].name;
				if(fileName.match(att)){
					data.filename=fileName;
				}else{
					$.showtip("不支持上传此类型文件");
					data.filename="";
					var html="<input id=\"file\" name=\"file\" type=\"file\"/>选择文件</a>";
					$(this).parent().html(html);
					filechange();
					$.hideloading();
				}
			}else{
				data.filename="";
			}
			
			$(".newfilename").html(data.filename);
    		if(data.filename!=""){
    			$(".submitMeeting").removeClass("hidden");
    		}else{
    			$(".submitMeeting").addClass("hidden");
    		}
    	});
	};
	
	//会议附件下载
	var loadlable=function(){
		$(".loadul").html("");
        if(congfig.data.meetingfile!=null&&congfig.data.meetingfile.length>0){
        	for(var i=0;i<congfig.data.meetingfile.length;i++){
        		var html="<li>"+"<a href=\""+congfig.data.meetingfile[i].base_meeting_src+
                "\" class=\"downloadMeeting\" target=\"newWindows\">"+congfig.data.meetingfile[i].base_meeting_filename+"</a>"+
        				 "<span del=\""+congfig.data.meetingfile[i].base_file_code+"\" class=\" glyphicon-remove delClick hidden\"></span>"+
                         "</li>";
        		$(".loadul").append(html);
        	}
        	
        	delclick();
        }
	};
	
	var click=function(){
		$(".submitMeeting").unbind().bind("click",function(){
			if(data.filename!=""){
				upajax();
			}else{
				$.showtip("请选择上传文件");
			}
			
		});
		
	};
	
	var upajax=function(){
		$.showloading();
		$.ajaxFileUpload({
	    	   url:"AddMeetingInfo.html?logintype="+cookieopt.getlogintype()
	    		   +"&meetingcode="+meetingcode,//需要链接到服务器地址
	           type:'post',
	           secureuri:true,
	           fileElementId:"file",  //文件选择框的id属性
	           dataType: 'json', //服务器返回的格式，可以是json
	           success:function(json){
	        	   $.hideloading();
	        	   if(json.message=="success"){
	        		   $.showtip("上传成功");
	        		   data.filename="";
	        		   $(".submitMeeting").addClass("hidden");
	        		   $(".newfilename").html("");
	        		   filechange();
	        		   congfig.data.meetingfile=json.info;
	        		   loadlable();
	        	   }else if(json.message=="nodata"){
	        		   $.showtip("参数存在空项");
	        	   }else if(json.message=="nullfile"){
	        		   $.showtip("请选择上传文件");
	        	   }else if(json.message=="noword"){
	        		   $.showtip("请上传.doc或.docx格式文件");
	        	   }else{
	        		   $.showtip("系统异常");
	        	   }
	        	   $.hideloading();
	           },
		       error:function(){
		    	   $.showtip("请求异常");
		    	   $.hideloading();
		       }
    	});
	};
	
	//删除会议附件
	var delclick=function(){
		$(".delClick").unbind().bind("click",function(){
			var code=$(this).attr("del");
			layer.confirm('您确定要删除该条记录吗？', {
                title:"提示",
                icon: 0,
                btn: ['确定','取消'] //按钮
            }, function(index){
            	delajax(code);
                layer.close(index);
            }, function(index){
                layer.close(index);
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
                    congfig.data.meetingfile=vdata.fileinfo;
	        		loadlable();
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

            }
        });

    
	};
	
	return {
		initMeetingClick:click,
		loadlable:loadlable,
		data:data,
		filechange:filechange
	};
})();
