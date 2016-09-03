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
import cn.com.sims.model.sysstoredprocedure.SysStoredProcedure;
import cn.com.sims.service.system.sysstoredprocedure.ISysStoredProcedureService;

/**
 * @ClassName: TimingUpdateUserOperaData.java
 * @Description: 定时任务更新数据库,调用存储过程
 * @author rqq
 * @date 2015/11/16
 **/

public class TimingUpdateUserOperaData {
    @Resource
	 ISysStoredProcedureService storedProcedureService;//存储过程

	private static final Logger logger = Logger.getLogger(TimingUpdateUserOperaData.class);
	/**
	 * @Title: updateuseroperadata
	 * @Description:定时任务调用存储过程计算用户的参会次数，添加会议次数，纠错次数，添加数据次数
	 * @return
	 */
	public void updateuseroperadata() throws Exception {
		logger.info("TimingUpdateUserOperaData updateuseroperadata 方法开始");
		
		//调用存储过程
		try {
		    storedProcedureService.callSysuseropertotalnum(new SysStoredProcedure("", "", "", CommonUtil.findNoteTxtOfXML("ADMIN-CODE"),"",""));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TimingUpdateUserOperaData updateuseroperadata 方法异常"+e);
		}
		
		logger.info("TimingUpdateUserOperaData updateuseroperadata 方法结束");
	}
	
	/**
	 * @Title: updateinvestfeature
	 * @Description:定时任务调用存储过程判断机构特征的领投及活跃
	 * @return
	 */
	public void updateinvestfeature() throws Exception {
		logger.info("TimingUpdateUserOperaData updateinvestfeature 方法开始");
		SysStoredProcedure sysstoredprocedure=new SysStoredProcedure();
		//调用存储过程
		try {
		    //更新者
		    sysstoredprocedure.setUserCode(CommonUtil.findNoteTxtOfXML("ADMIN-CODE"));
		    //	 前月，（投资机构特征活跃使用） 
		    sysstoredprocedure.setBeforemonth(Integer.valueOf(CommonUtil.findNoteTxtOfXML("BEFOREMONTH")));
		    //后月，（投资机构特征活跃使用） 
		    sysstoredprocedure.setAftermonth(Integer.valueOf(CommonUtil.findNoteTxtOfXML("AFTERMONTH")));
		    storedProcedureService.callTimeinvesfeature(sysstoredprocedure);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TimingUpdateUserOperaData updateinvestfeature 方法异常"+e);
		}
		
		logger.info("TimingUpdateUserOperaData updateinvestfeature 方法结束");
	}
	
	

}