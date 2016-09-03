package cn.com.sims.action.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.common.commonUtil.ConstantUrl;
import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.dao.financing.financingsearch.FinancingSearchDao;
import cn.com.sims.model.basecompinfo.BaseCompInfo;
import cn.com.sims.model.basecompnoteinfo.BaseCompnoteInfo;
import cn.com.sims.model.baseentrepreneurinfo.BaseEntrepreneurInfo;
import cn.com.sims.model.basefinancplanemail.BaseFinancplanEmail;
import cn.com.sims.model.basefinancplaninfo.BaseFinancplanInfo;
import cn.com.sims.model.basetradeinfo.BaseTradeInfo;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.model.viewcompinfo.viewCompInfo;
import cn.com.sims.service.basetradeinfo.IBaseTradeInfoService;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.code.SerialNumberGeneratorService;
import cn.com.sims.service.company.companyCommon.companyCommonService;
import cn.com.sims.service.company.companydetail.ICompanyDetailService;
import cn.com.sims.service.system.syslabelelementinfo.ISysLabelelementInfoService;
import cn.com.sims.service.system.sysstoredprocedure.ISysStoredProcedureService;
import cn.com.sims.service.tradeinfo.ITradeInfoService;

/** 
 * @author  yl
 * @date ：2015年10月27日 
 * 公司详情页面
 */
@Controller
public class CompanyDeatilAction {
	@Resource
	FinancingSearchDao financingSearchDao;//融资计划dao
	@Resource
	ICompanyDetailService companyDetailService;
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;//系统更新记录
	@Resource
	ISysStoredProcedureService storedProcedureService;//存储过程
	@Resource
	ISysLabelelementInfoService dic;//字典
	@Resource
	ITradeInfoService tradeInfoService;// 交易service
	@Resource
	SerialNumberGeneratorService serialNumberGeneratorService;//生成id
	@Resource
	companyCommonService companyCommonService;//公司公用方法Service
	//2015-12-01 TASK073 yl add start
	@Resource
	IBaseTradeInfoService iBaseTradeInfoService;//交易信息(基础)
	//2015-12-01 TASK073 yl add end
	private static final Logger logger = Logger
			.getLogger(CompanyDeatilAction.class);
	
	
	@RequestMapping(value = "findCompanyDeatilByCode")
	public String findCompanyDeatilByCode(HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam("code") String code,
			@RequestParam(value="type",required=false) String type,
			@RequestParam(value="backtype",required=false) String backtype) throws Exception {
		logger.info("CompanyDeatilAction.findCompanyDeatilByCode()方法Start");
		SysUserInfo userInfo=(SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		//公司信息基础数据
//		BaseCompInfo baseCompInfo = null;
		//公司详细信息（业务数据）
		viewCompInfo viewCompInfo = null;
		//联系人list
		List<Map<String,String>> entrepreneurList = null;
		//字典 行业
		List<Map<String, String>> induList=null;
		//行业不可删除
//		List<Map<String, String>> induDelList=null;
		//字典 筐
		List<Map<String, String>> baskList=null;
		//字典 阶段
		List<Map<String, String>> stageList=null;
		//融资计划
		List<Map<String, String>> financplanList=null;
		//备注集合
		List<Map<String, String>> noteList = null;
		//融资信息
		List<Map<String, String>> financList=null;
		//版本号
		long version = 0;
//		String message = "success";
		Map<String,Object> map=new HashMap<String,Object>();
//		Page page=new Page();
		try {
			//根据公司ｉｄ查询公司信息
			viewCompInfo=companyDetailService.findCompanyDeatilByCode(code);
			//根据公司ｉｄ查询公司联系人信息
			entrepreneurList=companyDetailService.findCompanyPeopleByCode(code);
			//查询行业
			induList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-comindu"));
			//查询阶段
			stageList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-comstage"));
			//查询筐
			baskList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-bask"));
			//根据公司id查询融资计划信息
			financplanList = companyDetailService.findFinancplanByCode(code);
			//2015-12-14 yl mod 修改前台显示条数　start
			//每页显示的条数
			/*map.put("pageSize", 5);*/
			//map.put("pageSize", Integer.parseInt(CommonUtil.findNoteTxtOfXML("SHOWPAGESIZE")));
			map.put("pageSize", new Page().getPageSize());
			//2015-12-14 yl mod 修改前台显示条数　end
			//此页显示的开始索引
			map.put("startIndex", 0);
			map.put("code", code);
			//根据公司id查询融资信息
			financList = companyDetailService.findFinancByCode(map);
			//根据公司ｉｄ查询基础表排他锁
			version = companyDetailService.findVersionByCode(code);
			//根据公司id查询note信息
			noteList = companyDetailService.findNoteByCode(code);
			
			if(type!=null&&type.equals("search")){
				Timestamp timestamp=CommonUtil.getNowTime_tamp();
				/*添加系统更新记录*/
				baseUpdlogInfoService.insertUpdlogInfo(
						CommonUtil.findNoteTxtOfXML("Lable-company"),
						CommonUtil.findNoteTxtOfXML("Lable-company-name"),
						"", 
						"",
						CommonUtil.OPERTYPE_YK,
						userInfo.getSys_user_code(), 
						userInfo.getSys_user_name(),
						CommonUtil.findNoteTxtOfXML("CODE-YK-SEARCH"),
						CommonUtil.findNoteTxtOfXML("CONTENT-YK-SEARCH"), 
						CommonUtil.findNoteTxtOfXML("search")+CommonUtil.findNoteTxtOfXML("company"),
						"",
						"[{\"搜索\":\"{\""+viewCompInfo.getBase_comp_code()
						+"\":\""+viewCompInfo.getBase_comp_name()+"\"}\"}]",
						logintype,
						userInfo.getSys_user_code(),
						timestamp,
						userInfo.getSys_user_code(),
						timestamp);
			}
			
			logger.info("CompanyDeatilAction.findCompanyDeatilByCode()方法end");
		} catch (Exception e) {
			logger.error("CompanyDeatilAction.findCompanyDeatilByCode()方法异常", e);
			e.printStackTrace();
		}

		request.setAttribute("viewCompInfo",viewCompInfo!=null?new JSONObject(viewCompInfo):new viewCompInfo());
		request.setAttribute("induList", JSONArray.fromObject(induList));
		request.setAttribute("stageList", JSONArray.fromObject(stageList));
		request.setAttribute("baskList", JSONArray.fromObject(baskList));
		request.setAttribute("noteList", JSONArray.fromObject(noteList));
		request.setAttribute("financplanList", JSONArray.fromObject(financplanList));
		request.setAttribute("financList", JSONArray.fromObject(financList));
		request.setAttribute("version", version);
		request.setAttribute("entrepreneurList",JSONArray.fromObject(entrepreneurList));
		request.setAttribute("nowdate",new Timestamp(new Date().getTime()));
		request.setAttribute("backtype",backtype==null?"":backtype);
		
		String Url = ConstantUrl.compDeatil(logintype);
		return Url;

	}

	/**
	 * 修改公司名称
	 * @param request
	 * @param response
	 * @param page
	 * @param logintype
	 * @param code
	 * @param name
	 * @param fullname
	 * @param ename
	 * @param version
	 * @param oldstr
	 * @throws Exception
	*@author yl
	*@date 2015年10月28日
	 */
	@RequestMapping(value = "updateName")
	public void updateName(HttpServletRequest request,
			HttpServletResponse response, Page page,
			@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam("code") String code,@RequestParam("name") String name,
			@RequestParam("fullname") String fullname,@RequestParam("ename") String ename,
			@RequestParam("version") long version,@RequestParam("oldstr") String oldstr) throws Exception {
		logger.info("CompanyDeatilAction.updateName()方法Start");
		
		//公司详细信息（业务数据）
		viewCompInfo viewCompInfo = null;
		BaseCompInfo view = new BaseCompInfo();
//		SysStoredProcedure sysStoredProcedure = new SysStoredProcedure();
		
//		Map<String,Object> compMap = new HashMap<String,Object>();
		SysUserInfo sysUserInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		String message = "success";
		int data=0;

		try {
			if(!oldstr.split(";")[0].replace("简称：", "").equals(name)){
				data=companyCommonService.selectName(name);
			}
			
			if(data==0){
				String[] pinYin=CommonUtil.getPinYin(name);//获取公司简称的拼音全拼,简称拼音首字母
				view.setBase_comp_code(code);
				view.setBase_comp_name(name);
				view.setBase_comp_fullname(fullname);
				view.setBase_comp_ename(ename);
				view.setBase_comp_namep(pinYin[0]);
				view.setBase_comp_namef(pinYin[1]);
				view.setBase_datalock_viewtype(version+1);
				view.setBase_datalock_pltype("0");
				view.setUpdid(sysUserInfo.getSys_user_code());
				view.setBase_comp_estate("3");
				view.setUpdtime(new Timestamp(new Date().getTime()));
				
				
				//根据投资人id查询投资人参与的交易信息总条数
				int lock = companyDetailService.tranModifyCompName(view);
				if(lock!=0){
					
					
					Timestamp time = new Timestamp(new Date().getTime());
					/*添加系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-company"),
							CommonUtil.findNoteTxtOfXML("Lable-company-name"),
							view.getBase_comp_code(), 
							view.getBase_comp_name(),
							CommonUtil.OPERTYPE_YK,
							sysUserInfo.getSys_user_code(), 
							sysUserInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
							CommonUtil.findNoteTxtOfXML("compNameUpdate"),
							"["+oldstr+"]",
							"修改为[简称："+name+";全称："+fullname+";英文名称："+ename+"]",
							logintype,
							sysUserInfo.getSys_user_code(),
							time,
							sysUserInfo.getSys_user_code(),
							time);
					
					
					message="success";
				}else{
					message=CommonUtil.findNoteTxtOfXML("updateFail");
				}
			}else{
				message="公司名称已存在";
			}
			//根据公司ｉｄ查询基础表排他锁
			version = companyDetailService.findVersionByCode(code);
			//根据公司ｉｄ查询公司信息
			viewCompInfo=companyDetailService.findCompanyDeatilByCode(code);
			
			logger.info("CompanyDeatilAction.updateName()方法end");
		} catch (Exception e) {
			message="error";
			logger.error("CompanyDeatilAction.updateName() 发生异常", e);
			e.printStackTrace();
		}
		
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("viewCompInfo", viewCompInfo!=null?new JSONObject(viewCompInfo):new viewCompInfo());
			resultJSON.put("version", version);
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("CompanyDeatilAction.updateName() 发生异常", e);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			logger.error("CompanyDeatilAction.updateName() 发生异常", e);
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 修改联系人方式
	 * @param request
	 * @param response
	 * @param page
	 * @param logintype
	 * @param code
	 * @param name
	 * @param fullname
	 * @param ename
	 * @param version
	 * @param oldstr
	 * @throws Exception
	*@author yl
	*@date 2015年10月28日
	 */
	@RequestMapping(value = "updatePeople")
	public void updatePeople(HttpServletRequest request,
			HttpServletResponse response, Page page,
			@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam("code") String code,@RequestParam("peopleName") String peopleName,
			@RequestParam("phone") String phone,@RequestParam("email") String email,
			@RequestParam("wechat") String wechat,	@RequestParam("version") long version,	
			@RequestParam("oldstr") String oldstr,@RequestParam("peoplecode") String peoplecode,
			@RequestParam("posi") String posi) throws Exception {
		logger.info("CompanyDeatilAction.updatePeople()方法Start");
		
		//联系人list
		List<Map<String,String>> entrepreneurList = null;
		//公司详细信息（业务数据）
//		viewCompInfo viewCompInfo = null;
		BaseCompInfo view = new BaseCompInfo();
		BaseEntrepreneurInfo entrep = new BaseEntrepreneurInfo();
//		SysStoredProcedure sysStoredProcedure = new SysStoredProcedure();
//		
//		Map<String,Object> compMap = new HashMap<String,Object>();
		SysUserInfo sysUserInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		String message = "success";

		try {
			
			view.setBase_comp_code(code);
			view.setBase_datalock_viewtype(version+1);
			view.setBase_datalock_pltype("0");
			view.setUpdid(sysUserInfo.getSys_user_code());
			view.setBase_comp_estate("3");
			view.setUpdtime(new Timestamp(new Date().getTime()));
			
			
			//修改公司基础表版本号
			int lock = companyDetailService.tranModifyCompName(view);
			if(lock!=0){
				String[] pinYin=CommonUtil.getPinYin(peopleName);//获取公司联系人名称的拼音全拼,简称拼音首字母
				entrep.setBase_entrepreneur_code(peoplecode);
				entrep.setBase_entrepreneur_email(email);
				entrep.setBase_entrepreneur_estate("3");
				entrep.setBase_entrepreneur_name(peopleName);
				entrep.setBase_entrepreneur_phone(phone);
				entrep.setBase_entrepreneur_wechat(wechat);
				entrep.setBase_entrepreneur_namep(pinYin[0]);
				entrep.setBase_entrepreneur_namepf(pinYin[1]);
				entrep.setUpdid(sysUserInfo.getSys_user_code());
				entrep.setUpdtime(new Timestamp(new Date().getTime()));
				//修改联系人
				int data=companyDetailService.tranModifyCompPeople(posi,code,entrep);
				Timestamp time = new Timestamp(new Date().getTime());
				/*添加系统更新记录*/
				baseUpdlogInfoService.insertUpdlogInfo(
						CommonUtil.findNoteTxtOfXML("Lable-company"),
						CommonUtil.findNoteTxtOfXML("Lable-company-name"),
						view.getBase_comp_code(), 
						view.getBase_comp_name(),
						CommonUtil.OPERTYPE_YK,
						sysUserInfo.getSys_user_code(), 
						sysUserInfo.getSys_user_name(),
						CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
						CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
						CommonUtil.findNoteTxtOfXML("compPeopleUpdate"),
						"修改联系方式"+oldstr,
						"修改为[名称：" +  peopleName+ ";职位:"+posi+";手机号：" +phone + ";"+"邮箱：" + email+";微信:"+wechat+"]",
						logintype,
						sysUserInfo.getSys_user_code(),
						time,
						sysUserInfo.getSys_user_code(),
						time);
				//根据公司ｉｄ查询基础表排他锁
				version = companyDetailService.findVersionByCode(code);
				//根据公司ｉｄ查询公司联系人信息
				entrepreneurList=companyDetailService.findCompanyPeopleByCode(code);
				message="success";
			}else{
				message=CommonUtil.findNoteTxtOfXML("updateFail");
			}
			
			logger.info("CompanyDeatilAction.updatePeople()方法end");
		} catch (Exception e) {
			message="error";
			logger.error("CompanyDeatilAction.updatePeople() 发生异常", e);
			e.printStackTrace();
		}
		
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("entrepreneurList", JSONArray.fromObject(entrepreneurList));
			resultJSON.put("version", version);
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("CompanyDeatilAction.updatePeople() 发生异常", e);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			logger.error("CompanyDeatilAction.updatePeople() 发生异常", e);
			e.printStackTrace();
			throw e;
		}
	}
	
	@RequestMapping(value = "addPeople")
	public void addPeople(HttpServletRequest request,
			HttpServletResponse response, Page page,
			@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam("code") String code,@RequestParam("peopleName") String peopleName,
			@RequestParam("phone") String phone,@RequestParam("email") String email,
			@RequestParam("wechat") String wechat,	@RequestParam("version") long version,	
			@RequestParam("oldstr") String oldstr,@RequestParam("peoplecode") String peoplecode,
			@RequestParam("posi") String posi) throws Exception {
		logger.info("CompanyDeatilAction.addPeople()方法Start");
		
		//联系人list
		List<Map<String,String>> entrepreneurList = null;
		//公司详细信息（业务数据）
//		viewCompInfo viewCompInfo = null;
		BaseCompInfo view = new BaseCompInfo();
		BaseEntrepreneurInfo entrep = new BaseEntrepreneurInfo();
//		SysStoredProcedure sysStoredProcedure = new SysStoredProcedure();
		
//		Map<String,Object> compMap = new HashMap<String,Object>();
		SysUserInfo sysUserInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		String message = "success";

		try {
			
			view.setBase_comp_code(code);
			view.setBase_datalock_viewtype(version+1);
			view.setBase_datalock_pltype("0");
			view.setUpdid(sysUserInfo.getSys_user_code());
			view.setBase_comp_estate("3");
			view.setUpdtime(new Timestamp(new Date().getTime()));
			
			
			//修改公司基本表
			int lock = companyDetailService.tranModifyCompName(view);
			if(lock!=0){
				if(!"".equals(peoplecode)){
					Map map=new HashMap();
					map.put("code", code);
					map.put("peoplecode", peoplecode);
					int count=companyDetailService.selectPeople(map);
					if(count==0){
						String[] pinYin=CommonUtil.getPinYin(peopleName);//获取公司联系人名称的拼音全拼,简称拼音首字母
						entrep.setBase_entrepreneur_code(peoplecode);
						entrep.setBase_entrepreneur_email(email);
						entrep.setBase_entrepreneur_estate("3");
						entrep.setBase_entrepreneur_name(peopleName);
						entrep.setBase_entrepreneur_phone(phone);
						entrep.setBase_entrepreneur_wechat(wechat);
						entrep.setBase_entrepreneur_namep(pinYin[0]);
						entrep.setBase_entrepreneur_namepf(pinYin[1]);
						entrep.setUpdid(sysUserInfo.getSys_user_code());
						entrep.setUpdtime(new Timestamp(new Date().getTime()));
						//修改联系人
						int data=companyDetailService.tranModifyCompPeo(posi,code,entrep);
					}else{
						message="repeat";
					}
					
				}else{
					String entercode=CommonUtil.PREFIX_ENTREPRENEUR+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.ENTREPRENEUR_TYPE);//生成一个投资机构code
					String[] pinYin=CommonUtil.getPinYin(peopleName);//获取公司联系人名称的拼音全拼,简称拼音首字母
					entrep.setBase_entrepreneur_code(entercode);
					entrep.setBase_entrepreneur_name(peopleName);
					entrep.setBase_entrepreneur_phone(phone);
					entrep.setBase_entrepreneur_wechat(wechat);
					entrep.setBase_entrepreneur_email(email);
					entrep.setBase_entrepreneur_estate("2");
					entrep.setBase_entrepreneur_stem("2");
					entrep.setDeleteflag("0");
					entrep.setBase_entrepreneur_namep(pinYin[0]);
					entrep.setBase_entrepreneur_namepf(pinYin[1]);
					entrep.setCreateid(sysUserInfo.getSys_user_code());
					entrep.setCreatetime(new Timestamp(new Date().getTime()));
					entrep.setUpdid(entrep.getCreateid());
					entrep.setUpdtime(entrep.getCreatetime());
					companyDetailService.tranModifyinsertCompPeople(posi,code,entrep);
				}
				if(!"repeat".equals(message)){
					Timestamp time = new Timestamp(new Date().getTime());
					/*添加系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-company"),
							CommonUtil.findNoteTxtOfXML("Lable-company-name"),
							view.getBase_comp_code(), 
							view.getBase_comp_name(),
							CommonUtil.OPERTYPE_YK,
							sysUserInfo.getSys_user_code(), 
							sysUserInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
							CommonUtil.findNoteTxtOfXML("compPeopleUpdate"),
							"",
							"添加联系人[名称：" +  peopleName+ ";职位:"+posi+";手机号：" +phone + ";"+"邮箱：" + email+";微信:"+wechat+"]",
							logintype,
							sysUserInfo.getSys_user_code(),
							time,
							sysUserInfo.getSys_user_code(),
							time);
					message="success";
				}

				//根据公司ｉｄ查询基础表排他锁
				version = companyDetailService.findVersionByCode(code);
				//根据公司ｉｄ查询公司联系人信息
				entrepreneurList=companyDetailService.findCompanyPeopleByCode(code);
				
			}else{
				message=CommonUtil.findNoteTxtOfXML("updateFail");
			}
			
			logger.info("CompanyDeatilAction.addPeople()方法end");
		} catch (Exception e) {
			message="error";
			logger.error("CompanyDeatilAction.addPeople() 发生异常", e);
			e.printStackTrace();
		}
		
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("entrepreneurList", JSONArray.fromObject(entrepreneurList));
			resultJSON.put("version", version);
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("CompanyDeatilAction.addPeople() 发生异常", e);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			logger.error("CompanyDeatilAction.addPeople() 发生异常", e);
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 添加公司note
	 * @param request
	 * @param response
	 * @param noteInfo
	 * @param logintype
	 * @throws Exception
	*@author yl
	*@date 2015年10月29日
	 */
	@RequestMapping(value = "addCompNote")
	public void addCompNote(HttpServletRequest request,
			HttpServletResponse response,BaseCompnoteInfo noteInfo,String logintype) throws Exception {
		List<Map<String, String>> noteList = null;//定义公司备注集合
		String message = "error";
		/* 添加投资人note */
		try {
			String code=CommonUtil.PREFIX_COMPNOTE+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.COMPANYNOTE_TYPE);//生成一个投资机构code
			SysUserInfo sysUserInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
			
			noteInfo.setSys_user_name(sysUserInfo.getSys_user_name());//添加创建者用户名
			noteInfo.setCreateid(sysUserInfo.getSys_user_code());//创建者id
			noteInfo.setCreatetime(new Timestamp(new Date().getTime()));//创建时间
			noteInfo.setBase_compnote_code(code);//投资人note code
			noteInfo.setBase_comp_code(noteInfo.getBase_comp_code());
			noteInfo.setUpdtime(noteInfo.getCreatetime());
			noteInfo.setUpdid(noteInfo.getCreateid());
			noteInfo.setDeleteflag("0");
			int i=companyDetailService.insertCompNote(noteInfo);//添加公司note到数据库
			//根据公司id查询note信息
			noteList = companyDetailService.findNoteByCode(noteInfo.getBase_comp_code());
			if(i==1){
				message="success";
			}
			
		} catch (Exception e) {
			message="error";
			logger.error("InvestmentDetailInfoAction.addCompNote() 发生异常", e);
			e.printStackTrace();
		}
		
		/* 返回投资机构note集合 */
		PrintWriter out = null;
		JSONArray jsonArray=null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			jsonArray=JSONArray.fromObject(noteList);
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("noteList", jsonArray);
			resultJSON.put("message", message);
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.addCompNote() 发生异常", e);
			e.printStackTrace();
			throw e;
		}finally{
			out.close();
		}
		
	}
	/**
	 * 公司备注删除
	 * @param request
	 * @param response
	 * @param logintype
	 * @param notecode
	 * @throws Exception
	*@author yl
	*@date 2015年11月11日
	 */
	@RequestMapping(value = "compnote_del")
	public void compnote_del(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam("notecode") String notecode) throws Exception {
		logger.info("InvestmentDetailInfoAction.compnote_del()方法Start");
		String message;
		try {
			int i=companyDetailService.compnote_del(notecode);
			if (i==1) {
				message="success";
			}else {
				message="failure";
			}
			logger.info("InvestmentDetailInfoAction.compnote_del()方法end");
		} catch (Exception e) {
			message="error";
			logger.error("InvestmentDetailInfoAction.compnote_del() 发生异常", e);
			e.printStackTrace();
		}
		
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("InvestmentDetailInfoAction.compnote_del() 发生异常", e);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.compnote_del() 发生异常", e);
			e.printStackTrace();
			throw e;
		}
	}
	
	@RequestMapping(value = "updateCompInfo")
	public void editOrgInfo(HttpServletRequest request,
			HttpServletResponse response,String code,String compName,String type,String logintype,String newData,String oldData,long version) throws Exception {
		SysUserInfo sysUserInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);//获取当前登录人
		viewCompInfo compInfo=new viewCompInfo();//定义公司详情
		BaseCompInfo baseInfo=new BaseCompInfo();//定义公司信息
		String message="success";//返回消息
//		String resultData="";//返回数据
//		String oldda="";
		String newda="";
		String newdata="";
		try{
			baseInfo.setBase_comp_code(code);
			baseInfo.setBase_datalock_viewtype(version+1);
			baseInfo.setBase_datalock_pltype("0");
			baseInfo.setUpdid(sysUserInfo.getSys_user_code());
			baseInfo.setBase_comp_estate("3");
			baseInfo.setUpdtime(new Timestamp(new Date().getTime()));
			if(type.equals("Lable-stage")){
				JSONArray newArray=JSONArray.fromObject(newData);
				if(newArray.size()>0){
					newda=newArray.getJSONObject(0).getString("code");
					newdata=newArray.getJSONObject(0).getString("name");
				}
				baseInfo.setBase_comp_stage(newda);
				int lock=companyDetailService.tranModifyCompName(baseInfo);
				if(lock!=0){
					Timestamp time = new Timestamp(new Date().getTime());
					/*添加系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-company"),
							CommonUtil.findNoteTxtOfXML("Lable-company-name"),
							code, 
							compName,
							CommonUtil.OPERTYPE_YK,
							sysUserInfo.getSys_user_code(), 
							sysUserInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
							CommonUtil.findNoteTxtOfXML("compStageUpdate"),
							"["+oldData+"]",
							"修改为["+newdata+"]",
							logintype,
							sysUserInfo.getSys_user_code(),
							time,
							sysUserInfo.getSys_user_code(),
							time);
				}else{
					message=CommonUtil.findNoteTxtOfXML("updateFail");
				}
			}else{
				int lock=companyDetailService.tranModifyCompName(baseInfo);
				
				/*如果lock不为0则更新成功*/
				if(lock!=0){
					/*修改公司标签*/
					companyDetailService.tranModifyUpdateCompLab(sysUserInfo, newData, oldData, code, type,compName,logintype);	
				}else {
					/*message="已被修改,请刷新页面再修改";*/
					message=CommonUtil.findNoteTxtOfXML("updateFail");
				}
			}	
			/*查询公司详情*/
			compInfo=companyDetailService.findCompanyDeatilByCode(code);
			//根据公司ｉｄ查询基础表排他锁
			version = companyDetailService.findVersionByCode(code);
		}catch(Exception e){
			
		}
		
		
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			/*resultJSON.put("list", resultData!=null?JSONArray.fromObject(resultData):null);*/
			resultJSON.put("compInfo", compInfo!=null?new JSONObject(compInfo):new viewCompInfo());
			resultJSON.put("version", version);
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.updateOrgInfo() 修改投资机构标签信息 返回查询投资机构信息 发生异常", e);
			e.printStackTrace();
			throw e;
		}finally{
			out.close();
		}
		
	}
	/**
	 * 通过公司code查询交易
	 * @param request
	 * @param response
	 * @param page
	 * @param logintype
	 * @param code
	 * @throws Exception
	*@author yl
	*@date 2015年11月11日
	 */
	@RequestMapping(value = "findTradeByCode")
	public void findTradeByCode(HttpServletRequest request,
			HttpServletResponse response, Page page,
			@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam("code") String code) throws Exception {
		logger.info("InvestmentDetailInfoAction.findTradeByCode()方法Start");
		
		Map<String,Object> tradeMap = new HashMap<String,Object>();
		//融资信息
		List<Map<String, String>> financList=null;
		
		String message = "success";

		try {
			//根据投资人id查询投资人参与的交易信息总条数
			int totalCount = tradeInfoService.findTradeCount(code);
			//给page类总条数赋值
			page.setTotalCount(totalCount);
			//获取每页显示的条数
			tradeMap.put("pageSize", page.getPageSize());
			//开始索引
			tradeMap.put("startIndex", page.getStartCount());
			//公司ｉｄ
			tradeMap.put("code", code);

			//根据公司id查询融资信息
			financList = companyDetailService.findFinancByCode(tradeMap);
			logger.info("InvestmentDetailInfoAction.findTradeByCode()方法end");
		} catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.findTradeByCode()方法异常", e);
			e.printStackTrace();
		}

		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("financList", JSONArray.fromObject(financList));
			resultJSON.put("page", new JSONObject(page));
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("InvestmentDetailInfoAction.findTradeByCode() 发生异常", e);
			e.printStackTrace();
			throw e;
		}finally{
			out.close();
		}

	}
	
	@RequestMapping(value = "addFinancplan")
	public void addFinancplan(HttpServletRequest request,
			HttpServletResponse response, Page page,
			@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam("code") String code,@RequestParam("plantime") String plantime,
			@RequestParam("emailtime") String emailtime,@RequestParam("text") String text,
			@RequestParam("compName") String compName) throws Exception {
		logger.info("CompanyDeatilAction.addFinancplan()方法Start");
		
		//融资计划
		BaseFinancplanInfo baseFinancplanInfo = new BaseFinancplanInfo();
		//存储过程
//		SysStoredProcedure sysStoredProcedure = new SysStoredProcedure();
		//融资邮件
		BaseFinancplanEmail baseFinancplanEmail = new BaseFinancplanEmail();
//		Map<String,Object> compMap = new HashMap<String,Object>();
		//融资计划
		List<Map<String, String>> financplanList=null;
		SysUserInfo sysUserInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		String message = "success";

		try {
			
			  /**
			   * 添加融资计划表的信息
			   */
				String plancode=CommonUtil.PREFIX_FINANCPLAN+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.FINANCPLAN_TYPE);//生成一个公司融资计划code
				baseFinancplanInfo.setBase_financplan_code(plancode);
				baseFinancplanInfo.setBase_comp_code(code);
				baseFinancplanInfo.setBase_financplan_date(plantime);
				baseFinancplanInfo.setBase_financplan_remindate(emailtime.substring(0,10));
				baseFinancplanInfo.setBase_financplan_cont(text);
				baseFinancplanInfo.setDeleteflag("0");
				baseFinancplanInfo.setBase_financplan_sendef("0");
				baseFinancplanInfo.setBase_financplan_sendwf("0");
				baseFinancplanInfo.setBase_financplan_sendestate("0");
				baseFinancplanInfo.setBase_financplan_sendwstate("0");
				baseFinancplanInfo.setCreateid(sysUserInfo.getSys_user_code());
				baseFinancplanInfo.setCreatetime(new Timestamp(new Date().getTime()));
				baseFinancplanInfo.setUpdid(sysUserInfo.getSys_user_code());
				baseFinancplanInfo.setUpdtime(baseFinancplanInfo.getCreatetime());
				/**
				 * 添加融资计划发送邮件信息
				 */
				baseFinancplanEmail.setBase_financplan_code(plancode);
				baseFinancplanEmail.setBase_financplan_email(sysUserInfo.getSys_user_code());
				baseFinancplanEmail.setBase_financplan_sendwstate("0");
				baseFinancplanEmail.setCreateid(sysUserInfo.getSys_user_code());
				baseFinancplanEmail.setCreatetime(new Timestamp(new Date().getTime()));
				baseFinancplanEmail.setDeleteflag("0");
				baseFinancplanEmail.setUpdid(sysUserInfo.getSys_user_code());
				baseFinancplanEmail.setUpdtime(baseFinancplanEmail.getCreatetime());
				baseFinancplanEmail.setBase_financplan_remindate(emailtime.substring(0,10));
				//添加融资计划
				int data = companyDetailService.tranModifyaddFinancplan(baseFinancplanInfo,baseFinancplanEmail);
				Timestamp time=new Timestamp(new Date().getTime());
				/*添加系统更新记录*/
				baseUpdlogInfoService.insertUpdlogInfo(
						CommonUtil.findNoteTxtOfXML("Lable-company"),
						CommonUtil.findNoteTxtOfXML("Lable-company-name"),
						code, 
						compName,
						CommonUtil.OPERTYPE_YK,
						sysUserInfo.getSys_user_code(), 
						sysUserInfo.getSys_user_name(),
						CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
						CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
						CommonUtil.findNoteTxtOfXML("addFinancplan"),
						"",
						"添加["+text+"]",
						logintype,
						sysUserInfo.getSys_user_code(),
						time,
						sysUserInfo.getSys_user_code(),
						time);
				if(data!=0){
					message="success";
			   }else{
				   message="failer";
			   }
				//根据公司id查询融资计划信息
				financplanList = companyDetailService.findFinancplanByCode(code);
				
			
			logger.info("CompanyDeatilAction.addFinancplan()方法end");
		} catch (Exception e) {
			message="error";
			logger.error("CompanyDeatilAction.addFinancplan() 发生异常", e);
			e.printStackTrace();
		}
		
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("financplanList", JSONArray.fromObject(financplanList));
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("CompanyDeatilAction.addFinancplan() 发生异常", e);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			logger.error("CompanyDeatilAction.addFinancplan() 发生异常", e);
			e.printStackTrace();
			throw e;
		}
	}
	//2015-12-01 TASK073 yl add start
	/**
	 * 删除融资信息
	 * @param request
	 * @param response
	 * @param logintype 登录标识(电脑:PC,微信:WX,手机:MB)
	 * @param tradeCode 交易code
	 * @param orgCodeString 机构code串
	 * @param tradeDate 交易日期
	 * @param companyName 公司名称
	 * @throws Exception
	 */
	@RequestMapping(value = "delTradeInfo")
	public void delTradeInfo(HttpServletRequest request,
			HttpServletResponse response,String logintype,String tradeCode,
     String orgCodeString,String tradeDate,String companyName,String compcode) throws Exception {
		logger.info("CompanyDeatilAction.delTradeInfo() 开始");
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		String message = "success";
		String version="";//排他锁版本号
		try {
			//交易信息(基础)
			BaseTradeInfo baseTradeInfo=iBaseTradeInfoService.findBaseTradeInfoByCode(tradeCode);
			if(baseTradeInfo!=null){
				//获取交易信息排他锁版本号
				version=baseTradeInfo.getBase_datalock_viewtype()+"";
				int data=iBaseTradeInfoService.tranDeleteTradeInfoByTradeCode(
						userInfo.getSys_user_code(), tradeCode, version, orgCodeString);
				if(data>0){
					Timestamp timestamp=CommonUtil.getNowTime_tamp();
					/*删除交易信息系统更新记录*/
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-company"),
							CommonUtil.findNoteTxtOfXML("Lable-company-name"),
							compcode, 
							companyName,
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-DELE"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-DELE"), 
							CommonUtil.findNoteTxtOfXML("tradeInfoDelete"),
							"删除[公司:"+companyName+",交易日期:"+tradeDate+"]交易记录",
							"",
							logintype,
							userInfo.getSys_user_code(),
							timestamp,
							userInfo.getSys_user_code(),
							timestamp);
				}else if(data==0){
					message="delete";
				}else{
					message=CommonUtil.findNoteTxtOfXML("updateFail");
				}
			}else{
				message="delete";
			}
			logger.info("CompanyDeatilAction.delTradeInfo() 结束");
		} catch (Exception e) {
			message=CommonUtil.findNoteTxtOfXML("deleteFail");
			logger.error("CompanyDeatilAction.delTradeInfo() 发生异常", e);
			e.printStackTrace();
		}
		
		
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	//2015-12-01 TASK073 yl add end
}
