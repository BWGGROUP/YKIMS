/**
 * Created by shbs-tp001 on 15-9-16.
 */
var kuang=["1","4","6"];
var pagetable;//交易子页
var fundtable;//基金子页
var investortable;//投资人子页
var meetingtable;//会议子页
var updlogtable;//更新记录子页

var page={};//近期交易分页
var meetingpage={};//会议分页
var updlogpage={};//更新记录分页

var orgFName="";//投资机构全称
var orgName="";//投资机构名称
var orgEName="";//投资机构英文名称

var baskJson;
var basklist;//投资机构选择框
var induJson;
var indulist;//行业选择框
var payattJson;
var payattlist;//近期关注选择框
var stageJson;
var bggroundlist;//背景选择框
var bggrounJson;
var stagelist;//投资阶段选择框
var featJson;
var featlist;//特征选择框
var typeJson;
var typelist;//类型选择框

var currData={};//币种字典
var notedata={};//备注数据集
var linkData = {};//易凯联系人数据集
var tradedata={};//近期交易数据集




//加载数据
var detailInfo={};//投资机构详情
var version="";//排他锁版本号
//var fundInfo=[];//基金
var tradeInfo=[];//交易
var noteInfo=[];//备注
var linkList=[];//易凯联系人

var currencyInfo=[];//币种
var currencyChildInfo=[];//币种子项
var baskList=[];//筐
var induList=[];//行业
var bggroundList=[];//背景
var stageList=[];//投资阶段
var featList=[];//投资特征
var typeList=[];//类型

var unDelLabelList=[];//交易记录机构label标签信息
var userInfoList=[];//用户
var investorList=[];//投资人
var organization_search=sessionStorage.getItem("organization_search");
$(function () {
//    $(".main-content").css("min-height",$(window).height());
    //初始加载
//    orgCode=detailInfo.base_investment_code;//赋值投资机构code
	
    initInvestmentAjax();
    
    delOrgConfig.init();
    
    currData={list:currencyInfo};//币种字典
    notedata={list:noteInfo,initSize:CommonVariable().NOTECONFIGNUM};//备注数据集
    linkData = {list:linkList};//易凯联系人数据集
    tradedata={list:tradeInfo};//近期交易数据集
    
    pageconfig();//加载投资机构信息
    investorInfoconfig.init();//加载投资人
    fundconfig.init();//加载投资机构基金
    fundconfig.addClick();//注册添加基金事件
    fundconfig.moreClick();//基金更多
    fundconfig.returnBtnClick();//基金子页返回
    tradeLinkConfig.init();//加载投资机构交易init
    tradeLinkConfig.addClick();//添加交易事件
    investmentNote.init();//加载备注
    ykLinkManConfig.rendering();//易凯联系人
    
	checktip_havemore();//控制加载更多按钮的方法
	
	//行业弹层初始数据
    baskJson=eval(detailInfo.view_investment_baskcont);//关注筐
    basklist=dicListConfig(false,baskList,baskJson);
    
    induJson=eval(detailInfo.view_investment_inducont);//行业
	indulist=dicListConfig(true,induList,induJson);
	
	payattJson=eval(detailInfo.view_investment_payattcont);//近期关注
	payattlist=dicListConfig(false,induList,payattJson);
	
	if(detailInfo.view_investment_backcode!=null&&detailInfo.view_investment_backcont!=null){
		bggroundJson=eval([{code:detailInfo.view_investment_backcode,name:detailInfo.view_investment_backcont}]);//背景
	}else{
		bggroundJson=[];
	}
	
	bggroundlist=dicListConfig(false,bggroundList,bggroundJson);
	
	stageJson=eval(detailInfo.view_investment_stagecont);//投资阶段
	stagelist=dicListConfig(true,stageList,stageJson);
	
	featJson=eval(detailInfo.view_investment_featcont);//特征
	featlist=dicListConfig(false,featList,featJson);
	
	typeJson=eval(detailInfo.view_investment_typecont);//类型
	typelist=dicListConfig(false,typeList,typeJson);
	
	//关注筐弹层
    $("#ul-bask").click(function () {
    	$.showloading();
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
                		url:"updateOrgInfo.html",
                		type:"post",
                		async:false,
                		data:{
                				type:'Lable-bask',
                			    orgCode:orgCode,
                				orgName:orgName,
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
                				basklist=dicListConfig(false,baskList,data.list);
                    			baskJson=data.list;
                    			version=data.version;
                            	var html=template.compile(trade_data.orgInfoList)(data);
                            	if(html==""){
                            		html=trade_data.orgInfoListNull;
                            	}
                            	$("#ul-bask").html(html);
                			}else{
                				$.showtip(data.message);
                			}
                			refreshladle($this);
                		}
                	});
            		
            	}
            	setTimeout("$.hidetip()",2000);
            }
        });
        $.hideloading();
    });
    //行业弹层
    $("#ul-indu").click(function () {
    	$.showloading();
    	var $this=$(this);
        listefit.config({
            title:"关注行业",
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
	            		url:"updateOrgInfo.html",
	            		type:"post",
	            		async:false,
	            		data:{
	            				type:'Lable-indu',
	            			    orgCode:orgCode,
	            				orgName:orgName,
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
	            				indulist=dicListConfig(true,induList,data.list);
	                			induJson=data.list;
	                			version=data.version;
	            				var html=template.compile(trade_data.orgInfoList)(data);
	            				if(html==""){
	                        		html=trade_data.orgInfoListNull;
	                        	}
	            				$("#ul-indu").html(html);
	            			}else{
	            				$.showtip(data.message);
	            			}
	            			refreshladle($this);
	            		}
	            	});
            	}
            	setTimeout("$.hidetip()",2000);
            }
        });
        $.hideloading();//等待动画隐藏
    });
    
  //近期特别关注弹层
    $("#ul-payatt").click(function () {
    	$.showloading();
    	var $this=$(this);
        listefit.config({
            title:"近期特别关注",
            list:payattlist,
            radio:false,
            besure: function () {
            	var oldData=oldContent(payattJson);
            	var newData=choiceContent();
            	if(oldData==newData){
            		$.showtip("未修改数据");
            	}else{
            		$.showloading();//等待动画
	            	$.ajax({
	            		url:"updateOrgInfo.html",
	            		type:"post",
	            		async:false,
	            		data:{
	            				type:'Lable-payatt',
	            			    orgCode:orgCode,
	            				orgName:orgName,
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
	            				payattlist=dicListConfig(false,induList,data.list);
	                			payattJson=data.list;
	                			version=data.version;
	                			var html="";
	                			if(data.list.length>0){
	                				html=template.compile(trade_data.orgInfoList)(data);
		            				if(html==""){
		                        		html=trade_data.orgInfoListNull;
		                        	}
	                			}else{
	                        		html=trade_data.orgInfoListNull;
	                        	}
	            				
	            				$("#ul-payatt").html(html);
	            			}else{
	            				$.showtip(data.message);
	            			}
	            			refreshladle($this);
	            		}
	            	});
            	}
            	setTimeout("$.hidetip()",2000);
            }
        });
        $.hideloading();//等待动画隐藏
    });
    
    
  //背景弹层
    $("#ul-bgground").click(function () {
    	$.showloading();
    	var $this=$(this);
        listefit.config({
            title:"背景",
            list:bggroundlist,
            radio:true,
            besure: function () {
            	var oldData=oldContent(bggroundJson);
            	var newData=choiceContent();
            	if(oldData==newData){
            		$.showtip("未修改数据");
            	}else{
            		$.showloading();//等待动画
	            	$.ajax({
	            		url:"updateOrgInfo.html",
	            		type:"post",
	            		async:false,
	            		data:{
	            				type:'Lable-bground',
	            			    orgCode:orgCode,
	            				orgName:orgName,
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
	            				bggroundlist=dicListConfig(false,bggroundList,data.list);
	                			bggroundJson=data.list;
	                			version=data.version;
	                			var html="";
	                			if(data.list.length>0){
	                				html=template.compile(trade_data.orgInfoList)(data);
		            				if(html==""){
		                        		html=trade_data.orgInfoListNull;
		                        	}
	                			}else{
	                        		html=trade_data.orgInfoListNull;
	                        	}
	            				
	            				$("#ul-bgground").html(html);
	            			}else{
	            				$.showtip(data.message);
	            			}
	            			refreshladle($this);
	            		}
	            	});
            	}
            	setTimeout("$.hidetip()",2000);
            }
        });
        $.hideloading();//等待动画隐藏
    });
    
    
  //阶段弹层
    $("#ul-stage").click(function () {
    	$.showloading();
    	var $this=$(this);
        listefit.config({
            title:"阶段",
            list:stagelist,
            radio:false,
            besure: function () {
            	var oldData=oldContent(stageJson);
            	var newData=choiceContent();
            	if(oldData==newData){
            		$.showtip("未修改数据");
            	}else{
            		$.showloading();//等待动画
	            	$.ajax({
	            		url:"updateOrgInfo.html",
	            		type:"post",
	            		async:false,
	            		data:{
	            				type:'Lable-investage',
	            			    orgCode:orgCode,
	            				orgName:orgName,
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
	            				stagelist=dicListConfig(true,stageList,data.list);
	                			stageJson=data.list;
	                			version=data.version;
	            				var html=template.compile(trade_data.orgInfoList)(data);
	            				if(html==""){
	                        		html=trade_data.orgInfoListNull;
	                        	}
	            				$("#ul-stage").html(html);
	            			}else{
	            				$.showtip(data.message);
	            			}
	            			refreshladle($this);
	            		}
	            	});
            	}
            	setTimeout("$.hidetip()",2000);
            }
        });
        $.hideloading();//等待动画隐藏
    });
    
  //投资机构特征弹层
    $("#ul-feat").click(function () {
    	$.showloading();
    	var $this=$(this);
        listefit.config({
            title:"投资机构特征",
            list:featlist,
            radio:false,
            besure: function () {
            	var oldData=oldContent(featJson);
            	var newData=choiceContent();
            	if(oldData==newData){
            		$.showtip("未修改数据");
            	}else{
            		$.showloading();//等待动画
	            	$.ajax({
	            		url:"updateOrgInfo.html",
	            		type:"post",
	            		async:false,
	            		data:{
	            				type:'Lable-feature',
	            			    orgCode:orgCode,
	            				orgName:orgName,
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
	            				featlist=dicListConfig(false,featList,data.list);
	                			featJson=data.list;
	                			version=data.version;
	            				var html=template.compile(trade_data.orgInfoList)(data);
	            				if(html==""){
	                        		html=trade_data.orgInfoListNull;
	                        	}
	            				$("#ul-feat").html(html);
	            			}else{
	            				$.showtip(data.message);
	            			}
	            			refreshladle($this);
	            		}
	            	});
            	}
            	setTimeout("$.hidetip()",2000);
            }
        });
        $.hideloading();//等待动画隐藏
    });
    
    
  //投资机构类型弹层
    $("#ul-type").click(function () {
    	$.showloading();
    	var $this=$(this);
        listefit.config({
            title:"投资机构类型",
            list:typelist,
            radio:false,
            besure: function () {
            	var oldData=oldContent(typeJson);
            	var newData=choiceContent();
            	if(oldData==newData){
            		$.showtip("未修改数据");
            	}else{
            		$.showloading();//等待动画
	            	$.ajax({
	            		url:"updateOrgInfo.html",
	            		type:"post",
	            		async:false,
	            		data:{
	            				type:'Lable-type',
	            			    orgCode:orgCode,
	            				orgName:orgName,
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
	            				typelist=dicListConfig(false,typeList,data.list);
	                			typeJson=data.list;
	                			version=data.version;
	                			var html=trade_data.orgInfoListNull;
	                			if(typeJson!=null){
	                				html=template.compile(trade_data.orgInfoList)(data);
		            				if(html==""){
		                        		html=trade_data.orgInfoListNull;
		                        	}
	                			}
	            				$("#ul-type").html(html);
	            			}else{
	            				$.showtip(data.message);
	            			}
	            			refreshladle($this);
	            		}
	            	});
            	}
            	setTimeout("$.hidetip()",2000);
            }
        });
        $.hideloading();//等待动画隐藏
    });
    
    //编辑投资机构名称
    editInvementName.click(); 
   
    //添加易凯联系人
    ykLinkManConfig.addclick();

    //注册近期交易更多事件
    $(".tradeMore").click(function(){
    	pageDataRendering();
    });
    //注册近期交易子页返回
    $(".tradeReturn").click(function(){
    	pagetable.hidepage();
    });
    
    //会议记录事件
    $(".meetingClick").click(function(){
    	pageMeetingDataRendering();
    });
    
    //会议子页返回
    $(".meetingReturn").click(function(){
    	meetingtable.hidepage();
    });
    
    //更新记录事件
    $(".updlogClick").click(function(){
    	pageUpdlogDataRendering();
    });
    
    //更新子页返回
    $(".updlogReturn").click(function(){
    	updlogtable.hidepage();
    });
    
    $(".btn-note").click(function(){
    	submitNote();
    });
    
    right_menu.congfig();
    if(backtype!=""){
    	organization_search=eval("(" + organization_search + ")");
        $(".goback").show();
    }
  //返回
    goBackConfig.init();
});

function initInvestmentAjax(){
	$.showloading();//等待动画
	$.ajax({
		url:"initInvestmentByOrgCode.html",
		type:"post",
		async:false,
		data:{orgCode:orgCode, logintype:cookieopt.getlogintype()},
		dataType: "json",
		success: function(data){
			$.hideloading();//隐藏等待动画
			if(data.message=="success"){
				detailInfo=data.investementDetailInfo;//投资机构信息
				orgCode=detailInfo.base_investment_code;
				version=data.version;//排他锁版本号
				fundconfig.data.fundList=data.fundList;//基金
				tradeInfo=data.tradeList;//交易
				noteInfo=data.noteList;//备注
				linkList=data.linkList;//易凯联系人

				currencyInfo=data.currencyList;//币种
				currencyChildInfo=data.currencyChildList;//币种子项
				baskList=data.baskList;//筐
				induList=data.induList;//行业
				bggroundList=data.bggroundList;//背景
				stageList=data.stageList;//投资阶段
				featList=data.featureList;//投资特征
				typeList=data.typeList;//类型

				unDelLabelList=data.unDelLabelList;//交易记录机构label标签信息
				userInfoList=data.userInfoList;//用户
				investorList=data.investorList;//投资人
				
				
			}
		}
	});
}


//根据币种类型筛选币种子项
function currency(vCode){
	var list=[];
	var map={};
	for ( var i = 0; i < currencyChildInfo.length; i++) {
		if(currencyChildInfo[i].sys_label_code==vCode){
			map={};
			map.code=currencyChildInfo[i].sys_labelelement_code;
			map.name=currencyChildInfo[i].sys_labelelement_name;
			list.push(map);
		}
	}
	return {list:list};
}



var right_menu=(function () {
    var congfig= function () {
        $(".do-right-menu").click(function () {
            $(".right-mulu").show();
            $(".left-menu-btn").unbind();
        });
        $("[data-i-href]").click(function () {
            window.location.hash=$(this).attr("data-i-href");
        });
        $(".right-mulu").unbind().bind("touchend", function (e) {
            close();
            e.stopPropagation();
        });
        $(".right-mulu-box").unbind().bind("touchend", function (e) {
            e.stopPropagation();
        });
        $(".right-mulu-box ul a").bind("click", function (e) {
            close();
            e.stopPropagation();
        });
        $(".do-top").bind("touchend", function (e) {
            $('.content-body').animate({scrollTop:0},500);
            e.stopPropagation();
        });

    };
    var close= function () {
        $(".right-mulu-box").removeClass("showrightaction").addClass("hiderightaction");
        setTimeout(function () {
            $(".right-mulu-box").removeClass("hiderightaction").addClass("showrightaction");
            $(".right-mulu").hide();
            $("body").css("overflow","");
        },200);
     
    };
    return{
        congfig:congfig
    };
})();

/* 初始加载投资机构信息 */
function pageconfig(){
	var data={};
	var html="";
	orgFName=detailInfo.base_investment_fullname==null?"":detailInfo.base_investment_fullname;//投资机构全称
	orgName=detailInfo.base_investment_name==null?"":detailInfo.base_investment_name;//赋值投资机构名称
	orgEName=detailInfo.base_investment_ename==null?"":detailInfo.base_investment_ename;//赋值投资机构英文名称
	
	//加载投资机构名称,英文名称
	$("#box-title").html(orgNameRending(orgName,orgEName));
			//orgName+"<div><span class=\"small\">"+orgEName+"</span></div>");
	
	var baskJson=eval(detailInfo.view_investment_baskcont);//关注筐
	if(baskJson!=null){
		data={list:baskJson};
		html=template.compile(trade_data.orgInfoList)(data);
	}else{
		html=trade_data.orgInfoListNull;
	}
	$("#ul-bask").html(html);
	
	
	var induJson=eval(detailInfo.view_investment_inducont);//行业
	if(induJson!=null){
		data={list:induJson};
		html=template.compile(trade_data.orgInfoList)(data);
	}else{
		html=trade_data.orgInfoListNull;
	}
	$("#ul-indu").html(html);
	
	var payattJson=eval(detailInfo.view_investment_payattcont);//近期关注
	if(payattJson!=null){
		data={list:payattJson};
		html=template.compile(trade_data.orgInfoList)(data);
	}else{
		html=trade_data.orgInfoListNull;
	}
	$("#ul-payatt").html(html);
	
	//背景
	if(detailInfo.view_investment_backcode!=null && detailInfo.view_investment_backcont!=null){
		var bgData=[{code:detailInfo.view_investment_backcode,name:detailInfo.view_investment_backcont}];
		var bggroundJson=eval(bgData);
		data={list:bggroundJson};
		html=template.compile(trade_data.orgInfoList)(data);
	}else{
		html=trade_data.orgInfoListNull;
	}
	$("#ul-bgground").html(html);
	
	var stageJson=eval(detailInfo.view_investment_stagecont);//投资阶段
	if(stageJson!=null){
		data={list:stageJson};
		html=template.compile(trade_data.orgInfoList)(data);
	}else{
		html=trade_data.orgInfoListNull;
	}
	$("#ul-stage").html(html);
	
	var featJson=eval(detailInfo.view_investment_featcont);//特征
	if(featJson!=null){
		data={list:featJson};
		html=template.compile(trade_data.orgInfoList)(data);
	}else{
		html=trade_data.orgInfoListNull;
	}
	$("#ul-feat").html(html);
	
	
	var typeJson=eval(detailInfo.view_investment_typecont);//类型
	if(typeJson!=null){
		data={list:typeJson};
		html=template.compile(trade_data.orgInfoList)(data);
	}else{
		html=trade_data.orgInfoListNull;
	}
	$("#ul-type").html(html);
}




//点击交易更多渲染近期交易界面
function pageDataRendering(){
	$.showloading();//等待动画
	$.ajax({
		url:"findPageTradeByOrgId.html",
		type:"post",
		async:false,
		data:{orgCode:orgCode, logintype:cookieopt.getlogintype()},
		dataType: "json",
		success: function(data){
			$.hideloading();//隐藏等待动画
			if(data.message=="success"){
				page=data.page;
				tradedata=data;
    			tradeLinkConfig.rendering();
    			$(".tradeLable").html("共"+data.page.totalCount+"条");
	        	pagetable=$("#tradePage").showpage();

			}else{
				$.showtip(data.message);
			}
			setTimeout("$.hidetip()",2000);
		}
	});
}


//近期交易分页请求
function pageList(){
	$('.tradePageaction').jqPagination({
        link_string	: '/?page={page_number}',
        max_page	: page.totalPage,
        current_page: page.pageCount,
        paged		: function(p) {
            if(p!=page.pageCount){
            	$.showloading();//等待动画
            	$.ajax({
            		url:"findPageTradeByOrgId.html",
            		type:"post",
            		async:false,
            		data:{orgCode:orgCode,pageCount:p, logintype:cookieopt.getlogintype()},
            		dataType: "json",
            		success: function(data){
            			$.hideloading();//隐藏等待动画
            			if(data.message=="success"){
	            			page=data.page;
	            			tradedata=data;
	            			tradeLinkConfig.rendering();
            			}else{
            				$.showtip(data.message);
            				setTimeout("$.hidetip()",2000);
            			}
            		}
            	});
            }
        }
    });
}


//添加备注
function submitNote(){
	var note=$("#orgNote").val();
	if(orgCode==null){
		$.showtip("未发现投资机构信息");
	}else if(note.trim()!=""){
		$.showloading();//等待动画
		$.ajax({
    		url:"addOrganizationNote.html",
    		type:"post",
    		async:true,
    		data:{
    			base_investment_code:orgCode,
    			base_invesnote_content:note,
    			base_ele_name:orgName,
    			logintype:cookieopt.getlogintype()},
    		dataType: "json",
    		success: function(data){
    			$.hideloading();//等待动画隐藏
    			if(data.message=="success"){
    				$.showtip("保存成功");
	    			$("#orgNote").val("");
	    			notedata.list=data.list;
	    			investmentNote.rendering();
    			}else{
    				$.showtip(data.message);
    			}
    		}
    	});
	}else{
		$.showtip("备注信息不能为空");
	}
	setTimeout("$.hidetip()",2000);
}


//会议子页
function pageMeetingDataRendering(){
	$.showloading();//等待动画
	$.ajax({
		url:"screenmeetinglist.html",
		type:"post",
		async:false,
		data:{orgaincode:orgCode, logintype:cookieopt.getlogintype()},
		dataType: "json",
		success: function(data){
			$.hideloading();//隐藏等待动画
			if(data.message=="success"||data.message=="nomore"){
				meetingpage=data.page;
				var html=trade_data.meetingListNull;
	        	if(data.list.length>0 && data.list[0]!=null){
	        		html=template.compile(trade_data.meetingList)(data);
	        		$(".pageaction").removeClass("display-none").addClass("display-block");
	        	}else{
	        		$(".pageaction").removeClass("display-block").addClass("display-none");
	        	}
	        	$("#pageMeetingBody tbody").html(html);
	        	$(".meetingLable").html("共"+data.page.totalCount+"条");
	        	meetingtable=$("#meetingPage").showpage();
	        	meetingLinkConfig.click();//注册选中行事件
	        	pageMeetingList();
			}else{
				$.showtip(data.message);
				setTimeout("$.hidetip()",2000);
			}
			
		}
	});
}


//会议分页请求
function pageMeetingList(){
	$('.meetingPageaction').jqPagination({
        link_string	: '/?page={page_number}',
        max_page	: meetingpage.totalPage,
        paged		: function(p) {
            if(p!=meetingpage.pageCount){
            	$.showloading();//等待动画
            	$.ajax({
            		url:"screenmeetinglist.html",
            		type:"post",
            		async:true,
            		data:{orgaincode:orgCode,pageCount:p, logintype:cookieopt.getlogintype()},
            		dataType: "json",
            		success: function(data){
            			$.hideloading();//隐藏等待动画
            			if(data.message=="success"){
	            			meetingpage=data.page;
	            			var html=trade_data.meetingListNull;
	                    	if(data.list.length>0&& data.list[0]!=null){
	                    		html=template.compile(trade_data.meetingList)(data);
	                    		$(".pageaction").removeClass("display-none").addClass("display-block");
	        	        	}else{
	        	        		$(".pageaction").removeClass("display-block").addClass("display-none");
	        	        	}
	                    	$("#pageMeetingBody tbody").html(html);
	                    	meetingLinkConfig.click();//注册选中行事件
            			}else{
            				$.showtip(data.message);
            				setTimeout("$.hidetip()",2000);
            			}
            			
            		}
            	});
            }
        }
    });
}

//更新记录子页面渲染
function pageUpdlogDataRendering(){
	$.showloading();//等待动画
	$.ajax({
		url:"findOrgUpdlogInfoByCode.html",
		type:"post",
		async:false,
		data:{type:'Lable-investment',code:orgCode, logintype:cookieopt.getlogintype()},
		dataType: "json",
		success: function(data){
			$.hideloading();//隐藏等待动画
			if(data.message=="success"){
				updlogpage=data.page;
	        	var html=trade_data.updlogListNull;
	        	if(data.list.length>0 && data.list[0]!=null){
	        		html=template.compile(trade_data.updlogList)(data);
	        		$(".pageaction").removeClass("display-none").addClass("display-block");
	        	}else{
	        		$(".pageaction").removeClass("display-block").addClass("display-none");
	        	}
	        	$("#pageUpdlogBody tbody").html(html);
	        	$(".updlogLable").html("共"+data.page.totalCount+"条");
	        	updlogtable=$("#updlogPage").showpage();
	        	pageUpdlogList();
			}else{
				$.showtip(data.message);
				setTimeout("$.hidetip()",2000);
			}
			
		}
	});
}


//更新记录分页请求
function pageUpdlogList(){
	$('.updlogPageaction').jqPagination({
        link_string	: '/?page={page_number}',
        max_page	: updlogpage.totalPage,
        paged		: function(p) {
            if(p!=updlogpage.pageCount){
            	$.showloading();//等待动画
            	$.ajax({
            		url:"findOrgUpdlogInfoByCode.html",
            		type:"post",
            		async:true,
            		data:{type:'Lable-investment',code:orgCode,pageCount:p, logintype:cookieopt.getlogintype()},
            		dataType: "json",
            		success: function(data){
            			$.hideloading();//隐藏等待动画
            			if(data.message=="success"){
	            			updlogpage=data.page;
	                    	var html=trade_data.updlogListNull;
	                    	if(data.list.length>0 && data.list[0]!=null){
	                    		html=template.compile(trade_data.updlogList)(data);
	                    		$(".pageaction").removeClass("display-none").addClass("display-block");
	    	        		}else{
	    	        			$(".pageaction").removeClass("display-block").addClass("display-none");
	    	        		}
	                    	$("#pageUpdlogBody tbody").html(html);
            			}else{
            				$.showtip(data.message);
            				setTimeout("$.hidetip()",2000);
            			}
            			
            		}
            	});
            }
        }
    });
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
					if(unDelLabelList!=null && vDel==true){
						for ( var k = 0; k < unDelLabelList.length; k++) {
							//判断已选标签信息是否已存在与交易记录中,存在则加上不可删除标识no-delete:ture
							if(unDelLabelList[k].sys_labelelement_code==vObj[j].code){
								map.no_delete = true;
							}
						}
					}
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

//弹层选择筐选择值,返回( "name1,name2,name3," )
function choiceName(){
	var list="";
	$(".list li[class='active']").each(function(){
		list+=$(this).text()+",";
	});
	return list;
}

//弹层选择筐选择值,返回("code1,code2,...")
function choiceCode(){
	var list="";
	$(".list li[class='active']").each(function(){
		list+=$(this).attr("tip-l-i")+",";
	});
	return list;
}
//获取传入对象code,返回("code1,code2,...")
function dataCode(vObj){
	var olddata="";
	if(vObj!=null){
		for ( var i = 0; i < vObj.length; i++) {
			olddata+=vObj[i].code+",";
		}
	}
	return olddata;
}

//动态加载投资规模
function findScale(vCode){
	var html=template.compile(trade_data.scaleDIC)(currency(vCode));
	$("#fundScale").html(html);
}

var trade_data={
		currencyDIC:'<select id="fundCurrency" class="inputdef" onchange="findScale(this.value)">'+
					'<%for(var i=0;i<list.length;i++){%>'+
					'<option value="<%=list[i].sys_labelelement_code%>"><%=list[i].sys_labelelement_name%></option>'+
					'<%}%></select>',
		scaleDIC:'<select id="fundScale" class="inputdef" >'+
					'<%for(var i=0;i<list.length;i++){%>'+
					'<option value="<%=list[i].code%>"><%=list[i].name%></option>'+
					'<%}%></select>',
    	tradeList:'<%for(var i=0;i<list.length;i++){%>'+
					'<tr class="tradeLink" id="<%=list[i].base_trade_code%>">'+
					'<td><%=dateFormat(list[i].base_trade_date,"yyyy-MM")%></td>'+
					'<td><%=list[i].base_comp_name%></td>'+
					'<td><%=list[i].base_trade_stagecont%></td>'+
					'<td><%=list[i].base_trade_inmoney%></td>'+
					'<td><button class="btn btn-default smart tradeDeleteClick" i="<%=i%>" del-code="<%=list[i].base_trade_code%>">删除</button></td>'+
					'</tr><%}%>',		
		tradeListNull:'<tr><td colspan=\'5\'>暂无数据</td></tr>',
		
		investorLabel:'<%for(var i=0;i<list.length;i++){%>'+
					'<li><div class="investorEditDiv"><span class="name investorLinkDetial" id="<%=list[i].base_investor_code%>"><%=list[i].base_investor_name==""?"未知":list[i].base_investor_name%></span>'+
			        '  <span class="position"><%=list[i].base_investor_posiname%></span>'+
			        '<span del-code="<%=list[i].base_investor_code%>" ref-bool="true" name="<%=list[i].base_investor_name%>" class="glyphicon glyphicon-remove investorDelClick"></span>'+
					'</div></li>'+
			        '<%}%>',
		investorLabelNull:'<li><span class="position">暂无数据</span></li>',
    	noteList:'<%for(var i=0;i<list.length;i++){%>'+
	    			'<tr <%=nodermore(i,initSize)%> >'+
	    			'<td><%=dateFormat(list[i].createtime.time,"yyyy-MM-dd")%></td>'+
	    			'<td><%=list[i].sys_user_name%></td>'+
	    			'<td><pre><%=list[i].base_invesnote_content%></pre></td>'+
	    			'<td><button class="btn btn-default smart noteDeleteClick" i="<%=i%>" del-code="<%=list[i].base_invesnote_code%>">删除</button></td></tr>'+
	    			'<%}%>',
    	noteListNull:'<tr><td colspan=\'4\'>暂无数据</td></tr>',
    	
    	investorList:'<%for(var i=0;i<list.length;i++){%>'+
					'<tr class="investorLinkDetial" id="<%=list[i].base_investor_code%>">'+
					'<td><%=list[i].base_investor_name%></td>'+
					'<td><%=list[i].base_investor_posiname%></td>'+
					'<td><%=list[i].base_investor_state%></td>'+
                    '<td><button class="btn btn-default smart investorDelClick" ref-bool="false"'+
                    'name="<%=list[i].base_investor_name%>" del-code="<%=list[i].base_investor_code%>">删除</button></td>'+
					'</tr><%}%>',
		investorListNull:'<tr><td colspan=\'4\'>暂无数据</td></tr>',

    	invesfundList:'<%for(var i=0;i<list.length;i++){%>'+
    				'<tr id="<%=list[i].base_invesfund_code%>" ref-code="true"><td><%=list[i].base_invesfund_name%></td>'+
    				'<td><%=list[i].base_invesfund_currencyname%></td>'+
    				'<td><%=list[i].base_invesfund_scale%></td>'+
    				'<td><%=list[i].base_invesfund_state=="0"?"有效":"无效"%></td></tr>'+
    				'<%}%>',
         invesfundChildList:'<%for(var i=0;i<list.length;i++){%>'+
			        '<tr id="<%=list[i].base_invesfund_code%>" ref-code="false"><td><%=list[i].base_invesfund_name%></td>'+
			        '<td><%=list[i].base_invesfund_currencyname%></td>'+
			        '<td><%=list[i].base_invesfund_scale%></td>'+
			        '<td><%=list[i].base_invesfund_state=="0"?"有效":"无效"%></td></tr>'+
			        '<%}%>',
    	invesfundListNull:'<tr><td colspan=\'4\'>暂无数据</td></tr>',
    	linkManList:'<%for(var i=0;i<list.length;i++){%>'+
    				'<li data-i="<%=i%>"><span id="<%=list[i].sys_user_code%>" class="name"><a ><%=list[i].sys_user_name%></a></span></li>'+
    				'<%}%>',
    	linkManListNull:'<li class="black">暂无数据</li>',
    	orgInfoList:'<%for(var i=0;i<list.length;i++){%>'+
			    	'<li data-i="<%=i%>"><%=list[i].name%></li>'+
			    	'<%}%>',
		orgInfoListNull:'<li>暂无数据</li>',
	    updlogList:'<%for(var i=0;i<list.length;i++){%>'+
                    '<tr><td><%=dateFormat(list[i].updtime.time,"yyyy-MM-dd")%></td>'+
                    '<td><%=list[i].sys_user_name%></td>'+
                    '<td><%=list[i].base_updlog_opercont%></td>'+
                    '<td><%=list[i].base_updlog_oridata%>'+
                    '<%=list[i].base_updlog_newdata%></td></tr>'+
                    '<%}%>',
		updlogListNull:'<tr><td colspan=\'4\'>暂无数据</td></tr>',
		meetingList:'<%for(var i=0;i<list.length;i++){%>'+
                    '<tr class="meetingLink" id="<%=list[i].base_meeting_code%>" mt_visible="<%=list[i].visible%>">'+
                    '<td><%=list[i].base_meeting_time%></td>'+
                    '<td><%=substring(list[i].createName,"10")%></td>'+
                    '<td><%=substring(list[i].base_meeting_compcont+" / "+list[i].base_meeting_invicont,"10")%></td></tr>'+
                    '<%}%>',
		meetingListNull:'<tr><td colspan=\'3\'>暂无数据</td></tr>'
};
//应用模板 截取字符串
template.helper("substring", function (str,long) {
    str= str.replace("<br/>","");
    if(str.length>long){
        str= str.substring(0,long)+"...";
    }
    return str;
});

//控制加载更多按钮的方法
function checktip_havemore(){
    $("ul[ro]").each(function () {
        if( $(this)[0].scrollHeight>$(this)[0].clientHeight){
            $(this).parents(".shgroup").addClass("havemore");
        }else{
        	$(this).parents(".shgroup").removeClass("havemore");
        }
//      
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


//编辑投资机构名称
var editInvementName=(function(){
	var edit=function(){
		$("#box-title").bind("click",function () {
	        inputlsit_edit.config({
	            title:"编辑投资机构名称",
	            list:[{id:"investmentFullName",lable:"机构全称",value:orgFName,type:"text",maxlength:200},
	                  {id:"investmentCName",lable:"机构简称",value:orgName,type:"text",maxlength:20},
	                  {id:"investmentEName",lable:"英文名称",value:orgEName,type:"text",maxlength:200}],
	            submit: function () {
	            	var fName=$("#investmentFullName").val();
	            	var cName=$("#investmentCName").val();
	            	var eName=$("#investmentEName").val();
	            	if(orgCode==null || orgCode==""){
	            		inputlsit_edit.close();
	            		$.showtip("未发现投资机构");
	            	}else if(cName.trim()==""){
	            		$.showtip("机构简称不能为空");
	            	}else if(fName==orgFName&&cName==orgName&&eName==orgEName){
	            		$.showtip("机构名称没有修改");
	            	}else{
	            		$.showloading();//等待动画
	            		$.ajax({
	                		url:"updateInvestmentName.html",
	                		type:"post",
	                		async:false,
	                		data:{
	                			orgCode:orgCode,
	                			orgName:orgName,
	                			version:version,
	                			logintype:cookieopt.getlogintype(),
	                			fName:fName,
	                			cName:cName,
	                			eName:eName,
	                			oldFname:orgFName,
	                			oldCname:orgName,
	                			oldEname:orgEName
	                				},
	                		dataType: "json",
	                		success: function(data){
	                			$.hideloading();//等待动画隐藏
	                			inputlsit_edit.close();
	                			if(data.message=="success"){
	                				$.showtip("保存成功");
	                				orgFName=data.fullname;//投资机构全称
	                				orgName=data.cnname;//赋值投资机构名称
	                				orgEName=data.enname;//赋值投资机构英文名称
	                				version=data.version;//更新最新版本号
	                				$("#box-title").html(orgNameRending(orgName,orgEName));
	                				//$("#box-title").html(orgName+"<div><span class=\"small\">"+orgEName+"</span></div>");
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
	return{
		click:edit
	};
})();

//基金
var fundconfig=(function(){
    var initdata={
        list:[],
        fundList:[],
        page:{pageCount:1},
        refBool:"true",
        saveBool:true
    };
    var rend=function(){
        var html=trade_data.invesfundListNull;
        if(initdata.fundList.length>0 && initdata.fundList[0]!=null){
            var data={list:initdata.fundList};
            html=template.compile(trade_data.invesfundList)(data);
        }
        $("#fundTable tbody").html(html);
        if(initdata.fundList.length>0 && initdata.fundList[0]!=null){
        	editClick();
        }
    };

    var rendChild=function(){
        var html=trade_data.invesfundListNull;
        if(initdata.list.length>0&&initdata.list[0]!=null){
            html=template.compile(trade_data.invesfundChildList)({list:initdata.list});
            $(".pageaction").removeClass("display-none").addClass("display-block");
        }else{
            $(".pageaction").removeClass("display-block").addClass("display-none");
        }
        $("#pageFundBody tbody").html(html);
        if(initdata.list.length>0&&initdata.list[0]!=null){
        	editClick();
            pageFundList();
        }
    };

    //添加基金弹筐
    var addClick=function(){
        $(".fundDiv .addbtnfund").bind("click",function () {
            if(orgCode!=null){
                var htmltext='<li><span class="lable">名称:</span><span class="in"><input id="fundName" type="text" class="inputdef" maxlength="20"></span></li>' +
                    '<li><span class="lable">币种:</span><span class="in">'+template.compile(trade_data.currencyDIC)(currData)+'</span></li>'+
                    '<li><span class="lable">金额:</span><span class="in">'+template.compile(trade_data.scaleDIC)(currency(currencyInfo[0].sys_labelelement_code))+'</span></li>';
                initdata.saveBool=false;
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
                        if(orgCode==null){
                            $.showtip("未发现投资机构信息");
                        }else{
                        	if(initdata.saveBool==false){
                        		initdata.saveBool=true;
                        		$.showloading();//等待动画
                                $.ajax({
                                    url:"addOrganizationInvesfund.html",
                                    type:"post",
                                    async:false,
                                    data:{
                                        base_investment_code:orgCode,
                                        base_ele_name:orgName,
                                        base_invesfund_name:fundName,
                                        base_invesfund_currency:fundCurrency,
                                        base_invesfund_currencyname:fundCurrencyTxt,
                                        base_invesfund_scalecode:fundScale,
                                        base_invesfund_scale:fundScaleTxt,
                                        logintype:cookieopt.getlogintype(),
                                        version:version
                                    },
                                    dataType: "json",
                                    success: function(data){
                                        $.hideloading();//等待动画隐藏
                                        inputlsit_edit.close();
                                        if(data.message=="success"){
                                        	version=data.version;
                                        	initdata.fundList=data.list;
                                            rend();
                                            $.showtip("保存成功");
                                        }else{
                                            $.showtip(data.message);
                                        }
                                        setTimeout("$.hidetip()",2000);
                                    }
                                });
                        	}
                            
                        }
                        setTimeout("$.hidetip()",2000);
                    }

                });
            }else{
                $.showtip("未发现投资机构");
                setTimeout("$.hidetip()",2000);
            }

        });
    };


    //修改基金
    var editClick=function(){
        $("#fundTable tbody tr,#pageFundBody tbody tr").unbind().bind("click",function(){
            if(orgCode!=null){
                var code=$(this).attr("id");
                initdata.refBool=$(this).attr("ref-code");
                var fName="";
                var fCurrCode="";
                var fCurrTxt="";
                var fScaleCode="";
                var fScaleTxt="";
                var fState="";

                var txtList=[];

                if(initdata.refBool=="true"){
                    txtList=initdata.fundList;
                }else {
                    txtList=initdata.list;
                }

                for (var i = 0; i < txtList.length; i++) {
                    if (code == txtList[i].base_invesfund_code) {
                        fName = txtList[i].base_invesfund_name;
                        fCurrCode = txtList[i].base_invesfund_currency;
                        fCurrTxt = txtList[i].base_invesfund_currencyname;
                        fScaleCode = txtList[i].base_invesfund_scalecode;
                        fScaleTxt = txtList[i].base_invesfund_scale;
                        fState = txtList[i].base_invesfund_state;
                    }
                }
                var htmltext=
                	'<li><span class="lable">名称:</span><span class="in"><input id="fundName" type="text" class="inputdef" maxlength="20"></span></li>' +
                    '<li><span class="lable">币种:</span><span class="in">'+template.compile(trade_data.currencyDIC)(currData)+'</span></li>'+
                    '<li><span class="lable">金额:</span><span class="in">'+template.compile(trade_data.scaleDIC)(currency(fCurrCode))+'</span></li>'+
                    '<li><span class="lable">状态:</span><span class="in"><select id="state" class="inputdef"><option value="0">有效</option><option value="1">无效</option></select></span></li>';

                inputlsit_edit.config({
                    title:"修改基金",
                    html:true,
                    cancle:"删除",//取消按钮文字 （如果不写，则不会显示该按钮）
                    besurebtn:"确定",//确定按钮文字
                    htmltext:htmltext,
                    submit: function () {
                    	
                        var fundName=$("#fundName").val().trim();
                        var fundCurrency=$("#fundCurrency").find("option:selected").val();
                        var fundCurrencyTxt=$("#fundCurrency").find("option:selected").text();
                        var fundScale=$("#fundScale").find("option:selected").val();
                        var fundScaleTxt=$("#fundScale").find("option:selected").text();
                        var state=$("#state").val();
                        
                        if(orgCode==null){
                        	inputlsit_edit.close();
                            $.showtip("未发现投资机构信息");
                        }else if(fName==fundName && fCurrCode==fundCurrency&&fScaleCode==fundScale&&fState==state){
                            $.showtip("未修改数据");
                        }else{
                            $.showloading();//等待动画
                            $.ajax({
                                url:"editOrganizationInvesfund.html",
                                type:"post",
                                async:false,
                                data:{
                                    base_investment_code:orgCode,
                                    base_ele_name:orgName,
                                    base_invesfund_code:code,
                                    base_invesfund_name:fundName,
                                    base_invesfund_currency:fundCurrency,
                                    base_invesfund_currencyname:fundCurrencyTxt,
                                    base_invesfund_scalecode:fundScale,
                                    base_invesfund_scale:fundScaleTxt,
                                    base_invesfund_state:state,
                                    fundName:fName,
                                    fundCurrency:fCurrTxt,
                                    fundScale:fScaleTxt,
                                    fundState:fState,
                                    pageCount:initdata.page.pageCount,
                                    refBool:initdata.refBool,
                                    logintype:cookieopt.getlogintype(),
                                    version:version
                                },
                                dataType: "json",
                                success: function(data){
                                    $.hideloading();//等待动画隐藏
                                    inputlsit_edit.close();
                                    if(data.message=="success"){
                                    	version=data.version;
                                        if(initdata.refBool=="true"){
                                            initdata.fundList=data.fundlist;
                                            rend();
                                        }else{
                                            initdata.fundList=data.fundlist;
                                            initdata.page=data.page;
                                            initdata.list=data.list;
                                            rendChild();
                                            rend();
                                        }
                                        $.showtip("保存成功");
                                    }else{
                                        $.showtip(data.message);
                                    }
                                    setTimeout("$.hidetip()",2000);
                                }
                            });
                        }
                        setTimeout("$.hidetip()",2000);
                    },
                    canmit:function(){
                        inputlsit_edit.config({
                            title:"提示",//弹层标题
                            html:true,//是否以html显示
                            besurebtn:"确定",//确定按钮文字
                            cancle:"取消",//取消按钮文字 （如果不写，则不会显示该按钮）
                            htmltext:"<div style='text-align: center;font-size: 15px;'>您确定要删除该条记录吗？</div>",
                            submit: function () {
                                $.showloading();//等待动画
                                $.ajax({
                                    url:"deleteOrganizationInvesfund.html",
                                    type:"post",
                                    async:false,
                                    data:{
                                        orgCode:orgCode,
                                        orgName:orgName,
                                        fundCode:code,
                                        fundName:fName,
                                        currency:fCurrTxt,
                                        scale:fScaleTxt,
                                        pageCount:initdata.page.pageCount,
                                        refBool:initdata.refBool,
                                        logintype:cookieopt.getlogintype(),
                                        version:version
                                    },
                                    dataType: "json",
                                    success: function(data){
                                        $.hideloading();//等待动画隐藏
                                        if(data.message=="success"){
                                        	version=data.version;
                                            $.showtip("删除成功");
                                            if(initdata.refBool=="true"){
                                                initdata.fundList=data.fundlist;
                                                rend();
                                            }else{
                                                initdata.page=data.page;
                                                initdata.list=data.list;
                                                rendChild();
                                                if(initdata.page.pageCount<=1){
                                                    initdata.fundList=data.fundlist;
                                                    rend();
                                                }
                                            }
                                            inputlsit_edit.close();
                                        }else{
                                            $.showtip(data.message);
                                        }
                                        setTimeout("$.hidetip()",2000);
                                    }
                                });


                                inputlsit_edit.close();
                            },//点击确定按钮回调方法
                            canmit: function () {
                                inputlsit_edit.close();
                            }//点击取消按钮回调方法
                        });
                    }

                });

                $("#fundName").val(fName);
                $("#fundCurrency").val(fCurrCode);
                $("#fundScale").val(fScaleCode);
                $("#state").val(fState);

                
            }else{
                $.showtip("未发现投资机构");
                setTimeout("$.hidetip()",2000);
            }


        });

    };

    //点击基金更多渲染基金子界面
    var moreClick=function (){
        $(".fundMore").unbind().bind("click",function(){
            initdata.refBool="false";
            $.showloading();//等待动画
            $.ajax({
                url:"findPageFundByOrgId.html",
                type:"post",
                async:false,
                data:{orgCode:orgCode, logintype:cookieopt.getlogintype()},
                dataType: "json",
                success: function(data){
                    $.hideloading();//隐藏等待动画
                    if(data.message=="success"){
                        initdata.page=data.page;
                        initdata.list=data.list;
                        $(".fundLable").html("共"+data.page.totalCount+"条");
                        rendChild();
                        fundtable=$("#fundPage").showpage();
                    }else{
                        $.showtip(data.message);
                    }
                    setTimeout("$.hidetip()",2000);
                }
            });
        });

    };
//基金分页请求
    var pageFundList=function (){
        $('.fundPageaction').jqPagination({
            link_string	: '/?page={page_number}',
            max_page	: initdata.page.totalPage,
            current_page: initdata.page.pageCount,
            paged		: function(p) {

                if(p!=initdata.page.pageCount){
                    $.showloading();//等待动画
                    $.ajax({
                        url:"findPageFundByOrgId.html",
                        type:"post",
                        async:true,
                        data:{
                            orgCode:orgCode,
                            pageCount:p,
                            logintype:cookieopt.getlogintype()},
                        dataType: "json",
                        success: function(data){
                            $.hideloading();//隐藏等待动画
                            if(data.message=="success"){
                                initdata.page=data.page;
                                initdata.list=data.list;
                                rendChild();
                            }else{
                                $.showtip(data.message);
                            }
                            setTimeout("$.hidetip()",2000);
                        }
                    });
                }
            }
        });
    };

    //基金子页返回
    var returnBtnClick=function() {
        $(".fundReturn").click(function () {
            initdata.refBool = "true";
            fundtable.hidepage();
        });
    };

    return{
        data:initdata,
        init:rend,
        addClick:addClick,
        moreClick:moreClick,
        returnBtnClick:returnBtnClick
    };

})();

//易凯联系人
var ykLinkManConfig=(function(){
	//加载易凯联系人
	var rendering=function(){
		var html=trade_data.linkManListNull;
		if(linkData.list.length>0 && linkData.list[0]!=null){
			html=template.compile(trade_data.linkManList)(linkData);
			if(html==""){
				html=trade_data.linkManListNull;
			}
		}
		$("#YKLinkMan").html(html);
		ykLinkClick();
	};
	//添加易凯联系人
	var add=function(){
		$(".yikairen .addbtn").unbind().bind("click",function () {
		        inputlsit_edit.config({
		            title:"添加易凯联系人",
		            list:[{id:"ykLinkManName",lable:"易凯联系人",optionlist:userInfoList,type:"select"}],
		            submit: function () {
		            	var userName=$("#ykLinkManName").find("option:selected").text();
		            	var userId=$("#ykLinkManName").find("option:selected").attr("id");
		            	var str=false;
		            	if(orgCode!=null){
			            	if(linkList!=null){
				            	for ( var i = 0; i < linkList.length; i++) {
									if(userId==linkList[i].sys_user_code){
										str=true;
										break;
									}
								}
			            	}
		            	}
		            	if(orgCode==null || orgCode==""){
		            		inputlsit_edit.close();
		            		$.showtip("未发现投资机构信息");
		            	}else if(str==true){
		            		$.showtip("联系人已存在");
		            	}else{
		            		$.showloading();//等待动画
		            		$.ajax({
		                		url:"addOrgYKLinkMan.html",
		                		type:"post",
		                		async:false,
		                		data:{
		    	            			base_investment_code:orgCode,
		    	            			base_ele_name:orgName,
		    	            			sys_user_code:userId,
		    	            			sys_user_name:userName,
		    	            			logintype:cookieopt.getlogintype(),
		    	            			version:version
		                			},
		                		dataType: "json",
		                		success: function(data){
		                			$.hideloading();//等待动画隐藏
		                			inputlsit_edit.close();
		                			if(data.message=="success"){
		                				$.showtip("保存成功");
		                				version=data.version;
		                				linkData=data;
		                				linkList=data.list;
		                				rendering();
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
	//提交编辑
	var linkSubmit=function(userIdOld,userNameOld,userIdNew,userNameNew){
		$.showloading();//等待动画
    	$.ajax({
    		url:"editOrgYKLinkMan.html",
    		type:"post",
    		async:false,
    		data:{
        			orgCode:orgCode,
        			orgName:orgName,
        			oldUserCode:userIdOld,
        			oldUserName:userNameOld,
        			newUserCode:userIdNew,
        			newUserName:userNameNew,
        			version:version,
        			logintype:cookieopt.getlogintype()
    			},
    		dataType: "json",
    		success: function(data){
    			$.hideloading();//等待动画隐藏
    			inputlsit_edit.close();
    			if(data.message=="success"){
    				$.showtip("保存成功");
    				version=data.version;
    				linkData=data;
    				linkList=data.list;
    				rendering();
    			}else{
    				$.showtip(data.message);
    			}
    		}
    	});
    };
    //注册易凯联系人编辑事件
    var ykLinkClick=function(){
	    $("#YKLinkMan .name").bind("click",function () {
	    	var userNameOld=$(this).text();
	    	var userIdOld=$(this).attr("id");
	        inputlsit_edit.config({
	            title:"编辑易凯联系人",
	            list:[{id:"edLinkManName",lable:"易凯联系人",optionlist:userInfoList,type:"select"}],
	            submit: function () {
	            	var userNameNew=$("#edLinkManName").find("option:selected").text();
	            	var userIdNew=$("#edLinkManName").find("option:selected").attr("id");
	            	var str=true;
	            	for ( var i = 0; i < linkList.length; i++) {
						if(userIdNew==linkList[i].sys_user_code){
							str=false;
							break;
						}
					}
	            	if(userIdOld==userIdNew){
	            		$.showtip("未修改联系人");
	            	}else if(str==false){
	            		inputlsit_edit.close();
	            		$.showtip("联系人已存在");
	            	}else{
	            		linkSubmit(userIdOld,userNameOld,userIdNew,userNameNew);
	            		inputlsit_edit.close();
	            	}
	            	setTimeout("$.hidetip()",2000);
	            }
	        });
	        $("#edLinkManName").val(userNameOld);
	    });
    };
    return{
    	editclick:ykLinkClick,
    	addclick:add,
    	rendering:rendering
    }; 
})();

//投资机构备注
var investmentNote=(function(){
	//初始加载数据
	var init=function(){
			rendering();
			more();
	};
	
	//渲染投资备注
	var rendering=function(){
		var html=trade_data.noteListNull;
		if(notedata.list.length>0 && notedata.list[0]!=null){
			html=template.compile(trade_data.noteList)(notedata);
		    if(html==""){
		    	html=trade_data.noteListNull;
		    }
		}
		$("#noteList tbody").html(html);
		
		if(CommonVariable().NOTECONFIGNUM<notedata.list.length){
            $(".closeshearch").show();
            if($(".closeshearch").hasClass("active")){
            	$("[nodemore]").show();
	            $(this).html('收起<span class="glyphicon glyphicon-chevron-up"></span>');
            }else{
           	 	$("[nodemore]").hide();
            	$(".closeshearch").html('更多<span class="glyphicon glyphicon-chevron-down"></span>');
            }
           
        }else{
        	$(".closeshearch").hide();
        	$(".closeshearch").html('更多<span class="glyphicon glyphicon-chevron-down"></span>').removeClass("active");
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
		$(".noteDeleteClick").bind("click",function () {
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
	//删除投资机构备注后台请求
	var del=function(noteCode){
		$.showloading();//等待动画
		$.ajax({
    		url:"tranDeleteInvestmentNote.html",
    		type:"post",
    		async:false,
    		data:{
        			noteCode:noteCode,
        			orgCode:orgCode,
        			logintype:cookieopt.getlogintype()
    			},
    		dataType: "json",
    		success: function(data){
    			$.hideloading();//等待动画隐藏
    			if(data.message=="success"||data.message=="delete"){
                    if(data.message=="success"){
                    	$.showtip("删除成功");
                    }else{
                    	$.showtip("数据已被删除");
                    }
                	notedata.list=data.list;
                	rendering();
                }else{
                	$.showtip("删除失败");
                }
                setTimeout("$.hidetip()",2000);
    		}
    	});
	};
	
	return{
		click:delClick,
		init:init,
		rendering:rendering
	};
})();

//投资人
var investorInfoconfig=(function(){
    var initdata={
        page:{pageCount:1},
        list:[]
    };

    var init=function(){
        rend();
        addClick();
        moreClick();
    };

	var rend=function(){
		var html=trade_data.investorLabelNull;
		if(investorList.length>0 &&investorList[0]!=null){
			var data={list:investorList};
			html=template.compile(trade_data.investorLabel)(data);
		    if(html==""){
		    	html=trade_data.investorLabelNull;
		    }
		}	
		$("#investorUl").html(html);
        if(investorList.length>0 &&investorList[0]!=null){
            click();//选择投资人跳转详情
            deleteClick();//删除事件
        }

	};

    var rendChild=function(){
        var html=trade_data.investorListNull;

        if(initdata.list.length>0&&initdata.list[0]!=null){
            html=template.compile(trade_data.investorList)({list:initdata.list});
            $(".pageaction").removeClass("display-none").addClass("display-block");
        }else{
            $(".pageaction").removeClass("display-block").addClass("display-none");
        }
        $("#pageInvestorBody tbody").html(html);
        if(initdata.list.length>0&&initdata.list[0]!=null){
            click();//选择投资人跳转详情
            deleteClick();//删除事件
            pageInvestorList();
        }

    };

    //投资人更多事件
    var moreClick=function(){
        $(".invstorMore").click(function(){
        	investortable=$("#investorPage").showpage();
            pageAjax();
            returnClick();
        });
    };


    //投资人子页返回
    var returnClick=function(){
        $(".investorReturn").unbind().bind("click",function(){
            investortable.hidepage();
        });
    };


    //投资人分页请求
    var pageAjax=function(){
        $.showloading();//等待动画
        $.ajax({
            url:"findPageOrgInvestorByOrgId.html",
            type:"post",
            async:false,
            data:{orgCode:orgCode,pageCount:initdata.page.pageCount, logintype:cookieopt.getlogintype()},
            dataType: "json",
            success: function(data){
                $.hideloading();//隐藏等待动画
                if(data.message=="success"){
                    initdata.page=data.page;
                    initdata.list=data.list;
                    $(".investorLable").html("共"+data.page.totalCount+"条");
                    rendChild();
                    if(initdata.page.pageCount<=1){
                        investorList=data.list;
                        rend();
                    }

                }else{
                    $.showtip(data.message);
                }
                setTimeout("$.hidetip()",2000);
            }
        });
    };


    //投资人分页插件注册
    function pageInvestorList(){
        $('.investorPageaction').jqPagination({
            link_string	: '/?page={page_number}',
            max_page	: initdata.page.totalPage,
            current_page: initdata.page.pageCount,
            paged		: function(p) {
                if(p!=initdata.page.pageCount){
                    initdata.page.pageCount=p;
                    pageAjax();
                }
            }
        });
    }

    //添加投资人事件
    var addClick=function(){
        $(".addbtnInvestor").click(function(){
            if(orgCode==null){
                $.showtip("未发现投资机构信息");
                setTimeout("$.hidetip()",2000);
            }else{
                window.location.href="investor_add.html?investmentCode="+orgCode+"&logintype="+cookieopt.getlogintype()
                    +"&backherf=findInvestmentById.html?id="+orgCode;
            }
        });
    };


	//删除投资人事件
    var deleteClick=function(){
    	$(".investorDelClick").unbind().bind("click",function(e){
            var refBool=$(this).attr("ref-bool");
    		var code=$(this).attr("del-code");
    		var invesName=$(this).attr("name");
    		inputlsit_edit.config({
                title:"提示",//弹层标题
                html:true,//是否以html显示
                besurebtn:"确定",//确定按钮文字
                cancle:"取消",//取消按钮文字 （如果不写，则不会显示该按钮）
                htmltext:"<div style='text-align: center;font-size: 15px;'>您确定要删除<span style='color:red;'>"+invesName+"</span>吗？</div>",
                submit: function () {
                    $.showloading();//等待动画
                    $.ajax({
                        url:"delInvestorInfo.html",
                        type:"post",
                        async:false,
                        data:{
                        	invstorCode:code,
                        	investName:invesName,
                            orgCode:orgCode,
                            pageCount:initdata.page.pageCount,
                            orgName:orgName,
                            logintype:cookieopt.getlogintype()
                        },
                        dataType: "json",
                        success: function(data){
                            $.hideloading();//等待动画隐藏
                            if(data.message=="success"){
                                $.showtip("删除成功");
                                if(refBool=="false"){
                                    initdata.list=data.list;
                                    initdata.page=data.page;
                                    $(".investorLable").html("共"+data.page.totalCount+"条");
                                    rendChild();
                                    if(initdata.page.pageCount<=1){
                                        investorList=data.list;
                                        rend();
                                    }
                                }else{
                                    investorList=data.list;
                                    rend();
                                }

                            }else if(data.message=="delete"){
                            	$.showtip("数据已删除，请刷新页面");
                            }else{
                                $.showtip(data.message);
                            }
                            setTimeout("$.hidetip()",2000);
                        }
                    });
                    inputlsit_edit.close();
                },//点击确定按钮回调方法
                canmit: function () {
                    inputlsit_edit.close();
                }//点击取消按钮回调方法
            });
            
            e.stopPropagation();
    	});
    };

    //选择行跳转详情
	var click=function(){
		$(".investorLinkDetial").unbind().bind("click",function () {
			var vCode=$(this).attr("id");
			window.location.href="findInvestorDeatilByCode.html?code="+vCode+"&logintype="+cookieopt.getlogintype()
			+"&backherf=findInvestmentById.html?id="+orgCode;
		});
	};

	return{
        init:init
	};
})();

//近期交易
var tradeLinkConfig=(function(){
	var init=function(){
		var html=trade_data.tradeListNull;
		if(tradedata.list.length>0 && tradedata.list[0]!=null){
			html=template.compile(trade_data.tradeList)(tradedata);
		    if(html==""){
		    	html=trade_data.tradeListNull;
		    }
		}
		$("#tradeTable tbody").html(html);
		click();
		deleteClick();
	};
	
	var rendering=function(){
		var html=trade_data.tradeListNull;
    	if(tradedata.list.length>0 && tradedata.list[0]!=null){
    		html=template.compile(trade_data.tradeList)(tradedata);
    		$(".pageaction").removeClass("display-none").addClass("display-block");
		}else{
			$(".pageaction").removeClass("display-block").addClass("display-none");
		}
    	$("#pageTradeBody tbody").html(html);
		 click();
		 deleteClick();
        if(tradedata.list.length>0 && tradedata.list[0]!=null) {
            pageList();
        }
	};
	var click=function(){
		$(".tradeLink").bind("click",function () {
			var vCode=$(this).attr("id");
			window.location.href="findTradeDetialInfo.html?tradeCode="+vCode+"&logintype="+cookieopt.getlogintype()+"&backherf=findInvestmentById.html?id="+orgCode;
		});
	};
	
	//删除交易事件
    var deleteClick=function(){
    	$(".tradeDeleteClick").unbind().bind("click",function(e){
        	var id=$(this).attr("del-code");
            inputlsit_edit.config({
                title:"提示",//弹层标题
                html:true,//是否以html显示
                besurebtn:"确定",//确定按钮文字
                cancle:"取消",//取消按钮文字 （如果不写，则不会显示该按钮）
                htmltext:"<div style='text-align: center;font-size: 15px;'>您确定要删除该条记录吗？</div>",
                submit: function () {
                    deleteAjax(id);
                    inputlsit_edit.close();
                },//点击确定按钮回调方法
                canmit: function () {
                    inputlsit_edit.close();
                }//点击取消按钮回调方法
            });
        	e.stopPropagation();
        });
    };
    
    //删除交易ajax
    var deleteAjax=function(vId){
    	$.showloading();//等待动画
        $.ajax({
            url:"deleteInvestmentTrade.html",
            type:"post",
            async:false,
            data:{
                orgCode:orgCode,
                tradeCode:vId,
                pageCount:page.pageCount,
                logintype:cookieopt.getlogintype(),
                version:version
            },
            dataType: "json",
            success: function(data){
                $.hideloading();//隐藏等待动画
                if(data.message=="success"||data.message=="delete"){
                	if(data.message=="success"){
                		$.showtip("删除成功");
                	}else{
                		$.showtip("交易已被删除");
                	}
                	version=data.version;
                    page=data.page;
                    tradedata.list=data.list;
                    $(".tradeLable").html("共"+data.page.totalCount+"条");
                    if(page.pageCount<=1){
                    	init();
                        rendering();
                    }else{
                        rendering();
                    }
                }else{
                    $.showtip(data.message);
                }
                setTimeout("$.hidetip()",2000);
            }
        });
    };
    
    var addTradeClick=function(){
    	$(".addbtnTrade").unbind().bind("click",function(){
    		window.location.href="addTradeInfoinit.html?logintype="+cookieopt.getlogintype()+"&investmentcode="+orgCode+"&backherf=findInvestmentById.html?id="+orgCode;
    	});
    };
    
	return{
		addClick:addTradeClick,
		rendering:rendering,
		init:init
	};
})();

//会议跳转
var meetingLinkConfig=(function(){
	var click=function(){
		$(".meetingLink").bind("click",function () {
			//2016-1-31 查看权限判断
			var vMeetingVisible = $(this).attr("mt_visible");
			if(vMeetingVisible == "0"){
				 $.showtip("您没有查看此会议的权限");
				 setTimeout(function () {
                     $.hidetip();
                 },2000);
				return ;
			}
			var vCode=$(this).attr("id");
			window.location.href="meeting_info.html?meetingcode="+vCode+"&logintype="+cookieopt.getlogintype();
			
			
		});
	};
	
	return{
		click:click
	};
})();

//判断note显示行数
template.helper("nodermore", function (a,b) {
    if(a<b){
        return "";
    }else{
        return "nodemore";
    }
});

//拼接投资机构名称
function orgNameRending(orgName,orgEName){
	var vTxt=orgName+"<span class=\"small\">"+orgEName+"</span>";
	if(detailInfo.deleteflag=='1'){
		vTxt+="<span class='deleteflag'>(无效)</span>";
	}
	return vTxt;
}

//2016-4-21 删除投资机构
var delOrgConfig=(function(){
	var rending=function(){
		if(detailInfo.deleteflag=='0'){
			$(".org_del").html("标记为无效");
			deleteClick();
		}else{
			$(".org_del").html("标记为有效");
			backClick();
		}
		//加载投资机构名称,英文名称
		$("#box-title").html(orgNameRending(orgName,orgEName));
	};
	//删除投资机构
	var deleteClick=function(){
		$(".org_del").unbind().bind("click",function(){
			inputlsit_edit.config({
                title:"提示",//弹层标题
                html:true,//是否以html显示
                besurebtn:"确定",//确定按钮文字
                cancle:"取消",//取消按钮文字 （如果不写，则不会显示该按钮）
                htmltext:"<div style='text-align: center;font-size: 15px;'>您确定要标记该机构为无效吗</div>",
                submit: function () {
                	updAjax();
                    inputlsit_edit.close();
                },//点击确定按钮回调方法
                canmit: function () {
                    inputlsit_edit.close();
                }//点击取消按钮回调方法
            });
		});
	};
	//还原投资机构
	var backClick=function(){
		$(".org_del").unbind().bind("click",function(){
			inputlsit_edit.config({
                title:"提示",//弹层标题
                html:true,//是否以html显示
                besurebtn:"确定",//确定按钮文字
                cancle:"取消",//取消按钮文字 （如果不写，则不会显示该按钮）
                htmltext:"<div style='text-align: center;font-size: 15px;'>您确定要标记该机构为有效吗</div>",
                submit: function () {
                	updAjax();
                    inputlsit_edit.close();
                },//点击确定按钮回调方法
                canmit: function () {
                    inputlsit_edit.close();
                }//点击取消按钮回调方法
            });
		});
	};
	
	var updAjax=function(){
		 $.showloading();//等待动画
	        $.ajax({
	            url:"updateOrganizationDelete.html",
	            type:"post",
	            async:false,
	            data:{
	            	type:detailInfo.deleteflag,
	            	orgcode:orgCode,
	            	version:version,
	            	logintype:cookieopt.getlogintype()
	            },
	            dataType: "json",
	            success: function(data){
	                $.hideloading();//隐藏等待动画
	                if(data.message=="success"){
	                	if(detailInfo.deleteflag=='1'){
	                		$.showtip("已标记为有效");
	                	}else{
	                		$.showtip("已标记为无效");
	                	}
	                	setTimeout(function () {
                            $.hidetip();
                        },2000);
                		version=data.version;
                		detailInfo.deleteflag=data.deleteflag;
                		rending();
	                }else{
	                    $.showtip(data.message);
	                    setTimeout("$.hidetip()",2000);
	                }
	            }
	        });
	};
	
	return {
		init:rending
	};
})();

//返回
var goBackConfig=(function(){

    var click=function(){

        $(".goback").unbind().bind("click",function(){
        	if(backtype=="searchscre"){
        		searchscre();
        	}else if(backtype=="searchtext"){
        		searchtext();
        	}else if(backtype=="searchfoot"){
                searchfoot();
            }
            
        });


    };
    
    var searchscre=function(){
    	var type="hidden";
        var w = document.createElement("form");
        document.body.appendChild(w);
        var a= document.createElement("input");
        var b= document.createElement("input");
        var c= document.createElement("input");
        var d= document.createElement("input");
        var e= document.createElement("input");
        var f= document.createElement("input");
        var g= document.createElement("input");
        var h= document.createElement("input");
        var k= document.createElement("input");
        var l= document.createElement("input");
        var m= document.createElement("input");//2016-4-21 add 状态
        var n= document.createElement("input");//2016-5-3 add 类型
        var o= document.createElement("input");
        var p= document.createElement("input");
        a.type= b.type= c.type= d.type= e.type= f.type= g.type= h.type= k.type= l.type =m.type=n.type=o.type=p.type = type;
        a.name="kuang";
        a.value=organization_search.kuang;
        b.name="hangye";
        b.value=organization_search.hangye;
        c.name="payatt";
        c.value=organization_search.payatt;
        d.name="currency";
        d.value=organization_search.currency;
        e.name="scale";
        e.value=organization_search.scale;
        f.name="stage";
        f.value=organization_search.stage;
        g.name="feature";
        g.value=organization_search.feature;
        h.name="competition";
        l.value=organization_search.bground;
        l.name="bground";
        h.value=organization_search.competition;
        k.name="logintype";
        k.value=cookieopt.getlogintype();
        m.name="deleteflag";
        m.value=organization_search.deleteflag;
        n.name="invtype";
        n.value=organization_search.invtype;
        o.name="type";
        o.value="goback";
        p.name="pageCount";
        p.value=organization_search.pageCount;

        w.appendChild(a);
        w.appendChild(b);
        w.appendChild(c);
        w.appendChild(d);
        w.appendChild(e);
        w.appendChild(f);
        w.appendChild(g);
        w.appendChild(h);
        w.appendChild(k);
        w.appendChild(l);
        w.appendChild(m);
        w.appendChild(n);
        w.appendChild(o);
        w.appendChild(p);
        w.action = "searchMoreCondition.html";
        w.method="post";

        w.submit();
    };
    var searchtext=function(){
    	var type="hidden";
        var w = document.createElement("form");
        document.body.appendChild(w);
        var a= document.createElement("input");
        var b= document.createElement("input");
        var c= document.createElement("input");
        var d= document.createElement("input");
        
        a.type= b.type= c.type= d.type=type;
        a.name="logintype";
        a.value=cookieopt.getlogintype();
        b.name="type";
        b.value=1;
        c.name="name";
        c.value=organization_search.name;
        d.name="pageCount";
        d.value=organization_search.pageCount;
        
        w.appendChild(a);
        w.appendChild(b);
        w.appendChild(c);
        w.appendChild(d);
        
        w.action = "gotoInvestmentList.html";
        w.method="post";

        w.submit();
        
    };


    var searchfoot=function(){
        var type="hidden";
        var w = document.createElement("form");
        document.body.appendChild(w);
        var a= document.createElement("input");
        var b= document.createElement("input");
        a.type= b.type=type;
        a.name="logintype";
        a.value=cookieopt.getlogintype();
        b.name="backtype";
        b.value="myfoot";
        w.appendChild(a);
        w.appendChild(b);
        w.action = "myfoot_search.html";
        w.method="post";

        w.submit();
    };

    return{
        init:click
    };
})();