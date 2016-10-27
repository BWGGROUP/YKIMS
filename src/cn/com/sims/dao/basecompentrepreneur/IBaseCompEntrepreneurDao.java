package cn.com.sims.dao.basecompentrepreneur;

import java.util.Map;

import cn.com.sims.model.basecompentrepreneur.BaseCompEntrepreneur;

/**
 * 
 * @author rqq
 * @date 2015-10-23
 */
public interface IBaseCompEntrepreneurDao {
    /**
	 * 查询公司创业者关系是否存在for　it桔子
	 * @param str
	 * @param base_comp_code：公司code;base_entrepreneur_code:创业者code
	 * @return
	 * @throws Exception
	*@author rqq
	*@date 2015年10月26日
	 */
	public BaseCompEntrepreneur queryCompEntrepreneurbycodeforit(String str,Map<String,String> map) throws Exception;
	
	/**
	 * 添加公司创业者关系信息
	 * @param str
	 * @param basecompentrepreneur:公司创业者关系信息对象
	 * @return
	 * @throws Exception
	*@author rqq
	*@date 2015年10月26日
	 */
	public void insertCompEntrepreneurforit(String str,BaseCompEntrepreneur basecompentrepreneur) throws Exception;
	
	/**
	 * 更新公司创业者关系信息
	 * @param str
	 * @param basecompentrepreneur:公司创业者关系信息对象
	 * @return
	 * @throws Exception
	*@author RQQ
	*@date 2015年10月26日
	 */
	public void updateCompEntrepreneurforit(String str,BaseCompEntrepreneur basecompentrepreneur) throws Exception;
	
	
	
	
}
