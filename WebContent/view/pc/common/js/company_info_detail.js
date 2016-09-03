/**
 * Created by shbs-tp004 on 15-9-13.
 */
var optactive = 0;
var menuactive = 2;
var type;
var oldstr;
var peopleName;
var phone;
var email;
var wechat;
var peoplecode;
var posi;
var refBool=true;
var tablelist;
var submitflg=0;//添加联系人控制重复提交
var submitrz=0;//融资计划按钮控制多次点击
$(function() {
	config.init();
    add_rz.click_config();
    addTrade.click();
});
var config = (function() {
	var data = {
		viewCompInfo : viewCompInfo,
		inducont : viewCompInfo.view_comp_inducont == null ? []
				: eval(viewCompInfo.view_comp_inducont),
		bask : viewCompInfo.view_comp_baskcont == null ? []
				: eval(viewCompInfo.view_comp_baskcont),
		induList : induList,
		stageList : stageList,
		baskList : baskList,
		noteList : eval(noteList),
		financplanList : eval(financplanList),
		financList : eval(financList),
		version : version,
		noteconfignum : CommonVariable().NOTECONFIGNUM,
		entrepreneurList : eval(entrepreneurList),
		zhangkai : false,
		flanflg : false
	};
	var init = function() {
		if (data.viewCompInfo.base_comp_name == null
				|| data.viewCompInfo.base_comp_name.trim() == "") {
			$(".name").html("简称(未填写)");
		} else {
			$(".name").html(data.viewCompInfo.base_comp_name);
			$(".comp-input").val(data.viewCompInfo.base_comp_name);
		}
		if (data.viewCompInfo.base_comp_fullname == null
				|| data.viewCompInfo.base_comp_fullname.trim() == "") {
			$(".fullname").html("全称(未填写)");
		} else {
			$(".fullname").html(data.viewCompInfo.base_comp_fullname);
			$(".compfullname-input").val(data.viewCompInfo.base_comp_fullname);
		}
		if (data.viewCompInfo.base_comp_ename == null
				|| data.viewCompInfo.base_comp_ename.trim() == "") {
			$(".ename").html("英文名称(未填写)");
		} else {
			$(".ename").html(data.viewCompInfo.base_comp_ename);
			$(".compename-input").val(data.viewCompInfo.base_comp_ename);
		}
		var inducont = template.compile(data_model.inducont)(data);
		var bask = template.compile(data_model.bask)(data);
		var financplanList = template.compile(data_model.financplanList)(data);
		var financList = template.compile(data_model.financList)(data);
		var noteList = template.compile(data_model.noteList)(data);
		var entrepreneurList = template.compile(data_model.entrepreneurList)(data);
		$(".bask").html(bask);
		$(".induc").html(inducont);
		$(".stagea").html(data.viewCompInfo.base_comp_stagecont);
		notepost.noteconfig();
		$(".financ").html(
				financList == "" ? '<tr class="nohover"><td colspan=\'5\'>暂无数据</td></tr>'
						: financList);
		$(".financ tr").each(function(index, e) {$(this).bind("click",function() {
												if (data.financList != null) {
													window.location.href = "findTradeDetialInfo.html?tradeCode="+data.financList[index].base_trade_code
															+ "&logintype="+ cookieopt.getlogintype()+"&backherf=findCompanyDeatilByCode.html?code="+viewCompInfo.base_comp_code;;
												}

											});
						});
		$(".financplan").html(
				financplanList == "" ? '<tr class="nohover"><td colspan=\'2\'>暂无数据</td></tr>'
						: financplanList);
		$(".people").html(entrepreneurList);

		if (data.noteconfignum < data.financplanList.length) {
			$(".closefinacplan").show();
			$("[finamores]").hide();
		}
		refBool=false;
        delTradeconfig.click(refBool);//调用删除融资信息
		click();
		
		if(backtype!=""){
			$(".goback").show();
			$(".goback").unbind().bind("click",function(){
				if(backtype=="searchcompany"
					||backtype=="searchcname"){
					window.location.href="company_search.html?logintype="
						+cookieopt.getlogintype()+"&backtype="+backtype;
				}else if(backtype=="searchtrade"){
                    window.location.href="trade_search.html?logintype="
                        +cookieopt.getlogintype()+"&backtype="+backtype;
                }else if(backtype=="searchfoot"){
                    window.location.href="myfoot_search.html?logintype="
                        +cookieopt.getlogintype()+"&backtype="+backtype;
                }
			});
			
		}
	};
	//保存公司简称按钮
	$(".savecompname").unbind().bind("click", function() {
		editname_comp.editname();
	});
	//保存公司全称
	$(".savefullname").unbind().bind("click", function() {
		editname_comp.editfullname();
	});
	//保存公司英文名称
	$(".saveename").unbind().bind("click", function() {
		editname_comp.editename();
	});
	//添加投资机构
	$("#btn_people_add").unbind().bind("click", function() {
		addenupeople.init();
	});

	var click = function() {
		alone_edit.config_function();
		$(".people li ul")
				.each(
						function(index) {
							//保存职位
							$(this)
									.find(".savecontposi")
									.unbind()
									.bind(
											"click",
											function() {
												var text = $(this).prev().val();
												if (config.data.entrepreneurList[index].base_entrepreneur_posiname != null
														&& config.data.entrepreneurList[index].base_entrepreneur_posiname == text) {
													$.showtip("职位未改变");
												} else {
													oldstr = olddata(index);
													peoplecode = config.data.entrepreneurList[index].base_entrepreneur_code;
													phone = config.data.entrepreneurList[index].base_entrepreneur_phone;
													posi = text.trim();
													peopleName = config.data.entrepreneurList[index].base_entrepreneur_name;
													email = config.data.entrepreneurList[index].base_entrepreneur_email;
													wechat = config.data.entrepreneurList[index].base_entrepreneur_wechat;
													editPeople_ajax("updatePeople.html");
													alone_edit.close($(this)
															.find(".canEdit"));
												}
											});
							//保存姓名
							$(this)
									.find(".savecontname")
									.unbind()
									.bind(
											"click",
											function() {
												var text = $(this).prev().val();
												if (config.data.entrepreneurList[index].base_entrepreneur_name != null
														&& config.data.entrepreneurList[index].base_entrepreneur_name == text.trim()) {
													$.showtip("姓名未改变");
												}else if(text.trim()==""){
													$.showtip("姓名不能为空");
												} else {
													oldstr = olddata(index);
													peoplecode = config.data.entrepreneurList[index].base_entrepreneur_code;
													phone = config.data.entrepreneurList[index].base_entrepreneur_phone;
													peopleName = text.trim();
													posi = config.data.entrepreneurList[index].base_entrepreneur_posiname;
													email = config.data.entrepreneurList[index].base_entrepreneur_email;
													wechat = config.data.entrepreneurList[index].base_entrepreneur_wechat;
													editPeople_ajax("updatePeople.html");
													alone_edit.close($(this)
															.find(".canEdit"));
												}
											});
							//保存手机
							$(this)
									.find(".savecontphone")
									.unbind()
									.bind(
											"click",
											function() {
												var text = $(this).prev().val();
												if (config.data.entrepreneurList[index].base_entrepreneur_phone != null
														&& config.data.entrepreneurList[index].base_entrepreneur_phone == text) {
													$.showtip("手机未改变");
												} else {
													oldstr = olddata(index);
													peoplecode = config.data.entrepreneurList[index].base_entrepreneur_code;
													phone = text.trim();
													peopleName = config.data.entrepreneurList[index].base_entrepreneur_name;
													posi = config.data.entrepreneurList[index].base_entrepreneur_posiname;
													email = config.data.entrepreneurList[index].base_entrepreneur_email;
													wechat = config.data.entrepreneurList[index].base_entrepreneur_wechat;
													if (check(phone, "")) {
														editPeople_ajax("updatePeople.html");
														alone_edit
																.close($(this)
																		.find(
																				".canEdit"));
													}

												}
											});
							//保存微信
							$(this).find(".savecontwechat").unbind().bind("click",function() {
												var text = $(this).prev().val();
												if (config.data.entrepreneurList[index].base_entrepreneur_wechat != null
														&& config.data.entrepreneurList[index].base_entrepreneur_wechat == text) {
													$.showtip("微信未改变");
												} else {
													oldstr = olddata(index);
													peoplecode = config.data.entrepreneurList[index].base_entrepreneur_code;
													phone = config.data.entrepreneurList[index].base_entrepreneur_phone;
													peopleName = config.data.entrepreneurList[index].base_entrepreneur_name;
													posi = config.data.entrepreneurList[index].base_entrepreneur_posiname;
													email = config.data.entrepreneurList[index].base_entrepreneur_email;
													wechat = text.trim();
													editPeople_ajax("updatePeople.html");
													alone_edit.close($(this)
															.find(".canEdit"));

												}
											});
							//保存邮箱
							$(this)
									.find(".savecontemail")
									.unbind()
									.bind(
											"click",
											function() {
												var text = $(this).prev().val();
												if (config.data.entrepreneurList[index].base_entrepreneur_email != null
														&& config.data.entrepreneurList[index].base_entrepreneur_email == text) {
													$.showtip("邮箱未改变");
												} else {
													oldstr = olddata(index);
													peoplecode = config.data.entrepreneurList[index].base_entrepreneur_code;
													phone = config.data.entrepreneurList[index].base_entrepreneur_phone;
													peopleName = config.data.entrepreneurList[index].base_entrepreneur_name;
													posi = config.data.entrepreneurList[index].base_entrepreneur_posiname;
													email = text.trim();
													wechat = config.data.entrepreneurList[index].base_entrepreneur_wechat;
													if (check("", email)) {
														editPeople_ajax("updatePeople.html");
														alone_edit
																.close($(this)
																		.find(
																				".canEdit"));
													}

												}
											});
						});
		//参与交易更多
		$(".lockmore").unbind().bind("click",function() {
							layer.open({
										type : 1,
										title : '融资信息',
										shadeClose : false,
										shade : 0.6,
										scrollbar : false,
                                skin: 'layui-layer-rim', //加上边框
										maxmin : false, //开启最大化最小化按钮
										area : [ '700px', '350px' ],
										content : '<div class=" tran_content_table tran_content tradebox" ><table><thead><tr class="nohover"><th width="20%">投资日期</th><th width="20%">阶段</th><th width="20%">金额</th><th width="30%">投资机构</th><th width="10%">操作</th></tr></thead><tbody class="stagemore"></tbody></table><div class="customBootstrap"><ul class="totalSize" id="totalsize"　></ul><ul class="pagination" id="pagination1"></ul></div></div>'
									});
            trade_more.init();
						});
        //会议记录
        $('.meet_record').unbind().bind("click",function(){
            layer.open({
                type: 1,
                title: '会议记录',
                shadeClose: false,
                shade: 0.6,
                skin: 'layui-layer-rim', //加上边框
                scrollbar: false,
                maxmin: false, //开启最大化最小化按钮
                area: ['700px', '350px'],
                content: '<div class=" tran_content_table tran_content tradebox" ><table><thead><tr class="nohover"><th width="20%">时间</th><th width="30%">记录人</th><th width="50%">公司/机构</th></tr></thead><tbody class="meetingtable"></tbody></table><div class="customBootstrap"><ul class="totalSize" id="totalsize"　></ul><ul class="pagination" id="pagination1"></ul></div></div>'
            });
            meeting.init();
        });
        //更新记录
        $('.update_record').unbind().bind("click",function(){
            layer.open({
                type: 1,
                title: '更新记录',
                shadeClose: false,
                shade: 0.6,
                scrollbar: false,
                skin: 'layui-layer-rim', //加上边框
                maxmin: false, //开启最大化最小化按钮
                area: ['700px', '350px'],
                content: '<div class=" tran_content_table tran_content tradebox" ><table><thead><tr class="nohover"><th width="20%">时间</th><th width="20%">更新人</th><th width="20%">操作</th><th width="40%">内容</th></tr></thead><tbody class="updatetable"></tbody></table><div class="customBootstrap"><ul class="totalSize" id="totalsize"　></ul><ul class="pagination" id="pagination1"></ul></div></div>'
            });
            updaterecord.init();
        });
		$(".closefinacplan")
				.unbind()
				.bind(
						"click",
						function() {
							if (!$(this).hasClass("active")) {
								$("[finamores]").show();
								$(this)
										.html(
												'收起<span class="glyphicon glyphicon-chevron-up"></span>')
										.addClass("active");
								click();
								data.flanflg = true;
							} else {
								$("[finamores]").hide();
								$(this)
										.html(
												'更多<span class="glyphicon glyphicon-chevron-down"></span>')
										.removeClass("active");
								data.flanflg = false;
							}
						});

		$(".closeshearch")
				.unbind()
				.bind(
						"click",
						function() {
							if (!$(this).hasClass("active")) {
								$("[nodemore]").show();
								$(this)
										.html(
												'收起<span class="glyphicon glyphicon-chevron-up"></span>')
										.addClass("active");
								click();
								data.zhangkai = true;
							} else {
								$("[nodemore]").hide();
								$(this)
										.html(
												'更多<span class="glyphicon glyphicon-chevron-down"></span>')
										.removeClass("active");
								data.zhangkai = false;
							}
						});
		$(".closefinacplan")
				.unbind()
				.bind(
						"click",
						function() {
							if (!$(this).hasClass("active")) {
								$("[finamores]").show();
								$(this)
										.html(
												'收起<span class="glyphicon glyphicon-chevron-up"></span>')
										.addClass("active");
								click();
								data.flanflg = true;
							} else {
								$("[finamores]").hide();
								$(this)
										.html(
												'更多<span class="glyphicon glyphicon-chevron-down"></span>')
										.removeClass("active");
								data.flanflg = false;
							}
						});
	};
    //融资计划
    var rzconfig= function () {
        var financplanList=template.compile(data_model.financplanList)(config.data);
        $(".financplan").html(financplanList==""?'<tr class="nohover"><td colspan=\'2\'>暂无数据</td></tr>':financplanList);
        if(config.data.noteconfignum>=config.data.financplanList.length){

            $(".closefinacplan").html('更多<span class="glyphicon glyphicon-chevron-down"></span>').removeClass("active").hide();
        }else{
            if(!$(".closefinacplan").hasClass("active")){
                $(".closefinacplan").html('更多<span class="glyphicon glyphicon-chevron-down"></span>').show();
            }

        }
        if(!data.flanflg){
            $("[finamores]").hide();
        }

        click();
    };
	//添加备注
	$(".savenote").unbind().bind("click", function() {
		notepost.add();
	});
	return {
		init : init,
		data : data,
		//        noteconfig:noteconfig,
		        rzconfig:rzconfig,
		click : click
	};
})();
//编辑公司名称
var editname_comp = (function() {
    var oldstr={};

    function olddata_fu() {
        oldstr.name=config.data.viewCompInfo.base_comp_name;
        oldstr.fullname=config.data.viewCompInfo.base_comp_fullname;
        oldstr.ename=config.data.viewCompInfo.base_comp_ename;
    }
	function editname() {
		var compName = $(".comp-input").val();
		if (compName == config.data.viewCompInfo.base_comp_name) {
			$.showtip("名称未改变");
		} else if (compName.trim() == "") {
			$.showtip("简称不能为空");
		} else {
            olddata_fu();
			config.data.viewCompInfo.base_comp_name = compName.trim();
			editname_ajax();
		}
	}
	function editfullname() {
		var fullName = $(".compfullname-input").val();
        if(config.data.viewCompInfo.base_comp_fullname==null&&fullName==""){
            $.showtip("全称未改变");
        }
		if (fullName == config.data.viewCompInfo.base_comp_fullname) {
			$.showtip("全称未改变");
		} else {
			alone_edit.close($(".fullname"));
            olddata_fu();
			config.data.viewCompInfo.base_comp_fullname = fullName.trim();
			editname_ajax();
		}
	}
	function editename() {
		var compename = $(".compename-input").val();
        if(config.data.viewCompInfo.base_comp_ename==null&&compename==""){
            $.showtip("英文名称未改变");
        }
		if (compename == config.data.viewCompInfo.base_comp_ename) {
			$.showtip("英文名称未改变");
//		} else if (compename.trim() == "") {
//			alone_edit.close($(".ename"));
//			editname_ajax()
		} else {
			alone_edit.close($(".ename"));
            olddata_fu();
			config.data.viewCompInfo.base_comp_ename = compename.trim();
			editname_ajax();
		}
	}
	//编辑公司调的ajax
	function editname_ajax() {
		$.showloading();
		$.ajax({
			url : "updateName.html",
			type : "post",
			async : true,
			data : {
				code : config.data.viewCompInfo.base_comp_code,
				name : config.data.viewCompInfo.base_comp_name,
				fullname : config.data.viewCompInfo.base_comp_fullname,
				ename : config.data.viewCompInfo.base_comp_ename,
				version : config.data.version,
				oldstr : "简称：" + oldstr.name
						+ ";全称：" + oldstr.fullname
						+ ";英文名称：" + oldstr.ename,
				logintype : cookieopt.getlogintype()
			},
			dataType : "json",
			success : function(data) {
				$.hideloading();
				if (data.message == "success") {
					$.showtip("保存成功");
					config.data.viewCompInfo = data.viewCompInfo;
					config.data.version = data.version;
                    alone_edit.close($(".name"));
					name_compShow();
				} else if (data.message == "已被修改,请刷新页面再修改") {
					$.showtip("已被修改,请刷新页面再修改", "error");
					alone_edit.close();
				}else if(data.message=="公司名称已存在"){
					config.data.viewCompInfo = data.viewCompInfo;
					config.data.version = data.version;
					$.showtip("公司名称已存在");
					 alone_edit.close($(".name"));
	      setTimeout("$.hidetip()",2000);
				} 
				else {
					$.showtip("保存失败", "error");
				}

			}
		});
	}
	//公司名称展示
	function name_compShow() {
		if (config.data.viewCompInfo.base_comp_name == null
				|| config.data.viewCompInfo.base_comp_name.trim() == "") {
			$(".name").html("简称(未填写)");
		} else {
			$(".name").html(config.data.viewCompInfo.base_comp_name);
			$(".comp-input").val(config.data.viewCompInfo.base_comp_name);
		}
		if (config.data.viewCompInfo.base_comp_fullname == null
				|| config.data.viewCompInfo.base_comp_fullname.trim() == "") {
			$(".fullname").html("全称(未填写)");
		} else {
			$(".fullname").html(config.data.viewCompInfo.base_comp_fullname);
			$(".compfullname-input").val(
					config.data.viewCompInfo.base_comp_fullname);
		}
		if (config.data.viewCompInfo.base_comp_ename == null
				|| config.data.viewCompInfo.base_comp_ename.trim() == "") {
			$(".ename").html("英文名称(未填写)");
		} else {
			$(".ename").html(config.data.viewCompInfo.base_comp_ename);
			$(".compename-input").val(config.data.viewCompInfo.base_comp_ename);
		}
	}
	return {
		editname : editname,
		editfullname : editfullname,
		editename : editename
	};
})();
//筐弹层
var bask = (function() {
	$(".baskedit").unbind().bind("click", function() {
		tip_edit.config({
			list : dicListConfig(true, config.data.baskList, config.data.bask),
			$this : $(this),
			radio : false,
			besure : function() {
				var oldData = oldContent(config.data.bask);
				var newData = choiceContent();
				if (oldData == newData) {
					$.showtip("未修改数据");
				} else {
					$.showloading();//等待动画
					type = "Lable-bask";
					edit_lable(oldData, newData);
					tip_edit.close();
				}
			}
		});
	});
})();
//行业弹层
var inducot = (function() {
	$(".hangyeedit").click(
			function() {
				tip_edit.config({
					title : "行业",
					list : dicListConfig(true, config.data.induList,
							config.data.inducont),
					$this : $(this),
					radio : false,
					besure : function() {
						var oldData = oldContent(config.data.inducont);
						var newData = choiceContent();
						if (oldData == newData) {
							$.showtip("未修改数据");
						} else {
							$.showloading();//等待动画
							type = "Lable-indu";
							edit_lable(oldData, newData);
							tip_edit.close();
						}
					}
				});
			});
})();
//阶段弹层
var stage = (function() {
	$(".stageedit").click(function() {
		var statge = [];
		statge.code = config.data.viewCompInfo.base_comp_stage;
		var listselectcode = [ {
			code : config.data.viewCompInfo.base_comp_stage
		} ];
		tip_edit.config({
			title : "阶段",
			list : dicListConfig(true, config.data.stageList, listselectcode),
			$this : $(this),
			radio : true,
			besure : function() {
				var oldData = config.data.viewCompInfo.base_comp_stagecont;
				var newData = choiceContent();
				if (oldData == newData) {
					$.showtip("未修改数据");
				} else {
					$.showloading();//等待动画
					type = "Lable-stage";
					edit_lable(oldData, newData);
					tip_edit.close();
				}
			}
		});
	});
})();
//修改公司标签
function edit_lable(oldData, newData) {
	$.showloading();
	$
			.ajax({
				url : "updateCompInfo.html",
				type : "post",
				async : true,
				data : {
					type : type,
					code : config.data.viewCompInfo.base_comp_code,
					compName : config.data.viewCompInfo.base_comp_name,
					logintype : cookieopt.getlogintype(),
					oldData : oldData,
					newData : newData,
					version : config.data.version
				},
				dataType : "json",
				success : function(data) {
					$.hideloading();//等待动画隐藏
					if (data.message == "success") {
						$.showtip("保存成功");
						config.data.bask = (data.compInfo.view_comp_baskcont == null ? []
								: eval(data.compInfo.view_comp_baskcont));
						config.data.inducont = (data.compInfo.view_comp_inducont == null ? []
								: eval(data.compInfo.view_comp_inducont));
						config.data.viewCompInfo.base_comp_stagecont = data.compInfo.base_comp_stagecont;
						config.data.viewCompInfo.base_comp_stage = data.compInfo.base_comp_stage;
						config.data.version = data.version;
						config.init();
					} else {
						$.showtip(data.message);
					}
				}

			});
}
//添加联系人
var addenupeople = (function() {
	var opt = {};
	function init() {
		//页面层
		layer
				.open({
					title : "添加联系人",
					type : 1,
					skin : 'layui-layer-rim', //加上边框
					area : [ '500px', '318px' ], //宽高
					content : '<div class="addfrom"><ul class="inputlist addpeoplebox"><li><span class="lable">联系人姓名:</span><span class="in"><select id="select_org" class=" inputdef"><option></option></select><input value="" id="peoplename" type="text" class="inputdef" style="display:none" maxlength="20" /></span><span class="in"><button id="btn_people_add" class="btn btn_icon_add add_peoplename"></button></span></li>'
							+ '<li><span class="lable">职位:</span><span class="in"><input value="" id="posi" type="text" class="inputdef" maxlength="20" /></span></li>'
							+ '<li><span class="lable">手机:</span><span class="in"><input value="" id="phone" type="text" class="inputdef" maxlength="11" /></span>'
							+ '<li><span class="lable">邮箱:</span><span class="in"><input value="" id="email" type="text" class="inputdef" maxlength="200" /></span>'
							+ '<li><span class="lable">微信:</span><span class="in"><input value="" id="wechat" type="text" class="inputdef" maxlength="200" /></span></li></ul><div class="btn-box"><button class="btn btn-default saveorgan">保存</button></div></div>'
				});
		//选择联系人
		opt.$orgainselect = $("#select_org")
				.select2(
						{
							placeholder : "请选择联系人",//文本框的提示信息
							minimumInputLength : 0, //至少输入n个字符，才去加载数据
							allowClear : true, //是否允许用户清除文本信息
							ajax : {
								url : "findEntreByName.html",
								dataType : "json",
								cache : true,
								type : "post",
								delay : 250,//加载延迟
								data : function(params) {
									return {
										name : params.term || "",
										pageSize : CommonVariable().SELSECLIMIT,
										pageCount : "1",
										logintype : cookieopt.getlogintype(),
										type : "1"
									};
								},
								processResults : function(data, page) {
									for (var i = 0; i < data.list.length; i++) {
										data.list[i].id = data.list[i].base_entrepreneur_code;
										data.list[i].text = data.list[i].base_entrepreneur_name;
									}
									return {
										results : data.list
									//返回结构list值
									};
								}

							},
							escapeMarkup : function(markup) {
								return markup;
							}, // let our custom formatter work
							templateResult : formatRepo
						// 格式化返回值 显示再下拉类表中
						});
		 $("#select_org").on("change", function() { 
	        	$("#phone").val(opt.$orgainselect.select2("data")[0].base_entrepreneur_phone);
	        	$("#email").val(opt.$orgainselect.select2("data")[0].base_entrepreneur_email);
	        	$("#wechat").val(opt.$orgainselect.select2("data")[0].base_entrepreneur_wechat);
	        });
		click();
	}

	function click() {
        $(".add_peoplename").click(function(){
            opt.$orgainselect.val(null).trigger("change");
            $(".select2-container").css('display','none');
            $("#peoplename").css('display','block');
            $(".add_peoplename").css('display','none');
        });
		$(".saveorgan").click(function() {
			submitflg++;
			if(submitflg==1){
				if (selectdata().olist.length == 0) {
	                if($("#peoplename").val().trim()==""){
	                    $.showtip("请选择联系人");
	                    submitflg=0;
	                }else{
	                    peopleName=$("#peoplename").val().trim();
	                    peoplecode="";
	                    oldstr = "";
	                    phone = $("#phone").val().trim();
	                    email = $("#email").val().trim();
	                    wechat = $("#wechat").val().trim();
	                    posi = $("#posi").val().trim();
	                    if (peopleName == "") {
	                        $.showtip("姓名不能为空");
	                        submitflg=0;
	                    } else {
	                        if (check(phone, email)) {
	                            editPeople_ajax("addPeople.html");
	                            submitflg=0;
	                        }else{
	                        	submitflg=0;
	                        }

	                    }
	                }

				} else {
					var code = selectdata().olist[0].id;
					for (var i = 0; i < config.data.entrepreneurList.length; i++) {
						if (code == config.data.entrepreneurList[i].base_entrepreneur_code) {
							$.showtip("该联系人已存在");
							submitflg=0;
							return;
						}
					}
					oldstr = "";
					peopleName = selectdata().olist[0].text;
					peoplecode = selectdata().olist[0].id;
					phone = $("#phone").val().trim();
					email = $("#email").val().trim();
					wechat = $("#wechat").val().trim();
					posi = $("#posi").val().trim();
					if (peopleName!=null&&peopleName.trim() == "") {
						$.showtip("姓名不能为空");
						submitflg=0;
					} else {
						peopleName=peopleName.trim();
						if (check(phone, email)) {
							editPeople_ajax("addPeople.html");
							submitflg=0;
						}else{
							submitflg=0;
						}

					}

				}
			}
			
		});
	}

	function selectdata() {
		var map = {};
		var o = opt.$orgainselect.select2("data");
		var olist = [];
		for (var i = 0; i < o.length; i++) {
			if (!o[i].selected) {
				olist.push(o[i]);
			}
		}
		map.olist = olist;
		return map;
	}
	function formatRepo(repo) {
		return repo.text;
	}
	return {
		init : init
	};
})();

//编辑联系人调用的ajax
function editPeople_ajax(url) {
	$.showloading();
	$.ajax({
		url : url,
		type : "post",
		async : true,
		data : {
			code : config.data.viewCompInfo.base_comp_code,
			peopleName : peopleName,
			phone : phone,
			email : email,
			wechat : wechat,
			posi : posi,
			version : config.data.version,
			oldstr : oldstr,
			peoplecode : peoplecode,
			logintype : cookieopt.getlogintype()
		},
		dataType : "json",
		success : function(data) {
			$.hideloading();
			if (data.message == "success") {
				$.showtip("保存成功");
				config.data.entrepreneurList = eval(data.entrepreneurList);
				config.data.version = data.version;
				var entrepreneurList = template.compile(
						data_model.entrepreneurList)(config.data);
				$(".people").html(entrepreneurList);
				config.click();
                layer.closeAll();
			} else if (data.message == "已被修改,请刷新页面再修改") {
				$.showtip("已被修改,请刷新页面再修改");
				var entrepreneurList = template.compile(
						data_model.entrepreneurList)(config.data);
				$(".people").html(entrepreneurList);
				config.click();
				layer.closeAll();
			} else if (data.message == "repeat") {
				config.data.entrepreneurList = eval(data.entrepreneurList);
				config.data.version = data.version;
				$.showtip("联系人已存在");
			} else {
				$.showtip("保存失败","error");
			}

		}
	});
}
//备注信息操作
var notepost = (function() {
	function addnote() {
		var note = $("#note").val();
		/*note = note.split("\n").join("<br/>");*/
		if (note.trim() != "") {
			$.showloading();
			$.ajax({
				async : true,
				dataType : 'json',
				type : 'post',
				url : "addCompNote.html",
				data : {
					base_comp_code : config.data.viewCompInfo.base_comp_code,
					base_compnote_content : note,
					logintype : cookieopt.getlogintype()
				},
				success : function(data) {
					$.hideloading();
					if (data.message == "success") {
						config.data.noteList = data.noteList;
						noteconfig();
						$("#note").val("");
						$.showtip("保存成功");
					} else if (data.message == "error") {
						$.showtip("保存失败", "error");
					} else {
						$.showtip("请求失败", "error");
					}
				}
			});
		} else {
			$.showtip("备注信息不能为空");
			$("#note").val("");
		}
	}
	function removenote(notecode) {
		$.showloading();
		$.showloading();
		$.ajax({
			async : true,
			dataType : 'json',
			type : 'post',
			url : "compnote_del.html",
			data : {
				notecode : notecode,
				logintype : cookieopt.getlogintype()
			},
			success : function(data) {
				if (data.message == "success") {
					$.showtip("删除成功");
					noteconfig(notecode);
				} else if (data.message == "failure") {
                    $.showtip("数据已被删除","error");
                    noteconfig(notecode);
				} else {
					$.showtip("请求失败");

				}
				$.hideloading();
			}
		});
	}

	function noteconfig(notecode) {
		if (notecode) {
			config.data.noteList.baoremove(Number($(
					"[del-code='" + notecode + "']").attr("i")));
		}

		var noteList = template.compile(data_model.noteList)(config.data);
        if(noteList==""){
            $(".notetable").html(data_model.noteListNull);
        }else{
            $(".notetable").html(noteList);
        }

		if (config.data.noteconfignum >= config.data.noteList.length) {

			$(".closeshearch").html(
					'更多<span class="glyphicon glyphicon-chevron-down"></span>')
					.removeClass("active").hide();
		} else {
			if (!$(".closeshearch").hasClass("active")) {
				$(".closeshearch")
						.html(
								'更多<span class="glyphicon glyphicon-chevron-down"></span>')
						.show();
			}

		}
		if (!config.data.zhangkai) {
			$("[nodemore]").hide();
		}

		click();
	}
	;

	function click() {
		$("[del-code]").unbind().bind("click", function() {
			$this = $(this);
			layer.confirm('您确定要删除该条记录吗？', {
				title : "提示",
				icon : 0,
				btn : [ '确定', '取消' ]
			//按钮
			}, function(index) {
				removenote($this.attr("del-code"));
				layer.close(index);
			}, function(index) {
				layer.close(index);
			});
		});
	}
	return {
		noteconfig : noteconfig,
		add : addnote
	};
})();
//参与交易更多
var trade_more=(function(){
	var page = {};
	page.pageCount = 1;
	function init() {
		page = {};
		page.pageCount = 1;

		tablelist = "";
		select();
	}
	function select() {
		$.ajax({
			url : "findTradeByCode.html",
			type : "post",
			async : true,
			data : {
				code : config.data.viewCompInfo.base_comp_code,
				pageCount : page.pageCount,
				logintype : cookieopt.getlogintype()
			},
			dataType : "json",
			success : function(data) {
				page = data.page;
				tablelist = data.financList;
				var html = template.compile(data_model.financList)(data);
				if (html == "") {
					html = '<tr class="nohover"><td  colspan="5" style="width:100%;">暂无数据</td></tr>';
					$(".stagemore").html(html);
					$("#pagination1").hide();
				} else {
					$(".stagemore").html(html);
					$("#pagination1").show();
					if(data.page.totalCount>0){
						$(".totalSize").show();
						$(".totalSize").html("<li>共"+data.page.totalCount+"条</li>");
	                }else{
	                	$(".totalSize").hide();
	                }
					jqPagination();
				}
				click();
			}
		});
					
	}
	function jqPagination() {

		$.jqPaginator('#pagination1', {
			totalPages : page.totalPage,
			visiblePages : 5,
			currentPage : page.pageCount,
			onPageChange : function(num, type) {
				if (page.pageCount != num) {
					page.pageCount = num;
					select();
				}

			}
		});

	}

	function click() {
		$(".stagemore tr").each(function(index) {$(this).unbind().bind("click",function() {
			window.location.href = "findTradeDetialInfo.html?logintype="+ cookieopt.getlogintype()+ "&tradeCode="+ tablelist[index].base_trade_code+"&backherf=findCompanyDeatilByCode.html?code="+viewCompInfo.base_comp_code;
											});
						});
		refBool=true;
	    delTradeconfig.click(refBool);

	}
	return {
		init : init,
		select:select
	};
})();
//添加融资计划
var add_rz=(function(){
    var plantime;
    var emailtime;
    var text;
    var click_config= function () {
        $(".addrz").unbind().bind("click", function () {
                layer.open({
                    title : "添加融资计划",
                    type : 1,
                    skin : 'layui-layer-rim', //加上边框
                    area : [ '500px', '327px' ], //宽高
                    content : '<div class="addfrom"><ul class="inputlist">'
                        + '<li><span class="lable">计划时间:</span><span class="in"><input type="text" class=" inputdef" id="plantime" data-date-format="yyyy-mm-dd" readonly></span></li>'
                        + '<li><span class="lable">提醒时间:</span><span class="in"><input type="text" class=" inputdef" id="emailtime" data-date-format="yyyy-mm-dd" readonly></span></li>'
                        + '<li><span class="lable">计划内容:</span><span class="in">' +
                        '<textarea id="text" class="flan" maxlength="100"></textarea></span></li></ul><div class="btn-box"><button class="btn btn-default saverz">保存</button></div></div>',
                        success: function(layero, index){
                        	 $('#plantime').datepicker();
                            $('#emailtime').datepicker();
                        											}
                });
            click();

        });

    };
function click(){
    $(".saverz").click(function(){
    	submitrz++;
    	if(submitrz==1){
    		plantime = $("#plantime").val();
            emailtime = $("#emailtime").val();
            text = $("#text").val();
            if(plantime.trim()==""){
                $.showtip("融资计划时间不能为空");
                submitrz=0;
                return;
            }
            if(emailtime.trim()==""){
                $.showtip("邮件提醒时间不能为空");
                submitrz=0;
                return;
            }
            if(text.trim()==""){
                $.showtip("融资计划内容不能为空");
                $("#text").val("");
                submitrz=0;
                return;
            }
            if (plantime.trim() != "" && emailtime.trim()!="" && text.trim()!="") {

                var thisnowdate = new Date(nowdate.substring(0, 10).replace(/-/g, "/"));
                emailtime = new Date(emailtime.replace(/-/g, "/").replace("年", "/").replace("月", "/").replace("日", "/"));
                plantime = new Date(plantime.replace(/-/g, "/").replace("年", "/").replace("月", "/").replace("日", "/"));
                if (thisnowdate > plantime) {
                    $.showtip("融资计划时间小于当前日期");
                    submitrz=0;
                    return;
                }else if (thisnowdate > emailtime) {
                    $.showtip("邮件提醒时间小于当前日期");
                    submitrz=0;
                    return;
                }

                emailtime = emailtime.format(emailtime, "yyyy-MM-dd hh:mm:ss");

                plantime = plantime.getTime();
                oldstr = "";
                add_rz();
            }
    	}
        
    });

}
    function add_rz() {
        $.showloading();
        $.ajax({
            url:"addFinancplan.html",
            type:"post",
            async:true,
            data:{
                code:config.data.viewCompInfo.base_comp_code,
                compName:config.data.viewCompInfo.base_comp_name,
                logintype:cookieopt.getlogintype(),
                plantime:plantime,
                emailtime:emailtime,
                text:text
            },
            dataType: "json",
            success: function(data){
            	submitrz=0;
                $.hideloading();//等待动画隐藏
                if(data.message=="success"){
                    $.showtip("保存成功");
                    config.data.financplanList=eval(data.financplanList);
                    config.rzconfig();
                }else if(data.message=="failer"){
                    $.showtip("保存失败");
                }
                layer.closeAll();
            }
        });
    }
    return{
        click_config:click_config
    };
})();
//会议记录
var meeting=(function () {
    var opt={};
    var tabledata;
    function init() {
        opt.page={
            pageCount:1
        };
        select();
    }
    function select() {
        $.showloading();//等待动画
        $.ajax({
            url:"screenmeetinglist.html",
            type:"post",
            async:true,
            data:{companycode:config.data.viewCompInfo.base_comp_code,
                pageCount:opt.page.pageCount,
                logintype:cookieopt.getlogintype()},
            dataType: "json",
            success: function(data){
                tabledata=data;
                $.hideloading();//隐藏等待动画
                opt.page=data.page;
                if(data.message=="success"){
                    var html=template.compile(data_model.tablelist)(data);
                    $(".meetingtable").html(html);
                    if(data.page.totalCount>0){
                    	$(".totalSize").show();
                    	$(".totalSize").html("<li>共"+data.page.totalCount+"条</li>");
                    }else{
                    	$(".totalSize").hide();
                    }
                }else if(data.message=="nomore"){
                    var html='<tr class="nohover"><td  colspan="3" style="width:100%;">暂无数据</td></tr>';
                    $(".meetingtable").html(html);
                    $(".totalSize").hide();
                }
                jqPagination();
                click();
            }
        });

    }
    function jqPagination () {
        $.jqPaginator('#pagination1', {
            totalPages: opt.page.totalPage,
            visiblePages: 5,
            currentPage:opt.page.pageCount,
            onPageChange: function (num, type) {
                if(opt.page.pageCount!=num){
                    opt.page.pageCount=num;
                    select();
                }

            }
        });
    }

    function click() {
        $(".meetingtable tr").each(function(index,element){
            $(this).bind("click", function () {
                if(tabledata.list[index].visible=="1"){//可见详情
                    window.location.href="meeting_info.html?meetingcode="+tabledata.list[index].base_meeting_code+"&logintype="+cookieopt.getlogintype();
                }else{
                    $.showtip("您没有查看此会议的权限");
                }
            });
        });
    }
    return{
        init:init
    };
})();
//更新记录
var updaterecord=(function () {
    var data={};
    function init() {
        data.page={
            pageCount:1
        };
        select();
    }
    function select() {
        $.showloading();//等待动画
        $.ajax({
            url:"findOrgUpdlogInfoByCode.html",
            type:"post",
            async:true,
            data:{type:'Lable-company',
                code:config.data.viewCompInfo.base_comp_code,
                pageCount:data.page.pageCount,
                logintype:cookieopt.getlogintype()},
            dataType: "json",
            success: function(json){
                $.hideloading();//隐藏等待动画
                data.page=json.page;
                if(json.message=="success"){
                    var html=template.compile(data_model.updlogList)(json);
                    if(html==""){
                    	html='<tr><td  colspan="4" style="width:100%;">暂无数据</td></tr>';
                    }
                    $(".updatetable").html(html);
                    if(data.page.totalCount>0){
                    	$(".totalSize").show();
                    	$(".totalSize").html("<li>共"+data.page.totalCount+"条</li>");
                    }else{
                    	$(".totalSize").hide();
                    }
                }else if(json.message=="nomore"){
                    var html='<tr><td  colspan="4" style="width:100%;">暂无数据</td></tr>';
                    $(".updatetable").html(html);
                    $(".totalSize").hide();
                }
                jqPagination();

            }
        });

    }
    function jqPagination () {
        $.jqPaginator('#pagination1', {
            totalPages: data.page.totalPage,
            visiblePages: 5,
            currentPage:data.page.pageCount,
            onPageChange: function (num, type) {
                if(data.page.pageCount!=num){
                    data.page.pageCount=num;
                    select();
                }

            }
        });
    }
    return{
        init:init
    };
})();
//组成旧数据
function oldContent(vObj) {
	var list = "[";
	if (vObj != null) {
		for (var i = 0; i < vObj.length; i++) {
			list += "{code:'" + vObj[i].code + "',name:'" + vObj[i].name
					+ "'},";
		}
	}
	list += "]";
	return list;
}
//新数据拼成
function choiceContent() {
	var list = "[";
	$(".list li[class='active']").each(
			function() {
				list += "{code:'" + $(this).attr("tip-l-i") + "',name:'"
						+ $(this).text() + "'},";
			});
	list += "]";
	return list;
}
//标签弹出层集合
function dicListConfig(vDel, vList, vObj) {
	var list = [];
	var map = {};
	for (var i = 0; i < vList.length; i++) {
		map = {};
		map.name = vList[i].sys_labelelement_name;
		map.id = vList[i].sys_labelelement_code;
		if (vObj != null) {
			for (var j = 0; j < vObj.length; j++) {
				//判断标签是否被选中,存在则加上选中标识
				if (vList[i].sys_labelelement_code == vObj[j].code) {
					map.select = true;
				}
			}
		}
		list.push(map);
	}
	return list;
}
//验证邮箱，手机号格式
function check(phone, email) {
	var check_phone= /^1\d{10}$/;//手机格式验证
    var check_email=/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/ ;//邮箱验证


	if (phone != ""&&phone!=null) {
		if (!check_phone.test(phone)) {
			$.hideloading();
			$.showtip("请输入有效的手机号码");
			return false;
		}
	}
	if (email != ""&&email!=null) {
		if (!check_email.test(email)) {
			$.hideloading();
			$.showtip("请输入有效的邮箱");
			return false;
		}
	}
	return true;
}
function olddata(index) {
	var oldstr = "[名称："
			+ config.data.entrepreneurList[index].base_entrepreneur_name
			+ ";职位:"
			+ config.data.entrepreneurList[index].base_entrepreneur_posiname
			+ ";手机号："
			+ config.data.entrepreneurList[index].base_entrepreneur_phone + ";"
			+ "邮箱："
			+ config.data.entrepreneurList[index].base_entrepreneur_email
			+ ";微信:"
			+ config.data.entrepreneurList[index].base_entrepreneur_wechat
			+ "]";
	return oldstr;
}
//删除融资信息
var delTradeconfig=(function(){
    var click=function(){
        $("[del-tradecode]").unbind().bind("click",function(e){
            $this=$(this);

            if($this.attr("del-tradecode")==null){
                $.showtip("未发现融资信息","normal",2000);
                return;
            }
            layer.confirm('您确定要删除交易信息吗？', {
                title:"提示",
                icon: 0,
                btn: ['确定','取消'] //按钮
            }, function(index){
                del($this.attr("del-tradecode"));
                layer.close(index);
            }, function(index){
                layer.close(index);
            });
            e.stopPropagation();
        });

    };

    var del=function(tradeCode){
    	var orgCodeString;
    	var tradeDate;
        $.showloading();//等待动画
        var ii=Number($("[del-tradecode='"+tradeCode+"']").attr("i"));
        if(refBool){
        	orgCodeString=tablelist[ii].view_investment_code;
        	tradeDate=tablelist[ii].base_trade_date;
        }else{
        	orgCodeString=config.data.financList[ii].view_investment_code;
        	tradeDate=config.data.financList[ii].base_trade_date;
        }
        $.ajax({
            url:"delTradeInfo.html",
            type:"post",
            async:true,
            data:{
            	compcode:viewCompInfo.base_comp_code,
                tradeCode:tradeCode,
                orgCodeString:orgCodeString,
                tradeDate:tradeDate,
                companyName:viewCompInfo.base_comp_name,
                logintype:cookieopt.getlogintype()
            },
            dataType: "json",
            success: function(data){
                $.hideloading();//等待动画隐藏
                if(data.message=="success") {
                    $.showtip("删除成功");
                    if(refBool==true){
                    	trade_more.select();
                    	select();
                     /*delTradeconfig.click(refBool);*/
                    }else{
                            /*config.data.financList.baoremove(Number($("[del-tradecode='"+tradeCode+"']").attr("i")));
                            fina();*/
                    	select();
                    }

                }else if(data.message=="delete"){
                    $.showtip("交易信息已被删除");
                    trade_more.select();
                	select();

                }else{
                    $.showtip(data.message);
                }
                setTimeout("$.hidetip()",2000);
            }
        });
    };

    return {
        click:click
    };
})();
//添加交易
var addTrade=(function(){
	var click=function(){
        $(".addtradebtn").unbind().bind("click",function(){
        	window.location.href="addTradeInfoinit.html?logintype="+cookieopt.getlogintype()+"&compcode="+viewCompInfo.base_comp_code+"&backherf=findCompanyDeatilByCode.html?code="+viewCompInfo.base_comp_code;
        });
   
            };
	return {
		click:click
	};
})();
//数据模板
var data_model = {
	inducont : '<% for (var i=0;i<inducont.length;i++ ){%>'
			+ '<li><%=inducont[i].name%></li>' + '<%}%>',
	inducontnull : '<li>暂无数据</li>',
	bask : '<%for (var i=0;i<bask.length;i++ ){%>'
			+ '  <li><span class="comp"><%=bask[i].name%></li>' + '<%}%>',
	viewTradeInfoNull : '<tr class="nohover"><td colspan=\'4\'>暂无数据</td></tr>',
	noteList : '<% for (var i=0;i<noteList.length;i++ ){ %>'
			+ '<tr class="nohover" <%= nodermore(i,noteconfignum)%>>'
			+ '<td><%=dateFormat(noteList[i].createtime.time,"yyyy-MM-dd")%></td>'
			+ '<td><%=noteList[i].sys_user_name%></td>'
			+ '<td><pre><%=noteList[i].base_compnote_content %></pre></td>'
			+ '<td><button class="btn btn_delete" i="<%=i%>" del-code="<%=noteList[i].base_compnote_code%>"></button></td>'
			+ '</tr>' + '<%}%>',
	noteListNull : '<tr class="nohover"><td colspan=\'4\'>暂无数据</td></tr>',
	financplanList : '<% for (var i=0;i<financplanList.length;i++ ){%>'
			+ '<tr class="nohover" <%= finamores(i,noteconfignum)%>>'
			+ '<td><%=dateFormat(financplanList[i].base_financplan_date,"yyyy-MM-dd")%></td>'
			+ '<td><pre><% print(financplanList[i].base_financplan_cont)%></pre></td>'
			+ '</tr>' + '<%}%>',
	financList : '<% for (var i=0;i<financList.length;i++ ){%>' + '<tr>'
			+ '<td><%=dateFormat(financList[i].base_trade_date,"yyyy-MM")%></td>'
			+ '<td><%=financList[i].base_trade_stagecont%></td>'
			+ '<td><%=financList[i].base_trade_money%></td>'
			+ '<td><%=financList[i].view_investment_name%></td>'
			+ '<td><button class="btn btn_delete" i="<%=i%>" del-tradecode="<%=financList[i].base_trade_code%>"></button></td>'
			+ '</tr>'
			+ '<%}%>',
	updlogList : '<%for(var i=0;i<list.length;i++){%>'
			+ '<tr class="nohover"><td><%=dateFormat(list[i].updtime.time,"yyyy-MM-dd")%></td>'
			+ '<td><%=list[i].sys_user_name%></td>'
			+ '<td><%=list[i].base_updlog_opercont%></td>'
			+ '<td><%=list[i].base_updlog_oridata==""?"":list[i].base_updlog_oridata%>'
			+ '<%=list[i].base_updlog_newdata==""?"":list[i].base_updlog_newdata%></td></tr>'
			+ '<%}%>',
	entrepreneurList : '<% for (var i=0;i<entrepreneurList.length;i++ ){%>'
			+ '<li><ul>'
			+ '<li>'
			+ '<% entrepreneurList[i].name=entrepreneurList[i].base_entrepreneur_name;if(entrepreneurList[i].base_entrepreneur_name==""||entrepreneurList[i].base_entrepreneur_name==null){entrepreneurList[i].base_entrepreneur_name="姓名"} %>'
			+ '<span class="canEdit color_blue  contname"><%=entrepreneurList[i].base_entrepreneur_name%></span>'
			+ '<div class="txtEdit lable_hidden">'
			+ '<input class="txtInfo" value="<%=entrepreneurList[i].name%>" maxlength="20" />'
			+ '<button class="txtSave savecontname">保存</button></div>'
			+ '</li>'
			+ '<li>'
			+ '<% entrepreneurList[i].posiname=entrepreneurList[i].base_entrepreneur_posiname;if(entrepreneurList[i].base_entrepreneur_posiname==""||entrepreneurList[i].base_entrepreneur_posiname==null){entrepreneurList[i].base_entrepreneur_posiname="职位(未填写)"} %>'
			+ '<span class="canEdit  color_blue  contposi"><%=entrepreneurList[i].base_entrepreneur_posiname%></span>'
			+ '<div class="txtEdit lable_hidden">'
			+ '<input class="txtInfo" value="<%=entrepreneurList[i].posiname%>" maxlength="20"/>'
			+ '<button class="txtSave savecontposi">保存</button></div>'
			+ '</li>'
			+ '<li>'
			+ '<% entrepreneurList[i].phone=entrepreneurList[i].base_entrepreneur_phone;if(entrepreneurList[i].base_entrepreneur_phone==""||entrepreneurList[i].base_entrepreneur_phone==null){entrepreneurList[i].base_entrepreneur_phone="手机(未填写)"} %>'
			+ '<span class="canEdit color_blue contphone"><%=entrepreneurList[i].base_entrepreneur_phone%></span>'
			+ '<div class="txtEdit lable_hidden">'
			+ '<input class="txtInfo" value="<%=entrepreneurList[i].phone%>" maxlength="11"/>'
			+ '<button class="txtSave savecontphone">保存</button></div>'
			+ '</li>'
			+ '<li>'
			+ '<% entrepreneurList[i].wechat=entrepreneurList[i].base_entrepreneur_wechat;if(entrepreneurList[i].base_entrepreneur_wechat==""||entrepreneurList[i].base_entrepreneur_wechat==null){entrepreneurList[i].base_entrepreneur_wechat="微信(未填写)"} %>'
			+ '<span class="canEdit color_blue contwechat"><%=entrepreneurList[i].base_entrepreneur_wechat%></span>'
			+ '<div class="txtEdit lable_hidden">'
			+ '<input class="txtInfo" value="<%=entrepreneurList[i].wechat%>" maxlength="200"/>'
			+ '<button class="txtSave savecontwechat">保存</button></div>'
			+ '</li>'
			+ '<li>'
			+ '<% entrepreneurList[i].email=entrepreneurList[i].base_entrepreneur_email;if(entrepreneurList[i].base_entrepreneur_email==""||entrepreneurList[i].base_entrepreneur_email==null){entrepreneurList[i].base_entrepreneur_email="邮箱(未填写)"} %>'
			+ '<span class="canEdit color_blue contemail"><%=entrepreneurList[i].base_entrepreneur_email%></span>'
			+ '<div class="txtEdit lable_hidden">'
			+ '<input class="txtInfo" value="<%=entrepreneurList[i].email%>" maxlength="200"/>'
			+ '<button class="txtSave savecontemail">保存</button></div>'
			+ '</li>'
			+ '</ul></li>'
			+ '<% entrepreneurList[i].base_entrepreneur_name=entrepreneurList[i].name;'
			+ 'entrepreneurList[i].base_entrepreneur_posiname=entrepreneurList[i].posiname;'
			+ 'entrepreneurList[i].base_entrepreneur_phone=entrepreneurList[i].phone;'
			+ 'entrepreneurList[i].base_entrepreneur_wechat=entrepreneurList[i].wechat;'
			+ 'entrepreneurList[i].base_entrepreneur_email=entrepreneurList[i].email;%>'
			+ '<%}%>',
	tablelist : '<% for (var i=0;i<list.length;i++ ){%>' + '<tr>'
			+ '<td><%=list[i].base_meeting_time%></td>'
			+ '<td><%=substring(list[i].createName,"10")%></td>'
			+ '<td><%=substring(list[i].base_meeting_compcont+" / "+list[i].base_meeting_invicont,"40")%></td>'
			+ '</tr>' + '<%}%>',
	orgainlist : '<% for (var i=0;i<list.length;i++ ){%>'
			+ '<div data-org-code="<%=list[i].base_entrepreneur_code%>"'
			+ 'data-en-phone="<%=list[i].base_entrepreneur_phone%>"'
			+ 'data-en-email="<%=list[i].base_entrepreneur_email%>"'
			+ 'data-en-wechat="<%=list[i].base_entrepreneur_wechat%>"'
			+ 'class="item"><%=list[i].base_entrepreneur_name %></div>'
			+ '<%}%>',
	addPeo : '<div class="shgroup">' + ' <div class="title "><%=name%>:</div>'
			+ ' <div class="tiplist " style="padding-right:10px;">'
			+ ' <input type="text" class="inputdef inputdef_l addpeoplename" maxlength="20">'
			+ '</div>' + '<div style="display:table-cell">'
			+ '<button class="btn btn-default" id="addpeoname"'
			+ ' style="width:70px;height:25px;line-height:26px;">'
			+ '确定</button></div></div>'
};
template.helper("nodermore", function(a, b) {
	if (a < b) {
		return "";
	} else {
		return "nodemore";
	}
});
template.helper("finamores", function(a, b) {
	if (a < b) {
		return "";
	} else {
		return "finamores";
	}
});
//应用模板 截取字符串
template.helper("substring", function (str,long) {
    str= str.replace("<br/>","");
    if(str.length>long){
        str= str.substring(0,long)+"...";
    }
    return str;
});
function fina(){
	var financList=template.compile(data_model.financList)(config.data);
	$(".financ").html(
			financList == "" ? '<tr class="nohover"><td colspan=\'5\'>暂无数据</td></tr>'
					: financList);
}
function select() {
	$.ajax({
				url : "findTradeByCode.html",
				type : "post",
				async : true,
				data : {
					code : config.data.viewCompInfo.base_comp_code,
					pageCount : 1,
					//spageSize:new CommonVariable().SHOWPAGESIZE,
					logintype : cookieopt.getlogintype()
				},
				dataType : "json",
				success : function(data) {
					page = data.page;
					config.data.financList = data.financList;
					var html = template.compile(data_model.financList)(data);
					if (html == "") {
						html = '<tr class="nohover"><td  colspan="5" style="width:100%;">暂无数据</td></tr>';
						$(".financ").html(html);
					} else {
						$(".financ").html(html);
					}	
                    clicks();
				}
				
			});

}
function clicks() {
	$(".financ tr").each(function(index, e) {$(this).bind("click",function() {
		if (config.data.financList != null) {
			window.location.href = "findTradeDetialInfo.html?tradeCode="+config.data.financList[index].base_trade_code
					+ "&logintype="+ cookieopt.getlogintype()+"&backherf=findCompanyDeatilByCode.html?code="+config.data.viewCompInfo.base_comp_code;;
		}

	});
});
delTradeconfig.click(refBool);//调用删除融资信息
}