package cn.com.sims.dao.viewtradeser;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

import cn.com.sims.model.viewtradeser.ViewTradSer;

/**
 * 
 * @author duwenjie
 *
 */
@Service
public class ViewTradeSerDaoImpl implements IViewTradeSerDao{

	@Resource
	private SqlMapClient sqlMapClient;
	/**
	 * 根据交易code查询交易信息
	 * @param tradecode 交易code
	 * @author duwenjie
	 */
	@Override
	public ViewTradSer findTradeSerInfoByTradeCode(String str, String tradeCode)
			throws Exception {
		return (ViewTradSer) sqlMapClient.queryForObject(str, tradeCode);
	}
	
	/**
	 * 根据交易code删除交易详情
	 * @param tradeCode交易code
	 * @author duwenjie
	 * @date 2015-11-20
	 */
	@Override
	public int deleteTradeSerByTradeCode(String str, String tradeCode)
			throws Exception {
		return sqlMapClient.delete(str, tradeCode);
	}

}
