
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<link rel="stylesheet" href="view/mobile/css/company_search.css">

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
			<div class="searchform">
				<span class="line"><input name="name" class="search-input"></span> <span
					class="line"><button class="btn search-btn">搜索</button></span>
				<div class="search_b_list" style="">
					<ul>

					</ul>
				</div>
			</div>
			<div class="box">
				<div class="box-title">
					公司<span class="small">筛选</span>
				</div>
			</div>
			<div class="box-body">
				<div class="display-table">
					<div class="shgroup ">
						<div class="title no-border">关注筐:</div>
						<div class="tiplist no-border">
							<ul class="" ro="0">
    <c:forEach items="${baskList}" var="list">
    <li tip="关注筐" cl="${list.sys_labelelement_code}">${list.sys_labelelement_name}</li>
    </c:forEach>
							</ul>
							<div class="more">
								<span class="glyphicon glyphicon-chevron-down"></span>
							</div>
						</div>
					</div>

					<div class="shgroup ">
						<div class="title">关注行业：</div>
						<div class="tiplist">
							<ul class="" ro="1">
    <c:forEach items="${induList}" var="list">
    <li tip="行业" cl="${list.sys_labelelement_code}">${list.sys_labelelement_name}</li>
    </c:forEach>
							</ul>
							<div class="more">
								<span class="glyphicon glyphicon-chevron-down"></span>
							</div>
						</div>

					</div>
					<div class="shgroup ">
						<div class="title">投资阶段：</div>
						<div class="tiplist">
							<ul class="" ro="2">
    <c:forEach items="${stageList}" var="list">
    <li tip="阶段" cl="${list.sys_labelelement_code}">${list.sys_labelelement_name}</li>
    </c:forEach>

							</ul>
							<div class="more">
								<span class="glyphicon glyphicon-chevron-down"></span>
							</div>
						</div>

					</div>
				</div>
				<div class="box border-bottom">
					<div class="box-title">已选条件</div>
					<div class="box-body">
						<div id="condition" class="right-group-box-body"
							style="min-height: 40px"></div>

					</div>
				</div>
				<div class="btn-box m">
					<a><button class="btn btn-default submit">筛 选</button></a>
				</div>
    <div id="trcontent" style="display: none">
				<table>
					<thead>
						<tr>
							<th width="40%">公司</th>
							<th width="30%">行业</th>
							<th width="30%">阶段</th>
						</tr>
					</thead>
					<tbody id="tablecontent">


					</tbody>
				</table>
				<div class="pageaction" style="display: none">
					<div class="gigantic pagination">
						<a href="#" class="previous" data-action="previous">&lsaquo;</a> <input
							type="tel" readonly="readonly" /> <a href="#" class="next"
							data-action="next">&rsaquo;</a>
						<lable class="totalSize" id="totalSize"></lable>
					</div>
				</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" >
	var backtype ="${backtype}";
</script>
<script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
<script type="text/javascript" src="view/mobile/js/Template.js"></script>
<script type="text/javascript" src="view/mobile/js/common.js"></script>
<script type="text/javascript"
	src="view/mobile/js/jquery.jqpagination.js"></script>
<script type="text/javascript" src="view/mobile/js/company_search.js"></script>
</html>