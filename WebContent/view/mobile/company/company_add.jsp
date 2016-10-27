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
    <link rel="stylesheet" href="view/mobile/css/dropload.css">
    <link rel="stylesheet" href="view/mobile/css/jqpagination.css">
    <link rel="stylesheet" href="view/mobile/css/page.css">
    <link rel="stylesheet" href="view/mobile/css/company_add.css">

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
                添加公司
            </div>
            <div class="box-body">
                <div class="display-table">
                    <div class="shgroup " id="edit-name">
                        <div class="title no-border" >公司名称:</div>
                        <div class="tiplist no-border nameUp">
                            <ul class="">
                                <li class="little-name"><span class="color-def">公司简称</span></li>
                                <li class="full-name"><span class="color-def">公司全称</span></li>
                                <li class="en-name"><span class="color-def">英文名称</span></li>
                            </ul>
                            <div class="edit-btn">
                            </div>
                        </div>
                    </div>
                    <div class="shgroup " >
                        <div class="title " >关注筐:</div>
                        <div class="tiplist kuang">
                            <ul class="kuanglist">

                            </ul>

                            <div id="edit-kuang" class="edit-btn">
                            </div>
                        </div>
                    </div>
                    <div class="shgroup " >
                        <div class="title " >行业:</div>
                        <div class="tiplist indu">
                            <ul class="hangyelist">

                            </ul>

                            <div id="edit-hangye" class="edit-btn">
                            </div>
                        </div>
                    </div>
                    <div class="shgroup " >
                    <div class="title " >阶段:</div>
                    <div class="tiplist stage">
                    <ul class="ul-stage">

                    </ul>

                    <div id="edit-stage" class="edit-btn">
                    </div>
                    </div>
                    </div>

                    <div class="shgroup " >
                        <div class="title " >联系人:</div>
                        <div class="tiplist people">
                            <ul class="peoplelist">

                            </ul>

                            <div id="add-people" class="addbtn">
                            </div>
                        </div>
                    </div>

                </div>
                <div class="shgroup  node">
                    <div class="title no-border motitle" >添加备注:</div>
                    <div class="meeting-box">
                        <textarea placeholder="输入内容..." class="compnote" maxlength="100"></textarea>
                    </div>
                </div>
                <%--<div class="notecompet">--%>
                    <%--<button class="btn btn-default">提交</button>--%>
                <%--</div>--%>
    <div class="notecompet">
    <button class="btn btn-default creatComp">创建公司</button>
    <button class="btn btn-default creatTrad" style="width: auto;">创建并添加交易</button>
    <button class="btn btn-default creatRz" style="width: auto;">创建并添加融资计划</button>
    </div>
            </div>
        </div>
    </div>
</div>


</body>
    <script>
    var inductList=${induList};
    var baskList=${baskList};
    var stageList=${stageList};
</script>

    <script type="text/javascript" src="view/mobile/js/jquery.min.js"></script>
    <script type="text/javascript" src="view/mobile/js/common.js"></script>
    <script type="text/javascript" src="view/mobile/js/Template.js"></script>
    <script type="text/javascript" src="view/mobile/js/company_add.js"></script>
    <script type="text/javascript"
    src="view/mobile/js/jquery.jqpagination.js"></script>
    <script type="text/javascript" src="view/mobile/js/common.js"></script>
    <script type="text/javascript" src="view/mobile/js/page.js"></script>
    <script type="text/javascript" src="view/mobile/js/dropload.js"></script>
</html>