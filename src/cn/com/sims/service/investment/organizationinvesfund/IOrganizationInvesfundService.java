package cn.com.sims.service.investment.organizationinvesfund;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.model.baseinvesfundinfo.BaseInvesfundInfo;

/**
 * 
 * @author shbs-tp004 duwenjie
 *
 */
@Service
public interface IOrganizationInvesfundService {

	/**
	 * 根据机构ＩＤ查询机构基金信息（基础层）
	 * @param 机构ｉｄ
	 * @return　基金集合
	 * @throws Exception
	 */
	public List<BaseInvesfundInfo> findInvesfundByOrganizationId(String orgCode) throws Exception;

	/**
	 * 查询投资机构基金总数
	 * @param orgCode投资机构code
	 * @return
	 * @throws Exception
	 */
	public int findCountTfundByOrgId(String orgCode)throws Exception;
	
	/**
	 * 根据机构Code分页查询机构基金信息（基础层）
	 * @param 机构code
	 * @return　基金集合
	 * @throws Exception
	 */
	public List<BaseInvesfundInfo> findPageInvesfundByOrgId(String orgCode,Page page) throws Exception;

	/**
	 * 添加投资机构基金信息
	 * @param str
	 * @param 新增投资机构基金对象
	 * @throws Exception
	 * 
	 * 2015-11-26 修改 duwenjie
	 */
	public void tranSaveInsertInvesfund(BaseInvesfundInfo info,String userId) throws Exception;
	
	public int tranModifyInvesfund(BaseInvesfundInfo info,String userId) throws Exception;
	
	public int tranDeleteInvesfund(String investmentCode,String fundCode,String userId) throws Exception;
}
