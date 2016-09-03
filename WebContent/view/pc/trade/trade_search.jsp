<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
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
<link rel="stylesheet" href="view/pc/common/css/datepicker.css">
<link rel="stylesheet" href="view/pc/common/css/jqPaginator.css">
<link rel="stylesheet" href="view/pc/common/css/trade_search.css">


</head>
<body>
    <jsp:include  page="../common/head.jsp"/>
	<div id="main-content" class="content">
		<div class="main-content-box main-content-box-left">
			<div class="left-menu-box"><jsp:include  page="../common/left_menu.jsp"/></div>
		</div>
		<div class="main-content-box main-content-box-right">
			<div class="right-content">
				<div class="head">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					搜索 ><span class="strong">近期交易</span>
				</div>

				<div class="right-content-body">
					<div class="right-group-box">
						<div class="head">
							<div class="title">
								近期交易<span class="small">筛选</span>
							</div>

						</div>
						<div class="right-group-box-body">
							<div class="search_condition">
								<ul>
									<li>时间：</li>
									<li><input type="text" class="span2 starttime"
										id="dp1" readonly> － <input type="text"
										class="span2 endtime"  id="dp2"
									readonly></li>
									<li><button class="btn btn_search " onclick="clearDate()">清除</button></li>
								</ul>
							</div>
    <div class="shgroup ">
    <div class="title">近期：</div>
    <div class="tiplist">
    <ul class="" ro="2">
    <li tip="近期" cl="1">一个月内</li>
    <li tip="近期" cl="3">三个月内</li>
    <li tip="近期" cl="6">半年内</li>
    <li tip="近期" cl="12">一年内</li>
    <li tip="近期" cl="24">两年内</li>
    </ul>
    </div>
    <div class="more">
    更多<span class="glyphicon glyphicon-chevron-down"></span>
    </div>
    </div>
							<div class="shgroup ">
								<div class="title">关注行业：</div>
								<div class="tiplist">
									<ul class="" ro="0">

    <c:forEach items="${induList}" var="list">
    <li tip="行业" cl="${list.sys_labelelement_code}">${list.sys_labelelement_name}</li>
    </c:forEach>
									</ul>
								</div>
								<div class="more">
									更多<span class="glyphicon glyphicon-chevron-down"></span>
								</div>
							</div>
							<div class="shgroup ">
								<div class="title">投资阶段：</div>
								<div class="tiplist">
									<ul class="" ro="1">
    <c:forEach items="${stageList}" var="list">
    <li tip="阶段" cl="${list.sys_labelelement_code}">${list.sys_labelelement_name}</li>
    </c:forEach>
									</ul>
								</div>
								<div class="more">
									更多<span class="glyphicon glyphicon-chevron-down"></span>
								</div>
							</div>
						</div>

					</div>
					<div class="right-group-box">
						<div class="head">
							<div class="title">已选条件</div>
						</div>
						<div id="condition" class="right-group-box-body"></div>
					</div>

					<div class="searchbox">
						<button class="btn btn-default screen">搜 索</button>
					</div>

					<div class="right-group-box trcontent" id="trcontent">

						<div class="right-group-box-body companytable">
							<table class="companytablelist">
								<thead>
									<tr>
										<th style="width: 15%;">时间</th>
										<th style="width: 15%;">公司</th>
										<th style="width: 15%;">阶段</th>
										<th style="width: 15%;">金额</th>
										<th style="width: 15%;">行业</th>
										<th style="width: 20%;">投资者</th>
									</tr>
								</thead>
								<tbody id="tablecontent">

								</tbody>
							</table>
						</div>

						<div class="customBootstrap">
							<ul class="totalSize" id="totalsize"　>
							</ul>
							<ul class="pagination" id="pagination1">

							</ul>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<div class="line1"></div>
	<div class="line2"></div>
	<div class="line3"></div>
</body>
<script type="text/javascript">
	var backtype="${backtype}";
</script>
<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/Template.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>
<script type="text/javascript" src="view/pc/common/js/jqPaginator.js"></script>
<script type="text/javascript" src="view/pc/common/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="view/pc/common/js/trade_search.js"></script>
</html>