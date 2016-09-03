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
    <link rel="stylesheet" href="view/mobile/css/organization_add.css">
    <link rel="stylesheet" href="view/mobile/css/bootstrap-switch.css">
    
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
                添加投资机构
            </div>
            <div class="box-body">
                <div class="display-table">
	               		<div class="shgroup "  >
	                        <div class="title no-border">机构名称:</div>
	                        <div class="tiplist no-border ">
	                            <ul class="orglist editOrg">
	                            </ul>
	
	                            <div class="edit-btn editOrg">
	                            </div>
	                        </div>
	                    </div>
                    <div class="shgroup " id="edit-kuang" >
                        <div class="title ">筐:</div>
                        <div class="tiplist ">
                            <ul class="dasklist editDask">
                            </ul>

                            <div class="edit-btn editDask">
                            </div>
                        </div>
                    </div>
                    <div class="shgroup " id="edit-hangye">
                        <div class="title " >行业:</div>
                        <div class="tiplist ">
                            <ul class="indulist editIndu">
                            </ul>

                            <div  class="edit-btn editIndu">
                            </div>
                        </div>
                    </div>
                    <div class="shgroup " id="edit">
                        <div class="title " >近期关注:</div>
                        <div class="tiplist ">
                            <ul class="payattlist editPayatt">
                            </ul>

                            <div  class="edit-btn editPayatt">
                            </div>
                        </div>
                    </div>
                    <div class="shgroup ">
                        <div class="title " >背景:</div>
                        <div class="tiplist ">
                         	<ul class="bggroundlist editBgground">
                            </ul>

                            <div class="edit-btn editBgground">
                            </div>
<!--                            <input id="switch-state" type="checkbox" checked data-size="mini" data-on-color="warning" data-off-color="warning" data-on-text="国内" data-off-text="国外">-->
                        </div>
                    </div>
                    <div class="shgroup " id="edit-kuang" >
                        <div class="title ">投资阶段:</div>
                        <div class="tiplist ">
                            <ul class="stagelist editStage">
                            </ul>

                            <div class="edit-btn editStage">
                            </div>
                        </div>
                    </div>
                    <div class="shgroup " id="leixing" >
                        <div class="title ">类型:</div>
                        <div class="tiplist ">
                            <ul class="typelist eidtType">
                            </ul>

                            <div class="edit-btn eidtType">
                            </div>
                        </div>
                    </div>
                    <div class="shgroup " id="edit-kuang" >
                        <div class="title ">投资特征:</div>
                        <div class="tiplist ">
                            <ul class="featlist editFeat">
                            </ul>

                            <div class="edit-btn editFeat">
                            </div>
                        </div>
                    </div>

                </div>
                <div id="jz" class="shgroup jijintable" >
                    <div class="addbtn addbtnfund"></div>
                    <div class="title no-border" >基金:</div>
                    <div class="tablepadding ">
                        <table class="invesFundTable">
                            <thead>
                            <tr>
                                <th width="25%">
                                    名称
                                </th>
                                <th width="15%">
                                    币种
                                </th>
                                <th width="25%">
                                    金额
                                </th>
                                <th width="15%">
                                    状态
                                </th>
                                <th width="20%">
                                	操作
                                </th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>

                    </div>

                </div>

				<div class="shgroup node" id="yk" >
						<div class="addbtn addbtnyk"></div>
                        <div class="title no-border">易凯联系人:</div>
                        <div class="yklist ">
                        </div>
                 </div>

                <div class="shgroup  node" style="margin-top: 10px">
                    <div class="title no-border motitle" >添加备注:</div>
                    <div class="meeting-box">
                        <textarea placeholder="输入内容..." id="orgNote"></textarea>
                    </div>
                </div>
                <div class="notecompet">
<!--                    <div class="submd" >-->
<!---->
<!--                    </div>-->

                    <div class="bottom-btn" >
                        <button class="btn btn-default orgSubmit">创建机构</button>
                        </br>
                        <button class="btn btn-default orgSubmitToTrade">创建并添加交易</button>
                        <button class="btn btn-default orgSubmitToInvestor">创建并添加投资人</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


</body>

<script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
<script type="text/javascript" src="view/mobile/js/common.js"></script>
<script type="text/javascript" src="view/mobile/js/Template.js"></script>
<script type="text/javascript" src="view/mobile/js/bootstrap-switch.js"></script>
<script type="text/javascript" src="view/mobile/js/organization_add.js"></script>
</html>