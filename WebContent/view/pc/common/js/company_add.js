/**
 * Created by shbs-tp001 on 15-9-10.
 */
var optactive=1;
var menuactive=4;
var postdata={};
var type;
var listselectcode=null;
var inductlist=[];
var stagelist=[];
var submitflg=0;//添加联系人点击flg
var submitrz=0;//控制融资计划多次提交
$(function () {
    postdata.basket=[];//选中的筐
    postdata.induct=[];//选中的行业
    postdata.stage=null;//选中的阶段
    postdata.peoplelit=[];//联系人
    //添加投资机构
    $("#btn_people_add").unbind().bind("click", function() {
        addenupeople.init();
    });
    $("#name").blur(function () {
        if($(this).val().trim()!=""){
            checkcombyname();
        }
    });


    function checkcombyname() {
        $.ajax({
            url:"checkcombyname.html",
            type:"post",
            async:true,
            data:{
                name:$("#name").val().trim(),
                logintype:cookieopt.getlogintype()
            },
            dataType: "json",
            success: function(data){
                if(data.message=="companyexist"){
                    $.showtip("公司简称已存在");
                }else if(data.message=="error"){
                    $.showtip("请求失败");
                }
            }
        });
    }
});

//筐弹层
var bask = (function() {
    $(".baskedit").unbind().bind("click", function() {
    	$this=$(this);
        tip_edit.config({
        	list : dicListConfig(true, baskList, postdata.basket),
            $this : $(this),
            radio : false,
            besure : function() {
                var h="";
                var type="";
                postdata.basket=[];
                $this.next().html("");
                $(".list li").each(function(index,element){
                    if($(this).hasClass("active")){
                        type="1";
                        var map={};
                        map.code=$(this).attr("tip-l-i");
                        map.name=$(this).html();

                        h=h+"<span class='comp1'>"+$(this).html()+"</span>"
                        postdata.basket.push(map);
                    }
                });
                if(type!=1){
                    listselectcode=null;
                }
                $(".bask").html(h);
               tip_edit.close();
            }
        })
    });
})();
//行业弹层
var inducot = (function() {
    $(".hangyeedit").click(
        function() {
            tip_edit.config({
                title : "行业",
                list : dicListConfig(true, inductList,postdata.induct),
                $this : $(this),
                radio : false,
                besure : function() {
                    var h="";
                    var type="";
                    postdata.induct=[];
                    $(".list li").each(function(index,element){
                        if($(this).hasClass("active")){
                            type="1";
                            
                            var map={};
                            map.code=$(this).attr("tip-l-i");
                            map.name=$(this).html();
                            h=h+"<span class='comp1'>"+$(this).html()+"</span>"
                            postdata.induct.push(map);

                        }
                    });

                    if(type!=1){
                        inductlist=null;
                    }
                    $(".induc").html(h);
                    tip_edit.close();
                }
            });
        });
})();
//阶段弹层
var stage = (function() {
    $(".stageedit").click(function() {
        tip_edit.config({
            title : "阶段",
            list : dicListConfig(true, stageList, stagelist),
            $this : $(this),
            radio : true,
            besure : function() {
                var h="";
                var type="";
                $(".list li").each(function(index,element){
                    if($(this).hasClass("active")){
                        type="1";
                        h=h+"<span class='comp1'>"+$(this).html()+"</span>"
                        stagelist=[{code:$(this).attr("tip-l-i")}];
                    }
                });
                postdata.stage=choiceContent();
                if(type!=1){
                    stagelist=null;
                }
                $(".stage").html(h);
                tip_edit.close();
            }
        });
    });
})();
//添加联系人
var addenupeople = (function() {
    var opt = {};
    function init() {
        //页面层
        layer
            .open({
                title : "添加联系人",
                type : 1,
                skin : 'layui-layer-rim', //加上边框
                area : [ '500px', '318px' ], //宽高
                content : '<div class="addfrom"><ul class="inputlist addpeoplebox"><li><span class="lable">联系人姓名:</span><span class="in"><select id="select_org" class=" inputdef"><option></option></select><input value="" id="peoplename" type="text" class="inputdef" style="display:none" maxlength="20"></span><span class="in"><button id="btn_people_add" class="btn btn_icon_add add_peoplename"></button></span></li>'
                    + '<li><span class="lable">职位:</span><span class="in"><input value="" id="posi" type="text" class="inputdef" maxlength="25"></span></li>'
                    + '<li><span class="lable">手机:</span><span class="in"><input value="" id="phone" type="text" class="inputdef" maxlength="11"></span>'
                    + '<li><span class="lable">邮箱:</span><span class="in"><input value="" id="email" type="text" class="inputdef" maxlength="200"></span>'
                    + '<li><span class="lable">微信:</span><span class="in"><input value="" id="wechat" type="text" class="inputdef" maxlength="200"></span></li></ul><div class="btn-box"><button class="btn btn-default saveorgan">保存</button></div></div>'
            });
        //选择联系人
        opt.$orgainselect = $("#select_org")
            .select2(
            {
                placeholder : "请选择联系人",//文本框的提示信息
                minimumInputLength : 0, //至少输入n个字符，才去加载数据
                allowClear : true, //是否允许用户清除文本信息
                ajax : {
                    url : "findEntreByName.html",
                    dataType : "json",
                    cache : true,
                    type : "post",
                    delay : 250,//加载延迟
                    data : function(params) {
                        return {
                            name : params.term || "",
                            pageSize : CommonVariable().SELSECLIMIT,
                            pageCount : "1",
                            logintype : cookieopt.getlogintype(),
                            type : "1"
                        }
                    },
                    processResults : function(data, page) {
                        for (var i = 0; i < data.list.length; i++) {
                            data.list[i].id = data.list[i].base_entrepreneur_code;
                            data.list[i].text = data.list[i].base_entrepreneur_name;
                        }
                        return {
                            results : data.list
                            //返回结构list值
                        };
                    }

                },
                escapeMarkup : function(markup) {
                    return markup;
                }, // let our custom formatter work
                templateResult : formatRepo
                // 格式化返回值 显示再下拉类表中
            });
        $("#select_org").on("change", function() { 
        	$("#phone").val(opt.$orgainselect.select2("data")[0].base_entrepreneur_phone);
        	$("#email").val(opt.$orgainselect.select2("data")[0].base_entrepreneur_email);
        	$("#wechat").val(opt.$orgainselect.select2("data")[0].base_entrepreneur_wechat);
        });
        click();
    }

    function click() {
        //添加联系人
        $(".add_peoplename").click(function(){
            opt.$orgainselect.val(null).trigger("change");
            $(".select2-container").css('display','none');
            $("#peoplename").css('display','block');
            $(".add_peoplename").css('display','none');
        });
        //添加联系人保存按钮
        $(".saveorgan").click(function() {
        	submitflg++;
        	if(submitflg==1){
        		if (selectdata().olist.length == 0) {
                    if($("#peoplename").val().trim()==""){
                        $.showtip("请选择联系人");
                        submitflg=0;
                    }else{
                        var pel=new Object();
                        pel.code="";
                        pel.name=$("#peoplename").val().trim();
                        pel.posi=$("#posi").val().trim();
                        pel.email=$("#email").val().trim();
                        pel.phone=$("#phone").val().trim();
                        pel.wechat=$("#wechat").val().trim();
                        if (pel.name.trim() == "") {
                            $.showtip("姓名不能为空");
                            submitflg=0;
                        } else {
                            if (check(pel.phone, pel.email)) {
                                postdata.peoplelit.push(pel);
                                peoplecont();
                                layer.closeAll();
                                submitflg=0;
                            }else{
                            	submitflg=0;
                            }

                        }
                    }

                } else {
                    var code = selectdata().olist[0].id;
                    for (var i = 0; i < postdata.peoplelit.length; i++) {
                        if (code == postdata.peoplelit[i].code) {
                            $.showtip("该联系人已存在");
                            submitflg=0;
                            return;
                        }
                    }
                    var pel=new Object();
                    pel.code=selectdata().olist[0].id;;
                    pel.name=selectdata().olist[0].text;;
                    pel.posi=$("#posi").val().trim();
                    pel.email=$("#email").val().trim();
                    pel.phone=$("#phone").val().trim();
                    pel.wechat=$("#wechat").val().trim();
                    if (pel.name.trim() == "") {
                        $.showtip("姓名不能为空");
                        submitflg=0;
                    } else {
                        if (check(pel.phone, pel.email)) {
                            postdata.peoplelit.push(pel);
                            peoplecont();
                            layer.closeAll();
                            submitflg=0;
                        }else{
                        	submitflg=0;
                        }

                    }

                }
        	}
            
        });
        //联系人修改
        $("[edit]").unbind().bind("click", function () {
            var i=$(this).attr("i");
            //页面层
            layer.open({
                title:"修改联系人",
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['420px', '320px'], //宽高
                content: '<div class="addfrom"><ul class="inputlist">' +
                    '<li><span class="lable">姓名:</span><span class="in">' +
                    '<input value="'+postdata.peoplelit[i].name+'" id="peoplename" type="text" class="inputdef" maxlength="20"></span></li>' +
                    '<li><span class="lable">职位:</span><span class="in">' +
                    '<input value="'+postdata.peoplelit[i].posi+'" id="posi" type="text" class="inputdef" maxlength="20"></span></li>' +
                    '<li><span class="lable">手机:</span><span class="in">' +
                    '<input value="'+postdata.peoplelit[i].phone+'" id="phone" type="text" class="inputdef" maxlength="11"></span></li>'+
                    '<li><span class="lable">邮箱:</span><span class="in">' +
                    '<input value="'+postdata.peoplelit[i].email+'" id="email" type="text" class="inputdef" maxlength="200"></span></li>'+
                '<li><span class="lable">微信:</span><span class="in">' +
                    '<input value="'+postdata.peoplelit[i].wechat+'" id="wechat" type="text" class="inputdef" maxlength="200"></span></li>'+
                    '</ul><div class="btn-box"><button class="btn btn-default editorgan">保存</button></div></div>'
            });
            $(".editorgan").unbind().bind("click", function () {
            	if($("#peoplename").val().trim()==""){
            		$.showtip("姓名不能为空");
            	}else{
            		if(check($("#phone").val(),$("#email").val())){
            			postdata.peoplelit[i].name=$("#peoplename").val().trim();
                        postdata.peoplelit[i].posi=$("#posi").val().trim();
                        postdata.peoplelit[i].phone=$("#phone").val().trim();
                        postdata.peoplelit[i].email=$("#email").val().trim();
                        postdata.peoplelit[i].wechat=$("#wechat").val().trim();
                        layer.closeAll('page');
                        peoplecont();
            		}
            		
            	}
                
            });
        });
        $("[remove]").unbind().bind("click", function () {
            var i=$(this).attr("i");
            postdata.peoplelit.baoremove(Number(i));
            peoplecont();
        });
    }

    function selectdata() {
        var map = {};
        var o = opt.$orgainselect.select2("data");
        var olist = [];
        for (var i = 0; i < o.length; i++) {
            if (!o[i].selected) {
                olist.push(o[i]);
            }
        }
        map.olist = olist;
        return map;
    }
    function peoplecont() {
        var html=template.compile(data_model.peoplist)(postdata);
        $(".people").html(html);
        click();
    }
    function formatRepo(repo) {
        return repo.text;
    }
    return {
        init : init,
        peoplecont:peoplecont
    }
})();
//点击创建公司按钮
var add_compan=(function(){
    $(".creatComp").click(function(){
        var name=$("#name").val();
        if(name.trim()==""){
            $.showtip("公司简称不能为空");
        }else{
            type="1";
            creat_ajax(type);
        }
    });

})();
//点击创建并添加交易按钮
var add_companTrad=(function(){
    $(".creatTrad").click(function(){
        var name=$("#name").val();
        if(name==""){
            $.showtip("公司简称不能为空");
            setTimeout("$.hidetip()",2000);
        }else{
            type="2";
            creat_ajax(type);
        }
    });

})();
//点击创建并添加融资计划按钮
var add_companRZ=(function(){
    $(".creatRz").click(function(){
        var name=$("#name").val();
        if(name==""){
            $.showtip("公司简称不能为空");
            setTimeout("$.hidetip()",2000);
        }else{
            type="3";
            creat_ajax(type);
        }
    });

})();
//创建公司，创建公司并添加融资计划，创建公司并添加交易按钮调用ajax
var creat_ajax= function (type) {
    $.showloading();
    var note="";
    if($(".compnote").val().trim()!=""){
    	note=$(".compnote").val();
    }
    $.ajax({
        url:"addCompany.html",
        type:"post",
        async:true,
        data:{
            name:$("#name").val().trim(),
            fullname:$("#fullname").val().trim(),
            ename:$("#ename").val().trim(),
            bask:JSON.stringify(postdata.basket),
            indu:JSON.stringify(postdata.induct),
            peoplelist:JSON.stringify(postdata.peoplelit),
            base_compnote_content:note,
            stagecode:postdata.stage,
            logintype:cookieopt.getlogintype()
        },
        dataType: "json",
        success: function(data){
            $.hideloading();
            if(data.message=="success"){
                $.showtip("保存成功");
                if(type==1){//创建公司
                    setTimeout(function(){
                        window.location.href="company_add.html?logintype="+cookieopt.getlogintype();
                    },2000);

                }else if(type==2){//创建交易
                    setTimeout(function(){
                        window.location.href="addTradeInfoinit.html?compcode="+data.code+"&logintype="+cookieopt.getlogintype();
                    },2000);

                }else if(type==3){//创建融资计划
                    setTimeout(function(){
                        add_rz(data.code,data.name,data.time);
                    },2000)

                }

            }else if(data.message=="repeat"){
                $.showtip("公司简称已存在");
            }else{
                $.showtip("保存失败");
            }

            $.hideloading();
        }
    });
};
//添加融资计划
function add_rz(a,b,c){

    var plantime;
    var emailtime;
    var text;
    layer.open({
        title : "添加融资计划",
        type : 1,
        skin : 'layui-layer-rim', //加上边框
        area : [ '500px', '327px' ], //宽高
        content : '<div class="addfrom"><ul class="inputlist">'
            + '<li><span class="lable">计划时间:</span><span class="in"><input type="text" class=" inputdef" id="plantime" data-date-format="yyyy-mm-dd" readonly></span></li>'
            + '<li><span class="lable">提醒时间:</span><span class="in"><input type="text" class=" inputdef" id="emailtime" data-date-format="yyyy-mm-dd" readonly></span></li>'
            + '<li><span class="lable">计划内容:</span><span class="in">' +
            /*'<input value="" id="text" type="textarea" class="inputdef finatext">' +*/
            '<textarea id="text" class="flan" maxlength="100"></textarea>'+
            '</span></li></ul><div class="btn-box"><button class="btn btn-default saverz">保存</button></div></div>',
        success: function(layero, index){
            $('#plantime').datepicker();
            $('#emailtime').datepicker();
        }
    });
    click();
    function click(){
        $(".saverz").click(function(){
        	submitrz++;
        	if(submitrz==1){
        		plantime = $("#plantime").val();
                emailtime = $("#emailtime").val();
                text = $("#text").val();
                if(plantime.trim()==""){
                    $.showtip("融资计划时间不能为空");
                    submitrz=0;
                    return;
                }
                if(emailtime.trim()==""){
                    $.showtip("邮件提醒时间不能为空");
                    submitrz=0;
                    return;
                }
                if(text.trim()==""){
                    $.showtip("融资计划内容不能为空");
                    submitrz=0;
                    return;
                }
                if (plantime.trim() != "" && emailtime.trim()!="" && text.trim()!="") {

                    var thisnowdate = new Date(c.substring(0, 10).replace(/-/g, "/"));
                    emailtime = new Date(emailtime.replace(/-/g, "/").replace("年", "/").replace("月", "/").replace("日", "/"));
                    plantime = new Date(plantime.replace(/-/g, "/").replace("年", "/").replace("月", "/").replace("日", "/"));
                    if (thisnowdate > plantime) {
                        $.showtip("融资计划时间小于当前日期");
                        submitrz=0;
                        return;
                    }else if (thisnowdate > emailtime) {
                        $.showtip("邮件提醒时间小于当前日期");
                        submitrz=0;
                        return;
                    							}

                    emailtime = emailtime.format(emailtime, "yyyy-MM-dd hh:mm:ss");

                    plantime = plantime.getTime();
                    add_rz(a,b);
                }
        	}
            
        });

    }
    function add_rz(a,b) {
        $.showloading();
        $.ajax({
            url:"addFinancplan.html",
            type:"post",
            async:true,
            data:{
                code:a,
                compName:b,
                logintype:cookieopt.getlogintype(),
                plantime:plantime,
                emailtime:emailtime,
                text:text
            },
            dataType: "json",
            success: function(data){
;
                $.hideloading();//等待动画隐藏
                if(data.message=="success"){
                    $.showtip("保存成功");
                    setTimeout(function(){
                    	submitrz=0
                        window.location.href="company_add.html?logintype="+cookieopt.getlogintype();
                    },2000)

                }else if(data.message=="failer"){
                    $.showtip("保存失败");
                	submitrz=0
                }
                layer.closeAll();
            }
        });
    }
}
//标签弹出层集合
function dicListConfig(vDel, vList, vObj) {
    var list = [];
    var map = {};
    for (var i = 0; i < vList.length; i++) {
        map = {};
        map.name = vList[i].sys_labelelement_name;
        map.id = vList[i].sys_labelelement_code;
        if (vObj != null) {
            for (var j = 0; j < vObj.length; j++) {
                //判断标签是否被选中,存在则加上选中标识
                if (vList[i].sys_labelelement_code == vObj[j].code) {
                    map.select = true;
                }
            }
        }
        list.push(map);
    }
    return list;
}
//验证邮箱，手机号格式
function check(phone, email) {
	var check_phone= /^1\d{10}$/;//手机格式验证
    var check_email=/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/ ;//邮箱验证


    if (phone != ""&&phone!=null) {
        if (!check_phone.test(phone)) {
            $.hideloading();
            $.showtip("请输入有效的手机号码");
            setTimeout("$.hidetip()", 2000);
            return false;
        }
    }
    if (email != ""&&email!=null) {
        if (!check_email.test(email)) {
            $.hideloading();
            $.showtip("请输入有效的邮箱");
            setTimeout("$.hidetip()", 2000);
            return false;
        }
    }
    return true;
}

//组成旧数据
function oldContent(vObj) {
    var list = "[";
    if (vObj != null) {
        for (var i = 0; i < vObj.length; i++) {
            list += "{code:'" + vObj[i].code + "',name:'" + vObj[i].name
                + "'},";
        }
    }
    list += "]";
    return list;
}
//新数据拼成
function choiceContent(){
    var list="";
    $(".list li[class='active']").each(function(){

        list=$(this).attr("tip-l-i");
    });

    return list;
}
var data_model={
    peoplist:'<% for (var i=0;i<peoplelit.length;i++ ){ %>' +
        '<li><span class="comp"><%=peoplelit[i].name %></span>' +
        '<span class="comp"><%=peoplelit[i].posi %></span>' +
        '<span class="comp"><%=peoplelit[i].phone %></span>' +
        '<span class="comp"><%=peoplelit[i].email %></span>'+
        '<span class="comp"><%=peoplelit[i].wechat %></span>'+
        '<span class="comp orremove" edit i="<%=i%>">修改</span>' +
        '<span class="comp orremove" remove i="<%=i%>">删除</span>' +
        '</li>' +
        '<%}%>'
};