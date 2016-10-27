/**
 * Created by shbs-tp004 on 15-9-9.
 */
var optactive=0;
var menuactive=0;
$(function () {

    congfig.init();
});


var congfig= (function () {
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
        phone:$(".phone").val(),
        email:$(".email").val(),
        wchat:$(".wchat").val(),
        id:$(".investor").attr("id"),
        induList:eval(induList),
        unDelLabelList:eval(unDelLabelList),
        type:"",
        version:investorbInfo.base_datalock_viewtype,
        zhangkai:false,
        tradeshowmore:false
    };
    function init() {
        //联系方式默认显示
        if($("#phone").html()==""){
            $("#phone").html("手机(未填写)");
        }
        if($("#email").html()==""){
            $("#email").html("邮箱(未填写)");
        }
        if($("#wchat").html()==""){
            $("#wchat").html("微信(未填写)");
        }
        var inducont=template.compile(data_model.inducont)(data);//行业html
        $(".induc").html(inducont);
        var payattcont=template.compile(data_model.payattcont)(data);//近期关注html
        $(".patty").html(payattcont);
        var cont=template.compile(data_model.cont)(data);//所属机构
        $(".peopleContent").html(cont);
        if($(".contposi").html()==""){
            $(".contposi").html("职位(未填写)");
        }
        notepost.confignotehtml();

        var viewTradeInfo=template.compile(data_model.viewTradeInfo)(data);//参与交易
        if(data.viewTradeInfo.length==0){
            viewTradeInfo=data_model.viewTradeInfoNull;
        }
        $(".stage").html(viewTradeInfo);
        click();
        if(backherf!=""){
            $(".goback").show();
           $(".goback").click(function () {
               window.location.href=backherf+"&logintype="+cookieopt.getlogintype();
           });
        }
    }

    function click() {
        alone_edit.config_function();
        trade_more.click();

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
        //保存姓名按钮
        $(".saveinvestorname").unbind().bind("click",function () {
            editinvsetor.editname();
        });
        //保存手机号
        $(".savephone").unbind().bind("click",function () {
            editinvsetor.editphone();
        });
        //保存邮箱
        $(".saveemail").unbind().bind("click",function () {
            editinvsetor.editemail();
        });
        //保存微信
        $(".savewchat").unbind().bind("click",function () {
            editinvsetor.editwchat();
        });
        //添加投资机构
        $("#btn_detail_add").unbind().bind("click",function () {
            addorganization.init();
        });
        //添加备注
        $(".savenote").unbind().bind("click",function () {
            notepost.add();
        });

        $(".peopleContent li ul").each(function (index) {
            //保存职位
            $(this).find(".savecontposi").unbind().bind("click", function () {
               var text=$(this).prev().val();
                if(congfig.data.cont[index].posi!=""&&congfig.data.cont[index].posi==text){
                    $.showtip("职位未发生改变");
                }else{
                    editinvsetor.change_orgin({
                        index:index,
                        investmentName:congfig.data.cont[index].name,
                        posi:text,
                        state:congfig.data.cont[index].state
                    });
                    alone_edit.close($(this).find(".canEdit"));
                }
            });
            //保存在职状态
            $(this).find(".savecontstate").unbind().bind("click", function () {
                var text=$(this).prev().val();
                if(congfig.data.cont[index].state!=""&&congfig.data.cont[index].state==text){
                    $.showtip("在职状态未发生改变");
                }else{
                    editinvsetor.change_orgin({
                        index:index,
                        investmentName:congfig.data.cont[index].name,
                        posi:congfig.data.cont[index].posi,
                        state:text
                    });
                    alone_edit.close($(this).find(".canEdit"));
                }
            });

        });
        //行业
        $(".hangyeedit").unbind().bind("click",function () {
            tip_edit.config({
                list:dicListConfig(true,congfig.data.induList,congfig.data.inducont,unDelLabelList),
                $this:$(this),
                besure: function () {

                    editinvsetor.chang_indu();
                }
            });
        });
        //近期关注
        $(".lastcare").unbind().bind("click",function () {
            tip_edit.config({
                list:dicListConfig(true,congfig.data.induList,congfig.data.payattcont,null),
                $this:$(this),
                besure: function () {
                    editinvsetor.chang_lastcare();
                }
            });
        });
        //参与交易更多
        $(".lockmore").unbind().bind("click",function () {
            layer.open({
                type: 1,
                title: '参与交易',
                shadeClose: false,
                shade: 0.6,
                skin : 'layui-layer-rim', //加上边框
                scrollbar: false,
                maxmin: false, //开启最大化最小化按钮
                area: ['700px', '350px'],
                content: '<div class=" tran_content_table tran_content tradebox" ><table><thead><tr class="nohover"><th width="20%">时间</th><th width="30%">被投公司</th><th width="20%">阶段</th><th width="20%">金额</th><th width="10%">操作</th></tr></thead><tbody class="stagemore"></tbody></table><div class="customBootstrap"><ul class="totalSize" id="totalsize"　></ul><ul class="pagination" id="pagination1"></ul></div></div>',
                cancel: function(index){
                congfig.data.tradeshowmore=false;
                }
            });
            trade_more.init();
        });
       //会议记录
        $('.meet_record').unbind().bind("click",function(){
            layer.open({
                type: 1,
                title: '会议记录',
                shadeClose: false,
                shade: 0.6,
                skin : 'layui-layer-rim', //加上边框
                scrollbar: false,
                maxmin: false, //开启最大化最小化按钮
                area: ['700px', '350px'],
                content: '<div class=" tran_content_table tran_content tradebox" ><table><thead><tr class="nohover"><th width="20%">时间</th><th width="30%">参会人</th><th width="50%">会议内容</th></tr></thead><tbody class="meetingtable"></tbody></table><div class="customBootstrap"><ul class="totalSize" id="totalsize"　></ul><ul class="pagination" id="pagination1"></ul></div></div>'
            });
            meeting.init();
        });
        //更新记录
     $('.update_record').unbind().bind("click",function(){
                layer.open({
                    type: 1,
                    title: '更新记录',
                    shadeClose: false,
                    shade: 0.6,
                    skin : 'layui-layer-rim', //加上边框
                    scrollbar: false,
                    maxmin: false, //开启最大化最小化按钮
                    area: ['700px', '350px'],
                    content: '<div class=" tran_content_table tran_content tradebox" ><table><thead><tr class="nohover"><th width="20%">时间</th><th width="20%">更新人</th><th width="20%">操作</th><th width="40%">内容</th></tr></thead><tbody class="updatetable"></tbody></table><div class="customBootstrap"><ul class="totalSize" id="totalsize"　></ul><ul class="pagination" id="pagination1"></ul></div></div>'
                });
            updaterecord.init();
            });

    }
    return{
        init:init,
        data:data,
        click:click
    };
})();
//修改投资人信息
var editinvsetor=(function () {
    function editname() {
        var investorName=$(".investorname-input").val().trim();
        if(investorName==congfig.data.name){
            $.showtip("名称未改变");
        }else if(investorName==""){
            $.showtip("名称不能为空");
        }else{
            alone_edit.close($(".investor"));
            congfig.data.investorbInfo.base_investor_name=investorName;
            congfig.data.type="name";
            doajax();
        }
    }

    function editphone() {
        var phone = $(".phone").val().trim();
        if(phone==congfig.data.phone) {
            $.showtip("手机号码未改变");
        }else{
            if(check(phone,"")){
                alone_edit.close($("#phone"));
                congfig.data.investorbInfo.base_investor_phone=phone;
                congfig.data.type="phone";
                doajax();
            }
        }
    }
    function editemail() {
        var email = $(".email").val().trim();
        if(email==congfig.data.email) {
            $.showtip("邮箱未改变");
        }else{
            if(check("",email)){
                alone_edit.close($("#email"));
                congfig.data.investorbInfo.base_investor_email=email;
                congfig.data.type="phone";
                doajax();
            }
        }
    }
    function editwchat() {
        var wchat = $(".wchat").val().trim();
        if(wchat==congfig.data.wchat) {
            $.showtip("微信未改变");
        }else{
                alone_edit.close($("#wchat"));
                congfig.data.investorbInfo.base_investor_wechat=wchat;
                congfig.data.type="phone";
                doajax();
        }
    }
    //修改投资人基本信息
    function doajax() {
        $.showloading();
        $.ajax({
            url:"editInvestorInfo.html",
            type:"post",
            async:false,
            data:{
                info:JSON.stringify(congfig.data.investorbInfo),
                type:congfig.data.type,
                logintype:cookieopt.getlogintype()
            },
            dataType: "json",
            success: function(data){

                if(data.message=="success"){
                    $.showtip("保存成功","success");
                    congfig.data.investorbInfo=data.baseInfo;
                    congfig.data.version=data.baseInfo.base_datalock_viewtype;
                    reloadinvestorbInfo();
                }else if(data.message=="已被修改,请刷新页面再修改"){
                    $.showtip("数据已被修改，请刷新页面再修改","error");
                }else{
                    $.showtip("修改失败","error");
                }
                $.hideloading();
            }
        });
    }
//修改所属机构
    function change_orgin(opt) {
        $.showloading();
        $.ajax({
            url:"editInvestor.html",
            type:"post",
            async:false,

            data:{
                code:congfig.data.code,
                investmentcode:congfig.data.cont[opt.index].code,
                investmentName:opt.investmentName,
                oldstr:"投资机构名称:"+congfig.data.cont[opt.index].name+";职位:"+congfig.data.cont[opt.index].posi+";状态:"+congfig.data.cont[opt.index].state,
                state:opt.state,
                posi:opt.posi,
                name:congfig.data.name,
                version:congfig.data.version,
                logintype:cookieopt.getlogintype()
            },
            dataType: "json",
            success: function(data){
                $.hideloading();
                if(data.message=="success"){
                    $.showtip("保存成功","success");
                    setTimeout("$.hidetip()",2000);
                    congfig.data.cont=eval(data.investorInfo.view_investment_cont);
                    congfig.data.version=data.version;
                    congfig.data.investorbInfo.base_datalock_viewtype=data.version;
                    var cont=template.compile(data_model.cont)(congfig.data);
                    $(".peopleContent").html(cont);
                    congfig.click();
                }else if(data.message=="已被修改,请刷新页面再修改"){
                    $.showtip("数据已被修改，请刷新页面再修改","error");
                }else{
                    $.showtip("更新失败","error");
                }


            }
        });
    }
//修改行业
    function chang_indu() {

        if(oldContent(congfig.data.inducont)==choiceContent()){
            $.showtip("行业未改变");
            return;
        }
        $.showloading();//等待动画
        $.ajax({
            url:"updateInvsetorInfo.html",
            type:"post",
            async:false,
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
                    $.showtip("保存成功!","success");
                    congfig.data.inducont=(data.list[0]==null?[]:eval(data.list));
                    congfig.data.version=data.version;
                    congfig.data.investorbInfo.base_datalock_viewtype=data.version;
                    var html=template.compile(data_model.inducont)(congfig.data);
                    $(".induc").html(html);
                }else{
                    $.showtip(data.message,"error");
                }
            }
        });
        tip_edit.close();
    }

    function chang_lastcare(){
        if(oldContent(congfig.data.payattcont)==choiceContent()){
            $.showtip("近期关注未改变");
            return;
        }
        $.showloading();//等待动画
        $.ajax({
            url:"updateInvsetorInfo.html",
            type:"post",
            async:false,
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
                    $.showtip("保存成功!","success");
                    congfig.data.payattcont=(data.list[0]==null?[]:eval(data.list));
                    congfig.data.version=data.version;
                    congfig.data.investorbInfo.base_datalock_viewtype=data.version;
                    var html=template.compile(data_model.payattcont)(congfig.data);
                    $(".patty").html(html);
                }else{
                    $.showtip(data.message,"error");
                }
            }
        });
        tip_edit.close();
    }

    function check(phone,email){
        var check_phone=/^[1][3,7,4,5,8][0-9]{9}$/;//手机格式验证
        var check_email=/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;//邮箱验证

        if(phone!=""){
            if(!check_phone.test(phone)){
                $.showtip("请输入有效的手机号码");
                return false;
            }
        }
        if(email!=""){
            if(!check_email.test(email)){
                $.showtip("请输入有效的邮箱");
                return false;
            }
        }
        return true;
    }
    function reloadinvestorbInfo() {
        congfig.data.name=congfig.data.investorbInfo.base_investor_name;
        congfig.data.phone=congfig.data.investorbInfo.base_investor_phone;
        congfig.data.email=congfig.data.investorbInfo.base_investor_email;
        congfig.data.wchat=congfig.data.investorbInfo.base_investor_wechat;
        $(".investor").html(congfig.data.name);
        $("#phone").html(congfig.data.phone);
        $("#email").html(congfig.data.email);
        $("#wchat").html(congfig.data.wchat);
        if($("#phone").html()==""){
            $("#phone").html("手机(未填写)");
        }
        if($("#email").html()==""){
            $("#email").html("邮箱(未填写)");
        }
        if($("#wchat").html()==""){
            $("#wchat").html("微信(未填写)");
        }
    }
    return{
        editname:editname,
        editphone:editphone,
        editemail:editemail,
        editwchat:editwchat,
        change_orgin:change_orgin,
        chang_indu:chang_indu,
        chang_lastcare:chang_lastcare
    };
})();
//添加投资机构
var addorganization=(function () {
    var opt={};
    function init() {
        //页面层
       layer.open({
            title:"添加所属投资机构",
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['420px', '240px'], //宽高
            content: '<div class="addfrom"><ul class="inputlist"><li><span class="lable">投资机构:</span><span class="in"><select id="select_org" class=" inputdef"><option></option></select></span></li><li><span class="lable">职位:</span><span class="in"><input value="" id="posi" type="text" class="inputdef" maxlength="20"></span></li><li><span class="lable">状态:</span><span class="in"><select class="inputdef" id="state"> <option id="stateon" value="在职">在职</option> <option id="stateoff" value="已离职">已离职</option></select></span></li></ul><div class="btn-box"><button class="btn btn-default saveorgan">保存</button></div></div>'
        });
        //选择投资机构
        opt.$orgainselect=$("#select_org").select2({
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

        click();
    }

    function click() {
        $(".saveorgan").click(function () {
            if(selectdata().olist.length==0){
                $.showtip("请选择投资机构");
            }else{
                var code=selectdata().olist[0].id;
                for(var i=0;i<congfig.data.cont.length;i++){
                    if(code==congfig.data.cont[i].code){
                        $.showtip("已属于该投资机构");

                        return;
                    }
                }
                addinvest_ajax();
                layer.closeAll('page');
            }
        });
    }
    function addinvest_ajax(){
        $.showloading();
        $.ajax({
            url:"addInvestor.html",
            type:"post",
            async:false,
            data:{
                code:congfig.data.code,
                investmentcode:selectdata().olist[0].id,
                investmentname:selectdata().olist[0].text,
                state:$("#state").val(),
                posi:$("#posi").val(),
                oldstr:"",
                name:congfig.data.name,
                version:congfig.data.version,
                logintype:cookieopt.getlogintype()
            },
            dataType: "json",
            success: function(data){
                $.hideloading();
                if(data.message=="success"){
                    $.showtip("保存成功","success");
                    setTimeout("$.hidetip()",2000);
                    congfig.data.cont=eval(data.investorInfo.view_investment_cont);
                    congfig.data.version=data.version;
                    congfig.data.investorbInfo.base_datalock_viewtype=data.version;
                    var cont=template.compile(data_model.cont)(congfig.data);
                    $(".peopleContent").html(cont);
                   congfig.click();
                }else if(data.message=="已被修改,请刷新页面再修改"){
                    $.showtip("数据已被修改，请刷新页面再修改","error");
                }else{
                    $.showtip("保存失败","error");
                }

            }
        });
    }
    function selectdata() {
        var map={};
        var o=opt.$orgainselect.select2("data");
        var olist=[];
        for(var i=0;i< o.length;i++){
            if(!o[i].selected){
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
//参与交易更多
var trade_more=(function () {
    var page={};
    page.pageCount=1;
    var tablelist="";
    function init(){
        page={};
        page.pageCount=1;
        congfig.data.tradeshowmore=true;
        tablelist="";
        select();
    }
    function select() {
        $.ajax({
            url:"findNoteByInvestorCode.html",
            type:"post",
            async:false,
            data:{
                code:congfig.data.id,
                pageCount:page.pageCount,
                logintype:cookieopt.getlogintype()},
            dataType: "json",
            success: function(data){
                $.hideloading();
                page=data.page;
                tablelist=data.viewTradeInfo;
                var html=template.compile(data_model.viewTradeInfo)(data);
                if(html==""){
                    $(".stagemore").html(data_model.viewTradeInfoNull);
                    $("#pagination1").hide();
                }else{
                    $(".stagemore").html(html);
                    $("#pagination1").show();
                }
                if(data.page.totalCount>0){
                	$(".totalSize").show();
                	$(".totalSize").html("<li>共"+data.page.totalCount+"条</li>");
                }else{
                	$(".totalSize").hide();
                }
                if(page.pageCount==1){
                    $(".stage").html(html);
                }
                jqPagination ();
                click();
            }
        });
    }
    function jqPagination () {

        $.jqPaginator('#pagination1', {
            totalPages: page.totalPage,
            visiblePages: 5,
            currentPage:page.pageCount,
            onPageChange: function (num, type) {
                if(page.pageCount!=num){
                    page.pageCount=num;
                    select();
                }

            }
        });


    }

    function click() {
        $(".stage tr,.stagemore tr").unbind().bind("click",function () {
            var code=$(this).attr("code");
            if(code!=null&&code!=""){
                window.location.href="findTradeDetialInfo.html?tradeCode="+code+"&logintype="+cookieopt.getlogintype()+"&backherf=findInvestorDeatilByCode.html?code="+congfig.data.id;
            }
        });
        $("[del-trad-code]").unbind().bind("click",function (e) {
            $this=$(this);
            layer.confirm('您确定要删除该条记录吗？', {
                title:"提示",
                icon: 0,
                btn: ['确定','取消'] //按钮
            }, function(index){
                delinvestor_trade($this);
                layer.close(index);
            }, function(index){
                layer.close(index);
            });
            e.stopPropagation();
        });
        $("#btn_trade_add").unbind().bind("click", function () {
            var investmentcode;
            if(!congfig.data.cont.length){
                layer.confirm('请添加所属机构，再添加交易信息。', {
                    title:"提示",
                    icon: 0,
                    btn: ['确定'] //按钮
                }, function(index){
                    layer.close(index);
                });
            }else if(congfig.data.cont.length>1){
               var potion='<select class="selscttrade_org">';
                for(var i =0;i< congfig.data.cont.length;i++){
                    potion=potion+"<option value='"+congfig.data.cont[i].code+"'>"+congfig.data.cont[i].name+"</option>";
                }
                potion=potion+"</select>";
                layer.confirm(potion,{
                    title:"请选择添加交易的投资机构",
                    btn: ['确定',"取消"] //按钮
                }, function(index){
                    investmentcode=$(".selscttrade_org").val();
                    layer.close(index);
                    if(investmentcode){
                        window.location.href="addTradeInfoinit.html?logintype="+cookieopt.getlogintype()+
                            "&backherf=findInvestorDeatilByCode.html?code="+congfig.data.id+"&investmentcode="+investmentcode+"&investorcode="+congfig.data.id;
                    }
                }, function (index) {
                    layer.close(index);
                });
            }else if(congfig.data.cont.length==1){
                investmentcode=congfig.data.cont[0].code;
            }
            if(investmentcode){
                window.location.href="addTradeInfoinit.html?logintype="+cookieopt.getlogintype()+
                    "&backherf=findInvestorDeatilByCode.html?code="+congfig.data.id+"&investmentcode="+investmentcode+"&investorcode="+congfig.data.id;
            }

        });

    }
    function delinvestor_trade($this) {
        var tradecode=$this.attr("del-trad-code");
        var investmentcode=$this.attr("investmentcode");
        var companyName=$this.attr("companyName");
        var tradeDate=$this.attr("tradeDate");
        var index=$this.attr("i");
        var investmentName;
        for(var i=0;i< congfig.data.cont.length;i++){
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
                investorcode:congfig.data.id
            },
            success: function (data) {
                if(data.message=="success"){
                    $.showtip("删除成功","success");
                    if(page.pageCount==1){
                        $.ajax({
                            url:"findNoteByInvestorCode.html",
                            type:"post",
                            async:true,
                            data:{
                                code:congfig.data.id,
                                pageCount:1,
                                logintype:cookieopt.getlogintype()},
                            dataType: "json",
                            success: function(data){
                                $.hideloading();
                                congfig.data.viewTradeInfo=data.viewTradeInfo;
                                var viewTradeInfo=template.compile(data_model.viewTradeInfo)(congfig.data);
                                if(viewTradeInfo==""){
                                    $(".stage").html(data_model.viewTradeInfoNull);
                                }else{
                                    $(".stage").html(viewTradeInfo);
                                }
                                click();
                            }
                        });
                    }
                    if(congfig.data.tradeshowmore){           
                        select();
                    }
                }else if(data.message=="notrade"){
                    $.showtip("该条交易已被删除","error");
                }else{
                    $.showtip("数据请求错误","error");
                }
                $.hideloading();
            }
        });
    }
    return{
        init:init,
        click:click
    };
})();
//备注信息操作
var notepost=(function () {
    function addnote() {
        var note=$("#note").val();
        if(note.trim()!=""){
            $.showloading();
            $.ajax({
                url:"addInvestorNote.html",
                type:"post",
                async:true,
                data:{
                    base_investor_code: congfig.data.id,
                    base_invesnote_content:note,
                    logintype:cookieopt.getlogintype()},
                dataType: "json",
                success: function(data){
                    $.hideloading();
                    $("#note").val("");
                    $.showtip("保存成功","success");
                    if(data.noteList.length>0){
                        congfig.data.noteList=data.noteList;
                        confignotehtml();
                    }
                }
            });
        }else{
            $.showtip("备注信息不能为空");
        }
    }
    function removenote(notecode) {
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
                    $.showtip("删除成功","success");
                    confignotehtml(notecode);
                }else if(data.message=="failure"){
                    $.showtip("数据已被删除","error");
                    confignotehtml(notecode);
                }else{
                    $.showtip("请求失败","error");

                }
                $.hideloading();
            }
        });
    }

    function confignotehtml(notecode) {
        if(notecode){
            congfig.data.noteList.baoremove(Number($("[del-code='"+notecode+"']").attr("i")));
        }

        var noteList=template.compile(data_model.noteList)(congfig.data);//备注信息
        if(congfig.data.noteList.length==0){
            noteList=data_model.viewTradeInfoNull;
        }
        $(".notetable").html(noteList);
        if(congfig.data.noteconfignum>= congfig.data.noteList.length){
            $(".closeshearch").html('更多<span class="glyphicon glyphicon-chevron-down"></span>').removeClass("active").hide();
            congfig.data.zhangkai=false;
        }else{
            if(!$(".closeshearch").hasClass("active")){
                $(".closeshearch").html('更多<span class="glyphicon glyphicon-chevron-down"></span>').show();
            }
        }
        if(!congfig.data.zhangkai){
            $("[nodemore]").hide();
        }

        click();
    }

    function click() {
        $("[del-code]").unbind().bind("click", function () {
            $this=$(this);
            layer.confirm('您确定要删除该条记录吗？', {
                title:"提示",
                icon: 0,
                btn: ['确定','取消'] //按钮
            }, function(index){
                removenote($this.attr("del-code"));
                layer.close(index);
            }, function(index){
                layer.close(index);
            });
        });
    }
    return{
        confignotehtml:confignotehtml,
        add:addnote
    };
})();
//会议记录 
var meeting=(function () {
    var opt={};
    var tabledata;
    function init() {
        opt.page={
            pageCount:1
        };
        select();
    }
    function select() {
        $.showloading();//等待动画
        $.ajax({
            url:"screenmeetinglist.html",
            type:"post",
            async:false,
            data:{investorcode:congfig.data.id,
                pageCount:opt.page.pageCount,
                logintype:cookieopt.getlogintype()},
            dataType: "json",
            success: function(data){
                tabledata=data;
                $.hideloading();//隐藏等待动画
                opt.page=data.page;
                if(data.message=="success"){
                    var html=template.compile(data_model.tablelist)(data);
                    $(".meetingtable").html(html);
                    if(data.page.totalCount>0){
                    	$(".totalSize").show();
                    	$(".totalSize").html("<li>共"+data.page.totalCount+"条</li>");
                    }else{
                    	$(".totalSize").hide();
                    }
                }else if(data.message=="nomore"){
                    var html='<tr class="nohover"><td  colspan="3" style="width:100%;">暂无数据</td></tr>';
                    $(".meetingtable").html(html);
                }
                jqPagination();
                click();
            }
        });

    }
    function jqPagination () {
        $.jqPaginator('#pagination1', {
            totalPages: opt.page.totalPage,
            visiblePages: 5,
            currentPage:opt.page.pageCount,
            onPageChange: function (num, type) {
                if(opt.page.pageCount!=num){
                    opt.page.pageCount=num;
                    select();
                }

            }
        });
    }

    function click() {
        $(".meetingtable tr").each(function(index,element){
            $(this).bind("click", function () {
                if(tabledata.list[index].visible=="1"){//可见详情
                    window.location.href="meeting_info.html?meetingcode="+tabledata.list[index].base_meeting_code+"&logintype="+cookieopt.getlogintype();
                }else{
                    $.showtip("您没有查看此会议的权限");
                    setTimeout(function () {
                        $.hidetip();
                    },2000);
                }
            });
        });
    }
    return{
        init:init
    };
})();
//更新记录
var updaterecord=(function () {
    var data={};
    function init() {
        data.page={
            pageCount:1
        };
        select();
    }
    function select() {
        $.showloading();//等待动画
        $.ajax({
            url:"findOrgUpdlogInfoByCode.html",
            type:"post",
            async:false,
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
                    	html='<tr class="nohover"><td  colspan="4" style="width:100%;">暂无数据</td></tr>';
                    }
                    $(".updatetable").html(html);
                    if(data.page.totalCount>0){
                    	$(".totalSize").show();
                    	$(".totalSize").html("<li>共"+data.page.totalCount+"条</li>");
                    }else{
                    	$(".totalSize").hide();
                    }
                }else if(json.message=="nomore"){
                    var html='<tr class="nohover"><td  colspan="4" style="width:100%;">暂无数据</td></tr>';
                    $(".updatetable").html(html);

                }
                jqPagination();

            }
        });

    }
    function jqPagination () {
        $.jqPaginator('#pagination1', {
            totalPages: data.page.totalPage,
            visiblePages: 5,
            currentPage:data.page.pageCount,
            onPageChange: function (num, type) {
                if(data.page.pageCount!=num){
                    data.page.pageCount=num;
                    select();
                }

            }
        });
    }
    return{
        init:init
    };
})();
var data_model={
    inducont:'<% for (var i=0;i<inducont.length;i++ ){%>' +
        '<li><%=inducont[i].name%></li>' +
        '<%}%>',
    payattcont:'<% for (var i=0;i<payattcont.length;i++ ){%>' +
        '  <li><%=payattcont[i].name%></li>' +
        '<%}%>',
    cont:'<%for (var i=0;i<cont.length;i++ ){%>' +
        '  <li><ul>' +
        '<li>' +
        '<span class="color-def contname"><%=cont[i].name%></span>' +
        '</li>' +
        '<li>' +
        '<% cont[i].posivalue=cont[i].posi;if(cont[i].posi==""){cont[i].posi="职位(未填写)"} %>' +
        '<span class="canEdit contposi"><%=cont[i].posi%></span>' +
        '<div class="txtEdit lable_hidden"><input class="txtInfo" value="<%=cont[i].posivalue%>" maxlength="20" /><button class="txtSave savecontposi">保存</button></div>' +
        '</li>' +
        '<li>' +
        '<%if(cont[i].state=="已离职"){cont[i].selected="selected"}%>' +
        '<span class="canEdit contstate ">(<%=cont[i].state%>)</span>' +
        '<div class="txtEdit lable_hidden"><select class="select"><option id="stateon" value="在职">在职</option><option id="stateoff" value="已离职" <%=cont[i].selected%>>已离职</option></select><button class="txtSave savecontstate">保存</button></div>' +
        '<% cont[i].posi =cont[i].posivalue %>' +
        '</li>' +
        '</ul></li>' +
        '<%}%>',
    noteList:'<% for (var i=0;i<noteList.length;i++ ){ %>' +
        '<tr <%= nodermore(i,noteconfignum)%>>' +
        '<td><%=dateFormat(noteList[i].createtime.time,"yyyy-MM-dd")%></td>' +
        '<td><%=noteList[i].sys_user_name%></td>' +
        '<td><pre><%=noteList[i].base_invesnote_content%></pre></td>' +
        '<td><button i="<%=i%>" del-code="<%=noteList[i].base_investornote_code%>" class="btn btn_delete"></button></td>' +
        '</tr>'+
        '<%}%>',
    viewTradeInfo:'<% for (var i=0;i<viewTradeInfo.length;i++ ){ %>' +
        '<tr code=<%=viewTradeInfo[i].base_trade_code%> >' +
        '<td><%=dateFormat(viewTradeInfo[i].base_trade_date,"yyyy-MM")%></td>' +
        '<td><%=viewTradeInfo[i].base_comp_name%></td>' +
        '<td><%=viewTradeInfo[i].base_trade_stagecont%></td>' +
        '<td><%=viewTradeInfo[i].base_trade_inmoney%></td>'+
        '<td><button class="btn btn_delete" del-trad-code="<%=viewTradeInfo[i].base_trade_code%>" investmentcode="<%=viewTradeInfo[i].base_investment_code%>" companyName="<%=viewTradeInfo[i].base_comp_name%>" tradeDate="<%=viewTradeInfo[i].base_trade_date%>" i="<%=i%>"></button></td>'+
        '</tr>'+
        '<%}%>',
    viewTradeInfoNull:'<tr class="nohover"><td colspan=\'5\'>暂无数据</td></tr>',
    tablelist:  '<% for (var i=0;i<list.length;i++ ){%>'+
        '<tr>' +
        '<td><%=list[i].base_meeting_time%></td>' +
        '<td><%=substring(list[i].view_meeting_usercodename,"10")%></td>' +
        '<td><%=substring(list[i].base_meeting_content,"10")%></td>'+
        '</tr>'+
        '<%}%>',
    updlogList:'<%for(var i=0;i<list.length;i++){%>'+
        '<tr class="nohover"><td><%=dateFormat(list[i].updtime.time,"yyyy-MM-dd")%></td>'+
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
        return "nodemore";
    }
});
//应用模板 截取字符串
template.helper("substring", function (str,long) {
    str= str.replace("<br/>","");
    if(str.length>long){
        str= str.substring(0,long)+"...";
    }
    return str;
});
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
function oldContent(vObj){

    return JSON.stringify(vObj);
}
function choiceContent(){
    var list=[];
        $(".list li[class='active']").each(function(){
            var map={};
            map.code=$(this).attr("tip-l-i");
            map.name=$(this).html();
            list.push(map);
    });
    return JSON.stringify(list);
}
