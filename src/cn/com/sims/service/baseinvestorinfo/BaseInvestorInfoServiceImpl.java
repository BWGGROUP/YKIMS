package cn.com.sims.service.baseinvestorinfo;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.dao.baseinvestmentinvestor.IBaseInvestmentInvestorDao;
import cn.com.sims.dao.investor.InvestorDao;
import cn.com.sims.dao.system.sysstoredprocedure.ISysStoredProcedureDao;
import cn.com.sims.model.baseinvestmentinvestor.BaseInvestmentInvestor;
import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.sysstoredprocedure.SysStoredProcedure;
import cn.com.sims.service.baseinvesreponinfo.IBaseInvesreponInfoServiceImpl;

/**
 * 
 * @author duwenjie
 *
 */
@Service
public class BaseInvestorInfoServiceImpl implements IBaseInvestorInfoService{

	 //机构与投资人关系dao基础
    @Resource
    IBaseInvestmentInvestorDao baseinvestmentinvestordao;
    
    // 投资人dao
    @Resource
    InvestorDao investordao;
    
    // 存储过程
    @Resource
    ISysStoredProcedureDao sysstoredproceduredao;
    
    
    private static final Logger gs_logger = Logger.getLogger(IBaseInvesreponInfoServiceImpl.class);
	
    
    /**
     * 添加投资人及机构投资人关系
     * @param info 投资人对象
     * @param invesInvesotrInfo 投资机构投资人
     * 
     */
	@Override
	public void tranModifyInsertBaseInvestorOrBetweenInvestment(BaseInvestorInfo info,BaseInvestmentInvestor invesInvesotrInfo)
			throws Exception {
		gs_logger.info("tranModifyInsertBaseInvestorInfo 方法开始");
		try {
			//投资人
			if(info!=null){
			investordao.insertBaseInvestorforIT(
        			"baseInvestorInfo.insertBaseInvestorforIT",info);
			/* 调用存储过程,更新业务数据库 */
            sysstoredproceduredao.callViewinvestment(
    				"SysStoredProcedure.callViewinvestorInfo",
    				new SysStoredProcedure(info.getBase_investor_code(),"add","all",
    						info.getCreateid(),"", ""));
			}
            //投资人投资机构关系
            if(invesInvesotrInfo!=null){
            	baseinvestmentinvestordao.insertInvestmentInvestorforit(
            			"BaseInvestmentInvestor.insertInvestmentInvestorforit",invesInvesotrInfo);
            			/* 调用存储过程,更新业务数据库 */
                    	sysstoredproceduredao.callViewinvestment(
                    			"SysStoredProcedure.callViewinvestorInfo",
                    			new SysStoredProcedure(
                    					invesInvesotrInfo.getBase_investor_code(),"upd","investment",
                    					invesInvesotrInfo.getCreateid(),"", ""));
                			
            }
    			
		} catch (Exception e) {
			gs_logger.error("tranModifyInsertBaseInvestorInfo 方法异常",e);
			throw e;
		}
		gs_logger.info("tranModifyInsertBaseInvestorInfo 方法结束");
	}

}
