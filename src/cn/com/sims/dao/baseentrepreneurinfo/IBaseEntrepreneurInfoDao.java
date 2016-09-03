package cn.com.sims.dao.baseentrepreneurinfo;

import java.util.List;
import java.util.Map;

import cn.com.sims.model.baseentrepreneurinfo.BaseEntrepreneurInfo;

/**
 * 
 * @author rqq
 * @date 2015-10-26
 */
public interface IBaseEntrepreneurInfoDao {
	
	/**
	 * 根据对应IT桔子id查询创业者数据库中是否存在记录(基础层)
	 * @author rqq
	 * @date 2015-10-26 
	 * @param str
	 * @param tmpentrepreneurcode创业者对应IT桔子id
	 * @return
	 * @throws Exception
	 */
	public BaseEntrepreneurInfo findBaseEntrepreneurBytmpcodeForIT(String str,String tmpentrepreneurcode)throws Exception;
	
	/**
	 * 更新it桔子导入数据创业者数据
	 * @author rqq
	 * @date 2015-10-26 
	 * @param str
	 * @param baseentrepreneurinfo创业者数据
	 * @return
	 * @throws Exception
	 */
	public void updateBaseEntrepreneurforIT(String str,
		BaseEntrepreneurInfo baseentrepreneurinfo) throws Exception;
	
	/**
	 * 插入it桔子导入数据创业者数据
	 * @author rqq
	 * @date 2015-10-26 
	 * @param str
	 * @param baseentrepreneurinfo创业者数据
	 * @return
	 * @throws Exception
	 */
	public void insertBaseEntrepreneurforIT(String str,
		BaseEntrepreneurInfo baseentrepreneurinfo) throws Exception;	
	
	/**
	 * 根据公司CODE输入姓名模糊匹配公司联系人（中文，全拼，首字母，英文名）数目
	 * @author rqq
	 * @date 2015-11-03 
	 * @param str
	 * @param map: name:公司联系人姓名,compcode:公司code
	 * @return
	 * @throws Exception
	 */
	public int queryEntrepreneurlistnumBycompId(String str,Map<String, Object> map)throws Exception;
	
	
	/**
	 * 根据公司CODE输入姓名模糊匹配公司联系人（中文，全拼，首字母，英文名）
	 * @author rqq
	 * @date 2015-11-03 
	 * @param str
	 * @param map: name:公司联系人姓名,compcode:公司code,pagestart:分页起始,limit:数目
	 * @return
	 * @throws Exception
	 */
	public List<BaseEntrepreneurInfo> queryEntrepreneurlistBycompId(String str,Map<String, Object> map)throws Exception;
	
	
}
