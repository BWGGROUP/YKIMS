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
    <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
    <link rel="stylesheet" href="view/mobile/css/font.css">
    <link rel="stylesheet" href="view/mobile/css/common.css">
    <link rel="stylesheet" href="view/mobile/css/search_main.css">

</head>
<body>
<div class="main-content">
    <div class="header">
        <div class="header-left left-menu-btn"></div>
        <div class="header-center"><div class="logo"></div></div>
        <div class="header-right"></div>
    </div>
    <div class="content-body">
        <div class="senior">
           <a da-href="initInvestmentSearch.html"> <span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span> 高级筛选</a>
        </div>
        <div class="searchform">
          <form  method="post" style="height:100%" id="form">
            <input type="hidden" name="type" value="1">
            <span class="line"><input name="name"  class="search-input"></span>
            <span class="line"><button  type="button" class="btn search-btn">搜索</button></span>
          </form>
            <div class="search_b_list" style="">
               
            </div>
        </div>

    </div>

</div>

</body>
    <script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
    <script type="text/javascript" src="view/mobile/js/Template.js"></script>
    <script type="text/javascript" src="view/mobile/js/common.js"></script>
    <script type="text/javascript" src="view/mobile/js/search_main.js"></script>
</html>