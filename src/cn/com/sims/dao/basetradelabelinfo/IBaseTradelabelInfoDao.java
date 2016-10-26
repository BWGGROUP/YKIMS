package cn.com.sims.dao.basetradelabelinfo;


import cn.com.sims.model.basetradelabelinfo.BaseTradelabelInfo;


/**
 * 
 * @author rqq
 *
 */
public interface IBaseTradelabelInfoDao {

	/**
	 * 添加交易标签，it桔子导入
	 * @author rqq
	 * @date 2015-10-27 
	 * @param str
	 * @param basetradelabelinfo 交易标签对象
	 * @throws Exception
	 */
	public void insertBaseTradelabelInfoforit(String str,BaseTradelabelInfo basetradelabelinfo) throws Exception;
	
	/**
	 * 添加交易标签
	 * @param str
	 * @param info交易标签对象
	 * @date 2015-10-28
	 * @author duwenjie 
	 * @return
	 * @throws Exception
	 */
	public void insertBaseTradelabelInfo(String str,BaseTradelabelInfo info) throws Exception;
	
	/**
	 * 删除交易标签
	 * @param str
	 * @param info标签信息删除条件(交易id	base_trade_code,标签元素code	sys_labelelement_code,标签code	sys_label_code)
	 * @date 2015-10-28
	 * @author duwenjie 
	 * @return
	 * @throws Exception
	 */
	public int deleteBaseTradeLabelInfo(String str,BaseTradelabelInfo info) throws Exception;
	
	public int deleteBaseTradeLabelInfoByTradeCode(String str,String tradeCode)throws Exception;
}
