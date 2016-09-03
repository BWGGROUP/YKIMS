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
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.baseinvestmentinvestor.BaseInvestmentInvestor;
import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.baseinvestorlabelinfo.BaseInvestorlabelInfo;
import cn.com.sims.model.syslabelelement.SysLabelelementInfo;
import cn.com.sims.service.code.SerialNumberGeneratorService;
import cn.com.sims.service.importitdata.IImportITdataService;

@Controller
public class ImportInvestorITdata{
    
    	//导入service
    @Resource
    IImportITdataService iimportitdataservice;
	//生成id　service
    @Resource
	SerialNumberGeneratorService serialNumberGeneratorService;
    @Resource
    ImportInvestmentITdata importinvestmentitdata;
    /* log */
    private static final Logger logger = Logger.getLogger(ImportInvestorITdata.class);
    
	
	/**
	 * 导入it桔子抓取的投资人数据
	 * @throws Exception
	 */
	public void importinvestordata()throws Exception {
	    System.out.println("importinvestordata start:"+System.currentTimeMillis());
	    logger.info("importinvestordata 的importinvestordata导入投资人信息开始");
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
			logger.info("importinvestordata 的importinvestordata 成功创建数据连接");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("importinvestordata 的importinvestordata mysql未开启或JDBCHelper.createMysqlTemplate中参数配置不正确!",ex);
			return;
		}
		try {
		 /* ２:处理抓取存储的投资人数据 */
		  /* ２.1:查询抓取存储的投资人数据 */
		//读取数据库数据
			JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate("importtemp1");
			//拼接sql，查询数据
			String sql="select Tmp_Investor_Code, Tmp_Investor_Name,Tmp_Investor_Domain"
						+" FROM Tmp_Investor_Info";
			List<Map<String, Object>> tmpinvestorlist=jdbcTemplate.queryForList(sql);
			
			 /* ２.2:处理需要导入的投资人行业标签 */
			//匹配行业标签　
			importinvestmentitdata.matchlabledbdata("Tmp_Investor_Info","Tmp_Investor_Domain","","Lable-orgindu","投资领域: ");
			/* ２.3:处理需要导入的投资人数据 */
			//封装导入数据
			//循环查询到的数据
			String investorCode;
			for (int i=0;i<tmpinvestorlist.size();i++){
					investorCode="";
			    	String opreString="nodo";
			    	BaseInvestorInfo baseinvestor=new BaseInvestorInfo();   
        			Map<String, Object> userMap = (Map<String, Object>) tmpinvestorlist.get(i); 
        			
        			/**截取投资人路径code 转为原路径格式 http:// */
        			investorCode=importinvestmentitdata.changeSrc(userMap.get("Tmp_Investor_Code").toString().trim());
        			/**截取 end*/
        			//判断id 和name 不允许为空
        			if(investorCode!=null && userMap.get("Tmp_Investor_Name")!=null){
        				
        			    	//2.3.1.:根据对应IT桔子id判断数据库是否存在该投资人信息
        			    baseinvestor=iimportitdataservice.findBaseInvestorBytmpcodeForIT(investorCode);
                			//2.3.1.1.1.1:数据库不存在记录，创建
                		if(baseinvestor==null || "".equals(baseinvestor)){
                		    	baseinvestor=new BaseInvestorInfo();  
                    		opreString="add";
                    						//投资人对应it桔子id 
                    		baseinvestor.setTmp_Investor_Code(investorCode);
        							//投资人名称
                    		baseinvestor.setBase_investor_name(userMap.get("Tmp_Investor_Name").toString().trim());
                    						//生成投资人id
                    		String code=CommonUtil.PREFIX_INVESTOR+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.INVESTOR_TYPE);
                    		baseinvestor.setBase_investor_code(code);
                    						//投资人姓名拼音全拼,投资人姓名拼音首字母
                    		String[] pinYin=CommonUtil.getPinYin(baseinvestor.getBase_investor_name());
								//投资人姓名拼音全拼
                    		baseinvestor.setBase_investor_namep(pinYin[0]);
                        					//投资人姓名拼音首字母
                    		baseinvestor.setBase_investor_namepf(pinYin[1]);
                    						//创建来源
                    		baseinvestor.setBase_investor_stem("1");
        							//当前状态
                    		baseinvestor.setBase_investor_estate("1");
                    						//排它锁
                    		baseinvestor.setBase_datalock_viewtype(1);
        						//PL锁状态
                    		baseinvestor.setBase_datalock_pltype("0");
                    						//删除标识
                    		baseinvestor.setDeleteflag("0");
                						//创建者
                    		baseinvestor.setCreateid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
                						//创建时间
                    		baseinvestor.setCreatetime(CommonUtil.getNowTime_tamp());
                						//更新者
                    		baseinvestor.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
                    						//更新时间
                    		baseinvestor.setUpdtime(baseinvestor.getCreatetime());
                				}
                			//2.3.1.1.1.2:若数据库存在记录
                		else{
                		    if ("1".equals(baseinvestor.getBase_investor_estate()) 
                		    		|| "4".equals(baseinvestor.getBase_investor_estate())) {
								opreString="do";
						  		/*2.3.1.1.1.2.1:判断数据是否发生变化，若发生变化更新数据
						  		【当前状态：it桔子修改】，【PL锁状态】*/
                		    if(!userMap.get("Tmp_Investor_Name").toString().trim().equals(baseinvestor.getBase_investor_name())){
                					opreString="upd";
									//投资人名称
                	              baseinvestor.setBase_investor_name(userMap.get("Tmp_Investor_Name").toString().trim());
                    							//投资人姓名拼音全拼,投资人姓名拼音首字母
                            	String[] pinYin=CommonUtil.getPinYin(baseinvestor.getBase_investor_name());
                            						//投资人姓名拼音全拼
                            	baseinvestor.setBase_investor_namep(pinYin[0]);
                           						//投资人姓名拼音首字母
                            	baseinvestor.setBase_investor_namepf(pinYin[1]);
                            						//当前状态
                            	baseinvestor.setBase_investor_estate("4");
                    			//PL锁状态
                            	baseinvestor.setBase_datalock_pltype("0");
                           						//更新者
                            	baseinvestor.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
                            						//更新时间
                            	baseinvestor.setUpdtime(CommonUtil.getNowTime_tamp());
                		    	}
                		}
                		}
                		//2.3.1.２:判断投资人信息是否可进行操作，可操作则处理行业标签
                		if(!"nodo".equals(opreString)){
        					//交易行业标签（存储数据库使用）
        					List<BaseInvestorlabelInfo> baseinvestorlabellist=new ArrayList<BaseInvestorlabelInfo>();
        					//抓取数据库的投资人行业标签转为数组
        					if(userMap.get("Tmp_Investor_Domain")!=null){
        					String[] induStrArray = userMap.get("Tmp_Investor_Domain").toString().split(" ");
        					//处理循环放置截取数据
            				for (int j = 0; j < induStrArray.length; j++) {
            				    BaseInvestorlabelInfo baseinvestorlabelinfo= new BaseInvestorlabelInfo();
            			 			String labelelementname="";
//									if(induStrArray[j].indexOf("投资领域:")>=0){
//										labelelementname=induStrArray[j].substring("投资领域:".length()).trim();
//										}
//									else{
//											labelelementname=induStrArray[j].trim();
//										}
            			 			if(induStrArray[j]!=null&& !"".equals(induStrArray[j].trim())){
            			 				labelelementname=induStrArray[j].trim();
            			 			}
            			 			
									if(labelelementname!=null && !"".equals(labelelementname)){
										//查询标签是否存在的参数map
            							Map<String, String> indumap=new HashMap<String, String>();
            									//标签所属code
            							indumap.put("labelcode", CommonUtil.findNoteTxtOfXML("Lable-orgindu"));
            							indumap.put("labelelementname",labelelementname);
            									//查询标签是否存在
            							SysLabelelementInfo syslabelelementinfo=iimportitdataservice.querylabelelementbyname(indumap);
            									//若存在
									if(syslabelelementinfo!=null && !"".equals(syslabelelementinfo)){
											//投资人id
									    	baseinvestorlabelinfo.setBase_investor_code(baseinvestor.getBase_investor_code());
											//标签元素code
									    	baseinvestorlabelinfo.setSys_labelelement_code(syslabelelementinfo.getSys_labelelement_code());
											//标签code
									    	baseinvestorlabelinfo.setSys_label_code(syslabelelementinfo.getSys_label_code());
											//删除标识
									    	baseinvestorlabelinfo.setDeleteflag("0");
											//创建者
									    	baseinvestorlabelinfo.setCreateid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
											//创建时间
									    	baseinvestorlabelinfo.setCreatetime(CommonUtil.getNowTime_tamp());
											//更新者
									    	baseinvestorlabelinfo.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
											//更新时间
									    	baseinvestorlabelinfo.setUpdtime(baseinvestorlabelinfo.getCreatetime());
											//放置list
									    	baseinvestorlabellist.add(baseinvestorlabelinfo);
											}
            								}
            							}
        							}
									//存储数据库，并调用存储过程
            						iimportitdataservice.tranModifyimportinvestorinfo(baseinvestor, baseinvestorlabellist, opreString,baseinvestor.getBase_investor_code());
            						}
        					}
					}  
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("importinvestordata 的importinvestordata 投资人信息导入数据库出现异常",e);
			return;
		}
		  logger.info("importinvestordata 的importinvestordata导入投资人信息结束");
		    System.out.println("importinvestordata END:"+System.currentTimeMillis());
	}
	
	
	
	/**
	 * 导入it桔子抓取的投资人机构关系数据
	 * @throws Exception
	 */
	public void importinvestmentinvestordata()throws Exception {
	    System.out.println("importinvestmentinvestordata start:"+System.currentTimeMillis());
	    logger.info("importinvestordata 的importinvestmentinvestordata导入投资人机构关系信息开始");
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
			logger.info("importinvestordata 的importinvestmentinvestordata 成功创建数据连接");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("importinvestordata 的importinvestmentinvestordata mysql未开启或JDBCHelper.createMysqlTemplate中参数配置不正确!",ex);
			return;
		}
		try {
		 /* ２:处理抓取存储的投资人机构关系数据 */
		  /* ２.1:查询抓取存储的投资人机构关系数据 */
		//读取数据库数据
			JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate("importtemp1");
			//拼接sql，查询数据
			String sql="select Tmp_Investment_Code, Tmp_Investor_Code,Tmp_Investor_Position,Tmp_Inment_Istor_Active"
						+" FROM Tmp_Investment_Investor";
			List<Map<String, Object>> tmpinvestmentinvestorlist=jdbcTemplate.queryForList(sql);
			
			/* ２.２:处理需要导入的投资人机构关系数据 */
			//封装导入数据
			//循环查询到的数据
			String investmentCode,investorCode;
			for (int i=0;i<tmpinvestmentinvestorlist.size();i++){
					investmentCode="";
					investorCode="";
			    	String opreString="nodo";
			    	BaseInvestmentInvestor investorinfo=new BaseInvestmentInvestor();   
        			Map<String, Object> userMap = (Map<String, Object>) tmpinvestmentinvestorlist.get(i);
        			/** 截取路径code  */
        			investmentCode=importinvestmentitdata.changeSrc(userMap.get("Tmp_Investment_Code").toString().trim());
        			investorCode=importinvestmentitdata.changeSrc(userMap.get("Tmp_Investor_Code").toString().trim());
        			/** 截取 end*/
        			//判断投资机构对应it桔子id和投资人对应it桔子id 不允许为空
        			if(userMap.get("Tmp_Investment_Code")!=null 
        					&& userMap.get("Tmp_Investor_Code")!=null){
        			
        			    	//2.2.1.:根据对应IT桔子id查询数据库是否存在该投资人和机构信息
        			BaseInvestorInfo baseinvestorinfo=iimportitdataservice.findBaseInvestorBytmpcodeForIT(investorCode);
        			baseInvestmentInfo baseinvestmentinfo=iimportitdataservice.findBaseInvestmentBytmpcodeForIT(investmentCode);
        				//2.2.1.1:数据库中存在投资人信息和机构信息且投资人的当前状态是it桔子创建或修改
                		if(baseinvestorinfo!=null && !"".equals(baseinvestorinfo) 
                			&& baseinvestmentinfo!=null && !"".equals(baseinvestmentinfo)
                			&&  ("1".equals(baseinvestorinfo.getBase_investor_estate()) 
                				|| "4".equals(baseinvestorinfo.getBase_investor_estate()))){
                		    			
                		    			//2.2.1.1.1:判断数据库中是否存在投资人和机构的关系		
                		    Map<String, String> tormap=new HashMap<String, String>();
                		    tormap.put("base_investor_code",baseinvestorinfo.getBase_investor_code());
                		    tormap.put("base_investment_code",baseinvestmentinfo.getBase_investment_code());
                		    investorinfo=iimportitdataservice.queryInvestmentInvestorbycodeforit(tormap);
                		    			//2.2.1.1.1.1:若数据库不存在，则添加记录	
                		    if(investorinfo==null ||"".equals(investorinfo)){
                				investorinfo=new BaseInvestmentInvestor();
                		    	opreString="add";
                    						//投资人id
                    		investorinfo.setBase_investor_code(baseinvestorinfo.getBase_investor_code());
                    						//投资机构id
                    		investorinfo.setBase_investment_code(baseinvestmentinfo.getBase_investment_code());
//                    		if(userMap.get("Tmp_Investor_Position")!=null){
//                    		   if(userMap.get("Tmp_Investor_Position").toString().indexOf("(已离职)")>=0){
//	    								// 在职状态
//                    			investorinfo.setBase_investor_state("1");
//                        	   					//职位名称
//                        		investorinfo.setBase_investor_posiname(
//                        			userMap.get("Tmp_Investor_Position").toString().substring(0,
//                        				userMap.get("Tmp_Investor_Position").toString().indexOf("(已离职)")
//                        								).trim()
//                        							);
//
//                    		    					}
//                    		   else {
//                    		       					// 在职状态
//                    		   investorinfo.setBase_investor_state("0");
//                                					//职位名称
//                             investorinfo.setBase_investor_posiname(userMap.get("Tmp_Investor_Position").toString().trim());
//                    		   					}
//                    		}else{
//                    		    					// 在职状态
//                     		   investorinfo.setBase_investor_state("0");
//                    						}
                    		
                    		// 在职状态
                  		    investorinfo.setBase_investor_state(userMap.get("Tmp_Inment_Istor_Active").toString().trim());
                    		//职位名称
                    		investorinfo.setBase_investor_posiname(userMap.get("Tmp_Investor_Position").toString().trim());
                    						//删除标识
                    		investorinfo.setDeleteflag("0");
                						//创建者
                    		investorinfo.setCreateid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
                						//创建时间
                    		investorinfo.setCreatetime(CommonUtil.getNowTime_tamp());
                						//更新者
                    		investorinfo.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
                    						//更新时间
                    		investorinfo.setUpdtime(investorinfo.getCreatetime());
                				}
                			//2.2.1.1.1.２:若数据库存在记录，判断是否发生变化
                		else{
                		    BaseInvestmentInvestor tmpinvestorinfo=new BaseInvestmentInvestor();
                		    // 在职状态
                		    tmpinvestorinfo.setBase_investor_state(userMap.get("Tmp_Inment_Istor_Active").toString().trim());
                		    //职位名称
                		    tmpinvestorinfo.setBase_investor_posiname(
									userMap.get("Tmp_Investor_Position").toString().trim());
//                		    if(userMap.get("Tmp_Investor_Position")!=null){
//                				if(userMap.get("Tmp_Investor_Position").toString().indexOf("(已离职)")>=0){
//                			    			// 在职状态
//                				 tmpinvestorinfo.setBase_investor_state("1");
//								//职位名称
//                				 tmpinvestorinfo.setBase_investor_posiname(
//                             	 userMap.get("Tmp_Investor_Position").toString().substring(0,
//                             				userMap.get("Tmp_Investor_Position").toString().indexOf("(已离职)")
//                             			).trim());
//	    						}else {
//	       								// 在职状态
//	    								tmpinvestorinfo.setBase_investor_state("0");
//                						//职位名称
//	    								tmpinvestorinfo.setBase_investor_posiname(
//	    										userMap.get("Tmp_Investor_Position").toString().trim());
//                    	   		}
//                		    }else{
//                    	    	// 在职状态
//                		    	tmpinvestorinfo.setBase_investor_state("0");
//                    		}
                		    			//判断是否发生变化
                		    if (!((tmpinvestorinfo.getBase_investor_state()!=null
                			    	&&investorinfo.getBase_investor_state()!=null
                		          &&tmpinvestorinfo.getBase_investor_state().equals(investorinfo.getBase_investor_state())
                			    				) 
	                			 ||(tmpinvestorinfo.getBase_investor_state()==null
	                	    	    &&investorinfo.getBase_investor_state()==null
	                	     	                		) ) ||
    			    				!((tmpinvestorinfo.getBase_investor_posiname()!=null
    	                			 &&investorinfo.getBase_investor_posiname()!=null
    	                		    &&tmpinvestorinfo.getBase_investor_posiname().equals(investorinfo.getBase_investor_posiname())
    	                			    				) 
    	                			 ||(tmpinvestorinfo.getBase_investor_posiname()==null
    	                	    	    &&investorinfo.getBase_investor_posiname()==null
    	                	     	                		) )
                			    			) {
								opreString="upd";
								// 在职状态
								investorinfo.setBase_investor_state(tmpinvestorinfo.getBase_investor_state());
					   			//职位名称
								investorinfo.setBase_investor_posiname(tmpinvestorinfo.getBase_investor_posiname());
                           					//更新者
								investorinfo.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
                            					//更新时间
								investorinfo.setUpdtime(CommonUtil.getNowTime_tamp());
                		    	}
                		}
                		    				//2.2.1.1.1.3:存储数据库，并调用存储过程
                		    if(!"nodo".equals(opreString)){
            						iimportitdataservice.tranModifyimportInvestmentInvestor(investorinfo, opreString);
            				}
                		}
        			}
				}  
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("importinvestordata 的importinvestmentinvestordata 投资人机构关系信息导入数据库出现异常",e);
			return;
		}
		  logger.info("importinvestordata 的importinvestmentinvestordata导入投资人机构关系信息结束");
		    System.out.println("importinvestmentinvestordata END:"+System.currentTimeMillis());
	}
	

}