package cn.com.sims.service.baseinvestornoteinfo;

import java.util.List;
import java.util.Map;

import cn.com.sims.model.baseinvestornoteinfo.BaseInvestornoteInfo;

/**
 * @author E-mail:
 * @version ：2015年10月16日 类说明
 */
public interface IInvestorNoteService {
	/**
	 * 根据投资人id获取投资人note信息
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> findNoteByInvestorCode(String code)
			throws Exception;

	/**
	 * 根据投资人noteid删除note信息
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 * @author yl
	 * @date 2015年10月19日
	 */
	public int investotnote_del(String id) throws Exception;

	public void insertInvestorNote(BaseInvestornoteInfo noteInfo)
			throws Exception;
}
