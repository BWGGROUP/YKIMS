package cn.com.sims.service.baseinveslabelinfo;

import java.util.List;
import java.util.Map;
import cn.com.sims.model.baseinveslabelinfo.BaseInveslabelInfo;
import cn.com.sims.model.sysuserinfo.SysUserInfo;

public interface IBaseInveslabelInfoService {
	/**
	 * 添加投资机构标签
	 * @param info投资机构标签对象
	 * @throws Exception
	 */
	public void insertOrgInveslabelInfo(BaseInveslabelInfo info) throws Exception;
	
	/**
	 * 删除投资机构标签
	 * @param info投资机构标签对象
	 * @throws Exception
	 */
	public void tranDeleteInveslabel(BaseInveslabelInfo info) throws Exception;
	
	
	
	/**
	 * 更新投资机构标签信息
	 * @param userInfo用户对象
	 * @param newData新选择标签code字符串
	 * @param oldData原标签code字符串
	 * @param orgCode投资机构code
	 * @param type 操作类型
	 * @param orgName机构名称
	 * @param logintype 登录标识
	 * @throws Exception
	 */
	public void tranModifyUpdateBaseInvestment(SysUserInfo userInfo,String newData,String oldData,String orgCode,String type,String orgName,String logintype)throws Exception;
	
	
	/**
	 * 根据投资机构code查询投资机构交易记录标签信息
	 * @param orgCode机构code
	 * @return
	 * @throws Exception
	 * @author duwenjie
	 */
	public List<Map<String, String>> findTradeLabelByOrgCode(String orgCode)throws Exception;
	
}
