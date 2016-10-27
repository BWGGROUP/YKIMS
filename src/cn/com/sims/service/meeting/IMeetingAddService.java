package cn.com.sims.service.meeting;


import java.util.List;
import java.util.Map;

import cn.com.sims.model.basecompentrepreneur.BaseCompEntrepreneur;
import cn.com.sims.model.basecompinfo.BaseCompInfo;
import cn.com.sims.model.baseentrepreneurinfo.BaseEntrepreneurInfo;
import cn.com.sims.model.basefinancplanemail.BaseFinancplanEmail;
import cn.com.sims.model.basefinancplaninfo.BaseFinancplanInfo;
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.baseinvestmentinvestor.BaseInvestmentInvestor;
import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.basemeetinginfo.BaseMeetingInfo;
import cn.com.sims.model.basemeetinglabelinfo.BaseMeetingLabelInfo;
import cn.com.sims.model.basemeetingnoteinfo.BaseMeetingNoteInfo;
import cn.com.sims.model.basemeetingparp.BaseMeetingParp;
import cn.com.sims.model.basemeetingrele.BaseMeetingRele;
import cn.com.sims.model.basemeetingshare.BaseMeetingShare;

/**
 * @ClassName: ITradeAddService
 * @author rqq
 * @date 2015-11-02
 */
public interface IMeetingAddService {
	
	/**
	 * 根据公司CODE输入姓名模糊匹配公司联系人（中文，全拼，首字母，英文名）总条数
	* @author rqq
	* @param map: name:公司联系人姓名,compcode:公司code
	* @date 2015-11-03 
	 */
	public int queryEntrepreneurlistnumBycompId(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据公司CODE输入姓名模糊匹配公司联系人（中文，全拼，首字母，英文名）
	* @author rqq
	* @date 2015-11-03 
	* @param  map: name:公司联系人姓名,compcode:公司code,pagestart:分页起始,limit:数目
	* @throws Exception
	 */
	public List<BaseEntrepreneurInfo> queryEntrepreneurlistBycompId(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询全部易凯用户
	 * @author rqq
	 * @date 2015-11-09 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> querysysuserinfo() throws Exception;
	
	/**
	 * 查询全部普通权限筐
	 * @author rqq
	 * @date 2015-11-09 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> querysyswadinfo() throws Exception;
	
	/**
	 * 查询全部超级权限筐
	 * @author rqq
	 * @date 2015-11-09 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> querysupersyswadinfo() throws Exception;
	/**
	 * 添加会议
	 * @author rqq
	 * @date 2015-11-09
	 * @param basecompinfolist：公司对象list;
	 * basefinancplaninfolist:融资计划list
	 * basefinancplanemaillist:融资计划提醒人员list
	 * 	baseentrepreneurlist:公司联系人list
	 * basecompentrepreneurlist:公司联系人关系list
	 * baseinvestmentinfolist：机构list;
	 * baseinvestorinfolist:投资人list
	 * baseinvestmentinvestorlist:机构投资人关系list
	 * basemeetinginfo：会议对象；
	 * basemeetingsharellist：会议分享list;
	 * basemeetingparplist：会议参会者list
	 * basemeetingrelelist：会议相关机构公司投资人信息list
	 * basemeetingnoteinfo:会议note对象
	 * @throws Exception
	*/
	public void tranModifyaddmeetinginfo(
		List<BaseCompInfo> basecompinfolist,
		List<BaseFinancplanInfo> basefinancplaninfolist,
		List<BaseFinancplanEmail> basefinancplanemaillist,
		List<BaseEntrepreneurInfo> baseentrepreneurlist,
		List<BaseCompEntrepreneur> basecompentrepreneurlist,
		List<baseInvestmentInfo> baseinvestmentinfolist,
		List<BaseInvestorInfo> baseinvestorinfolist,
		List<BaseInvestmentInvestor> baseinvestmentinvestorlist,
		BaseMeetingInfo basemeetinginfo,
		List<BaseMeetingShare> basemeetingsharellist,
		List<BaseMeetingParp> basemeetingparplist,
		List<BaseMeetingRele> basemeetingrelelist,
		BaseMeetingNoteInfo basemeetingnoteinfo,
		List<BaseMeetingLabelInfo> meetinglabellist
		) throws Exception;
	
}