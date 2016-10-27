package cn.com.sims.dao.syswadinfo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 
 * @author rqq
 * @date 2015-11-09
 */
@Service
public class SysWadInfoDaoImpl implements SysWadInfoDao{
	@Resource
	private SqlMapClient sqlMapClient;
	@Override
	public List<Map<String,String>> querysyswadinfo(String str) throws Exception {
		return sqlMapClient.queryForList(str);
	}
}
