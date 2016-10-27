package cn.com.sims.service.investor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.dao.baseinvestmentinvestor.IBaseInvestmentInvestorDao;
import cn.com.sims.dao.baseinvestorlabelinfo.IBaseInvestorlabelInfoDao;
import cn.com.sims.dao.baseinvestornote.IInvestornoteDao;
import cn.com.sims.dao.investor.InvestorDao;
import cn.com.sims.dao.system.sysstoredprocedure.ISysStoredProcedureDao;
import cn.com.sims.model.baseinvestmentinvestor.BaseInvestmentInvestor;
import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.baseinvestorlabelinfo.BaseInvestorlabelInfo;
import cn.com.sims.model.baseinvestornoteinfo.BaseInvestornoteInfo;
import cn.com.sims.model.sysstoredprocedure.SysStoredProcedure;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.model.viewInvestorInfo.viewInvestorInfo;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.investment.InvestmentServiceImpl;
import cn.com.sims.service.system.sysstoredprocedure.ISysStoredProcedureService;

/**
 * 
 * @author yl
 *
 */
@Service
public class investorServiceImpl implements InvestorService {
	@Resource
	InvestorDao investorDao;
	@Resource
	IBaseInvestorlabelInfoDao labelInfoDao;// 投资人标签dao
	@Resource
	IBaseInvestmentInvestorDao dao;// 投资人投资机构dao
	@Resource
	IInvestornoteDao investornotedao;//投资人note
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;// 系统更新记录
	@Resource
	ISysStoredProcedureService storedProcedureService;// 存储过程
	//2015-12-02 TASK76 yl add start
	@Resource
	ISysStoredProcedureDao isdao;//存储过程dao
	//2015-12-02 TASK76 yl add end
	private static final Logger gs_logger = Logger
			.getLogger(InvestmentServiceImpl.class);

	@Override
	public List findInvestorName(String name) throws Exception {
		gs_logger.info("investorServiceImpl findInvestorName方法结束");
		List list = new ArrayList();
		try {
			list = investorDao.findInvestor(
					"viewInvestorInfo.findInvestorName", name);
		} catch (Exception e) {
			gs_logger.error("investorServiceImpl findInvestorName方法异常",e);
			throw e;
		}
		gs_logger.info("investorServiceImpl findInvestorName方法结束");
		return list;
	}

	@Override
	public List<Map<String, String>> findInvestorByOrgId(String organizationId)
			throws Exception {
		gs_logger.info("findInvestorByOrgId方法开始");
		List<Map<String, String>> list = null;
		try {
			list = investorDao.findInvestorByOrgId(
					"viewInvestorInfo.findInvestorByOrgId", organizationId);
		} catch (Exception e) {
			gs_logger.error("findInvestorByOrgId方法异常",e);
			throw e;
		} finally {
			gs_logger.info("findInvestorByOrgId方法结束");
		}
		return list;
	}

	/**
	 * 根据投资人code 查询投资人详细信息
	 * 
	 * @param code
	 *            投资人code
	 * @return
	 * @throws Exception
	 */
	@Override
	public viewInvestorInfo findInvestorDeatilByCode(String code)
			throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("findInvestorDeatilByCode方法开始");
		viewInvestorInfo info = null;
		try {
			info = investorDao.findInvestorDeatilByCode(
					"viewInvestorInfo.findInvestorDeatilByCode", code);
			//2015-11-27 TASK070 yl mod start
			if(info!=null){
				String cont=investorDao.findInvestmentInvest("viewInvestorInfo.findInvestmentInvest",code);
				info.setView_investment_cont(cont);
			}
			//2015-11-27 TASK070 yl mod end
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("findInvestorDeatilByCode方法异常",e);
			throw e;
		}
		gs_logger.info("findInvestorDeatilByCode方法结束");
		return info;
	}

	@Override
	public BaseInvestorInfo findInvestorByCode(String code) throws Exception {
		// TODO Auto-generated method stub

		gs_logger.info("findInvestorByCode方法开始");
		BaseInvestorInfo info = null;
		try {
			info = investorDao.findInvestorByCode(
					"baseInvestorInfo.findInvestorByCode", code);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("findInvestorByCode方法异常",e);
			throw e;
		}
		gs_logger.info("findInvestorByCode方法结束");
		return info;
	}

	@Override
	public int tranModifyInvestor(BaseInvestorInfo info) throws Exception {
		// TODO Auto-generated method stub
		int data = 0;
		gs_logger.info("investorServiceImpl.tranModifyInvestor方法开始");
		try {
			data = investorDao.tranModifyInvestor(
					"baseInvestorInfo.tranModifyInvestor", info);
			if(data!=0){
				/*调用存储过程*/
				storedProcedureService.callViewinvestorInfo(new SysStoredProcedure(info.getBase_investor_code(), "upd", "basic", info.getUpdid(),"",""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("investorServiceImpl.tranModifyInvestor方法异常",e);
			throw e;
		} finally {
			gs_logger.info("investorServiceImpl.tranModifyInvestor方法结束");
		}
		return data;
	}

	@Override
	public void tranModifyUpdateBaseInvestor(SysUserInfo userInfo,
			String newData, String oldData, String investorCode, String type,
			String investorName, String logintype) throws Exception {
		// TODO Auto-generated method stub
		String addFlg = "";
		String delFlg = "";
		String indutype = type;
		JSONArray newArray=JSONArray.fromObject(newData);
		JSONArray oldArray=JSONArray.fromObject(oldData);
		String[] addData=new String[newArray.size()];//添加数组
		String[] addDataName=new String[newArray.size()];//添加数组对应名称
		String[] delData=new String[oldArray.size()];//删除数组
		String[] delDataName=new String[oldArray.size()];//删除数组对应名称
		int ai=0,di=0;
		boolean bl=false;//判断标识
		if(newArray.size()>0){
			if(oldArray.size()>0){
				//判断新数据是否在原数据中,不在则添加到addData数组中
				for (int i = 0; i < newArray.size(); i++) {
					JSONObject jsonObject=newArray.getJSONObject(i);
					for (int j = 0; j < oldArray.size(); j++) {
						JSONObject oldjson=oldArray.getJSONObject(j);
						if(jsonObject.getString("code").equals(oldjson.getString("code"))){
							bl=true;
							break;
						}
					}
					if(bl==false){
						addData[ai]=jsonObject.getString("code");
						addDataName[ai]=jsonObject.getString("name");
						ai++;
					}else{
						bl=false;
					}
				}
				
				//判断原数据是否存在新数据中,若不存在则添加到删除数组中
				for (int i = 0; i < oldArray.size(); i++) {
					JSONObject jsonObject=oldArray.getJSONObject(i);
					for (int j = 0; j < newArray.size(); j++) {
						JSONObject newJson=newArray.getJSONObject(j);
						if(jsonObject.getString("code").equals(newJson.getString("code"))){
							bl=true;
							break;
						}
					}
					if(bl==false){
						delData[di]=jsonObject.getString("code");
						delDataName[di]=jsonObject.getString("name");
						di++;
					}else{
						bl=false;
					}
				}
				
			}else{
				for (int i = 0; i < newArray.size(); i++) {
					JSONObject jsonObject=newArray.getJSONObject(i);
					addData[i]=jsonObject.getString("code");
					addDataName[i]=jsonObject.getString("name");
				}
			}
			
		}else {
			if(oldArray.size()>0){
				for (int i = 0; i < oldArray.size(); i++) {
					JSONObject jsonObject=oldArray.getJSONObject(i);
					delData[i]=jsonObject.getString("code");
					delDataName[i]=jsonObject.getString("name");
				}
			}
		}
		/**
		 * 判断添加的是行业标签还是近期关注标签
		 */
		if ("Lable-indu".equals(type)) {
			indutype="Lable-orgindu";
			addFlg = CommonUtil.findNoteTxtOfXML("addInvestorInduLabel");
		} else if ("Lable-payatt".equals(type)) {
			addFlg = CommonUtil.findNoteTxtOfXML("addInvestorPattyLabel");
		}
		if ("Lable-indu".equals(type)) {
			indutype="Lable-orgindu";
			delFlg = CommonUtil.findNoteTxtOfXML("delInvestorInduLabel");
		} else if ("Lable-payatt".equals(type)) {
			delFlg = CommonUtil.findNoteTxtOfXML("delInvestorPattyLabel");
		}
		
		for (int i = 0; i < addData.length; i++) {
			if (addData[i] != null) {
				try {
					Timestamp time = new Timestamp(new Date().getTime());
					/* 添加投资人标签 */
					labelInfoDao.insertInveslabelInfo(
							"baseInvestorlabelInfo.insertInvestorlabelInfo",
							new BaseInvestorlabelInfo(investorCode, addData[i],
									CommonUtil.findNoteTxtOfXML(indutype), "0",
									userInfo.getSys_user_code(), time, userInfo.getSys_user_code(),
									time));

					/* 添加系统更新记录 */
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-people"),
							CommonUtil.findNoteTxtOfXML("Lable-people-name"),
							investorCode, investorName, CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(),
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"),
							addFlg, "", "添加["+addDataName[i]+"]", logintype,
							userInfo.getSys_user_code(), time, userInfo.getSys_user_code(), time);
				} catch (Exception e) {
					gs_logger.error("tranModifyUpdateBaseInvestor 添加投资人标签 方法异常",e);
					e.printStackTrace();
					throw e;
				}
			}
		}

		for (int i = 0; i < delData.length; i++) {
			if (delData[i]!=null) {
				try {
					Timestamp time = new Timestamp(new Date().getTime());
					/* 删除投资机构标签 */
					labelInfoDao.tranDeleteInvestorlabel(
							"baseInvestorlabelInfo.tranDeleteInvestorlabel",
							new BaseInvestorlabelInfo(investorCode,
									delData[i], CommonUtil
											.findNoteTxtOfXML(indutype), "0",
									userInfo.getSys_user_code(), time, userInfo.getSys_user_code(),
									time));
					/* 添加系统更新记录 */
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-people"),
							CommonUtil.findNoteTxtOfXML("Lable-people-name"),
							investorCode, investorName, CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(),
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"),
							delFlg, "删除["+delDataName[i]+"]", "", logintype,
							userInfo.getSys_user_code(), time, userInfo.getSys_user_code(), time);
				} catch (Exception e) {
					gs_logger.error("tranModifyUpdateBaseInvestor 删除投资人标签 方法异常",e);
					e.printStackTrace();
					throw e;
				}
			}
		}
		/* 调用存储过程 */
		storedProcedureService.callViewinvestorInfo(new SysStoredProcedure(
				investorCode, "upd", CommonUtil.findNoteTxtOfXML(type),
				userInfo.getSys_user_code(), "",""));
	}

	@Override
	public List<Map<String, String>> findPageInvestorByOrgId(String orgCode,
			Page page) throws Exception {
		gs_logger.info("findPageInvestorByOrgId方法开始");
		List<Map<String, String>> list = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			map.put("pageSize", page.getPageSize() + "");
			map.put("startCount", page.getStartCount() + "");
			map.put("orgCode", orgCode);
			list = investorDao.findPageInvestorByOrgId(
					"viewInvestorInfo.findPageInvestorByOrgId", map);
		} catch (Exception e) {
			gs_logger.error("findPageInvestorByOrgId方法异常",e);
			throw e;
		} finally {
			gs_logger.info("findPageInvestorByOrgId方法结束");
		}
		return list;
	}

	@Override
	public int findCountInvestor(String orgCode) throws Exception {
		int data = 0;
		gs_logger.info("findCountInvestor方法开始");
		try {
			data = investorDao.findCountInvestor(
					"viewInvestorInfo.findCountInvestor", orgCode);
		} catch (Exception e) {
			gs_logger.error("findCountInvestor方法异常",e);
			throw e;
		} finally {
			gs_logger.info("findCountInvestor方法结束");
		}
		return data;
	}

	@Override
	public int tranModifyUpdInvestor(Map map) throws Exception {
		// TODO Auto-generated method stub
		int data = 0;
		gs_logger.info("tranModifyUpdInvestor方法开始");
		try {
			data = dao.tranModifyUpdInvestor(
					"BaseInvestmentInvestor.updInvestor", map);
			if(data!=0){
				/*调用存储过程*/
				storedProcedureService.callViewinvestorInfo(new SysStoredProcedure(map.get("code").toString(), "upd", "investment", map.get("updid").toString(),"",""));
			}
		} catch (Exception e) {
			gs_logger.error("tranModifyUpdInvestor方法异常",e);
			throw e;
		} finally {
			gs_logger.info("tranModifyUpdInvestor方法结束");
		}
		return data;
	}

	@Override
	public int insertInvestor(BaseInvestmentInvestor info) throws Exception {
		// TODO Auto-generated method stub
		int data = 0;
		gs_logger.info("insertInvestor方法开始");
		try {
			data = dao.insertInvestor("BaseInvestmentInvestor.insertInvestor",info);
					if(data!=0){
						/*调用存储过程*/
						storedProcedureService.callViewinvestorInfo(new SysStoredProcedure(info.getBase_investor_code(), "upd", "investment", info.getUpdid(),"",""));
					}
					
		} catch (Exception e) {
			gs_logger.error("insertInvestor方法异常",e);
			throw e;
		} finally {
			gs_logger.info("insertInvestor方法结束");
		}
		return data;
	}

	@Override
	public int findInvestmentInstor(Map map) throws Exception {
		// TODO Auto-generated method stub
		int data = 0;
		gs_logger.info("findInvestmentInstor方法开始");
		try {
			data = dao.findInvestmentInstor(
					"BaseInvestmentInvestor.findInvestmentInstor", map);
		} catch (Exception e) {
			gs_logger.error("findInvestmentInstor方法异常",e);
			throw e;
		} finally {
			gs_logger.info("findInvestmentInstor方法结束");
		}
		return data;
	}

	@Override
	public List<Map<String, String>> findTradeLabelByCode(String code)
			throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("findTradeLabelByCode方法开始");
		List<Map<String, String>> list = null;
		try {
			list = dao.findTradeLabelByCode(
					"baseInvestorlabelInfo.findTradeLabelByCode", code);
		} catch (Exception e) {
			gs_logger.error("findTradeLabelByCode方法异常",e);
			throw e;
		} finally {
			gs_logger.info("findTradeLabelByCode方法结束");
		}
		return list;
	}

	/**
	 * 查询所有投资人(下拉筐数据)
	 * @return id,text
	 * @author duwenjie
	 */
	@Override
	public List<Map<String, String>> findAllInvestorInfo() throws Exception {
		gs_logger.info("findAllInvestorInfo方法开始");
		List<Map<String, String>> list=null;
		try {
			list=investorDao.findAllInvestorInfo("baseInvestorInfo.findAllInvestorInfo");
		} catch (Exception e) {
			gs_logger.error("findAllInvestorInfo方法异常",e);
			e.printStackTrace();
			throw e;
		}
		gs_logger.info("findAllInvestorInfo方法结束");
		return list;
	}

	@Override
	public String findInvestment(String ccode) throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("findInvestment方法开始");
		String name="";
		try {
			name=investorDao.findInvestment("baseInvestmentInfo.findInvestment",ccode);
		} catch (Exception e) {
			gs_logger.error("findInvestment方法异常",e);
			e.printStackTrace();
			throw e;
		}
		gs_logger.info("findInvestment方法结束");
		return name;
	}

	@Override
	public int tranModifyInvestorInfo(BaseInvestorInfo info,
			List<BaseInvestorlabelInfo> labelList,
			List<BaseInvestmentInvestor> investmentinvestorInfoList,
			BaseInvestornoteInfo noteInfo) throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("tranModifyInvestorInfo方法开始");
		int data=0;
		try {
			//添加投资人
			investorDao.insetInvstor("baseInvestorInfo.insetInvstor",info);
			//添加投资人标签
			if(labelList!=null&&labelList.size()>0){
				for(int i=0;i<labelList.size();i++){
					labelInfoDao.insertInveslabelInfo("baseInvestorlabelInfo.insertInvestorlabelInfo",labelList.get(i));
				}
			}
			if(investmentinvestorInfoList!=null&&investmentinvestorInfoList.size()>0){
				for(int i=0;i<investmentinvestorInfoList.size();i++){
					dao.insertInvestor("BaseInvestmentInvestor.insertInvestor",investmentinvestorInfoList.get(i));
				}
			}
			if(noteInfo!=null&&noteInfo.getBase_investornote_code()!=null){
				investornotedao.insertInvestorNote("baseInvestornoteInfo.insertInvestorNote", noteInfo);
			}
			/*调用存储过程*/
			storedProcedureService.callViewinvestorInfo(new SysStoredProcedure(info.getBase_investor_code(), "add", "all", info.getCreateid(),"",""));
			data=1;
		} catch (Exception e) {
			gs_logger.error("tranModifyInvestorInfo方法异常",e);
			e.printStackTrace();
			throw e;
		}
		gs_logger.info("tranModifyInvestorInfo方法结束");
		return data;
	}
		
	/**
	 * 根据投资机构Code查询所有投资人(下拉筐数据)
	 * @return id,text
	 * @author duwenjie
	 */
	@Override
	public List<Map<String, String>> findInvestorInfoByOrgCode(String orgcode)
			throws Exception {
		gs_logger.info("findInvestorInfoByOrgCode方法开始");
		List<Map<String, String>> list=null;
		try {
			list=investorDao.findInvestorInfoByOrgCode("baseInvestorInfo.findInvestorInfoByOrgCode", orgcode);
		} catch (Exception e) {
			gs_logger.error("findInvestorInfoByOrgCode方法异常",e);
			e.printStackTrace();
			throw e;
		}
		gs_logger.info("findInvestorInfoByOrgCode方法结束");
		return list;
	}
	@Override
	public int tranDeleteInvestor(String orgCode, String invstorCode,String userCode)
			throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("tranDeleteInvestor方法开始");
		Map<String, String> map = new HashMap<String, String>();
		int data=0;
		String estate;
		try {
			//机构code
			map.put("orgCode", orgCode);
			//投资人code
			map.put("invstorCode", invstorCode);
			//投资人的当前状态:易凯修改
			map.put("estate", "3");
			//修改者
			map.put("userCode", userCode);
			//修改时间
			map.put("updtime", CommonUtil.getNowTime_tamp().toString());
			//删除投资人投资机构关系表
			data=investorDao.tranDeleteInvestor("baseInvestorInfo.tranDeleteInvestor", map);
			if(data>0){
				//修改投资人的当前状态
				investorDao.updateInvestor("baseInvestorInfo.updateInvestor",map);
				//调用存储过程
				isdao.callViewinvestorInfo("SysStoredProcedure.callViewinvestorInfo", new SysStoredProcedure(invstorCode, "upd", "investment",userCode ,"",""));	
			}
			gs_logger.info("tranDeleteInvestor方法结束");
}catch(Exception e){
	gs_logger.error("tranDeleteInvestor方法异常",e);
	throw e;
}
		return data;
}

	@Override
	public int selectTrade(String orgCode, String invstorCode) throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("selectTrade方法开始");
		
		Map<String, String> map = new HashMap<String, String>();
		int data=0;
		
		try {
			//机构code
			map.put("orgCode", orgCode);
			//投资人code
			map.put("invstorCode", invstorCode);
			//删除投资人投资机构关系表
			data=investorDao.selectTrade("basetradeinves.selectTrade", map);
			
			gs_logger.info("selectTrade方法结束");
}catch(Exception e){
	gs_logger.error("selectTrade方法异常",e);
	e.printStackTrace();
	throw e;
}
		return data;
	}
}