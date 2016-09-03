/**
 * Created by shbs-tp001 on 15-9-23.
 * Edit by duwenjie on 2015-11-3
 */
$(function () {
	//初始加载字典数据
	initconfig.initLoad();
    //基金添加事件
    fundconfig.addClick();
    //创建机构事件
    addconfig.addclick();
	
//    $("input[type=\"checkbox\"], input[type=\"radio\"]").not("[data-switch-no-init]").bootstrapSwitch();
});

var initconfig=(function(){
	var initdata={
			currencyList:[],//币种
			currencyChildList:[],//币种子项
			baskList:[],//筐
			induList:[],//行业
			stageList:[],//投资阶段
			featList:[],//投资特征
			typeList:[],//类型
			bgroundList:[],//背景
            investorList:[]//用户
	};
	
	var initLoad=function(){
		$.showloading();//等待动画
		$.ajax({
    		url:"initInvestmentInfoData.html",
    		type:"post",
    		async:false,
    		data:{logintype:cookieopt.getlogintype()},
    		dataType: "json",
    		success: function(data){
    			$.hideloading();//等待动画隐藏
    			if(data.message=="success"){
    				initdata.currencyList=data.currencyList;
    				initdata.currencyChildList=data.currencyChildList;
    				initdata.baskList=data.baskList;
    				initdata.induList=data.induList;
    				initdata.stageList=data.stageList;
    				initdata.featList=data.featList;
    				initdata.typeList=data.typeList;
    				initdata.bgroundList=data.bgroundList;
                    initdata.investorList=data.investorList;
    				
    				rendering();//渲染标签选中事件
    			}else{
    				$.showtip(data.message);
    				setTimeout("$.hidetip()", 2000);
    			}
    		}
    	});
		
		
	};
	
	var rendering=function(){
		$(".editOrg").bind("click", function () {
	        inputlsit_edit.config({
	            title:"机构名称",
	            list:[{id:"orgFullName",lable:"机构全称",type:"text",value:addconfig.data.orgfname},
	                  {id:"orgName",lable:"机构简称",type:"text",value:addconfig.data.orgname},
	                  {id:"orgEname",lable:"英文名称",type:"text",value:addconfig.data.orgename}],
	            submit: function () {
	            	 var fullName=$("#orgFullName").val().trim();
	                 var cName=$("#orgName").val().trim();
	                 var eName=$("#orgEname").val().trim();
	                 if(cName==""){
	                     $.showtip("机构简称不能为空");
	                 }else{
	                	 addconfig.data.orgfname=fullName;
	                	 addconfig.data.orgname=cName;
	                	 addconfig.data.orgename=eName;
                         checkorgbyname();
	                 }
	                 setTimeout("$.hidetip()",2000);
	            	
	            }
	        });
	   });
		 $(".editDask").bind("click", function () {
		        listefit.config({
		            title:"关注筐",
                    radio:false,
		            list:dicListConfig(initdata.baskList,addconfig.data.baskInfo),
		            besure: function () {
		            	addconfig.data.baskInfo=choiceContent();
		            	var html="";
		            	if(addconfig.data.baskInfo.length>0){
		            		html=template.compile(label_data.orgInfoList)({list:addconfig.data.baskInfo});
		            	}
		            	$(".dasklist").html(html);
		            }
		        });
		   });
		 $(".editIndu").bind("click", function () {
		        listefit.config({
		            title:"行业",
                    radio:false,
		            list:dicListConfig(initdata.induList,addconfig.data.induInfo),
		            besure: function () {
		            	addconfig.data.induInfo=choiceContent();
		            	var html="";
		            	if(addconfig.data.induInfo.length>0){
		            		html=template.compile(label_data.orgInfoList)({list:addconfig.data.induInfo});
		            	}
		            	$(".indulist").html(html);
		            }
		        });
		   });
		 $(".editPayatt").bind("click", function () {
		        listefit.config({
		            title:"近期关注",
                    radio:false,
		            list:dicListConfig(initdata.induList,addconfig.data.payattInfo),
		            besure: function () {
		            	addconfig.data.payattInfo=choiceContent();
		            	var html="";
		            	if(addconfig.data.payattInfo.length>0){
		            		html=template.compile(label_data.orgInfoList)({list:addconfig.data.payattInfo});
		            	}
		            	$(".payattlist").html(html);
		            }
		        });
		   });
        $(".editBgground").bind("click", function () {
            listefit.config({
                title:"背景",
                radio:true,
                list:dicListConfig(initdata.bgroundList,addconfig.data.bggroundInfo),
                besure: function () {
                    addconfig.data.bggroundInfo=choiceContent();
                    var html="";
                    if(addconfig.data.bggroundInfo.length>0){
                        html=template.compile(label_data.orgInfoList)({list:addconfig.data.bggroundInfo});
                    }
                    $(".bggroundlist").html(html);
                }
            });
        });
		 $(".eidtType").bind("click", function () {
		        listefit.config({
		            title:"类型",
                    radio:false,
		            list:dicListConfig(initdata.typeList,addconfig.data.typeInfo),
		            besure: function () {
		            	addconfig.data.typeInfo=choiceContent();
		            	var html="";
		            	if(addconfig.data.typeInfo.length>0){
		            		html=template.compile(label_data.orgInfoList)({list:addconfig.data.typeInfo});
		            	}
		            	$(".typelist").html(html);
		            }
		        });
		   });
		 $(".editStage").bind("click", function () {
		        listefit.config({
		            title:"阶段",
                    radio:false,
		            list:dicListConfig(initdata.stageList,addconfig.data.stageInfo),
		            besure: function () {
		            	addconfig.data.stageInfo=choiceContent();
		            	var html="";
		            	if(addconfig.data.stageInfo.length>0){
		            		html=template.compile(label_data.orgInfoList)({list:addconfig.data.stageInfo});
		            	}
		            	$(".stagelist").html(html);
		            }
		        });
		   });
		 $(".editFeat").bind("click", function () {
		        listefit.config({
		            title:"投资特征",
                    radio:false,
		            list:dicListConfig(initdata.featList,addconfig.data.featInfo),
		            besure: function () {
		            	addconfig.data.featInfo=choiceContent();
		            	var html="";
		            	if(addconfig.data.featInfo.length>0){
		            		html=template.compile(label_data.orgInfoList)({list:addconfig.data.featInfo});
		            	}
		            	$(".featlist").html(html);
		            }
		        });
		   });
		
	};
    function checkorgbyname() {
        $.ajax({
            url:"checkorgbyname.html",
            type:"post",
            async:true,
            data:{
                orgFullName:addconfig.data.orgfname,
                orgName:addconfig.data.orgname,
                orgEname:addconfig.data.orgename,
                logintype:cookieopt.getlogintype()
            },
            dataType: "json",
            success: function(data){
                if(data.message=="only"){
                    var list={list:[{name:addconfig.data.orgfname},{name:addconfig.data.orgname},{name:addconfig.data.orgename}]};
                    $(".orglist").html(template.compile(label_data.orgInfoList)(list));
                    inputlsit_edit.close();
                }else if(data.message=="error"){
                    $.showtip("请求失败");
                }else{
                    $.showtip(data.message);
                }
                setTimeout("$.hidetip()",2000);
            }
        });
    }
	return {
		initLoad:initLoad,
		data:initdata
	};
})();

//基金
var fundconfig=(function(){
	var addClick=function(){
		//添加基金弹筐
		$(".addbtnfund").bind("click",function () {
            var htmltext=
                '<li><span class="lable">名称:</span><span class="in"><input id="fundName" type="text" class="inputdef"></span></li>' +
                '<li><span class="lable">币种:</span><span class="in">'+template.compile(label_data.currencyDIC)({list:initconfig.data.currencyList})+'</span></li>'+
                '<li><span class="lable">规模:</span><span class="in">'+template.compile(label_data.scaleDIC)(currency(initconfig.data.currencyList[0].sys_labelelement_code))+'</span></li>';
            inputlsit_edit.config({
                title:"新增基金",
                html:true,
                htmltext:htmltext,
                submit: function () {
                    var fundName=$("#fundName").val().trim();
                    var fundCurrency=$("#fundCurrency").find("option:selected").val();
                    var fundCurrencyTxt=$("#fundCurrency").find("option:selected").text();
                    var fundScale=$("#fundScale").find("option:selected").val();
                    var fundScaleTxt=$("#fundScale").find("option:selected").text();
                    var map={};
                    map.fundName=fundName;
                    map.fundCurrency=fundCurrency;
                    map.fundCurrencyTxt=fundCurrencyTxt;
                    map.fundScale=fundScale;
                    map.fundScaleTxt=fundScaleTxt;
                    addconfig.data.fundInfo.push(map);
                    rendering();
                    inputlsit_edit.close();
                    setTimeout("$.hidetip()",2000);
                }
            });
		    
		});
	};

    var rendering=function(){
        var data=eval(addconfig.data.fundInfo);
        var html=template.compile(label_data.invesfundList)({list:data});
        $(".invesFundTable tbody").html(html);
        delClick();

    };
	var delClick=function(){
		//删除按钮事件
        $(".delFundClick").unbind().bind("click",function () {
            addconfig.data.fundInfo.baoremove($(this).attr("del-code"));
            rendering();
        });

    };


    return {
        addClick:addClick
    };
})();

//易凯联系人
var yklinkconfig=(function(){
    var rendyk=function(){
        var html="";
        if(addconfig.data.ykInfo.length>0){
            html=template.compile(label_data.ykLabelList)({list:addconfig.data.ykInfo});
        }
        $(".yklist").html(html);
        delClick();
    };

    var addClick=function(){
        $(".addbtnyk").unbind().bind("click",function () {
            inputlsit_edit.config({
                title:"添加易凯联系人",
                list:[{id:"ykLinkManName",lable:"易凯联系人",optionlist:initconfig.data.investorList,type:"select"}],
                submit: function () {
                    var userName=$("#ykLinkManName").find("option:selected").text();
                    var userId=$("#ykLinkManName").find("option:selected").attr("id");
                    var bool=true;
                    if(addconfig.data.ykInfo!=null){
                        for ( var i = 0; i < addconfig.data.ykInfo.length; i++) {
                            if(userId==addconfig.data.ykInfo[i].code){
                                bool=false;
                                break;
                            }
                        }
                    }
                    if(!bool){
                        $.showtip("联系人已添加");
                        setTimeout("$.hidetip()",2000);
                    }else{
                        var map={};
                        map.code=userId;
                        map.name=userName;
                        addconfig.data.ykInfo.push(map);
                        rendyk();
                        inputlsit_edit.close();
                    }


                }
            });
        });
    };

    var delClick=function(){
        $(".ykDelClick").unbind().bind("click",function(){
            var code=$(this).attr("id");
            for(var i=0;i<addconfig.data.ykInfo.length;i++){
                if(code==addconfig.data.ykInfo[i].code){
                    addconfig.data.ykInfo.baoremove(i);
                }
            }
            rendyk();
        });
    };

    return{
        click:addClick()
    };

})();


var addconfig=(function(){
	var labelData={
			orgCode:'',
			message:'',
			orgfname:'',
			orgname:'',
			orgename:'',
			baskInfo:[],
			induInfo:[],
			payattInfo:[],
			bggroundInfo:[],
			typeInfo:[],
			stageInfo:[],
			featInfo:[],
            fundInfo:[],
            ykInfo:[]
	};

    var submitClick=function(){
    	//创建机构
        $(".orgSubmit").unbind().bind("click",function(){
        	var note=$("#orgNote").val();
        	if(labelData.orgname==""){
       		 $.showtip("机构简称不能为空");
       		setTimeout("$.hidetip()",2000);
	       	}else{
	       		addAjax(note);
	            if(labelData.message=="success"){
	            	$.showtip("保存成功");
	            	setTimeout(function(){
	            		$.hidetip();
		            	window.location.reload();
	            	},2000);
	            }else{
	            	$.showtip(labelData.message);
	            }
	       	}
        });
        
        //创建机构并添加交易
        $(".orgSubmitToTrade").unbind().bind("click",function(){
        	var note=$("#orgNote").val();
        	if(labelData.orgname==""){
          		 $.showtip("机构简称不能为空");
          		setTimeout("$.hidetip()",2000);
   	       	}else{
   	       		if(labelData.orgCode==""){
	   	       		addAjax(note);
	                if(labelData.message=="success"){
	                	$.showtip("保存成功");
	    	   	       	setTimeout(function(){
	    	        		$.hidetip();
	    	        		window.location.href="addTradeInfoinit.html?investmentcode="+labelData.orgCode+"&logintype="+cookieopt.getlogintype();
	    	        	},2000);
	                }else{
	                	$.showtip(labelData.message);
	                	setTimeout("$.hidetip()",2000);
	                }
   	       		}else{
   	       			window.location.href="addTradeInfoinit.html?investmentcode="+labelData.orgCode+"&logintype="+cookieopt.getlogintype();
   	       		}
            }
        });
        
        //创建机构并添加投资人
        $(".orgSubmitToInvestor").unbind().bind("click",function(){
        	var note=$("#orgNote").val();
        	if(labelData.orgname==""){
          		$.showtip("机构简称不能为空");
          		setTimeout("$.hidetip()",2000);
   	       	}else{
   	       		if(labelData.orgCode==""){
	   	       		addAjax(note);
	                if(labelData.message=="success"){
	                	$.showtip("保存成功");
	    	   	       	setTimeout(function(){
	    	        		$.hidetip();
	    	        		window.location.href="investor_add.html?investmentCode="+labelData.orgCode+"&logintype="+cookieopt.getlogintype();
	    	        	},2000);
	                }else{
	                	$.showtip(labelData.message);
	                	setTimeout("$.hidetip()",2000);
	                }
	       		}else{
	       			window.location.href="investor_add.html?investmentCode="+labelData.orgCode+"&logintype="+cookieopt.getlogintype();
	       		}
            }
        });
    };
	
	var addAjax=function(note){
		$.showloading();//等待动画
		$.ajax({
    		url:"insertInvestmentInfo.html",
    		type:"post",
    		async:false,
    		data:{
                orgFullName:labelData.orgfname,
                orgName:labelData.orgname,
                orgEname:labelData.orgename,
                orgNote:note,
                baskInfo:JSON.stringify(labelData.baskInfo),
                induInfo:JSON.stringify(labelData.induInfo),
                payattInfo:JSON.stringify(labelData.payattInfo),
                bggroundInfo:JSON.stringify(labelData.bggroundInfo),
                typeInfo:JSON.stringify(labelData.typeInfo),
                stageInfo:JSON.stringify(labelData.stageInfo),
                featInfo:JSON.stringify(labelData.featInfo),
                fundInfo:JSON.stringify(addconfig.data.fundInfo),
                yklinkInfo:JSON.stringify(addconfig.data.ykInfo),
                logintype:cookieopt.getlogintype()
            },
    		dataType: "json",
    		success: function(data){
    			$.hideloading();//等待动画隐藏
    			labelData.message=data.message;
    			if(data.message=="success"){
    				labelData.orgCode=data.orgCode;
    			}
    		}
    	});
	};
	
	return {
		data:labelData,
		addclick:submitClick
	};
	
})();

var label_data={
		currencyDIC:'<select id="fundCurrency" class="inputdef" onchange="findScale(this.value)">'+
		'<%for(var i=0;i<list.length;i++){%>'+
		'<option value="<%=list[i].sys_labelelement_code%>"><%=list[i].sys_labelelement_name%></option>'+
		'<%}%></select>',
		scaleDIC:'<select id="fundScale" class="inputdef" >'+
		'<%for(var i=0;i<list.length;i++){%>'+
		'<option value="<%=list[i].code%>"><%=list[i].name%></option>'+
		'<%}%></select>',
        ykSelect:'<select id="ykLink" class="inputdef" >'+
        '<%for(var i=0;i<list.length;i++){%>'+
        '<option value="<%=list[i].id%>"><%=list[i].text%></option>'+
        '<%}%></select>',

		orgInfoList:'<%for(var i=0;i<list.length;i++){%>'+
    	'<li data-i="<%=i%>"><%=list[i].name%></li>'+
    	'<%}%>',
    	orgInfoListNull:'<li>暂无数据</li>',
        invesfundList:'<%for(var i=0;i<list.length;i++){%>'+
        '<tr><td><%=list[i].fundName%></td>'+
        '<td><%=list[i].fundCurrencyTxt%></td>'+
        '<td><%=list[i].fundScaleTxt%></td>'+
        '<td>有效</td>'+
        '<td><button class="smart btn btn-default delFundClick" i="<%=i%>" del-code="<%=i%>">删除</button></td></tr>'+
        '<%}%>',
        ykLabelList:'<%for(var i=0;i<list.length;i++){%>'+
        '<div class="ykLabel"><span class="labelSpan"><%=list[i].name%></span><span id="<%=list[i].code%>" class="glyphicon glyphicon-remove ykDelClick"></span></div>'+
        '<%}%>'
};


//弹层选择筐选择值,返回[{code:'',name:''}]
function choiceContent(){
		var list=[];
		var map={};
		$(".list li[class='active']").each(function(){
			map={};
			map.code=$(this).attr("tip-l-i");
			map.name=$(this).text();
			list.push(map);
		});
	return list;
}

//投资机构标签弹出层集合
function dicListConfig(vList,vObj){
//	vObj=eval(vObj);
	var list=[];
	var map={};
	for(var i=0;i<vList.length;i++){
		map={};
		map.name=vList[i].sys_labelelement_name;
		map.id=vList[i].sys_labelelement_code;
		if(vObj.length>0){
			for ( var j = 0; j < vObj.length; j++) {
				//判断标签是否被选中,存在则加上选中标识
				if(vList[i].sys_labelelement_code==vObj[j].code){
					map.select=true;
				}
			}
		}
		list.push(map);
	}
	
	return list;
}

//根据币种类型筛选币种子项
function currency(vCode){
	var list=[];
	var map={};
	for ( var i = 0; i < initconfig.data.currencyChildList.length; i++) {
		if(initconfig.data.currencyChildList[i].sys_label_code==vCode){
			map={};
			map.code=initconfig.data.currencyChildList[i].sys_labelelement_code;
			map.name=initconfig.data.currencyChildList[i].sys_labelelement_name;
			list.push(map);
		}
	}
	return {list:list};
}

//动态加载投资规模
function findScale(vCode){
	var html=template.compile(label_data.scaleDIC)(currency(vCode));
	$("#fundScale").html(html);
}
