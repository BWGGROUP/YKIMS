<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <title>易凯投资</title>
    <meta charset="UTF-8">
    <meta name="keywords" content="易凯投资">
    <meta name=”renderer” content=”webkit”>
    <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
    <link rel="stylesheet" href="view/mobile/css/font.css">
    <link rel="stylesheet" href="view/mobile/css/common.css">
    <link rel="stylesheet" href="view/mobile/css/company_info.css">
    <link rel="stylesheet" href="view/mobile/css/dropload.css">
    <link rel="stylesheet" href="view/mobile/css/jqpagination.css">
    <link rel="stylesheet" href="view/mobile/css/page.css">

</head>
<body>
	<div class="main-content">
		<div class="header">
			<div class="header-left left-menu-btn"></div>
			<div class="header-center">
				<div class="logo"></div>
			</div>
			<div class="header-right"></div>
		</div>
		<div class="content-body">
			<div class="box">
				
				<div class="box-title">公司详情
				<div class="goback">
	              <button class="returnBtn ">返回</button>
	        	</div>
				</div>
				
				<div class="box-body">
					<div class="display-table">
						<div class="shgroup gongsi">
							<div class="title no-border">名称：</div>
							<div class="tiplist no-border">
								<ul class="names" ro="0">
									<li data-i="1" id="name"></li>
									<li data-i="2" id="fullname"></li>
									<li data-i="3" id="ename"></li>

								</ul>
							</div>

						</div>
						<div class="shgroup  listtip">
							<div class="title no-border ">关注筐：</div>
							<div class="tiplist no-border">
								<ul class="bask" ro="0">
									<%--<li data-i="1">互联网金融</li>--%>
									<%--<li data-i="2">社区社交</li>--%>
									<%--<li data-i="3">娱乐与内容</li>--%>
								</ul>
							</div>

						</div>
						<div class="shgroup listtip">
							<div class="title no-border ">行业：</div>
							<div class="tiplist no-border">
								<ul class="induc" ro="1">
									<%--<li data-i="1">SNS社交网络</li>--%>
									<%--<li data-i="2">智能硬件</li>--%>
								</ul>
							</div>

						</div>
						<div class="shgroup listtip">
							<div class="title no-border ">阶段：</div>
							<div class="tiplist no-border">
								<ul ro="5" id="ul-stage">
									<li tip="类型" cl="0" class="stage"></li>
									<%--<li tip="类型" cl="1">VC</li>--%>
									<%--<li tip="类型" cl="2">对冲基金</li>--%>
								</ul>

							</div>

						</div>
						<div class="shgroup contact">
							<div class="title no-border">联系人：</div>
							<div class="tiplist no-border">
								<ul class="people" ro="5">
									<%--<li><span class="comp">谢旭</span> <span class="comp">CEO</span><span class="comp">xiexu@inapp.com</span></li>--%>
								</ul>

							</div>
							<div id="add-people" class="addbtn"></div>
						</div>
						<div class="shgroup ">
							<div class="title no-border motitle">融资计划：</div>
							<div class="tablepadding">
								<table>
									<thead>
										<tr>
											<th width="20%">计划融资日期</th>
											<th width="80%">内容</th>
										</tr>
									</thead>
									<tbody class="financplan">
									</tbody>
									<%--<tr>--%>
									<%--<td>2015.05.16</td>--%>
									<%--<td>实物投资是指企业以现金、实物、无形资产等投入其他企业进行的投资</td>--%>
									<%--</tr>--%>
								</table>
								<div class="closefinacplan">
									更多<span class="glyphicon glyphicon-chevron-down"></span>
								</div>
								<div class="addrz">
									<button class="btn btn-rongz">添加融资计划</button>
								</div>
							</div>
						</div>
						<div class="shgroup topo">
							<div class="morebtn tradeMore" id="flanmore">
								<span class="glyphicon glyphicon-chevron-right">
							</div>
							<div class="title no-border motitle">融资信息:</div>
							<div class="tablepadding">
								<table>
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

							</div>
							<div class="addtradebtn"></div>
						</div>
						<div class="shgroup  tipedit" style="margin-top: 25px">
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
						<div class="shgroup  tipedit" style="margin-top: 10px">
							<div class="title no-border motitle">添加备注:</div>
							<div class="meeting-box">
								<textarea placeholder="输入内容..." class="notetext" maxlength="100"></textarea>
							</div>

						</div>
						<div class="notecompet">
							<button class="btn btn-default" id="addnote">提交备注</button>
							<br>
							<button class="btn btn-default btn-default updlogClick">查看更新记录</button>
							<button class="btn btn-default btn-default meetingClick">查看会议记录</button>
						</div>
					</div>
				</div>
			</div>
			

	

		</div>
	</div>
	<!-- 会议更多子页-->
			<div id="meetingPage" class="pageBack">
				<div class="page_body">
					<div class="box">
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
										<th>记录人</th>
										<th>公司/机构</th>
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
					<div class="box">
						<div class="box-title">
							<div style="float: left">更新记录</div>
							<div class="box-return">
								<button class="returnBtn updlogReturn">返回</button>
							</div>
						</div>
						<div class="box-body">
							<table id="pageUpdlogBody">
								<thead>
									<tr>
										<th style="width: 20%">时间</th>
										<th style="width: 20%">更新人</th>
										<th style="width: 20%">操作</th>
										<th style="width: 40%">内容</th>
									</tr>
								</thead>
								<tbody>
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
				<!--交易更多页面-->
			<div id="tradePage" style="background-color: #ffffff;">
				<div class="page_body">
					<div class="box">
						<div class="box-title">
							融资信息
							<div class="box-return">
								<button class="returnBtn tradereturn">返回</button>
							</div>
						</div>
						<div class="box-body">
							<table id="pageTradeBody">
								<thead>
									<tr>
										<th width="20%">投资日期</th>
										<th width="20%">阶段</th>
										<th width="20%">金额</th>
										<th width="30%">投资机构</th>
										<th width="10%">操作</th>
										</th>
									</tr>
								</thead>
								<tbody id="pageTradeBodya">
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
</body>
<script>
    var viewCompInfo=${viewCompInfo};
    var induList=${induList};
    var stageList=${stageList};
    var baskList=${baskList};
    var noteList=${noteList};
    var financplanList=${financplanList};
    var version=${version};
    var financList=${financList};
    var entrepreneurList=${entrepreneurList};
    var nowdate = "${nowdate}";
    var backtype="${backtype}";
    </script>
    <script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
    <script type="text/javascript" src="view/mobile/js/Template.js"></script>
    <script type="text/javascript"
    src="view/mobile/js/jquery.jqpagination.js"></script>
    <script type="text/javascript" src="view/mobile/js/page.js"></script>
    <script type="text/javascript" src="view/mobile/js/dropload.js"></script>
    <script type="text/javascript" src="view/mobile/js/common.js"></script>
    <script type="text/javascript" src="view/mobile/js/company_info.js"></script>
</html>