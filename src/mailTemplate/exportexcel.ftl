<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 14">
<style>
td {
	mso-number-format: \@;
}

td {
	text-align: center;
	vertical-align: middle;
	max-width:500px;
}
</style>
<#if mainlist?exists> 
<#list mainlist as main>
<table cellspacing="0" cellpadding="0">
	<tr>
		<td></td>
		<td>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>

					<td height="50px" align="center" colspan="4" rowspan="2"
						valign="middle"><b><font size="5">${main.baseinfo.base_investment_name}<font></b>
					<td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
			<table border="1" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td height="21" align="center" valign="middle">机构名称</td>
					<td height="21" align="center" colspan="3" valign="middle">${main.baseinfo.base_investment_name!}</td>
				</tr>
				<tr>
					<td height="21" align="center" valign="middle">类型</td>
					<td height="21" align="center" colspan="3" valign="middle">${main.info.view_investment_typename!}</td>
				</tr>
				<tr>
					<td height="21" align="center" valign="middle">行业</td>
					<td height="21" align="center" colspan="3" valign="middle">${main.info.view_investment_induname!}</td>
				</tr>
				<tr>
					<td height="21" align="center" valign="middle">阶段</td>
					<td height="21" align="center" colspan="3">${main.info.view_investment_stagename}</td>
				</tr>
				<tr>
					<td height="21" align="center" valign="middle">基金信息</td>
					<td height="21" align="center" colspan="3" valign="middle">
						<#if main.fundList?exists && (main.fundList?size > 0)>
						<table>
							<tr>
								<td>名称</td>
								<td>币种</td>
								<td>金额</td>
							</tr>
							<#list main.fundList as fund>
							<tr>
								<td>${fund.base_invesfund_name!}</td>
								<td>${fund.base_invesfund_currencyname!}</td>
								<td>${fund.base_invesfund_scale!}</td>
							</tr>
							</#list>
						</table> 
						</#if>
					</td>
				</tr>
				<tr>
					<td height="21" align="center" valign="middle">近期交易</td>
					<td height="21" align="center" colspan="3" valign="middle">
						<#if main.tradeList?exists && (main.tradeList?size > 0)>
						<table>
							<tr>
								<td>公司</td>
								<td>阶段</td>
								<td>金额</td>
							</tr>
							<#list main.tradeList as trade>
							<tr>
								<td>${trade.base_comp_name!}</td>
								<td>${trade.base_trade_stagecont!}</td>
								<td>${trade.base_trade_inmoney!}</td>
							</tr>
							</#list>
						</table> 
						</#if>
					</td>
				</tr>

			</table>
		</td>
	</tr>
</table>
<table cellspacing="0" cellpadding="0">
	<tr></tr>
</table>
</#list>
</#if>
</body>

</html>
