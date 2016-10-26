package cn.com.sims.dao.basecomplabelinfo;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

import cn.com.sims.model.basecomplabelinfo.BaseComplabelInfo;
import cn.com.sims.model.baseinveslabelinfo.BaseInveslabelInfo;

/**
 * 
 * @author rqq
 * @date 2015-10-23
 */

@Service
public class BaseComplabelInfoDaoImpl implements IBaseComplabelInfoDao{

	@Resource
	private SqlMapClient sqlMapClient;
	
	@Override
	public void insertBaseComplabelInfoforit(String str,BaseComplabelInfo basecomplabelinfo)
			throws Exception {
		sqlMapClient.insert(str, basecomplabelinfo);		

	}


	@Override
	public void insertComplabelInfo(String str, BaseComplabelInfo info)
			throws Exception {
		// TODO Auto-generated method stub
		sqlMapClient.insert(str, info);		
	}

	@Override
	public void tranDeleteComplabel(String str, BaseComplabelInfo info)
			throws Exception {
		// TODO Auto-generated method stub
			sqlMapClient.delete(str, info);
	}
}
