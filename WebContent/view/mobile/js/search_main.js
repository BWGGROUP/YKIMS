/**
 * Created by shbs-tp001 on 15-9-15.
 */
var outtime = 300;//搜索文本框变化时的延时时间
var timefunction;
var doajax = "";//判断上一次请求是否完全返回
$(function() {
	search_organization_tip.config();
	click_listen();
});
//投资机构搜模块索提示
var search_organization_tip = (function() {

	var config = function() {

        $(".search-input").keydown(function(e){
            var curkey= e.which;
            if(curkey==13){
                $(".search-btn").click();
            }
        });

		$(".search-input").bind("input propertychange", function() {
			if ($(this).val() == "") {
                cookSearch();

			} else {
				if (timefunction) {
					clearTimeout(timefunction);
				}
				timefunction = setTimeout(function() {
					if (doajax != "") {
						doajax.abort();//上一次请求作废
					}
					selecttip();
					outtime = 1000;
				}, outtime);
			}
		});

		$(".search-input").blur(function() {
			setTimeout(function() {
				$(".search_b_list").slideUp(200);
			}, 100);
		});

		$(".search-input").focus(function() {
			if ($(this).val().length > 0&& $(".search_b_list ul li").length > 0) {
				$(".search_b_list").slideDown(200);
			}else{
                cookSearch();
            }
		});
	};
	var selecttip = function() {
		if ($("[name='name']").val() != "") {
			$.showloading();

			doajax = $.ajax({
				async : true,
				dataType : 'json',
				type : 'post',
				data : {
					name : $("[name='name']").val().trim(),
					logintype : cookieopt.getlogintype()
				},
				url : "search.html",
				success : function(data) {
					$.hideloading();
					if (data.investmentlist.length > 0
							|| data.investorList.length > 0) {
						var html = template.compile(data_modle.tiplist)(data);
						$(".search_b_list").html(html);
						$(".search_b_list").slideDown(200);
						tipclick();
					} else {
						$(".search_b_list").hide();
					}
					doajax = "";
				}

			});
		}
		var tipclick = function() {
			$(".search_b_list ul li").unbind().bind("click", function() {
				$(".search-input").val($(this).children("span").html());
                saveCookie({name:$(this).children("span").html(),type:$(this).children("b").html(),href:$(this).attr("href")});
                window.location.href=$(this).attr("href")+"&type=search";
			});
		};

	};

    //cookie存储搜索历史
    var cookSearch=function(){
        var searchList=unescape(cookieopt.getcookie("searchList"));
        if(searchList!=null&& searchList!="null"&&searchList!=""){
            var data={searchList:eval(searchList)};
            var html = template.compile(data_modle.searchlist)(data);
            $(".search_b_list").html(html);
            $(".search_b_list").slideDown(200);
            cookClick();
        }else{
            $(".search_b_list").slideUp(200);//模糊查询下拉框收起
        }
    };

    //cookie数据单击事件
    var cookClick=function(){
        $(".search_b_list ul li").unbind().bind("click", function() {
            $(".search-input").val($(this).children("span").html());
            
            var type=$(this).children("b").html();
            if(type=="搜索") {
                var data = {};
                data.type = "text";
                data.name = $(this).children("span").html();
                sessionStorage.setItem("organization_search", JSON
                    .stringify(data));
                $("#form")[0].action = $(this).attr("href");
                $("#form").submit();
                $.hideloading();
            }else{
                window.location.href=$(this).attr("href");
            }

        });
    };

	return {
		config : config
	};
})();

function click_listen() {
	$(".search-btn").click(
			function() {
				$.showloading();
				var name = $("[name='name']").val();
				/*if (name != "") {*/
					var data = {};
					data.type = "text";
					data.searchtext = name;
                    //保存cookie
                    if(name!=""){
                    	saveCookie({name:name,type:'搜索',href:"gotoInvestmentList.html?logintype="
                            + cookieopt.getlogintype()});
                    }
					sessionStorage.setItem("organization_search", JSON.stringify(data));
					$("#form")[0].action = "gotoInvestmentList.html?logintype="
							+ cookieopt.getlogintype();
					$("#form").submit();
				/*}*/
				$.hideloading();
			});
	//a 连接加logintype
	$("[da-href]").unbind().bind("click", function() {
		var url = $(this).attr("da-href");
		if (url.indexOf("?") > 0) {
			url = url + "&logintype=" + cookieopt.getlogintype();
		} else {
			url = url + "?logintype=" + cookieopt.getlogintype();
		}
		window.location.href = url;
	});
}

//保存搜索历史记录
function saveCookie(cook){
    var cookdata = [];
    var searchList=unescape(cookieopt.getcookie("searchList"));
    if(searchList!=null&&searchList!="null"&& searchList!=""){
        cookdata=eval(searchList);
        var bool=true;
        for(var i=0;i<cookdata.length;i++){
            if(cookdata[i].name==cook.name){
                bool=false;
            }
        }
        if(bool){
            if(cookdata.length>9){
                cookdata.splice(0,1);
            }
            cookdata.push(cook);
            cookieopt.setcookie("searchList",JSON.stringify(cookdata));
        }
    }else{
        cookdata.push(cook);
        cookieopt.setcookie("searchList",JSON.stringify(cookdata));
    }
    
}

var data_modle = {
//	tiplist : '<ul>'
//			+ '<%for(var i=0;i<investmentlist.length;i++){%>'
//			+ '<a href="findInvestmentById.html?id=<%=investmentlist[i].code%>&logintype=<%=logintype()%>"><b><li><%=investmentlist[i].nameStr%></b><span><%=investmentlist[i].name%></span></li> </a>'
//			+ '<%}%>'
//			+ '<%for(var i=0;i<investorList.length;i++){%>'
//			+ '<a href="findInvestorDeatilByCode.html?code=<%=investorList[i].code%>&logintype=<%=logintype()%>"><li><b><%=investorList[i].nameStr%></b><span><%=investorList[i].name%></span></li> </a>'
//			+ '<%}%>' + '</ul>',
    tiplist : '<ul class="searchul">'
        + '<%for(var i=0;i<investmentlist.length;i++){%>'
        + '<li href="findInvestmentById.html?id=<%=investmentlist[i].code%>&logintype=<%=logintype()%>"><b><%=investmentlist[i].nameStr%></b><span><%=investmentlist[i].name%></span></li>'
        + '<%}%>'
        + '<%for(var i=0;i<investorList.length;i++){%>'
        + '<li href="findInvestorDeatilByCode.html?code=<%=investorList[i].code%>&logintype=<%=logintype()%>"><b><%=investorList[i].nameStr%></b><span><%=investorList[i].name%></span></li>'
        + '<%}%>' + '</ul>',
    searchlist:'<ul>'
        + '<%for(var i=0;i<searchList.length;i++){%>'
        + '<li href="<%=searchList[i].href%>"><b><%=searchList[i].type%></b><span><%=searchList[i].name%></span></li>'
        + '<%}%>' + '</ul>'
};
template.helper("logintype", function() {
	return cookieopt.getlogintype();
});