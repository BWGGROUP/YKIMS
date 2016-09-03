
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
<meta name="viewport"
	content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
<link rel="stylesheet" href="view/mobile/css/font.css">
<link rel="stylesheet" href="view/mobile/css/common.css">
<link rel="stylesheet" href="view/mobile/css/login.css">
</head>
<body>
	<div class="main-content"  style='background: url("../images/login_bgx.gif");'>
		<div class="header">
			<div class="header-left left-menu-btn"></div>
			<div class="header-center">
				<div class="logo"></div>
			</div>
			<div class="header-right"></div>
		</div>

	<div class="content-body">
		<div class="fromcontent">
			<div class="title">个人信息修改</div>
			<div class="body">
				<div class="item">
					<div class="lab">邮箱:</div>
					<div class="inp">
    ${userInfo.sys_user_email}
					</div>
				</div>
				<div class="item">
					<div class="lab">姓名:</div>
					<div class="inp">
						<input class="inputdef name"  placeholder="姓名" value="${userInfo.sys_user_name}"/>
					</div>
				</div>
				<div class="item">
					<div class="lab">英文名:</div>
					<div class="inp">
						<input class="inputdef en_name" placeholder="英文名" value="${userInfo.sys_user_ename}"/>
					</div>
				</div>
				<div class="item">
					<div class="inp">
						<button class="btn btn-default sub"
							style="width: 100%; margin-top: 10px;" code="${userInfo.sys_user_code}">修改用户信息</button>
					</div>
				</div>
			</div>
		</div>
	</div>
		</div>
</body>
<script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
<script type="text/javascript" src="view/mobile/js/common.js"></script>
<script type="text/javascript" src="view/mobile/js/userinfo.js"></script>
</html>