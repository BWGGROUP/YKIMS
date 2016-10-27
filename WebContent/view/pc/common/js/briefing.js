/**
 * Created by shbs on 26/10/2016.
 */
var optactive=0;
var menuactive=6;

$(function () {
init.select2();
    clicklisten();
});

var init=(function () {
    var opt={};
    function  select2() {
        opt.$orgainselect=$("#select_org").select2({
            placeholder:"请选择机构",//文本框的提示信息
            minimumInputLength:0,   //至少输入n个字符，才去加载数据
            allowClear: true,  //是否允许用户清除文本信息
            ajax:{
                url:"findInvestmentNameListByName.html",
                dataType:"json",
                cache: true,
                type:"post",
                delay: 250,//加载延迟
                data: function (params) {
                    return{
                        name:(params.term||"").replace(/(^\s*)|(\s*$)/g,''),
                        pageSize:CommonVariable().SELSECLIMIT,
                        pageCount:"1",
                        logintype:cookieopt.getlogintype(),
                        type:"1"
                    };
                },
                processResults: function (data, page) {
                    for(var i=0;i<data.list.length;i++){
                        data.list[i].id=data.list[i].base_investment_code;
                        data.list[i].text=data.list[i].base_investment_name;
                    }
                    return {
                        results: data.list//返回结构list值
                    };
                }

            },
            escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
            templateResult: formatRepo // 格式化返回值 显示再下拉类表中
        });

    }
    function formatRepo (repo) {
        return repo.text;
    }
    function selectdata() {
        var map={};
        var o=opt.$orgainselect.select2("data");
        var olist=[];
        for(var i=0;i< o.length;i++){
            if(!o[i].selected){
                olist.push(o[i]);
            }
        }
        map.olist=olist;
        return map;
    }
    return {
        select2:select2,
        selectdata:selectdata
    }
})();

function clicklisten() {
    $("#PDF").click(function () {
    	var data=init.selectdata();
    	if(data.olist.length>0){
    		var code="";
            for(var i=0;i<data.olist.length;i++){
            	code+=data.olist[i].base_investment_code+",";
            }
            
            code=code.substring(0,code.length-1);
            window.open("exportpdf.html?logintype=PC&code="+code);
    	}
    	
        
    });
    $("#excel").click(function () {
    	var data=init.selectdata();
    	if(data.olist.length>0){
    		var code="";
            for(var i=0;i<data.olist.length;i++){
            	code+=data.olist[i].base_investment_code+",";
            }
            
            code=code.substring(0,code.length-1);
            $("#frame")[0].src="exportexcel.html?logintype=PC&code="+code;
    	}
    })
}


