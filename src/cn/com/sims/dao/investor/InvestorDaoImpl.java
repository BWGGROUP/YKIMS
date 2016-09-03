package cn.com.sims.dao.investor;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.viewInvestorInfo.viewInvestorInfo;

import com.ibatis.sqlmap.client.SqlMapClient;
@Service
public class InvestorDaoImpl implements InvestorDao{
	@Resource
	private SqlMapClient sqlMapClient;
/**
 * 模糊查询投资人
 */
	@Override
	public List findInvestor(String str, String name) throws Exception {
		return sqlMapClient.queryForList(str, name);
	}

	@Override
	public List<Map<String, String>> findInvestorByOrgId(String str, String id)
			throws Exception {
		return sqlMapClient.queryForList(str, id);
	}
/**
 * 根据投资人code 查询投资人详细信息
 * @author yl
 * @date 2015-10-15 
 */
	@Override
	public viewInvestorInfo findInvestorDeatilByCode(String str, String code)
			throws Exception {
		// TODO Auto-generated method stub
		return (viewInvestorInfo) sqlMapClient.queryForObject(str, code);
	}

@Override
public BaseInvestorInfo findInvestorByCode(String str, String code)
		throws Exception {
	// TODO Auto-generated method stub
	return (BaseInvestorInfo) sqlMapClient.queryForObject(str, code);
}

@Override
public int tranModifyInvestor(String str, BaseInvestorInfo info)
		throws Exception {
	// TODO Auto-generated method stub
	return sqlMapClient.update(str, info);
}

	@Override
	public List<Map<String, String>> findPageInvestorByOrgId(String str,
			Map<String, String> map) throws Exception {
		return sqlMapClient.queryForList(str, map);
	}

	@Override
	public int findCountInvestor(String str, String orgCode) throws Exception {
		return (Integer) sqlMapClient.queryForObject(str, orgCode);
	}
	
	@Override
	public BaseInvestorInfo findBaseInvestorBytmpcodeForIT(String str,String tmpinvestorcode)
		throws Exception {
		return (BaseInvestorInfo) sqlMapClient.queryForObject(str, tmpinvestorcode);
	}
	
	@Override
	public void updateBaseInvestorforIT(String str,
		BaseInvestorInfo baseinvestorinfo) throws Exception {
		sqlMapClient.update(str, baseinvestorinfo);
	}
	
	@Override
	public void insertBaseInvestorforIT(String str,
		BaseInvestorInfo baseinvestorinfo) throws Exception {
		 sqlMapClient.insert(str, baseinvestorinfo);
	}

	/**
	 * 查询所有投资人id,名称,下拉筐使用
	 * @return id,text
	 * @author duwenjie
	 * 
	 */
	@Override
	public List<Map<String, String>> findAllInvestorInfo(String str)
			throws Exception {
		return sqlMapClient.queryForList(str);
	}

	@Override
	public String findInvestment(String str, String code) throws Exception {
		// TODO Auto-generated method stub
		return (String)sqlMapClient.queryForObject(str, code);
	}

	@Override
	public void insetInvstor(String str, BaseInvestorInfo info)
			throws Exception {
		// TODO Auto-generated method stub
		sqlMapClient.insert(str, info);
	}
	
	@Override
	public int queryInvestorlistnumByOrgId(String str,
		Map<String, Object> map) throws Exception {
	    return (Integer) sqlMapClient.queryForObject(str, map);
	}

	@Override
	public List<viewInvestorInfo> queryInvestorlistByOrgId(String str,
		Map<String, Object> map) throws Exception {
	    return sqlMapClient.queryForList(str,map);
	}
	
	/**
	 * 根据投资机构code查询投资人id,名称,下拉筐使用
	 * @return id,text
	 * @author duwenjie
	 * 
	 */
	@Override
	public List<Map<String, String>> findInvestorInfoByOrgCode(String str,
			String orgCode) throws Exception {
		return sqlMapClient.queryForList(str,orgCode);
	}
	//2015-11-27 TASK070 yl add start
	@Override
	public String findInvestmentInvest(String str, String code) throws SQLException {
		// TODO Auto-generated method stub
		return (String)sqlMapClient.queryForObject(str,code);
	}
	//2015-11-27 TASK070 yl add start

	@Override
	public int tranDeleteInvestor(String str, Map map) throws Exception {
		// TODO Auto-generated method stub
		return (Integer)sqlMapClient.delete(str,map);
	}

	@Override
	public int updateInvestor(String str, Map map) throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.update(str, map);
	}

	@Override
	public int selectTrade(String str, Map map) throws Exception {
		// TODO Auto-generated method stub
		return (Integer)sqlMapClient.queryForObject(str, map);
	}

	@Override
	public BaseInvestorInfo findOrgInvestorByName(String str,
			Map<String, String> map) throws Exception {
		return (BaseInvestorInfo) sqlMapClient.queryForObject(str, map);
	}
}