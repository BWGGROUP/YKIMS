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
    <link rel="stylesheet" href="view/mobile/css/trade_add.css">

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
            <div class="box-title" >
                <span style="float:left;">添加交易</span>
                <button class="btn btn-return" style="display:none;" id="returnbutton">返回</button>
            </div>
             
            <div class="box-body">
                <div class="display-table">
                    <div class="shgroup " >
                        <div class="title no-border" >交易日期:</div>
                        <div class="tiplist no-border">
                         <input type="date" class="inputdefpage inputdef" id="tradedate"/>
                        </div>
                    </div>
 												<div  class="shgroup ">
                        <div class="title" >被投公司:</div>
                        <div class="tiplist">
                            <input class="inputdefpage inputdef company" id="company" readonly UNSELECTABLE='true'
																	onfocus="this.blur();" />
                        </div>
                    </div>
 													<div  class="shgroup listtip " id="kuangid">
                        <div class="title">筐:</div>
                        <div class="tiplist">
                            <ul id="ul-bask" class="baskClick" ro="0">

                            </ul>
                            	<div class="edit-btn">
                            </div>
                        </div>

                    </div>
                    <div class="shgroup listtip " id="induid">
                        <div class="title" >行业:</div>
                        <div class="tiplist">
                            <ul id="ul-indu" class="induClick"  ro="1">
                            </ul>
                            <div class="edit-btn">
                            </div>
                        </div>

                    </div>
                    <div class="shgroup listtip" id="stageid">
                        <div class="title">阶段:</div>
                        <div class="tiplist ">
                            <ul  id="ul-stage" class="stageClick"  ro="5">
                            </ul>
																	<div class="edit-btn">
                            </div>
                        </div>

                    </div>
                    <div class="shgroup" >
                        <div class="title ">融资金额:</div>
                        <div class="tiplist">
                            <input type="text" class="inputdef inputdef_l" id='trademoney' maxlength="20"/>
                        </div>
                    </div>
                    <div class="shgroup" >
                        <div class="title">公司估值:</div>
                        <div class="tiplist">
                            <input type="text" class="inputdef inputdef_l" id='tradecomnum' maxlength="20"/>
                        			<select class="inputdef" id="tradecomnumtype" style="width:75px;">
                                <option value="0">获投前</option>
                                <option value="1">获投后</option>
                            </select>
                        </div>
                    </div>
                    </div>
                       <div class="shgroup" id="rongzi">
                        <div class="title  no-border motitle">融资信息:</div>
                        <div class="tablepadding">
                            <table id="tradeInfo">
                                <thead>
                                <tr>
                                    <th width="30%">投资机构</th>
                                    <th width="20%">投资人</th>
                                    <th width="30%">金额</th>
                                    <th width="20%">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
																			<div class="addbtn"></div>
                        </div>
                    
                 </div>
                <div class="shgroup  node" style="margin-top: 25px">
                    <div class="title no-border motitle" >添加备注:</div>
                    <div class="meeting-box">
                        <textarea placeholder="输入内容..."  id='noteinfoid' maxlength="100"></textarea>
                    </div>
                </div>
                <div class="notecompet">
                    <button class="btn btn-default" id='addtradesub'>创建交易</button>
                </div>
            </div>
        </div>
    </div>
</div>
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
<script type="text/javascript" src="view/mobile/js/Template.js"></script>
<script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
<script type="text/javascript" src="view/mobile/js/common.js"></script>
<script type="text/javascript" src="view/mobile/js/dropload.js"></script>
<script type="text/javascript" src="view/mobile/js/page.js"></script>
<script type="text/javascript" src="view/mobile/js/jquery.jqpagination.js"></script>
<script type="text/javascript" src="view/mobile/js/trade_add.js"></script>
</html>