<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<base href="<%=basePath%>">
<head lang="en">
<title>易凯投资</title>
<meta charset="UTF-8">
<meta name="keywords" content="易凯投资">
<meta name=”renderer” content=”webkit”>
<meta http-equiv=”X-UA-Compatible” content=”IE=Edge,chrome=1″>
<link rel="stylesheet" href="view/pc/common/css/common.css">
<link rel="stylesheet" href="view/pc/common/css/font.css">
<link rel="stylesheet" href="view/pc/common/css/layer.css">
<link rel="stylesheet" href="view/pc/common/css/datepicker.css">
<link rel="stylesheet" href="view/pc/common/css/jqPaginator.css">
<link rel="stylesheet" href="view/pc/common/css/select2.min.css">
<link rel="stylesheet" href="view/pc/common/css/company_info_detail.css">
</head>
<body>
	<jsp:include page="../common/head.jsp" />
	<div id="main-content" class="content">
		<span class="main-content-box main-content-box-left">
			<div class="left-menu-box">
				<jsp:include page="../common/left_menu.jsp" />
			</div>
		</span> <span class="main-content-box main-content-box-right">
			<div class="right-content">
				<div class="head">
					<span class="glyphicon glyphicon-home"> </span> <span
						class="head-title"> 搜索 > 公司 > <span class="strong">公司详情</span>
					</span>
				</div>
				<div class="right-content-body">
					<div class="right-group-box">
						<div class="head">
							<div class="title">公司详细信息</div>
							<div class="goback"><button class="btn btn-default smart-btn">返回</button></div>
						</div>
						<div class="right-group-box-body">
							<div class="shgroup">
								<div class="title">名称:</div>
								<div class="tiplist">
									<ul class="">
										<li data-i="0"><span class="canEdit color_blue name"></span>
											<div class="txtEdit lable_hidden">
												<input class="txtInfo comp-input" maxlength="20"/>
												<button class="txtSave savecompname">保存</button>
											</div></li>
										<li data-i="1"><span class="canEdit color_blue fullname"></span>
											<div class="txtEdit lable_hidden">
												<input class="txtInfo compfullname-input"  maxlength="200"/>
												<button class="txtSave savefullname">保存</button>
											</div></li>
										<li data-i="1"><span class="canEdit color_blue ename"></span>
											<div class="txtEdit lable_hidden">
												<input class="txtInfo compename-input"  maxlength="200"/>
												<button class="txtSave saveename">保存</button>
											</div></li>
									</ul>
								</div>
							</div>
							<div class="shgroup">
								<div class="title">
									<span class="tip-edit baskedit">筐
										<span class="caret"></span>:
									</span>
								</div>
								<div class="tiplist">
									<ul class="bask">

									</ul>
								</div>
							</div>
							<div class="shgroup">
								<div class="title">
									<span class="tip-edit hangyeedit">行业
										<span class="caret"></span>:
									</span>
								</div>
								<div class="tiplist">
									<ul class="induc">

									</ul>
								</div>
							</div>
							<div class="shgroup">
								<div class="title">
									<span class="tip-edit stageedit">阶段
										<span class="caret"></span>:
									</span>
								</div>
								<div class="tiplist">
									<ul class="stage">
                                        <li class="stagea"></li>
									</ul>
								</div>
							</div>
							<div class="shgroup">
								<div class="title">联系人:</div>
								<div class="tiplist" style="margin-left: 120px;position: relative;">
									<ul class="people" style="min-height: 27px;margin-right: 10%;">

									</ul>
									<div class="people_add_img">
										<button id="btn_people_add"
											class="btn btn_icon_add add_people"></button>
									</div>
								</div>
							</div>
							<div class="shgroup">
								<div class="title">融资计划:</div>
								<div class="tiplist">
									<button class="btn btn_plan addrz">添加融资计划</button>
								</div>
							</div>

							<div class="body_div ">
								<div class="tab_title">融资计划:</div>
								<div class="tran_content_table">
									<table>
										<thead>
											<tr>
												<th class="td_colsone" width="20%">计划融资日期</th>
												<th class="td_colsone" width="80%">内容</th>
											</tr>
										</thead>
										<tbody class="financplan">
										</tbody>
									</table>
									<div class="closefinacplan">
										更多<span class="glyphicon glyphicon-chevron-down"></span>
									</div>
								</div>
							</div>
							<div class="body_div">
								<div class="tab_title">融资信息:</div>
								<div class="tran_content_table">
									<table id="pageTradeBody">
										<thead>
											<tr>
												<th width="20%">投资日期</th>
												<th width="20%">阶段</th>
												<th width="20%">金额</th>
												<th width="30%">投资机构</th>
												<th width="10%">操作</th>
											</tr>
										</thead>
										<tbody class="financ">
										</tbody>
									</table>
                <div class="body_div_btn">
                    <button class="btn addtradebtn"></button>
                </div>
								</div>
								<div class="lockmore" id="flanmore">更多</div>
							</div>
							<div class="body_div">
								<div class="tab_title">备注:</div>
								<div class="tran_content_table">
									<table>
										<thead>
											<tr>
												<th width="20%">时间</th>
												<th width="20%">记录人</th>
												<th width="49%">内容</th>
												<th width="10%">操作</th>
											</tr>
										</thead>
										<tbody class='notetable'>
										</tbody>
									</table>
									<div class="closeshearch">
										展开<span class="glyphicon glyphicon-chevron-down"></span>
									</div>
								</div>
								<div class="tran_content_table">
									<textarea id="note" class="tran_textarea" placeholder="添加备注..."  maxlength="100"></textarea>
									<div class="record_save">
										<button class="btn btn-default-save savenote">保存备注</button>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="right-content-footer">
						<button class="btn btn-default meet_detial meet_record">查看会议记录</button>
						<button class="btn btn-default meet_update update_record">查看更新记录</button>
					</div>
				</div>
			</div>
		</span>


	</div>
	<div class="line1"></div>
	<div class="line2"></div>
	<div class="line3"></div>
</body>
<script>
	try {
		var viewCompInfo = ${viewCompInfo};
		var induList = ${induList};
		var stageList = ${stageList};
		var baskList = ${baskList};
		var noteList = ${noteList};
		var financplanList = ${financplanList};
		var version = ${version};
		var financList = ${financList};
		var entrepreneurList = ${entrepreneurList};
		var nowdate = "${nowdate}  ";
		var backtype="${backtype}";
	} catch (e) {

	}
</script>
<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/Template.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>
<script type="text/javascript" src="view/pc/common/js/jqPaginator.js"></script>
<script type="text/javascript" src="view/pc/common/js/select2.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/layer.js"></script>
<script type="text/javascript"
	src="view/pc/common/js/bootstrap-datepicker.js"></script>
<script type="text/javascript"
	src="view/pc/common/js/company_info_detail.js"></script>
</html>