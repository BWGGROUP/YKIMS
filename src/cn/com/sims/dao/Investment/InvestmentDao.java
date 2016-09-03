package cn.com.sims.dao.Investment;

import java.util.List;
import java.util.Map;

import cn.com.sims.model.baseinvesfundinfo.BaseInvesfundInfo;
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.viewInvestmentInfo.investmentInfo;
import cn.com.sims.model.viewInvestmentInfo.viewInvestmentInfo;
import cn.com.sims.model.viewInvestorInfo.viewInvestorInfo;
/**
 * 
 * @author yanglian
 * @version 2015-09-24
 */
public interface InvestmentDao {
	/**
	 * 通过拼音，名称，首字母模糊查询投资机构
	 * @param str
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<baseInvestmentInfo> findInvestment(String str,String name) throws Exception;
	/**
	 * 通过拼音，名称，首字母模糊查询投资机构
	 * @param str
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List findInvestmentByName(String str,Map<String, Object> paraMap) throws Exception;
	/**
	 * 通过拼音，名称，首字母模糊查询投资机构
	 * @param str
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<viewInvestmentInfo> gotoSearchByName(String str,Map<String, Object> paraMap) throws Exception;
	/**
	 * 通过拼音，名称，首字母模糊查询符合条件投资机构总条数
	 * @param str
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public int findCountSizeByName(String str,Map<String, Object> paraMap) throws Exception;
	/**
	 * 通过类型查询标签初始值
	 * @param str
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List findLable(String str,String type) throws Exception;
	/**
	 * 根据名称，首字母，全拼查询公司
	 * @param str
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List findCompany(String str,String name) throws Exception;
	/**
	 * 多条件查询投资机构信息
	 * @param str
	 * @param paraMap
	 * @return
	 * @throws Exception
	 */
	public List findInvestmentByMoreCon(String str,Map<String, Object> paraMap) throws Exception;
	/**
	 * 通过多条件查询符合条件投资机构总条数
	 * @param str
	 * @param paraMap
	 * @return
	 * @throws Exception
	 */
	public int findInvestmentByMoreCount(String str,Map<String, Object> paraMap) throws Exception;
	public String findCompanyRe(String str,Map<String, Object> paraMap) throws Exception;
	/**
	 * 多条件查询投资机构信息
	 * @param str
	 * @param paraMap
	 * @return
	 * @throws Exception
	 */
	public List<investmentInfo> findInvestmentByMoreConExport(String str,Map<String, Object> paraMap) throws Exception;
	
	/**
	 * 查询规模
	 * @param str
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List findLableScale(String str) throws Exception;
	
	/**
	 * 查询投资机构信息
	 * @param str
	 * @param paraMap
	 * @return
	 * @throws Exception
	 */
	public List<investmentInfo> findInvestmentByNameExport(String str,Map<String, Object> paraMap) throws Exception;

	/**
	 * 根据机构简称查询模糊查询该机构信息数目
	 * @author rqq
	 * @date 2015-11-03 
	 * @param str
	 * @param map: name:机构简称
	 * @return
	 * @throws Exception
	 */
	public int queryInvestmentlistnumByOrgname(String str,Map<String, Object> map)throws Exception;
	
	
	/**
	 * 根据机构简称查询模糊查询该机构信息
	 * @author rqq
	 * @date 2015-11-03 
	 * @param str
	 * @param map: name:机构简称,pagestart:分页起始,limit:数目
	 * @return
	 * @throws Exception
	 */
	public List<viewInvestmentInfo> queryInvestmentlistByOrgname(String str,Map<String, Object> map)throws Exception;
	/**
	 * 查询基金
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年11月19日
	 */
	public List<BaseInvesfundInfo> findInvesfund(String str,Map<String, Object> map)throws Exception;
	
}
