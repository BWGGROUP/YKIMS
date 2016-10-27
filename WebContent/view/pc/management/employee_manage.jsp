<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

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
    <meta http-equiv=”X-UA-Compatible” content=”IE=Edge,chrome=1″ >
    <link rel="stylesheet" href="view/pc/common/css/common.css">
    <link rel="stylesheet" href="view/pc/common/css/font.css">
    <link rel="stylesheet" href="view/pc/common/css/layer.css">
    <link rel="stylesheet" href="view/pc/common/css/jqPaginator.css">
    <link rel="stylesheet" href="view/pc/common/css/employee/company_employee_manage.css">

</head>
<body>
 <jsp:include  page="../common/head.jsp"/>
<div id="main-content" class="content">
    <div class="main-content-box main-content-box-left">
        <div class="left-menu-box">
    <jsp:include  page="left.jsp"/>
        </div>
    </div>
    <div class="main-content-box main-content-box-right">
        <div class="right-content">
            <div class="head">
                <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                管理 ><span class="strong">员工信息</span>
            </div>
            <div class="right-content-body">
                <div class="right-group-box">
                    <div class="head">
                        <div class="title">
                            员工管理
                        </div>
                    </div>
                    <div  class="right-group-box-body">
    <div class="sharebox">
    <input class="fn-input search-input" placeholder="员工姓名或英文名">
    <button class="btn btn-default search-btn">搜索</button>
    <button id="btn_add" class="btn btn-default add_link">添加员工</button>
    </div>
                        <div class="employee_info">
                            <div class="info_table">
                                <table>
                                    <thead>
                                        <tr>
                                            <th width="15%">姓名</th>
                                            <th width="20%">邮箱</th>
                                            <th width="15%">微信</th>
                                            <th width="9%">参会次数</th>
                                            <th width="9%">添加会议</th>
                                            <th width="9%">更新次数</th>
                                            <th width="9%">添加记录</th>
                                            <th width="14%">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody class="info_content">

                                    </tbody>
                                </table>
    <div class="customBootstrap">
    	<ul class="totalSize" id="totalsize"　>
		</ul>
	    <ul class="pagination" id="pagination1" style="display:none">
	
	    </ul>
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
<script type="text/javascript" src="view/pc/common/js/Template.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>
    <script type="text/javascript" src="view/pc/common/js/layer.js"></script>
    <script type="text/javascript" src="view/pc/common/js/jqPaginator.js"></script>
<script type="text/javascript" src="view/pc/common/js/employee/company_employee_manage.js"></script>
</html>