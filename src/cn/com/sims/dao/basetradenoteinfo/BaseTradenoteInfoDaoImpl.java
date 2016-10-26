package cn.com.sims.dao.basetradenoteinfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sims.model.basetradenoteinfo.BaseTradenoteInfo;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 交易备注实现
 * @author shbs-tp004
 *
 */
@Service
public class BaseTradenoteInfoDaoImpl implements IBaseTradenoteInfoDao{

	@Resource
	private SqlMapClient sqlMapClient;
	
	/**
	 * 根据交易code查询交易备注
	 * @author duwenjie
	 */
	@Override
	public List<BaseTradenoteInfo> findTradenoteByTradeCode(String str,
			String tradeCode) throws Exception {
		return  sqlMapClient.queryForList(str, tradeCode);
	}
	
	/**
	 * 添加交易备注信息
	 * @author duwenjie
	 */
	@Override
	public void insertBaseTradenoteInfo(String str, BaseTradenoteInfo info)
			throws Exception {
		sqlMapClient.insert(str, info);
	}

	/**
	 * 删除交易备注信息
	 * @author duwenjie
	 */
	@Override
	public int deleteBaseTradenoteInfo(String str, String noteCode)
			throws Exception {
		return sqlMapClient.delete(str, noteCode);
	}

	
	/**
	 * 根据交易code删除交易备注
	 * @param tradeCode交易code
	 * @author duwenjie
	 * @date 2011-11-20
	 */
	@Override
	public int deleteBaseTradenoteInfoByTradeCode(String str, String tradeCode)
			throws Exception {
		return sqlMapClient.delete(str, tradeCode);
	}

}
