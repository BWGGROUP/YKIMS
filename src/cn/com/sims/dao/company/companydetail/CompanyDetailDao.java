package cn.com.sims.dao.company.companydetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.sims.model.basecompentrepreneur.BaseCompEntrepreneur;
import cn.com.sims.model.basecompinfo.BaseCompInfo;
import cn.com.sims.model.basecompnoteinfo.BaseCompnoteInfo;
import cn.com.sims.model.baseentrepreneurinfo.BaseEntrepreneurInfo;
import cn.com.sims.model.basefinancplanemail.BaseFinancplanEmail;
import cn.com.sims.model.basefinancplaninfo.BaseFinancplanInfo;
import cn.com.sims.model.viewcompinfo.viewCompInfo;

/** 
 * @author  yl
 * @date ：2015年10月27日 
 * 公司详情dao
 */
public interface CompanyDetailDao {
	/**
	 * 根据公司ｉｄ查询公司信息
	 * @param str
	 * @param code
	 * @return
	 * @throws Exception
	*@author yl
	*@date 2015年10月27日
	 */
public viewCompInfo findCompanyDeatilByCode(String str,String code) throws Exception;
/**
 * 根据公司ｉｄ查询公司note信息
 * @param str
 * @param code
 * @return
 * @throws Exception
*@author yl
*@date 2015年10月27日
 */
public List<Map<String,String>> findCompanyNoteByCode(String str,String code) throws Exception;
/**
 * 根据公司ｉｄ查询公司融资计划信息
 * @param str
 * @param code
 * @return
 * @throws Exception
*@author yl
*@date 2015年10月27日
 */
public List<Map<String,String>> findFinancplanByCode(String str,String code) throws Exception;
/**
 * 根据公司ｉｄ查询公司融资信息
 * @param str
 * @param code
 * @return
 * @throws Exception
*@author yl
*@date 2015年10月27日
 */
public List<Map<String,String>> findFinancByCode(String str,Map<String, Object> map) throws Exception;
/**
 * 根据公司ｉｄ查询公司基础表版本号
 * @param str
 * @param code
 * @return
 * @throws Exception
*@author yl
*@date 2015年10月28日
 */
public long findVersionByCode(String str,String code) throws Exception;
/**
 * 根据公司ｉｄ查询公司联系人信息
 * @param str
 * @param code
 * @return
 * @throws Exception
*@author yl
*@date 2015年10月28日
 */
public List<Map<String,String>> findCompanyPeopleByCode(String str,String code) throws Exception;
/**
 * 修该公司名称
 * @param str
 * @param map
 * @return
 * @throws Exception
*@author yl
*@date 2015年10月28日
 */
public int tranModifyCompName(String str,BaseCompInfo view) throws Exception;
/**
 * 修改公司联系人
 * @param str
 * @param view
 * @return
 * @throws Exception
*@author yl
*@date 2015年10月28日
 */
public int tranModifyCompPeople(String str,BaseEntrepreneurInfo view) throws Exception;
/**
 * 修改公司联系人
 * @param str
 * @param view
 * @return
 * @throws Exception
*@author yl
*@date 2015年10月29日
 */
public void insertCompPeople(String str,BaseEntrepreneurInfo view) throws Exception;
/**
 * 修改公司和创业者关系
 * @param str
 * @param view
 * @return
 * @throws Exception
*@author yl
*@date 2015年10月29日
 */
public int updateCompany(String str,BaseCompEntrepreneur view) throws Exception;
/**
 * 插入公司和创业者关系表
 * @param str
 * @param view
 * @return
 * @throws Exception
*@author yl
*@date 2015年10月29日
 */
public void insertComp_people(String str,BaseCompEntrepreneur view) throws Exception;
/**
 * 插入公司note
 * @param str
 * @param info
 * @throws Exception
*@author yl
*@date 2015年10月29日
 */
public void insertCompNote(String str,BaseCompnoteInfo info) throws Exception;
/**
 * 删除公司note
 * @param str
 * @param code
 * @throws Exception
*@author yl
*@date 2015年10月29日
 */
public int compnote_del(String str,String code) throws Exception;

/**
 * 添加融资计划
 * @param str
 * @param baseFinancplanInfo
 * @return
 * @throws Exception
*@author yl
*@date 2015年10月30日
 */
public void insertFinancplan(String str,BaseFinancplanInfo baseFinancplanInfo) throws Exception;
/**
 * 添加融资计划的邮箱
 * @param str
 * @param BaseFinancplanEmail
 * @throws Exception
*@author yl
*@date 2015年10月30日
 */
public void insertPlanEamil(String str,BaseFinancplanEmail BaseFinancplanEmail) throws Exception;
/**
 * 查询公司名称
 * @param str
 * @param code
 * @return
 * @throws Exception
*@author yl
*@date 2015年10月30日
 */
public String findNameByCode(String str,String code) throws Exception;
/**
 * 通过创业者信息查询总条数
 * @param str
 * @param map
 * @return
 * @throws Exception
*@author yl
*@date 2015年11月3日
 */
public int findCountSizeByName(String str,Map<String,Object> map) throws Exception;
/**
 * 通过创业者信息查询创业者信息
 * @param str
 * @param map
 * @return
 * @throws Exception
*@author yl
*@date 2015年11月3日
 */
public List findEnterByName(String str,Map<String,Object> map) throws Exception;
/**
 * 查询公司名称是否重名
 * @param str
 * @param map
 * @return
 * @throws Exception
*@author yl
*@date 2015年11月4日
 */
public int selectName(String str,String name) throws Exception;

/**
 * 查询公司联系人是否重复
 * @param str
 * @param map
 * @return
 * @throws Exception
*@author yl
*@date 2015年11月5日
 */
public int selectPeople(String str,Map map) throws Exception;

/**
 * 根据输入模糊匹配公司（中文，全拼，首字母，英文名）总条数
 * @author rqq
 * @param map: name:公司相关名称
 * 2015-11-03
 */
public int querycompanylistnumByname(String str,Map<String, Object> map) throws Exception;

/**
 * 根据输入模糊匹配公司（中文，全拼，首字母，英文名）
 * @author rqq
 * @param map:pagestart:分页起始,limit:数目，name：名称
 * 2015-11-03
 */
public List<viewCompInfo> querycompanylistByname(String str,Map<String, Object> map) throws Exception;
/**
 * 根据输入公司名称判断名称是否唯一
* @author rqq
* @date 2015-11-04 
* @param  name:公司简称
* @throws Exception
 */
public int queryCompOnlyNamefortrade(String string, String name)throws Exception;
//2015-11-30 TASK070 yl add start
/**
 * 根据交易code查询出机构名称
 * @param str
 * @param code:交易code
 * @return
 * @throws Exception
*@author yl
*@date 2015年11月30日
 */
public String findInvestNameByTrad(String str,String code) throws Exception;
//2015-11-30 TASK070 yl add end
}
