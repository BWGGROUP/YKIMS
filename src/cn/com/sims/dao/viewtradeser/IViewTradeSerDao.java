package cn.com.sims.dao.viewtradeser;

import cn.com.sims.model.viewtradeser.ViewTradSer;

/**
 * 
 * @author duwenjie
 *
 */
public interface IViewTradeSerDao {

	public ViewTradSer findTradeSerInfoByTradeCode(String str,String tradeCode)throws Exception;
	
	public int deleteTradeSerByTradeCode(String str,String tradeCode)throws Exception;
}
