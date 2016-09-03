<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>线程测试</title>
</head>
<body>
		<form action="threadtest.html" method="post">
			线程个数 m个人：<input type="text" name="threadnum" value="10">
			<br/>
			线程执行次数　m个人请求n次：		
			<input type="text" name="threadcount" value="10">
			<br/>
			线程执行次数时间间隔 　n次请求时间间隔x　毫秒：		
			<input type="text" name="threadcounttime" value="100">
			<br/>
			单次每个线程之间执行时间间隔　m个人请求时间间隔y毫秒		
			<input type="text" name="threadnumtime" value="100">
			<br/>
			单个线程内容方法执行次数　　线程执行的方法多少次
			<input type="text" name="threadfuncount" value="10">
			
			<input type="submit"value="提交">
		</form>
</body>
</html>