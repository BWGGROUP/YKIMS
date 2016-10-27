package cn.com.sims.dao.baseinvestornote;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sims.model.baseinvestornoteinfo.BaseInvestornoteInfo;

import com.ibatis.sqlmap.client.SqlMapClient;

/** 
 * @author  yl
 * @date ：2015年10月16日 
 * 投资人note信息dao
 */
@Service
public class InvestornoteDaoImpl implements IInvestornoteDao{
	@Resource
	private SqlMapClient sqlMapClient;
	/**
	 * 根据投资人ｉｄ获取投资人note信息
	 * 
	 * @param str
	 * @param 投资人id            　
	 * @return
	 * @throws Exception
	 * @author yl
	 * @date 2015年10月16日
	 */
	@Override
	public List<Map<String, String>> findTradeInfoByInvestorCode(String str,
			String code) throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str, code);
	}
	@Override
	public int investornote_del(String str, String id) throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.delete(str, id);
	}
	@Override
	public void insertInvestorNote(String str, BaseInvestornoteInfo noteinfo)
			throws Exception {
		// TODO Auto-generated method stub
		sqlMapClient.insert(str, noteinfo);
	}
}
