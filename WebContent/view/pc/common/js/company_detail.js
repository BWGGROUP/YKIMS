/**
 * Created by shbs-tp004 on 15-9-9.
 * Edit by duwenjie 2015-11-9
 */
var optactive=0;
var menuactive=0;
var organization_search=sessionStorage.getItem("organization_search");

$(function () {
    organization_search=eval("(" + organization_search + ")");
    if(backtype!=""){
        $(".goback").show();
    }
	//初始加载数据
	initInvestmentAjax();

    //初始加载标签,事件
    initLoadData.init();

    //投资人
    loadInvestorConfig.init();
    //投资人添加事件
    loadInvestorConfig.addClick();
    //投资人更多事件
    loadInvestorConfig.moreClick();
    //基金
    loadFundConfig.init();

    //近期交易
    tradeInfoConfig.init();

    //易凯联系人
    ykLinkManConfig.init();

    //备注
    noteInfoConfig.init();

    //会议记录
    meetingConfig.click();

    //更新记录
    updlogConfig.click();
    
    //删除投资机构
    delOrgConfig.init();

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
			if(data.message=="success"){
				initLoadData.data.detailInfo=data.investementDetailInfo;//投资机构信息
				initLoadData.data.version=data.version;//排他锁版本号
				initLoadData.data.fundList=data.fundList;//基金
				initLoadData.data.tradeList=data.tradeList;//交易
				initLoadData.data.noteList=data.noteList;//备注
				initLoadData.data.linkList=data.linkList;//易凯联系人

				initLoadData.data.currencyList=data.currencyList;//币种
				initLoadData.data.currencyChildList=data.currencyChildList;//币种子项
				initLoadData.data.baskList=data.baskList;//筐
				initLoadData.data.induList=data.induList;//行业
				initLoadData.data.bggroundList=data.bggroundList;//背景
				initLoadData.data.stageList=data.stageList;//投资阶段
				initLoadData.data.featList=data.featureList;//投资特征
				initLoadData.data.typeList=data.typeList;//类型

				initLoadData.data.unDelLabelList=data.unDelLabelList;//交易记录机构label标签信息
				initLoadData.data.userInfoList=data.userInfoList;//用户
				initLoadData.data.investorList=data.investorList;//投资人

                initLoadData.data.orgFName=initLoadData.data.detailInfo.base_investment_fullname==null?"":initLoadData.data.detailInfo.base_investment_fullname;//投资机构全称
                initLoadData.data.orgName=initLoadData.data.detailInfo.base_investment_name==null?"":initLoadData.data.detailInfo.base_investment_name;//赋值投资机构名称
                initLoadData.data.orgEName=initLoadData.data.detailInfo.base_investment_ename==null?"":initLoadData.data.detailInfo.base_investment_ename;//赋值投资机构英文名称

                initLoadData.dataJson.baskJson=eval(initLoadData.data.detailInfo.view_investment_baskcont);//关注筐
                initLoadData.dataJson.induJson=eval(initLoadData.data.detailInfo.view_investment_inducont);//行业
                initLoadData.dataJson.payattJson=eval(initLoadData.data.detailInfo.view_investment_payattcont);//近期关注
                if(initLoadData.data.detailInfo.view_investment_backcode!=null && initLoadData.data.detailInfo.view_investment_backcont!=null) {
                    initLoadData.dataJson.bggroundJson = eval([
                        {code: initLoadData.data.detailInfo.view_investment_backcode, name: initLoadData.data.detailInfo.view_investment_backcont}
                    ]);//背景
                }else{
                    initLoadData.dataJson.bggroundJson = null;
                }
                initLoadData.dataJson.stageJson=eval(initLoadData.data.detailInfo.view_investment_stagecont);//投资阶段
                initLoadData.dataJson.featJson=eval(initLoadData.data.detailInfo.view_investment_featcont);//特征
                initLoadData.dataJson.typeJson=eval(initLoadData.data.detailInfo.view_investment_typecont);//类型
                
                orgCode=initLoadData.data.detailInfo.base_investment_code;
            }
			$.hideloading();//隐藏等待动画
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
                    if(initLoadData.data.unDelLabelList!=null && vDel==true){
                        for ( var k = 0; k < initLoadData.data.unDelLabelList.length; k++) {
                            //判断已选标签信息是否已存在与交易记录中,存在则加上不可删除标识no-delete:ture
                            if(initLoadData.data.unDelLabelList[k].sys_labelelement_code==vObj[j].code){
                                map.no_delete = true;
                                break;
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

//弹层选择筐选择值,返回[{code:'',name:''}]
function oldContent(vObj){
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
}

//根据币种类型筛选币种子项
function currency(vCode){
    var list=[];
    var map={};
    for ( var i = 0; i < initLoadData.data.currencyChildList.length; i++) {
        if(initLoadData.data.currencyChildList[i].sys_label_code==vCode){
            map={};
            map.id=initLoadData.data.currencyChildList[i].sys_labelelement_code;
            map.text=initLoadData.data.currencyChildList[i].sys_labelelement_name;
            list.push(map);
        }
    }
    return list;
}

//判断note显示行数
template.helper("nodermore", function (a,b) {
    if(a<b){
        return "";
    }else{
        return "nodemore";
    }
});

//筛选条件组展示更多
function showmore(a){
    if($(a).parents(".shgroup").hasClass("heightauto")){
        $(a).parents(".shgroup").removeClass("heightauto");
        $(a).html(' 更多<span class="glyphicon glyphicon-chevron-down" ></span>');
    }else{
        $(a).parents(".shgroup").addClass("heightauto");
        $(a).html(' 收起<span class="glyphicon glyphicon-chevron-up" ></span>');

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

//转换多选筐选择结果
function listToData(vList){
    var list=[];
    var map={};
    if(vList.length>0 && vList[0]!=null){
        for(var i=0;i<vList.length;i++){
            map={};
            map.id=vList[i].sys_labelelement_code;
            map.text=vList[i].sys_labelelement_name;
            list.push(map);
        }
    }
    return list;
};

//关联币种
function findScale(Val){
    var scal=template.compile(trade_data.scaleDIC)({list:currency(Val)});
    $(".editScale").html(scal);
}


var initLoadData=(function(){
	var initdata={
            orgFName:"",//投资机构全称
            orgName:"",//投资机构名称
            orgEName:"",//投资机构英文名称

			detailInfo:{},//投资机构详情
			version:"",//排他锁版本号
			fundList:[],//基金
			tradeList:[],//交易
			noteList:[],//备注
			linkList:[],//易凯联系人

			currencyList:[],//币种
			currencyChildList:[],//币种子项
			baskList:[],//筐
			induList:[],//行业
			bggroundList:[],//背景
			stageList:[],//投资阶段
			featList:[],//投资特征
			typeList:[],//类型

			unDelLabelList:[],//交易记录机构label标签信息
			userInfoList:[],//用户
			investorList:[]//投资人	
	};

    var dataJson={
        baskJson:null,//关注筐
        induJson:null,//行业
        payattJson:null,//近期关注
        bggroundJson:null,//背景
        stageJson:null,//投资阶段
        featJson:null,//特征
        typeJson:null//类型

    };

    var initConfig=function(){
        rendOrgName();
        rendBask();
        baskClick();
        rendIndu();
        induClick();
        rendPayatt();
        payattClick();
        rendStage();
        stageClick();
        rendBgground();
        bggroundClick();
        rendType();
        typeClick();
        rendFeat();
        featClick();
    };



    //渲染机构名称
	var rendOrgName=function(){
//        $(".orgName").html(initdata.orgName);
        $(".orgNameEname").html(orgNameRending(initdata.orgName,initdata.orgEName));
        orgNameClick();
    };

    //编辑机构名称
    var orgNameClick=function(){
        $(".orgNameEname").unbind().bind("click",function () {
            if (orgCode == null) {
                $.showtip("未发现投资机构信息", "normal", 2000);
                return;
            }
            //编辑弹层
            layer.open({
                title: "编辑投资机构名称",
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['420px', '240px'], //宽高
                content: '<div class="addfrom"><ul class="inputlist">' +
                    '<li><span class="lable">机构全称:</span>' +
                    '<span class="in"><input value="" id="orgfName" maxlength="200" type="text" class="inputdef"></span></li>'+
                    '<li><span class="lable">机构简称:</span>' +
                    '<span class="in"><input value="" id="orgcName" maxlength="20" type="text" class="inputdef"></span></li>'+
                    '<li><span class="lable">英文名称:</span>' +
                    '<span class="in"><input value="" id="orgeName" maxlength="200" type="text" class="inputdef"></span></li></ul>' +
                    '<div class="btn-box"><button class="btn btn-default saveOrgName">保存</button></div>' +
                    '</div>'
            });
            $("#orgfName").val(initLoadData.data.orgFName);
            $("#orgcName").val(initLoadData.data.orgName);
            $("#orgeName").val(initLoadData.data.orgEName);
            $(".saveOrgName").unbind().bind("click",function(){
                var fName=$("#orgfName").val();
                var cName=$("#orgcName").val();
                var eName=$("#orgeName").val();
                if(orgCode==null || orgCode==""){
                    $.showtip("未发现投资机构","normal",2000);
                }else if(cName.trim()==""){
                    $.showtip("机构简称不能为空","normal",2000);
                }else if(fName==initLoadData.data.orgFName&&cName==initLoadData.data.orgName&&eName==initLoadData.data.orgEName){
                    $.showtip("机构名称没有修改","normal",2000);
                }else{
                    $.showloading();//等待动画
                    $.ajax({
                        url:"updateInvestmentName.html",
                        type:"post",
                        async:false,
                        data:{
                            orgCode:orgCode,
                            orgName:initLoadData.data.orgName,
                            version:initLoadData.data.version,
                            logintype:cookieopt.getlogintype(),
                            fName:fName,
                            cName:cName,
                            eName:eName,
                            oldFname:initLoadData.data.orgFName,
                            oldCname:initLoadData.data.orgName,
                            oldEname:initLoadData.data.orgEName
                        },
                        dataType: "json",
                        success: function(data){
                            $.hideloading();//等待动画隐藏
                            if(data.message=="success"){
                                $.showtip("保存成功","success",2000);
                                initLoadData.data.orgFName=data.fullname;//投资机构全称
                                initLoadData.data.orgName=data.cnname;//赋值投资机构名称
                                initLoadData.data.orgEName=data.enname;//赋值投资机构英文名称
                                initLoadData.data.version=data.version;//更新最新版本号
                                rendOrgName();
                                layer.closeAll("page");
                            }else{
                                $.showtip(data.message,"error",2000);
                            }
                        }
                    });
                }

            });
        });
    };


    //渲染关注筐
    var rendBask=function(){
        var  html=trade_data.orgInfoListNull;
//        var baskJson=eval(initdata.detailInfo.view_investment_baskcont);//关注筐
        if(dataJson.baskJson!=null){
            html=template.compile(trade_data.orgInfoList)({list:dataJson.baskJson});
        }
        $(".ul-bask").html(html);
        refreshladle($(".ul-bask"));
    };

    //筐事件
    var baskClick=function(){
        //关注筐弹层
        $(".baskClick").bind("click",function () {
            if(orgCode==null){
                $.showtip("未发现投资机构信息","normal",2000);
                return ;
            }
            var $this=$(this);
//            var baskJson=eval(initdata.detailInfo.view_investment_baskcont);
            tip_edit.config({
                $this:$this,
                title:"关注筐",
                list:dicListConfig(false,initdata.baskList,dataJson.baskJson),
                radio:false,
                besure: function () {
                    var oldData=oldContent(dataJson.baskJson);
                    var newData=choiceContent();
                    if(oldData==newData){
                        $.showtip("未修改数据","normal",2000);
                    }else{
                        baskAjax(newData,oldData);
                    }
                }
            });
        });
    };

    //筐请求
    var baskAjax=function(newData,oldData){
        $.showloading();//等待动画
        $.ajax({
            url:"updateOrgInfo.html",
            type:"post",
            async:false,
            data:{
                type:'Lable-bask',
                orgCode:orgCode,
                orgName:initdata.orgName,
                logintype:cookieopt.getlogintype(),
                oldData:oldData,
                newData:newData,
                version:initdata.version
            },
            dataType: "json",
            success: function(data){
                $.hideloading();//等待动画隐藏
                if(data.message=="success"){
                    $.showtip("保存成功","success",2000);
                    dataJson.baskJson=eval(data.list);
                    initdata.version=data.version;
                    rendBask();
                    tip_edit.close();
                }else{
                    $.showtip(data.message,"error",2000);
                }
            }
        });
    };



    //行业
    var rendIndu=function(){
        var  html=trade_data.orgInfoListNull;
//        var induJson=eval(initdata.detailInfo.view_investment_inducont);
        if(dataJson.induJson!=null){
            html=template.compile(trade_data.orgInfoList)({list:dataJson.induJson});
        }
        $(".ul-indu").html(html);
        refreshladle($(".ul-indu"));
    };

    //行业事件
    var induClick=function(){
        //行业弹层
        $(".induClick").bind("click",function () {
            if(orgCode==null){
                $.showtip("未发现投资机构信息","normal",2000);
                return ;
            }
            var $this=$(this);
            tip_edit.config({
                $this:$this,
                title:"关注行业",
                list:dicListConfig(true,initdata.induList,dataJson.induJson),
                radio:false,
                besure: function () {
                    var oldData=oldContent(dataJson.induJson);
                    var newData=choiceContent();
                    if(oldData==newData){
                        $.showtip("未修改数据","normal",2000);
                    }else{
                        induAjax(newData,oldData);
                    }
                }
            });
        });
    };

//    行业请求
    var induAjax=function(newData,oldData){
        $.showloading();//等待动画
        $.ajax({
            url:"updateOrgInfo.html",
            type:"post",
            async:false,
            data:{
                type:'Lable-indu',
                orgCode:orgCode,
                orgName:initdata.orgName,
                logintype:cookieopt.getlogintype(),
                oldData:oldData,
                newData:newData,
                version:initdata.version
            },
            dataType: "json",
            success: function(data){
                $.hideloading();//等待动画隐藏
                if(data.message=="success"){
                    $.showtip("保存成功","success",2000);
                    dataJson.induJson=eval(data.list);
                    initdata.version=data.version;
                    rendIndu();
                    tip_edit.close();
                }else{
                    $.showtip(data.message,"error",2000);
                }
            }
        });
    };




    //近期关注
    var rendPayatt=function() {
        var html = trade_data.orgInfoListNull;
//        var payattJson = eval(initdata.detailInfo.view_investment_payattcont);
        if (dataJson.payattJson != null) {
            html = template.compile(trade_data.orgInfoList)({list: dataJson.payattJson});
        }
        $(".ul-payatt").html(html);
        refreshladle($(".ul-payatt"));
    };

    //近期关注事件
    var payattClick=function(){
        //近期特别关注弹层
        $(".payattClick").bind("click",function () {
            if(orgCode==null){
                $.showtip("未发现投资机构信息","normal",2000);
                return ;
            }
            var $this=$(this);
            tip_edit.config({
                $this:$this,
                title:"近期特别关注",
                list:dicListConfig(false,initdata.induList,dataJson.payattJson),
                radio:false,
                besure: function () {
                    var oldData=oldContent(dataJson.payattJson);
                    var newData=choiceContent();
                    if(oldData==newData){
                        $.showtip("未修改数据","normal",2000);
                    }else{
                        payattAjax(newData,oldData);
                    }
                }
            });
        });

    };

//    近期关注请求
    var payattAjax=function(newData,oldData){
        $.showloading();//等待动画
        $.ajax({
            url:"updateOrgInfo.html",
            type:"post",
            async:false,
            data:{
                type:'Lable-payatt',
                orgCode:orgCode,
                orgName:initdata.orgName,
                logintype:cookieopt.getlogintype(),
                oldData:oldData,
                newData:newData,
                version:initdata.version
            },
            dataType: "json",
            success: function(data){
                $.hideloading();//等待动画隐藏
                if(data.message=="success"){
                    $.showtip("保存成功","success",2000);
                    dataJson.payattJson=eval(data.list);
                    initdata.version=data.version;
                    rendPayatt();
                    tip_edit.close();
                }else{
                    $.showtip(data.message,"error",2000);
                }
            }
        });
    };


    //背景
    var rendBgground=function(){
        var html=trade_data.orgInfoListNull;
        if(dataJson.bggroundJson!=null){
            html=template.compile(trade_data.orgInfoList)({list:dataJson.bggroundJson});
        }
        $(".ul-bgground").html(html);
    };

    //背景事件
    var bggroundClick=function(){
        //背景弹层
        $(".bggroundClick").bind("click",function () {
            if(orgCode==null){
                $.showtip("未发现投资机构信息","normal",2000);
                return ;
            }
            var $this=$(this);
            tip_edit.config({
                $this:$this,
                title:"背景",
                list:dicListConfig(false,initdata.bggroundList,dataJson.bggroundJson),
                radio:true,
                besure: function () {
                    var oldData=oldContent(dataJson.bggroundJson);
                    var newData=choiceContent();
                    if(oldData==newData){
                        $.showtip("未修改数据","normal",2000);
                    }else{
                        bggroundAjax(newData,oldData);
                    }
                }
            });
        });

    };
    //背景请求
    var bggroundAjax=function(newData,oldData){
        $.ajax({
            url:"updateOrgInfo.html",
            type:"post",
            async:false,
            data:{
                type:'Lable-bground',
                orgCode:orgCode,
                orgName:initdata.orgName,
                logintype:cookieopt.getlogintype(),
                oldData:oldData,
                newData:newData,
                version:initdata.version
            },
            dataType: "json",
            success: function(data){
                $.hideloading();//等待动画隐藏
                if(data.message=="success"){
                    $.showtip("保存成功","success",2000);
                    dataJson.bggroundJson=eval(data.list);
                    initdata.version=data.version;
                    rendBgground();
                    tip_edit.close();
                }else{
                    $.showtip(data.message,"error",2000);
                }
            }
        });
    };

    //投资阶段
    var rendStage=function(){
        var html=trade_data.orgInfoListNull;
//        var stageJson=eval(initdata.detailInfo.view_investment_stagecont);
        if(dataJson.stageJson!=null){
            html=template.compile(trade_data.orgInfoList)({list:dataJson.stageJson});
        }
        $(".ul-stage").html(html);
        refreshladle($(".ul-stage"));
    };

    var stageClick=function(){
        //阶段弹层
        $(".stageClick").bind("click",function () {
            if(orgCode==null){
                $.showtip("未发现投资机构信息","normal",2000);
                return ;
            }
            var $this=$(this);
            tip_edit.config({
                $this:$this,
                title:"阶段",
                list:dicListConfig(true,initdata.stageList,dataJson.stageJson),
                radio:false,
                besure: function () {
                    var oldData=oldContent(dataJson.stageJson);
                    var newData=choiceContent();
                    if(oldData==newData){
                        $.showtip("未修改数据","normal",2000);
                    }else{
                        stageAjax(newData,oldData);
                    }
                }
            });
        });

    };

    //阶段请求
    var stageAjax=function(newData,oldData){
        $.showloading();//等待动画
        $.ajax({
            url:"updateOrgInfo.html",
            type:"post",
            async:false,
            data:{
                type:'Lable-investage',
                orgCode:orgCode,
                orgName:initdata.orgName,
                logintype:cookieopt.getlogintype(),
                oldData:oldData,
                newData:newData,
                version:initdata.version
            },
            dataType: "json",
            success: function(data){
                $.hideloading();//等待动画隐藏
                if(data.message=="success"){
                    $.showtip("保存成功","success",2000);
                    dataJson.stageJson=eval(data.list);
                    initdata.version=data.version;
                    rendStage();
                    tip_edit.close();
                }else{
                    $.showtip(data.message,"error",2000);
                }
            }
        });
    };


    //特征
    var rendFeat=function() {
        var html = trade_data.orgInfoListNull;
//        var featJson = eval(initdata.detailInfo.view_investment_featcont);
        if (dataJson.featJson != null) {
            html = template.compile(trade_data.orgInfoList)({list: dataJson.featJson});
        }
        $(".ul-feat").html(html);
        refreshladle($(".ul-feat"));
    };
    //特征事件
    var featClick=function(){
        //投资机构特征弹层
        $(".featClick").bind("click",function () {
            if(orgCode==null){
                $.showtip("未发现投资机构信息","normal",2000);
                return ;
            }
            var $this=$(this);
            tip_edit.config({
                $this:$this,
                title:"投资机构特征",
                list:dicListConfig(false,initdata.featList,dataJson.featJson),
                radio:false,
                besure: function () {
                    var oldData=oldContent(dataJson.featJson);
                    var newData=choiceContent();
                    if(oldData==newData){
                        $.showtip("未修改数据","normal",2000);
                    }else{
                        featAjax(newData,oldData);
                    }
                }
            });
        });
    };

    //特征请求
    var featAjax=function(newData,oldData){
        $.showloading();//等待动画
        $.ajax({
            url:"updateOrgInfo.html",
            type:"post",
            async:false,
            data:{
                type:'Lable-feature',
                orgCode:orgCode,
                orgName:initdata.orgName,
                logintype:cookieopt.getlogintype(),
                oldData:oldData,
                newData:newData,
                version:initdata.version
            },
            dataType: "json",
            success: function(data){
                $.hideloading();//等待动画隐藏
                if(data.message=="success"){
                    $.showtip("保存成功","success",2000);
                    dataJson.featJson=eval(data.list);
                    initdata.version=data.version;
                    rendFeat();
                    tip_edit.close();
                }else{
                    $.showtip(data.message,"error",2000);
                }
            }
        });
    };


    //类型
    var rendType=function(){
        var html=trade_data.orgInfoListNull;
//        var typeJson=eval(initdata.detailInfo.view_investment_typecont);//类型
        if(dataJson.typeJson!=null){
            html=template.compile(trade_data.orgInfoList)({list:dataJson.typeJson});
        }
        $(".ul-type").html(html);
        refreshladle($(".ul-type"));
    };

    //类型事件
    var typeClick=function(){
        //投资机构类型弹层
        $(".typeClick").bind("click",function () {
            if(orgCode==null){
                $.showtip("未发现投资机构信息","normal",2000);
                return ;
            }
            var $this=$(this);
            tip_edit.config({
                $this:$this,
                title:"投资机构类型",
                list:dicListConfig(false,initdata.typeList,dataJson.typeJson),
                radio:false,
                besure: function () {
                    var oldData=oldContent(dataJson.typeJson);
                    var newData=choiceContent();
                    if(oldData==newData){
                        $.showtip("未修改数据","normal",2000);
                    }else{
                        typeAjax(newData,oldData);
                    }
                }
            });
        });
    };


    //类型请求
    var typeAjax=function(newData,oldData){
        $.showloading();//等待动画
        $.ajax({
            url:"updateOrgInfo.html",
            type:"post",
            async:false,
            data:{
                type:'Lable-type',
                orgCode:orgCode,
                orgName:initdata.orgName,
                logintype:cookieopt.getlogintype(),
                oldData:oldData,
                newData:newData,
                version:initdata.version
            },
            dataType: "json",
            success: function(data){
                $.hideloading();//等待动画隐藏
                if(data.message=="success"){
                    $.showtip("保存成功","success",2000);
                    dataJson.typeJson=eval(data.list);
                    initdata.version=data.version;
                    rendType();
                    tip_edit.close();
                }else{
                    $.showtip(data.message,"error",2000);
                }
            }
        });
    };



	return {
		data:initdata,
        dataJson:dataJson,
        init:initConfig
	};
})();


//投资人
var loadInvestorConfig=(function(){
    var initdata={
        page:{
            pageCount:1
        },
        list:[],
        refBool:'true'
    };

    var init=function(){
        var html=trade_data.investorLabelNull;
        if(initLoadData.data.investorList.length>0 && initLoadData.data.investorList[0]!=null){
            var data={list:initLoadData.data.investorList};
            html=template.compile(trade_data.investorLabel)(data);
            if(html==""){
                html=trade_data.investorLabelNull;
            }
        }
        $(".ul-investor").html(html);
        $(".investorMoreClick").removeClass("display-none").addClass("display-block");
        infoClick();//注册投资人事件
        deleteClick();//删除投资人事件
    };

    //渲染子页
    var rendChildBody=function(){
    	var html=trade_data.investorChildBodyNull;
        if(initdata.list.length>0&&initdata.list[0]!=null){
            html=template.compile(trade_data.investorChildBody)({list:initdata.list});
            jqPagination();
        }else{
        	$("#pagination1").hide();
        }
        $(".investorBody").html(html);
        infoClick();//投资人跳转事件
        deleteClick();
    };

    var pageAjax=function(){
        $.showloading();//等待动画
        $.ajax({
            url:"findPageOrgInvestorByOrgId.html",
            type:"post",
            async:false,
            data:{
            	orgCode:orgCode,
            	pageCount:initdata.page.pageCount,
            	logintype:cookieopt.getlogintype()
            },
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
                    rendChildBody();
                }else{
                    $.showtip(data.message,"normal",2000);
                }

            }
        });

    };

    function jqPagination () {
        $.jqPaginator('#pagination1', {
            totalPages: initdata.page.totalPage,
            visiblePages: 5,
            currentPage:initdata.page.pageCount,
            onPageChange: function (num, type) {
                if(initdata.page.pageCount!=num){
                    initdata.page.pageCount=num;
                    pageAjax();
                }

            }
        });


    }
    
    //投资人更多事件
    var moreClick=function(){
    	$(".investorMoreClick").bind("click",function(){
            if(orgCode==null){
                $.showtip("未发现投资机构信息","normal",2000);
                return ;
            }
            initdata.page={pageCount:1};
    		layer.open({
                type: 1,
                title: '投资人信息',
                skin : 'layui-layer-rim', //加上边框
                area: ['700px', '350px'],
                content: trade_data.investorChild
            });
    		pageAjax();
    	});
    };

    
    //投资人跳转
    var infoClick=function(){
        $(".investorLinkDetial").unbind().bind("click",function () {
            if(orgCode==null){
                $.showtip("未发现投资机构信息","normal",2000);
                return ;
            }
            var vCode=$(this).attr("id");
            window.location.href="findInvestorDeatilByCode.html?code="+vCode+"&logintype="+cookieopt.getlogintype()
            +"&backherf=findInvestmentById.html?id="+orgCode;
        });
    };
    
    var addClick=function(){
    	$("#btn_investor_add").unbind().bind("click",function(){
    		if(orgCode==null){
                $.showtip("未发现投资机构信息","normal",2000);
                return ;
            }
        	window.location.href="investor_add.html?investmentCode="+orgCode+"&logintype="+cookieopt.getlogintype()
        	+"&backherf=findInvestmentById.html?id="+orgCode;
        
    	});
    };

    //删除投资人事件
    var deleteClick=function(){
    	$(".investorDelClick").unbind().bind("click",function(e){
            if($(".investorChildTable").length>0){
                initdata.refBool="false";
            }else{
                initdata.refBool="true";
            }
    		var code=$(this).attr("del-code");
    		var invesName=$(this).attr("name");
            layer.confirm('您确定要删除 <span style="color:red;">'+invesName+'</span> 吗？', {
                title:"提示",
                icon: 0,
                btn: ['确定','取消'] //按钮
            }, function(index){
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
                        orgName:initLoadData.data.orgName,
                        logintype:cookieopt.getlogintype()
                    },
                    dataType: "json",
                    success: function(data){
                        $.hideloading();//等待动画隐藏
                        if(data.message=="success"){
                            $.showtip("删除成功","success",2000);
                            if(initdata.refBool=="false"){
                                initdata.list=data.list;
                                initdata.page=data.page;
                                rendChildBody();
                                if(initdata.page.pageCount<=1){
                                    initLoadData.data.investorList=data.list;
                                    init();
                                }
                            }else{
                                initLoadData.data.investorList=data.list;
                                init();
                            }

                        }else if(data.message=="delete"){
                        	$.showtip("数据已删除，请刷新页面","normal",2000);
                        }else{
                            $.showtip(data.message,"error",2000);
                        }
                    }
                });
                layer.close(index);
//                layer.close(layerEdit);
            }, function(index){
                layer.close(index);
            });
            e.stopPropagation();
    	});
    };
    return {
        init:init,
        moreClick:moreClick,
        addClick:addClick
    };

})();

//基金
var loadFundConfig=(function(){
    var initdata={
        page:{
            pageCount:1
        },
        list:[],
        currency:{},
        scale:{},
        refBool:'true'
    };
    var editdata={
        currency:{},
        scale:{}
    };

	var init=function(){
        rendFund();
        addClick();
        moreClick();
    };
	
	//渲染基金信息
	var rendFund=function(){
		var html=trade_data.invesfundListNull;
		if(initLoadData.data.fundList.length>0 && initLoadData.data.fundList[0]!=null){
			var data={list:initLoadData.data.fundList};
		    var html=template.compile(trade_data.invesfundList)(data);
		    if(html==""){
		    	html=trade_data.invesfundListNull;
		    }
		}
		$(".fundTable tbody").html(html);
        if(initLoadData.data.fundList.length>0 && initLoadData.data.fundList[0]!=null) {
            editClick();
        }
	};

    //渲染弹层基金信息
    var rendFundChild=function(){
        var html=trade_data.invesfundListNull;
        if(initdata.list.length>0 && initdata.list[0]!=null){
            var data={list:initdata.list};
            var html=template.compile(trade_data.invesfundChildList)(data);
            if(html==""){
                html=trade_data.invesfundListNull;
            }
            jqPagination();
        }else{
        	$("#pagination1").hide();
        }
        $(".fundBody").html(html);
        if(initdata.list.length>0 && initdata.list[0]!=null) {
            editClick();
        }
    };

    //添加基金
    var addClick=function(){
    	//添加基金按钮事件
    	$('#btn_detail_add').bind("click",function(){
            if(orgCode==null){
                $.showtip("未发现投资机构信息","normal",2000);
                return ;
            }
            var curr=template.compile(trade_data.currencyDIC)({list:initLoadData.data.currencyList});
            var scal=template.compile(trade_data.scaleDIC)({list:currency(initLoadData.data.currencyList[0].sys_labelelement_code)});

            var layerAdd=layer.open({
                type: 1,
                title: '添加基金',
                skin : 'layui-layer-rim', //加上边框
                area: ['450px', '240px'],
                content: '<div class="addfrom"><ul class="inputlist">'+
                    '<li><span class="lable">名称:</span>'+
                    '<span class="in"><input id="fundNameAdd" class="inputdef" maxlength="20"/></span></li>'+
                    '<li><span class="lable">币种:</span>'+
                    '<span class="in editCurrency"> </span></li>'+
                    '<li><span class="lable">金额:</span><span class="in editScale"> </span></li></ul>'+
                    '<div class="btn-box"><button class="btn btn-default saveAddFund">保存</button></div>'+
                    '</div>'
            });

            $(".editCurrency").html(curr);
            $(".editScale").html(scal);
            
            $(".saveAddFund").unbind().bind("click",function(){
            	var fundName=$("#fundNameAdd").val().trim();
            	var fundCurrency=$("#fundCurrency").find("option:selected").val();
                var fundCurrencyTxt=$("#fundCurrency").find("option:selected").text();
                var fundScale=$("#fundScale").find("option:selected").val();
                var fundScaleTxt=$("#fundScale").find("option:selected").text();
            	
            	
                $.showloading();//等待动画
                $.ajax({
                    url: "addOrganizationInvesfund.html",
                    type: "post",
                    async: false,
                    data: {
                        base_investment_code: orgCode,
                        base_ele_name: initLoadData.data.orgName,
                        base_invesfund_name: fundName,
                        base_invesfund_currency: fundCurrency,
                        base_invesfund_currencyname: fundCurrencyTxt,
                        base_invesfund_scalecode: fundScale,
                        base_invesfund_scale: fundScaleTxt,
                        logintype: cookieopt.getlogintype(),
                        version:initLoadData.data.version
                    },
                    dataType: "json",
                    success: function (data) {
                        $.hideloading();//等待动画隐藏
                        if (data.message == "success") {
                            $.showtip("保存成功","success",2000);
                            initLoadData.data.version=data.version;
                            initLoadData.data.fundList=data.list;
                            rendFund();
                        } else {
                            $.showtip(data.message,"error",2000);
                        }
                        layer.close(layerAdd);
                    }
                });
            
            });
            
        });
    };

    //编辑
    var editClick=function(){
        $(".notes_table tr,.fundBody tr").unbind().bind("click",function(){
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
                txtList=initLoadData.data.fundList;
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
            var curr=template.compile(trade_data.currencyDIC)({list:initLoadData.data.currencyList});
            var scal=template.compile(trade_data.scaleDIC)({list:currency(fCurrCode)});

            var layerEdit=layer.open({
                type: 1,
                title: '修改基金',
                skin : 'layui-layer-rim', //加上边框
                area: ['450px', '300px'],
                content: '<div class="addfrom"><ul class="inputlist">'+
                    '<li><span class="lable">名称:</span>'+
                    '<span class="in"><input id="fundNameEdit" class="inputdef" maxlength="20"/></span></li>'+
                    '<li><span class="lable">币种:</span>'+
                    '<span class="in editCurrency"> </span></li>'+
                    '<li><span class="lable">金额:</span>'+
                    '<span class="in editScale"> </span></li>'+
                    '<li><span class="lable">状态:</span>'+
                    '<span class="in"><select id="edit_state" class=" inputdef"><option value="0">有效</option><option value="1">无效</option></select> </span></li></ul>'+
                    '<div class="fundbtn-box"><button class="btn btn-default deleteFundClick">删除</button><button class="btn btn-default saveEditFund">保存</button></div>'+
                    '</div>'
            });

            $(".editCurrency").html(curr);
            $(".editScale").html(scal);
            $("#fundCurrency").val(fCurrCode);
            $("#fundScale").val(fScaleCode);
            $("#fundNameEdit").val(fName);
            $("#edit_state").val(fState);

            //保存修改
            $(".saveEditFund").unbind().bind("click",function(){
                var fundName=$("#fundNameEdit").val().trim();
                var fundCurrency=$("#fundCurrency").find("option:selected").val();
                var fundCurrencyTxt=$("#fundCurrency").find("option:selected").text();
                var fundScale=$("#fundScale").find("option:selected").val();
                var fundScaleTxt=$("#fundScale").find("option:selected").text();
                var state=$("#edit_state").val();
                if(orgCode==null){
                    $.showtip("未发现投资机构信息","normal",2000);
                }else if(fName==fundName && fCurrCode==fundCurrency&&fScaleCode==fundScale&&fState==state){
                    $.showtip("未修改数据","normal",2000);
                }else{
                    $.showloading();//等待动画
                    $.ajax({
                        url:"editOrganizationInvesfund.html",
                        type:"post",
                        async:false,
                        data:{
                            base_investment_code:orgCode,
                            base_ele_name:initLoadData.data.orgName,
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
                            version:initLoadData.data.version
                        },
                        dataType: "json",
                        success: function(data){
                            $.hideloading();//等待动画隐藏
                            if(data.message=="success"){
                            	initLoadData.data.version=data.version;
                                if(initdata.refBool=="true"){
                                    initLoadData.data.fundList=data.fundlist;
                                    rendFund();
                                }else{
                                    initLoadData.data.fundList=data.fundlist;
                                    initdata.page=data.page;
                                    initdata.list=data.list;
                                    rendFundChild();
                                    rendFund();
                                }

                                layer.close(layerEdit);
                                $.showtip("保存成功","success",2000);
                            }else{
                                $.showtip(data.message,"normal",2000);
                            }
                        }
                    });
                }
            });

            //删除基金
            $(".deleteFundClick").unbind().bind("click",function(){
                layer.confirm('您确定要删除该条记录吗？', {
                    title:"提示",
                    icon: 0,
                    btn: ['确定','取消'] //按钮
                }, function(index){
                    $.showloading();//等待动画
                    $.ajax({
                        url:"deleteOrganizationInvesfund.html",
                        type:"post",
                        async:false,
                        data:{
                            orgCode:orgCode,
                            orgName:initLoadData.data.orgName,
                            fundCode:code,
                            fundName:fName,
                            currency:fCurrTxt,
                            scale:fScaleTxt,
                            pageCount:initdata.page.pageCount,
                            refBool:initdata.refBool,
                            logintype:cookieopt.getlogintype(),
                            version:initLoadData.data.version
                        },
                        dataType: "json",
                        success: function(data){
                            $.hideloading();//等待动画隐藏
                            if(data.message=="success"){
                                $.showtip("保存成功","success",2000);
                                initLoadData.data.version=data.version;
                                if(initdata.refBool=="true"){
                                    initLoadData.data.fundList=data.fundlist;
                                    rendFund();
                                }else{
                                    initdata.page=data.page;
                                    initdata.list=data.list;
                                    rendFundChild();
                                    if(initdata.page.pageCount<=1){
                                        initLoadData.data.fundList=data.fundlist;
                                        rendFund();
                                    }
                                }
                            }else{
                                $.showtip(data.message,"error",2000);
                            }
                        }
                    });
                    layer.close(index);
                    layer.close(layerEdit);
                }, function(index){
                    layer.close(index);
                });
            });

        });
    };
    
    //基金更多事件
    var moreClick=function(){
    	$(".fundMoreClick").bind("click",function(){
            if(orgCode==null){
                $.showtip("未发现投资机构信息","normal",2000);
                return ;
            }
    		layer.open({
                type: 1,
                title: '投资机构基金',
                skin : 'layui-layer-rim', //加上边框
                area: ['700px', '350px'],
                content: trade_data.invesfundListChild
            });
    		initdata.page.pageCount=1;
            pageAjax();
    	});
    };

    var pageAjax=function(){
        $.showloading();//等待动画
        $.ajax({
            url:"findPageFundByOrgId.html",
            type:"post",
            async:false,
            data:{
                orgCode:orgCode,
                pageCount:initdata.page.pageCount,
                logintype:cookieopt.getlogintype()
            },
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
                    rendFundChild();
                }else{
                    $.showtip(data.message,"error",2000);
                }
            }
        });
    };


    function jqPagination () {
        $.jqPaginator('#pagination1', {
            totalPages: initdata.page.totalPage,
            visiblePages: 5,
            currentPage:initdata.page.pageCount,
            onPageChange: function (num, type) {
                if(initdata.page.pageCount!=num){
                    initdata.page.pageCount=num;
                    pageAjax();
                }
            }
        });
    }

    return {
        data:initdata,
        init:init
    };
})();

//近期交易
var tradeInfoConfig=(function(){
	var initdata={
		page:{
	        pageCount:1
	    },
	    list:[]
	};
	
	var init=function(){
        rendTrade();
        moreClick();
        addTradeClick();
	};

    //渲染交易
    var rendTrade=function(){
        var html=trade_data.tradeListNull;
        if(initLoadData.data.tradeList.length>0 && initLoadData.data.tradeList[0]!=null){
            html=template.compile(trade_data.tradeList)({list:initLoadData.data.tradeList});
            if(html==""){
                html=trade_data.tradeListNull;
            }
        }
        $(".tradeTable").html(html);
        lineclick();
        deleteClick();
        
    };

    //渲染交易弹层
    var rendTradeChild=function(){
        var html=trade_data.tradeListNull;
        if(initdata.list.length>0 && initdata.list[0]!=null){
            html=template.compile(trade_data.tradeList)({list:initdata.list});
            if(html==""){
                html=trade_data.tradeListNull;
            }
        }
        $(".tradeBody").html(html);
        if(initdata.list.length>0 && initdata.list[0]!=null){
        	jqPagination();
        	lineclick();
            deleteClick();
        }else{
        	$("#pagination1").hide();
        }
        
    };

    //交易更多事件
    var moreClick=function(){
        $(".tradeMoreClick").bind("click",function(){
            if(orgCode==null){
                $.showtip("未发现投资机构信息","normal",2000);
                return ;
            }
            layer.open({
                type: 1,
                title: '近期交易',
                skin : 'layui-layer-rim', //加上边框
                area: ['700px', '350px'],
                content: trade_data.tradeListChild
            });
            initdata.page.pageCount=1;
            pageAjax();
        });
    };

    var lineclick=function(){
        $(".tradeLink").unbind().bind("click",function () {
            if(orgCode==null){
                $.showtip("未发现投资机构信息","normal",2000);
                return ;
            }
            var vCode=$(this).attr("id");
            window.location.href="findTradeDetialInfo.html?tradeCode="+vCode+"&logintype="+cookieopt.getlogintype()+"&backherf=findInvestmentById.html?id="+orgCode;
        });
    };

    //删除事件
    var deleteClick=function(){
    	$(".tradeInfoDeleteClick").unbind().bind("click",function(e){
        	var id=$(this).attr("del-code");
            layer.confirm('您确定要删除该条交易吗？', {
                title:"提示",
                icon: 0,
                btn: ['确定','取消'] //按钮
            }, function(index){
            	deleteAjax(id);
                layer.close(index);
            }, function(index){
                layer.close(index);
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
                pageCount:initdata.page.pageCount,
                logintype:cookieopt.getlogintype(),
                version:initLoadData.data.version
            },
            dataType: "json",
            success: function(data){
                $.hideloading();//隐藏等待动画
                if(data.message=="success"||data.message=="delete"){
                	if(data.message=="success"){
                		$.showtip("删除成功","success",2000);
                	}else{
                		$.showtip("交易已被删除","normal",2000);
                	}
                	initLoadData.data.version=data.version;
                    initdata.page=data.page;
                    if(data.page.totalCount>0){
                    	$(".totalSize").show();
                    	$(".totalSize").html("<li>共"+data.page.totalCount+"条</li>");
                    }else{
                    	$(".totalSize").hide();
                    }
                    if(initdata.page.pageCount<=1){
                        initLoadData.data.tradeList=data.list;
                    	rendTrade();
                    	if($(".investmentTradeChildTable").length>0){
                            initdata.list=data.list;
                    		rendTradeChild();
                    	}
                    }else{
                        initdata.list=data.list;
                    	rendTradeChild();
                    }
                }else{
                    $.showtip(data.message,"error",2000);
                }

            }
        });
    };
    
    
    var pageAjax=function(vId){
        $.showloading();//等待动画
        $.ajax({
            url:"findPageTradeByOrgId.html",
            type:"post",
            async:false,
            data:{
                orgCode:orgCode,
                pageCount:initdata.page.pageCount,
                logintype:cookieopt.getlogintype()
            },
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
                    rendTradeChild();

                }else{
                    $.showtip(data.message,"error",2000);
                }

            }
        });
    };

    function jqPagination () {
        $.jqPaginator('#pagination1', {
            totalPages: initdata.page.totalPage,
            visiblePages: 5,
            currentPage:initdata.page.pageCount,
            onPageChange: function (num, type) {
                if(initdata.page.pageCount!=num){
                    initdata.page.pageCount=num;
                    pageAjax();
                }
            }
        });
    }
    
    var addTradeClick=function(){
    	$(".btn_trade_add").unbind().bind("click",function(){
    		window.location.href="addTradeInfoinit.html?logintype="+cookieopt.getlogintype()
    		+"&investmentcode="+orgCode+"&backherf=findInvestmentById.html?id="+orgCode;
    	});
    };

    return {
        init:init
    };

})();

//易凯联系人
var ykLinkManConfig=(function(){
    var opt={};

    var init=function(){
        rendering();
        addClick();
    };

    //加载易凯联系人
    var rendering=function(){
        var html=trade_data.linkManListNull;
        if(initLoadData.data.linkList.length>0 && initLoadData.data.linkList[0]!=null){
            html=template.compile(trade_data.linkManList)({list:initLoadData.data.linkList});
            if(html==""){
                html=trade_data.linkManListNull;
            }
        }
        $(".linkInvestor").html(html);
        ykLinkClick();
    };
    //添加易凯联系人
    var addClick=function(){

        $(".linkAddClick").unbind().bind("click",function () {
            if(orgCode==null){
                $.showtip("未发现投资机构信息","normal",2000);
                return ;
            }
            opt={};
            //添加弹层
            layer.open({
                title:"添加易凯联系人",
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['420px', '240px'], //宽高
                content: '<div class="addfrom"><ul class="inputlist">'+
                    '<li><span class="lable">易凯联系人:</span>'+
                    '<span class="in"><select id="select_linkman" class=" inputdef"><option></option></select> </span></li></ul>'+
                    '<div class="btn-box"><button class="btn btn-default saveLinkInvestor">保存</button></div>'+
                    '</div>'
            });
            
            for(var i=0;i<initLoadData.data.userInfoList.length;i++){
                initLoadData.data.userInfoList[i].selected=false;
            }
            //下拉筐插件
            opt.$orgainselect=$("#select_linkman").select2({
                placeholder:"请选择联系人",//文本框的提示信息
                minimumInputLength:0,   //至少输入n个字符，才去加载数据
                allowClear: true,  //是否允许用户清除文本信息
                data:initLoadData.data.userInfoList,

                escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
                templateResult: formatRepo // 格式化返回值 显示再下拉类表中
            });

            //保存事件
            $(".saveLinkInvestor").unbind().bind("click",function(){
                if(orgCode==null){
                    $.showtip("未发现投资机构信息","normal",2000);
                    return ;
                }
                if(selectdata().olist.length==0){
                    $.showtip("请选择联系人","normal",2000);
                }else{
                    var code=selectdata().olist[0].id;
                    for ( var i = 0; i < initLoadData.data.linkList.length; i++) {
                        if(code==initLoadData.data.linkList[i].sys_user_code){
                            $.showtip("联系人已存在","normal",2000);
                            return;
                        }
                    }
                    if(orgCode==null){
                        $.showtip("未发现投资机构信息","normal",2000);
                    }else{
                        $.showloading();//等待动画
                        $.ajax({
                            url:"addOrgYKLinkMan.html",
                            type:"post",
                            async:false,
                            data:{
                                base_investment_code:orgCode,
                                base_ele_name:initLoadData.data.orgName,
                                sys_user_code:selectdata().olist[0].id,
                                sys_user_name:selectdata().olist[0].text,
                                logintype:cookieopt.getlogintype(),
                                version:initLoadData.data.version
                            	},
                            dataType: "json",
                            success: function(data){
                                $.hideloading();//等待动画隐藏
                                if(data.message=="success"){
                                    $.showtip("保存成功","success",2000);
                                    initLoadData.data.version=data.version;
                                    initLoadData.data.linkList=data.list;
                                    rendering();

                                }else{
                                    $.showtip(data.message,"error",2000);
                                }
                                layer.closeAll('page');
                            }
                        });
                    }
                }
            });


        });
    };



    //注册易凯联系人编辑事件
    var ykLinkClick=function(){
        $(".linkManEditClick").unbind().bind("click",function () {
            if(orgCode==null){
                $.showtip("未发现投资机构信息","normal",2000);
                return ;
            }
            var userNameOld=$(this).text();
            var userIdOld=$(this).attr("id");
            opt={};
            layer.open({
                title:"编辑易凯联系人",
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['420px', '240px'], //宽高
                content: '<div class="addfrom"><ul class="inputlist">'+
                    '<li><span class="lable">易凯联系人:</span>'+
                    '<span class="in"><select id="select_linkman" class=" inputdef"><option></option></select> </span></li></ul>'+
                    '<div class="btn-box"><button class="btn btn-default saveLinkInvestor">保存</button></div>'+
                    '</div>'
            });

            for(var i=0;i<initLoadData.data.userInfoList.length;i++){
                if(initLoadData.data.userInfoList[i].id==userIdOld){
                    initLoadData.data.userInfoList[i].selected=true;
                }else{
                    initLoadData.data.userInfoList[i].selected=false;
                }
            }

            //下拉筐插件
            opt.$orgainselect=$("#select_linkman").select2({
                placeholder:"请选择联系人",//文本框的提示信息
                minimumInputLength:0,   //至少输入n个字符，才去加载数据
                allowClear: true,  //是否允许用户清除文本信息
                data:initLoadData.data.userInfoList,

                escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
                templateResult: formatRepo // 格式化返回值 显示再下拉类表中
            });

            //保存事件
            $(".saveLinkInvestor").unbind().bind("click",function() {
                if(orgCode==null){
                    $.showtip("未发现投资机构信息","normal",2000);
                    return ;
                }
                if (selectdata().olist.length == 0) {
                    $.showtip("请选择联系人","normal",2000);
                } else {
                    var code = selectdata().olist[0].id;
                    var name = selectdata().olist[0].text;
                    var bool=true;
                    for (var i = 0; i < initLoadData.data.linkList.length; i++) {
                        if (code == initLoadData.data.linkList[i].sys_user_code) {
                            bool=false;
                            break;
                        }
                    }
                    if (orgCode == null ) {
                        $.showtip("未发现投资机构信息","normal",2000);
                        layer.closeAll('page');
                    }else if(code==userIdOld){
                        $.showtip("未修改数据","normal",2000);
                    }else if(!bool){
                    	$.showtip("联系人已存在","normal",2000);
                    	layer.closeAll('page');
                    }else {
                        linkSubmit(userIdOld, userNameOld, code, name);
                        layer.closeAll('page');
                    }
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
                orgName:initLoadData.data.orgName,
                oldUserCode:userIdOld,
                oldUserName:userNameOld,
                newUserCode:userIdNew,
                newUserName:userNameNew,
                version:initLoadData.data.version,
                logintype:cookieopt.getlogintype()
            },
            dataType: "json",
            success: function(data){
                $.hideloading();//等待动画隐藏
                if(data.message=="success"){
                    $.showtip("保存成功","success",2000);
                    initLoadData.data.version=data.version;
                    initLoadData.data.linkList=data.list;
                    rendering();
                }else{
                    $.showtip(data.message,"error",2000);
                }
            }
        });
    };

    function selectdata() {
        var map={};
        var o=opt.$orgainselect.select2("data");
        var olist=[];
        for(var i=0;i< o.length;i++){
            if(o[i].id!=""){
                olist.push(o[i]);
            }
        }
        map.olist=olist;
        return map;
    }

    function formatRepo (repo) {
        return repo.text;
    }
    return{
        init:init
    };
})();

//备注
var noteInfoConfig=(function(){

    //初始加载数据
    var init=function(){
        rendering();
        more();
        submitClick();
    };

    //渲染投资备注
    var rendering=function(){
        var html=trade_data.noteListNull;
        if(initLoadData.data.noteList.length>0 && initLoadData.data.noteList[0]!=null){
            html=template.compile(trade_data.noteList)({list:initLoadData.data.noteList,initSize:CommonVariable().NOTECONFIGNUM});
            if(html==""){
                html=trade_data.noteListNull;
            }
        }
        $(".noteBody").html(html);

        if(CommonVariable().NOTECONFIGNUM<initLoadData.data.noteList.length){
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
            if(orgCode==null){
                $.showtip("未发现投资机构信息","normal",2000);
                return ;
            }
            if(!$(this).hasClass("active")){
                $("[nodemore]").show();
                $(this).html('收起<span class="glyphicon glyphicon-chevron-up"></span>').addClass("active");
            }else{
                $("[nodemore]").hide();
                $(this).html('更多<span class="glyphicon glyphicon-chevron-down"></span>').removeClass("active");
            }
        });
    };
    //删除备注事件
    var delClick=function(){
        $(".noteDeleteClick").unbind().bind("click",function () {
            if(orgCode==null){
                $.showtip("未发现投资机构信息","normal",2000);
                return ;
            }
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
                    	$.showtip("删除成功","success",2000);
                    }else{
                    	$.showtip("数据已被删除","success",2000);
                    }
                    initLoadData.data.noteList=data.list;
                    rendering();
                }else{
                    $.showtip(data.message,"error",2000);
                }
            }
        });
    };


    var submitClick=function(){
        $(".detail_save").click(function(){
            submitNote();
        });
    };

    //添加备注
    var submitNote=function(){
        var note=$("#orgNote").val();
        if(orgCode==null){
            $.showtip("未发现投资机构信息","normal",2000);
        }else if(note.trim()!=""){
            $.showloading();//等待动画
            $.ajax({
                url:"addOrganizationNote.html",
                type:"post",
                async:true,
                data:{
                    base_investment_code:orgCode,
                    base_invesnote_content:note,
                    base_ele_name:initLoadData.data.orgName,
                    logintype:cookieopt.getlogintype()},
                dataType: "json",
                success: function(data){
                    $.hideloading();//等待动画隐藏
                    if(data.message=="success"){
                        $.showtip("保存成功","success",2000);
                        $("#orgNote").val("");
                        initLoadData.data.noteList=data.list;
                        rendering();
                    }else{
                        $.showtip(data.message,"error",2000);
                    }
                }
            });
        }else{
            $.showtip("备注信息不能为空","normal",2000);
        }
    };

    return {
        init:init
    };

})();

//会议记录
var meetingConfig=(function(){
    var initdata={
        page:{
            pageCount:1
        },
        list:[]
    };

    var rendMeet=function(){
        var html=trade_data.meetingListNull;
        if(initdata.list.length>0 && initdata.list[0]!=null){
            html=template.compile(trade_data.meetingList)({list:initdata.list});
            if(html==""){
                html=trade_data.meetingListNull;
            };
            pageMeetingList();
        }
        $(".meetBody").html(html);
        click();
        
    };

    //会议记录事件
    var meetClick=function(){
        $(".meet_detial").click(function(){
            layer.open({
                type: 1,
                title: '会议记录',
//                shadeClose: false,
//                shade: 0.6,
//                scrollbar: false,
//                maxmin: false, //开启最大化最小化按钮
                skin : 'layui-layer-rim', //加上边框
                area: ['700px', '350px'],
                content: trade_data.meetingListChild
            });
            pageMeetingDataRendering();
        });
    };


    //会议子页
    function pageMeetingDataRendering(){
        $.showloading();//等待动画
        $.ajax({
            url:"screenmeetinglist.html",
            type:"post",
            async:false,
            data:{orgaincode:orgCode,pageCount:initdata.page.pageCount, logintype:cookieopt.getlogintype()},
            dataType: "json",
            success: function(data){
                $.hideloading();//隐藏等待动画
                if(data.message=="success"||data.message=="nomore"){
                    initdata.page=data.page;
                    initdata.list=data.list;
                    if(data.page.totalCount>0){
                    	$(".totalSize").show();
                    	$(".totalSize").html("<li>共"+data.page.totalCount+"条</li>");
                    }else{
                    	$(".totalSize").hide();
                    }
                    rendMeet();
                }else{
                    $.showtip(data.message,"error",2000);
                }
            }
        });
    }


//会议分页请求
    function pageMeetingList(){
        $.jqPaginator('#pagination1', {
            totalPages: initdata.page.totalPage,
            visiblePages: 5,
            currentPage:initdata.page.pageCount,
            onPageChange: function (num, type) {
                if(initdata.page.pageCount!=num){
                    initdata.page.pageCount=num;
                    pageMeetingDataRendering();
                }
            }
        });

    }


    //会议跳转
    var click=function(){
        $(".meetingLink").bind("click",function () {
            var vCode=$(this).attr("id");
          //2016-1-31 查看权限判断
			var vMeetingVisible = $(this).attr("mt_visible");
			if(vMeetingVisible == "0"){
				 $.showtip("您没有查看此会议的权限");
				 setTimeout(function () {
                     $.hidetip();
                 },2000);
				return ;
			}
            window.location.href="meeting_info.html?meetingcode="+vCode+"&logintype="+cookieopt.getlogintype();

        });
    };


    return {
        click:meetClick
    };
})();

//更新记录
var updlogConfig=(function(){
    var initdata={
        page:{
            pageCount:1
        },
        list:[]
    };

    var rendUpdlog=function(){
        var html=trade_data.updlogListNull;
        if(initdata.list.length>0 && initdata.list[0]!=null){
            html=template.compile(trade_data.updlogList)({list:initdata.list});
            if(html==""){
                html=trade_data.updlogListNull;
            };
            jqpagination();
        }
        $(".updlogBody").html(html);
        
    };

    var updlogClick=function(){
        $(".updlog_detial").click(function(){
        	if(orgCode==null){
        		$.showtip("未发现投资机构信息","normal",2000);
        		return;
        	}
            layer.open({
                type: 1,
                title: '更新记录',
                skin : 'layui-layer-rim', //加上边框
                area: ['700px', '350px'],
                content: trade_data.updlogListChild
            });
            updlogAjax();
        });
    };

    //更新记录请求
    var updlogAjax=function(){
        $.showloading();//等待动画
        $.ajax({
            url:"findOrgUpdlogInfoByCode.html",
            type:"post",
            async:false,
            data:{type:'Lable-investment',pageCount:initdata.page.pageCount,code:orgCode, logintype:cookieopt.getlogintype()},
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
                    rendUpdlog();
                }else{
                    $.showtip(data.message,"error",2000);
                }
            }
        });
    };


    //更新记录分页插件
    var jqpagination=function(){
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


    return {
      click:updlogClick
    };

})();


//拼接投资机构名称
function orgNameRending(orgName,orgEName){
	var vTxt=orgName+"<span class=\"small\">"+orgEName+"</span>";
	if(initLoadData.data.detailInfo.deleteflag=='1'){
		vTxt+="<span class='deleteflag'>(无效)</span>";
	}
	return vTxt;
}

//2016-4-20 删除投资机构
var delOrgConfig=(function(){
	var rending=function(){
		if(initLoadData.data.detailInfo.deleteflag==0){
			$(".org_del").html("标记为无效");
			deleteClick();
		}else{
			$(".org_del").html("标记为有效");
			backClick();
		}
		$(".orgNameEname").html(orgNameRending(initLoadData.data.orgName,initLoadData.data.orgEName));
	};
	//删除投资机构
	var deleteClick=function(){
		$(".org_del").unbind().bind("click",function(){
			layer.confirm('您确定要标记该机构为无效吗？', {
                title:"提示",
                icon: 0,
                btn: ['确定','取消'] //按钮
            }, function(index){
            	updAjax();
                layer.close(index);
            }, function(index){
                layer.close(index);
            });
		});
	};
	//还原投资机构
	var backClick=function(){
		$(".org_del").unbind().bind("click",function(){
			layer.confirm('您确定要标记该机构为有效吗？', {
                title:"提示",
                icon: 0,
                btn: ['确定','取消'] //按钮
            }, function(index){
            	updAjax();
                layer.close(index);
            }, function(index){
                layer.close(index);
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
	            	type:initLoadData.data.detailInfo.deleteflag,
	            	orgcode:orgCode,
	            	version:initLoadData.data.version,
	            	logintype:cookieopt.getlogintype()
	            },
	            dataType: "json",
	            success: function(data){
	                $.hideloading();//隐藏等待动画
	                if(data.message=="success"){
	                	if(initLoadData.data.detailInfo.deleteflag=='1'){
	                		$.showtip("已标记为有效", "success", 2000);
	                	}else{
	                		$.showtip("已标记为无效", "success", 2000);
	                	}
	                	initLoadData.data.version=data.version;
                		initLoadData.data.detailInfo.deleteflag=data.deleteflag;
	                	rending();
	                }else{
	                    $.showtip(data.message,"error",2000);
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




var trade_data={
    linkManSelect:'<select id="linkInvestor" class="inputdef"'+
        '<%for(var i=0;i<list.length;i++){%>'+
        '<option value="<%=list[i].id%>"><%=list[i].text%></option>'+
        '<%}%></select>',
    currencyDIC:'<select id="fundCurrency" class="inputdef" onchange="findScale(this.value)">'+
        '<%for(var i=0;i<list.length;i++){%>'+
        '<option value="<%=list[i].sys_labelelement_code%>"><%=list[i].sys_labelelement_name%></option>'+
        '<%}%></select>',
    scaleDIC:'<select id="fundScale" class="inputdef" >'+
        '<%for(var i=0;i<list.length;i++){%>'+
        '<option value="<%=list[i].id%>"><%=list[i].text%></option>'+
        '<%}%></select>',
    tradeListChild:'<div class=" tran_content_table_child tran_content tradebox" >'+
        '<table class="investmentTradeChildTable"><thead  class="tHead">'+
        '<tr class="nohover">'+
        '<th width="14%">时间</th>'+
        '<th width="33%">公司</th>'+
        '<th width="20%">阶段</th>'+
        '<th width="7%">领投</th>'+
        '<th width="20%">金额</th>'+
        '<th width="6%">操作</th></thead>'+
        '<tbody class="tradeBody"></tbody></table>'+
        '<div class="customBootstrap"><ul class="totalSize" id="totalsize"　></ul>'+
    	'<ul class="pagination" id="pagination1"></ul></div></div>',

    tradeList:'<%for(var i=0;i<list.length;i++){%>'+
        '<tr class="tradeLink" id="<%=list[i].base_trade_code%>">'+
        '<td><%=dateFormat(list[i].base_trade_date,"yyyy-MM")%></td>'+
        '<td><%=list[i].base_comp_name%></td>'+
        '<td><%=list[i].base_trade_stagecont%></td>'+
        '<td><%=list[i].base_trade_collvote%></td>'+
        '<td><%=list[i].base_trade_inmoney%></td>'+
        '<td><button class="btn btn_delete tradeInfoDeleteClick" del-code="<%=list[i].base_trade_code%>" name="<%=list[i].base_investor_name%>"></button></td>'+
        '</tr><%}%>',
    tradeListNull:'<tr><td colspan=\'6\'>暂无数据</td></tr>',

    investorLabel:'<%for(var i=0;i<list.length;i++){%>'+
        '<li><div class="investorEditDiv"><span class="name investorLinkDetial" id="<%=list[i].base_investor_code%>"><%=list[i].base_investor_name==""?"未知":list[i].base_investor_name%></span>'+
        ' <span class="position"><%=list[i].base_investor_posiname%></span>'+
        '<span del-code="<%=list[i].base_investor_code%>" name="<%=list[i].base_investor_name%>" class="glyphicon glyphicon-remove investorDelClick"></span></div>'+
        '</li><%}%>',
    investorLabelNull:'<li></li>',
    noteList:'<%for(var i=0;i<list.length;i++){%>'+
        '<tr <%=nodermore(i,initSize)%> >'+
        '<td><%=dateFormat(list[i].createtime.time,"yyyy-MM-dd")%></td>'+
        '<td><%=list[i].sys_user_name%></td>'+
        '<td><pre><%=list[i].base_invesnote_content%></pre></td>'+
        '<td><button class="btn btn_delete noteDeleteClick" i="<%=i%>" del-code="<%=list[i].base_invesnote_code%>"></button></td></tr>'+
        '<%}%>',
    noteListNull:'<tr><td colspan=\'4\'>暂无数据</td></tr>',

    investorChild:'<div class=" tran_content_table_child tran_content tradebox" >'+
        '<table class="investorChildTable"><thead  class="tHead">'+
        '<tr class="nohover">'+
        '<th width="40%">姓名</th>'+
        '<th width="35%">职位</th>'+
        '<th width="20%">状态</th>' +
        '<th width="5%">操作</th></thead>'+
        '<tbody class="investorBody"></tbody></table>'+
        '<div class="customBootstrap"><ul class="totalSize" id="totalsize"　></ul>'+
    	'<ul class="pagination" id="pagination1"></ul></div></div>',
    investorChildBody:'<%for(var i=0;i<list.length;i++){%>'+
        '<tr id="<%=list[i].base_investor_code%>" class="investorLinkDetial">'+
        '<td><%=list[i].base_investor_name%></td>'+
        '<td><%=list[i].base_investor_posiname%></td>'+
        '<td><%=list[i].base_investor_state%></td>'+
        '<td><button class="btn btn_delete investorDelClick" name="<%=list[i].base_investor_name%>" del-code="<%=list[i].base_investor_code%>"></button></td></tr>'+
        '</tr><%}%>',
    investorChildBodyNull:'<tr><td colspan="4">暂无数据</td></tr>',
    investorList:'<%for(var i=0;i<list.length;i++){%>'+
        '<tr class="investorLinkDetial" id="<%=list[i].base_investor_code%>">'+
        '<td class="blue" ><%=list[i].base_investor_name%></td>'+
        '<td><%=list[i].base_investor_posiname%></td>'+
        '<td><%=list[i].base_investor_state%></td>'+
        '<%}%>',
    investorListNull:'<tr><td colspan=\'4\'>暂无数据</td></tr>',

    invesfundListChild:'<div class=" tran_content_table_child tran_content tradebox" >'+
        '<table><thead  class="tHead">'+
        '<tr class="nohover">'+
        '<th width="30%">名称</th>'+
        '<th width="20%">币种</th>'+
        '<th width="30%">金额</th>'+
        '<th width="20%">状态</th></tr></thead>'+
        '<tbody class="fundBody"></tbody></table>'+
        '<div class="customBootstrap"><ul class="totalSize" id="totalsize"　></ul>'+
    	'<ul class="pagination" id="pagination1"></ul></div></div>',

    invesfundList:'<%for(var i=0;i<list.length;i++){%>'+
        '<tr id="<%=list[i].base_invesfund_code%>" ref-code="true"><td><%=list[i].base_invesfund_name%></td>'+
        '<td><%=list[i].base_invesfund_currencyname%></td>'+
        '<td><%=list[i].base_invesfund_scale%></td>'+
        '<td><%=list[i].base_invesfund_state=="1"?"无效":"有效"%></td></tr>'+
        '<%}%>',
    invesfundChildList:'<%for(var i=0;i<list.length;i++){%>'+
        '<tr id="<%=list[i].base_invesfund_code%>" ref-code="false"><td><%=list[i].base_invesfund_name%></td>'+
        '<td><%=list[i].base_invesfund_currencyname%></td>'+
        '<td><%=list[i].base_invesfund_scale%></td>'+
        '<td><%=list[i].base_invesfund_state=="1"?"无效":"有效"%></td></tr>'+
        '<%}%>',
    invesfundListNull:'<tr class="listNull"><td colspan=\'5\'>暂无数据</td></tr>',
    linkManList:'<%for(var i=0;i<list.length;i++){%>'+
        '<li data-i="<%=i%>"><span id="<%=list[i].sys_user_code%>" class="name linkManEditClick"><a ><%=list[i].sys_user_name%></a></span></li>'+
        '<%}%>',
    linkManListNull:'<li></li>',
    orgInfoList:'<%for(var i=0;i<list.length;i++){%>'+
        '<li data-i="<%=i%>"><%=list[i].name%></li>'+
        '<%}%>',
    orgInfoListNull:'<li></li>',
    updlogListChild:'<div class=" tran_content_table_child tran_content tradebox" >'+
        '<table><thead  class="tHead">'+
        '<tr class="nohover">'+
        '<th width="15%">时间</th>'+
        '<th width="15%">更新人</th>'+
        '<th width="20%">操作</th>'+
        '<th width="50%">内容</th></tr></thead>'+
        '<tbody class="updlogBody"></tbody></table>'+
        '<div class="customBootstrap"><ul class="totalSize" id="totalsize"　></ul>'+
    	'<ul class="pagination" id="pagination1"></ul></div></div>',
    updlogList:'<%for(var i=0;i<list.length;i++){%>'+
        '<tr><td><%=dateFormat(list[i].updtime.time,"yyyy-MM-dd")%></td>'+
        '<td><%=list[i].sys_user_name%></td>'+
        '<td><%=list[i].base_updlog_opercont%></td>'+
        '<td><%=list[i].base_updlog_oridata==""?"":list[i].base_updlog_oridata%>'+
        '<%=list[i].base_updlog_newdata==""?"":list[i].base_updlog_newdata%></td></tr>'+
        '<%}%>',
    updlogListNull:'<tr><td colspan=\'4\'>暂无数据</td></tr>',

    meetingListChild:'<div class=" tran_content_table_child tran_content tradebox" >'+
        '<table><thead class="tHead">'+
        '<tr class="nohover">'+
        '<th width="15%">时间</th>'+
        '<th width="35%">记录人</th>'+
        '<th width="50%">公司/机构</th>'+
        '<tbody class="meetBody"></tbody></table>'+
        '<div class="customBootstrap"><ul class="totalSize" id="totalsize"　></ul>'+
    	'<ul class="pagination" id="pagination1"></ul></div></div>',

    meetingList:'<%for(var i=0;i<list.length;i++){%>'+
        '<tr class="meetingLink" id="<%=list[i].base_meeting_code%>" mt_visible="<%=list[i].visible%>">'+
        '<td><%=list[i].base_meeting_time%></td>'+
        '<td><%=substring(list[i].createName,"10")%></td>'+
        '<td><%=substring(list[i].base_meeting_compcont + " / " + list[i].base_meeting_invicont,"40")%></td></tr>'+
        '<%}%>',
    meetingListNull:'<tr><td colspan=\'3\'>暂无数据</td></tr>'
};

//应用模板 截取字符串
template.helper("substring", function (str,long) {
    str= str.replace(/<br\/>/g,"");
    if(str.length>long){
        str= str.substring(0,long)+"...";
    }
    return str;
});