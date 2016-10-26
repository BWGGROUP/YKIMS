package cn.com.sims.dao.meeting;

import java.util.HashMap;
import java.util.List;

import cn.com.sims.model.basemeetingnoteinfo.BaseMeetingNoteInfo;
import cn.com.sims.model.viewmeetingrele.ViewMeetingRele;

/**
 * 
 * @author zzg
 *@date 20155-10-14
 */
public interface MeetingDao {
	/**根据相关投资机构 公司 记录人 筛选会议列表
	 * @author zzg
	 * @date 20155-10-14
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<ViewMeetingRele> screenlist(String str,HashMap<String, Object> map) throws Exception;
	/**根据相关投资机构 公司 记录人 筛选会议列表（总条数）
	 * @author zzg
	 * @date 20155-10-14
	 * @param map
	 * @return
	 * @throws Exception
	 */
		public int screenlist_num(String str,HashMap<String, Object> map) throws Exception;
		/**
		 * 根据会议code查询会议备注
		 * @author zzg
		 * @date 2015-10-15
		 * @param id
		 * @return
		 * @throws Exception
		 */
		public ViewMeetingRele viewmeetingreleBycode(String str,String id) throws Exception;
		/**
		 * 根据会议code查询会议备注list
		 * @author zzg
		 * @date 2015-10-15
		 * @param id
		 * @return
		 * @throws Exception
		 */
		public List<BaseMeetingNoteInfo> basemeetingnoteBymeetcode(String str,String id) throws Exception;
		/**
		 * 根据会议notecode删除会议备注
		 * @author zzg
		 * @date 2015-10-16
		 */
		public int meetingnote_del(String str,String id) throws Exception;
		/**
		 *添加会议备注
		 * @author zzg
		 * @date 2015-10-16
		 */
		public void meetingnote_add(String str,BaseMeetingNoteInfo b) throws Exception;
}
