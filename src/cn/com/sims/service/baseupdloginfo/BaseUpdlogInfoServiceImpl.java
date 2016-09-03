package cn.com.sims.service.baseupdloginfo;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.dao.Investment.InvestmentDao;
import cn.com.sims.dao.baseupdloginfo.IBaseUpdlogInfoDao;
import cn.com.sims.dao.financing.financingsearch.FinancingSearchDao;
import cn.com.sims.dao.viewmeetingrele.IViewMeetingReleDao;
import cn.com.sims.model.baseupdloginfo.BaseUpdlogInfo;

/**
 * 
 * @author shbs-tp004 duwenjie
 * 2010-10-10
 */
@Service
public class BaseUpdlogInfoServiceImpl implements IBaseUpdlogInfoService {

	@Resource
	IBaseUpdlogInfoDao dao;
	
	@Resource
	InvestmentDao investmentDao;
	
	@Resource
	IViewMeetingReleDao meetingReleDao;
	
	@Resource
	FinancingSearchDao financingSearchDao;
	
	private static final Logger gs_logger = Logger.getLogger(BaseUpdlogInfoServiceImpl.class);
	
	@Override
	public void insertUpdlogInfo(String	base_updlog_type,
			String	base_updlog_typename,
			String	base_ele_code,
			String	base_ele_name,
			String	base_updlog_opertype,
			String	sys_user_code,
			String	sys_user_name,
			String	base_updlog_opertypecode,
			String	base_updlog_opertypename,
			String	base_updlog_opercont,
			String	base_updlog_oridata,
			String	base_updlog_newdata,
			String	operaflag,
			String	createid,
			Timestamp createtime,
			String	updid,
			Timestamp	updtime	) throws Exception {
		gs_logger.info("addInvesnoteInfo方法开始");
		try {
			BaseUpdlogInfo updlogInfo=new BaseUpdlogInfo();
			updlogInfo.setBase_updlog_type(base_updlog_type);//操作模块类型
			updlogInfo.setBase_updlog_typename(base_updlog_typename);//模块类型名称
			updlogInfo.setBase_ele_code(base_ele_code);//模块的业务code
			updlogInfo.setBase_ele_name(base_ele_name);//模块的业务名称
			updlogInfo.setBase_updlog_opertype(base_updlog_opertype);//操作者类型
			updlogInfo.setSys_user_code(sys_user_code);//操作者
			updlogInfo.setSys_user_name(sys_user_name);//操作者名称
			updlogInfo.setBase_updlog_opertypecode(base_updlog_opertypecode);//操作类型code
			updlogInfo.setBase_updlog_opertypename(base_updlog_opertypename);//操作显示内容
			updlogInfo.setBase_updlog_opercont(base_updlog_opercont);//操作内容
			updlogInfo.setBase_updlog_oridata(base_updlog_oridata);//原数据
			updlogInfo.setBase_updlog_newdata(base_updlog_newdata);//更改后数据
			updlogInfo.setOperaflag(operaflag);//操作设备
			updlogInfo.setCreateid(createid);//创建者
			updlogInfo.setCreatetime(createtime);//创建时间
			updlogInfo.setUpdid(updid);//更新者
			updlogInfo.setUpdtime(updtime);//更新时间
			
			dao.insertUpdlogInfo("BaseUpdlogInfo.insertUpdlogInfo", updlogInfo);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("addInvesnoteInfo方法异常",e);
			throw e;
		}finally{
			gs_logger.info("addInvesnoteInfo方法结束");
		}
		
	}

	@Override
	public List<BaseUpdlogInfo> findPageUpdlogInfo(Page page,String type, String code)
			throws Exception {
		gs_logger.info("findPageUpdlogInfo方法开始");
		List<BaseUpdlogInfo> list=null;
		Map<String, String>  map = new HashMap<String, String>();
		try {
			
			map.put("pageSize", page.getPageSize()+"");
			map.put("startCount", page.getStartCount()+"");
			map.put("code", code);
			map.put("type", type);
			list=dao.findPageUpdlogInfo("BaseUpdlogInfo.findPageUpdlogInfo", map);
		}catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("findPageUpdlogInfo方法异常",e);
			throw e;
		}finally{
			gs_logger.info("findPageUpdlogInfo方法结束");
		}
		return list;
	}

	@Override
	public int findPageUpdlogCount(String code, String type) throws Exception {
		gs_logger.info("findPageUpdlogCount方法开始");
		int data=0;
		Map<String, String> map = new HashMap<String, String>();
		try {
			map.put("code", code);
			map.put("type", type);
			data=dao.findPageUpdlogCount("BaseUpdlogInfo.findPageUpdlogCount", map);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("findPageUpdlogCount方法异常",e);
			throw e;
		}finally{
			gs_logger.info("findPageUpdlogCount方法结束");
		}
		return data;
	}

	/**
	 * 条件查询用户操作足迹数量
	 * @param str
	 * @param map(logtype:标签类型{Lable-investment,Lable-trading...} ,opertype:操作类型{YK-CRE,YK-UPD...}，usercode：用户code)
	 * @return
	 * @throws Exception
	 */
	@Override
	public int findUpdByupdlogUserCount(Map<String, Object> map)
			throws Exception {
		gs_logger.info("findOrgByupdlogUserCount方法开始");
		int data=0;
		try {
			data=dao.findUpdByupdlogUserCount("BaseUpdlogInfo.findUpdByupdlogUserCount", map);
		} catch (Exception e) {
			gs_logger.error("findOrgByupdlogUserCount方法异常",e);
			throw e;
		}
		gs_logger.info("findOrgByupdlogUserCount方法结束");
		return data;
	}
	
	/**
	 * 条件查询用户操作交易足迹数量
	 * @param str
	 * @param map(logtype:标签类型{Lable-trading} ,opertype:操作类型{YK-CRE,YK-UPD...}，usercode：用户code)
	 * @return
	 * @throws Exception
	 */
	@Override
	public int findTradeCountByupdlogUser(Map<String, Object> map)
			throws Exception {
		gs_logger.info("findTradeCountByupdlogUser方法开始");
		int data=0;
		try {
			data=dao.findUpdByupdlogUserCount("BaseUpdlogInfo.findTradeCountByupdlogUser", map);
		} catch (Exception e) {
			gs_logger.error("findTradeCountByupdlogUser方法异常",e);
			throw e;
		}
		gs_logger.info("findTradeCountByupdlogUser方法结束");
		return data;
	}

	/**
	 * 条件查询用户机构操作足迹信息
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> findOrgByupdlogUser(Map<String, Object> map)
			throws Exception {
		gs_logger.info("findOrgByupdlogUser方法开始");
		List<Map<String, Object>> list=null;
		try {
			list=dao.findOrgByupdlogUser("BaseUpdlogInfo.findOrgByupdlogUser", map);
			if(list!=null){
				Map<String, Object> comMap=null;
				for (Map<String, Object> upMap : list) {
					comMap=new HashMap<String, Object>();
					comMap.put("base_investment_code", upMap.get("code"));
					comMap.put("lableinduList", null);
					String companyName = investmentDao.findCompanyRe(
							"viewInvestmentInfo.findCompanyRe", comMap);
					upMap.put("companyName", companyName);
				}
			}
		} catch (Exception e) {
			gs_logger.error("findOrgByupdlogUser方法异常",e);
			throw e;
		}
		gs_logger.info("findOrgByupdlogUser方法结束");
		return list;
	}

	/**
	 * 查询用户交易足迹信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> findTradeByupdlogUser(
			Map<String, Object> map) throws Exception {
		gs_logger.info("findTradeByupdlogUser方法开始");
		List<Map<String, Object>> list=null;
		try {
			list=dao.findTradeByupdlogUser("BaseUpdlogInfo.findTradeByupdlogUser", map);
		} catch (Exception e) {
			gs_logger.error("findTradeByupdlogUser 方法异常",e);
			throw e;
		}
		
		gs_logger.info("findTradeByupdlogUser方法开始");
		return list;
	}

	
	/**
	 * 查询用户操作公司足迹信息
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> findCompanyByupdlogUser(
			Map<String, Object> map) throws Exception {
		gs_logger.info("findCompanyByupdlogUser方法开始");
		List<Map<String, Object>> list=null;
		try {
			list=dao.findCompanyByupdlogUser("BaseUpdlogInfo.findCompanyByupdlogUser", map);
		} catch (Exception e) {
			gs_logger.error("findCompanyByupdlogUser 方法异常",e);
			throw e;
		}
		
		gs_logger.info("findCompanyByupdlogUser方法开始");
		return list;
	}

	/**
	 * 查询用户操作会议足迹信息
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> findMeetingByupdlogUser(
			Map<String, Object> map) throws Exception {
		gs_logger.info("findMeetingByupdlogUser方法开始");
		List<Map<String, Object>> list=null;
		try {
			list=dao.findMeetingByupdlogUser("BaseUpdlogInfo.findMeetingByupdlogUser", map);
		} catch (Exception e) {
			gs_logger.error("findMeetingByupdlogUser 方法异常",e);
			throw e;
		}
		gs_logger.info("findMeetingByupdlogUser方法结束");
		return list;
	}

	/**
	 * 会议操作足迹信息数量
	 * @return
	 * @throws Exception
	 */
	@Override
	public int findMeetingCountByupdlogUser(Map<String, Object> map)
			throws Exception {
		gs_logger.info("findMeetingCountByupdlogUser方法开始");
		int data=0;
		try {
			data=dao.findMeetingCountByupdlogUser("BaseUpdlogInfo.findMeetingCountByupdlogUser", map);
		} catch (Exception e) {
			gs_logger.error("findMeetingCountByupdlogUser方法异常",e);
			throw e;
		}
		gs_logger.info("findMeetingCountByupdlogUser方法结束");
		return data;
	}

	/**
	 * 查询用户参与会议信息
	 * @param usercode 用户code
	 * @date 2016-6-12
	 * @author dwj
	 */
	@Override
	public int findJoinMeetingCountByUsercode(String usercode) throws Exception {
		gs_logger.info("findJoinMeetingCountByUsercode方法开始");
		int data=0;
		try {
			data=meetingReleDao.findJoinMeetingCountByUsercode("ViewMeetingRele.findJoinMeetingCountByUsercode", usercode);
		} catch (Exception e) {
			gs_logger.error("findJoinMeetingCountByUsercode方法异常",e);
			throw e;
		}
		gs_logger.info("findJoinMeetingCountByUsercode方法结束");
		return data;
	}

	/**
	 * 查询用户参与会议信息
	 * @param usercode 用户code
	 * @date 2016-6-12
	 * @author dwj
	 */
	@Override
	public List<Map<String, Object>> findJoinMeetingByUsercode(Map<String, Object> map)
			throws Exception {
		gs_logger.info("findJoinMeetingByUsercode方法开始");
		List<Map<String, Object>> data=null;
		try {
			data=meetingReleDao.findJoinMeetingByUsercode("ViewMeetingRele.findJoinMeetingByUsercode", map);
		} catch (Exception e) {
			gs_logger.error("findJoinMeetingByUsercode方法异常",e);
			throw e;
		}
		gs_logger.info("findJoinMeetingByUsercode方法结束");
		return data;
	}

	
	/**
	 * 查询用户近一个月创建融资计划数量
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	@Override
	public int findFincingCountByUsercode(String usercode) throws Exception {
		gs_logger.info("findFincingCountByUsercode方法开始");
		int data=0;
		try {
			data=financingSearchDao.findFincingCountByUsercode("BaseFinancplanInfo.findFMCountByUsercode", usercode);
		} catch (Exception e) {
			gs_logger.error("findFincingCountByUsercode方法开始",e);
			throw e;
		}
		
		gs_logger.info("findFincingCountByUsercode方法结束");
		return data;
	}

	/**
	 * 查询用户近一个月创建融资计划
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
			data=financingSearchDao.findFincingByUsercode("BaseFinancplanInfo.findFMByUsercode", map);
		} catch (Exception e) {
			gs_logger.error("findFincingByUsercode方法开始",e);
			throw e;
		}
		
		gs_logger.info("findFincingByUsercode方法结束");
		return data;
	}

}
