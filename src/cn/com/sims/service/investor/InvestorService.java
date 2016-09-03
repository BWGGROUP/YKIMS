package cn.com.sims.service.investor;

import java.util.List;
import java.util.Map;

import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.model.baseinvestmentinvestor.BaseInvestmentInvestor;
import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.baseinvestorlabelinfo.BaseInvestorlabelInfo;
import cn.com.sims.model.baseinvestornoteinfo.BaseInvestornoteInfo;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.model.viewInvestorInfo.viewInvestorInfo;

public interface InvestorService {
	
	
	public List<Map<String, String>> findAllInvestorInfo() throws Exception;
	
	public List<Map<String, String>> findInvestorInfoByOrgCode(String orgCode) throws Exception;
	
	
	public List findInvestorName(String name) throws Exception;
	
	/**
	 * 根据投资机构id查询投资机构投资人信息
	 * @param organizationId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> findInvestorByOrgId(String organizationId) throws Exception;
	/**
	 * 根据投资人code 查询投资人详细信息
	 * @param code 投资人id
	 * @return
	 * @throws Exception
	 */
	public viewInvestorInfo findInvestorDeatilByCode(String code) throws Exception;
	/**
	 * 根据投资人code 查询投资人详细信息(基础表)
	 * @param code 投资人id
	 * @return
	 * @throws Exception
	 */
	public BaseInvestorInfo findInvestorByCode(String code) throws Exception;
	
	public int tranModifyInvestor(BaseInvestorInfo info) throws Exception;
	
	public void tranModifyUpdateBaseInvestor(SysUserInfo userInfo,
			String newData, String oldData, String investrCode, String type,String investorName,String logintype)
			throws Exception;
	/**
	 * 根据投资机构code分页查询投资人信息
	 * @param orgCode
	 * @param page
	 * @author duwenjie
	 * @date 2015-10-20
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> findPageInvestorByOrgId(String orgCode,Page page) throws Exception;
	
	/**
	 * 查询投资机构投资人数量
	 * @param orgCode
	 * @author duwenjie
	 * @date 2015-10-20
	 * @return
	 * @throws Exception
	 */
	public int findCountInvestor(String orgCode) throws Exception;
	/**
	 * 修改投资人投资机构
	 * @param map
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月22日
	 */
	public int tranModifyUpdInvestor(Map map) throws Exception;
	/**
	 * 添加投资人所属投资机构
	 * @param info
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月22日
	 */
	public int insertInvestor(BaseInvestmentInvestor info) throws Exception;
	/**
	 * 查询投资人所属机构是否已经添加过
	 * @param map
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月22日
	 */
	public int findInvestmentInstor(Map map) throws Exception;
	
	public List<Map<String, String>> findTradeLabelByCode(String orgCode) throws Exception;
 /**
  * 根据机构code查询投资机构
  * @param ccode
  * @return
  * @throws Exception
 *@author yl
 *@date 2015年11月5日
  */
	public String findInvestment(String ccode) throws Exception;
	/**
	 * 添加投资人
	 * @param info
	 * @param labelList
	 * @param investmentinvestorInfoList
	 * @param noteInfo
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年11月6日
	 */
	public int tranModifyInvestorInfo(BaseInvestorInfo info,List<BaseInvestorlabelInfo> labelList,List<BaseInvestmentInvestor> investmentinvestorInfoList,BaseInvestornoteInfo noteInfo) throws Exception;
	//2015-12-02 TASK76 yl add start
	/**
	 * 删除投资机构的投资人
	 * @param orgCode 机构code
	 * @param invstorCode　投资人code
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年12月2日
	 */
	public int tranDeleteInvestor(String orgCode,String invstorCode,String userCode) throws Exception;
  /**
   * 查询投资人参与交易的条数
   * @param orgCode 机构code
	 * @param invstorCode　投资人code
   * @return
   * @throws Exception
  *@author yl
  *@date 2015年12月2日
   */
	public int selectTrade(String orgCode,String invstorCode) throws Exception;
}