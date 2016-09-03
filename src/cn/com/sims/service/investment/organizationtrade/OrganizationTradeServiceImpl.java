package cn.com.sims.service.investment.organizationtrade;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.dao.Investment.organizationtrade.IOrganizationTradeDao;
import cn.com.sims.model.vieworganizationtrade.ViewOrganizationTrade;

@Service
public class OrganizationTradeServiceImpl implements IOrganizationTradeService {

	@Resource
	IOrganizationTradeDao dao;
	
	private static final Logger gs_logger = Logger.getLogger(OrganizationTradeServiceImpl.class);
	
	/**
	 * 根据机构ID查询机构交易详情(业务层)
	 * @param 投资机构id
	 * @throws Exception
	 * @author duwenjie
	 * 2015-09-25
	 */
	@Override
	public List<ViewOrganizationTrade> findTradeByOrganizationId(
			String organizationId) throws Exception {
		gs_logger.info("findTradeByOrganizationId方法开始");
		List<ViewOrganizationTrade> list = null;
		try {
			list= dao.findTradeByOrganizationId("ViewOrganizationTrade.findTradeByOrganizationId", organizationId); 
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("findTradeByOrganizationId方法异常",e);
			throw e;
		}
		gs_logger.info("findTradeByOrganizationId方法结束");
		return list;
	}

	@Override
	public List<ViewOrganizationTrade> findPageTradeByOrgId(String orgCode,
			Page page) throws Exception {
		gs_logger.info("findPageTradeByOrgId方法开始");
		List<ViewOrganizationTrade> list = null;
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map.put("pageSize", page.getPageSize()+"");
			map.put("startCount", page.getStartCount()+"");
			map.put("orgCode", orgCode);
			list= dao.findPageTradeByOrgId("ViewOrganizationTrade.findPageTradeByOrgId", map); 
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("findPageTradeByOrgId方法异常",e);
			throw e;
		}
		gs_logger.info("findPageTradeByOrgId方法结束");
		return list;
	}
	
	@Override
	public int findCountTradeByOrgId(String orgCode) throws Exception {
		gs_logger.info("findCountTradeByOrgId方法开始");
		int totalCount=0;
		try {
			totalCount=dao.findCountTradeByOrgId("ViewOrganizationTrade.findCountTradeByOrgId", orgCode);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("findCountTradeByOrgId方法异常",e);
			throw e;
		}
		gs_logger.info("findCountTradeByOrgId方法结束");
		return totalCount;
	}
}