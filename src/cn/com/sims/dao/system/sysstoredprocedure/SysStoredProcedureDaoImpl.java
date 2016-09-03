package cn.com.sims.dao.system.sysstoredprocedure;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

import cn.com.sims.model.sysstoredprocedure.SysStoredProcedure;


/**
 * 存储过程实现
 * @author duwenjie
 * @date 2015-10-14
 * 
 */

@Service
public class SysStoredProcedureDaoImpl implements ISysStoredProcedureDao{

	@Resource
	private SqlMapClient sqlMapClient;
	
	@Override
	public SysStoredProcedure callViewinvestment(String str,SysStoredProcedure info) throws Exception {
		return (SysStoredProcedure) sqlMapClient.insert(str, info);
	}

	@Override
	public SysStoredProcedure callViewinvestorInfo(String str,
			SysStoredProcedure info) throws Exception {
		// TODO Auto-generated method stub
		return (SysStoredProcedure) sqlMapClient.insert(str, info);
	}

	@Override
	public SysStoredProcedure callViewTradeser(String str,
			SysStoredProcedure info) throws Exception {
		return (SysStoredProcedure) sqlMapClient.insert(str, info);
	}

	@Override
	public SysStoredProcedure callViewTradeInfo(String str,
			SysStoredProcedure info) throws Exception {
		return (SysStoredProcedure) sqlMapClient.insert(str, info);
	}

	@Override
	public SysStoredProcedure callViewcompinfo(String str,
			SysStoredProcedure info) throws Exception {
		// TODO Auto-generated method stub
		return (SysStoredProcedure) sqlMapClient.insert(str, info);
	}

}
