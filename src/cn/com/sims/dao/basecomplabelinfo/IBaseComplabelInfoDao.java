package cn.com.sims.dao.basecomplabelinfo;

import cn.com.sims.model.basecomplabelinfo.BaseComplabelInfo;
import cn.com.sims.model.baseinveslabelinfo.BaseInveslabelInfo;

/**
 * 
 * @author rqq
 * @date 2015-10-23
 */
public interface IBaseComplabelInfoDao {
	/**
	 * 添加投资机构标签，it桔子导入
	 * @author rqq
	 * @date 2015-10-23 
	 * @param str
	 * @param basecomplabelinfo 公司标签对象
	 * @throws Exception
	 */
	public void insertBaseComplabelInfoforit(String str,BaseComplabelInfo basecomplabelinfo) throws Exception;
	/**
	 * 添加公司标签
	 * @param str
	 * @param info
	 * @throws Exception
	*@author yl
	*@date 2015年10月29日
	 */
	public void insertComplabelInfo(String str,BaseComplabelInfo info) throws Exception;
	/**
	 * 删除公司标签
	 * @param str
	 * @param info
	 * @throws Exception
	*@author yl
	*@date 2015年10月29日
	 */
	public void tranDeleteComplabel(String str,BaseComplabelInfo info) throws Exception;
}
