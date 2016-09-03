package cn.com.sims.dao.basemeetinginfo;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

import cn.com.sims.model.basemeetinginfo.BaseMeetingInfo;

/**
 * 
 * @author rqq
 * @date 2015-11-09 
 */

@Service
public class BaseMeetingInfoDaoImpl implements IBaseMeetingInfoDao{

	@Resource
	private SqlMapClient sqlMapClient;

	@Override
	public void addmeetinginfo(String str, BaseMeetingInfo basemeetinginfo)
		throws Exception {
	    sqlMapClient.insert(str,basemeetinginfo);
	    
	}

	@Override
	public void deleteMeetingInfoByCode(String str, String meetingcode)
			throws Exception {
		sqlMapClient.delete(str, meetingcode);
		
	}

	@Override
	public int updateBaseMeetingInfo(String str, BaseMeetingInfo info)
			throws Exception {
		return sqlMapClient.update(str, info);
	}

	@Override
	public BaseMeetingInfo findBaseMeetingInfo(String str, String meetingcode)
			throws Exception {
		return (BaseMeetingInfo) sqlMapClient.queryForObject(str,meetingcode);
	}
	
	
}
