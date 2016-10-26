package cn.com.sims.dao.system.syslabelelementinfo;

import java.util.List;
import java.util.Map;

import cn.com.sims.model.syslabelelement.SysLabelelementInfo;

/**
 * 
 * @author duwenjie
 * @date 2015-10-12
 */
public interface ISysLabelelementInfoDao {

	/**
	 * 查询字典表
	 * @param 字典code
	 * @return
	 * @throws Exception
	 * 
	 */
	public List<Map<String, String>> findDIC(String str,String labelCode) throws Exception;
	
	/**
	 * 获取币种子项数据
	 * @return
	 * @author duwenjie
	 */
	public List<Map<String, String>> findAllCurrencyChild(String str)throws Exception;
	
		
	/**
	 * 根据名字查询标签
	 * @author rqq
	 * @date 2015-10-22 
	 * @param map:labelelementname:标签元素名称,labelcode:标签code
	 * @throws Exception
	*/
	public SysLabelelementInfo querylabelelementbyname(String str,Map<String, String> map) throws Exception;
	
	/**
	 * 根据查询标签最大id
	 * @author rqq
	 * @date 2015-10-22 
	 * @param labelcode:标签code
	 * @throws Exception
	*/
	public String querymaxlabelelementbycode(String str,String labelcode) throws Exception;
	
	/**
	 * 插入标签元素
	 * @author rqq
	 * @date 2015-10-22 
	 * @param syslabelelementinfo:标签元素对象
	 * @throws Exception
	*/
	public void insertsyslabelementinfo(String str,SysLabelelementInfo syslabelelementinfo) throws Exception;
	public void updatalable(String str,SysLabelelementInfo lable) throws Exception ;
	
	/**
	 * 查询类型标签数量
	 * @param str
	 * @param labelcode
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-29
	 */
	public int findLabelCountByLabelcode(String str,String labelcode)throws Exception;
	/**
	 * 查询标签上一个排序标签信息
	 * @param str
	 * @param elcode
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-29
	 */
	public SysLabelelementInfo findLabelNextLabel(String str,Map<String, String> map)throws Exception;
	
	/**
	 * 根据标签code，序号index为元素序号+1
	 * @param str
	 * @param map
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 * @date 2016-3-29
	 */
	public int updateLabelIndex(String str,Map<String, Object> map) throws Exception;
}
