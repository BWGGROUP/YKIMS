package cn.com.sims.dao.financing.financingsearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.sims.model.basefinancplanemail.BaseFinancplanEmail;
import cn.com.sims.model.basefinancplaninfo.BaseFinancplanInfo;

public interface FinancingSearchDao {
	/**
	 * @zzg
	 * 查询融资计划
	 * @date 2015-10-10
	 */
		public List<BaseFinancplanInfo> financingsearch(String str,HashMap<String, Object> map) throws Exception;
		/**
		 * @zzg
		 * 查询融资计划总页数
		 * @date 2015-10-10
		 */
		public int financingsearch_pagetotal(String str,HashMap<String, Object> map) throws Exception;
		
		/**
		 * 查找需要发邮件的融资计划
		 * @param str
		 * @param time
		 * @return
		 * @throws Exception
		*@author yl
		*@date 2015年10月30日
		 */
		public List<BaseFinancplanInfo> findFinancing(String str,String time) throws Exception;
		/**
		 * 修改融资计划
		 * @param str
		 * @param time
		 * @return
		 * @throws Exception
		*@author yl
		*@date 2015年10月30日
		 */
		public int tranModifyFinlan(String str,BaseFinancplanInfo baseFinancplanInfo) throws Exception;
		/**
		 * 修改融资计划
		 * @param str
		 * @param time
		 * @return
		 * @throws Exception
		*@author yl
		*@date 2015年10月30日
		 */
		public int updateFinlanemail(String str,BaseFinancplanEmail basefinancplanemail) throws Exception;
		
		/**
		 * 查询用户创建融资计划数量
		 * @param str
		 * @param usercode
		 * @return
		 * @throws Exception
		 */
		public int findFincingCountByUsercode(String str,String usercode)throws Exception;
		
		/**
		 * 查询用户创建融资计划信息
		 * @param str
		 * @param map
		 * @return
		 * @throws Exception
		 */
		public List<Map<String, Object>> findFincingByUsercode(String str,Map<String, Object> map) throws Exception;
		
}
