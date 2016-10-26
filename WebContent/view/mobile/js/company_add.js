/**
 * Created by shbs-tp001 on 15-9-22.
 */
var little_name="";
var full_name="";
var en_name="";
var peopel=[];
var listselectcode=[];//选中的筐
var induselectcode=null;//选中的行业
var stagecode=null;//选中的阶段
var selectcode=null;//选中的阶段
var listselectcode1="[]";//选中的筐
var induselectcode1="[]";//选中的行业
var type;//页面创建公司(1)，创建并添加交易(2)，创建并添加融资计划(3)区分
var plantime;//融资时间
var emailtime;//邮件提醒时间
var text;//融资内容
var peocode="";//选择的联系人code
var submitflg=0;//添加联系人点击flg
var submitrz=0;//控制融资计划多次点击
$(function () {
//    config.data();
    compName_edit.click();
    kuang_edit.click();
    indu_edit.click();
    people_edit.click();

});

function showcompany() {
    $(".little-name").html((little_name!="")?little_name:'<span class="color-def">公司简称</span>');
    $(".full-name").html((full_name!="")?full_name:'<span class="color-def">公司全称</span>');
    $(".en-name").html((en_name!="")?en_name:'<span class="color-def">英文名称</span>');
}
function showpeopel(){
    var html="";
    for(var i=0;i<peopel.length;i++){
         html=html+'<li p-index="'+i+'"><span class="comp">'+peopel[i].name+'</span>'
        if(peopel[i].job!=""){
            html=html+'<span class="comp">'+peopel[i].job+'</span>'

        }
        if(peopel[i].email!=""){
            html=html+'<span class="comp">'+peopel[i].email+'</span>'
        }

        if(peopel[i].phone!=""){
            html=html+'<span class="comp">'+peopel[i].phone+'</span>'
        }
        if(peopel[i].weichart!=""){
            html=html+'<span class="comp">'+peopel[i].weichart+'</span>'
        }
        html=html+"</li>"
    }
    $(".peoplelist").html(html);
    peoplecick();
}

function peoplecick(){
    $("li[p-index]").unbind().bind("click", function () {
        var i=$(this).attr("p-index");
        inputlsit_edit.config({
            title:"修改联系人",
            besurebtn:"修改",
            cancle:"删除",
            list:[{id:"name",lable:"姓名",value:peopel[i].name,maxlength:"20"},{id:"job",lable:"职位",value:peopel[i].job,maxlength:"20"},{id:"email",lable:"邮箱",type:"email",value:peopel[i].email,maxlength:"200"},{id:"phone",lable:"电话",type:"tel",value:peopel[i].phone,maxlength:"11"},{id:"weichart",lable:"微信",value:peopel[i].weichart,maxlength:"200"}],
            submit: function () {
                var pel=new Object();
                pel.code=peopel[i].code;
                pel.name=$("#name").val().trim();
                pel.job=$("#job").val().trim();
                pel.email=$("#email").val().trim();
                pel.phone=$("#phone").val().trim();
                pel.weichart=$("#weichart").val().trim();
                if(pel.name==""&&(pel.job!=""||pel.email!=""||pel.phone!=""||pel.weichart!="")){
                    $.showtip("名称不能为空");
                    setTimeout("$.hidetip()",2000);
                }else{
                	if(check(pel.phone,pel.email)) {
                		peopel[i]=pel;
                  showpeopel();
                  inputlsit_edit.close();
                                                      }
                    
                }
                
            },
            canmit: function () {
                peopel.baoremove(i);
                showpeopel();
                inputlsit_edit.close();
            },
            complete: function () {
                $("[value='"+peopel[i].job+"']").attr("selected","true");
            }

        });
    });
}


//编辑公司名称
var compName_edit=(function(){
    var name_click=function(){
        $(".nameUp").bind("click", function () {
            inputlsit_edit.config({
                title:"公司名称",
                list:[{id:"little-name",lable:"公司简称",value:little_name,maxlength:"20"},{id:"full-name",lable:"公司全称",value:full_name,maxlength:"200"},{id:"en-namem",lable:"英文名称",value:en_name,maxlength:"200"}],
                submit: function () {
                    little_name=$("#little-name").val().trim();
                    full_name=$("#full-name").val().trim();
                    en_name=$("#en-namem").val().trim();
                    checkcombyname();
                }
            });
        });
    };
    function checkcombyname() {
        if(little_name==""){
            $.showtip("公司简称不可为空");
            setTimeout(function () {
                $.hidetip();
            },2000);
        }else{
            $.ajax({
                url:"checkcombyname.html",
                type:"post",
                async:true,
                data:{
                    name:little_name,
                    logintype:cookieopt.getlogintype()
                },
                dataType: "json",
                success: function(data){
                    if(data.message=="companyexist"){
                        $.showtip("公司简称已存在");
                    }else if(data.message=="error"){
                        $.showtip("请求失败");
                    }else{
                        showcompany();
                        inputlsit_edit.close();
                    }
                    setTimeout(function () {
                        $.hidetip();
                    },2000);

                }
            });
        }

    }
    return{
        click:name_click
    }

})();
//关注框初始化
var kuang_edit=(function(){
    var kuang_click=function(){
        $(".kuang").click(function () {
            listefit.config({
                title:"关注筐",
                list:dicListConfig(baskList,listselectcode),
                radio:false,
                besure: function () {
                    var h="";
                    var type="";
                    listselectcode=[];
                    $(".list li").each(function(index,element){
                        if($(this).hasClass("active")){
                            type="1";
                            h=h+"<li>"+$(this).html()+"</li>"
                            /*listselectcode=[{code:$(this).attr("tip-l-i")}];*/
                            var map={};
                            map.code=$(this).attr("tip-l-i");
                            listselectcode.push(map);
                        }
                    });
                    /*if(type!=1){
                        listselectcode=null;
                    }*/
                    listselectcode1=choiceContent();
                    $(".kuanglist").html(h)
                }
            });
            });

    }
    return{
        click:kuang_click
    }
})();
//行业初始化
var indu_edit=(function(){
    var indu_edit=function(){
        $(".indu").click(function () {
            listefit.config({
                title:"行业",
                list:dicListConfig(inductList,induselectcode),
                radio:false,
                besure: function () {
                    var h="";
                    var type="";
                    induselectcode1=null;
                    induselectcode=[];
                    $(".list li").each(function(index,element){
                        if($(this).hasClass("active")){
                            type="1";
                            h=h+"<li>"+$(this).html()+"</li>"
//                            induselectcode=[{code:$(this).attr("tip-l-i")}];
                            var map={};
                            map.code=$(this).attr("tip-l-i");
                            induselectcode.push(map);
                            	

                        }
                        induselectcode1=choiceContent();

                    });
                    if(type!=1){
                        induselectcode=null;
                    }
                    $(".hangyelist").html(h)
                }
            });
        });

    }
    return{
        click:indu_edit
    }
})();
//阶段弹层
var stage=(function(){
    $(".stage").click(function () {
        listefit.config({
            title:"阶段",
            list:dicListConfig(stageList,selectcode),
            radio:true,
            besure: function () {
                var h="";
                var type="";
                stagecode=null;
                $(".list li").each(function(index,element){
                    if($(this).hasClass("active")){
                        type="1";
                        h=h+"<li>"+$(this).html()+"</li>"
                        selectcode=[{code:$(this).attr("tip-l-i")}];
                        stagecode=$(this).attr("tip-l-i");
                    }

                });
                if(type!="1"){
                    stagecode=null;
                    selectcode=null;
                }
                $(".ul-stage").html(h)
            }
        });
    });
})();
//添加联系人
var people_edit=(function(){
    var edit_click=function(){
        $("#add-people").bind("click", function () {
            inputlsit_edit.config({
                title:"添加联系人",
                list:[{id:"name",lable:"姓名",readonly:"readonly",maxlength:"20"},{id:"job",lable:"职位",maxlength:"200"},{id:"email",lable:"邮箱",type:"email",maxlength:"200"},{id:"phone",lable:"电话",type:"tel",maxlength:"11"},{id:"weichart",lable:"微信",maxlength:"200"}],
                submit: function () {
                	submitflg++;
                	if(submitflg==1){
                		 var pel=new Object();
                         pel.code=selectorgain.data.orgaincode;
                         if(checkPeople(peocode,pel.code)){
                             pel.name=$("#name").val().trim();
                             pel.job=$("#job").val().trim();
                             pel.email=$("#email").val().trim();
                             pel.phone=$("#phone").val().trim();
                             pel.weichart=$("#weichart").val().trim();
                             if(pel.name==""&&(pel.job!=""||pel.email!=""||pel.phone!=""||pel.weichart!="")){
                                 $.showtip("名称不能为空");
                                 submitflg=0;
                                 setTimeout("$.hidetip()",2000);
                             }else {
                                 if(check(pel.phone,pel.email)) {
                                     peopel.push(pel);
                                     showpeopel();
                                     peocode+=","+selectorgain.data.orgaincode;
                                     inputlsit_edit.close();
                                     submitflg=0;
                                 }else{
                                	 submitflg=0;
                                 }
                             }
                         }else{
                             $.showtip("联系人已存在");
                             setTimeout("$.hidetip()",2000);
                             submitflg=0;
                         }
                	}


                },
                complete: function () {
                    $("#name").click(function () {
                        selectorgain.config();
                        $(this).focus(function () {
                            $(this).blur();
                        })
                    });
                }
            });
        });
    }
    //修改联系人
    var edit_people=function(){
    	$(".peoplelist li").each(function(index,e){
    		$(this).bind("click",function(){
                var reade;
                if(peopel[index].code!=""){
                    reade="readonly"
                }
    			inputlsit_edit.config({
                    title:"修改联系人",
                    list:[{id:"name",lable:"姓名",value:peopel[index].name,maxlength:"20"},{id:"job",lable:"职位",value:peopel[index].job,maxlength:"20"},{id:"email",lable:"邮箱",type:"email",value:peopel[index].email,maxlength:"200"},{id:"phone",lable:"电话",type:"tel",value:peopel[index].phone,maxlength:"11"},{id:"weichart",lable:"微信",value:peopel[index].weichart,maxlength:"200"}],
                    submit: function () {
                        var pel=new Object();
                        pel.name=$("#name").val().trim();
                        pel.job=$("#job").val().trim();
                        pel.email=$("#email").val().trim();
                        pel.phone=$("#phone").val().trim();
                        pel.weichart=$("#weichart").val().trim();
                        if(pel.name==""&&(pel.job!=""||pel.email!=""||pel.phone!=""||pel.weichart!="")){
                            $.showtip("名称不能为空");
                            setTimeout("$.hidetip()",2000);
                        }else{
                            if(check(pel.phone,pel.email)){
                                peopel[index]=pel;
                                showpeopel();
                                inputlsit_edit.close();
                            }

                        }

                    }
    			})
    		});
    	});
    }
    return{
        click:edit_click
    }
})();
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
//
function choiceContent(){
    var list="[";
    $(".list li[class='active']").each(function(){

        list+="{code:'"+$(this).attr("tip-l-i")+"'},";
   });
list+="]";
    return list;
}
//联系人ist
function choicePeople(people){
    var list="[";
    if(peopel!=null&&peopel.length>0){
        for(var i=0;i<people.length;i++){
            list+="{code:'"+peopel[i].code+"',name:'"+peopel[i].name+"',email:'"+peopel[i].email+"',phone:'"+peopel[i].phone+"',wechat:'"+peopel[i].weichart+"',posi:'"+peopel[i].job+"'},";
        }
    }

    $(".list li[class='active']").each(function(){
        list+="{code:'"+$(this).attr("tip-l-i")+"',name:'"+$(this).text()+"'},";
    });
    list+="]";
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
//       $(".inner").html("<div class='lists'><div class='item' style='text-align: center;color:#AAA'>不限</div></div>");
        $(".item:first").unbind().bind("click",function () {
            $("#name").val("");
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
                        $("#name").val($(".addpeoplename").val());
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
                name:$(".select-page-input").val(),
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
                        $("#name").val($(this).html());
                        $("#email").val($(this).attr('data-en-email'));
                        $("#phone").val($(this).attr('data-en-phone'));
                        $("#weichart").val($(this).attr('data-en-wechat'));
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
var data_model= {
    orgainlist: '<% for (var i=0;i<list.length;i++ ){%>' +
        '<div data-org-code="<%=list[i].base_entrepreneur_code%>"' +
        'data-en-phone="<%=list[i].base_entrepreneur_phone%>"'+
        'data-en-email="<%=list[i].base_entrepreneur_email%>"'+
        'data-en-wechat="<%=list[i].base_entrepreneur_wechat%>"'+
        'class="item"><%=list[i].base_entrepreneur_name %></div>' +
        '<%}%>',
    addPeo:'<div class="shgroup" style="margin-top: 10px;">'
+' <div class="title no-border"><%=name%>:</div>'
+' <div class="tiplist no-border" style="padding-right:10px;">'
+' <input type="text" class="inputdef inputdef_l addpeoplename" maxlength="20">'
+'</div>'
+'<div style="display:table-cell">'
+'<button class="btn btn-default" id="addpeoname"'
+' style="width:70px;height:25px;line-height:26px;">'
+'确定</button></div></div>'
}
//点击创建公司按钮
var add_compan=(function(){
    $(".creatComp").click(function(){
        if(little_name==""){
            $.showtip("公司简称不能为空");
            setTimeout("$.hidetip()",2000);
        }else{
            type="1";
            creat_ajax(type);
        }
    });

})();
//点击创建并添加交易按钮
var add_companTrad=(function(){
    $(".creatTrad").click(function(){
        if(little_name.trim()==""){
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
        if(little_name.trim()==""){
            $.showtip("公司简称不能为空");
            setTimeout("$.hidetip()",2000);
        }else{
            type="3";
            creat_ajax(type);
        }
    });

})();
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
            name:little_name,
            fullname:full_name,
            ename:en_name,
            bask:listselectcode1,
            indu:induselectcode1,
            peoplelist:choicePeople(peopel),
            base_compnote_content:note,
            stagecode:stagecode,
            logintype:cookieopt.getlogintype()
        },
        dataType: "json",
        success: function(data){
            $.hideloading()
            if(data.message=="success"){
                $.showtip("保存成功");
                setTimeout(function(){
                    $.hidetip();
                    if(type==1){//创建公司
                        window.location.href="company_add.html?logintype="+cookieopt.getlogintype();
                    }else if(type==2){//创建交易
                        window.location.href="addTradeInfoinit.html?compcode="+data.code+"&logintype="+cookieopt.getlogintype();
                    }else if(type==3){//创建融资计划
                        creatRz(data.code,data.name,data.time);
                    }
                },2000);
            }else if(data.message=="repeat"){
                $.showtip("公司简称已存在");
                setTimeout("$.hidetip()",2000);
            }else{
                $.showtip("保存失败");
                setTimeout("$.hidetip()",2000);
            }

            $.hideloading();
        }
    });
};
var creatRz=function(a,b,c){
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

                    var thisnowdate=new Date(c.substring(0,10).replace(/-/g,"/"));
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
                    add_rz(a,b);
//                    inputlsit_edit.close();
                } else {
                    $.showtip("不能为空");
                    $("#text").val("");
                    setTimeout("$.hidetip()", 2000);
                    submitrz=0;
                }
        	}
            

        }
    });
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
                $.hideloading();//等待动画隐藏
                if(data.message=="success"){
                    $.showtip("保存成功");
                    setTimeout(function(){
                    	submitrz=0;
                        $.hidetip();
                        inputlsit_edit.close();
                        window.location.href="company_add.html?logintype="+cookieopt.getlogintype();
                    },2000);

                }else if(data.message=="failer"){
                    $.showtip("保存失败");
                    inputlsit_edit.close();
                    setTimeout("$.hidetip()",2000);
                	submitrz=0;

                }

            }

        });
    }
};
var checkPeople=function(a,b){
    if(b!="" && a.indexOf(b)>0){
        return false;
    }
    return true;
};