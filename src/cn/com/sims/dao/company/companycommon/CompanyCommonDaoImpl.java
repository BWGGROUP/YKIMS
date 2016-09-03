package cn.com.sims.dao.company.companycommon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sims.model.basecompinfo.BaseCompInfo;

import com.ibatis.sqlmap.client.SqlMapClient;
@Service
public class CompanyCommonDaoImpl implements CompanyCommonDao {
	@Resource
	private SqlMapClient sqlMapClient;
	@Override
	public List<BaseCompInfo> companylistByname(String str, HashMap map)
			throws Exception {

		return sqlMapClient.queryForList(str,map);
	}
	@Override
	public int companylistByname_totalnum(String str, HashMap map)
			throws Exception {
		// TODO Auto-generated method stub
		return (Integer)sqlMapClient.queryForObject(str,map);
	}
	@Override
	public int companysearchlist_num(String str, HashMap<String, Object> map)
			throws Exception {
		
		return (Integer)sqlMapClient.queryForObject(str,map);
	}
	@Override
	public List<Map<String, Object>> companysearchlist(String str,
			HashMap<String, Object> map) throws Exception {

		return sqlMapClient.queryForList(str,map);
	}
	@Override
	public int company_searchlistbyname_num(String str,
			HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return (Integer)sqlMapClient.queryForObject(str,map);
	}
	@Override
	public List<Map<String, Object>> company_searchlistbyname(String str,
			HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList(str,map);
	}
		
	@Override
	public BaseCompInfo findBaseCompBynameForIT(String str,Map<String, String> map) throws Exception {
		return (BaseCompInfo) sqlMapClient.queryForObject(str, map);
	}
	
	@Override
	public BaseCompInfo findBaseCompBytmpcodeForIT(String str,String tmpcompanycode)
		throws Exception {
		return (BaseCompInfo) sqlMapClient.queryForObject(str, tmpcompanycode);
	}
	
	@Override
	public void updateBaseCompforIT(String str,
		BaseCompInfo basecompinfo) throws Exception {
		sqlMapClient.update(str, basecompinfo);
	}
	
	@Override
	public void insertBaseCompforIT(String str,
		BaseCompInfo basecompinfo) throws Exception {
		 sqlMapClient.insert(str, basecompinfo);
	}
	@Override
	public void insertcompany(String str,BaseCompInfo info) throws Exception {
		// TODO Auto-generated method stub
		sqlMapClient.insert(str, info);
	}

}
