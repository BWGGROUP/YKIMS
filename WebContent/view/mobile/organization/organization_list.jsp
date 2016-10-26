<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String hiddenPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>
<!DOCTYPE html>
<html>

<base href="<%=basePath%>">
<head lang="en">
<title>易凯投资</title>
<meta charset="UTF-8">
<meta name="keywords" content="易凯投资">
<meta name=”renderer” content=”webkit”>
<meta name="viewport"
	content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
<link rel="stylesheet" href="view/mobile/css/font.css">
<link rel="stylesheet" href="view/mobile/css/common.css">
<link rel="stylesheet" href="view/mobile/css/jqpagination.css">
<link rel="stylesheet" href="view/mobile/css/organization_list.css">

</head>
<body>
	<div class="main-content">
		<div class="header">
			<div class="header-left left-menu-btn"></div>
			<div class="header-center">
				<div class="logo"></div>
			</div>
			<div class="header-right"></div>
		</div>
		<div class="content-body">
			<!--<div class="head-option">-->
			<!--<div class="search-option active"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></div>-->
			<!--<div class="add-option"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></div>-->
			<!--</div>-->
			<div class="box">
				<div class="box-title">投资机构清单</div>
				<div class="box-body">
					<table>
						<thead>
							<tr>
								<th width="30%">投资机构</th>
								<th width="20%">类型</th>
								<th width="30%">相关行业近期交易</th>
								<th width="20%">投过竞争公司</th>
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
				</div>
			</div>
			<div class="pageaction" id="pageaction">
				<div  id="gigantic" class="gigantic pagination">
					<a href="#" class="previous" data-action="previous">&lsaquo;</a> <input
						type="tel" readonly="readonly" /> <a href="#" class="next"
						data-action="next">&rsaquo;</a>
					<lable class="totalSize" id="totalSize">共${investment.totalCount}条</lable>
				</div>
			</div>
			<div>
			<input id="path" type="hidden" value="<%=hiddenPath%>">
    <div class="bottom-btn">
    <button class="btn btn-default" id="goback">重新搜索</button>
    <button class="btn btn-default" id="exprtExcel">导出表格</button>
    </div>
			</div>
		</div>
	</div>
	<script>
		var mainpage="${investment.pageCunt}";
		var pagenow="${investment.pageCount}";
	</script>
	<script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
    <script type="text/javascript" src="view/mobile/js/Template.js"></script>
	<script type="text/javascript" src="view/mobile/js/common.js"></script>
	<script type="text/javascript"
		src="view/mobile/js/jquery.jqpagination.js"></script>
	<script type="text/javascript"
		src="view/mobile/js/organization_list.js"></script>
</body>
</html>