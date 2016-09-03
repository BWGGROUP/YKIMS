/**
 * Created by shbs-tp001 on 15-11-6.
 */
var optactive=0;
$(function(){
    $(".sub").click(function () {
        if($(".orpaw").val()==""){
            $.showtip("原密码不可为空");
            return;
        }if($(".paw").val()==""){
            $.showtip("密码不可为空");
            return;
        }
        if($(".paw").val().length<6){
            $.showtip("请输入至少6位密码");
            return;
        }
        if($(".besurepaw").val()==""){
            $.showtip("密码确认不可为空");
            return;
        }
        if($(".besurepaw").val()!=$(".paw").val()){
            $.showtip("两次密码输入不一致");
            return;
        }
        chageinfo();
    })

});

function chageinfo() {
    $.showloading();
    $.ajax({
        async: true,
        dataType: 'json',
        type: 'post',
        url: "changeuerinfo.html",
        data: {
            userid:$("button[code]").attr("code"),
            orpaw:$(".orpaw").val(),
            paw:$(".paw").val(),
            logintype: cookieopt.getlogintype()
        },
        success: function (json) {
            $.hideloading();
            if(json.message=="success"){
                $.showtip("修改成功","success");
                setTimeout(function(){
                    history.go(-1);
                },300)
            }else if(json.message=="nouser"){
                $.showtip("没有该用户信息","error");
            }else if(json.message=="orpaw_error"){
                $.showtip("原密码输入错误","error");
            }else{
                $.showtip("修改失败","error");
            }

        }
    });
}