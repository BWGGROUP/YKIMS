package cn.com.sims.service.baseinvestornoteinfo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.dao.baseinvestornote.IInvestornoteDao;
import cn.com.sims.model.baseinvestornoteinfo.BaseInvestornoteInfo;
import cn.com.sims.service.sysuserinfo.SysUserInfoServiceImpl;

/**
 * @author yl
 * @date ：2015年10月16日 投资人note　service层（基础数据）
 */
@Service
public class InvestorNoteServiceImpl implements IInvestorNoteService {
	@Resource
	IInvestornoteDao investornoteDao;// 投资人note信息dao
	private static final Logger gs_logger = Logger
			.getLogger(SysUserInfoServiceImpl.class);

	@Override
	public List<Map<String, String>> findNoteByInvestorCode(String code)
			throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("InvestorNoteServiceImpl findNoteByInvestorCode方法开始");
		List<Map<String, String>> investorNoteList = null;// 定义投资机构交易集合
		try {
			investorNoteList = investornoteDao.findTradeInfoByInvestorCode(
					"baseInvestornoteInfo.findNoteByInvestorCode", code);
			gs_logger
					.info("InvestorNoteServiceImpl findNoteByInvestorCode方法结束");
		} catch (Exception e) {
			gs_logger.error("InvestorNoteServiceImpl findNoteByInvestorCode方法异常",e);
			throw e;
		}
		return investorNoteList;
	}

	@Override
	public int investotnote_del(String id) throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("InvestorNoteServiceImpl investotnote_del方法 开始");
		int i = 0;
		try {
			i = investornoteDao.investornote_del(
					"baseInvestornoteInfo.investotnote_del", id);
			gs_logger.info("InvestorNoteServiceImpl investotnote_del方法 结束");
		} catch (Exception e) {
			gs_logger.error("InvestorNoteServiceImpl investotnote_del方法 异常",e);
			throw e;
		}
		return i;
	}

	@Override
	public void insertInvestorNote(BaseInvestornoteInfo noteInfo)
			throws Exception {
		// TODO Auto-generated method stub
		gs_logger.info("InvestorNoteServiceImpl.insertInvestorNote方法开始");
		try {
			investornoteDao.insertInvestorNote(
					"baseInvestornoteInfo.insertInvestorNote", noteInfo);
		} catch (Exception e) {
			e.printStackTrace();
			gs_logger.error("InvestorNoteServiceImpl.insertInvestorNote方法异常",e);
			throw e;
		} finally {
			gs_logger.info("InvestorNoteServiceImpl.insertInvestorNote方法结束");
		}
	}

}
