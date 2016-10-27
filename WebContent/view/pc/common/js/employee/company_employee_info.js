/**
 * Created by shbs-tp001 on 15-9-10.
 */
var optactive=0;
var menuactive=0;
$(function () {

init.config()
});

var init=(function () {
    var data={};
    function config(){
        data["姓名"]=$(".name-input").val();
        data["英文名"]=$(".en_name-input").val();
        data["邮箱"]=$(".email-input").val();
        data["手机号"]=$(".phone-input").val();
        data["微信号"]=$(".weichat-input").val();

        configshow();

    }

    function configshow() {

        $(".name").html(data["姓名"]);
        $(".en_name").html(data["英文名"]);
        $(".email").html(data["邮箱"]);
        $(".phone").html( data["手机号"]);
        $(".weichat").html(data["微信号"]);
        $(".name-input").val(data["姓名"]);
        $(".en_name-input").val(data["英文名"]);
        $(".email-input").val(data["邮箱"]);
        $(".phone-input").val(data["手机号"]);
        $(".weichat-input").val(data["微信号"]);
        if(data["英文名"]==""){
            $(".en_name").html("(未填写)");
        }
        if(data["手机号"]==""){
            $(".phone").html("(未填写)");
        }
        if(data["微信号"]==""){
            $(".weichat").html("(未填写)");
        }
        click();
    }

    function click() {
        $(".edittext").unbind().bind("click", function (e) {
            $(".edittext").show();
            $(".input").hide();
            $this=$(this);
            $this.hide();
            $this.next().show();

            $(window).bind("click", function () {
                $this.show();
                $this.next().hide();
                $(window).unbind("click");
                configshow();
            });
            $("input").bind("click", function (e) {
                e.stopPropagation();
            });
            e.stopPropagation();
        });
        $(".save-name").unbind().bind("click", function (e) {
            if($(".name-input").val()==""){
                $.showtip("姓名不可为空");
                $(".name-input").val(data["姓名"]);
                e.stopPropagation();
                return;
            }
            if($(".name-input").val()==data["姓名"]){
                $.showtip("姓名未做更改");
                e.stopPropagation();
                return;
            }
            editinfo();
            e.stopPropagation();
        });
        $(".save-en_name").unbind().bind("click", function (e) {
            if($(".en_name-input").val()==data["英文名"]){
                $.showtip("英文名未做更改");
                e.stopPropagation();
                return;
            }
            editinfo();
            e.stopPropagation();
        });
        $(".save-email").unbind().bind("click", function (e) {
            if($(".email-input").val()==""){
                $.showtip("邮箱不可为空");
                $(".email-input").val(data["邮箱"]);
                e.stopPropagation();
                return;
            }
            if($(".email-input").val()==data["邮箱"]){
                $.showtip("邮箱未做更改");
                e.stopPropagation();
                return;
            }

            var check_email=/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/ ;//邮箱验证
            if(!check_email.test($(".email-input").val())){
                $.showtip("请输入有效的邮箱");
                e.stopPropagation();
                return;
            }
            editinfo();
            e.stopPropagation();
        });
        $(".save-phone").unbind().bind("click", function (e) {
            if($(".phone-input").val()==data["手机号"]){
                $.showtip("手机号未做更改");
                e.stopPropagation();
                return;
            }
            var check_phone= /^1\d{10}$/;//手机格式验证
            if(!check_phone.test($(".phone-input").val())){
                $.showtip("请输入有效的手机号");
                e.stopPropagation();
                return;
            }
            editinfo();
            e.stopPropagation();
        });
        $(".save-weichat").unbind().bind("click", function (e) {
            if($(".weichat-input").val()==data["微信号"]){
                $.showtip("微信号未做更改");
                e.stopPropagation();
                return;
            }
            editinfo();
            e.stopPropagation();
        });
        $(".forgetpaw").unbind().bind("click", function (e) {
            layer.confirm('您确定要重置该用户登录密码吗？', {
                title:"提示",
                icon: 0,
                btn: ['确定','取消'] //按钮
            }, function(index){
                $.showloading();
                $.ajax({
                    async: true,
                    dataType: 'json',
                    type: 'post',
                    url:"forgetpassword.html",
                    data:{
                        emali:data["邮箱"],
                        logintype:cookieopt.getlogintype()
                    },
                    success: function (json) {
                        $.hideloading();
                        var message=json.message;
                        if(message=="success"){
                            layer.confirm('密码重置成功，用户密码已发送至'+data["邮箱"]+'邮箱中，请注意查收。', {
                                title:"提示",
                                icon: 1,
                                btn: ['确定'] //按钮
                            }, function(index){
                                layer.close(index);
                            });

                        }else if(message=="nouser"){
                            $.showtip("没有该用户信息","error");
                        }else{
                            $.showtip("密码重置失败","error");
                        }

                    }
                });
                layer.close(index);
            }, function(index){
                layer.close(index);
            });

        })

    }

    function editinfo() {
        var newdata={};
        newdata["姓名"]=$(".name-input").val();
        newdata["英文名"]=$(".en_name-input").val();
        newdata["邮箱"]=$(".email-input").val();
        newdata["手机号"]=$(".phone-input").val();
        newdata["微信号"]=$(".weichat-input").val();
        $.showloading();
        $.ajax({
            async: true,
            dataType: 'json',
            type: 'post',
            url: "admin/changeuerbaseinfo.html",
            data: {
                code:code,
                name:newdata["姓名"],
                email:newdata["邮箱"],
                en_name:newdata["英文名"],
                phone:newdata["手机号"],
                weichat:newdata["微信号"],
                newdata:JSON.stringify(newdata),
                olddata:JSON.stringify(data),
                logintype: cookieopt.getlogintype()
            },
            success: function (json) {
                $.hideloading();
                if(json.message=="success"){
                    $.showtip("修改成功","success");
                    data["姓名"]=newdata["姓名"];
                    data["邮箱"]=newdata["邮箱"];
                    data["英文名"]=newdata["英文名"];
                    data["手机号"]=newdata["手机号"];
                    data["微信号"]=newdata["微信号"];
                    configshow();
                }

            }
        });
    }
    return{
        config:config
    }
})();