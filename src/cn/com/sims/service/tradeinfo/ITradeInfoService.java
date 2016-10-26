package cn.com.sims.service.tradeinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.model.viewtradeinfo.ViewTradeInfo;
import cn.com.sims.model.viewtradeser.ViewTradSer;

/** 
 * @author  yl
 * @date ：2015年10月16日 
 * 机构交易详情service
 */
public interface ITradeInfoService {
	/**
	 * 根据投资人id查询其负责的交易信息
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> findTradeInfoByInvestorCode(HashMap<String, Object> map) throws Exception;
	/**
	 * 根据投资人id查询投资人参与的交易信息总条数
	 * @param code
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月16日
	 */
	public int findTradeInfoCount(String code) throws Exception;
	
	public List<Map<String,String>> findpageTradeInfoByCode(Map<String,Object> map) throws Exception;
	
	/**
	 * 多条件搜索交易列表
	 * @return
	 * @throws Exception
	 * @author zzg
	 * @date 2015-10-20
	 */
	public List<Map<String, Object>> tradesearchlist(HashMap<String, Object> map) throws Exception;
	/**
	 * 多条件搜索交易列表总页数
	 * @return
	 * @throws Exception
	 * @author zzg
	 * @date 2015-10-20
	 */
	public int tradesearchlist_num(HashMap<String, Object> map) throws Exception;
	
	/**
	 * 根据交易code查询交易详情
	 * @param tradeCode交易Code
	 * @param page分页对象
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2015-10-23
	 */
	public List<Map<String, String>> findTradeInfoByTradeCode(Page page,String tradeCode) throws Exception;
	
	/**
	 * 根据交易code查询交易数量
	 * @param tradeCode
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2015-10-29
	 */
	public int findTradeInfoCountByTradeCode(String tradeCode)throws Exception;
	
	/**
	 * 根据交易ID查询交易详情
	 * @param veiwTradeId交易ID
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2015-10-29
	 */
	public ViewTradeInfo findViewTradeInfoByID(String veiwTradeId) throws Exception;
	
	/**
	 * 根据code查询交易信息
	 * @param tradeCode
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2015-10-29
	 */
	public ViewTradSer findTradeSerInfoByTradeCode(String tradeCode)throws Exception;
	
	
	/**
	 * 查询公司交易总条数
	 * @param code
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月30日
	 */
	public int findTradeCount(String code) throws Exception;
	
	
	public int findCountTradeInfoByOrgCode(String orgCode) throws Exception;
	
	public List<Map<String, String>> findPageTradeInfoByOrgCode(Page page,String orgCode)throws Exception;
	
	/**
	 *通过交易code查询交易机构
	 * @return
	 * @throws Exception
	 * @author zzg
	 * @date 2015-11-27
	 */
	public Map<String, String> trade_investment(String code) throws Exception;
/**
 * 查询交易中　不属于该投机人的融资信息条数
 * @author zzg
 * @date 2015-12-01
 * @param map
 * @return
 * @throws Exception
 */
	public int investor_notin_tradser(HashMap<String, String> map)throws Exception;
}
