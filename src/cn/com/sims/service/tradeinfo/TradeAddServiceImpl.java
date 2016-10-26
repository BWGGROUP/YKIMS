package cn.com.sims.service.tradeinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.dao.Investment.InvestmentDao;
import cn.com.sims.dao.baseinvestmentinfo.IBaseInvestmentInfoDao;
import cn.com.sims.dao.baseinvestmentinvestor.IBaseInvestmentInvestorDao;
import cn.com.sims.dao.basetradeinfo.IBaseTradeInfoDao;
import cn.com.sims.dao.basetradeinves.IBaseTradeInvesDao;
import cn.com.sims.dao.basetradelabelinfo.IBaseTradelabelInfoDao;
import cn.com.sims.dao.basetradenoteinfo.IBaseTradenoteInfoDao;
import cn.com.sims.dao.company.companycommon.CompanyCommonDao;
import cn.com.sims.dao.company.companydetail.CompanyDetailDao;
import cn.com.sims.dao.investor.InvestorDao;
import cn.com.sims.dao.system.sysstoredprocedure.ISysStoredProcedureDao;
import cn.com.sims.model.basecompinfo.BaseCompInfo;
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.baseinvestmentinvestor.BaseInvestmentInvestor;
import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.basetradeinfo.BaseTradeInfo;
import cn.com.sims.model.basetradeinves.BaseTradeInves;
import cn.com.sims.model.basetradelabelinfo.BaseTradelabelInfo;
import cn.com.sims.model.basetradenoteinfo.BaseTradenoteInfo;
import cn.com.sims.model.sysstoredprocedure.SysStoredProcedure;
import cn.com.sims.model.viewInvestmentInfo.viewInvestmentInfo;
import cn.com.sims.model.viewInvestorInfo.viewInvestorInfo;
import cn.com.sims.model.viewcompinfo.viewCompInfo;

/**
 * @ClassName: TradeAddServiceImpl
 * @author rqq
 * @date 2015-11-02
 */
@Service
public class TradeAddServiceImpl implements ITradeAddService {


    // 公司dao
    @Resource
    CompanyDetailDao compdao;
    
    // 投资人dao
    @Resource
    InvestorDao investordao;
    
    // 机构dao业务
    @Resource
    InvestmentDao investmentdao;
    
    // 机构基础
    @Resource
    IBaseInvestmentInfoDao baseinvestmentinfodao;
    
    // 公司基础
    @Resource
    CompanyCommonDao companydao;
    
    // 存储过程
    @Resource
    ISysStoredProcedureDao sysstoredproceduredao;
    
    //机构与投资人关系dao基础
    @Resource
    IBaseInvestmentInvestorDao baseinvestmentinvestordao;
    
    //交易dao基础
    @Resource
    IBaseTradeInfoDao basetradeinfodao;
    
    //交易标签dao基础
    @Resource
    IBaseTradelabelInfoDao basetradelabelinfodao;
    
    //机构交易dao基础
    @Resource
    IBaseTradeInvesDao basetradeinvesdao;
    
    //交易note基础
    @Resource
    IBaseTradenoteInfoDao basetradenoteinfodao;
    
    private static final Logger gs_logger = Logger
	    .getLogger(TradeAddServiceImpl.class);

    @Override
    public int querycompanylistnumByname(Map<String, Object> map)
	    throws Exception {
    	gs_logger.info("TradeAddServiceImpl querycompanylistnumByname方法开始");
    	int i=0;
    	try {
    		i=compdao.querycompanylistnumByname("viewCompInfo.querycompanylistnumByname", map);
    		gs_logger.info("TradeAddServiceImpl querycompanylistnumByname方法结束");
    	} catch (Exception e) {
    		gs_logger.error("TradeAddServiceImpl querycompanylistnumByname方法异常",e);
    		throw e;
    		}
    	return i;
    }

    @Override
    public List<viewCompInfo> querycompanylistByname(Map<String, Object> map)
	    throws Exception {
    	gs_logger.info("TradeAddServiceImpl companylistByname方法开始");
    	List<viewCompInfo> list = new ArrayList<viewCompInfo>();
    	try {
    		list=compdao.querycompanylistByname("viewCompInfo.querycompanylistByname", map);
    		gs_logger.info("TradeAddServiceImpl querycompanylistByname方法结束");
    	} catch (Exception e) {
    		gs_logger.error("TradeAddServiceImpl querycompanylistByname方法异常",e);
    		throw e;
    		}
    	gs_logger.info("TradeAddServiceImpl querycompanylistByname方法结束");
    	return list;
    }

    @Override
    public int queryInvestorlistnumByOrgId(Map<String, Object> map)
	    throws Exception {
		gs_logger.info("TradeAddServiceImpl queryInvestorlistnumByOrgId方法开始");
    	int i=0;
    	try {
    		i=investordao.queryInvestorlistnumByOrgId("viewInvestorInfo.queryInvestorlistnumByOrgId", map);
    		gs_logger.info("TradeAddServiceImpl queryInvestorlistnumByOrgId方法结束");
    	} catch (Exception e) {
    		gs_logger.error("TradeAddServiceImpl queryInvestorlistnumByOrgId方法异常",e);
    		throw e;
    		}
    	return i;
    }

    @Override
    public List<viewInvestorInfo> queryInvestorlistByOrgId(Map<String, Object> map)
	    throws Exception {
		gs_logger.info("TradeAddServiceImpl queryInvestorlistByOrgId方法开始");
    	List<viewInvestorInfo> list = new ArrayList<viewInvestorInfo>();
    	try {
    		list=investordao.queryInvestorlistByOrgId("viewInvestorInfo.queryInvestorlistByOrgId", map);
    		gs_logger.info("TradeAddServiceImpl queryInvestorlistByOrgId方法结束");
    	} catch (Exception e) {
    		gs_logger.error("TradeAddServiceImpl queryInvestorlistByOrgId方法异常",e);
    		throw e;
    		}
    	gs_logger.info("TradeAddServiceImpl queryInvestorlistByOrgId方法结束");
    	return list;
    }

    
    @Override
    public int queryInvestmentlistnumByOrgname(Map<String, Object> map)
	    throws Exception {
		gs_logger.info("TradeAddServiceImpl queryInvestmentlistnumByOrgname方法开始");
    	int i=0;
    	try {
    		i=investmentdao.queryInvestmentlistnumByOrgname("viewInvestmentInfo.queryInvestmentlistnumByOrgname", map);
    		gs_logger.info("TradeAddServiceImpl queryInvestmentlistnumByOrgname方法结束");
    	} catch (Exception e) {
    		gs_logger.error("TradeAddServiceImpl queryInvestmentlistnumByOrgname方法异常",e);
    		throw e;
    		}
    	return i;
    }

    @Override
    public List<viewInvestmentInfo> queryInvestmentlistByOrgname(Map<String, Object> map)
	    throws Exception {
		gs_logger.info("TradeAddServiceImpl queryInvestmentlistByOrgname方法开始");
    	List<viewInvestmentInfo> list = new ArrayList<viewInvestmentInfo>();
    	try {
    		list=investmentdao.queryInvestmentlistByOrgname("viewInvestmentInfo.queryInvestmentlistByOrgname", map);
    		gs_logger.info("TradeAddServiceImpl queryInvestmentlistByOrgname方法结束");
    	} catch (Exception e) {
    		gs_logger.error("TradeAddServiceImpl queryInvestmentlistByOrgname方法异常",e);
    		throw e;
    		}
    	gs_logger.info("TradeAddServiceImpl queryInvestmentlistByOrgname方法结束");
    	return list;
    }

    @Override
    public int queryInvestmentOnlyNamefottrade(String name) throws Exception {
	gs_logger.info("TradeAddServiceImpl queryInvestmentOnlyNamefottrade方法开始");
    	int i=0;
    	try {
    		i=baseinvestmentinfodao.queryInvestmentOnlyNamefottrade("baseInvestmentInfo.queryInvestmentOnlyNamefottrade", name);
    		gs_logger.info("TradeAddServiceImpl queryInvestmentOnlyNamefottrade方法结束");
    	} catch (Exception e) {
    		gs_logger.error("TradeAddServiceImpl queryInvestmentOnlyNamefottrade方法异常",e);
    		throw e;
    		}
    	return i;
    }

    @Override
    public int queryCompOnlyNamefortrade(String name) throws Exception {
	gs_logger.info("TradeAddServiceImpl queryCompOnlyNamefortrade方法开始");
    	int i=0;
    	try {
    		i=compdao.queryCompOnlyNamefortrade("viewCompInfo.queryCompOnlyNamefortrade", name);
    		gs_logger.info("TradeAddServiceImpl queryCompOnlyNamefortrade方法结束");
    	} catch (Exception e) {
    		gs_logger.error("TradeAddServiceImpl queryCompOnlyNamefortrade方法异常",e);
    		throw e;
    		}
    	return i;
    }

    @Override
    public void tranModifyaddtradeinfo(BaseCompInfo basecompinfo,
	    List<baseInvestmentInfo> baseinvestmentinfolist,
	    List<BaseInvestorInfo> baseinvestorinfolist,
	    List<BaseInvestmentInvestor> baseinvestmentinvestorlist,
	    BaseTradeInfo basetradeinfo,
	    List<BaseTradelabelInfo> basetradelabellist,
	    List<BaseTradeInves> basetradeinveslist,
	    BaseTradenoteInfo basetradenoteinfo) throws Exception {
		gs_logger.info("TradeAddServiceImpl tranModifyaddtradeinfo方法开始");
    	try {
    	    //添加公司
    		if(basecompinfo!=null && !"".equals(basecompinfo)){
    		companydao.insertBaseCompforIT(
    			    "BaseCompdInfo.insertBaseCompforIT", basecompinfo);
			/* 调用存储过程,更新业务数据库 */
			sysstoredproceduredao.callViewinvestment(
				"SysStoredProcedure.callViewcompinfo",
				new SysStoredProcedure(
					basecompinfo.getBase_comp_code(),"add","all",
					basecompinfo.getCreateid(),"", ""));
    			}
    		
    		//添加投资机构
    		for(int i=0;i<baseinvestmentinfolist.size();i++){
    		baseInvestmentInfo baseinvestmentinfo=baseinvestmentinfolist.get(i);
    		baseinvestmentinfodao.insertBaseInvestmentforIT(
			"baseInvestmentInfo.insertBaseInvestmentforIT",baseinvestmentinfo);
			/* 调用存储过程,更新业务数据库 */
			sysstoredproceduredao.callViewinvestment(
				"SysStoredProcedure.callViewinvestment",
				new SysStoredProcedure(baseinvestmentinfo.getBase_investment_code(),"add","all",
					baseinvestmentinfo.getCreateid(),"", ""));
    			}
    		
    		//添加投资人
    		for(int i=0;i<baseinvestorinfolist.size();i++){
    		BaseInvestorInfo baseinvestorinfo=baseinvestorinfolist.get(i);
        		investordao.insertBaseInvestorforIT(
        			"baseInvestorInfo.insertBaseInvestorforIT",baseinvestorinfo);
			/* 调用存储过程,更新业务数据库 */
            sysstoredproceduredao.callViewinvestment(
    				"SysStoredProcedure.callViewinvestorInfo",
    				new SysStoredProcedure(baseinvestorinfo.getBase_investor_code(),"add","all",
    					baseinvestorinfo.getCreateid(),"", ""));
    			}
    		
    		//添加投资人机构关系
    		for(int i=0;i<baseinvestmentinvestorlist.size();i++){
    		BaseInvestmentInvestor baseinvestmentinvestor=baseinvestmentinvestorlist.get(i);
    		baseinvestmentinvestordao.insertInvestmentInvestorforit(
			"BaseInvestmentInvestor.insertInvestmentInvestorforit",baseinvestmentinvestor);
			/* 调用存储过程,更新业务数据库 */
        	sysstoredproceduredao.callViewinvestment(
        			"SysStoredProcedure.callViewinvestorInfo",
        			new SysStoredProcedure(
        				baseinvestmentinvestor.getBase_investor_code(),"upd","investment",
        				baseinvestmentinvestor.getCreateid(),"", ""));
    			}
    		
    		   //添加交易
    		basetradeinfodao.insertBaseTradeinfoforIT(
				"BaseTradeInfo.insertBaseTradeinfoforIT", basetradeinfo);
    		
    		//添加交易标签
    		for (int i = 0; i < basetradelabellist.size(); i++) {
		    basetradelabelinfodao.insertBaseTradelabelInfoforit(
			    "basetradelabelinfo.insertBaseTradelabelInfoforit",
			    basetradelabellist.get(i));
			}
    		//添加机构交易
    		for (int i = 0; i < basetradeinveslist.size(); i++) {
        		basetradeinvesdao.insertBaseTradeInveforIT(
    				"basetradeinves.insertBaseTradeInveforIT", basetradeinveslist.get(i));
			}
    		
		/*添加交易， 调用存储过程,更新业务数据库 */
		sysstoredproceduredao.callViewinvestment(
				"SysStoredProcedure.callViewtradeser",
				new SysStoredProcedure(
					basetradeinfo.getBase_trade_code(),"add","all",
					basetradeinfo.getCreateid(),"", ""));
		  //添加交易note
    		if(basetradenoteinfo!=null && !"".equals(basetradenoteinfo)){
    		basetradenoteinfodao.insertBaseTradenoteInfo(
				"BaseTradenoteInfo.insertBaseTradenoteInfo", basetradenoteinfo);
    			}
    		gs_logger.info("TradeAddServiceImpl tranModifyaddtradeinfo方法结束");
    	} catch (Exception e) {
    		gs_logger.error("TradeAddServiceImpl tranModifyaddtradeinfo方法异常",e);
    		throw e;
    		}
	
    }
  
}