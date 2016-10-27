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
<meta http-equiv=”X-UA-Compatible” content=”IE=Edge,chrome=1″>
<link rel="stylesheet" href="view/pc/common/css/common.css">
<link rel="stylesheet" href="view/pc/common/css/font.css">
<style type="text/css">
.wrapper {
  margin:40px auto;
  text-align:center;
}

/*EXAMPLE ONE - EMBOSS, NEW BROWSERS*/

p {
  background:#fff;
  -webkit-background-clip: text;
  color: transparent;
  text-shadow: rgba(0,0,0,0.2) 1px 1px 1px;
  font-size:20px;
  line-height:1.2em;
}

/*EXAMPLE TWO - INLET, NEW BROWSERS*/

p:nth-child(2) {
  font-size:3em;
  background:#BABABA;
  -webkit-background-clip: text;
  color: transparent;
  text-shadow: rgba(255,255,255,0.2) 1px 4px 2px;
}

/*EXAMPLE THREE - CROSS-BROWSER COMPATIBLE*/

.inlet {
height:100%;
  color: rgba(120,120,120,1.0);
  position: relative;
}

.inlet:before, .inlet:after {
  content: attr(title);
  color: rgba(240,240,240,.5);
  position: absolute;
  display:block;
  left: 0;
  right: 0;
  margin: auto;
}

.inlet:before { 
  top: 1px; 
  left: 1px;
}
.inlet:after  { 
  top: 2px; 
  left: 2px; 
}
</style>
</head>
<body>
    <jsp:include  page="./view/pc/common/head.jsp"/>
	<div id="main-content" class="content">
		<span class="main-content-box main-content-box-left">
			<div class="left-menu-box">
    <jsp:include  page="./view/pc/common/left_menu.jsp"/>
    </div>
		</span> <span class="main-content-box main-content-box-right">
			<div class="right-content">
				<div class="head">
					<span class="glyphicon glyphicon-home"> </span>
					</span>
				</div>
				<div class="right-content-body">
					<div class="right-group-box">
						<div class="head">
						</div>
						<div class="right-group-box-body">
						<div class="wrapper">
  					<div class="inlet" title="ERROR" style="font-size:100px;">ERROR<p>您没有权限查看此页面！</p></div>
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
<script type="text/javascript">var optactive=0;</script>
<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
<script type="text/javascript" src="view/pc/common/js/common.js"></script>

</html>