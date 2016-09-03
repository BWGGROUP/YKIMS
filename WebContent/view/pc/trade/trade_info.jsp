<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt'%>
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
    <meta http-equiv=”X-UA-Compatible” content=”IE=Edge,chrome=1″ >
    <link rel="stylesheet" href="view/pc/common/css/common.css">
    <link rel="stylesheet" href="view/pc/common/css/animate.css">
	<link rel="stylesheet" href="view/pc/common/css/select2.min.css">
	<link rel="stylesheet" href="view/pc/common/css/layer.css">
	<link rel="stylesheet" href="view/pc/common/css/jqPaginator.css">
    <link rel="stylesheet" href="view/pc/common/css/font.css">
    <link rel="stylesheet" href="view/pc/common/css/datepicker.css">
    <link rel="stylesheet" href="view/pc/common/css/trade_info.css">

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
                搜索 > 近期交易 > <span class="strong">交易详情</span>
            </div>
            <div class="right-content-body">
                <div class="right-group-box">
                    <div class="head">
                        <div class="title">
                            交易详情

                        </div>
    <div class="goback"><button class="btn btn-default smart-btn">返回</button></div>
                    </div>
                    <div  class="right-group-box-body">
                        <div class="shgroup ">
                            <div class="title">被投资公司:</div>
                            <div class="tiplist">
                                <ul class="companyClick" >
                                    <li>
                                        <span id="company" class="canEdit color_blue"></span>
<!--                                        <div class="txtEdit lable_hidden">-->
<!--                                            <input class="txtInfo"/>-->
<!--                                            <button class="txtSave" >保存</button>-->
<!--                                        </div>-->
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="shgroup tradeDateClick">
                            <div class="title">时间:</div>
                            <div class="tiplist ">
                                <ul >
                                    <li id="">
                                    	<span id="tradeDate" class="canEdit color_blue"></span>
                                        <div class="txtEdit lable_hidden">
                                            <input id="createDate" readOnly="readonly" data-date-format="yyyy-mm-dd" class="txtInfo tradeDate"/>
                                            <button class="txtSave tradeDateSaveClick" >保存</button>
                                        </div>
                                    </li>
                                </ul>
                            </div>

                        </div>
                        <div class="shgroup">
                            <div class="title">
                                <span class="tip-edit baskClick">
                                	关注筐<span class="caret"></span>:
<!--                                    <span class="glyphicon glyphicon-menu-down" ></span>:-->
                                </span>
                            </div>
                            <div class="tiplist">
                                <ul id="ul-bask" class="ul-bask" >
<!--                                    <li data-i="0">O2O</li>-->
                                </ul>
                            </div>
                            <div class="more display-none">
                            	更多<span class="glyphicon glyphicon-chevron-down">
                            </span></div>
                        </div>
                        <div class="shgroup">
                            <div class="title">
                                <span class="tip-edit induClick">
                                	行业<span class="caret"></span>:
                                </span>
                            </div>
                            <div class="tiplist">
                                <ul id="ul-indu" class="ul-indu" >
<!--                                    <li data-i="0">汽车</li>-->
                                </ul>
                            </div>
                            <div class="more display-none">
                            	更多<span class="glyphicon glyphicon-chevron-down">
                            </span></div>
                        </div>
                        <div class="shgroup ">
                            <div class="title">
                                <span class="tip-edit stageClick">
                                	阶段<span class="caret"></span>:
                                </span>
                            </div>
                            <div class="tiplist">
                                <ul id="ul-stage" class="" >
<!--                                    <li data-i="0">B轮</li>-->
                                </ul>
                            </div>
                        </div>
                        <div class="shgroup heightauto">
                            <div class="title">融资金额:</div>
                            <div class="tiplist">
                                <ul class="tradeMoneyClick" >
                                    <li data-i="0">
                                        <span id="tradeMoney" class="canEdit color_blue"></span>
                                        <div class="txtEdit lable_hidden">
                                            <input id="createTradeMoney" maxlength="100" class="txtInfo"/>
                                            <button class="txtSave tradeMoneySaveClick" >保存</button>
                                        </div>
                                    </li>
<!--                                    <li class="money_unit">-->
<!--                                        CNY-->
<!--                                    </li>-->

                                </ul>
                            </div>
                        </div>
                        <div class="shgroup heightauto">
                            <div class="title">公司估值:</div>
                            <div class="tiplist">
                                <ul class="" >
                                    <li data-i="0">
                                        <span id="companyMoney" class="canEdit color_blue"></span>
                                        <div class="txtEdit lable_hidden">
                                            <input id="createCompanyMoney" class="txtInfo" maxlength="100"/>
                                            <button class="txtSave companyMoneySaveClick" >保存</button>
                                        </div>
                                    </li>
                                    <li class="money_unit">
                                        <span id="companyMoneyType" class="canEdit color_blue"></span>
                                        <div class="txtEdit lable_hidden">
											<select id="createCompanyMoneyType" class="txtInfo">
												<option value="0">获投前</option>
												<option value="1">获投后</option>
											</select>
                                            <button class="txtSave selectMoneyType comMonTypeSaveClick" >保存</button>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>

                        <div class="trade_detial">
                            <div class="trade_detial_title">
                                融资信息:
                            </div>
                            <div class="trade_detial_list">
                            	<table>
                                <thead class="Thead">
                                    <th width="30%">投资机构</th>
                                    <th width="20%">投资人</th>
                                    <th width="14%">领投</th>
                                    <th width="30%">金额</th>
                                    <th width="6%">操作</th>
                                </thead>
                                <tbody class="tradeBody">
                                    
                                </tbody>
                            	</table>
                            	<div class="body_div_btn">
                                        <button class="btn btn_icon_add"></button>
                                </div>
                                <div class="tabMore">
											<span class="tradeMoreClick">更多</span>
									</div>
                            </div>
                        </div>
<!--                        <div class="trade_detial">-->
<!--                            <div class="trade_detial_ck">-->
<!--                                <span>对赌<input type="checkbox" class="ck_input"/></span>-->
<!--                                <span>分次付款<input type="checkbox" class="ck_input"/></span>-->
<!--                            </div>-->
<!--                        </div>-->
                        <div class="trade_detial">
                            <div class="trade_detial_title">
                                备注:
                            </div>
                            <div class="trade_detial_list">
                            	<table>
                                <thead class="Thead">
                                <th width="17%">时间</th>
                                <th width="17%">记录人</th>
                                <th width="60%">内容</th>
                                <th width="6%">操作</th>
                                </thead>
                                <tbody class="noteBody">
                                
                                </tbody>
                            </table>
                            <div class="closeshearch" style="display: block;">
                            	</div>
                            </div>
                            
                            <textarea id="noteContent" class="trade_detial_txt" maxlength="100" placeholder="添加备注..."></textarea>
                        </div>
                        <div class="btn_div">
                            <button class="btn btn-default-save submitNote" >保存备注</button>
                        </div>
                    </div>
                </div>
                <div class="right-content-footer">
                    <button class="btn btn-default delTradeClick" >删除交易</button>
                    <button class="btn btn-default updlogClick" >查看更新记录</button>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="line1"></div>
<div class="line2"></div>
<div class="line3"></div>
</body>
	<script type="text/javascript">
		var tradeInfo=${tradeInfo};//交易详情
    var backherf="${backherf}";
	</script>
	<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
    <script type="text/javascript" src="view/pc/common/js/Template.js"></script>
    <script type="text/javascript" src="view/pc/common/js/common.js"></script>
    <script type="text/javascript" src="view/pc/common/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="view/pc/common/js/select2.min.js"></script>
    <script type="text/javascript" src="view/pc/common/js/layer.js"></script>
    <script type="text/javascript" src="view/pc/common/js/jqPaginator.js"></script>
    <script type="text/javascript" src="view/pc/common/js/trade_info.js"></script>
</html>