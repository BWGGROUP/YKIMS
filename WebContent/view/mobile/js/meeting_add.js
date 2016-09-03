var investmentInfoList=[];//机构list
var compInfoList=[];//公司list

var parpuserJson;//与会者list
var sharewadJson;//分享范围list
var meetingtypeJson;//会议类型

var sharetype="1";//分享类型

$(function () {
   init();
});
function init(){
	
	if (userInfo != null && "" != userInfo) {
		// 参会人员
		parpuserJson = eval("[{code:'"+userInfo.sys_user_code+ "',name:'"+
				userInfo.sys_user_name+ "'}]");
	}
	//参会人员
	labelconfig.YKparpLoad();
	// 机构信息
	InvestmentInfoconfig.InvestmentInfoLoad();
	// 添加机构
	InvestmentInfoconfig.InvestmentAddClick();
	// 公司信息
	CompInfoconfig.CompInfoLoad();
	// 添加公司
	CompInfoconfig.CompAddClick();
	//会议类型
	meetingTypeConfig.click();
	//分享范围
	labelconfig.ShareWadLoad();
	//保存
	addmeetingsub();
}

var labelconfig = (function() {
// 初始加载易凯参会人
var initYKparp = function() {
	var html = "";
	if (parpuserJson != null && parpuserJson != "" ) {
		var data = {
			list : parpuserJson
		};
		html = template.compile(table_data.labelInfoList)(data);
	} else {
		html = table_data.labelNull;
	}
	$("#ykparp").html(html);
	YKuserClick();
};
// 参会人员事件
var YKuserClick = function() {
	var ykusershowlist = dicListConfig(true, YKuserList, parpuserJson);
	$("#EKRid").unbind().bind("click", function() {
		var $this = $(this);
		listefit.config({
			title : "参会人",
			list : ykusershowlist,
			radio : false,
			besure : function() {
				parpuserJson = eval(choiceContent());
				initYKparp();
			}
		});
	});
};

//初始加载分享范围
var initShareWad = function() {
	var html = "";
	if (sharewadJson != null && sharewadJson != "" ) {
		var data = {
			list : sharewadJson
		};
		html = template.compile(table_data.labelInfoList)(data);
	} else {
		html = table_data.labelNull;
	}
	$("#sharewad").html(html);
	ShareWadClick();
};
// 分享范围事件
var ShareWadClick = function() {
	var sharewadshowlist = dicListConfig(true, SysWadList, sharewadJson);
	$("#shareid").unbind().bind("click", function() {
		var $this = $(this);
		var htmltxt='<div class="sharefrom"><ul class="inputshare">' +
        '<li><span class=""><input id="ra1" name="sharetype" type="radio" value="1" checked/></span><span class="">分享给筐</span></li>' +
        '<li><span class=""><input id="ra2" name="sharetype" type="radio" value="2" /></span><span class="">分享给人</span></li>' +
        '</ul></div>';
		
		 inputlsit_edit.config({
             title:"提示",//弹层标题
             html:true,//是否以html显示
             besurebtn:"确定",//确定按钮文字
             cancle:"取消",//取消按钮文字 （如果不写，则不会显示该按钮）
             htmltext:htmltxt,
             submit: function () {
            	 sharetype=$('.inputshare input[name="sharetype"]:checked').val();
                 inputlsit_edit.close();
                 var sharelist;
     			if(sharetype=="1"){
     				sharelist=dicListConfig(true, SysWadList, sharewadJson);
     			}else if(sharetype=="2"){
     				sharelist=dicListConfig(true, YKuserList, sharewadJson);
     			}
                 
     			setTimeout(function(){
     				listefit.config({
         				title : "分享范围",
         				list : sharelist,
         				radio : false,
         				besure : function() {
         					sharewadJson = eval(choiceContent());
         					initShareWad();
         				}
         			});
     			}, 500);
                 
                 
             },//点击确定按钮回调方法
             canmit: function () {
                 inputlsit_edit.close();
             }//点击取消按钮回调方法
         });
		
	});
};
return {
	YKparpLoad : initYKparp,
	ShareWadLoad : initShareWad
};
})();


//会议类型
var meetingTypeConfig=(function(){
	var initMeetingType=function(){
		var html = "";
		if (meetingtypeJson != null && meetingtypeJson != "" ) {
			var data = {
				list : meetingtypeJson
			};
			html = template.compile(table_data.labelInfoList)(data);
		} else {
			html = table_data.labelNull;
		}
		$("#meetingtypelist").html(html);
//		refreshladle($("#meetingType"));
	};
	
	var click=function(){
		
		$("#meetingtype_id").unbind().bind("click", function() {
			var meetingtypeshowlist = dicTypeListConfig(false, meetingTypeList, meetingtypeJson);
			var $this = $(this);
			listefit.config({
				title : "会议类型",
				list : meetingtypeshowlist,
				radio : true,
				besure : function() {
					meetingtypeJson = eval(choiceContent());
					initMeetingType();
				}
			});
		});

	};
	
	return {
		click:click
	};
})();

//选择机构
var InvestmentInfoconfig = (function() {
	//机构打印
	var initInvestment = function() {
		var html = "";
		if(investmentInfoList[0]!=null){
			html=template.compile(table_data.investmentInfoList)({list:investmentInfoList});
		}
		$("#touzilist").html(html);
		invesclick();// 编辑机构选中行事件
	};

	// 机构信息选中行事件
	var invesclick = function() {
		$("#touzilist li").bind("click", function() {
			var id = $(this).attr("p-index");
			inputlsit_edit.config({
			    title:"相关机构",
			    list:[{id:"investment",lable:"投资机构",type:"text",readonly:"readonly"},
			      	{id:"investor",lable:"投资人",type:"text",readonly:"readonly"},
			      	{id:"investorposiname",lable:"职位",type:"text",maxlength:"20"}],
		        besurebtn:"修改",
		        cancle:"删除",
			    complete: function () {

			        $("#investorposiname").attr('readonlyflag',"readonlyflag");
	       	        //机构code
                    $("#investment").attr("investmentcode",investmentInfoList[id].base_investment_code);
                    //机构name
                    $("#investment").val(investmentInfoList[id].base_investment_name);

                    selectinvestment.searchdata.searchorg=  investmentInfoList[id].base_investment_name;

                    //机构添加类型
                    $("#investment").attr("operatype",investmentInfoList[id].investmenttype);
                    //投资人code
                    $("#investor").attr("investorcode",investmentInfoList[id].base_investor_code);
                    //投资人name
                    $("#investor").val(investmentInfoList[id].base_investor_name);

                    selectinvestor.searchdata.searchinv=investmentInfoList[id].base_investor_name;
                    //投资人类型
                    $("#investor").attr("operatype",investmentInfoList[id].investortype);
                      //投资人职位
                    $("#investorposiname").val(investmentInfoList[id].base_investor_posiname);
	  				investmentClick();
	  				investorClick();
	  				if((investmentInfoList[id].investortype=="add"
	  						|| investmentInfoList[id].investmenttype=="add")
	  							&& investmentInfoList[id].base_investor_name!=null
	  							&& investmentInfoList[id].base_investor_name!=""){
						    $("#investorposiname").attr('readonly',false);
	  				}else{
	  						$("#investorposiname").attr('readonly',true);
	  				}
			      },
			      submit: function () {
			    	  var bool=0;
			    	  if(investmentInfoList.length>0){
			    		  for ( var i = 0; i < investmentInfoList.length; i++) {
								if(i!=id && investmentInfoList[i].base_investment_name==$("#investment").val()
                                         && investmentInfoList[i].base_investor_name==$("#investor").val()
										){
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
			    		  investmentinfo.base_investment_code=$("#investment").attr("investmentcode");
			    		  //机构name
			    		  investmentinfo.base_investment_name=$("#investment").val();
			    		  //机构添加类型
			    		  investmentinfo.investmenttype=$("#investment").attr("operatype");
			    		  //投资人code
			    		  investmentinfo.base_investor_code=$("#investor").attr("investorcode");
			    		  //投资人name
			    		  investmentinfo.base_investor_name=$("#investor").val();
			    		  //投资人类型
			    		  investmentinfo.investortype=$("#investor").attr("operatype");
			    		  //投资人职位
			    		  investmentinfo.base_investor_posiname=$("#investorposiname").val();
			    		 
			    		  investmentInfoList[id]=investmentinfo;

                          selectinvestment.searchdata.searchorg="";
                          selectinvestor.searchdata.searchinv="";

			    		  initInvestment();
			    		  inputlsit_edit.close();
			    	  }

			      },
			      canmit: function () {
                          selectinvestment.searchdata.searchorg="";
                          selectinvestor.searchdata.searchinv="";
                          investmentInfoList.baoremove(id);
			    	  	  initInvestment();
						  inputlsit_edit.close();
		          }
			  });

		});
	};

	//添加相关机构图标事件
	var addinvenstmentClick=function(){
		$("#add-touzi").bind("click",function(e){
				inputlsit_edit.config({
				      title:"相关机构",
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
				    	  if(investmentInfoList.length>0){
				    		  for ( var i = 0; i < investmentInfoList.length; i++) {
									if(investmentInfoList[i].base_investment_name==$("#investment").val()
                                            && investmentInfoList[i].base_investor_name==$("#investor").val()
                                        ){
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
				    		  investmentinfo.base_investment_code=$("#investment").attr("investmentcode");
				    		  //机构name
				    		  investmentinfo.base_investment_name=$("#investment").val();
				    		  //机构添加类型
				    		  investmentinfo.investmenttype=$("#investment").attr("operatype");
				    		  //投资人code
				    		  investmentinfo.base_investor_code=$("#investor").attr("investorcode");
				    		  //投资人name
				    		  investmentinfo.base_investor_name=$("#investor").val();
				    		  //投资人类型
				    		  investmentinfo.investortype=$("#investor").attr("operatype");
				    		  //投资人职位
				    		  investmentinfo.base_investor_posiname=$("#investorposiname").val();
				    		 
				    		  investmentInfoList.push(investmentinfo);
				    		  initInvestment();

                              selectinvestment.searchdata.searchorg="";
                              selectinvestor.searchdata.searchinv="";

				    		  inputlsit_edit.close();
				    	  }
				      }
				  });
				
			
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
		InvestmentInfoLoad : initInvestment,
		InvestmentAddClick : addinvenstmentClick
	};
})();

//选择公司
var CompInfoconfig = (function() {
	//公司打印
	var initComp = function() {
		var html = "";
		if(compInfoList[0]!=null){
			html=template.compile(table_data.compInfoList)({list:compInfoList});
		}
		$("#gongsilist").html(html);
		compeditclick();// 编辑公司选中行事件
	};

	// 公司信息选中行事件
	var compeditclick = function() {
		$("#gongsilist li").unbind().bind("click", function() {
			var id = $(this).attr("p-index");
			inputlsit_edit.config({
			      title:"相关公司",
			      list:[
			        {id:"compid",lable:"公司",type:"text",readonly:"readonly"},
			      		{id:"entrepreneur",lable:"联系人",type:"text",readonly:"readonly"},
			      		{id:"entrepreneurposiname",lable:"职位",type:"text",maxlength:"20"},
				       {id: "planshow", lable: "创建融资计划",type:"checkbox"},
				       {id: "plantime", lable: "计划时间",type:"date"},
				       {id: "emailtime", lable: "提醒时间",type:"date"},
				       {id: "palntext", lable: "计划内容",type:"textarea"}
			      				],
		        besurebtn:"修改",
		        cancle:"删除",
			    complete: function () {
				    $("#entrepreneurposiname").attr('readonlyflag',"readonlyflag");
	       	  	//公司code
	    		  $("#compid").attr("base_comp_code",compInfoList[id].base_comp_code);
	    		  //公司name
	    		  $("#compid").val(compInfoList[id].base_comp_name);

                  selectcompany.searchdata.searchcom=compInfoList[id].base_comp_name;

	    		  //公司添加类型
	    		  $("#compid").attr("operatype",compInfoList[id].comptype);
	    		  //公司联系人code
	    		  $("#entrepreneur").attr("base_entrepreneur_code",compInfoList[id].base_entrepreneur_code);
	    		  //公司联系人name
	    		  $("#entrepreneur").val(compInfoList[id].base_entrepreneur_name);

                  selectentrepreneur.searchdata.searchinv=compInfoList[id].base_entrepreneur_name;

	    		  //公司联系人类型
	    		  $("#entrepreneur").attr("operatype",compInfoList[id].entrepreneurtype);
	    		  //公司联系人职位
	    		  $("#entrepreneurposiname").val(compInfoList[id].base_entrepreneur_posiname);
	    		  
	    		  if((compInfoList[id].comptype=="add"
						|| compInfoList[id].entrepreneurtype=="add")
							&& compInfoList[id].base_entrepreneur_name!=null
							&& compInfoList[id].base_entrepreneur_name!=""){
					    $("#entrepreneurposiname").attr('readonly',false);
					}
					else{
						$("#entrepreneurposiname").attr('readonly',true);
					}
	    		  if(compInfoList[id].financplanflag){
	    			  $("#planshow").prop("checked",true);
	    			  //融资计划计划时间
	    			  $("#plantime").val(compInfoList[id].base_financplan_date);
	    			  //融资计划提醒时间
	    			  $("#emailtime").val(compInfoList[id].base_financplan_remindate);
	    			  //融资计划计划内容
	    			  $("#palntext").val(compInfoList[id].base_financplan_cont);
	    		  }else{
	    			  $("#planshow").prop("checked",false);
		    		  $("#planshow").parents("ul[class='inputlist']").children().eq(4).css("display","none");
		    		  $("#planshow").parents("ul[class='inputlist']").children().eq(5).css("display","none");
		    		  $("#planshow").parents("ul[class='inputlist']").children().eq(6).css("display","none");
	    		  }

	    		 
	    		  	//选择公司
					compselectClick();
	  				//选择公司联系人
					entrepreneurselectClick();
					//融资计划选择
					compplanClick();
			      },
			      submit: function () {
			    	  var bool=0;
			    	  if(compInfoList.length>0){
			    		  for ( var i = 0; i < compInfoList.length; i++) {
								if(compInfoList[i].base_comp_code==$("#compid").attr("base_comp_code") 
										&& "select"==compInfoList[i].comptype
										&& i!=id){
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
			    		  compinfo.base_comp_code=$("#compid").attr("base_comp_code");
			    		  //公司name
			    		  compinfo.base_comp_name=$("#compid").val();
			    		  //公司添加类型
			    		  compinfo.comptype=$("#compid").attr("operatype");
			    		  //公司联系人code
			    		  compinfo.base_entrepreneur_code=$("#entrepreneur").attr("base_entrepreneur_code");
			    		  //公司联系人name
			    		  compinfo.base_entrepreneur_name=$("#entrepreneur").val();
			    		  //公司联系人类型
			    		  compinfo.entrepreneurtype=$("#entrepreneur").attr("operatype");
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
			    		  compInfoList[id]=compinfo;

                          selectcompany.searchdata.searchcom="";
                          selectentrepreneur.searchdata.searchinv="";

			    		  initComp();


			    		  inputlsit_edit.close();
			    	  }
			    	  setTimeout(function () {$.hidetip();},2000);
			      },
			      canmit: function () {

                      selectcompany.searchdata.searchcom="";
                      selectentrepreneur.searchdata.searchinv="";

			    	  compInfoList.baoremove(id);
			    	  initComp();
						  inputlsit_edit.close();
		            }
			  });

		});
	};

	//添加公司图标事件
	var addcompClick=function(){
		$("#add-gongsi").bind("click",function(e){
				inputlsit_edit.config({
				      title:"相关公司",
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
				    	  if(compInfoList.length>0){
				    		  for ( var i = 0; i < compInfoList.length; i++) {
									if(compInfoList[i].base_comp_code==$("#compid").attr("base_comp_code") 
											&& "select"==compInfoList[i].comptype){
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
				    		  compinfo.base_comp_code=$("#compid").attr("base_comp_code");
				    		  //公司name
				    		  compinfo.base_comp_name=$("#compid").val();
				    		  //公司添加类型
				    		  compinfo.comptype=$("#compid").attr("operatype");
				    		  //公司联系人code
				    		  compinfo.base_entrepreneur_code=$("#entrepreneur").attr("base_entrepreneur_code");
				    		  //公司联系人name
				    		  compinfo.base_entrepreneur_name=$("#entrepreneur").val();
				    		  //公司联系人类型
				    		  compinfo.entrepreneurtype=$("#entrepreneur").attr("operatype");
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
				    		  compInfoList.push(compinfo);
				    		  initComp();


                              selectcompany.searchdata.searchcom="";
                              selectentrepreneur.searchdata.searchinv="";

				    		  inputlsit_edit.close();
				    	  }
				    	  setTimeout(function () {$.hidetip();},2000);
				      }
				  });

			
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
		CompInfoLoad : initComp,
		CompAddClick : addcompClick
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
				var data = {name: '机构简称'};  
				var addcomphtml=template.compile(table_data.addcomp)(data);
				$(".inner").html("<div class='lists'></div>");
				$(".lists").html(addcomphtml);

                $(".addcompinvestname").val(savedata.searchorg);

				$("#addcompinvet").unbind().bind("click", function() {
					if(""!=$(".addcompinvestname").val().trim()){
//						 var bool=0;
//				    	  if(investmentInfoList.length>0){
//				    		  for ( var i = 0; i < investmentInfoList.length; i++) {
//									if(investmentInfoList[i].base_investment_name==$(".addcompinvestname").val().trim()
//
//                                        ){
//										bool=1;
//										break;
//									}
//					    	  }
//				    	  }
//				    	  if(bool==1){
//				    		  $.showtip("当前添加的机构已存在");
//				    		  setTimeout(function () {$.hidetip();},2000);
//				    	  }else{
//						    invementadd_ajax();
//				    	  }
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

                savedata.searchorg=$(".select-page-input").val().trim();

                opt.page=data.page;
                if(data.message=="success"){
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

				if (data.message == "nodata") {
			        $("#investment").val($(".addcompinvestname").val().trim());
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
        serarchinv:""
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


                                var bool=0;
                                if(investmentInfoList.length>0){
                                    for ( var i = 0; i < investmentInfoList.length; i++) {
                                        if(investmentInfoList[i].base_investment_name==$(".addcompinvestname").val().trim()
                                            && investmentInfoList[i].base_investment_name==$(".addcompinvestname").val().trim()
                                        ){
                                                bool=1;
                                                break;
                                        }
                                    }
                                }
                                if(bool==1){
                                    $.showtip("当前添加的机构已存在");
                                    setTimeout(function () {$.hidetip();},2000);
                                    return;
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

                savedata.searchinv=$(".select-page-input").val().trim();

				opt.page = data.page;
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
								});
					},
					addclick: function () {
						var data = {name: '公司简称'};  
						var addcomphtml=template.compile(table_data.addcomp)(data);
						$(".inner").html("<div class='lists'></div>");
						$(".lists").html(addcomphtml);
                        $(".addcompinvestname").val(savedata.searchcom);
						$("#addcompinvet").unbind().bind("click", function() {
							if(""!=$(".addcompinvestname").val().trim()){
								 var bool=0;
						    	  if(compInfoList.length>0){
						    		  for ( var i = 0; i < compInfoList.length; i++) {
											if(compInfoList[i].base_comp_name==$(".addcompinvestname").val().trim() ){
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
		data : opt ,
        searchdata:savedata
	};
})();


//创业者选择页面
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
                }
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

                savedata.searchinv=$(".select-page-input").val().trim();

                opt.page=data.page;
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

//标签弹出层集合
function dicListConfig(vDel, vList, vObj) {
	var list = [];
	var map = {};
	for (var i = 0; i < vList.length; i++) {
		map = {};
		map.name = vList[i].name;
		map.id = vList[i].code;
		if (vObj != null) {
			for (var j = 0; j < vObj.length; j++) {
				// 判断标签是否被选中,存在则加上选中标识
				if (vList[i].code == vObj[j].code) {
					map.select = true;
				}
			}
		}
		list.push(map);
	}
	return list;
}
//标签弹出层集合
function dicTypeListConfig(vDel,vList,vObj){
    var list=[];
    var map={};
    for(var i=0;i<vList.length;i++){
        map={};
		map.name = vList[i].sys_labelelement_name;
		map.id = vList[i].sys_labelelement_code;
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

//提交按钮
function addmeetingsub(){
    $("#addmeetingsub").unbind().bind("click", function () {
    	

    	//会议日期
    	var meetingdate=new Date($("#meetingdate").val());
    	//会议内容
    	var meetingcontent=$("#meetingcontentid").val().trim();
    	//验证必须选择会议日期输入会议内容
    	if($("#meetingdate").val().trim()==""){
				$.hideloading();
				$.showtip("请选择会议日期");
				setTimeout(function() {$.hidetip();}, 2000);
				return;
    	}
    	else if(isNaN(meetingdate.getTime())){
				$.hideloading();
	    	$.showtip("请重新选择会议日期");
				setTimeout(function() {$.hidetip();}, 2000);
		    	return;
            }
    else if(meetingcontent==null || ""==meetingcontent){
    	$.hideloading();
    	$.showtip("会议内容不可为空");
			setTimeout(function() {$.hidetip();}, 2000);
	    	return;
    }
    else{
		meetingdate=meetingdate.format(meetingdate,"yyyy-MM-dd");
		 //备注
		//2015/12/10 bug336 rqq ModStart
		 var noteinfo="";
		 if($("#noteinfoid").val().trim()!=null && $("#noteinfoid").val().trim()!=""){
			 noteinfo=$("#noteinfoid").val();
		 }
		 meetingcontent=$("#meetingcontentid").val();
		//2015/12/10 bug336 rqq ModEnd
            $(".addmeetingsub").attr("disabled","disabled");
		 $.showloading();
			$.ajax({
				async : true,
				dataType : 'json',
				type : 'post',
				url : "addmeetinginfo.html",
				data : {
					meetingdate : meetingdate,
					sharetype : sharetype,
					investmentInfoList:JSON.stringify(investmentInfoList),
					compInfoList:JSON.stringify(compInfoList),
					parpuserJson:JSON.stringify(parpuserJson),
					sharewadJson:JSON.stringify(sharewadJson),
					meetingtypeJson:JSON.stringify(meetingtypeJson),
					meetingcontent:meetingcontent,
					noteinfo:noteinfo,
					logintype : cookieopt.getlogintype()
				},
				success : addsussessfun,
                error: function () {
                    $(".addmeetingsub").removeAttr("disabled");
                }
			});
    }
    });
    }
   function addsussessfun(json){
	   $.hideloading();
		if (json.message == "success") {
			$.showtip("保存成功");
			setTimeout(function() {$.hidetip();
                $(".addmeetingsub").removeAttr("disabled");
			window.location.href="addMeetingInfoinit.html?logintype="+cookieopt.getlogintype();;
			}, 2000);
		}
		else if (json.message == "compnameexsit") {
			$.showtip(json.messagedetail);
			setTimeout(function() {$.hidetip();}, 2000);
            $(".addmeetingsub").removeAttr("disabled");
		}
		else if(json.message == "invesnameexsit"){
			$.showtip(json.messagedetail);
			setTimeout(function() {$.hidetip();}, 2000);
            $(".addmeetingsub").removeAttr("disabled");
		
		}else {
			$.showtip("保存失败");
			setTimeout(function() {$.hidetip();}, 2000);
            $(".addmeetingsub").removeAttr("disabled");
		}
   }
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
	labelNull : '<li></li>'
};
