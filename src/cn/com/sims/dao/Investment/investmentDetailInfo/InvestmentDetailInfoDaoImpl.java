package cn.com.sims.dao.Investment.investmentDetailInfo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sims.model.baseinvesfundinfo.BaseInvesfundInfo;
import cn.com.sims.model.baseinvesnoteinfo.BaseInvesnoteInfo;
import cn.com.sims.model.viewInvestmentInfo.viewInvestmentInfo;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 
 * @author duwenjie
 *2015-09-25
 */
@Service
public class InvestmentDetailInfoDaoImpl implements IInvestmentDetailInfoDao {
	@Resource
	private SqlMapClient sqlMapClient;

	/**
	 * 根据ID查询投资机构详情
	 * @param str
	 * @param 投资机构ID
	 * @return
	 * @throws Exception
	 */
	@Override
	public viewInvestmentInfo findInvestmentDetailById(String str,String id) throws Exception {
		return (viewInvestmentInfo) sqlMapClient.queryForObject(str, id);
	}

	/**
	 * 修改投资机构删除标识
	 * @param str
	 * @param map（orgcode:机构code，del:删除标识（0：正常，1：删除））
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateViewOrgDel(String str, Map<String, Object> map)
			throws Exception {
		return sqlMapClient.update(str, map);
	}


}