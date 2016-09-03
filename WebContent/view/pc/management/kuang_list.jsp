<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <link rel="stylesheet" href="view/pc/common/css/employee/kuang_list.css">


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
                管理 ><span class="strong">筐管理</span>
            </div>
            <div class="right-content-body">
                <div class="right-group-box">
                    <div class="head">
                        <div class="title">
                            筐管理
                        </div>
                    </div>
                    <div  class="right-group-box-body">
                        <div class="sharebox">
                        <input class="fn-input search-input" placeholder="筐名称">
                            <button class="btn btn-default search-btn">搜索</button>
                            <button class="btn btn-default add-btn">添加筐</button>
                        </div>
                        <table class="companytablelist">
                            <thead>
                            <tr>
                                <th style="width:15%;">名称</th>
                                <th style="width:20%;">权限</th>
                                <th style="width:50%;">人员</th>
                                <th style="width:15%;">操作</th>

                            </tr>
                            </thead>
                            <tbody id="tablecontent">
    <tr><td  colspan="4" style="width:100%;">暂无数据</td></tr>

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
<div class="line1"></div>
<div class="line2"></div>
<div class="line3"></div>
</body>
<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>
<script type="text/javascript" src="view/pc/common/js/Template.js"></script>
    <script type="text/javascript" src="view/pc/common/js/layer.js"></script>
<script type="text/javascript" src="view/pc/common/js/jqPaginator.js"></script>
<script type="text/javascript" src="view/pc/common/js/employee/kuang_list.js"></script>
</html>