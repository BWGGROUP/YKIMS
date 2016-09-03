    <%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8"%>
            <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
        <!DOCTYPE html>
        <html>
        <head lang="en">
        <base href="<%=basePath%>">
    <title>易凯投资</title>
    <meta charset="UTF-8">
    <meta name="keywords" content="易凯投资">
    <meta name=”renderer” content=”webkit”>
    <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
    <link rel="stylesheet" href="view/mobile/css/font.css">
    <link rel="stylesheet" href="view/mobile/css/common.css">
    <link rel="stylesheet" href="view/mobile/css/jqpagination.css">
    <link rel="stylesheet" href="view/mobile/css/trade_search.css">

    <script>

    </script>
</head>
<body>
<div class="main-content">
    <div class="header">
        <div class="header-left left-menu-btn"></div>
        <div class="header-center"><div class="logo"></div></div>
        <div class="header-right"></div>
    </div>
    <div class="content-body">
        <div class="box">
            <div class="box-title">
                近期交易<span class="small">筛选</span>
            </div>
        </div>
        <div class="box-body">
            <div class="display-table">
                <div class="shgroup ">
                    <div class="title no-border" > 起始时间:</div>
                    <div class="tiplist no-border">
                        <input  type="month" class="inputdef starttime" >
                    </div>
                </div>

                    <div class="shgroup ">
                        <div class="title " >截止时间:</div>
                        <div class="tiplist ">
                            <input  type="month" class="inputdef endtime" >
                        </div>
                    </div>
        <div class="shgroup ">
        <div class="title">近期:</div>
        <div class="tiplist">
        <ul class="" ro="2">
        <li tip="近期" cl="1">一个月内</li>
        <li tip="近期" cl="3">三个月内</li>
        <li tip="近期" cl="6">半年内</li>
        <li tip="近期" cl="12">一年内</li>
        <li tip="近期" cl="24">两年内</li>
        </ul>
        <div class="more">
        <span class="glyphicon glyphicon-chevron-down" ></span>
        </div>
        </div>
        </div>
                <div class="shgroup ">
                    <div class="title " >关注行业:</div>
                    <div class="tiplist ">
                        <ul class=""  ro="0">
        <c:forEach items="${induList}" var="list">
        <li tip="行业" cl="${list.sys_labelelement_code}">${list.sys_labelelement_name}</li>
        </c:forEach>
                        </ul>
                        <div class="more">
                            <span class="glyphicon glyphicon-chevron-down" ></span>
                        </div>
                    </div>
                </div>
                <div class="shgroup ">
                    <div class="title">投资阶段:</div>
                    <div class="tiplist">
                        <ul class=""  ro="1">
        <c:forEach items="${stageList}" var="list">
        <li tip="阶段" cl="${list.sys_labelelement_code}">${list.sys_labelelement_name}</li>
        </c:forEach>

                        </ul>
                        <div class="more">
                            <span class="glyphicon glyphicon-chevron-down" ></span>
                        </div>
                    </div>
                </div>

                </div>
            <div class="box border-bottom">
                <div class="box-title">
                    已选条件
                </div>
                <div class="box-body">
                    <div id="condition" class="right-group-box-body" style="min-height: 40px">

                    </div>

                </div>
            </div>
            <div class="btn-box m">
                <a ><button class="btn btn-default submit">搜 索</button></a>
            </div>
        <div id="trcontent" style="display: none">
            <table >
                <thead>
                <tr>
                    <th width="30%">
                        时间
                    </th>
                    <th width="25%">
                        公司
                    </th>
                    <th width="20%">
                        金额
                    </th>
                    <th width="30%">
                        投资者
                    </th>
                </tr>
                </thead>
                <tbody id="tablecontent">


                </tbody>
            </table>
            <div class="pageaction"  style="display: none">
                <div class="gigantic pagination">
                    <a href="#" class="previous" data-action="previous">&lsaquo;</a>
                    <input type="tel" readonly="readonly" />
                    <a href="#" class="next" data-action="next">&rsaquo;</a>
                    <lable class="totalSize" id="totalSize"></lable>
                </div>
            </div>
            </div>
            </div>
    </div>
</div>



</body>
	<script type="text/javascript" >
		var backtype="${backtype}";
	</script>
    <script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
    <script type="text/javascript" src="view/mobile/js/Template.js"></script>
    <script type="text/javascript" src="view/mobile/js/common.js"></script>
    <script type="text/javascript" src="view/mobile/js/jquery.jqpagination.js"></script>
    <script type="text/javascript" src="view/mobile/js/trade_search.js"></script>
</html>