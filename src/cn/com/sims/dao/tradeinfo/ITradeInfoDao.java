package cn.com.sims.dao.tradeinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.sims.model.viewtradeinfo.ViewTradeInfo;

/** 
 * @author  yl
 * @date ：2015年10月16日 
 * 机构交易详情dao
 */
public interface ITradeInfoDao {
	/**
	 * 根据投资人id查询投资人参与的交易信息
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> findTradeInfoByInvestorCode(String str,HashMap<String, Object> map) throws Exception;
	/**
	 *  根据投资人id查询投资人参与的交易信息总条数
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月16日
	 */
	public int findTradeInfoCount(String str,String code) throws Exception;
	
	
	public List<Map<String,Object>> findpageTradeInfoByCode(String str,Map<String,Object> map) throws Exception;
	/**
	 * 多条件搜索交易列表
	 * @return
	 * @throws Exception
	 * @author zzg
	 * @date 2015-10-20
	 */
	public List<Map<String, Object>> tradesearchlist(String str,HashMap<String, Object> map) throws Exception;
	/**
	 * 多条件搜索交易列表总页数
	 * @return
	 * @throws Exception
	 * @author zzg
	 * @date 2015-10-20
	 */
	public int tradesearchlist_num(String str,HashMap<String, Object> map) throws Exception;
	
	/**
	 * 根据交易code查询交易数量
	 * @param str
	 * @param tradeCode交易code
	 * @return
	 * @throws Exception
	 */
	public int findTradeInfoCountByTradeCode(String str,String tradeCode)throws Exception;
	
	/**
	 * 根据交易code查询交易详情
	 * @param str
	 * @param tradeCode交易Code
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2015-10-23
	 */
	public List<Map<String, String>> findTradeInfoByTradeCode(String str,Map<String, String> map) throws Exception;
	
	/**
	 * 根据id查询交易详情
	 * @param str
	 * @param viewTradeId交易ID
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2015-10-29
	 */
	public ViewTradeInfo findViewTradeInfoByID(String str,String viewTradeId) throws Exception;
	
	/**
	 * 查询公司交易总条数
	 * @param str
	 * @param code
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月30日
	 */
	public int findTradeCount(String str,String code) throws Exception;
	
	public int findCountTradeInfoByOrgCode(String str,String orgCode) throws Exception;
	
	public List<Map<String, String>> findPageTradeInfoByOrgCode(String str,Map<String, String> map)throws Exception;
	
	public int deleteViewTradeInfoByTradeCode(String str,String tradeCode)throws Exception;
	public	Map<String, String> trade_investment(String str,String code)throws Exception;
	public int investor_notin_tradser(String str,HashMap<String, String> map)throws Exception;
	public List<Map<String, String>> findPageTradeInfoByDate(String str,HashMap<String, String> map)throws Exception;
	
}
