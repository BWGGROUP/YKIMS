package cn.com.sims.dao.basemeetingrele;



import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

import cn.com.sims.model.basemeetingrele.BaseMeetingRele;

/**
 * 
 * @author rqq
 * @date 2015-11-09 
 */

@Service
public class BaseMeetingReleDaoImpl implements IBaseMeetingReleDao{

	@Resource
	private SqlMapClient sqlMapClient;

	@Override
	public void addmeetingrele(String str, BaseMeetingRele basemeetingrele)
		throws Exception {
	    sqlMapClient.insert(str, basemeetingrele);
	    
	}

	@Override
	public void deleteMeetingReleByCode(String str, String meetingcode)
			throws Exception {
		sqlMapClient.delete(str,meetingcode);
		
	}

	@Override
	public int deleteMeetingReleByType(String str, Map<String, String> map)
			throws Exception {
		return sqlMapClient.delete(str,map);
	}


	


	
	
}
