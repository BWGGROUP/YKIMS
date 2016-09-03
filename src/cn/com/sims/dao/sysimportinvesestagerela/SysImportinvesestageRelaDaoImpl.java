package cn.com.sims.dao.sysimportinvesestagerela;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 
 * @author RQQ
 *@date 2015-11-30
 */
@Service
public class SysImportinvesestageRelaDaoImpl implements SysImportinvesestageRelaDao{
	@Resource
	private SqlMapClient sqlMapClient;

	@Override
	public String queryinvesstageforIT(String str,
		String labelelementname) throws Exception {
	    return (String)sqlMapClient.queryForObject(str, labelelementname);
	}
	
}
