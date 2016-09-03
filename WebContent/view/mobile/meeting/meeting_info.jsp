<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <link rel="stylesheet" href="view/mobile/css/meeting_info.css">
</head>
<body>
<div class="main-content">
    <div class="header">
        <div class="header-left left-menu-btn"></div>
        <div class="header-center"><div class="logo"></div></div>
        <div class="header-right"></div>
    </div>
    <div class="content-body">
        <div class="box-title">
            会议详情
            <div class="goback"><button class="returnBtn">返回</button></div>
        </div>
        <div class="">
            <div class="shgroup  people">
                <div class="title no-border" >易凯参会人:</div>
                <div class="tiplist no-border">
                    <ul class="meetpeo" ro="0">

                    </ul>
                    <div class="more"><span class="glyphicon glyphicon-chevron-down"></span></div>
                </div>
                <div class="edit-btn hidden"></div>

            </div>
            <div class="shgroup  jigou">
                <div class="title no-border" >相关机构:</div>
                <div class="tiplist no-border">
                    <ul class="invicont" ro="0">


                    </ul>
                    <div class="more"><span class="glyphicon glyphicon-chevron-down"></span></div>
                </div>
                <div class="addbtn hidden"></div>

            </div>
            <div class="shgroup  gongsi">
                <div class="title no-border" >相关公司:</div>
                <div class="tiplist no-border">
                    <ul class="companycont" ro="0">

                    </ul>
                    <div class="more" style="display:none"><span class="glyphicon glyphicon-chevron-down"></span></div>
                </div>
				<div class="addbtn hidden"></div>
            </div>
            <div class="shgroup meetingtype">
                <div class="title no-border" >会议类型:</div>
                <div class="tiplist no-border">
                    <ul class="typecontent" ro="0">

                    </ul>
                </div>
                <div class="edit-btn hidden"></div>

            </div>
            <div class="shgroup share hidden">
                <div class="title no-border" >分享范围:</div>
                <div class="tiplist no-border">
                    <ul class="sharecontent" ro="0">

                    </ul>
                </div>
                <div class="edit-btn hidden"></div>

            </div>
            <div class="shgroup  tipedit">
                <div class="title no-border motitle" >会议内容:</div>
                <div class="meeting-box contentDiv">
                </div>
                <div class="meetcontbtn hidden">
			   		<button class="btn btn-default smart saveMeetCont">保存</button>
			    </div>
            </div>
            <div class="shgroup  tipedit meetfilediv">
                <div class="title no-border meetingfile" >会议附件:</div>
                <div class="filediv">
                	<ul class="meetingfileul">
<!--                		<li><span class="filename">拉萨多久放了卡.doc</span> <span class="glyphicon glyphicon-remove fileDelClick"></span></li>-->
                	</ul>
                </div>
            </div>
            <div class="shgroup  tipedit">
                <div class="title no-border motitle" >备注:</div>
                <div class="meeting-box">
                    <table>
                        <tbody class="notetable">

                        </tbody>
                    </table>
    <div class="closeshearch" >展开<span class="glyphicon glyphicon-chevron-down"></span></div>
                </div>
            </div>
            <div class="shgroup  tipedit">
                <div class="title no-border motitle" >添加备注:</div>
                <div class="meeting-box">
                    <textarea placeholder="输入内容..." class="notetext" maxlength="100"></textarea>
                </div>
            </div>
    <div class="notecompet">
    <button class="btn btn-default addnote">提交备注</button>
    </br>

    </div>
        </div>

    </div>
</div>
</body>
    <script>
    var nowdate = '${nowdate}';
    var meeting_object=${viewMeetingRele};
    var meetingpeople=meeting_object.view_meeting_usercodename;
    var meeting_invicont=meeting_object.base_meeting_invicont;
    var meeting_compcont=meeting_object.base_meeting_compcont;
    var meetingcont=meeting_object.base_meeting_content;
    var meetingcode=meeting_object.base_meeting_code;
    var backtype="${backtype}";

    </script>
    <script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
    <script type="text/javascript" src="view/mobile/js/Template.js"></script>
    <script type="text/javascript" src="view/mobile/js/common.js"></script>
    <script type="text/javascript" src="view/mobile/js/dropload.js"></script>
    <script type="text/javascript" src="view/mobile/js/meeting_info.js"></script>
</html>