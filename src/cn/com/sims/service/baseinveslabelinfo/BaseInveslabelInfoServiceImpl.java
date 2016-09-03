package cn.com.sims.service.baseinveslabelinfo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.dao.baseinveslabelinfo.IBaseInveslabelInfoDao;
import cn.com.sims.model.baseinveslabelinfo.BaseInveslabelInfo;
import cn.com.sims.model.sysstoredprocedure.SysStoredProcedure;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.service.baseinvesreponinfo.IBaseInvesreponInfoServiceImpl;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.system.sysstoredprocedure.ISysStoredProcedureService;

/**
 * 
 * @author duwenjie
 * @date 2015-10-13
 */

@Service
public class BaseInveslabelInfoServiceImpl implements IBaseInveslabelInfoService{

	@Resource
	IBaseInveslabelInfoDao dao;
	
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;
	
	@Resource
	ISysStoredProcedureService storedProcedureService;//存储过程
	
	private static final Logger gs_logger = Logger.getLogger(IBaseInvesreponInfoServiceImpl.class);
	
	
	
	@Override
	public void insertOrgInveslabelInfo(BaseInveslabelInfo info)
			throws Exception {
		gs_logger.info("insertOrgInveslabelInfo 方法开始");
		try {
			dao.insertOrgInveslabelInfo("BaseInveslabelInfo.insertOrgInveslabelInfo", info);
		} catch (Exception e) {
			gs_logger.error("insertOrgInveslabelInfo 方法异常");
			e.printStackTrace();
			throw e;
		}finally{
			gs_logger.info("insertOrgInveslabelInfo方法结束");
		}
	}

	@Override
	public void tranDeleteInveslabel(BaseInveslabelInfo info) throws Exception {
		gs_logger.info("tranDeleteInveslabel 方法开始");
		try {
			dao.insertOrgInveslabelInfo("BaseInveslabelInfo.tranDeleteInveslabel", info);
		} catch (Exception e) {
			gs_logger.error("tranDeleteInveslabel 方法异常",e);
			e.printStackTrace();
			throw e;
		}finally{
			gs_logger.info("tranDeleteInveslabel方法结束");
		}
	}

	@Override
	public void tranModifyUpdateBaseInvestment(SysUserInfo userInfo,
			String newData, String oldData, String orgCode, String type,String orgName,String logintype)
			throws Exception {
		String indutype=type;
		Timestamp timestamp = new Timestamp(new Date().getTime());
		JSONArray newArray=JSONArray.fromObject(newData);
		JSONArray oldArray=JSONArray.fromObject(oldData);
		String[] addData=new String[newArray.size()];//添加数组
		String[] addDataName=new String[newArray.size()];//添加数组对应名称
		String[] delData=new String[oldArray.size()];//删除数组
		String[] delDataName=new String[oldArray.size()];//删除数组对应名称
		int ai=0,di=0;
		boolean bl=false;//判断标识
		if(type.equals("Lable-indu")){
			indutype="Lable-orgindu";
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
		try {
			for (int i = 0; i < addData.length; i++) {
				if(addData[i]!=null){
						/*添加投资机构标签*/
						dao.insertOrgInveslabelInfo("BaseInveslabelInfo.insertOrgInveslabelInfo",
								new BaseInveslabelInfo(orgCode, addData[i], CommonUtil.findNoteTxtOfXML(indutype), "", userInfo.getSys_user_code(), timestamp, userInfo.getSys_user_code(), timestamp));
						/*添加系统更新记录*/
						baseUpdlogInfoService.insertUpdlogInfo(
								CommonUtil.findNoteTxtOfXML("Lable-investment"),
								CommonUtil.findNoteTxtOfXML("Lable-investment-name"),
								orgCode, 
								orgName,
								CommonUtil.OPERTYPE_YK,
								userInfo.getSys_user_code(), 
								userInfo.getSys_user_name(),
								CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
								CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
								CommonUtil.findNoteTxtOfXML("upd")+
								CommonUtil.findNoteTxtOfXML("investment")+
								CommonUtil.findNoteTxtOfXML(indutype+"-name"),
								"",
								"添加标签["+addDataName[i]+"]",
								logintype,
								userInfo.getSys_user_code(),
								timestamp,
								userInfo.getSys_user_code(),
								timestamp);
				}
			}
			
			for (int i = 0; i < delData.length; i++) {
				if (delData[i]!=null) {
						/*删除投资机构标签*/
						dao.tranDeleteInveslabel("BaseInveslabelInfo.tranDeleteInveslabel",
								new BaseInveslabelInfo(orgCode, delData[i], CommonUtil.findNoteTxtOfXML(indutype), null, userInfo.getSys_user_code(), null, null, null));
						/*添加系统更新记录*/
						baseUpdlogInfoService.insertUpdlogInfo(
								CommonUtil.findNoteTxtOfXML("Lable-investment"),
								CommonUtil.findNoteTxtOfXML("Lable-investment-name"),
								orgCode, 
								orgName,
								CommonUtil.OPERTYPE_YK,
								userInfo.getSys_user_code(), 
								userInfo.getSys_user_name(),
								CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
								CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
								CommonUtil.findNoteTxtOfXML("upd")+
								CommonUtil.findNoteTxtOfXML("investment")+
								CommonUtil.findNoteTxtOfXML(indutype+"-name"),
								"删除标签["+delDataName[i]+"]",
								"",
								logintype,
								userInfo.getSys_user_code(),
								timestamp,
								userInfo.getSys_user_code(),
								timestamp);
				}
			}
			
			/*调用存储过程*/
			storedProcedureService.callViewinvestment(new SysStoredProcedure(orgCode, "upd", CommonUtil.findNoteTxtOfXML(type), userInfo.getSys_user_code(),"",""));
		} catch (Exception e) {
			gs_logger.error("tranModifyUpdateBaseInvestment 修改投资机构标签 方法异常",e);
			e.printStackTrace();
			throw e;
		}
		
		
	}

	/**
	 * 根据投资机构code查询投资机构交易记录标签信息
	 * @param orgCode机构code
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 */
	@Override
	public List<Map<String, String>> findTradeLabelByOrgCode(String orgCode)
			throws Exception {
		gs_logger.info("findTradeLabelByOrgCode 方法开始");
		List<Map<String, String>> list=null;
		try {
			list=dao.findTradeLabelByOrgCode("BaseInveslabelInfo.findTradeLabelByOrgCode", orgCode);
		} catch (Exception e) {
			gs_logger.error("findTradeLabelByOrgCode 方法异常",e);
			e.printStackTrace();
			throw e;
		}finally{
			gs_logger.info("findTradeLabelByOrgCode方法结束");
		}
		return list;
	}

}