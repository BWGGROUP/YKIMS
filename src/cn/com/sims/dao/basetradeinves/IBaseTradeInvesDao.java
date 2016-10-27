package cn.com.sims.dao.basetradeinves;

import java.util.HashMap;
import java.util.Map;

import cn.com.sims.model.basetradeinves.BaseTradeInves;
import cn.com.sims.model.basetradeinves.BaseTradeInves;

/**
 * 
 * @author rqq
 *
 */
public interface IBaseTradeInvesDao {
	
	public void insertBaseTradeInvesInfo(String str,BaseTradeInves info)throws Exception;
	
	public int deleteTradeInvesInfo(String str,Map<String, String> map)throws Exception;

	public int deleteTradeInvesInfoByTradeCode(String str,String tradeCode)throws Exception;
	
    /**
	* 根据对应IT桔子id查询机构交易数据库中是否存在记录(基础层)
	* @author rqq
	* @date 2015-10-27 
	* @param str
	* @param map:base_trade_code:交易id;base_investment_code:机构id
	* @return
	* @throws Exception
	 */
	public BaseTradeInves findBaseTradeInveBytmpcodeForIT(String str,Map<String,String> map)throws Exception;
		
	/**
	* 更新it桔子导入数据机构交易数据
	* @author rqq
	* @date 2015-10-27 
	* @param str
	* @param basetradeinves机构交易数据
	* @return
	* @throws Exception
	*/
	public void updateBaseTradeInveforIT(String str,
		BaseTradeInves basetradeinves) throws Exception;
		
	/**
	* 插入it桔子导入数据库机构交易数据
	* @author rqq
	* @date 2015-10-27 
	* @param str
	* @param basetradeinves机构交易数据
	* @return
	* @throws Exception
	 */
	public void insertBaseTradeInveforIT(String str,
		BaseTradeInves basetradeinves) throws Exception;
	/**
	 * 根据交易code查询机构交易交易信息
	 * @param tradeCode交易code
	 * @return
	 * @throws Exception
	 * @author zzg
	 * @date 2015-12-02
	 */
	public BaseTradeInves findBaseTradeInvesByCode(String str,HashMap<String, String> map) throws Exception;
	/**
	 * 修改机构交易信息
	 * @author zzg
	 * @date 2015-12-02
	 * @return
	 * @throws Exception
	 */
	public int updata_tradeinves(String str,BaseTradeInves trade) throws Exception;
}
