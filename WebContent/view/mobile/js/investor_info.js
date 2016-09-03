/**
 * Created by shbs-tp001 on 15-9-24.
 */

var investorcode;//投资人id
var pagetable;
var meetingtable;
var payattJson;
var payattlist;//近期关注选择框
$(function () {
    congfig.init();
    editinfo.click_config();
    editphone.clickphone_config();
    editinvest.click_config();
    addinvestor.addclick();
    trade.click();

});
var congfig=(function(){

    var data={
        inducont:investor_inducont==""?[]:eval(investor_inducont),
        payattcont:investor_payattcont==""?[]:eval(investor_payattcont),
        cont:investor_cont==""?[]:eval(investor_cont),
        viewTradeInfo:eval(viewTradeInfo),
        noteList:eval(noteList),
        investorbInfo:investorbInfo,
        noteconfignum:CommonVariable().NOTECONFIGNUM,
        code:investorbInfo.base_investor_code,
        name:$(".investor").html(),
        phone:phone,
        email:email,
        wchat:wechat,
        id:$(".investor").attr("id"),
        induList:eval(induList),
        unDelLabelList:eval(unDelLabelList),
        type:"",
        version:investorbInfo.base_datalock_viewtype,
        zhangkai:false,
        tradeshowmore:false
    };
    var init=function(){

        payattJson=eval(data.payattcont);//近期关注
        payattlist=dicListConfig(induList,payattJson);
        var inducont=template.compile(data_model.inducont)(data);
        var payattcont=template.compile(data_model.payattcont)(data);
        var cont=template.compile(data_model.cont)(data);
        var noteList=template.compile(data_model.noteList)(data);
        var viewTradeInfo=template.compile(data_model.viewTradeInfo)(data);
        investorcode = $(".investor").attr("id");
        if(inducont==""){
            $(".induc").html('<li>暂无数据</li>');
        }else{
            $(".induc").html(inducont);
        }
        if(payattcont==""){
            $(".patty").html('<li>暂无数据</li>');
        }else{
            $(".patty").html(payattcont);
        }
        if(cont==""){
            $(".peopleContent").html('<li style="color: black";>暂无数据</li>');
        }else{
            $(".peopleContent").html(cont);
        }
        if(noteList==""){
            $(".notetable").html('<tr><td colspan=\'4\' style="text-align: center">暂无数据</td></tr>');
        }else{
            $(".notetable").html(noteList);
        }
        if(viewTradeInfo==""){
            $(".stage").html(data_model.viewTradeInfoNull);
        }else{
            $(".stage").html(viewTradeInfo);
        }
        /*$(".peopleContent").html(cont);
         $(".notetable").html(noteList);
         $(".stage").html(viewTradeInfo);*/
        eidtmes(data);
        if(data.noteconfignum<data.noteList.length){
            $(".closeshearch").show();
            $("[nodemore]").hide();
        }

        if(backherf!=""){
            $(".goback").show();
            $(".goback").click(function () {
                window.location.href=backherf+"&logintype="+cookieopt.getlogintype();
            });
        }
        click();
    };
    var click= function () {
        $(".closeshearch").unbind().bind("click", function () {
            if(!$(this).hasClass("active")){
                $("[nodemore]").show();
                $(this).html('收起<span class="glyphicon glyphicon-chevron-up"></span>').addClass("active");
                data.zhangkai=true;
                click();
            }else{
                $("[nodemore]").hide();
                $(this).html('更多<span class="glyphicon glyphicon-chevron-down"></span>').removeClass("active");
                data.zhangkai=false;
            }
        });
        $("[del-code]").unbind().bind("click", function () {
            var $this=$(this);
            inputlsit_edit.config({
                title:"提示",//弹层标题
                html:true,//是否以html显示
                besurebtn:"确定",//确定按钮文字
                cancle:"取消",//取消按钮文字 （如果不写，则不会显示该按钮）
                htmltext:"<div style='text-align: center;font-size: 15px;'>您确定要删除该条记录吗？</div>",
                submit: function () {
                    deletnote($this.attr("del-code"));
                    inputlsit_edit.close();
                },//点击确定按钮回调方法
                canmit: function () {
                    inputlsit_edit.close();
                }//点击取消按钮回调方法
            })
        });
    };
    //点击提交备注按钮
    $(".btn-note").click(function(){
        checkpeoexit();
        submitNote();

    });
    //交易加载更多
    $(".morebtn").click(function(){
        if(checkpeoexit()){
            congfig.data.tradeshowmore=true;
            tradeMore.showpage();
//            tradeMore.config_data();
//            tradeMore.pageDataRendering();

        }

    });
    //交易更多页面的返回按钮点击事件
    $(".tradereturn").click(function(){
        pagetable.hidepage();
        congfig.data.tradeshowmore=false;
    });
    //会议记录事件
    $(".meetingClick").click(function(){
        if(checkpeoexit()) {
            pageMeetingDataRendering.show();
        }
    });

    //会议子页返回
    $(".meetingReturn").click(function(){
        pagetable.hidepage();
    });

    //更新记录事件
    $(".updlogClick").click(function(){
        if(checkpeoexit()) {
            pageUpdlogDataRendering.show();
        }
    });

    //更新子页返回
    $(".updlogReturn").click(function(){
        pagetable.hidepage();
    });
    //行业弹层
    $(".induc").click(function () {
        if(congfig.data.id==""){
            $.showtip("此联系人不存在");
            setTimeout("$.hidetip()",2000);
            return;
        }
        listefit.config({
            title:"关注行业",
            list: dicListConfig(true,congfig.data.induList,congfig.data.inducont,unDelLabelList),
            besure: function () {
                $.showloading();//等待动画
                if(oldContent(congfig.data.inducont)==choiceContent()){
                    $.showtip("数据未修改");
                    setTimeout("$.hidetip()",2000);
                    $.hideloading();
                    return;
                }
                $.ajax({
                    url:"updateInvsetorInfo.html",
                    type:"post",
                    async:true,
                    data:{
                        type:'Lable-indu',
                        investorCode:congfig.data.id,
                        investorName:congfig.data.name,
                        logintype:cookieopt.getlogintype(),
                        oldData:oldContent(congfig.data.inducont),
                        newData:choiceContent(),
                        version:congfig.data.version
                    },
                    dataType: "json",
                    success: function(data){
                        $.hideloading();//等待动画隐藏
                        if(data.message=="success"){
                            $.showtip("保存成功!");
                            congfig.data.inducont=(data.list[0]==null?[]:eval(data.list));
                            congfig.data.version=data.version;
                            congfig.data.investorbInfo.base_datalock_viewtype=data.version;
                            var html=template.compile(data_model.inducont)(congfig.data);
                            if(html==""){
                                html='<li>暂无数据</li>';
                            }
                            $(".induc").html(html);
                        }else{
                            $.showtip(data.message);
                        }
                        setTimeout("$.hidetip()",2000);
                    }
                });
            }
        });
    });

    //近期特别关注弹层
    $(".patty").click(function () {
        if(congfig.data.id==""){
            $.showtip("此联系人不存在");
            setTimeout("$.hidetip()",2000);
            return;
        }
        listefit.config({
            title:"近期特别关注",
            list:dicListConfig(true,congfig.data.induList,congfig.data.payattcont,null),
            besure: function () {
                $.showloading();//等待动画
                if(oldContent(congfig.data.payattcont)==choiceContent()){
                    $.showtip("数据未修改");
                    setTimeout("$.hidetip()",2000);
                    $.hideloading();
                    return;
                }
                $.ajax({
                    url:"updateInvsetorInfo.html",
                    type:"post",
                    async:true,
                    data:{
                        type:'Lable-payatt',
                        investorCode:congfig.data.id,
                        investorName:congfig.data.name,
                        logintype:cookieopt.getlogintype(),
                        oldData:oldContent(congfig.data.payattcont),
                        newData:choiceContent(),
                        version:congfig.data.version
                    },
                    dataType: "json",
                    success: function(data){
                        $.hideloading();//等待动画隐藏
                        if(data.message=="success"){
                            $.showtip("保存成功!");
                            congfig.data.payattcont=(data.list[0]==null?[]:eval(data.list));
                            congfig.data.version=data.version;
                            congfig.data.investorbInfo.base_datalock_viewtype=data.version;
                            var html=template.compile(data_model.payattcont)(congfig.data);
                            if(html==""){
                                html='<li>暂无数据</li>';
                            }
                            $(".patty").html(html);
                        }else{
                            $.showtip(data.message);
                        }
                        setTimeout("$.hidetip()",1000);
                    }
                });
            }
        });
    });
    //删除备注
    var deletnote=function(notecode){
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "investornote_delet.html",
            data: {
                notecode: notecode,
                logintype: cookieopt.getlogintype()
            },
            success: function (data) {
                if(data.message=="success"){
                    $.showtip("删除成功");
                    noteconfig(notecode);
                }else if(data.message=="failure"){
                    $.showtip("数据已被删除","error");
                    noteconfig(notecode);
                }else{
                    $.showtip("请求失败");

                }
                setTimeout(function () {
                    $.hidetip();
                },2000);
                $.hideloading();
            }
        });
    };
    //跳转添加交易
    $("#add-rongzi").click(function (e) {

        if(!congfig.data.cont.length){
            inputlsit_edit.config({
                title:"提示",//弹层标题
                html:true,//是否以html显示
                besurebtn:"确定",//确定按钮文字
                htmltext:"<div style='text-align: center;font-size: 15px;'>请添加所属机构，再添加交易信息</div>",
                submit: function () {
                    inputlsit_edit.close();
                }});
        }else if(congfig.data.cont.length>1){
            var array=[];
            for(var i=0;i<congfig.data.cont.length;i++){
                var map={};
                map.id=congfig.data.cont[i].code;
                map.text=congfig.data.cont[i].name;
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
                        "&backherf=findInvestorDeatilByCode.html?code="+congfig.data.id+"&investmentcode="+investmentcode+"&investorcode="+congfig.data.id;
                }

            });
        }else if(congfig.data.cont.length==1){
            var investmentcode=congfig.data.cont[0].code;
            window.location.href="addTradeInfoinit.html?logintype="+cookieopt.getlogintype()+
                "&backherf=findInvestorDeatilByCode.html?code="+congfig.data.id+"&investmentcode="+investmentcode+"&investorcode="+congfig.data.id;
        }
    });
    //note删除成功后的回调函数
    var noteconfig= function (notecode) {
        if(notecode){
            data.noteList.baoremove(Number($("[del-code='"+notecode+"']").attr("i")));
        }

        var noteList=template.compile(data_model.noteList)(data);
        if(noteList==""){
            noteList='<tr><td colspan=\'4\' style="text-align: center">暂无数据</td></tr>';
        }
        $(".notetable").html(noteList);
        if(data.noteconfignum>= data.noteList.length){

            $(".closeshearch").html('更多<span class="glyphicon glyphicon-chevron-down"></span>').removeClass("active").hide();
        }else{
            if(!$(".closeshearch").hasClass("active")){
                $(".closeshearch").html('更多<span class="glyphicon glyphicon-chevron-down"></span>').show();
            }
        }
        if(!data.zhangkai){
            $("[nodemore]").hide();
        }

        click();
    };
    return{
        init:init,
        data:data,
        noteconfig:noteconfig
    }
})();
var eidtmes=function(data){

    congfig.data.name=congfig.data.investorbInfo.base_investor_name;
    congfig.data.phone=congfig.data.investorbInfo.base_investor_phone;
    congfig.data.email=congfig.data.investorbInfo.base_investor_email;
    congfig.data.wchat=congfig.data.investorbInfo.base_investor_wechat;

    $(".investor").html(congfig.data.name);
    if(congfig.data.phone==""||congfig.data.phone==null){
        $("#phone").html("(手机)").addClass("config");
    }else{
        $("#phone").html(congfig.data.phone).removeClass("config");
    }
    if(congfig.data.email==""||congfig.data.email==null){
        $("#email").html("(邮箱)").addClass("config");
    }else{
        $("#email").html(congfig.data.email).removeClass("config");
    }
    if(congfig.data.wchat==""||congfig.data.wchat==null){
        $("#wchat").html("(微信)").addClass("config");
    }else{
        $("#wchat").html(congfig.data.wchat).removeClass("config");
    }


};

var aa= function () {
    if(congfig.data.id==""){
        $.showtip("此联系人不存在");
        setTimeout("$.hidetip()",2000);
        return;
    }
    $.ajax({
        url:"editInvestorInfo.html",
        type:"post",
        async:true,
        data:{
            info:JSON.stringify(congfig.data.investorbInfo),
            type:congfig.data.type,
            logintype:cookieopt.getlogintype()
        },
        dataType: "json",
        success: function(data){
            $.hideloading()
            if(data.message=="success"){
                $.showtip("保存成功");
                setTimeout("$.hidetip()",2000);
                congfig.data.investorbInfo=data.baseInfo;
                congfig.data.version=data.baseInfo.base_datalock_viewtype;
                eidtmes(data);
                inputlsit_edit.close();
            }else if(data.message=="已被修改,请刷新页面再修改"){
                $.showtip("数据已被修改，请刷新页面再修改");
                setTimeout("$.hidetip()",2000);
            }else{
                $.showtip("保存失败");
                setTimeout("$.hidetip()",2000);
            }


        }
    });
};
//添加备注
function submitNote(){
    var note=$("#note").val();
    if(note.trim()!=""){
        $.ajax({
            url:"addInvestorNote.html",
            type:"post",
            async:true,
            data:{
                base_investor_code: $(".investor").attr("id"),
                base_invesnote_content:$("#note").val(),
                logintype:cookieopt.getlogintype()},
            dataType: "json",
            success: function(data){
                $("#note").val("");
                $.showtip("保存成功");
                setTimeout("$.hidetip()",2000);
                if(data.noteList.length>0){
                    congfig.data.noteList=data.noteList;
                    congfig.noteconfig();
                }
            }
        });
    }else{
        $("#note").focus();
        $.showtip("备注信息不能为空");
        setTimeout("$.hidetip()",2000);
    }
}
//点击交易更多渲染近期交易界面
var tradeMore=(function(){
    var page={};
    var firstload=true;
    page.pageCount=1;
    function config_data() {
        page.pageCount=1;
        firstload=true;
    }
    function showpage(){
        pagetable=$("#tradePage").showpage();
        config_data();
        pageDataRendering();
    }
    function pageDataRendering(){
        $.ajax({
            url:"findNoteByInvestorCode.html",
            type:"post",
            async:true,
            data:{
                code:investorcode,
                pageCount:page.pageCount,
                logintype:cookieopt.getlogintype()},
            dataType: "json",
            success: function(data){

                var html=template.compile(data_model.viewTradeInfo)(data);
                if(html==""){
                    html=data_model.viewTradeInfoNull;
                }
                if(congfig.data.tradeshowmore){
                    $("#pageTradeBody tbody").html(html);
                }
                if(page.pageCount==1){
                    $(".stage").html(html);
                }
                $(".tradeLable").html("共"+data.page.totalCount+"条");
                page=data.page;
                trade.click();
                pageList(page);


            }
        });
    }
    //近期交易分页下页上页请求
    function pageList(page){
        if(Number(page.totalPage)>0) {
            $('.pagination').jqPagination({
                link_string: '/?page={page_number}',
                max_page: page.totalPage,
                current_page: page.pageCount,
                paged: function (p) {
                    if (p != page.pageCount) {
                        page.pageCount=p;
                        pageDataRendering();
                    }
                }
            });
            $('.pageaction').show();
            firstload=false;
        }else{
            $('.pageaction').hide();
        }
    }
    return{
        pageDataRendering:pageDataRendering,
        config_data:config_data,
        showpage:showpage
    }
})();
//编辑投资人姓名
var editinfo=(function () {
    var click_config= function () {
        $(".investor").click(function () {
            inputlsit_edit.config({
                title:"编辑投资人",
                list:[{id :"investorName",lable:"投资人",value:congfig.data.name,maxlength:"20"}],
                submit: function () {
                    var investorName=$("#investorName").val();

                    if(investorName==congfig.data.name){
                        $.showtip("名称未改变");
                        setTimeout("$.hidetip()",2000);
                    }else if(investorName.trim()==""){
                        $.showtip("名称不能为空");
                        setTimeout("$.hidetip()",2000);
                    }else{
                        inputlsit_edit.close();
                        congfig.data.investorbInfo.base_investor_name=investorName;
                        congfig.data.type="name";

                        aa();
                    }

                    inputlsit_edit.close()
                }
            });
        });
    };
    return{
        click_config:click_config
    }
})();
//编辑投资人的手机号，微信，邮箱
var editphone=(function () {
    var clickphone_config= function () {
        $(".basic").click(function () {

            inputlsit_edit.config({
                title:"编辑联系方式",
                list:[{id :"telphone",lable:"手机号",value:congfig.data.phone,maxlength:"11"},
                    {id :"e-mail",lable:"邮箱",value:congfig.data.email,maxlength:"100"},
                    {id :"wechat",lable:"微信",value:congfig.data.wchat,maxlength:"100"}],
                submit: function () {
                    $.showloading();
                    var phone = $("#telphone").val().trim();
                    var email=$("#e-mail").val().trim();
                    var wchat=$("#wechat").val().trim();
                    if(congfig.data.phone==null){
                        congfig.data.phone="";
                    }
                    if(congfig.data.email==null){
                        congfig.data.email="";
                    }
                    if(congfig.data.wchat==null){
                        congfig.data.wchat="";
                    }
                    if(phone==congfig.data.phone&&email==congfig.data.email&&wchat==congfig.data.wchat) {
                        inputlsit_edit.close();
                        $.showtip("数据未修改");
                        $.hideloading();
                        setTimeout("$.hidetip()", 2000);
                        inputlsit_edit.close()
                    }else{
                        if(check(phone,email)!=false){
                            inputlsit_edit.close();
                            congfig.data.investorbInfo.base_investor_phone=phone.trim();
                            congfig.data.investorbInfo.base_investor_wechat=wchat.trim();
                            congfig.data.investorbInfo.base_investor_email=email.trim();
                            congfig.data.type="phone";

                            aa();
                        };

                    }

                }
            });
        });
    };
    return{
        clickphone_config:clickphone_config
    }
})();
var addinvestor=(function(){
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
                        for(var i=0;i<congfig.data.cont.length;i++){
                            if(selectorgain.data.orgaincode==congfig.data.cont[i].code){
                                $.showtip("已属于该投资机构");
                                setTimeout("$.hidetip()",2000);

                                return;
                            }
                        }
                        var investmentname=$("#investment").val();
                        addinvest_ajax(investmentname);

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
function addinvest_ajax(investmentname){

    $.ajax({
        url:"addInvestor.html",
        type:"post",
        async:true,
        data:{
            code:congfig.data.code,
            investmentcode:selectorgain.data.orgaincode,
            investmentname:investmentname,
            state:$("#state").val().trim(),
            posi:$("#posi").val().trim(),
            oldstr:"",
            name:congfig.data.name.trim(),
            version:congfig.data.version,
            logintype:cookieopt.getlogintype()
        },
        dataType: "json",
        success: function(data){
            $.hideloading();
            if(data.message=="success"){
                $.showtip("保存成功");
                setTimeout("$.hidetip()",2000);
                congfig.data.cont=eval(data.investorInfo.view_investment_cont);
                congfig.data.version=data.version;
                congfig.data.investorbInfo.base_datalock_viewtype=data.version;
                var cont=template.compile(data_model.cont)(congfig.data);
                if(cont==""){
                    cont='<li>暂无数据</li>';
                }
                $(".peopleContent").html(cont);
                editinvest.click_config();
                inputlsit_edit.close();
            }else if(data.message=="已被修改,请刷新页面再修改"){
                $.showtip("数据已被修改，请刷新页面再修改");
                setTimeout("$.hidetip()",2000);
            }else{
                $.showtip("保存失败");
                setTimeout("$.hidetip()",2000);
            }

        }
    });
}
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
}
//编辑投资人所属机构
var editinvest=(function(){
    var click_config= function () {
        $(".peopleContent li").each(function (index,el) {
            $(this).unbind().bind("click",function(){

                inputlsit_edit.config({
                    title: "编辑投资人",
                    list: [
                        {id: "investment", lable: "投资机构", value: congfig.data.cont[index].name,disabled:"disabled"},
                        {id: "posi", lable: "职位", value: congfig.data.cont[index].posi,maxlength:"20"},
                        {id: "state", lable: "状态", type:"select",optionlist:[{id:"stateon",text:"在职"},{id:"stateoff",text:"已离职"}]}
                    ],
                    submit: function () {
                        $.showloading();
                        if(congfig.data.cont[index].name==$("#investment").val()&&congfig.data.cont[index].posi==$("#posi").val()&&congfig.data.cont[index].state==$("#state").val()){
                            $.showtip("数据未修改");
                            setTimeout("$.hidetip()",2000);
                            $.hideloading();
                            inputlsit_edit.close();
                        }else{
                            $.ajax({
                                url:"editInvestor.html",
                                type:"post",
                                async:true,

                                data:{
                                    code:congfig.data.code,
                                    investmentcode:congfig.data.cont[index].code,
                                    investmentName:$("#investment").val(),
                                    oldstr:"投资机构名称:"+congfig.data.cont[index].name+";职位:"+congfig.data.cont[index].posi+";状态:"+congfig.data.cont[index].state,
                                    state:$("#state").val(),
                                    posi:$("#posi").val().trim(),
                                    name:congfig.data.name,
                                    version:congfig.data.version,
                                    logintype:cookieopt.getlogintype()
                                },
                                dataType: "json",
                                success: function(data){
                                    $.hideloading()
                                    if(data.message=="success"){
                                        $.showtip("保存成功");
                                        setTimeout("$.hidetip()",2000);
                                        congfig.data.cont=eval(data.investorInfo.view_investment_cont);
                                        congfig.data.version=data.version;
                                        congfig.data.investorbInfo.base_datalock_viewtype=data.version;
                                        var cont=template.compile(data_model.cont)(congfig.data);
                                        if(cont==""){
                                            cont='<li>暂无数据</li>';
                                        }
                                        $(".peopleContent").html(cont);
                                        editinvest.click_config();
                                        inputlsit_edit.close();
//                                        eidtmes(data);
                                    }else if(data.message=="已被修改,请刷新页面再修改"){
                                        $.showtip("数据已被修改，请刷新页面再修改");
                                        setTimeout("$.hidetip()",2000);
                                    }else{
                                        $.showtip("保存失败");
                                        setTimeout("$.hidetip()",2000);
                                    }


                                }
                            });
                        }


                    },
                    complete:function() {
                        if(congfig.data.cont[index].state=="已离职"){
                            $("#stateoff").attr("selected",true);
                        }
                    }
                });
            });

        });


    };
    return{
        click_config:click_config
    }
})();

var trade=(function(){
    var click=function(){
        $(".stage tr").unbind().bind("click",function () {
            var code=$(this).attr("code");
            if(code!=null&&code!=""){
                window.location.href="findTradeDetialInfo.html?tradeCode="+code+"&logintype="+cookieopt.getlogintype()+"&backherf=findInvestorDeatilByCode.html?code="+congfig.data.id;
            }

        });
        $("#pageTradeBody tr").unbind().bind("click",function () {
            var code=$(this).attr("code");
            window.location.href="findTradeDetialInfo.html?tradeCode="+code+"&logintype="+cookieopt.getlogintype()+"&backherf=findInvestorDeatilByCode.html?code="+congfig.data.id;
        });
        $("[del-trad-code]").unbind().bind("click",function (e) {
            var $this=$(this);
            inputlsit_edit.config({
                title:"提示",//弹层标题
                html:true,//是否以html显示
                besurebtn:"确定",//确定按钮文字
                cancle:"取消",//取消按钮文字 （如果不写，则不会显示该按钮）
                htmltext:"<div style='text-align: center;font-size: 15px;'>您确定要删除该条记录吗？</div>",
                submit: function () {
                    delinvestor_trade($this);
                    inputlsit_edit.close();
                },//点击确定按钮回调方法
                canmit: function () {
                    inputlsit_edit.close();
                }//点击取消按钮回调方法
            });
            e.stopPropagation();
        });
    };

    function delinvestor_trade($this) {
        var tradecode=$this.attr("del-trad-code");
        var investmentcode=$this.attr("investmentcode");
        var companyName=$this.attr("companyName");
        var tradeDate=$this.attr("tradeDate");
        var index=$this.attr("i");
        var investmentName;
        for(var i in congfig.data.cont){
            if(congfig.data.cont[i].code===investmentcode){
                investmentName=congfig.data.cont[i].name;
                break;
            }
        }
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url:"delinvestor_trade.html",
            data:{
                investmentcode:investmentcode,
                tradecode:tradecode,
                logintype:cookieopt.getlogintype(),
                investmentName:investmentName,
                tradeDate:tradeDate,
                companyName:companyName,
                investorcode:investorcode
            },
            success: function (data) {
                if(data.message=="success"){
                    $.showtip("删除成功");
                    tradeMore.pageDataRendering();
                }else if(data.message=="notrade"){
                    $.showtip("该条交易已被删除");
                }else{
                    $.showtip("数据请求错误");
                }
                setTimeout(function () {
                    $.hidetip();
                },2000);
                $.hideloading();
            }
        });
    }
    return{
        click:click
    }
})();

var data_model={
    inducont:'<% for (var i=0;i<inducont.length;i++ ){%>' +
        '<li><%=inducont[i].name%></li>' +
        '<%}%>',
    payattcont:'<% for (var i=0;i<payattcont.length;i++ ){%>' +
        '  <li><span class="comp"><%=payattcont[i].name%></span></li>' +
        '<%}%>',
    cont:'<%for (var i=0;i<cont.length;i++ ){%>' +
        '  <li><span class="comp"><%=cont[i].name%></span> <%if(cont[i].posi==""){%><span class="comp color-def">职位(未填写)</span><%}else{ %><span class="comp"><%=cont[i].posi%></span><%}%></span><span class="comp color-def">(<%=cont[i].state%>)</span></li>' +
        '<%}%>',
    viewTradeInfo:'<% for (var i=0;i<viewTradeInfo.length;i++ ){ %>' +
        '<tr  code=<%=viewTradeInfo[i].base_trade_code%>>' +
        '<td><%=dateFormat(viewTradeInfo[i].base_trade_date,"yyyy-MM")%></td>' +
        '<td><%=viewTradeInfo[i].base_comp_name%></td>' +
        '<td><%=viewTradeInfo[i].base_trade_stagecont%></td>' +
        '<td><%=viewTradeInfo[i].base_trade_inmoney%></td>'+
        '<td><button class="btn btn-default smart" del-trad-code="<%=viewTradeInfo[i].base_trade_code%>" investmentcode="<%=viewTradeInfo[i].base_investment_code%>" companyName="<%=viewTradeInfo[i].base_comp_name%>" tradeDate="<%=viewTradeInfo[i].base_trade_date%>" i="<%=i%>">删除</button></td>'+
        '</tr>'+
        '<%}%>',
    viewTradeInfoNull:'<tr><td colspan=\'5\'>暂无数据</td></tr>',
    noteList:'<% for (var i=0;i<noteList.length;i++ ){ %>' +
        '<tr <%= nodermore(i,noteconfignum)%>>' +
        '<td><%=dateFormat(noteList[i].createtime.time,"yyyy-MM-dd")%></td>' +
        '<td><%=noteList[i].sys_user_name%></td>' +
        '<td><pre><%=noteList[i].base_invesnote_content%></pre></td>' +
        '<td><button class="btn btn-default smart" i="<%=i%>" del-code="<%=noteList[i].base_investornote_code%>">删除</button></td>' +
        '</tr>'+
        '<%}%>',
    orgainlist:'<% for (var i=0;i<list.length;i++ ){%>' +
        '<div data-org-code="<%=list[i].base_investment_code%>" class="item"><%=list[i].base_investment_name %></div>' +
        '<%}%>',
    tablelist:  '<% for (var i=0;i<list.length;i++ ){%>'+
        '<tr>' +
        '<td><%=list[i].base_meeting_time%></td>' +
        '<td><%=substring(list[i].view_meeting_usercodename,"10")%></td>' +
        '<td><%=substring(list[i].base_meeting_content,"10")%></td>'+
        '</tr>'+
        '<%}%>',
    updlogList:'<%for(var i=0;i<list.length;i++){%>'+
        '<tr><td><%=dateFormat(list[i].updtime.time,"yyyy-MM-dd")%></td>'+
        '<td><%=list[i].sys_user_name%></td>'+
        '<td><%=list[i].base_updlog_opercont%></td>'+
        '<td><%=list[i].base_updlog_oridata==""?"":list[i].base_updlog_oridata%>'+
        '<%=list[i].base_updlog_newdata==""?"":list[i].base_updlog_newdata%></td></tr>'+
        '<%}%>'
};
template.helper("nodermore", function (a,b) {
    if(a<b){
        return "";
    }else{
        return "nodemore"
    }
});

//投资机构选择页面
var selectorgain=(function () {
    var opt={orgaincode:""};
    var dataconfig= function () {
        opt.page={
            pageCount:1,
            pageSize:new CommonVariable().PAGESIZE,
            totalCount:2
        };
        $(".inner").html("<div class='lists'><div class='itemnocare' style='text-align: center;color:#AAA'>不限</div></div>");
        $(".itemnocare").unbind().bind("click",function () {
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
                    if(data.list.length==0){
                        $.showtip("无结果数据");
                        setTimeout(function () {
                            $.hidetip();
                        },2000);
                    }
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

var companypage=(function () {

})();
//会议子页
var pageMeetingDataRendering=(function (){
    var data={};
    var tabledata;
    function config_data() {
        data.page={
            pageCount:1
        }
    }
    function showpage(){
        pagetable=$("#meetingPage").showpage();
        config_data();
        select();
    }

    function select() {
        $.showloading();//等待动画
        $.ajax({
            url:"screenmeetinglist.html",
            type:"post",
            async:true,
            data:{investorcode:congfig.data.id,
                pageCount:data.page.pageCount,
                logintype:cookieopt.getlogintype()},
            dataType: "json",
            success: function(json){
                tabledata=json;
                $.hideloading();//隐藏等待动画
                data.page=json.page;
                if(json.message=="success"){
                    var html=template.compile(data_model.tablelist)(json);
                    $("#pageMeetingBody tbody").html(html);
                }else if(json.message=="nomore"){
                    var html='<tr><td  colspan="3" style="width:100%;">暂无数据</td></tr>';
                    $("#pageMeetingBody tbody").html(html);
                    pagetable=$("#meetingPage").showpage();
                }
                $(".meetingLable").html("共"+data.page.totalCount+"条");
                jqPagination();
                companyclick();
            }
        });

    }
    var jqPagination= function () {
        if(Number(data.page.totalPage)>0){
            $('.pagination').jqPagination({
                link_string	: '/?page={page_number}',
                max_page	:data.page.totalPage,
                current_page:data.page.pageCount,
                paged		: function(a) {
                    if(a!=page.pageCount){
                        page.pageCount=a;
                        select();
                    }
                }
            });
            $('.pageaction').show();
        }else{
            $('.pageaction').hide();
        }
    };
    var companyclick= function () {
        $(".meeting-tbody tr").each(function(index,element){
            $(this).bind("click", function () {
                if(tabledata.list[index].visible=="1"){//可见详情
                    window.location.href="meeting_info.html?meetingcode="+tabledata.list[index].base_meeting_code+"&logintype="+cookieopt.getlogintype();
                }else{
                    $.showtip("您没有查看此会议的权限");
                    setTimeout(function () {
                        $.hidetip();
                    },2000);
                }
            })
        });
    };

    return{
        show:showpage
    }
})();

var pageUpdlogDataRendering=(function (){
    var data={};

    function config_data() {
        data.page={
            pageCount:1
        }
    }
    function showpage(){
        pagetable=$("#updlogPage").showpage();
        config_data();
        select();
    }

    function select() {
        $.showloading();//等待动画
        $.ajax({
            url:"findOrgUpdlogInfoByCode.html",
            type:"post",
            async:true,
            data:{type:'Lable-people',
                code:congfig.data.id,
                pageCount:data.page.pageCount,
                logintype:cookieopt.getlogintype()},
            dataType: "json",
            success: function(json){
                $.hideloading();//隐藏等待动画
                data.page=json.page;
                if(json.message=="success"){
                    var html=template.compile(data_model.updlogList)(json);
                    if(html==""){
                        html='<tr><td  colspan="4" style="width:100%;">暂无数据</td></tr>';
                    }
                    $("#pageUpdlogBody tbody").html(html);
                    $(".updlogLable").html("共"+data.page.totalCount+"条");
//                    pagetable=$("#meetingPage").showpage();
                }else if(json.message=="nomore"){
                    var html='<tr><td  colspan="4" style="width:100%;">暂无数据</td></tr>';
                    $("#pageUpdlogBody tbody").html(html);
//                    pagetable=$("#meetingPage").showpage();
                }
                jqPagination();
//                companyclick();
            }
        });

    }
    var jqPagination= function () {
        if(Number(data.page.totalPage)>0){
            $('.pagination').jqPagination({
                link_string	: '/?page={page_number}',
                max_page	:data.page.totalPage,
                current_page:data.page.pageCount,
                paged		: function(a) {
                    if(a!=data.page.pageCount){
                        data.page.pageCount=a;
                        select();
                    }
                }
            });
            $('.pageaction').show();
        }else{
            $('.pageaction').hide();
        }
    };

    return{
        show:showpage
    }
})();

//投资人标签弹出层集合
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
//应用模板 截取字符串
template.helper("substring", function (str,l) {
    if(str.length>l){
        str= str.substring(0,l)+"..."
    }
    return str;
});

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
    for ( var i = 0; i < vObj.length; i++) {
        olddata+=vObj[i].code+",";
    }
    return olddata;
}
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
function choiceContent(){
    var list="[";
    $(".list li[class='active']").each(function(){
        list+="{code:'"+$(this).attr("tip-l-i")+"',name:'"+$(this).text()+"'},";
    });
    list+="]";
    return list;
}
function checkpeoexit(){
    if(congfig.data.id==""){
        $.showtip("此联系人不存在");
        setTimeout("$.hidetip()",2000);
        return false;
    }
    return true;
}