package cn.com.sims.service.baseinvesreponinfo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import cn.com.sims.dao.baseinvesreponinfo.IBaseInvesreponInfoDao;
import cn.com.sims.model.baseinvesreponinfo.BaseInvesreponInfo;

/**
 * 
 * @author duwenjie
 * @date 2015-10-12
 *
 */

@Service
public class IBaseInvesreponInfoServiceImpl implements IBaseInvesreponInfoService {

	@Resource
	IBaseInvesreponInfoDao dao;
	
	private static final Logger gs_logger = Logger.getLogger(IBaseInvesreponInfoServiceImpl.class);
	
	
	@Override
	public List<Map<String, String>> findYKLinkManByOrgCode(String orgCode)
			throws Exception {
		gs_logger.info("findYKLinkManByOrgCode方法开始");
		List<Map<String, String>> list=null;
		try {
			list=dao.findYKLinkManByOrgCode("BaseInvesreponInfo.findLinkManByOrgCode", orgCode);
		} catch (Exception e) {
			gs_logger.error("findYKLinkManByOrgCode方法异常",e);
			throw e;
		}finally{
			gs_logger.info("findYKLinkManByOrgCode方法结束");
		}
		
		return list;
	}


	@Override
	public void insertYKLinkMan(BaseInvesreponInfo info) throws Exception {
		gs_logger.info("insertYKLinkMan方法开始");
		try {
			dao.insertYKLinkMan("BaseInvesreponInfo.insertYKLinkMan",info);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("insertYKLinkMan方法异常",e);
			throw e;
		}finally{
			gs_logger.info("insertYKLinkMan方法结束");
		}
		
	}


	@Override
	public int tranModifyYKLinkMan(String orgCode, String oldUserCode,
			String newUserCode, String newUserName,String updCode) throws Exception {
		int data=0;
		gs_logger.info("tranModifyYKLinkMan方法开始");
		try {
			BaseInvesreponInfo info=new BaseInvesreponInfo();
			info.setBase_investment_code(orgCode);
			info.setSys_user_code(newUserCode);
			info.setSys_user_name(newUserName);
			info.setUpdid(updCode);
			info.setUpdtime(new Timestamp(new Date().getTime()));
			info.setCreateid(oldUserCode);//把原易凯用户code存进createid进行传参
			
			data=dao.tranModifyYKLinkMan("BaseInvesreponInfo.tranModifyYKLinkMan", info);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("tranModifyYKLinkMan方法异常",e);
			throw e;
		}finally{
			gs_logger.info("tranModifyYKLinkMan方法结束");
		}
		return data;
	}

}
