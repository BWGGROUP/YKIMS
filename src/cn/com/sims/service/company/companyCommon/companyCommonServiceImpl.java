package cn.com.sims.service.company.companyCommon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.sims.model.basecompentrepreneur.BaseCompEntrepreneur;
import cn.com.sims.model.basecompinfo.BaseCompInfo;
import cn.com.sims.model.basecomplabelinfo.BaseComplabelInfo;
import cn.com.sims.model.basecompnoteinfo.BaseCompnoteInfo;
import cn.com.sims.model.baseentrepreneurinfo.BaseEntrepreneurInfo;
import cn.com.sims.model.sysstoredprocedure.SysStoredProcedure;
import cn.com.sims.service.system.sysstoredprocedure.ISysStoredProcedureService;
import cn.com.sims.dao.basecomplabelinfo.IBaseComplabelInfoDao;
import cn.com.sims.dao.company.companycommon.CompanyCommonDao;
import cn.com.sims.dao.company.companydetail.CompanyDetailDao;

@Service
public class companyCommonServiceImpl implements companyCommonService {
	@Resource
	CompanyCommonDao CompanyCommonDao;
	//公司标签dao
	@Resource
	IBaseComplabelInfoDao dao;
	//公司详情dao
	@Resource
	CompanyDetailDao companyDetailDao;
	@Resource
	ISysStoredProcedureService storedProcedureService;//存储过程
	private static final Logger gs_logger = Logger
			.getLogger(companyCommonServiceImpl.class);

	/**
	 * 根据输入模糊匹配公司（中文，全拼，首字母，英文名）
	 * 
	 * @author zzg
	 * @param 公司相关名称
	 *            2015-10-09
	 */
	public List<BaseCompInfo> companylistByname(HashMap map) throws Exception {
		gs_logger.info("companyCommonServiceImpl companylistByname方法开始");
		List<BaseCompInfo> list = new ArrayList<BaseCompInfo>();
		try {
			list=CompanyCommonDao.companylistByname("BaseCompdInfo.companylistbyname", map);
			gs_logger.info("companyCommonServiceImpl companylistByname方法结束");
		} catch (Exception e) {
			gs_logger.error("companyCommonServiceImpl companylistByname方法异常",e);
			throw e;
		}
		gs_logger.info("companyCommonServiceImpl companylistByname方法结束");
		return list;
	}

	@Override
	public int companylistByname_totalnum(HashMap map) throws Exception {
		gs_logger.info("companyCommonServiceImpl companylistByname_totalnum方法开始");
		int i=0;
		try {
			i=CompanyCommonDao.companylistByname_totalnum("BaseCompdInfo.listbyname_totalnum", map);
			gs_logger.info("companyCommonServiceImpl companylistByname_totalnum方法结束");
		} catch (Exception e) {
			gs_logger.error("companyCommonServiceImpl companylistByname_totalnum方法异常",e);
			throw e;
		}
		return i;
	}

	@Override
	public int companysearchlist_num(HashMap<String, Object> map)
			throws Exception {
		gs_logger.info("companyCommonServiceImpl companysearchlist_num方法开始");
		int i=0;
		try {
			i=CompanyCommonDao.companysearchlist_num("viewCompInfo.companysearchlist_num", map);
			gs_logger.info("companyCommonServiceImpl companysearchlist_num方法结束");
		} catch (Exception e) {
			gs_logger.error("companyCommonServiceImpl companysearchlist_num方法异常",e);
			throw e;
		}
		return i;
	}

	@Override
	public List<Map<String, Object>> companysearchlist(HashMap<String, Object> map) throws Exception {
		gs_logger.info("companyCommonServiceImpl companysearchlist方法开始");
		List<Map<String, Object>> list=null;
		try {
			list=CompanyCommonDao.companysearchlist("viewCompInfo.companysearchlist", map);
			gs_logger.info("companyCommonServiceImpl companysearchlist方法结束");
		} catch (Exception e) {
			gs_logger.error("companyCommonServiceImpl companysearchlist方法异常",e);
			throw e;
		}
		return list;
	}

	@Override
	public int company_searchlistbyname_num(HashMap<String, Object> map)
			throws Exception {
		gs_logger.info("companyCommonServiceImpl company_searchlistbyname_num方法开始");
		int i=0;
		try {
			i=CompanyCommonDao.companysearchlist_num("viewCompInfo.company_searchlistbyname_num", map);
			gs_logger.info("companyCommonServiceImpl company_searchlistbyname_num方法结束");
		} catch (Exception e) {
			gs_logger.error("companyCommonServiceImpl company_searchlistbyname_num方法异常",e);
			throw e;
		}
		return i;
	}

	@Override
	public List<Map<String, Object>> company_searchlistbyname(
			HashMap<String, Object> map) throws Exception {
		List<Map<String, Object>> list=null;
		try {
			list=CompanyCommonDao.companysearchlist("viewCompInfo.company_searchlistbyname", map);
			gs_logger.info("companyCommonServiceImpl company_searchlistbyname方法结束");
		} catch (Exception e) {
			gs_logger.error("companyCommonServiceImpl company_searchlistbyname方法异常",e);
			throw e;
		}
		return list;
	}

	@Override
	public int insertcompany(BaseCompInfo info) throws Exception {
		// TODO Auto-generated method stub
		int data=0;
		try {
			CompanyCommonDao.insertcompany("BaseCompdInfo.insertcompany", info);
			data=1;
			gs_logger.info("companyCommonServiceImpl insertcompany方法结束");
		} catch (Exception e) {
			gs_logger.error("companyCommonServiceImpl insertcompany方法异常",e);
			throw e;
		}
		return data;
	}

	@Override
	public int insertLabel(BaseComplabelInfo info) throws Exception {
		// TODO Auto-generated method stub
		int data=0;
		try {
			dao.insertComplabelInfo("BaseCompdInfo.insertComplabelInfo", info);
			data=1;
			gs_logger.info("companyCommonServiceImpl insertLabel方法结束");
		} catch (Exception e) {
			gs_logger.error("companyCommonServiceImpl insertLabel方法异常",e);
			throw e;
		}
		return data;
		
	}

	@Override
	public int insertEnter(BaseEntrepreneurInfo info) throws Exception {
		// TODO Auto-generated method stub
		int data=0;
		try {
			companyDetailDao.insertCompPeople(
					"baseentrepreneurinfo.insertCompPeople", info);
			data=1;
			gs_logger.info("companyCommonServiceImpl insertEnter方法结束");
		} catch (Exception e) {
			gs_logger.error("companyCommonServiceImpl insertEnter方法异常",e);
			throw e;
		}
		return data;
	}

	@Override
	public int insertComp_people(BaseCompEntrepreneur info) throws Exception {
		// TODO Auto-generated method stub
		int data=0;
		try {
			companyDetailDao.insertComp_people(
					"basecompentrepreneur.insertComp_people", info);
			data=1;
			gs_logger.info("companyCommonServiceImpl insertComp_people方法结束");
		} catch (Exception e) {
			gs_logger.error("companyCommonServiceImpl insertComp_people方法异常",e);
			throw e;
		}
		return data;
	}

	@Override
	public int findCountSizeByName(Map<String,Object> map) throws Exception {
		// TODO Auto-generated method stub
		int data;
		try {
			data=companyDetailDao.findCountSizeByName("baseentrepreneurinfo.findCountSizeByName", map);

			gs_logger.info("companyCommonServiceImpl findCountSizeByName方法结束");
		} catch (Exception e) {
			gs_logger.error("companyCommonServiceImpl findCountSizeByName方法异常",e);
			throw e;
		}
		return data;
	}

	@Override
	public List findEnterByName(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		List list = new ArrayList(50);
		try{
		list=companyDetailDao.findEnterByName("baseentrepreneurinfo.findEnterByName", map);
		}catch(Exception e){
			gs_logger.error("companyCommonServiceImpl findEnterByName方法异常",e);
			throw e;
		}
		gs_logger.info("companyCommonServiceImpl findEnterByName方法结束");
		return list;
	}

	@Override
	public int tranModifyCompany(BaseCompInfo info,
			List<BaseComplabelInfo> labelList,
			List<BaseEntrepreneurInfo> enterInfoList,
			List<BaseEntrepreneurInfo> enterInfoListup,
			List<BaseCompEntrepreneur> compEntrepreneurList,
			BaseCompnoteInfo noteInfo) throws Exception {
		// TODO Auto-generated method stub
		int data=0;
		try{
			/**
			 * 添加公司标签信息
			 */
			if(labelList!=null && labelList.size()>0){
				for(int i=0;i<labelList.size();i++){
					dao.insertComplabelInfo("basecomplabelinfo.insertComplabelInfo", labelList.get(i));
				}
			}
			/**
			 * 添加创业者信息
			 */
			if(enterInfoList!=null&&enterInfoList.size()>0){
				for(int i=0;i<enterInfoList.size();i++){
					companyDetailDao.insertCompPeople(
							"baseentrepreneurinfo.insertCompPeople", enterInfoList.get(i));
				}
			}
			/**
			 * 修改创业者信息
			 */
			if(enterInfoListup!=null&&enterInfoListup.size()>0){
				for(int i=0;i<enterInfoListup.size();i++){
					companyDetailDao.tranModifyCompPeople(
							"baseentrepreneurinfo.tranModifyCompPeople",enterInfoListup.get(i));
				}
			}
			/**
			 * 添加创业者公司关系表
			 */
			if(compEntrepreneurList!=null&&compEntrepreneurList.size()>0){
				for(int i=0;i<compEntrepreneurList.size();i++){
					companyDetailDao.insertComp_people(
							"basecompentrepreneur.insertComp_people", compEntrepreneurList.get(i));
				}
			}
			/**
			 *添加公司note
			 */
			if(noteInfo.getBase_comp_code()!=null){
				companyDetailDao.insertCompNote(
						"BaseCompnoteInfo.insertCompNote", noteInfo);
			}
			//添加公司信息
			CompanyCommonDao.insertcompany("BaseCompdInfo.insertcompany", info);
			/*调用存储过程*/
			storedProcedureService.callViewcompinfo(new SysStoredProcedure(info.getBase_comp_code(), "add", "all", info.getCreateid(),"",""));
			data=1;
			}catch(Exception e){
				gs_logger.error("companyCommonServiceImpl tranModifyCompany方法异常",e);
				throw e;
			}
			gs_logger.info("companyCommonServiceImpl tranModifyCompany方法结束");
		return data;
	}

	@Override
	public int selectName(String name) throws Exception {
		// TODO Auto-generated method stub
		int data = 0;
		try{
			data=companyDetailDao.selectName("BaseCompdInfo.selectName", name);
		}catch(Exception e){
			gs_logger.error("companyCommonServiceImpl selectName方法异常",e);
			throw e;
		}
		gs_logger.info("companyCommonServiceImpl selectName方法结束");
		return data;
	}

}
