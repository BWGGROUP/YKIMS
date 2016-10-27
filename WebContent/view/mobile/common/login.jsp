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
	<div class="main-content" style='background: url("../images/login_bgx.gif");'>
		<div class="header">
			<div class="header-left left-menu-btn"></div>
			<div class="header-center">
				<div class="logo"></div>
			</div>
			<div class="header-right"></div>
		</div>

	<div class="content-body">
		<div class="fromcontent">
			<div class="title">用户登录</div>
			<div class="body">
				<div class="item">
					<div class="lab">邮箱:</div>
					<div class="inp">
						<input class="inputdef email" placeholder="用户邮箱" maxlength="100"/>
					</div>
				</div>
				<div class="item">
					<div class="lab">密码:</div>
					<div class="inp">
						<input class="inputdef pass" type="password" placeholder="密码" maxlength="50"/>
					</div>
				</div>
				<div class="item">
					<div class="lab">验证码:</div>
					<div class="inp">
						<input class="inputdef yanzhengma" placeholder="验证码"  maxlength="4"/>
					</div>
				</div>
				<div class="item" style="display: block">
					<div style="text-align: right">
						<img class="Verify_code" src="Verify_code.html">
					</div>
				</div>
				<div class="item">
					<div class="lab">&nbsp;</div>
					<div class="inp txtright">
						<input id="jiluzhanghao" type="checkbox" value=""/>记住账号
					</div>
				</div>
				<div class="item">
					<div class="inp">
						<button class="btn btn-default sub"
							style="width: 100%; margin-top: 10px;">登录</button>
					</div>
				</div>
    <div style="text-align: right;color: #747474;text-decoration: underline;margin-top: 20px;"><span style="cursor: pointer;" class="forgetpaw">找回密码</span></div>
			</div>
		</div>
	</div>
    </div>
</body>
<script type="text/javascript">
    var WUI="${WUI}";
    var islogin=true;
    </script>
<script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
    <script type="text/javascript" src="view/mobile/js/Template.js"></script>
<script type="text/javascript" src="view/mobile/js/common.js"></script>
<script type="text/javascript" src="view/mobile/js/login.js"></script>
</html>