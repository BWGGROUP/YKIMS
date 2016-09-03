package cn.com.sims.dao.company.companydetail;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sims.model.basecompentrepreneur.BaseCompEntrepreneur;
import cn.com.sims.model.basecompinfo.BaseCompInfo;
import cn.com.sims.model.basecompnoteinfo.BaseCompnoteInfo;
import cn.com.sims.model.baseentrepreneurinfo.BaseEntrepreneurInfo;
import cn.com.sims.model.basefinancplanemail.BaseFinancplanEmail;
import cn.com.sims.model.basefinancplaninfo.BaseFinancplanInfo;
import cn.com.sims.model.viewcompinfo.viewCompInfo;

import com.ibatis.sqlmap.client.SqlMapClient;

/** 
 * @author  yl
 * @date ：2015年10月27日 
 * 公司详情dao
 */
@Service
public class CompanyDetailDaoImpl implements CompanyDetailDao{
	@Resource
	private SqlMapClient sqlMapClient;
/**
 * 根据公司ｉｄ查询公司信息
 */
	@Override
	public viewCompInfo findCompanyDeatilByCode(String str, String code)
			throws Exception {
		// TODO Auto-generated method stub
		return (viewCompInfo)sqlMapClient.queryForObject(str,code);
	}
@Override
public List<Map<String, String>> findCompanyNoteByCode(String str, String code)
		throws Exception {
	// TODO Auto-generated method stub
	return sqlMapClient.queryForList(str,code);
}
@Override
public List<Map<String, String>> findFinancplanByCode(String str, String code)
		throws Exception {
	// TODO Auto-generated method stub
	return sqlMapClient.queryForList(str,code);
}
@Override
public List<Map<String, String>> findFinancByCode(String str,Map<String, Object> map)
		throws Exception {
	// TODO Auto-generated method stub
	return sqlMapClient.queryForList(str,map);
}
@Override
public long findVersionByCode(String str, String code) throws Exception {
	// TODO Auto-generated method stub
	Long kk=(Long)sqlMapClient.queryForObject(str,code);
	if(kk==null){
		kk=(long)0;
	}
	return kk;
}
@Override
public List<Map<String, String>> findCompanyPeopleByCode(String str, String code)
		throws Exception {
	// TODO Auto-generated method stub
	return sqlMapClient.queryForList(str,code);
}
@Override
public int tranModifyCompName(String str, BaseCompInfo view)
		throws Exception {
	// TODO Auto-generated method stub
	return sqlMapClient.update(str, view);
}
@Override
public int tranModifyCompPeople(String str, BaseEntrepreneurInfo view)
		throws Exception {
	// TODO Auto-generated method stub
	return sqlMapClient.update(str, view);
}
@Override
public void insertCompPeople(String str, BaseEntrepreneurInfo view)
		throws Exception {
	// TODO Auto-generated method stub
	sqlMapClient.insert(str, view);
}
@Override
public int updateCompany(String str, BaseCompEntrepreneur view)
		throws Exception {
	// TODO Auto-generated method stub
	return sqlMapClient.update(str, view);
}
@Override
public void insertComp_people(String str, BaseCompEntrepreneur view)
		throws Exception {
	// TODO Auto-generated method stub
	sqlMapClient.insert(str, view);
}
@Override
public void insertCompNote(String str, BaseCompnoteInfo info) throws Exception {
	// TODO Auto-generated method stub
	sqlMapClient.insert(str, info);
}
@Override
public int compnote_del(String str, String code) throws Exception {
	// TODO Auto-generated method stub
	return sqlMapClient.update(str, code);
}
@Override
public void insertFinancplan(String str, BaseFinancplanInfo baseFinancplanInfo)
		throws Exception {
	// TODO Auto-generated method stub
	sqlMapClient.insert(str, baseFinancplanInfo);
}
@Override
public void insertPlanEamil(String str, BaseFinancplanEmail BaseFinancplanEmail)
		throws Exception {
	// TODO Auto-generated method stub
	sqlMapClient.insert(str, BaseFinancplanEmail);
}
@Override
public String findNameByCode(String str, String code) throws Exception {
	// TODO Auto-generated method stub
	return (String)sqlMapClient.queryForObject(str, code);
}
@Override
public int findCountSizeByName(String str, Map<String, Object> map)
		throws Exception {
	// TODO Auto-generated method stub
	return (Integer)sqlMapClient.queryForObject(str, map);
}
@Override
public List findEnterByName(String str, Map<String, Object> map)
		throws Exception {
	// TODO Auto-generated method stub
	return sqlMapClient.queryForList(str, map);
}
@Override
public int selectName(String str, String name) throws Exception {
	// TODO Auto-generated method stub
	return (Integer)sqlMapClient.queryForObject(str, name);
}
@Override
public int selectPeople(String str, Map map) throws Exception {
	// TODO Auto-generated method stub
	return (Integer)sqlMapClient.queryForObject(str, map);
}
@Override
public int querycompanylistnumByname(String str, Map<String, Object> map)
	throws Exception {
	return (Integer)sqlMapClient.queryForObject(str,map);
}
@Override
public List<viewCompInfo> querycompanylistByname(String str,
	Map<String, Object> map) throws Exception {
	return sqlMapClient.queryForList(str,map);
}
@Override
public int queryCompOnlyNamefortrade(String string, String name) throws Exception {
    return (Integer)sqlMapClient.queryForObject(string,name);
}
@Override
public String findInvestNameByTrad(String str, String code) throws Exception {
	// TODO Auto-generated method stub
	return (String)sqlMapClient.queryForObject(str,code);
}
}
