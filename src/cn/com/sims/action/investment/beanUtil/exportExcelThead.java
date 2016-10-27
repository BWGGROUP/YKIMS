package cn.com.sims.action.investment.beanUtil;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.sims.action.investment.investmentSearchAction;
import cn.com.sims.common.bean.EmailBean;
import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.service.investment.IInvestmentService;

/**
 * @author yanglian
 * @version ：2015年10月12日 类说明
 */

public class exportExcelThead  {

	cn.com.sims.util.email.IEmailSenderService emailSenderService;// 邮件service
	IInvestmentService investmentService;// 投资机构service
	private static final Logger logger = Logger
			.getLogger(investmentSearchAction.class);
	SysUserInfo sysUserInfo;
	private String hangye;
	private String kuang;
	private String currency;
	private String feature;
	private String stage;
	private String scale;
	private String payatt;
	private String competition;
	//2015-12-18 TASK92 yl add start
	//机构背景
	private String bground;
	//2015-12-18 TASK92 yl add end
	private String labletype;//20160607 add 投资类型
	
	HttpServletRequest request;
	HttpServletResponse response;
	String path = "";
	String hiddenPath;
	String type;
	String investmentName;

	public String getBground() {
		return bground;
	}

	public void setBground(String bground) {
		this.bground = bground;
	}

	public String getInvestmentName() {
		return investmentName;
	}

	public void setInvestmentName(String investmentName) {
		this.investmentName = investmentName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getHiddenPath() {
		return hiddenPath;
	}

	public void setHiddenPath(String hiddenPath) {
		this.hiddenPath = hiddenPath;
	}

	public String getLabletype() {
		return labletype;
	}

	public void setLabletype(String labletype) {
		this.labletype = labletype;
	}
	
	// 文件导出路径
	private String pathnew;

	public cn.com.sims.util.email.IEmailSenderService getEmailSenderService() {
		return emailSenderService;
	}

	public void setEmailSenderService(
			cn.com.sims.util.email.IEmailSenderService emailSenderService) {
		this.emailSenderService = emailSenderService;
	}

	public SysUserInfo getSysUserInfo() {
		return sysUserInfo;
	}

	public void setSysUserInfo(SysUserInfo sysUserInfo) {
		this.sysUserInfo = sysUserInfo;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public IInvestmentService getInvestmentService() {
		return investmentService;
	}

	public void setInvestmentService(IInvestmentService investmentService) {
		this.investmentService = investmentService;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getHangye() {
		return hangye;
	}

	public void setHangye(String hangye) {
		this.hangye = hangye;
	}

	public String getKuang() {
		return kuang;
	}

	public void setKuang(String kuang) {
		this.kuang = kuang;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getPayatt() {
		return payatt;
	}

	public void setPayatt(String payatt) {
		this.payatt = payatt;
	}

	public String getCompetition() {
		return competition;
	}

	public void setCompetition(String competition) {
		this.competition = competition;
	}

	public void run() {
		logger.info("表格导出开始");
		List investmentList = new ArrayList();
		String message = "success";
		int countSize = 0;
		Map<String, Object> paraMap = new HashMap<String, Object>();
		// 标识有误选择竞争公司　text:未选择竞争公司 scre:选择了竞争公司
		String types = "text";
		String downloadPath;
		try {
			path = CommonUtil.findNoteTxtOfXML("INVESTMENT_DOWN");
			downloadPath = CommonUtil.findNoteTxtOfXML("INVESTMENT_DOWNLOAD");
			if("text".equals(type)){
				paraMap.put("investmentName", investmentName);
				investmentList = investmentService
						.findInvestmentByNameExport(paraMap);
			}else{
				/**
				 * 筐,行业,币种,投资特征,投资阶段,融资规模值转化成list
				 */
				List lableBaskList;
				List lableinduList;// 行业list
				List lableCurrencyList;// 币种list
				List lableFeatureList;// 投资特征list
				List lableStageList;// 投资阶段list
				List lableScaleList;// 融资规模list
				List lablepayatteList;// 近期关注list
				List competitionList;// 竞争公司list
				List usdScaleList = new ArrayList(50);// 美元融资规模list
				List cnyScaleList = new ArrayList(50);// 人民币融资规模list
				//2015-12-18 TASK92 yl add start
				List lableBgroundList;//背景list
				//2015-12-18 TASK92 yl add end
				
				List lableTypeList;//类型list
				
				// 判断前台传来的参数是否为“”，并转化成list
				//2015-12-18 TASK92 yl add start
				//投资阶段list
				if ("".equals(bground)) {
					lableBgroundList = null;
				} else {
					lableBgroundList = Arrays.asList(bground.split(","));
					
				}
				//2015-12-18 TASK92 yl add end
				if ("".equals(kuang)) {
					lableBaskList = null;
				} else {
					lableBaskList = Arrays.asList(kuang.split(","));// 筐list
				}
				if ("".equals(hangye)) {
					lableinduList = null;
				} else {
					lableinduList = Arrays.asList(hangye.split(","));// 行业list
				}
				if ("".equals(currency)) {
					lableCurrencyList = null;
				} else {
					lableCurrencyList = Arrays.asList(currency.split(","));// 币种list
				}
				if ("".equals(feature)) {
					lableFeatureList = null;
				} else {
					lableFeatureList = Arrays.asList(feature.split(","));// 特征list
				}
				if ("".equals(stage)) {
					lableStageList = null;
				} else {
					lableStageList = Arrays.asList(stage.split(","));// 阶段list
				}
				if ("".equals(scale)) {
					lableScaleList = null;
				} else {
					lableScaleList = Arrays.asList(scale.split(","));// 规模list
					for(int i=0;i<lableCurrencyList.size();i++){
						if(lableCurrencyList.get(i).toString().contains("Lable-currency-001")){
							usdScaleList.add(null);
						}else if(lableCurrencyList.get(i).toString().contains("Lable-currency-002")){
							cnyScaleList.add(null);
						}
					}
					
					for(int i=0;i<lableScaleList.size();i++){
						if(lableScaleList.get(i).toString().contains("usd")){
							if(usdScaleList.get(0)==null){
								usdScaleList.set(0, lableScaleList.get(i));
							}else{
								usdScaleList.add(lableScaleList.get(i));
							}
						}else if(lableScaleList.get(i).toString().contains("cny")){
							if(cnyScaleList.get(0)==null){
								cnyScaleList.set(0,lableScaleList.get(i));
							}else{
								cnyScaleList.add(lableScaleList.get(i));
							}
						}
					}
				}
				if ("".equals(payatt)) {
					lablepayatteList = null;
				} else {
					lablepayatteList = Arrays.asList(payatt.split(","));// 关注list
				}
				if ("".equals(competition)) {
					competitionList = null;
				} else {
					competitionList = Arrays.asList(competition.split(","));// 竞争公司list
				}
				if (competitionList != null) {
					types = "scre";
				}
				
				//添加类型条件　20160607 dwj
				if("".equals(labletype)){
					lableTypeList=null;
				}else {
					lableTypeList=Arrays.asList(labletype.split(","));//投资类型
				}
				
				// 筐，行业，币种，投资特征，投资阶段，融资规模初始化查询
				paraMap.put("lableBaskList", lableBaskList);
				paraMap.put("lableinduList", lableinduList);
				paraMap.put("lableCurrencyList", lableCurrencyList);
				paraMap.put("lableFeatureList", lableFeatureList);
				paraMap.put("lableStageList", lableStageList);
				paraMap.put("lableScaleList", lableScaleList);
				paraMap.put("lablepayatteList", lablepayatteList);
				paraMap.put("competitionList", competitionList);
				paraMap.put("usdScaleList", usdScaleList);
				paraMap.put("cnyScaleList", cnyScaleList);
				//2015-12-18 TASK92 yl add start
				paraMap.put("lableBgroundList", lableBgroundList);
				//2015-12-18 TASK92 yl add end
				//2016-6-7 add dwj
				paraMap.put("lableTypeList", lableTypeList);
				
				
				// 根据多条件查询符合条件的投资机构
				investmentList = investmentService
						.findInvestmentByMoreConExport(paraMap,competitionList);
			}
			

			Map<String, String> map = new LinkedHashMap<String, String>();
			// 设置导出的字段。其中map中的key必须与实体类中的字段一致，不区分大小写，这里需要注意的是：请确保实体类中给导出的字段提供了set、get方法，不然会取不到值
			System.out.println(investmentList);
			map.put("countNo", "序号");
			map.put("base_investment_name", "投资机构名称");
			map.put("invesfund", "基金名称和金额");
			map.put("view_investment_stagename", "投资阶段");
			map.put("view_investment_typename", "投资类型");
			map.put("view_investment_backcont", "背景");
			map.put("companyName", "相关行业近期交易");
			map.put("view_investment_compcode", "投过竞争公司");
			
			
    //uuid生成文件名称
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");//
			String s = UUID.randomUUID().toString();
			String s1 = s.substring(0, 8) + s.substring(9, 13)
					+ s.substring(14, 18) + s.substring(19, 23)
					+ s.substring(24);
			String fileName=CommonUtil.getNowTime().replace("-", "")+"/"+s1;
			File dir = new File(path);
			if (dir.exists()) {

			} else {
				dir.mkdirs();
			}
			// 生成文件名
			pathnew = path + "/" + fileName + ".xls";
			String downloadTime = CommonUtil.getNowdateTime();
			int dayint = Integer.parseInt(CommonUtil.findNoteTxtOfXML("Email_FAILURE_TIME"));
			ExportExcelAction.ImportExcel(investmentList, pathnew, map,
					CommonUtil.EXPORT_EXCEL_PRODUCT);
			File file = new File(pathnew);
			boolean fileExist = false;
			CommonUtil.findNoteTxtOfXML("Email_FAILURE_TIME");
			if (file.isFile() && file.exists()) {
				List<String> mailList = new ArrayList<String>(); /* 存储邮箱地址 */
				List<EmailBean> mailBeanList = new ArrayList<EmailBean>();/* 邮箱中的变量 */
				EmailBean emailBean = new EmailBean();
				emailBean.setDateTime(CommonUtil.datecalculation(CommonUtil.getNowTime_tamp(),dayint));//失效时间
				emailBean.setDownloadTime(downloadTime);//存放下载时间
				emailBean.setAccountName(sysUserInfo.getSys_user_name());//存放用户名
				emailBean.setDownloadUrl(hiddenPath+downloadPath+fileName+ ".xls");//下载地址
				mailBeanList.add(emailBean);
				mailList.add(sysUserInfo.getSys_user_email());
				//mailList.add("1032356484@qq.com");
				/*mailList.add("qianqian_ren518@163.com");*/
				emailSenderService.sendEmail(mailList, mailBeanList,
						"excelDownload.ftl");
			}
			message = "success";
			logger.info("表格导出结束");
		} catch (Exception e) {
			logger.error("exportExcelThead run 方法异常:", e);
			e.printStackTrace();
		}
	}
}
