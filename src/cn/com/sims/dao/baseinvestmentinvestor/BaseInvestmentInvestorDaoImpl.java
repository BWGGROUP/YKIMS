package cn.com.sims.dao.baseinvestmentinvestor;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sims.model.baseinvestmentinvestor.BaseInvestmentInvestor;
import cn.com.sims.model.baseinvestorlabelinfo.BaseInvestorlabelInfo;

import com.ibatis.sqlmap.client.SqlMapClient;

/** 
 * @author  yl
 * @date ：2015年10月22日 
 * 投资人投资机构 
 */
@Service
public class BaseInvestmentInvestorDaoImpl implements IBaseInvestmentInvestorDao{
	@Resource 
	private SqlMapClient sqlMapClient;
	@Override
	public int tranModifyUpdInvestor(String str, Map map) throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.update(str, map);
	}
	@Override
	public int insertInvestor(String str, BaseInvestmentInvestor info)
			throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.update(str, info);
	}
	@Override
	public int findInvestmentInstor(String str, Map map) throws Exception {
		// TODO Auto-generated method stub
		return (Integer)sqlMapClient.queryForObject(str, map);
	}
	@Override
	public List<Map<String, String>> findTradeLabelByCode(String str,
			String code) throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str, code);
	}
	
		@Override
	public BaseInvestmentInvestor queryInvestmentInvestorbycodeforit(String str,Map<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		return (BaseInvestmentInvestor)sqlMapClient.queryForObject(str, map);
	}
	
	@Override
	public void insertInvestmentInvestorforit(String str,BaseInvestmentInvestor baseinvestmentinvestor)
			throws Exception {
		// TODO Auto-generated method stub
		 sqlMapClient.insert(str, baseinvestmentinvestor);
	}
	
	@Override
	public void updateInvestmentInvestorforit(String str,BaseInvestmentInvestor baseinvestmentinvestor)
			throws Exception {
		// TODO Auto-generated method stub
		sqlMapClient.update(str, baseinvestmentinvestor);
	}
}
