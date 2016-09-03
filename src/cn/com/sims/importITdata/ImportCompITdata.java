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
import cn.com.sims.model.basecomplabelinfo.BaseComplabelInfo;
import cn.com.sims.model.syslabelelement.SysLabelelementInfo;
import cn.com.sims.service.code.SerialNumberGeneratorService;
import cn.com.sims.service.importitdata.IImportITdataService;

@Controller
public class ImportCompITdata{
    
    	//导入service
    @Resource
    IImportITdataService iimportitdataservice;
	//生成id　service
    @Resource
	SerialNumberGeneratorService serialNumberGeneratorService;
    @Resource
    ImportInvestmentITdata importinvestmentitdata;
    /* log */
    private static final Logger logger = Logger.getLogger(ImportCompITdata.class);
 
	/**
	 * 导入it桔子抓取的公司数据
	 * @throws Exception
	 */
	public void importcompdata()throws Exception {
	    System.out.println("importcompdata start:"+System.currentTimeMillis());
	    logger.info("ImportCompITdata 的importcompdata 导入公司信息开始");
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
			logger.info("ImportCompITdata 的importcompdata 成功创建数据连接");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("ImportCompITdata 的importcompdata mysql未开启或JDBCHelper.createMysqlTemplate中参数配置不正确!",ex);
			return;
		}
		try {
		 /* ２:处理抓取存储的公司数据 */
		  /* ２.1:查询抓取存储的公司数据 */
		//读取数据库数据
			JdbcTemplate jdbcTemplate = JDBCHelper.getJdbcTemplate("importtemp1");
			//拼接sql，查询数据
			String sql="select Tmp_Company_Code, Tmp_Company_Name,Tmp_Company_Comp,Tmp_Company_Fundstatus,Tmp_Company_Industry"
						+" FROM Tmp_Company_Info";
			List<Map<String, Object>> tmpcomplist=jdbcTemplate.queryForList(sql);
			
			 /* ２.2:处理需要导入的公司标签 */
			//匹配行业标签　
			importinvestmentitdata.matchlabledbdata("Tmp_Company_Info","Tmp_Company_Industry","","Lable-comindu","行业:");
			//匹配轮次标签　
			importinvestmentitdata.matchlabledbdata("Tmp_Company_Info","Tmp_Company_Fundstatus","","Lable-comstage","");
			/* ２.3:处理需要导入的公司数据 */
			//封装导入数据
			//循环查询到的数据
			String fullname="";
			String labelelementname="";
			String companyCode;
			for (int i=0;i<tmpcomplist.size();i++){
					companyCode="";
			    	String opreString="nodo";
			    	BaseCompInfo basecompinfo=new BaseCompInfo();   
        			Map<String, Object> userMap = (Map<String, Object>) tmpcomplist.get(i);
        			companyCode=importinvestmentitdata.changeSrc(userMap.get("Tmp_Company_Code").toString().trim());
        			//公司全称
            		fullname=userMap.get("Tmp_Company_Comp").toString().trim();
            		if(fullname.indexOf("公司全称")>=0){
            			fullname=fullname.substring(fullname.indexOf("公司全称")+"公司全称:".length(),fullname.length());
            		}
        			
        			
        			//判断id 和name 不允许为空
        			if(companyCode!=null && userMap.get("Tmp_Company_Name")!=null){
        			    Map<String, String> maptmpname=new HashMap<String, String>();
        			    maptmpname.put("basecompname", userMap.get("Tmp_Company_Name").toString().trim());
        			    if(fullname!=null){
        					maptmpname.put("basecompfullname", fullname);
        			    }else {
        			    	maptmpname.put("basecompfullname", null);
        			    }
        			    //2.3.1:判断公司简称是否易凯已创建
        			    BaseCompInfo nameinfo=iimportitdataservice.findBaseCompBynameForIT(maptmpname);
	        			//若2.3.1.1:若公司不存在易凯创建记录
	        			if(nameinfo==null || "".equals(nameinfo)){
        			    
            			    //2.3.1.1.1:根据对应IT桔子id判断数据库是否存在该公司信息
        			    basecompinfo=iimportitdataservice.findBaseCompBytmpcodeForIT(companyCode);
                			//2.3.1.1.1.1:数据库不存在记录，创建
                		if(basecompinfo==null || "".equals(basecompinfo)){
                		    basecompinfo=new BaseCompInfo();  
                    		opreString="add";
                    		//公司对应it桔子id 
                    		basecompinfo.setTmp_Company_Code(companyCode);
                    		//公司简称
                    		basecompinfo.setBase_comp_name(userMap.get("Tmp_Company_Name").toString().trim());
                    		if(fullname!=null){
	                    		//公司全称
	                    		basecompinfo.setBase_comp_fullname(fullname);
                    		}else {
                    			basecompinfo.setBase_comp_fullname(null);
                    		}
                    		//生成公司id
                    		String code=CommonUtil.PREFIX_COMP+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.COMPANY_TYPE);
                    		basecompinfo.setBase_comp_code(code);
                    		if(userMap.get("Tmp_Company_Fundstatus")!=null){
                    			//公司阶段
                                //查询标签是否存在的参数map
                				Map<String, String> indumap=new HashMap<String, String>();
                				//标签所属code
                				indumap.put("labelcode", CommonUtil.findNoteTxtOfXML("Lable-comstage"));
                				labelelementname=userMap.get("Tmp_Company_Fundstatus").toString().trim();
                				if(labelelementname.indexOf("(")>=0
                		    				&&labelelementname.indexOf(")")>labelelementname.indexOf("(")){
                		    			if(labelelementname.indexOf("获投状态")>=0){
                		    				labelelementname=labelelementname.substring(labelelementname.indexOf("获投状态")+"获投状态:".length(),labelelementname.lastIndexOf(")"));
                		    			}else{
                		    				labelelementname=labelelementname.substring(labelelementname.indexOf("(")+1,labelelementname.lastIndexOf(")"));
                		    			}
                		    	}
                				indumap.put("labelelementname",labelelementname);
                				//查询标签是否存在
                				SysLabelelementInfo syslabelelementinfo=iimportitdataservice.querylabelelementbyname(indumap);
								//若存在
                				if(syslabelelementinfo!=null && !"".equals(syslabelelementinfo)){
                					basecompinfo.setBase_comp_stage(syslabelelementinfo.getSys_labelelement_code());
                				}
                    		}
                    		//公司简称拼音全拼,公司简称拼音首字母
                    		String[] pinYin=CommonUtil.getPinYin(basecompinfo.getBase_comp_name());
                    		//公司简称拼音全拼
                    		basecompinfo.setBase_comp_namep(pinYin[0]);
                    		//公司简称拼音首字母
                    		basecompinfo.setBase_comp_namef(pinYin[1]);
                    		//创建来源
                    		basecompinfo.setBase_comp_stem("1");
        					//当前状态
                    		basecompinfo.setBase_comp_estate("1");
                    		//排它锁
                    		basecompinfo.setBase_datalock_viewtype(1);
        					//PL锁状态
                    		basecompinfo.setBase_datalock_pltype("0");
                    		//删除标识
                    		basecompinfo.setDeleteflag("0");
                			//创建者
                    		basecompinfo.setCreateid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
                			//创建时间
                    		basecompinfo.setCreatetime(CommonUtil.getNowTime_tamp());
                			//更新者
                    		basecompinfo.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
                			//更新时间
                    		basecompinfo.setUpdtime(basecompinfo.getCreatetime());
                		}else{//2.3.1.1.1.2:若数据库存在记录
                		    if ("1".equals(basecompinfo.getBase_comp_estate()) 
                		    		|| "4".equals(basecompinfo.getBase_comp_estate())) {
                					opreString="do";
				    				
                          	/*2.3.1.1.1.2.1:判断数据是否发生变化，若发生变化更新数据
						  	当前状态：it桔子修改】，【PL锁状态】*/
                			BaseCompInfo tmpbasecompinfo=new BaseCompInfo();
                			//获投状态
                			if(userMap.get("Tmp_Company_Fundstatus")!=null){
                				labelelementname=userMap.get("Tmp_Company_Fundstatus").toString().trim();
                				if(labelelementname.indexOf("(")>=0
            		    				&&labelelementname.indexOf(")")>labelelementname.indexOf("(")){
            		    			if(labelelementname.indexOf("获投状态:")>=0){
            		    				labelelementname=labelelementname.substring(labelelementname.indexOf("获投状态")+"获投状态:".length(),labelelementname.lastIndexOf(")"));
            		    			}else{
            		    				labelelementname=labelelementname.substring(labelelementname.indexOf("(")+1,labelelementname.lastIndexOf(")"));
            		    			}
            		    		}
                				
                				
                				//公司阶段
                                //查询标签是否存在的参数map
            					Map<String, String> indumap=new HashMap<String, String>();
            					//标签所属code
            					indumap.put("labelcode", CommonUtil.findNoteTxtOfXML("Lable-comstage"));
            					indumap.put("labelelementname",labelelementname);
            					//查询标签是否存在
            					SysLabelelementInfo syslabelelementinfo=iimportitdataservice.querylabelelementbyname(indumap);
								//若存在
            					if(syslabelelementinfo!=null && !"".equals(syslabelelementinfo)){
            						tmpbasecompinfo.setBase_comp_stage(syslabelelementinfo.getSys_labelelement_code());
            					}
                			}
                		    if((userMap.get("Tmp_Company_Name")!=null && basecompinfo.getBase_comp_name()!=null 
                			    &&!userMap.get("Tmp_Company_Name").toString().trim().equals(basecompinfo.getBase_comp_name()))
                			    ||!((fullname!=null && basecompinfo.getBase_comp_fullname()!=null 
                				  		&& fullname.equals(basecompinfo.getBase_comp_fullname()))
                				  			||( fullname==null && basecompinfo.getBase_comp_fullname()==null ))
                				||!(( tmpbasecompinfo.getBase_comp_stage()!=null 
                				 			&& basecompinfo.getBase_comp_stage()!=null
                				 			&& tmpbasecompinfo.getBase_comp_stage().equals(basecompinfo.getBase_comp_stage()))
                				 			||(tmpbasecompinfo.getBase_comp_stage()==null 
                				 					&& basecompinfo.getBase_comp_stage()==null 
                         				 			))){
                		    	opreString="upd";
                				//公司简称
                				basecompinfo.setBase_comp_name(userMap.get("Tmp_Company_Name").toString().trim());
	                	        if(fullname!=null){
	                	         //公司全称
	                	        	 basecompinfo.setBase_comp_fullname(fullname);
	                			}else {
	                	              basecompinfo.setBase_comp_fullname(null);
	                			}
	                	        //公司简称拼音全拼,公司简称拼音首字母
	                	        String[] pinYin=CommonUtil.getPinYin(basecompinfo.getBase_comp_name());
	                	        //公司简称拼音全拼
	                	        basecompinfo.setBase_comp_namep(pinYin[0]);
	                	        //公司简称拼音首字母
	                	        basecompinfo.setBase_comp_namef(pinYin[1]);
	                	        //公司阶段
	                	        basecompinfo.setBase_comp_stage(tmpbasecompinfo.getBase_comp_stage());
	                	        //当前状态
	                	        basecompinfo.setBase_comp_estate("4");
                    			//PL锁状态
	                	        basecompinfo.setBase_datalock_pltype("0");
	                	        //更新者
	                	        basecompinfo.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
	                	        //更新时间
	                	        basecompinfo.setUpdtime(CommonUtil.getNowTime_tamp());
                		    }
                		}
                		}
                		//2.3.1.２:判断公司信息是否可进行操作，可操作则处理行业标签
                		if(!"nodo".equals(opreString)){
        					//公司行业标签（存储数据库使用）
        					List<BaseComplabelInfo> basecomplabellist=new ArrayList<BaseComplabelInfo>();
        					if(userMap.get("Tmp_Company_Industry")!=null){
        					//抓取数据库的公司行业标签转为数组
        					String[] induStrArray = userMap.get("Tmp_Company_Industry").toString().split(" ");
        					//处理循环放置截取数据
            				for (int j = 0; j < induStrArray.length; j++) {
            				    BaseComplabelInfo basecomplabelinfo= new BaseComplabelInfo();
            			 			labelelementname=induStrArray[j].trim();;
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
											//公司id
									    	basecomplabelinfo.setBase_comp_code(basecompinfo.getBase_comp_code());
											//标签元素code
									    	basecomplabelinfo.setSys_labelelement_code(syslabelelementinfo.getSys_labelelement_code());
											//标签code
									    	basecomplabelinfo.setSys_label_code(syslabelelementinfo.getSys_label_code());
											//删除标识
									    	basecomplabelinfo.setDeleteflag("0");
											//创建者
									    	basecomplabelinfo.setCreateid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
											//创建时间
									    	basecomplabelinfo.setCreatetime(CommonUtil.getNowTime_tamp());
											//更新者
											basecomplabelinfo.setUpdid(CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"));
											//更新时间
											basecomplabelinfo.setUpdtime(basecomplabelinfo.getCreatetime());
											//放置list
											basecomplabellist.add(basecomplabelinfo);
											}
									   }
            						}
                    			        }
									//存储数据库，并调用存储过程
            						iimportitdataservice.tranModifyimportcompinfo(basecompinfo, basecomplabellist, opreString);
            					}
					}  
        			}
        			
			}
			System.out.println("importcompdata end:"+System.currentTimeMillis());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ImportCompITdata 的importcompdata 公司信息导入数据库出现异常",e);
			return;
		}
		  logger.info("ImportCompITdata 的importcompdata 导入公司信息结束");
	}
	
}