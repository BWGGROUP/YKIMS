/**
 * Created by shbs-tp001 on 15-9-28.
 */
var optactive=0;
var menuactive=1;
var newdata=[];
$(function () {
    click();
});

function click() {
	$("input[name=checkbox]").unbind().bind('click',function(){
		if($(this).is(":checked")==true
				&&$(this).attr("code")=="JURI-FUND"){
			if(!$("input[code=JURI-BUES]").is(":checked")){
				$("input[code=JURI-BUES]").prop("checked",'true');
			}
			
		}
		
		if($(this).attr("code")=="JURI-BUES"){
			if($("input[code=JURI-BUES]").is(":checked")){
				$("input[code=JURI-BUES]").prop("checked",true);
			}else{
				$("input[code=JURI-BUES]").prop("checked",false);
				if(!$("input[code=JURI-BACKMANAGE]").is(":checked")){
					$("input[code=JURI-FUND]").prop("checked",false);
				}
				
			}
			
		}
	});
	
    $('.add').click(function() {
        //获取选中的选项，删除并追加给对方
        $('#select2 option:selected').appendTo('#select1');
    });
    $('.remove').click(function() {
        $('#select1 option:selected').appendTo('#select2');
    });
    //全部移到左边
    $('.add_all').click(function() {
        //获取全部的选项,删除并追加给对方
        $('#select2 option').appendTo('#select1');
    });
    //全部移到右边
    $('.remove_all').click(function() {
        $('#select1 option').appendTo('#select2');
    });

    //双击选项
    $('#select1').dblclick(function(){ //绑定双击事件
        //获取全部的选项,删除并追加给对方
        $("option:selected",this).appendTo('#select2'); //追加给对方
    });
    //双击选项
    $('#select2').dblclick(function(){
        $("option:selected",this).appendTo('#select1');
    });
    $(".submit").click(function () {
        submitinfo();
    });
}
function initdata() {

}


function submitinfo() {
    var quxlist=[];
    var peolist=[];
    var qxname=[];
    var peoname=[];
    $("input[name='checkbox']:checked").each(function(){
        quxlist.push($(this).attr("code"));
        qxname.push($(this).next().html());
    });
    $("#select1 option").each(function(){
        peolist.push($(this).attr("code"));
        peoname.push($(this).html());
    });
    newdata.push([$(".con.name").html()]);
    newdata.push(qxname);
    newdata.push(peoname);
    if($(".name-input").val().trim()==""){
        $.showtip("筐名称不可为空");
        return;
    }
    if(quxlist.length==0){
        $.showtip("请选择至少一种权限");
        return;
    }
    if(peolist.length==0){
        $.showtip("请选择至少一添加一个员工");
        return;
    }
    $.showloading();
    $.ajax({
        async: true,
        dataType: 'json',
        type: 'post',
        url: "admin/kuangaddinfo.html",
        data: {
            quxlist:JSON.stringify(quxlist),
            peolist:JSON.stringify(peolist),
            newdata:JSON.stringify(newdata),
            name:$(".name-input").val(),
            logintype: cookieopt.getlogintype()
        },
        success: function (json) {
            $.hideloading();
            if(json.message=="success"){
                $.showtip("保存成功","success");
                setTimeout(function () {
                    window.location.href="admin/kuang.html?logintype=PC"
                },300);

            }else if(json.message=="exist"){
                $.showtip("该名称已存在","error");
            }else{
                $.showtip("保存失败","error");
            }

        },
        complete:function(){
            initdata();
        }
    });
}