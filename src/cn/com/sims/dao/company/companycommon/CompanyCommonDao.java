package cn.com.sims.dao.company.companycommon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.sims.model.basecompinfo.BaseCompInfo;

public interface CompanyCommonDao {
	/**
	 * 根据输入模糊匹配公司（中文，全拼，首字母，英文名）
	 * @author zzg
	 * @param 公司相关名称
	 * 2015-10-09
	 */
		public List<BaseCompInfo> companylistByname(String str,HashMap map) throws Exception;
		/**
		 * 根据输入模糊匹配公司（中文，全拼，首字母，英文名）总条数
		 * @author zzg
		 * @param 公司相关名称
		 * 2015-10-09
		 */
		public int companylistByname_totalnum(String str,HashMap map) throws Exception;
			
		/**
		* 根据名字查询公司数据库中是否存在易凯创建或修改的(基础层)
		* @author rqq
		* @date 2015-10-23 
		* @param str
		* @param map:basecompname公司简称;basecompfullname:公司全称
		* @return
		* @throws Exception
		*/
		public BaseCompInfo findBaseCompBynameForIT(String str,Map<String, String> map)throws Exception;
		
			
		/**
		* 根据对应IT桔子id查询公司数据库中是否存在记录(基础层)
		* @author rqq
		* @date 2015-10-23 
		* @param str
		* @param tmpcompanycode公司对应IT桔子id
		* @return
		* @throws Exception
		 */
		public BaseCompInfo findBaseCompBytmpcodeForIT(String str,String tmpcompanycode)throws Exception;
			
		/**
		* 更新it桔子导入数据公司数据
		* @author rqq
		* @date 2015-10-23 
		* @param str
		* @param basecompinfo公司数据
		* @return
		* @throws Exception
		*/
		public void updateBaseCompforIT(String str,
			BaseCompInfo basecompinfo) throws Exception;
			
		/**
		* 插入it桔子导入数据公司数据
		* @author rqq
		* @date 2015-10-23 
		* @param str
		* @param basecompinfo公司数据
		* @return
		* @throws Exception
		 */
		public void insertBaseCompforIT(String str,
			BaseCompInfo basecompinfo) throws Exception;
			/**
			 * 多条件搜索公司列表总页数
			 * @return
			 * @throws Exception
			 * @author zzg
			 * @date 2015-10-22
			 */
			public int companysearchlist_num(String str,HashMap<String, Object> map) throws Exception;
			/**
			 * 多条件搜索公司列表总页数
			 * @return
			 * @throws Exception
			 * @author zzg
			 * @date 2015-10-22
			 */
			public List<Map<String, Object>> companysearchlist(String str,HashMap<String, Object> map) throws Exception;
			/**
			 * 通过名称搜索公司列表 总条数
			 * @param map
			 * @return
			 * @throws Exception
			 */
			public int company_searchlistbyname_num(String str,HashMap<String, Object> map) throws Exception;
			/**
			 * 通过名称搜索公司列表
			 * @param map
			 * @return
			 * @throws Exception
			 */
			public List<Map<String, Object>> company_searchlistbyname(String str,HashMap<String, Object> map) throws Exception;
			
			public void insertcompany(String str,BaseCompInfo info) throws Exception;
}
