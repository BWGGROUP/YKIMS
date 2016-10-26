package cn.com.sims.service.system.syslabelelementinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.sims.model.syslabelelement.SysLabelelementInfo;

/**
 * 
 * @author duwenjie
 * @date 2015-10-12
 */
public interface ISysLabelelementInfoService {

	/**
	 * 查看字典
	 * @param 字典code
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> findDIC(String labelCode) throws Exception;
	
	/**
	 * 获取币种子项数据
	 * @return
	 * @author duwenjie
	 */
	public List<Map<String, String>> findAllCurrencyChild()throws Exception;
	/**
	 * 根据标签code查询标签
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public SysLabelelementInfo lablebycode(String code) throws Exception;
	/**
	 * 更新标签
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Boolean updatalable(SysLabelelementInfo lable) throws Exception;
	/**
	 * 添加标签
	 * @param code
	 * @return
	 * @throws Exception
	 */
public Boolean insertsyslabelementinfo(SysLabelelementInfo syslabelelementinfo)throws Exception ;
/**
 * 
 * @return
 * @throws Exception
 */
public SysLabelelementInfo lablebysys_lable_codeandname(HashMap<String, String> map)throws Exception ;

/**
 * 查询类型标签数量
 * @param labelcode
 * @return
 * @throws Exception
 * @author duwenjie
 * @date 2016-3-29
 */
public int findLabelCountByLabelcode(String labelcode)throws Exception;

/**
 * 查询标签上一个序号标签信息
 * @param map
 * @return
 * @throws Exception
 * @author duwenjie
 * @date 2016-3-29
 */
public SysLabelelementInfo findLabelNextLabel(Map<String, String> map)throws Exception;

/**
 * 标签元素排序
 * @param addmap (sign:+或-，elcode标签元素code)
 * @param minMap (sign:+或-，elcode标签元素code)
 * @return
 * @throws Exception
 * @author duwenjie
 * @date 2016-3-29
 */
public int tranModifyUpdateLabelIndex(Map<String, Object> addMap,Map<String, Object> minMap)throws Exception;
}
