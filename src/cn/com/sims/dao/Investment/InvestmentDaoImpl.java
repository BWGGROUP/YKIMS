package cn.com.sims.dao.Investment;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sims.model.baseinvesfundinfo.BaseInvesfundInfo;
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.viewInvestmentInfo.investmentInfo;
import cn.com.sims.model.viewInvestmentInfo.viewInvestmentInfo;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service
public class InvestmentDaoImpl implements InvestmentDao {
	@Resource
	private SqlMapClient sqlMapClient;

	@Override
	public List<baseInvestmentInfo> findInvestment(String str, String name) throws Exception {
		return sqlMapClient.queryForList(str, name);
	}

	@Override
	public List findInvestmentByName(String str,Map<String, Object> paraMap )
			throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str, paraMap);
	}

	@Override
	public List<viewInvestmentInfo> gotoSearchByName(String str, Map<String, Object> paraMap) throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str,paraMap);
	}

	@Override
	public int findCountSizeByName(String str, Map<String, Object> paraMap)
			throws Exception {
		// TODO Auto-generated method stub
		return Integer.parseInt(sqlMapClient.queryForObject(str,paraMap).toString());
	}

	@Override
	public List findLable(String str,String type) throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str,type);
	}

	@Override
	public List findCompany(String str, String name) throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str,name);
	}

	@Override
	public List findInvestmentByMoreCon(String str, Map<String, Object> paraMap)
			throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str,paraMap);
	}

	@Override
	public int findInvestmentByMoreCount(String str, Map<String, Object> paraMap)
			throws Exception {
		// TODO Auto-generated method stub
		return Integer.parseInt(sqlMapClient.queryForObject(str,paraMap).toString());
	}

	@Override
	public String findCompanyRe(String str, Map<String, Object> paraMap)
			throws Exception {
		// TODO Auto-generated method stub
		if(sqlMapClient.queryForObject(str,paraMap)!=null){
		return sqlMapClient.queryForObject(str,paraMap).toString();
			
	}else{
		return "";
	}
		
	}
	@Override
	public List findInvestmentByMoreConExport(String str,
			Map<String, Object> paraMap) throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str,paraMap);
	}

	@Override
	public List findLableScale(String str) throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str);
	}

	@Override
	public List<investmentInfo> findInvestmentByNameExport(String str,
			Map<String, Object> paraMap) throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str,paraMap);
	}

	@Override
	public int queryInvestmentlistnumByOrgname(String str,
		Map<String, Object> map) throws Exception {
	    return (Integer)sqlMapClient.queryForObject(str,map);
	}

	@Override
	public List<viewInvestmentInfo> queryInvestmentlistByOrgname(
		String str, Map<String, Object> map) throws Exception {
	    return sqlMapClient.queryForList(str,map);
	}
	@Override
	public List<BaseInvesfundInfo> findInvesfund(String str, Map<String, Object> map) throws Exception {
	    return sqlMapClient.queryForList(str,map);
	}
	
}
