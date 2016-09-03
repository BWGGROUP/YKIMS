/**
 * Created by shbs-tp001 on 15-9-18.
 */
var optactive=1;
var menuactive=1;
$(function () {
    //初始加载
    initDataConfig.init();

    //基金添加
    fundConfig.click();

    //保存机构
    addconfig.addclick();

    //易凯联系人
    ykLinkconfig.click();

});

var initDataConfig=(function() {
    var initdata = {
        currencyList: [],//币种
        currencyChildList: [],//币种子项
        baskList: [],//筐
        induList: [],//行业
        stageList: [],//投资阶段
        featList: [],//投资特征
        typeList: [],//类型
        bgroundList: [],//背景
        investorList:[]//用户
    };

    var initconfig = function () {
        initLoad();
        labelClick();
    };


    var initLoad = function () {
        $.showloading();//等待动画
        $.ajax({
            url: "initInvestmentInfoData.html",
            type: "post",
            async: false,
            data: {logintype: cookieopt.getlogintype()},
            dataType: "json",
            success: function (data) {
                $.hideloading();//等待动画隐藏
                if (data.message == "success") {
                    initdata.currencyList = data.currencyList;
                    initdata.currencyChildList = data.currencyChildList;
                    initdata.baskList = data.baskList;
                    initdata.induList = data.induList;
                    initdata.stageList = data.stageList;
                    initdata.featList = data.featList;
                    initdata.typeList = data.typeList;
                    initdata.bgroundList = data.bgroundList;
                    initdata.investorList = data.investorList;
                } else {
                    $.showtip(data.message, "error", 2000);
                }
            }
        });
    };

    var labelClick=function() {
        //机构名称
        $(".orgNameClick").bind("click", function () {
            layer.open({
                type: 1,
                title: '机构名称',
                skin: 'layui-layer-rim', //加上边框
                area: ['420px', '240px'],
                content: '<div class="addfrom"><ul class="inputlist">'+
                    '<li><span class="lable">机构全称:</span>'+
                    '<span class="in"><input id="orgFName" type="text" class="inputdef" maxlength=\"200\" /></span></li>'+
                    '<li><span class="lable">机构简称:</span>'+
                    '<span class="in"><input id="orgCName" type="text" class="inputdef" maxlength=\"20\" /></span></li>'+
                    '<li><span class="lable">英文名称:</span>'+
                    '<span class="in"><input id="orgEName" type="text" class="inputdef" maxlength=\"200\" /></span></li></ul>'+
                    '<div class="btn-box"><button class="btn btn-default saveOrgName">保存</button></div>'+
                    '</div>'
            });
            $("#orgFName").val(addconfig.data.orgfname);
            $("#orgCName").val(addconfig.data.orgname);
            $("#orgEName").val(addconfig.data.orgename);

            $(".saveOrgName").unbind().bind("click",function(){
                var fName=$("#orgFName").val();
                var cName=$("#orgCName").val();
                var eName=$("#orgEName").val();
                if(cName.trim()==""){
                    $.showtip("机构简称不能为空","normal",2000);
                }else{
                    addconfig.data.orgfname=fName;
                    addconfig.data.orgname=cName;
                    addconfig.data.orgename=eName;
                    checkorgbyname();
                }
            });

        });
        //关注筐
        $(".baskClick").bind("click", function () {
        	var $this=$(this);
        	tip_edit.config({
        		$this:$this,
                title: "关注筐",
                radio: false,
                list: dicListConfig(initdata.baskList, addconfig.data.baskInfo),
                besure: function () {
                    addconfig.data.baskInfo = choiceContent();
                    var html = "";
                    if (addconfig.data.baskInfo.length > 0) {
                        html = template.compile(org_data.orgInfoList)({list: addconfig.data.baskInfo});
                    }
                    $(".basklist").html(html);
                    tip_edit.close();
                }
            });
        });
        //行业
        $(".induClick").bind("click", function () {
            var $this=$(this);
            tip_edit.config({
                $this:$this,
                title: "行业",
                radio: false,
                list: dicListConfig(initdata.induList, addconfig.data.induInfo),
                besure: function () {
                    addconfig.data.induInfo = choiceContent();
                    var html = "";
                    if (addconfig.data.induInfo.length > 0) {
                        html = template.compile(org_data.orgInfoList)({list: addconfig.data.induInfo});
                    }
                    $(".indulist").html(html);
                    tip_edit.close();
                }
            });
        });
        //近期关注
        $(".payattClick").bind("click", function () {
            var $this=$(this);
            tip_edit.config({
                $this:$this,
                title: "近期关注",
                radio: false,
                list: dicListConfig(initdata.induList, addconfig.data.payattInfo),
                besure: function () {
                    addconfig.data.payattInfo = choiceContent();
                    var html = "";
                    if (addconfig.data.payattInfo.length > 0) {
                        html = template.compile(org_data.orgInfoList)({list: addconfig.data.payattInfo});
                    }
                    $(".payattlist").html(html);
                    tip_edit.close();
                }
            });
        });
        //背景
        $(".bggroundClick").bind("click", function () {
            var $this=$(this);
            tip_edit.config({
                $this:$this,
                title: "背景",
                radio: true,
                list: dicListConfig(initdata.bgroundList, addconfig.data.bggroundInfo),
                besure: function () {
                    addconfig.data.bggroundInfo = choiceContent();
                    var html = "";
                    if (addconfig.data.bggroundInfo.length > 0) {
                        html = template.compile(org_data.orgInfoList)({list: addconfig.data.bggroundInfo});
                    }
                    $(".bggroundlist").html(html);
                    tip_edit.close();
                }
            });
        });
        //类型
        $(".typeClick").bind("click", function () {
            var $this=$(this);
            tip_edit.config({
                $this:$this,
                title: "类型",
                radio: false,
                list: dicListConfig(initdata.typeList, addconfig.data.typeInfo),
                besure: function () {
                    addconfig.data.typeInfo = choiceContent();
                    var html = "";
                    if (addconfig.data.typeInfo.length > 0) {
                        html = template.compile(org_data.orgInfoList)({list: addconfig.data.typeInfo});
                    }
                    $(".typelist").html(html);
                    tip_edit.close();
                }
            });
        });
        //阶段
        $(".stageClick").bind("click", function () {
            var $this=$(this);
            tip_edit.config({
                $this:$this,
                title: "阶段",
                radio: false,
                list: dicListConfig(initdata.stageList, addconfig.data.stageInfo),
                besure: function () {
                    addconfig.data.stageInfo = choiceContent();
                    var html = "";
                    if (addconfig.data.stageInfo.length > 0) {
                        html = template.compile(org_data.orgInfoList)({list: addconfig.data.stageInfo});
                    }
                    $(".stagelist").html(html);
                    tip_edit.close();
                }
            });
        });
        //特征
        $(".featClick").bind("click", function () {
            var $this=$(this);
            tip_edit.config({
                $this:$this,
                title: "投资特征",
                radio: false,
                list: dicListConfig(initdata.featList, addconfig.data.featInfo),
                besure: function () {
                    addconfig.data.featInfo = choiceContent();
                    var html = "";
                    if (addconfig.data.featInfo.length > 0) {
                        html = template.compile(org_data.orgInfoList)({list: addconfig.data.featInfo});
                    }
                    $(".featlist").html(html);
                    tip_edit.close();
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
                    $(".orglist").html(template.compile(org_data.orgInfoList)(list));
                    layer.closeAll("page");
                }else if(data.message=="error"){
                    $.showtip("请求失败");
                }else{
                    $.showtip(data.message);
                }
            }
        });
    }
    
	return {
		data:initdata,
        init:initconfig
	};
    
    
	
})();

//机构保存按钮
var addconfig=(function(){
    var labelData={
        orgCode:'',
        message:'',
        orgfname:'',
        orgname:'',
        orgename:'',
        note:'',
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
        $(".orgSubmit").bind("click",function(){
            var note=$("#orgNote").val();
            if(labelData.orgname==""){
                $.showtip("机构简称不能为空");
            }else{
                addAjax(note);
                if(labelData.message=="success"){
                    $.showtip("保存成功","success",2000);
                    setTimeout(function(){
                        window.location.reload();
                    },2000);
                }else{
                    $.showtip(labelData.message,"error",2000);
                }
            }
        });

        //创建机构并添加交易
        $(".orgSubmitToTrade").bind("click",function(){
            var note=$("#orgNote").val();
            if(labelData.orgname==""){
                $.showtip("机构简称不能为空");
            }else{
                if(labelData.orgCode==""){
                    addAjax(note);
                    if(labelData.message=="success"){
                        $.showtip("保存成功","success",2000);
                        setTimeout(function(){
                            window.location.href="addTradeInfoinit.html?investmentcode="+labelData.orgCode+"&logintype="+cookieopt.getlogintype();
                        },2000);
                    }else{
                        $.showtip(labelData.message,"error",2000);
                    }
                }else{
                    window.location.href="addTradeInfoinit.html?investmentcode="+labelData.orgCode+"&logintype="+cookieopt.getlogintype();
                }
            }
        });

        //创建机构并添加投资人
        $(".orgSubmitToInvestor").bind("click",function(){
            var note=$("#orgNote").val();
            if(labelData.orgname==""){
                $.showtip("机构简称不能为空");
            }else{
                if(labelData.orgCode==""){
                    addAjax(note);
                    if(labelData.message=="success"){
                        $.showtip("保存成功","success",2000);
                        setTimeout(function(){
                            window.location.href="investor_add.html?investmentCode="+labelData.orgCode+"&logintype="+cookieopt.getlogintype();
                        },2000);
                    }else{
                        $.showtip(labelData.message,"error",2000);
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

    //获取select2插件选择数据转为[{code:'',name:''}]格式
    var selectLabel=function (vLabel){
        var list=[];
        var map={};
        var d=vLabel.select2("data");
        for(var i=0;i< d.length;i++){
            if(d[i].id!=""){
                map={};
                map.code=d[i].id;
                map.name=d[i].text;
                list.push(map);
            }
        }

        return list;
    };

    return {
        data:labelData,
        addclick:submitClick,
        selectLabel:selectLabel
    };

})();

//添加基金
var fundConfig=(function(){
    var initdata={
        currency:{},
        scale:{}
    };
//	渲染基金
	var rendFund=function(){
		if(addconfig.data.fundInfo.length>0 && addconfig.data.fundInfo[0]!=null){
            var html=template.compile(org_data.fundList)({list:addconfig.data.fundInfo});
            $(".fund_table").html(html);
            deleteClick();
        }

		
	};

    //基金添加事件 测试机构有限公司111601 测试基金111601
    var addclick=function(){
        $('#btn_detail_add').click(function(){
            var curr=template.compile(org_data.currencyDIC)({list:initDataConfig.data.currencyList});
            var scal=template.compile(org_data.scaleDIC)({list:currency(initDataConfig.data.currencyList[0].sys_labelelement_code)});

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
                    '<li><span class="lable">规模:</span><span class="in editScale"> </span></li></ul>'+
                    '<div class="btn-box"><button class="btn btn-default saveAddFund">保存</button></div>'+
                    '</div>'
            });

            $(".editCurrency").html(curr);
            $(".editScale").html(scal);

            $(".saveAddFund").unbind().bind("click",function() {
                var fundName = $("#fundNameAdd").val().trim();
                var fundCurrency = $("#fundCurrency").find("option:selected").val();
                var fundCurrencyTxt = $("#fundCurrency").find("option:selected").text();
                var fundScale = $("#fundScale").find("option:selected").val();
                var fundScaleTxt = $("#fundScale").find("option:selected").text();
                var map={};
                map.fundName=fundName;
                map.fundCurrency=fundCurrency;
                map.fundCurrencyTxt=fundCurrencyTxt;
                map.fundScale=fundScale;
                map.fundScaleTxt=fundScaleTxt;
                addconfig.data.fundInfo.push(map);
                rendFund();
                layer.close(layerAdd);
            });
           
        });
    };


    //删除基金
    var deleteClick=function(){
        $(".btn_delete").unbind().bind("click",function(){
            var index=$(this).parent().parent().index();
            addconfig.data.fundInfo.splice(index,1);
            var tr=$(this).parent().parent()[0];
            var tbody=$(this).parent().parent().parent()[0];
            tbody.removeChild(tr);
        });
    };

    return {
        click:addclick,
        data:initdata
    };
})();

//易凯联系人
var ykLinkconfig=(function(){
	var initdata={
		selectData:{}
	};
	
	var rendyk=function(){
        var html="";
		if(addconfig.data.ykInfo.length>0){
            html=template.compile(org_data.ykLabelList)({list:addconfig.data.ykInfo});
        }
        $(".yklinkContent").html(html);
        delClick();
	};
	
	var addClick=function(){
		$("#btn_yklink_add").unbind().bind("click",function(){
			//添加弹层
            var yks=layer.open({
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
            
            for(var i=0;i<initDataConfig.data.investorList.length;i++){
                initDataConfig.data.investorList[i].selected=false;
            }
            //下拉筐插件
            initdata.selectData=$("#select_linkman").select2({
                placeholder:"请选择联系人",//文本框的提示信息
                minimumInputLength:0,   //至少输入n个字符，才去加载数据
                allowClear: false,  //是否允许用户清除文本信息
                data:initDataConfig.data.investorList,
                escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
                templateResult: formatRepo // 格式化返回值 显示再下拉类表中
            });


            $(".saveLinkInvestor").unbind().bind("click",function(){

                if(selectdata().olist.length==0){
                    $.showtip("请选择联系人","normal",2000);
                }else{
                    var map={};
                    var bool=true;
                    map.code=selectdata().olist[0].id;
                    map.name=selectdata().olist[0].text;
                    for(var i=0;i<addconfig.data.ykInfo.length;i++){
                        if(map.code==addconfig.data.ykInfo[i].code){
                            bool=false;
                            break;
                        }
                    }
                    if(bool){
                        addconfig.data.ykInfo.push(map);
                        rendyk();
                        layer.close(yks);
                    }else{
                        $.showtip("联系人已添加","normal",2000);
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
                    addconfig.data.ykInfo.splice(i,1);
                }
            }
            rendyk();
		});
	};
	
    function selectdata() {
        var map={};
        var o=initdata.selectData.select2("data");
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

    return {
        click:addClick
    };


	
})();


var org_data={
    currencyDIC:'<select id="fundCurrency" class="inputdef" onchange="findScale(this.value)">'+
        '<%for(var i=0;i<list.length;i++){%>'+
        '<option value="<%=list[i].sys_labelelement_code%>"><%=list[i].sys_labelelement_name%></option>'+
        '<%}%></select>',
    scaleDIC:'<select id="fundScale" class="inputdef" >'+
        '<%for(var i=0;i<list.length;i++){%>'+
        '<option value="<%=list[i].id%>"><%=list[i].text%></option>'+
        '<%}%></select>',
    fundList:'<%for(var i=0;i<list.length;i++){%>'+
        '<tr><td><%=list[i].fundName%></td>'+
        '<td><%=list[i].fundCurrencyTxt%></td>'+
        '<td><%=list[i].fundScaleTxt%></td>'+
        '<td>有效</td>'+
        '<td><button class="btn_delete" ></button></td></tr>'+
        '<%}%>',
    orgInfoList:'<%for(var i=0;i<list.length;i++){%>'+
        '<span class="labelSpan"><%=list[i].name%></span>'+
        '<%}%>',
    ykLabelList:'<%for(var i=0;i<list.length;i++){%>'+
    '<div class="ykLabel"><span class="labelSpan"><%=list[i].name%></span><span id="<%=list[i].code%>" class="glyphicon glyphicon-remove ykDelClick"></span></div>'+
    '<%}%>'
        
};


//根据币种类型筛选币种子项
function currency(vCode){
    var list=[];
    var map={};
    for ( var i = 0; i < initDataConfig.data.currencyChildList.length; i++) {
        if(initDataConfig.data.currencyChildList[i].sys_label_code==vCode){
            map={};
            map.id=initDataConfig.data.currencyChildList[i].sys_labelelement_code;
            map.text=initDataConfig.data.currencyChildList[i].sys_labelelement_name;
            list.push(map);
        }
    }
    return list;
}

//关联币种
function findScale(Val){
    var scal=template.compile(org_data.scaleDIC)({list:currency(Val)});
    $(".editScale").html(scal);
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

//投资机构标签弹出层集合
function dicListConfig(vList,vObj){
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

