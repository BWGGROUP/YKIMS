/**
 * Created by shbs-tp001 on 15-9-21.
 */
//var basklist;//交易筐
var baskJson;//选择筐
//var indulist;//交易行业
var induJson;//选择行业
var stagelist;//阶段
var stageJson;//选择阶段
var notedata={};//备注数据集
var tradeCode=tradeInfo.base_trade_code;//交易Code
var stageCode;//阶段code
var stageCont;//阶段
var companyCode;//被投公司code
var companyName;//被投公司简称
var tradeDate;//投资日期
var tradeMoney;//融资额
var companyMoney;//公司估值
var companyMoneyType;//公司估值状态
var refBool=true;//判断删除完成是否刷新子页
var tradetable;//融资信息分页


var version;//排他锁版本号
var tradepage={};//基金分页
var tradeInfoList={};//融资信息
var baskList={};//关注筐集合
var induList={};//行业集合
var stageList={};//阶段集合
var noteList={};//交易备注

$(function () {
	if(tradeCode!=null){
		//初始化加载
		initAjax();
		$(".goback").show();
        $(".goback").click(function () {
        	if(backherf=="searchfoot"){
        		var type="hidden";
                var w = document.createElement("form");
                document.body.appendChild(w);
                var a= document.createElement("input");
                var b= document.createElement("input");
                a.type= b.type=type;
                a.name="logintype";
                a.value=cookieopt.getlogintype();
                b.name="backtype";
                b.value=backherf;
                w.appendChild(a);
                w.appendChild(b);
                w.action = "myfoot_search.html";
                w.method="post";

                w.submit();
        	}else if(backherf=="searchtrade"){
        		window.location.href="trade_search.html?logintype="+cookieopt.getlogintype()+"&backtype="+backherf;
        	}else{
        		window.location.href=backherf+"&logintype="+cookieopt.getlogintype();
        	}
        });
	}else{
		$.showtip("未找到所需数据");
		setTimeout("$.hidetip()",2000);
		if(backherf!=""){
			setTimeout(function(){
				if(backherf=="searchfoot"){
	        		var type="hidden";
	                var w = document.createElement("form");
	                document.body.appendChild(w);
	                var a= document.createElement("input");
	                var b= document.createElement("input");
	                a.type= b.type=type;
	                a.name="logintype";
	                a.value=cookieopt.getlogintype();
	                b.name="backtype";
	                b.value=backherf;
	                w.appendChild(a);
	                w.appendChild(b);
	                w.action = "myfoot_search.html";
	                w.method="post";

	                w.submit();
	        	}else if(backherf=="searchtrade"){
	        		window.location.href="trade_search.html?logintype="
	                    +cookieopt.getlogintype()+"&backtype="+backherf;
	        	}else{
	        		window.location.href=backherf+"&logintype="+cookieopt.getlogintype();
	        	}
			},2000);
		}else{
			setTimeout(function(){
				window.location.href="trade_search.html?logintype="+cookieopt.getlogintype();
			},2000);
		}
	}
	//初始化渲染
	init();
});

function initAjax(){
	$.showloading();//等待动画
	$.ajax({
		url:"initTradeDetialInfo.html",
		type:"post",
		async:false,
		data:{
				tradeCode:tradeCode,
    			logintype:cookieopt.getlogintype()
			},
		dataType: "json",
		success: function(data){
			$.hideloading();//等待动画隐藏
			if(data.message=="success"){
				version=data.version;//排他锁版本号
				tradeInfoList=data.tradeInfoList;//融资信息
				baskList=data.baskList;//关注筐集合
				induList=data.induList;//行业集合
				stageList=data.stageList;//阶段集合
				noteList=data.noteList;//交易备注
				tradeInfo=data.tradeInfo;//交易详情
			}else{
				$.showtip(data.message);
				setTimeout("$.hidetip()",2000);
			}
			
		}
	});
};

function init(){
//	tradepage.pageSize=CommonVariable().PAGESIZE;//加载分页一页条数
	baskJson=eval(tradeInfo.view_trade_baskcont);//关注筐
	induJson=eval(tradeInfo.view_trade_inducont);//行业
	
	tradeCode=tradeInfo.base_trade_code;//交易Code
	stageCode=tradeInfo.base_trade_stage;//阶段code
	stageCont=tradeInfo.base_trade_stagecont!=null?tradeInfo.base_trade_stagecont:"暂无数据";//阶段

	//被投公司code
	companyCode=tradeInfo.base_comp_code;
	//被投公司简称
	companyName=tradeInfo.base_comp_name;
	//投资日期
	tradeDate=tradeInfo.base_trade_date;
	//融资金额
	tradeMoney=tradeInfo.base_trade_money;
	//公司估值
	companyMoney=tradeInfo.base_trade_comnum;
    //公司估值状态
    companyMoneyType=tradeInfo.base_trade_comnumtype;

    notedata={list:noteList,initSize:CommonVariable().NOTECONFIGNUM};//备注数据集
	
	labelconfig.companyLoad();//被投公司
	labelconfig.dateLoad();//交易时间
	labelconfig.daskLoad();//加载关注筐
	labelconfig.induLoad();//加载行业
	labelconfig.stageLoad();//加载阶段
	labelconfig.tradeMoneyLoad();//加载融资额
	labelconfig.companyMoneyLoad();//加载公司估值
	tradeInfoconfig.tradeInfoLoad();//融资信息
	tradeInfoconfig.tradeMoreClick();//融资信息分页
	tradeInfoconfig.tradeInvesAddClick();//添加融资信息
	tradeNoteconfig.tradeNoteLoad();//备注
	tradeNoteconfig.tradeNoteSubmit();//备注提交
	updlogconfig.initUpdlog();//更新记录
	delTradeconfig.click();//删除交易
	
}

//投资机构标签弹出层集合
function dicListConfig(vDel,vList,vObj){
	var list=[];
	var map={};
	for(var i=0;i<vList.length;i++){
		map={};
		map.name=vList[i].sys_labelelement_name;
		map.id=vList[i].sys_labelelement_code;
		if(vObj!=null){
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

//弹层选择筐选择值,返回[{code:'',name:''}]
function choiceContent(){
	var list="[";
	$(".list li[class='active']").each(function(){
		list+="{code:'"+$(this).attr("tip-l-i")+"',name:'"+$(this).text()+"'},";
	});
	list+="]";
	return list;
}

//弹层选择筐选择值,返回[{code:'',name:''}]
function oldContent(vObj){
	var list="[";
	if(vObj!=null){
		for ( var i = 0; i < vObj.length; i++) {
			list+="{code:'"+vObj[i].code+"',name:'"+vObj[i].name+"'},";
		}
	}
	list+="]";
	return list;
}
//控制加载更多按钮的方法
function checktip_havemore(){
    $("ul[ro]").each(function () {
        if( $(this)[0].scrollHeight>$(this)[0].clientHeight){
            $(this).parents(".shgroup").addClass("havemore");
        }else{
        	$(this).parents(".shgroup").removeClass("havemore");
        }
    });
    $(".havemore .more").unbind().bind("click",function () {
        showmore($(this));
    });
    
 
}

//操作标签更多
function showmore(a){
    if($(a).parents(".shgroup").hasClass("heightauto")){
        $(a).parents(".shgroup").removeClass("heightauto");
        $(a).html('<span class="glyphicon glyphicon-chevron-down" ></span>');
    }else{
        $(a).parents(".shgroup").addClass("heightauto");
        $(a).html('<span class="glyphicon glyphicon-chevron-up" ></span>');

    }
}
//刷新操作标签
function refreshladle(a){
	var shgroup=$(a).parents(".shgroup");
	if(shgroup.hasClass("heightauto")){
		shgroup.removeClass("heightauto");
		if( $(a)[0].scrollHeight>$(a)[0].clientHeight){
			if(!shgroup.hasClass("havemore")){
				shgroup.addClass("havemore");
			}	 
	        }else{
	        	shgroup.removeClass("havemore");
	        }
		 shgroup.addClass("heightauto");
	}else{
		if( $(a)[0].scrollHeight>$(a)[0].clientHeight){
			if(!shgroup.hasClass("havemore")){
				shgroup.addClass("havemore");
			}
	        }else{
	        	shgroup.removeClass("havemore");
	        }
	}
	 $(".havemore .more").unbind().bind("click",function () {
	        showmore($(this));
	  });
}



var labelconfig=(function(){
	//加载被投公司
	var initCompany=function(){
		var html=table_data.contentNull;
		if(companyName!=null && companyName!=""){
			html=companyName;
		}
		$("#company").html(html);
		$(".companyClick").unbind().bind("click",function () {
			if(companyCode!=null && companyCode!=""){
				window.location.href="findCompanyDeatilByCode.html?logintype="+cookieopt.getlogintype()+"&code="+companyCode;
			}else{
				$.showtip("未发现公司");
				setTimeout("$.hidetip()", 2000);
			}
			
		});
	};
	
	//加载交易时间
	var initTradeDate=function(){
		//加载时间
		var html=table_data.contentNull;
		if(tradeDate!=null && tradeDate!=""){
			html=tradeDate;
		}
		$("#tradeDate").html(html);
		tradeDateClick();
	};
	
	//加载交易时间单击事件
	var tradeDateClick=function(){
	  $(".tradeDateClick").unbind().bind("click",function () {
		  var htmltext='<span class="lable">投资日期:</span><span class="in"><input id="base_trade_date" type="date" class="inputdef"></span>';
		  inputlsit_edit.config({
		      title:"投资日期",
		      html:true,
	          htmltext:htmltext,
		      submit: function () {
		    	  var date=$("#base_trade_date").val();
		    	  inputlsit_edit.close();
		    	  if(tradeCode==null){
		    		  $.showtip("未发现交易信息");
		    	  }else if(date==tradeDate){
		    		  $.showtip("未修改数据");
		    	  }else if(date==""){
		    		  $.showtip("投资日期不能为空");
		    	  }else{
		    		  tradeDateAjax(date);
		    	  }
		    	  setTimeout("$.hidetip()",2000);
		    	  
		      }
		  });
		  $("#base_trade_date").val(tradeDate);
	  });
	  
	};
	
	//交易时间提交
	var tradeDateAjax=function(data){
		$.showloading();//等待动画
    	$.ajax({
    		url:"updateTradeDetialInfo.html",
    		type:"post",
    		async:false,
    		data:{
    				base_trade_code:tradeCode,
    				type:'base_trade_date',
        			logintype:cookieopt.getlogintype(),
        			oldData:tradeDate,
        			base_trade_date:data,
        			version:version
    			},
    		dataType: "json",
    		success: function(data){
    			$.hideloading();//等待动画隐藏
    			if(data.message=="success"){
    				$.showtip("保存成功");
    				version=data.version;
    				tradeDate=data.newData;
    				initTradeDate();
    			}else{
    				$.showtip(data.message);
    			}
    		}
    	});
	
	};
	
	
	//初始加载关注筐
	var initDask=function(){
		var html="";
		if(baskJson!=null && baskJson!=""){
			var data={list:baskJson};
			html=template.compile(table_data.labelInfoList)(data);
		}else{
			html=table_data.labelNull;
		}
		$("#ul-bask").html(html);
		daskClick();
	};
	//关注筐事件
	var daskClick=function(){
		var basklist=dicListConfig(true,baskList,baskJson);
		$(".baskClick").unbind().bind("click",function () {
			var $this=$(this);
	        listefit.config({
	            title:"关注筐",
	            list:basklist,
	            radio:false,
	            besure: function () {
	            	var oldData=oldContent(baskJson);
	            	var newData=choiceContent();
	            	if(oldData==newData){
	            		$.showtip("未修改数据");
	            	}else{
	            		$.showloading();//等待动画
	                	$.ajax({
	                		url:"updateTradeInfoLabel.html",
	                		type:"post",
	                		async:false,
	                		data:{
	                				type:'trade-Lable-bask',
	                				tradeCode:tradeCode,
	    	            			logintype:cookieopt.getlogintype(),
	    	            			oldData:oldData,
	    	            			newData:newData,
	    	            			version:version
	                			},
	                		dataType: "json",
	                		success: function(data){
	                			$.hideloading();//等待动画隐藏
	                			if(data.message=="success"){
	                				$.showtip("保存成功");
	                    			baskJson=data.list;
	                    			version=data.version;
	                    			initDask();
	                			}else{
	                				$.showtip(data.message);
	                			}
//	                			refreshladle($this);
	                		}
	                	});
	            		
	            	}
	            	
	            	setTimeout("$.hidetip()",2000);
	            }
	        });
	    });
	};
	
	
	var initIndu=function(){
		if(induJson!=null && induJson!=""){
			var data={list:induJson};
			html=template.compile(table_data.labelInfoList)(data);
		}else{
			html=table_data.labelNull;
		}
		$("#ul-indu").html(html);
		induClick();
	};
	var induClick=function(){
		 //行业弹层
		var indulist=dicListConfig(false,induList,induJson);
	    $(".induClick").unbind().bind("click",function () {
	    	var $this=$(this);
	        listefit.config({
	            title:"行业",
	            list:indulist,
	            radio:false,
	            besure: function () {
	            	var oldData=oldContent(induJson);
	            	var newData=choiceContent();
	            	if(oldData==newData){
	            		$.showtip("未修改数据");
	            	}else{
	            		$.showloading();//等待动画
		            	$.ajax({
		            		url:"updateTradeInfoLabel.html",
		            		type:"post",
		            		async:false,
		            		data:{
		            				type:'trade-Lable-indu',
		            				tradeCode:tradeCode,
			            			logintype:cookieopt.getlogintype(),
			            			oldData:oldData,
			            			newData:newData,
			            			version:version
		            			},
		            		dataType: "json",
		            		success: function(data){
		            			$.hideloading();//等待动画隐藏
		            			if(data.message=="success"){
		            				$.showtip("保存成功");
		                			induJson=data.list;
		                			version=data.version;
		                			initIndu();
		            			}else{
		            				$.showtip(data.message);
		            			}
//		            			refreshladle($this);
		            		}
		            	});
	            	}
	            	
	            	setTimeout("$.hidetip()",2000);
	            }
	        });
	    });
	};
	
	
	//加载阶段
	var initStage=function(){
		var html=table_data.contentNull;
		if(stageCont!=null && stageCont!=""){
			html=stageCont;
		}
		$("#ul-stage").html(html);
		stageClick();
	};
	
	var stageClick=function(){
		//阶段弹层
	    $(".stageClick").unbind().bind('click',function () {
	    	var stageJson=eval([{'code':stageCode,'name':stageCont}]);
	    	var stagelist=dicListConfig(false,stageList,stageJson);
	        listefit.config({
	            title:"阶段",
	            list:stagelist,
	            radio:true,
	            besure: function () {
	            	var newData=$(".list li[class='active']").attr("tip-l-i");
	            	var newDataName=$(".list li[class='active']").html();
	            	if(typeof(newData)=="undefined"){
	            		newData="[]";
	            		newDataName="";
	            	}
	            	if(tradeCode==null||tradeCode==""){
	            		$.showtip("未发现交易信息");
	            	}else if(stageCode==newData || (stageCode==null&&newData=="[]")){
	            		$.showtip("未修改数据");
	            	}else{
	            		$.showloading();//等待动画
		            	$.ajax({
		            		url:"updateTradeDetialInfo.html",
		            		type:"post",
		            		async:false,
		            		data:{
		            			base_trade_code:tradeCode,
		        				type:'base_trade_stage',
		            			logintype:cookieopt.getlogintype(),
		            			oldData:stageCont,
		            			newStageName:newDataName,
		            			base_trade_stage:newData,
		            			version:version
		            			},
		            		dataType: "json",
		            		success: function(data){
		            			$.hideloading();//等待动画隐藏
		            			if(data.message=="success"){
		            				$.showtip("保存成功");
		            				version=data.version;
		            				stageCont=data.newData;
		            				stageCode=data.newCode;
		            				initStage();
		            			}else{
		            				$.showtip(data.message);
		            			}
		            		}
		            	});
	            	}
	            	setTimeout("$.hidetip()",2000);
	            }
	        });
	    });
	};
	
	
	//加载融资额
	var initTradeMoney=function(){
		var html=table_data.contentNull;
		if(tradeMoney!=null && tradeMoney!=""){
			html=tradeMoney;
		}
		$("#tradeMoney").html(html);
		tradeMoneyClick();
	};
	
	//融资额单击事件
	var tradeMoneyClick=function(){
	  $(".tradeMoneyClick").unbind().bind("click",function () {
		  inputlsit_edit.config({
		      title:"编辑融资金额",
		      list:[{id:"newTradeMoney",lable:"融资金额",maxlength:"100"}],
		      submit: function () {
		    	  var money=$("#newTradeMoney").val();
		    	  
		    	  inputlsit_edit.close();
		    	  
		    	  if(tradeCode==null){
		    		  $.showtip("未发现交易信息");
		    	  }else if(money.trim()==""){
		    		  $.showtip("融资金额不能为空");
		    	  }else if(tradeMoney==money.trim()){
		    		  $.showtip("未修改数据");
		    	  }else{
		    		  tradeAjax(money);
		    	  }
		    	  setTimeout("$.hidetip()", 2000);
		      }
		  });
		  if(tradeMoney!=null && tradeMoney!="暂无数据"){
			  $("#newTradeMoney").val(tradeMoney);
		  }
	  });
	  
	};
	
	//融资金额提交
	var tradeAjax=function(data){
		$.showloading();//等待动画
    	$.ajax({
    		url:"updateTradeDetialInfo.html",
    		type:"post",
    		async:false,
    		data:{
    				base_trade_code:tradeCode,
    				type:'base_trade_money',
        			logintype:cookieopt.getlogintype(),
        			oldData:tradeMoney,
        			base_trade_money:data,
        			version:version
    			},
    		dataType: "json",
    		success: function(data){
    			$.hideloading();//等待动画隐藏
    			if(data.message=="success"){
    				$.showtip("保存成功");
    				tradeMoney=data.newData;
    				version=data.version;
    				initTradeMoney();
    			}else{
    				$.showtip(data.message);
    			}
    		}
    	});
	
	};

	
	
	//加载公司估值
	var initCompanyMoney=function(){
		var html=table_data.contentNull;
		if(companyMoney!=null && companyMoney!=""){
			html=companyMoney+"&nbsp;(<span class=\"companyTypeCheck\">"+(companyMoneyType==0?'获投前':companyMoneyType==1?'获投后':'未知')+"</span>)";
		}
		$("#companyMoney").html(html);
		companyMoneyClick();
	};
	
	//公司估值单击事件
	var companyMoneyClick=function(){
	  $(".companyMoneyClick").unbind().bind("click",function () {
		  var htmlText="<ul><li><span class=\"lable\">&nbsp;</span>" +
		  		"<span class=\"in\" style=\"text-align:left;\" ><input id=\"type0\"  name='comMoneyType' checked type='radio' value='0'>获投前 " +
                "<input id=\"type1\" name='comMoneyType' type='radio' value='1'>获投后</span></li>" +
		  		"<li><span class=\"lable\">公司估值:</span>" +
		  		"<span class=\"in\"><input id=\"newCompanyMoney\" type=\"text\" " +
		  		"class=\"inputdef\" maxlength=\"100\"></span></li></ul>";
		  inputlsit_edit.config({
		      title:"编辑公司估值",
		      html:true,
	          htmltext:htmlText,
//		      list:[{id:"newCompanyMoney",lable:"公司估值",maxlength:"100"}],
		      submit: function () {
		    	  var money=$("#newCompanyMoney").val();
		    	  var check=$('.in input[name="comMoneyType"]:checked ').val();
		    	  
		    	  inputlsit_edit.close();
		    	  
		    	  if(tradeCode==null){
		    		  $.showtip("未发现交易信息");
		    	  }else if(money.trim()==""){
		    		  $.showtip("公司估值不能为空");
		    	  }else if(companyMoney==money.trim()&&companyMoneyType==check){
		    		  $.showtip("未修改数据");
		    	  }else{
		    		  companyMoneyAjax(money,check);
		    		  
		    	  }
		    	  setTimeout("$.hidetip()",2000);
		      }
		  });
		  if(companyMoney!=null && companyMoney!="暂无数据"){
			  $("#newCompanyMoney").val(companyMoney);
			  var rObj=document.getElementsByName("comMoneyType");
              for(var i=0;i<rObj.length;i++){
                  if(rObj[i].value==companyMoneyType){
                	  rObj[i].checked="true";
                  }
              }
		  }
	  });
	};
	
	//公司估值提交
	var companyMoneyAjax=function(data,check){
		$.showloading();//等待动画
    	$.ajax({
    		url:"updateTradeDetialInfo.html",
    		type:"post",
    		async:false,
    		data:{
    				base_trade_code:tradeCode,
    				type:'base_trade_comnum',
        			logintype:cookieopt.getlogintype(),
        			oldData:companyMoney,
        			comMoneyType:companyMoneyType,
        			base_trade_comnum:data,
        			base_trade_comnumtype:check,
        			version:version
    			},
    		dataType: "json",
    		success: function(data){
    			$.hideloading();//等待动画隐藏
    			if(data.message=="success"){
    				$.showtip("保存成功");
    				version=data.version;
    				companyMoney=data.newData;
    				companyMoneyType=data.newType;
    				initCompanyMoney();
    			}else{
    				$.showtip(data.message);
    			}
    		}
    	});
	
	};
	
	return {
		companyLoad:initCompany,
		dateLoad:initTradeDate,
		daskLoad:initDask,
		induLoad:initIndu,
		stageLoad:initStage,
		tradeMoneyLoad:initTradeMoney,
		companyMoneyLoad:initCompanyMoney
	};
})();

var tradeInfoconfig=(function(){
//	初始加载
	var initTrade=function(){
		var html=table_data.tradeInvesListNull;
		if(tradeInfoList.length>0&&tradeInfoList[0]!=null){
			html=template.compile(table_data.tradeInvesList)({list:tradeInfoList});
			if(html==""){
				html=table_data.tradeInvesListNull;
			}
		}
		$("#tradeInfo tbody").html(html);

		if(tradeInfoList.length>0&&tradeInfoList[0]!=null){
			click();//融资信息选中行事件
			delClick();//删除按钮事件
		}
		
	};
	
	//初始融资信息子页
	var initchildpage=function(){
            refChildPage();//渲染子页table
            tradetable=$("#tradePage").showpage();//显示子页
            moreBack();//返回事件

	};

    //渲染子页面
    var refChildPage=function(){
        var html=table_data.tradeInvesListNull;
        if(tradeInfoList.length>0&&tradeInfoList[0]!=null){
        	html=template.compile(table_data.tradeInvesList)({list:tradeInfoList});
        	$(".pageaction").removeClass("display-none").addClass("display-block");
        }else{
        	$(".pageaction").removeClass("display-block").addClass("display-none");
        }
        $("#pageTradeBody tbody").html(html);
        pageTradeList();//分页控件
        if(tradeInfoList.length>0&&tradeInfoList[0]!=null){
        	childPageClick();//分页详情事件
            delClick();//删除按钮事件
        }
        
    };
	
	//融资信息选中行事件
	var click=function(){
		$("#tradeInfo tbody tr").unbind().bind("click",function(){
			var id=$(this).attr("id");
			tradedetial(id);
		});
	};
	
	//子页详情选中行事件
	var childPageClick=function(){
		$("#pageTradeBody tbody tr").unbind().bind("click",function(){
			var id=$(this).attr("id");
			tradedetial(id);
		});
	};
	
	//融资信息详情展示
	var tradedetial=function(vId){
        //zzg 修改　需求变更
		if(tradeInfoList.length>0){
			var data={};
			for ( var i = 0; i < tradeInfoList.length; i++) {
				if(tradeInfoList[i].base_investment_code==vId){
					data=tradeInfoList[i];
				}
			}
			var htmlText=template.compile(table_data.tradeInvesDetail)(data);
			inputlsit_edit.config({
			    title:"融资信息",
			    html:true,
                besurebtn:"修改",//确定按钮文字
                cancle:"关闭",//取消按钮文字 （如果不写，则不会显示该按钮）
		        htmltext:htmlText,
			    submit: function () {
                    selectinvestor.searchdata.searchinv="";
                      changetrade_data();
			    },
                canmit: function () {
                    selectinvestor.searchdata.searchinv="";
                    inputlsit_edit.close();
                },//点击取消按钮回调方法
                complete: function () {
                    $("#collvote").val(data.base_trade_collvote);
                    $("#subpay").val(data.base_trade_subpay);
                    $("#ongam").val(data.base_trade_ongam);
                    selectinvestment.data.investmentcode=data.base_investment_code;
                    selectinvestor.data.investorcode=data.base_investor_code;

                    selectinvestor.searchdata.searchinv=data.base_investor_name;

                    $("#investor").unbind().bind("click",function(e){
                            selectinvestor.config();
                    });

                }//效果加载完成回调方法
			  });
		}


        function changetrade_data() {
            var ischange=false;
            if(data.base_investor_code!==selectinvestor.data.investorcode){
                ischange=true;
            }else if(data.base_investor_name!==$("#investor").val().trim()&&$("#investor").val().trim()!=""){
                ischange=true;
            }
            if($("#inmoney").val().trim()!=data.base_trade_inmoney){
                ischange=true;
            }
            if($("#collvote").val()!=data.base_trade_collvote){
                ischange=true;
            }
            if($("#subpay").val()!=data.base_trade_subpay){
                ischange=true;
            }
            if($("#ongam").val()!=data.base_trade_ongam){
                ischange=true;
            }
            if(!ischange){
                $.showtip("内容未做更改");
                setTimeout(function () {
                    $.hidetip();
                },2000);
                return;
            }else{
                var json={};
                json.tradecode=data.base_trade_code;
                json.base_investment_code=data.base_investment_code;
                json.base_trade_collvote=$("#collvote").val();
                json.base_trade_ongam=$("#ongam").val();
                json.base_trade_subpay=$("#subpay").val();
                json.base_trade_inmoney=$("#inmoney").val().trim();
                json.version=version;
                json.base_investor_code=selectinvestor.data.investorcode;
                json.base_investor_name=$("#investor").val().trim();
                json.base_investment_name=data.base_investment_name;
                inputlsit_edit.close();
                update_list_tade(JSON.stringify(json));
            }

        }
        function update_list_tade(json) {
            $.ajax({
                url:"update_list_tade.html",
                type:"post",
                async:true,
                data:{
                    olddata:JSON.stringify(data),
                    jsonobject:json,
                    logintype:cookieopt.getlogintype()
                },
                dataType: "json",
                success: function(data){
                    $.hideloading();//隐藏等待动画
                    if(data.message=="success"){
                        initAjax();
                        init();
                        if($("#tradePage").hasClass("Page")){
                            tradePageAjax(tradepage.pageCount);//分页请求
                            initchildpage();//初始化子页
                        }
                        $.showtip("保存成功");
                    }else if(data.message=="notrade"){
                        $.showtip("该交易已不存在");
                    }else if(data.message=="havechange"){
                        $.showtip("已被修改,请刷新页面再修改");
                    }
                    setTimeout(function () {
                        $.hidetip();
                    },2000);
                }
            });
        }
        //zzg 修改结束　需求变更
	};
	
	//融资信息删除事件
	var delClick=function(){
		$(".tradeInfoDelClick").unbind().bind("click",function(e){
			var id=$(this).attr("del-code");
			inputlsit_edit.config({
                title:"提示",//弹层标题
                html:true,//是否以html显示
                besurebtn:"确定",//确定按钮文字
                cancle:"取消",//取消按钮文字 （如果不写，则不会显示该按钮）
                htmltext:"<div style='text-align: center;font-size: 15px;'>您确定要删除该条记录吗？</div>",
                submit: function () {
                	delAjax(id);
                    inputlsit_edit.close();
                },//点击确定按钮回调方法
                canmit: function () {
                    inputlsit_edit.close();
                }//点击取消按钮回调方法
            });
			e.stopPropagation();
		});
		
	};

	
	//添加融资信息图标事件
	var addClick=function(){
		$(".addbtn").unbind().bind("click",function(e){
			if(tradeCode!=null){
				inputlsit_edit.config({
				      title:"添加融资信息",
				      list:[
				        {id:"investment",lable:"投资机构",type:"text",readonly:"readonly"},
				        {id:"investor",lable:"投资人",type:"text",readonly:"readonly"},
				        {id:"inmoney",lable:"金额",type:"text",maxlength:"20"},
				      	{id:"collvote",lable:"领投",optionlist:[{id:'1',text:'否'},{id:'0',text:'是'}],type:"select"},
				      	{id:"subpay",lable:"分期",optionlist:[{id:'1',text:'否'},{id:'0',text:'是'}],type:"select"},
				      	{id:"ongam",lable:"对赌",optionlist:[{id:'1',text:'否'},{id:'0',text:'是'}],type:"select"}
				      ],
				      submit: function () {
				    	  var investment= selectinvestment.data.investmentcode;//$("#investment").find("option:selected").attr("id");
				    	  var investmentName=$("#investment").val().trim();
				    	  var investor=selectinvestor.data.investorcode;//$("#investor").find("option:selected").attr("id");
				    	  var investorName=$("#investor").val().trim();
				    	  var collvote=$("#collvote").find("option:selected").attr("id");
				    	  var ongam=$("#ongam").find("option:selected").attr("id");
				    	  var subpay=$("#subpay").find("option:selected").attr("id");
				    	  var inmoney=$("#inmoney").val().trim();
				    	  var bool=0;
				    	  if(tradeInfoList.length>0&&tradeInfoList[0]!=null){
				    		  for ( var i = 0; i < tradeInfoList.length; i++) {
									if(tradeInfoList[i].base_trade_code==tradeCode&&tradeInfoList[i].base_investment_code==investment){
										bool=1;
										break;
									}
					    	  }
				    	  }
				    	  if(bool==1){
				    		  inputlsit_edit.close();
				    		  $.showtip("当前投资机构已存在");
				    	  }else if(investment==""&&investmentName==""){
				    		  $.showtip("投资机构不能为空");
				    	  }else{
				    		  addAjax(investment,investmentName,investor,investorName,collvote,ongam,subpay,inmoney);
				    		  inputlsit_edit.close();
				    	  }
				    	  setTimeout("$.hidetip()", 2000);
				      }
				  });
				investmentClick();
				investorClick();
				}else{
					$.showtip("未发现交易信息");
				}
			setTimeout("$.hidetip()",2000);
		});
	};
	
	var investmentClick=function(){
		$("#investment").unbind().bind("click",function(e){
			selectinvestment.config();
		});
	};
	
	var investorClick=function(){
		$("#investor").unbind().bind("click",function(e){
			if($("#investment").val().trim()!=""){
				selectinvestor.config();
			}else{
				$.showtip("请先选择投资机构");
				setTimeout("$.hidetip()",2000);
			}
			
		});
	};
	
	//注册融资信息更多图标事件
	var moreTrade=function(){
	    $(".tradeMore").click(function(){
	    	if(tradeCode!=null && tradeCode!=""){
	    		refBool=false;//代表子页请求
		    	tradePageAjax(0);//分页请求
	            initchildpage();//初始化子页
	    	}else{
	    		$.showtip("未发现交易信息");
				setTimeout("$.hidetip()", 2000);
	    	}
	    	
	    });
	};
	
	
	var moreBack=function(){
		//融资子页返回
	    $(".tradeReturn").click(function(){
	    	if(tradetable!=null){
		    	tradetable.hidepage();
	    	}
	    	tradetable=null;
	    	refBool=true;
//	    	tradePageAjax(0);
//            initTrade();
	    });
	};
	
	//融资信息分页控件
	var pageTradeList=function(){
		$('.tradePageaction').jqPagination({
	        link_string	: '/?page={page_number}',
	        max_page	: tradepage.totalPage,
            current_page: tradepage.pageCount,
	        paged		: function(p) {
	            if(p!=tradepage.pageCount){
	            	tradePageAjax(p);
	            }
	        }
	    });
	};
	
	//分页请求查询
	var tradePageAjax=function(vP){
		$.showloading();//等待动画
		$.ajax({
    		url:"findPageTradeInvesInfo.html",
    		type:"post",
    		async:false,
    		data:{
    			tradeCode:tradeCode,
    			pageCount:vP,
    			logintype:cookieopt.getlogintype()
    			},
    		dataType: "json",
    		success: function(data){
    			$.hideloading();//隐藏等待动画
    			if(data.message=="success"){
    				tradepage=data.page;
                    tradeInfoList=data.list;
                    $(".tradeLable").html("共"+data.page.totalCount+"条");
                    refChildPage();//渲染子页
    			}else{
    				$.showtip(data.message);
    			}
    			setTimeout("$.hidetip()",2000);
    		}
    	});
	};

    //添加融资信息提交
    var addAjax=function(investment,investmentName,investor,investorName,collvote,ongam,subpay,inmoney){
        $.showloading();//等待动画
        $.ajax({
            url:"insertTradeInvesInfo.html",
            type:"post",
            async:false,
            data:{
            	orgName:investmentName,
            	investor:investorName,
//                pageSize:tradepage.pageSize,
                base_trade_code:tradeCode,
                base_investment_code:investment,
                base_investor_code:investor,
                base_trade_collvote:collvote,
                base_trade_inmoney:inmoney,
                base_trade_ongam:ongam,
                base_trade_subpay:subpay,
                logintype:cookieopt.getlogintype(),
                version:version
            },
            dataType: "json",
            success: function(data){
                $.hideloading();//等待动画隐藏
                if(data.message=="success"){
                    $.showtip("保存成功");
                    tradeInfoList=data.list;
                    version=data.version;
                    tradepage=data.page;//重新获取分页
                    initTrade();
                }else{
                    $.showtip(data.message);
                }
            }
        });
    };

    //删除融资信息提交
    var delAjax=function(vId){
    	var orgName,investor,money;
    	for ( var i = 0; i < tradeInfoList.length; i++) {
			if(tradeInfoList[i].base_investment_code==vId){
				orgName=tradeInfoList[i].base_investment_name;
				investor=tradeInfoList[i].base_investor_name;
				money=tradeInfoList[i].base_trade_inmoney;
				break;
			}
		}
        $.showloading();//等待动画
        $.ajax({
            url:"deleteTradeInvesInfo.html",
            type:"post",
            async:false,
            data:{
                refBool:refBool,
                orgName:orgName,
                investor:investor,
                money:money,
                orgCode:vId,
                tradeCode:tradeCode,
                pageCount:tradepage.pageCount,
//                pageSize:tradepage.pageSize,
                logintype:cookieopt.getlogintype(),
                version:version
            },
            dataType: "json",
            success: function(data){
                $.hideloading();//等待动画隐藏
                if(data.message=="success"){
                    $.showtip("删除成功");
                    tradeInfoList=data.list;
                    version=data.version;
                    tradepage=data.page;
                    $(".tradeLable").html("共"+data.page.totalCount+"条");
                    if(tradepage.pageCount>1){
                        refChildPage();//渲染融资信息子页
                    }else{
                        refChildPage();
                        initTrade();//渲染融资信息
                    }

                }else{
                    $.showtip(data.message);
                }
                setTimeout("$.hidetip()",2000);
            }
        });
    };
	
	return {
		tradeInfoLoad:initTrade,
		tradeInvesAddClick:addClick,
		tradeMoreClick:moreTrade
	};
})();

var updlogconfig=(function(){
	var upddata={
			updlogtable:{},
			list:[],
			updlogpage:{}
	};
	var initconfig=function(){
		$(".updlogBtn").unbind().bind('click',function(){
			upddata.updlogpage={pageCount:1,totalPage:1};
			updlogAjax();
			returnClick();
		});
	};
	
	var rendering=function(){
		var html=table_data.updlogListNull;
    	if(upddata.list.length>0 && upddata.list[0]!=null){
    		html=template.compile(table_data.updlogList)(upddata);
    		$(".pageaction").removeClass("display-none").addClass("display-block");
    	}else{
    		$(".pageaction").removeClass("display-block").addClass("display-none");
    	}
    	$("#pageUpdlogBody tbody").html(html);

    	upddata.updlogtable=$("#updlogPage").showpage();
    	pageContoller();
	};
	
	var pageContoller=function(){
		//分页控件
		$('.updlogPageaction').jqPagination({
	        link_string	: '/?page={page_number}',
	        max_page	: upddata.updlogpage.totalPage,
	        current_page: upddata.updlogpage.pageCount,
	        paged		: function(p) {
	            if(p!=upddata.updlogpage.pageCount){
	            	upddata.updlogpage.pageCount=p;
	            	updlogAjax();
	            }
	        }
	    });

	};
	
	var updlogAjax=function(){
		$.showloading();//等待动画
		$.ajax({
			url:"findOrgUpdlogInfoByCode.html",
			type:"post",
			async:false,
			data:{
				type:'Lable-trading',
				code:tradeCode,
				pageCount:upddata.updlogpage.pageCount,
				logintype:cookieopt.getlogintype()},
			dataType: "json",
			success: function(data){
				$.hideloading();//隐藏等待动画
				if(data.message=="success"){
					upddata.updlogpage=data.page;
					upddata.list=data.list;
					$(".updlogLable").html("共"+data.page.totalCount+"条");
					rendering();
				}else{
					$.showtip(data.message);
				}
				setTimeout("$.hidetip()",2000);
			}
		});
	};
	
	var returnClick=function(){
		$(".updlogReturn").click(function(){
	    	upddata.updlogtable.hidepage();
	    });
	};
	
	return {
		initUpdlog:initconfig
	};
})();

//备注
var tradeNoteconfig=(function(){
	//初始加载数据
	var init=function(){
			rendering();
			more();
	};
	
	//渲染投资备注
	var rendering=function(){
		var html=table_data.noteListNull;
		if(notedata.list.length>0&&notedata.list[0]!=null){
			html=template.compile(table_data.noteList)(notedata);
		    if(html==""){
		    	html=table_data.noteListNull;
		    }
		}
		$("#noteList tbody").html(html);
		if(CommonVariable().NOTECONFIGNUM<notedata.list.length){
            $(".closeshearch").show();
            if($(".closeshearch").hasClass("active")){
            	$("[nodemore]").show();
	            $(this).html('收起<span class="glyphicon glyphicon-chevron-up "></span>');
            }else{
           	 	$("[nodemore]").hide();
            	$(".closeshearch").html('更多<span class="glyphicon glyphicon-chevron-down "></span>');
            }
           
        }else{
        	$(".closeshearch").hide();
        	$(".closeshearch").html('更多<span class="glyphicon glyphicon-chevron-down "></span>').removeClass("active");
        }        
		delClick();
	};
	
	//更多
	var more=function(){
		$(".closeshearch").unbind().bind("click", function () {
	           if(!$(this).hasClass("active")){
	               $("[nodemore]").show();
	               $(this).html('收起<span class="glyphicon glyphicon-chevron-up "></span>').addClass("active");
	           }else{
	               $("[nodemore]").hide();
	               $(this).html('更多<span class="glyphicon glyphicon-chevron-down "></span>').removeClass("active");
	           }
	        });
	};
	//删除备注事件
	var delClick=function(){
		$(".noteDelClick").unbind().bind("click",function () {
			var noteCode=$(this).attr("del-code");
			inputlsit_edit.config({
                title:"提示",//弹层标题
                html:true,//是否以html显示
                besurebtn:"确定",//确定按钮文字
                cancle:"取消",//取消按钮文字 （如果不写，则不会显示该按钮）
                htmltext:"<div style='text-align: center;font-size: 15px;'>您确定要删除该条记录吗？</div>",
                submit: function () {
                	del(noteCode);
                    inputlsit_edit.close();
                },//点击确定按钮回调方法
                canmit: function () {
                    inputlsit_edit.close();
                }//点击取消按钮回调方法
            });
			
		});
	};
	//删除交易备注后台请求
	var del=function(noteCode){
		$.showloading();//等待动画
		$.ajax({
    		url:"deleteTradeNote.html",
    		type:"post",
    		async:false,
    		data:{
        			noteCode:noteCode,
        			tradeCode:tradeCode,
        			logintype:cookieopt.getlogintype()
    			},
    		dataType: "json",
    		success: function(data){
    			$.hideloading();//等待动画隐藏
    			if(data.message=="success"||data.message=="delete"){
                    if(data.message=="success"){
                    	$.showtip("删除成功","success",2000);
                    }else{
                    	$.showtip("数据已被删除","success",2000);
                    }
                	notedata.list=data.list;
                	rendering();
                }else{
                	 $.showtip(data.message);
                }
                setTimeout("$.hidetip()",2000);
    		}
    	});
	};
	
	var addNote=function(){
		$(".submitNote").unbind().bind("click",function(){
			var noteCon=$("#noteContent").val();
			if(tradeCode==null){
				$.showtip("未发现交易信息");
			}else if(noteCon.trim()==""){
				$.showtip("备注内容不能为空");
			}else{
				$.showloading();//等待动画
				$.ajax({
		    		url:"addTradeNote.html",
		    		type:"post",
		    		async:false,
		    		data:{
		    				base_trade_code:tradeCode,
		    				base_tradenote_content:noteCon,
		        			logintype:cookieopt.getlogintype()
		    			},
		    		dataType: "json",
		    		success: function(data){
		    			$.hideloading();//等待动画隐藏
		    			if(data.message=="success"){
		                	$.showtip("保存成功");
		                	notedata.list=data.list;
		                	rendering();
		                	$("#noteContent").val("");
		                }else{
		                	$.showtip(data.message);
		                }
		    		}
		    	});
			}
			 setTimeout("$.hidetip()",2000);
			
		});
	};
	
	return{
		tradeNoteLoad:init,
		tradeNoteSubmit:addNote
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
            pageSize:CommonVariable().PAGESIZE,
            totalCount:1
        };
        opt.firstload=true;
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
                investment_ajax(true);
                opt.dropload= WCsearch_list.dropload({
                    loadDownFn: function (me) {
                        if(Number(opt.page.pageCount)<Number(opt.page.totalPage)){
                            opt.page.pageCount=opt.page.pageCount+1;
                            investment_ajax(false);
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
            },
            addclick: function () {
				var data = {name: '机构简称'};  
				var addcomphtml=template.compile(table_data.addcomp)(data);
				$(".inner").html("<div class='lists'></div>");
				$(".lists").html(addcomphtml);

                $(".addcompinvestname").val(savedata.searchorg);

				$("#addcompinvet").unbind().bind("click", function() {
					if(""!=$(".addcompinvestname").val().trim()){
						 var bool=0;
				    	  if(tradeInfoList.length>0){
				    		  for ( var i = 0; i < tradeInfoList.length; i++) {
									if(tradeInfoList[i].base_investment_name==$(".addcompinvestname").val().trim() ){
										bool=1;
										break;
									}
					    	  }
				    	  }
				    	  if(bool==1){
				    		  $.showtip("当前添加的机构已存在");
				    		  setTimeout(function () {$.hidetip();},2000);
				    	  }else{
				    		  invementadd_ajax();
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
    //添加投资机构
    var invementadd_ajax = function() {
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
				if (data.message == "nodata") {
			        $("#investment").val($(".addcompinvestname").val().trim());
			        opt.investmentcode="";
			        selectinvestor.data.investorcode="";
                    $("#investor").val("");
                    
					WCsearch_list.closepage();
				}else if(data.message == "exsit"){
					$.showtip("机构简称已存在");
					setTimeout(function() {$.hidetip();}, 2000);
				
				}else {
					$.showtip("添加异常");
					setTimeout(function() {$.hidetip();}, 2000);
				}
				$.hideloading();
			}
		});
	};
	
    var investment_ajax= function (vBool) {
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

                savedata.searchorg=$(".select-page-input").val().trim();

                if(data.message=="success"){
                	opt.page=data.page;
                	var html=table_data.investmentlistNull;
                	if(data.list.length>0){
                		html=template.compile(table_data.investmentlist)(data);
                		if(vBool==true){
                			$(".lists").html(html);
                		}else{
                			$(".lists").append(html);
                		}
                        opt.dropload.resetload();
                        $(".item").unbind().bind("click",function () {
                            $("#investment").val($(this).html());
                            opt.investmentcode=$(this).attr('data-com-code');
                            selectinvestor.data.investorcode="";
                            $("#investor").val("");
                            
                            WCsearch_list.closepage();
                        });
                	}else{
                		//20151224 RQQ ModStart
//                		$(".lists").html(html);
                		$(".lists").html("");
                		$.showtip("暂无数据");
                		//20151224 RQQ ModEnd
                	}
                	
                }else if(data.message=="nomore"){
                	$.showtip("暂无数据");
                }else{
                    $.showtip(data.message);
                }
                setTimeout("$.hidetip()",2000);
            }
        });
    };
    
    return {
        config:config,
        data:opt,
        searchdata:savedata
    };
})();

//投资人选择界面
var selectinvestor=(function () {
    var opt={investorcode:""};

    var savedata={
        searchinv:""
    };

    var dataconfig= function () {
        opt.page={
            pageCount:1,
            pageSize:CommonVariable().PAGESIZE,
            totalCount:1
        };
        opt.firstload=true;
        $(".inner").html("<div class='lists'></div>");
        $(".itemnocare").unbind().bind("click",function () {
            $("#investor").val("");
            opt.investorcode="";
            WCsearch_list.closepage();
        });
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
                investor_ajax(true);
                opt.dropload= WCsearch_list.dropload({
                    loadDownFn: function (me) {
                        if(Number(opt.page.pageCount)<Number(opt.page.totalPage)){
                            opt.page.pageCount=opt.page.pageCount+1;
                            investor_ajax(false);
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
            },
			addclick: function () {
				var data = {name: '投资人姓名'};  
				var addcomphtml=template.compile(table_data.addcomp)(data);
				$(".inner").html("<div class='lists'></div>");
				$(".lists").html(addcomphtml);

                $(".addcompinvestname").val(savedata.searchinv);

				$("#addcompinvet").unbind().bind("click", function() {
					if(""!=$(".addcompinvestname").val().trim()){
						$("#investor").val($(".addcompinvestname").val().trim());
					    opt.investorcode=""; 
					}
					WCsearch_list.closepage();
				});
			}
            
        });
        dataconfig();
    };
    var investor_ajax= function (vBool) {
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "queryinvestorlistByinvementcode.html",
            data: {
                name:$(".select-page-input").val(),
                invementcode:selectinvestment.data.investmentcode,
                pageSize:opt.page.pageSize,
                pageCount:opt.page.pageCount,
                logintype:cookieopt.getlogintype()
            },
            success: function (data) {
            	$.hideloading();

                savedata.searchinv=$(".select-page-input").val().trim();

                if(data.message=="success"){
                	opt.page=data.page;
                	var html=table_data.investorlistNull;
                	if(data.list.length>0){
                		html=template.compile(table_data.investorlist)(data);
                		if(vBool==true){
                            $(".inner").html("<div class='lists'></div>");
                            $(".itemnocare").unbind().bind("click",function () {
                                $("#investor").val("");
                                opt.investorcode="";
                                WCsearch_list.closepage();
                            });
                		}
                			$(".lists").append(html);
                        opt.dropload.resetload();
                        $(".item").unbind().bind("click",function () {
                            $("#investor").val($(this).html());
                            opt.investorcode=$(this).attr('data-investor-code');
                            
                            WCsearch_list.closepage();
                        });
                	}else{
                		$(".lists").html(html);
                	}
                	
                }else if(data.message=="nomore"){
                	$(".lists").html("");
                	$.showtip("暂无数据");
                }else{
                	$(".lists").html("");
                    $.showtip(data.message);
                }
                setTimeout("$.hidetip()",2000);
                
            }
        });
    };
    return {
        config:config,
        data:opt,
        searchdata:savedata
    };
})();


//删除交易
var delTradeconfig=(function(){
	var click=function(){
		$(".delTradeClick").unbind().bind("click",function(){
            if(tradeCode==null){
                $.showtip("未发现交易信息");
                setTimeout("$.hidetip()",2000);
                return;
            }
            inputlsit_edit.config({
                title:"提示",//弹层标题
                html:true,//是否以html显示
                besurebtn:"确定",//确定按钮文字
                cancle:"取消",//取消按钮文字 （如果不写，则不会显示该按钮）
                htmltext:"<div style='text-align: center;font-size: 15px;'>您确定要删除该条记录吗？</div>",
                submit: function () {
                    del();
                    inputlsit_edit.close();
                },//点击确定按钮回调方法
                canmit: function () {
                    inputlsit_edit.close();
                }//点击取消按钮回调方法
            });
		});
		
	};

    var del=function(){
        $.showloading();//等待动画
        $.ajax({
            url:"deleteTradeInfo.html",
            type:"post",
            async:false,
            data:{
                tradeCode:tradeCode,
                version:version,
                orgCodeString:tradeInfo.view_investment_code,
                tradeDate:tradeInfo.base_trade_date,
                companyName:tradeInfo.base_comp_name,
                logintype:cookieopt.getlogintype()
            },
            dataType: "json",
            success: function(data){
                $.hideloading();//等待动画隐藏
                if(data.message=="success"){
                    $.showtip("删除成功");
                    setTimeout(function(){
                        window.location.href="trade_search.html?logintype="+cookieopt.getlogintype();
                    },2000);

                }else{
                        $.showtip(data.message);
                }
                setTimeout("$.hidetip()",2000);
            }
        });
    };

    return {
        click:click
    };
})();

var table_data={
	noteList:'<%for(var i=0;i<list.length;i++){%>'+
	'<tr <%=nodermore(i,initSize)%> >'+
	'<td><%=dateFormat(list[i].createtime.time,"yyyy-MM-dd")%></td>'+
	'<td><%=list[i].sys_user_name%></td>'+
	'<td><pre><%=list[i].base_tradenote_content%></pre></td>'+
	'<td><button class="btn btn-default smart noteDelClick" i="<%=i%>" del-code="<%=list[i].base_tradenote_code%>">删除</button></td></tr>'+
	'<%}%>',
	noteListNull:'<tr><td colspan="4">暂无数据</td></tr>',
	tradeInvesDetail:'<li><span class="lable">投资机构:</span><span class="in" style="text-align:left;padding-left: 15px;"><%=base_investment_name%></span></li>' +
    '<li><span class="lable">投资人:</span><span class="in"><input readonly onfocus="this.blur();" id="investor" value="<%=base_investor_name%>" class="inputdef" ></span></li>' +
    '<li><span class="lable">金额:</span><span class="in"><input id="inmoney" value="<%=base_trade_inmoney%>" class="inputdef" ></span></li>' +
    '<li><span class="lable">领投:</span><span class="in"><select class="inputdef" id="collvote"> <option value="1">否</option><option  value="0">是</option></select></span></li>' +
    '<li><span class="lable">分期:</span><span class="in"><select class="inputdef" id="subpay"> <option value="1">否</option><option  value="0">是</option></select></span></li>' +
    '<li><span class="lable">对赌:</span><span class="in"><select class="inputdef" id="ongam"> <option value="1">否</option><option  value="0">是</option></select></span></li>',
	tradeInvesList:'<%for(var i=0;i<list.length;i++){%>'+
	'<tr id="<%=list[i].base_investment_code%>">'+
	'<td><%=list[i].base_investment_name%></td>'+
	'<td><%=list[i].base_investor_name%></td>'+
	'<td><%=list[i].base_trade_inmoney%></td>'+
	'<td><button class="btn btn-default smart tradeInfoDelClick" i="<%=i%>" del-code="<%=list[i].base_investment_code%>">删除</button></td></tr>'+
	'<%}%>',
	tradeInvesListNull:'<tr><td colspan="4">暂无数据</td></tr>',
	labelInfoList:'<%for(var i=0;i<list.length;i++){%>'+
	'<li data-i="<%=i%>"><%=list[i].name%></li>'+
	'<%}%>',
	labelNull:'<li>暂无数据</li>',
	investmentlist:'<% for (var i=0;i<list.length;i++ ){%>' +
    '<div data-com-code="<%=list[i].base_investment_code%>" class="item"><%=list[i].base_investment_name %></div>' +
    '<%}%>',
    investmentlistNull:'<div class="item">暂无数据</div>',
    investorlist:'<% for (var i=0;i<list.length;i++ ){%>' +
    '<div data-investor-code="<%=list[i].base_investor_code%>" '
			+'class="item"><%=list[i].base_investor_name %></div>' +
			'<%}%>',
	investorlistNull:'<option id="">暂无投资人</option>',
    
	contentNull:'暂无数据',
	companylist:'<% for (var i=0;i<list.length;i++ ){%>' +
    '<div data-com-code="<%=list[i].base_comp_code%>" class="item"><%=list[i].base_comp_name %></div>' +
    '<%}%>',
    investorSelectList:'<% for (var i=0;i<list.length;i++ ){%>' +
    '<option id="<%=list[i].id%>"><%=list[i].text%></option>'+
    '<%}%>',
    investorSelectListNull:'<option id="">暂无投资人</option>',
    investorSelectClear:'<option id="">请选择投资机构</option>',
    updlogList:'<%for(var i=0;i<list.length;i++){%>'+
	'<tr><td><%=dateFormat(list[i].updtime.time,"yyyy-MM-dd")%></td>'+
	'<td><%=list[i].sys_user_name%></td>'+
	'<td><%=list[i].base_updlog_opercont%></td>'+
	'<td><%=list[i].base_updlog_oridata==""?"":list[i].base_updlog_oridata%>'+
	'<%=list[i].base_updlog_newdata==""?"":list[i].base_updlog_newdata%></td></tr>'+
	'<%}%>',
	updlogListNull:'<tr><td colspan=\'4\'>暂无数据</td></tr>',
	addcomp:	'<div class="shgroup" style="margin-top:10px;">'
	    +' <div class="title no-border"><%=name%>:</div>' 
	    +' <div class="tiplist no-border" style="padding-right:10px;">' 
	    +' <input type="text" class="inputdef inputdef_l addcompinvestname" maxlength="20">' 
	    +'</div>' 
	    +'<div style="display:table-cell">'
	    +'<button class="btn btn-default smart" id="addcompinvet" >'
	    	+'确定</button></div></div>'
};


//判断note显示行数
template.helper("nodermore", function (a,b) {
    if(a<b){
        return "";
    }else{
        return "nodemore";
    }
});
