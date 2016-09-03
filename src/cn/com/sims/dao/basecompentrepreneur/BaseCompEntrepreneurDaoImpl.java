package cn.com.sims.dao.basecompentrepreneur;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

import cn.com.sims.model.basecompentrepreneur.BaseCompEntrepreneur;

/**
 * 
 * @author rqq
 * @date 2015-10-23
 */

@Service
public class BaseCompEntrepreneurDaoImpl implements IBaseCompEntrepreneurDao{

	@Resource
	private SqlMapClient sqlMapClient;
	
	@Override
	public BaseCompEntrepreneur queryCompEntrepreneurbycodeforit(String str,Map<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		return (BaseCompEntrepreneur)sqlMapClient.queryForObject(str, map);
	}
	
	@Override
	public void insertCompEntrepreneurforit(String str,BaseCompEntrepreneur basecompentrepreneur)
			throws Exception {
		// TODO Auto-generated method stub
		sqlMapClient.insert(str, basecompentrepreneur);
	}
	
	@Override
	public void updateCompEntrepreneurforit(String str,BaseCompEntrepreneur basecompentrepreneur)
			throws Exception {
		// TODO Auto-generated method stub
		 sqlMapClient.update(str, basecompentrepreneur);
	}
	

}
