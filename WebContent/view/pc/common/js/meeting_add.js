/**
 * Created by shbs-tp001 on 15-9-18.
 */
var optactive=1;
var menuactive=0;
var investmentInfoList=[];//机构list
var compInfoList=[];//公司list
var parpuserJson;//与会者list
var sharewadJson;//分享范围list
var meetingtypeJson;//会议类型
var sharetype="1";
var opt={};
var addinvesbool=true;
var addcompbool=true;
var att=".*\.(doc|docx|xls|txt|pdf)$";
$(function () {
    $('#meetingdate').datepicker();
    init();
});



function init(){
	addmeetinglabel.choiceclick();
	
	addmeetinglabel.delclick();
	
	addmeetinglabel.addclick();
	
	meetingTypeConfig.click();
	
	if (userInfo != null && "" != userInfo) {
		// 参会人员
		parpuserJson = eval("[{code:'"+userInfo.sys_user_code+ "',name:'"+
				userInfo.sys_user_name+ "'}]");
	}
	//参会人员
	labelconfig.YKparpLoad();
	// 机构信息
	InvestmentInfoconfig.InvestmentInfoLoad();
	// 添加机构
	InvestmentInfoconfig.InvestmentAddClick();
	// 公司信息
	CompInfoconfig.CompInfoLoad();
	// 添加公司
	CompInfoconfig.CompAddClick();
	//分享范围
	labelconfig.ShareWadLoad();
	//保存
	addmeetingsub();
}
function click_lisetn(){

   //添加公司
   $("#add-gongsi").click(function(){
       addorganization.init();
   });
}
var labelconfig = (function() {
// 初始加载易凯参会人
var initYKparp = function() {
	var html = "";
	if (parpuserJson != null && parpuserJson != "" ) {
		var data = {
			list : parpuserJson
		};
		html = template.compile(table_data.labelInfoList)(data);
	} else {
		html = table_data.labelNull;
	}
	$("#ykparp").html(html);
	refreshladle($("#ykparp"));
	YKuserClick();
};
// 参会人员事件
var YKuserClick = function() {
	var ykusershowlist = dicListConfig(true, YKuserList, parpuserJson);
	$("#EKRid").unbind().bind("click", function() {
		var $this = $(this).children("div").children("span");
		tip_edit.config({
     $this:$this,
     title:"参会人员",
			list : ykusershowlist,
			radio : false,
			besure : function() {
				parpuserJson = eval(choiceContent());
				tip_edit.close();
				initYKparp();
			}
		});
	});
};

//初始加载分享范围
var initShareWad = function() {
	var html = "";
	if (sharewadJson != null && sharewadJson != "" ) {
		var data = {
			list : sharewadJson
		};
		html = template.compile(table_data.labelInfoList)(data);
	} else {
		html = table_data.labelNull;
	}
	$("#sharewad").html(html);
	refreshladle($("#sharewad"));
	ShareWadClick();
};
// 分享范围事件
var ShareWadClick = function() {
//	var sharewadshowlist = dicListConfig(true, SysWadList, sharewadJson);
//	var shareusershowlist = dicListConfig(true, YKuserList, sharewadJson);
	$("#shareid").unbind().bind("click", function() {
		var $this = $(this).children("div").children("span");
		layer.open({
            title:"选择分享类型",
            type: 1,
            scrollbar: false,
            skin: 'layui-layer-rim', //加上边框
            area: ['350px','200px'], //宽高
            content: '<div class="sharefrom"><ul class="inputshare">' +
                '<li><span class=""><input id="ra1" name="sharetype" type="radio" value="1" checked/></span><span class="">分享给筐</span></li>' +
                '<li><span class=""><input id="ra2" name="sharetype" type="radio" value="2" /></span><span class="">分享给人</span></li>' +
                '</ul><div class="btn-box"><button class="btn btn-default getShareVal">确定</button></div></div>'
        });
		$(".getShareVal").unbind().bind("click",function(){
			sharetype=$('.inputshare input[name="sharetype"]:checked').val();
			layer.closeAll("page");
			var sharelist;
			if(sharetype=="1"){
				sharelist=dicListConfig(true, SysWadList, sharewadJson);
			}else if(sharetype=="2"){
				sharelist=dicListConfig(true, YKuserList, sharewadJson);
			}
            tip_edit.config({
			$this:$this,
			title : "分享范围",
			list : sharelist,
			radio : false,
			besure : function() {
				sharewadJson = eval(choiceContent());
				tip_edit.close();
				initShareWad();
			}
		});
			
			
		});
		$('#ra'+sharetype).attr("checked",true);
		
		
		
		
		
		
		
//		layer.confirm('请选择分享类型', {
//            title:"提示",
//            icon: 0,
//            btn: ['筐','人'] //按钮
//        }, function(index){
//        	sharetype="1";//类型：筐（1）
//            layer.close(index);
//            tip_edit.config({
//    			$this:$this,
//    			title : "分享范围",
//    			list : sharewadshowlist,
//    			radio : false,
//    			besure : function() {
//    				sharewadJson = eval(choiceContent());
//    			  tip_edit.close();
//    				initShareWad();
//    			}
//    		});
//            
//        }, function(index){
//        	sharetype="2";//类型：人（2）
//            layer.close(index);
//            tip_edit.config({
//    			$this:$this,
//    			title : "分享范围",
//    			list : shareusershowlist,
//    			radio : false,
//    			besure : function() {
//    				sharewadJson = eval(choiceContent());
//    				tip_edit.close();
//    				initShareWad();
//    			}
//    		});
//        });
		
		
		
	});
};
return {
	YKparpLoad : initYKparp,
	ShareWadLoad : initShareWad
};
})();

//选择机构
var InvestmentInfoconfig = (function() {

    var savedata={
        searchorg:"",
        searchinv:""
    };

	//机构打印
	var initInvestment = function() {
		var html = "";
		if(investmentInfoList[0]!=null){
			html=template.compile(table_data.investmentInfoList)({list:investmentInfoList});
		}
		$("#touzilist").html(html);
		touzieditclick();// 编辑／删除机构事件
	};

    //添加相关机构图标事件
    var addinvenstmentClick=function(){

	    //点击添加机构图标
	    $("#add-touzi").bind("click",function(e){
    
        //页面层
        layer.open({
            title:"相关机构",
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['500px', '240px'], //宽高
            content: '<div class="addfrom"><ul class="inputlist">'
            	+'<li><span class="lable">投资机构:</span><span class="in">'
            	+'<select id="investment" class=" inputdef"><option></option></select></span>'
            	+'<span class="addbuttonselect">'
            	+'<button id="btn_investment_add" class="btn btn_icon_add"></button></span></li>'
            	+'<li><span class="lable">投资人:</span><span class="in">'
            	+'<select id="investor" class=" inputdef"><option></option></select></span>'
            	+'<span class="addbuttonselect">'
            	+'<button id="btn_investor_add" class="btn btn_icon_add"></button></span></li>'
            	+'<li><span class="lable">职位:</span><span class="in">'
            	+'<input value="" readonly id="investorposiname" type="text" class="inputdef" maxlength="20"></span></li>'
            	+'</ul><div class="btn-box"><button class="btn btn-default saveorgan">保存</button></div></div>'
        });
        investmentselect_ajax();
        investmentadd_ajax();
        investoradd_ajax();
        investorselect_ajax();
		opt.$investornselect.prop("disabled", true);
        addinventsaveclick();
        
      
    //相关机构保存
    function addinventsaveclick() {
        $(".saveorgan").unbind().bind("click", function () {
            if($("#investment").val()==null 
        			|| $("#investment").val().trim()==""){
                $.showtip("请选择投资机构");
			      return;
            }else{
            	var base_investment_code;
            	var base_investment_name;
            	var investmenttype;
            	if($("#btn_investment_add").hasClass("btn_icon_add")){
            		base_investment_code=$("#investment").val()  ;
            		base_investment_name=$("#investment").find("option:selected").text();
            		investmenttype="select";
            	}else{
            		base_investment_code="";
            		base_investment_name=$("#investment").val();
            		investmenttype="add";
            		invementadd_ajax();
            		if(!addinvesbool){
            			 $.showtip("该投资机构已添加");
                         return;
            		}
            		
            	}
            	var base_investor_code;
            	var base_investor_name;
            	var investortype;
            	if($("#btn_investor_add").hasClass("btn_icon_add")){
            		base_investor_code=$("#investor").val()  ;
            		base_investor_name=$("#investor").find("option:selected").text();
            		investortype="select";
            	}else{
            		base_investor_code="";
            		base_investor_name=$("#investor").val();
            		investortype="add";
                    if(investmenttype=="select"){
                        addinvestor_ajax();
                        if(!addinvesbool){
                            $.showtip("该投资机构投资人已添加");
                            return;
                        }
                    }

            	}
            	
                for(var i=0;i<  investmentInfoList.length;i++){
                    if(base_investment_code==  investmentInfoList[i].base_investment_code
                    		&& "select"==investmentInfoList[i].investmenttype
                    		&& base_investor_name==investmentInfoList[i].base_investor_name){
                        $.showtip("已选择该投资机构投资人");
                        return;
                    }else if(base_investment_name == investmentInfoList[i].base_investment_name
                    		&& base_investor_name == investmentInfoList[i].base_investor_name){
                        $.showtip("该投资机构投资人已添加");
                        return;
                    }else if(base_investment_name == investmentInfoList[i].base_investment_name
                    		&&base_investor_name==""&&investmentInfoList[i].base_investor_name==""){
                    	$.showtip("该投资机构已添加");
                        return;
                    }
                }
            						
            	var investmentinfo=new Object();
	    		//机构code
            	investmentinfo.base_investment_code=base_investment_code;
	    		//机构name
            	investmentinfo.base_investment_name=base_investment_name;
	    		//机构添加类型
            	investmentinfo.investmenttype=investmenttype;
	    		//投资人code
	    		investmentinfo.base_investor_code=base_investor_code;
	    		//投资人name
	    		investmentinfo.base_investor_name=base_investor_name;
	    		if(base_investor_name==null
	    				  ||base_investor_name=="" ){
	    			$("#investorposiname").val("");
	    		}
	    		//投资人类型
	    		investmentinfo.investortype=investortype;
	    		  
	    		//投资人职位
                investmentinfo.base_investor_posiname=$("#investorposiname").val();

                investmentInfoList.push(investmentinfo);

                savedata.searchorg="";
                savedata.searchinv="";

                layer.closeAll('page');
                initInvestment();
            	
            }
        });
    }



	});
    };
    var touzieditclick=function(){
	    //相关机构编辑
        $("[touziedit]").unbind().bind("click", function () {
            var i=$(this).attr("i");
            //页面层
            layer.open({
            title:"添加所属投资机构",
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['500px', '240px'], //宽高
            content: '<div class="addfrom"><ul class="inputlist">'
                    +'<li><span class="lable">投资机构:</span><span class="in">'
                    +'<select id="investment" class=" inputdef">'
                    +'<option value="'+investmentInfoList[i].base_investment_code+'" selected="selected">'
                    +investmentInfoList[i].base_investment_name+'</option></select></span>'
                    +'<span class="addbuttonselect">'
                    +'<button id="btn_investment_add" class="btn btn_icon_add"></button></span></li>'
                    +'<li><span class="lable">投资人:</span><span class="in">'
                    +'<select id="investor" class=" inputdef">'
                    +'<option value="'+investmentInfoList[i].base_investor_code+'" selected="selected">'
                   +investmentInfoList[i].base_investor_name+'</option></select></span>'
                    +'<span class="addbuttonselect">'
                    +'<button id="btn_investor_add" class="btn btn_icon_add"></button></span></li>'
                    +'<li><span class="lable">职位:</span><span class="in">'
                    +'<input value="'
                    +investmentInfoList[i].base_investor_posiname+'"  id="investorposiname" type="text" class="inputdef" maxlength="20"></span></li>'
                    +'</ul><div class="btn-box"><button class="btn btn-default editorgan">保存</button></div></div>'
        });
        if(investmentInfoList[i].investmenttype=="select"){
            investmentselect_ajax();
        }else if(investmentInfoList[i].investmenttype=="add"){
            $("#investment").parent().html('<input value="'
                +investmentInfoList[i].base_investment_name
                +'"id="investment" type="text" class="inputdef" maxlength="20">');
            $("#btn_investment_add").removeClass("btn_icon_add").addClass("btn_icon_search");
        }

        if(investmentInfoList[i].investortype=="select"){
            investorselect_ajax();
        }else if(investmentInfoList[i].investortype=="add"){
            $("#investor").parent().html('<input value="'
                +investmentInfoList[i].base_investor_name
                +'"id="investor" type="text" class="inputdef" maxlength="20">');
            $("#btn_investor_add").removeClass("btn_icon_add").addClass("btn_icon_search");
        }
        if($("#btn_investment_add").hasClass("btn_icon_add")
            && $("#btn_investor_add").hasClass("btn_icon_add")){
            $("#investorposiname").attr("readonly","readonly");
        }else{
            $("#investorposiname").removeAttr("readonly");
        }

        investmentadd_ajax();
        investoradd_ajax();
        editinventsaveclick();
    


        
        //编辑相关机构保存
       function editinventsaveclick(){

        $(".editorgan").unbind().bind("click", function () {
            if($("#investment").val()==null || $("#investment").val().trim()==""){
                $.showtip("请选择投资机构");
                return;
            }else{
                var base_investment_code;
                var base_investment_name;
                var investmenttype;
                if($("#btn_investment_add").hasClass("btn_icon_add")){
                    base_investment_code=$("#investment").val()  ;
                    base_investment_name=$("#investment").find("option:selected").text();
                    investmenttype="select";
                }else{
                    base_investment_code="";
                    base_investment_name=$("#investment").val();
                    investmenttype="add";
                    invementadd_ajax();
                    if(!addinvesbool){
                         $.showtip("该投资机构已添加");
                         return;
                    }
                }

                var base_investor_code;
                var base_investor_name;
                var investortype;
                if($("#btn_investor_add").hasClass("btn_icon_add")){
                    base_investor_code=$("#investor").val()  ;
                    base_investor_name=$("#investor").find("option:selected").text();
                    investortype="select";
                }else{
                    base_investor_code="";
                    base_investor_name=$("#investor").val();
                    investortype="add";
                    if(investmenttype=="select"){
                        addinvestor_ajax();
                        if(!addinvesbool){
                            $.showtip("该投资机构投资人已添加");
                            return;
                        }
                    }
                }

                for(var j=0;j<  investmentInfoList.length;j++){
                    if(base_investment_code==  investmentInfoList[j].base_investment_code
                            && "select"==investmentInfoList[j].investmenttype
                            && base_investor_name == investmentInfoList[j].base_investor_name
                            && i!=j){
                        $.showtip("已选择该投资机构投资人");
                        return;
                    }else if(base_investment_name==  investmentInfoList[j].base_investment_name
                            && base_investor_name == investmentInfoList[j].base_investor_name
                            && i!=j){
                        $.showtip("该投资机构投资人已添加");
                        return;
                    }
                }
                var investmentinfo=new Object();
                //机构code
                investmentinfo.base_investment_code=base_investment_code;
                //机构name
                investmentinfo.base_investment_name=base_investment_name;
                //机构添加类型
                investmentinfo.investmenttype=investmenttype;
                //投资人code
                investmentinfo.base_investor_code=base_investor_code;
                //投资人name
                investmentinfo.base_investor_name=base_investor_name;

                if(base_investor_name==null ||base_investor_name=="" ){
                      $("#investorposiname").val("");
                }
                //投资人类型
                investmentinfo.investortype=investortype;

                //投资人职位
                investmentinfo.base_investor_posiname=$("#investorposiname").val();
            }
            investmentInfoList[i]=investmentinfo;

            savedata.searchorg="";
            savedata.searchinv="";

            layer.closeAll('page');
            initInvestment();
        });
       }
    });

        //相关机构移出
        $("[touziremove]").unbind().bind("click", function () {
            var i=$(this).attr("i");
            investmentInfoList.baoremove(Number(i));
            initInvestment();
        });
    };

    //投资机构选择页面
    function investmentselect_ajax() {
        //选择投资机构
        opt.$orgainselect=$("#investment").select2({
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

                    savedata.searchorg=(params.term||"");

                    return{
                        name:params.term||"",
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

      //机构发生变化清空投资人搜索重置信息
        $("#investment").on("change", function() {
            if($("#btn_investment_add").hasClass("btn_icon_add")){
               //机构发生变化
               //若现在投资人为添加状态，职位可输入
               if($("#btn_investor_add").hasClass("btn_icon_search")){
                       $("#investorposiname").removeAttr("readonly");
               }
               //若现在投资人为搜索状态，初始化搜索，职位不可输入
               else if($("#investment").val()!=null
                   &&$("#investment").val().trim()!="" ){
                    $("#investor").parent().html('<select id="investor" class=" inputdef"><option></option></select>');
                            investorselect_ajax();
                               $("#investorposiname").val("");
                               $("#investorposiname").attr("readonly","readonly");
                        }
               else{
                   $("#investor").parent().html('<select id="investor" class=" inputdef" ><option></option></select>');
                investorselect_ajax();
                   opt.$investornselect.prop("disabled", true);
                       $("#investorposiname").val("");
                       $("#investorposiname").attr("readonly","readonly");
               }
            }
            });
    }
    function  investmentadd_ajax() {

        //添加投资机构点击
        $("#btn_investment_add").unbind().bind("click", function () {
            //现在是搜索切换为添加，职位允许输入
            if($("#btn_investment_add").hasClass("btn_icon_add")){
                $("#investment").parent().html('<input value="" id="investment" type="text" class="inputdef" maxlength="20">');

                $("#investment").val(savedata.searchorg);

                $("#btn_investment_add").removeClass("btn_icon_add").addClass("btn_icon_search");
                $("#investorposiname").removeAttr("readonly");

                if($("#btn_investor_add").hasClass("btn_icon_add")){
                     investorselect_ajax();
                    opt.$investornselect.prop("disabled", false);
                }

            }
            //现在是添加切换为搜索，职位不允许输入，初始化机构筛选和投资人筛选
            else{
                $("#investment").parent().html('<select id="investment" class=" inputdef"><option></option></select>');
                investmentselect_ajax();
                $("#btn_investment_add").removeClass("btn_icon_search").addClass("btn_icon_add");
                    $("#investorposiname").val("");
                    if($("#btn_investor_add").hasClass("btn_icon_add")){
                $("#investorposiname").attr("readonly","readonly");
                opt.$investornselect.val("").trigger("change");
                investorselect_ajax();
                            opt.$investornselect.prop("disabled", true);
                         }
                     else{
                         $("#investorposiname").removeAttr("readonly");
                      }
            }
        });

    }

//投资人选择页面
function investorselect_ajax() {
	var invementcode;
	if($("#btn_investment_add").hasClass("btn_icon_add")){
		invementcode=$("#investment").val()  ;
						}
	else{
		invementcode="";
					}
    //选择投资人
    opt.$investornselect=$("#investor").select2({
        placeholder:"请选择投资人",//文本框的提示信息
        minimumInputLength:0,   //至少输入n个字符，才去加载数据
        allowClear: true,  //是否允许用户清除文本信息
        ajax:{
            url:"queryinvestorlistByinvementcode.html",
            dataType:"json",
            cache: true,
            type:"post",
            delay: 250,//加载延迟
            data: function (params) {

                savedata.searchinv=(params.term||"");

                return{
                    name:params.term||"",
                    pageSize:CommonVariable().SELSECLIMIT,
                    pageCount:"1",
                    logintype:cookieopt.getlogintype(),
    											invementcode:invementcode,
                    type:"1"
                };
            },
            processResults: function (data, page) {
                for(var i=0;i<data.list.length;i++){
                    data.list[i].id=data.list[i].base_investor_code;
                    data.list[i].text=data.list[i].base_investor_name;
                   
                }
                return {
                    results: data.list//返回结构list值
                };
            }

        },
        escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
        templateResult: formatRepo // 格式化返回值 显示再下拉类表中
    });
    
  //投资人发生变化职位信息信息
    $("#investor").on("change", function() { 
    	if($("#btn_investor_add").hasClass("btn_icon_add")){
		        //职位
		  $("#investorposiname").val(opt.$investornselect.select2("data")[0].base_investor_posiname);
    		
    	}
    	}); 
}
	function  investoradd_ajax() {

        //添加投资人点击
        $("#btn_investor_add").unbind().bind("click", function () {
        	if($("#btn_investor_add").hasClass("btn_icon_add")){
        	    $("#investor").parent().html('<input value="" id="investor" type="text" class="inputdef" maxlength="20">');

                $("#investor").val(savedata.searchinv);

                $("#btn_investor_add").removeClass("btn_icon_add").addClass("btn_icon_search");
        	    $("#investorposiname").val("");
			   	$("#investorposiname").removeAttr("readonly");
        	}
        	else{
        		$("#investor").parent().html('<select id="investor" class=" inputdef" ><option></option></select>');
          $("#btn_investor_add").removeClass("btn_icon_search").addClass("btn_icon_add");
         		investorselect_ajax();
        		if($("#investment").val()!=null
      	    	   &&$("#investment").val().trim()!=null ){
        			opt.$investornselect.prop("disabled", false);
         		}
        		else{
        			opt.$investornselect.prop("disabled", true);
        			}
           $("#investorposiname").val("");
           if($("#btn_investment_add").hasClass("btn_icon_add")){
           $("#investorposiname").attr("readonly","readonly");
           				}
           else{
        		$("#investorposiname").removeAttr("readonly");
           			}
        	}
        });
		   
    }
    function invementadd_ajax() {
		$.showloading();
		$.ajax({
			async : false,
			dataType : 'json',
			type : 'post',
			url : "queryInvestmentOnlyNamefottrade.html",
			data : {
				name : $("#investment").val().trim(),
				logintype : cookieopt.getlogintype()
			},
			success : function(data) {
				$.hideloading();
				if (data.message == "nodata") {
					addinvesbool= true;
				}
				else if(data.message == "exsit"){
					addinvesbool= false;
				
				}else {
					addinvesbool= false;
				}
			}
		});
	};

    function addinvestor_ajax() {
        $.showloading();
        $.ajax({
            async : false,
            dataType : 'json',
            type : 'post',
            url : "queryOrgInvestorOnlyNamefottrade.html",
            data : {
                orgcode:$("#investment").val().trim() ,
                name : $("#investor").val().trim(),
                logintype : cookieopt.getlogintype()
            },
            success : function(data) {
                $.hideloading();
                if (data.message == "nodata") {
                    addinvesbool= true;
                }
                else if(data.message == "exsit"){
                    addinvesbool= false;

                }else {
                    addinvesbool= false;
                }
            }
        });
    };

    function formatRepo (repo) {
        return repo.text;
    }
    return{
    	InvestmentInfoLoad:initInvestment,
    	InvestmentAddClick:addinvenstmentClick
    };
})();


//选择公司
var CompInfoconfig = (function() {

    var savedata={
        searchcom:"",
        searchinv:""
    };

	//公司打印
	var initComp = function() {
		var html = "";
		if(compInfoList[0]!=null){
			html=template.compile(table_data.compInfoList)({list:compInfoList});
		}
		$("#complist").html(html);
		compeditclick();// 编辑／删除公司事件
	};

//添加公司图标事件
var addcompClick=function(){

	//点击添加公司图标
	$("#add-gongsi").bind("click",function(e){
    
        //页面层
        layer.open({
            title:"相关公司",
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['500px'], //宽高
            content: '<div class="addfrom"><ul class="inputlist">'
            	+'<li><span class="lable">公司:</span><span class="in">'
            	+'<select id="compid" class=" inputdef"><option></option></select></span>'
            	+'<span class="addbuttonselect">'
            	+'<button id="btn_comp_add" class="btn btn_icon_add"></button></span></li>'
            	+'<li><span class="lable">联系人:</span><span class="in">'
            	+'<select id="entrepreneur" class=" inputdef"><option></option></select></span>'
            	+'<span class="addbuttonselect">'
            	+'<button id="btn_entrepreneur_add" class="btn btn_icon_add"></button></span>'
            	+'</li>'
            	+'<li><span class="lable">职位:</span><span class="in">'
            	+'<input value="" readonly id="entrepreneurposiname" type="text" class="inputdef" maxlength="20"></span>'
            	+'</li>'
            	+'<li><span class="lable">创建融资计划:</span><span class="in">'
            	+'<input value=""  id="planshow" type="checkbox" ></span>'
            	+'</li>'
            	+'<li style="display:none"><span class="lable">计划时间:</span><span class="in">'
            	+'<input type="text" class="span2" id="plantime" data-date-format="yyyy-mm-dd" readonly></span>'
            	+'</li>'
            	+'<li style="display:none"><span class="lable">提醒时间:</span><span class="in">'
            	+'<input type="text" class="span2" id="emailtime" data-date-format="yyyy-mm-dd" readonly></span>'
            	+'</li>'
            	+'<li style="display:none"><span class="lable">计划内容:</span><span class="in">'
            	+'<textarea class="txt_redord" style="height:80px;"id="palntext" maxlength="100"></textarea>'
            	+'</li>'
            	+'</ul>'
            	+'<div class="btn-box"><button class="btn btn-default savecomp">保存</button></div></div>'
        });
        compinfoselect_ajax();
        compinfoadd_ajax();
   		entrepreneuradd_ajax();
   		entrepreneurselect_ajax();
   		opt.$entrepreneurselect.prop("disabled", true);
        addcompsaveclick();
        			//融资计划选择
					compplanClick();
      
    //相关公司保存
    function addcompsaveclick() {
        $(".savecomp").unbind().bind("click", function () {
            if($("#compid").val()==null
            		||$("#compid").val().trim()==""){
                $.showtip("请选择公司");
			      				return;
            }else{
            	var base_comp_code;
            	var base_comp_name;
            	var comptype;
            	if($("#btn_comp_add").hasClass("btn_icon_add")){
            		base_comp_code=$("#compid").val()  ;
            		base_comp_name=$("#compid").find("option:selected").text();
            		comptype="select";
            						}
            	else{
            		base_comp_code="";
            		base_comp_name=$("#compid").val();
            		comptype="add";
            		compadd_ajax();
            		if(!addcompbool){
            			 $.showtip("该公司已添加");
                         return;
            						}
            		
            					}
            	var base_entrepreneur_code;
            	var base_entrepreneur_name;
            	var entrepreneurtype;
            	if($("#btn_entrepreneur_add").hasClass("btn_icon_add")){
            		base_entrepreneur_code=$("#entrepreneur").val()  ;
            		base_entrepreneur_name=$("#entrepreneur").find("option:selected").text();
            		entrepreneurtype="select";
            						}
            	else{
            		base_entrepreneur_code="";
            		base_entrepreneur_name=$("#entrepreneur").val();
            		entrepreneurtype="add";
            					}
            	
                for(var i=0;i<compInfoList.length;i++){
                    if(base_comp_code==  compInfoList[i].base_comp_code
                    		&& "select"==compInfoList[i].base_comp_code){
                        $.showtip("已选择该公司");
                        return;
                    						}
                    else if(base_comp_name==  compInfoList[i].base_comp_name){
                        $.showtip("该公司已添加");
                        return;
                    						}
                						}
            						
            if(
	    			  $("#planshow").is(':checked')&& ($("#plantime").val().trim()==""
	    				  				|| isNaN(new Date($("#plantime").val()).getTime()))){
	    		  	$.showtip("融资计划的计划时间不可为空");
		    		  return;
	    	  				}
			    	  else if(
			    			  $("#planshow").is(':checked')
			    			  && ($("#emailtime").val().trim()==""
			    				  || isNaN(new Date($("#emailtime").val()).getTime()))
			    				  				){
			    		  	$.showtip("融资计划的提醒时间不可为空");
				    		  return;
			    	  		}
			    	  else if(
			    			  $("#planshow").is(':checked')&& $("#palntext").val().trim()==""){
			    		  	$.showtip("融资计划的计划内容不可为空");
				    		  return;
			    	  		}
	    	  else{
	    		  plantime = $("#plantime").val();
	    		  emailtime = $("#emailtime").val();

        if ($("#planshow").is(':checked')) {

				  var thisnowdate=new Date(nowdate.substring(0,10).replace(/-/g,"/"));
				  emailtime=new Date(emailtime.replace(/-/g,"/").replace("年","/").replace("月","/").replace("日","/"));
				  plantime=new Date(plantime.replace(/-/g,"/").replace("年","/").replace("月","/").replace("日","/"));
				  if(thisnowdate>plantime){
				      	$.showtip("融资计划的计划时间需大于当前日期");
										return;
				  						}
					  if(thisnowdate>emailtime ){
					  			$.showtip("融资计划的提醒时间需大于当前日期");
					      return;
					                      }
					      }
	    		  var compinfo=new Object();
	    		  //公司code
	    		  compinfo.base_comp_code=base_comp_code;
	    		  //公司name
	    		  compinfo.base_comp_name=base_comp_name;
	    		  //公司添加类型
	    		  compinfo.comptype=comptype;
	    		  //公司联系人code
	    		  compinfo.base_entrepreneur_code=base_entrepreneur_code;
	    		  //公司联系人name
	    		  compinfo.base_entrepreneur_name=base_entrepreneur_name;
	    		  if(base_entrepreneur_name==null
	    				  ||base_entrepreneur_name=="" ){
	    			  $("#entrepreneurposiname").val("");
	    		  }
	    		  //公司联系人类型
	    		  compinfo.entrepreneurtype=entrepreneurtype;

	    		  //公司联系人职位
	    		  compinfo.base_entrepreneur_posiname=$("#entrepreneurposiname").val();
	    		  
	    		  //是否创建公司融资计划
	    		  compinfo.financplanflag=$("#planshow").is(':checked');
	    		  //公司融资计划计划日期
	    		  compinfo.base_financplan_date=$("#plantime").val();
	    		  //公司融资计划提醒日期
	    		  compinfo.base_financplan_remindate=$("#emailtime").val();
	    		//公司融资计划内容
	    		  compinfo.base_financplan_cont=$("#palntext").val();


	    		  compInfoList.push(compinfo);

                savedata.searchcom="";
                savedata.searchinv="";

                layer.closeAll('page');
                initComp();
	    	  }
            }
        });
    }



	});
};
var compeditclick=function(){ 
	//相关公司编辑
$("[compedit]").unbind().bind("click", function () {
    var i=$(this).attr("i");
    var contentstr="";
    contentstr='<div class="addfrom" ><ul class="inputlist">'
    	+'<li><span class="lable">公司:</span><span class="in">'
    	+'<select id="compid" class=" inputdef">'
    		+'<option value="'+compInfoList[i].base_comp_code+'" selected="selected">'
	    +compInfoList[i].base_comp_name+'</option></select></span>'
    	+'<span class="addbuttonselect">'
    	+'<button id="btn_comp_add" class="btn btn_icon_add"></button></span></li>'
    	+'<li><span class="lable">联系人:</span><span class="in">'
    	+'<select id="entrepreneur" class=" inputdef">'
			+'<option value="'+compInfoList[i].base_entrepreneur_code+'" selected="selected">'
	     +compInfoList[i].base_entrepreneur_name+'</option></select></span>'
    	+'<span class="addbuttonselect">'
    	+'<button id="btn_entrepreneur_add" class="btn btn_icon_add"></button></span>'
    	+'</li>'
    	+'<li><span class="lable">职位:</span><span class="in">'
    	+'<input value="'
    	+compInfoList[i].base_entrepreneur_posiname+'" id="entrepreneurposiname" type="text" class="inputdef" maxlength="20"></span>'
    	+'</li>'
    	+'<li><span class="lable">创建融资计划:</span><span class="in">';
    var checked="";
    var stylestr="";
    if(compInfoList[i].financplanflag){
    	checked="checked";
    		}
    else{
    	stylestr="style='display:none'";
    		}
    contentstr=	contentstr+'<input value=""  id="planshow" type="checkbox"'
    		+checked+' ></span>'
    	+'</li>'
    	+'<li '+stylestr+'><span class="lable">计划时间:</span><span class="in">'
    	+'<input type="text" class="span2" id="plantime" data-date-format="yyyy-mm-dd" readonly></span>'
    	+'</li>'
    	+'<li '+stylestr+'><span class="lable">提醒时间:</span><span class="in">'
    	+'<input type="text" class="span2" id="emailtime" data-date-format="yyyy-mm-dd" readonly></span>'
    	+'</li>'
    	+'<li '+stylestr+'><span class="lable">计划内容:</span><span class="in">'
    	+'<textarea class="txt_redord" style="height:80px;"id="palntext" maxlength="100"></textarea>'
    	+'</li>'
    	+'</ul>'
    	+'<div class="btn-box"><button class="btn btn-default editcomp">保存</button></div></div>';
    //页面层
    layer.open({
        title:"相关公司",
        type: 1,
        skin: 'layui-layer-rim', //加上边框
        area: ['500px'], //宽高
        content: contentstr
    });

 		if(compInfoList[i].comptype=="select"){
 			compinfoselect_ajax();
 		}
 		else if(compInfoList[i].comptype=="add"){
        	$("#compid").parent().html('<input value="'
        	+compInfoList[i].base_comp_name
        	+'"id="compid" type="text" class="inputdef" maxlength="20">');
        	$("#btn_comp_add").removeClass("btn_icon_add").addClass("btn_icon_search");
 		}   
 		
 		if(compInfoList[i].entrepreneurtype=="select"){
 					entrepreneurselect_ajax();
 		}
 		else if(compInfoList[i].entrepreneurtype=="add"){
 		 			$("#entrepreneur").attr("operatype",'add');
        	$("#entrepreneur").parent().html('<input value="'
        +compInfoList[i].base_entrepreneur_name
        +'"id="entrepreneur" type="text" class="inputdef" maxlength="20">');
        	$("#btn_entrepreneur_add").removeClass("btn_icon_add").addClass("btn_icon_search");
 		}   
 		if($("#btn_comp_add").hasClass("btn_icon_add")
 				&& $("#btn_entrepreneur_add").hasClass("btn_icon_add")){
        $("#entrepreneurposiname").attr("readonly","readonly");
           }
     else{
         		$("#entrepreneurposiname").removeAttr("readonly");
            }
 		if(compInfoList[i].financplanflag){
 			 //融资计划计划时间
			  $("#plantime").val(compInfoList[i].base_financplan_date);
			  //融资计划提醒时间
			  $("#emailtime").val(compInfoList[i].base_financplan_remindate);
			  //融资计划计划内容
			  $("#palntext").val(compInfoList[i].base_financplan_cont);
			  //2015/12/10 BUG297 AddStart
			   $('#plantime').datepicker();
			   $('#emailtime').datepicker();
				//2015/12/10 BUG297 AddEnd
 		}
 		
 	    compinfoadd_ajax();
 		  entrepreneuradd_ajax();
 	    editcompsaveclick();
		//融资计划选择
		compplanClick();

   function editcompsaveclick(){
	   
    $(".editcomp").unbind().bind("click", function () {
    	if($("#compid").val()==null
        		||$("#compid").val().trim()==""){
            $.showtip("请选择公司");
		      return;
        }else{
        	var base_comp_code;
        	var base_comp_name;
        	var comptype;
        	if($("#btn_comp_add").hasClass("btn_icon_add")){
        		base_comp_code=$("#compid").val()  ;
        		base_comp_name=$("#compid").find("option:selected").text();
        		comptype="select";
        						}
        	else{
        		base_comp_code="";
        		base_comp_name=$("#compid").val();
        		comptype="add";
        		compadd_ajax();
        		if(!addcompbool){
        			 $.showtip("该公司已添加");
                     return;
        						}
        		
        					}
        	var base_entrepreneur_code;
        	var base_entrepreneur_name;
        	var entrepreneurtype;
        	if($("#btn_entrepreneur_add").hasClass("btn_icon_add")){
        		base_entrepreneur_code=$("#entrepreneur").val()  ;
        		base_entrepreneur_name=$("#entrepreneur").find("option:selected").text();
        		entrepreneurtype="select";
        						}
        	else{
        		base_entrepreneur_code="";
        		base_entrepreneur_name=$("#entrepreneur").val();
        		entrepreneurtype="add";
        					}
         	
             for(var j=0;j<  compInfoList.length;j++){
                 if(base_comp_code==  compInfoList[j].base_comp_code
                 		&& "select"==compInfoList[j].base_comp_code
                 		&& i!=j){
                     $.showtip("已选择该公司");
                     return;
                 						}
                 else if(base_comp_name==  compInfoList[j].base_comp_name
                		 && i!=j){
                     $.showtip("该公司已添加");
                     return;
                 						}
             						}
         						
         if(
	    			  $("#planshow").is(':checked')&& ($("#plantime").val().trim()==""
	    				  				|| isNaN(new Date($("#plantime").val()).getTime()))){
	    		  	$.showtip("融资计划的计划时间不可为空");
		    		  return;
	    	  				}
			    	  else if(
			    			  $("#planshow").is(':checked')
			    			  && ($("#emailtime").val().trim()==""
			    				  || isNaN(new Date($("#emailtime").val()).getTime()))
			    				  				){
			    		  	$.showtip("融资计划的提醒时间不可为空");
				    		  return;
			    	  		}
			    	  else if(
			    			  $("#planshow").is(':checked')&& $("#palntext").val().trim()==""){
			    		  	$.showtip("融资计划的计划内容不可为空");
				    		  return;
			    	  		}
	    	  else{
	    		  plantime = $("#plantime").val();
	    		  emailtime = $("#emailtime").val();

     if ($("#planshow").is(':checked')) {

				  var thisnowdate=new Date(nowdate.substring(0,10).replace(/-/g,"/"));
				  emailtime=new Date(emailtime.replace(/-/g,"/").replace("年","/").replace("月","/").replace("日","/"));
				  plantime=new Date(plantime.replace(/-/g,"/").replace("年","/").replace("月","/").replace("日","/"));
				  if(thisnowdate>plantime){
				      	$.showtip("融资计划的计划时间需大于当前日期");
										return;
				  						}
					  if(thisnowdate>emailtime ){
					  			$.showtip("融资计划的提醒时间需大于当前日期");
					      return;
					                      }
					      }
	    		  var compinfo=new Object();
	    		  //公司code
	    		  compinfo.base_comp_code=base_comp_code;
	    		  //公司name
	    		  compinfo.base_comp_name=base_comp_name;
	    		  //公司添加类型
	    		  compinfo.comptype=comptype;
	    		  //公司联系人code
	    		  compinfo.base_entrepreneur_code=base_entrepreneur_code;
	    		  //公司联系人name
	    		  compinfo.base_entrepreneur_name=base_entrepreneur_name;
	    		  //公司联系人类型
	    		  compinfo.entrepreneurtype=entrepreneurtype;
	    		  if(base_entrepreneur_name==null
	    				  ||base_entrepreneur_name=="" ){
	    			  $("#entrepreneurposiname").val("");
	    		  }
	    		  //公司联系人职位
	    		  compinfo.base_entrepreneur_posiname=$("#entrepreneurposiname").val();
	    		  
	    		  //是否创建公司融资计划
	    		  compinfo.financplanflag=$("#planshow").is(':checked');
	    		  //公司融资计划计划日期
	    		  compinfo.base_financplan_date=$("#plantime").val();
	    		  //公司融资计划提醒日期
	    		  compinfo.base_financplan_remindate=$("#emailtime").val();
	    		//公司融资计划内容
	    		  compinfo.base_financplan_cont=$("#palntext").val();

                compInfoList[i]=compinfo;

                savedata.searchcom="";
                savedata.searchinv="";

                layer.closeAll('page');
                initComp();
	    	  }
         }
    });
   }
});

//相关公司移出
$("[compremove]").unbind().bind("click", function () {
    var i=$(this).attr("i");
    compInfoList.baoremove(Number(i));
    initComp();
});
};
//公司选择页面
function compinfoselect_ajax() {
    //选择公司
    opt.$compselect=$("#compid").select2({
        placeholder:"请选择公司",//文本框的提示信息
        minimumInputLength:0,   //至少输入n个字符，才去加载数据
        allowClear: true,  //是否允许用户清除文本信息
        ajax:{
            url:"querycompanylistByname.html",
            dataType:"json",
            cache: true,
            type:"post",
            delay: 250,//加载延迟
            data: function (params) {

                savedata.searchcom=(params.term||"");

                return{
                    name:params.term||"",
                    pageSize:CommonVariable().SELSECLIMIT,
                    pageCount:"1",
                    logintype:cookieopt.getlogintype(),
                    type:"1"
                };
            },
            processResults: function (data, page) {
                for(var i=0;i<data.list.length;i++){
                    data.list[i].id=data.list[i].base_comp_code;
                    data.list[i].text=data.list[i].base_comp_name;
                }
                return {
                    results: data.list//返回结构list值
                };
            }

        },
        escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
        templateResult: formatRepo // 格式化返回值 显示再下拉类表中
    });
    
  //公司发生变化清空联系人信息
    $("#compid").on("change", function() { 
    	if($("#btn_comp_add").hasClass("btn_icon_add")){
    	//公司发生变化
    			//若现在联系人为添加状态，职位可输入
			   if($("#btn_entrepreneur_add").hasClass("btn_icon_search")){
				   $("#entrepreneurposiname").removeAttr("readonly");
	            			}
			   //若现在联系人为搜索状态，初始化搜索，职位不可输入
	       else if($("#compid").val()!=null
	    	   &&$("#compid").val().trim()!="" ){
	            $("#entrepreneur").parent().html('<select id="entrepreneur" class=" inputdef"><option></option></select>');
	            		entrepreneurselect_ajax();
	    				   $("#entrepreneurposiname").val("");
	    				   $("#entrepreneurposiname").attr("readonly","readonly");
	            	}
	       else{
	    	   $("#entrepreneur").parent().html('<select id="entrepreneur" class=" inputdef"><option></option></select>');
	    	   entrepreneurselect_ajax();
		    	 opt.$entrepreneurselect.prop("disabled", true);
				   $("#entrepreneurposiname").val("");
				   $("#entrepreneurposiname").attr("readonly","readonly");
	       }
    	}
    	}); 
}
function  compinfoadd_ajax() {

    //添加公司点击
    $("#btn_comp_add").unbind().bind("click", function () {
    	//现在是搜索切换为添加，职位允许输入
    	if($("#btn_comp_add").hasClass("btn_icon_add")){
        	$("#compid").parent().html('<input value="" id="compid" type="text" class="inputdef" maxlength="20">');

            $("#compid").val(savedata.searchcom);

            $("#btn_comp_add").removeClass("btn_icon_add").addClass("btn_icon_search");
    	       $("#entrepreneurposiname").removeAttr("readonly");
				   		if($("#btn_entrepreneur_add").hasClass("btn_icon_add")){
				   			entrepreneurselect_ajax();
				    		opt.$entrepreneurselect.prop("disabled", false);
					  	}
    	}
    	//现在是添加切换为搜索，职位不允许输入，初始化公司筛选
    	else{
    		$("#compid").parent().html('<select id="compid" class=" inputdef" ><option></option></select>');
      $("#btn_comp_add").removeClass("btn_icon_search").addClass("btn_icon_add");
    		compinfoselect_ajax();
				$("#entrepreneurposiname").val("");
				//处理联系人
	   		if($("#btn_entrepreneur_add").hasClass("btn_icon_add")){
	        $("#entrepreneurposiname").attr("readonly","readonly");
          opt.$entrepreneurselect.val("").trigger("change");
	   	   		entrepreneurselect_ajax();
	   	   		opt.$entrepreneurselect.prop("disabled", true);
	           }
	     else{
	         $("#entrepreneurposiname").removeAttr("readonly");
	            }
    	}
    });

}

//联系人选择页面
function entrepreneurselect_ajax() {
	var base_comp_code;
	if($("#btn_comp_add").hasClass("btn_icon_add")){
		base_comp_code=$("#compid").val()  ;
						}
	else{
		base_comp_code="";
					}
    //选择联系人
    opt.$entrepreneurselect=$("#entrepreneur").select2({
        placeholder:"请选择联系人",//文本框的提示信息
        minimumInputLength:0,   //至少输入n个字符，才去加载数据
        allowClear: true,  //是否允许用户清除文本信息
        ajax:{
            url:"queryentrepreneurlistBycompcode.html",
            dataType:"json",
            cache: true,
            type:"post",
            delay: 250,//加载延迟
            data: function (params) {

                savedata.searchinv=(params.term||"");

                return{
                    name:params.term||"",
                    pageSize:CommonVariable().SELSECLIMIT,
                    pageCount:"1",
                    logintype:cookieopt.getlogintype(),
                    compcode:base_comp_code,
                    type:"1"
                };
            },
            processResults: function (data, page) {
                for(var i=0;i<data.list.length;i++){
                    data.list[i].id=data.list[i].base_entrepreneur_code;
                    data.list[i].text=data.list[i].base_entrepreneur_name;
                   
                }
                return {
                    results: data.list//返回结构list值
                };
            }

        },
        escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
        templateResult: formatRepo // 格式化返回值 显示再下拉类表中
    });
    
  //联系人发生变化职位信息信息
    $("#entrepreneur").on("change", function() { 
    	if($("#btn_entrepreneur_add").hasClass("btn_icon_add")){
		        //职位
		  $("#entrepreneurposiname").val(opt.$entrepreneurselect.select2("data")[0].base_entrepreneur_posiname);
    		
    	}
    	}); 
}
	function  entrepreneuradd_ajax() {

        //添加联系人点击
        $("#btn_entrepreneur_add").unbind().bind("click", function () {
        	if($("#btn_entrepreneur_add").hasClass("btn_icon_add")){
        	$("#entrepreneur").parent().html('<input value="" id="entrepreneur" type="text" class="inputdef" maxlength="20">');

            $("#entrepreneur").val(savedata.searchinv);

                $("#btn_entrepreneur_add").removeClass("btn_icon_add").addClass("btn_icon_search");
        	$("#entrepreneurposiname").val("");
			   	$("#entrepreneurposiname").removeAttr("readonly");
        	}
        	else{
        		$("#entrepreneur").parent().html('<select id="entrepreneur" class=" inputdef" ><option></option></select>');
          $("#btn_entrepreneur_add").removeClass("btn_icon_search").addClass("btn_icon_add");
        		entrepreneurselect_ajax();
        		if($("#compid").val()!=null
     	    	   && $("#compid").val().trim()!=""){
        			opt.$entrepreneurselect.prop("disabled", false);
        			}
        		else{
        			opt.$entrepreneurselect.prop("disabled", true);
        		}
           $("#entrepreneurposiname").val("");
           if($("#btn_comp_add").hasClass("btn_icon_add")){
           $("#entrepreneurposiname").attr("readonly","readonly");
           				}
           else{
        		$("#entrepreneurposiname").removeAttr("readonly");
           			}
        	}
        });
		   
    }
function compadd_ajax() {
		$.showloading();
		$.ajax({
			async : true,
			dataType : 'json',
			type : 'post',
			url : "queryCompOnlyNamefortrade.html",
			data : {
				name : $("#compid").val().trim(),
				logintype : cookieopt.getlogintype()
			},
			success : function(data) {
				$.hideloading();
				if (data.message == "nodata") {
					addcompbool= true;
				}
				else if(data.message == "exsit"){
					addcompbool= false;
				
				}else {
					addcompbool= false;
				}
			}
		});
	};
	//融资计划选择
	var compplanClick=function(){
		$("#planshow").unbind().bind("click",function(e){
			if($("#planshow").is(':checked')){
			$("#planshow").parents("ul[class='inputlist']").children().eq(4).removeAttr("style");
	   $("#planshow").parents("ul[class='inputlist']").children().eq(5).removeAttr("style");
	   $("#planshow").parents("ul[class='inputlist']").children().eq(6).removeAttr("style");
	   $('#plantime').datepicker();
	   
	   $('#emailtime').datepicker();
			}
			else{
				//公司融资计划计划日期
	    		  $("#plantime").val("");
	    		//公司融资计划提醒日期
	    		  $("#emailtime").val("");
	    		//公司融资计划内容
	    		  $("#palntext").val("");
				$("#planshow").parents("ul[class='inputlist']").children().eq(4).css("display","none");
				$("#planshow").parents("ul[class='inputlist']").children().eq(5).css("display","none");
				$("#planshow").parents("ul[class='inputlist']").children().eq(6).css("display","none");
		}
		});
	};

    function formatRepo (repo) {
        return repo.text;
    }
    return{
    	CompInfoLoad:initComp,
    	CompAddClick:addcompClick
    };
})();

//标签弹出层集合
function dicListConfig(vDel,vList,vObj){
    var list=[];
    var map={};
    for(var i=0;i<vList.length;i++){
        map={};
					map.name = vList[i].name;
					map.id = vList[i].code;
        if(vObj!=null){
            for ( var j = 0; j < vObj.length; j++) {
            	//判断标签是否被选中,存在则加上选中标识
                if(vList[i].code==vObj[j].code){
                    map.select=true;
                }	
            }
        }
        list.push(map);
    }
    return list;
}

//标签弹出层集合
function dicTypeListConfig(vDel,vList,vObj){
    var list=[];
    var map={};
    for(var i=0;i<vList.length;i++){
        map={};
		map.name = vList[i].sys_labelelement_name;
		map.id = vList[i].sys_labelelement_code;
        if(vObj!=null){
            for ( var j = 0; j < vObj.length; j++) {
            	//判断标签是否被选中,存在则加上选中标识
                if(vList[i].sys_labelelement_code==vObj[j].code){
                    map.select=true;
                }	
            }
        }
        list.push(map);
    }
    return list;
}

//弹层选择筐选择值,返回[{code:'',name:''}]
function choiceContent(){
    var list="[";
    var num=0;
    var newNum=0;
    $(".list li[class='active']").each(function(){
        num++;
    });
    $(".list li[class='active']").each(function(){
        newNum++;
        list+="{code:'"+$(this).attr("tip-l-i")+"',name:'"+$(this).text()+"'}";
        if(newNum<num){
            list+=",";
        }
    });
    list+="]";
    return list;
}

//弹层选择筐选择值,返回[{code:'',name:''}]
function oldContent(vObj){
    var list="[";
    if(vObj!=null){
        for ( var i = 0; i < vObj.length; i++) {
            list+="{code:'"+vObj[i].code+"',name:'"+vObj[i].name+"'}";
            if(i<vObj.length-1){
                list+=",";
            }
        }
    }
    list+="]";
    return list;
}
//筛选条件组展示更多
function showmore(a){
    if($(a).parents(".shgroup").hasClass("heightauto")){
        $(a).parents(".shgroup").removeClass("heightauto");
        $(a).html(' 更多<span class="glyphicon glyphicon-chevron-down" ></span>');
    }else{
        $(a).parents(".shgroup").addClass("heightauto");
        $(a).html(' 收起<span class="glyphicon glyphicon-chevron-up" ></span>');

    }
}

//刷新操作标签
function refreshladle(a){
	var shgroup=$(a).parents(".shgroup");
	if(shgroup.hasClass("heightauto")){
		shgroup.removeClass("heightauto");
		if( $(a)[0].scrollHeight>shgroup.height()){
			if(!shgroup.hasClass("havemore")){
				shgroup.addClass("havemore");
			}	 
	    }else{
	        shgroup.removeClass("havemore");
	    }
		 shgroup.addClass("heightauto");
	}else{
		if( $(a)[0].scrollHeight>shgroup.height()){
			if(!shgroup.hasClass("havemore")){
				shgroup.addClass("havemore");
                a.parents(".tiplist").next(".more").removeClass("display-none").addClass("display-block");
			}
	    }else{
	        shgroup.removeClass("havemore");
            a.parents(".tiplist").next(".more").removeClass("display-block").addClass("display-none");
	    }
	}
	$(".havemore .more").unbind().bind("click",function () {
		 showmore($(this));
	});
}
//提交按钮
function addmeetingsub(){
	
    $("#addmeetingsub").unbind().bind("click", function () {
    	for ( var i = 0; i < addmeetinglabel.data.length; i++) {
			if(addmeetinglabel.data[i].filename!=null
					&&addmeetinglabel.data[i].filename!=""){
				if(!addmeetinglabel.data[i].filename.match(att)){
					$.showtip("存在不支持上传格式的文件");
					return;
				}
			}
		}

    	//会议日期
    	var meetingdate=new Date($("#meetingdate").val());
    	//会议内容
    	var meetingcontent=$("#meetingcontentid").val().trim();
    	//验证必须选择会议日期输入会议内容
    	if($("#meetingdate").val().trim()==""){
				$.hideloading();
				$.showtip("请选择会议日期");
				return;
    	}
    	else if(isNaN(meetingdate.getTime())){
				$.hideloading();
	    	$.showtip("请重新选择会议日期");
		    	return;
            }
    else if(meetingcontent==null || ""==meetingcontent){
    	$.hideloading();
    	$.showtip("会议内容不可为空");
	    	return;
    }
    else{
		meetingdate=meetingdate.format(meetingdate,"yyyy-MM-dd");
		 //备注
		//2015/12/10 bug336 rqq ModStart
		 var noteinfo=$("#noteinfoid").val();
            $("#addmeetingsub").attr("disabled","disabled");
            if(noteinfo.trim()==""){
                noteinfo="";
            }
		 meetingcontent=$("#meetingcontentid").val();
		//2015/12/10 bug336 rqq ModEnd
		 
		 $.showloading();
			$.ajax({
				async : true,
				dataType : 'json',
				type : 'post',
				url :"addmeetinginfo.html",
				data : {
					meetingdate : meetingdate,
					sharetype : sharetype,
					investmentInfoList:JSON.stringify(investmentInfoList),
					compInfoList:JSON.stringify(compInfoList),
					parpuserJson:JSON.stringify(parpuserJson),
					sharewadJson:JSON.stringify(sharewadJson),
					meetingtypeJson:JSON.stringify(meetingtypeJson),
					meetingcontent:meetingcontent,
					noteinfo:noteinfo,
					logintype : cookieopt.getlogintype()
				},
				success : function (json){
						if (json.message == "success") {
							$.showtip("保存成功");
							addmeeting.data.meetingcode=json.meetingcode;
							addmeeting.upfile();
						}else if (json.message == "compnameexsit") {
							$.hideloading();
				            $("#addmeetingsub").removeAttr("disabled");
							$.showtip(json.messagedetail);
							return;
						}else if(json.message == "invesnameexsit"){
							$.hideloading();
				            $("#addmeetingsub").removeAttr("disabled");
							$.showtip(json.messagedetail);
						return;
						}else {
							$.hideloading();
				            $("#addmeetingsub").removeAttr("disabled");
							$.showtip("保存失败","error");
							return;
						}
				   },
                error: function () {
                    $("#addmeetingsub").removeAttr("disabled");
                }
			});
    }
    });
    }
   
   //选择上传附件
   var addmeeting=(function(){
		var data={
				meetingcode:""
		};
		
		var upajax=function(){
			if(data.filename!=""&&data.meetingcode!=null&&data.meetingcode!=""){
				var fileId=$("input[name=file]");
				fileId.each(function(){
					var elementid=$(this).attr("id");
                    $.ajaxFileUpload({
 			    	   url:"AddMeetingInfo.html?logintype="+cookieopt.getlogintype()
 			    		   +"&meetingcode="+data.meetingcode,//需要链接到服务器地址
 			           type:'post',
 			           secureuri:true,
 			           fileElementId:elementid,  //文件选择框的id属性
 			           dataType: 'json', //服务器返回的格式，可以是json
 			           success:function(json){
 			        	   if(json.message!="success"){
 			        		   $.showtip("附件上传失败，可到会议详情重新上传");
 			        	   }
 			        	   $.hideloading();
 			        	   
 			           },error:function(){
 				    	   $.showtip("附件上传请求异常，可到会议详情重新上传");
 				    	   $.hideloading();
 				       }
                    });
                });
				setTimeout(function() {
	                   $("#addmeetingsub").removeAttr("disabled");
	                   window.location.href="addMeetingInfoinit.html?logintype="+cookieopt.getlogintype();
	   				}, 2000);
			}else{
				$.hideloading();
			}
			
		};
		
		return {
			upfile:upajax,
			data:data
		};
	})();
 
//会议内容上传附件添加按钮   
var addmeetinglabel=(function(){
	var data=[{
			filename:""
	}];
	
	var delclick=function(){
		$(".filedelete").unbind().bind("click",function(){
			var i=$(this).attr("del");
			
			if(i>0){
				data[i].filename="";
				$(this).parent().remove();
			}else{
				if(data[i].filename!=null&&data[i].filename!=""){
					data[i].filename="";
					var html="<input id=\"file0\" i=\"0\" name=\"file\" type=\"file\"/>选择文件</a>";
					$("input[i=0]").parent().html(html);
					choiceclick();
					$("span[i="+i+"]").html(data[i].filename);
				}
			}
			
		});
	};
	
	var choiceclick=function(){
		
		$("input[name=file]").on("change",function(e){
			
			var i=$(this).attr("i");
			var fileName="";
			if(e.currentTarget.files.length>0){
				fileName=e.currentTarget.files[0].name;
//				data[i].filename=fileName;
				if(fileName.match(att)){
					data[i].filename=fileName;
				}else{
					$.showtip("不支持上传此类型文件");
					data[i].filename="";
					var html="<input id=\"file"+$(this).attr("i")+"\" i=\""
					+$(this).attr("i")+"\" name=\"file\" type=\"file\"/>选择文件</a>";
					$(this).parent().html(html);
					choiceclick();
					$.hideloading();
				}
			}else{
				data[i].filename="";
			}
			
			$("span[i="+i+"]").html(data[i].filename);
		});
	};
	
	var addclick=function(){
		$(".add_meetingfile").unbind().bind("click",function(){
			var len=data.length;
			var html="<div number=\""+len+"\" class=\"tab_content_table meetingfile\">"+
			"<a id=\"achoice"+len+"\" href=\"javascript:;\" class=\"choicefile\">"+
			"<input id=\"file"+len+"\" i=\""+len+"\" name=\"file\" type=\"file\"/>选择文件</a>"+
			"<span class=\"filename \" i=\""+len+"\"></span><span class=\"btn_delete filedelete \"  del=\""+len+"\"></span>"+
			"</div>";
			
			$(".meetingfilesdiv").append(html);
			data.push({filename:""});
			
			choiceclick();
			delclick();
		});
	};
	
	return {
		data:data,
		addclick:addclick,
		choiceclick:choiceclick,
		delclick:delclick
	};
})();

//会议类型
var meetingTypeConfig=(function(){
	var initMeetingType=function(){
		var html = "";
		if (meetingtypeJson != null && meetingtypeJson != "" ) {
			var data = {
				list : meetingtypeJson
			};
			html = template.compile(table_data.labelInfoList)(data);
		} else {
			html = table_data.labelNull;
		}
		$("#meetingType").html(html);
//		refreshladle($("#meetingType"));
	};
	
	var click=function(){
		var meetingtypeshowlist = dicTypeListConfig(false, meetingTypeList, meetingtypeJson);
		$("#meetingtype_id").unbind().bind("click", function() {
			var $this = $(this).children("div").children("span");
			tip_edit.config({
		     $this:$this,
		     title:"会议类型",
				list : meetingtypeshowlist,
				radio : true,
				besure : function() {
					meetingtypeJson = eval(choiceContent());
					tip_edit.close();
					initMeetingType();
				}
			});
		});

	};
	
	return {
		click:click
	};
})();

var table_data = {
		investmentInfoList : '<%for(var i=0;i<list.length;i++){%>'
				+ '<li id="<%=list[i].base_investment_code%>" p-index="<%=i%>">'
				+ '<span class="comp"><%=list[i].base_investment_name%></span>'
				+ '<span class="comp"><%=list[i].base_investor_name%></span>'
				+ '<span class="comp"><%=list[i].base_investor_posiname%></span>'
				+	'<span class="comp orremove " touziedit i="<%=i%>">修改</span>' 
		   +'<span class="comp orremove " touziremove i="<%=i%>">删除</span>' 
				+ '</li>'
				+ '<%}%>',
		compInfoList : '<%for(var i=0;i<list.length;i++){%>'
			+ '<li id="<%=list[i].base_comp_code%>" p-index="<%=i%>">'
			+ '<span class="comp"><%=list[i].base_comp_name%></span>'
			+ '<span class="comp"><%=list[i].base_entrepreneur_name%></span>'
			+ '<span class="comp"><%=list[i].base_entrepreneur_posiname%></span>'
			+ '<span class="comp orremove " compedit i="<%=i%>">修改</span>' 
			+ '<span class="comp orremove " compremove i="<%=i%>">删除</span>' 
			+ '</li>'
			+ '<%}%>',
		labelInfoList : '<%for(var i=0;i<list.length;i++){%>'
				+ '<li data-i="<%=i%>"><%=list[i].name%></li>' + '<%}%>',
		labelNull : '<li></li>'
	};
