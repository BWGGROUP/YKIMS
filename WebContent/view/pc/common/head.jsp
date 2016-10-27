<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>易凯投资</title><!-- 信息管理系统 -->
</head>
<body>
	<div class="topheader">
		<div class="content">
		<div class="logo">	<a href="<%=basePath%>" style="width: 100%;height: 100%;display: -webkit-box;"></a></div>
			<div class="sys_set">
				<div class="loginuser">
					<!--<span> <img class="headerimg" src="images/def_head.png"> </span>-->
					<button class="btn loguserbtn"> Hi,${sessionScope['USERINFO']['sys_user_name']}<span class="caret"></span> </button>
                    <div class="user-menu">
                        <ul>
                           <a menu-u-href="userinfo.html"><li><span class="glyphicon glyphicon-user" style="padding-right: 9px;"></span>用户信息</li></a>
                           <a menu-u-href="userpassword.html"><li><span class="glyphicon glyphicon-cog" style="padding-right: 9px;"></span>密码管理</li></a>

    <a menu-u-href="admin/employee.html"><li><span class="glyphicon glyphicon-wrench" style="padding-right: 9px;"></span>后台管理</li></a>
    <a menu-u-href="logout.html"><li><span class="glyphicon glyphicon-off" style="padding-right: 9px;"></span>退出登录</li></a>
                        </ul>
                    </div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>