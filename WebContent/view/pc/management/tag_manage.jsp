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
    <link rel="stylesheet" href="view/pc/common/css/select2.min.css">
    <link rel="stylesheet" href="view/pc/common/css/employee/tag_manage.css">

</head>
<body>
<jsp:include page="../common/head.jsp" />
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
                管理 ><span class="strong">标签管理</span>
            </div>
            <div class="right-content-body">
                <div class="right-group-box">
                    <div class="head">
                        <div class="title">
                            标签管理
                        </div>
                    </div>
                    <div  class="right-group-box-body">
                        <div class="select_kuang">

                            <div class="selector">
                                <select  class="txtInfo" style="width: 200px;">
    <option></option>
    <c:forEach items="${labs}" var="list">
    <option value="${list.value}">${list.key}</option>
    </c:forEach>
    </select>
                            </div>

                        </div>
                        <div class="listcontent">
                            <ul class="edit-tip-box1">


                            </ul>
                            <div class="addbox">
                                <input class="add_input" maxlength="50">
                                <button class="btn btn-default btn-add">添加</button>
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
<script type="text/javascript" src="view/pc/common/js/select2.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/employee/tag_manage.js"></script>
</html>