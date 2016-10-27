package cn.com.sims.dao.baseinvestorlabelinfo;
import cn.com.sims.model.baseinvestorlabelinfo.BaseInvestorlabelInfo;


/** 
 * @author  yl
 * @date ：2015年10月22日 
 * 类说明 
 */

public interface IBaseInvestorlabelInfoDao {
	/**
	 * 添加投资人标签
	 * @param str
	 * @param info投资人标签对象
	 * @throws Exception
	 */
	public void insertInveslabelInfo(String str,BaseInvestorlabelInfo info) throws Exception;
	
	/**
	 * 删除投资人标签
	 * @param str
	 * @param info投资人标签对象
	 * @throws Exception
	 */
	public void tranDeleteInvestorlabel(String str,BaseInvestorlabelInfo info) throws Exception;
	
	/**
	 * 添加投资人行业标签，it桔子导入
	 * @author rqq
	 * @date 2015-10-22 
	 * @param str
	 * @param baseinvestorlabelinfo投资人标签对象
	 * @throws Exception
	 */
	public void insertBaseInvestorlabelInfoforit(String str,BaseInvestorlabelInfo baseinvestorlabelinfo) throws Exception;
}
