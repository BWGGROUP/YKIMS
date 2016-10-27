package cn.com.sims.dao.system.sysstoredprocedure;


import cn.com.sims.model.sysstoredprocedure.SysStoredProcedure;

/**
 * 存储过程接口
 * @author duwenjie
 * @date 2015-10-14
 * 
 */
public interface ISysStoredProcedureDao {

	
	/**
	 * 调用存储过程,查询投资机构标签操作是否成功
	 * @param 存储过程参数对象info
	 * @return 1/成功 2/失败
	 * @throws Exception
	 * @author duwenjie
	 */
	public SysStoredProcedure callViewinvestment(String str,SysStoredProcedure info) throws Exception;
	
	/**
	* 调用存储过程,查询投资机人修改是否成功
	 * @param 存储过程参数对象info
	 * @return 1/成功 2/失败
	 * @throws Exception
	 * @author yl
	 */
	public SysStoredProcedure callViewinvestorInfo(String str,SysStoredProcedure info) throws Exception;
	
	
	/**
	 * 调用交易信息存储过程
	 * @param str
	 * @param info存储过程传参对象
	 * @return 
	 * @throws Exception
	 * @author duwenjie
	 */
	public SysStoredProcedure callViewTradeser(String str,SysStoredProcedure info) throws Exception;
	/**
	 * 调用公司信息存储过程
	 * @param str
	 * @param info
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月28日
	 */
	public SysStoredProcedure callViewcompinfo(String str,SysStoredProcedure info) throws Exception;
	
	
	/**
	 * 交易画面调用存储过程
	 * @param str
	 * @param info存储过程传参对象
	 * @return
	 * @throws Exception
	 */
	public SysStoredProcedure callViewTradeInfo(String str,SysStoredProcedure info) throws Exception;
	
}
