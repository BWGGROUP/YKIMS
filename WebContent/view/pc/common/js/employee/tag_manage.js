/**
 * Created by shbs-tp001 on 15-9-28.
 */
var optactive=0;
var menuactive=2;
$(function () {

    tag_edite.config();
});

var tag_edite=(function () {
    var data={};
    
    var rending=function(){
    	var jdata={list:data.list};
    	var html=template.compile(data_model.list)(jdata);
        $(".edit-tip-box1").html(html);
        indexClick();
        click();
    };
    
    var config=function(){

        data.$eventSelect=$('.txtInfo').select2({
            language : 'zh-CN',
            placeholder:"选择标签"
        });
        data.$eventSelect.on("select2:select", function (e) {
            if(data.selectid!=e.params.data.id){
                data.selectid=e.params.data.id;
                selectlist();
                if( data.selectid=="Lable-currency"){
                    $(".addbox").hide();
                }else{
                    $(".addbox").show();
                }
            }


        });

    };
    var click= function () {
        $(".edit").unbind().bind("click",function () {
            $(".tipg .action").addClass("hidden");
            $(this).parents(".tipg").find(".tipg-name").addClass("hidden");
            $(this).parents(".tipg").find(".group-edit").removeClass("hidden")
        });

        $(".btn-llt").unbind().bind("click",function () {

            if($(this).prev().val().trim()==""){
                $.showtip("内容不可为空");
                $(this).prev().val($(this).parent().prev().html());
                return;
            }
            if($(this).parent().prev().html()== $(this).prev().val()){
                $.showtip("内容未做更改");
            }else{
                edittag($(this).attr("btndata-i"),$(this).prev().val());
            }

            $(".tipg .action").removeClass("hidden");
            $(this).parents(".tipg").find(".tipg-name").removeClass("hidden");
            $(this).parents(".tipg").find(".group-edit").addClass("hidden")
        });
        $(".btn-add").unbind().bind("click", function () {
            if($(".add_input").val().trim()==""){
                $.showtip("内容不可为空");
                return;
            }
            addtag();
        });
    };

    function selectlist() {
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "admin/tag_managelist.html",
            data: {
                code:data.selectid,
                logintype: cookieopt.getlogintype()
            },
            success: function (json) {
                $.hideloading();
                data.list=json.list;
                if(json.message=="success"){
                	rending();
                }else{
                    $.showtip("查询失败","error");
                }
            }
        });
    }
    
    var indexClick=function(){
    	$(".addindexClick").unbind().bind("click",function(){
    		var elcode=$(this).attr("up-code");
            $.showloading();
            $.ajax({
                async: true,
                dataType: 'json',
                type: 'post',
                url: "admin/updateLabelIndex.html",
                data: {
                	labelcode:data.selectid,
                    elcode:elcode,
                    logintype: cookieopt.getlogintype()
                },
                success: function (json) {
                    $.hideloading();
                    if(json.message=="success"){
                    	data.list=json.list;
                    	rending();
                    }else{
                        $.showtip("修改失败","error");
                    }
                }
            });
        
    	});
    };

    function edittag(code,name) {
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "admin/edittag_manage.html",
            data: {
                code:code,
                name:name,
                logintype: cookieopt.getlogintype()
            },
            success: function (json) {
                $.hideloading();
                data.list=json.list;
                if(json.message=="success"){
                    $.showtip("保存成功","success");
                    selectlist();
                }else if(json.message=="nochange"){
                    $.showtip("未做更改");
                }else{
                    $.showtip("保存失败","error");
                }
            }
        });
    }

    function addtag() {
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "admin/add_tag.html",
            data: {
                code:data.selectid,
                name:$(".add_input").val().trim(),
                logintype: cookieopt.getlogintype()
            },
            success: function (json) {
                $.hideloading();
                data.list=json.list;
                if(json.message=="success"){
                    $.showtip("保存成功","success");
                    selectlist();
                    $(".add_input").val("");
                }else if(json.message=="nameexist"){
                    $.showtip("该名称标签已存在","error");
                }else{
                    $.showtip("保存失败","error");
                }
            }
        });
    }
    return{
        config:config
    };
})();

var data_model={
  list:  '<% for (var i=0;i<list.length;i++ ){%>' +
      ' <li>' +
      '<div class="tipg">' +
      '<span class="tip-detail">' +
      '<span class="tipg-name"><%=list[i].sys_labelelement_name%></span>' +
//'<div class="group-edit hidden">' +
//      '<input type="text" name="groupedit" value="<%=list[i].sys_labelelement_name%>" class="fn-input"><button class="btn btn-llt" btndata-i="<%=list[i].sys_labelelement_code%>">OK</button></div>' +
//'</span>' +
//      '<span class="action"><a class="edit"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>' +
      '</span><span class="spanright">'+
      '<% if(i>0){%>'+
      '<span class="glyphicon glyphicon-chevron-up addindexClick" up-code="<%=list[i].sys_labelelement_code%>" aria-hidden="true"></span>'+
      '<%}%>'+
      '</span>' +
      '</div>' +
      '</li>' +
      '<%}%>'
};