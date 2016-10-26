/**
 * Created by shbs-tp001 on 15-10-12.
 */
$(function () {
    cookieopt.setcookie("logintype",null);
    
    /**dwj 实现功能，保存用户的登录信息到cookie中。当登录页面被打开时，就查询cookie**/
	var userNameValue = unescape(cookieopt.getcookie("localusername"));
	if(userNameValue!=null && userNameValue!="null" && userNameValue!=""){
		$(".email").val(userNameValue);
		document.getElementById("jiluzhanghao").checked = "true";
	}
	
    init();
    $(".sub").bind("click",function(){
        if($(".email").val()==""){
            $.showtip("请填写邮箱");
            setTimeout(function(){
                $.hidetip();
            },2000);
        }else if($(".pass").val()==""){
            $.showtip("请填写密码");
            setTimeout(function(){
                $.hidetip();
            },2000);
        }else if($(".yanzhengma").val()==""){
            $.showtip("请填写验证码");
            setTimeout(function(){
                $.hidetip();
            },2000);
        }else{
        	/**dwj 添加cookie */
        	var check=document.getElementById("jiluzhanghao");
        	if(check.checked){
        		cookieopt.setcookie("localusername",$(".email").val());
        	}
        	/** */
            $.showloading();
            $.ajax({
                async: true,
                dataType: 'json',
                type: 'post',
                url:"login.html",
                data:{
                    username:$(".email").val(),
                    password:$(".pass").val(),
                    Verifycode:$(".yanzhengma").val(),
                    WCuserid:WUI,
                    logintype:cookieopt.getlogintype()
                },
                success: function (data) {
                    var message=data.message;
                    if(message=="codeerror"){
                        $.showtip("验证码输入错误");
                        changeVerify_code();
                    }else if(message=="pass_error"){
                        $.showtip("密码输入错误");
                    }else if(message=="name_erro"){
                        $.showtip("邮箱输入错误");
                    }else if(message=="success"){
                        $.showtip("登录成功");
                        location.reload();
                    }else if(message=="bind_success"){
                        $.showtip("绑定成功");
                        location.reload();
                    }else{
                        $.showtip("数据请求失败");
                    }
                    setTimeout(function(){
                        $.hidetip();
                    },2000);
                    $.hideloading();
                }
            });
        }
    });
});
function init() {
    if(WUI!=""){
        $(".sub").html("绑定账户");
    }
    $(".Verify_code").bind("click",function(){
        changeVerify_code();
    });
    $(".forgetpaw").click(function () {
        inputlsit_edit.config({
            title:"找回密码",//弹层标题
            html:true,//是否以html显示
            besurebtn:"确定",//确定按钮文字
            htmltext:'<div class="tiptext">请填写邮箱，我们将为您重置密码，并发送至您的邮箱。</div><li><span class="lable">邮箱:</span><span class="in" ><input  id="forgetpaw" class="inputdef" placeholder="用户邮箱" style="width: 88%"/></span></li>',
            submit: function () {
                chagepaw();
            },//点击确定按钮回调方法
            canmit: function () {},//点击取消按钮回调方法
            complete: function () {}//效果加载完成回调方法
        });
    });
}

function changeVerify_code(){
    $(".Verify_code").attr("src","Verify_code.html?"+Math.random());
}
function chagepaw() {
    if($("#forgetpaw").val()==""){
        $.showtip("用户邮箱不能为空");
        setTimeout(function () {
            $.hidetip();
        },2000);
        return;
    }
    $.showloading();
    $.ajax({
        async: true,
        dataType: 'json',
        type: 'post',
        url:"forgetpassword.html",
        data:{
            emali:$("#forgetpaw").val(),
            logintype:cookieopt.getlogintype()
        },
        success: function (data) {
            $.hideloading();
            var message=data.message;
            if(message=="success"){
                alert("您的密码已发送到您的用户邮箱");
                inputlsit_edit.close();
            }else if(message=="nouser"){
                $.showtip("没有该用户信息");
            }else{
                $.showtip("密码重置失败");
            }
            setTimeout(function () {
                $.hidetip();
            },2500);
        }
    });
}