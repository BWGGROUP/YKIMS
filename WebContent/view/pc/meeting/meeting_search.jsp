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
<link rel="stylesheet" href="view/pc/common/css/jqPaginator.css">
<link rel="stylesheet" href="view/pc/common/css/select2.min.css">
<link rel="stylesheet" href="view/pc/common/css/meeting_search.css">
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
						class="head-title"> 搜索 > <span class="strong">会议</span>
					</span>
				</div>
				<div class="right-content-body">
					<div class="right-group-box">
						<div class="head">
							<div class="title">会议记录</div>
						</div>
						<div class="right-group-box-body">
							<div class="meet_search">
								<div class="search_condition_title">条件:</div>
								<div class="search_condition">
									<select id="select_org" class=" inputconf">
										<option></option>
									</select>
								</div>
								<div class="search_condition">
									<select id="select_com" class=" inputconf">
										<option></option>

									</select>
								</div>
								<div class="search_condition">
									<select id="select_peo" class=" inputconf">
										<option></option>

									</select>
								</div>
								<div class="search_condition">
									<select id="select_type" class=" inputconf">
										<option value=""　>全部</option>
										<c:forEach items="${typeLabelList}" var="item" >
											<option value="${item.sys_labelelement_code}">${item.sys_labelelement_name}</option>
										</c:forEach>

									</select>
								</div>
								
								<div class="search_condition searchdiv">
									<button class="btn btn-default-save">搜 索</button>
									<!-- btn-search-->
								</div>
								
							</div>

							<div class="div_line"></div>

							<div class="meet_search">
								<div class="meet_content">
									<table class="table_conf" id="trcontent" style="display:none">
										<thead class="tablehead">
											
										</thead>
										<tbody id="tablecontent">

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
	var backtype="${backtype}";
</script>
<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/Template.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>
<script type="text/javascript" src="view/pc/common/js/layer.js"></script>
<script type="text/javascript" src="view/pc/common/js/jqPaginator.js"></script>
<script type="text/javascript" src="view/pc/common/js/select2.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/meeting_search.js"></script>
</html>