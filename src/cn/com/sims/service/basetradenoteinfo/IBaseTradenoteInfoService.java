package cn.com.sims.service.basetradenoteinfo;

import java.util.List;
import cn.com.sims.model.basetradenoteinfo.BaseTradenoteInfo;

/**
 * 
 * @author duwenjie
 * @date 2015-10-23
 */
public interface IBaseTradenoteInfoService {
	
	public List<BaseTradenoteInfo> findTradenoteByTradeCode(String tradeCode)throws Exception;
	
	public void insertBaseTradenoteInfo(BaseTradenoteInfo info)throws Exception;
	
	public int deleteBaseTradenoteInfo(String noteCode)throws Exception;
	
}
