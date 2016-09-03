package cn.com.sims.service.basetradenoteinfo;

import java.util.List;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import cn.com.sims.dao.basetradenoteinfo.BaseTradenoteInfoDaoImpl;
import cn.com.sims.dao.basetradenoteinfo.IBaseTradenoteInfoDao;
import cn.com.sims.model.basetradenoteinfo.BaseTradenoteInfo;
import cn.com.sims.model.viewtradeinfo.ViewTradeInfo;

/**
 * 
 * @author duwenjie
 * @date 2015-10-23
 */
@Service
public class BaseTradenoteInfoServiceImpl implements IBaseTradenoteInfoService{

	@Resource
	IBaseTradenoteInfoDao dao;
	
	private static final Logger gs_logger = Logger.getLogger(BaseTradenoteInfoServiceImpl.class);
	
	/**
	 * 根据交易code查询交易备注信息
	 * @param tradeCode交易code
	 * @author duwenjie
	 * @date 2015-10-23
	 */
	@Override
	public List<BaseTradenoteInfo> findTradenoteByTradeCode(String tradeCode)
			throws Exception {
		gs_logger.info("findTradenoteByTradeCode方法开始");
		List<BaseTradenoteInfo> list=null;
		try {
			list=dao.findTradenoteByTradeCode("BaseTradenoteInfo.findTradenoteByTradeCode", tradeCode);
		} catch (Exception e) {
			gs_logger.error("findTradenoteByTradeCode方法异常",e);
			e.printStackTrace();
			throw e;
		}
		gs_logger.info("findTradenoteByTradeCode方法结束");
		return list;
	}

	
	/**
	 * 添加交易备注信息
	 * @param info 交易备注信息
	 * @author duwenjie
	 */
	@Override
	public void insertBaseTradenoteInfo(BaseTradenoteInfo info)
			throws Exception {
		gs_logger.info("insertBaseTradenoteInfo方法开始");
		try {
			dao.insertBaseTradenoteInfo("BaseTradenoteInfo.insertBaseTradenoteInfo", info);
		} catch (Exception e) {
			gs_logger.error("insertBaseTradenoteInfo方法异常",e);
			e.printStackTrace();
			throw e;
		}
		gs_logger.info("insertBaseTradenoteInfo方法结束");
	}

	
	/**
	 * 删除交易备注信息
	 * @param noteCode 交易noteid
	 * @author duwenjie
	 */
	@Override
	public int deleteBaseTradenoteInfo(String noteCode) throws Exception {
		gs_logger.info("deleteBaseTradenoteInfo方法开始");
		int data=0;
		try {
			data=dao.deleteBaseTradenoteInfo("BaseTradenoteInfo.deleteBaseTradenoteInfo", noteCode);
		} catch (Exception e) {
			gs_logger.error("deleteBaseTradenoteInfo方法异常",e);
			e.printStackTrace();
			throw e;
		}
		gs_logger.info("deleteBaseTradenoteInfo方法结束");
		return data;
	}

}
