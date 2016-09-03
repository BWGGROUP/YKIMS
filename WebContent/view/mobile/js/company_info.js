/**
 * Created by shbs-tp001 on 15-9-19.
 */
var oldstr;
var peopleName;
var phone;
var email;
var wechat;
var peoplecode;
var posi;
var type;
var pagetable;
var meetingtable;//会议子页
var updlogtable;//更新记录子页
var refBool=true;
var tablelists;
var submitflg=0;//添加公司联系人控制联系人确定按钮点击
var submitrz=0;//控制融资计划多次提交
$(function () {
    config.init();
    edit_name.click_config();//修改公司名称
    edit_People.click_config();//修改公司联系人
    add_People.click_config();//添加公司联系人
    addnote.btnclick();//添加备注
    add_rz.click_config();
    addTrade.click();
   
    
});
var config=(function(){
    var data={
        viewCompInfo:viewCompInfo,
        inducont:viewCompInfo.view_comp_inducont==null?[]:eval(viewCompInfo.view_comp_inducont),
        bask:viewCompInfo.view_comp_baskcont==null?[]:eval(viewCompInfo.view_comp_baskcont),
//        list:view_comp_person==null?[]:eval(viewCompInfo.view_comp_person),
        induList:induList,
        stageList:stageList,
        baskList:baskList,
        noteList:eval(noteList),
        financplanList:eval(financplanList),
        financList:eval(financList),
        version:version,
        noteconfignum:CommonVariable().NOTECONFIGNUM,
        entrepreneurList:eval(entrepreneurList),
        zhangkai:false,
        flanflg:false
    };
    var init=function(){

        var inducont=template.compile(data_model.inducont)(data);
        var bask=template.compile(data_model.bask)(data);
        var financplanList=template.compile(data_model.financplanList)(data);
        var financList=template.compile(data_model.financList)(data);
        var noteList=template.compile(data_model.noteList)(data);
        var entrepreneurList=template.compile(data_model.entrepreneurList)(data);
        $("#name").html(data.viewCompInfo.base_comp_name==null?"暂无数据":data.viewCompInfo.base_comp_name);
        $("#fullname").html(data.viewCompInfo.base_comp_fullname);
        $("#ename").html(data.viewCompInfo.base_comp_ename);
        if(bask==null||bask==""){
            $(".bask").html(data_model.inducontnull);
        }else{
            $(".bask").html(bask);
        }
        if(inducont==""){
            $(".induc").html(data_model.inducontnull);
        }else{
            $(".induc").html(inducont);
        }
        if(data.viewCompInfo.base_comp_stagecont==null){
            $(".stage").html("暂无数据");
        }else{
            $(".stage").html(data.viewCompInfo.base_comp_stagecont);
        }
        if(noteList==""){
            $(".notetable").html(data_model.noteListNull);
        }else{
            $(".notetable").html(noteList);
        }
        $(".financ").html(financList==""?'<tr><td colspan=\'5\'>暂无数据</td></tr>':financList);
        $(".financ tr").each(function(index,e){
            $(this).unbind().bind("click",function(){
               window.location.href="findTradeDetialInfo.html?tradeCode="+data.financList[index].base_trade_code+"&logintype="+cookieopt.getlogintype()+"&backherf=findCompanyDeatilByCode.html?code="+viewCompInfo.base_comp_code;
            })
        });
        $(".financplan").html(financplanList==""?'<tr><td colspan=\'2\'>暂无数据</td></tr>':financplanList);
        if(entrepreneurList==""){
            $(".people").html('<li><span style="color:#504F4F">暂无数据</span></li>');
        }else{
            $(".people").html(entrepreneurList);
        }
        if(data.noteconfignum<data.noteList.length){
            $(".closeshearch").show();
            $("[nodemore]").hide();
        }
        if(data.noteconfignum<data.financplanList.length){
            $(".closefinacplan").show();
            $("[finamores]").hide();
        }
        refBool=false;
        delTradeconfig.click(refBool);//调用删除融资信息
        click();

        if(backtype!=""){
			$(".goback").show();
			$(".goback").unbind().bind("click",function(){
				if(backtype=="searchcompany"
					||backtype=="searchcname"){
					window.location.href="company_search.html?logintype="
						+cookieopt.getlogintype()+"&backtype="+backtype;
				}else if(backtype=="searchtrade"){
                    window.location.href="trade_search.html?logintype="
                        +cookieopt.getlogintype()+"&backtype="+backtype;
                }else if(backtype=="searchfoot"){
                    window.location.href="myfoot_search.html?logintype="
                        +cookieopt.getlogintype()+"&backtype="+backtype;
                }
			});
			
		}
    };
    var click= function () {
        $(".closefinacplan").unbind().bind("click", function () {
            if(!$(this).hasClass("active")){
                $("[finamores]").show();
                $(this).html('收起<span class="glyphicon glyphicon-chevron-up"></span>').addClass("active");
                click();
                data.flanflg=true;
            }else{
                $("[finamores]").hide();
                $(this).html('更多<span class="glyphicon glyphicon-chevron-down"></span>').removeClass("active");
                data.flanflg=false;
            }
        });

        $(".closeshearch").unbind().bind("click", function () {
            if(!$(this).hasClass("active")){
                $("[nodemore]").show();
                $(this).html('收起<span class="glyphicon glyphicon-chevron-up"></span>').addClass("active");
                click();
                data.zhangkai=true;
            }else{
                $("[nodemore]").hide();
                $(this).html('更多<span class="glyphicon glyphicon-chevron-down"></span>').removeClass("active");
                data.zhangkai=false;
            }
        });
        $(".closefinacplan").unbind().bind("click", function () {
            if(!$(this).hasClass("active")){
                $("[finamores]").show();
                $(this).html('收起<span class="glyphicon glyphicon-chevron-up"></span>').addClass("active");
                click();
                data.flanflg=true;
            }else{
                $("[finamores]").hide();
                $(this).html('更多<span class="glyphicon glyphicon-chevron-down"></span>').removeClass("active");
                data.flanflg=false;
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
    var deletnote=function(notecode){
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "compnote_del.html",
            data: {
                notecode: notecode,
                logintype: cookieopt.getlogintype()
            },
            success: function (data) {
                if(data.message=="success"){
                    $.showtip("删除成功");
                    noteconfig(notecode);
                }else if(data.message=="failure"){
                    $.showtip("数据已被删除");
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
    var noteconfig= function (notecode) {
        if(notecode){
            data.noteList.baoremove(Number($("[del-code='"+notecode+"']").attr("i")));
        }

        var noteList=template.compile(data_model.noteList)(data);
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
    var rzconfig= function () {
        var financplanList=template.compile(data_model.financplanList)(config.data);
        $(".financplan").html(financplanList==""?'<tr><td colspan=\'2\'>暂无数据</td></tr>':financplanList);
        if(config.data.noteconfignum>=config.data.financplanList.length){

            $(".closefinacplan").html('更多<span class="glyphicon glyphicon-chevron-down"></span>').removeClass("active").hide();
        }else{
            if(!$(".closefinacplan").hasClass("active")){
                $(".closefinacplan").html('更多<span class="glyphicon glyphicon-chevron-down"></span>').show();
            }

        }
        if(!data.flanflg){
            $("[finamores]").hide();
        }

        click();
    };
    //会议记录事件
    $(".meetingClick").click(function(){
        pageMeetingDataRendering.show();
    });

    //会议子页返回
    $(".meetingReturn").click(function(){
        meetingtable.hidepage();
    });

    //更新记录事件
    $(".updlogClick").click(function(){
        pageUpdlogDataRendering.show();
    });

    //更新子页返回
    $(".updlogReturn").click(function(){
        updlogtable.hidepage();
    });
    //交易加载更多
    $("#flanmore").click(function(){
        tradeMore.showpage();

    });
    //交易更多页面的返回按钮点击事件
    $(".tradereturn").click(function(){
        pagetable.hidepage();
    });
    return{
        init:init,
        data:data,
        noteconfig:noteconfig,
        rzconfig:rzconfig
    }
})();
//2015-12-14 yl add start
function label(){
	var inducont=template.compile(data_model.inducont)(config.data);
    var bask=template.compile(data_model.bask)(config.data);
    if(bask==null||bask==""){
        $(".bask").html(data_model.inducontnull);
    }else{
        $(".bask").html(bask);
    }
    if(inducont==""){
        $(".induc").html(data_model.inducontnull);
    }else{
        $(".induc").html(inducont);
    }
    if(config.data.viewCompInfo.base_comp_stagecont==null){
        $(".stage").html("暂无数据");
    }else{
        $(".stage").html(config.data.viewCompInfo.base_comp_stagecont);
    }
}
//2015-12-14 yl add end
//编辑公司名称
var edit_name=(function(){
    var click_config= function () {
        $(".names").click(function () {
            inputlsit_edit.config({
                title:"编辑公司名称",
                list:[{id :"jname",lable:"公司简称",value:config.data.viewCompInfo.base_comp_name,maxlength:"20"},
                    {id :"namefull",lable:"公司全称",value:config.data.viewCompInfo.base_comp_fullname,maxlength:"200"},
                    {id :"enname",lable:"英文名称",value:config.data.viewCompInfo.base_comp_ename,maxlength:"200"}],
                submit: function () {
                    var jname=$("#jname").val().trim();
                    var namefull=$("#namefull").val().trim();
                    var enname=$("#enname").val().trim();
                    if(config.data.viewCompInfo.base_comp_name==null){
                    	config.data.viewCompInfo.base_comp_name="";
                                                      }
                    if(config.data.viewCompInfo.base_comp_ename==null){
                    	config.data.viewCompInfo.base_comp_ename="";
                                                      }
                    if(config.data.viewCompInfo.base_comp_fullname==null){
                    	config.data.viewCompInfo.base_comp_fullname="";
                                                      }
                    if(jname==config.data.viewCompInfo.base_comp_name&&
                        namefull==config.data.viewCompInfo.base_comp_fullname&&
                        enname==config.data.viewCompInfo.base_comp_ename){
                        $.showtip("数据未修改");
                        setTimeout("$.hidetip()",2000);
                        inputlsit_edit.close();
                    }else if(jname.trim()!=""){
                        oldstr="简称："+config.data.viewCompInfo.base_comp_name+";全称："
                            +config.data.viewCompInfo.base_comp_fullname+";英文名称："+
                            config.data.viewCompInfo.base_comp_ename;
                        /*config.data.viewCompInfo.base_comp_name=jname;
                        config.data.viewCompInfo.base_comp_fullname=namefull;
                        config.data.viewCompInfo.base_comp_ename=enname;*/
                        editname_ajax(jname,namefull,enname);
                        
                    }else{
                        $.showtip("简称不能为空");
                        setTimeout("$.hidetip()",2000);
                    }

                }
            });
        });
    };
    return{
        click_config:click_config
    }
})();
//编辑公司联系人
var edit_People=(function(){

    var click_config= function () {
            $(".people li").each(function (index,el) {
                $(this).unbind().bind("click", function () {
                    inputlsit_edit.config({
                        title: "编辑联系人",
                        list: [
                            {id: "peopleName", lable: "名称",
                                value: config.data.entrepreneurList[index].base_entrepreneur_name,maxlength:"20"},
                            {id: "posi", lable: "职位",
                                value: config.data.entrepreneurList[index].base_entrepreneur_posiname,maxlength:"20"},
                            {id: "phone", lable: "手机号",
                                value: config.data.entrepreneurList[index].base_entrepreneur_phone,maxlength:"11"},
                            {id: "email", lable: "邮箱",
                                value: config.data.entrepreneurList[index].base_entrepreneur_email,maxlength:"100"},
                            {id: "wechat", lable: "微信",
                                value: config.data.entrepreneurList[index].base_entrepreneur_wechat,maxlength:"100"}
                        ],
                        submit: function () {
                            var url="updatePeople.html";
                            peopleName = $("#peopleName").val().trim();
                            phone = $("#phone").val().trim();
                            email = $("#email").val().trim();
                            wechat=$("#wechat").val().trim();
                            posi=$("#posi").val().trim();
                            peoplecode=config.data.entrepreneurList[index].base_entrepreneur_code;
                            if(config.data.entrepreneurList[index].base_entrepreneur_name==null){
                            	config.data.entrepreneurList[index].base_entrepreneur_name=""
                                                                            }
                            if(config.data.entrepreneurList[index].base_entrepreneur_phone==null){
                            	config.data.entrepreneurList[index].base_entrepreneur_phone=""
                                                                            }
                            if(config.data.entrepreneurList[index].base_entrepreneur_email==null){
                            	config.data.entrepreneurList[index].base_entrepreneur_email=""
                                                                            }
                            if(config.data.entrepreneurList[index].base_entrepreneur_wechat==null){
                            	config.data.entrepreneurList[index].base_entrepreneur_wechat=""
                                                                            }
                            if(config.data.entrepreneurList[index].base_entrepreneur_posiname==null){
                            	config.data.entrepreneurList[index].base_entrepreneur_posiname=""
                                                                            }
                            if (peopleName == config.data.entrepreneurList[index].base_entrepreneur_name &&
                                phone == config.data.entrepreneurList[index].base_entrepreneur_phone &&
                                email == config.data.entrepreneurList[index].base_entrepreneur_email &&
                                wechat== config.data.entrepreneurList[index].base_entrepreneur_wechat &&
                                posi == config.data.entrepreneurList[index].base_entrepreneur_posiname) {
                                $.showtip("数据未修改");
                                setTimeout("$.hidetip()", 2000);
                                inputlsit_edit.close();
                            }else if (peopleName.trim() != "") {

                                oldstr = "[名称：" + config.data.entrepreneurList[index].base_entrepreneur_name +
                                    ";职位:"+config.data.entrepreneurList[index].base_entrepreneur_posiname+
                                    ";手机号："
                                    + config.data.entrepreneurList[index].base_entrepreneur_phone + ";" +
                                    "邮箱：" +
                                    config.data.entrepreneurList[index].base_entrepreneur_email+
                                ";微信:"+config.data.entrepreneurList[index].base_entrepreneur_wechat+"]";
                                if(check(phone,email)!=false) {
                                    editPeople_ajax(url);
                                    /*click_config();*/
//                                    inputlsit_edit.close();
                                }
                            } else {
                                $.showtip("名称不能为空");
                                setTimeout("$.hidetip()", 2000);
                            }

                        }
                 /*       ,complete: function () {
                        $("#peopleName").click(function () {
                            selectorgain.config();
                            $(this).focus(function () {
                                $(this).blur();
                            })
                        });
                    }*/
                    });
                });
                });


    };
    return{
        click_config:click_config
    }
})();
//添加联系人
var add_People=(function(){

    var click_config= function () {
            $("#add-people").unbind().bind("click", function () {
                inputlsit_edit.config({
                    title: "添加联系人",
                    list: [
                        {id: "peopleName", lable: "名称",maxlength:"20","readonly":"readonly"},
                        {id: "posi", lable: "职位",maxlength:"20"},
                        {id: "phone", lable: "手机号",maxlength:"11"},
                        {id: "email", lable: "邮箱",maxlength:"100"},
                        {id: "wechat", lable: "微信",maxlength:"100"}
                    ],
                    submit: function () {
                    	submitflg++;
                    	if(submitflg==1){
                    		 var url;
                             peoplecode=selectorgain.data.orgaincode;
                             url="addPeople.html";
                             peopleName = $("#peopleName").val().trim();
                             phone = $("#phone").val().trim();
                             email = $("#email").val().trim();
                             wechat=$("#wechat").val().trim();
                             posi=$("#posi").val().trim();
                             if (peopleName.trim() != "") {
                                 $.showloading();
                                 oldstr="";
                                 if(check(phone,email)!=false) {
                                     editPeople_ajax(url);
                                     edit_People.click_config();
//                                     inputlsit_edit.close();
                                 }
                             } else {
                                 $.showtip("名称不能为空");
                                 setTimeout("$.hidetip()", 2000);
                                 submitflg=0;
                             }
                    	}
                       

                    },
                    complete: function () {
                        $("#peopleName").click(function () {
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
        click_config:click_config
    }
})();
//创业者选择页面
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
            $("#peopleName").val("");
            opt.orgaincode="";
            WCsearch_list.closepage();
        });
    };
    var config=function(){

        WCsearch_list.config({
            allowadd:true,
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
            },
            addclick: function () {
                var data = {name: '姓名'};
                var addcomphtml=template.compile(data_model.addPeo)(data);
                $(".inner").html("<div class='lists'></div>");
                $(".select-page-input").val("");
                $(".lists").html(addcomphtml);
                $("#addpeoname").unbind().bind("click", function() {
                    opt.orgaincode="";
                    if(""!=$(".addpeoplename").val().trim()){
                        $("#peopleName").val($(".addpeoplename").val());
                    }

                    WCsearch_list.closepage();
                });
            }
        });
        dataconfig();
    };
    var orgain_ajax=function(){
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url:"findEntreByName.html",
            data:{
                name:$(".select-page-input").val().trim(),
                pageSize:opt.page.pageSize,
                pageCount:opt.page.pageCount,
                logintype:cookieopt.getlogintype()
            },
            success: function (data) {
                opt.page=data.page;
                if(data.message=="success"){
                    var html=template.compile(data_model.orgainlist)(data);
                    $(".lists").append(html);

                    opt.dropload.resetload();
                    $(".item").unbind().bind("click",function () {
                        $("#peopleName").val($(this).html());
                        $("#email").val($(this).attr('data-en-email'));
                        $("#phone").val($(this).attr('data-en-phone'));
                        $("#wechat").val($(this).attr('data-en-wechat'));
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
//添加备注
var addnote=(function () {
    var btnclick= function () {
        $("#addnote").unbind().bind("click", function () {
            var text=$(".notetext").val();
            /*text= text.split("\n").join("<br/>");*/
            if(text.trim()!=""){
                ajaxaddnote(text);
            }else{
                $.showtip("备注信息不能为空");
                $(".notetext").val("");
                setTimeout(function () {
                    $.hidetip();
                },2000);
            }
        });
    };
    var ajaxaddnote= function (text) {
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "addCompNote.html",
            data: {
                base_comp_code: config.data.viewCompInfo.base_comp_code,
                base_compnote_content:text,
                logintype: cookieopt.getlogintype()
            },
            success: function (data) {
                $.hideloading();
                if(data.message=="success"){
                    config.data.noteList=data.noteList;
                    config.noteconfig();
                    $(".notetext").val("");
                    $.showtip("保存成功");
                }else if(data.message=="error"){
                    $.showtip("保存失败");
                }else{
                    $.showtip("请求失败");
                }
                setTimeout(function () {
                    $.hidetip();
                },2000);
            }
        });
    };
    return{
        btnclick:btnclick
    }
})();
//编辑联系人调用的ajax
function editPeople_ajax(url){
    $.showloading();
    $.ajax({
        url:url,
        type:"post",
        async:true,
        data:{
        code:config.data.viewCompInfo.base_comp_code,
        peopleName:peopleName,
        phone:phone,
        email:email,
        wechat:wechat,
            posi:posi,
        version:config.data.version,
        oldstr:oldstr,
            peoplecode:peoplecode,
        logintype:cookieopt.getlogintype()
        },
        dataType: "json",
        success: function(data){
            $.hideloading();
            submitflg=0;
            if(data.message=="success"){
                $.showtip("保存成功");
                setTimeout("$.hidetip()",2000);
                config.data.entrepreneurList=eval(data.entrepreneurList);
                config.data.version=data.version;
                var entrepreneurList=template.compile(data_model.entrepreneurList)(config.data)
                $(".people").html(entrepreneurList);
                inputlsit_edit.close();
            }else if(data.message=="已被修改,请刷新页面再修改"){
                $.showtip("已被修改,请刷新页面再修改");
                setTimeout("$.hidetip()",2000);
                inputlsit_edit.close();
            }else if(data.message=="repeat") {
                config.data.entrepreneurList=eval(data.entrepreneurList);
                config.data.version=data.version;
                $.showtip("联系人已存在");
                setTimeout("$.hidetip()",2000);
            }else{
                $.showtip("保存失败");
                setTimeout("$.hidetip()",2000);
                inputlsit_edit.close();
            }
            edit_People.click_config();

        }
    });
}
//编辑公司调的ajax
function editname_ajax(jname,namefull,enname){
	$.showloading();//等待动画
    $.ajax({
        url:"updateName.html",
        type:"post",
        async:true,
        data:{
            code:config.data.viewCompInfo.base_comp_code,
            name:jname,
            fullname:namefull,
            ename:enname,
            version:config.data.version,
            oldstr:oldstr,
            logintype:cookieopt.getlogintype()
        },
        dataType: "json",
        success: function(data){
            $.hideloading();
            if(data.message=="success"){
                $.showtip("保存成功");
                setTimeout("$.hidetip()",2000);
                config.data.viewCompInfo=data.viewCompInfo;
                config.data.version=data.version;
                config.data.viewCompInfo.base_comp_name=jname;
                config.data.viewCompInfo.base_comp_fullname=namefull;
                config.data.viewCompInfo.base_comp_ename=enname;
                $("#name").html(data.viewCompInfo.base_comp_name);
                $("#fullname").html(data.viewCompInfo.base_comp_fullname);
                $("#ename").html(data.viewCompInfo.base_comp_ename);
                inputlsit_edit.close();
            }else if(data.message=="已被修改,请刷新页面再修改"){
                $.showtip("已被修改,请刷新页面再修改");
                setTimeout("$.hidetip()",2000);
                inputlsit_edit.close();
            }else if(data.message=="公司名称已存在"){
            	config.data.viewCompInfo=data.viewCompInfo;
                config.data.version=data.version;
            	$.showtip("公司名称已存在");
                setTimeout("$.hidetip()",2000);
            }else{
                $.showtip("保存失败");
                setTimeout("$.hidetip()",2000);
            }

        }
    });
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
}
//点击交易更多渲染近期交易界面
var tradeMore=(function(){
    var page={};

    function config_data() {
        page.pageCount=1;
    }
    function showpage(){
    	pagetable=$("#tradePage").showpage();
        config_data();
        pageDataRendering();
    }
    function pageDataRendering(){
    	$.showloading();//等待动画
        $.ajax({
            url:"findTradeByCode.html",
            type:"post",
            async:true,
            data:{
                code:config.data.viewCompInfo.base_comp_code,
                pageCount:page.pageCount,
                logintype:cookieopt.getlogintype()},
            dataType: "json",
            success: function(data){
            	$.hideloading();//等待动画
                page=data.page;
                tablelists=data.financList;
                if(data.financList.length>0){
                	$(".tradeLable").html("共"+data.page.totalCount+"条");
                    var html=template.compile(data_model.financList)(data);
                    if(html==""){
                        html='<tr><td  colspan="5" style="width:100%;">暂无数据</td></tr>';
                        $("#pageTradeBody tbody").html(html);
                        
                        pageList(page);
                    }else{
                    	$("#pageTradeBody tbody").html(html);
                        pageList(page);
                        click();
                    }
                }else{
                	html='<tr><td  colspan="5" style="width:100%;">暂无数据</td></tr>';
                    $("#pageTradeBody tbody").html(html);
                    
                    pageList(page);
                }

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
        }else{
            $('.pageaction').hide();
        }
    }
    function click() {
		$("#pageTradeBodya tr").each(function(index) {$(this).unbind().bind("click",function() {
            window.location.href="findTradeDetialInfo.html?tradeCode="+tablelists[index].base_trade_code+"&logintype="+cookieopt.getlogintype()+"&backherf=findCompanyDeatilByCode.html?code="+config.data.viewCompInfo.base_comp_code;
											})
						});
        refBool=true;
        delTradeconfig.click(refBool);

	}
    return{
        pageDataRendering:pageDataRendering,
        config_data:config_data,
        showpage:showpage
    }
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
        meetingtable=$("#meetingPage").showpage();
        config_data();
        select();
    }

    function select() {
        $.showloading();//等待动画
        $.ajax({
            url:"screenmeetinglist.html",
            type:"post",
            async:true,
            data:{companycode:config.data.viewCompInfo.base_comp_code,
                pageCount:data.page.pageCount,
                logintype:cookieopt.getlogintype()},
            dataType: "json",
            success: function(data){
                tabledata=data;
                $.hideloading();//隐藏等待动画
                page=data.page;
                if(data.message=="success"){
                    var html=template.compile(data_model.tablelist)(data);
                    if(html==""){
                        html='<tr><td  colspan="3" style="width:100%;">暂无数据</td></tr>';
                    }
                    $("#pageMeetingBody tbody").html(html);
                    $(".meetingLable").html("共"+data.page.totalCount+"条");
                    pagetable=$("#meetingPage").showpage();
                }else if(data.message=="nomore"){
                    var html='<tr><td  colspan="3" style="width:100%;">暂无数据</td></tr>';
                    $("#pageMeetingBody tbody").html(html);
                    pagetable=$("#meetingPage").showpage();
                }
                jqPagination();
                companyclick();
            }
        });

    }
    var jqPagination= function () {
        if(Number(page.totalPage)>0){
            $('.pagination').jqPagination({
                link_string	: '/?page={page_number}',
                max_page	:page.totalPage,
                current_page:page.pageCount,
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
//更新记录
var pageUpdlogDataRendering=(function (){
    var data={};

    function config_data() {
        data.page={
            pageCount:1
        }
    }
    function showpage(){
        updlogtable=$("#updlogPage").showpage();
        config_data();
        select();
    }

    function select() {
        $.showloading();//等待动画
        $.ajax({
            url:"findOrgUpdlogInfoByCode.html",
            type:"post",
            async:true,
            data:{type:'Lable-company',
                code:config.data.viewCompInfo.base_comp_code,
                pageCount:data.page.pageCount,
                logintype:cookieopt.getlogintype()},
            dataType: "json",
            success: function(json){
                $.hideloading();//隐藏等待动画
                data.page=json.page;
                if(json.message=="success"){
                    var html=template.compile(data_model.updlogList)(json);
                    $("#pageUpdlogBody tbody").html(html==""?'<tr><td  colspan="4" style="width:100%;">暂无数据</td></tr>':html);
                    $(".updlogLable").html("共"+data.page.totalCount+"条");
                    updlogtable=$("#updlogPage").showpage();
                }
                jqPagination();
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
//筐弹层
var bask=(function(){
    $(".bask").click(function () {
        $.showloading();
        var $this=$(this);
        listefit.config({
            title:"关注筐",
            list:dicListConfig(true,config.data.baskList,config.data.bask),
            radio:false,
            besure: function () {
                var oldData=oldContent(config.data.bask);
                var newData=choiceContent();
                if(oldData==newData){
                    $.showtip("数据未修改");
                    setTimeout("$.hidetip()",2000);
                }else{
                    $.showloading();//等待动画
                    type="Lable-bask";
                    edit_lable(oldData,newData);
                    listefit.close();
                }
            }
        });
        $.hideloading();//等待动画隐藏
    });
})();
//行业弹层
var induc=(function(){
    $(".induc").click(function () {
        $.showloading();
        var $this=$(this);
        listefit.config({
            title:"行业",
            list:dicListConfig(true,config.data.induList,config.data.inducont),
            radio:false,
            besure: function () {
                var oldData=oldContent(config.data.inducont);
                var newData=choiceContent();
                if(oldData==newData){
                    $.showtip("数据未修改");
                    setTimeout("$.hidetip()",2000);
                }else{
                    $.showloading();//等待动画
                    type="Lable-indu";
                    edit_lable(oldData,newData);
                    listefit.close();
                }
            }
        });
        $.hideloading();//等待动画隐藏
    });
})();
//阶段弹层
var stage=(function(){
    $("#ul-stage").click(function () {
        var statge=[];
        statge.code=config.data.viewCompInfo.base_comp_stage;
        var listselectcode=[{code:config.data.viewCompInfo.base_comp_stage}];
        var $this=$(this);
        listefit.config({
            title:"阶段",
            list:dicListConfig(true,config.data.stageList,listselectcode),
            radio:true,
            besure: function () {
                var oldData=config.data.viewCompInfo.base_comp_stagecont;
                var newData=choiceContent();
                if(oldData==choiceContentstage()||((oldData==""||oldData==null)&&choiceContentstage()=="")){
                    $.showtip("数据未修改");
                    setTimeout("$.hidetip()",2000);
                }else{
                    
                    type="Lable-stage";
                    edit_lable(oldData,newData);
                    listefit.close();
                }
            }
        });
    });
})();
function edit_lable(oldData,newData){
	$.showloading();//等待动画
    $.ajax({
        url:"updateCompInfo.html",
        type:"post",
        async:true,
        data:{
            type:type,
            code:config.data.viewCompInfo.base_comp_code,
            compName:config.data.viewCompInfo.base_comp_name,
            logintype:cookieopt.getlogintype(),
            oldData:oldData,
            newData:newData,
            version:config.data.version
        },
        dataType: "json",
        success: function(data){
            $.hideloading();//等待动画隐藏
            if(data.message=="success"){
                $.showtip("保存成功");
                setTimeout("$.hidetip()",2000);
                config.data.bask=(data.compInfo.view_comp_baskcont==null?[]:eval(data.compInfo.view_comp_baskcont));
                config.data.inducont=(data.compInfo.view_comp_inducont==null?[]:eval(data.compInfo.view_comp_inducont));
                config.data.viewCompInfo.base_comp_stagecont=data.compInfo.base_comp_stagecont;
                config.data.viewCompInfo.base_comp_stage=data.compInfo.base_comp_stage;
                config.data.version=data.version;
                /*config.init();*/
                label();
            }else{
                $.showtip(data.message);
            }
            setTimeout("$.hidetip()",2000);
        }
    });
}
//添加融资计划
var add_rz=(function(){
    var plantime;
    var emailtime;
    var text;
    var click_config= function () {
        $(".addrz").unbind().bind("click", function () {
            inputlsit_edit.config({
                title: "添加融资计划",
                list: [
                    {id: "plantime", lable: "计划时间",type:"date"},
                    {id: "emailtime", lable: "提醒时间",type:"date"},
                    {id: "text", lable: "计划内容",type:"textarea",maxlength:"100"}
                ],
                submit: function () {
                	submitrz++;
                	if(submitrz==1){
                		plantime = $("#plantime").val();
                        emailtime = $("#emailtime").val();
                        text = $("#text").val();

                        if (plantime.trim() != "" && emailtime.trim()!="" && text.trim()!="") {

                            var thisnowdate=new Date(nowdate.substring(0,10).replace(/-/g,"/"));
                            emailtime=new Date(emailtime.replace(/-/g,"/").replace("年","/").replace("月","/").replace("日","/"));
                            plantime=new Date(plantime.replace(/-/g,"/").replace("年","/").replace("月","/").replace("日","/"));
                            if(thisnowdate>emailtime || thisnowdate>plantime){
                                $.showtip("所选日期小于当前日期");
                                setTimeout("$.hidetip()",2000);
                                submitrz=0;
                                return;
                            }

                            emailtime= emailtime.format(emailtime,"yyyy-MM-dd hh:mm:ss");

                            plantime=plantime.getTime();
                            oldstr="";
                            add_rz();
                            click_config();
                            inputlsit_edit.close();
                        } else {
                            $.showtip("不能为空");
                            setTimeout("$.hidetip()", 2000);
                            submitrz=0;
                        }
                	}
                    

                }
            });
        });


    };

    function add_rz() {
        $.showloading();
        $.ajax({
            url:"addFinancplan.html",
            type:"post",
            async:true,
            data:{
                code:config.data.viewCompInfo.base_comp_code,
                compName:config.data.viewCompInfo.base_comp_name,
                logintype:cookieopt.getlogintype(),
                plantime:plantime,
                emailtime:emailtime,
                text:text
            },
            dataType: "json",
            success: function(data){
            	submitrz=0;
                $.hideloading();//等待动画隐藏
                if(data.message=="success"){
                    $.showtip("保存成功");
                    setTimeout("$.hidetip()",2000);
                    config.data.financplanList=eval(data.financplanList);
                    config.rzconfig();
                }else if(data.message=="failer"){
                    $.showtip("保存失败");
                }
                setTimeout("$.hidetip()",2000);
            }
        });
    }
    return{
        click_config:click_config
    }
})();
//删除融资信息
var delTradeconfig=(function(){
    var click=function(){
        $("[del-tradecode]").unbind().bind("click",function(e){
          var $this=$(this);

            if($this.attr("del-tradecode")==null){
                $.showtip("未发现融资信息","normal",2000);
                return;
            }
            inputlsit_edit.config({
                title:"提示",//弹层标题
                html:true,//是否以html显示
                besurebtn:"确定",//确定按钮文字
                cancle:"取消",//取消按钮文字 （如果不写，则不会显示该按钮）
                htmltext:"<div style='text-align: center;font-size: 15px;'>您确定要删除该条记录吗？</div>",
                submit: function () {
                    del($this.attr("del-tradecode"));
                    inputlsit_edit.close();
                },//点击确定按钮回调方法
                canmit: function () {
                    inputlsit_edit.close();
                }//点击取消按钮回调方法
            });
            e.stopPropagation();
        });

    };

    var del=function(tradeCode){
        $.showloading();//等待动画
        var ii=Number($("[del-tradecode='"+tradeCode+"']").attr("i"));
        var orgCodeString;
    	   var tradeDate;
    	   if(refBool){
    		   orgCodeString=tablelists[ii].view_investment_code;
    		   tradeDate=tablelists[ii].base_trade_date;
    	   }else{
    		   orgCodeString=config.data.financList[ii].view_investment_code;
    		   tradeDate=config.data.financList[ii].base_trade_date;
    	   }
        $.ajax({
            url:"delTradeInfo.html",
            type:"post",
            async:true,
            data:{
            	compcode:viewCompInfo.base_comp_code,
                tradeCode:tradeCode,
                orgCodeString:orgCodeString,
                tradeDate:tradeDate,
                companyName:viewCompInfo.base_comp_name,
                logintype:cookieopt.getlogintype()
            },
            dataType: "json",
            success: function(data){
                $.hideloading();//等待动画隐藏
                if(data.message=="success") {
                    $.showtip("删除成功");
                    if(refBool==true){
                        tradeMore.pageDataRendering();
                        select();
                    }else{              
                    	select();
                    }

                }else if(data.message=="delete"){
                    $.showtip("交易信息已被删除");
                    tradeMore.pageDataRendering();
                    select();
                }else{
                    $.showtip(data.message);
                }
                setTimeout("$.hidetip()",2000);
                click();
            }
        });
    };

    return {
        click:click
    };
})();
//添加交易
var addTrade=(function(){
	var click=function(){
        $(".addtradebtn").unbind().bind("click",function(){
        	window.location.href="addTradeInfoinit.html?logintype="+cookieopt.getlogintype()+"&compcode="+viewCompInfo.base_comp_code+"&backherf=findCompanyDeatilByCode.html?code="+viewCompInfo.base_comp_code;
        })
   
            };
	return {
		click:click
	}
})();
var data_model={
    inducont:'<% for (var i=0;i<inducont.length;i++ ){%>' +
        '<li><%=inducont[i].name%></li>' +
        '<%}%>',
    inducontnull:'<li>暂无数据</li>',
    bask:'<%for (var i=0;i<bask.length;i++ ){%>' +
        '  <li><span class="comp"><%=bask[i].name%></li>' +
        '<%}%>',
    viewTradeInfoNull:'<tr><td colspan=\'4\'>暂无数据</td></tr>',
    noteList:'<% for (var i=0;i<noteList.length;i++ ){ %>' +
        '<tr <%= nodermore(i,noteconfignum)%>>' +
        '<td><%=dateFormat(noteList[i].createtime.time,"yyyy-MM-dd")%></td>' +
        '<td><%=noteList[i].sys_user_name%></td>' +
        '<td><pre><%=noteList[i].base_compnote_content %></pre></td>' +
        '<td><button class="btn btn-default smart" i="<%=i%>" del-code="<%=noteList[i].base_compnote_code%>">删除</button></td>' +
        '</tr>'+
        '<%}%>',
    noteListNull:'<tr><td colspan=\'4\'>暂无数据</td></tr>',
    financplanList:'<% for (var i=0;i<financplanList.length;i++ ){%>' +
        '<tr <%= finamores(i,noteconfignum)%>>' +
        '<td><%=dateFormat(financplanList[i].base_financplan_date,"yyyy-MM-dd")%></td>' +
        '<td><pre><% print(financplanList[i].base_financplan_cont)%></pre></td>' +
        '</tr>'+
        '<%}%>',
    financList:  '<% for (var i=0;i<financList.length;i++ ){%>'+
        '<tr code=<%=financList[i].base_trade_code%>>' +
        '<td><%=dateFormat(financList[i].base_trade_date,"yyyy-MM")%></td>' +
        '<td><%=financList[i].base_trade_stagecont%></td>' +
        '<td><%=financList[i].base_trade_money%></td>' +
        '<td><%=financList[i].view_investment_name%></td>'+
        '<td><button class="btn btn-default smart" i="<%=i%>" del-tradecode="<%=financList[i].base_trade_code%>">删除</button></td>' +
        '</tr>'+
        '<%}%>',
    updlogList:'<%for(var i=0;i<list.length;i++){%>'+
        '<tr><td><%=dateFormat(list[i].updtime.time,"yyyy-MM-dd")%></td>'+
        '<td><%=list[i].sys_user_name%></td>'+
        '<td><%=list[i].base_updlog_opercont%></td>'+
        '<td><%=list[i].base_updlog_oridata==""?"":list[i].base_updlog_oridata%>'+
        '<%=list[i].base_updlog_newdata==""?"":list[i].base_updlog_newdata%></td></tr>'+
        '<%}%>',
    entrepreneurList:'<% for (var i=0;i<entrepreneurList.length;i++ ){%>' +
        '<li><span class="comp"><%=entrepreneurList[i].base_entrepreneur_name%></span>' +
    '<span class="comp"><%=entrepreneurList[i].base_entrepreneur_posiname%></span>' +
        '<span class="comp"><%=entrepreneurList[i].base_entrepreneur_phone%></span>' +
        '<span class="comp"><%=entrepreneurList[i].base_entrepreneur_wechat%>' +
        '</span><span class="comp"><%=entrepreneurList[i].base_entrepreneur_email%></span></li>' +
        '<%}%>',
    tablelist:  '<% for (var i=0;i<list.length;i++ ){%>'+
        '<tr>' +
        '<td><%=list[i].base_meeting_time%></td>' +
        '<td><%=substring(list[i].createName,"10")%></td>' +
        '<td><%=substring(list[i].base_meeting_compcont+" / "+list[i].base_meeting_invicont,"10")%></td>'+
        '</tr>'+
        '<%}%>',
    orgainlist: '<% for (var i=0;i<list.length;i++ ){%>' +
        '<div data-org-code="<%=list[i].base_entrepreneur_code%>"' +
        'data-en-phone="<%=list[i].base_entrepreneur_phone%>"'+
        'data-en-email="<%=list[i].base_entrepreneur_email%>"'+
        'data-en-wechat="<%=list[i].base_entrepreneur_wechat%>"'+
        'class="item"><%=list[i].base_entrepreneur_name %></div>' +
        '<%}%>',
    addPeo:'<div class="shgroup" style="margin-top:10px">'
        +' <div class="title no-border"><%=name%>:</div>'
        +' <div class="tiplist no-border" style="padding-right:10px;">'
        +' <input type="text" class="inputdef inputdef_l addpeoplename" >'
        +'</div>'
        +'<div style="display:table-cell">'
        +'<button class="btn btn-default" id="addpeoname"'
        +' style="width:70px;height:25px;line-height:26px;">'
        +'确定</button></div></div>'
};
template.helper("nodermore", function (a,b) {
    if(a<b){
        return "";
    }else{
        return "nodemore"
    }
});
template.helper("finamores", function (a,b) {
    if(a<b){
        return "";
    }else{
        return "finamores"
    }
});
//应用模板 截取字符串
template.helper("substring", function (str,l) {
    str= str.replace("<br/>","");
    if(str.length>l){
        str= str.substring(0,l)+"..."
    }
    return str;
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
function choiceContentstage(){
    var list="";
    $(".list li[class='active']").each(function(){
        list+=$(this).text();
    });
    return list;
}
template.helper("logintype", function() {
    return cookieopt.getlogintype();
});
function fina(){
	var financList=template.compile(data_model.financList)(config.data);
	$(".financ").html(financList==""?'<tr><td colspan=\'5\'>暂无数据</td></tr>':financList);
	$(".financ tr").each(function(index,e){
        $(this).unbind().bind("click",function(){
           window.location.href="findTradeDetialInfo.html?tradeCode="+config.data.financList[index].base_trade_code+"&logintype="+cookieopt.getlogintype()+"&backherf=findCompanyDeatilByCode.html?code="+config.data.viewCompInfo.base_comp_code;
        })
    });
	delTradeconfig.click();
}
function select() {
	$.showloading();//等待动画
	$.ajax({
				url : "findTradeByCode.html",
				type : "post",
				async : true,
				data : {
					code : config.data.viewCompInfo.base_comp_code,
					pageCount : 1,
					//pageSize:new CommonVariable().SHOWPAGESIZE,
					logintype : cookieopt.getlogintype()
				},
				dataType : "json",
				success : function(data) {
					$.hideloading();
					page = data.page;
					config.data.financList = data.financList;
					var html = template.compile(data_model.financList)(data);
					if (html == "") {
						html = '<tr class="nohover"><td  colspan="5" style="width:100%;">暂无数据</td></tr>';
						$(".financ").html(html);
					} else {
						$(".financ").html(html);
					}
					$(".tradeLable").html("共"+data.page.totalCount+"条");
					fina();
				}
			});
}