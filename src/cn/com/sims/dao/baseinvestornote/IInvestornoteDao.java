package cn.com.sims.dao.baseinvestornote;

import java.util.List;
import java.util.Map;

import cn.com.sims.model.baseinvesnoteinfo.BaseInvesnoteInfo;
import cn.com.sims.model.baseinvestornoteinfo.BaseInvestornoteInfo;

/** 
 * @author  yl
 * @date ：2015年10月16日 
 * 投资人note信息dao
 */
public interface IInvestornoteDao {
	/**
	 * 根据投资人ｉｄ获取投资人note信息
	 * 
	 * @param str
	 * @param 投资人id            　
	 * @return
	 * @throws Exception
	 * @author yl
	 * @date 2015年10月16日
	 */
	public List<Map<String,String>> findTradeInfoByInvestorCode(String str,String code) throws Exception;
	/**
	 * 根据投资人noteid删除note信息
	 * @param str
	 * @param id
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月19日
	 */
	public int investornote_del(String str,String id) throws Exception;

	/**
	 * 插入投资机构note信息
	 * 
	 * @param str
	 * @param noteinfo
	 * @throws Exception
	 * @author yl
	 * @date 2015年10月19日
	 */
	public void insertInvestorNote(String str,BaseInvestornoteInfo noteinfo) throws Exception;
}
