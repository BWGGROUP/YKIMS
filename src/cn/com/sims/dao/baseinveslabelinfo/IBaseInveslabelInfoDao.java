package cn.com.sims.dao.baseinveslabelinfo;

import java.util.List;
import java.util.Map;

import cn.com.sims.model.baseinveslabelinfo.BaseInveslabelInfo;

/**
 * 
 * @author duwenjie
 * @date 2015-10-13
 */
public interface IBaseInveslabelInfoDao {
	
	
	/**
	 * 添加投资机构标签
	 * @param str
	 * @param info投资机构标签对象
	 * @throws Exception
	 */
	public void insertOrgInveslabelInfo(String str,BaseInveslabelInfo info) throws Exception;
	
	/**
	 * 删除投资机构标签
	 * @param str
	 * @param info投资机构标签对象
	 * @throws Exception
	 */
	public void tranDeleteInveslabel(String str,BaseInveslabelInfo info) throws Exception;
	
	/**
	 * 添加投资机构标签，it桔子导入
	 * @author rqq
	 * @date 2015-10-22 
	 * @param str
	 * @param baseinveslabelinfo投资机构标签对象
	 * @throws Exception
	 */
	public void insertBaseInveslabelInfoforit(String str,BaseInveslabelInfo baseinveslabelinfo) throws Exception;
	
	/**
	 * 根据投资机构code查询投资机构交易记录标签信息
	 * @param str
	 * @param orgCode机构Code
	 * @return sys_labelelement_code,sys_label_code集合
	 * @throws Exception
	 * @author duwenjie
	 */
	public List<Map<String, String>> findTradeLabelByOrgCode(String str,String orgCode) throws Exception;
}
