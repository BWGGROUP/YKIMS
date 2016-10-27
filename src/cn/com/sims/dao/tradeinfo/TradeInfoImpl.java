package cn.com.sims.dao.tradeinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sims.model.viewtradeinfo.ViewTradeInfo;

import com.ibatis.sqlmap.client.SqlMapClient;

/** 
 * @author  yl
 * @date ：2015年10月16日 
 * 机构交易详情dao
 */
@Service
public class TradeInfoImpl implements ITradeInfoDao{
	@Resource
	private SqlMapClient sqlMapClient;
	/**
	 * 根据投资人id查询投资人参与的交易信息
	 */
	@Override
	public List<Map<String, String>> findTradeInfoByInvestorCode(String str,HashMap<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str, map);
	}
	@Override
	public List<Map<String, Object>> tradesearchlist(String str,
			HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str, map);
	}
	@Override
	public int tradesearchlist_num(String str,HashMap<String, Object> map)
			throws Exception {
	
		return (Integer) sqlMapClient.queryForObject(str, map);
	}
	/**
	 * 根据投资人id查询投资人参与的交易信息总条数
	 */
	@Override
	public int findTradeInfoCount(String str, String code)
			throws Exception {
		// TODO Auto-generated method stub
		return (Integer) sqlMapClient.queryForObject(str,code);
	}
	
	@Override
	public List<Map<String,Object>> findpageTradeInfoByCode(String str, Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str,map);
	}
	
	/**
	 * 根据交易code查询交易详情
	 * @param str
	 * @param map交易Code,pageCount,pageSize
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2015-10-23
	 */
	@Override
	public List<Map<String, String>> findTradeInfoByTradeCode(String str,
			Map<String, String> map) throws Exception {
		return (ArrayList<Map<String, String>>) sqlMapClient.queryForList(str, map);
	}
	
	/**
	 * 根据交易ID查询交易详情
	 * @param str
	 * @param viewTradeId交易ID
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2015-10-29
	 */
	@Override
	public ViewTradeInfo findViewTradeInfoByID(String str, String viewTradeId)
			throws Exception {
		return (ViewTradeInfo) sqlMapClient.queryForObject(str,viewTradeId);
	}
	
	/**
	 * 根据交易code查询交易数量
	 * @param str
	 * @param tradeCode交易code
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2015-10-29 
	 */
	@Override
	public int findTradeInfoCountByTradeCode(String str, String tradeCode)
			throws Exception {
		return (Integer) sqlMapClient.queryForObject(str, tradeCode);
	}
	@Override
	public int findTradeCount(String str, String code) throws Exception {
		// TODO Auto-generated method stub
		return (Integer) sqlMapClient.queryForObject(str, code);
	}
	
	/**
	 * 根据投资机构code查询交易信息数量
	 * @param orgCode 机构code
	 * @author duwenjie
	 * @date 2015-11-4
	 */
	@Override
	public int findCountTradeInfoByOrgCode(String str, String orgCode)
			throws Exception {
		return (Integer) sqlMapClient.queryForObject(str, orgCode);
	}
	
	/**
	 * 根据投资机构codef分页查询交易信息
	 * @param map 机构code,pageSize,startCount
	 * @author duwenjie
	 * @date 2015-11-4
	 */
	@Override
	public List<Map<String, String>> findPageTradeInfoByOrgCode(String str,
			Map<String, String> map) throws Exception {
		return sqlMapClient.queryForList(str, map);
	}
	
	/**
	 * 根据交易code删除交易详情
	 * @param tradeCode交易code
	 * @author duwenjie
	 * @date 2015-11-20
	 */
	@Override
	public int deleteViewTradeInfoByTradeCode(String str, String tradeCode)
			throws Exception {
		return sqlMapClient.delete(str, tradeCode);
	}
	@Override
	public Map<String, String> trade_investment(String str, String code)
			throws Exception {

		return (Map<String, String>) sqlMapClient.queryForObject(str, code);
	}
	@Override
	public int investor_notin_tradser(String str, HashMap<String, String> map)
			throws Exception {

		return (Integer) sqlMapClient.queryForObject(str, map);
	}
	@Override
	public List<Map<String, String>> findPageTradeInfoByDate(String str, HashMap<String, String> map) throws Exception {
		return sqlMapClient.queryForList(str, map);
	}
	
}
