<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
<base href="<%=basePath%>">
<title>易凯投资</title>
<meta charset="UTF-8">
<meta name="keywords" content="易凯投资">
<meta name=”renderer” content=”webkit”>
<meta name="viewport"
	content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
<link rel="stylesheet" href="view/mobile/css/font.css">
<link rel="stylesheet" href="view/mobile/css/common.css">
<link rel="stylesheet" href="view/mobile/css/jqpagination.css">
<link rel="stylesheet" href="view/mobile/css/dropload.css">
<link rel="stylesheet" href="view/mobile/css/financing_search.css">

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
				<div class="box-title">融资计划</div>
				<div class="box-body">

					<div class="display-table">
						<div class="shgroup  ">
							<div class="title no-border rongzdate">截止日期:</div>
							<div class="tiplist no-border">
								<input id="datePicker" style="width: 235px;" type="date"
									class="inputdef" autocomplete="off">

							</div>

						</div>
						<div class="shgroup  ">
							<div class="title rongzdate">公司名称:</div>
							<div class="tiplist ">
								<input readonly UNSELECTABLE='true' onfocus="this.blur();"
									style="width: 235px;" class="inputdef cmopany">

							</div>

						</div>

					</div>
					<div
						style="width: 70%;padding-bottom: 20px;margin: 20px auto 0;">
						<button class="btn btn-default btn-rongz">搜索</button>
					</div>
					<div id="trcontent" style="display: none">
						<table>
							<thead>
								<tr>
									<th width="25%">时间</th>
									<th width="25%">公司</th>
									<th width="50%">内容</th>

								</tr>
							</thead>
							<tbody id="tablecontent">

							</tbody>
						</table>
						<div class="pageaction" style="display: none">
							<div class="gigantic pagination">
								<a href="#" class="previous" data-action="previous">&lsaquo;</a>
								<input type="tel" readonly="readonly" /> <a href="#"
									class="next" data-action="next">&rsaquo;</a>
								<lable class="totalSize" id="totalSize"></lable>
							</div>
						</div>
					</div>


				</div>
			</div>
		</div>
	</div>


	<script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
	<script type="text/javascript" src="view/mobile/js/Template.js"></script>
	<script type="text/javascript" src="view/mobile/js/common.js"></script>
	<script type="text/javascript"
		src="view/mobile/js/jquery.jqpagination.js"></script>
	<script type="text/javascript" src="view/mobile/js/dropload.js"></script>
	<script type="text/javascript" src="view/mobile/js/financing_search.js"></script>
</body>
</html>