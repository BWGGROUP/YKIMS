package cn.com.sims.dao.baseinvestmentinfo;

import java.util.List;
import java.util.Map;

import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;

/**
 * 
 * @author duwenjie
 * @date 2015-10-14
 */
public interface IBaseInvestmentInfoDao {
	
	
	public void insertBaseInvestmentInfo(String str,baseInvestmentInfo info)throws Exception;
	
	public List<Map<String, String>> findAllBaseInvestment(String str)throws Exception;
	
	/**
	 * 更新投资机构信息(基础层)
	 * @param info投资机构信息
	 * @return
	 * @throws Exception
	 */
	public int tranModifyBaseInvestment(String str,baseInvestmentInfo info) throws Exception;
	
	/**
	 * 查询投资机构信息根据code(基础层)
	 * @param str
	 * @param orgCode机构code
	 * @return
	 * @throws Exception
	 */
	public baseInvestmentInfo findBaseInvestmentByCode(String str,String orgCode)throws Exception;
	
	
	/**
	 * 判断投资机构简称称是否唯一
	 * @param str
	 * @param info
	 * @return 0为唯一名称,否则为已存在
	 * @throws Exception
	 */
	public int findInvestmentOnlyName(String str,baseInvestmentInfo info)throws Exception;
	
	/**
	 * 判断投资机构全称是否唯一
	 * @param str
	 * @param info
	 * @return 0为唯一名称,否则为已存在
	 * @throws Exception
	 */
	public int findInvestmentOnlyFullName(String str,baseInvestmentInfo info)throws Exception;
	
	/**
	 * 判断投资机构英文名称是否唯一
	 * @param str
	 * @param info
	 * @return 0为唯一名称,否则为已存在
	 * @throws Exception
	 */
	public int findInvestmentOnlyEName(String str,baseInvestmentInfo info)throws Exception;
	
	/**
	 * 根据名字查询机构数据库中是否存在易凯创建或修改的(基础层)
	 * @author rqq
	 * @date 2015-10-22 
	 * @param str
	 * @param baseinvestmentname机构简称
	 * @return
	 * @throws Exception
	 */
	public baseInvestmentInfo findBaseInvestmentBynameForIT(String str,String baseinvestmentname)throws Exception;
	
	/**
	 * 根据对应IT桔子id查询机构数据库中是否存在记录(基础层)
	 * @author rqq
	 * @date 2015-10-22 
	 * @param str
	 * @param tmpinvestmentcode机构对应IT桔子id
	 * @return
	 * @throws Exception
	 */
	public baseInvestmentInfo findBaseInvestmentBytmpcodeForIT(String str,String tmpinvestmentcode)throws Exception;
	
	/**
	 * 更新it桔子导入数据机构数据
	 * @author rqq
	 * @date 2015-10-22 
	 * @param str
	 * @param baseinvestmentinfo机构数据
	 * @return
	 * @throws Exception
	 */
	public void updateBaseInvestmentforIT(String str,
		baseInvestmentInfo baseinvestmentinfo) throws Exception;
	
	/**
	 * 插入it桔子导入数据机构数据
	 * @author rqq
	 * @date 2015-10-22 
	 * @param str
	 * @param baseinvestmentinfo机构数据
	 * @return
	 * @throws Exception
	 */
	public void insertBaseInvestmentforIT(String str,
		baseInvestmentInfo baseinvestmentinfo) throws Exception;
	
	/**
	 * 添加交易判断投资机构简称是否唯一
	 * @param str
	 * @param name:机构名称
	 * @return 0为唯一名称,否则为已存在
	 * @throws Exception
	 */
	public int queryInvestmentOnlyNamefottrade(String str,String name)throws Exception;
	
	/**
	 * 修改投资机构删除标识
	 * @param str
	 * @param map（orgcode:机构code,del:删除标识，lock:排他锁版本号）
	 * @return
	 * @throws Exception
	 */
	public int updateBaseOrgDelete(String str,Map<String, Object> map)throws Exception;
}
