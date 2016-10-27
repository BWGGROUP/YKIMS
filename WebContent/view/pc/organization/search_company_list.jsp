<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String hiddenPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html>
<base href="<%=basePath%>">
<head lang="zh-ch">
	<title>易凯投资</title>
    <meta charset="UTF-8">
    <meta name="keywords" content="易凯投资">
    <meta name=”renderer” content=”webkit”>
    <meta http-equiv=”X-UA-Compatible” content=”IE=Edge,chrome=1″ >
    <link rel="stylesheet" href="view/pc/common/css/common.css">
    <link rel="stylesheet" href="view/pc/common/css/font.css">
    <link rel="stylesheet" href="view/pc/common/css/jqPaginator.css">
    <link rel="stylesheet" href="view/pc/common/css/layer.css">
    <link rel="stylesheet" href="view/pc/common/css/search_company_list.css">


</head>
<body>
<jsp:include  page="../common/head.jsp"/>
<div id="main-content" class="content">
    <span class="main-content-box main-content-box-left">
        <div class="left-menu-box">
            <jsp:include  page="../common/left_menu.jsp"/>
        </div>
    </span>
    <span class="main-content-box main-content-box-right">
        <div class="right-content">
            <div class="head">
                <span class="glyphicon glyphicon-home">  </span>
                <span class="head-title">
                    搜索 > 投资机构 ><span class="strong">机构列表</span>
                </span>
            </div>
            <div class="right-content-body">
                <div class="right-group-box right-no_border">
                    <div class="head head_change">
                        <div class="title">
                            投资机构清单
                        </div>

                    </div>
                    <div class="right-group-box-body right_company_content">
                        <table>
                            <thead>
                                <tr>
                                    <th class="td_one">
                                        投资机构
                                    </th>

                                    <th class="td_two">
                                        类型
                                    </th>

                                    <th class="td_four">
                                        相关行业近期交易
                                    </th>
    <th class="td_three">
    投过竞争公司
    </th>
                                </tr>
                            </thead>
                            <tbody id="datalist">

    <c:choose>
    <c:when test="${investment['types']=='text'}">
    <c:if test="${empty investment['investmentList']}">
    <tr noexcel><td colSpan="4">暂无数据</td></tr>
    </c:if>
    <c:forEach items="${investment['investmentList']}" var="arrayListI">
    <tr>
    <td><a code="${arrayListI.base_investment_code}" class="company">${arrayListI.base_investment_name}</a></td>
    <td>${arrayListI.view_investment_typename}</td>

    <td>${arrayListI.companyName}</td>
    <td>
    </td>
    </tr>
    </c:forEach>
    </c:when>
    <c:otherwise>
    <c:if test="${empty investment['investmentList']}">
    <tr noexcel ><td colSpan="4">暂无数据</td></tr>
    </c:if>
    <c:forEach items="${investment['investmentList']}" var="arrayListI">
    <tr>
    <td><a code="${arrayListI.base_investment_code}" class="company">${arrayListI.base_investment_name}</a></td>
    <td>${arrayListI.view_investment_typename}</td>

    <td>${arrayListI.companyName}</td>
    <td>${arrayListI.view_investment_compcode}
    <%-- <c:choose>
<c:when test="${arrayListI.view_investment_compcode==''}">否  </c:when>
<c:otherwise>是</c:otherwise>
</c:choose> --%></td>
    </tr>
    </c:forEach>
    </c:otherwise>
    </c:choose>


                            </tbody>
                        </table>
                        <div>
                            <div class="customBootstrap">
                            	<c:if test="${investment.totalCount>0}">
	                            	<ul class="totalSize" id="totalsize"　>
	                            		<li>共${investment.totalCount}条</li>
	                            	</ul>
                            	</c:if>
                                <ul class="pagination" id="pagination1">
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="right-content-footer">
    <input id="path" type="hidden" value="<%=hiddenPath%>">
                    <button class="btn btn-default btn_research"  id="goback">重新搜索</button>
                    <button class="btn btn-default" id="exprtExcel">导出表格</button>
                </div>
            </div>
        </div>
    </span>


</div>

<div class="line1"></div>
<div class="line2"></div>
<div class="line3"></div>
</body>
    <script>
    var mainpage="${investment.pageCunt}";
    var pagenow="${investment.pageCount}";
    </script>
<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/Template.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>
    <script type="text/javascript" src="view/pc/common/js/layer.js"></script>
<script type="text/javascript" src="view/pc/common/js/search_company_list.js"></script>
<script type="text/javascript" src="view/pc/common/js/jqPaginator.js"></script>
</html>