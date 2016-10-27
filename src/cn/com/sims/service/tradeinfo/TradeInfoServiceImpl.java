package cn.com.sims.service.tradeinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sun.org.apache.xml.internal.security.Init;

import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.dao.tradeinfo.ITradeInfoDao;
import cn.com.sims.dao.viewtradeser.IViewTradeSerDao;
import cn.com.sims.model.viewtradeinfo.ViewTradeInfo;
import cn.com.sims.model.viewtradeser.ViewTradSer;
import cn.com.sims.service.sysuserinfo.SysUserInfoServiceImpl;

/** 
 * @author  yl
 * @date ：2015年10月16日 
 *  机构交易详情service
 */
@Service
public class TradeInfoServiceImpl implements ITradeInfoService{
	@Resource
	ITradeInfoDao tradeInfoDao;//投资机构交易详情dao层
	
	@Resource
	IViewTradeSerDao dao;//交易信息
	
	private static final Logger gs_logger = Logger
			.getLogger(TradeInfoServiceImpl.class);

	@Override
	public List<Map<String, String>> findTradeInfoByInvestorCode( HashMap<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("TradeInfoServiceImpl findTradeInfoByInvestorCode方法开始");
		List<Map<String, String>> viewTradeInfoList = null;//定义投资机构交易集合
		try{
			viewTradeInfoList = tradeInfoDao.findTradeInfoByInvestorCode("viewTradeInfo.findTradeInfoByInvestorCode",map);
			gs_logger.info("TradeInfoServiceImpl findTradeInfoByInvestorCode方法结束");
		}catch(Exception e){
			gs_logger.error("TradeInfoServiceImpl findTradeInfoByInvestorCode方法异常",e);
			throw e;
		}
		return viewTradeInfoList;
	}
/**
 * 根据投资人id查询投资人参与的交易信息总条数
 */
	@Override
	public int findTradeInfoCount(String code) throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("TradeInfoServiceImpl findTradeInfoCount方法开始");
		int totalCount = 0;//总条数
		try{
			totalCount = tradeInfoDao.findTradeInfoCount("viewTradeInfo.findTradeInfoCount",code);
			gs_logger.info("TradeInfoServiceImpl findTradeInfoCount方法结束");
		}catch(Exception e){
			gs_logger.error("TradeInfoServiceImpl findTradeInfoCount方法异常",e);
			throw e;
		}
		return totalCount;
	}
	
	public List findpageTradeInfoByCode(Map<String,Object> map) throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("TradeInfoServiceImpl findpageTradeInfoByCode方法开始");
		List<Map<String,Object>> tradeList = null;
		try{
			tradeList = tradeInfoDao.findpageTradeInfoByCode("viewTradeInfo.findpageTradeInfoByCode",map);
			gs_logger.info("TradeInfoServiceImpl findpageTradeInfoByCode方法结束");
		}catch(Exception e){
			gs_logger.error("TradeInfoServiceImpl findpageTradeInfoByCode方法异常",e);
			throw e;
		}
		return tradeList;
	}
	
	@Override
	public List<Map<String, Object>> tradesearchlist(HashMap<String, Object> map)
			throws Exception {
		gs_logger.info("TradeInfoServiceImpl tradesearchlist方法开始");
	List<Map<String, Object>> list=null;
	try {
		list=tradeInfoDao.tradesearchlist("ViewTradSer.tradesearchlist", map);
		gs_logger.info("TradeInfoServiceImpl tradesearchlist方法结束");
	} catch (Exception e) {
		gs_logger.error("TradeInfoServiceImpl tradesearchlist方法异常",e);
		throw e;
	}
		return list;
	}

	@Override
	public int tradesearchlist_num(
			HashMap<String, Object> map) throws Exception {
		gs_logger.info("TradeInfoServiceImpl tradesearchlist_num方法开始");
		int i=0;
		try {
			i=tradeInfoDao.tradesearchlist_num("ViewTradSer.tradesearchlist_num", map);
			gs_logger.info("TradeInfoServiceImpl tradesearchlist_num方法结束");
		} catch (Exception e) {
			gs_logger.error("TradeInfoServiceImpl tradesearchlist_num方法异常",e);
			throw e;
		}
		return i;
	}
	
	/**
	 * 根据交易code查询交易详情
	 * @param tradeCode交易Code
	 * @param page分页对象
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2015-10-23
	 */
	@Override
	public List<Map<String, String>> findTradeInfoByTradeCode(Page page,String tradeCode)
			throws Exception {
		gs_logger.info("TradeInfoServiceImpl findTradeInfoByTradeCode方法开始");
		List<Map<String, String>> info=null;
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map.put("pageSize", page.getPageSize()+"");
			map.put("startCount", page.getStartCount()+"");
			map.put("tradeCode", tradeCode);
			info=tradeInfoDao.findTradeInfoByTradeCode("viewTradeInfo.findTradeInfoByTradeCode", map);
		} catch (Exception e) {
			gs_logger.error("TradeInfoServiceImpl findTradeInfoByTradeCode方法异常",e);
			throw e;
		}
		gs_logger.info("TradeInfoServiceImpl findTradeInfoByTradeCode方法结束");
		return info;
	}
	
	
	
	/**
	 * 根据交易ID查询交易详情
	 * @param veiwTradeId交易ID
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2015-10-29
	 */
	@Override
	public ViewTradeInfo findViewTradeInfoByID(String veiwTradeId)
			throws Exception {
		gs_logger.info("TradeInfoServiceImpl findViewTradeInfoByID方法开始");
		ViewTradeInfo info=null;
		try {
			info=tradeInfoDao.findViewTradeInfoByID("viewTradeInfo.findViewTradeInfoByID", veiwTradeId);
		} catch (Exception e) {
			gs_logger.error("TradeInfoServiceImpl findViewTradeInfoByID方法异常",e);
			throw e;
		}
		gs_logger.info("TradeInfoServiceImpl findViewTradeInfoByID方法结束");
		return info;
	}
	@Override
	public int findTradeInfoCountByTradeCode(String tradeCode) throws Exception {
		gs_logger.info("TradeInfoServiceImpl findTradeInfoCountByTradeCode方法开始");
		int data=0;
		try {
			data=tradeInfoDao.findTradeInfoCountByTradeCode("viewTradeInfo.findTradeInfoCountByTradeCode", tradeCode);
		} catch (Exception e) {
			gs_logger.error("TradeInfoServiceImpl findTradeInfoCountByTradeCode方法异常",e);
			e.printStackTrace();
			throw e;
		}
		gs_logger.info("TradeInfoServiceImpl findTradeInfoCountByTradeCode方法结束");
		return data;
	}
	
	
	/**
	 * 根据code查询交易信息
	 * @param tradeCode
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2015-10-30
	 */
	@Override
	public ViewTradSer findTradeSerInfoByTradeCode(String tradeCode)
			throws Exception {
		gs_logger.info("TradeInfoServiceImpl findTradeSerInfoByTradeCode方法开始");
		ViewTradSer data=null;
		try {
			data=dao.findTradeSerInfoByTradeCode("ViewTradSer.findTradeSerInfoByTradeCode", tradeCode);
		} catch (Exception e) {
			gs_logger.error("TradeInfoServiceImpl findTradeSerInfoByTradeCode方法异常",e);
			e.printStackTrace();
			throw e;
		}
		gs_logger.info("TradeInfoServiceImpl findTradeSerInfoByTradeCode方法结束");
		return data;
	}
		@Override
	public int findTradeCount(String code) throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("TradeInfoServiceImpl findTradeCount方法开始");
		int totalCount = 0;//总条数
		try{
			totalCount = tradeInfoDao.findTradeCount("ViewTradSer.findTradeCount",code);
//			totalCount = tradeInfoDao.findTradeCount("viewTradeInfo.findTradeCount",code);
			
			gs_logger.info("TradeInfoServiceImpl findTradeCount方法结束");
		}catch(Exception e){
			gs_logger.error("TradeInfoServiceImpl findTradeCount方法异常",e);
			throw e;
		}
		return totalCount;
	}
		
		/**
		 * 跟句投资机构code查询交易信息数量
		 * @param orgCode 机构code
		 * @author duwenjie
		 * @date 2015-11-4
		 */
		@Override
		public int findCountTradeInfoByOrgCode(String orgCode) throws Exception {
			gs_logger.info("TradeInfoServiceImpl findCountTradeInfoByOrgCode 开始");
			int data=0;
			try {
				data=tradeInfoDao.findCountTradeInfoByOrgCode("viewTradeInfo.findCountTradeInfoByOrgCode", orgCode);
			} catch (Exception e) {
				gs_logger.error("TradeInfoServiceImpl findCountTradeInfoByOrgCode方法异常",e);
				e.printStackTrace();
				throw e;
			}
			gs_logger.info("TradeInfoServiceImpl findCountTradeInfoByOrgCode 结束");
			return data;
		}
		
		/**
		 * 根据投资机构code分页查询交易信息
		 * @param page 分页对象
		 * @param orgCode 机构code
		 * @author duwenjie
		 * @date 2015-11-4
		 */
		@Override
		public List<Map<String, String>> findPageTradeInfoByOrgCode(Page page,
				String orgCode) throws Exception {
			gs_logger.info("TradeInfoServiceImpl findPageTradeInfoByOrgCode 开始");
			List<Map<String, String>> list=null;
			HashMap<String, String> map = new HashMap<String, String>();
			try {
				map.put("pageSize", page.getPageSize()+"");
				map.put("startCount", page.getStartCount()+"");
				map.put("orgCode", orgCode);
				list=tradeInfoDao.findPageTradeInfoByOrgCode("viewTradeInfo.findPageTradeInfoByOrgCode", map);
			} catch (Exception e) {
				gs_logger.error("TradeInfoServiceImpl findPageTradeInfoByOrgCode 异常",e);
				e.printStackTrace();
				throw e;
			}
			gs_logger.info("TradeInfoServiceImpl findPageTradeInfoByOrgCode 结束");
			return list;
		}
		@Override
		public Map<String, String> trade_investment(String code)
				throws Exception {
			gs_logger.info("TradeInfoServiceImpl trade_investment 开始");
			Map<String, String> map=null;
			try {
				map=tradeInfoDao.trade_investment("viewTradeInfo.trade_investment", code);
			} catch (Exception e) {
				gs_logger.error("TradeInfoServiceImpl trade_investment 异常",e);
				throw e;
			}
			gs_logger.info("TradeInfoServiceImpl trade_investment 结束");
			return map;
		}
		@Override
		public int investor_notin_tradser(HashMap<String, String> map)
				throws Exception {
			gs_logger.info("TradeInfoServiceImpl investor_notin_tradser 开始");
			int data=0;
			try {
				data=tradeInfoDao.investor_notin_tradser("viewTradeInfo.investor_notin_tradser", map);
			} catch (Exception e) {
				gs_logger.error("TradeInfoServiceImpl investor_notin_tradser方法异常",e);
				e.printStackTrace();
				throw e;
			}
			gs_logger.info("TradeInfoServiceImpl investor_notin_tradser 结束");
			return data;
		}
		@Override
		public List<Map<String, String>> findPageTradeInfoByDate(String date, String orgCode) throws Exception {
			gs_logger.info("TradeInfoServiceImpl findPageTradeInfoByDate 开始");
			List<Map<String, String>> list=null;
			HashMap<String, String> map = new HashMap<String, String>();
			try {
				map.put("date", date);
				map.put("orgCode", orgCode);
				list=tradeInfoDao.findPageTradeInfoByDate("viewTradeInfo.findPageTradeInfoBydate", map);
			} catch (Exception e) {
				gs_logger.error("TradeInfoServiceImpl findPageTradeInfoByDate方法异常",e);
				e.printStackTrace();
				throw e;
			}
			gs_logger.info("TradeInfoServiceImpl findPageTradeInfoByDate 结束");
			return list;
		}

}
