package cn.com.sims.service.basetradeinfo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.aspectj.weaver.ast.Var;
import org.springframework.stereotype.Service;

import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.dao.baseinvestmentinvestor.IBaseInvestmentInvestorDao;
import cn.com.sims.dao.basetradeinfo.IBaseTradeInfoDao;
import cn.com.sims.dao.basetradeinves.IBaseTradeInvesDao;
import cn.com.sims.dao.basetradelabelinfo.IBaseTradelabelInfoDao;
import cn.com.sims.dao.basetradenoteinfo.IBaseTradenoteInfoDao;
import cn.com.sims.dao.investor.InvestorDao;
import cn.com.sims.dao.system.sysstoredprocedure.ISysStoredProcedureDao;
import cn.com.sims.dao.tradeinfo.ITradeInfoDao;
import cn.com.sims.dao.viewtradeser.IViewTradeSerDao;
import cn.com.sims.model.baseinveslabelinfo.BaseInveslabelInfo;
import cn.com.sims.model.baseinvestmentinvestor.BaseInvestmentInvestor;
import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.basetradeinfo.BaseTradeInfo;
import cn.com.sims.model.basetradeinves.BaseTradeInves;
import cn.com.sims.model.basetradelabelinfo.BaseTradelabelInfo;
import cn.com.sims.model.sysstoredprocedure.SysStoredProcedure;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.service.baseinvesreponinfo.IBaseInvesreponInfoServiceImpl;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.system.sysstoredprocedure.ISysStoredProcedureService;

/**
 * 
 * @author duwenjie
 *
 */
@Service
public class BaseTradeInfoServiceImpl implements IBaseTradeInfoService{

	@Resource
	IBaseTradeInfoDao dao;
	
	@Resource
	IBaseTradelabelInfoDao labelDao;
	
	@Resource
	IBaseTradeInvesDao invesDao;
	
	@Resource
	IBaseTradeInfoDao tradeInfodao;
	
	@Resource
	IBaseTradenoteInfoDao noteDao;
    // 存储过程
    @Resource
    ISysStoredProcedureDao sysstoredproceduredao;
	
	@Resource
	IViewTradeSerDao tradeSerDao;
	
	@Resource
	ITradeInfoDao viewTradeInfoDao;
    // 投资人dao
    @Resource
    InvestorDao investordao;
    //机构与投资人关系dao基础
    @Resource
    IBaseInvestmentInvestorDao baseinvestmentinvestordao;
	@Resource
	ISysStoredProcedureService storedProcedureService;//存储过程
	
	//系统更新记录
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;
	
	private static final Logger gs_logger = Logger.getLogger(BaseTradeInfoServiceImpl.class);
	
	/**
	 * 根据交易code修改交易信息
	 * @param info 交易信息
	 * @author duwenjie
	 */
	@Override
	public int updateTradeInfo(BaseTradeInfo info) throws Exception {
		gs_logger.info("BaseTradeInfoServiceImpl.updateTradeInfo 方法开始");
		int data=0;
		try {
			//PL锁状态改为 0等待处理
			info.setBase_datalock_pltype("0");
			data=dao.updateTradeInfo("BaseTradeInfo.updateTradeInfo", info);
		} catch (Exception e) {
			gs_logger.error("BaseTradeInfoServiceImpl.updateTradeInfo 方法异常",e);
			e.printStackTrace();
			throw e;
		}
		gs_logger.info("BaseTradeInfoServiceImpl.updateTradeInfo 方法结束");
		return data;
	}

	/**
	 * 根据交易code查询交易信息
	 * @param tradeCode交易code
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 */
	@Override
	public BaseTradeInfo findBaseTradeInfoByCode(String tradeCode) throws Exception {
		gs_logger.info("BaseTradeInfoServiceImpl.findBaseTradeInfoByCode 方法开始");
		BaseTradeInfo data=null;
		try {

			data=dao.findBaseTradeInfoByCode("BaseTradeInfo.findBaseTradeInfoByCode", tradeCode);
		} catch (Exception e) {
			gs_logger.error("BaseTradeInfoServiceImpl.findBaseTradeInfoByCode 方法异常",e);
			e.printStackTrace();
			throw e;
		}
		gs_logger.info("BaseTradeInfoServiceImpl.findBaseTradeInfoByCode 方法结束");
		return data;
	}

	
	/**
	 * 修改交易标签信息
	 * @param userInfo登录用户对象
	 * @param newData新数据
	 * @param oldData原数据
	 * @param tradeCode交易code
	 * @param type标签类型(筐Lable-bask,行业Lable-indu)
	 * @param logintype登录标识(电脑:PC,微信:WX,手机:MB)
	 * @throws Exception
	 */
	@Override
	public void tranModifyBaseTradeLabelInfo(SysUserInfo userInfo,
			String newData, String oldData, String tradeCode, String type,
			String logintype) throws Exception {
		gs_logger.info("BaseTradeInfoServiceImpl.tranModifyBaseTradeLabelInfo 方法开始");
		String indutype = type;
		Timestamp timestamp = new Timestamp(new Date().getTime());
		JSONArray newArray=JSONArray.fromObject(newData);
		JSONArray oldArray=JSONArray.fromObject(oldData);
		String[] addData=new String[newArray.size()];//添加数组
		String[] addDataName=new String[newArray.size()];//添加数组对应名称
		String[] delData=new String[oldArray.size()];//删除数组
		String[] delDataName=new String[oldArray.size()];//删除数组对应名称
		int ai=0,di=0;
		boolean bl=false;//判断标识
		
		if(type.equals("trade-Lable-indu")){
			indutype="Lable-comindu";
		}
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

		for (int i = 0; i < addData.length; i++) {
			if(addData[i]!=null){
				try {
					/*添加交易标签*/
					labelDao.insertBaseTradelabelInfo("basetradelabelinfo.insertBaseTradelabelInfo",
							new BaseTradelabelInfo(
									tradeCode,
									addData[i],
									CommonUtil.findNoteTxtOfXML(indutype),
									"0", 
									userInfo.getSys_user_code(), 
									timestamp, 
									userInfo.getSys_user_code(),
									timestamp));
					/*添加系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-trading"),
							CommonUtil.findNoteTxtOfXML("Lable-trading-name"),
							tradeCode, 
							"",
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
							CommonUtil.findNoteTxtOfXML("add")+
							CommonUtil.findNoteTxtOfXML("tradae")+
							CommonUtil.findNoteTxtOfXML(indutype+"-name"),
							"",
							"添加标签["+addDataName[i]+"]",
							logintype,
							userInfo.getSys_user_code(),
							timestamp,
							userInfo.getSys_user_code(),
							timestamp);
				} catch (Exception e) {
					gs_logger.error("tranModifyBaseTradeLabelInfo 添加投资机构标签 方法异常",e);
					e.printStackTrace();
					throw e;
				}
			}
		}
		
		for (int i = 0; i < delData.length; i++) {
			if (delData[i]!=null) {
				try {
					/*删除交易标签*/
					labelDao.deleteBaseTradeLabelInfo("basetradelabelinfo.deleteBaseTradeLabelInfo", 
							new BaseTradelabelInfo(
									tradeCode,delData[i],CommonUtil.findNoteTxtOfXML(indutype),
									"","", null, "",null));

					/*添加系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-trading"),
							CommonUtil.findNoteTxtOfXML("Lable-trading-name"),
							tradeCode, 
							"",
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-DELE"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-DELE"), 
							CommonUtil.findNoteTxtOfXML("del")+
							CommonUtil.findNoteTxtOfXML("tradae")+
							CommonUtil.findNoteTxtOfXML(indutype+"-name"),
							"删除标签["+delDataName[i]+"]",
							"",
							logintype,
							userInfo.getSys_user_code(),
							timestamp,
							userInfo.getSys_user_code(),
							timestamp);
				} catch (Exception e) {
					gs_logger.error("tranModifyBaseTradeLabelInfo 删除交易标签 方法异常",e);
					e.printStackTrace();
					throw e;
				}
			}
		}
		gs_logger.info("BaseTradeInfoServiceImpl.tranModifyBaseTradeLabelInfo 方法结束");
		
	}

	/**
	 * 添加机构交易信息
	 * @param info机构交易信息
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2010-10-30
	 */
	@Override
	public int tranModifyInsertBaseTradeInvesInfo(SysUserInfo userInfo,BaseTradeInves info,long version) throws Exception {
		gs_logger.info("BaseTradeInfoServiceImpl.insertBaseTradeInvesInfo 方法开始");
		int data=0;
		try {
			info.setDeleteflag("0");
//			info.setBase_trade_stem("2");
			
			Timestamp timestamp=new Timestamp(new Date().getTime());
			BaseTradeInfo tradeInfo=new BaseTradeInfo();
			tradeInfo.setBase_trade_code(info.getBase_trade_code());
			tradeInfo.setBase_datalock_pltype("0");
			tradeInfo.setBase_trade_estate("3");
			tradeInfo.setBase_datalock_viewtype(version+1);
			tradeInfo.setUpdid(userInfo.getSys_user_code());
			tradeInfo.setUpdtime(timestamp);
			data=tradeInfodao.updateTradeInfo("BaseTradeInfo.updateTradeInfo", tradeInfo);
			
			if(data!=0){
				invesDao.insertBaseTradeInvesInfo("basetradeinves.insertBaseTradeInvesInfo", info);
				//调用机构交易存储过程 添加
				storedProcedureService.callViewTradeInfo(new SysStoredProcedure(info.getBase_trade_code(), "add", "investmentinfo", userInfo.getSys_user_code(), "", info.getBase_investment_code()));
				
			}
			
		} catch (Exception e) {
			gs_logger.error("BaseTradeInfoServiceImpl.insertBaseTradeInvesInfo 方法异常",e);
			e.printStackTrace();
			throw e;
		}
		gs_logger.info("BaseTradeInfoServiceImpl.insertBaseTradeInvesInfo 方法结束");
		return data;
	}

	
	/**
	 * 删除机构交易信息
	 * @param tradeCode交易code
	 * @param orgCode机构code
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2010-10-30
	 */
	@Override
	public int tranModifyDeleteTradeInvesInfo(SysUserInfo userInfo,String tradeCode, String orgCode,long version)
			throws Exception {
		gs_logger.info("BaseTradeInfoServiceImpl.deleteTradeInvesInfo 方法开始");
		int data=0;
		Map<String, String> map = new HashMap<String, String>();
		try {
			map.put("tradeCode", tradeCode);
			map.put("orgCode", orgCode);
			
			Timestamp timestamp=new Timestamp(new Date().getTime());
			BaseTradeInfo tradeInfo=new BaseTradeInfo();
			tradeInfo.setBase_trade_code(tradeCode);
			tradeInfo.setBase_datalock_pltype("0");
			tradeInfo.setBase_datalock_viewtype(version+1);
			tradeInfo.setUpdid(userInfo.getSys_user_code());
			tradeInfo.setUpdtime(timestamp);
			//date:2015-12-01 author:zzg option:add Task:074
			tradeInfo.setBase_trade_estate("3");
			//date:2015-12-01 author:zzg option:add_end Task:074
			//修改交易信息表PL锁
			int upData=tradeInfodao.updateTradeInfo("BaseTradeInfo.updateTradeInfo", tradeInfo);
			if(upData!=0){
				data=invesDao.deleteTradeInvesInfo("basetradeinves.deleteTradeInvesInfo", map);
				if(data!=0){
					//调用机构交易存储过程 删除
					storedProcedureService.callViewTradeInfo(new SysStoredProcedure(tradeCode, "delete", "investmentinfo", userInfo.getSys_user_code(), "", orgCode));
				}
			}
		
		} catch (Exception e) {
			gs_logger.error("BaseTradeInfoServiceImpl.deleteTradeInvesInfo 方法异常",e);
			e.printStackTrace();
			throw e;
		}
		gs_logger.info("BaseTradeInfoServiceImpl.deleteTradeInvesInfo 方法结束");
		return data;
	}

	
	/**
	 * 删除交易信息
	 * @param tradeCode 交易code
	 * @param version 排他锁版本号
	 * @param orgcodeString 投资机构code串
	 * @author duwenjie
	 * @date 2015-11-20
	 */
	@Override
	public int tranDeleteTradeInfoByTradeCode(String userId,String tradeCode, String version,
			String orgcodeString) throws Exception {
		gs_logger.info("BaseTradeInfoServiceImpl tranDeleteTradeInfoByTradeCode 方法开始");
		int data=0;
		Map<String, String> map = new HashMap<String, String>();		
		try {
			map.put("tradeCode", tradeCode);
			map.put("version", version);
			//2015-12-01 TASK073 yl add start
			//数据备份
			tradeInfodao.database("BaseTradeInfo.database",map);
			//2015-12-01 TASK073 yl add end
			//删除交易信息(基础)
			data=tradeInfodao.deleteTradeInfoByTradeCode("BaseTradeInfo.deleteTradeInfoByTradeCode", map);
			if(data>0){
				//删除机构交易
				invesDao.deleteTradeInvesInfoByTradeCode("basetradeinves.deleteTradeInvesInfoByTradeCode", tradeCode);
				//删除交易标签
				labelDao.deleteBaseTradeLabelInfoByTradeCode("basetradelabelinfo.deleteBaseTradeLabelInfoByTradeCode", tradeCode);
				//删除交易备注
				noteDao.deleteBaseTradenoteInfoByTradeCode("BaseTradenoteInfo.deleteBaseTradenoteInfoByTradeCode", tradeCode);
				//删除机构交易详情（业务）
				tradeSerDao.deleteTradeSerByTradeCode("ViewTradSer.deleteTradeSerByTradeCode", tradeCode);
				//删除交易详情（业务）
				viewTradeInfoDao.deleteViewTradeInfoByTradeCode("viewTradeInfo.deleteViewTradeInfoByTradeCode", tradeCode);
			
				if(!"".equals(orgcodeString)&&orgcodeString!=null){
					String[] orgCode=orgcodeString.split(",");
					for (int i = 0; i < orgCode.length; i++) {
						//机构存储过程
						storedProcedureService.callViewinvestment(new SysStoredProcedure(orgCode[i], "upd", "Lable-comp", userId, "", ""));
					}
				}
			}
			
			
		} catch (Exception e) {
			gs_logger.error("BaseTradeInfoServiceImpl tranDeleteTradeInfoByTradeCode 方法异常",e);
			e.printStackTrace();
			throw e;
		}
		gs_logger.info("BaseTradeInfoServiceImpl tranDeleteTradeInfoByTradeCode 方法结束");
		return data;
	}



	@Override
	public boolean tranModifyUpdate_trade(BaseTradeInves bastradinfo,BaseInvestorInfo baseinvestor,BaseInvestmentInvestor investorinfo,
			SysUserInfo userInfo,String version)
			throws Exception {
		gs_logger.info("BaseTradeInfoServiceImpl tranModifyUpdate_trade 方法开始");
						boolean br=false;
						int data=0;
						try {
							Timestamp timestamp=new Timestamp(new Date().getTime());
							BaseTradeInfo tradeInfo=new BaseTradeInfo();
							tradeInfo.setBase_trade_code(bastradinfo.getBase_trade_code());
							tradeInfo.setBase_datalock_pltype("0");
							tradeInfo.setBase_trade_estate("3");
							tradeInfo.setBase_datalock_viewtype(Long.valueOf(version)+1);
							tradeInfo.setUpdid(userInfo.getSys_user_code());
							tradeInfo.setUpdtime(timestamp);
							data=tradeInfodao.updateTradeInfo("BaseTradeInfo.updateTradeInfo", tradeInfo);
							if (data!=0) {
								if (baseinvestor!=null) {
									//投资人
									investordao.insertBaseInvestorforIT(
						        			"baseInvestorInfo.insertBaseInvestorforIT",baseinvestor);
						      //投资人与机构关系      
						        	baseinvestmentinvestordao.insertInvestmentInvestorforit(
						        			"BaseInvestmentInvestor.insertInvestmentInvestorforit",investorinfo);
						        			/* 调用存储过程,更新业务数据库 */
						        	/* 调用存储过程,更新业务数据库 */
						            sysstoredproceduredao.callViewinvestment(
						    				"SysStoredProcedure.callViewinvestorInfo",
						    				new SysStoredProcedure(baseinvestor.getBase_investor_code(),"add","all",
						    						baseinvestor.getCreateid(),"", ""));
								}
								invesDao.updata_tradeinves("basetradeinves.updata_tradeinves", bastradinfo);
						/* 调用存储过程,更新业务数据库 */
						sysstoredproceduredao.callViewTradeInfo(
								"SysStoredProcedure.callViewtradeinfo",
								new SysStoredProcedure(
										bastradinfo.getBase_trade_code(),"upd","investmentinfo",
										bastradinfo.getCreateid(),"",bastradinfo.getBase_investment_code()));
						br=true;
							}
						} catch (Exception e) {
							gs_logger.error("BaseTradeInfoServiceImpl tranModifyUpdate_trade 方法异常",e);
							throw e;
						}
						gs_logger.info("BaseTradeInfoServiceImpl tranModifyUpdate_trade 方法结束");
		return br;
	}

	@Override
	public BaseTradeInves findBaseTradeInvesByCode(HashMap<String, String> map)
			throws Exception {
		gs_logger.info("BaseTradeInfoServiceImpl findBaseTradeInvesByCode 方法开始");
		BaseTradeInves baseTradeInves=null;
		try {
			baseTradeInves=invesDao.findBaseTradeInvesByCode("basetradeinves.findBaseTradeInvesByCode", map);
		} catch (Exception e) {
			gs_logger.error("BaseTradeInfoServiceImpl findBaseTradeInvesByCode 方法结束",e);
			throw e;
		}
		gs_logger.info("BaseTradeInfoServiceImpl findBaseTradeInvesByCode 方法结束");
		return baseTradeInves;
	}



	

}
