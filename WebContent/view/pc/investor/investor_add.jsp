<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort()
+ path + "/";
%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    <link rel="stylesheet" href="view/pc/common/css/layer.css">
    <link rel="stylesheet" href="view/pc/common/css/select2.min.css">
    <link rel="stylesheet" href="view/pc/common/css/investor_add.css">
</head>
<body >
<jsp:include page="../common/head.jsp" />
<div id="main-content" class="content">
    <div class="main-content-box main-content-box-left">
        <div class="left-menu-box">
            <jsp:include page="../common/left_menu.jsp" />
        </div>
    </div>
    <div class="main-content-box main-content-box-right">
        <div class="right-content">
            <div class="head">
                <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                添加 ><span class="strong">投资人</span>
            </div>


            <div class="right-content-body">
                <div class="right-group-box">
                    <div class="head">
                        <div class="title">
                            投资人
                        </div>
						<div class="goback"><button class="btn btn-default smart-btn">返回</button></div>
                    </div>
                    <div  class="right-group-box-body">
                        <div class="investor_info">
                            <div class="investor_info_content">

                                <div class="info_content_title">
                                    姓名：
                                </div>
                                <div class="content_txt">
                                    <input type="text" name="username" class="fn-input" AUTOCOMPLETE="OFF" maxlength="20"/>
                                </div>
                            </div>
    <div class="investor_info_content">

    <div class="info_content_title">
    所属机构：
    </div>
    <div class="content_txt" style="width:88% ;position: relative;min-height: 25px;">
  <ul class="orul">
    </li>
    </ul>
    <div class="trade_add_img">
    <button id="btn_detail_add" class="btn btn_icon_add add_company"></button>
    </div>
    </div>
    </div>
                            <div class="investor_info_content">
                                <div class="info_content_title tip-edit hangye">
                                   行业<span class="caret"></span>：
                                </div>
                                <div class="content_txt">

                                </div>

                            </div>
    <div class="investor_info_content">

    <div class="info_content_title tip-edit guanzhu">
    近期关注<span class="caret"></span>：
    </div>
    <div class="content_txt" >

    </div>
    </div>
                            <div class="investor_info_content">
                                <div class="info_content_title">
                                    电话：
                                </div>
                                <div class="content_txt">
                                    <input type="text" name="phone" class="fn-input" AUTOCOMPLETE="OFF" maxlength="11"/>
                                </div>

                            </div>
    <div class="investor_info_content">

    <div class="info_content_title">
    邮箱：
    </div>
    <div class="content_txt">
    <input type="text" name="email" class="fn-input" AUTOCOMPLETE="OFF" maxlength="200"/>
    </div>
    </div>
                            <div class="investor_info_content">
                                <div class="info_content_title">
                                    微信：
                                </div>
                                <div class="content_txt">
                                    <input type="text" name="weichat" class="fn-input" AUTOCOMPLETE="OFF" maxlength="200"/>
                                </div>
                            </div>
                        </div>
                        <div class="add_redord">
                            <div class="add_redord_txt">
                                <textarea class="txt_redord compnote" placeholder="添加备注..." maxlength="100"></textarea>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="add_redord_btn">
                    <button class="btn btn-default creatInvestor">创建投资人</button>
                    <button class="btn btn-default creatTrad">添加近期交易</button>
                </div>
            </div>

        </div>
    </div>
</div>

<div class="line1"></div>
<div class="line2"></div>
<div class="line3"></div>

</body>
    <script>
    var investmentCode='${investmentCode}';
    var investmentName='${investmentname}';
    var induList=${induList};
    var backherf='${backherf}';
    </script>
<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/Template.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>
<script type="text/javascript" src="view/pc/common/js/layer.js"></script>
<script type="text/javascript" src="view/pc/common/js/select2.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/investor_add.js"></script>
</html>