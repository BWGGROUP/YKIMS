<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
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
<link rel="stylesheet" href="view/pc/common/css/jqPaginator.css">
<link rel="stylesheet" href="view/pc/common/css/select2.min.css">
<link rel="stylesheet" href="view/pc/common/css/investor_detail.css">




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
						class="head-title"> 搜索 > 投资机构 ><span class="strong">投资人</span>
					</span>
				</div>
				<div class="right-content-body">
					<div class="right-group-box">
						<div class="head">
							<div class="title">投资人详情</div>
    <div class="goback"><button class="btn btn-default smart-btn">返回</button></div>
						</div>
						<div class="right-group-box-body">
							<div class="shgroup ">
								<div class="title">投资人：</div>
								<div class="tiplist">
									<ul class="">
										<li><span class="canEdit investor" id='${investorInfo.base_investor_code}'>${investorInfo.base_investor_name}</span>
											<div class="txtEdit lable_hidden">
												<input class="txtInfo investorname-input" value="${investorInfo.base_investor_name}" maxlength="20"/>
												<button class="txtSave saveinvestorname">保存</button>
											</div></li>

									</ul>
								</div>
							</div>
							<div class="shgroup ">
								<div class="title">所属机构：</div>
								<div class="tiplist" style="margin-left: 120px;position: relative;">
									<ul class=" viroul peopleContent" style="min-height: 27px;width: 527px;">

									</ul>
    <div class="trade_add_img">
    <button id="btn_detail_add" class="btn btn_icon_add add_company"></button>
    </div>
								</div>
							</div>
							<div class="shgroup">
								<div class="title">联系方式：</div>
								<div class="tiplist">
									<ul class="">
										<li><span class="canEdit" id="phone" >${investorInfo['base_investor_phone']}</span>
											<div class="txtEdit lable_hidden">
												<input class="txtInfo phone" value="${investorInfo['base_investor_phone']}" maxlength="11"/>
												<button class="txtSave savephone">保存</button>
											</div></li>
										<li><span class="canEdit" id="email">${investorInfo['base_investor_email']}</span>
											<div class="txtEdit lable_hidden">
												<input class="txtInfo email" value="${investorInfo['base_investor_email']}" maxlength="100"/>
												<button class="txtSave saveemail">保存</button>
											</div></li>
										<li><span class="canEdit " id="wchat">${investorInfo['base_investor_wechat']}</span>
											<div class="txtEdit lable_hidden">
												<input class="txtInfo wchat" value="${investorInfo['base_investor_wechat']}" maxlength="100"/>
												<button class="txtSave savewchat">保存</button>
											</div></li>
									</ul>
								</div>
							</div>
							<div class="shgroup">
								<div class="title">
									<span class="tip-edit hangyeedit">行业<span class="caret"></span>：
									</span>
								</div>
								<div class="tiplist fontColorBlack">
									<ul class="induc">

									</ul>
								</div>
							</div>
							<div class="shgroup">
								<div class="title">
									<span class="tip-edit lastcare">近期关注<span class="caret"></span>：
									</span>
								</div>
								<div class="tiplist fontColorBlack">
									<ul class="patty">

									</ul>
								</div>
							</div>
						</div>

						<div class="tran_body">
							<div class="tab_title">参与交易：</div>
							<div class="tran_content_table tran_content">
								<table>
    <thead>
    <tr class="nohover">
    <th width="20%">时间</th>
    <th width="30%">被投公司</th>
    <th width="20%">阶段</th>
    <th width="20%">金额</th>
    <th width="10%">操作</th>
    </tr>
    </thead>
									<tbody class="stage">
    </tbody>
								</table>

							</div>
    <button id="btn_trade_add" class="btn btn_icon_add add_company"></button>
    <div class="lockmore">更多</div>
						</div>
						<div class="tran_body">
							<div class="tab_title">备注：</div>
							<div class="tran_content_table tran_content_note">
								<table>
									<thead>
										<tr>
											<th class="td_colsone">时间</th>
											<th class="td_colstwo">记录人</th>
											<th class="td_colsthree">内容</th>
											<th class="td_colsfour">操作</th>
										</tr>
									</thead>
    <tbody class="notetable"></tbody>

								</table>
    <div class="closeshearch" >
    更多<span class="glyphicon glyphicon-chevron-down"></span>
    </div>
							</div>
							<div class="textarea_detial">
								<textarea id="note" class="tran_textarea" placeholder="添加备注..." maxlength="100"></textarea>
								<div class="record_save">

									<button class="btn btn-default-save savenote">保存备注</button>
								</div>
							</div>
						</div>
					</div>
					<div class="detail_record">
						<button class="btn btn_record meet_record">查看会议记录</button>
						<button class="btn btn_record update_record">查看更新记录</button>
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
    try{
    var investor_inducont="${investorInfo['view_investor_inducont']}";
    var investor_payattcont="${investorInfo['view_investor_payattcont']}";
    var investor_cont="${investorInfo['view_investment_cont']}";
    var viewTradeInfo=${viewTradeInfo};
    var noteList=${noteList};
    var investorbInfo=${baseInfo};
    var induList=${induList};
    var unDelLabelList=${unDelLabelList};
    var backherf="${backherf}";
    }catch(e){}


    </script>
<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/Template.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>
<script type="text/javascript" src="view/pc/common/js/jqPaginator.js"></script>
<script type="text/javascript" src="view/pc/common/js/select2.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/layer.js"></script>
<script type="text/javascript"
	src="view/pc/common/js/investor_detail.js"></script>
</html>