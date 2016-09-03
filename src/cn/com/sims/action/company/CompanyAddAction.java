package cn.com.sims.action.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import cn.com.sims.model.basecompentrepreneur.BaseCompEntrepreneur;
import cn.com.sims.model.basecompinfo.BaseCompInfo;
import cn.com.sims.model.basecomplabelinfo.BaseComplabelInfo;
import cn.com.sims.model.basecompnoteinfo.BaseCompnoteInfo;
import cn.com.sims.model.baseentrepreneurinfo.BaseEntrepreneurInfo;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.model.viewInvestmentInfo.viewInvestmentInfo;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.code.SerialNumberGeneratorService;
import cn.com.sims.service.company.companyCommon.companyCommonService;
import cn.com.sims.service.company.companydetail.ICompanyDetailService;
import cn.com.sims.service.system.syslabelelementinfo.ISysLabelelementInfoService;

/**
 * @author yl
 * @date ：2015年11月2日 添加公司
 */
@Controller
public class CompanyAddAction {
	@Resource
	ICompanyDetailService companyDetailService;
	@Resource
	ISysLabelelementInfoService dic;// 字典
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;//系统更新记录
	@Resource
	companyCommonService companyCommonService;//公司公用方法Service
	@Resource
	SerialNumberGeneratorService serialNumberGeneratorService;//生成id
	private static final Logger logger = Logger
			.getLogger(CompanyAddAction.class);
/**
 * 跳转到公司添加页面
 * @param request
 * @param logintype
 * @param response
 * @return
 * @throws Exception
*@author yl
*@date 2015年11月3日
 */
	@RequestMapping(value = "company_add")
	public String company_add(
			HttpServletRequest request,
			@RequestParam(value = "logintype", required = false) String logintype,
			HttpServletResponse response) throws Exception {
		// 字典 行业
		List<Map<String, String>> induList = null;
		// 字典 筐
		List<Map<String, String>> baskList = null;
		// 字典 阶段
		List<Map<String, String>> stageList = null;
		// 查询行业
		induList = dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-comindu"));
		// 查询筐
		baskList = dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-bask"));
		// 查询阶段
		stageList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-comstage"));
		request.setAttribute("induList", JSONArray.fromObject(induList));
		request.setAttribute("baskList", JSONArray.fromObject(baskList));
		request.setAttribute("stageList", JSONArray.fromObject(stageList));
		String Url = ConstantUrl.companyAdd(logintype);
		return Url;
	}
/**
 * 添加公司
 * @param request
 * @param response
 * @param page
 * @param logintype
 * @param name
 * @param fullname
 * @param ename
 * @param bask
 * @param indu
 * @param peoplelist
 * @param noteInfo
 * @throws Exception
*@author yl
*@date 2015年11月9日
 */
	@RequestMapping(value = "addCompany")
	public void addCompany(HttpServletRequest request,
			HttpServletResponse response, Page page,
			@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam("name") String name,@RequestParam("fullname") String fullname,
			@RequestParam("ename") String ename,@RequestParam("bask") String bask,
			@RequestParam("indu") String indu,@RequestParam("peoplelist") String peoplelist,
			BaseCompnoteInfo noteInfo,@RequestParam("stagecode") String stagecode) throws Exception {
		logger.info("CompanyAddAction.addCompany()方法Start");
		
		BaseCompInfo info = new BaseCompInfo();
//		SysStoredProcedure sysStoredProcedure = new SysStoredProcedure();
		
//		Map<String,Object> compMap = new HashMap<String,Object>();
		List<BaseEntrepreneurInfo> enterInfoList=new ArrayList<BaseEntrepreneurInfo>();
		List<BaseEntrepreneurInfo> enterInfoListup=new ArrayList<BaseEntrepreneurInfo>();	
		List<BaseCompEntrepreneur> compEntrepreneurList=new ArrayList<BaseCompEntrepreneur>();
		List<BaseComplabelInfo> labelList=new ArrayList<BaseComplabelInfo>();
		SysUserInfo sysUserInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		String message = "success";
		String compcode="";

		try {
			int data=companyCommonService.selectName(name);
			if(data==0){
				compcode=CommonUtil.PREFIX_COMP+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.COMPANYNOTE_TYPE);//生成一个公司code
				
				String[] pinYin=CommonUtil.getPinYin(name);//获取公司简称的拼音全拼,简称拼音首字母
				info.setBase_comp_code(compcode);
				info.setBase_comp_name(name);
				info.setBase_comp_fullname(fullname);
				info.setBase_comp_ename(ename);
				info.setBase_comp_namep(pinYin[0]);
				info.setBase_comp_namef(pinYin[1]);
				info.setBase_comp_stem("2");
				info.setBase_comp_estate("2");
				info.setBase_comp_stage(stagecode);
				info.setBase_datalock_viewtype(0);
				info.setBase_datalock_pltype("0");
				info.setDeleteflag("0");
				info.setCreateid(sysUserInfo.getSys_user_code());
				info.setCreatetime(new Timestamp(new Date().getTime()));
				info.setUpdtime(info.getCreatetime());
				info.setUpdid(info.getCreateid());
				//添加公司信息
//				int lock = companyCommonService.insertcompany(info);
//				if(lock!=0){
					/**
					 * 添加公司筐信息
					 */
				if(bask!=null){
					JSONArray basklist=JSONArray.fromObject(bask);
					for(int i=0;i<basklist.size();i++){
						net.sf.json.JSONObject jsonObject=basklist.getJSONObject(i);
						//公司标签信息
						BaseComplabelInfo labelinfo = new BaseComplabelInfo();
						labelinfo.setBase_comp_code(compcode);
						labelinfo.setSys_labelelement_code(jsonObject.getString("code"));
						labelinfo.setSys_label_code("Lable-bask");
						labelinfo.setDeleteflag("0");
						labelinfo.setCreateid(sysUserInfo.getSys_user_code());
						labelinfo.setCreatetime(new Timestamp(new Date().getTime()));
						labelinfo.setUpdid(labelinfo.getCreateid());
						labelinfo.setUpdtime(labelinfo.getCreatetime());
						labelList.add(labelinfo);
					}
				}
				
					/**
					 * 添加公司行业信息
					 */
				if(indu!=null){
					JSONArray indulist=JSONArray.fromObject(indu);
					for(int i=0;i<indulist.size();i++){
						net.sf.json.JSONObject jsonObject=indulist.getJSONObject(i);
						//公司标签信息
						BaseComplabelInfo labelinfo = new BaseComplabelInfo();
						labelinfo.setBase_comp_code(compcode);
						labelinfo.setSys_labelelement_code(jsonObject.getString("code"));
						labelinfo.setSys_label_code("Lable-comindu");
						labelinfo.setDeleteflag("0");
						labelinfo.setCreateid(sysUserInfo.getSys_user_code());
						labelinfo.setCreatetime(new Timestamp(new Date().getTime()));
						labelinfo.setUpdtime(labelinfo.getCreatetime());
						labelinfo.setUpdid(labelinfo.getCreateid());
						labelList.add(labelinfo);
					}
				}
				

					/**
					 * 添加创业者信息
					 */
					JSONArray entreList=JSONArray.fromObject(peoplelist);
					if(entreList!=null && entreList.size()>0){
						for (int i = 0; i < entreList.size(); i++) {	
							net.sf.json.JSONObject jsonObject=entreList.getJSONObject(i);
							if("".equals(jsonObject.get("code").toString())||jsonObject.get("code")==null){
								//生成一个创业者code
								String peoCode=CommonUtil.PREFIX_ENTREPRENEUR+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.ENTREPRENEUR_TYPE);
								BaseEntrepreneurInfo enterInfo = new BaseEntrepreneurInfo();
								String[] EnterpinYin=CommonUtil.getPinYin(jsonObject.get("name").toString());
								enterInfo.setBase_entrepreneur_code(peoCode);
								enterInfo.setBase_entrepreneur_email(jsonObject.get("email").toString());
								enterInfo.setBase_entrepreneur_phone(jsonObject.get("phone").toString());
								enterInfo.setBase_entrepreneur_wechat(jsonObject.get("wechat").toString());
								enterInfo.setBase_entrepreneur_stem("2");
								enterInfo.setBase_entrepreneur_estate("2");
								enterInfo.setBase_entrepreneur_name(jsonObject.get("name").toString());
								enterInfo.setBase_entrepreneur_namep(EnterpinYin[0]);
								enterInfo.setBase_entrepreneur_namepf(EnterpinYin[1]);
								enterInfo.setCreateid(sysUserInfo.getSys_user_code());
								enterInfo.setCreatetime(new Timestamp(new Date().getTime()));
								enterInfo.setUpdid(enterInfo.getCreateid());
								enterInfo.setUpdtime(enterInfo.getCreatetime());
								enterInfo.setDeleteflag("0");
								enterInfoList.add(enterInfo);
//								int data=companyCommonService.insertEnter(enterInfo);
//								if(data!=0){
									BaseCompEntrepreneur compEntrepreneur = new BaseCompEntrepreneur();
									compEntrepreneur.setBase_comp_code(compcode);
									compEntrepreneur.setBase_entrepreneur_posiname(jsonObject.get("posi").toString());
									compEntrepreneur.setBase_entrepreneur_code(peoCode);
									compEntrepreneur.setCreateid(sysUserInfo.getSys_user_code());
									compEntrepreneur.setCreatetime(new Timestamp(new Date().getTime()));
									compEntrepreneur.setUpdtime(compEntrepreneur.getCreatetime());
									compEntrepreneur.setUpdid(compEntrepreneur.getCreateid());
									compEntrepreneur.setDeleteflag("0");
									compEntrepreneurList.add(compEntrepreneur);
//									companyCommonService.insertComp_people(compEntrepreneur);
//								}
							}else if(jsonObject.get("code")!=null && jsonObject.get("code")!=""){
								BaseEntrepreneurInfo enterInfo = new BaseEntrepreneurInfo();
								String[] EnterpinYin=CommonUtil.getPinYin(jsonObject.get("name").toString());
								enterInfo.setBase_entrepreneur_code(jsonObject.get("code").toString());
								enterInfo.setBase_entrepreneur_email(jsonObject.get("email").toString());
								enterInfo.setBase_entrepreneur_phone(jsonObject.get("phone").toString());
								enterInfo.setBase_entrepreneur_wechat(jsonObject.get("wechat").toString());
								enterInfo.setBase_entrepreneur_estate("3");
								enterInfo.setBase_entrepreneur_namep(EnterpinYin[0]);
								enterInfo.setBase_entrepreneur_namepf(EnterpinYin[1]);
								enterInfo.setBase_entrepreneur_name(jsonObject.get("name").toString());
								enterInfo.setUpdid(sysUserInfo.getSys_user_code());
								enterInfo.setUpdtime(new Timestamp(new Date().getTime()));
								enterInfo.setDeleteflag("0");
								enterInfoListup.add(enterInfo);
//								int data=companyCommonService.insertEnter(enterInfo);
//								if(data!=0){
									BaseCompEntrepreneur compEntrepreneur = new BaseCompEntrepreneur();
									compEntrepreneur.setBase_comp_code(compcode);
									compEntrepreneur.setBase_entrepreneur_posiname(jsonObject.get("posi").toString());
									compEntrepreneur.setBase_entrepreneur_code(enterInfo.getBase_entrepreneur_code());
									compEntrepreneur.setCreateid(sysUserInfo.getSys_user_code());
									compEntrepreneur.setCreatetime(new Timestamp(new Date().getTime()));
									compEntrepreneur.setUpdid(compEntrepreneur.getCreateid());
									compEntrepreneur.setUpdtime(compEntrepreneur.getCreatetime());
									compEntrepreneur.setDeleteflag("0");
									compEntrepreneurList.add(compEntrepreneur);
//									companyCommonService.insertComp_people(compEntrepreneur);
//								}
							}
						}
					}
					/**
					 * 添加公司备注
					 */
					if(noteInfo.getBase_compnote_content()!=null&&noteInfo.getBase_compnote_content().trim()!=""){
						//生成一个公司note code
						String notecode=CommonUtil.PREFIX_COMPNOTE+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.COMPANYNOTE_TYPE);
						//添加创建者用户名
						noteInfo.setSys_user_name(sysUserInfo.getSys_user_name());
						//创建者id
						noteInfo.setCreateid(sysUserInfo.getSys_user_code());
						//创建时间
						noteInfo.setCreatetime(new Timestamp(new Date().getTime()));
						//公司note code
						noteInfo.setBase_compnote_code(notecode);
						noteInfo.setBase_comp_code(info.getBase_comp_code());
						noteInfo.setUpdtime(noteInfo.getCreatetime());
						noteInfo.setUpdid(noteInfo.getCreateid());
						noteInfo.setDeleteflag("0");
						//添加公司note到数据库
//						int i=companyDetailService.insertCompNote(noteInfo);
					}
					int count=companyCommonService.tranModifyCompany(info,labelList,enterInfoList,enterInfoListup,compEntrepreneurList,noteInfo);
					if(count!=0){
						Timestamp time = new Timestamp(new Date().getTime());
						/*添加系统更新记录*/
						baseUpdlogInfoService.insertUpdlogInfo(
								CommonUtil.findNoteTxtOfXML("Lable-company"),
								CommonUtil.findNoteTxtOfXML("Lable-company-name"),
								info.getBase_comp_code(), 
								info.getBase_comp_name(),
								CommonUtil.OPERTYPE_YK,
								sysUserInfo.getSys_user_code(), 
								sysUserInfo.getSys_user_name(),
								CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
								CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
								CommonUtil.findNoteTxtOfXML("addComp"),
								"",
								"添加公司["+info.getBase_comp_name()+"]",
								logintype,
								sysUserInfo.getSys_user_code(),
								time,
								"",
								time);
						if(entreList!=null && entreList.size()>0){
							for (int i = 0; i < entreList.size(); i++) {	
								net.sf.json.JSONObject jsonObject=entreList.getJSONObject(i);
								/*添加系统更新记录*/
								baseUpdlogInfoService.insertUpdlogInfo(
										CommonUtil.findNoteTxtOfXML("Lable-company"),
										CommonUtil.findNoteTxtOfXML("Lable-company-name"),
										info.getBase_comp_code(), 
										info.getBase_comp_name(),
										CommonUtil.OPERTYPE_YK,
										sysUserInfo.getSys_user_code(), 
										sysUserInfo.getSys_user_name(),
										CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
										CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
										CommonUtil.findNoteTxtOfXML("addCompPeople"),
										"",
										"添加公司联系人[姓名:"+jsonObject.getString("name")+";职位:"+jsonObject.getString("posi")+";手机:"+jsonObject.getString("phone")+";邮箱:"+jsonObject.getString("email")+";微信:"+jsonObject.getString("wechat")+"]",
										logintype,
										sysUserInfo.getSys_user_code(),
										time,
										"",
										time);
							}
							}
						message="success";
					}
			}else{
				compcode="";
				//2015-12-16 duwenjie 判断以存在返回信息
				message=CommonUtil.findNoteTxtOfXML("repeat");//"公司简称重复";
			}
			
//			}else{
//				message=CommonUtil.findNoteTxtOfXML("updateFail");
//			}
			//根据公司ｉｄ查询公司信息

			
			logger.info("CompanyAddAction.addCompany()方法end");
		} catch (Exception e) {
			message="error";
			logger.error("CompanyAddAction.addCompany() 发生异常", e);
			e.printStackTrace();
		}
		
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("code", compcode);
			resultJSON.put("name", name);
			resultJSON.put("time", new Timestamp(new Date().getTime()));
/*			resultJSON.put("viewCompInfo", new JSONObject(viewCompInfo));
			resultJSON.put("version", version);*/
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("CompanyAddAction.addCompany() 发生异常", e);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			logger.error("CompanyAddAction.addCompany() 发生异常", e);
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 根据名称模糊查询创业者信息
	 * @param request
	 * @param response
	 * @param logintype
	 * @param page
	 * @param name
	 * @throws Exception
	*@author yl
	*@date 2015年11月3日
	 */
	@RequestMapping(value = "findEntreByName")
	public void findEntreByName(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="logintype",required=false)String logintype,
			Page page, @RequestParam("name") String name) throws Exception {

		logger.info("CompanyAddAction.findEntreByName()方法Start");

		List list = null;
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String message = "success";

		try {
					list = new ArrayList<viewInvestmentInfo>();
					paraMap.put("name", name);
					//根据输入条件查询符合要求的投资机构总条数
					int countSize = companyCommonService.findCountSizeByName(paraMap);
					page.setTotalCount(countSize);
					//每页显示的条数
					paraMap.put("pageSize", page.getPageSize());
					//此页显示的开始索引
					paraMap.put("startIndex", page.getStartCount());
					//根据输入条件查询，条数，开始索引查询符合条件的投资机构信息
					list = companyCommonService.findEnterByName(paraMap);
		} catch (Exception e) {
			logger.error("CompanyAddAction.findEntreByName()方法异常", e);
			e.printStackTrace();
		}

		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			JSONArray jsonArray = JSONArray.fromObject(list);
			resultJSON.put("list", jsonArray);
			resultJSON.put("page", new JSONObject(page));
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("CompanyAddAction.findEntreByName() 发生异常",
					e);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			logger.error("CompanyAddAction.findEntreByName() 发生异常",
					e);
			e.printStackTrace();
			throw e;
		}

	}
	/**
	 * 根据公司简称 判断简称是否已存在
	 * @author zzg
	 * @date 2015-12-18
	 * @param request
	 * @param response
	 * @param logintype
	 * @param name
	 * @throws Exception
	 */
	@RequestMapping(value = "checkcombyname")
	public void checkcombyname(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="logintype",required=false)String logintype,
@RequestParam("name") String name) throws Exception {
		logger.info("CompanyAddAction.checkcombyname()方法Start");
		String message="error";
		int data=0;
		try {
			data=companyCommonService.selectName(name);
			if (data==0) {
				message="nocompany";
			}else {
				message="companyexist";
			}
		} catch (Exception e) {
			logger.error("CompanyAddAction.checkcombyname() 发生异常",e);
		}
		logger.info("CompanyAddAction.checkcombyname()方法end");
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
			logger.error("CompanyAddAction.findEntreByName() 发生异常",
					e);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			logger.error("CompanyAddAction.findEntreByName() 发生异常",
					e);
			e.printStackTrace();
			throw e;
		}
	}
}
