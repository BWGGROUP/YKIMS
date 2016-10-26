package cn.com.sims.service.tradeinfo;


import java.util.List;
import java.util.Map;

import cn.com.sims.model.basecompinfo.BaseCompInfo;
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.baseinvestmentinvestor.BaseInvestmentInvestor;
import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.basetradeinfo.BaseTradeInfo;
import cn.com.sims.model.basetradeinves.BaseTradeInves;
import cn.com.sims.model.basetradelabelinfo.BaseTradelabelInfo;
import cn.com.sims.model.basetradenoteinfo.BaseTradenoteInfo;
import cn.com.sims.model.viewInvestmentInfo.viewInvestmentInfo;
import cn.com.sims.model.viewInvestorInfo.viewInvestorInfo;
import cn.com.sims.model.viewcompinfo.viewCompInfo;

/**
 * @ClassName: ITradeAddService
 * @author rqq
 * @date 2015-11-02
 */
public interface ITradeAddService {

	/**
	 * 根据输入模糊匹配公司（中文，全拼，首字母，英文名）总条数
	* @author rqq
	* @date 2015-11-03 
	 */
	public int querycompanylistnumByname(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据输入模糊匹配公司（中文，全拼，首字母，英文名）
	* @author rqq
	* @date 2015-11-03 
	* @param  map:pagestart:分页起始,limit:数目，name：名称
	* @throws Exception
	 */
	public List<viewCompInfo> querycompanylistByname(Map<String, Object> map) throws Exception;


	/**
	 * 根据输入模糊匹配投资人（中文，全拼，首字母，英文名）总条数
	* @author rqq
	* @param map: name:投资人姓名,invementcode:机构code
	* @date 2015-11-03 
	 */
	public int queryInvestorlistnumByOrgId(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据输入模糊匹配投资人（中文，全拼，首字母，英文名）
	* @author rqq
	* @date 2015-11-03 
	* @param  map: name:投资人姓名,invementcode:机构code,pagestart:分页起始,limit:数目
	* @throws Exception
	 */
	public List<viewInvestorInfo> queryInvestorlistByOrgId(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据输入模糊匹配机构（中文，全拼，英文，首字母，英文名）总条数
	* @author rqq
	* @param map: name:机构简称
	* @date 2015-11-04 
	 */
	public int queryInvestmentlistnumByOrgname(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据输入模糊匹配机构（中文，全拼，英文，首字母，英文名）
	* @author rqq
	* @date 2015-11-04 
	* @param  map: name:机构简称,pagestart:分页起始,limit:数目
	* @throws Exception
	 */
	public List<viewInvestmentInfo> queryInvestmentlistByOrgname(Map<String, Object> map) throws Exception;

	/**
	 * 根据输入机构名称判断名称是否唯一
	* @author rqq
	* @date 2015-11-04 
	* @param  name:机构简称
	* @throws Exception
	 */
	public int queryInvestmentOnlyNamefottrade(String name) throws Exception;

	/**
	 * 根据输入公司名称判断名称是否唯一
	* @author rqq
	* @date 2015-11-04 
	* @param  name:公司简称
	* @throws Exception
	 */
	public int queryCompOnlyNamefortrade(String name) throws Exception;
	
	/**
	 * 添加交易
	 * @author rqq
	 * @date 2015-11-05
	 * @param basecompinfo：公司对象；
	 * baseinvestmentinfolist：机构list;
	 * baseinvestorinfolist:投资人list
	 * baseinvestmentinvestorlist:机构投资人关系list
	 * basetradeinfo：交易对象；
	 * basetradelabellist：交易标签list;
	 * basetradeinveslist：机构交易list
	 * basetradenoteinfo:交易note对象
	 * @throws Exception
	*/
	public void tranModifyaddtradeinfo(
		BaseCompInfo basecompinfo,
		List<baseInvestmentInfo> baseinvestmentinfolist,
		List<BaseInvestorInfo> baseinvestorinfolist,
		List<BaseInvestmentInvestor> baseinvestmentinvestorlist,
		BaseTradeInfo basetradeinfo,
		List<BaseTradelabelInfo> basetradelabellist,
		List<BaseTradeInves> basetradeinveslist,
		BaseTradenoteInfo basetradenoteinfo
		) throws Exception;
	
}