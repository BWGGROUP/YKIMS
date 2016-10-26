package cn.com.sims.dao.basetradelabelinfo;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sims.model.basetradelabelinfo.BaseTradelabelInfo;

import com.ibatis.sqlmap.client.SqlMapClient;


/**
 * 
 * @author rqq
 *
 */

@Service
public class BaseTradelabelInfoDaoImpl implements IBaseTradelabelInfoDao{

	@Resource
	private SqlMapClient sqlMapClient;
	
	@Override
	public void insertBaseTradelabelInfoforit(String str,BaseTradelabelInfo basetradelabelinfo)
			throws Exception {
		sqlMapClient.insert(str, basetradelabelinfo);		

	}

	@Override
	public void insertBaseTradelabelInfo(String str, BaseTradelabelInfo info)
			throws Exception {
		sqlMapClient.insert(str, info);
	}

	@Override
	public int deleteBaseTradeLabelInfo(String str, BaseTradelabelInfo info)
			throws Exception {
		return sqlMapClient.delete(str, info);
	}

	/**
	 * 根据交易code删除交易标签信息
	 * @param tradeCode交易code
	 * @author duwenjie
	 * @date 2015-11-20
	 */
	@Override
	public int deleteBaseTradeLabelInfoByTradeCode(String str, String tradeCode)
			throws Exception {
		return sqlMapClient.delete(str, tradeCode);
	}

}
