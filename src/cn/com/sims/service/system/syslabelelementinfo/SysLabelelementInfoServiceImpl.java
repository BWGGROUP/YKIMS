package cn.com.sims.service.system.syslabelelementinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.dao.system.syslabelelementinfo.ISysLabelelementInfoDao;
import cn.com.sims.model.syslabelelement.SysLabelelementInfo;

/**
 * 
 * @author duwenjie
 *	2015-10-12
 */

@Service
public class SysLabelelementInfoServiceImpl implements ISysLabelelementInfoService{
	
	@Resource
	ISysLabelelementInfoDao dao;
	
	private static final Logger gs_logger = Logger.getLogger(SysLabelelementInfoServiceImpl.class);
	
	@Override
	public List<Map<String, String>> findDIC(String labelCode) throws Exception {
		gs_logger.info("findDIC方法开始");
		List<Map<String, String>> list=null;
		try {
			list=dao.findDIC("SysLabelelementInfo.findDIC", labelCode); 
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("findDIC方法异常",e);
			throw e;
		}finally{
			gs_logger.info("findDIC方法结束");
		}

		return list;
	}

	@Override
	public List<Map<String, String>> findAllCurrencyChild() throws Exception {
		gs_logger.info("findAllCurrencyChild方法开始");
		List<Map<String, String>> list=null;
		try {
			list=dao.findAllCurrencyChild("SysLabelelementInfo.findAllCurrencyChild"); 
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("findAllCurrencyChild方法异常",e);
			throw e;
		}finally{
			gs_logger.info("findAllCurrencyChild方法结束");
		}
		return list;
	}

	@Override
	public SysLabelelementInfo lablebycode(String code) throws Exception {
		 gs_logger.info("SysLabelelementInfoServiceImpl SysLabelelementInfo方法开始");
		SysLabelelementInfo lable=null;
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("code", code);
		try {
			lable=dao.querylabelelementbyname("SysLabelelementInfo.lablebycode",map);
		} catch (Exception e) {
			 gs_logger.error("SysLabelelementInfoServiceImpl SysLabelelementInfo方法异常",e);
			 throw e;
		}
		 gs_logger.info("SysLabelelementInfoServiceImpl SysLabelelementInfo方法结束");
		return lable;
	}

	@Override
	public Boolean updatalable(SysLabelelementInfo lable) throws Exception {
		 gs_logger.info("SysLabelelementInfoServiceImpl updatalable方法开始");
		Boolean b=false;
		try {
			dao.updatalable("SysLabelelementInfo.updatalable",lable);
			b=true;
		} catch (Exception e) {
			 gs_logger.error("SysLabelelementInfoServiceImpl SysLabelelementInfo方法异常",e);
			 throw e;
		}
		 gs_logger.info("SysLabelelementInfoServiceImpl updatalable方法结束");
		return b;
	}

	@Override
	public Boolean insertsyslabelementinfo(
			SysLabelelementInfo syslabelelementinfo) throws Exception {
		gs_logger.info("SysLabelelementInfoServiceImpl insertsyslabelementinfo方法开始");
		Boolean b=false;
		try {
			dao.insertsyslabelementinfo("SysLabelelementInfo.insertsyslabelementinfo", syslabelelementinfo);
			b=true;
		} catch (Exception e) {
			 gs_logger.error("SysLabelelementInfoServiceImpl insertsyslabelementinfo方法异常",e);
			 throw e;
		}
		 gs_logger.info("SysLabelelementInfoServiceImpl insertsyslabelementinfo方法结束");
		return b;
	}

	@Override
	public SysLabelelementInfo lablebysys_lable_codeandname(
			HashMap<String, String> map) throws Exception {
		SysLabelelementInfo lable=null;
		gs_logger.info("SysLabelelementInfoServiceImpl lablebysys_lable_codeandname方法开始");
		try {
			lable=dao.querylabelelementbyname("SysLabelelementInfo.bysyscodeandname", map);
		} catch (Exception e) {
			gs_logger.error("SysLabelelementInfoServiceImpl lablebysys_lable_codeandname方法异常",e);
			throw e;
		}
		gs_logger.info("SysLabelelementInfoServiceImpl lablebysys_lable_codeandname方法结束");
		return lable;
	}

	
	/**
	 * 查询类型标签数量
	 * @param labelcode
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-29
	 */
	@Override
	public int findLabelCountByLabelcode(String labelcode) throws Exception {
		gs_logger.info("SysLabelelementInfoServiceImpl findLabelCountByLabelcode方法开始");
		int data=0;
		try {
			data=dao.findLabelCountByLabelcode("SysLabelelementInfo.findLabelCountByLabelcode", labelcode);
		} catch (Exception e) {
			gs_logger.error("SysLabelelementInfoServiceImpl findLabelCountByLabelcode方法异常",e);
			throw e;
		}
		gs_logger.info("SysLabelelementInfoServiceImpl findLabelCountByLabelcode方法结束");
		return data;
	}

	/**
	 * 查询标签上一个序号标签信息
	 * @param map
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-29
	 */
	@Override
	public SysLabelelementInfo findLabelNextLabel(Map<String, String> map)
			throws Exception {
		SysLabelelementInfo info=null;
		gs_logger.info("SysLabelelementInfoServiceImpl findLabelNextLabel方法开始");
		try {
			info = dao.findLabelNextLabel("SysLabelelementInfo.findLabelNextLabel", map);
		} catch (Exception e) {
			gs_logger.error("SysLabelelementInfoServiceImpl findLabelNextLabel方法异常",e);
			throw e;
		}
		
		gs_logger.info("SysLabelelementInfoServiceImpl findLabelNextLabel方法结束");
		return info;
	}
	
	/**
	 * 标签元素排序
	 * @param addmap (sign:+或-，elcode标签元素code)
	 * @param minMap (sign:+或-，elcode标签元素code)
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-29
	 */
	@Override
	public int tranModifyUpdateLabelIndex(Map<String, Object> addMap,Map<String, Object> minMap)
			throws Exception {
		gs_logger.info("SysLabelelementInfoServiceImpl tranModifyUpdateLabelIndex方法开始");
		int data=0;
		try {
			data=dao.updateLabelIndex("SysLabelelementInfo.updateLabelIndex", addMap);
			if (data>0) {
				data=dao.updateLabelIndex("SysLabelelementInfo.updateLabelIndex", minMap);
			}
			
		} catch (Exception e) {
			gs_logger.error("SysLabelelementInfoServiceImpl tranModifyUpdateLabelIndex方法异常",e);
			throw e;
		}
		gs_logger.info("SysLabelelementInfoServiceImpl tranModifyUpdateLabelIndex方法结束");
		return data;
	}


}
