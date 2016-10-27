package cn.com.sims.dao.baseinveslabelinfo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;

import cn.com.sims.model.baseinveslabelinfo.BaseInveslabelInfo;

/**
 * 
 * @author duwenjie
 * @date 2015-10-13
 */

@Service
public class BaseInveslabelInfoDaoImpl implements IBaseInveslabelInfoDao{

	@Resource
	private SqlMapClient sqlMapClient;
	
	@Override
	public void insertOrgInveslabelInfo(String str, BaseInveslabelInfo info)
			throws Exception {
		sqlMapClient.insert(str, info);		

	}

	@Override
	public void tranDeleteInveslabel(String str, BaseInveslabelInfo info)
			throws Exception {
		sqlMapClient.delete(str, info);
	}
	@Override
	public void insertBaseInveslabelInfoforit(String str,BaseInveslabelInfo baseinveslabelinfo)
			throws Exception {
		sqlMapClient.insert(str, baseinveslabelinfo);		

	}

	/**
	 * 根据投资机构code查询投资机构交易记录标签信息
	 * @param str
	 * @param orgCode机构Code
	 * @return sys_labelelement_code,sys_label_code集合
	 * @throws Exception
	 * @author duwenjie
	 */
	@Override
	public List<Map<String, String>> findTradeLabelByOrgCode(String str,
			String orgCode) throws Exception {
		return sqlMapClient.queryForList(str, orgCode);
	}
}