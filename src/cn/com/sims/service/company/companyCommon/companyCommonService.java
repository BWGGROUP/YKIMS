package cn.com.sims.service.company.companyCommon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.sims.model.basecompentrepreneur.BaseCompEntrepreneur;
import cn.com.sims.model.basecompinfo.BaseCompInfo;
import cn.com.sims.model.basecomplabelinfo.BaseComplabelInfo;
import cn.com.sims.model.basecompnoteinfo.BaseCompnoteInfo;
import cn.com.sims.model.baseentrepreneurinfo.BaseEntrepreneurInfo;

public interface companyCommonService {
/**
 * 根据输入模糊匹配公司（中文，全拼，首字母，英文名）
* @author zzg
 * 2015-10-09
 */
	public List<BaseCompInfo> companylistByname(HashMap map) throws Exception;
	/**
	 * 根据输入模糊匹配公司（中文，全拼，首字母，英文名）总条数
	* @author zzg
	 * 2015-10-09
	 */
		public int companylistByname_totalnum(HashMap map) throws Exception;
		/**
		 * 多条件搜索公司列表总页数
		 * @return
		 * @throws Exception
		 * @author zzg
		 * @date 2015-10-22
		 */
		public int companysearchlist_num(HashMap<String, Object> map) throws Exception;
		/**
		 * 多条件搜索公司列表总页数
		 * @return
		 * @throws Exception
		 * @author zzg
		 * @date 2015-10-22
		 */
		public List<Map<String, Object>> companysearchlist(HashMap<String, Object> map) throws Exception;
		/**
		 * 通过名称搜索公司列表 总条数
		 * @param map
		 * @return
		 * @throws Exception
		 */
		public int company_searchlistbyname_num(HashMap<String, Object> map) throws Exception;
		/**
		 * 通过名称搜索公司列表
		 * @param map
		 * @return
		 * @throws Exception
		 */
		public List<Map<String, Object>> company_searchlistbyname(HashMap<String, Object> map) throws Exception;
        /**
         * 添加公司
   * @param info
   * @return
   * @throws Exception
   *@author yl
   *@date 2015年11月3日
         */
		public int insertcompany(BaseCompInfo info) throws Exception;
		/**
		 * 添加
		 * @param info
		 * @return
		 * @throws Exception
		*@author yl
		*@date 2015年11月3日
		 */
		public int insertLabel(BaseComplabelInfo info) throws Exception;
		/**
		 * 添加创业者信息
		 * @param info
		 * @return
		 * @throws Exception
		*@author yl
		*@date 2015年11月3日
		 */
		public int insertEnter(BaseEntrepreneurInfo info) throws Exception;
		
		/**
		 * 公司创业者关系表
		 * @param info
		 * @return
		 * @throws Exception
		*@author yl
		*@date 2015年11月3日
		 */
		public int insertComp_people(BaseCompEntrepreneur info) throws Exception;
		/**
		 * 通过创业者名称模糊查询创业者总条数
		 * @param name
		 * @return
		 * @throws Exception
		*@author yl
		*@date 2015年11月3日
		 */
		public int findCountSizeByName(Map<String,Object> map) throws Exception;
		/**
		 * 通过创业者名称模糊查询创业者
		 * @param map
		 * @return
		 * @throws Exception
		*@author yl
		*@date 2015年11月3日
		 */
		public List findEnterByName(Map<String,Object> map) throws Exception;
		/**
		 * 添加公司
		 * @return
		 * @throws Exception
		*@author yl
		*@date 2015年11月3日
		 */
		public int tranModifyCompany(BaseCompInfo info,List<BaseComplabelInfo> labelList,List<BaseEntrepreneurInfo> enterInfoList,List<BaseEntrepreneurInfo> enterInfoListup,List<BaseCompEntrepreneur> compEntrepreneurList,BaseCompnoteInfo noteInfo) throws Exception;
		/**
		 * 查找公司是否重名
		 * @param name
		 * @return
		 * @throws Exception
		*@author yl
		*@date 2015年11月4日
		 */
		public int selectName(String name) throws Exception;
		
}
