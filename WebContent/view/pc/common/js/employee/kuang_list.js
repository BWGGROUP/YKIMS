/**
 * Created by shbs-tp001 on 15-10-30.
 */
var optactive=0;
var menuactive=1;
$(function () {


    selecttable.select();
    $(".btn-default.search-btn").click(function () {
        selecttable.select();
    });
    $(".btn-default.add-btn").click(function () {
        window.location.href="admin/kuangadd.html?logintype="+cookieopt.getlogintype();
    });
});

var selecttable=(function () {
    var data={page:{
        pageCount:1

    }};
    var tabledata={};
    function select() {
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "admin/kuanglistbyname.html",
            data: {
                name:$(".search-input").val(),
                pageCount:data.page.pageCount,
                logintype: cookieopt.getlogintype()
            },
            success: function (json) {
                $.hideloading();
                tabledata=json;
                data.page=json.page;
                if(json.message=="success"){
                	 if(data.page.totalCount>0){
                     	$(".totalSize").show();
                     	$(".totalSize").html("<li>共"+data.page.totalCount+"条</li>");
                     }else{
                     	$(".totalSize").hide();
                     }
                    var html=template.compile(model_data.tablelist)(json);
                    $("#tablecontent").html(html);
                    $("#pagination1").show();
                }else if(json.message=="nomore"){
                    var html='<tr><td  colspan="4" style="width:100%;">暂无数据</td></tr>';
                    $("#tablecontent").html(html);
                    $(".totalSize").hide();
                    $("#pagination1").hide();
                }else {
                    $.showtip("数据加载错误","error");
                    $("#pagination1").hide();
                }

                jqPagination ();
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
                    select();
                }

            }
        });


    }
    function deletekuang(code,name){
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "admin/kuangdelete.html",
            data: {
                code:code,
                name:name,
                logintype: cookieopt.getlogintype()
            },
            success: function (json) {
                $.hideloading();
                if(json.message=="success"){
                    $.showtip("删除成功","success");
                    select();
                }else{
                    $.showtip("删除失败","error");
                }
            }
        });
    }
    function click() {
        $(".delete").unbind().bind("click",function(){
            var code=$(this).attr("c");
            var name=$(this).attr("name");
            if(code== new CommonVariable().SUPERKCODE 
            		|| code == new CommonVariable().SUPERMEET){
                layer.confirm('该条记录不可删除！', {
                    title:"提示",
                    icon: 0,
                    btn: ['确定'] //按钮
                }, function(index){
                    layer.close(index);
                });
            return;
            }
            layer.confirm('您确定要删除该条记录吗？', {
                title:"提示",
                icon: 0,
                btn: ['确定','取消'] //按钮
            }, function(index){
                    deletekuang(code,name);
                layer.close(index);
            }, function(index){
                layer.close(index);
            });


        });
        $(".companyname").unbind().bind("click",function(){
            var code=$(this).attr("c");
            window.location.href="admin/kuanginfo.html?logintype="+cookieopt.getlogintype()+"&code="+code;
        });
    }
    return {
        select:select
    };
})();

var model_data={
    tablelist: '<% for (var i=0;i<list.length;i++ ){%>' +
        '<tr>' +
        '<td><span class="companyname" c="<%=list[i].sys_wad_code%>" ><%=list[i].sys_wad_name%></span></td>' +
        '<td><%=list[i].sys_juri_name%></td>' +
        '<td><%=list[i].sys_user_name%></td>' +
        '<td><a c="<%=list[i].sys_wad_code%>" name="<%=list[i].sys_wad_name%>" class="delete">删除 </a></td>' +
        '</tr>' +
        '<%}%>'
};

function addkuang(name){
    $.showloading();
    $.ajax({
        async: true,
        dataType: 'json',
        type: 'post',
        url: "admin/kuangadd.html",
        data: {
            name:name,
            logintype: cookieopt.getlogintype()
        },
        success: function (json) {
            $.hideloading();
            if(json.message=="success"){
                $.showloading("保存成功","success");
                selecttable.select();
            }else{
                $.showloading("保存失败","error");
            }
        }
    });
}