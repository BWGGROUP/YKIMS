package cn.com.sims.service.company.companydetail;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.dao.basecomplabelinfo.IBaseComplabelInfoDao;
import cn.com.sims.dao.company.companydetail.CompanyDetailDao;
import cn.com.sims.model.basecompentrepreneur.BaseCompEntrepreneur;
import cn.com.sims.model.basecompinfo.BaseCompInfo;
import cn.com.sims.model.basecomplabelinfo.BaseComplabelInfo;
import cn.com.sims.model.basecompnoteinfo.BaseCompnoteInfo;
import cn.com.sims.model.baseentrepreneurinfo.BaseEntrepreneurInfo;
import cn.com.sims.model.basefinancplanemail.BaseFinancplanEmail;
import cn.com.sims.model.basefinancplaninfo.BaseFinancplanInfo;
import cn.com.sims.model.baseinveslabelinfo.BaseInveslabelInfo;
import cn.com.sims.model.sysstoredprocedure.SysStoredProcedure;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.model.viewcompinfo.viewCompInfo;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.company.companyCommon.companyCommonServiceImpl;
import cn.com.sims.service.system.sysstoredprocedure.ISysStoredProcedureService;

/** 
 * @author  yl
 * @date ：2015年10月27日 
 * 类说明 
 */
@Service
public class CompanyDetailServiceImpl implements ICompanyDetailService{
	@Resource
	CompanyDetailDao companyDetailDao;
	@Resource
	IBaseComplabelInfoDao dao;
	@Resource
	ISysStoredProcedureService storedProcedureService;//存储过程
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;//系统更新记录
	private static final Logger gs_logger = Logger
			.getLogger(CompanyDetailServiceImpl.class);
	
	/**
	 * 根据公司ｉｄ查询公司详细信息
	 */
	@Override
	public viewCompInfo findCompanyDeatilByCode(String code) throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("CompanyDetailServiceImpl findCompanyDeatilByCode()方法开始");
		
		//公司详细信息
		viewCompInfo viewCompInfo = null;
		
		try {
			viewCompInfo=companyDetailDao.findCompanyDeatilByCode("viewCompInfo.findCompanyDeatilByCode", code);
			gs_logger.info("CompanyDetailServiceImpl findCompanyDeatilByCode()方法结束");
			
		} catch (Exception e) {
			gs_logger.error("CompanyDetailServiceImpl findCompanyDeatilByCode()方法异常",e);
			throw e;
			
		}
		gs_logger.info("CompanyDetailServiceImpl findCompanyDeatilByCode()方法结束");
		return viewCompInfo;
	}
	/**
	 * 根据公司ｉｄ查询公司note信息
	 */
	@Override
	public List<Map<String, String>> findNoteByCode(String code) throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("CompanyDetailServiceImpl findNoteByCode()方法开始");
		
		//公司note信息
		List<Map<String, String>> noteList=null;
		try {
			noteList=companyDetailDao.findCompanyNoteByCode("BaseCompnoteInfo.findCompanyNoteByCode", code);
			gs_logger.info("CompanyDetailServiceImpl findNoteByCode()方法结束");
			
		} catch (Exception e) {
			gs_logger.error("CompanyDetailServiceImpl findNoteByCode()方法异常",e);
			throw e;
			
		}
		gs_logger.info("CompanyDetailServiceImpl findNoteByCode()方法结束");
		return noteList;
	}
	@Override
	public List<Map<String, String>> findFinancplanByCode(String code)
			throws Exception {
		// TODO Auto-generated method stub
  gs_logger.info("CompanyDetailServiceImpl findFinancplanByCode()方法开始");
		
		//公司融资计划信息
		List<Map<String, String>> noteList=null;
		try {
			noteList=companyDetailDao.findFinancplanByCode("BaseFinancplanInfo.findFinancplanByCode", code);
			gs_logger.info("CompanyDetailServiceImpl findFinancplanByCode()方法结束");
			
		} catch (Exception e) {
			gs_logger.error("CompanyDetailServiceImpl findFinancplanByCode()方法异常",e);
			throw e;
			
		}
		gs_logger.info("CompanyDetailServiceImpl findFinancplanByCode()方法结束");
		return noteList;
	}
	@Override
	public List<Map<String, String>> findFinancByCode(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
gs_logger.info("CompanyDetailServiceImpl findFinancByCode()方法开始");
		
		//公司交易信息
		List<Map<String, String>> financList=null;
		try {
			financList=companyDetailDao.findFinancByCode("ViewTradSer.findFinancByCode", map);
//			financList=companyDetailDao.findFinancByCode("viewTradeInfo.findFinancByCode", map);
			//2015-11-30 TASK070 yl mod start
			if(financList!=null){
				for(int i=0;i<financList.size();i++){
					Map map1=financList.get(i);
					String view_investment_name=companyDetailDao.findInvestNameByTrad("baseInvestmentInfo.findInvestNameByTrad",map1.get("base_trade_code").toString());
					financList.get(i).put("view_investment_name", view_investment_name);
				}
			}
			//2015-11-30 TASK070 yl mod end
			
			gs_logger.info("CompanyDetailServiceImpl findFinancByCode()方法结束");
			
		} catch (Exception e) {
			gs_logger.error("CompanyDetailServiceImpl findFinancByCode()方法异常",e);
			throw e;
			
		}
		gs_logger.info("CompanyDetailServiceImpl findFinancByCode()方法结束");
		return financList;
	}
	@Override
	public long findVersionByCode(String code) throws Exception {
		// TODO Auto-generated method stub
gs_logger.info("CompanyDetailServiceImpl findVersionByCode()方法开始");
		
		//公司基础表排他锁版本号
		long version =0;
		try {
		 version=companyDetailDao.findVersionByCode("BaseCompdInfo.findVersionByCode", code);
			gs_logger.info("CompanyDetailServiceImpl findVersionByCode()方法结束");
			
		} catch (Exception e) {
			gs_logger.error("CompanyDetailServiceImpl findVersionByCode()方法异常",e);
			throw e;
			
		}
		gs_logger.info("CompanyDetailServiceImpl findVersionByCode()方法结束");
		return version;
	}
	@Override
	public List<Map<String, String>> findCompanyPeopleByCode(String code)
			throws Exception {
		// TODO Auto-generated method stub
gs_logger.info("CompanyDetailServiceImpl findCompanyPeopleByCode()方法开始");
		
		//公司note信息
		List<Map<String, String>> peopleLisy=null;
		try {
			peopleLisy=companyDetailDao.findCompanyPeopleByCode("baseentrepreneurinfo.findCompanyPeopleByCode", code);
			gs_logger.info("CompanyDetailServiceImpl findCompanyPeopleByCode()方法结束");
			
		} catch (Exception e) {
			gs_logger.error("CompanyDetailServiceImpl findCompanyPeopleByCode()方法异常",e);
			throw e;
			
		}
		gs_logger.info("CompanyDetailServiceImpl findCompanyPeopleByCode()方法结束");
		return peopleLisy;
	}
	@Override
	public int tranModifyCompName(BaseCompInfo view) throws Exception {
		// TODO Auto-generated method stub
		int data = 0;
		gs_logger.info("CompanyDetailServiceImpl.tranModifyCompName方法开始");
		try {
			data = companyDetailDao.tranModifyCompName(
					"BaseCompdInfo.tranModifyCompName", view);
			if(data!=0){
				/*调用存储过程*/
				storedProcedureService.callViewcompinfo(new SysStoredProcedure(view.getBase_comp_code(), "upd", "basic", view.getUpdid(),"",""));
			}
		} catch (Exception e) {
			gs_logger.error("CompanyDetailServiceImpl.tranModifyCompName方法异常",e);
			throw e;
		} finally {
			gs_logger.info("CompanyDetailServiceImpl.tranModifyCompName方法结束");
		}
		return data;
	}
	@Override
	public int tranModifyCompPeople(String posi,String code,BaseEntrepreneurInfo view) throws Exception {
		// TODO Auto-generated method stub
		int data = 0;
		BaseCompEntrepreneur compEntrepreneur = new BaseCompEntrepreneur();
		gs_logger.info("CompanyDetailServiceImpl.tranModifyCompPeople方法开始");
		try {
			data = companyDetailDao.tranModifyCompPeople(
					"baseentrepreneurinfo.tranModifyCompPeople", view);
			if(data!=0){
				compEntrepreneur.setBase_comp_code(code);
				compEntrepreneur.setBase_entrepreneur_posiname(posi);
				compEntrepreneur.setBase_entrepreneur_code(view.getBase_entrepreneur_code());
				compEntrepreneur.setUpdid(view.getUpdid());
				compEntrepreneur.setUpdtime(new Timestamp(new Date().getTime()));
				data = companyDetailDao.updateCompany(
						"basecompentrepreneur.updateCompany", compEntrepreneur);
				/*调用存储过程*/
				storedProcedureService.callViewcompinfo(new SysStoredProcedure(code, "upd", "compperson", view.getUpdid(),"",""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("CompanyDetailServiceImpl.tranModifyCompPeople方法异常",e);
			throw e;
		} finally {
			gs_logger.info("CompanyDetailServiceImpl.tranModifyCompPeople方法结束");
		}
		return data;
	}
	@Override
	public void tranModifyinsertCompPeople(String posi, String code,
			BaseEntrepreneurInfo view) throws Exception {
		// TODO Auto-generated method stub
		BaseCompEntrepreneur compEntrepreneur = new BaseCompEntrepreneur();
		gs_logger.info("CompanyDetailServiceImpl.insertCompPeople方法开始");
		try {
			companyDetailDao.insertCompPeople(
					"baseentrepreneurinfo.insertCompPeople", view);
				compEntrepreneur.setBase_comp_code(code);
				compEntrepreneur.setBase_entrepreneur_posiname(posi);
				compEntrepreneur.setBase_entrepreneur_code(view.getBase_entrepreneur_code());
				compEntrepreneur.setCreateid(view.getCreateid());
				compEntrepreneur.setCreatetime(new Timestamp(new Date().getTime()));
				compEntrepreneur.setUpdtime(compEntrepreneur.getCreatetime());
				compEntrepreneur.setUpdid(compEntrepreneur.getCreateid());
				companyDetailDao.insertComp_people(
						"basecompentrepreneur.insertComp_people", compEntrepreneur);
				/*调用存储过程*/
				storedProcedureService.callViewcompinfo(new SysStoredProcedure(code, "upd", "compperson", view.getUpdid(),"",""));
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("CompanyDetailServiceImpl.tranModifyCompPeople方法异常",e);
			throw e;
		} finally {
			gs_logger.info("CompanyDetailServiceImpl.tranModifyCompPeople方法结束");
		}
	}
	@Override
	public int insertCompNote(BaseCompnoteInfo info) throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("CompanyDetailServiceImpl.insertCompNote方法开始");
		int i;
		try {
			companyDetailDao.insertCompNote(
					"BaseCompnoteInfo.insertCompNote", info);
			i=1;
		} catch (Exception e) {
			i=0;
			e.printStackTrace();
			gs_logger.error("CompanyDetailServiceImpl.insertCompNote方法异常",e);
			throw e;
		} finally {
			gs_logger.info("CompanyDetailServiceImpl.insertCompNote方法结束");
		}
		return i;
	}
	@Override
	public int compnote_del(String code) throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("CompanyDetailServiceImpl.compnote_del方法开始");
		
		int data=0;
		try {
			data=companyDetailDao.compnote_del(
					"BaseCompnoteInfo.compnote_del", code);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("CompanyDetailServiceImpl.compnote_del方法异常",e);
			throw e;
		} finally {
			gs_logger.info("CompanyDetailServiceImpl.compnote_del方法结束");
		}
		return data;
	}
	@Override
	public void tranModifyUpdateCompLab(SysUserInfo userInfo,
			String newData, String oldData, String code, String type,String compName,String logintype)
			throws Exception {
		Timestamp timestamp = new Timestamp(new Date().getTime());
		String indutype=type;
		JSONArray newArray=JSONArray.fromObject(newData);
		JSONArray oldArray=JSONArray.fromObject(oldData);
		String[] addData=new String[newArray.size()];//添加数组
		String[] addDataName=new String[newArray.size()];//添加数组对应名称
		String[] delData=new String[oldArray.size()];//删除数组
		String[] delDataName=new String[oldArray.size()];//删除数组对应名称
		int ai=0,di=0;
		boolean bl=false;//判断标识
		String lable="";//识别修改行业标签，还是筐标签
		if("Lable-indu".equals(type)){
			indutype="Lable-comindu";
			lable=CommonUtil.findNoteTxtOfXML("company")+CommonUtil.findNoteTxtOfXML("induLable");
		}else if("Lable-bask".equals(type)){
			lable=CommonUtil.findNoteTxtOfXML("company")+CommonUtil.findNoteTxtOfXML("baskLable");
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
					Timestamp time=new Timestamp(new Date().getTime());
					/*添加公司标签*/
					dao.insertComplabelInfo("basecomplabelinfo.insertComplabelInfo",
							new BaseComplabelInfo(code, addData[i], CommonUtil.findNoteTxtOfXML(indutype), "0", userInfo.getSys_user_code(), time, userInfo.getSys_user_code(), time));
					/*添加系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-company"),
							CommonUtil.findNoteTxtOfXML("Lable-company-name"),
							code, 
							compName,
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
							CommonUtil.findNoteTxtOfXML("add")+lable,
							"",
							"添加["+addDataName[i]+"]",
							logintype,
							userInfo.getSys_user_code(),
							timestamp,
							userInfo.getSys_user_code(),
							timestamp);
				} catch (Exception e) {
					gs_logger.error("tranModifyUpdateCompLab 添加投资机构标签 方法异常",e);
					e.printStackTrace();
					throw e;
				}
			}
		}
		
			for (int i = 0; i < delData.length; i++) {
				if (delData[i]!=null) {
				try {
					Timestamp time=new Timestamp(new Date().getTime());
					/*删除公司标签*/
					dao.tranDeleteComplabel("basecomplabelinfo.tranDeleteInveslabel",
							new BaseComplabelInfo(code, delData[i], CommonUtil.findNoteTxtOfXML(indutype), "0", userInfo.getSys_user_code(), time, userInfo.getSys_user_code(), time));
					/*添加系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-company"),
							CommonUtil.findNoteTxtOfXML("Lable-company-name"),
							code, 
							compName,
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
							CommonUtil.findNoteTxtOfXML("del")+lable,
							"删除["+delDataName[i]+"]",
							"",
							logintype,
							userInfo.getSys_user_code(),
							timestamp,
							userInfo.getSys_user_code(),
							timestamp);
				} catch (Exception e) {
					gs_logger.error("tranModifyUpdateCompLab 删除投资机构标签 方法异常",e);
					e.printStackTrace();
					throw e;
				}
			}
		}
		/*调用存储过程*/
		storedProcedureService.callViewcompinfo(new SysStoredProcedure(code, "upd", CommonUtil.findNoteTxtOfXML(type), userInfo.getSys_user_code(),"",""));
		
	}
	@Override
	public int insertFinancplan(BaseFinancplanInfo baseFinancplanInfo)
			throws Exception {
		// TODO Auto-generated method stub
		//公司详细信息
				int data=0;
				
				try {
					companyDetailDao.insertFinancplan("BaseFinancplanInfo.insertFinancplan", baseFinancplanInfo);

					data=1;
					gs_logger.info("CompanyDetailServiceImpl insertFinancplan()方法结束");
					
				} catch (Exception e) {
					data=0;
					gs_logger.error("CompanyDetailServiceImpl insertFinancplan()方法异常",e);
					throw e;
					
				}
				gs_logger.info("CompanyDetailServiceImpl insertFinancplan()方法结束");
				return data;
	}
	@Override
	public int insertPlanEamil(BaseFinancplanEmail baseFinancplanEmail)
			throws Exception {
		// TODO Auto-generated method stub
		//公司详细信息
		int data=0;
		
		try {
			companyDetailDao.insertPlanEamil("BaseFinancplanEmail.insertPlanEamil",baseFinancplanEmail);

			data=1;
			gs_logger.info("CompanyDetailServiceImpl insertPlanEamil()方法结束");
			
		} catch (Exception e) {
			data=0;
			gs_logger.error("CompanyDetailServiceImpl insertPlanEamil()方法异常",e);
			throw e;
			
		}
		gs_logger.info("CompanyDetailServiceImpl insertPlanEamil()方法结束");
		return data;
	}
	@Override
	public int tranModifyCompPeo(String posi, String code,
			BaseEntrepreneurInfo view) throws Exception {
		// TODO Auto-generated method stub
		int data = 0;
		BaseCompEntrepreneur compEntrepreneur = new BaseCompEntrepreneur();
		gs_logger.info("CompanyDetailServiceImpl.tranModifyCompPeo方法开始");
		try {
			data = companyDetailDao.tranModifyCompPeople(
					"baseentrepreneurinfo.tranModifyCompPeople", view);
			if(data!=0){
				compEntrepreneur.setBase_comp_code(code);
				compEntrepreneur.setBase_entrepreneur_posiname(posi);
				compEntrepreneur.setBase_entrepreneur_code(view.getBase_entrepreneur_code());
				compEntrepreneur.setCreateid(view.getUpdid());
				compEntrepreneur.setCreatetime(new Timestamp(new Date().getTime()));
				compEntrepreneur.setUpdtime(compEntrepreneur.getCreatetime());
				compEntrepreneur.setUpdid(compEntrepreneur.getCreateid());
				compEntrepreneur.setDeleteflag("0");
				companyDetailDao.insertComp_people(
						"basecompentrepreneur.insertComp_people", compEntrepreneur);
				/*调用存储过程*/
				storedProcedureService.callViewcompinfo(new SysStoredProcedure(code, "upd", "compperson", view.getUpdid(),"",""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("CompanyDetailServiceImpl.tranModifyCompPeo方法异常",e);
			throw e;
		} finally {
			gs_logger.info("CompanyDetailServiceImpl.tranModifyCompPeo方法结束");
		}
		return data;
	}
	@Override
	public int selectPeople(Map map) throws Exception {
		// TODO Auto-generated method stub
		int data = 0;
		try{
			data=companyDetailDao.selectPeople("basecompentrepreneur.selectPeople", map);
		}catch(Exception e){
			gs_logger.error("CompanyDetailServiceImpl selectPeople方法异常",e);
			throw e;
		}
		gs_logger.info("CompanyDetailServiceImpl selectPeople方法结束");
		return data;
	}
	@Override
	public int tranModifyaddFinancplan(
		BaseFinancplanInfo baseFinancplanInfo,
		BaseFinancplanEmail baseFinancplanEmail) throws Exception {
	    gs_logger.info("CompanyDetailServiceImpl tranModifyaddFinancplan()方法开始");
	  //公司详细信息
		int data=0;
		
		try {
			companyDetailDao.insertFinancplan("BaseFinancplanInfo.insertFinancplan", baseFinancplanInfo);
			companyDetailDao.insertPlanEamil("BaseFinancplanEmail.insertPlanEamil",baseFinancplanEmail);
			data=1;
			
		} catch (Exception e) {
			data=0;
			gs_logger.error("CompanyDetailServiceImpl tranModifyaddFinancplan()方法异常",e);
			throw e;
			
		}
		gs_logger.info("CompanyDetailServiceImpl tranModifyaddFinancplan()方法结束");
		return data;
	}
}
