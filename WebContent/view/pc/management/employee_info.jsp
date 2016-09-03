<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
    <link rel="stylesheet" href="view/pc/common/css/layer.css">
<link rel="stylesheet"
	href="view/pc/common/css/employee/company_employee_info.css">


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
					管理 ><span class="strong">员工信息详情</span>
				</div>
				<div class="right-content-body">
					<div class="right-group-box">
						<div class="head">
							<div class="title">员工信息</div>
						</div>
						<div class="right-group-box-body">
							<div class="items">
								<div class="lable ">姓名:</div>
								<div class="con edittext name">${sysUserInfo.sys_user_name}</div>
								<div class="con input">
									<input class="fn-input name-input"
										value="${sysUserInfo.sys_user_name}" maxlength="20">
									<button class="btn btn-default smart save-name">保存</button>
								</div>
                                <div class="con forgetpaw">重置密码</div>
							</div>

							<div class="items">
								<div class="lable ">英文名:</div>
								<div class="con edittext en_name">${sysUserInfo.sys_user_ename}</div>
								<div class="con input">
									<input class="fn-input en_name-input"
										value="${sysUserInfo.sys_user_ename}" maxlength="40">
									<button class="btn btn-default smart save-en_name">保存</button>
								</div>
							</div>

							<div class="items">
								<div class="lable ">邮箱:</div>
								<div class="con edittext email">${sysUserInfo.sys_user_email}</div>
								<div class="con input">
									<input class="fn-input email-input"
										value="${sysUserInfo.sys_user_email}" maxlength="200">
									<button class="btn btn-default smart save-email">保存</button>
								</div>
							</div>
							<div class="items">
								<div class="lable ">手机号:</div>
								<div class="con edittext phone">${sysUserInfo.sys_user_phone}</div>
								<div class="con input">
									<input class="fn-input phone-input"
										value="${sysUserInfo.sys_user_phone}" maxlength="11">
									<button class="btn btn-default smart save-phone">保存</button>
								</div>
							</div>

							<div class="items">
								<div class="lable ">微信号:</div>
								<div class="con edittext weichat">${sysUserInfo.sys_user_wechatnum}</div>
								<div class="con input">
									<input class="fn-input weichat-input"
										value="${sysUserInfo.sys_user_wechatnum}" maxlength="20">
									<button class="btn btn-default smart save-weichat">保存</button>
								</div>
							</div>


							<div class="items">
								<div class="lable ">所属筐:</div>
								<div class="con">
									<c:forEach items="${wads}" var="list">
										<span class="comp">${list.sys_wad_name}</span>
									</c:forEach>
								</div>
							</div>
							<div class="items">
								<div class="lable ">参会次数:</div>
								<div class="con">
									<c:choose>
										<c:when test="${sysUserInfo.sys_user_cmnum==null}">0</c:when>
										<c:otherwise>${sysUserInfo.sys_user_cmnum}</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="items">
								<div class="lable ">更新次数:</div>
								<div class="con">
									<c:choose>
										<c:when test="${sysUserInfo.sys_user_updreconum==null}">0</c:when>
										<c:otherwise>  ${sysUserInfo.sys_user_updreconum}</c:otherwise>
									</c:choose>
								</div>
							</div>

							<div class="items">
								<div class="lable ">添加会议次数:</div>
								<div class="con">
									<c:choose>
										<c:when test="${sysUserInfo.sys_user_addmnum==null}">0</c:when>
										<c:otherwise>  ${sysUserInfo.sys_user_addmnum}</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="items">
								<div class="lable ">添加记录次数:</div>
								<div class="con">
									<c:choose>
										<c:when test="${sysUserInfo.sys_user_addreconum==null}">0</c:when>
										<c:otherwise>  ${sysUserInfo.sys_user_addreconum}</c:otherwise>
									</c:choose>
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
<script>
	var code = "${sysUserInfo.sys_user_code}";
</script>
<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
    <script type="text/javascript" src="view/pc/common/js/Template.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>
    <script type="text/javascript" src="view/pc/common/js/layer.js"></script>
<script type="text/javascript"
	src="view/pc/common/js/employee/company_employee_info.js"></script>
</html>