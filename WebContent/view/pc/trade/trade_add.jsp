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
<meta http-equiv=”X-UA-Compatible” content=”IE=Edge,chrome=1″>
<link rel="stylesheet" href="view/pc/common/css/common.css">
<link rel="stylesheet" href="view/pc/common/css/font.css">
<link rel="stylesheet" href="view/pc/common/css/datepicker.css">
<link rel="stylesheet" href="view/pc/common/css/select2.min.css">
<link rel="stylesheet" href="view/pc/common/css/layer.css">
<link rel="stylesheet" href="view/pc/common/css/trade_add.css">

</head>
<body>
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
					添加 ><span class="strong">交易</span>
				</div>


				<div class="right-content-body">
					<div class="right-group-box">
						<div class="head">
							<div class="title" style="float:left">近期交易</div>
             <button class="btn btn-return btn-default" style="display:none;" id="returnbutton">返回</button>
						</div>
						<div class="right-group-box-body">
							<div class="trade_add_detial">
								<div class="info_content_title">
									<!-- trade_add_title-->
									日期：
								</div>
								<div class="select_div">
									<input type="text" class="span2" id="dp1"
										data-date-format="yyyy-mm-dd" readonly>
								</div>
							</div>
							<div class="trade_add_detial">
								<div class="info_content_title">被投公司：</div>
								<div class="select_div">
									<select id="select_com" class="select2 select_con">
										<option></option>
									</select>
                                    <div class="addcombox">
                                     <input type="text" class="span2 addcom-input" maxlength="20"/>
                                    </div>
    <div class="option"><img src="view/pc/common/icon/icon_add.png" class="option-img" title="添加公司"> </div>
								</div>


							</div>
							<div class="trade_add_detial">
								<div class="info_content_title tip-edit kuang">筐<span class="caret"></span>：</div>
								<div class="select_div select-kuang">

								</div>

							</div>
							<div class="trade_add_detial">

								<div class="info_content_title tip-edit hangye">
									行业<span class="caret"></span>：
								</div>
								<div class="select_div select-hangye">

								</div>

							</div>

							<div class="trade_add_detial">

								<div class="info_content_title tip-edit jieduan">阶段<span class="caret"></span>：</div>
								<div class="select_div select-jieduan">

								</div>
							</div>
							<div class="trade_add_detial">
								<div class="info_content_title">融资金额：</div>
								<div class="select_div">
									<input type="text" id="trademoney" class="span2" maxlength="20"/>
								</div>

							</div>
							<div class="trade_add_detial">

								<div class="info_content_title">公司估值：</div>
								<div class="select_div">
									<input type="text" id="tradecomnum" class="span2" maxlength="20"/>
									<select class="inputdef" id="tradecomnumtype" style="background:#fff;width:75px;">
                <option value="0">获投前</option>
                <option value="1">获投后</option>
               </select>
								</div>
							</div>

							<div class="trade_add">
								<div class="info_content_title">融资信息：</div>
								<div class="trade_add_choice tran_content_table">
    <table>
    <thead>
    <tr class="nohover">
    <th width="20%">投资机构</th>
    <th width="20%">投资人</th>
    <th width="20%">金额</th>
    <th width="10%">领投</th>
    <th width="10%">分期</th>
    <th width="10%">对赌</th>
    <th width="10%">操作</th>
    </tr>
    </thead>
    <tbody class="stage">

    </tbody>
    </table>
								<div class="trade_add_img">
									<button class="btn btn_icon_add"></button>
								</div>
							</div>

							<!--<div class="trade_add">-->
							<!--<div class="trade_add_ck">-->
							<!--<span><input type="checkbox" class="ck_input"/>对赌</span>-->
							<!--<span><input type="checkbox" class="ck_input"/>分次付款</span>-->
							<!--</div>-->
							<!--</div>-->
							<div class="add_redord">
								<div class="add_redord_txt">
									<textarea id="noteinfoid" class="txt_redord" placeholder="添加备注..." maxlength="100"></textarea>
								</div>

							</div>
						</div>
					</div>
					<div class="add_redord_btn">
						<button class="btn btn-default creattrade">创建交易</button>
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
    var baskList=${baskList};//关注筐集合
    var induList=${induList};//行业集合
    var stageList=${stageList};//阶段集合
    var baseinvestmentinfo=${baseinvestmentinfo};//机构
    var viewCompInfo=${viewCompInfo};//公司
    var backherf="${backherf}";//返回路径
    var viewInvestorInfo=${viewInvestorInfo}//投资人
    </script>
<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/Template.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>
<script type="text/javascript" src="view/pc/common/js/select2.min.js"></script>
<script type="text/javascript"
	src="view/pc/common/js/bootstrap-datepicker.js"></script>
    <script type="text/javascript" src="view/pc/common/js/layer.js"></script>
<script type="text/javascript" src="view/pc/common/js/trade_add.js"></script>
</html>