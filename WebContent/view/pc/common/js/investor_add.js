/**
 * Created by shbs-tp001 on 15-9-10.
 */
var optactive=1;
var menuactive=2;
var postdata={};
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
    postdata.investment=[];
    postdata.pattylist=[];//关注
    postdata.indulist=[];//行业

    click_lisetn();
    if(investmentCode!=null&&investmentCode!=""){
        var investment = new Object();
        investment.code=investmentCode;
        investment.name=investmentName;
        investment.posi="";
        investment.state="在职";
        postdata.investment.push(investment);
        addorganization.initinvestment();
    }
});

function click_lisetn(){
    //添加所属机构
   $("#btn_detail_add").click(function(){
       addorganization.init();
   });
//创建投资人点击
        $(".creatInvestor").click(function(){
            if($("[name='username']").val().trim()==""){
                $.showtip("姓名不能为空","error");
            }else{
                type="1";
                creat_ajax(type);
            }
        });

//创建投资人点击
        $(".creatTrad").click(function(){
            if($("[name='username']").val().trim()==""){
                $.showtip("姓名不能为空","error");
            }else{
                type="2";
                creat_ajax(type);
            }
        });

    $(".hangye").click(function () {
        $this=$(this);
        tip_edit.config({
            list:dicListConfig(false,induList, postdata.indulist,[]),
            $this:$(this),
            besure: function () {
                postdata.indulist=[];
                $this.next().html("");
                $(".list li[class='active']").each(function(){
                    var map={};
                    map.code=$(this).attr("tip-l-i");
                    map.name=$(this).html();
                    $this.next().append("<span class='comp1'>"+map.name+"</span>");
                    postdata.indulist.push(map);
                });
                tip_edit.close();
            }
        })
    });
    $(".guanzhu").click(function () {
        $this=$(this);
        tip_edit.config({
            list:dicListConfig(false,induList, postdata.pattylist,[]),
            $this:$this,
            besure: function () {
                postdata.pattylist=[];
                $this.next().html("");
                $(".list li[class='active']").each(function(){
                    var map={};
                    map.code=$(this).attr("tip-l-i");
                    map.name=$(this).html();
                    $this.next().append("<span class='comp1'>"+map.name+"</span>");
                    postdata.pattylist.push(map);
                });
                tip_edit.close();
            }
        })
    })
}

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

        select2();
        click();
    }

    function select2() {
        //选择投资机构
        opt.$orgainselect=$("#select_org").select2({
            placeholder:"请选择机构",//文本框的提示信息
            minimumInputLength:0,   //至少输入n个字符，才去加载数据
            allowClear: false,  //是否允许用户清除文本信息
            ajax:{
                url:"findInvestmentNameListByName.html",
                dataType:"json",
                cache: true,
                type:"post",
                delay: 250,//加载延迟
                data: function (params) {
                    return{
                        name:(params.term||"").replace(/(^\s*)|(\s*$)/g,''),
                        pageSize:CommonVariable().SELSECLIMIT,
                        pageCount:"1",
                        logintype:cookieopt.getlogintype(),
                        type:"1"
                    }
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
    function click() {
        $(".saveorgan").unbind().bind("click", function () {
            if(selectdata().olist.length==0){
                $.showtip("请选择投资机构");
            }else{
                var code=selectdata().olist[0].id;
                for(var i=0;i<  postdata.investment.length;i++){
                    if(code==  postdata.investment[i].code){
                        $.showtip("已选择该投资机构");
                        return;
                    }
                }
                var map={};
                map.code=code;map.posi=$("#posi").val();map.state=$("#state").val();
                map.name=selectdata().olist[0].text;
                postdata.investment.push(map);
                layer.closeAll('page');
                initinvestment();
            }
        });

        $("[edit]").unbind().bind("click", function () {
            var i=$(this).attr("i");
            //页面层
            layer.open({
                title:"添加所属投资机构",
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['420px', '240px'], //宽高
                content: '<div class="addfrom"><ul class="inputlist"><li><span class="lable">投资机构:</span><span class="in"><select id="select_org" class=" inputdef"> <option value="'+postdata.investment[i].code+'" selected="selected">'+postdata.investment[i].name+'</option></select></span></li><li><span class="lable">职位:</span><span class="in"><input value="'+postdata.investment[i].posi+'" id="posi" type="text" class="inputdef" maxlength="20"></span></li><li><span class="lable">状态:</span><span class="in"><select class="inputdef" id="state"> <option id="stateon" value="在职">在职</option> <option id="stateoff" value="已离职">已离职</option></select></span></li></ul><div class="btn-box"><button class="btn btn-default editorgan">保存</button></div></div>'
            });
            $("#state").val(postdata.investment[i].state);
            select2();
            $(".editorgan").unbind().bind("click", function () {
                if(selectdata().olist.length==0){

                }else{
                    var code=selectdata().olist[0].id;
                    postdata.investment[i].code=code;
                    postdata.investment[i].name=selectdata().olist[0].text
                }
                postdata.investment[i].posi=$("#posi").val();
                postdata.investment[i].state=$("#state").val();
                layer.closeAll('page');
                initinvestment();
            });
        });
        $("[remove]").unbind().bind("click", function () {
            var i=$(this).attr("i");
            postdata.investment.baoremove(Number(i));
            initinvestment();
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

    function initinvestment() {
        var html=template.compile(data_model.orlist)(postdata);
        $(".orul").html(html);
        click();
    }
    function formatRepo (repo) {
        return repo.text;
    }
    return{
        init:init,
        initinvestment:initinvestment
    }
})();
//页面调用的ajax
var creat_ajax= function (type) {
    if($("[name='phone']").val()!=""){
        var check_phone=/^[1][3,7,4,5,8][0-9]{9}$/;//手机格式验证
        if(!check_phone.test($("[name='phone']").val())){
            $.showtip("请输入有效的手机号码");
            return false;
        }
    }
    if($("[name='email']").val()!=""){
        var check_email=/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;//邮箱验证
        if(!check_email.test($("[name='email']").val())){
            $.showtip("请输入有效的邮箱");
            return false;
        }
    }
    $.showloading();
    $(".creatInvestor,.creatTrad").attr("disabled","true");
    var note=$(".compnote").val();
    if(note.trim()==""){
        note=""
    }
    if(!postdata.investment.length&&type==2){
        layer.confirm('请添加所属机构，否则无法创建交易信息', {
            title:"提示",
            icon: 0,
            btn: ['确定'] //按钮
        }, function(index){
            layer.close(index);
        });
        $.hideloading();
        $(".creatInvestor,.creatTrad").removeAttr("disabled");
       return ;
    }
    $.ajax({
        url:"addInvestorInfo.html",
        type:"post",
        async:false,
        data:{
            name:$("[name='username']").val().trim(),
            investment:JSON.stringify(postdata.investment),
            phone:$("[name='phone']").val().trim(),
            email:$("[name='email']").val().trim(),
            wechat:$("[name='weichat']").val(),
            pattylist:JSON.stringify(postdata.pattylist),
            indulist:JSON.stringify(postdata.indulist),
            base_invesnote_content:note,
            logintype:cookieopt.getlogintype()
        },
        dataType: "json",
        success: function(data){
            $.hideloading();
            if(data.message=="success"){
                $.showtip("保存成功","success");
                setTimeout(function () {
                    if(type==1){
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
                $.showtip("保存失败","error");
            }

        },
        error:function(){
            $(".creatInvestor").removeAttr("disabled");
        }
    });
};
var data_model={
    orlist:'<% for (var i=0;i<investment.length;i++ ){ %>' +
        '<li><span class="comp"><%=investment[i].name %></span>' +
        /*'<span class="comp" id="posi"><%=investment[i].posi %></span>' +*/
        '<span class="comp"><%if(investment[i].posi==""){%>职位(未填写)<%}else{ %><%=investment[i].posi%><%}%></span>' +
        '<span class="comp"><%=investment[i].state %></span>' +
        '<span class="comp orremove" edit i="<%=i%>">修改</span>' +
        '<span class="comp orremove" remove i="<%=i%>">删除</span>' +
        '</li>' +
        '<%}%>'
};
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

function addtrade_config(investorcode) {
    if(postdata.investment.length>1){
        var potion='<select class="selscttrade_org">';
        for(var i =0;i< postdata.investment.length;i++){
            potion=potion+"<option value='"+postdata.investment[i].code+"'>"+postdata.investment[i].name+"</option>";
        }
        potion=potion+"</select>";
        layer.confirm(potion,{
            title:"请选择添加交易的投资机构",
            btn: ['确定',"取消"] //按钮
        }, function(index){
            investmentcode=$(".selscttrade_org").val();
            layer.close(index);
            if(investmentcode){
                window.location.href="addTradeInfoinit.html?logintype="+cookieopt.getlogintype()+"&investmentcode="+investmentcode+"&investorcode="+investorcode;
            }
        }, function (index) {
            layer.close(index);
        });
    }else{
        window.location.href="addTradeInfoinit.html?logintype="+cookieopt.getlogintype()+"&investmentcode="+postdata.investment[0].code+"&investorcode="+investorcode;
    }
}