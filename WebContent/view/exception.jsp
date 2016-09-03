<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.lang.Exception" %>
<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head lang="zh">
<title>易凯投资-异常页面</title>
<meta charset="UTF-8">
<meta name="keywords" content="易凯投资异常">
<meta name=”renderer” content=”webkit”>
<meta name="viewport"
	content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
<link rel="stylesheet" href="view/mobile/css/font.css">
<link rel="stylesheet" href="view/mobile/css/common.css">
<style type="text/css">
*,*:after,*:before {
  padding:0;
  margin:0;
}
body {
  font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", sans-serif; 
  font-weight: 600;
  background:#ccc;
}
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
  float: right;
  width: 70%;
}

/*EXAMPLE TWO - INLET, NEW BROWSERS*/

p:nth-child(2) {
  font-size:20px;
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
  width:100%;
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
.wrap {
width:100%;
height: 100%;
display: -webkit-box;
display: -moz-box;
display: box;
-webkit-box-orient: vertical;
-moz-box-orient: vertical;
box-orient: vertical;
}
</style>
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
			<div >
			<div ><%Exception ex=(Exception)request.getAttribute("exception");%>
			<H2>Exception:</H2>
			<div  class=“wrap”>
			<%ex.printStackTrace(new PrintWriter(out));%>
			</div>
			
			</div>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
<script type="text/javascript" src="view/mobile/js/common.js"></script>
</html>