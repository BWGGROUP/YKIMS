package cn.com.sims.service.investment;

import java.util.List;
import java.util.Map;
import cn.com.sims.model.viewInvestmentInfo.investmentInfo;



/**
 * @author yanglian
 * @version 2015-09-24
 */
public interface IInvestmentService {
	/**
	 * 通过名称，拼音，首字母模糊查询投资机构信息
	 * @param name
	 * @return
	 * @throws Exception
	 */
  public List findInvestment(String name) throws Exception;
  /**
	 * 通过名称，拼音，首字母模糊查询投资机构信息
	 * @param name
	 * @return
	 * @throws Exception
	 */
  public List findInvestmentByName(Map<String, Object> paraMap) throws Exception;
  
  /**
	 * 通过名称，拼音，首字母模糊查询投资机构信息
	 * @param name
	 * @return 机构名称集合
	 * @throws Exception
	 * @author duwenjie
	 * @date 2015-12-15
	 */
  public List findInvestmentNameListByName(Map<String, Object> paraMap) throws Exception;

  
  /**
	 * 通过名称，拼音，首字母模糊查询投资机构信息
	 * @param pageSize
	 * @param name
	 * @return
	 * @throws Exception
	 */
  public List gotoSearchByName(Map<String, Object> paraMap) throws Exception;
  /**
	 * 通过名称，拼音，首字母模糊查询投资机构符合条件的总条数
	 * @param pageSize
	 * @param name
	 * @return
	 * @throws Exception
	 */
  public int findCountSizeByName(Map<String, Object> paraMap) throws Exception;
  /**
	 * 查询筐
	 * @param pageSize
	 * @param name
	 * @return
	 * @throws Exception
	 */
 public List findLable(String type) throws Exception;
 /**
  * 根据名称，拼音，首字母模糊查询符合条件的公司
  * @param name
  * @return
  * @throws Exception
  */
 public List findCompany(String name) throws Exception;
 /**
  * 多条件查询投资机构
  * @param paraMap
  * @return
  * @throws Exception
  */
 public List findInvestmentByMoreCon(Map<String, Object> paraMap,List list) throws Exception;
 /**
  * 查询多条件的符合信息的总条数
  * @param paraMap
  * @return
  * @throws Exception
  */
 public int findInvestmentByMoreCount(Map<String, Object> paraMap) throws Exception;
 /**
  * 多条件查询投资机构
  * @param paraMap
  * @return
  * @throws Exception
  */
 public List<investmentInfo> findInvestmentByMoreConExport(Map<String, Object> paraMap,List competitionList) throws Exception;
 
 /**
	 * 查询规模
	 * @return
	 * @throws Exception
	 */
public List findLableScle() throws Exception;
/**
 * 根据name模糊查询投资机构
 * @param paraMap
 * @return
 * @throws Exception
 */
public List<investmentInfo> findInvestmentByNameExport(Map<String, Object> paraMap) throws Exception;

}
