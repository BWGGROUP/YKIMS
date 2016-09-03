/**
 * Created by rqq on 15-11-02.
 */
var baskJson;// 选择筐
var induJson;// 选择行业
var stagelist;// 阶段
var stageJson;// 选择阶段
var tradeCode;// 交易Code
var stageCode;// 阶段code
var stageCont;// 阶段
var companyCode;// 被投公司code
var companyName;// 被投公司简称
var tradeDate;// 投资日期
var tradeMoney;// 融资金额
var companyMoney;// 公司估值
var tradeId;// 交易ID
var tradeInfoList=[];//融资信息
$(function() {
	if (viewCompInfo != null && "" != viewCompInfo) {
		// 关注筐
		baskJson = eval(viewCompInfo.view_comp_baskcont);
		// 行业
		induJson = eval(viewCompInfo.view_comp_inducont);

		// 被投公司code
		companyCode = viewCompInfo.base_comp_code;
		// 被投公司简称
		companyName = viewCompInfo.base_comp_name != null ? viewCompInfo.base_comp_name: "";
	}
	
	//2015-11-30 TASK078 RQQ AddStart
    //初始化融资信息
      if (baseinvestmentinfo != null && "" != baseinvestmentinfo) {
         var tradeinfo={};
         					//机构code
          tradeinfo.base_investment_code=baseinvestmentinfo.base_investment_code;
          				//机构名称
          tradeinfo.base_investment_name=baseinvestmentinfo.base_investment_name;
          				//机构类型
          tradeinfo.investmenttype="select";
          			//投资人code
          tradeinfo.base_investor_code="";
          				//投资人姓名
          tradeinfo.base_investor_name="";
          				//投资人类型
          tradeinfo.investortype="select";
          				//交易金额
          tradeinfo.base_trade_inmoney="";
          				//是否领投
          tradeinfo.base_trade_collvote='1';
         					//是否对赌
         tradeinfo.base_trade_ongam='1';
         				//是否分次付款
         tradeinfo.base_trade_subpay='1';
          if(viewInvestorInfo){
              //投资人code
              tradeinfo.base_investor_code=viewInvestorInfo.base_investor_code;
              //投资人姓名
              tradeinfo.base_investor_name=viewInvestorInfo.base_investor_name;
          }
         tradeInfoList.push(tradeinfo);

      }
      //初始化返回信息
      if(backherf != null && "" != backherf && "null"!=backherf ){
      	$("#returnbutton").css("display","");
      		}
    //2015-11-30 TASK078 RQQ AddEnd
	// 初始化
	init();

});

function init() {
	// 被投公司
	labelconfig.companyLoad();
	// 交易时间
	labelconfig.dateLoad();
	// 加载关注筐
	labelconfig.daskLoad();
	// 加载行业
	labelconfig.induLoad();
	// 加载阶段
	labelconfig.stageLoad();
	// 加载融资金额
	labelconfig.tradeMoneyLoad();
	// 加载公司估值
	labelconfig.companyMoneyLoad();
	// 融资信息
	tradeInfoconfig.tradeInfoLoad();
	// 添加融资信息
	tradeInfoconfig.tradeInvesAddClick();
	//提交按钮
	addtradeclick();
	
	//2015-11-30 TASK078 RQQ AddStart
    //返回按钮
    $("#returnbutton").unbind().bind("click", function ()  {
    	 if(backherf != null && "" != backherf && "null"!=backherf ){
    		 window.location.href=backherf+"&logintype="+cookieopt.getlogintype();
         		}
    	
   });
 //2015-11-30 TASK078 RQQ AddEnd

}

// 标签弹出层集合
function dicListConfig(vDel, vList, vObj) {
	var list = [];
	var map = {};
	for (var i = 0; i < vList.length; i++) {
		map = {};
		map.name = vList[i].sys_labelelement_name;
		map.id = vList[i].sys_labelelement_code;
		if (vObj != null) {
			for (var j = 0; j < vObj.length; j++) {
				// 判断标签是否被选中,存在则加上选中标识
				if (vList[i].sys_labelelement_code == vObj[j].code) {
					map.select = true;
				}
			}
		}
		list.push(map);
	}
	return list;
}

// 弹层选择筐选择值,返回[{code:'',name:''}]
function choiceContent() {
	var list = "[";
	$(".list li[class='active']").each(
			function() {
				list += "{code:'" + $(this).attr("tip-l-i") + "',name:'"
						+ $(this).text() + "'},";
			});
	list += "]";
	return list;
}

// 控制加载更多按钮的方法
function checktip_havemore() {
	$("ul[ro]").each(function() {
		if ($(this)[0].scrollHeight > $(this)[0].clientHeight) {
			$(this).parents(".shgroup").addClass("havemore");
		} else {
			$(this).parents(".shgroup").removeClass("havemore");
		}

	});
	$(".havemore .more").unbind().bind("click", function() {
		showmore($(this));
	});

}

// 操作标签更多
function showmore(a) {
	if ($(a).parents(".shgroup").hasClass("heightauto")) {
		$(a).parents(".shgroup").removeClass("heightauto");
		$(a).html('<span class="glyphicon glyphicon-chevron-down" ></span>');
	} else {
		$(a).parents(".shgroup").addClass("heightauto");
		$(a).html('<span class="glyphicon glyphicon-chevron-up" ></span>');

	}
}
// 刷新操作标签
function refreshladle(a) {
	var shgroup = $(a).parents(".shgroup");
	if (shgroup.hasClass("heightauto")) {
		shgroup.removeClass("heightauto");
		if ($(a)[0].scrollHeight > $(a)[0].clientHeight) {
			if (!shgroup.hasClass("havemore")) {
				shgroup.addClass("havemore");
			}
		} else {
			shgroup.removeClass("havemore");
		}
		shgroup.addClass("heightauto");
	} else {
		if ($(a)[0].scrollHeight > $(a)[0].clientHeight) {
			if (!shgroup.hasClass("havemore")) {
				shgroup.addClass("havemore");
			}
		} else {
			shgroup.removeClass("havemore");
		}
	}
	$(".havemore .more").unbind().bind("click", function() {
		showmore($(this));
	});
}

var labelconfig = (function() {
	// 加载被投公司
	var initCompany = function() {
		var html = table_data.contentNull;
		if (companyName != null && companyName != "") {
			html = companyName;
		}
		$(".company").val(html);
		if(companyCode!=null && companyCode!=""){
		$(".company").attr("opertype","select");
		$(".company").attr("compcode",companyCode);
		}
		companyClick();
	};

	// 被投公司点击事件
	var companyClick = function() {
		$(".company").bind("click", function() {
			selectcompany.config();
		});
	};

	// 加载交易时间
	var initTradeDate = function() {
		// 加载时间
		var html = table_data.contentNull;
		if (tradeDate != null && tradeDate != "") {
			html = tradeDate;
		}
		$("#tradeDate").html(html);
		tradeDateClick();
	};

	// 加载交易时间单击事件
	var tradeDateClick = function() {
		$(".tradeDateClick").bind(
						"click",
						function() {
							var htmltext = '<span class="lable">投资日期:</span><span class="in"><input id="base_trade_date" type="date" class="inputdef"></span>';
							inputlsit_edit.config({
								title : "投资日期",
								html : true,
								htmltext : htmltext,
								submit : function() {
									inputlsit_edit.close();
								}
							});
							$("#base_trade_date").val(tradeDate);
						});

	};

	// 初始加载关注筐
	var initDask = function() {
		var html = "";
		if (baskJson != null && baskJson != "") {
			var data = {
				list : baskJson
			};
			html = template.compile(table_data.labelInfoList)(data);
		} else {
			html = table_data.labelNull;
		}
		$("#ul-bask").html(html);
		daskClick();
	};
	// 关注筐事件
	var daskClick = function() {
		var basklist = dicListConfig(true, baskList, baskJson);
		$("#kuangid").unbind().bind("click", function() {
			var $this = $(this);
			listefit.config({
				title : "筐",
				list : basklist,
				radio : false,
				besure : function() {
					baskJson = eval(choiceContent());
					initDask();
				}
			});
		});
	};

	// 行业初始化
	var initIndu = function() {
		if (induJson != null && induJson != "") {
			var data = {
				list : induJson
			};
			html = template.compile(table_data.labelInfoList)(data);
		} else {
			html = table_data.labelNull;
		}
		$("#ul-indu").html(html);
		induClick();
	};
	// 行业点击事件
	var induClick = function() {
		// 行业弹层
		var indulist = dicListConfig(false, induList, induJson);
		$("#induid").unbind().bind("click", function() {
			var $this = $(this);
			listefit.config({
				title : "行业",
				list : indulist,
				radio : false,
				besure : function() {
					induJson = eval(choiceContent());
					initIndu();

				}

			});
		});
	};

	// 加载阶段
	var initStage = function() {
		var html = table_data.contentNull;
		if (stageJson != null && stageJson != "") {
			var data = {
				list : stageJson
			};
			html = template.compile(table_data.labelInfoList)(data);
		} else {
			html = table_data.labelNull;
		}

		$("#ul-stage").html(html);
		stageClick();
	};

	var stageClick = function() {
		var stagelist = dicListConfig(false, stageList, stageJson);
		// 阶段弹层
		$("#stageid").unbind().bind("click", function() {
			listefit.config({
				title : "阶段",
				list : stagelist,
				radio : true,
				besure : function() {
					stageJson = eval(choiceContent());
					initStage();
				}
			});
		});
	};

	// 加载融资额
	var initTradeMoney = function() {
		var html = table_data.contentNull;
		if (tradeMoney != null && tradeMoney != "") {
			html = tradeMoney;
		}
		$("#tradeMoney").html(html);
		tradeMoneyClick();
	};

	// 融资金额单击事件
	var tradeMoneyClick = function() {
		$(".tradeMoneyClick").bind("click", function() {
			inputlsit_edit.config({
				title : "融资金额",
				list : [ {
					id : "newTradeMoney",
					lable : "融资金额"
				} ],
				submit : function() {
					inputlsit_edit.close();
				}
			});
			$("#newTradeMoney").val(tradeMoney);
		});

	};

	// 加载公司估值
	var initCompanyMoney = function() {
		var html = table_data.contentNull;
		if (companyMoney != null && companyMoney != "") {
			html = companyMoney;
		}
		$("#companyMoney").html(html);
		companyMoneyClick();
	};

	// 公司估值单击事件
	var companyMoneyClick = function() {
		$(".companyMoneyClick").bind("click", function() {
			inputlsit_edit.config({
				title : "公司估值",
				list : [ {
					id : "newCompanyMoney",
					lable : "公司估值"
				} ],
				submit : function() {
					inputlsit_edit.close();
				}
			});
			$("#newCompanyMoney").val(companyMoney);
		});
	};

	return {
		companyLoad : initCompany,
		dateLoad : initTradeDate,
		daskLoad : initDask,
		induLoad : initIndu,
		stageLoad : initStage,
		tradeMoneyLoad : initTradeMoney,
		companyMoneyLoad : initCompanyMoney
	};
})();

var tradeInfoconfig = (function() {

	var initTrade = function() {
		var html = table_data.tradeInvesListNull;
		if(tradeInfoList[0]!=null){
			html=template.compile(table_data.tradeInvesList)({list:tradeInfoList});
			if(html==""){
				html=table_data.tradeInvesListNull;
			}
		}
		$("#tradeInfo tbody").html(html);
		if(tradeInfoList.length>0&&tradeInfoList[0]!=null){
		click();// 融资信息选中行事件
		delClick();// 删除按钮事件
		}
	};

	// 融资信息选中行事件
	var click = function() {
		$("#tradeInfo tbody tr").bind("click", function() {
			var id = $(this).attr("p-index");
			//2015-11-30 TASK078 RQQ ModStart
//			tradedetial(id);
			var id = $(this).attr("p-index");
			if(tradeInfoList.length>0 && tradeInfoList[0]!=null && tradeInfoList[id]!=null){
			inputlsit_edit.config({
			      title:"编辑融资信息",
			      list:[
			         {id:"investment",lable:"投资机构",type:"span",value:tradeInfoList[id].base_investment_name},
				      	{id:"investor",lable:"投资人",type:"text",readonly:"readonly"},
				      	{id:"inmoney",lable:"金额",type:"text",maxlength:"20"},
				      	{id:"collvote",lable:"领投",optionlist:[{id:'1',text:'否'},{id:'0',text:'是'}],type:"select"},
				      	{id:"subpay",lable:"分期",optionlist:[{id:'1',text:'否'},{id:'0',text:'是'}],type:"select"},
				      	{id:"ongam",lable:"对赌",optionlist:[{id:'1',text:'否'},{id:'0',text:'是'}],type:"select"}
			      				],
		        besurebtn:"保存",
		        cancle:"删除",
			    complete: function () {
	       	  //机构code
	    		  $("#investment").attr("investmentcode",tradeInfoList[id].base_investment_code);
	    		  //机构name
//	    		  $("#investment").val(tradeInfoList[id].base_investment_name);
	    		  //机构添加类型
	    		  $("#investment").attr("operatype",tradeInfoList[id].investmenttype);
	    		  //投资人code
	    		  $("#investor").attr("investorcode",tradeInfoList[id].base_investor_code);
	    		  //投资人name
	    		  $("#investor").val(tradeInfoList[id].base_investor_name);

                  selectinvestor.searchdata.searchinv=tradeInfoList[id].base_investor_name;

	    		  //投资人类型
	    		  $("#investor").attr("operatype",tradeInfoList[id].investortype);

	    		  //金额
	    		  $("#inmoney").val(tradeInfoList[id].base_trade_inmoney);
	    		  //领投
	    		  $("#collvote").children("#"+tradeInfoList[id].base_trade_collvote).attr("selected","selected");
	    		  //分期
	    		  $("#subpay").children("#"+tradeInfoList[id].base_trade_subpay).attr("selected","selected");
	    		  //对赌
	    		  $("#ongam").children("#"+tradeInfoList[id].base_trade_ongam).attr("selected","selected");
//					investmentClick();
					investorClick();
	  					
			      },
			      submit: function () {
			    	  var bool=0;
			    	  if(tradeInfoList.length>0){
//			    		  for ( var i = 0; i < tradeInfoList.length; i++) {
//								if(tradeInfoList[i].base_investment_code==$("#investment").attr("investmentcode") 
//										&& "select"==tradeInfoList[i].investmenttype
//										&& i!=id){
//									bool=1;
//									break;
//								}
//				    	  }
			    	  
//			    	  if(bool==1){
//			    		  $.showtip("当前投资机构已存在");
//			    		  setTimeout(function () {$.hidetip();},2000);
//			    		  return;
//			    	  		}else{
			    		  var tradeinfo=new Object();
			    		  //机构code
			    		  tradeinfo.base_investment_code=$("#investment").attr("investmentcode");
			    		  //机构name
			    		  tradeinfo.base_investment_name=$("#investment").text();
			    		  //机构添加类型
			    		  tradeinfo.investmenttype=$("#investment").attr("operatype");
			    		  //投资人code
			    		  tradeinfo.base_investor_code=$("#investor").attr("investorcode");
			    		  //投资人name
			    		  tradeinfo.base_investor_name=$("#investor").val();
			    		  //投资人类型
			    		  tradeinfo.investortype=$("#investor").attr("operatype");
			    		  //金额
			    		  tradeinfo.base_trade_inmoney=$("#inmoney").val();
			    		  //是否领投
			    		  tradeinfo.base_trade_collvote=$("#collvote").find("option:selected").attr("id");
			    		  //是否对赌
			    		  tradeinfo.base_trade_ongam=$("#ongam").find("option:selected").attr("id");
			    		  //是否分次付款
			    		  tradeinfo.base_trade_subpay=$("#subpay").find("option:selected").attr("id");
			    		  tradeInfoList[id]=tradeinfo;

                          selectinvestor.searchdata.searchinv="";

                          initTrade();
			    		  inputlsit_edit.close();
			    	  }
//			    	  }
			      },
			      canmit: function () {

                      selectinvestor.searchdata.searchinv="";
			    	  tradeInfoList.baoremove(id);
			    	  initTrade();
						  inputlsit_edit.close();
		            }
			  });
			}
			//2015-11-30 TASK078 RQQ ModEnd
		});
	};


	// 融资信息详情展示
	var tradedetial = function(vId) {
		if (tradeInfoList[0] != null) {
			var data = {};
				if (vId<tradeInfoList.length) {
					data = tradeInfoList[vId];
				}
			var htmlText = template.compile(table_data.tradeInvesDetail)(data);
			inputlsit_edit.config({
				title : "融资信息详情",
				html : true,
				htmltext : htmlText,
				submit : function() {
					inputlsit_edit.close();
				}
			});
		}
	};

	// 融资信息删除事件
	var delClick = function() {
		$(".tradeInfoDelClick").unbind().bind(
						"click",
						function(e) {
							var id = $(this).attr("p-index");
							//2015-11-30 TASK078 RQQ ModStart
//							inputlsit_edit
//									.config({
//										title : "提示",// 弹层标题
//										html : true,// 是否以html显示
//										besurebtn : "确定",// 确定按钮文字
//										cancle : "取消",// 取消按钮文字
//														// （如果不写，则不会显示该按钮）
//										htmltext : "<div style='text-align: center;font-size: 15px;'>您确定要删除该条记录吗？</div>",
//										submit : function() {
											tradeInfoList.baoremove(id);
											initTrade();
//											inputlsit_edit.close();
//										},// 点击确定按钮回调方法
//										canmit : function() {
//											inputlsit_edit.close();
//										}// 点击取消按钮回调方法
//									});
							//2015-11-30 TASK078 RQQ ModEnd
							e.stopPropagation();
						});

	};

	//添加融资信息图标事件
	var addClick=function(){
		$(".addbtn").bind("click",function(e){
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
				    	  var bool=0;
				    	  if(tradeInfoList.length>0){
				    		  for ( var i = 0; i < tradeInfoList.length; i++) {
									if(tradeInfoList[i].base_investment_code==$("#investment").attr("investmentcode") && "select"==tradeInfoList[i].investmenttype){
										bool=1;
										break;
									}
					    	  }
				    	  }
				    	  if(bool==1){
				    		  $.showtip("当前投资机构已存在");
				    	  }else if($("#investment").val().trim()==""){
				    		  $.showtip("投资机构不能为空");
				    	  }else{
				    		  var tradeinfo=new Object();
				    		  //机构code
				    		  tradeinfo.base_investment_code=$("#investment").attr("investmentcode");
				    		  //机构name
				    		  tradeinfo.base_investment_name=$("#investment").val();
				    		  //机构添加类型
				    		  tradeinfo.investmenttype=$("#investment").attr("operatype");
				    		  //投资人code
				    		  tradeinfo.base_investor_code=$("#investor").attr("investorcode");
				    		  //投资人name
				    		  tradeinfo.base_investor_name=$("#investor").val();
				    		  //投资人类型
				    		  tradeinfo.investortype=$("#investor").attr("operatype");
				    		  //金额
				    		  tradeinfo.base_trade_inmoney=$("#inmoney").val();
				    		  //是否领投
				    		  tradeinfo.base_trade_collvote=$("#collvote").find("option:selected").attr("id");
				    		  //是否对赌
				    		  tradeinfo.base_trade_ongam=$("#ongam").find("option:selected").attr("id");
				    		  //是否分次付款
				    		  tradeinfo.base_trade_subpay=$("#subpay").find("option:selected").attr("id");

                              selectinvestment.searchdata.searchorg="";
                              selectinvestor.searchdata.searchinv="";

                              tradeInfoList.push(tradeinfo);
			          initTrade();
				    		  inputlsit_edit.close();
				    	  }
				    	  setTimeout(function () {$.hidetip();},2000);
				      },
          complete: function () {
                    	//2015-11-30 TASK078 RQQ ModStart
//                        $("input[readonly]").focus(function(){
//                            $(this).blur();
//                        });
												investmentClick();
												investorClick();
                    	//2015-11-30 TASK078 RQQ ModEnd
                    }
				  });
			
		});
	};
	//融资信息的机构选择
	var investmentClick=function(){
		$("#investment").unbind().bind("click",function(e){
			selectinvestment.config();
		});
	};
	//融资信息的投资人选择
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
		tradeInfoLoad : initTrade,
		tradeInvesAddClick : addClick
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
						};
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
								})
					},
					addclick: function () {
						var data = {name: '公司简称'};  
						var addcomphtml=template.compile(table_data.addcomp)(data);
						$(".inner").html("<div class='lists'></div>");
						$(".lists").html(addcomphtml);

                        $(".addcompinvestname").val(savedata.searchcom);

						$("#addcompinvet").unbind().bind("click", function() {
							if(""!=$(".addcompinvestname").val().trim()){
							companyadd_ajax();
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
					var html = template.compile(table_data.companylist)(data);

					$(".lists").append(html);

					opt.dropload.resetload();
					$(".item").unbind().bind("click", function() {
						$(".company").val($(this).html());
						$(".company").attr("opertype","select");
						$(".company").attr("compcode",$(this).attr('data-com-code'));
						if(($("#ul-bask").html()==null
								|| ""==$("#ul-bask").html().trim()
								|| $("#ul-bask").children("li").html()==null
								|| ""==$("#ul-bask").children("li").html().trim()
								|| "暂无数据"==$("#ul-bask").children("li").html().trim()) 
								&& ($("#ul-indu").html()==null
								|| ""==$("#ul-indu").html().trim() 
								|| $("#ul-indu").children("li").html()==null
								|| ""==$("#ul-indu").children("li").html().trim()
								|| "暂无数据"==$("#ul-indu").children("li").html().trim())){
							// 关注筐data-bask-cont
							baskJson = eval($(this).attr('data-bask-cont'));
							// 行业
							induJson = eval($(this).attr('data-indu-cont'));
							// 加载关注筐
							labelconfig.daskLoad();
							// 加载行业
							labelconfig.induLoad();
						}
						opt.companycode = $(this).attr('data-com-code');
						WCsearch_list.closepage();
					})
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
					$(".company").val($(".addcompinvestname").val().trim());
					$(".company").attr("opertype","add");
					opt.companycode = "";
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
		data : opt
	}
})();

//机构选择页面
var selectinvestment=(function () {
    var opt={investmentcode:""};

    var savedata={
        searchorg:""
    };

    var dataconfig= function () {
        opt.page={
    			pageCount : 1,
    			pageSize:new CommonVariable().PAGESIZE,
    			totalCount : 2
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
                savedata.searchorg=$(".select-page-input").val().trim();
                opt.page=data.page;
                if(data.message=="success"){
                	var html=table_data.investmentlistNull;
                	if(data.list.length>0){
                		html=template.compile(table_data.investmentlist)(data);
                		$(".lists").append(html);
                		opt.dropload.resetload();
                		$(".item").unbind().bind("click",function () {
                    $("#investment").val($(this).html());
                    opt.investmentcode=$(this).attr('data-org-code'); 
                    $("#investment").attr("operatype",'select');
                    if($("#investment").attr("investmentcode")!=$(this).attr('data-org-code')){
                    	  $("#investor").val("");
                      $("#investor").attr("operatype",'no');
                      $("#investor").attr("investorcode","");
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
				if (data.message == "nodata") {

			        $("#investment").val($(".addcompinvestname").val().trim());

			        opt.investmentcode=""; 
			        $("#investment").attr("operatype",'add');
			        $("#investment").attr("investmentcode","");
						WCsearch_list.closepage();
				}
				else if(data.message == "exsit"){
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
								})
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
							    $("#investor").attr("operatype",'add');
							    $("#investor").attr("investorcode","");
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
                savedata.searchinv= $(".select-page-input").val().trim();
				opt.page = data.page;
				if (data.message == "success"){

					var html=table_data.investorSelectListNull;
			    if(data.list.length>0){
			           html=template.compile(table_data.investorSelectList)(data);
			            
									$(".lists").append(html);
				
									opt.dropload.resetload();
									$(".item").unbind().bind("click", function() {
					         
				        $("#investor").val($(this).html());
				        opt.investorcode=$(this).attr('data-investor-code'); 
				        $("#investor").attr("operatype",'select');
				        $("#investor").attr("investorcode",$(this).attr('data-investor-code'));
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
	return {
		config : config,
		data : opt,
        searchdata:savedata
	}
})();


var table_data = {
	tradeInvesDetail : '<li><span class="lable">投资机构:</span><span class="in"><%=base_investment_name%></span></li>'
			+ '<li><span class="lable">投资人:</span><span class="in">'
			+'<%=base_investor_name%></span></li>'
			+ '<li><span class="lable">金额:</span><span class="in"><%=base_trade_inmoney%>'
			+'</span></li>'
			+ '<li><span class="lable">领投:</span><span class="in">'
			+'<% if(base_trade_collvote=="0"){%>是<% } else{%>否<%}%></span></li>'
			+ '<li><span class="lable">分期:</span><span class="in">'
			+'<% if(base_trade_subpay=="0"){%>是<% } else{%>否<%}%></span></li>'
			+ '<li><span class="lable">对赌:</span><span class="in">'
			+'<% if(base_trade_ongam=="0"){%>是<% } else{%>否<%}%></span></li>'


			,
	tradeInvesList : '<%for(var i=0;i<list.length;i++){%>'
			+ '<tr id="<%=list[i].base_investment_code%>" p-index="<%=i%>">'
			+ '<td><%=list[i].base_investment_name%></td>'
			+ '<td><%=list[i].base_investor_name%></td>'
			+ '<td><%=list[i].base_trade_inmoney%></td>'
			+ '<td><button class="btn btn-default smart tradeInfoDelClick" p-index="<%=i%>" del-code="<%=list[i].base_investment_code%>">删除</button></td></tr>'
			+ '<%}%>',
	tradeInvesListNull : '<tr><td colspan="4">暂无数据</td></tr>',
	labelInfoList : '<%for(var i=0;i<list.length;i++){%>'
			+ '<li data-i="<%=i%>"><%=list[i].name%></li>' + '<%}%>',
	labelNull : '<li></li>',
	contentNull : '',
	companylist : '<% for (var i=0;i<list.length;i++ ){%>'
			+ '<div data-com-code="<%=list[i].base_comp_code%>" '
			+'data-bask-cont="<%=list[i].view_comp_baskcont%>" '
			+'data-indu-cont="<%=list[i].view_comp_inducont%>" '
			+'class="item"><%=list[i].base_comp_name %></div>'
			+ '<%}%>',
	investmentlist:'<% for (var i=0;i<list.length;i++ ){%>' +
	        '<div data-org-code="<%=list[i].base_investment_code%>" class="item"><%=list[i].base_investment_name %></div>' +
	        '<%}%>',
	investorSelectList:'<% for (var i=0;i<list.length;i++ ){%>' +
    '<div data-investor-code="<%=list[i].base_investor_code%>" '
			+'class="item"><%=list[i].base_investor_name %></div>' +
			'<%}%>',
	investorSelectListNull:'<option id="">暂无投资人</option>',
	investorSelectClear:'<option id="">请选择投资机构</option>',
	addcomp:	'<div class="shgroup" style="margin-top:10px;">'
    +' <div class="title no-border"><%=name%>:</div>' 
    +' <div class="tiplist no-border" style="padding-right:10px;">' 
    +' <input type="text" class="inputdef inputdef_l addcompinvestname" maxlength="20">' 
    +'</div>' 
    +'<div style="display:table-cell">'
    +'<button class="btn btn-default smart" id="addcompinvet" >'
    	+'确定</button></div></div>'
};

function labelContent(vObj){
	var list="[";
	if(vObj!=null){
		for ( var i = 0; i < vObj.length; i++) {
			list+="{code:'"+vObj[i].code+"'},";
		}
	}
	list+="]";
	return list;
}
//提交按钮
function addtradeclick(){
    $("#addtradesub").unbind().bind("click", function () {

    var data={};
    	data={
                induList:[],
                stageList:[],
                tradedate:tradedate
            };
    	//交易日期
    	var tradedate=new Date($("#tradedate").val());
    	if($("#tradedate").val().trim()==""){
				$.hideloading();
				$.showtip("请选择交易日期");
				setTimeout(function() {$.hidetip();}, 2000);
				return;
    	}
    	else if(!isNaN(tradedate.getTime())){
            	tradedate=tradedate.format(tradedate,"yyyy-MM-dd")
    }else if(isNaN(tradedate.getTime())){
			$.hideloading();
    	$.showtip("请重新选择交易日期");
			setTimeout(function() {$.hidetip();}, 2000);
	    	return;
            }
    	data={
                compinfo:[],
                basklist:[],
                stageList:[],
                indulist:[]
            };
    	
    	//被投公司id
		if($(".company").val()==null ||$(".company").val().trim()==""){
			$.hideloading();
			$.showtip("请选择公司");
			setTimeout(function() {$.hidetip();}, 2000);
			return;
		}
		else{
			var compinfo=new Object();
  		  //公司信息code
			compinfo.opertype=$(".company").attr("opertype");
			compinfo.compcode=$(".company").attr("compcode");
			compinfo.compname=$(".company").val().trim();
			data.compinfo.push(compinfo);
		};
		
    	//融资金额
		 var trademoney=$("#trademoney").val().trim();
	    //公司估值
		 var tradecomnum=$("#tradecomnum").val().trim();
		//公司估值类型
		 var tradecomnumtype=$("#tradecomnumtype").val().trim();
        //备注
        var noteinfo=$("#noteinfoid").val();
        if(noteinfo.trim()==""){
            noteinfo="";
        }
        $("#addtradesub").attr("disabled","disabled");
	    	$.showloading();
			$.ajax({
				async : true,
				dataType : 'json',
				type : 'post',
				url : "addtradeinfo.html",
				data : {
					tradedate : tradedate,
					compinfo:JSON.stringify(data.compinfo),
					basklist:JSON.stringify(baskJson),
					indulist:JSON.stringify(induJson),
					stageList:JSON.stringify(stageJson),
					tradeInfoList:JSON.stringify(tradeInfoList),
					trademoney:trademoney,
					tradecomnum:tradecomnum,
					tradecomnumtype:tradecomnumtype,
					noteinfo:noteinfo,
					
					logintype : cookieopt.getlogintype()
				},
				success : function(data) {
					$.hideloading();
					if (data.message == "success") {
						$.showtip("保存成功");
						setTimeout(function() {$.hidetip();
                            $("#addtradesub").removeAttr("disabled");
                            //2015-11-30 TASK078 RQQ ModStart
                            if(backherf != null && "" != backherf && "null"!=backherf ){
                                window.location.href=backherf+"&logintype="+cookieopt.getlogintype();
                            }
                            else{
                                window.location.href="addTradeInfoinit.html?logintype="+cookieopt.getlogintype();
                            }
                            //2015-11-30 TASK078 RQQ ModEnd
						}, 2000);
					}
					else if (data.message == "compnameexsit") {
						$.showtip("公司名称已存在");
						setTimeout(function() {$.hidetip();}, 2000);
                        $("#addtradesub").removeAttr("disabled");
					}
					else if(data.message == "invesnameexsit"){
						$.showtip(data.messagedetail);
						setTimeout(function() {$.hidetip();}, 2000);
                        $("#addtradesub").removeAttr("disabled");
					
					}else {
						$.showtip("保存失败");
						setTimeout(function() {$.hidetip();}, 2000);
                        $("#addtradesub").removeAttr("disabled");
					}
				}
			});
    });
    }
	

