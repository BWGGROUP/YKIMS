/**
 * Created by shbs-tp001 on 15-11-6.
 */
var name;
var en_name;
$(function () {
    name=$(".name").val();
    en_name=$(".en_name").val();

    $("button[code]").click(function () {
        if(name==$(".name").val()&& en_name==$(".en_name").val()){
            $.showtip("内容未做更改");
            setTimeout(function(){
                $.hidetip();
            },2000);
            return;
        }
        changeinfo()
    })
});

function changeinfo() {
    $.showloading();
    $.ajax({
        async: true,
        dataType: 'json',
        type: 'post',
        url: "changeuerinfo.html",
        data: {
            userid:$("button[code]").attr("code"),
            en_name:$(".en_name").val(),
            name:$(".name").val(),
            logintype: cookieopt.getlogintype()
        },
        success: function (json) {
            $.hideloading();
            if(json.message=="success"){
                $.showtip("修改成功");
                setTimeout(function(){
                    history.go(-1);
                },300)
            }else if(json.message=="nouser"){
                $.showtip("没有该用户信息");

            }else{
                $.showtip("修改失败");
            }
            setTimeout(function(){
                $.hidetip();
            },2000);
        }
    });
}