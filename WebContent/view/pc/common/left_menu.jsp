<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <link rel="stylesheet" href="view/pc/common/css/left.css">
</head>
<body>
    <div class="left-content">
        <div class="left-head">
            <span class="span-box"><button class=" btn optbtn" index="0"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button></span>
            <span class="span-box"><button class=" btn optbtn" index="1"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></button></span>
        </div>

        <div class="left-menu-body">
            <ul class="menu-list search" style="display: none">
                <a menu-href="gotoSearchMain.html">
                    <li class="li_organization">
                        <span class="icon"><img src="view/pc/common/icon/organization.png"> </span>投资机构
                    </li>
                </a>
                <a menu-href="trade_search.html">
                    <li class="li_lastdate">
                        <span class="icon"><img src="view/pc/common/icon/lastdate.png"></span>近期交易
                    </li>
                </a>
                <a menu-href="company_search.html">
                    <li class="li_company">
                        <span class="icon"><img src="view/pc/common/icon/company.png"></span>
                        公<span class="adkb"></span>司
                    </li>
                </a>
                <a menu-href="meeting_search.html">
                    <li class="li_meeting">
                        <span class="icon"><img src="view/pc/common/icon/meeting.png"></span>
                        会<span class="adkb"></span>议
                    </li>
                </a>
                <a menu-href="financing_search.html">
                    <li class="li_finan">
                        <span class="icon"><img src="view/pc/common/icon/finan.png"></span>融资计划
                    </li>
                </a>
                <a menu-href="myfoot_search.html">
                    <li class="li_myfoot">
                        <span class="icon"><img src="view/pc/common/icon/myfoot.png"></span>我的足迹
                    </li>
                </a>
            </ul>
            <ul class="menu-list add" style="display: none">
                <a menu-href="addMeetingInfoinit.html">
                    <li >
                        <span class="icon"><img src="view/pc/common/icon/meeting.png"></span>
                        会议记录
                    </li>
                </a>
                <a menu-href="organization_add.html">
                    <li>
                        <span class="icon"><img src="view/pc/common/icon/organization.png"></span>
                        投资机构
                    </li>
                </a>
                <a menu-href="investor_add.html">
                    <li>
                        <span class="icon"><img src="view/pc/common/icon/people.png"></span>
                        投<span class="adkb1"></span>资<span class="adkb1"></span>人
                    </li>
                </a>
                <a menu-href="addTradeInfoinit.html">
                <li>
                    <span class="icon"><img src="view/pc/common/icon/lastdate.png"></span>
                    交<span class="adkb"></span>易
                </li>
                </a>
                <a menu-href="company_add.html">
                    <li>
                        <span class="icon"><img src="view/pc/common/icon/company.png"></span>
                        公<span class="adkb"></span>司
                    </li>
                </a>
            </ul>
        </div>
    </div>
</body>
</html>