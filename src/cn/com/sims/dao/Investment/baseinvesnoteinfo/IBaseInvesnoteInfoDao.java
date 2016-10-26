package cn.com.sims.dao.Investment.baseinvesnoteinfo;

import java.util.List;
import java.util.Map;

import cn.com.sims.model.baseinvesnoteinfo.BaseInvesnoteInfo;
/**
 * 
 * @author shbs-tp004 duwenjie
 *
 */
public interface IBaseInvesnoteInfoDao {


	/**
	 * 根据ID查询投资机构note
	 * @param str
	 * @param 投资机构ID
	 * @return
	 * @throws Exception
	 */
	public List<BaseInvesnoteInfo> findInvesnoteByOrgId(String str,String id) throws Exception;
	
	/**
	 * 添加投资机构备注
	 * @param 新增备注noteInfo
	 * @throws Exception
	 */
	public void insertOrgNote(String str,BaseInvesnoteInfo noteInfo) throws Exception;

	/**
	 * 根据code查询投资机构备注数量
	 * @param orgCode投资机构code
	 * @return
	 * @throws Exception
	 */
	public int findCountByOrgId(String str,String orgCode) throws Exception;
	
	/**
	 * 根据ID分页查询投资机构note
	 * @param str
	 * @param 投资机构ID
	 * @return
	 * @throws Exception
	 */
	public List<BaseInvesnoteInfo> findPageInvesnoteByOrgId(String str,Map<String, String> map) throws Exception;
	
	
	/**
	 * 根据投资机构备注code删除
	 * @param str
	 * @param noteCode
	 * @throws Exception
	 */
	public int tranDeleteInvestmentNote(String str,String noteCode)throws Exception;
}
