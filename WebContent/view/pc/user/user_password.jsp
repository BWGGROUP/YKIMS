
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
<base href="<%=basePath%>">
<head lang="en">
<title>易凯投资</title>
<meta charset="UTF-8">
<meta name="keywords" content="易凯投资">
<meta name=”renderer” content=”webkit”>
<meta http-equiv=”X-UA-Compatible” content=”IE=Edge,chrome=1″>
<link rel="stylesheet" href="view/pc/common/css/font.css">
<link rel="stylesheet" href="view/pc/common/css/common.css">
<link rel="stylesheet" href="view/pc/common/css/login.css">
<style>
.lab {
	width: 66px;
}
 body{
    background-image: none;
    }
</style>
</head>
<body>
	<jsp:include page="../common/head.jsp" />
	<div id="main-content" class="content">
		<div class="main-content-box main-content-box-left">
			<div class="left-menu-box"><jsp:include
					page="../common/left_menu.jsp" /></div>
		</div>
		<div class="main-content-box main-content-box-right">
			<div class="right-content">
				<div class="head">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					用户管理 ><span class="strong">密码管理</span>
				</div>

				<div class="right-content-body">
					<div class="right-group-box">
						<div class="head">
							<div class="title" style="text-align: left;">
								密码修改
							</div>

						</div>
						<div class="right-group-box-body">
							<div class="loginform" style="margin: 64px auto;">
								<div class="title"
									style="border-bottom: 1px solid #BBB6B6; text-align: left;">密码修改</div>
								<div class="body">
									<div class="item">
										<div class="lab">原密码:</div>
										<div class="inp">
											<input class="fn-input pass orpaw" type="password"
												placeholder="原密码" maxlength="25"/>
										</div>
									</div>
									<div class="item">
										<div class="lab">新密码:</div>
										<div class="inp">
											<input class="fn-input pass paw" type="password"
												placeholder="新密码(至少6位)" maxlength="25"/>
										</div>
									</div>
									<div class="item">
										<div class="lab">密码确认:</div>
										<div class="inp">
											<input class="fn-input pass besurepaw" type="password"
												placeholder="密码确认(至少6位)" maxlength="25"/>
										</div>
									</div>


									<div class="item">
										<div class="inp">
											<button class="btn btn-default sub"
												style="width: 100%; margin-top: 10px;"
												code="${userInfo.sys_user_code}">密码修改</button>
										</div>
									</div>

								</div>
							</div>

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

<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>
<script type="text/javascript" src="view/pc/common/js/user_password.js"></script>
</html>