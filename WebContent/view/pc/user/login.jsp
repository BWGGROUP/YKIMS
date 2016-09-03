<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html >
<base href="<%=basePath%>">
<head lang="en">
<title>易凯投资</title>
<meta charset="UTF-8">
<meta name="keywords" content="易凯投资">
<meta name=”renderer” content=”webkit”>
<meta http-equiv=”X-UA-Compatible” content=”IE=Edge,chrome=1″>
<link rel="stylesheet" href="view/pc/common/css/font.css">
<link rel="stylesheet" href="view/pc/common/css/common.css">
<link rel="stylesheet" href="view/pc/common/css/layer.css">
<link rel="stylesheet" href="view/pc/common/css/login.css">
</head>
<body>
	<div class="topheader">
		<div class="content">
			<div class="logo"></div>
			<div class="sys_set">
				<div class="loginuser"></div>

			</div>
		</div>
	</div>
	<div id="main-content" class="content">
		<div class="loginform">
			<div class="title">用户登录</div>
			<div class="body">
				<div class="item">
					<div class="lab">邮箱:</div>
					<div class="inp">
						<input class="fn-input email" placeholder="用户邮箱" maxlength="100"/>
					</div>
				</div>
				<div class="item">
					<div class="lab">密码:</div>
					<div class="inp">
						<input class="fn-input pass" type="password" placeholder="密码" maxlength="50"/>
					</div>
				</div>
				<div class="item">
					<div class="lab">验证码:</div>
					<div class="inp">
						<input class="fn-input yanzhengma" placeholder="验证码" style="width:186px;margin-top: 6px;" maxlength="4"/>
                             <img class="Verify_code" src="Verify_code.html" style="float:right">
					</div>
				</div>
				<div class="item">
					<div class="lab">&nbsp;</div>
					<div class="inp">
						<input id="jiluzhanghao" type="checkbox" value=""/>记住账号
					</div>
						
					</div>
				</div>

				<div class="item">
					<div class="inp">
						<button class="btn btn-default sub"
							style="width: 100%; margin-top: 10px;">登录</button>
					</div>
				</div>
				<div style="text-align: right;color: #747474;text-decoration: underline;margin-top: 20px;">
				<span style="cursor: pointer;" class="forgetpaw">找回密码</span></div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>
    <script type="text/javascript" src="view/pc/common/js/layer.js"></script>
    <script type="text/javascript" src="view/pc/common/js/login.js"></script>
</html>