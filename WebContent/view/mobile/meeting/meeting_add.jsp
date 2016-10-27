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
    <link rel="stylesheet" href="view/mobile/css/meeting_add.css">


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
                添加会议
            </div>
            <div class="box-body">
                <div class="display-table">
                    <div class="shgroup " >
                        <div class="title no-border" >会议日期:</div>
                        <div class="tiplist no-border">
                            <input type="date" class="inputdef" id="meetingdate"/>
                        </div>
                    </div>
                    <div class="shgroup " id="EKRid">
                        <div class="title " >易凯参会人:</div>
                        <div class="tiplist ">
                            <ul class="dasklist" id="ykparp">
                            
                            </ul>
                            <div class="edit-btn">
                            </div>
                        </div>
                    </div>
                    <div class="shgroup " >
                        <div class="title " >相关机构:</div>
                        <div class="tiplist ">
                            <ul id="touzilist" class="touzilist">

                            </ul>

                            <div id="add-touzi" class="addbtn">
                            </div>
                        </div>
                    </div>
                    <div class="shgroup " >
                        <div class="title " >相关公司:</div>
                        <div class="tiplist ">
                            <ul id="gongsilist" class="touzilist">

                            </ul>

                            <div id="add-gongsi" class="addbtn">
                            </div>
                        </div>
                    </div>
                    <div class="shgroup " id="meetingtype_id">
                        <div class="title " >会议类型:</div>
                        <div class="tiplist ">
                            <ul id="meetingtypelist" class="meetingtype_ul">

                            </ul>

                            <div class="edit-btn">
                            </div>
                        </div>
                    </div>
                    <div class="shgroup " id="shareid">
                        <div class="title " >分享范围:</div>
                        <div class="tiplist ">
                            <ul class="dasklist" id="sharewad">

                            </ul>
                            <div class="edit-btn">
                            </div>
                        </div>
                    </div>
		              
            </div>
                </div>
                 <div class="shgroup  tipedit" id="meetingcontent">
		                <div class="title no-border motitle" >会议内容:</div>
		                <div class="meeting-box">
		  													<textarea placeholder="会议内容..."  id='meetingcontentid' maxlength="3000"></textarea>
		                </div>
                <div class="shgroup  node" style="margin-top: 10px">
                    <div class="title no-border motitle" >添加备注:</div>
                    <div class="meeting-box">
                        <textarea placeholder="输入内容..."  id='noteinfoid' maxlength="100"></textarea>
                    </div>
                </div>
                <div class="notecompet">
                    <button class="btn btn-default" id='addmeetingsub'>创建会议</button>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
		<script>
		 	var YKuserList=${YKuserList};//易凯参会人员
		 	var SysWadList=${SysWadList};//筐	
		 	var userInfo=${userInfo};//当前用户
		 	var meetingTypeList=${meetingTypeList};//会议类型
		  	var nowdate = "${nowdate}";
		</script>
		<script type="text/javascript" src="view/mobile/js/Template.js"></script>
		<script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
		<script type="text/javascript" src="view/mobile/js/common.js"></script>
		<script type="text/javascript" src="view/mobile/js/dropload.js"></script>
		<script type="text/javascript" src="view/mobile/js/page.js"></script>
		<script type="text/javascript" src="view/mobile/js/jquery.jqpagination.js"></script>
   <script type="text/javascript" src="view/mobile/js/meeting_add.js"></script>
</html>