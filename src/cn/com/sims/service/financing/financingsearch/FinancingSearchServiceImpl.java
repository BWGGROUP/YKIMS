package cn.com.sims.service.financing.financingsearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.model.basefinancplaninfo.BaseFinancplanInfo;
import cn.com.sims.dao.financing.financingsearch.FinancingSearchDao;
@Service
public class FinancingSearchServiceImpl implements FinancingSearchService{
	@Resource
	FinancingSearchDao financingSearchDao;
	private static final Logger gs_logger = Logger.getLogger(FinancingSearchServiceImpl.class);
	@Override
	public List<BaseFinancplanInfo> financingsearch(String companycode,
			String timestamp, Page page) throws Exception {
		gs_logger.info("FinancingSearchServiceImpl financingsearch方法开始");
		HashMap<String, Object> map=new HashMap<String, Object>();
		if ("".equals(companycode)) {
			companycode=null;
		}
		if ("".equals(timestamp)) {
			timestamp=null;
		}
		map.put("companycode", companycode);
		map.put("timestamp", timestamp);
		map.put("startCount", page.getStartCount());
		map.put("pageSize", page.getPageSize());
		List<BaseFinancplanInfo> list;
		try {
			list=financingSearchDao.financingsearch("BaseFinancplanInfo.financingsearchlist", map);
			gs_logger.info("FinancingSearchServiceImpl financingsearchlist方法结束");
		} catch (Exception e) {
			gs_logger.error("FinancingSearchServiceImpl financingsearchlist方法异常",e);
			throw e;
		}
		return list;
	}

	@Override
	public int financingsearch_pagetotal(String companycode, String timestamp)
			throws Exception {
		gs_logger.info("FinancingSearchServiceImpl financingsearch_pagetotal方法开始");
		HashMap<String, Object> map=new HashMap<String, Object>();
		if ("".equals(companycode)) {
			companycode=null;
		}
		if ("".equals(timestamp)) {
			timestamp=null;
		}
		map.put("companycode", companycode);
		map.put("timestamp", timestamp);
		int i=0;
		try {
			 i=financingSearchDao.financingsearch_pagetotal("BaseFinancplanInfo.financingsearch_pagetotal", map);
			gs_logger.info("FinancingSearchServiceImpl financingsearch_pagetotal方法结束");
		} catch (Exception e) {
			gs_logger.error("FinancingSearchServiceImpl financingsearch_pagetotal方法异常",e);
			throw e;
		}
	
		return i;
	}

	/**
	 * 查询用户创建融资计划数量
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	@Override
	public int findFincingCountByUsercode(String usercode) throws Exception {
		gs_logger.info("findFincingCountByUsercode方法开始");
		int data=0;
		try {
			data=financingSearchDao.findFincingCountByUsercode("BaseFinancplanInfo.findFincingCountByUsercode", usercode);
		} catch (Exception e) {
			gs_logger.error("findFincingCountByUsercode方法开始",e);
			throw e;
		}
		
		gs_logger.info("findFincingCountByUsercode方法结束");
		return data;
	}

	/**
	 * 查询用户创建融资计划
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> findFincingByUsercode(
			Map<String, Object> map) throws Exception {
		gs_logger.info("findFincingByUsercode方法开始");
		List<Map<String, Object>> data=null;
		try {
			data=financingSearchDao.findFincingByUsercode("BaseFinancplanInfo.findFincingByUsercode", map);
		} catch (Exception e) {
			gs_logger.error("findFincingByUsercode方法开始",e);
			throw e;
		}
		
		gs_logger.info("findFincingByUsercode方法结束");
		return data;
	}

	

}
