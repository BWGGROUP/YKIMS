<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

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
<meta http-equiv=”X-UA-Compatible” content=”IE=Edge,chrome=1″ >
<link rel="stylesheet" href="view/pc/common/css/common.css">
<link rel="stylesheet" href="view/pc/common/css/font.css">
<link rel="stylesheet" href="view/pc/common/css/jqPaginator.css">
<link rel="stylesheet" href="view/pc/common/css/company_search.css">

</head>
<body>
    <jsp:include  page="../common/head.jsp"/>
<div id="main-content" class="content">
    <div class="main-content-box main-content-box-left">
        <div class="left-menu-box">
    <jsp:include  page="../common/left_menu.jsp"/>
        </div>
    </div>
<div class="main-content-box main-content-box-right">
    <div class="right-content">
        <div class="head">
            <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
            搜索 ><span class="strong">公司</span>
        </div>
        <div class="searchform">
            <span class="line"><input class="search-input"/></span>
            <span class="line"><button class="btn search-btn">搜索</button></span>
            <div class="search_b_list">
                <ul>
                </ul>
            </div>
        </div>

        <div class="right-content-body">
            <div class="right-group-box">
                <div class="head">
                    <div class="title">
                        公 司<span class="small">筛选</span>
                    </div>

                </div>
                <div  class="right-group-box-body">

                    <div class="shgroup ">
                        <div class="title">关注筐：</div>
                        <div class="tiplist">
                            <ul class="" ro="0">
    <c:forEach items="${baskList}" var="list">
    <li tip="关注筐" cl="${list.sys_labelelement_code}">${list.sys_labelelement_name}</li>
    </c:forEach>

                            </ul>
                        </div>
                        <div class="more">
                            更多<span class="glyphicon glyphicon-chevron-down" aria-hidden="true"></span>
                        </div>
                    </div>
                    <div class="shgroup ">
                        <div class="title">关注行业：</div>
                        <div class="tiplist">
                            <ul class=""  ro="1">
    <c:forEach items="${induList}" var="list">
    <li tip="行业" cl="${list.sys_labelelement_code}">${list.sys_labelelement_name}</li>
    </c:forEach>
                            </ul>
                        </div>
                        <div class="more">
                            更多<span class="glyphicon glyphicon-chevron-down" ></span>
                        </div>
                    </div>
                    <div class="shgroup ">
                        <div class="title">投资阶段：</div>
                        <div class="tiplist">
                            <ul class=""  ro="2">
    <c:forEach items="${stageList}" var="list">
    <li tip="阶段" cl="${list.sys_labelelement_code}">${list.sys_labelelement_name}</li>
    </c:forEach>
                            </ul>
                        </div>
                        <div class="more">
                            更多<span class="glyphicon glyphicon-chevron-down" ></span>
                        </div>
                    </div>
                </div>




            </div>
            <div class="right-group-box">
                <div class="head">
                    <div class="title">
                        已选条件
                    </div>
                </div>
                <div id="condition" class="right-group-box-body">

                </div>
            </div>

            <div class="searchbox">
                <button class="btn btn-default screen">筛 选</button>
            </div>

            <div class="right-group-box companytable-box">

                <div class="right-group-box-body " id="trcontent">
                    <table class="companytablelist">
                        <thead>
                        <tr>
                            <th style="width:30%;">公司</th>
                            <th style="width:25%;">关注筐</th>
                            <th style="width:25%;">行业</th>
                            <th style="width:20%;">阶段</th>


                        </tr>
                        </thead>
                        <tbody id="tablecontent">

                        </tbody>
                    </table>
    <div class="customBootstrap">
    	<ul class="totalSize" id="totalsize"　>
		</ul>
	    <ul class="pagination" id="pagination1">
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
<script  type="text/javascript">
	var backtype="${backtype}";
</script>
<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/Template.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>
<script type="text/javascript" src="view/pc/common/js/jqPaginator.js"></script>
<script type="text/javascript" src="view/pc/common/js/company_search.js"></script>
</html>