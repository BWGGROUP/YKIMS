<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
    <link rel="stylesheet" href="view/pc/common/css/animate.css">
    <link rel="stylesheet" href="view/pc/common/css/font.css">
    <link rel="stylesheet" href="view/pc/common/css/datepicker.css">
    <link rel="stylesheet" href="view/pc/common/css/sweetalert.css">
	<link rel="stylesheet" href="view/pc/common/css/select2.min.css">
	<link rel="stylesheet" href="view/pc/common/css/layer.css">
    <link rel="stylesheet" href="view/pc/common/css/company_add_organization.css">
    

</head>
<body><!-- onResize=" config_function.autoline();" -->
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
                添加 > <span class="strong">投资机构</span>
            </div>


            <div class="right-content-body">
                <div class="right-group-box">
                    <div class="head">
                        <div class="title">
                            投资机构
                        </div>
                    </div>
                    <div  class="right-group-box-body">
                        <div class="company_info">
<!--                            <div class="info_content">-->
<!--                                <div class="info_content_title">-->
<!--                                    全称:-->
<!--                                </div>-->
<!--                                <div class="info_content_txt">-->
<!--                                    <input id="orgFName" class="txt_input" type="text"/>-->
<!--                                    <span class="must_icon">*</span>-->
<!--                                </div>-->
<!--                            </div>-->
							<div class="info_content">
                                <div class="tip-edit info_content_title orgNameClick">
                                    机构名称<span class="caret"></span>:
                                </div>
                                <div class="info_content_company orglist">
                                </div>
                            </div>
                            <div class="info_content">
                                <div class="tip-edit info_content_title baskClick">
                                    筐<span class="caret"></span>:
                                </div>
                                <div class="info_content_company basklist">
<!--                                    <select id="select_bask" multiple="multiple" class="select2 select_people">-->
<!--                                    </select>-->
                                </div>
                            </div>
	                        <div class="info_content">
                                <div class="tip-edit info_content_title induClick">
                                    行业<span class="caret"></span>:
                                </div>
                                <div class="info_content_company indulist">
                                </div>
                            </div>
                            <div class="info_content">
                                <div class="tip-edit info_content_title payattClick">
                                    近期关注<span class="caret"></span>:
                                </div>
                                <div class="info_content_company payattlist">
                                </div>
                            </div>
                            <div class="info_content">
                                <div class="tip-edit info_content_title bggroundClick">
                                    背景<span class="caret"></span>:
                                </div>
                                <div class="info_content_company bggroundlist">
                                </div>
                            </div>
	                        <div class="info_content">
                                <div class="tip-edit info_content_title stageClick">
                                    投资阶段<span class="caret"></span>:
                                </div>
                                <div class="info_content_company stagelist">
                                </div>
                            </div>
							<div class="info_content">
                                <div class="tip-edit info_content_title typeClick">
                                    类型<span class="caret"></span>:
                                </div>
                                <div class="info_content_company typelist">
                                </div>
                            </div>
                            <div class="info_content">
                                <div class="tip-edit info_content_title featClick">
                                    投资特征<span class="caret"></span>:
                                </div>
                                <div class="info_content_company featlist">
<!--                                    <div class="">-->
<!--                                        <select id="select_feat" multiple="multiple" class="select2 select_tz">-->
<!--                                        </select>-->
<!--                                    </div>-->
                                </div>
                            </div>

                            <div class="add_redord"><!--add_redord -->
                                <div class="info_content_title">
                                    基金:
                                </div>
                                <div class="info_content_table">
                                    <table id="fundTable" class="fundTable">
                                        <thead class="tHead">
                                        <tr>
                                            <th width="31%">名称</th>
                                            <th  width="17%">币种</th>
                                            <th  width="29%">金额</th>
                                            <th  width="16%">状态</th>
                                            <th width="7%">操作</th>
                                        </tr>
                                        </thead>
                                        <tbody class="fund_table">
                                        </tbody>
                                    </table>
                                </div>
                                <div class="trade_add_img">
                                    <button id="btn_detail_add" class="btn btn_icon_add "></button>
                                </div>
                            </div>
							
							<div class="add_redord">
                                 <div class="info_content_title">
                                    易凯联系人:
                                </div>
                                <div class="info_content_company yklinkContent">
                                </div>
								<div class="trade_add_img">
                                    <button id="btn_yklink_add" class="btn btn_icon_add "></button>
                                </div>
                            </div>
                            
                            <div class="add_redord">
                                <div class="add_redord_txt">
                                    <textarea id="orgNote" class="txt_redord" maxlength="100" placeholder="添加备注..."></textarea>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

                <div class="add_redord_btn">
                    <button class="btn btn-default orgSubmit">创建机构</button>
                    <button class="btn btn-default orgSubmitToTrade">创建并添加交易</button>
                    <button class="btn btn-default orgSubmitToInvestor">创建并添加投资人</button>
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
	
	</script>
	<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
    <script type="text/javascript" src="view/pc/common/js/Template.js"></script>
    <script type="text/javascript" src="view/pc/common/js/common.js"></script>
    <script type="text/javascript" src="view/pc/common/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="view/pc/common/js/layer.js"></script>
	<script type="text/javascript" src="view/pc/common/js/select2.min.js"></script>
    <script type="text/javascript" src="view/pc/common/js/sweetalert.min.js"></script>
    <script type="text/javascript" src="view/pc/common/js/company_add_organization.js"></script>

</html>