/**
 * Created by shbs-tp004 on 15-9-9.
 */
var optactive=0;
var menuactive=1;
$(function () {
    initLoadConfig.data.tradeCode=tradeInfo!=null?tradeInfo.base_trade_code:"";//交易Code
	//初始加载数据
    initLoadConfig.init();
    alone_edit.config_function();
    $('.tradeDate').datepicker();
    //加载交易标签
    labelconfig.init();
    //融资信息
    tradeInfoconfig.init();
    //备注
    tradeNoteconfig.init();
    //更新记录
    updlogconfig.initClick();
    //删除交易
    delTradeconfig.click();
    if(backherf!=""){
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
        		window.location.href="trade_search.html?logintype="
                    +cookieopt.getlogintype()+"&backtype="+backherf;
        	}else{
        		window.location.href=backherf+"&logintype="+cookieopt.getlogintype();
        	}
        });
    }
});

//初始加载
var initLoadConfig=(function(){
	var initdata={
            tradeCode:"",
			version:"",
            tradepage:{},
            tradeInfoList:[],
            baskList:[],
            induList:[],
            stageList:[],
            noteList:{list:[],initSize:CommonVariable().NOTECONFIGNUM},
            showtrademore:false
	};
	
	var initLoadAjax=function(){
		$.showloading();//等待动画
		$.ajax({
			url:"initTradeDetialInfo.html",
			type:"post",
			async:false,
			data:{
					tradeCode:initLoadConfig.data.tradeCode,
	    			logintype:cookieopt.getlogintype()
				},
			dataType: "json",
			success: function(data){
				$.hideloading();//等待动画隐藏
				initdata.version=data.version;//排他锁版本号
                initdata.tradepage=data.page;//基金分页
                initdata.tradeInfoList=data.tradeInfoList;//融资信息
                initdata.baskList=data.baskList;//关注筐集合
                initdata.induList=data.induList;//行业集合
                initdata.stageList=data.stageList;//阶段集合
                initdata.noteList.list=data.noteList;//交易备注
				if(data.message!="success"){
					$.showtip(data.message,"error",2000);
					if(backherf!=""){
						setTimeout(function(){
							window.location.href=backherf+"&logintype="+cookieopt.getlogintype();
						},2000);
					}else{
						setTimeout(function(){
							window.location.href="trade_search.html?logintype="+cookieopt.getlogintype();
						},2000);
					}
					
				}
			}
		});
	};

    var init=function(){
        initLoadAjax();

        labelconfig.data.baskJson=eval(tradeInfo.view_trade_baskcont);//关注筐
        labelconfig.data.induJson=eval(tradeInfo.view_trade_inducont);//行业

        labelconfig.data.stageCode=tradeInfo.base_trade_stage;//阶段code
        //阶段
        labelconfig.data.stageCont=tradeInfo.base_trade_stagecont;
        //被投公司code
        labelconfig.data.companyCode=tradeInfo.base_comp_code;
        //被投公司简称
        labelconfig.data.companyName=tradeInfo.base_comp_name;
        //投资日期
        labelconfig.data.tradeDate=tradeInfo.base_trade_date;
        //融资金额
        labelconfig.data.tradeMoney=tradeInfo.base_trade_money;
        //公司估值
        labelconfig.data.companyMoney=tradeInfo.base_trade_comnum;
        //公司估值类型
        labelconfig.data.companyMoneyType=tradeInfo.base_trade_comnumtype;

        $("#createDate").val(labelconfig.data.tradeDate);
        $("#createTradeMoney").val(labelconfig.data.tradeMoney);
        $("#createCompanyMoney").val(labelconfig.data.companyMoney);
        $("#companyMoneyType").val(labelconfig.data.companyMoneyType);
        
    };


    return {
        data:initdata,
        init:init
    };


})();

//交易标签
var labelconfig=(function(){
    var initdata={
        baskJson:{},//关注筐
        induJson:{},//行业
        stageCode:"",//阶段code
        stageCont:"",//阶段
        companyCode:"",//被投公司code
        companyName:"",//被投公司简称
        tradeDate:"",//投资日期
        tradeMoney:"",//融资金额
        companyMoney:"",//公司估值
        companyMoneyType:""//公司估值类型
    };

    var init=function(){
        //初始公司
        initCompany();
        //初始日期
        initTradeDate();
        //日期保存事件
        tradeDateClick();
        //初始筐
        initDask();
        //初始行业
        initIndu();
        // 初始阶段
        initStage();
        //融资金额
        initTradeMoney();
        //融资金额编辑保存
        tradeMoneyClick();
        //公司估值
        initCompanyMoney();
        //公司估值编辑保存
        companyMoneyClick();
        //公司估值
        initCompanyMoneyType();
        //公司估值编辑保存
        companyMoneyTypeClick();
        
    };


    //加载被投公司
    var initCompany=function(){
        var html=table_data.contentNull;
        if(initdata.companyName!=null && initdata.companyName!=""){
            html=initdata.companyName;
        }
        $("#company").html(html);
        $(".companyClick li").unbind().bind("click",function () {
            if(initdata.companyCode!=null && initdata.companyCode!=""){
                window.location.href="findCompanyDeatilByCode.html?logintype="+cookieopt.getlogintype()+"&code="+initdata.companyCode;
            }else{
                $.showtip("未发现公司","normal",2000);
            }

        });
    };

    //加载交易时间
    var initTradeDate=function(){
        //加载时间
        var html=table_data.contentNull;
        if(initdata.tradeDate!=null && initdata.tradeDate!=""){
            html=initdata.tradeDate;
        }
        $("#tradeDate").html(html);

    };

    //加载交易时间单击事件
    var tradeDateClick=function(){
        $(".tradeDateSaveClick").unbind().bind("click",function(){
        	var $this=$(this);
            var date=$("#createDate").val();
            if(initLoadConfig.data.tradeCode==null){
                $.showtip("未发现交易信息","normal",2000);
            }else if(date==initdata.tradeDate){
                $.showtip("未修改数据","normal",2000);
            }else if(date==""){
            	$.showtip("时间不能为空","normal",2000);
            }else{
                $.showloading();//等待动画
                $.ajax({
                    url:"updateTradeDetialInfo.html",
                    type:"post",
                    async:false,
                    data:{
                        base_trade_code:initLoadConfig.data.tradeCode,
                        type:'base_trade_date',
                        logintype:cookieopt.getlogintype(),
                        oldData:initdata.tradeDate,
                        base_trade_date:date,
                        version:initLoadConfig.data.version
                    },
                    dataType: "json",
                    success: function(data){
                        $.hideloading();//等待动画隐藏
                        if(data.message=="success"){
                            $.showtip("保存成功","success",2000);
                            initLoadConfig.data.version=data.version;
                            initdata.tradeDate=data.newData;
                            initTradeDate();
                            alone_edit.close($this.parent().parent().children(".canEdit"));
                        }else{
                            $.showtip(data.message,"error",2000);
                        }
                    }
                });
            }
        });

    };

    //初始加载关注筐
    var initDask=function(){
        var html="";
        if(initdata.baskJson!=null && initdata.baskJson!=""){
            var data={list:initdata.baskJson};
            html=template.compile(table_data.labelInfoList)(data);
        }
        $("#ul-bask").html(html);
        refreshladle($(".ul-bask"));
        daskClick();
    };

    //关注筐事件
    var daskClick=function(){
        var basklist=dicListConfig(true,initLoadConfig.data.baskList,initdata.baskJson);
        $(".baskClick").unbind().bind("click",function () {
            var $this=$(this);
            tip_edit.config({
                $this:$this,
                title:"关注筐",
                list:basklist,
                radio:false,
                besure: function () {
                    var oldData=oldContent(initdata.baskJson);
                    var newData=choiceContent();
                    if(initLoadConfig.data.tradeCode==null||initLoadConfig.data.tradeCode==""){
                        $.showtip("未发现交易信息","normal",2000);
                    }else if(oldData==newData){
                        $.showtip("未修改数据","normal",2000);
                    }else{
                        $.showloading();//等待动画
                        $.ajax({
                            url:"updateTradeInfoLabel.html",
                            type:"post",
                            async:false,
                            data:{
                                type:'trade-Lable-bask',
                                tradeCode:initLoadConfig.data.tradeCode,
                                logintype:cookieopt.getlogintype(),
                                oldData:oldData,
                                newData:newData,
                                version:initLoadConfig.data.version
                            },
                            dataType: "json",
                            success: function(data){
                                $.hideloading();//等待动画隐藏
                                if(data.message=="success"){
                                    $.showtip("保存成功","success",2000);
                                    initdata.baskJson=data.list;
                                    initLoadConfig.data.version=data.version;
                                    initDask();
                                    tip_edit.close();
                                }else{
                                    $.showtip(data.message,"error",2000);
                                }
                            }
                        });

                    }
                }
            });
        });
    };

    var initIndu=function(){
    	var html="";
        if(initdata.induJson!=null && initdata.induJson!=""){
            var data={list:initdata.induJson};
            html=template.compile(table_data.labelInfoList)(data);
        }
        $("#ul-indu").html(html);
        refreshladle($(".ul-indu"));
        induClick();
    };

    var induClick=function(){
        //行业弹层
        var indulist=dicListConfig(false,initLoadConfig.data.induList,initdata.induJson);
        $(".induClick").unbind().bind("click",function () {
            var $this=$(this);
            tip_edit.config({
                $this:$this,
                title:"行业",
                list:indulist,
                radio:false,
                besure: function () {
                    var oldData=oldContent(initdata.induJson);
                    var newData=choiceContent();
                    if(initLoadConfig.data.tradeCode==null||initLoadConfig.data.tradeCode==""){
                        $.showtip("未发现交易信息","normal",2000);
                    }else if(oldData==newData){
                        $.showtip("未修改数据","normal",2000);
                    }else{
                        $.showloading();//等待动画
                        $.ajax({
                            url:"updateTradeInfoLabel.html",
                            type:"post",
                            async:false,
                            data:{
                                type:'trade-Lable-indu',
                                tradeCode:initLoadConfig.data.tradeCode,
                                logintype:cookieopt.getlogintype(),
                                oldData:oldData,
                                newData:newData,
                                version:initLoadConfig.data.version
                            },
                            dataType: "json",
                            success: function(data){
                                $.hideloading();//等待动画隐藏
                                if(data.message=="success"){
                                    $.showtip("保存成功","success",2000);
                                    initdata.induJson=data.list;
                                    initLoadConfig.data.version=data.version;
                                    initIndu();
                                    tip_edit.close();
                                }else{
                                    $.showtip(data.message,"error",2000);
                                }
                            }
                        });
                    }
                }
            });
        });
    };

    //加载阶段
    var initStage=function(){
        var html="";//table_data.labelNull;
        if(initdata.stageCont!=null && initdata.stageCont!=""){
            html="<li>"+initdata.stageCont+"</li>";
        }
        $("#ul-stage").html(html);
        stageClick();
    };

    var stageClick=function(){
        //阶段弹层
        $(".stageClick").unbind().bind('click',function () {
            var stageJson=eval([{'code':initdata.stageCode,'name':initdata.stageCont}]);
            var stagelist=dicListConfig(false,initLoadConfig.data.stageList,stageJson);
            var $this=$(this);
            tip_edit.config({
                $this:$this,
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
                    if(initLoadConfig.data.tradeCode==null||initLoadConfig.data.tradeCode==""){
                        $.showtip("未发现交易信息","normal",2000);
                    }else if(initdata.stageCode==newData || (initdata.stageCode==null&&newData=="[]")){
                        $.showtip("未修改数据","normal",2000);
                    }else{
                        $.showloading();//等待动画
                        $.ajax({
                            url:"updateTradeDetialInfo.html",
                            type:"post",
                            async:false,
                            data:{
                                base_trade_code:initLoadConfig.data.tradeCode,
                                type:'base_trade_stage',
                                logintype:cookieopt.getlogintype(),
                                oldData:initdata.stageCont,
                                newStageName:newDataName,
                                base_trade_stage:newData,
                                version:initLoadConfig.data.version
                            },
                            dataType: "json",
                            success: function(data){
                                $.hideloading();//等待动画隐藏
                                if(data.message=="success"){
                                    $.showtip("保存成功","success",2000);
                                    initLoadConfig.data.version=data.version;
                                    initdata.stageCont=data.newData;
                                    initdata.stageCode=data.newCode;
                                    initStage();
                                    tip_edit.close();
                                }else{
                                    $.showtip(data.message,"error",2000);
                                }
                            }
                        });
                    }
                }
            });
        });
    };

    //加载融资额
    var initTradeMoney=function(){
        var html=table_data.contentNull;
        if(initdata.tradeMoney!=null && initdata.tradeMoney!=""){
            html=initdata.tradeMoney;
        }
        $("#tradeMoney").html(html);
    };

    //融资额单击事件
    var tradeMoneyClick=function(){
    	$(".tradeMoneySaveClick").unbind().bind("click",function () {
    		var $this=$(this);
            var money=$("#createTradeMoney").val();
            if(initLoadConfig.data.tradeCode==null){
                $.showtip("未发现交易信息","normal",2000);
            }else if(money.trim()==""){
                $.showtip("融资金额不能为空","normal",2000);
            }else if(initdata.tradeMoney==money.trim()){
                $.showtip("未修改数据","normal",2000);
            }else{
                $.showloading();//等待动画
                $.ajax({
                    url:"updateTradeDetialInfo.html",
                    type:"post",
                    async:false,
                    data:{
                        base_trade_code:initLoadConfig.data.tradeCode,
                        type:'base_trade_money',
                        logintype:cookieopt.getlogintype(),
                        oldData:initdata.tradeMoney,
                        base_trade_money:money,
                        version:initLoadConfig.data.version
                    },
                    dataType: "json",
                    success: function(data){
                        $.hideloading();//等待动画隐藏
                        if(data.message=="success"){
                            $.showtip("保存成功","success",2000);
                            initdata.tradeMoney=data.newData;
                            initLoadConfig.data.version=data.version;
                            initTradeMoney();
                            alone_edit.close($this.parent().parent().children(".canEdit"));
                        }else{
                            $.showtip(data.message,"error",2000);
                        }
                    }
                });
            }
    	});
    };

    //加载公司估值
    var initCompanyMoney=function(){
        var html=table_data.contentNull;
        if(initdata.companyMoney!=null && initdata.companyMoney!=""){
            html=initdata.companyMoney;
        }
        $("#companyMoney").html(html);
    };

    //公司估值单击事件
    var companyMoneyClick=function(){
        $(".companyMoneySaveClick").unbind().bind("click",function () {
            var $this=$(this);
            var money=$("#createCompanyMoney").val();
            if(initLoadConfig.data.tradeCode==null){
                $.showtip("未发现交易信息","normal",2000);
            }else if(money.trim()==""){
                $.showtip("公司估值不能为空","normal",2000);
            }else if(initdata.companyMoney==money.trim()){
                $.showtip("未修改数据","normal",2000);
            }else{
                $.showloading();//等待动画
                $.ajax({
                    url:"updateTradeDetialInfo.html",
                    type:"post",
                    async:false,
                    data:{
                        base_trade_code:initLoadConfig.data.tradeCode,
                        type:'base_trade_comnum',
                        logintype:cookieopt.getlogintype(),
                        oldData:initdata.companyMoney,
                        base_trade_comnum:money,
                        version:initLoadConfig.data.version
                    },
                    dataType: "json",
                    success: function(data){
                        $.hideloading();//等待动画隐藏
                        if(data.message=="success"){
                            $.showtip("保存成功","success",2000);
                            initLoadConfig.data.version=data.version;
                            initdata.companyMoney=data.newData;
                            initCompanyMoney();
                            alone_edit.close($this.parent().parent().children(".canEdit"));
                        }else{
                            $.showtip(data.message,"error",2000);
                        }
                    }
                });
            }
        });
    };

    
  //加载公司估值类型
    var initCompanyMoneyType=function(){
        var html=table_data.contentNull;
        if(initdata.companyMoneyType!=null && initdata.companyMoneyType!=""){
            html="("+(initdata.companyMoneyType=="0"?"获投前":initdata.companyMoneyType=="1"?"获投后":"未知")+")";
        }
        $("#companyMoneyType").html(html);
        if(initdata.companyMoneyType!=null){
        	$("#createCompanyMoneyType").val(initdata.companyMoneyType);
        }
    };

    //公司估值类型单击事件
    var companyMoneyTypeClick=function(){
        $(".comMonTypeSaveClick").unbind().bind("click",function () {
            var $this=$(this);
            var money=$("#createCompanyMoneyType").val();
            if(initLoadConfig.data.tradeCode==null){
                $.showtip("未发现交易信息","normal",2000);
            }else if(initdata.companyMoneyType==money){
                $.showtip("未修改数据","normal",2000);
            }else if(labelconfig.data.companyMoney==null||labelconfig.data.companyMoney==""){
            	$.showtip("公司估值不能为空","normal",2000);
            }else{
                $.showloading();//等待动画
                $.ajax({
                    url:"updateTradeDetialInfo.html",
                    type:"post",
                    async:false,
                    data:{
                        base_trade_code:initLoadConfig.data.tradeCode,
                        type:'base_trade_comnum',
                        logintype:cookieopt.getlogintype(),
                        comMoneyType:initdata.companyMoneyType,
                        base_trade_comnumtype:money,
                        version:initLoadConfig.data.version
                    },
                    dataType: "json",
                    success: function(data){
                        $.hideloading();//等待动画隐藏
                        if(data.message=="success"){
                            $.showtip("保存成功","success",2000);
                            initLoadConfig.data.version=data.version;
                            initdata.companyMoneyType=data.newType;
                            initCompanyMoneyType();
                            alone_edit.close($this.parent().parent().children(".canEdit"));
                        }else{
                            $.showtip(data.message,"error",2000);
                        }
                    }
                });
            }
        });
    };

    return {
        data:initdata,
        init:init
    };
})();


var editLabelConfig=(function(){

    var init=function(){
        date();
    };

	var date=function(){

	};

    return {
        init:init
    };
})();


//融资信息
var tradeInfoconfig=(function(){
    var opt={
        refBool:true,
        list:[],
        page:{pageCount:1},
        invement:{},
        investor:{}
    };

    var savedata={
        searchorg:"",
        searchorginv:"",
        searchcom:"",
        searchcominv:""
    };

    var init=function(){
        rendtrade();
        addClick();
        moreClick();
    };

	var rendtrade=function(){
		var html=table_data.tradeInvesListNull;
		if(initLoadConfig.data.tradeInfoList.length>0&&initLoadConfig.data.tradeInfoList[0]!=null){
			html=template.compile(table_data.tradeInvesList)({list:initLoadConfig.data.tradeInfoList});
			if(html==""){
				html=table_data.tradeInvesListNull;
			}
		}
		$(".tradeBody").html(html);

		if(initLoadConfig.data.tradeInfoList.length>0&&initLoadConfig.data.tradeInfoList[0]!=null){
			click();//融资信息选中行事件
			delClick();//删除按钮事件
		}
	};
    var rendtradeChild=function() {
        var html = table_data.tradeInvesListNull;
        if (opt.list.length > 0 && opt.list[0] != null) {
            html = template.compile(table_data.tradeInvesList)({list: opt.list});
            if (html == "") {
                html = table_data.tradeInvesListNull;
            }
            jqPagination();
        }else{
        	$("#pagination1").hide();
        }
        $(".tradeBodyChild").html(html);

        if (opt.list.length > 0 && opt.list[0] != null) {
            click();//融资信息选中行事件
            delClick();//删除按钮事件
        }
    };
    //融资信息选中行事件
    var click=function(){
        $(".tradeBody tr").unbind().bind("click",function(){
            var id=$(this).attr("id");
            tradedetial(id);
        });
    };

    //融资信息详情展示
    var tradedetial=function(vId){
            var data={};
            var index=null;
        //zzg 修改　需求变更
        if(initLoadConfig.data.showtrademore){
            data=opt.list[vId];
        }else{
            data=initLoadConfig.data.tradeInfoList[vId];
        }


        data.addinvestor=false;
            var htmlText=template.compile(table_data.tradeInvesDetail)(data);
            index=layer.open({
                type: 1,
                title: '融资信息详情',
//                shadeClose: false,
//                shade: 0.6,
                scrollbar: false,
//                maxmin: false, //开启最大化最小化按钮
                skin : 'layui-layer-rim', //加上边框
                area: ['418px'],
                content: htmlText
            });


            $("#btn_investor_add").unbind().bind("click", function () {
                if(!data.addinvestor){
                    $(".select2").hide();
                    $("#select_investor_input").show();

                    $("#select_investor_input").val(savedata.searchorginv);

                    $(this).removeClass("btn_icon_add").addClass("btn_icon_search");
                }else{
                    $(".select2").show();
                    $("#select_investor_input").hide().val("");
                    $(this).removeClass("btn_icon_search").addClass("btn_icon_add");
                    investor_select();
                }
                data.addinvestor=!data.addinvestor;
            });
        $("#collvote").val(data.base_trade_collvote);
        $("#subpay").val(data.base_trade_subpay);
        $("#ongam").val(data.base_trade_ongam);

        savedata.searchorginv=data.base_investor_name;

        investor_select();
        function investor_select(){
            data.investor_select=$("#select_investor").select2({
                placeholder: "请选择投资人",//文本框的提示信息
                minimumInputLength: 0,   //至少输入n个字符，才去加载数据
                allowClear: true,  //是否允许用户清除文本信息
                ajax:{
                    dataType:"json",
                    cache: true,
                    type:"post",
                    delay: 250,//加载延迟
                    url: "queryinvestorlistByinvementcode.html",
                    data: function (params) {

                        savedata.searchorginv=(params.term||"");

                        return{
                            name:params.term||"",
                            invementcode:data.base_investment_code,
                            pageSize:CommonVariable().SELSECLIMIT,
                            pageCount:"1",
                            logintype:cookieopt.getlogintype(),
                            type:"1"
                        };
                    },
                    processResults: function (json, page) {
                        for(var i=0;i<json.list.length;i++){
                            json.list[i].id=json.list[i].base_investor_code;
                            json.list[i].text=json.list[i].base_investor_name;
                        }
                        return {
                            results: json.list//返回结构list值
                        };
                    }
                }
            });
        }
        //点击修改
        $(".tradeTrueBtnClick").unbind().bind("click",function(){
            var ischange=false;
            if(data.addinvestor){
                if($("#select_investor_input").val().trim()!=""){
                    ischange=true;
                }else if(data.base_investor_name!=""&&$("#select_investor_input").val().trim()==""){
                    ischange=true;
                }
            }else{
                var investor_code=data.investor_select.select2("data")[0]?data.investor_select.select2("data")[0].id:null;
                var investor_name=data.investor_select.select2("data")[0]?data.investor_select.select2("data")[0].text:"";
                if(data.base_investor_code!=investor_code&&investor_code!=""){
                    ischange=true;
                }
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
                $.showtip("内容未做更改","normal",2000);
                return;
            }else{
                var json={};
                json.tradecode=data.base_trade_code;
                json.base_investment_code=data.base_investment_code;
                if(data.addinvestor){
                    json.base_investor_code="";
                    json.base_investor_name=$("#select_investor_input").val().trim();

                }else{
                    json.base_investor_code=data.investor_select.select2("data")[0]?data.investor_select.select2("data")[0].id:"";
                    json.base_investor_name=data.investor_select.select2("data")[0]?data.investor_select.select2("data")[0].text:"";
                }
                json.base_trade_collvote=$("#collvote").val();
                json.base_trade_ongam=$("#ongam").val();
                json.base_trade_subpay=$("#subpay").val();
                json.base_trade_inmoney=$("#inmoney").val().trim();
                json.version=initLoadConfig.data.version;
                json.base_investment_name=data.base_investment_name;
                update_list_tade(JSON.stringify(json));
            }



            layer.close(index);
        });

        function update_list_tade(json) {
            delete  data.investor_select;
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

                    savedata.searchorginv="";

                    if(data.message=="success"){
                        if(initLoadConfig.data.showtrademore){
                            initLoadConfig.init();
                            tradeInfoconfig.init();
                            tradePageAjax();
                        }else{
                            initLoadConfig.init();
                            tradeInfoconfig.init();
                        }

                        $.showtip("保存成功","normal",2000);
                    }else if(data.message=="notrade"){
                        $.showtip("该交易已不存在","normal",2000);
                    }else if(data.message=="havechange"){
                        $.showtip("已被修改,请刷新页面再修改");
                    }
                }
            });
        }

        //zzg 修改结束　需求变更
    };

    //更多
    var moreClick=function(){
        $(".tradeMoreClick").unbind().bind("click",function(){
            if(initLoadConfig.data.tradeCode==null||initLoadConfig.data.tradeCode==""){
                $.showtip("未发现交易信息","normal",2000);
                return;
            }
            layer.open({
                type: 1,
                title: '融资信息',
//                shadeClose: false,
//                shade: 0.6,
//                scrollbar: false,
//                maxmin: false, //开启最大化最小化按钮
                skin : 'layui-layer-rim', //加上边框
                area: ['700px', '350px'],
                content: table_data.tradeListChild,
                cancel: function(index){
                    initLoadConfig.data.showtrademore=false;
                }
            });
            tradePageAjax();
            initLoadConfig.data.showtrademore=true;
        });
    };

    //分页请求查询
    var tradePageAjax=function(){
        $.showloading();//等待动画
        $.ajax({
            url:"findPageTradeInvesInfo.html",
            type:"post",
            async:true,
            data:{
                tradeCode:initLoadConfig.data.tradeCode,
                pageCount:opt.page.pageCount,
                logintype:cookieopt.getlogintype()
            },
            dataType: "json",
            success: function(data){
                $.hideloading();//隐藏等待动画
                if(data.message=="success"){
                    opt.page=data.page;
                    opt.list=data.list;
                    if(data.page.totalCount>0){
                    	$(".totalSize").show();
                    	$(".totalSize").html("<li>共"+data.page.totalCount+"条</li>");
                    }else{
                    	$(".totalSize").hide();
                    }
//                    initLoadConfig.data.tradeInfoList=data.list;
                    rendtradeChild();//渲染子页
                }else{
                    $.showtip(data.message,"normal",2000);
                }
            }
        });
    };

    var delClick=function(){
        $(".deleteTradeClick").unbind().bind("click",function(e){
            //判断是否是弹层请求
            if($(".tradeListChild").length>0){
                opt.refBool=false;
            }else{
                opt.refBool=true;
            }
            var id=$(this).attr("del-code");
            layer.confirm('您确定要删除该条记录吗？', {
                title:"提示",
                icon: 0,
                btn: ['确定','取消'] //按钮
            }, function(index){
                delAjax(id);
                layer.close(index);
            }, function(index){
                layer.close(index);
            });
            e.stopPropagation();
        });
    };

    var delAjax=function(vId){
        var orgName,investor,money;

        for ( var i = 0; i < initLoadConfig.data.tradeInfoList.length; i++) {
            if(initLoadConfig.data.tradeInfoList[i].base_investment_code==vId){
                orgName=initLoadConfig.data.tradeInfoList[i].base_investment_name;
                investor=initLoadConfig.data.tradeInfoList[i].base_investor_name;
                money=initLoadConfig.data.tradeInfoList[i].base_trade_inmoney;
                break;
            }
        }
        $.showloading();//等待动画
        $.ajax({
            url:"deleteTradeInvesInfo.html",
            type:"post",
            async:false,
            data:{
                refBool:opt.refBool,
                orgName:orgName,
                investor:investor,
                money:money,
                orgCode:vId,
                tradeCode:initLoadConfig.data.tradeCode,
                pageCount:opt.page.pageCount,
                logintype:cookieopt.getlogintype(),
                version:initLoadConfig.data.version
            },
            dataType: "json",
            success: function(data){
                $.hideloading();//等待动画隐藏
                if(data.message=="success"){
                    $.showtip("删除成功","normal",2000);
                    opt.list=data.list;
                    initLoadConfig.data.version=data.version;
                    opt.page=data.page;
                    if(opt.refBool==false){
                    	if(data.page.totalCount>0){
                        	$(".totalSize").show();
                        	$(".totalSize").html("<li>共"+data.page.totalCount+"条</li>");
                        }else{
                        	$(".totalSize").hide();
                        }
                        rendtradeChild();//渲染融资信息子页
                        if(opt.page.pageCount==1){
                            initLoadConfig.data.tradeInfoList=data.list;
                            rendtrade();
                        }

                    }else{
                    	initLoadConfig.data.tradeInfoList=data.list;
                        rendtrade();//渲染融资信息
                    }

                }else{
                    $.showtip(data.message,"error",2000);
                }
            }
        });
    };

	
	var addClick=function(){
		$(".btn_icon_add").unbind().bind("click",function(){
            if(initLoadConfig.data.tradeCode==null||initLoadConfig.data.tradeCode==""){
                $.showtip("未发现交易信息","normal",2000);
                return;
            }
			layer.open({
                type: 1,
                title: '添加融资信息',
//                shadeClose: false,
//                shade: 0.6,
//                scrollbar: false,
//                maxmin: false, //开启最大化最小化按钮
                skin : 'layui-layer-rim', //加上边框
                area: ['400px', '350px'],
                content: table_data.tradeAddList
            });

            //投资机构注册select2
			select2_investment();
            //投资人注册
            opt.investor=$("#select_investor").select2({
                placeholder: "请先选择机构",//文本框的提示信息
                minimumInputLength: 0,   //至少输入n个字符，才去加载数据
                allowClear: false  //是否允许用户清除文本信息
            });
            investmentclick();
            investorclick();
            saveClick();
		});
		
	};
	//注册投资机构插件
	var select2_investment=function(){
		opt.invement=$("#select_org").select2({
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

                    savedata.searchorg=(params.term||"");

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
	};
	
	//注册投资人构插件
	var select2_investor=function(){
        var code="";
        if(opt.invement!=null){
            $('#select_investor option').remove();
            var inves=opt.invement.select2("data");
            if(inves.length>0&&inves[0].id!=""){
                code=inves[0].id;
            }else{
                if($("#btn_investor_add").hasClass("btn_icon_add")){
                    opt.investor=$("#select_investor").select2({
                        placeholder: "请先选择机构",//文本框的提示信息
                        minimumInputLength: 0,   //至少输入n个字符，才去加载数据
                        allowClear: false  //是否允许用户清除文本信息
                    });
                }
                return;
            }
        }

        if($("#btn_investor_add").hasClass("btn_icon_add")){
            opt.investor=$("#select_investor").select2({
                placeholder: "请选择投资人",//文本框的提示信息
                minimumInputLength: 0,   //至少输入n个字符，才去加载数据
                allowClear: false,  //是否允许用户清除文本信息
                ajax:{
                    dataType:"json",
                    cache: true,
                    type:"post",
                    delay: 250,//加载延迟
                    url: "queryinvestorlistByinvementcode.html",
                    data: function (params) {

                        savedata.searchorginv=(params.term||"");

                        return{
                            name:params.term||"",
                            invementcode:code,
                            pageSize:CommonVariable().SELSECLIMIT,
                            pageCount:"1",
                            logintype:cookieopt.getlogintype(),
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
                }
            });
        }


	};
    //投资机构添加搜索事件
	var investmentclick=function(){
		$("#btn_investment_add").unbind().bind("click",function(){
			if($(this).hasClass("btn_icon_search")){
				$(this).removeClass("btn_icon_search").addClass("btn_icon_add");
				var html="<select id=\"select_org\" class=\"select2\" onchange=\"changeInvestment()\"></select>";
                $(".investmentSelect").html(html);
                select2_investment();
                opt.investor=null;
                if($("#btn_investor_add").hasClass("btn_icon_add")){
                    $('#select_investor option').remove();
                    opt.investor=$("#select_investor").select2({
                        placeholder: "请先选择机构",//文本框的提示信息
                        minimumInputLength: 0,   //至少输入n个字符，才去加载数据
                        allowClear: false  //是否允许用户清除文本信息
                    });

                }
			}else{
                $(this).removeClass("btn_icon_add").addClass("btn_icon_search");
                var html="<input id=\"select_org\" type=\"text\" class=\"inputMoney\" maxlength=\"200\">";
				$(".investmentSelect").html(html);
                opt.invement=null;
                $("#select_org").val(savedata.searchorg);
                if($("#btn_investor_add").hasClass("btn_icon_add")){
                    select2_investor();
                }

            }
		});
	};
	//投资人添加搜索事件
	var investorclick=function(){
		$("#btn_investor_add").unbind().bind("click",function(){
            if($(this).hasClass("btn_icon_search")){
                $(this).removeClass("btn_icon_search").addClass("btn_icon_add");
                var html="<select id=\"select_investor\" class=\"select2\" \"></select>";
                $(".investorSelect").html(html);
                select2_investor();
            }else{
                $(this).removeClass("btn_icon_add").addClass("btn_icon_search");
                var html="<input id=\"select_investor\" type=\"text\" class=\"inputMoney\" maxlength=\"200\">";
                $(".investorSelect").html(html);

                $("#select_investor").val(savedata.searchorginv);

                opt.investor=null;
            }
		});
	};

    //保存按钮
    var saveClick=function(){
        $(".saveTradeBtnClick").unbind().bind("click",function(){
            var orgcode="";
            var orgname="";
            var investorcode="";
            var investorname="";
            if($("#btn_investment_add").hasClass("btn_icon_add")){
                var inv=opt.invement.select2("data");
                if(inv.length>0&&inv[0].id!=""){
                    orgcode=inv[0].id;
                    orgname=inv[0].text;
                }
            }else{
                orgname=$("#select_org").val();
            }
            if($("#btn_investor_add").hasClass("btn_icon_add")){
                var inv=opt.investor.select2("data");
                if(inv.length>0&&inv[0].id!=""){
                    investorcode=inv[0].id;
                    investorname=inv[0].text;
                }
            }else{
                investorname=$("#select_investor").val();
            }
            var bool=0;
            if(initLoadConfig.data.tradeInfoList.length>0&&initLoadConfig.data.tradeInfoList[0]!=null){
                for ( var i = 0; i < initLoadConfig.data.tradeInfoList.length; i++) {
                    if(initLoadConfig.data.tradeInfoList[i].base_investment_code==orgcode){
                        bool=1;
                        break;
                    }
                }
            }
            var collvote=$("#collvote").val();
            var ongam=$("#ongam").val();
            var subpay=$("#subpay").val();
            var inmoney=$("#inmoney").val();
            if(bool==1){
                $.showtip("当前机构已存在","normal",2000);
            }else if(orgcode==""&&orgname.trim()==""){
                $.showtip("投资机构不能为空","normal",2000);
            }
            //zzg 修改
//            else if(investorcode==""&&investorname.trim()==""){
//                $.showtip("投资人不能为空");
//            }
            //zzg 修改结束　
            else{
                saveTradeAjax(orgcode,orgname,investorcode,investorname,collvote,ongam,subpay,inmoney);
            }
        });
    };

    //添加融资信息提交
    var saveTradeAjax=function(investment,investmentName,investor,investorName,collvote,ongam,subpay,inmoney){
        $.showloading();//等待动画
        $.ajax({
            url:"insertTradeInvesInfo.html",
            type:"post",
            async:false,
            data:{
                orgName:investmentName,
                investor:investorName,
                base_trade_code:initLoadConfig.data.tradeCode,
                base_investment_code:investment,
                base_investor_code:investor,
                base_trade_collvote:collvote,
                base_trade_inmoney:inmoney,
                base_trade_ongam:ongam,
                base_trade_subpay:subpay,
                logintype:cookieopt.getlogintype(),
                version:initLoadConfig.data.version
            },
            dataType: "json",
            success: function(data){
                $.hideloading();//等待动画隐藏

                savedata.searchorg="";
                savedata.searchorginv="";

                if(data.message=="success"){
                    $.showtip("保存成功","success",2000);
                    initLoadConfig.data.tradeInfoList=data.list;
                    initLoadConfig.data.version=data.version;
                    opt.page=data.page;//重新获取分页
                    rendtrade();
                    layer.closeAll("page");
                }else{
                    $.showtip(data.message,"error",2000);
                }
            }
        });
    };

    function formatRepo (repo) {
        return repo.text;
    }

    function jqPagination () {
        $.jqPaginator('#pagination1', {
            totalPages: opt.page.totalPage,
            visiblePages: 5,
            currentPage:opt.page.pageCount,
            onPageChange: function (num, type) {
                if(opt.page.pageCount!=num){
                    opt.page.pageCount=num;
                    tradePageAjax();
                }
            }
        });
    }

    return {
        init:init,
        data:opt,
        investorSelect2:select2_investor,
        searchdata:savedata
        
    };
	
})();

var updlogconfig=(function(){
    var initdata={
        page:{pageCount:1},
        list:[]
    };

    var rendering=function(){
        var html=table_data.updlogListNull;
        if(initdata.list.length>0 && initdata.list[0]!=null){
            html=template.compile(table_data.updlogList)({list:initdata.list});
            pageContoller();
        }
        $(".updlogBody").html(html);
        
    };

    var updClick=function() {
        $(".updlogClick").unbind().bind("click", function () {
            if(initLoadConfig.data.tradeCode==null||initLoadConfig.data.tradeCode==""){
                $.showtip("未发现交易信息","normal",2000);
                return;
            }
            layer.open({
                type: 1,
                title: '更新记录',
//                shadeClose: false,
//                shade: 0.6,
//                scrollbar: false,
//                maxmin: false, //开启最大化最小化按钮
                skin : 'layui-layer-rim', //加上边框
                area: ['700px', '350px'],
                content: table_data.updlogListChild
            });
            updlogAjax();
        });
    };
    var pageContoller=function(){
        //分页控件
        $.jqPaginator('#pagination1', {
            totalPages: initdata.page.totalPage,
            visiblePages: 5,
            currentPage:initdata.page.pageCount,
            onPageChange: function (num, type) {
                if(initdata.page.pageCount!=num){
                    initdata.page.pageCount=num;
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
                code:initLoadConfig.data.tradeCode,
                pageCount:initdata.page.pageCount,
                logintype:cookieopt.getlogintype()},
            dataType: "json",
            success: function(data){
                $.hideloading();//隐藏等待动画
                if(data.message=="success"){
                    initdata.page=data.page;
                    initdata.list=data.list;
                    if(data.page.totalCount>0){
                    	$(".totalSize").show();
                    	$(".totalSize").html("<li>共"+data.page.totalCount+"条</li>");
                    }else{
                    	$(".totalSize").hide();
                    }
                    rendering();
                }else{
                    $.showtip(data.message,"normal",2000);
                }
                setTimeout("$.hidetip()",2000);
            }
        });
    };

    return {
      initClick:updClick
    };
})();


//备注
var tradeNoteconfig=(function(){
	//初始加载数据
	var init=function(){
			rendering();
			more();
            addNote();
	};
	
	//渲染投资备注
	var rendering=function(){
		var html=table_data.noteListNull;
		if(initLoadConfig.data.noteList.list.length>0&&initLoadConfig.data.noteList.list[0]!=null){
			html=template.compile(table_data.noteList)(initLoadConfig.data.noteList);
		    if(html==""){
		    	html=table_data.noteListNull;
		    }
		}
		$(".noteBody").html(html);
		if(CommonVariable().NOTECONFIGNUM<initLoadConfig.data.noteList.list.length){
            $(".closeshearch").show();
            if($(".closeshearch").hasClass("active")){
            	$("[nodemore]").show();
	            $(this).html('收起<span class="glyphicon glyphicon-chevron-up blue"></span>');
            }else{
           	 	$("[nodemore]").hide();
            	$(".closeshearch").html('更多<span class="glyphicon glyphicon-chevron-down blue"></span>');
            }
           
        }else{
        	$(".closeshearch").hide();
        	$(".closeshearch").html('更多<span class="glyphicon glyphicon-chevron-down blue"></span>').removeClass("active");
        }        
		delClick();
	};
	
	//更多
	var more=function(){
		$(".closeshearch").unbind().bind("click", function () {
	           if(!$(this).hasClass("active")){
	               $("[nodemore]").show();
	               $(this).html('收起<span class="glyphicon glyphicon-chevron-up blue"></span>').addClass("active");
	           }else{
	               $("[nodemore]").hide();
	               $(this).html('更多<span class="glyphicon glyphicon-chevron-down blue"></span>').removeClass("active");
	           }
	        });
	};
	//删除备注事件
	var delClick=function(){
		$(".noteDelClick").unbind().bind("click",function () {
			var noteCode=$(this).attr("del-code");
			layer.confirm('您确定要删除该条记录吗？', {
                title:"提示",
                icon: 0,
                btn: ['确定','取消'] //按钮
            }, function(index){
                del(noteCode);
                layer.close(index);
            }, function(index){
                layer.close(index);
            });
			
		});
	};
	//删除投资机构备注后台请求
	var del=function(noteCode){
		$.showloading();//等待动画
		$.ajax({
    		url:"deleteTradeNote.html",
    		type:"post",
    		async:false,
    		data:{
        			noteCode:noteCode,
        			tradeCode:initLoadConfig.data.tradeCode,
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
                	initLoadConfig.data.noteList.list=data.list;
                	rendering();
                }else{
                	 $.showtip(data.message,"error",2000);
                }
    		}
    	});
	};
	
	var addNote=function(){
		$(".submitNote").unbind().bind("click",function(){
			var noteCon=$("#noteContent").val();
			if(initLoadConfig.data.tradeCode==null||initLoadConfig.data.tradeCode==""){
				$.showtip("未发现交易信息","normal",2000);
			}else if(noteCon.trim()==""){
				$.showtip("备注内容不能为空","normal",2000);
			}else{
				$.showloading();//等待动画
				$.ajax({
		    		url:"addTradeNote.html",
		    		type:"post",
		    		async:false,
		    		data:{
		    				base_trade_code:initLoadConfig.data.tradeCode,
		    				base_tradenote_content:noteCon,
		        			logintype:cookieopt.getlogintype()
		    			},
		    		dataType: "json",
		    		success: function(data){
		    			$.hideloading();//等待动画隐藏
		    			if(data.message=="success"){
		                	$.showtip("保存成功","success",2000);
                            initLoadConfig.data.noteList.list=data.list;
		                	rendering();
		                	$("#noteContent").val("");
		                }else{
		                	$.showtip(data.message,"error",2000);
		                }
		    		}
		    	});
			}
			
		});
	};
	
	return{
		init:init
	};
})();

//删除交易
var delTradeconfig=(function(){
	var click=function(){
		$(".delTradeClick").unbind().bind("click",function(){
            if(initLoadConfig.data.tradeCode==null||initLoadConfig.data.tradeCode==""){
                $.showtip("未发现交易信息","normal",2000);
                return;
            }
            layer.confirm('您确定要删除交易信息吗？', {
                title:"提示",
                icon: 0,
                btn: ['确定','取消'] //按钮
            }, function(index){
                del();
                layer.close(index);
            }, function(index){
                layer.close(index);
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
                tradeCode:initLoadConfig.data.tradeCode,
                version:initLoadConfig.data.version,
                orgCodeString:tradeInfo.view_investment_code,
                tradeDate:tradeInfo.base_trade_date,
                companyName:tradeInfo.base_comp_name,
                logintype:cookieopt.getlogintype()
            },
            dataType: "json",
            success: function(data){
                $.hideloading();//等待动画隐藏
                if(data.message=="success"){
                    $.showtip("删除成功","success",2000);
                    window.setTimeout(function(){
                        window.location.href="trade_search.html?logintype="+cookieopt.getlogintype();
                    },2000);

                }else{
                    $.showtip(data.message,"error",2000);
                }
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
        '<td><button class="btn btn_delete noteDelClick" i="<%=i%>" del-code="<%=list[i].base_tradenote_code%>"></button></td></tr>'+
        '<%}%>',
    noteListNull:'<tr class="nohover"><td colspan="4">暂无数据</td></tr>',
    tradeAddList:
    	'<div class="tradeAddChild"><ul>'+
    	'<li><span class="lable">投资机构:</span><span class="in investmentSelect"><select id="select_org" class="select2 select_tz" onchange="changeInvestment()"></select></span><span class="ulBtn"><button id="btn_investment_add" class="btn btn_icon_add"></button></span></li>' +
        '<li><span class="lable">投资人:</span><span class="in investorSelect"><select id="select_investor" class="select2 select_tz" ></select></span><span class="ulBtn"><button id="btn_investor_add" class="btn btn_icon_add"></button></span></li>' +
        '<li><span class="lable">金额:</span><span class="in"><input id="inmoney" type="text" class="inputMoney" maxlength="100"/></span></li>' +
        '<li><span class="lable">领投:</span><span class="in"><select id="collvote" class="select_tz"><option value="1">否</option><option value="0">是</option></select></span></li>' +
        '<li><span class="lable">分期:</span><span class="in"><select id="subpay" class=" select_tz"><option value="1">否</option><option value="0">是</option></select></li>' +
        '<li><span class="lable">对赌:</span><span class="in"><select id="ongam" class=" select_tz"><option value="1">否</option><option value="0">是</option></select></span></li>'+
        '</ul></div>'+
        '<div class="btn-box"><button class="btn btn-default saveTradeBtnClick">保存</button></div>',
    //zzg 修改　需求变更
    tradeInvesDetail:'<div class="tradeDetialChild"><ul>'+
        '<li><span class="lable">投资机构:</span><span class="in investmentSelect"><%=base_investment_name%></span></li>' +
        '<li><span class="lable">投资人:</span><span class="in investorSelect" ><select id="select_investor" class="select2 select_tz" >' +
        '<%if(base_investor_code!=""){%>' +
        '<option value="<%=base_investor_code%>"><%=base_investor_name%></option>' +
        '<%}%>' +
        '</select><input class="inputMoney" id="select_investor_input" style="display: none"></span><span class="ulBtn2"><button id="btn_investor_add" class="btn btn_icon_add"></button></span></li>' +
        '<li><span class="lable">金额:</span><span class="in"><input id="inmoney" value="<%=base_trade_inmoney%>" class="inputMoney" ></span></li>' +
        '<li><span class="lable">领投:</span><span class="in"><select id="collvote" class="select_tz"><option value="1">否</option><option value="0">是</option></select></span></li>' +
        '<li><span class="lable">分期:</span><span class="in"><select id="subpay" class=" select_tz"><option value="1">否</option><option value="0">是</option></select></li>' +
        '<li><span class="lable">对赌:</span><span class="in"><select id="ongam" class=" select_tz"><option value="1">否</option><option value="0">是</option></select></span></li>'+
        '</ul><button class="btn btn-default tradeTrueBtnClick">修改</button></div>',
    //zzg 修改　结束
    tradeListChild:'<div class="tradeListChild" >'+
        '<table><thead  class="Thead">'+
        '<tr ><th width="30%">投资机构</th>'+
        '<th width="20%">投资人</th>'+
        '<th width="14%">领投</th>'+
        '<th width="30%">金额</th>'+
        '<th width="6%">操作</th></tr></thead>'+
        '<tbody class="tradeBody tradeBodyChild"></tbody></table>'+
        '<div class="customBootstrap"><ul class="totalSize" id="totalsize"　></ul><ul class="pagination" id="pagination1"></ul></div></div>',
    tradeInvesList:'<%for(var i=0;i<list.length;i++){%>'+
        '<tr id="<%=i%>">'+
        '<td><%=list[i].base_investment_name%></td>'+
        '<td><%=list[i].base_investor_name%></td>'+
        '<td><%=isorno(list[i].base_trade_collvote)%></td>'+
        '<td><%=list[i].base_trade_inmoney%></td>'+
        '<td><button class="btn btn_delete deleteTradeClick" i="<%=i%>" del-code="<%=list[i].base_investment_code%>"></button></td></tr>'+
        '<%}%>',
    tradeInvesListNull:'<tr class="nohover"><td colspan="5">暂无数据</td></tr>',
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
    updlogListChild:'<div class="tradeListChild" >'+
        '<table><thead  class="Thead">'+
        '<tr class="">'+
        '<th width="15%">时间</th>'+
        '<th width="15%">更新人</th>'+
        '<th width="20%">操作</th>'+
        '<th width="50%">内容</th></tr></thead>'+
        '<tbody class="updlogBody"></tbody></table>'+
        '<div class="customBootstrap"><ul class="totalSize" id="totalsize"　></ul><ul class="pagination" id="pagination1"></ul></div></div>',
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

template.helper("isorno", function (a) {
    if(a=="0"){
        return "是";
    }else{
        return "否";
    }
});

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
        $(a).html('更多<span class="glyphicon glyphicon-chevron-down" ></span>');
    }else{
        $(a).parents(".shgroup").addClass("heightauto");
        $(a).html('收起<span class="glyphicon glyphicon-chevron-up" ></span>');

    }
}
//刷新操作标签
function refreshladle(a){
    var shgroup=$(a).parents(".shgroup");
    if(shgroup.hasClass("heightauto")){
        shgroup.removeClass("heightauto");
        if( $(a)[0].scrollHeight>shgroup.height()){
            if(!shgroup.hasClass("havemore")){
                shgroup.addClass("havemore");
            }
        }else{
            shgroup.removeClass("havemore");
        }
        shgroup.addClass("heightauto");
    }else{
        if( $(a)[0].scrollHeight>shgroup.height()){
            if(!shgroup.hasClass("havemore")){
                shgroup.addClass("havemore");
                a.parents(".tiplist").next(".more").removeClass("display-none").addClass("display-block");
            }
        }else{
            shgroup.removeClass("havemore");
            a.parents(".tiplist").next(".more").removeClass("display-block").addClass("display-none");
        }
    }
    $(".havemore .more").unbind().bind("click",function () {
        showmore($(this));
    });
}

function changeInvestment(){
    tradeInfoconfig.investorSelect2();
};





//function click_lisetn(){
//    $(".tip-edit").click(function () {
//        var list=[{"name":"互联网金融","id":"1","select":true},{"name":"社区社交","id":"2","select":false},{"name":"旅游","id":"3","select":false},{"name":"大数据云服务","id":"4","select":false},{"name":"医疗器械","id":"5","select":false},{"name":"广告营销","id":"6","select":false},{"name":"汽车交通","id":"7","select":false},{"name":"汽车交通","id":"7","select":false}];
//        tip_edit.config({
//            list:list,
//            $this:$(this)
//        })
//    });
//}
