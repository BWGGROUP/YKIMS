<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

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
                搜索 ><span class="strong">投资机构</span>
            </div>
            <div class="searchform">
    <form  method="post"  id="form" onsubmit="return checsubmit()">
    			<input type="hidden" id="type" name="type" value="1"/>
                <span class="line"><input type="text" class="search-input" name="name" autocomplete="off"/></span>
                <span class="line"><button type="button" class="btn search-btn">搜索</button></span>
    </form>
                <div class="search_b_list">

                </div>
            </div>
            <div class="right-content-body">
                <div class="right-group-box">
                    <div class="head">
                      <div class="title">
                        投资机构<span class="small">筛选</span>
                      </div>

                    </div>
                    <div  class="right-group-box-body">

                        <div class="shgroup ">
                            <div class="title">筐：</div>
                            <div class="tiplist">
                                <ul class="" ro="0">
    <c:forEach items="${investment['lableBaskList']}" var="arrayListI">
    <li tip="筐"  cl="${arrayListI.sys_labelelement_code}">${arrayListI.sys_labelelement_name}</li>
    </c:forEach>

                                </ul>
                            </div>
                            <div class="more">
                            更多<span class="glyphicon glyphicon-chevron-down" aria-hidden="true"></span>
                            </div>
                        </div>
                        <div class="shgroup ">
                            <div class="title">行业：</div>
                            <div class="tiplist">
                                <ul class=""  ro="1">
    <c:forEach items="${investment['lableinduList']}" var="arrayListI">
    <li tip="行业" cl="${arrayListI.sys_labelelement_code}">${arrayListI.sys_labelelement_name}</li>
    </c:forEach>
                                </ul>
                            </div>
                            <div class="more">
                                更多<span class="glyphicon glyphicon-chevron-down" ></span>
                            </div>
                        </div>
                        <div class="groupmore">
                            <div class="shgroup ">
                                <div class="title">近期关注：</div>
                                <div class="tiplist">
                                    <ul class=""  ro="2">
    <c:forEach items="${investment['lableinduList']}" var="arrayListI">
    <li tip="关注" cl="${arrayListI.sys_labelelement_code}">${arrayListI.sys_labelelement_name}</li>
    </c:forEach>
                                    </ul>
                                </div>
                                <div class="more">
                                    更多<span class="glyphicon glyphicon-chevron-down" ></span>
                                </div>
                            </div>
                            <div class="shgroup ">
                                <div class="title">基金币种：</div>
                                <div class="tiplist">
                                    <ul class=""  ro="3">
    <c:forEach items="${investment['lableCurrencyList']}" var="arrayListI">
    <li tip="币种" BZ cl="${arrayListI.sys_labelelement_code}">${arrayListI.sys_labelelement_name}</li>
    </c:forEach>

                                    </ul>
                                </div>
                                <div class="more">
                                    更多<span class="glyphicon glyphicon-chevron-down" ></span>
                                </div>
                               </div>
                                <div class="shgroup ">
                                    <div class="title">基金规模：</div>
                                    <div class="tiplist">
                                        <ul class=""  ro="4">
    <c:forEach items="${investment['lableScaleList']}" var="arrayListI">
    <li tip="规模" GM cl="${arrayListI.sys_labelelement_code}">${arrayListI.sys_labelelement_name}</li>
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
    <ul class=""  ro="5">
    <c:forEach items="${investment['lableStageList']}" var="arrayListI">
    <li tip="阶段"  cl="${arrayListI.sys_labelelement_code}">${arrayListI.sys_labelelement_name}</li>
    </c:forEach>
                                    </ul>
                                </div>
                                <div class="more">
                                    更多<span class="glyphicon glyphicon-chevron-down" ></span>
                                </div>
                            </div>
                            <div class="shgroup ">
                                <div class="title">投资特征：</div>
                                <div class="tiplist">
                                    <ul class=""  ro="6">
    <c:forEach items="${investment['lableFeatureList']}" var="arrayListI">
    <li tip="特征"  cl="${arrayListI.sys_labelelement_code}">${arrayListI.sys_labelelement_name}</li>
    </c:forEach>

                                    </ul>
                                </div>
                                <div class="more">
                                    更多<span class="glyphicon glyphicon-chevron-down" ></span>
                                </div>
                            </div>
                            <!-- 2016-5-3 duwenjie add start -->
                            <div class="shgroup ">
                                <div class="title">投资类型：</div>
                                <div class="tiplist">
                                    <ul class=""  ro="9">
    <c:forEach items="${investment['lableTypeList']}" var="arrayListI">
    <li tip="类型"  cl="${arrayListI.sys_labelelement_code}">${arrayListI.sys_labelelement_name}</li>
    </c:forEach>

                                    </ul>
                                </div>
                                <div class="more">
                                    更多<span class="glyphicon glyphicon-chevron-down" ></span>
                                </div>
                            </div>
                            <!--2016-5-3 dwj add 投资类型 end -->
                            
    <!--2015-12-18 yl add start-->
    <div class="shgroup ">
    <div class="title">背景：</div>
    <div class="tiplist">
    <ul class=""  ro="7">
    <c:forEach items="${investment['lableBgroundList']}" var="arrayListI">
    <li tip="背景"  cl="${arrayListI.sys_labelelement_code}">${arrayListI.sys_labelelement_name}</li>
    </c:forEach>

    </ul>
    </div>
    <div class="more">
    更多<span class="glyphicon glyphicon-chevron-down" ></span>
    </div>
    </div>
    <!--2015-12-18 yl add end-->
    
    <!--2016-4-21 dwj start-->
    <div class="shgroup " style="display: none;">
    <div class="title">状态：</div>
    <div class="tiplist">
    <ul class=""  ro="8">
	    <li tip="状态"  cl="0">有效</li>
		<li tip="状态"  cl="1">无效</li>
    </ul>
    </div>
    <div class="more">
    更多<span class="glyphicon glyphicon-chevron-down" ></span>
    </div>
    </div>
    <!--2016-4-21 dwj add end-->
                            <div class="fitcompany-boder"></div>
                            <div class="shgroup fitcompany">

                                <div class="title">竞争公司：

                                </div>
                                <div class="tiplist">
                                    <span >
                                     <input class="company1" name="company" AUTOCOMPLETE="OFF"/>
                                       <div class="companytip">
                                           <ul>

                                           </ul>
                                       </div>
                                    </span>
                                    <span >
                                        <input class="company2" name="company" AUTOCOMPLETE="OFF"/>
                                         <div class="companytip">
                                             <ul>

                                             </ul>
                                         </div>
                                    </span>
                                    <span >
                                        <input class="company3" name="company" AUTOCOMPLETE="OFF"/>
                                         <div class="companytip">
                                             <ul>

                                             </ul>
                                         </div>
                                    </span>
                                </div>

                            </div>
                        </div>
                        </div>
                    <div class="closeshearch">
                            收起<span class="glyphicon glyphicon-chevron-up" ></span>
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
                    <button class="btn btn-default btn_search">筛 选</button>
                </div>

            </div>
        </div>
    </div>

    <div class="line1"></div>
    <div class="line2"></div>
    <div class="line3"></div>
</div>

</body>
<link rel="stylesheet" href="view/pc/common/css/organization_search.css">
<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/Template.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>
<script type="text/javascript" src="view/pc/common/js/organization_search.js"></script>
</html>