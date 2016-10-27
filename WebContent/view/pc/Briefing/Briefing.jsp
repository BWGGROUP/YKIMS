<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<link rel="stylesheet" href="view/pc/common/css/select2.min.css">
<link rel="stylesheet" href="view/pc/common/css/briefing.css">
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
						class="head-title"> 搜索 > <span class="strong">机构简介</span>
					</span>
				</div>
				<div class="right-content-body">
					<div class="right-group-box">
						<div class="head">
							<div class="title">机构简介</div>
						</div>
						<div class="right-group-box-body">
<div class="meet_search">
	<div class="search_condition_title"> 选择机构:</div>
	<div class="search_condition">
	<select id="select_org" style="width:100%"class=" inputconf" multiple="multiple">

	</select>
	</div>
<div style="width:100%;margin-top:40px;text-align:center">
	<button class="btn btn-default-save" id="PDF" style="margin-right:10px">生成PDF</button>
	<button class="btn btn-default-save" id="excel" style="margin-left:10px">生成Excel</button>
	</div>
	</div>
						</div>
					</div>
				</div>
			</div>
		</span>
<iframe id="frame" style="display:none"></iframe>

	</div>
	<div class="line1"></div>
	<div class="line2"></div>
	<div class="line3"></div>
</body>
<script  type="text/javascript">

	var backtype="${backtype}";
</script>
<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/Template.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>
<script type="text/javascript" src="view/pc/common/js/layer.js"></script>
<script type="text/javascript" src="view/pc/common/js/select2.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/briefing.js"></script>
</html>