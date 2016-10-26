package cn.com.sims.dao.sysimportinvesestagerela;

/**
 * 
 * @author RQQ
 *@date 2015-11-30
 */
public interface SysImportinvesestageRelaDao {
    /**
	 * 导入投资机构阶段时匹配规则，IT桔子
	 * @param str
	 * @param labelelementname：标签名称
	 * @return
	 * @throws Exception
	*@author rqq
	*@date 2015年11月30日
	 */
	public String queryinvesstageforIT(String str, String labelelementname) throws Exception;
	
}
