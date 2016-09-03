package cn.com.sims.service.investment.organizationtrade;

import java.util.List;
import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.model.vieworganizationtrade.ViewOrganizationTrade;

/**
 * 
 * @author duwenjie
 *
 */
public interface IOrganizationTradeService {
	/**
	 * 根据机构ID查询机构交易详情(业务层)
	 * @param 投资机构id
	 * @throws Exception
	 * @author duwenjie
	 * 2015-09-25
	 */
	public List<ViewOrganizationTrade> findTradeByOrganizationId(String organizationId) throws Exception;
	
	/**
	 * 根据机构id分页查询机构交易记录
	 * @param str
	 * @param 分页page
	 * @return
	 * @throws Exception
	 * 2015-10-8
	 */
	public List<ViewOrganizationTrade> findPageTradeByOrgId(String orgCode,Page page) throws Exception;
	
	/**
	 * 根据机构id查询机构交易记录总条数
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	public int findCountTradeByOrgId(String orgCode) throws Exception;
	
}
