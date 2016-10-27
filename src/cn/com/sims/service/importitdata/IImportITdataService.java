package cn.com.sims.service.importitdata;

import java.util.List;
import java.util.Map;

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

/**
 * @ClassName: IImportITdataService
 * @author rqq
 * @date 2014-8-8
 */

public interface IImportITdataService {
    /**
	 * 根据名字查询标签
	 * @author rqq
	 * @date 2015-10-22 
	 * @param  map:labelelementname:标签元素名称,labelcode:标签code
	 * @throws Exception
	*/
	public SysLabelelementInfo querylabelelementbyname(Map<String, String> map) throws Exception;
	
	/**
	 * 查询标签最大code
	 * @author rqq
	 * @date 2015-10-22 
	　* @param  labelcode:标签code
	　* @throws Exception
	*/
	public String querymaxlabelelementbycode(String labelcode) throws Exception;
	
	/**
	 * 插入标签元素
	 * @author rqq
	 * @date 2015-10-22 
	 * @param syslabelelementinfo:标签元素对象
	 * @throws Exception
	*/
	public void insertsyslabelementinfo(SysLabelelementInfo syslabelelementinfo) throws Exception;
	
	/**
	 *  根据名字查询机构数据库中是否存在易凯创建或修改的
	 * @author rqq
	 * @date 2015-10-22 
	 * @param baseinvestmentname机构简称
	 * @throws Exception
	*/
	public baseInvestmentInfo findBaseInvestmentBynameForIT(String baseinvestmentname) throws Exception;
	
	/**
	 *  根据对应IT桔子id查询机构数据库中是否存在记录(基础层)
	 * @author rqq
	 * @date 2015-10-22 
	 * @param tmpinvestmentcode机构对应IT桔子id
	 * @throws Exception
	*/
	public baseInvestmentInfo findBaseInvestmentBytmpcodeForIT(String tmpinvestmentcode) throws Exception;
	
	/**
	 * it桔子导入投资机构更新数据库
	 * @author rqq
	 * @date 2015-10-22 
	 * @param baseinvestmentinfo：机构对象；baseinveslabellist：标签对象；opreString：执行操作：add/upd/do
	 * @throws Exception
	*/
	public void tranModifyimportinvestmentinfo(baseInvestmentInfo baseinvestmentinfo,
		List<BaseInveslabelInfo> baseinveslabellist,String opreString,String baseinvestmentcode) throws Exception;
	
	/**
	 *  根据对应IT桔子id查询投资人数据库中是否存在记录(基础层)
	 * @author rqq
	 * @date 2015-10-22 
	 * @param tmpinvestorcode投资人对应IT桔子id
	 * @throws Exception
	*/
	public BaseInvestorInfo findBaseInvestorBytmpcodeForIT(String tmpinvestorcode) throws Exception;
	
	/**
	 * it桔子导入投资人更新数据库
	 * @author rqq
	 * @date 2015-10-22 
	 * @param baseinvestorinfo：投资人对象；baseinvestorlabellist：投资人行业标签对象；opreString：执行操作：add/upd
	 * @throws Exception
	*/
	public void tranModifyimportinvestorinfo(BaseInvestorInfo baseinvestorinfo,
		List<BaseInvestorlabelInfo> baseinvestorlabellist,String opreString,String baseinvestorcode) throws Exception;
	
	
	/**
	 *  投资人和机构的关系是否存在	(基础层)
	 * @author rqq
	 * @date 2015-10-23
	 * @param map：base_investment_code：机构code;base_investor_code:投资人code
	 * @throws Exception
	*/
	public BaseInvestmentInvestor queryInvestmentInvestorbycodeforit(Map<String,String> map) throws Exception;
	
	/**
	 * it桔子导入投资人关系更新数据库
	 * @author rqq
	 * @date 2015-10-23 
	 * @param baseinvestmentinvestor：投资人机构关系对象；opreString：执行操作：add/upd
	 * @throws Exception
	*/
	public void tranModifyimportInvestmentInvestor(BaseInvestmentInvestor baseinvestmentinvestor,
		String opreString) throws Exception;
	
	/**
	 *  根据名字查询公司数据库中是否存在易凯创建或修改的
	 * @author rqq
	 * @date 2015-10-23 
	 * @param map:basecompname公司简称;basecompfullname:公司全称
	 * @throws Exception
	*/
	public BaseCompInfo findBaseCompBynameForIT(Map<String, String> map) throws Exception;
	
	/**
	 *  根据对应IT桔子id查询公司数据库中是否存在记录(基础层)
	 * @author rqq
	 * @date 2015-10-23 
	 * @param tmpinvestmentcode机构对应IT桔子id
	 * @throws Exception
	*/
	public BaseCompInfo findBaseCompBytmpcodeForIT(String tmpcompanycode) throws Exception;
	
	/**
	 * it桔子导入公司更新数据库
	 * @author rqq
	 * @date 2015-10-23 
	 * @param basecompinfo：公司对象；basecomplabellist：行业标签对象；opreString：执行操作：add/upd/do
	 * @throws Exception
	*/
	public void tranModifyimportcompinfo(BaseCompInfo basecompinfo,
		List<BaseComplabelInfo> basecomplabellist,String opreString) throws Exception;
	
	
	/**
	 *  根据公司联系人（创业者）对应IT桔子id查询公司联系人（创业者）数据库中是否存在记录(基础层)
	 * @author rqq
	 * @date 2015-10-26 
	 * @param tmpentrepreneurcode公司联系人（创业者）对应IT桔子id
	 * @throws Exception
	*/
	public BaseEntrepreneurInfo findBaseEntrepreneurBytmpcodeForIT(String tmpentrepreneurcode) throws Exception;
	
	/**
	 * it桔子导入公司联系人（创业者）更新数据库
	 * @author rqq
	 * @date 2015-10-26 
	 * @param baseentrepreneurinfo：公司联系人创业者对象；opreString：执行操作：add/upd
	 * @throws Exception
	*/
	public void tranModifyimportentrepreneurinfo(BaseEntrepreneurInfo baseentrepreneurinfo,String opreString) throws Exception;
	
	
	/**
	 *  公司联系人（创业者）和公司的关系是否存在	(基础层)
	 * @author rqq
	 * @date 2015-10-26
	 * @param map：base_comp_code：公司code;base_entrepreneur_code:创业者code
	 * @throws Exception
	*/
	public BaseCompEntrepreneur queryCompEntrepreneurbycodeforit(Map<String,String> map) throws Exception;
	
	/**
	 * 公司联系人（创业者）和公司的关系更新数据库
	 * @author rqq
	 * @date 2015-10-26 
	 * @param baseinvestmentinvestor：公司联系人（创业者）和机构的关系对象；opreString：执行操作：add/upd,basecompcode：公司code
	 * @throws Exception
	*/
	public void tranModifyimportCompEntrepreneur(BaseCompEntrepreneur basecompentrepreneur,
		String opreString,String basecompcode) throws Exception;
	
	/**
	 *  根据对应IT桔子id查询交易数据库中是否存在记录(基础层)
	 * @author rqq
	 * @date 2015-10-2７ 
	 * @param tmpinvesrecordid交易对应IT桔子id
	 * @throws Exception
	*/
	public BaseTradeInfo findTradeinfoBytmpcodeForIT(String tmpinvesrecordid) throws Exception;
	
	/**
	 * it桔子导入交易更新数据库
	 * @author rqq
	 * @date 2015-10-27 
	 * @param basetradeinfo：交易对象；basetradelabellist：交易行业标签对象；opreString：执行操作：add/upd
	 * @throws Exception
	*/
	public void tranModifyimporttradeinfo(BaseTradeInfo basetradeinfo,
		List<BaseTradelabelInfo> basetradelabellist,String opreString) throws Exception;
	
	/**
	 *  根据对应IT桔子id查询机构交易数据库中是否存在记录(基础层)
	 * @author rqq
	 * @date 2015-10-2７ 
	 * @param map:base_trade_code:交易id;base_investment_code:机构id
	 * @throws Exception
	*/
	public BaseTradeInves findBaseTradeInveBytmpcodeForIT(Map<String,String> map) throws Exception;
	
	/**
	 * it桔子导入机构交易更新数据库
	 * @author rqq
	 * @date 2015-10-27 
	 * @param basetradeinves：机构交易对象；opreString：执行操作：add/upd
	 * @throws Exception
	*/
	public void tranModifyimporttradeinves(BaseTradeInves basetradeinves,String opreString) throws Exception;
	
	/**
	 * 根据公司code,轮次查询交易信息
	 * @author rqq
	 * @date 2015-11-16 
	 * @param basetradeinfo：交易对象
	 * @throws Exception
	*/
	public int findcomptradeByCodeforit(BaseTradeInfo basetradeinfo) throws Exception;
	
	//2015-11-30 TASK069 RQQ AddStart
	/**
	 * 导入投资机构阶段时匹配规则，IT桔子
	 * @author rqq
	 * @date 2015-11-30 
	 * @param labelelementname：标签名称
	 * @throws Exception
	*/
	public String queryinvesstageforIT(String labelelementname) throws Exception;
	//2015-11-30 TASK069 RQQ AddEnd
	
	
}
