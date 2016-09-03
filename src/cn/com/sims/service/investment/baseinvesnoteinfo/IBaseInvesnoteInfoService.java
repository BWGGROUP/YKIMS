package cn.com.sims.service.investment.baseinvesnoteinfo;

import java.util.List;

import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.model.baseinvesnoteinfo.BaseInvesnoteInfo;

/**
 * 
 * @author shbs-tp004 duwenjie
 * 2015-10-09
 */
public interface IBaseInvesnoteInfoService {

	/**
	 * 添加投资机构note
	 * @param noteInfo
	 * @throws Exception
	 */
	public void insertOrgNote(BaseInvesnoteInfo noteInfo) throws Exception;

	
	/**
	 * 根据ID查询投资机构note
	 * @param str
	 * @param 投资机构ID
	 * @return
	 * @throws Exception
	 */
	public List<BaseInvesnoteInfo> findInvesnoteByOrgId(String orgCode) throws Exception;
	
	/**
	 * 根据code查询投资机构备注数量
	 * @param orgCode投资机构code
	 * @return
	 * @throws Exception
	 */
	public int findCountByOrgId(String orgCode) throws Exception;
	
	/**
	 * 根据ID分页查询投资机构note
	 * @param str
	 * @param 投资机构code
	 * @return
	 * @throws Exception
	 */
	public List<BaseInvesnoteInfo> findPageInvesnoteByOrgId(String orgCode,Page page) throws Exception;
	
	
	/**
	 * 根据投资机构备注code删除
	 * @param noteCode投资机构备注code
	 * @throws Exception
	 */
	public int tranDeleteInvestmentNote(String noteCode)throws Exception; 
	
	
	
	
}
