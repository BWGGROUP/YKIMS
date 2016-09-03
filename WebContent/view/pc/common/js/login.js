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
	
    
    document.onkeydown=keyDownSearch;

    function keyDownSearch(e) {
        // 兼容FF和IE和Opera
        var theEvent = e || window.event;
        var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
        if (code == 13) {
            $(".sub").click();
            return false;
        }
        return true;
    }
    $(".Verify_code").bind("click",function(){
        changeVerify_code();
    });
    $(".sub").bind("click",function(){
        if($(".email").val()==""){
            $.showtip("请填写邮箱","error");
        }else if($(".pass").val()==""){
            $.showtip("请填写密码","error");
        }else if($(".yanzhengma").val()==""){
            $.showtip("请填写验证码","error");
        }else{
        	/**dwj 添加cookie */
        	var check=document.getElementById("jiluzhanghao");
        	if(check.checked){
        		cookieopt.setcookie("localusername",$(".email").val());
        	}
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
                    logintype:cookieopt.getlogintype()
                },
                success: function (data) {
                    $.hideloading();
                    var message=data.message;
                    if(message=="codeerror"){
                        $.showtip("验证码输入错误","error");
                        changeVerify_code();
                    }else if(message=="pass_error"){
                        $.showtip("密码输入错误","error");
                    }else if(message=="name_erro"){
                        $.showtip("邮箱输入错误","error");
                    }else if(message=="success"){
                        $.showtip("登录成功","success");
                        location.reload();
                    }else{
                        $.showtip("数据请求失败","error");
                    }

                }
            });
        }
    });

    $(".forgetpaw").click(function () {
        layer.confirm('<div class="addfrom"><div class="tiptext">请填写邮箱，我们将为您重置密码，并发送至您的邮箱。</div><ul class="inputlist"><li><span class="lable">邮箱:</span><span class="in"><input  id="email" type="text" class="inputdef" placeholder="用户邮箱"></span></li></ul></div>', {
            title:"提示",
            area: ['400px', '260px'], //宽高
            btn: ['确定','取消'] //按钮
        }, function(index){
            forgetpaw();

        }, function(index){
            layer.close(index);
        });
    });
});


function changeVerify_code(){
    $(".Verify_code").attr("src","Verify_code.html?"+Math.random());
}
function forgetpaw() {
    if($("#email").val()==""){
        $.showtip("邮箱不能为空");
        return;
    }

    $.showloading();
    $.ajax({
        async: true,
        dataType: 'json',
        type: 'post',
        url:"forgetpassword.html",
        data:{
          emali:$("#email").val(),
          logintype:cookieopt.getlogintype()
        },
        success: function (data) {
            $.hideloading();
            var message=data.message;
            if(message=="success"){
                $.showtip("您的密码已发送到您的用户邮箱","success");
                layer.closeAll();
            }else if(message=="nouser"){
                $.showtip("没有该用户信息","error");
            }else{
                $.showtip("密码重置失败","error");
            }

        }
    });
}