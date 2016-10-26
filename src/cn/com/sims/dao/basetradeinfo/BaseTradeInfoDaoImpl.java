package cn.com.sims.dao.basetradeinfo;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

import cn.com.sims.model.basetradeinfo.BaseTradeInfo;

/**
 * 
 * @author duwenjie
 *
 */

@Service
public class BaseTradeInfoDaoImpl implements IBaseTradeInfoDao{

	@Resource
	private SqlMapClient sqlMapClient;
	
	/**
	 * 根据交易code修改交易信息
	 * @param str
	 * @param info 
	 * @author duwenjie
	 */
	@Override
	public int updateTradeInfo(String str,BaseTradeInfo info) throws Exception {
		return sqlMapClient.update(str, info);
	}
	
	@Override
	public BaseTradeInfo findTradeinfoBytmpcodeForIT(String str,String tmpinvesrecordid)
		throws Exception {
		return (BaseTradeInfo) sqlMapClient.queryForObject(str, tmpinvesrecordid);
	}
	
	@Override
	public void updateBaseTradeinfoforIT(String str,
		BaseTradeInfo basetradeinfo) throws Exception {
		sqlMapClient.update(str, basetradeinfo);
	}
	
	@Override
	public void insertBaseTradeinfoforIT(String str,
		BaseTradeInfo basetradeinfo) throws Exception {
		 sqlMapClient.insert(str, basetradeinfo);
	}

	/**
	 * 根据交易code查询交易信息
	 * @param str
	 * @param tradeCode交易code
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 */
	@Override
	public BaseTradeInfo findBaseTradeInfoByCode(String str, String tradeCode)
			throws Exception {
		return (BaseTradeInfo) sqlMapClient.queryForObject(str, tradeCode);
	}

	@Override
	public int findcomptradeByCodeforit(String str,
		BaseTradeInfo basetradeinfo) throws Exception {
	    // TODO Auto-generated method stub
	    return (Integer) sqlMapClient.queryForObject(str, basetradeinfo);
	}

	/**
	 * 删除交易信息
	 * @param map (tradeCode:交易code,version:排他锁版本号)
	 * @author duwenjie
	 * @date 2015-11-20
	 */
	@Override
	public int deleteTradeInfoByTradeCode(String str, Map<String, String> map)
			throws Exception {
		return sqlMapClient.delete(str, map);
	}
	//2015-12-01 TASK073 yl add start
	@Override
	public void database(String str, Map map) throws Exception {
		// TODO Auto-generated method stub
		sqlMapClient.insert(str, map);
	}
	//2015-12-01 TASK073 yl add end
}
