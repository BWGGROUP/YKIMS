package cn.com.sims.util.task;

/** 
 * @File: TimingImportITData.java 
 * @Package cn.com.sims.util.task
 * @Description: TODO 
 * @Copyright: Copyright(c)2015 
 * @Company: shbs  
 * @author rqq
 * @date 2015/10/20 
 * @version V1.0 
 **/


import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.importITdata.ImportCompITdata;
import cn.com.sims.importITdata.ImportEntrepreneurITdata;
import cn.com.sims.importITdata.ImportInvestmentITdata;
import cn.com.sims.importITdata.ImportInvestorITdata;
import cn.com.sims.importITdata.ImportTradeITdata;

/**
 * @ClassName: TimingImportITData.java
 * @Description: 定时任务更新数据库
 * @author rqq
 * @date 2015/10/20
 **/

public class TimingImportITData {
    @Resource
    ImportInvestmentITdata importinvestmentitdata;
    @Resource
    ImportInvestorITdata importinvestoritdata;
    @Resource
    ImportCompITdata importcompitdata;
    @Resource
    ImportEntrepreneurITdata importentrepreneuritdata;
    @Resource
    ImportTradeITdata importtradeitdata;
	private static final Logger logger = Logger.getLogger(TimingImportITData.class);
	/**
	 * @Title: importdata
	 * @Description:定时任务从it桔子抓取的临时数据库数据导入至正式数据库
	 * @return
	 */
	public void importdata() throws Exception {
		logger.info("TimingImportITData importdata 方法开始");
		System.out.println("==================IT桔子导入数据开始");
		//导入投资机构
		try {
		    logger.info(CommonUtil.getNowTime()+"TimingImportITData importdata 方法导入机构(importinvestmentdata)信息start");
		    importinvestmentitdata.importinvestmentdata();
			logger.info(CommonUtil.getNowTime()+"TimingImportITData importdata 方法导入机构(importinvestmentdata)信息end");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TimingImportITData importdata 方法导入机构(importinvestmentdata)信息异常"+e);
		}
		
		//导入投资人
		try {
		    logger.info(CommonUtil.getNowTime()+"TimingImportITData importdata 方法导入投资人(importinvestordata)start");
		    importinvestoritdata.importinvestordata();
			logger.info(CommonUtil.getNowTime()+"TimingImportITData importdata 方法导入投资人(importinvestordata)end");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TimingImportITData importdata 方法导入投资人(importinvestordata)信息异常"+e);
		}
		
		//导入投资人机构关系
		try {
		    logger.info(CommonUtil.getNowTime()+"TimingImportITData importdata 方法导入投资人机构关系(importinvestmentinvestordata)start");
		    importinvestoritdata.importinvestmentinvestordata();
			logger.info(CommonUtil.getNowTime()+"TimingImportITData importdata 方法导入投资人机构关系(importinvestmentinvestordata)end");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TimingImportITData importdata 方法导入投资人机构关系(importinvestmentinvestordata)信息异常"+e);
		}
		
		//导入公司
		try {
			logger.info(CommonUtil.getNowTime()+"TimingImportITData importdata 方法导入公司(importcompdata)start");
			importcompitdata.importcompdata();
			logger.info(CommonUtil.getNowTime()+"TimingImportITData importdata 方法导入公司(importcompdata)end");
			} catch (Exception e) {
				e.printStackTrace();
			logger.error("TimingImportITData importdata 方法导入公司(importcompdata)信息异常"+e);
			}
		
		//导入创业者公司联系人
		try {
			logger.info(CommonUtil.getNowTime()+"TimingImportITData importdata 方法导入创业者公司联系人(importentrepreneurdata)start");
			importentrepreneuritdata.importentrepreneurdata();
			logger.info(CommonUtil.getNowTime()+"TimingImportITData importdata 方法导入创业者公司联系人(importentrepreneurdata)end");
			} catch (Exception e) {
				e.printStackTrace();
			logger.error("TimingImportITData importdata 方法导入创业者公司联系人(importentrepreneurdata)信息异常"+e);
			}
		
		//导入创业者公司联系人关系信息
		try {
			logger.info(CommonUtil.getNowTime()+"TimingImportITData importdata 方法导入创业者公司联系人关系(importcompentrepreneurdata)start");
			importentrepreneuritdata.importcompentrepreneurdata();
			logger.info(CommonUtil.getNowTime()+"TimingImportITData importdata 方法导入创业者公司联系人关系(importcompentrepreneurdata)end");
			} catch (Exception e) {
				e.printStackTrace();
			logger.error("TimingImportITData importdata 方法导入创业者公司联系人关系(importcompentrepreneurdata)信息异常"+e);
			}
		//导入交易信息
		try {
			logger.info(CommonUtil.getNowTime()+"TimingImportITData importdata 方法导入交易信息(importtradeitdata)start");
			importtradeitdata.importtradeitdata();
			logger.info(CommonUtil.getNowTime()+"TimingImportITData importdata 方法导入交易信息(importtradeitdata)end");
			} catch (Exception e) {
				e.printStackTrace();
			logger.error("TimingImportITData importdata 方法导入交易信息(importtradeitdata)异常"+e);
			}
		//导入机构交易信息
		try {
			logger.info(CommonUtil.getNowTime()+"TimingImportITData importdata 方法导入机构交易(importtradeinvedata)start");
			importtradeitdata.importtradeinvedata();
			logger.info(CommonUtil.getNowTime()+"TimingImportITData importdata 方法导入机构交易(importtradeinvedata)end");
			} catch (Exception e) {
				e.printStackTrace();
			logger.error("TimingImportITData importdata 方法导入机构交易(importtradeinvedata)异常"+e);
			}
		System.out.println("=============================IT桔子导入数据结束");
		logger.info("TimingImportITData 的 importdata 方法 结束");
	}
	
	
}