/**
 * Created by shbs-tp001 on 15-9-17.
 */
var optactive=1;
var menuactive=3;
$(function () {
    $('#dp1').datepicker();
    init.config();
});

var init=(function (){
var data={
    baskJson:[],//选择筐
    induJson:[],//选择行业
    stageJson:[],//选择阶段
    compinfo:[],//选择公司
    conpanyadd:false,
    conpanyadd_detil:[{img:"view/pc/common/icon/icon_add.png",title:"添加公司"},{img:"view/pc/common/icon/search.png",title:"检索公司"}],
    tradeInfoList:[]
};

    var savedata={
        searchcom:"",
        searchorg:"",
        searchinv:""
    };




    function config(){

//初始化公司
        if (viewCompInfo != null && "" != viewCompInfo) {
            // 关注筐
            if(viewCompInfo.view_comp_baskcont!=""){
                data.baskJson = eval(viewCompInfo.view_comp_baskcont);
            }
           if(viewCompInfo.view_comp_inducont!=""){
               // 行业
               data.induJson = eval(viewCompInfo.view_comp_inducont);
           }

            var map={};
            map.opertype="select";
            map.compcode= viewCompInfo.base_comp_code;
            map.compname= viewCompInfo.base_comp_name != null ? viewCompInfo.base_comp_name: "";
            data.compinfo.push(map);
            $("#select_com").append('<option value="'+data.compinfo[0].compcode+'" selected="selected"> '+data.compinfo[0].compname+'</option>');
        }
        conditional_choose.select2();

        click_listen();
        
        
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
           init.data.tradeInfoList.push(tradeinfo);
        }
        //初始化返回信息
        if(backherf != null && "" != backherf && "null"!=backherf ){
        	$("#returnbutton").css("display","");
        		}
      //2015-11-30 TASK078 RQQ AddEnd
        org_choose.configtale();

        initshoutip();
    }

    return{
        data:data,
        searchdata:savedata,
        config:config
    };
})();

function click_listen() {
    $(".kuang").click(function () {
        $this=$(this);
        tip_edit.config({
            list:dicListConfig(false,baskList, init.data.baskJson,[]),
            $this:$(this),
            radio:false,//是否单选
            besure: function () {
                init.data.baskJson=[];
                $(".list li[class='active']").each(function(){
                    var map={};
                    map.code=$(this).attr("tip-l-i");
                    map.name=$(this).html();
                    init.data.baskJson.push(map);
                });
                initshoutip();
                tip_edit.close();

            }
        });
    });
    $(".hangye").click(function () {
        $this=$(this);
        tip_edit.config({
            list:dicListConfig(false,induList, init.data.induJson,[]),
            $this:$(this),
            radio:false,//是否单选
            besure: function () {
                init.data.induJson=[];
                $(".list li[class='active']").each(function(){
                    var map={};
                    map.code=$(this).attr("tip-l-i");
                    map.name=$(this).html();
                    init.data.induJson.push(map);
                });
                initshoutip();
                tip_edit.close();
            }
        });
    });
    $(".jieduan").click(function () {
        $this=$(this);
        tip_edit.config({
            list:dicListConfig(false,stageList, init.data.stageJson,[]),
            $this:$(this),
            radio:true,//是否单选
            besure: function () {
                init.data.stageJson=[];

                $(".list li[class='active']").each(function(){
                    var map={};
                    map.code=$(this).attr("tip-l-i");
                    map.name=$(this).html();

                    init.data.stageJson.push(map);
                });
                initshoutip();
                tip_edit.close();
            }
        });
    });
    $(".option").click(function () {
      init.data.conpanyadd=!init.data.conpanyadd;
        if(init.data.conpanyadd){
            $(".option-img").attr("src",init.data.conpanyadd_detil[1].img);

            $(".addcom-input").val(init.searchdata.searchcom);

            $(".option-img").attr("title",init.data.conpanyadd_detil[1].title);
            $(this).prev().show().prev().hide();
            conditional_choose.opt.$companyselect.val(null).trigger("change");
//            init.data.induJson=[];
//            init.data.baskJson=[];
//            init.data.compinfo=[];
            initshoutip();
        }else{
            $(".option-img").attr("src",init.data.conpanyadd_detil[0].img);
            $(".option-img").attr("title",init.data.conpanyadd_detil[0].title);
            $(this).prev().hide().prev().show();
            $(".addcom-input").val("");
//            init.data.induJson=[];
//            init.data.baskJson=[];
//            init.data.compinfo=[];
            initshoutip();
        }
    });
    $(".btn_icon_add").click(function () {
        org_choose.config();
    });
    $(".creattrade").click(function () {
         creat();
    });
    
	//2015-11-30 TASK078 RQQ AddStart
    //返回按钮
    $("#returnbutton").click(function () {
    	 if(backherf != null && "" != backherf && "null"!=backherf ){
    		 window.location.href=backherf+"&logintype="+cookieopt.getlogintype();
         		}
    	
   });
 //2015-11-30 TASK078 RQQ AddEnd
}

function initshoutip() {
    $(".select-kuang,.select-hangye,.select-jieduan").html("");
    var backhtml=template.compile(data_modal.baskJsontiplist)(init.data);
    var induhtml=template.compile(data_modal.induJsontiplist)(init.data);
    try{$(".select-kuang").html(backhtml); }catch (e){}
    try{$(".select-hangye").html(induhtml);}catch (e){}
    try{$(".select-jieduan").html("<span class='comp1'>"+init.data.stageJson[0].name+"<span>");}catch (e){}
}

var org_choose=(function () {
    var opt={
        disabled_org:false,
        disabled_peo:true
    };
    function config() {
        opt.invementcode="";
        opt.disabled_org=false;
        opt.disabled_peo=true;
        opt.addinvement=false;
        opt.addpeople=false;
        opt.tradeinfo={};
        layer.open({
            title:"添加融资信息",
            type: 1,
            scrollbar: false,
            skin: 'layui-layer-rim', //加上边框
            area: ['500px'], //宽高
            content: '<div class="addfrom"><ul class="inputlist">' +
                '<li><span class="lable">投资机构:</span><span class="in"><select id="select_org" class=" inputdef"><option></option></select></span><span class="addbuttonselect"><button id="btn_comp_add" class="btn btn_icon_add"></button></span></li>' +
                '<li><span class="lable">投资人:</span><span class="in"><select id="select_org_peo" class=" inputdef"><option></option></select></span><span class="addbuttonselect"><button id="btn_entrepreneur_add" class="btn btn_icon_add"></button></span></li>' +
                '<li><span class="lable">金额:</span><span class="in"><input value="" id="inmoney" type="text" class="inputdef" maxlength="20"></span></li>' +
                '<li><span class="lable">领投:</span><span class="in"><select class="inputdef" id="collvote"> <option value="1">否</option> <option value="0">是</option></select></span></li>' +
                '<li><span class="lable">分期:</span><span class="in"><select class="inputdef" id="subpay"> <option value="1">否</option> <option value="0">是</option></select></span></li>' +
                '<li><span class="lable">对赌:</span><span class="in"><select class="inputdef" id="ongam"> <option value="1">否</option> <option value="0">是</option></select></span></li>' +
                '</ul><div class="btn-box"><button class="btn btn-default saveorgan">保存</button></div></div>'
        });

        select2();
        click();
    }

    function select2() {
        try{
            //选择投资机构
            if(!opt.addinvement){
                opt.$orgainselect=$("#select_org").select2({
                    placeholder:"请选择机构",//文本框的提示信息
                    minimumInputLength:0,   //至少输入n个字符，才去加载数据
//            allowClear: true,  //是否允许用户清除文本信息
                    ajax:{
                        url:"findInvestmentNameListByName.html",
                        dataType:"json",
                        cache: true,
                        type:"post",
                        delay: 250,//加载延迟
                        data: function (params) {

                            init.searchdata.searchorg=(params.term||"");

                            return{
                                name:(params.term||"").replace(/(^\s*)|(\s*$)/g,''),
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
            }

        }catch (e){}
        try{
            //选择投资人
            opt.$orgainselect_peo=$("#select_org_peo").select2({
                placeholder:"请选择投资人",//文本框的提示信息
                minimumInputLength:0,   //至少输入n个字符，才去加载数据
//            allowClear: true,  //是否允许用户清除文本信息
                ajax:{
                    url:"queryinvestorlistByinvementcode.html",
                    dataType:"json",
                    cache: true,
                    type:"post",
                    delay: 250,//加载延迟
                    data: function (params) {

                        init.searchdata.searchinv=(params.term||"");

                        return{
                            name:(params.term||"").replace(/(^\s*)|(\s*$)/g,''),
                            pageSize:CommonVariable().SELSECLIMIT,
                            pageCount:"1",
                            logintype:cookieopt.getlogintype(),
                            invementcode:opt.invementcode,
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
        }catch (e){}


        opt.$orgainselect.on("select2:select", function (e) {
            opt.invementcode= e.params.data.id;
            opt.disabled_peo=false;
            autodisable();
            opt.$orgainselect_peo.val(null).trigger("change");

            opt.tradeinfo.base_investment_code=e.params.data.id;
            opt.tradeinfo.base_investment_name=e.params.data.text;
            opt.tradeinfo.investmenttype="select";
        });
        autodisable();
    }

    function autodisable() {
        opt.$orgainselect.prop("disabled", opt.disabled_org);
        opt.$orgainselect_peo.prop("disabled", opt.disabled_peo);
        if(opt.disabled_peo){
            opt.$orgainselect_peo.val(null).trigger("change");
        }
    }
    function click() {
        //添加投资机构点击
        $("#btn_comp_add").unbind().bind("click", function () {
            if($("#btn_comp_add").hasClass("btn_icon_add")){
                $("#select_org").parent().html('<input value="" id="select_org" type="text" class="inputdef" maxlength="20">');

                $("#select_org").val(init.searchdata.searchorg);

                $("#btn_comp_add").removeClass("btn_icon_add").addClass("btn_icon_search");
                opt.invementcode="";
                opt.disabled_peo=false;
                select2();
                opt.addinvement=true;
            }else{
                $("#select_org").parent().html('<select id="select_org" class=" inputdef"><option></option></select>');
                $("#btn_comp_add").removeClass("btn_icon_search").addClass("btn_icon_add");

                opt.invementcode="";
                opt.disabled_peo=true;
                opt.addinvement=false;
                select2();

            }
        });
        //添加投资人点击
        $("#btn_entrepreneur_add").unbind().bind("click", function () {
            if($("#btn_entrepreneur_add").hasClass("btn_icon_add")){
                $("#select_org_peo").parent().html('<input value="" id="select_org_peo" type="text" class="inputdef" maxlength="20">');

                $("#select_org_peo").val(init.searchdata.searchinv);

                $("#btn_entrepreneur_add").removeClass("btn_icon_add").addClass("btn_icon_search");
                opt.addpeople=true;
            }else{
                $("#select_org_peo").parent().html('<select id="select_org_peo" class=" inputdef"><option></option></select>');
                $("#btn_entrepreneur_add").removeClass("btn_icon_search").addClass("btn_icon_add");
                select2();
                opt.addpeople=false;
            }
        });
        //点击保存
        $(".saveorgan").unbind().bind("click",function () {
            var tradeinfo={};
            if(opt.addinvement){
                if($("#select_org").val().trim()==""){
                    $.showtip("投资机构名称不能为空");
                    return;
                }else{
                    tradeinfo.base_investment_code="";
                    tradeinfo.base_investment_name=$("#select_org").val();
                    tradeinfo.investmenttype="add";
                }
            }else{

                if(opt.tradeinfo.base_investment_code==undefined){
                    $.showtip("请选择投资机构");
                    return;
                }else{
                    tradeinfo.base_investment_code=opt.tradeinfo.base_investment_code;
                    tradeinfo.base_investment_name=opt.tradeinfo.base_investment_name;
                    tradeinfo.investmenttype=opt.tradeinfo.investmenttype;
                }
            }

            for(i in init.data.tradeInfoList){
                if(init.data.tradeInfoList[i].base_investment_name==tradeinfo.base_investment_name){
                    $.showtip("该机构已存在");
                    return;
                }
            }
            if(opt.addpeople){
//                if($("#select_org_peo").val().trim()==""){
//                    $.showtip("投资人姓名不能为空");
//                    return;
//                }else{
                    tradeinfo.base_investor_code="";
                    tradeinfo.base_investor_name=$("#select_org_peo").val();
                    tradeinfo.investortype="add";
//                }
            }else{
                var sl=opt.$orgainselect_peo.select2("data");
                tradeinfo.base_investor_code=sl[0].id;
                tradeinfo.base_investor_name=sl[0].text;
                tradeinfo.investortype="select";
            }
            tradeinfo.base_trade_inmoney=$("#inmoney").val();
            //是否领投
            tradeinfo.base_trade_collvote=$("#collvote").find("option:selected").attr("value");
            //是否对赌
            tradeinfo.base_trade_ongam=$("#ongam").find("option:selected").attr("value");
            //是否分次付款
            tradeinfo.base_trade_subpay=$("#subpay").find("option:selected").attr("value");
            init.data.tradeInfoList.push(tradeinfo);

            init.searchdata.searchorg="";
            init.searchdata.searchinv="";

            configtale();
            layer.closeAll("page");
        });

        $(".btn_delete").unbind().bind("click", function (e) {
           init.data.tradeInfoList.baoremove(Number($(this).attr("i")));
            configtale();
            e.stopPropagation();
        });
    	$("tbody[class='stage'] tr").unbind().bind("click", function () {
    		var id = $(this).attr("p-index");
    		//2015-11-30 TASK078 RQQ AddStart
    		if(init.data.tradeInfoList.length>0 && init.data.tradeInfoList[0]!=null && init.data.tradeInfoList[id]!=null){
    	        opt.invementcode=init.data.tradeInfoList[id].base_investment_code;
    	        opt.disabled_org=false;
    	        opt.disabled_peo=false;
    	        opt.addinvement=false;
    	        opt.addpeople=false;
    	        opt.tradeinfo={};
    	        layer.open({
    	            title:"编辑融资信息",
    	            type: 1,
    	            scrollbar: false,
    	            skin: 'layui-layer-rim', //加上边框
    	            area: ['500px'], //宽高
    	            content: '<div class="addfrom"><ul class="inputlist">' +
    	                '<li><span class="lable">投资机构:</span><span class="in" base_investment_code="'
    	                +init.data.tradeInfoList[id].base_investment_code+'" investmenttype="'
    	                	+init.data.tradeInfoList[id].investmenttype+'">'
    	            			+init.data.tradeInfoList[id].base_investment_name
    	            			+'</span></li>' +
    	                '<li><span class="lable">投资人:</span><span class="in">'
    	            			+'<select id="select_org_peo" class=" inputdef">'
    	            			+'<option value="'+init.data.tradeInfoList[id].base_investor_code+'" selected="selected">'
    	         			   +init.data.tradeInfoList[id].base_investor_name+'</option>'
    	         			   +'</select></span><span class="addbuttonselect"><button id="btn_entrepreneur_add" class="btn btn_icon_add"></button></span></li>' +
    	                '<li><span class="lable">金额:</span><span class="in">'
    	                +'<input value="'+init.data.tradeInfoList[id].base_trade_inmoney+'" id="inmoney" type="text" class="inputdef" maxlength="20"></span></li>' +
    	                '<li><span class="lable">领投:</span><span class="in"><select class="inputdef" id="collvote"> <option value="1">否</option> <option value="0">是</option></select></span></li>' +
    	                '<li><span class="lable">分期:</span><span class="in"><select class="inputdef" id="subpay"> <option value="1">否</option> <option value="0">是</option></select></span></li>' +
    	                '<li><span class="lable">对赌:</span><span class="in"><select class="inputdef" id="ongam"> <option value="1">否</option> <option value="0">是</option></select></span></li>' +
    	                '</ul><div class="btn-box"><button class="btn btn-default editorgan">保存</button></div></div>'
    	        });

    	        //投资人
    	   if(init.data.tradeInfoList[id].investortype=="add" 
    		   && init.data.tradeInfoList[id].base_investor_name !=null
    		   && ""!=init.data.tradeInfoList[id].base_investor_name 
    		   ){
    		   $("#select_org_peo").parent().html('<input value="'
        +init.data.tradeInfoList[id].base_investor_name
        +'"id="select_org_peo" type="text" class="inputdef" maxlength="20" investortype="add">' );
        	$("#btn_entrepreneur_add").removeClass("btn_icon_add").addClass("btn_icon_search");
        	opt.addpeople=true;
    	   		}   
    	 //领投
    	   $("#collvote").children("option[value='"+init.data.tradeInfoList[id].base_trade_collvote+"']").attr("selected","selected");
    	   //分期
    	   $("#subpay").children("option[value='"+init.data.tradeInfoList[id].base_trade_collvote+"']").attr("selected","selected");
    	   //对赌
    	   $("#ongam").children("option[value='"+init.data.tradeInfoList[id].base_trade_subpay+"']").attr("selected","selected");
    	 

    	        select2();
    	        click();
    	    }
    		 //点击修改的保存
            $(".editorgan").unbind().bind("click",function () {
                var tradeinfo={};
                							//机构
                tradeinfo.base_investment_code=init.data.tradeInfoList[id].base_investment_code;
                tradeinfo.base_investment_name=init.data.tradeInfoList[id].base_investment_name;
                tradeinfo.investmenttype=init.data.tradeInfoList[id].investmenttype;
            
                if(opt.addpeople){
                        tradeinfo.base_investor_code="";
                        tradeinfo.base_investor_name=$("#select_org_peo").val();
                        tradeinfo.investortype="add";
                }else{
                    var sl=opt.$orgainselect_peo.select2("data");
                    tradeinfo.base_investor_code=sl[0].id;
                    tradeinfo.base_investor_name=sl[0].text;
                    tradeinfo.investortype="select";
                					}
                tradeinfo.base_trade_inmoney=$("#inmoney").val();
                						//是否领投
                tradeinfo.base_trade_collvote=$("#collvote").find("option:selected").attr("value");
                						//是否对赌
                tradeinfo.base_trade_ongam=$("#ongam").find("option:selected").attr("value");
                						//是否分次付款
                tradeinfo.base_trade_subpay=$("#subpay").find("option:selected").attr("value");
                init.data.tradeInfoList[id]=tradeinfo;

                init.searchdata.searchinv="";

                configtale();
                layer.closeAll("page");
            });
    		//2015-11-30 TASK078 RQQ AddEnd
         });
    }
    function formatRepo (repo) {
        return repo.text;
    }

    function configtale() {
        if(init.data.tradeInfoList.length>0){
            var html=template.compile(data_modal.table)(init.data);

        }else{
            var html='<tr class="nohover"><td  colspan="7" style="width:100%;">暂无数据</td></tr>';
        }

        $(".stage").html(html);
        click();
    }
    return{
        config:config,
        opt:opt,
        configtale:configtale
    };

})();

//条件选择
var conditional_choose=(function(){
    var opt={};
    function select2() {

        //选择公司
        opt.$companyselect=$("#select_com").select2({
            placeholder:"请选择公司",//文本框的提示信息
            minimumInputLength:0,   //至少输入n个字符，才去加载数据
//            allowClear: true,  //是否允许用户清除文本信息
            ajax:{
                url:"querycompanylistByname.html",
                dataType:"json",
                cache: true,
                type:"post",
                delay: 250,//加载延迟
                data: function (params) {

                    init.searchdata.searchcom=(params.term||"");

                    return{
                        name:params.term||"",
                        pageSize:CommonVariable().SELSECLIMIT,
                        pageCount:"1",
                        logintype:cookieopt.getlogintype()
                    };
                },
                processResults: function (data) {
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
        opt.$companyselect.on("select2:select", function (e) {

            var map={};
            map.opertype="select";
            map.compcode= e.params.data.id;
            map.compname= e.params.data.base_comp_name;
            init.data.compinfo=[];
            init.data.compinfo.push(map);
            if(init.data.baskJson.length==0&&init.data.induJson.length==0){
                if(e.params.data.view_comp_baskcont!=""){
                    init.data.baskJson= eval(e.params.data.view_comp_baskcont);
                }
                if(e.params.data.view_comp_inducont!=""){
                    init.data.induJson= eval(e.params.data.view_comp_inducont);
                }
                initshoutip();
            }

        });
    }
    function formatRepo (repo) {
        return repo.text;
    }

    return {
        select2:select2,
        opt:opt
    };
})();

//投资机构标签弹出层集合
function dicListConfig(vDel,vList,vObj,unDelLabelList){
    var list=[];
    var map={};
    if(vList!=null && vList!=""){
        for(var i=0;i<vList.length;i++){
            map={};
            map.name=vList[i].sys_labelelement_name;
            map.id=vList[i].sys_labelelement_code;
            if(vObj!=null){
                for ( var j = 0; j < vObj.length; j++) {
                    //判断标签是否被选中,存在则加上选中标识
                    if(vList[i].sys_labelelement_code==vObj[j].code){
                        map.select=true;
                        if(vDel&&unDelLabelList!=null){
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
    }

    return list;
}
var data_modal={
    table:'<% for (var i=0;i<tradeInfoList.length;i++ ){%>' +
        '<tr p-index="<%=i%>">' +
        '<td><%=tradeInfoList[i].base_investment_name%></td>' +
        '<td><%=tradeInfoList[i].base_investor_name%></td>' +
        '<td><%=tradeInfoList[i].base_trade_inmoney%></td>' +
        '<td><%=istrue(tradeInfoList[i].base_trade_collvote)%></td>' +
        '<td><%=istrue(tradeInfoList[i].base_trade_subpay)%></td>' +
        '<td><%=istrue(tradeInfoList[i].base_trade_ongam)%></td>' +
        '<td><button i="<%=i%>" class=" btn_delete"></button></td>' +
        '</tr>' +
         '<%}%>',
    baskJsontiplist:'<% for (var i=0;i<baskJson.length;i++ ){%>' +
        '<span class=comp1><%=baskJson[i].name%></span>' +
            '<%}%>',
    induJsontiplist:'<% for (var i=0;i<induJson.length;i++ ){%>' +
        '<span class=comp1><%=induJson[i].name%></span>' +
        '<%}%>'
};

template.helper("istrue", function (str) {
    if(str=="1"){
        return "否";
    }else{
        return "是";
    }
});
//点击提交
function creat() {

    if($("#dp1").val()==""){
        $.showtip("请选择时间");
        return;
    }
    var tradedate= new Date($("#dp1").val().replace(/-/g,"/"));

    if(!isNaN(tradedate.getTime())){
        tradedate=tradedate.format(tradedate,"yyyy-MM-dd");
    }else{
        $.showtip("时间选择错误");
        return;
    }

    if(init.data.conpanyadd){
        if($(".addcom-input").val().trim()==""){
            $.showtip("请输入公司名称");
            return;
        }
        var map={};
        map.opertype="add";
        map.compcode= "";
        map.compname= $(".addcom-input").val();
        init.data.compinfo=[];
        init.data.compinfo.push(map);

    }else{
        if(init.data.compinfo.length==0){
            $.showtip("请选择公司");
            return;
        }
    }
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

    $.showloading();
    $(".creattrade").attr("disabled","disabled");
    $.ajax({
        async : true,
        dataType : 'json',
        type : 'post',
        url : "addtradeinfo.html",
        data : {
            tradedate : tradedate,
            compinfo:JSON.stringify(init.data.compinfo),
            basklist:JSON.stringify(init.data.baskJson),
            indulist:JSON.stringify(init.data.induJson),
            stageList:JSON.stringify(init.data.stageJson),
            tradeInfoList:JSON.stringify(init.data.tradeInfoList),
            trademoney:trademoney,
            tradecomnum:tradecomnum,
            tradecomnumtype:tradecomnumtype,
            noteinfo:noteinfo,

            logintype : cookieopt.getlogintype()
        },
        success : function(data) {
            $.hideloading();
            if (data.message == "success") {
                $.showtip("保存成功","success");
                setTimeout(function() {
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
                $(".creattrade").removeAttr("disabled");
            }
            else if(data.message == "invesnameexsit"){
                $.showtip(data.messagedetail);
                $(".creattrade").removeAttr("disabled");
            }else {
                $.showtip("保存失败","error");
                $(".creattrade").removeAttr("disabled");
            }
        },error: function () {
            $(".creattrade").removeAttr("disabled");
        }
    });
}