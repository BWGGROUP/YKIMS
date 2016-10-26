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
    <meta http-equiv=”X-UA-Compatible” content=”IE=Edge,chrome=1″ >
    <link rel="stylesheet" href="view/pc/common/css/common.css">
    <link rel="stylesheet" href="view/pc/common/css/font.css">
    <link rel="stylesheet" href="view/pc/common/css/layer.css">
    <link rel="stylesheet" href="view/pc/common/css/datepicker.css">
	<link rel="stylesheet" href="view/pc/common/css/select2.min.css">
    <link rel="stylesheet" href="view/pc/common/css/meeting_info.css">
</head>
<body>
    <jsp:include  page="../common/head.jsp"/>
	<div id="main-content" class="content">
    <span class="main-content-box main-content-box-left">
        <div class="left-menu-box">
    <jsp:include  page="../common/left_menu.jsp"/>
        </div>
    </span>
    <span class="main-content-box main-content-box-right">
        <div class="right-content">
            <div class="head">
                <span class="glyphicon glyphicon-home">  </span>
                <span class="head-title">
                    搜索 > 会议 ><span class="strong">会议记录</span>
                </span>
            </div>
            <div class="right-content-body">
                <div class="right-group-box">
                    <div class="head">
                        <div class="title">
                            会议记录详情
                        </div>
                        <div class="goback"><button class="btn btn-default smart-btn">返回</button></div>
                    </div>
                    <div class="right-group-box-body">
                        <div class="shgroup editJoinMeet">
                            <div class="title">
                                易凯参会人<span class="caret hidden joinmeeting"></span>：
                            </div>
                            <div class="tiplist">
                                <ul class="meetpeo">

                                </ul>
                            </div>
                        </div>
                        <div class="shgroup">
                            <div class="title">
                                相关机构：
                            </div>
                            <div class="tiplist">
                                <ul class="invicont" >

                                </ul>
                                <div class="trade_add_img hidden">
									<button id="add-org" class="btn btn_icon_add add_org"></button>
								</div>
                            </div>
                        </div>

                        <div class="shgroup">
                            <div class="title">
                                相关公司：
                            </div>
                            <div class="tiplist">
                                <ul class="companycont" >

                                </ul>
                                <div class="trade_add_img hidden">
									<button id="add-gongsi" class="btn btn_icon_add add_company"></button>
								</div>
                            </div>
                        </div>
                        <div class="shgroup meetingtypeClick ">
                            <div class="title">
                               会议类型<span class="caret hidden"></span>：
                            </div>
                            <div class="tiplist">
                                <ul class="typerange" >

                                </ul>
                            </div>
                        </div>
                        <div class="shgroup shareClick hidden">
                            <div class="title">
                               分享范围<span class="caret"></span>：
                            </div>
                            <div class="tiplist">
                                <ul class="sharerange" >

                                </ul>
                            </div>
                        </div>
                        <div class="shgroup">
                            <div class="tab_title">会议内容：
                            </div>
                            <div class="tab_content_table contentDiv">
<!--    <pre class="meeting-conten">-->
<!--    </pre>-->
                            </div>
                            <div class="record_save saveMeetCont hidden">
                                    <button class="btn btn-default-save saveMeetClick">保存内容</button>
                            </div>
                        </div>
                        
                        <div class="shgroup">
                            <div class="tab_title">会议附件：
                            </div>
                            <div class="tab_content_table meetingfile">
<!--                            	<form action="AddMeetingInfo.html?logintype=PC" method="post"  enctype="multipart/form-data">-->
                            	<a href="javascript:;" class="choicefile hidden">
                            		<input id="file" name="file" type="file" class=""/>选择文件
                            	</a>
                            	<span class="filename newfilename hidden"></span>
                            	<input type="button" value="上传" class="btn btn-default-save submitMeeting hidden"/>
<!--                            	<a id="loadmeetingfile" href="#" class="btn btn-default-save downloadMeeting hidden" target="newWindows">下载</a>-->
                            </div>
                            <div class="tab_content_table meetingfile">
                            	<ul class="loadul">
                            	</ul>
                            	
                            </div>
                           
                        </div>
                        <div class="shgroup">
                            <div class="tab_title">备注：</div>
                            <div class="tab_content_table tran_content_note">
                                <table>
                                    <thead>
                                        <tr>
                                            <th width="20%">时间</th>
                                            <th width="20%">记录人</th>
                                            <th width="50%">内容</th>
                                            <th width="10%">删除</th>
                                        </tr>
                                    </thead>
                                    <tbody class="notetable">

                                    </tbody>
                                </table>
    <div class="closeshearch" >更多<span class="glyphicon glyphicon-chevron-down"></span></div>
                            </div>
                            <div class="tran_content_table">
                                <div class="record_save">
                                <textarea class="tran_textarea notetext" placeholder="添加备注..." maxlength="100"></textarea>
                                </div>
                                <div class="record_save">
                                    <button class="btn btn-default-save addnote" >保存备注</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </span>


</div>
<div class="line1"></div>
<div class="line2"></div>
<div class="line3"></div>
</body>
    <script>
    var paths="<%=path%>";
    var basepath="<%=basePath%>";
    var nowdate = '${nowdate}';
    var meeting_object=${viewMeetingRele};
    var meetingcode='${meetingcode}';
    var meetingpeople=meeting_object.view_meeting_usercodename;
    var meeting_invicont=meeting_object.base_meeting_invicont;
    var meeting_compcont=meeting_object.base_meeting_compcont;
    var meetingcont=meeting_object.base_meeting_content;
    var meetingcode=meeting_object.base_meeting_code;
    var backtype="${backtype}";
    </script>
    <script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
    <script type="text/javascript" src="view/pc/common/js/Template.js"></script>
    <script type="text/javascript" src="view/pc/common/js/common.js"></script>
    <script type="text/javascript" src="view/pc/common/js/ajaxfileupload.js"></script>
    
    <script type="text/javascript" src="view/pc/common/js/layer.js"></script>
    <script type="text/javascript"
	src="view/pc/common/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="view/pc/common/js/select2.min.js"></script>
    <script type="text/javascript" src="view/pc/common/js/meeting_info.js"></script>
</html>