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
<head lang="en">
<base href="<%=basePath%>">
<title>易凯投资</title>
<meta charset="UTF-8">
<meta name="keywords" content="易凯投资">
<meta name=”renderer” content=”webkit”>
<meta http-equiv=”X-UA-Compatible” content=”IE=Edge,chrome=1″>
<link rel="stylesheet" href="view/pc/common/css/common.css">
<link rel="stylesheet" href="view/pc/common/css/font.css">
<link rel="stylesheet" href="view/pc/common/css/select2.min.css">
<link rel="stylesheet" href="view/pc/common/css/datepicker.css">
<link rel="stylesheet" href="view/pc/common/css/jqPaginator.css">
<link rel="stylesheet" href="view/pc/common/css/financing_search.css">
</head>
<body>
    <jsp:include  page="../common/head.jsp"/>
	<div id="main-content" class="content">
		<span class="main-content-box main-content-box-left">
			<div class="left-menu-box"><jsp:include  page="../common/left_menu.jsp"/></div>
		</span> <span class="main-content-box main-content-box-right">
			<div class="right-content">
				<div class="head">
					<span class="glyphicon glyphicon-home"> </span> <span
						class="head-title"> 搜索 > <span class="strong">融资计划</span>
					</span>
				</div>
				<div class="right-content-body">
					<div class="right-group-box">
						<div class="head">
							<div class="title">融资计划</div>
						</div>
						<div class="right-group-box-body">
							<div class="search_plan">
								<div class="search_condition">
									<ul>
										<li>截止日期:</li>
										<li><input type="text" class=" inputconf" id="dp1"
											data-date-format="yyyy-mm-dd" readonly> <!--<button id="btn_search" class="btn btn_search">搜索</button>-->
										</li>
										<li>公司名称:</li>
										<li><select id="chosecompany" class="inputconf" >
												<option></option>
										</select></li>
										<li>
											<button id="btn_search" class="btn btn-default-save">搜索</button>
											<!--<button id="btn_search" class="btn btn_search">搜索</button>-->
										</li>
									</ul>
								</div>
								<div class="search_planContent lable_hidden">
									<table class="table_conf">
										<thead>
											<tr>
												<th width="20%">计划融资日期</th>
												<th width="30%">公司名称</th>
												<th>内容</th>
											</tr>
										</thead>
										<tbody id="tablecontent">

										</tbody>
									</table>
									<div>
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
				</div>
			</div>
		</span>


	</div>
	<div class="line1"></div>
	<div class="line2"></div>
	<div class="line3"></div>
</body>
<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
    <script type="text/javascript" src="view/pc/common/js/Template.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>
<script type="text/javascript"
	src="view/pc/common/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="view/pc/common/js/jqPaginator.js"></script>
<script type="text/javascript" src="view/pc/common/js/select2.min.js"></script>
<script type="text/javascript"
	src="view/pc/common/js/financing_search.js"></script>
</html>