package cn.com.sims.service.company.companydetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.sims.model.basecompinfo.BaseCompInfo;
import cn.com.sims.model.basecompnoteinfo.BaseCompnoteInfo;
import cn.com.sims.model.baseentrepreneurinfo.BaseEntrepreneurInfo;
import cn.com.sims.model.basefinancplanemail.BaseFinancplanEmail;
import cn.com.sims.model.basefinancplaninfo.BaseFinancplanInfo;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.model.viewcompinfo.viewCompInfo;

/** 
 * @author  yl
 * @date ：2015年10月27日 
 * 类说明 
 */
public interface ICompanyDetailService {
	/**
	 * 根据公司ｉｄ查询公司信息
	 * @param 公司ｉｄ
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月27日
	 */
	public viewCompInfo findCompanyDeatilByCode(String code) throws Exception;
	/**
	 *  根据公司ｉｄ查询公司note信息
	 * @param  公司ｉｄ
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月27日
	 */
	public List<Map<String, String>> findNoteByCode(String code) throws Exception;
	/**
	 * 根据公司ｉｄ查询公司融资计划信息
	 * @param code
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月27日
	 */
	public List<Map<String, String>> findFinancplanByCode(String code) throws Exception;
	/**
	 * 根据公司ｉｄ查询公司融资信息
	 * @param code
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月27日
	 */
	public List<Map<String, String>> findFinancByCode(Map<String, Object> map) throws Exception;
	/**
	 * 根据公司ｉｄ查询公司基础表版本号
	 * @param code
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月28日
	 */
	public long findVersionByCode(String code) throws Exception;
	/**
	 * 根据公司ｉｄ查询公司联系人信息
	 * @param code
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月28日
	 */
	public List<Map<String, String>> findCompanyPeopleByCode(String code) throws Exception;
	/**
	 * 修改公司基础表
	 * @param view
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月28日
	 */
	public int tranModifyCompName(BaseCompInfo view) throws Exception;
	/**
	 * 修改公司联系人
	 * @param view
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月28日
	 */
	public int tranModifyCompPeople(String posi,String code,BaseEntrepreneurInfo view) throws Exception;
	/**
	 * 添加公司联系人关系
	 * @param posi
	 * @param code
	 * @param view
	 * @throws Exception
	*@author yl
	*@date 2015年10月29日
	 */
	public void tranModifyinsertCompPeople(String posi,String code,BaseEntrepreneurInfo view) throws Exception;
	/**
	 * 添加公司note
	 * @param info
	 * @throws Exception
	*@author yl
	*@date 2015年10月29日
	 */
	public int insertCompNote(BaseCompnoteInfo info) throws Exception;
	/**
	 * 删除公司note
	 * @param code
	 * @throws Exception
	*@author yl
	*@date 2015年10月29日
	 */
	public int compnote_del(String code) throws Exception;
	/**
	 * 更改公司标签
	 * @param userInfo
	 * @param newData
	 * @param oldData
	 * @param code
	 * @param type
	 * @param compName
	 * @param logintype
	 * @throws Exception
	*@author yl
	*@date 2015年10月29日
	 */
	public void tranModifyUpdateCompLab(SysUserInfo userInfo,String newData,String oldData,String code,String type,String compName,String logintype)throws Exception;
	/**
	 * 添加融资计划
	 * @param baseFinancplanInfo
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月30日
	 */
	public int insertFinancplan(BaseFinancplanInfo baseFinancplanInfo) throws Exception;
	/**
	 * 添加融资计划发送邮件信息
	 * @param baseFinancplanInfo
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月30日
	 */
	public int insertPlanEamil(BaseFinancplanEmail baseFinancplanEmail) throws Exception;
	/**
	 * 修改公司联系人
	 * @param posi
	 * @param code
	 * @param view
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年11月5日
	 */
	public int tranModifyCompPeo(String posi,String code,BaseEntrepreneurInfo view) throws Exception;
	/**
	 * 查询公司添加的联系人是不是已存在
	 * @param map
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年11月5日
	 */
	public int selectPeople(Map map) throws Exception;
	
	/**
	 * 添加融资计划事务
	 * @param baseFinancplanInfo
	 * baseFinancplanEmail:发送邮件对象
	 * @return
	 * @throws Exception
	*@author rqq
	*@date 2015年11月11日
	 */
	public int tranModifyaddFinancplan(BaseFinancplanInfo baseFinancplanInfo,BaseFinancplanEmail baseFinancplanEmail) throws Exception;
}
