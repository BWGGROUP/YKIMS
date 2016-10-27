package cn.com.sims.dao.basemeetinglabelinfo;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sims.model.basemeetinglabelinfo.BaseMeetingLabelInfo;

import com.ibatis.sqlmap.client.SqlMapClient;

@Service
public class BaseMeetinglabelInfoDaoImpl implements BaseMeetinglabelInfoDao {

	@Resource
	private SqlMapClient sqlMapClient;

	@Override
	public void insertBaseMeetingLabelInfo(String str, BaseMeetingLabelInfo info)
			throws Exception {
		sqlMapClient.insert(str, info);
		
	}

	@Override
	public int deleteBaseMeetingLabelInfo(String str, Map<String, String> map)
			throws Exception {
		return sqlMapClient.delete(str, map);
	}
	
	
}
