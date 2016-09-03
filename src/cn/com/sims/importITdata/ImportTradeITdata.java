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
import cn.com.sims.model.basecompinfo.BaseCompInfo;
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.basetradeinfo.BaseTradeInfo;
import cn.com.sims.model.basetradeinves.BaseTradeInves;
import cn.com.sims.model.basetradelabelinfo.BaseTradelabelInfo;
import cn.com.sims.model.syslabelelement.SysLabelelementInfo;
import cn.com.sims.service.code.SerialNumberGeneratorService;
import cn.com.sims.service.importitdata.IImportITdataService;

@Controller
public class ImportTradeITdata{
    
    	//导入service
    @Resource
    IImportITdataService iimportitdataservice;
	//生成id　service
    @Resource
	SerialNumberGeneratorService serialNumberGeneratorService;
    @Resource
    ImportInvestmentITdata importinvestmentitdata;
    /* log */
    private static final Logger logger = Logger.getLogger(ImportTradeITdata.class);
    
	
	/**
	 * 导入it桔子抓取的交易数据
	 * @throws Exception
	 */
	public void importtradeitdata()throws Exception {
	    System.out.println("ImportTradeITdata start:"+System.currentTimeMillis());
	    logger.info("ImportTradeITdata 的importtradeitdata导入交易信息开始");
		try {
		    /* 1:建立（连接it桔子抓取数据库）数据库的数据库连接 */
			//用JDBCHelper在JDBCTemplate池中建立一个名为importtemp1的JDBCTemplate 
			JDBCHelper
					.createMysqlTemplate(
							"importtemp1",
							ConstantUrl.ITSIMSDBURL,
							ConstantUrl.ITSIMSDBUSERNAME,
							ConstantUrl.ITSIMSDBUSERPAW, 
							ConstantUrl.ITSIMSDBINITIALSIZE,
							ConstantUrl.ITSIMSDMAXACTIVE);
			logger.info("ImportTradeITdata 的importtradeitdata 成功创建数据连接");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("ImportTradeITdata 的importtradeitdata mysql未开启或JDBCHelper.createMysqlTemplate中参数配置不正确!",ex);
			return;
		}
		try {
		 /* ２:处理抓取存储的交易数据 */
		  /* ２.1:查询抓取存储的交易数据 */
		//读取数据库数据
			JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate("importtemp1");
			//拼接sql，查询数据
			String sql="SELECT record.*,company.Tmp_Company_Name FROM (SELECT  CONCAT('http://',SUBSTRING(a.Tmp_Inves_Record_ID,13,40)) AS Tmp_Inves_Record_ID, "
					 +" CONCAT('http://',SUBSTRING(a.Tmp_Company_Code,13,40)) AS Tmp_Company_Code, "
					 +" a.Tmp_Inves_Record_Time,a.Tmp_Inves_Record_Round,a.Tmp_Inves_Record_Money,a.Tmp_Inment_Istor_Industry "
					 +" FROM Tmp_Investment_Record a ) record "
					 +" LEFT JOIN "
					 +" (SELECT CONCAT('http://',SUBSTRING(b.Tmp_Company_Code,12,40)) AS Tmp_Company_Code,b.Tmp_Company_Name FROM Tmp_Company_Info b) company "
					 +" ON record.Tmp_Company_Code=company.Tmp_Company_Code "
					 +" WHERE company.Tmp_Company_Name is not null ";
//				"select a.Tmp_Inves_Record_ID, a.Tmp_Company_Code,"
//				+ "a.Tmp_Inves_Record_Time,a.Tmp_Inves_Record_Round,"
//				+ "a.Tmp_Inves_Record_Money,a.Tmp_Inment_Istor_Industry"
//				+" ,b.Tmp_Company_Name"
//				+" FROM Tmp_Investment_Record a , Tmp_Company_Info b"
//				+" where a.Tmp_Company_Code=b.Tmp_Company_Code and b.Tmp_Company_Name is not null";
			List<Map<String, Object>> tmpinvestorlist=jdbcTemplate.queryForList(sql);
			
			 /* ２.2:处理需要导入的交易行业标签 */
			//匹配行业标签　
			importinvestmentitdata.matchlabledbdata("Tmp_Investment_Record","Tmp_Inment_Istor_Industry","","Lable-comindu","");
			//匹配轮次标签　
			importinvestmentitdata.matchlabledbdata("Tmp_Investment_Record","Tmp_Inves_Record_Round","","Lable-trastage","");
			
			/* ２.3:处理需要导入的交易数据 */
			//封装导入数据
			//循环查询到的数据
			for (int i=0;i<tmpinvestorlist.size();i++){
			    	String opreString="nodo";
			    	BaseTradeInfo basetradeinfo=new BaseTradeInfo();   
        			Map<String, Object> userMap = (Map<String, Object>) tmpinvestorlist.get(i); 
        			//判断交易id 和公司id 不允许为空
        			if(userMap.get("Tmp_Inves_Record_ID")!=null && userMap.get("Tmp_Company_Code")!=null){
        				
        			  //2.3.1:根据交易对应IT桔子id判断数据库是否存在该公司信息
        			    BaseCompInfo basecompinfo=iimportitdataservice.findBaseCompBytmpcodeForIT(userMap.get("Tmp_Company_Code").toString().trim());
        			    if(basecompinfo==null || "".equals(basecompinfo)){
        			    Map<String, String> maptmpname=new HashMap<String, String>();
        			    maptmpname.put("basecompname", userMap.get("Tmp_Company_Name").toString());
        				maptmpname.put("basecompfullname", null);
        			    basecompinfo=iimportitdataservice.findBaseCompBynameForIT(maptmpname);
        			    			}
        			    if(basecompinfo!=null && !"".equals(basecompinfo)){
        			    	//2.3.２.1:若存在公司，根据交易对应IT桔子id判断数据库是否存在该交易信息
        			    	basetradeinfo=iimportitdataservice.findTradeinfoBytmpcodeForIT(userMap.get("Tmp_Inves_Record_ID").toString().trim());
                			//2.3.2.1.1:数据库不存在记录，创建
                		if(basetradeinfo==null || "".equals(basetradeinfo)){
                		    basetradeinfo=new BaseTradeInfo();  
                    		opreString="add";
                    						//生成交易id
                    		String code=CommonUtil.PREFIX_TRADE+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.TRADE_TYPE);
                    		basetradeinfo.setBase_trade_code(code);
                    						//公司id
                    		basetradeinfo.setBase_comp_code(basecompinfo.getBase_comp_code());
                    						//交易对应it桔子id 
                    		basetradeinfo.setTmp_Inves_Record_ID(userMap.get("Tmp_Inves_Record_ID").toString().trim());
                    						//投资日期
                    		if(userMap.get("Tmp_Inves_Record_Time")!=null){
                    		    					//投资日期
                    		    basetradeinfo.setBase_trade_date(CommonUtil.formatdatefromstr(
                    			    userMap.get("Tmp_Inves_Record_Time").toString().trim()));
							}else {
                              basetradeinfo.setBase_trade_date(null);
                    						}
                    						//交易阶段
                    		if(userMap.get("Tmp_Inves_Record_Round")!=null){
                    							//交易阶段
                            						//查询标签是否存在的参数map
                    			Map<String, String> indumap=new HashMap<String, String>();
                    							//标签所属code
                    			indumap.put("labelcode", CommonUtil.findNoteTxtOfXML("Lable-trastage"));
                    			indumap.put("labelelementname",userMap.get("Tmp_Inves_Record_Round").toString().trim());
                    							//查询标签是否存在
                    			SysLabelelementInfo syslabelelementinfo=iimportitdataservice.querylabelelementbyname(indumap);
                    							//若存在
                    			if(syslabelelementinfo!=null && !"".equals(syslabelelementinfo)){
                    			    basetradeinfo.setBase_trade_stage(syslabelelementinfo.getSys_labelelement_code());
                    			}
                    		}
                    						//融资金额
                    		if(userMap.get("Tmp_Inves_Record_Money")!=null){
                    		    					//融资金额
                    		    basetradeinfo.setBase_trade_money(
                    			    userMap.get("Tmp_Inves_Record_Money").toString().trim());
							}else {
                                basetradeinfo.setBase_trade_money(null);
                    		}
                    						
                    						//创建来源
                    		basetradeinfo.setBase_trade_stem("1");
        							//当前状态
                    		basetradeinfo.setBase_trade_estate("1");
                    						//排它锁
                    		basetradeinfo.setBase_datalock_viewtype(1);
        						//PL锁状态
                    		basetradeinfo.setBase_datalock_pltype("0");
                    						//删除标识
                    		basetradeinfo.setDeleteflag("0");
                						//创建者
                    		basetradeinfo.setCreateid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
                						//创建时间
                    		basetradeinfo.setCreatetime(CommonUtil.getNowTime_tamp());
                						//更新者
                    		basetradeinfo.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
                    						//更新时间
                    		basetradeinfo.setUpdtime(basetradeinfo.getCreatetime());
                    		int m=iimportitdataservice.findcomptradeByCodeforit(basetradeinfo);
                    		if(m==0){
                    			opreString="add";
                    		}else{
                    			opreString="nodo";
                    		}
                		}
                			//2.3.2.1.1.2:若数据库存在记录
                		else{
                		    if ("1".equals(basetradeinfo.getBase_trade_estate()) 
                		    		|| "4".equals(basetradeinfo.getBase_trade_estate())) {
								opreString="do";
								BaseTradeInfo tmpbasetradeinfo=new BaseTradeInfo(); 
								//公司id
								tmpbasetradeinfo.setBase_comp_code(basecompinfo.getBase_comp_code());
				    	                    	//交易对应it桔子id 
								tmpbasetradeinfo.setTmp_Inves_Record_ID(userMap.get("Tmp_Inves_Record_ID").toString().trim());
				    	                    	//投资日期
				    	       if(userMap.get("Tmp_Inves_Record_Time")!=null){
				    	    	   //投资日期
				    	    	   tmpbasetradeinfo.setBase_trade_date(CommonUtil.formatdatefromstr(
				    	    			  userMap.get("Tmp_Inves_Record_Time").toString().trim()));
				    			}else {
				    				tmpbasetradeinfo.setBase_trade_date(null);
				    			}
				    	               //交易阶段
				    	      if(userMap.get("Tmp_Inves_Record_Round")!=null){
				    	                    		//交易阶段
				    	                            	//查询标签是否存在的参数map
				    	           Map<String, String> indumap=new HashMap<String, String>();
				    	                    		//标签所属code
				    	           indumap.put("labelcode", CommonUtil.findNoteTxtOfXML("Lable-trastage"));
				    	           indumap.put("labelelementname",userMap.get("Tmp_Inves_Record_Round").toString().trim());
				    	                    		//查询标签是否存在
				    	           SysLabelelementInfo syslabelelementinfo=iimportitdataservice.querylabelelementbyname(indumap);
				    	                    		//若存在
				    	           if(syslabelelementinfo!=null && !"".equals(syslabelelementinfo)){
				    	               tmpbasetradeinfo.setBase_trade_stage(syslabelelementinfo.getSys_labelelement_code());
				    	                    			}
				    	                    		}
				    	      		//融资金额
				    	           if(userMap.get("Tmp_Inves_Record_Money")!=null){
				    	        	   //融资金额
				    	               tmpbasetradeinfo.setBase_trade_money(
				    	                  userMap.get("Tmp_Inves_Record_Money").toString().trim());
				    				}else {
				    	              tmpbasetradeinfo.setBase_trade_money(null);
				    				}
						  		/*2.3.2.1.1.2.1:判断数据是否发生变化，若发生变化更新数据
						  		【当前状态：it桔子修改】，【PL锁状态】*/
                		    if(
                			    !((tmpbasetradeinfo.getBase_comp_code()!=null 
                			    && basetradeinfo.getBase_comp_code()!=null 
                				   		&&
                				  tmpbasetradeinfo.getBase_comp_code().equals(basetradeinfo.getBase_comp_code()))
                			    			||
                			    (tmpbasetradeinfo.getBase_comp_code()==null 
                	            && basetradeinfo.getBase_comp_code()==null))
                	            				||
                	           !((tmpbasetradeinfo.getBase_trade_date()!=null 
                	            && basetradeinfo.getBase_trade_date()!=null 
                	                			   &&
                	           tmpbasetradeinfo.getBase_trade_date().equals(basetradeinfo.getBase_trade_date()))
                	                			  ||
                	            (tmpbasetradeinfo.getBase_trade_date()==null 
                	            && basetradeinfo.getBase_trade_date()==null))
                	            				||
                	           !((tmpbasetradeinfo.getBase_trade_stage()!=null 
                	            && basetradeinfo.getBase_trade_stage()!=null 
                	                			   &&
                	           tmpbasetradeinfo.getBase_trade_stage().equals(basetradeinfo.getBase_trade_stage()))
                	                			  ||
                	            (tmpbasetradeinfo.getBase_trade_stage()==null 
                	            && basetradeinfo.getBase_trade_stage()==null))
                	            				||
                	           !((tmpbasetradeinfo.getBase_trade_money()!=null 
                	            && basetradeinfo.getBase_trade_money()!=null 
                	                			   &&
                	           tmpbasetradeinfo.getBase_trade_money().equals(basetradeinfo.getBase_trade_money()))
                	                			  ||
                	            (tmpbasetradeinfo.getBase_trade_money()==null 
                	            && basetradeinfo.getBase_trade_money()==null))
                			    			){
                					opreString="upd";
                							//公司id
    	                    	basetradeinfo.setBase_comp_code(basecompinfo.getBase_comp_code());
    	                    						//交易对应it桔子id 
    	                    	basetradeinfo.setTmp_Inves_Record_ID(userMap.get("Tmp_Inves_Record_ID").toString().trim());
    	                    		    				//投资日期
    	                    	basetradeinfo.setBase_trade_date(tmpbasetradeinfo.getBase_trade_date());
    	                    						//交易阶段
    	                    	basetradeinfo.setBase_trade_stage(tmpbasetradeinfo.getBase_trade_stage());	
    	                    						//融资金额
    	                    	basetradeinfo.setBase_trade_money(tmpbasetradeinfo.getBase_trade_money());
                            						//当前状态
                            	basetradeinfo.setBase_trade_estate("4");
                    			//PL锁状态
                            	basetradeinfo.setBase_datalock_pltype("0");
                           						//更新者
                            	basetradeinfo.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
                            						//更新时间
                            	basetradeinfo.setUpdtime(CommonUtil.getNowTime_tamp());
                		    	}
                		}
                		}
                		//2.3.２.1.２:判断交易信息是否可进行操作，可操作则处理行业标签
                		if(!"nodo".equals(opreString)){
        					//交易行业标签（存储数据库使用）
        					List<BaseTradelabelInfo> basetradelabellist=new ArrayList<BaseTradelabelInfo>();
        					//抓取数据库的机构行业标签转为数组
        					if(userMap.get("Tmp_Inment_Istor_Industry")!=null){
        					String[] induStrArray = userMap.get("Tmp_Inment_Istor_Industry").toString().split(",");
        					//处理循环放置截取数据
            				for (int j = 0; j < induStrArray.length; j++) {
            				BaseTradelabelInfo basetradelabelinfo= new BaseTradelabelInfo();
            			 			String labelelementname=induStrArray[j].trim();
            			 			if(labelelementname!=null && !"".equals(labelelementname)){
										//查询标签是否存在的参数map
            							Map<String, String> indumap=new HashMap<String, String>();
            									//标签所属code
            							indumap.put("labelcode", CommonUtil.findNoteTxtOfXML("Lable-comindu"));
            							indumap.put("labelelementname",labelelementname);
            									//查询标签是否存在
            							SysLabelelementInfo syslabelelementinfo=iimportitdataservice.querylabelelementbyname(indumap);
            									//若存在
									if(syslabelelementinfo!=null && !"".equals(syslabelelementinfo)){
											//交易id
									    	basetradelabelinfo.setBase_trade_code(basetradeinfo.getBase_trade_code());
											//标签元素code
									    	basetradelabelinfo.setSys_labelelement_code(syslabelelementinfo.getSys_labelelement_code());
											//标签元素code
									    	basetradelabelinfo.setSys_label_code(syslabelelementinfo.getSys_label_code());
											//删除标识
									    	basetradelabelinfo.setDeleteflag("0");
											//创建者
									    	basetradelabelinfo.setCreateid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
											//创建时间
									    	basetradelabelinfo.setCreatetime(CommonUtil.getNowTime_tamp());
											//更新者
									    	basetradelabelinfo.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
											//更新时间
									    	basetradelabelinfo.setUpdtime(basetradelabelinfo.getCreatetime());
											//放置list
									    	basetradelabellist.add(basetradelabelinfo);
											}
            								}
            							}
        							}
									//存储数据库，并调用存储过程
            						iimportitdataservice.tranModifyimporttradeinfo(basetradeinfo, basetradelabellist, opreString);
            						}
        					}
					}  
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ImportTradeITdata 的importtradeitdata 交易信息导入数据库出现异常",e);
			return;
		}
		  logger.info("ImportTradeITdata 的importtradeitdata导入交易信息结束");
		    System.out.println("ImportTradeITdata END:"+System.currentTimeMillis());
	}
	
	
	
	/**
	 * 导入it桔子抓取的机构交易数据
	 * @throws Exception
	 */
	public void importtradeinvedata()throws Exception {
	    System.out.println("importtradeinvedata start:"+System.currentTimeMillis());
	    logger.info("ImportTradeITdata 的importtradeinvedata导入机构交易信息开始");
		try {
		    /* 1:建立（连接it桔子抓取数据库）数据库的数据库连接 */
			//用JDBCHelper在JDBCTemplate池中建立一个名为importtemp1的JDBCTemplate 
			JDBCHelper
					.createMysqlTemplate(
							"importtemp1",
							ConstantUrl.ITSIMSDBURL,
							ConstantUrl.ITSIMSDBUSERNAME,
							ConstantUrl.ITSIMSDBUSERPAW, 
							ConstantUrl.ITSIMSDBINITIALSIZE,
							ConstantUrl.ITSIMSDMAXACTIVE);
			logger.info("ImportTradeITdata 的importtradeinvedata 成功创建数据连接");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("ImportTradeITdata 的importtradeinvedata mysql未开启或JDBCHelper.createMysqlTemplate中参数配置不正确!",ex);
			return;
		}
		try {
		 /* ２:处理抓取存储的机构交易数据 */
		  /* ２.1:查询抓取存储的机构交易数据 */
		//读取数据库数据
			JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate("importtemp1");
			//拼接sql，查询数据
			String sql="SELECT rela.*,comp.Tmp_Company_Name,investment.Tmp_Investment_Name FROM (select  "
						+" CONCAT('http://', SUBSTRING(a.Tmp_Investment_Code,13,40)) AS Tmp_Investment_Code,"
						+" CONCAT('http://', SUBSTRING(a.Tmp_Inves_Record_ID,13,40)) AS Tmp_Inves_Record_ID,"
						+" CONCAT('http://', SUBSTRING(a.Tmp_Company_Code,13,40)) AS Tmp_Company_Code,"
						+" a.Tmp_Investment_Recordrela_Money,"
						+" a.Tmp_Investor_Code"
						+" FROM Tmp_Investment_Recordrela a ) AS rela"
						+" INNER JOIN (SELECT "
						+" CONCAT('http://',SUBSTRING(Tmp_Company_Code,12,40)) AS Tmp_Company_Code,"
						+" Tmp_Company_Name FROM Tmp_Company_Info"
						+" ) as comp ON rela.Tmp_Company_Code=comp.Tmp_Company_Code AND comp.Tmp_Company_Name IS NOT NULL"
						+" INNER JOIN (SELECT "
						+" CONCAT('http://',SUBSTRING(Tmp_Investment_Code,13,40)) AS Tmp_Investment_Code,"
						+" Tmp_Investment_Name FROM Tmp_Investment_Info) AS investment"
						+" ON rela.Tmp_Investment_Code = investment.Tmp_Investment_Code AND investment.Tmp_Investment_Name IS NOT NULL";
									
//				"select  a.Tmp_Investment_Code,a.Tmp_Inves_Record_ID,a.Tmp_Company_Code,"
//						+"a.Tmp_Investment_Recordrela_Money,a.Tmp_Investor_Code"
//						+",b.Tmp_Company_Name"
//						+",c.Tmp_Investment_Name"
//						+" FROM Tmp_Investment_Recordrela a INNER JOIN Tmp_Company_Info b"
//						+" on a.Tmp_Company_Code=b.Tmp_Company_Code and b.Tmp_Company_Name is not null"
//						+" inner join Tmp_Investment_Info c"
//						+" on c.Tmp_Investment_Code=a.Tmp_Investment_Code and c.Tmp_Investment_Name is not null";
			List<Map<String, Object>> tmptradeinveslist=jdbcTemplate.queryForList(sql);
			
			/* ２.２:处理需要导入的机构交易数据 */
			//封装导入数据
			//循环查询到的数据
			String investorCode;
			for (int i=0;i<tmptradeinveslist.size();i++){
					investorCode="";
			    	String opreString="nodo";
			    	BaseTradeInves basetradeinves=new BaseTradeInves();   
        			Map<String, Object> userMap = (Map<String, Object>) tmptradeinveslist.get(i); 
        			//判断投资机构对应it桔子id和公司对应it桔子id和交易对应it桔子id 不允许为空
        			if(userMap.get("Tmp_Investment_Code")!=null 
        				&& userMap.get("Tmp_Inves_Record_ID")!=null
	        				&& userMap.get("Tmp_Company_Code")!=null){
	        			 //2.2.1.:根据对应IT桔子id查询数据库是否存在该公司，交易和机构信息
	        			BaseInvestorInfo baseinvestorinfo=null;
	        			if(userMap.get("Tmp_Investor_Code")!=null){
	        				
	        				investorCode=importinvestmentitdata.changeSrc(userMap.get("Tmp_Investor_Code").toString().trim());
	        				
	        				//投资人
	        				baseinvestorinfo=iimportitdataservice.findBaseInvestorBytmpcodeForIT(investorCode);
	        			}
	        			//机构
	        			baseInvestmentInfo baseinvestmentinfo=iimportitdataservice.findBaseInvestmentBytmpcodeForIT(userMap.get("Tmp_Investment_Code").toString().trim());
	        			if(baseinvestmentinfo==null || "".equals(baseinvestmentinfo)){
	        				 baseinvestmentinfo= iimportitdataservice
	        				 	.findBaseInvestmentBynameForIT(userMap.get("Tmp_Investment_Name").toString().trim());
	        			}
	        			//公司
	        			BaseCompInfo basecompinfo=iimportitdataservice.findBaseCompBytmpcodeForIT(userMap.get("Tmp_Company_Code").toString().trim());
	        			if(basecompinfo==null || "".equals(basecompinfo)){
	        			    Map<String, String> maptmpname=new HashMap<String, String>();
	        			    maptmpname.put("basecompname", userMap.get("Tmp_Company_Name").toString());
	        			    maptmpname.put("basecompfullname", null);
	        			    basecompinfo=iimportitdataservice.findBaseCompBynameForIT(maptmpname);
	        			}
	        			//交易
	        			BaseTradeInfo basetradeinfo=iimportitdataservice.findTradeinfoBytmpcodeForIT(userMap.get("Tmp_Inves_Record_ID").toString().trim());
	        			//2.2.1.1:数据库中存在公司信息，交易和机构信息且交易的当前状态是it桔子创建或修改
	        			if(basecompinfo!=null && !"".equals(basecompinfo) 
	                			&& baseinvestmentinfo!=null && !"".equals(baseinvestmentinfo)
	                			&& basetradeinfo!=null && !"".equals(basetradeinfo)
	                			&&  ("1".equals(basetradeinfo.getBase_trade_estate()) 
	                				|| "4".equals(basetradeinfo.getBase_trade_estate()))){
	                		    			
	        				//2.2.1.1.1:判断数据库中是否存在机构交易的关系		
	        				Map<String, String> tormap=new HashMap<String, String>();
	        				tormap.put("base_trade_code",basetradeinfo.getBase_trade_code());
	        				tormap.put("base_investment_code",baseinvestmentinfo.getBase_investment_code());
	        				basetradeinves=iimportitdataservice.findBaseTradeInveBytmpcodeForIT(tormap);
	        				//2.2.1.1.1.1:若数据库不存在，则添加记录	
	        				if(basetradeinves==null ||"".equals(basetradeinves)){
	        					basetradeinves=new BaseTradeInves();
	        					opreString="add";
	        					//交易id
	        					basetradeinves.setBase_trade_code(basetradeinfo.getBase_trade_code());
	        					//投资人id
	        					if(baseinvestorinfo!=null && !"".equals(baseinvestorinfo)){
	        						//投资人id
	                		    	basetradeinves.setBase_investor_code(baseinvestorinfo.getBase_investor_code());
	        					}else {
									//投资人id
	                		    	basetradeinves.setBase_investor_code(null);
	        					}
	        					//投资机构id
	        					basetradeinves.setBase_investment_code(baseinvestmentinfo.getBase_investment_code());
	        					//金额
	        					if(userMap.get("Tmp_Investment_Recordrela_Money")!=null){
	        						//金额
	        						basetradeinves.setBase_trade_inmoney(userMap.get("Tmp_Investment_Recordrela_Money").toString().trim());
			    				}else {
			    					//金额
	                		    	basetradeinves.setBase_trade_inmoney(null);
			    				}
	        					//2015-11-30 TASK078 RQQ AddStart
	        					//是否领投
	                    		basetradeinves.setBase_trade_collvote("1");
	                    		//是否对赌
	                    		basetradeinves.setBase_trade_ongam("1");
	                    		//分次付款
	                    		basetradeinves.setBase_trade_subpay("1");
	                    		//2015-11-30 TASK078 RQQ AddEnd
	                    		//删除标识
	                    		basetradeinves.setDeleteflag("0");
	                    		//创建者
	                    		basetradeinves.setCreateid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
	                    		//创建时间
	                    		basetradeinves.setCreatetime(CommonUtil.getNowTime_tamp());
	                    		//更新者
	                    		basetradeinves.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
	                    		//更新时间
	                    		basetradeinves.setUpdtime(basetradeinves.getCreatetime());
	        				}else{//2.2.1.1.1.２:若数据库存在记录，判断是否发生变化
	                		    BaseTradeInves tmptradeinves=new BaseTradeInves();
	                		    //投资人id
	            		    	if(baseinvestorinfo!=null && !"".equals(baseinvestorinfo)){
	            		    		//投资人id
	            		    		tmptradeinves.setBase_investor_code(baseinvestorinfo.getBase_investor_code());
	            		    	}else {
	            		    		//投资人id
	            		    		tmptradeinves.setBase_investor_code(null);
	            		    	}
	            		    	//金额
	            		    	if(userMap.get("Tmp_Investment_Recordrela_Money")!=null){
	            		    		//金额
	            		    		tmptradeinves.setBase_trade_inmoney(userMap.get("Tmp_Investment_Recordrela_Money").toString().trim());
	            		    	}else {
	            		    		//金额
	            		    		tmptradeinves.setBase_trade_inmoney(null);
	            		    	}
	            		    	//判断是否发生变化
	                		    if (!((tmptradeinves.getBase_investor_code()!=null
	                		    		&& basetradeinves.getBase_investor_code()!=null
	                		        	&& tmptradeinves.getBase_investor_code().equals(basetradeinves.getBase_investor_code()))
	                			    || (tmptradeinves.getBase_investor_code()==null
	                		            && basetradeinves.getBase_investor_code()==null))
	                		        ||!((tmptradeinves.getBase_trade_inmoney()!=null
	                		        	&& basetradeinves.getBase_trade_inmoney()!=null
	                		        	&& tmptradeinves.getBase_trade_inmoney().equals(basetradeinves.getBase_trade_inmoney()))
	                	             || (tmptradeinves.getBase_trade_inmoney()==null
	                	            	&& basetradeinves.getBase_trade_inmoney()==null))) {
									opreString="upd";
									//投资人id
				                	basetradeinves.setBase_investor_code(tmptradeinves.getBase_investor_code());
				                	//金额
				                	basetradeinves.setBase_trade_inmoney(tmptradeinves.getBase_trade_inmoney());
				                	//更新者
				                	basetradeinves.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
	                            	//更新时间
				                	basetradeinves.setUpdtime(CommonUtil.getNowTime_tamp());
	                		    }
	                		}
	        				//2.2.1.1.1.3:存储数据库，并调用存储过程
	        				if(!"nodo".equals(opreString)){
	        					iimportitdataservice.tranModifyimporttradeinves(basetradeinves, opreString);
	        				}
	        			}
        			}
					}  
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ImportTradeITdata 的importtradeinvedata 机构交易信息导入数据库出现异常",e);
			return;
		}
		  logger.info("ImportTradeITdata 的importtradeinvedata导入机构交易信息结束");
		    System.out.println("importtradeinvedata END:"+System.currentTimeMillis());
	}
	

}