package cn.com.sims.dao.syswadinfo;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author rqq
 * @date 2015-11-09
 */
public interface SysWadInfoDao {
	/**
	 * 查询全部普通权限筐
	 * @author rqq
	 * @date 2015-11-09 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> querysyswadinfo(String str) throws Exception;
}
