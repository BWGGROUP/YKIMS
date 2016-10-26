/**
 * Created by shbs-tp001 on 15-9-14.
 */
var timefunction;
var outtime=300;//搜索文本框变化时的延时时间
var doajax="";//判断上一次请求是否完全返回
var goajax="";
var competition;
var kuang;
var hangye;
var payatt;//关注
var stage;//阶段
var currency;//币种
var scale;//规模
var feature;//特征
var bground;//背景
var deleteflag;//状态
var invtype;//类型
$(function () {
    search_company_tip.config();
    checktip_havemore();//控制加载更多按钮的方法
    config_companyinptu();
    $(".havemore .more").click(function () {
        showmore($(this));
    });

    $(".tiplist li").click(function () {
//&&$(this).offset().top<($(this).parents(".box-body").height()+$(this).parents(".box-body").offset().top)
        if($(this).attr("active")!="active"){
            var data=new Object();
            var info=new Object();
            info.dev=$(this).attr("tip");
            info.tip=$(this).html();
            info.ro=$(this).parents("ul").attr("ro");
            info.cl=$(this).attr("cl");
            data.info=info;
            if($(this).attr("gm")!="undefined"){
                if(info.cl.indexOf("usd")>0&&$($("[bz]")[0]).attr("active")!="active"){//美元
                    $($("ul[ro='3'] li")[0]).click();
                }else if(info.cl.indexOf("cnyscale")>0&&$($("[bz]")[1]).attr("active")!="active"){
                    $($("ul[ro='3'] li")[1]).click();
                }
            }
            var html=template.compile(model_data.condition)(data);
            $("#condition").append(html);
            $(this).attr("active","active");
            $(this).addClass("active");
            delcondition();

        }else{
            if($(this).html()=="USD"){
                $("[cl*='usd'][active='active']").click();
            }else if($(this).html()=="CNY"){
                $("[cl*='cnyscale'][active='active']").click();
            }
            $(this).attr("active","");
            $(this).removeClass("active");
            $(".condition span[ro='"+$(this).parents("ul").attr("ro")+"'][cl='"+$(this).attr("cl")+"']").parents(".condition").remove();


        }

    });
//搜索按钮点击
			$(".search-btn").click(function(){
                searchdatainit();
                subpost();
			});


			
});
//控制加载更多按钮的方法
function checktip_havemore(){
    $("ul[ro]").each(function () {
        if( $(this)[0].scrollHeight>$(this)[0].clientHeight){
            $(this).parents(".shgroup").addClass("havemore");
        }

    });

}
function config_companyinptu(){
    $("input[name='name']").each(function () {
        $(this).val("");
    });
}
function searchdatainit(){
    competition="";
    kuang="";
    hangye="";
    payatt="";//关注
    stage="";//阶段
    currency="";//币种
    scale="";//规模
    feature="";//特征
    bground="";//背景
    deleteflag="";//状态
    invtype="";//类型
    //竞争公司
    $("[compet-active='active']").each(function(){
        if($(this).html()==$(this).parents("span").children("[name='name']").val()){
            if(competition==""){
                competition=($(this).attr("data-code"))+",";
            }else{
                competition+=($(this).attr("data-code"))+",";
            }
        }

        
    });
    //筐
    $("[ro='0'] li[active='active']").each(function(){
    	if(kuang==""){
    		kuang=($(this).attr("cl"))+",";
    	}else{
    		kuang+=($(this).attr("cl"))+",";
    	} 
    });
    //行业
    $("[ro='1'] li[active='active']").each(function(){
    	if(hangye==""){
    		hangye=($(this).attr("cl"))+",";
    	}else{
    		hangye+=($(this).attr("cl"))+",";
    	} 
    });
    //近期关注
    $("[ro='2'] li[active='active']").each(function(){
    	if(payatt==""){
    		payatt=($(this).attr("cl"))+",";
    	}else{
    		payatt+=($(this).attr("cl"))+",";
    	} 

    });
    //币种
    $("[ro='3'] li[active='active']").each(function(){
    	if(currency==""){
    		currency=($(this).attr("cl"))+",";
    	}else{
    		currency+=($(this).attr("cl"))+"";
    	} 
        //currency=","+($(this).attr("cl"));
    });

    //规模
    $("[ro='4'] li[active='active']").each(function(){
       // scale=","+($(this).attr("cl"));
        if(scale==""){
        	scale=($(this).attr("cl"))+",";
    	}else{
    		scale+=($(this).attr("cl"))+",";
    	}
    });
    //阶段
    $("[ro='5'] li[active='active']").each(function(){
        //stage=","+($(this).attr("cl"));
        if(stage==""){
        	stage=($(this).attr("cl"))+",";
    	}else{
    		stage+=($(this).attr("cl"))+",";
    	} 
    });
    //特征
    $("[ro='6'] li[active='active']").each(function(){
        //feature=","+($(this).attr("cl"));
        if(feature==""){
        	feature=($(this).attr("cl"))+",";
    	}else{
    		feature+=($(this).attr("cl"))+",";
    	} 
    });
    //背景
    $("[ro='7'] li[active='active']").each(function(){
        if(bground==""){
            bground=($(this).attr("cl"))+",";
        }else{
            bground+=($(this).attr("cl"))+",";
        }
    });
  //状态
    $("[ro='8'] li[active='active']").each(function(){
        if(deleteflag==""){
            deleteflag=($(this).attr("cl"))+",";
        }else{
            deleteflag+=($(this).attr("cl"))+",";
        }
    });
    
    //类型
    $("[ro='9'] li[active='active']").each(function(){
        if(invtype==""){
            invtype=($(this).attr("cl"))+",";
        }else{
            invtype+=($(this).attr("cl"))+",";
        }
    });
}
//删除筛选条件框
function delcondition(){
    $(".condition .del").unbind().bind("click",function () {
        var ro=$(this).attr("ro");
        var cl=$(this).attr("cl");
        $("ul[ro='"+ro+"'] li[cl='"+cl+"']").click();
        $(this).parents(".condition").remove();

    });
}
//筛选条件组展示更多
function showmore(a){
    if($(a).parents(".shgroup").hasClass("heightauto")){
        $(a).parents(".shgroup").removeClass("heightauto");
        $(a).html('<span class="glyphicon glyphicon-chevron-down" ></span>');
    }else{
        $(a).parents(".shgroup").addClass("heightauto");
        $(a).html('<span class="glyphicon glyphicon-chevron-up" ></span>');

    }
}
//数据模板
var model_data= {
    condition: '<div class="condition"><span><%=info.dev%>：</span><span class="tip"><%=info.tip%></span><span class="del" ro=<%=info.ro%> cl=<%=info.cl%> ><span class="glyphicon glyphicon-remove"></span></span></div>',
    tiplist: '<ul>' +
        '<%for(var i=0;i<list.length;i++){%>' +
        '<li data-code="<%=list[i].base_comp_code%>"><%=list[i].base_comp_name%></li>' +
        '<%}%>' +
        '</ul>'
};
function subpost(){
    $.showloading();
    var type="hidden";
    var w = document.createElement("form");
    document.body.appendChild(w);
    var a= document.createElement("input");
    var b= document.createElement("input");
    var c= document.createElement("input");
    var d= document.createElement("input");
    var e= document.createElement("input");
    var f= document.createElement("input");
    var g= document.createElement("input");
    var h= document.createElement("input");
    var k= document.createElement("input");
    var l= document.createElement("input");
    var m= document.createElement("input");
    var n= document.createElement("input");
    var o= document.createElement("input");
    a.type= b.type= c.type= d.type= e.type= f.type= g.type= h.type= k.type=l.type=m.type=n.type=o.type=type;
    a.name="kuang";
    a.value=kuang;
    b.name="hangye";
    b.value=hangye;
    c.name="payatt";
    c.value=payatt;
    d.name="currency";
    d.value=currency;
    e.name="scale";
    e.value=scale;
    f.name="stage";
    f.value=stage;
    g.name="feature";
    g.value=feature;
    h.name="competition";
    h.value=competition;
    k.name="logintype";
    k.value=cookieopt.getlogintype();
    l.value=bground;
    l.name="bground";
    m.value=deleteflag;
    m.name="deleteflag";
    n.name="invtype";
    n.value=invtype;
    o.name="type";
    o.value="search";
    
    w.appendChild(a);
    w.appendChild(b);
    w.appendChild(c);
    w.appendChild(d);
    w.appendChild(e);
    w.appendChild(f);
    w.appendChild(g);
    w.appendChild(h);
    w.appendChild(k);
    w.appendChild(l);
    w.appendChild(m);
    w.appendChild(n);
    w.appendChild(o);
    w.action = "searchMoreCondition.html";
    w.method="post";
    var data={};
    data.type="scre";
    data.kuang=kuang;
    data.hangye=hangye;
    data.payatt=payatt;
    data.currency=currency;
    data.scale=scale;
    data.stage=stage;
    data.feature=feature;
    data.bground=bground;
    data.competition=competition;
    data.deleteflag=deleteflag;
    data.invtype=invtype;
    data.logintype=cookieopt.getlogintype();
    sessionStorage.setItem("organization_search",JSON.stringify(data));
   
    w.submit();

    $.hideloading();
}
var search_company_tip=(function(){
 var $this="";
    var config=function(){
        $(".company1,.company2,.company3").bind("input propertychange",function(){
        	$this=$(this);
        	if($(this).val()==""){
				 $(".companytip").slideUp(200);//模糊查询下拉框收起
			}else{
                if(timefunction){
                    clearTimeout(timefunction);
                                                   }
                  timefunction=setTimeout(function(){
              							if(doajax!=""){
              								doajax.abort();//上一次请求作废
              											}
              							selecttip();
              							outtime=1000;
              							},outtime);
      							}
//            tipclick();
        });
        $(".company1,.company2,.company3").blur(function () {
            var $this= $(this);
            setTimeout(function () {
                $this.parents("span").children(".companytip").slideUp(200);
            },100);

        });
        $(".company1,.company2,.company3").focus(function () {
            if($(this).val().length>0){
                $(this).parents("span").children(".companytip").slideDown(200);
                setTimeout(function () {
                    $(".box-body").scrollTop(10000);
                },200);
            }
        });

    };

    
    var selecttip=function(){
    	if($this.val()!=""){
		   doajax=$.ajax({
	        async: true,
	        dataType: 'json',
	        type: 'post',
	        data:{
	        name:$this.val().trim(),
            logintype:cookieopt.getlogintype()
	        							},
	        url:"searchCompany.html",
	        success: function (data) {
				        	if(data.list.length>0){
				        		var html=template.compile(model_data.tiplist)(data);
				        		$this.next(".companytip").html(html);
				        		$this.next(".companytip").slideDown(200);
				                tipclick();
				        	}else{
				        		 $(".companytip").hide();
				        	}
	        	doajax="";
	        		}
	    });
    	}
		var  tipclick=function(){
	        $(".companytip ul li").unbind().bind("click",function(){
	            $(this).parents("span").children("input").val($(this).html());
	            $(this).parents("ul li").each(function(){
	            	$(this).removeAttr("compet-active");
	            });
	            $(this).attr("compet-active","active");
	        });
	    };
    };
    return {
        config:config
    };
//    }
})();
