/**
 * Created by shbs-tp001 on 15-9-23.
 */
var induselectcode=null;
var pattyselectcode=null;
var phone="";
var email="";
var wechat="";
var investPeo=[];
var induselectcode1="[]"
var listselectcode1="[]";
var invcode="";
$(function () {
	//--start duwenjie 2015-12-07
	//判断是否传返回参数
	if(backherf!=""){
        $(".goback").show();
       $(".goback").click(function () {
           window.location.href=backherf+"&logintype="+cookieopt.getlogintype();
       });
    }
	//--end
    config.init();
    bask_edit.click();
    indu_edit.click();
    people_edit.click();
    add_investment.addclick();
    add_invesor.addclick();
});
var config=(function(){
    data={
        phone:"",
        email:"",
        wechat:"",
        name:""
    }
    var init=function(){
        if(investmentCode!=null&&investmentCode!=""){
            var investment = new Object();
            investment.code=investmentCode;
            investment.investmentname=investmentName;
            investment.posi="";
            investment.state="在职";
            investPeo.push(investment);
            showInvestment();
        }
    };
    return{
        data:data,
        init:init
    }
})();
//行业初始化
var indu_edit=(function(){
    var indu_edit=function(){
        $("#edit-indu").click(function () {
            listefit.config({
                title:"行业",
                list:dicListConfig(indulist,induselectcode),
                besure: function () {
                    var h="";
                    induselectcode=[];
                    $(".list li").each(function(index,element) {
                        if ($(this).hasClass("active")) {
                            h=h+"<li>"+$(this).html()+"</li>"
                            var map={};
                            map.code=$(this).attr("tip-l-i");
                            induselectcode.push(map);
                        }
                    });
                    induselectcode1=choiceContent();
                    $(".hangyelist").html(h)
                }
            });
        });

    }
    return{
        click:indu_edit
    }
})();
//近期关注初始化
var bask_edit=(function(){
    var bask_edit=function(){
        $("#edit-patty").click(function () {
            listefit.config({
                title:"近期关注",
                list:dicListConfig(indulist,pattyselectcode),
                besure: function () {
                    var h="";
                    pattyselectcode=[];
                    $(".list li").each(function(index,element){
                        if($(this).hasClass("active")){
                            h=h+"<li>"+$(this).html()+"</li>"
                            var map={};
                            map.code=$(this).attr("tip-l-i");
                            pattyselectcode.push(map);

                        }

                    });
                    listselectcode1=choiceContent();
                    $(".pattylist").html(h)
                }
            });
        });

    }
    return{
        click:bask_edit
    }
})();
//联系方式
var people_edit=(function(){
    var edit_click=function(){
        $(".add-contact").bind("click", function () {
            inputlsit_edit.config({
                title:"联系方式",
                list:[{id:"phone",lable:"电话",type:"tel",value:config.data.phone,maxlength:"11"},
                    {id:"email",lable:"邮箱",type:"email",value:config.data.email,maxlength:"100"},
                    {id:"wechat",lable:"微信",value:config.data.wechat,maxlength:"100"}],
                submit: function () {
                    var phone=$("#phone").val();
                    var email=$("#email").val();
                    var wechat=$("#wechat").val();
                    if(check(phone,email)){
                        config.data.phone=phone;
                        config.data.email=email;
                        config.data.wechat=wechat;
                        showtel(config.data.phone,config.data.email,config.data.wechat);
                        inputlsit_edit.close();
                    }
                }
            });
        });
    }
    //联系方式展示
    var showtel=function(phone,email,wechat){
       if(phone==""||phone==null){
          $(".phone").html("(手机)").addClass("config");
       }else{
          $(".phone").html(phone).removeClass("config");
       }
       if(email==""||email==null){
          $(".email").html("(邮箱)").addClass("config");
       }else{
          $(".email").html(email).removeClass("config");
       }
       if(wechat==""||wechat==null){
           $(".wchat").html("(微信)").addClass("config");
       }else{
           $(".wchat").html(wechat).removeClass("config");
       }


    }
    return{
        click:edit_click
    }
})();
//添加所属机构
var add_investment=(function(){
    function addclick() {
        $("#add-touzi").bind("click", function () {
            inputlsit_edit.config({
                title: "添加所属机构",
                list: [
                    {id: "investment", lable: "投资机构", readonly:"readonly"},
                    {id: "posi", lable: "职位",maxlength:"20"},
                    {id: "state", lable: "状态", type:"select",optionlist:[{id:"stateon",text:"在职"},{id:"stateoff",text:"已离职"}]}
                ],
                submit: function () {
                    if(selectorgain.data.orgaincode==""){
                        $.showtip("请选择投资机构");
                        setTimeout("$.hidetip()",2000);
                    }else{
                        if(!checkInvestment(invcode,selectorgain.data.orgaincode)){
                            $.showtip("该投资机构已选择");
                            setTimeout("$.hidetip()",2000)
                        }else{
                            var investmentPeople=new Object();
                            investmentPeople.code=selectorgain.data.orgaincode;
                            investmentPeople.investmentname=$("#investment").val();
                            investmentPeople.posi=$("#posi").val().trim();
                            investmentPeople.state=$("#state").val();
                            investPeo.push(investmentPeople);
                            showInvestment();
                            inputlsit_edit.close();
                        }

                    }
                },
                complete: function () {
                    $("#investment").click(function () {
                        selectorgain.config();
                        $(this).focus(function () {
                            $(this).blur();
                        })
                    });
                }

            });
        });

    };
    return{
        addclick:addclick
    }
})();
//添加姓名
var add_invesor=(function(){
    function addclick() {
        $(".peoplename").unbind().bind("click", function () {
            inputlsit_edit.config({
                title: "添加投资人",
                list: [{id: "investorname", lable: "姓名",value:config.data.name,maxlength:"20"}],
                submit: function() {
                    if($("#investorname").val().trim()==""){
                        $.showtip("姓名不能为空");
                        setTimeout("$.hidetip()",2000);
                    }else{
                        config.data.name=$("#investorname").val();
                        $(".investor").html(config.data.name);
                        inputlsit_edit.close();
                        addclick();
                    }
                }

            });
        });

    };
    return{
        addclick:addclick
    }
})();
//投资机构展示
function showInvestment(){
    var html="";
    invcode="";
    for(var i=0;i<investPeo.length;i++){
        html=html+'<li p-index="'+i+'"><span class="comp">'+investPeo[i].investmentname+'</span>'
        html=html+((investPeo[i].posi!="")?('<span class="comp">'+investPeo[i].posi+'</span>'):('<span class="color-def">职位(未填写)</span>'))
        html=html+'<span class="comp color-def">('+investPeo[i].state+')</span>'
        html=html+"</li>"
        invcode+=investPeo[i].code+",";
    }
    if(investPeo.length==0){
        html='<li data-i="1" id="investmentName"><span class="color-def comp">投资机构名称</span><span class="color-def comp">职位</span><span class="color-def comp">在职状态</span></li>'
    }
    $(".peopleContent").html(html);
    peoplecick();
}
//创建投资人点击
var add_investor=(function(){
    $(".creatInvestor").click(function(){
        if(config.data.name.trim()==""){
            $.showtip("姓名不能为空");
            setTimeout("$.hidetip()",2000);
        }else{
            type="1";
            creat_ajax(type);
        }
    });

})();
//创建投资人点击
var add_trad=(function(){
    $(".creatTrad").click(function(){
        if(config.data.name.trim()==""){
            $.showtip("姓名不能为空");
            setTimeout("$.hidetip()",2000);
        }else{
            type="2";
            creat_ajax(type);
        }
    });

})();
//页面调用的ajax
var creat_ajax= function (type) {
    if(!investPeo.length&&type==2){
        inputlsit_edit.config({
            title:"提示",//弹层标题
            html:true,//是否以html显示
            besurebtn:"确定",//确定按钮文字
            htmltext:"<div style='text-align: center;font-size: 15px;padding: 0 14px;'>请添加所属机构，否则无法创建交易信息</div>",
            submit: function () {
                inputlsit_edit.close();
            }});
        return;
    }

    $.showloading();
    $(".creatInvestor,.creatTrad").attr("disabled","true");
    $.ajax({
        url:"addInvestorInfo.html",
        type:"post",
        async:true,
        data:{
            name:config.data.name.trim(),
            investment:choiceInvestment(investPeo),
//            investment:"",
            phone:config.data.phone.trim(),
            email:config.data.email.trim(),
            wechat:config.data.wechat.trim(),
            pattylist:listselectcode1,
            indulist:induselectcode1,
            base_invesnote_content:$(".compnote").val().trim(),
            logintype:cookieopt.getlogintype()
        },
        dataType: "json",
        success: function(data){
            $.hideloading();
            if(data.message=="success"){
                $.showtip("保存成功");
                setTimeout(function(){
                    $.hidetip();
                    if(type==1){//创建公司
                    	//--start duwenjie 2015-12-07
                    	//判断返回参数是否为空，确定刷新还是跳转返回路径
                    	if(backherf!=""){
                    		window.location.href=backherf+"&logintype="+cookieopt.getlogintype();
                    	}else{
                    		window.location.href="investor_add.html?logintype="+cookieopt.getlogintype();
                    	}
                    	//--end
                    }else if(type==2){//创建交易
                        $(".creatInvestor,.creatTrad").removeAttr("disabled");
//                        window.location.href="addTradeInfoinit.html?compcode="+data.code+"&logintype="+cookieopt.getlogintype();
                        addtrade_config(data.code);
                    }
                },300);


            }else{
                $.showtip("保存失败");
                setTimeout("$.hidetip()",2000);
                $(".creatInvestor,.creatTrad").removeAttr("disabled");
            }

            $.hideloading();
        },error: function () {
            $(".creatInvestor,.creatTrad").removeAttr("disabled");
        }
    });
};

function peoplecick(){
    $("li[p-index]").unbind().bind("click", function () {
        var i=$(this).attr("p-index");
        inputlsit_edit.config({
            title:"修改投资机构",
            besurebtn:"修改",
            cancle:"删除",
            list:[
                {id: "investment", lable: "投资机构", readonly:"readonly",value:investPeo[i].investmentname,maxlength:"11"},
                {id: "posi", lable: "职位",value:investPeo[i].posi,maxlength:"20"},
                {id: "state", lable: "状态", type:"select",optionlist:[{id:"stateon",text:"在职"},{id:"stateoff",text:"已离职"}]}
            ],
            submit: function () {
                var investmentPeople=new Object();
                investmentPeople.code=investPeo[i].code;
                investmentPeople.investmentname=$("#investment").val();
                investmentPeople.posi=$("#posi").val().trim();
                investmentPeople.state=$("#state").val().trim();
                investPeo[i]=investmentPeople;
                showInvestment();
                inputlsit_edit.close();
            },
            canmit: function () {
                investPeo.baoremove(i);
                showInvestment();
                inputlsit_edit.close();
            },
            complete: function () {
                if(investPeo[i].state=="已离职"){
                    $("#stateoff").attr("selected",true);
                }else{
                    $("#stateon").attr("selected",true);
                }
            }

        });
    });
}
function dicListConfig(vList,vObj){
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
//验证邮箱，手机号格式
function check(phone,email){
	  var check_phone= /^1\d{10}$/;//手机格式验证
    var check_email=/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/ ;//邮箱验证

    if(phone!=""){
        if(!check_phone.test(phone)){
            $.hideloading();
            $.showtip("请输入有效的手机号码");
            setTimeout("$.hidetip()",2000);
            return false;
        }
    }
    if(email!=""){
        if(!check_email.test(email)){
            $.hideloading();
            $.showtip("请输入有效的邮箱");
            setTimeout("$.hidetip()",2000);
            return false;
        }
    }
    return true;
}
//投资机构选择页面
var selectorgain=(function () {
    var opt={orgaincode:""};
    var dataconfig= function () {
        opt.page={
            pageCount:1,
            pageSize:new CommonVariable().PAGESIZE,
            totalCount:2
        };
        opt.firstload=true;
        $(".inner").html("<div class='lists'></div>");
        $(".item:first").unbind().bind("click",function () {
            $("#investment").val("");
            opt.orgaincode="";
            WCsearch_list.closepage();
        });
    };
    var config=function(){

        WCsearch_list.config({
            allowadd:false,
            searchclick: function () {
                dataconfig();
                if(opt.dropload){
                    opt.dropload.Destroy();
                };
                $.showloading();
                orgain_ajax();
                opt.dropload= WCsearch_list.dropload({
                    loadDownFn: function (me) {
                        if(Number(opt.page.pageCount)<Number(opt.page.totalPage)){
                            opt.page.pageCount=opt.page.pageCount+1;
                            orgain_ajax();
                        }else{
                            $.showtip("暂无更多数据");
                            setTimeout(function () {
                                $.hidetip();
                            },2000);
                            opt.dropload.resetload();
                            opt.dropload.lock();
                        }
                    }
                })
            }
        });
        dataconfig();
    };
    var orgain_ajax=function(){
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url:"findInvestmentNameListByName.html",
            data:{
                name:$(".select-page-input").val().trim(),
                pageSize:opt.page.pageSize,
                pageCount:opt.page.pageCount,
                logintype:cookieopt.getlogintype(),
                type:"1"
            },
            success: function (data) {
                opt.page=data.page;
                if(data.message=="success"){
                    var html=template.compile(data_model.orgainlist)(data);
                    $(".lists").append(html);

                    opt.dropload.resetload();
                    $(".item").unbind().bind("click",function () {
                        $("#investment").val($(this).html());
                        opt.orgaincode=$(this).attr('data-org-code');
                        WCsearch_list.closepage();
                    })
                }else{
                    $.showtip("查询异常");
                    setTimeout(function () {
                        $.hidetip();
                    },2000);
                }
                $.hideloading();
            }
        });
    };
    return{
        config:config,
        data:opt
    }

})();
var data_model={
    orgainlist:'<% for (var i=0;i<list.length;i++ ){%>' +
    '<div data-org-code="<%=list[i].base_investment_code%>" class="item"><%=list[i].base_investment_name %></div>' +
    '<%}%>'
};
function choiceContent(){
    var list="[";
    $(".list li[class='active']").each(function(){
        list+="{code:'"+$(this).attr("tip-l-i")+"'},";
    });
    list+="]";

    return list;
}

function choiceInvestment(investPeo){
    var list="[";
    if(investPeo!=null&&investPeo.length>0){
        for(var i=0;i<investPeo.length;i++){
            list+="{code:'"+investPeo[i].code+"',posi:'"+investPeo[i].posi+"',state:'"+investPeo[i].state+"'},";
        }
    }
    list=list+"]";
    return list;
}
function checkInvestment(a,b){
    if(b!=""&& a.indexOf(b)>=0){
        return false;
    }
    return true;
}
function addtrade_config(investorcode) {
    if(investPeo.length>1){
        var array=[];
        for(var i=0;i<investPeo.length;i++){
            var map={};
            map.id=investPeo[i].code;
            map.text=investPeo[i].investmentname;
            array.push(map);
        }
        inputlsit_edit.config({
            title: "请选择添加交易的投资机构",
            list: [
                {id: "investment", lable: "投资机构", type:"select",optionlist:array}
            ],
            submit: function () {
                var investmentcode=$("#investment option:selected").attr("id");
                inputlsit_edit.close();
                window.location.href="addTradeInfoinit.html?logintype="+cookieopt.getlogintype()+
"&investmentcode="+investmentcode+"&investorcode="+investorcode;
            }
        });
    }else{
        window.location.href="addTradeInfoinit.html?logintype="+cookieopt.getlogintype()+"&investmentcode="+investPeo[0].code+"&investorcode="+investorcode;
    }
}