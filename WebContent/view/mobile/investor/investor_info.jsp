<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<base href="<%=basePath%>">
<head lang="en">
<title>易凯投资</title>
<meta charset="UTF-8">
<meta name="keywords" content="易凯投资">
<meta name=”renderer” content=”webkit”>
<meta name="viewport"
	content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
<link rel="stylesheet" href="view/mobile/css/font.css">
<link rel="stylesheet" href="view/mobile/css/common.css">
<link rel="stylesheet" href="view/mobile/css/dropload.css">
<link rel="stylesheet" href="view/mobile/css/jqpagination.css">
<link rel="stylesheet" href="view/mobile/css/page.css">
<link rel="stylesheet" href="view/mobile/css/investor_info.css">



</head>
<body>
	<div class="main-content">
		<div class="header">
			<div class="header-left left-menu-btn">
    </div>
			<div class="header-center">
				<div class="logo"></div>
			</div>
			<div class="header-right"></div>
		</div>
		<div class="content-body">
			<div class="box">

				<div class="box-title">投资人详情
    <div class="goback"><button class="btn btn-default smart-btn">返回</button></div>
    </div>

				<div class="box-body">
					<div class="display-table">
						<div class="shgroup ">
							<div class="title no-border">投资人:</div>
							<div class="tiplist no-border">

								<ul class="peoplename" ro="0">
									<li data-i="code" class="investor"
										id='${investorInfo.base_investor_code}'>${investorInfo.base_investor_name}</li>
								</ul>
							</div>
						</div>
						<div class="shgroup " style="position: relative">
							<div class="title no-border">所属机构:</div>
							<div class="tiplist no-border">
								<ul class="peopleContent" ro="0">
									<%--<li data-i="1"><span class='comp'>经纬中国</span><span class='comp'>创始合伙人</span><span class='comp color-def'>(在职)</span></li>--%>

								</ul>
								<div id="add-touzi" class="addbtn"></div>
							</div>

						</div>
						<div class="shgroup ">
							<div class="title no-border">联系方式:</div>
							<div class="tiplist no-border">
								<ul class="" ro="0">
									<li data-i="1" class="basic" id="phone"></li>
									<li data-i="1" class="basic" id="email"></li>
									<li data-i="1" class="basic" id="wchat"></li>
								</ul>
							</div>
						</div>
						<div class="shgroup listtip">
							<div class="title no-border ">行业:</div>
							<div class="tiplist no-border">
								<ul class="induc" ro="1">
									<%--<li data-i="1">SNS社交网络</li>--%>
									<%--<li data-i="2">智能硬件</li>--%>
								</ul>
							</div>

						</div>
						<div class="shgroup listtip">
							<div class="title no-border ">近期关注:</div>
							<div class="tiplist no-border">
								<ul class="patty" ro="1">

								</ul>
							</div>

						</div>


					</div>
					<div class="shgroup " style="display: block;">
    <div class="morebtn tradeMore">
    <span class="glyphicon glyphicon-chevron-right tradm">
    </div>
						<div class="title no-border">参与交易:</div>
						<div class="tablepadding">
							<table>
								<thead>
									<tr>
										<th width="20%">时间</th>
										<th width="25%">被投公司</th>
										<th width="20%">阶段</th>
										<th width="20%">金额</th>
										<th width="15%">操作</th>
									</tr>
								</thead>
								<tbody class="stage">

								</tbody>
							</table>
    <div id="add-rongzi" class="addbtn"></div>
						</div>
					</div>

					<div class="shgroup  " style="margin-top: 35px; display: block">
						<div class="title no-border motitle">备注:</div>
						<div class="meeting-box">
							<table>
								<tbody class="notetable">

								</tbody>
							</table>
							<div class="closeshearch">
								更多<span class="glyphicon glyphicon-chevron-down"></span>
							</div>
						</div>
					</div>

				</div>
				<div class="shgroup  " style="margin-top: 10px">
					<div class="title no-border" style="display: block">添加备注:</div>
					<div class="meeting-box" style="display: block">
						<textarea placeholder="输入内容..." id="note" maxlength="100"></textarea>
					</div>
				</div>

				<div class="notecompet">
					<button class="btn btn-default btn-note">提交备注</button>
					<br>
					<button class="btn btn-default btn-default updlogClick">查看更新记录</button>
					<button class="btn btn-default btn-default meetingClick">查看会议记录</button>
				</div>
			</div>
		</div>
		
	</div>
	</div>
	<div id="tradePage" style="background-color: #ffffff;">
			<div class="page_body">
				<div class="box">
					<div class="box-title">
						近期交易
						<div class="box-return">
							<button class="returnBtn tradereturn">返回</button>
						</div>
					</div>
					<div class="box-body">
						<table id="pageTradeBody">
							<thead>
								<tr>
								    <th width="20%">时间</th>
								    <th width="25%">被投公司</th>
								    <th width="20%">阶段</th>
								    <th width="20%">金额</th>
								    <th width="15%">操作</th>
								</tr>
							</thead>
							<tbody id="pageTradeBody">
							</tbody>
						</table>
					</div>
					<div class="pageaction">
						<div class="gigantic pagination">
							<a href="#" class="previous" data-action="previous">&lsaquo;</a>
							<input type="tel" readonly="readonly" /> <a href="#"
								class="next" data-action="next">&rsaquo;</a>
							<lable class="totalSize tradeLable" id="totalSize"></lable>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 会议更多子页-->
		<div id="meetingPage" class="pageBack">
			<div class="page_body">
				<div class="box child">
					<div class="box-title">
						会议
						<div class="box-return">
							<button class="returnBtn meetingReturn">返回</button>
						</div>
					</div>
					<div class="box-body">
						<table id="pageMeetingBody">
							<thead>
								<tr>
									<th>时间</th>
									<th>参会人</th>
									<th>内容</th>
								</tr>
							</thead>
							<tbody class="meeting-tbody">
							</tbody>
						</table>
					</div>
					<div class="pageaction">
						<div class="gigantic pagination meetingPageaction">
							<a href="#" class="previous" data-action="previous">&lsaquo;</a>
							<input type="tel" readonly="readonly" /> <a href="#"
								class="next" data-action="next">&rsaquo;</a>
							<lable class="totalSize meetingLable" id="totalSize"></lable>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- 更新记录更多子页-->
		<div id="updlogPage" class="pageBack">
			<div class="page_body">
				<div class="box child">
					<div class="box-title">
						<div style="float:left"> 更新记录</div>
						<div class="box-return">
							<button class="returnBtn updlogReturn">返回</button>
						</div>
					</div>
					<div class="box-body">
						<table id="pageUpdlogBody">
							<thead>
								<tr>
									<th style="width:20%">时间</th>
									<th style="width:20%">更新人</th>
									<th style="width:20%">操作</th>
									<th style="width:40%">内容</th>
								</tr>
							</thead>
							<tbody >
							</tbody>
						</table>
					</div>
					<div class="pageaction">
						<div class="gigantic pagination updlogPageaction">
							<a href="#" class="previous" data-action="previous">&lsaquo;</a>
							<input type="tel" readonly="readonly" /> <a href="#"
								class="next" data-action="next">&rsaquo;</a>
							<lable class="totalSize updlogLable" id="totalSize"></lable>
						</div>
					</div>
				</div>
			</div>
		</div>
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
    var phone="${investorInfo['base_investor_phone']}";
    var email="${investorInfo['base_investor_email']};";
    var wechat="${investorInfo['base_investor_wechat']}";
    var backherf="${backherf}";
}catch(e){
	
}
    </script>
</html>
<script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
<script type="text/javascript" src="view/mobile/js/Template.js"></script>
<script type="text/javascript"
	src="view/mobile/js/jquery.jqpagination.js"></script>
<script type="text/javascript" src="view/mobile/js/common.js"></script>
<script type="text/javascript" src="view/mobile/js/page.js"></script>
<script type="text/javascript" src="view/mobile/js/dropload.js"></script>
<script type="text/javascript" src="view/mobile/js/investor_info.js"></script>