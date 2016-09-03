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
<meta name="viewport"
	content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
<link rel="stylesheet" href="view/mobile/css/font.css">
<link rel="stylesheet" href="view/mobile/css/common.css">
<link rel="stylesheet" href="view/pc/common/css/layer.css">
<link rel="stylesheet" href="view/mobile/css/dropload.css">
<link rel="stylesheet" href="view/mobile/css/jqpagination.css">
<link rel="stylesheet" href="view/mobile/css/meeting_search.css">

</head>
<body>
	<div class="main-content">
		<div class="header">
			<div class="header-left left-menu-btn"></div>
			<div class="header-center">
				<div class="logo"></div>
			</div>
			<div class="header-right"></div>
		</div>
		<div class="content-body">
			<div class="box">
				<div class="box-title">会议记录</div>
				<div class="box-body">
					<div class="display-table">
						<div class="shgroup  tipedit">
							<div class="title no-border">相关投资机构:</div>
							<div class="tiplist no-border">
								<input class="inputdef orgain" readonly UNSELECTABLE='true'
									onfocus="this.blur();" />
							</div>

						</div>
						<div class="shgroup  tipedit">
							<div class="title no-border">相关公司:</div>
							<div class="tiplist no-border">
								<input class="inputdef company" readonly UNSELECTABLE='true'
									onfocus="this.blur();" />
							</div>

						</div>
						<div class="shgroup  tipedit">
							<div class="title no-border">记录人:</div>
							<div class="tiplist no-border">
								<input class="inputdef people" readonly UNSELECTABLE='true'
									onfocus="this.blur();" />
							</div>

						</div>
						<div class="shgroup  tipedit">
							<div class="title no-border">会议类型:</div>
							<div class="tiplist no-border">
								<input class="inputdef meetingtype" readonly UNSELECTABLE='true'
									onfocus="this.blur();" />
							</div>

						</div>

					</div>
    <div class="searchbtn">
    <button class="btn btn-default submit">搜索</button>
    </div>
				</div>


				<div id="trcontent" style="display: none">
					<table>
						<thead class="tablehead">
							
						</thead>
						<tbody id="tablecontent">

						</tbody>
					</table>
					<div class="pageaction" style="display: none">
						<div class="gigantic pagination">
							<a href="#" class="previous" data-action="previous">&lsaquo;</a>
							<input type="tel" readonly="readonly" /> <a href="#"
								class="next" data-action="next">&rsaquo;</a>
							<lable class="totalSize" id="totalSize"></lable>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>



</body>
<script  type="text/javascript">
	var message='${message}';
	var backtype="${backtype}";
	var meetingtypeList=${typeLabelList};
</script>
<script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
<script type="text/javascript" src="view/mobile/js/common.js"></script>
<script type="text/javascript" src="view/mobile/js/Template.js"></script>
<script type="text/javascript"
	src="view/mobile/js/jquery.jqpagination.js"></script>
	
<script type="text/javascript" src="view/pc/common/js/layer.js"></script>
<script type="text/javascript" src="view/mobile/js/dropload.js"></script>
<script type="text/javascript" src="view/mobile/js/meeting_search.js"></script>
</html>