package cn.com.sims.service.importitdata;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.dao.basecompentrepreneur.IBaseCompEntrepreneurDao;
import cn.com.sims.dao.basecomplabelinfo.IBaseComplabelInfoDao;
import cn.com.sims.dao.baseentrepreneurinfo.IBaseEntrepreneurInfoDao;
import cn.com.sims.dao.baseinveslabelinfo.IBaseInveslabelInfoDao;
import cn.com.sims.dao.baseinvestmentinfo.IBaseInvestmentInfoDao;
import cn.com.sims.dao.baseinvestmentinvestor.IBaseInvestmentInvestorDao;
import cn.com.sims.dao.baseinvestorlabelinfo.IBaseInvestorlabelInfoDao;
import cn.com.sims.dao.basetradeinfo.IBaseTradeInfoDao;
import cn.com.sims.dao.basetradeinves.IBaseTradeInvesDao;
import cn.com.sims.dao.basetradelabelinfo.IBaseTradelabelInfoDao;
import cn.com.sims.dao.company.companycommon.CompanyCommonDao;
import cn.com.sims.dao.investor.InvestorDao;
import cn.com.sims.dao.sysimportinvesestagerela.SysImportinvesestageRelaDao;
import cn.com.sims.dao.system.syslabelelementinfo.ISysLabelelementInfoDao;
import cn.com.sims.dao.system.sysstoredprocedure.ISysStoredProcedureDao;
import cn.com.sims.model.basecompentrepreneur.BaseCompEntrepreneur;
import cn.com.sims.model.basecompinfo.BaseCompInfo;
import cn.com.sims.model.basecomplabelinfo.BaseComplabelInfo;
import cn.com.sims.model.baseentrepreneurinfo.BaseEntrepreneurInfo;
import cn.com.sims.model.baseinveslabelinfo.BaseInveslabelInfo;
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.baseinvestmentinvestor.BaseInvestmentInvestor;
import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.baseinvestorlabelinfo.BaseInvestorlabelInfo;
import cn.com.sims.model.basetradeinfo.BaseTradeInfo;
import cn.com.sims.model.basetradeinves.BaseTradeInves;
import cn.com.sims.model.basetradelabelinfo.BaseTradelabelInfo;
import cn.com.sims.model.syslabelelement.SysLabelelementInfo;
import cn.com.sims.model.sysstoredprocedure.SysStoredProcedure;

/**
 * 
 * @author rqq
 * @date 2015-10-20
 */

@Service
public class ImportITdataServiceImpl implements IImportITdataService {

    @Resource
    ISysLabelelementInfoDao syslabelelementinfodao;
    @Resource
    IBaseInvestmentInfoDao baseinvestmentinfodao;
    @Resource
    IBaseInveslabelInfoDao baseinveslabelinfodao;
    @Resource
    ISysStoredProcedureDao sysstoredproceduredao;
    @Resource
    InvestorDao investordao;
    @Resource
    IBaseInvestorlabelInfoDao baseinvestorlabelinfodao;
    @Resource
    IBaseInvestmentInvestorDao baseinvestmentinvestordao;
    @Resource
    CompanyCommonDao companydao;
    @Resource
    IBaseComplabelInfoDao basecomplabelinfodao;
    @Resource
    IBaseEntrepreneurInfoDao baseentrepreneurinfodao;
    @Resource
    IBaseCompEntrepreneurDao basecompentrepreneurdao;
    @Resource
    IBaseTradeInfoDao basetradeinfodao;
    @Resource
    IBaseTradelabelInfoDao basetradelabelinfodao;
    @Resource
    IBaseTradeInvesDao basetradeinvesdao;
    @Resource
    SysImportinvesestageRelaDao sysimportinvesestagereladao;
    
    private static final Logger gs_logger = Logger
	    .getLogger(ImportITdataServiceImpl.class);

    // 查询标签是否存在
    @Override
    public SysLabelelementInfo querylabelelementbyname(Map<String, String> map)
	    throws Exception {
		SysLabelelementInfo syslabelelementinfo = null;
		gs_logger.info("ImportITdataServiceImpl  querylabelelementbyname方法开始");
    	try {
    	    syslabelelementinfo = syslabelelementinfodao.querylabelelementbyname("SysLabelelementInfo.querylabelelementbyname", map);
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  querylabelelementbyname 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  querylabelelementbyname方法结束");
    		}
	return syslabelelementinfo;
    }

    // 查询指定标签的最大code
    @Override
    public String querymaxlabelelementbycode(String labelcode) throws Exception {
		String maxlabelcode = "";
		gs_logger.info("ImportITdataServiceImpl  querymaxlabelelementbycode方法开始");
    	try {
    	    maxlabelcode = syslabelelementinfodao
    		    .querymaxlabelelementbycode("SysLabelelementInfo.querymaxlabelelementbycode",labelcode);
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  querymaxlabelelementbycode 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  querymaxlabelelementbycode方法结束");
    		}
    	return maxlabelcode;
    }

    // 插入标签
    @Override
    public void insertsyslabelementinfo(SysLabelelementInfo syslabelelementinfo)
	    throws Exception {
		gs_logger.info("ImportITdataServiceImpl  insertsyslabelementinfo方法开始");
    	try {
    	    syslabelelementinfodao.insertsyslabelementinfo(
    		    "SysLabelelementInfo.insertsyslabelementinfo",syslabelelementinfo);
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  insertsyslabelementinfo 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  insertsyslabelementinfo方法结束");
    		}
    }

    // 根据名字查询机构数据库中是否存在易凯创建或修改的(基础层)
    @Override
    public baseInvestmentInfo findBaseInvestmentBynameForIT(
	    String baseinvestmentname) throws Exception {
		gs_logger.info("ImportITdataServiceImpl  findBaseInvestmentBynameForIT方法开始");
		baseInvestmentInfo baseinvestmentinfo = null;
    	try {
    	    baseinvestmentinfo = baseinvestmentinfodao.findBaseInvestmentBynameForIT(
    			    "baseInvestmentInfo.findBaseInvestmentBynameForIT",baseinvestmentname);
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  findBaseInvestmentBynameForIT 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  findBaseInvestmentBynameForIT方法结束");
    		}
    	return baseinvestmentinfo;
    }

    // 根据对应IT桔子id查询机构数据库中是否存在记录(基础层)
    @Override
    public baseInvestmentInfo findBaseInvestmentBytmpcodeForIT(
	    String tmpinvestmentcode) throws Exception {
		gs_logger.info("ImportITdataServiceImpl  findBaseInvestmentBytmpcodeForIT方法开始");
		baseInvestmentInfo baseinvestmentinfo = null;
    	try {
    	    baseinvestmentinfo = baseinvestmentinfodao
    		    .findBaseInvestmentBytmpcodeForIT("baseInvestmentInfo.findBaseInvestmentBytmpcodeForIT",
    			    tmpinvestmentcode);
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  findBaseInvestmentBytmpcodeForIT 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  findBaseInvestmentBytmpcodeForIT方法结束");
    		}
    	return baseinvestmentinfo;
    }

    // it桔子导入机构
    @Override
    public void tranModifyimportinvestmentinfo(
	    baseInvestmentInfo baseinvestmentinfo,
	    List<BaseInveslabelInfo> baseinveslabellist, String opreString,
	    String baseinvestmentcode) throws Exception {
    	gs_logger.info("ImportITdataServiceImpl  tranModifyimportinvestmentinfo方法开始");
    	try {
    	    if ("add".equals(opreString)) {
        		baseinvestmentinfodao.insertBaseInvestmentforIT(
        			"baseInvestmentInfo.insertBaseInvestmentforIT",baseinvestmentinfo);
    	    } else if ("upd".equals(opreString)) {
        		baseinvestmentinfodao.updateBaseInvestmentforIT(
        			"baseInvestmentInfo.updateBaseInvestmentforIT",baseinvestmentinfo);
    	    }
    	    if (!"nodo".equals(opreString)) {
        		for (int i = 0; i < baseinveslabellist.size(); i++) {
        		    baseinveslabelinfodao.insertBaseInveslabelInfoforit(
        			    "BaseInveslabelInfo.insertBaseInveslabelInfoforit",baseinveslabellist.get(i));
        			}
			/* 调用存储过程,操作筐 */
			sysstoredproceduredao.callViewinvestment(
				"SysStoredProcedure.callBaskindureapro",
				new SysStoredProcedure(baseinvestmentcode,"1","",
					CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"),"", ""));
    	    }
    	    if (!"nodo".equals(opreString)) {
    			/* 调用存储过程 */
    			sysstoredproceduredao.callViewinvestment(
    				"SysStoredProcedure.callViewinvestment",
    				new SysStoredProcedure(baseinvestmentcode,opreString,"all",
    					CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"),"", ""));
    	    		}
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  tranModifyimportinvestmentinfo 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  tranModifyimportinvestmentinfo方法结束");
    		}
    }

    // 根据对应IT桔子id查询投资人数据库中是否存在记录(基础层)
    @Override
    public BaseInvestorInfo findBaseInvestorBytmpcodeForIT(
	    String tmpinvestorcode) throws Exception {
    	gs_logger.info("ImportITdataServiceImpl  findBaseInvestorBytmpcodeForIT方法开始");
    	BaseInvestorInfo baseinvestorinfo = null;
    	try {
    	    baseinvestorinfo = investordao.findBaseInvestorBytmpcodeForIT(
    		    "baseInvestorInfo.findBaseInvestorBytmpcodeForIT",tmpinvestorcode);
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  findBaseInvestorBytmpcodeForIT 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  findBaseInvestorBytmpcodeForIT方法结束");
    		}
    	return baseinvestorinfo;
    }

    // it桔子导入投资人
    @Override
    public void tranModifyimportinvestorinfo(BaseInvestorInfo baseinvestorinfo,
	    List<BaseInvestorlabelInfo> baseinvestorlabellist,
	    String opreString, String baseinvestorcode) throws Exception {
    	gs_logger.info("ImportITdataServiceImpl  tranModifyimportinvestorinfo方法开始");
    	try {
        	    if ("add".equals(opreString)) {
        		investordao.insertBaseInvestorforIT(
        			"baseInvestorInfo.insertBaseInvestorforIT",baseinvestorinfo);
        	    } else if ("upd".equals(opreString)) {
        		investordao.updateBaseInvestorforIT(
        			"baseInvestorInfo.updateBaseInvestorforIT",baseinvestorinfo);
        	    		}
        	    if (!"nodo".equals(opreString)) {
            		for (int i = 0; i < baseinvestorlabellist.size(); i++) {
            		    baseinvestorlabelinfodao.insertBaseInvestorlabelInfoforit(
            				    "baseInvestorlabelInfo.insertBaseInvestorlabelInfoforit",
            				    baseinvestorlabellist.get(i));
            			}
        	    		}
        	    if (!"nodo".equals(opreString)) {
        				/* 调用存储过程 */
            		sysstoredproceduredao.callViewinvestment(
            				"SysStoredProcedure.callViewinvestorInfo",
            				new SysStoredProcedure(baseinvestorcode,opreString,"all",
            					CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"),"", ""));
        	    		}
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  tranModifyimportinvestorinfo 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  tranModifyimportinvestorinfo方法结束");
    		}
    }

    // 投资人和机构的关系是否存在 (基础层)
    @Override
    public BaseInvestmentInvestor queryInvestmentInvestorbycodeforit(
	    Map<String, String> map) throws Exception {
    	gs_logger.info("ImportITdataServiceImpl  queryInvestmentInvestorbycodeforit方法开始");
    	BaseInvestmentInvestor baseinvestmentinvestor = null;
    	try {
    	    baseinvestmentinvestor = baseinvestmentinvestordao.queryInvestmentInvestorbycodeforit(
    			    "BaseInvestmentInvestor.queryInvestmentInvestorbycodeforit",map);
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  queryInvestmentInvestorbycodeforit 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  queryInvestmentInvestorbycodeforit方法结束");
    		}
    	return baseinvestmentinvestor;
    }

    // it桔子导入投资人关系
    @Override
    public void tranModifyimportInvestmentInvestor(
	    BaseInvestmentInvestor baseinvestmentinvestor, String opreString)
	    throws Exception {
    	gs_logger.info("ImportITdataServiceImpl  tranModifyimportInvestmentInvestor方法开始");
    	try {
    	    if ("add".equals(opreString)) {
        		baseinvestmentinvestordao.insertInvestmentInvestorforit(
        			"BaseInvestmentInvestor.insertInvestmentInvestorforit",baseinvestmentinvestor);
    	    } else if ("upd".equals(opreString)) {
        		baseinvestmentinvestordao.updateInvestmentInvestorforit(
        			"BaseInvestmentInvestor.updateInvestmentInvestorforit",baseinvestmentinvestor);
    	    		}
    
    	    if (!"nodo".equals(opreString)) {
        			/* 调用存储过程 */
        		sysstoredproceduredao.callViewinvestment(
        				"SysStoredProcedure.callViewinvestorInfo",
        				new SysStoredProcedure(
        					baseinvestmentinvestor.getBase_investor_code(),"upd","investment",
        					CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"),"", ""));
        	    	}
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  tranModifyimportInvestmentInvestor 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  tranModifyimportInvestmentInvestor方法结束");
    	}
    }

    // 根据名字查询公司数据库中是否存在易凯创建或修改的(基础层)
    @Override
    public BaseCompInfo findBaseCompBynameForIT(Map<String, String> map)
	    throws Exception {
    	gs_logger.info("ImportITdataServiceImpl  findBaseCompBynameForIT方法开始");
    	BaseCompInfo basecompinfo = null;
    	try {
    	    basecompinfo = companydao.findBaseCompBynameForIT(
    		    "BaseCompdInfo.findcompinfoBynameForIT", map);
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  findBaseCompBynameForIT 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  findBaseCompBynameForIT方法结束");
    		}
    	return basecompinfo;
    }

    // 根据对应IT桔子id查询公司数据库中是否存在记录(基础层)
    @Override
    public BaseCompInfo findBaseCompBytmpcodeForIT(String tmpcompanycode)
	    throws Exception {
    	gs_logger.info("ImportITdataServiceImpl  findBaseCompBytmpcodeForIT方法开始");
    	BaseCompInfo basecompinfo = null;
    	try {
    	    basecompinfo = companydao.findBaseCompBytmpcodeForIT(
    		    "BaseCompdInfo.findcompinfoBytmpcodeForIT", tmpcompanycode);
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  findBaseCompBytmpcodeForIT 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  findBaseCompBytmpcodeForIT方法结束");
    		}
    	return basecompinfo;
    }

    // it桔子导入公司
    @Override
    public void tranModifyimportcompinfo(BaseCompInfo basecompinfo,
	    List<BaseComplabelInfo> basecomplabellist, String opreString)
	    throws Exception {
    	gs_logger.info("ImportITdataServiceImpl  tranModifyimportcompinfo方法开始");
    	try {
    	    if ("add".equals(opreString)) {
    			companydao.insertBaseCompforIT(
    			"BaseCompdInfo.insertBaseCompforIT", basecompinfo);
    	    } else if ("upd".equals(opreString)) {
    			companydao.updateBaseCompforIT(
    			"BaseCompdInfo.updateBaseCompforIT", basecompinfo);
    	    		}
    	    if (!"nodo".equals(opreString)) {
        		for (int i = 0; i < basecomplabellist.size(); i++) {
        		    basecomplabelinfodao.insertBaseComplabelInfoforit(
        			    "basecomplabelinfo.insertBaseComplabelInfoforit",
        			    basecomplabellist.get(i));
        				}
			/* 调用存储过程,操作筐 */
			sysstoredproceduredao.callViewinvestment(
				"SysStoredProcedure.callBaskindureapro",
				new SysStoredProcedure(basecompinfo.getBase_comp_code(),"2","",
					CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"),"", ""));
    	    		}
    	    if (!"nodo".equals(opreString)) {
    			/* 调用存储过程 */
    			sysstoredproceduredao.callViewinvestment(
    				"SysStoredProcedure.callViewcompinfo",
    				new SysStoredProcedure(
    					basecompinfo.getBase_comp_code(),opreString,"all",
    					CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"),"", ""));
    	    		}
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  tranModifyimportcompinfo 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  tranModifyimportcompinfo方法结束");
    	}
    }

    // 根据公司联系人（创业者）对应IT桔子id查询公司联系人（创业者）数据库中是否存在记录(基础层)
    @Override
    public BaseEntrepreneurInfo findBaseEntrepreneurBytmpcodeForIT(
	    String tmpentrepreneurcode) throws Exception {
	gs_logger.info("ImportITdataServiceImpl  findBaseEntrepreneurBytmpcodeForIT方法开始");
	BaseEntrepreneurInfo baseentrepreneurinfo = null;
	try {
	    baseentrepreneurinfo = baseentrepreneurinfodao.findBaseEntrepreneurBytmpcodeForIT(
			    "baseentrepreneurinfo.findBaseEntrepreneurBytmpcodeForIT",tmpentrepreneurcode);
	} catch (Exception e) {
	    gs_logger.error("ImportITdataServiceImpl  findBaseEntrepreneurBytmpcodeForIT 方法异常"+e);
	    e.printStackTrace();
//    	throw e;
	} finally {
	    gs_logger.info("ImportITdataServiceImpl  findBaseEntrepreneurBytmpcodeForIT方法结束");
	}
	return baseentrepreneurinfo;
    }

    // it桔子导入公司联系人（创业者）更新数据库
    @Override
    public void tranModifyimportentrepreneurinfo(
	    BaseEntrepreneurInfo baseentrepreneurinfo, String opreString)
	    throws Exception {
    	gs_logger.info("ImportITdataServiceImpl  tranModifyimportentrepreneurinfo方法开始");
    	try {
    	    if ("add".equals(opreString)) {
    			baseentrepreneurinfodao.insertBaseEntrepreneurforIT(
    				"baseentrepreneurinfo.insertBaseEntrepreneurforIT",
    				baseentrepreneurinfo);
    	    } else if ("upd".equals(opreString)) {
    			baseentrepreneurinfodao.updateBaseEntrepreneurforIT(
    				"baseentrepreneurinfo.updateBaseEntrepreneurforIT",
    				baseentrepreneurinfo);
    	    		}
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  tranModifyimportentrepreneurinfo 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  tranModifyimportentrepreneurinfo方法结束");
    	}
    }

    // 公司联系人（创业者）和公司的关系是否存在 (基础层)
    @Override
    public BaseCompEntrepreneur queryCompEntrepreneurbycodeforit(
	    Map<String, String> map) throws Exception {
    	gs_logger.info("ImportITdataServiceImpl  queryCompEntrepreneurbycodeforit方法开始");
    	BaseCompEntrepreneur basecompentrepreneur = null;
    	try {
    	    basecompentrepreneur = basecompentrepreneurdao.queryCompEntrepreneurbycodeforit(
    			    "basecompentrepreneur.queryCompEntrepreneurbycodeforit",map);
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  queryCompEntrepreneurbycodeforit 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  queryCompEntrepreneurbycodeforit方法结束");
    		}
    	return basecompentrepreneur;
    }

    // it桔子导入公司联系人（创业者）和公司的关系
    @Override
    public void tranModifyimportCompEntrepreneur(
	    BaseCompEntrepreneur basecompentrepreneur, String opreString,
	    String basecompcode) throws Exception {
    	gs_logger.info("ImportITdataServiceImpl  tranModifyimportCompEntrepreneur方法开始");
    	try {
    	    if ("add".equals(opreString)) {
    			basecompentrepreneurdao.insertCompEntrepreneurforit(
    				"basecompentrepreneur.insertCompEntrepreneurforit",
    				basecompentrepreneur);
    	    } else if ("upd".equals(opreString)) {
    			basecompentrepreneurdao.updateCompEntrepreneurforit(
    				"basecompentrepreneur.updateCompEntrepreneurforit",
    				basecompentrepreneur);
    	    		}
    
    	    if (!"nodo".equals(opreString)) {
    				/* 调用存储过程 */
    			sysstoredproceduredao.callViewinvestment(
    				"SysStoredProcedure.callViewcompinfo",
    				new SysStoredProcedure(basecompcode,"upd","compperson",
    					CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"),"", ""));
    	    		}
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  tranModifyimportCompEntrepreneur 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  tranModifyimportCompEntrepreneur方法结束");
    	}
    }

    // 根据对应IT桔子id查询交易数据库中是否存在记录(基础层)
    @Override
    public BaseTradeInfo findTradeinfoBytmpcodeForIT(String tmpinvesrecordid)
	    throws Exception {
    	gs_logger.info("ImportITdataServiceImpl  findTradeinfoBytmpcodeForIT方法开始");
    	BaseTradeInfo basetradeinfo = null;
    	try {
    	basetradeinfo = basetradeinfodao.findTradeinfoBytmpcodeForIT(
    		    "BaseTradeInfo.findTradeinfoBytmpcodeForIT", tmpinvesrecordid);
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  findTradeinfoBytmpcodeForIT 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  findTradeinfoBytmpcodeForIT方法结束");
    		}
    	return basetradeinfo;
    }

    // it桔子导入交易
    @Override
    public void tranModifyimporttradeinfo(BaseTradeInfo basetradeinfo,
		List<BaseTradelabelInfo> basetradelabellist,String opreString)
	    throws Exception {
    	gs_logger.info("ImportITdataServiceImpl  tranModifyimporttradeinfo方法开始");
    	try {
    	    if ("add".equals(opreString)) {
    		basetradeinfodao.insertBaseTradeinfoforIT(
    				"BaseTradeInfo.insertBaseTradeinfoforIT", basetradeinfo);
    	    } else if ("upd".equals(opreString)) {
    		basetradeinfodao.updateBaseTradeinfoforIT(
    				"BaseTradeInfo.updateBaseTradeinfoforIT", basetradeinfo);
    	    		}
    	    if (!"nodo".equals(opreString)) {
        		for (int i = 0; i < basetradelabellist.size(); i++) {
        		    basetradelabelinfodao.insertBaseTradelabelInfoforit(
        			    "basetradelabelinfo.insertBaseTradelabelInfoforit",
        			    basetradelabellist.get(i));
        			}
			/* 调用存储过程,操作筐 */
			sysstoredproceduredao.callViewinvestment(
				"SysStoredProcedure.callBaskindureapro",
				new SysStoredProcedure(basetradeinfo.getBase_trade_code(),"3","",
					CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"),"", ""));
    	    		}
    	    if (!"nodo".equals(opreString)) {
    			/* 调用存储过程 */
    			sysstoredproceduredao.callViewinvestment(
    				"SysStoredProcedure.callViewtradeser",
    				new SysStoredProcedure(
    					basetradeinfo.getBase_trade_code(),"add","all",
    					CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"),"", ""));
    	    		}
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  tranModifyimporttradeinfo 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  tranModifyimporttradeinfo方法结束");
    	}
        }
    	//查询机构交易是否存在，根据机构code　和交易code
    @Override
    public BaseTradeInves findBaseTradeInveBytmpcodeForIT(
	    Map<String, String> map) throws Exception {
		gs_logger.info("ImportITdataServiceImpl  findBaseTradeInveBytmpcodeForIT方法开始");
		BaseTradeInves basetradeinves = null;
    	try {
    	basetradeinves = basetradeinvesdao.findBaseTradeInveBytmpcodeForIT(
    		    "basetradeinves.findBaseTradeInveBytmpcodeForIT", map);
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  findBaseTradeInveBytmpcodeForIT 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  findBaseTradeInveBytmpcodeForIT方法结束");
    		}
    	return basetradeinves;
    }
	//导入机构交易
    @Override
    public void tranModifyimporttradeinves(BaseTradeInves basetradeinves,
	    String opreString) throws Exception {
		gs_logger.info("ImportITdataServiceImpl  tranModifyimporttradeinves方法开始");
    	try {
    	    if ("add".equals(opreString)) {
    		basetradeinvesdao.insertBaseTradeInveforIT(
    				"basetradeinves.insertBaseTradeInveforIT", basetradeinves);
    	    } else if ("upd".equals(opreString)) {
    		basetradeinvesdao.updateBaseTradeInveforIT(
    				"basetradeinves.updateBaseTradeInveforIT", basetradeinves);
    	    		}
    	    if ("add".equals(opreString)|| "upd".equals(opreString)) {
    			/* 调用存储过程 */
    			sysstoredproceduredao.callViewinvestment(
    				"SysStoredProcedure.callViewtradeinfo",
    				new SysStoredProcedure(
    					basetradeinves.getBase_trade_code(),"add","investmentinfo",
    					CommonUtil.findNoteTxtOfXML("ITIMPORT_USER_NAME"),"", basetradeinves.getBase_investment_code()));
    	    		}
    	} catch (Exception e) {
    	    gs_logger.error("ImportITdataServiceImpl  tranModifyimporttradeinves 方法异常"+e);
    	    e.printStackTrace();
//    	    throw e;
    	} finally {
    	    gs_logger.info("ImportITdataServiceImpl  tranModifyimporttradeinves方法结束");
    	}
	
    }

    
    @Override
    public int findcomptradeByCodeforit(BaseTradeInfo basetradeinfo)
	    throws Exception {
		gs_logger.info("ImportITdataServiceImpl  findcomptradeByCodeforit方法开始");
    	int i=0;
    	try {
    	    	//2015-11-30 TASK075 RQQ ModStart
    	    	i=basetradeinfodao.findcomptradeByCodeforit("BaseTradeInfo.findtmptradeByCodeforit", basetradeinfo);
        	   
        	    if(i==0){
        		i=basetradeinfodao.findcomptradeByCodeforit("BaseTradeInfo.findcomptradeByCodeforit", basetradeinfo);
        	    		}
        	    //2015-11-30 TASK075 RQQ ModEnd
        	} catch (Exception e) {
        	    gs_logger.error("ImportITdataServiceImpl  tranModifyimporttradeinves 方法异常"+e);
        	    e.printStackTrace();
    //    	    throw e;
        	} finally {
        	gs_logger.info("ImportITdataServiceImpl  findcomptradeByCodeforit方法结束");
        	}

    	return i;
    }
	//2015-11-30 TASK069 RQQ AddStart
    @Override
    public String queryinvesstageforIT(String labelelementname)
	    throws Exception {
		gs_logger.info("ImportITdataServiceImpl  queryinvesstageforIT方法开始");
		String sysimporaftercont=null;
    	try {
    	    sysimporaftercont=sysimportinvesestagereladao.queryinvesstageforIT("sysimportinvesestagerela.queryinvesstageforIT", labelelementname);
        	} catch (Exception e) {
        	    gs_logger.error("ImportITdataServiceImpl  queryinvesstageforIT 方法异常"+e);
        	    e.printStackTrace();;
        	} finally {
        	gs_logger.info("ImportITdataServiceImpl  queryinvesstageforIT方法结束");
        	}

    	return sysimporaftercont;
    }
	//2015-11-30 TASK069 RQQ AddEnd
    
  
}
