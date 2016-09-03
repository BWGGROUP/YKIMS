package cn.com.sims.dao.investor;

import java.util.List;
import java.util.Map;

import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.viewInvestorInfo.viewInvestorInfo;

public interface InvestorDao {
	public List findInvestor(String str, String name) throws Exception;
	
	
	
	public List<Map<String, String>> findAllInvestorInfo(String str) throws Exception;
	
	public List<Map<String, String>> findInvestorInfoByOrgCode(String str,String orgCode) throws Exception;
	
	
	
	
	/**
	 * 根据机构code查询投资机构投资人信息
	 * @param str
	 * @param 投资机构code
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> findInvestorByOrgId(String str, String id) throws Exception;
	
	/**
	 * 根据投资机构code查询投资人数量
	 * @author duwenjie
	 * @date 2015-10-20
	 * @param str
	 * @param orgCode
	 * @return
	 * @throws Exception
	 */
	public int findCountInvestor(String str,String orgCode)throws Exception;
	
	
	/**
	 * 根据机构code分页查询投资人信息
	 * @param str
	 * @param map
	 * @author duwenjie
	 * @date 2015-10-20
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> findPageInvestorByOrgId(String str, Map<String, String> map) throws Exception;
	
	
	/**
	  * 根据投资人code 查询投资人详细信息
  * @author yl
  * @date 2015-10-15 
  * @param str
  * @param code 投资人id
  * @return
  * @throws Exception
	 */
	public viewInvestorInfo findInvestorDeatilByCode(String str, String code) throws Exception;
	/**
	  * 根据投资人code 查询投资人详细信息(基础表)
 * @author yl
 * @date 2015-10-19 
 * @param str
 * @param code 投资人id
 * @return
 * @throws Exception
	 */
	public BaseInvestorInfo findInvestorByCode(String str, String code) throws Exception;
	public int tranModifyInvestor(String str, BaseInvestorInfo info) throws Exception;
	
	/**
	 * 根据对应IT桔子id查询投资人数据库中是否存在记录(基础层)
	 * @author rqq
	 * @date 2015-10-22 
	 * @param str
	 * @param tmpinvestorcode投资人对应IT桔子id
	 * @return
	 * @throws Exception
	 */
	public BaseInvestorInfo findBaseInvestorBytmpcodeForIT(String str,String tmpinvestorcode)throws Exception;
	
	/**
	 * 更新it桔子导入数据投资人数据
	 * @author rqq
	 * @date 2015-10-22 
	 * @param str
	 * @param baseinvestorinfo投资人数据
	 * @return
	 * @throws Exception
	 */
	public void updateBaseInvestorforIT(String str,
		BaseInvestorInfo baseinvestorinfo) throws Exception;
	
	/**
	 * 插入it桔子导入数据投资人数据
	 * @author rqq
	 * @date 2015-10-22 
	 * @param str
	 * @param baseinvestorinfo投资人数据
	 * @return
	 * @throws Exception
	 */
	public void insertBaseInvestorforIT(String str,
		BaseInvestorInfo baseinvestorinfo) throws Exception;	
	/**
	 * 根据投资机构code查询投资机构
	 * @param str
	 * @param code
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年11月5日
	 */
	public String findInvestment(String str,String code) throws Exception;
	/**
	 * 添加联系人
	 * @param str
	 * @param info
	 * @throws Exception
	*@author yl
	*@date 2015年11月6日
	 */
	public void insetInvstor(String str,BaseInvestorInfo info) throws Exception;
	
	/**
	 * 根据机构code查询模糊查询所属该机构投资人信息数目
	 * @author rqq
	 * @date 2015-11-03 
	 * @param str
	 * @param map: name:投资人姓名,invementcode:机构code
	 * @return
	 * @throws Exception
	 */
	public int queryInvestorlistnumByOrgId(String str,Map<String, Object> map)throws Exception;
	
	
	/**
	 * 根据机构code查询模糊查询所属该机构投资人信息
	 * @author rqq
	 * @date 2015-11-03 
	 * @param str
	 * @param map: name:投资人姓名,invementcode:机构code,pagestart:分页起始,limit:数目，
	 * @return
	 * @throws Exception
	 */
	public List<viewInvestorInfo> queryInvestorlistByOrgId(String str,Map<String, Object> map)throws Exception;
	//2015-11-27 TASK070 yl add start
	/**
	 * 根据投资人code查询其所属的投资机构及其职位
	 * @author yl
	 * @date 2015-11-03 
	 * @param str
	 * @param code:投资人code
	 * @return
	 * @throws Exception
	 */
	public String findInvestmentInvest(String str,String code)throws Exception;
	//2015-11-27 TASK070 yl add end
	//2015-12-02 TASK076 yl add start
	/*
	 * 删除投资机构的投资人
	 */
	public int tranDeleteInvestor(String str,Map map)throws Exception;
	/**
	 * 修改投资人的当前状态
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年12月2日
	 */
	public int updateInvestor(String str,Map map)throws Exception;
	/**
	 * 查询投资人参与交易的条数
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年12月2日
	 */
	public int selectTrade(String str,Map map)throws Exception;
	//2015-12-02 TASK076 yl add end
	
	/**
	 * 根据投资人名称查询投资人信息
	 * @param map orgcode:机构code，name:投资人名称
	 * @author dwj
	 * @date 2016-5-13
	 */
	public BaseInvestorInfo findOrgInvestorByName(String str,Map<String, String> map)throws Exception;
	
}