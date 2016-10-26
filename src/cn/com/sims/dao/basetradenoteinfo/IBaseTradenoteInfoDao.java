package cn.com.sims.dao.basetradenoteinfo;

import java.util.List;

import cn.com.sims.model.basetradenoteinfo.BaseTradenoteInfo;

/**
 * 交易备注Dao
 * @author duwenjie
 *
 */
public interface IBaseTradenoteInfoDao {

	public List<BaseTradenoteInfo> findTradenoteByTradeCode(String str,String tradeCode)throws Exception;

	public void insertBaseTradenoteInfo(String str,BaseTradenoteInfo info)throws Exception;
	
	public int deleteBaseTradenoteInfo(String str,String noteCode)throws Exception;
	
	public int deleteBaseTradenoteInfoByTradeCode(String str,String tradeCode)throws Exception;
}
