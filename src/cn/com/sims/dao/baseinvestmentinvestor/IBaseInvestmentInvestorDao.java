package cn.com.sims.dao.baseinvestmentinvestor;

import java.util.List;
import java.util.Map;

import cn.com.sims.model.baseinvestmentinvestor.BaseInvestmentInvestor;
import cn.com.sims.model.baseinvestorlabelinfo.BaseInvestorlabelInfo;

/** 
 * @author  yl
 * @date ：2015年10月22日 
 * 类说明 
 */
public interface IBaseInvestmentInvestorDao {
/**
 * 修改投资人投资机构
 * @param str
 * @param map
 * @return
 * @throws Exception
*@author yl
*@date 2015年10月22日
 */
	public int tranModifyUpdInvestor(String str,Map map) throws Exception;
	/**
	 * 添加投资人所属投资机构
	 * @param str
	 * @param info
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月22日
	 */
	public int insertInvestor(String str,BaseInvestmentInvestor info) throws Exception;
	/**
	 * 查询投资人所属机构是否已经添加
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月22日
	 */
	public int findInvestmentInstor(String str,Map map) throws Exception;
	
	public List<Map<String,String>> findTradeLabelByCode(String str,String code) throws Exception;
	
		/**
	 * 查询投资人所属机构是否存在for　it桔子
	 * @param str
	 * @param map：base_investment_code：机构code;base_investor_code:投资人code
	 * @return
	 * @throws Exception
	*@author rqq
	*@date 2015年10月23日
	 */
	public BaseInvestmentInvestor queryInvestmentInvestorbycodeforit(String str,Map<String,String> map) throws Exception;
	
	/**
	 * 添加投资人机构信息
	 * @param str
	 * @param baseinvestmentinvestor:投资人机构信息对象
	 * @return
	 * @throws Exception
	*@author rqq
	*@date 2015年10月23日
	 */
	public void insertInvestmentInvestorforit(String str,BaseInvestmentInvestor baseinvestmentinvestor) throws Exception;
	
	/**
	 * 更新投资人机构信息
	 * @param str
	 * @param baseinvestmentinvestor:投资人机构信息对象
	 * @return
	 * @throws Exception
	*@author RQQ
	*@date 2015年10月23日
	 */
	public void updateInvestmentInvestorforit(String str,BaseInvestmentInvestor baseinvestmentinvestor) throws Exception;
	
}
