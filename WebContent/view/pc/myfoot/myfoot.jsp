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
<meta http-equiv=”X-UA-Compatible” content=”IE=Edge,chrome=1″>
<link rel="stylesheet" href="view/pc/common/css/common.css">
<link rel="stylesheet" href="view/pc/common/css/font.css">
<link rel="stylesheet" href="view/pc/common/css/layer.css">
<link rel="stylesheet" href="view/pc/common/css/jqPaginator.css">
<link rel="stylesheet" href="view/pc/common/css/select2.min.css">
<link rel="stylesheet" href="view/pc/common/css/meeting_search.css">
<link rel="stylesheet" href="view/pc/common/css/myfoot.css">
</head>
<body>
    <jsp:include  page="../common/head.jsp"/>
	<div id="main-content" class="content">
		<span class="main-content-box main-content-box-left">
			<div class="left-menu-box">
    <jsp:include  page="../common/left_menu.jsp"/>
    </div>
		</span> <span class="main-content-box main-content-box-right">
			<div class="right-content">
				<div class="head">
					<span class="glyphicon glyphicon-home"> </span> <span
						class="head-title"> 搜索 > <span class="strong">我的足迹</span>
					</span>
				</div>
				<div class="right-content-body">
					<div class="right-group-box">
						<div class="head">
							<div class="title">我的足迹</div>
						</div>
						<div class="right-group-box-body">
							<div class="meet_search">
								<div class="search_condition_title">条件:</div>
								<div class="search_condition selecttypediv">
									<select id="select_type" class="inputconf selecttype">
										<option value="0">投资机构</option>
										<option value="1">近期交易</option>
										<option value="2">公司</option>
										<option value="3">会议</option>
										<option value="4">融资计划</option>
									</select>
								</div>
								<div class="search_condition handtypediv">
									<select id="select_con" class="inputconf handtype">
										<option value="YK-CRE">创建</option>
										<option value="YK-UPD">修改</option>
										<option value="YK-ADD">添加</option>
									</select>
								</div>
								<div class="search_condition" style="width: 90px;">
									<button class="btn btn-default-save searchClick">搜 索</button>
									<!-- btn-search-->
								</div>
								
							</div>

							<div class="div_line"></div>

							<div class="meet_search">
								<div class="meet_content">
									<table class="table_conf" id="trcontent" >
										<thead class="tablehead">
											
										</thead>
										<tbody class="tablecontent">

										</tbody>
									</table>
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
		</span>


	</div>
	<div class="line1"></div>
	<div class="line2"></div>
	<div class="line3"></div>
</body>
<script  type="text/javascript">
	var message='${message}';
	var backtype='${backtype}';
</script>
<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/Template.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>
<script type="text/javascript" src="view/pc/common/js/layer.js"></script>
<script type="text/javascript" src="view/pc/common/js/jqPaginator.js"></script>
<script type="text/javascript" src="view/pc/common/js/select2.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/myfoot.js"></script>
</html>