package cn.com.sims.dao.basetradeinfo;


import java.util.Map;

import cn.com.sims.model.basetradeinfo.BaseTradeInfo;

/**
 * 
 * @author duwenjie
 *
 */
public interface IBaseTradeInfoDao {

	public int deleteTradeInfoByTradeCode(String str,Map<String, String> map)throws Exception;
	/**
	 * 修改交易信息
	 * @param str
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public int updateTradeInfo(String str,BaseTradeInfo info)throws Exception;
	
	/**
	 * 根据交易code查询交易信息
	 * @param str
	 * @param tradeCode交易code
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 */
	public BaseTradeInfo findBaseTradeInfoByCode(String str,String tradeCode)throws Exception;
	
	
	/**
	* 根据对应IT桔子id查询交易数据库中是否存在记录(基础层)
	* @author rqq
	* @date 2015-10-27 
	* @param str
	* @param tmpinvesrecordid交易对应IT桔子id
	* @return
	* @throws Exception
	 */
	public BaseTradeInfo findTradeinfoBytmpcodeForIT(String str,String tmpinvesrecordid)throws Exception;
		
	/**
	* 更新it桔子导入数据交易数据
	* @author rqq
	* @date 2015-10-27 
	* @param str
	* @param basetradeinfo交易数据
	* @return
	* @throws Exception
	*/
	public void updateBaseTradeinfoforIT(String str,
		BaseTradeInfo basetradeinfo) throws Exception;
		
	/**
	* 插入it桔子导入数据库交易数据
	* @author rqq
	* @date 2015-10-27 
	* @param str
	* @param basetradeinfo交易数据
	* @return
	* @throws Exception
	 */
	public void insertBaseTradeinfoforIT(String str,
		BaseTradeInfo basetradeinfo) throws Exception;
	
	/**
	* 根据公司code,轮次查询交易信息(基础层)
	* @author rqq
	* @date 2015-11-16
	* @param str
	* @param basetradeinfo:交易信息
	* @return
	* @throws Exception
	 */
	public int findcomptradeByCodeforit(String str,BaseTradeInfo basetradeinfo)throws Exception;
	//2015-12-01 TASK073 yl add start
	/**
	 * 数据备份
	 * @param str
	 * @param map
	 * @throws Exception
	*@author yl
	*@date 2015年12月1日
	 */
	public void database(String str,Map map)throws Exception;
	//2015-12-01 TASK073 yl add end
}
