package cn.com.sims.service.meeting;

import java.util.HashMap;
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
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.model.viewmeetingrele.ViewMeetingRele;
/**
 * 
 * @author zzg
 *@date 20155-10-14
 */
public interface MeetingService {
/**根据相关投资机构 公司 记录人 筛选会议列表
 * @author zzg
 * @date 20155-10-14
 * @param map
 * @return
 * @throws Exception
 */
	public List<ViewMeetingRele> screenlist(HashMap<String, Object> map) throws Exception;
	/**根据相关投资机构 公司 记录人 筛选会议列表（总条数）
	 * @author zzg
	 * @date 20155-10-14
	 * @param map
	 * @return
	 * @throws Exception
	 */
		public int screenlist_num(HashMap<String, Object> map) throws Exception;
		/**
		 * 根据会议code查询会议详情
		 * @author zzg
		 * @date 2015-10-15
		 * @param id
		 * @return
		 * @throws Exception
		 */
		public ViewMeetingRele viewmeetingreleBycode(String id) throws Exception;
		/**
		 * 根据会议code查询会议备注list
		 * @author zzg
		 * @date 2015-10-15
		 * @param id
		 * @return
		 * @throws Exception
		 */
		public List<BaseMeetingNoteInfo> basemeetingnoteBymeetcode(String id) throws Exception;
		/**
		 * 根据会议notecode删除会议备注
		 * @author zzg
		 * @date 2015-10-16
		 */
		public int meetingnote_del(String id) throws Exception;
		/**
		 *添加会议备注
		 * @author zzg
		 * @date 2015-10-16
		 */
		public int meetingnote_add(BaseMeetingNoteInfo b) throws Exception;
		
		/**
		 * 根据会议code查询分享范围
		 * @param code 会议code
		 * @param type 分享类型（1：筐，2：人）
		 * @return
		 * @throws Exception
		 * @author duwenjie
		 * @date 2016-3-4
		 */
		public List<Map<String, String>> findMeetShareByMeetingCode(String code,String type)throws Exception;
		
		/**
		 * 修改会议分享信息
		 * @param createid 修改人id
		 * @param meetingcode 会议code
		 * @param addList
		 * @param delList
		 * @throws Exception
		 * @author duwenjie
		 * @date 2016-3-4
		 */
		public void tranModifyShareInfo(String createid,String meetingcode,List<BaseMeetingShare> addList,List<Map<String, String>> delList)throws Exception;
		
		/**
		 * 根据会议code删除会议相关信息
		 * @param meetingcode
		 * @throws Exception
		 * @author duwenjie
		 * @date 2016-3-7
		 */
		public void tranDeleteMeetingInfoByCode(String meetingcode)throws Exception;
		
		/**
		 * 修改会议信息
		 * @param info
		 * @return
		 * @throws Exception
		 * @author duwenjie
		 * @date 2016-3-9
		 */
		public int updateBaseMeetingInfo(BaseMeetingInfo info)throws Exception;
		
		/**
		 * 根据会议code查询会议信息
		 * @param meetingcode
		 * @return
		 * @throws Exception
		 * @author duwenjie
		 * @date 2016-3-9
		 */
		public BaseMeetingInfo findBaseMeetingInfoByCode(String meetingcode)throws Exception;
		
		/**
		 * 修改会议参会人
		 * @param meetcode
		 * @param list
		 * @throws Exception
		 * @author duwenjie
		 * @date 2016-3-10
		 */
		public void tranModifyMeetingParp(String createid,String meetingcode,List<Map<String, String>> dellist,List<BaseMeetingParp> list)throws Exception;
		
		/**
		 * 修改会议相关机构
		 * @param baseinvestmentinfo 机构信息
		 * @param investorInfo 投资人信息
		 * @param investmentinvestor 机构投资人关系
		 * @param meetingrele 会议相关机构
		 * @throws Exception
		 * @date 2016-3-14
		 * @author duwenjie
		 */
		public void tranModifyMeetingOrg(baseInvestmentInfo baseinvestmentinfo,BaseInvestorInfo investorInfo,BaseInvestmentInvestor investmentinvestor,BaseMeetingRele meetingrele)throws Exception;
		
		
		/**
		 * 删除会议相关机构信息
		 * @param createid
		 * @param map （meetingcode：会议code，reletype：相关类型（1：机构，2：公司），relecode：相关code）
		 * @throws Exception
		 * @date 2016-3-14
		 * @author duwenjie
		 */
		public int tranDeleteMeetingReleOrg(String createid,Map<String, String> map)throws Exception;
		
		/**
		 * 删除会议相关公司信息
		 * @param createid
		 * @param map （meetingcode：会议code，reletype：相关类型（1：机构，2：公司），relecode：相关code）
		 * @throws Exception
		 * @date 2016-3-15
		 * @author duwenjie
		 */
		public int tranDeleteMeetingReleCompany(String createid,Map<String, String> map)throws Exception;
		
		/**
		 * 查询会议与会人
		 * @param meetingcode
		 * @return
		 * @throws Exception
		 * @date 2016-3-14
		 * @author duwenjie
		 */
		public List<BaseMeetingParp> findMeetingUserInfo(String meetingcode)throws Exception;
		
		/**
		 * 修改会议相关公司
		 * @param basecompinfo 公司
		 * @param userinfo 联系人
		 * @param basefinancplaninfo 融资计划
		 * @param emailList 发送邮件
		 * @param basemeetingrele 公司会议关系
		 * @throws Exception
		 * @date 2016-3-15
		 * @author duwenjie
		 */
		public void tranModifyMeetingCompany(BaseCompInfo basecompinfo,
				BaseEntrepreneurInfo userinfo,
				BaseCompEntrepreneur basecompentrepreneur,
				BaseFinancplanInfo basefinancplaninfo,
				List<BaseFinancplanEmail> emailList,
				BaseMeetingRele basemeetingrele)throws Exception;
		
		/**
		 * 根据筐code查询用户信息
		 * 
		 * @param wadcode 筐code
		 * @return
		 * @throws Exception
		 * @author duwenjie
		 * @date 2016-3-22
		 */
		public List<SysUserInfo> findUserInfoByBaskCode(String wadcode)throws Exception;
		
		/**
		 * 查询登录用户是否有权限查看会议
		 * @param map
		 * @return
		 * @throws Exception
		 * @author duwenjie
		 * @date 2016-3-22
		 */
		public String screenloginInfo(Map<String, Object> map) throws Exception;
		
		/**
		 * 修改会议信息
		 * @param meetingInfo
		 * @return
		 * @throws Exception
		 * @author duwenjie
		 * @date 2016-3-23
		 */
		public int updateMeetingContent(BaseMeetingInfo meetingInfo)throws Exception;
		
		/**
		 * 查询会议分享的筐是否都包含指定权限
		 * @param meetingcode
		 * @param juri
		 * @return
		 * @throws Exception
		 */
		public boolean findMeetingJuri(String meetingcode,String juri) throws Exception;
		
		/**
		 * 修改会议类型
		 * @param info
		 * @throws Exception
		 */
		public void tranModifyUpdateMeetingType(BaseMeetingLabelInfo info)throws Exception;
}
