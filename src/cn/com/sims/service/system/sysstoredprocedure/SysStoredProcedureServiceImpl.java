package cn.com.sims.service.system.sysstoredprocedure;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.dao.system.sysstoredprocedure.ISysStoredProcedureDao;
import cn.com.sims.model.sysstoredprocedure.SysStoredProcedure;

/**
 * 存储过程实现
 * @author duwenjie
 * @date 2015-10-14
 */

@Service
public class SysStoredProcedureServiceImpl implements ISysStoredProcedureService {

	@Resource
	ISysStoredProcedureDao dao;
	
	private static final Logger gs_logger = Logger.getLogger(SysStoredProcedureServiceImpl.class);
	
	@Override
	public SysStoredProcedure callViewinvestment(SysStoredProcedure info)throws Exception  {
		gs_logger.info("callViewinvestment 方法开始");
		SysStoredProcedure data = null;
		try {
			data=dao.callViewinvestment("SysStoredProcedure.callViewinvestment", info);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("callViewinvestment 方法异常",e);
			throw e;
		}finally{
			gs_logger.info("callViewinvestment 方法结束");
		}
		return data;
	}

	@Override
	public SysStoredProcedure callViewinvestorInfo(SysStoredProcedure info)throws Exception  {
		// TODO Auto-generated method stub
		gs_logger.info("callViewinvestorInfo 方法开始");
		SysStoredProcedure data = null;
		try {
			data=dao.callViewinvestorInfo("SysStoredProcedure.callViewinvestorInfo", info);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("callViewinvestorInfo 方法异常",e);
			throw e;
		}finally{
			gs_logger.info("callViewinvestorInfo 方法结束");
		}
		return data;
	}

	@Override
	public SysStoredProcedure callViewTradeser(SysStoredProcedure info)
			throws Exception {
		gs_logger.info("callViewTradeser 方法开始");
		SysStoredProcedure data = null;
		try {
			data=dao.callViewTradeser("SysStoredProcedure.callViewtradeser", info);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("callViewTradeser 方法异常",e);
			throw e;
		}finally{
			gs_logger.info("callViewTradeser 方法结束");
		}
		return data;
	}

	@Override
	public SysStoredProcedure callViewTradeInfo(SysStoredProcedure info)
			throws Exception {
		gs_logger.info("callViewTradeInfo 方法开始");
		SysStoredProcedure data = null;
		try {
			data=dao.callViewTradeInfo("SysStoredProcedure.callViewtradeinfo", info);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("callViewTradeInfo 方法异常",e);
			throw e;
		}finally{
			gs_logger.info("callViewTradeInfo 方法结束");
		}
		return data;
	}
	@Override
	public SysStoredProcedure callViewcompinfo(SysStoredProcedure info)
			throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("callViewcompinfo 方法开始");
		SysStoredProcedure data = null;
		try {
			data=dao.callViewcompinfo("SysStoredProcedure.callViewcompinfo", info);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("callViewcompinfo 方法异常",e);
			throw e;
		}finally{
			gs_logger.info("callViewcompinfo 方法结束");
		}
		return data;
	}

	@Override
	public SysStoredProcedure callSysuseropertotalnum(SysStoredProcedure info)
			throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("SysStoredProcedureServiceImpl　callSysuseropertotalnum 方法开始");
		SysStoredProcedure data = null;
		try {
			data=dao.callViewcompinfo("SysStoredProcedure.callSysuseropertotalnum", info);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("SysStoredProcedureServiceImpl　callSysuseropertotalnum 方法异常",e);
			throw e;
		}finally{
			gs_logger.info("SysStoredProcedureServiceImpl callSysuseropertotalnum 方法结束");
		}
		return data;
	}

	@Override
	public SysStoredProcedure callTimeinvesfeature(SysStoredProcedure info)
		throws Exception {
	 		gs_logger.info("SysStoredProcedureServiceImpl　callTimeinvesfeature 方法开始");
	 		SysStoredProcedure data = null;
	 		try {
	 			data=dao.callViewcompinfo("SysStoredProcedure.callTimeinvesfeature", info);
	 		} catch (Exception e) {
	 			e.printStackTrace();
	 			gs_logger.error("SysStoredProcedureServiceImpl　callTimeinvesfeature 方法异常",e);
	 			throw e;
	 		}finally{
	 			gs_logger.info("SysStoredProcedureServiceImpl callTimeinvesfeature 方法结束");
	 		}
	 		return data;
	}
}
