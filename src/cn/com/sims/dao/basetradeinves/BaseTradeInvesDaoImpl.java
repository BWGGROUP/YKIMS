package cn.com.sims.dao.basetradeinves;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sims.model.basetradeinves.BaseTradeInves;

import com.ibatis.sqlmap.client.SqlMapClient;


/**
 * 
 * @author rqq
 *
 */

@Service
public class BaseTradeInvesDaoImpl implements IBaseTradeInvesDao{

	@Resource
	private SqlMapClient sqlMapClient;

	@Override
	public BaseTradeInves findBaseTradeInveBytmpcodeForIT(String str,
		Map<String,String> map) throws Exception {
	    return (BaseTradeInves) sqlMapClient.queryForObject(str, map);
	}

	@Override
	public void updateBaseTradeInveforIT(String str,
		BaseTradeInves basetradeinves) throws Exception {
	    sqlMapClient.update(str, basetradeinves);
	    
	}

	@Override
	public void insertBaseTradeInveforIT(String str,
		BaseTradeInves basetradeinves) throws Exception {
	    sqlMapClient.insert(str, basetradeinves);
	    
	}
	
	
	/**
	 * 添加机构交易信息
	 * @param info 机构交易信息对象
	 * @author duwenjie
	 * @date 2015-10-30
	 */
	@Override
	public void insertBaseTradeInvesInfo(String str, BaseTradeInves info)
			throws Exception {
		sqlMapClient.insert(str, info);
	}

	/**
	 * 删除机构交易信息
	 * @param map 机构code,交易code集合
	 * @author duwenjie
	 * @date 2015-10-30
	 */
	@Override
	public int deleteTradeInvesInfo(String str, Map<String, String> map)
			throws Exception {
		return sqlMapClient.delete(str, map);
	}

	/**
	 * 根据交易code删除机构交易信息
	 * @param tradeCode交易code
	 * @author duwenjie
	 * @date 2015-10-30
	 */
	@Override
	public int deleteTradeInvesInfoByTradeCode(String str, String tradeCode)
			throws Exception {
		return sqlMapClient.delete(str,tradeCode);
	}

	@Override
	public BaseTradeInves findBaseTradeInvesByCode(String str, HashMap<String, String> map)
			throws Exception {
		// TODO Auto-generated method stub
		return (BaseTradeInves) sqlMapClient.queryForObject(str,map);
	}

	@Override
	public int updata_tradeinves(String str, BaseTradeInves trade)
			throws Exception {
		
		return sqlMapClient.update(str, trade);
	}

}
