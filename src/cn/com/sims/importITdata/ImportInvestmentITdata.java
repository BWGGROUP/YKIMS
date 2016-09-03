package cn.com.sims.importITdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.common.commonUtil.ConstantUrl;
import cn.com.sims.crawler.util.JDBCHelper;
import cn.com.sims.model.baseinveslabelinfo.BaseInveslabelInfo;
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.syslabelelement.SysLabelelementInfo;
import cn.com.sims.service.code.SerialNumberGeneratorService;
import cn.com.sims.service.importitdata.IImportITdataService;
import cn.com.sims.service.system.syslabelelementinfo.ISysLabelelementInfoService;

@Controller
public class ImportInvestmentITdata {

    	// 导入service
    @Resource
    IImportITdataService iimportitdataservice;
    	// 生成id　service
    @Resource
    SerialNumberGeneratorService serialNumberGeneratorService;

    @Resource
	ISysLabelelementInfoService dic;//字典
    
    /* log */
    private static final Logger logger = Logger
	    .getLogger(ImportInvestmentITdata.class);

    /**
     * 导入it桔子抓取的机构数据
     * 
     * @throws Exception
     */
    public void importinvestmentdata() throws Exception {
	System.out.println("ImportInvestmentITdata　start:"+ System.currentTimeMillis());
	logger.info("ImportInvestmentITdata 的importinvestmentdata 导入机构信息开始");
	try {
	    /* 1:建立（连接it桔子抓取数据库）数据库的数据库连接 */
	    // 用JDBCHelper在JDBCTemplate池中建立一个名为importtemp1的JDBCTemplate
	    JDBCHelper.createMysqlTemplate("importtemp1",
		    ConstantUrl.ITSIMSDBURL, ConstantUrl.ITSIMSDBUSERNAME,
		    ConstantUrl.ITSIMSDBUSERPAW,
		    ConstantUrl.ITSIMSDBINITIALSIZE,
		    ConstantUrl.ITSIMSDMAXACTIVE);
	    logger.info("ImportInvestmentITdata 的importinvestmentdata 成功创建数据连接");
	} catch (Exception ex) {
	    ex.printStackTrace();
	    logger.error(
		    "ImportInvestmentITdata 的importinvestmentdata mysql未开启或JDBCHelper.createMysqlTemplate中参数配置不正确!",
		    ex);
	    return;
	}
	try {
	    /* ２:处理抓取存储的机构数据 */
	    /* ２.1:查询抓取存储的机构数据 */
	    // 读取数据库数据
	    JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate("importtemp1");
	    
	  //2015-11-30 TASK069 RQQ ModStart
	    // 拼接sql，查询数据
	    String sql = "select Tmp_Investment_Code, Tmp_Investment_Name,Tmp_Investment_Field"
	    	+ ",Tmp_Investment_Stage FROM Tmp_Investment_Info";
	    List<Map<String, Object>> tmpinvestmentlist = jdbcTemplate.queryForList(sql);
	  //2015-11-30 TASK069 RQQ ModEnd
	    
	    /* ２.2:处理需要导入的机构行业标签 */
	    // 匹配行业标签　
	    matchlabledbdata("Tmp_Investment_Info","Tmp_Investment_Field","","Lable-orgindu", "领域:");
	    /* ２.3:处理需要导入的机构数据 */
	    // 封装导入数据
	    // 循环查询到的数据
	    String tmpInvestmentcode;//IT桔子机构id
	    for (int i = 0; i < tmpinvestmentlist.size(); i++) {
	    	tmpInvestmentcode="";
    		String opreString = "nodo";
    		baseInvestmentInfo baseinves = new baseInvestmentInfo();
    		Map<String, Object> userMap = (Map<String, Object>) tmpinvestmentlist.get(i);
    		/** 截取最新机构路径为原路径格式 http://itjuzi.com/.... */
    		tmpInvestmentcode=userMap.get("Tmp_Investment_Code").toString().trim();
    		tmpInvestmentcode=changeSrc(tmpInvestmentcode);
    		/** 截取 end */
    		
    		// 判断id 和name 不允许为空
    		if (tmpInvestmentcode != null
    			&& userMap.get("Tmp_Investment_Name") != null) {

		    // 2.3.1:判断机构简称是否易凯已创建
		    baseInvestmentInfo nameinfo = iimportitdataservice
			    .findBaseInvestmentBynameForIT(userMap.get("Tmp_Investment_Name").toString().trim());
		    // 若2.3.1.1:若机构不存在易凯创建记录
		    if (nameinfo == null || "".equals(nameinfo)) {

			// 2.3.1.1.1:根据对应IT桔子id判断数据库是否存在该机构信息
			baseinves = iimportitdataservice
				.findBaseInvestmentBytmpcodeForIT(tmpInvestmentcode);
			
			// 2.3.1.1.1.1:数据库不存在记录，创建
			if (baseinves == null || "".equals(baseinves)) {
			    baseinves = new baseInvestmentInfo();
			    opreString = "add";
			    // 机构对应it桔子id
			    baseinves.setTmp_Investment_Code(tmpInvestmentcode);
			    // 机构简称
			    baseinves.setBase_investment_name(userMap.get("Tmp_Investment_Name").toString().trim());
			    // 生成投资机构id
			    String code = CommonUtil.PREFIX_INVESTMENT
				    + serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM,
					    CommonUtil.INVESTMENT_TYPE);
			    baseinves.setBase_investment_code(code);
			    // 机构简称拼音全拼,机构简称拼音首字母
			    String[] pinYin = CommonUtil.getPinYin(baseinves.getBase_investment_name());
			    // 机构简称拼音全拼
			    baseinves.setBase_investment_namep(pinYin[0]);
			    // 机构简称拼音首字母
			    baseinves.setBase_investment_namepf(pinYin[1]);
			    // 创建来源
			    baseinves.setBase_investment_stem("1");
			    // 当前状态
			    baseinves.setBase_investment_estate("1");
			    // 排它锁
			    baseinves.setBase_datalock_viewtype(1);
			  // PL锁状态
			    baseinves.setBase_datalock_pltype("0");
			    // 删除标识
			    baseinves.setDeleteflag("0");
			    // 创建者
			    baseinves.setCreateid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
			    // 创建时间
			    baseinves.setCreatetime(CommonUtil.getNowTime_tamp());
			    // 更新者
			    baseinves.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
			    // 更新时间
			    baseinves.setUpdtime(baseinves.getCreatetime());
			}else {// 2.3.1.1.1.2:若数据库存在记录
			    if ("1".equals(baseinves.getBase_investment_estate())
				    || "4".equals(baseinves.getBase_investment_estate())) {
			    	//1:IT桔子创建 4：IT桔子修改；
				opreString = "do";

				/*
				 * 2.3.1.1.1.2.1:判断数据是否发生变化，若发生变化更新数据
				 * 【当前状态：it桔子修改】，【PL锁状态】
				 */
				if (!userMap.get("Tmp_Investment_Name").toString().trim()
					.equals(baseinves.getBase_investment_name())) {
				    opreString = "upd";
				    // 机构简称
				    baseinves.setBase_investment_name(userMap.get("Tmp_Investment_Name").toString().trim());
				    // 机构简称拼音全拼,机构简称拼音首字母
				    String[] pinYin = CommonUtil.getPinYin(baseinves.getBase_investment_name());
				    // 机构简称拼音全拼
				    baseinves.setBase_investment_namep(pinYin[0]);
				    // 机构简称拼音首字母
				    baseinves.setBase_investment_namepf(pinYin[1]);
				    // 当前状态
				    baseinves.setBase_investment_estate("4");
				    // PL锁状态
				    baseinves.setBase_datalock_pltype("0");
				    // 更新者
				    baseinves.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
				    // 更新时间
				    baseinves.setUpdtime(CommonUtil.getNowTime_tamp());
				}
			    }
			}
			// 2.3.1.２:判断机构信息是否可进行操作，可操作则处理行业标签
			if (!"nodo".equals(opreString)) {
			    // 机构标签（存储数据库使用）
			    List<BaseInveslabelInfo> baseinveslabellist = new ArrayList<BaseInveslabelInfo>();
			    
			    //导入机构行业
			    if (userMap.get("Tmp_Investment_Field") != null) {
				// 抓取数据库的机构行业标签转为数组
			    String[] induStrArray = userMap.get("Tmp_Investment_Field").toString().split(" ");
				// 处理循环放置截取数据
				for (int j = 0; j < induStrArray.length; j++) {
				    BaseInveslabelInfo baseinveslabelinfo = new BaseInveslabelInfo();
				    String labelelementname = "";
//				    if (induStrArray[j].indexOf("领域:") >= 0) {
//						labelelementname = induStrArray[j].substring("领域:".length()).trim();
//				    } else {
//						labelelementname = induStrArray[j].trim();
//				    }
				    if(induStrArray[j]!=null&&!"".equals(induStrArray[j].trim())){
				    	labelelementname=induStrArray[j].trim();
				    }
				    
				   if(labelelementname!=null && !"".equals(labelelementname)){
				    // 查询标签是否存在的参数map
				    Map<String, String> indumap = new HashMap<String, String>();
				    // 标签所属code
				    indumap.put("labelcode", CommonUtil.findNoteTxtOfXML("Lable-orgindu"));
				    indumap.put("labelelementname",labelelementname);
				    // 查询标签是否存在
				    SysLabelelementInfo syslabelelementinfo = iimportitdataservice.querylabelelementbyname(indumap);
				    // 若存在
				    if (syslabelelementinfo != null
					    && !"".equals(syslabelelementinfo)) {
        					// 投资机构id
        					baseinveslabelinfo.setBase_investment_code(baseinves.getBase_investment_code());
        					// 标签元素code
        					baseinveslabelinfo.setSys_labelelement_code(syslabelelementinfo.getSys_labelelement_code());
        					// 标签code
        					baseinveslabelinfo.setSys_label_code(syslabelelementinfo.getSys_label_code());
        					// 删除标识
        					baseinveslabelinfo.setDeleteflag("0");
        					// 创建者
        					baseinveslabelinfo.setCreateid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
        					// 创建时间
        					baseinveslabelinfo.setCreatetime(CommonUtil.getNowTime_tamp());
        					// 更新者
        					baseinveslabelinfo.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
        					// 更新时间
        					baseinveslabelinfo.setUpdtime(baseinveslabelinfo.getCreatetime());
        					// 放置list
        					baseinveslabellist.add(baseinveslabelinfo);
    				    }
				   }
				}
			    }
				//2015-11-30 TASK069 RQQ AddStart
			    //导入机构投资阶段
			    if (userMap.get("Tmp_Investment_Stage") != null) {
				// 抓取数据库的机构阶段标签转为数组
//				String[] stageStrArray = userMap.get("Tmp_Investment_Stage").toString().split(",");
			    	String[] stageStrArray = userMap.get("Tmp_Investment_Stage").toString().trim().split(" ");
				// 处理循环放置截取数据
				for (int j = 0; j < stageStrArray.length; j++) {
				    BaseInveslabelInfo baseinveslabelinfo = new BaseInveslabelInfo();
				    String labelelementname = "";
//				    if (stageStrArray[j].indexOf("阶段:") >= 0) {
//						labelelementname = stageStrArray[j].substring("阶段:".length()).trim();
//				    } else {
//						labelelementname = stageStrArray[j].trim();
//				    }
				    if(stageStrArray[j]!=null&&!"".equals(stageStrArray[j].trim())){
				    	labelelementname=stageStrArray[j].trim();
				    }
				   if(labelelementname!=null && !"".equals(labelelementname)){
				       
				       //查询数据库匹配其导入后数据库名称
					String sysimporaftercont=iimportitdataservice.queryinvesstageforIT(labelelementname);
			    	if(sysimporaftercont!=null && !"".equals(sysimporaftercont)){
				    // 查询标签是否存在的参数map
				    Map<String, String> indumap = new HashMap<String, String>();
				    // 标签所属code
				    indumap.put("labelcode", CommonUtil.findNoteTxtOfXML("Lable-investage"));
				    indumap.put("labelelementname",sysimporaftercont);
				    // 查询标签是否存在
				    SysLabelelementInfo syslabelelementinfo = iimportitdataservice.querylabelelementbyname(indumap);
				    // 若存在
				    if (syslabelelementinfo != null) {
        					// 投资机构id
        					baseinveslabelinfo.setBase_investment_code(baseinves.getBase_investment_code());
        					// 标签元素code
        					baseinveslabelinfo.setSys_labelelement_code(syslabelelementinfo.getSys_labelelement_code());
        					// 标签code
        					baseinveslabelinfo.setSys_label_code(syslabelelementinfo.getSys_label_code());
        					// 删除标识
        					baseinveslabelinfo.setDeleteflag("0");
        					// 创建者
        					baseinveslabelinfo.setCreateid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
        					// 创建时间
        					baseinveslabelinfo.setCreatetime(CommonUtil.getNowTime_tamp());
        					// 更新者
        					baseinveslabelinfo.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
        					// 更新时间
        					baseinveslabelinfo.setUpdtime(baseinveslabelinfo.getCreatetime());
        					// 放置list
        					baseinveslabellist.add(baseinveslabelinfo);
    				    }
				   }
				   }
				}
			    }
				//2015-11-30 TASK069 RQQ AddEnd
			    // 存储数据库，并调用存储过程
			    iimportitdataservice.tranModifyimportinvestmentinfo(baseinves,
					    baseinveslabellist, opreString,baseinves.getBase_investment_code());
			}
		    }
		}
	    }
	    System.out.println("ImportInvestmentITdata　end:"+ System.currentTimeMillis());
	} catch (Exception e) {
	    e.printStackTrace();
	    logger.error("ImportInvestmentITdata 的importinvestmentdata 机构信息导入数据库出现异常",e);
	    return;
	}
	logger.info("ImportInvestmentITdata 的importinvestmentdata 导入机构信息结束");
	
    }

    /**
     * 处理指定标签元素字段，进行匹配添加操作
     * 
     * @param 字典code
     * @return
     * @throws Exception
     * 
     */
    public void matchlabledbdata(String tablename, String fieldcolm,
	    String conditionstr, String lablecode, String prestr)
	    throws Exception {
	logger.info("ImportInvestmentITdata 的matchlabledbdata 匹配并插入数据库标签开始");
	try {
	    // 读取数据库数据
	    JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate("importtemp1");
	    // 拼接sql，查询数据
	    String sql = "select DISTINCT " + fieldcolm + " as fieldcolm"
		    + " FROM " + tablename + " where " + fieldcolm
		    + " is NOT NULL" + conditionstr;
	    // 数据库sql查询结果list
	    List<Map<String, Object>> lablelist = jdbcTemplate.queryForList(sql);
	    // 标签集合list
	    List<String> lablestrList = new ArrayList<String>();
	    // 查询标签是否存在的参数map
	    Map<String, String> indumap = new HashMap<String, String>();
	    // 标签所属code
	    indumap.put("labelcode", CommonUtil.findNoteTxtOfXML(lablecode));
	    // 循环数据库sql查询去重放置标签list
	    for (int i = 0; i < lablelist.size(); i++) {
    		Map<String, Object> lableMap = (Map<String, Object>) lablelist.get(i);
    			// 拆分查询到的数据
    		String[] lableStrArray = lableMap.get("fieldcolm").toString().split(" ");
    			// 循环放置截取数据
    		for (int j = 0; j < lableStrArray.length; j++) {
    		    String labelelementname = "";
//    		    if (lableStrArray[j].indexOf(prestr) >= 0) {
//    				labelelementname = lableStrArray[j].substring(prestr.length()).trim();
//    		    } else {
//    				labelelementname = lableStrArray[j].trim();
//    		    }
    		    if(lableStrArray[j]!=null&&!"".equals(lableStrArray[j].trim())){
    		    	labelelementname=lableStrArray[j].trim();
    		    	if(lablecode.equals("Lable-stage")
    		    			||lablecode.equals("Lable-comstage")
    		    			||lablecode.equals("Lable-trastage")){
    		    		if(labelelementname.indexOf("(")>=0
    		    				&&labelelementname.indexOf(")")>labelelementname.indexOf("(")){
    		    			if(labelelementname.indexOf("获投状态")>=0){
    		    				labelelementname=labelelementname.substring(labelelementname.indexOf("获投状态")+"获投状态:".length(),labelelementname.lastIndexOf(")"));
    		    			}else{
    		    				labelelementname=labelelementname.substring(labelelementname.indexOf("(")+1,labelelementname.lastIndexOf(")"));
    		    			}
    		    		}
    		    	}
    		    	
    		    }
    		    if(labelelementname!=null && !"".equals(labelelementname)){
	    		    // 去重放置标签list
	    		    if (!lablestrList.contains(labelelementname)) {
	    				lablestrList.add(labelelementname);
	    		    }
    		    }
    		}
	    }

	    // 查询标签是否存在
	    for (int m = 0; m < lablestrList.size(); m++) {
			indumap.put("labelelementname", lablestrList.get(m));
			// 查询标签是否存在
			SysLabelelementInfo syslabelelementinfo = iimportitdataservice.querylabelelementbyname(indumap);
		// 若不存在，增加标签
		if (syslabelelementinfo == null
			|| "".equals(syslabelelementinfo)) {
		    SysLabelelementInfo labelelementinfo = new SysLabelelementInfo();
		    // 标签元素code
		    String labelelementcode = "";
		    // 取当前标签的最大值
		    String maxlablecode = iimportitdataservice
			    .querymaxlabelelementbycode(CommonUtil.findNoteTxtOfXML(lablecode));
		    // 标签的最大值不存在
		    if ("".equals(maxlablecode) || maxlablecode == null) {
				labelelementcode = CommonUtil.findNoteTxtOfXML(lablecode)
				+ CommonUtil.findNoteTxtOfXML("LABELELEMENT_CODE_INIT");
		    }else {// 标签的最大值存在,取最大值加１
				int codeint;
    			if (maxlablecode.lastIndexOf("0") >= 0) {
    			    codeint = Integer.valueOf(maxlablecode.substring(maxlablecode.lastIndexOf("0"))) + 1;
    			} else {
    			    codeint = Integer.valueOf(maxlablecode.substring(maxlablecode.length() - 3)) + 1;
    			}
    			labelelementcode = maxlablecode.substring(0,maxlablecode.length()
					- String.valueOf(codeint).length())
					+ String.valueOf(codeint);
		    }
		    //2016-4-22 排序标识
		    labelelementinfo.setLabel_index(dic.findLabelCountByLabelcode(CommonUtil.findNoteTxtOfXML(lablecode)));
		    // 标签元素code
		    labelelementinfo.setSys_labelelement_code(labelelementcode);
		    // 标签code
		    labelelementinfo.setSys_label_code(CommonUtil.findNoteTxtOfXML(lablecode));
		    // 标签元素名称
		    labelelementinfo.setSys_labelelement_name(lablestrList.get(m));
		    // 标签状态
		    labelelementinfo.setSys_labelelement_state("0");
		    // 删除标识
		    labelelementinfo.setDeleteflag("0");
		    // 创建者
		    labelelementinfo.setCreateid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
		    // 创建时间
		    labelelementinfo.setCreatetime(CommonUtil.getNowTime_tamp());
		    // 更新者
		    labelelementinfo.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
		    // 更新时间
		    labelelementinfo.setUpdtime(labelelementinfo.getCreatetime());
		    // 插入数据库
		    iimportitdataservice.insertsyslabelementinfo(labelelementinfo);
		}

	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    logger.error("ImportInvestmentITdata 的matchlabledbdata 匹配并插入数据库标签出现异常",e);
	}
	logger.info("ImportInvestmentITdata 的matchlabledbdata 匹配并插入数据库标签结束");
    }

    /**
     * 转换IT桔子新抓取路径https://www为原http://
     * @param code
     * @return
     */
    public String changeSrc(String code) {
		/** 截取最新机构路径为原路径格式 http://... */
		if(code.indexOf("www")>=0){
			code="http://"
				+code.substring(code.indexOf("www")+"www.".length(),
						code.length());
		}else if(code.indexOf("https")>=0){
			code="http"
				+code.substring(code.indexOf("https")+"https".length(),
						code.length());
		}
		/** 截取 end */
		return code;
	}
    
    public static void main(String[] args) {
    	String code="http://www.itjuzi.com/company/6890";
    	
    	if(code.indexOf("www")>=0){
			code="http://"
				+code.substring(code.indexOf("www")+"www.".length(),
						code.length());
		}else if(code.indexOf("https")>=0){
			code="http"
				+code.substring(code.indexOf("https")+"https".length(),
						code.length());
		}
    	
    	System.out.println(code);
	}
    
}