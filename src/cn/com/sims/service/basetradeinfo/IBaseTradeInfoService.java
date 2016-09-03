package cn.com.sims.service.basetradeinfo;

import java.util.HashMap;

import org.json.JSONObject;

import cn.com.sims.model.baseinvestmentinvestor.BaseInvestmentInvestor;
import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.basetradeinfo.BaseTradeInfo;
import cn.com.sims.model.basetradeinves.BaseTradeInves;
import cn.com.sims.model.sysuserinfo.SysUserInfo;

public interface IBaseTradeInfoService {
	
	
	public int tranDeleteTradeInfoByTradeCode(String userId,String tradeCode,String version,String orgcodeString)throws Exception;
	/**
	 * 根据交易code修改交易信息
	 * @param info
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 */
	public int updateTradeInfo(BaseTradeInfo info) throws Exception;
	
	/**
	 * 根据交易code查询机构交易交易信息
	 * @param tradeCode交易code
	 * @return
	 * @throws Exception
	 * @author zzg
	 * @date 2015-12-02
	 */
	public BaseTradeInves findBaseTradeInvesByCode(HashMap<String, String> map) throws Exception;
	/**
	 * 根据交易code查询交易信息
	 * @param tradeCode交易code
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 */
	public BaseTradeInfo findBaseTradeInfoByCode(String tradeCode) throws Exception;
	
	/**
	 * 修改交易标签信息
	 * @param userInfo登录用户对象
	 * @param newData新数据
	 * @param oldData原数据
	 * @param tradeCode交易code
	 * @param type标签类型(筐Lable-bask,行业Lable-indu)
	 * @param logintype登录标识(电脑:PC,微信:WX,手机:MB)
	 * @throws Exception
	 */
	public void tranModifyBaseTradeLabelInfo(SysUserInfo userInfo,String newData,String oldData,String tradeCode,String type,String logintype)throws Exception;

	
	
	/**
	 * 添加机构交易信息
	 * @param info机构交易信息
	 * @return
	 * @throws Exception
	 */
	public int tranModifyInsertBaseTradeInvesInfo(SysUserInfo userInfo,BaseTradeInves info,long version)throws Exception;
	
	/**
	 * 删除机构交易信息
	 * @param tradeCode交易code
	 * @param orgCode机构code
	 * @return
	 * @throws Exception
	 */
	public int tranModifyDeleteTradeInvesInfo(SysUserInfo userInfo,String tradeCode,String orgCode,long version)throws Exception;
	/**
	 * 修改交易详情　融资信息
	 * @author zzg
	 * @date 2015-12-02
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public boolean tranModifyUpdate_trade(BaseTradeInves bastradinfo,BaseInvestorInfo baseinvestor,BaseInvestmentInvestor investorinfo,SysUserInfo userInfo,String version) throws Exception;

}
