package cn.com.sims.service.system.sysstoredprocedure;

import cn.com.sims.model.sysstoredprocedure.SysStoredProcedure;

/**
 * 存储过程Service
 * @author duwenjie
 * @date 2015-10-14
 *
 */
public interface ISysStoredProcedureService {

	/**
	 * 存储过程,查看投资机构标签操作是否成功
	 * @param 存储过程参数info
	 * @return 1成功 2失败
	 */
	public SysStoredProcedure callViewinvestment(SysStoredProcedure info)throws Exception;
	
	public SysStoredProcedure callViewinvestorInfo(SysStoredProcedure info)throws Exception;
	
	public SysStoredProcedure callViewTradeser(SysStoredProcedure info) throws Exception ;
	
	public SysStoredProcedure callViewTradeInfo(SysStoredProcedure info) throws Exception;
	
		
	public SysStoredProcedure callViewcompinfo(SysStoredProcedure info)throws Exception;
	/**
	 * 调用计算用户操作次数的存储过程
	 * @param 存储过程参数info
	 * @date 2015-11-16
	 * @author rqq
	 */
	public SysStoredProcedure callSysuseropertotalnum(SysStoredProcedure info)throws Exception;
	
	/**
	 * 调用存储过程，机构投资特征
	 * @param 存储过程参数info
	 * @date 2015-11-20
	 * @author rqq
	 */
	public SysStoredProcedure callTimeinvesfeature(SysStoredProcedure info)throws Exception;
}
