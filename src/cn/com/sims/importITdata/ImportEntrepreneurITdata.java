package cn.com.sims.importITdata;

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
import cn.com.sims.model.basecompentrepreneur.BaseCompEntrepreneur;
import cn.com.sims.model.basecompinfo.BaseCompInfo;
import cn.com.sims.model.baseentrepreneurinfo.BaseEntrepreneurInfo;
import cn.com.sims.service.code.SerialNumberGeneratorService;
import cn.com.sims.service.importitdata.IImportITdataService;

@Controller
public class ImportEntrepreneurITdata{
    	//导入service
    @Resource
    IImportITdataService iimportitdataservice;
	//生成id　service
    @Resource
	SerialNumberGeneratorService serialNumberGeneratorService;
    @Resource
    ImportInvestmentITdata importinvestmentitdata;
	
    
    /* log */
    private static final Logger logger = Logger.getLogger(ImportEntrepreneurITdata.class);
  
	/**
	 * 导入it桔子抓取的公司联系人（创业者）数据
	 * @throws Exception
	 */
	public void importentrepreneurdata()throws Exception {
	    System.out.println("importentrepreneurdata start:"+System.currentTimeMillis());
	    logger.info("ImportEntrepreneurITdata 的importentrepreneurdata导入公司联系人（创业者信息开始");
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
			logger.info("ImportEntrepreneurITdata 的importentrepreneurdata 成功创建数据连接");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("ImportEntrepreneurITdata 的importentrepreneurdata mysql未开启或JDBCHelper.createMysqlTemplate中参数配置不正确!",ex);
			return;
		}
		try {
		 /* ２:处理抓取存储的公司联系人（创业者)数据 */
		  /* ２.1:查询抓取存储的公司联系人（创业者)数据 */
		//读取数据库数据
			JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate("importtemp1");
			//拼接sql，查询数据
			String sql="select Tmp_Entrepreneur_Code, Tmp_Entrepreneur_Name,Tmp_Entrepreneur_Blog"
						+" FROM Tmp_Entrepreneur_Info";
			List<Map<String, Object>> tmpentrepreneurlist=jdbcTemplate.queryForList(sql);
			
			/* ２.2:处理需要导入的公司联系人（创业者）数据 */
			//封装导入数据
			//循环查询到的数据
			
			String entrepreneurCode;//创业者code
			for (int i=0;i<tmpentrepreneurlist.size();i++){
					entrepreneurCode="";
			    	String opreString="nodo";
			    	BaseEntrepreneurInfo baseentrepreneurinfo=new BaseEntrepreneurInfo();   
        			Map<String, Object> userMap = (Map<String, Object>) tmpentrepreneurlist.get(i);
        			entrepreneurCode=importinvestmentitdata.changeSrc(userMap.get("Tmp_Entrepreneur_Code").toString().trim());
        			//判断id 和name 不允许为空
        			if(entrepreneurCode!=null && userMap.get("Tmp_Entrepreneur_Name")!=null){
        				
        			    	//2.2.1.:根据对应IT桔子id判断数据库是否存在该公司联系人（创业者)信息
        			    baseentrepreneurinfo=iimportitdataservice.findBaseEntrepreneurBytmpcodeForIT(entrepreneurCode);
                			//2.2.1.1:数据库不存在记录，创建
                		if(baseentrepreneurinfo==null || "".equals(baseentrepreneurinfo)){
                		    baseentrepreneurinfo=new BaseEntrepreneurInfo();  
                    		opreString="add";
                    						//公司联系人（创业者)对应it桔子id 
                    		baseentrepreneurinfo.setTmp_Entrepreneur_Code(entrepreneurCode);
        							//公司联系人（创业者)姓名
                    		baseentrepreneurinfo.setBase_entrepreneur_name(userMap.get("Tmp_Entrepreneur_Name").toString().trim());
                    						//生成公司联系人（创业者)id
                    		String code=CommonUtil.PREFIX_ENTREPRENEUR+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.ENTREPRENEUR_TYPE);
                    		baseentrepreneurinfo.setBase_entrepreneur_code(code);
                    						//公司联系人（创业者)微博
                    		if(userMap.get("Tmp_Entrepreneur_Blog")==null){
                    			baseentrepreneurinfo.setBase_entrepreneur_weibo(null);
                    		}else{
                    			baseentrepreneurinfo.setBase_entrepreneur_weibo(userMap.get("Tmp_Entrepreneur_Blog").toString());
                    		}
                    						//公司联系人（创业者)姓名拼音全拼,公司联系人（创业者)姓名拼音首字母
                    		String[] pinYin=CommonUtil.getPinYin(baseentrepreneurinfo.getBase_entrepreneur_name());
                    						//公司联系人（创业者)姓名拼音全拼
                    		baseentrepreneurinfo.setBase_entrepreneur_namep(pinYin[0]);
                    						//公司联系人（创业者)姓名拼音首字母
                    		baseentrepreneurinfo.setBase_entrepreneur_namepf(pinYin[1]);
                    						//创建来源
                    		baseentrepreneurinfo.setBase_entrepreneur_stem("1");
        							//当前状态
                    		baseentrepreneurinfo.setBase_entrepreneur_estate("1");
                    						//删除标识
                    		baseentrepreneurinfo.setDeleteflag("0");
                						//创建者
                    		baseentrepreneurinfo.setCreateid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
                						//创建时间
                    		baseentrepreneurinfo.setCreatetime(CommonUtil.getNowTime_tamp());
                						//更新者
                    		baseentrepreneurinfo.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
                    						//更新时间
                    		baseentrepreneurinfo.setUpdtime(baseentrepreneurinfo.getCreatetime());
                				}
                			//2.2.1.2:若数据库存在记录
                		else{
                		    if ("1".equals(baseentrepreneurinfo.getBase_entrepreneur_estate()) 
                		    		|| "4".equals(baseentrepreneurinfo.getBase_entrepreneur_estate())) {
						  		/*2.2.1.2.1:判断数据是否发生变化，若发生变化更新【创业者姓名】,【创业者微博】
						  		【当前状态：it桔子修改】，【PL锁状态】*/
                		    if(!userMap.get("Tmp_Entrepreneur_Name").toString().trim().equals(baseentrepreneurinfo.getBase_entrepreneur_name())
                			    			||
                			    !((userMap.get("Tmp_Entrepreneur_Blog")!=null 
                			    && baseentrepreneurinfo.getBase_entrepreneur_weibo()!=null 
                				   		&&
            			    	userMap.get("Tmp_Entrepreneur_Blog").toString().trim().equals(baseentrepreneurinfo.getBase_entrepreneur_weibo()))
                			    			||
                			    (userMap.get("Tmp_Entrepreneur_Blog")==null 
                	            && baseentrepreneurinfo.getBase_entrepreneur_weibo()==null))
                			    			){
                					opreString="upd";
									//公司联系人（创业者)名称
                	             baseentrepreneurinfo.setBase_entrepreneur_name(userMap.get("Tmp_Entrepreneur_Name").toString().trim());
                	             					//公司联系人（创业者)姓名拼音全拼,公司联系人（创业者)姓名拼音首字母
                         		String[] pinYin=CommonUtil.getPinYin(baseentrepreneurinfo.getBase_entrepreneur_name());
                         						//公司联系人（创业者)姓名拼音全拼
                         		baseentrepreneurinfo.setBase_entrepreneur_namep(pinYin[0]);
                         						//公司联系人（创业者)姓名拼音首字母
                         		baseentrepreneurinfo.setBase_entrepreneur_namepf(pinYin[1]);
                         						//公司联系人（创业者)微博
                            	if(userMap.get("Tmp_Entrepreneur_Blog")==null){
                            		baseentrepreneurinfo.setBase_entrepreneur_weibo(null);
                            						}
                            	else{
                            		baseentrepreneurinfo.setBase_entrepreneur_weibo(userMap.get("Tmp_Entrepreneur_Blog").toString());
                            						}
                            						//当前状态
                         		baseentrepreneurinfo.setBase_entrepreneur_estate("4");
                           						//更新者
                            	baseentrepreneurinfo.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
                            						//更新时间
                            	baseentrepreneurinfo.setUpdtime(CommonUtil.getNowTime_tamp());
                		    	}
                		}
                		}
                		//2.2.1.3:判断公司信息是否可进行操作，可操作则处理行业标签
                		if(!"nodo".equals(opreString)){
                		    						//存储数据库
            						iimportitdataservice.tranModifyimportentrepreneurinfo(baseentrepreneurinfo,opreString);
            						}
        					}
					}  
			
			System.out.println("importentrepreneurdata end:"+System.currentTimeMillis());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ImportEntrepreneurITdata 的importentrepreneurdata 公司联系人（创业者)信息导入数据库出现异常",e);
			return;
		}
		  logger.info("ImportEntrepreneurITdata 的importentrepreneurdata导入公司联系人（创业者)信息结束");
	}
	
	
	
	/**
	 * 导入it桔子抓取的公司联系人（创业者)公司关系数据
	 * @throws Exception
	 */
	public void importcompentrepreneurdata()throws Exception {
	    System.out.println("importcompentrepreneurdata start:"+System.currentTimeMillis());
	    logger.info("ImportEntrepreneurITdata 的importcompentrepreneurdata导入公司联系人（创业者）公司关系信息开始");
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
			logger.info("ImportEntrepreneurITdata 的importcompentrepreneurdata 成功创建数据连接");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("ImportEntrepreneurITdata 的importcompentrepreneurdata mysql未开启或JDBCHelper.createMysqlTemplate中参数配置不正确!",ex);
			return;
		}
		try {
		 /* ２:处理抓取存储的公司联系人（创业者）公司关系数据 */
		  /* ２.1:查询抓取存储的公司联系人（创业者）公司关系数据 */
		//读取数据库数据
			JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate("importtemp1");
			//拼接sql，查询数据
			String sql="select Tmp_Company_Code, Tmp_Entrepreneur_Code,Tmp_Compterm_Position"
						+" FROM Tmp_Compterm_info";
			List<Map<String, Object>> tmpcompentrepreneurlist=jdbcTemplate.queryForList(sql);
			
			/* ２.２:处理需要导入的公司联系人（创业者）公司关系数据 */
			//封装导入数据
			//循环查询到的数据
			String companyCode,entrepreneurCode;
			for (int i=0;i<tmpcompentrepreneurlist.size();i++){
					companyCode="";
					entrepreneurCode="";
			    	String opreString="nodo";
			    	BaseCompEntrepreneur basecompentrepreneur=new BaseCompEntrepreneur();   
        			Map<String, Object> userMap = (Map<String, Object>) tmpcompentrepreneurlist.get(i);
        			/**转换新抓取code为原http://格式 */
        			companyCode=importinvestmentitdata.changeSrc(userMap.get("Tmp_Company_Code").toString().trim());
        			entrepreneurCode=importinvestmentitdata.changeSrc(userMap.get("Tmp_Entrepreneur_Code").toString().trim());
        			
        			//判断投资公司对应it桔子id和公司联系人（创业者）对应it桔子id 不允许为空
        			if(companyCode!=null && entrepreneurCode!=null){
        			
        			    	//2.2.1.:根据对应IT桔子id查询数据库是否存在该公司联系人（创业者）和公司信息
        			    BaseEntrepreneurInfo baseentrepreneurinfo=iimportitdataservice.findBaseEntrepreneurBytmpcodeForIT(entrepreneurCode);
        			    BaseCompInfo basecompinfo=iimportitdataservice.findBaseCompBytmpcodeForIT(companyCode);
        				//2.2.1.1:数据库中存在公司联系人（创业者）信息和公司信息且公司联系人（创业者）的当前状态是it桔子创建或修改
                		if(baseentrepreneurinfo!=null && !"".equals(baseentrepreneurinfo) 
                			&& basecompinfo!=null && !"".equals(basecompinfo)
                			&&  ("1".equals(baseentrepreneurinfo.getBase_entrepreneur_estate()) 
                				|| "4".equals(baseentrepreneurinfo.getBase_entrepreneur_estate()))){
                		    			
                		    			//2.2.1.1.1:判断数据库中是否存在公司联系人（创业者）和公司的关系		
                		    Map<String, String> tormap=new HashMap<String, String>();
                		    tormap.put("base_comp_code",basecompinfo.getBase_comp_code());
                		    tormap.put("base_entrepreneur_code",baseentrepreneurinfo.getBase_entrepreneur_code());
                		    basecompentrepreneur=iimportitdataservice.queryCompEntrepreneurbycodeforit(tormap);
                		    			//2.2.1.1.1.1:若数据库不存在，则添加记录	
                		    if(basecompentrepreneur==null ||"".equals(basecompentrepreneur)){
                				basecompentrepreneur=new BaseCompEntrepreneur();
                		    	opreString="add";
                    						//公司联系人（创业者)id
                		    	basecompentrepreneur.setBase_entrepreneur_code(baseentrepreneurinfo.getBase_entrepreneur_code());
                    						//公司id
                		    	basecompentrepreneur.setBase_comp_code(basecompinfo.getBase_comp_code());
                    		if(userMap.get("Tmp_Compterm_Position")!=null){
                    		   if(userMap.get("Tmp_Compterm_Position").toString().indexOf("(已离职)")>=0){
	    								// 在职状态
                    		   basecompentrepreneur.setBase_entrepreneur_state("1");
                        	   					//职位名称
                    		   basecompentrepreneur.setBase_entrepreneur_posiname(
                        			userMap.get("Tmp_Compterm_Position").toString().substring(0,
                        				userMap.get("Tmp_Compterm_Position").toString().indexOf("(已离职)")
                        								).trim()
                        							);

                    		    					}
                    		   else {
                    		       					// 在职状态
                    		   basecompentrepreneur.setBase_entrepreneur_state("0");
                                					//职位名称
                    		   basecompentrepreneur.setBase_entrepreneur_posiname(
                             userMap.get("Tmp_Compterm_Position").toString().trim());
                    		   					}
                    						}
                    		else{
                    		    					// 在职状态
                    		    basecompentrepreneur.setBase_entrepreneur_state("0");
                    						}
                    						//删除标识
                    		basecompentrepreneur.setDeleteflag("0");
                						//创建者
                    		basecompentrepreneur.setCreateid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
                						//创建时间
                    		basecompentrepreneur.setCreatetime(CommonUtil.getNowTime_tamp());
                						//更新者
                    		basecompentrepreneur.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
                    						//更新时间
                    		basecompentrepreneur.setUpdtime(basecompentrepreneur.getCreatetime());
                				}
                			//2.2.1.1.1.２:若数据库存在记录，判断是否发生变化
                		else{
                		    BaseCompEntrepreneur tmpcompentrepreneur=new BaseCompEntrepreneur();
                		    if(userMap.get("Tmp_Compterm_Position")!=null){
                				if(userMap.get("Tmp_Compterm_Position").toString().indexOf("(已离职)")>=0){
                			    			// 在职状态
                				    tmpcompentrepreneur.setBase_entrepreneur_state("1");
								//职位名称
                				    tmpcompentrepreneur.setBase_entrepreneur_posiname(
                             	userMap.get("Tmp_Compterm_Position").toString().substring(0,
                             			userMap.get("Tmp_Compterm_Position").toString().indexOf("(已离职)")
                             			).trim());
	    							}
                		else {
	       							// 在职状态
                		    tmpcompentrepreneur.setBase_entrepreneur_state("0");
                						//职位名称
                		    tmpcompentrepreneur.setBase_entrepreneur_posiname(
                   		userMap.get("Tmp_Compterm_Position").toString().trim());
                    	   					}
                    					}
                    	else{
                    	    					// 在职状态
                    	tmpcompentrepreneur.setBase_entrepreneur_state("0");
                    					}
                		    			//判断是否发生变化
                		    if (!((tmpcompentrepreneur.getBase_entrepreneur_state()!=null
                			    	&&basecompentrepreneur.getBase_entrepreneur_state()!=null
                		          &&tmpcompentrepreneur.getBase_entrepreneur_state().equals(basecompentrepreneur.getBase_entrepreneur_state())
                			    				)
                			    				||
    			    				(tmpcompentrepreneur.getBase_entrepreneur_posiname()==null
    	                			 &&basecompentrepreneur.getBase_entrepreneur_state()==null))
                			    				||
                			    	!((tmpcompentrepreneur.getBase_entrepreneur_posiname()!=null
    	                			 &&basecompentrepreneur.getBase_entrepreneur_posiname()!=null)
    	                		    &&tmpcompentrepreneur.getBase_entrepreneur_posiname().equals(basecompentrepreneur.getBase_entrepreneur_posiname())
    	                		    				||
    	                		    (tmpcompentrepreneur.getBase_entrepreneur_posiname()==null
    	                		    &&basecompentrepreneur.getBase_entrepreneur_posiname()==null))
                			    			) {
								opreString="upd";
								// 在职状态
								basecompentrepreneur.setBase_entrepreneur_state(tmpcompentrepreneur.getBase_entrepreneur_state());
					   			//职位名称
								basecompentrepreneur.setBase_entrepreneur_posiname(tmpcompentrepreneur.getBase_entrepreneur_posiname());
                           					//更新者
								basecompentrepreneur.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
                            					//更新时间
								basecompentrepreneur.setUpdtime(CommonUtil.getNowTime_tamp());
                		    	}
                		}
                		    				//2.2.1.1.1.3:存储数据库，并调用存储过程
                		    if(!"nodo".equals(opreString)){
            						iimportitdataservice.tranModifyimportCompEntrepreneur(basecompentrepreneur, opreString,basecompentrepreneur.getBase_comp_code());
            						}
                					}
        					}
					}  
			    System.out.println("importcompentrepreneurdata end:"+System.currentTimeMillis());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ImportEntrepreneurITdata 的importcompentrepreneurdata 公司联系人（创业者）公司关系信息导入数据库出现异常",e);
			return;
		}
		  logger.info("ImportEntrepreneurITdata 的importcompentrepreneurdata导入公司联系人（创业者）公司关系信息结束");
	}
	


}