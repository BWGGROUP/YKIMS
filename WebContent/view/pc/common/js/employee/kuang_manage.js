/**
 * Created by shbs-tp001 on 15-9-28.
 */
var optactive=0;
var menuactive=1;
var olddata=[];
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
    if(code!=new CommonVariable().SUPERKCODE
    		&& code!=new CommonVariable().SUPERMEET){
        $(".con.name").click(function () {
            $(this).hide();
            $(".name-input").show();
            $(".name-input").val( $(this).html());
            $(".name-input").focus();
        });
        $(".name-input").blur(function () {
            if($(this).val()!=""){
                $(".con.name").html($(this).val());
            }
            $(this).hide();
            $(".con.name").show();
        });
    }else{
        $(".con.name").removeClass("name");
    }


    $(".submit").click(function () {
        submitinfo();
    });
    initdata();
}
function initdata() {
    $(".name-input").val( $(".con.name").html());
    newdata=[];
    var name=[$(".con.name").html()];

    var qx=[];
    $("input[name='checkbox']:checked").each(function(){
        qx.push($(this).next().html());
    });
    var peo=[];
    $("#select1 option").each(function(){
        peo.push($(this).html());
    });
    olddata.push(name);
    olddata.push(qx);
    olddata.push(peo);
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


    if(quxlist.length==0&&code!=new CommonVariable().SUPERKCODE){
        $.showtip("请选择至少一种权限");
        return;
    }
    if(peolist.length==0&&code!=new CommonVariable().SUPERKCODE){
        $.showtip("请选择至少一添加一个员工");
        return;
    }
    newdata.push([$(".name-input").val()]);
    newdata.push(qxname);
    newdata.push(peoname);
    $.showloading();
    $.ajax({
        async: true,
        dataType: 'json',
        type: 'post',
        url: "admin/kuangsubmitinfo.html",
        data: {
            quxlist:JSON.stringify(quxlist),
            peolist:JSON.stringify(peolist),
            code:code,
            olddata:JSON.stringify(olddata),
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

            }else{
                $.showtip("保存失败","error");
            }

        },
        complete:function(){
            initdata();
        }
    });
}