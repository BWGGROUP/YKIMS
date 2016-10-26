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
    <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
    <link rel="stylesheet" href="view/mobile/css/font.css">
    <link rel="stylesheet" href="view/mobile/css/common.css">
	<link rel="stylesheet" href="view/mobile/css/dropload.css">
    <link rel="stylesheet" href="view/mobile/css/page.css">
    <link rel="stylesheet" href="view/mobile/css/jqpagination.css">
    <link rel="stylesheet" href="view/mobile/css/trade_info.css">
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
                交易详情
    		<div class="goback"><button class="returnBtn">返回</button></div>
            </div>
            <div class="box-body">
                <div class="display-table">
                    <div  class="shgroup ">
                        <div class="title no-border" >被投公司:</div>
                        <div class="tiplist no-border">
                            <ul class="companyClick" ro="0">
                            	<li id="company" data-i="0"></li>
                            </ul>
                        </div>
                    </div>
                    <div  class="shgroup ">
                        <div class="title no-border" >时间:</div>
                        <div class="tiplist no-border">
                            <ul class="tradeDateClick" ro="0">
                                <li id="tradeDate" data-i="1"></li>
                            </ul>
                        </div>
                    </div>
                    <div  class="shgroup listtip ">
                        <div class="title no-border " >关注筐:</div>
                        <div class="tiplist no-border">
                            <ul id="ul-bask" class="baskClick" ro="0">

                            </ul>
                        </div>

                    </div>
                    <div class="shgroup listtip ">
                        <div class="title no-border " >行业:</div>
                        <div class="tiplist no-border">
                            <ul id="ul-indu" class="induClick"  ro="1">
                            </ul>
                        </div>

                    </div>
                    <div class="shgroup listtip">
                        <div class="title no-border ">阶段:</div>
                        <div class="tiplist no-border">
                            <ul  class="stageClick"  ro="5">
                                <li id="ul-stage" tip="类型" cl="0"></li>
                            </ul>

                        </div>

                    </div>
                    <div class="shgroup ">
                        <div class="title no-border ">融资金额:</div>
                        <div class="tiplist no-border">
                            <ul class="tradeMoneyClick"  ro="5">
                                <li id="tradeMoney" tip="类型" cl="0"></li>
                            </ul>

                        </div>

                    </div>
                    <div class="shgroup ">
                        <div class="title no-border rongzie">公司估值:</div>
                        <div class="tiplist no-border">
                            <ul class="companyMoneyClick"  ro="5">
                                <li id="companyMoney" tip="类型" cl="0"></li>
                            </ul>

                        </div>

                    </div>
                    <div class="shgroup ">
                    	<div class="morebtn tradeMore">
                        	<span class="glyphicon glyphicon-chevron-right blue">
                        </div>
                        <div class="title no-border motitle">融资信息:</div>
                        <div class="tablepadding">
                            <table id="tradeInfo">
                                <thead>
                                <tr>
                                    <th width="30%">
                                        投资机构
                                    </th>
                                    <th width="20%">
                                        投资人
                                    </th>

                                    <th width="30%">
                                        金额
                                    </th>
                                    <th width="20%">
                                        操作
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
							<div class="addbtn"></div>
                        </div>
                    </div>
                    <div class="shgroup  " style="margin-top: 20px">
                        <div class="title no-border motitle" >备注:</div>
                        <div class="meeting-box">
                            <table id="noteList">
                                <tbody>
                                </tbody>
                            </table>
                            <div class="closeshearch" style="display: block;"></div>
                        </div>
                    </div>
                    <div class="shgroup  " style="margin-top: 10px">
                        <div class="title no-border motitle" >添加备注:</div>
                        <div class="meeting-box">
                            <textarea id="noteContent" maxlength="100" placeholder="输入内容..."></textarea>
                        </div>
                    </div>
                    <div class="notecompet">
                        <button class="btn btn-default submitNote">提交备注</button>
                        <br/>
                        <button class="btn btn-default delTradeClick">删除交易</button>
                        <button class="btn btn-default updlogBtn">查看更新记录</button>
                    </div>
                </div>
                </div>
                
            </div>
    </div>
    
    
    
    
    <!-- 融资信息更多子页-->
	<div id="tradePage" class="pageBack">
    	<div class="page_body">
		<div class="box child">
	            <div class="box-title">
	             融资信息
	             <div class="box-return">
	              <button class="returnBtn tradeReturn">返回</button>
	             </div>
	            </div>
	            <div class="box-body">
	            	<table id="pageTradeBody">
	            		  <thead>
	            		  		<tr>
	            		  			<th>投资机构</th>
	            		  			<th>投资人</th>
	            		  			<th>金额</th>
	            		  			<th>操作</th>
	            		  		</tr>
	            		  </thead>
	                      <tbody>
	                 	  </tbody>
	                 </table>
	            </div>
	            <div class="pageaction">
		            <div class="gigantic pagination tradePageaction">
		                <a href="#" class="previous" data-action="previous">&lsaquo;</a>
		                <input type="tel" readonly="readonly" />
		                <a href="#" class="next" data-action="next">&rsaquo;</a>
		                <lable class="totalSize tradeLable" id="totalSize"></lable>
		            </div>
		        </div>
	    </div>
	    </div>
	</div>
    
    <!-- 更新记录更多子页-->
	<div id="updlogPage" class="pageBack">
    	<div class="page_body">
		<div class="box child">
	            <div class="box-title">
	             更新记录
	             <div class="box-return">
	              <button class="returnBtn updlogReturn">返回</button>
	             </div>
	            </div>
	            <div class="box-body">
	            	<table id="pageUpdlogBody">
	            		  <thead>
	            		  		<tr>
	            		  			<th>时间</th>
	            		  			<th>更新人</th>
	            		  			<th>操作</th>
	            		  			<th>内容</th>
	            		  		</tr>
	            		  </thead>
	                      <tbody>
	                 	  </tbody>
	                 </table>
	            </div>
	            <div class="pageaction">
		            <div class="gigantic pagination updlogPageaction">
		                <a href="#" class="previous" data-action="previous">&lsaquo;</a>
		                <input type="tel" readonly="readonly" />
		                <a href="#" class="next" data-action="next">&rsaquo;</a>
		                <lable class="totalSize updlogLable" id="totalSize"></lable>
		            </div>
		        </div>
	    </div>
	    </div>
	</div>
    
    
    
    
    
</div>



</body>

<script>
 	var tradeInfo=${tradeInfo};//交易详情
	var backherf="${backherf}";
</script>
<script type="text/javascript" src="view/mobile/js/Template.js"></script>
<script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
<script type="text/javascript" src="view/mobile/js/common.js"></script>
<script type="text/javascript" src="view/mobile/js/dropload.js"></script>
<script type="text/javascript" src="view/mobile/js/page.js"></script>
<script type="text/javascript" src="view/mobile/js/jquery.jqpagination.js"></script>
<script type="text/javascript" src="view/mobile/js/trade_info.js"></script>
</html>
