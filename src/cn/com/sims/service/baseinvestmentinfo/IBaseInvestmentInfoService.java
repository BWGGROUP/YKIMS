package cn.com.sims.service.baseinvestmentinfo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import cn.com.sims.model.baseinvesfundinfo.BaseInvesfundInfo;
import cn.com.sims.model.baseinveslabelinfo.BaseInveslabelInfo;
import cn.com.sims.model.baseinvesnoteinfo.BaseInvesnoteInfo;
import cn.com.sims.model.baseinvesreponinfo.BaseInvesreponInfo;
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.sysuserinfo.SysUserInfo;

/**
 * 
 * @author duwenjie
 * @date 2015-10-14
 */


public interface IBaseInvestmentInfoService {

	/**
	 * 更新投资机构信息(基础层)
	 * @param base_investment_code
	 * @param base_investment_name
	 * @param base_investment_ename
	 * @param base_investment_fullname
	 * @param base_investment_img
	 * @param base_investment_stem
	 * @param base_investment_estate
	 * @param base_investment_namep
	 * @param base_investment_namepf
	 * @param Tmp_Investment_Code
	 * @param deleteflag
	 * @param createid
	 * @param createtime
	 * @param updid
	 * @param updtime
	 * @param base_datalock_viewtype
	 * @param base_datalock_pltype
	 * @param sys_user_code
	 * @param base_datalock_sessionid
	 * @param base_datalock_viewtime
	 * @return
	 * @throws Exception
	 */
	public int tranModifyBaseInvestment(
			String  userCode,
			String	base_investment_code	,
			String	base_investment_name	,
			String	base_investment_ename	,
			String	base_investment_fullname	,
			String	base_investment_img	,
			String	base_investment_stem	,
			String	base_investment_estate	,
			String	base_investment_namep	,
			String	base_investment_namepf	,
			String	Tmp_Investment_Code	,
			String	deleteflag	,
			String	createid	,
			Timestamp	createtime	,
			String	updid	,
			Timestamp	updtime	,
			long	base_datalock_viewtype	,
			String	base_datalock_pltype	,
			String	sys_user_code	,
			String	base_datalock_sessionid	,
			String	base_datalock_viewtime)throws Exception; 

	/**
	 * 查询投资机构信息(基础层)
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	public baseInvestmentInfo findBaseInvestmentByCode(String orgCode) throws Exception;
	
	
	/**
	 * 判断投资机构名称是否唯一
	 * @param name简称
	 * @param fullName全称
	 * @param eName英文名称
	 * @return only为唯一,其他则返回不唯一项信息
	 * @throws Exception
	 */
	public String findOnlyInvementName(String orgCode,String name,String fullName,String eName)throws Exception;
	
	
	public List<Map<String, String>> findAllBaseInvestment()throws Exception;
	
	public void tranModifyInsertInvestmentInfo(SysUserInfo userInfo, baseInvestmentInfo investmentInfo,List<BaseInveslabelInfo> labelList,List<BaseInvesfundInfo>  fundlList,List<BaseInvesreponInfo> ykList,BaseInvesnoteInfo note)throws Exception;
	
	/**
	 * 根据投资人名称查询投资人信息
	 * @param map
	 * @return
	 * @throws Exception
	 * @author dwj
	 * @date 2016-5-13
	 */
	public BaseInvestorInfo findOrgInvestorByName(Map<String, String> map)throws Exception;
}
