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
    <link rel="stylesheet" href="view/mobile/css/page.css">
    <link rel="stylesheet" href="view/mobile/css/jqpagination.css">
    <link rel="stylesheet" href="view/mobile/css/organization_info.css">
    
</head>
<body>
<div class="main-content">
    <div class="header">
        <div class="header-left left-menu-btn"></div>
        <div class="header-center"><div class="logo"></div></div>
        <div class="header-right"></div>
    </div>
    <div class="content-body" >
        <!--<div class="head-option">-->
            <!--<div class="search-option active"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></div>-->
            <!--<div class="add-option"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></div>-->
        <!--</div>-->

        <div class="box"  id="xq">
        	<div class="goback">
	              <button class="returnBtn ">返回</button>
	        </div>
            <div id="box-title" class="box-title">
            
            </div>
            
            <div class="box-body">
                <div class="display-table">
                    <div id="xq" class="shgroup tipedit orgDask">
                        <div class="title no-border" >筐:</div>
                        <div class="tiplist  no-border">
                            <ul id="ul-bask" class="" ro="0">
                            </ul>
                            <div class="more"><span class="glyphicon glyphicon-chevron-down"></span></div>
                        </div>
                        
                    </div>

                    <div class="shgroup tipedit orgIndu">
                        <div class="title" >行业:</div>
                        <div class="tiplist">
                            <ul id="ul-indu" class=""  ro="1">
                            </ul>
                        </div>
                        <div class="more"><span class="glyphicon glyphicon-chevron-down"></span></div>
                    </div>
                    <div class="shgroup orgPayatt">
                        <div class="title">近期关注:</div>
                        <div class="tiplist">
                            <ul id="ul-payatt" class=""  ro="2">
                            </ul>
                        </div>
                        <div class="more"><span class="glyphicon glyphicon-chevron-down"></span></div>
                    </div>
                    <div class="shgroup orgBgground">
                        <div class="title">背景:</div>
                        <div class="tiplist">
                            <ul id="ul-bgground" class=""  ro="3">
                            </ul>
                        </div>
                        <div class="more"><span class="glyphicon glyphicon-chevron-down"></span></div>
                    </div>
                    <div class="shgroup tipedit orgStage">
                        <div class="title">投资阶段:</div>
                        <div class="tiplist">
                            <ul id="ul-stage" class=""  ro="5">
                            </ul>
                        </div>
                        <div class="more"><span class="glyphicon glyphicon-chevron-down"></span></div>
                        <!--<div class="edit-btn">-->
                        <!--</div>-->
                    </div>
                    <div class="shgroup tipedit orgType">
                        <div class="title">类型:</div>
                        <div class="tiplist">
                            <ul id="ul-type" class=""  ro="7">
                            </ul>
                        </div>
                        <div class="more"><span class="glyphicon glyphicon-chevron-down"></span></div>
                    </div>
                    <div class="shgroup tipedit orgFeat">
                        <div class="title">投资特征:</div>
                        <div class="tiplist">
                            <ul id="ul-feat" class=""  ro="6">
                            </ul>
                        </div>
                        <div class="more"><span class="glyphicon glyphicon-chevron-down"></span></div>
                    </div>
                    <div id="tzr" class="shgroup back touziren heightauto" >
                        <div class="title">投资人:</div>
                        <div class="tiplist">
                            <ul class="list" id="investorUl" ro="6">
                            </ul>
                        </div>
						<div class="morebtn ">
							<span class="glyphicon glyphicon-chevron-right invstorMore blue">
						</div>
						<div class="addbtnInvestor"></div>
                    </div>

                    <div id="jz" class="shgroup jijintable" style="padding-top: 10px">
                        <div class="morebtn fundMore">
                        	<span class="glyphicon glyphicon-chevron-right blue">
                        </div>
                        <div class="title">基金:</div>
                        <div class="tablepadding ">
                            <table id="fundTable">
                                <thead>
                                <tr>
                                    <th width="30%">
                                        名称
                                    </th>
                                    <th width="20%">
                                        币种
                                    </th>
                                    <th width="30%">
                                        金额
                                    </th>
                                    <th width="20%">
                                        状态
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                            <div class="fundDiv">
                            	<div class="addbtnfund"></div>
                            </div>
                        </div>

                    </div>
                    <div id="jy" class="shgroup jiaoyi" >
                    	<div class="morebtn tradeMore">
							<span class="glyphicon glyphicon-chevron-right blue">
						</div>
                        <div class="title">近期交易:</div>
                        <div class="tablepadding">
                            <table id="tradeTable">
                            	<thead>
                            		<tr>
                            			<th width="17%">时间</th>
                            			<th width="30%">公司</th>
                            			<th width="20%">阶段</th>
                            			<th width="15%">金额</th>
                            			<th width="18%">操作</th>
                            		</tr>
                            	</thead>
                                <tbody>
                                </tbody>
                            </table>
                            <div class="fundDiv">
                            	<div class="addbtnTrade"></div>
                            </div>
                        </div>

                    </div>
                    <div id="yklx" class="shgroup yikairen" style="">
                        <div class="addbtn"></div>
                        <div class="title">易凯联系人:</div>
                        <div class="tiplist-yk">
                            <ul id="YKLinkMan" ro="6"><!-- class="list" -->
                            
                            </ul>
                        </div>

                    </div>
                    <div class="shgroup " >
                        <div class="title" >备注:</div>
                        <div class="meeting-box">
                            <table id="noteList">
                                <tbody>
                                </tbody>
                            </table>
                            <div class="closeshearch" style="display: block;">
                            </div>
                        </div>
                    </div>
                    <div class="shgroup  " style="margin-top: 10px">
                        <div class="title " >添加备注:</div>
                        <div class="meeting-box">
                            <textarea id="orgNote" placeholder="输入内容..." maxlength="100"></textarea>
                        </div>
                    </div>
                    <div class="notecompet">
                        <button class="btn btn-default btn-note">提交备注</button>
                        <button class="btn btn-default org_del">标记为无效</button>
                    	</br>
                        <button class="btn btn-default updlogClick">查看更新记录</button>
                        <button class="btn btn-default meetingClick">查看会议记录</button>
                    </div>
                   
                    
                </div>

            </div>
        </div>
    </div>

<!--   <div class="fiexd-right-menu">-->
<!--       <div class="do-right-menu"></div>-->
<!--       <div class="do-top"></div>-->
<!--   </div>-->
<!--    <div class="right-mulu">-->
<!--        <div class="right-mulu-box showrightaction">-->
<!--            <div class="title">-->
<!--                快速定位-->
<!--            </div>-->
<!--            <ul>-->
<!--                <a data-i-href="xq"><li><span class="iconleft">&lsaquo;</span> 机构详情</li></a>-->
<!--                <a data-i-href="tzr"><li><span class="iconleft">&lsaquo;</span> 投资人</li></a>-->
<!--                <a data-i-href="jz"><li><span class="iconleft">&lsaquo;</span> 基金</li></a>-->
<!--                <a data-i-href="jy"><li><span class="iconleft">&lsaquo;</span> 近期交易</li></a>-->
<!--                <a data-i-href="yklx"><li><span class="iconleft">&lsaquo;</span> 易凯联系人</li></a>-->
<!--            </ul>-->
<!--        </div>-->
<!--    </div>-->
    
<!--    基金子页-->
    <div id="fundPage" class="pageBack">
    	<div class="page_body">
		<div class="box child">
	            <div class="box-title">
	             基金
	             <div class="box-return">
	              <button class="returnBtn fundReturn">返回</button>
	             </div>
	            </div>
	            <div class="box-body">
	            	<table id="pageFundBody">
	            		  <thead>
	            		  		<tr>
	            		  			<th>名称</th>
	            		  			<th>币种</th>
	            		  			<th>金额</th>
	            		  			<th>状态</th>
	            		  		</tr>
	            		  </thead>
	                      <tbody id="pageFundBody">
	                 	  </tbody>
	                 </table>
	            </div>
	            <div class="pageaction">
		            <div class="gigantic pagination fundPageaction">
		                <a href="#" class="previous" data-action="previous">&lsaquo;</a>
		                <input type="tel" readonly="readonly" />
		                <a href="#" class="next" data-action="next">&rsaquo;</a>
		                <lable class="totalSize fundLable" id="totalSize"></lable>
		            </div>
		        </div>
	    </div>
	    </div>
	</div>
	
<!--	近期交易更多子页-->
	<div id="tradePage" class="pageBack">
    	<div class="page_body">
		<div class="box child">
	            <div class="box-title">
	             近期交易
	             <div class="box-return">
	              <button class="returnBtn tradeReturn">返回</button>
	             </div>
	            </div>
	            <div class="box-body">
	            	<table id="pageTradeBody">
	            		  <thead>
	            		  		<tr>
	            		  			<th width="17%">时间</th>
                            		<th width="30%">公司</th>
                            		<th width="20%">阶段</th>
                            		<th width="15%">金额</th>
                            		<th width="18%">操作</th>
	            		  		</tr>
	            		  </thead>
	                      <tbody id="pageTradeBody">
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
	
	<!-- 投资人更多子页-->
	<div id="investorPage" class="pageBack">
    	<div class="page_body">
		<div class="box child">
	            <div class="box-title">
	             投资人
	             <div class="box-return">
	              <button class="returnBtn investorReturn">返回</button>
	             </div>
	            </div>
	            <div class="box-body">
	            	<table id="pageInvestorBody">
	            		  <thead>
	            		  		<tr>
	            		  			<th width="40%">姓名</th>
	            		  			<th width="25%">职位</th>
	            		  			<th width="15%">状态</th>
	            		  			<th width="20%">操作</th>
	            		  		</tr>
	            		  </thead>
	                      <tbody>
	                 	  </tbody>
	                 </table>
	            </div>
	            <div class="pageaction">
		            <div class="gigantic pagination investorPageaction">
		                <a href="#" class="previous" data-action="previous">&lsaquo;</a>
		                <input type="tel" readonly="readonly" />
		                <a href="#" class="next" data-action="next">&rsaquo;</a>
		                <lable class="totalSize investorLable" id="totalSize"></lable>
		            </div>
		        </div>
	    </div>
	    </div>
	</div>
	
	<!-- 会议更多子页-->
	<div id="meetingPage" class="pageBack">
    	<div class="page_body">
		<div class="box child">
	            <div class="box-title">
	             会议
	             <div class="box-return">
	              <button class="returnBtn meetingReturn">返回</button>
	             </div>
	            </div>
	            <div class="box-body">
	            	<table id="pageMeetingBody">
	            		  <thead>
	            		  		<tr>
	            		  			<th>时间</th>
	            		  			<th>记录人</th>
	            		  			<th>公司/机构</th>
	            		  		</tr>
	            		  </thead>
	                      <tbody>
	                 	  </tbody>
	                 </table>
	            </div>
	            <div class="pageaction">
		            <div class="gigantic pagination meetingPageaction">
		                <a href="#" class="previous" data-action="previous">&lsaquo;</a>
		                <input type="tel" readonly="readonly" />
		                <a href="#" class="next" data-action="next">&rsaquo;</a>
		                <lable class="totalSize meetingLable" id="totalSize"></lable>
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
<script type="text/javascript">
	var orgCode="${orgCode}";
	var backtype="${backtype}";
</script>
<script type="text/javascript" src="view/mobile/js/Template.js"></script>
<script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
<script type="text/javascript" src="view/mobile/js/common.js"></script>
<script type="text/javascript" src="view/mobile/js/page.js"></script>
<script type="text/javascript" src="view/mobile/js/jquery.jqpagination.js"></script>
<script type="text/javascript" src="view/mobile/js/organization_info.js"></script>
</html>