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
    <link rel="stylesheet" href="view/pc/common/css/select2.min.css">
    <link rel="stylesheet" href="view/pc/common/css/layer.css">
    <link rel="stylesheet" href="view/pc/common/css/datepicker.css">
    <link rel="stylesheet" href="view/pc/common/css/jqPaginator.css">
    <link rel="stylesheet" href="view/pc/common/css/company_add.css">
    


</head>
<body onResize=" config_function.autoline();">
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
                添加 > <span class="strong">公 司</span>
            </div>


            <div class="right-content-body">
                <div class="right-group-box">
                    <div class="head">
                        <div class="title">
                            公 司
                        </div>

                    </div>
                    <div  class="right-group-box-body">
                        <div class="company_info">
                            <div class="info_content">
                                <div class="info_content_title">
                                    简称:
                                </div>
                                <div class="info_content_txt">
                                    <input type="text" id="name"  maxlength="20"/>
                                </div>
    </div>
    <div class="info_content">
                                <div class="info_content_title">
                                    英文名称:
                                </div>
                                <div class="info_content_txt">
                                    <input type="text" id="ename"  maxlength="200"/>
                                </div>
    </div>
    <div class="info_content">
                                <div class="info_content_title">
                                    全称:
                                </div>
                                <div class="info_content_txt">
                                    <input type="text" id="fullname"  maxlength="200"/>
                                </div>
                            </div>
    <div class="info_content">
    <div class="info_content_title">
    <span class="tip-edit baskedit">筐
    <span class="caret"></span>:
    </span>
    </div>
    <div class="content_txt bask">

    </div>
    </div>
    <div class="info_content">
    <div class="info_content_title">
    <span class="tip-edit hangyeedit">行业
    <span class="caret"></span>:
    </span>
    </div>
    <div class="content_txt induc">

    </div>
    </div>
    <div class="info_content">
    <div class="info_content_title">
    <span class="tip-edit stageedit">阶段
    <span class="caret"></span>:
    </span>
    </div>
    <div class="content_txt stage">
    
    </div>
    </div>
    <div class="emm_info_content">
    <div class="info_content_title">联系人:</div>
    <div class="content_txt peoplel" style="width:86% ;position: relative;min-height: 25px;"">
    <ul class="people" style="min-height: 27px;width: 95%;">

    </ul>
    <div class="people_add_img">
    <button id="btn_people_add"
    class="btn btn_icon_add add_people"></button>
    </div>
    </div>
    </div>


                        </div>
                        <%--<div class="add_redord">--%>
                            <%--<div class="block_title">--%>
                                <%--联系人：--%>
                            <%--</div>--%>
                            <%--<div class="link_content">--%>
                                <%--<table>--%>
                                    <%--<thead>--%>
                                    <%--<tr>--%>
                                        <%--<th width="15%">姓名</th>--%>
                                        <%--<th width="22%">职位</th>--%>
                                        <%--<th width="18%">电话</th>--%>
                                        <%--<th width="22%">邮件</th>--%>
                                        <%--<th width="15%">微信</th>--%>
                                        <%--<th width="8%">删除</th>--%>
                                    <%--</tr>--%>
                                    <%--</thead>--%>
                                    <%--<tbody class="link_detail">--%>
                                    <%--<tr>--%>
                                        <%--<td>--%>
                                            <%--<input type="text" />--%>
                                        <%--</td>--%>
                                        <%--<td>--%>
                                            <%--<select id="select_job" class="select2 select_short">--%>
                                                <%--<option></option>--%>
                                                <%--<option>IT</option>--%>
                                                <%--<option>汽车</option>--%>
                                                <%--<option>房地产</option>--%>
                                            <%--</select>--%>
                                        <%--</td>--%>
                                        <%--<td>--%>
                                            <%--<input type="text" />--%>
                                        <%--</td>--%>
                                        <%--<td>--%>
                                            <%--<input type="text" />--%>
                                        <%--</td>--%>
                                        <%--<td>--%>
                                            <%--<input type="text" />--%>
                                        <%--</td>--%>
                                        <%--<td>--%>
                                            <%--<button class="btn btn_delete"></button>--%>
                                        <%--</td>--%>
                                    <%--</tr>--%>
                                    <%--</tbody>--%>
                                <%--</table>--%>
                                <%--<div class="footer_add">--%>
                                    <%--<button class="btn btn_icon_add add_link"></button>--%>
                                <%--</div>--%>

                            <%--</div>--%>
                        <%--</div>--%>
                        <div class="add_redord">
                            <div class="add_redord_txt">
                                <textarea class="txt_redord compnote" placeholder="添加备注..."  maxlength="100"></textarea>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="add_redord_btn">
                    <button class="btn btn-default creatComp">创建公司</button>
                    <button class="btn btn-default creatRz">融资计划</button>
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
    var inductList=${induList};
    var baskList=${baskList};
    var stageList=${stageList};
    </script>
<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
    <script type="text/javascript" src="view/pc/common/js/Template.js"></script>
    <script type="text/javascript" src="view/pc/common/js/common.js"></script>
    <script type="text/javascript" src="view/pc/common/js/select2.min.js"></script>
    <script type="text/javascript" src="view/pc/common/js/layer.js"></script>
    <script type="text/javascript" src="view/pc/common/js/company_add.js"></script>
    <script type="text/javascript" src="view/pc/common/js/jqPaginator.js"></script>
    <script type="text/javascript"
    src="view/pc/common/js/bootstrap-datepicker.js"></script>
</html>