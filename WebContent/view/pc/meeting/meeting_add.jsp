<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!DOCTYPE html>
<html>
<head lang="zh-ch">
<title>易凯投资</title>
<meta charset="UTF-8">
<meta name="keywords" content="易凯投资">
<meta name=”renderer” content=”webkit”>
<meta http-equiv=”X-UA-Compatible” content=”IE=Edge,chrome=1″>
<link rel="stylesheet" href="view/pc/common/css/common.css">
<link rel="stylesheet" href="view/pc/common/css/animate.css">
<link rel="stylesheet" href="view/pc/common/css/font.css">
<link rel="stylesheet" href="view/pc/common/css/layer.css">
<link rel="stylesheet" href="view/pc/common/css/datepicker.css">
<link rel="stylesheet" href="view/pc/common/css/sweetalert.css">
<link rel="stylesheet" href="view/pc/common/css/select2.min.css">
<link rel="stylesheet" href="view/pc/common/css/meeting_add.css">
</head>
<body>
	<jsp:include page="../common/head.jsp" />
	<div id="main-content" class="content">
		<span class="main-content-box main-content-box-left">
			<div class="left-menu-box">
				<jsp:include page="../common/left_menu.jsp" />
			</div>
		</span>
		<div class="main-content-box main-content-box-right">
			<div class="right-content">
				<div class="head">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					添加 > <span class="strong">会议记录</span>
				</div>


				<div class="right-content-body">
					<div class="right-group-box">
						<div class="head">
							<div class="title">会议记录</div>
						</div>
						<div class="right-group-box-body">
							<div class="company_info">
								<div class="info_content">
									<div class="info_content_title">时间日期：</div>
									<div class="info_content_txt">
										<input type="text" class="span2" id="meetingdate"
											data-date-format="yyyy-mm-dd" readonly>
									</div>
								</div>
								 <div class="shgroup" id="EKRid">
                  <div class="title">
                      <span class="tip-edit baskClick">参会人<span class="caret"></span>：</span>
                  </div>
                  <div class="tiplist">
                      <ul class="ul-bask" ro="1" id="ykparp">
                      </ul>
                  </div>
                  <div class="more display-none">更多<span class="glyphicon glyphicon-chevron-down"></span></div>
              </div>

			<div class="info_content">
				<div class="info_content_title">相关机构：</div>
				<div class="content_txt" style="width: 85%; position: relative; min-height: 25px;">
					<ul class="orul" id="touzilist" style="margin-right:45px;"></ul>
					<div class="trade_add_img">
						<button id="add-touzi"class="btn btn_icon_add add_company"></button>
					</div>
			</div>
			</div>

			<div class="info_content">
				<div class="info_content_title">相关公司：</div>
				<div class="content_txt"style="width: 85%; position: relative; min-height: 25px;">
					<ul class="orul" id="complist" style="margin-right:45px;"></ul>
					<div class="trade_add_img">
						<button id="add-gongsi" class="btn btn_icon_add add_company"></button>
					</div>
				</div>
			</div>
			<div class="shgroup" id="meetingtype_id">
                  <div class="title">
                      <span class="tip-edit meetingtypeClick"  >会议类型<span class="caret"></span>：</span>
                  </div>
                  <div class="tiplist">
                      <ul class="ul-meetingtype" ro="1" id="meetingType">
                      </ul>
                  </div>
                  <div class="more display-none">更多<span class="glyphicon glyphicon-chevron-down"></span></div>
              </div>			
			<div class="shgroup" id="shareid">
                  <div class="title">
                      <span class="tip-edit baskClick"  >分享范围<span class="caret"></span>：</span>
                  </div>
                  <div class="tiplist">
                      <ul class="ul-bask" ro="1" id="sharewad">
                      </ul>
                  </div>
                  <div class="more display-none">更多<span class="glyphicon glyphicon-chevron-down"></span></div>
              </div>
              
              <div class="info_content">
							<div class="info_content_title">会议内容：</div>
							<div class="meetingfilesdiv">
								<button id="add-meeting" class="btn btn_icon_add add_meetingfile"></button>
								<div class="tab_content_table meetingfile">
                            	<a href="javascript:;" class="choicefile ">
                            		<input id="file0" i=0 name="file" type="file" class=""/>选择文件
                            	</a>
                            	<span class="filename" i="0"></span>
                            	<span class="btn_delete filedelete"  del="0"></span>
                            	</div>
								
							</div>
							 
							<div class="add_redord_txt">
									<textarea class="txt_redord"   maxlength="3000" id="meetingcontentid"></textarea>
							</div>
			 </div>
			</div>
							<div class="add_redord">
								<div class="add_redord_txt">
									<textarea class="txt_redord" placeholder="添加备注..." id="noteinfoid" maxlength="100"></textarea>
								</div>
							</div>
						</div>

					</div>
					<div class="add_redord_btn">
						<button id="addmeetingsub" class="btn btn-default">创建会议记录</button>
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
		 	var YKuserList=${YKuserList};//易凯参会人员
		 	var SysWadList=${SysWadList};//筐
		 	var meetingTypeList=${meetingTypeList};//会议类型
		 	var userInfo=${userInfo};//当前用户
		  	var nowdate = "${nowdate}";
		</script>
<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/Template.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>
<script type="text/javascript" src="view/pc/common/js/ajaxfileupload.js"></script>
    
<script type="text/javascript"
	src="view/pc/common/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="view/pc/common/js/layer.js"></script>
<script type="text/javascript" src="view/pc/common/js/select2.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/sweetalert.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/meeting_add.js"></script>
</html>