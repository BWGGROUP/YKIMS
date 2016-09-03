<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt'%>
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
    <link rel="stylesheet" href="view/pc/common/css/sweetalert.css">
	<link rel="stylesheet" href="view/pc/common/css/jqPaginator.css">
	<link rel="stylesheet" href="view/pc/common/css/select2.min.css">
	<link rel="stylesheet" href="view/pc/common/css/layer.css">
    <link rel="stylesheet" href="view/pc/common/css/datepicker.css">
    <link rel="stylesheet" href="view/pc/common/css/company_detail.css">

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
                    搜索 > 投资机构 > <span class="strong">机构详情</span>
                </span>
            </div>
            <div class="right-content-body">
                <div class="right-group-box">
                    <div class="head heightauto">
                        <div class="title orgNameEname">
                        </div>
                        <div class="goback"><button class="btn btn-default smart-btn">返回</button></div>
                    </div>
                    <div class="right-group-box-body">
                        <div class="detail_relative">
                            <div class="detail_relative_bottom">
                                <div class="shgroup">
                                       <div class="title">
                                           <span class="tip-edit baskClick">
                                           筐<span class="caret"></span> :
                                           </span>
                                       </div>
                                       <div class="tiplist">
                                           <ul class="ul-bask" ro="1">
                                           </ul>
                                       </div>
                                       <div class="more display-none">更多<span class="glyphicon glyphicon-chevron-down"></span></div>
                                </div>
                                <div class="shgroup">
                                        <div class="title">
                                           <span class="tip-edit induClick">
                                           		行业<span class="caret"></span> :
                                           </span>
                                        </div>
                                        <div class="tiplist">
                                            <ul class="ul-indu" ro="2">
                                            </ul>
                                        </div>
                                        <div class="more display-none">更多<span class="glyphicon glyphicon-chevron-down"></span></div>
                                </div>
                                <div class="shgroup">
                                        <div class="title">
                                           <span class="tip-edit payattClick">
                                           		近期关注<span class="caret"></span> :
                                           </span>
                                        </div>
                                        <div class="tiplist">
                                            <ul class="ul-payatt" ro="3">
                                            </ul>
                                        </div>
                                        <div class="more display-none">更多<span class="glyphicon glyphicon-chevron-down"></span></div>
                                </div>
                                <div class="shgroup">
                                    <div class="title">
                                         <span class="tip-edit bggroundClick">
                                         		背景<span class="caret"></span> :
                                         </span>
                                    </div>
                                    <div class="tiplist">
                                        <ul class="ul-bgground" >

                                        </ul>
                                    </div>
                                </div>

                                <div class="shgroup">
                                        <div class="title">
                                            <span class="tip-edit stageClick">
                                            	投资阶段<span class="caret"></span> :
                                            </span>
                                        </div>
                                        <div class="tiplist">
                                            <ul class="ul-stage" ro="6">

                                            </ul>
                                        </div>
                                        <div class="more display-none">更多<span class="glyphicon glyphicon-chevron-down"></span></div>
                                </div>

                                <div class="shgroup">
                                        <div class="title">
                                            <span class="tip-edit typeClick">
                                            	类型<span class="caret"></span> :
                                            </span>
                                        </div>
                                        <div class="tiplist">
                                            <ul class="ul-type" ro="7">

                                            </ul>
                                        </div>
                                        <div class="more display-none">更多<span class="glyphicon glyphicon-chevron-down"></span></div>
                                </div>
                                <div class="shgroup">
                                        <div class="title">
                                            <span class="tip-edit featClick">
                                            		投资特征<span class="caret"></span> :
                                            </span>
                                        </div>
                                        <div class="tiplist">
                                            <ul class="ul-feat" ro="8">
                                            </ul>
                                        </div>
                                        <div class="more display-none">更多<span class="glyphicon glyphicon-chevron-down"></span></div>
                                   </div>

                           </div>
                        </div>
                        <div class="body_div havemore heightauto">
                            <div class="investorShgroup">
                                <div class="title">
                                    投资人:
                                </div>
                                <div class="tiplist">
                                    <ul class="ul-investor" >
                                        
                                    </ul>
                                </div>
                                <div class="investorMore">
											<button id="btn_investor_add" class="btn btn_icon_add"></button>
											<span class="investorMoreClick">更多</span>
								</div>
                            </div>
                        </div>
                        <div class="body_div">
                                <div class="tab_title">
                                    基金:
                                </div>
                                <div class="tran_content_table">
                                    <table class="content_table fundTable ">
                                        <thead class="tHead">
                                            <tr>
                                                <th width="35%">名称</th>
                                                <th  width="15%">币种</th>
                                                <th  width="35%">金额</th>
                                                <th  width="15%">状态</th>
                                            </tr>
                                        </thead>
                                        <tbody class="notes_table">
                                            
                                        </tbody>
                                    </table>
                                    <div class="body_div_btn">
                                        <button id="btn_detail_add" class="btn btn_icon_add"></button>
<!--                                        <button class="btn btn_icon_save"></button>-->
                                    </div>
                                    <div class="tabMore">
											<span class="fundMoreClick">更多</span>
									</div>
                                </div>
                                
                        </div>
                        <div class="body_div">
                            <div class="tab_title">
                                近期交易:
                            </div>
                            <div class="tran_content_table">
                                <table class="content_table">
                                    <thead  class="tHead">
                                    <tr>
                                        <th width="14%">时间</th>
                                        <th width="33%">公司</th>
                                        <th width="20%">阶段</th>
                                        <th width="7%">领投</th>
                                        <th width="20%">金额</th>
                                        <th width="6%">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody class="tradeTable">

                                    </tbody>
                                </table>
                                 <div class="body_div_btn">
                                        <button id="btn_trade_add" class="btn btn_icon_add btn_trade_add"></button>
                                 </div>
                                 <div class="tabMore">
									<span class="tradeMoreClick">更多</span>
								</div>
                            </div>
                        </div>
                        <div class="body_div">
                            <div class="shgroup heightauto">
                                <div class="title">
                                    易凯联系人:
                                </div>
                                <div class="tiplist linklist">
                                    <ul class="linkInvestor">
                                    </ul>
                                </div>
                                <div class="linkAddBtn">
                                    <button id="btn_detail_add" class="btn btn_icon_add linkAddClick"></button>
                                </div>
                            </div>
                        </div>
                        <div class="body_div">
                            <div class="tab_title">备注:</div>
                            <div class="tran_content_table">
                                <table class="content_table">
                                    <thead  class="tHead">
                                        <tr>
                                            <td width="15%">时间</td>
                                            <td width="20%">记录人</td>
                                            <td width="59%">内容</td>
                                            <td width="6%">操作</td>
                                        </tr>
                                    </thead>
                                    <tbody class="noteBody">
                                        
                                    </tbody>
                                </table>
                                <div class="closeshearch" style="display: block;">
                            	</div>
                            </div>
                        </div>
                        <div class="body_div noteDiv">
                            <textarea id="orgNote" class="body_div_txtDetail" maxlength="100" placeholder="添加备注..."></textarea>
                            <button class="btn btn-default-save detail_save" >保存备注</button>
                            <!--<button class="btn btn_icon_save"></button>-->
                        </div>
                    </div>
                </div>
                <div class="right-content-footer">
                    <button class="btn btn-default updlog_detial" >查看更新记录</button>
                    <button class="btn btn-default meet_detial" >查看会议记录</button>
                    <button class="btn btn-default org_del" >标记为无效</button>
                </div>
            </div>
        </div>
    </span>


</div>
<div class="line1"></div>
<div class="line2"></div>
<div class="line3"></div>
</body>
	<script type="text/javascript">
		var orgCode="${orgCode}";
		var backtype="${backtype}";
	</script>
 	<script type="text/javascript" src="view/pc/common/js/jquery.min.js"></script>
    <script type="text/javascript" src="view/pc/common/js/Template.js"></script>
    <script type="text/javascript" src="view/pc/common/js/common.js"></script>
    <script type="text/javascript" src="view/pc/common/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="view/pc/common/js/jqPaginator.js"></script>
	<script type="text/javascript" src="view/pc/common/js/select2.min.js"></script>
    <script type="text/javascript" src="view/pc/common/js/sweetalert.min.js"></script>
	<script type="text/javascript" src="view/pc/common/js/layer.js"></script>
    <script type="text/javascript" src="view/pc/common/js/company_detail.js"></script>

</html>