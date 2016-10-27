
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
    <style>
    .lab {
    width: 65px;
    font-size: 15px;
    text-align: right;
    margin-right: 8px;
    }
    </style>
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
			<div class="title">密码管理</div>
			<div class="body">
				<div class="item">
					<div class="lab">原密码:</div>
					<div class="inp">
						<input class="inputdef orpaw" type="password" placeholder="原密码" />
					</div>
				</div>
				<div class="item">
					<div class="lab">新密码:</div>
					<div class="inp">
						<input class="inputdef paw" type="password" placeholder="新密码(至少6位)" />
					</div>
				</div>
				<div class="item">
					<div class="lab">密码确认:</div>
					<div class="inp">
						<input class="inputdef besurepaw" type="password" placeholder="密码确认(至少6位)" />
					</div>
				</div>

				<div class="item">
					<div class="inp">
						<button class="btn btn-default sub"
							style="width: 100%; margin-top: 10px;" code="${userInfo.sys_user_code}">修改密码</button>
					</div>

				</div>
			</div>
		</div>
	</div>
	</div>
</body>
<script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
<script type="text/javascript" src="view/mobile/js/common.js"></script>
<script type="text/javascript" src="view/mobile/js/user_password.js"></script>
</html>