/**
 * Created by shbs-tp001 on 15-9-10.
 */
var optactive=0;
var menuactive=0;
$(function () {

    click_lisetn();
    init.config();
});

function click_lisetn(){
$(".search-btn").click(function () {
    init.config();
});
    $(".add_link").click(function () {
        adduser.init();
    });

}


var init=(function(){
    var data={
        page:{pageCount:1}
    };

    function config() {

        data.page={pageCount:1};
        selecttable();
    }
    function selecttable(){
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "userbyname.html",
            data: {
                name:$(".search-input").val(),
                pageCount:data.page.pageCount,
                logintype: cookieopt.getlogintype()
            },
            success: function (json) {
                $.hideloading();
                data.page=json.page;
                if(json.message=="success"){
                    var html=template.compile(data_model.table)(json);
                    $(".info_content").html(html);
                    if(data.page.totalCount>0){
                     	$(".totalSize").show();
                     	$(".totalSize").html("<li>共"+data.page.totalCount+"条</li>");
                     }else{
                     	$(".totalSize").hide();
                     }
                    $("#pagination1").show();
                }else if(json.message=="nomore"){
                    $(".info_content").html("<tr colspan='8'>暂无数据</tr>");
                    $(".info_content").html(html);
                    $(".totalSize").hide();
                    $("#pagination1").hide();
                }else {
                    $.showtip("数据加载错误","error");
                    $("#pagination1").hide();
                }
                jqPagination();
                click();
            }
        });
    }

    function jqPagination () {
        $.jqPaginator('#pagination1', {
            totalPages: data.page.totalPage,
            visiblePages: 5,
            currentPage: data.page.pageCount,
            onPageChange: function (num, type) {
                if(data.page.pageCount!=num){
                    data.page.pageCount=num;
                    selecttable();
                }
            }
        });


    }

    function click() {
        $(".delete").unbind().bind("click", function () {
            var code=$(this).attr("c");
            layer.confirm('您确定要删除该条记录吗？', {
                title:"提示",
                icon: 0,
                btn: ['确定','取消'] //按钮
            }, function(index){
                deleteuser(code);
                layer.close(index);
            }, function(index){
                layer.close(index);
            });
        });
        $(".companyname").unbind().bind("click", function () {
            var code=$(this).attr("c");
            window.location.href="admin/employee_info.html?logintype="+cookieopt.getlogintype()+"&code="+code;
        });
    }

    function deleteuser(code) {
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "deleteuser.html",
            data: {
                code:code,
                logintype: cookieopt.getlogintype()
            },
            success: function (json) {
                $.hideloading();
                if(json.message=="success"){
                    $.showtip("删除成功","success");
                    selecttable();
                }else if(json.message=="loginuser"){
                    $.showtip("不可删除自己","error");
                }else{
                    $.showtip("删除失败","error");
                }

            }
        });
    }

    return{
        config:config,
        selecttable:  selecttable
    }
})();

var adduser=(function () {
    var data={};
    function addinit() {
        layer.open({
            title:"添加员工",
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: "420px", //宽高
            content: '<div class="addfrom"><ul class="inputlist"><li><span class="lable">姓名:</span><span class="in"><input value="" id="name" type="text" class="inputdef"></span></li><li><span class="lable">英文名:</span><span class="in"><input value="" id="en_name" type="text" class="inputdef"></span></li><li><span class="lable">邮箱:</span><span class="in"><input value="" id="email" type="text" class="inputdef"></span></li><li><span class="lable">微信:</span><span class="in"><input value="" id="weichat" type="text" class="inputdef"></span></li><li><span class="lable">手机:</span><span class="in"><input value="" id="phone" type="text" class="inputdef"></span></li></ul><div class="btn-box"><button class="btn btn-default save">保存</button></div></div>'
        });

        $(".save").unbind().bind("click",function(){
            addajax();
        });
    }

    function addajax(){
        var check_phone= /^1\d{10}$/;//手机格式验证
        var check_email=/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/ ;//邮箱验证
        if($("#name").val().trim()==""){
            $.showtip("姓名不可为空");
            return;
        }
        if($("#email").val().trim()==""){
            $.showtip("邮箱不可为空");
            return;
        }
        if(!check_email.test($("#email").val().trim())){
            $.showtip("请输入有效的邮箱");
            return;
        }
        if($("#phone").val().trim()!=""&&!check_phone.test($("#phone").val().trim())){
            $.showtip("请输入有效的手机号码");
            return;
        }
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "useradd.html",
            data: {
                name:$("#name").val(),
                en_name:$("#en_name").val(),
                email:$("#email").val().trim(),
                phone:$("#phone").val().trim(),
                weichat:$("#weichat").val(),
                logintype: cookieopt.getlogintype()
            },
            success: function (json) {
                $.hideloading();
                if(json.message=="success"){
                    layer.closeAll('page');
                    layer.confirm('添加成功，用户密码已发送至所添加用户的邮箱中，请注意查收', {
                        title:"提示",
                        icon: 1,
                        btn: ['确定'] //按钮
                    }, function(index){
                        layer.close(index);
                        init.config();
                    });

                }else if(json.message=="email_exist"){
                    $.showtip("该邮箱用户已存在","error");
                }else{
                    $.showtip("添加失败","error");
                }

            }
        });
    }
    return{
        init:addinit
    }
})();
var data_model={
    table:'<% for (var i=0;i<list.length;i++ ){' +
        'list[i].sys_user_cmnum=list[i].sys_user_cmnum==""?0:list[i].sys_user_cmnum;' +
        'list[i].sys_user_updreconum=list[i].sys_user_updreconum==""?0:list[i].sys_user_updreconum;' +
        'list[i].sys_user_addmnum=list[i].sys_user_addmnum==""?0:list[i].sys_user_addmnum;' +
        'list[i].sys_user_addreconum=list[i].sys_user_addreconum==""?0:list[i].sys_user_addreconum;' +
        '%>' +
        '<tr>' +
        '<td><span class="companyname" c="<%=list[i].sys_user_code%>"><%=list[i].sys_user_name%></span></td>' +
        '<td><%=list[i].sys_user_email%></td>' +
        '<td><%=list[i].sys_user_wechatnum%></td>' +
        '<td><%=list[i].sys_user_cmnum%></td>' +
        '<td><%=list[i].sys_user_addmnum%></td>' +
        '<td><%=list[i].sys_user_updreconum%></td>' +
        '<td><%=list[i].sys_user_addreconum%></td>' +
        '<td><a class="delete" c="<%=list[i].sys_user_code%>">删除</a></td>' +
        '</tr>' +
        '<%}%>'
};