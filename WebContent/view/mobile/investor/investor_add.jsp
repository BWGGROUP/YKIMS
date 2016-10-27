<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <title>易凯投资</title>
    <meta charset="UTF-8">
    <meta name="keywords" content="易凯投资">
    <meta name=”renderer” content=”webkit”>
    <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
    <link rel="stylesheet" href="view/mobile/css/font.css">
    <link rel="stylesheet" href="view/mobile/css/common.css">
    <link rel="stylesheet" href="view/mobile/css/investor_add.css">
    <link rel="stylesheet" href="view/mobile/css/dropload.css">
    <link rel="stylesheet" href="view/mobile/css/page.css">


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
                添加投资人
                <div class="goback"><button class="btn btn-default smart-btn">返回</button></div>
            </div>
            
            <div class="box-body">
                <div class="display-table">
                    <%--<div class="shgroup" >--%>
                        <%--<div class="title no-border" >所属投资机构:</div>--%>
                        <%--<div class="tiplist no-border">--%>
                           <%--<select class="inputdef selectdef">--%>
                               <%--<option>经纬中国</option>--%>
                               <%--<option>中科创星</option>--%>
                               <%--<option>德邦物流</option>--%>
                           <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <div class="shgroup "  >
                        <div class="title no-border">姓名:</div>
                        <div class="tiplist no-border">
                         <%--<input class="inputdef"/>--%>
                            <ul class="peoplename" ro="0">
                            <li data-i="code" class="investor">
    <%--<span class="color-def">姓名</span>--%>
    </li>
                            </ul>
                        </div>
                    </div>
                    <%--<div class="shgroup" >--%>
                        <%--<div class="title " >职位:</div>--%>
                        <%--<div class="tiplist ">--%>
                            <%--<select class="inputdef selectdef">--%>
                                <%--<option>CEO</option>--%>
                                <%--<option>经理</option>--%>
                                <%--<option>职员</option>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="shgroup " style="position: relative">
                    <div class="title">所属机构:</div>
                    <div class="tiplist">
                    <ul class="peopleContent" ro="0">
                    <li data-i="1" id="investmentName"><span class="color-def">投资机构名称</span>
                    <span class="color-def">职位</span>
                    <span class="color-def">在职状态</span></li>
                    </ul>
                    <div id="add-touzi" class="addbtn"></div>
                    </div>

                    </div>
                    <div class="shgroup ">
                    <div class="title">联系方式:</div>
                    <div class="tiplist add-contact">
                    <ul class="" ro="0">
                    <li data-i="1" class="phone"><span class="color-def">(手机)</span></li>
                    <li data-i="1" class="email"><span class="color-def">(邮箱)</span></li>
                    <li data-i="1" class="wchat"><span class="color-def">(微信)</span></li>
                    </ul>
                    </div>
                    </div>
                    <%--<div class="shgroup "  >--%>
                        <%--<div class="title">电话:</div>--%>
                        <%--<div class="tiplist ">--%>
                            <%--<input class="inputdef" type="tel"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="shgroup "  >--%>
                        <%--<div class="title">邮箱:</div>--%>
                        <%--<div class="tiplist ">--%>
                            <%--<input class="inputdef" type="email"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="shgroup "  >--%>
                        <%--<div class="title">微信:</div>--%>
                        <%--<div class="tiplist ">--%>
                            <%--<input class="inputdef"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="shgroup " id="edit-indu" >
                        <div class="title ">行业:</div>
                        <div class="tiplist ">
                            <ul class="hangyelist">

                            </ul>

                            <div class="edit-btn">
                            </div>
                        </div>
                    </div>
                    <div class="shgroup " id="edit-patty">
                        <div class="title " >近期关注:</div>
                        <div class="tiplist ">
                            <ul class="pattylist">

                            </ul>

                            <div  class="edit-btn">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="shgroup  node" >
                    <div class="title no-border motitle" >添加备注:</div>
                    <div class="meeting-box">
                        <textarea class="compnote" placeholder="输入内容..."  maxlength="100"></textarea>
                    </div>
                </div>
                <div class="notecompet">
                    <button class="btn btn-default creatInvestor">创建投资人</button>
                    <button class="btn btn-default creatTrad" style="width:auto">创建并添加交易</button>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
    <script>
    var indulist=${induList};
    var investmentCode='${investmentCode}';
    var investmentName='${investmentname}';
    var backherf='${backherf}';
    </script>
</html>
    <script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
    <script type="text/javascript" src="view/mobile/js/page.js"></script>
    <script type="text/javascript" src="view/mobile/js/common.js"></script>
    <script type="text/javascript" src="view/mobile/js/Template.js"></script>
    <script type="text/javascript" src="view/mobile/js/investor_add.js"></script>
    <script type="text/javascript" src="view/mobile/js/dropload.js"></script>
