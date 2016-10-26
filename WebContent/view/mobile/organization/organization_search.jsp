<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
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
    <meta content="telephone=no" name="format-detection" />
    <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
    <link rel="stylesheet" href="view/mobile/css/font.css">
    <link rel="stylesheet" href="view/mobile/css/common.css">
    <link rel="stylesheet" href="view/mobile/css/organization_search.css">
    
</head>
<body>
<div class="main-content">
    <div class="header">
        <div class="header-left left-menu-btn"></div>
        <div class="header-center"><div class="logo"></div></div>
        <div class="header-right"></div>
    </div>
    <div class="content-body">
        <!--<div class="head-option">-->
            <!--<div class="search-option active"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></div>-->
            <!--<div class="add-option"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></div>-->
        <!--</div>-->
        <div class="box">
            <div class="box-title">
                投资机构<span class="small">筛选</span>
            </div>
            <div class="box-body">
                <div class="display-table">
                    <div class="shgroup ">
                        <div class="title no-border" >筐:</div>
                        <div class="tiplist no-border">
                            <ul class="" ro="0">
                            <c:forEach items="${investment['lableBaskList']}" var="arrayListI">
																				<li tip="筐"  cl="${arrayListI.sys_labelelement_code}">${arrayListI.sys_labelelement_name}</li>
																		 </c:forEach>
                            </ul>
                            <div class="more"><span class="glyphicon glyphicon-chevron-down"></span></div>
                        </div>
                    </div>

                    <div class="shgroup ">
                        <div class="title">关注行业：</div>
                        <div class="tiplist">
                            <ul class=""  ro="1">
                            <c:forEach items="${investment['lableinduList']}" var="arrayListI">
																				<li tip="行业" cl="${arrayListI.sys_labelelement_code}">${arrayListI.sys_labelelement_name}</li>
																		 </c:forEach>
                            </ul>
                            <div class="more">
                                <span class="glyphicon glyphicon-chevron-down" ></span>
                            </div>
                        </div>

                    </div>
                    <div class="shgroup ">
                        <div class="title">近期关注：</div>
                        <div class="tiplist">
                            <ul class=""  ro="2">
                            <c:forEach items="${investment['lableinduList']}" var="arrayListI">
																				<li tip="关注" cl="${arrayListI.sys_labelelement_code}">${arrayListI.sys_labelelement_name}</li>
																		 </c:forEach>
                            </ul>
                            <div class="more">
                                <span class="glyphicon glyphicon-chevron-down" ></span>
                            </div>
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
                            <div class="more">
                                <span class="glyphicon glyphicon-chevron-down" ></span>
                            </div>
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
                            <div class="more">
                                <span class="glyphicon glyphicon-chevron-down" ></span>
                            </div>
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
                            <div class="more">
                                <span class="glyphicon glyphicon-chevron-down" ></span>
                            </div>
                        </div>

                    </div>
                    <div class="shgroup ">
                        <div class="title">投资特征：</div>
                        <div class="tiplist">
                            <ul class=""  ro="6">
                             <c:forEach items="${investment['lableFeatureList']}" var="arrayListI">
																				<li tip="特征"  cl="${arrayListI.sys_labelelement_code}">${arrayListI.sys_labelelement_name}</li>
																		 </c:forEach>
                                <div class="more">
                                    <span class="glyphicon glyphicon-chevron-down" ></span>
                                </div>
                            </ul>
                        </div>

                    </div>
                    <div class="shgroup ">
                        <div class="title">投资类型：</div>
                        <div class="tiplist">
                            <ul class=""  ro="9">
                             	<c:forEach items="${investment['lableTypeList']}" var="arrayListI">
									<li tip="类型"  cl="${arrayListI.sys_labelelement_code}">${arrayListI.sys_labelelement_name}</li>
								</c:forEach>
                                <div class="more">
                                    <span class="glyphicon glyphicon-chevron-down" ></span>
                                </div>
                            </ul>
                        </div>

                    </div>
                    <div class="shgroup ">
                        <div class="title">背景：</div>
                        <div class="tiplist">
                            <ul class=""  ro="7">
                             <c:forEach items="${investment['lableBgroundList']}" var="arrayListI">
																				<li tip="背景"  cl="${arrayListI.sys_labelelement_code}">${arrayListI.sys_labelelement_name}</li>
																		 </c:forEach>
                                <div class="more">
                                    <span class="glyphicon glyphicon-chevron-down" ></span>
                                </div>
                            </ul>
                        </div>

                    </div>
                     <div class="shgroup " style="display: none;">
                        <div class="title">状态：</div>
                        <div class="tiplist">
                            <ul class=""  ro="8">
								<li tip="状态"  cl="0">有效</li>
								<li tip="状态"  cl="1">无效</li>
                                <div class="more">
                                    <span class="glyphicon glyphicon-chevron-down" ></span>
                                </div>
                            </ul>
                        </div>

                    </div>
                    <div class="shgroup fitcompany">

                        <div class="title">竞争公司：

                        </div>
                        <div class="tiplist">
                                    <span >
                                     <input name='name' class="company1" value=""/>
                                       <div class="companytip">
                                           <ul>

                                           </ul>
                                       </div>
                                    </span>
                                    <span >
                                        <input name='name' class="company2" value=""/>
                                         <div class="companytip">
                                             <ul>

                                             </ul>
                                         </div>
                                    </span>
                                    <span >
                                        <input name='name' class="company3" value=""/>
                                         <div class="companytip">
                                             <ul>

                                             </ul>
                                         </div>
                                    </span>
                        </div>

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
           <button class="btn btn-default search-btn">搜 索</button>
        </div>
    </div>
</div>
<div class="left-menu">
    <!-- <div class="user-head">
        <img src="" onerror="javascript:this.src='images/def_head.png'">

    </div>
    <div class="user-name">
        前缘
    </div> -->

</div>
<script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
<script type="text/javascript" src="view/mobile/js/Template.js"></script>
<script type="text/javascript" src="view/mobile/js/common.js"></script>
<script type="text/javascript" src="view/mobile/js/organization_search.js"></script>
</body>
</html>
