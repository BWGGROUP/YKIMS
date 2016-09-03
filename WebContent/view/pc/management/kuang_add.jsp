<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<link rel="stylesheet"
	href="view/pc/common/css/employee/kuang_manage.css">


</head>
<body>
	<jsp:include page="../common/head.jsp" />
	<div id="main-content" class="content">
		<div class="main-content-box main-content-box-left">
			<div class="left-menu-box">
				<jsp:include page="left.jsp" />
			</div>
		</div>
		<div class="main-content-box main-content-box-right">
			<div class="right-content">
				<div class="head">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					管理 > <span class="strong">筐管理</span>
				</div>
				<div class="right-content-body">
					<div class="right-group-box">
						<div class="head">
							<div class="title">筐管理</div>
						</div>
						<div class="right-group-box-body">
							<div class="select_kuang">
								<div class="items">
									<div class="lable ">名称:</div>
                                     <div class="con " ><input class="fn-input name-input" style="display:block" maxlength="20"></div>
								</div>
								<div class="items">
									<div class="lable">权限:</div>
    <c:forEach items="${juilist}" var="list">
    <div class="con">
    <input name='checkbox' type="checkbox" ${list.checked} ${list.disabled} code="${list.sys_juri_code}" maxlength="100"> <span>${list.sys_juri_name}</span>
    </div>
    </c:forEach>

								</div>
							</div>
							<div class="group-select">
								<div class="group1">
									<div class="lable">已选员工：</div>
									<select id="select1" multiple="multiple">

									</select>
								</div>
								<div class="group-center">
									<button class="add"><</button>
									<button class="add_all"><<</button>
									<button class="remove">></button>
									<button class="remove_all">>></button>
								</div>
								<div class="group2">
									<div class="lable">备选员工：</div>
									<select id="select2" multiple="multiple">
										<c:forEach items="${outlist}" var="list">
											<option code="${list.sys_user_code}">${list.sys_user_name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="action">
								<button class="btn btn-default submit">保存</button>
							</div>
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
<script>
        var code="${kuanginfo.sys_wad_code}";
    </script>
<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>
<script type="text/javascript" src="view/pc/common/js/Template.js"></script>
<script type="text/javascript"
	src="view/pc/common/js/employee/kuang_add.js"></script>
</html>