package cn.com.sims.action.management;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import cn.com.sims.action.user.UserAction;
import cn.com.sims.common.bean.EmailBean;
import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.common.commonUtil.ConstantUrl;
import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.common.commonUtil.VerifyCodeUtils;
import cn.com.sims.model.syslabelelement.SysLabelelementInfo;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.model.sysuserwad.SysUserWad;
import cn.com.sims.model.syswadinfo.SysWadInfo;
import cn.com.sims.model.syswadjuri.SysWadJuri;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.code.SerialNumberGeneratorService;
import cn.com.sims.service.importitdata.IImportITdataService;
import cn.com.sims.service.system.syslabelelementinfo.ISysLabelelementInfoService;
import cn.com.sims.service.system.sysuserwad.SysUserWadService;
import cn.com.sims.service.sysuserinfo.SysUserInfoService;
import cn.com.sims.util.email.IEmailSenderService;

/**
 * 后台管理-员工管理页面
 * @author zzg
 * @date 2015-10-29
 *
 */
@Controller
public class ManagementAction {
	private static final Logger logger = Logger
	.getLogger(ManagementAction.class);

	@Resource
	ISysLabelelementInfoService dic;//字典
	@Resource
	SysUserWadService SysUserWadService;
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;//系统更新记录
	@Resource
	SysUserInfoService SysUserInfoService;
	@Resource
	SerialNumberGeneratorService serialNumberGeneratorService;//生成id
	@Resource
	IEmailSenderService emailSenderService;//email 发送service
    @Resource
    IImportITdataService iimportitdataservice;
	/**
	 * 跳转到员工管理
	 * @param request
	 * @param response
	 * @param logintype
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"admin/employee"})

	public String employee(HttpServletRequest request,
			HttpServletResponse response,String logintype) throws Exception {

		String Url=ConstantUrl.employee(logintype);
		return Url;
	}
	/**
	 * 跳转到筐管理
	 * @param request
	 * @param response
	 * @param logintype
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "admin/kuang")
	public String kuang(HttpServletRequest request,
			HttpServletResponse response,String logintype) throws Exception {
		logger.info("ManagementAction.kuang()方法Start");
		List<Map<String, String>> baskList=null;//字典 关注筐
		try {
			baskList=dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-bask"));//查询筐
		} catch (Exception e) {
			logger.error("ManagementAction.kuang()方法异常",e);
			e.printStackTrace();
		}
		logger.info("ManagementAction.kuang()方法end");
		request.setAttribute("baskList", JSONArray.fromObject(baskList));
		String Url=ConstantUrl.kuang(logintype);
		return Url;
	}
	@RequestMapping(value = "admin/manageandpeobyKcode")
	public void manageandpeobyKcode(HttpServletRequest request,
			HttpServletResponse response,String logintype,
			@RequestParam("Kcode") String Kcode) throws Exception {
		logger.info("ManagementAction.manageandpeobyKcode()方法Start");
		//权限list
//		List<SysWadJuri> sysWadJuris=null;
		//属于这个筐的用户
//		List<SysUserInfo> inlist=null;
		//不属于这个筐的用户
//		List<SysUserInfo> outlist=null;
		try {
//			sysWadJuris=
		} catch (Exception e) {
			logger.error("ManagementAction.manageandpeobyKcode()方法异常",e);
		}
		logger.info("ManagementAction.manageandpeobyKcode()方法end");
	}
	/**
	 * 根据名称查询筐列表
	 * @param request
	 * @param response
	 * @param logintype
	 * @param name
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "admin/kuanglistbyname")
	public void kuanglistbyname(HttpServletRequest request,
			HttpServletResponse response,String logintype,
			@RequestParam("name") String name,Page page) throws Exception {
		logger.info("ManagementAction.kuanglistbyname()方法Start");
		List<Map<String, Object>> list=null;
		String message;
		int list_total=0;
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("name", name);
		try {
			list_total=SysUserWadService.wadbyname_num(map);
			page.setTotalCount(list_total);
			map.put("start", page.getStartCount());
			map.put("size", page.getPageSize());
			list=SysUserWadService.wadbyname(map);
			message="success";
			if (list.size()==0) {
				message="nomore";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ManagementAction.kuanglistbyname()方法异常",e);
			message="error";
		}
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("list", list);
			resultJSON.put("page", new JSONObject(page));
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("ManagementAction.kuanglistbyname()方法异常",e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("ManagementAction.kuanglistbyname()方法异常",e);
			e.printStackTrace();
		}
	}
	/**SysUserInfoService
	 * 根据筐code删除筐
	 * @param request
	 * @param response
	 * @param logintype
	 * @param code
	 * @throws Exception
	 */
	@RequestMapping(value = "admin/kuangdelete")
	public void kuangdelete(HttpServletRequest request,
			HttpServletResponse response,String logintype,
			@RequestParam("code") String code,
			@RequestParam("name") String name) throws Exception {
		String message="error";
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		logger.info("ManagementAction.kuangdelete()方法Start");
		//如果不是超级管理权限code
		if (!code.equals(CommonUtil.findNoteTxtOfXML("JURI-ALL"))) {
			try {
				Boolean b=SysUserWadService.tranDeletewadbycode(code);
				if (b) {
					/* 添加系统更新记录 */
					Timestamp time = new Timestamp(new Date().getTime());
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-syskuang"),
							CommonUtil.findNoteTxtOfXML("Lable-syskuang-name"),
							code,
							name,
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-DELE"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-DELE"), 
							CommonUtil.findNoteTxtOfXML("delesystkuang"),
							"删除["+name+"]",
							"",
							logintype,
							userInfo.getSys_user_code(),
							time,
							userInfo.getSys_user_code(), 
							time);
					message="success";
				}
			} catch (Exception e) {
				logger.error("ManagementAction.kuangdelete() 发生异常", e);
			}
			
		}
		logger.info("ManagementAction.kuangdelete()方法end");
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
			logger.error("ManagementAction.kuangdelete() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("ManagementAction.kuangdelete() 发生异常", e);
			e.printStackTrace();
		}
	}
	/**
	 * 跳转到系统筐详情页面
	 * @author zzg
	 * @date 2015-11-02
	 * @param request
	 * @param response
	 * @param logintype
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "admin/kuanginfo")
	public String kuanginfo(HttpServletRequest request,
			HttpServletResponse response,String logintype,
			@RequestParam("code") String code) throws Exception {
		logger.info("ManagementAction.kuanginfo()方法开始");
		//属于这个筐的用户
		List<SysUserInfo> inlist=null;
		//不属于这个筐的用户
		List<SysUserInfo> outlist=null;
		//筐详情
		HashMap<String, Object> kuanginfo = new HashMap<String, Object>();
		//所有权限list
		List<Map<String, String>> juilist=new ArrayList<Map<String,String>>();
		//筐的权限list
		List<SysWadJuri> wadjurilist=null;
		HashMap<String,Object> map =new HashMap<String, Object>();
		map.put("code", code);
		map.put("admin", CommonUtil.findNoteTxtOfXML("ADMIN-CODE"));
		try {
			inlist=SysUserInfoService.userbyKcode(map);
			outlist=SysUserInfoService.usernoinKcode(map);
			kuanginfo=SysUserWadService.wadinfobycode(code);
			wadjurilist=SysUserWadService.wad_juri(code);
			/*duwenjie 添加判断查看会议超级权限*/
			if (code.equals(CommonUtil.findNoteTxtOfXML("JURI-ALL"))) {
				//超级权限
				Map<String, String> juimap=new HashMap<String, String>();
				juimap.put("sys_juri_code", CommonUtil.findNoteTxtOfXML("JURI-ALL"));
				juimap.put("sys_juri_name", "超级权限");
				juimap.put("checked", "checked");
				juimap.put("disabled", "disabled");
				juilist.add(juimap);
				
			}else if(code.equals(CommonUtil.findNoteTxtOfXML("JURI-PUBLIC"))) {
				//查看会议超级权限
				juilist=SysUserWadService.juList(code);
				for (int i = 0; i < juilist.size(); i++) {
					for (int j = 0; j < wadjurilist.size(); j++) {
						if (wadjurilist.get(j).getSys_juri_code().equals(juilist.get(i).get("sys_juri_code"))) {
							juilist.get(i).put("checked", "checked");
							break;
						}
					}
					juilist.get(i).put("disabled", "disabled");
				}
				
				Map<String, String> juimap=new HashMap<String, String>();
				juimap.put("sys_juri_code", CommonUtil.findNoteTxtOfXML("JURI-PUBLIC"));
				juimap.put("sys_juri_name", "查看会议超级权限");
				juimap.put("checked", "checked");
				juimap.put("disabled", "disabled");
				juilist.add(juimap);
			}else {
				juilist=SysUserWadService.juList(code);
				for (int i = 0; i < juilist.size(); i++) {
					for (int j = 0; j < wadjurilist.size(); j++) {
						if (wadjurilist.get(j).getSys_juri_code().equals(juilist.get(i).get("sys_juri_code"))) {
							juilist.get(i).put("checked", "checked");
							break;
						}
					}
				}
			}
			
			
		} catch (Exception e) {
			logger.error("ManagementAction.kuanginfo() 发生异常", e);
			e.printStackTrace();
		}
		request.setAttribute("kuanginfo", kuanginfo);
		request.setAttribute("inlist", inlist);
		request.setAttribute("outlist", outlist);
		request.setAttribute("juilist", juilist);
		String Url=ConstantUrl.kuanginfo(logintype);
		logger.info("ManagementAction.kuanginfo()方法结束");
		return Url;
	}
	/**
	 * 修改系筐详情
	 * @author zzg
	 * @date 2015-11-03
	 * @param request
	 * @param response
	 * @param logintype
	 * @param code
	 * @throws Exception
	 */
	@RequestMapping(value = "admin/kuangsubmitinfo")
	public void kuangsubmitinfo(HttpServletRequest request,
			HttpServletResponse response,String logintype,
			@RequestParam("code") String code,
			@RequestParam("name") String name,
			@RequestParam("quxlist") String quxlist,
			@RequestParam("olddata") String olddata,
			@RequestParam("newdata") String newdata,
			@RequestParam("peolist") String peolist) throws Exception {
		logger.info("ManagementAction.kuangsubmitinfo()方法开始");
		String message="error";	
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		JSONArray quxiArray=JSONArray.fromObject(quxlist);
		JSONArray peoArray=JSONArray.fromObject(peolist);
		JSONArray oldArray=JSONArray.fromObject(olddata);
		JSONArray newArray=JSONArray.fromObject(newdata);
		
		try {
			List<SysWadJuri> sysWadJuris=	kuangsubmitinfo_qx(quxiArray,code,request);
			List<SysUserWad> sysUserWads=kuangsubmitinfo_peo(peoArray,code,request);
			HashMap<String, Object> map=new HashMap<String, Object>();
			map.put("code", code);
			map.put("name", name);
			map.put("updid", userInfo.getSys_user_code());
			map.put("updtime", new Timestamp(new Date().getTime()));
			if (code.equals(CommonUtil.findNoteTxtOfXML("JURI-ALL"))
					||code.equals(CommonUtil.findNoteTxtOfXML("JURI-PUBLIC"))) {
				map.put("name", oldArray.get(0).toString());
			}
		boolean b=	SysUserWadService.tranModifywadallinfo(map, sysWadJuris, sysUserWads);
		if (b) {
			/* 添加系统更新记录 */
			Timestamp time = new Timestamp(new Date().getTime());
			baseUpdlogInfoService.insertUpdlogInfo(
					CommonUtil.findNoteTxtOfXML("Lable-syskuang"),
					CommonUtil.findNoteTxtOfXML("Lable-syskuang-name"),
					code,
					oldArray.getString(0),
					CommonUtil.OPERTYPE_YK,
					userInfo.getSys_user_code(), 
					userInfo.getSys_user_name(),
					CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
					CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
					CommonUtil.findNoteTxtOfXML("updatasystkuang"),
					"名称:"+oldArray.get(0).toString()+";权限:"+oldArray.get(1).toString()+";人员:"+oldArray.get(2).toString(),
					"修改为（名称:"+newArray.get(0).toString()+";权限:"+newArray.get(1).toString()+";人员:"+newArray.get(2).toString()+")",
					logintype,
					userInfo.getSys_user_code(),
					time,
					userInfo.getSys_user_code(), 
					time);
			message="success";
		}
			
		} catch (Exception e) {
			logger.error("ManagementAction.kuangsubmitinfo() 发生异常", e);
			e.printStackTrace();
		}
		
		
		logger.info("ManagementAction.kuangsubmitinfo()方法end");
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
			logger.error("ManagementAction.kuangsubmitinfo() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("ManagementAction.kuangsubmitinfo() 发生异常", e);
			e.printStackTrace();
		}
	}
	/**
	 * 修改系统筐权限
	 * @author zzg
	 * @date 2015-11-03
	 * @param quxiArray
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<SysWadJuri> kuangsubmitinfo_qx(JSONArray quxiArray,String code,HttpServletRequest request)throws Exception {
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		List<SysWadJuri> sysWadJuris=new ArrayList<SysWadJuri>();
		try {

			for (int i = 0; i < quxiArray.size(); i++) {
				SysWadJuri wadJuri=new SysWadJuri();
				wadJuri.setCreateid(userInfo.getSys_user_code());
				wadJuri.setCreatetime(new Timestamp(new Date().getTime()));
				wadJuri.setSys_wad_code(code);
				wadJuri.setSys_juri_code(quxiArray.getString(i));
				sysWadJuris.add(wadJuri);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysWadJuris;
	}
	/**
	 * 修改系统筐下所属人员
	 * @author zzg
	 * @date 2015-11-03
	 * @param peoArray
	 * @param code
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<SysUserWad>  kuangsubmitinfo_peo(JSONArray peoArray,String code,HttpServletRequest request)throws Exception {
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		List<SysUserWad> SysUserWads=new ArrayList<SysUserWad>();
		try {
	
			for (int i = 0; i < peoArray.size(); i++) {
				SysUserWad sysUserWad=new SysUserWad();
				sysUserWad.setCreateid(userInfo.getSys_user_code());
				sysUserWad.setSys_user_code(peoArray.getString(i));
				sysUserWad.setCreatetime(new Timestamp(new Date().getTime()));
				sysUserWad.setSys_wad_code(code);
				SysUserWads.add(sysUserWad);
			}
			if (code.equals( CommonUtil.findNoteTxtOfXML("JURI-ALL"))) {
				SysUserWad sysUserWad=new SysUserWad();
				sysUserWad.setCreateid(userInfo.getSys_user_code());
				sysUserWad.setSys_user_code(CommonUtil.findNoteTxtOfXML("ADMIN-CODE"));
				sysUserWad.setCreatetime(new Timestamp(new Date().getTime()));
				sysUserWad.setSys_wad_code(code);
				SysUserWads.add(sysUserWad);
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysUserWads;
	}
	/**
	 * 添加系统筐(此接口作废)
	 * @author zzg
	 * @date 2015-11-03
	 * @param request
	 * @param response
	 * @param logintype
	 * @param name
	 * @throws Exception
	 */
	@RequestMapping(value = "admin/kuangaddaction")
	public void kuangadd(HttpServletRequest request,
			HttpServletResponse response,String logintype,
			@RequestParam("name") String name) throws Exception {
		logger.info("ManagementAction.kuangadd()方法开始");
		String message="error";
		SysWadInfo sysWadInfo=new SysWadInfo();
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		String code=CommonUtil.USER_WAD+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.USERWAD_TYPE);//生成一个投资机构code
		sysWadInfo.setCreateid(userInfo.getSys_user_code());
		sysWadInfo.setSys_wad_name(name);
		sysWadInfo.setDeleteflag("0");
		sysWadInfo.setCreatetime(new Timestamp(new Date().getTime()));
		sysWadInfo.setSys_wad_type("1");
		sysWadInfo.setSys_wad_code(code);
		try {
		Boolean b=	SysUserWadService.addwadinfo(sysWadInfo);
		if (b) {
			Timestamp time = new Timestamp(new Date().getTime());
			baseUpdlogInfoService.insertUpdlogInfo(
					CommonUtil.findNoteTxtOfXML("Lable-syskuang"),
					CommonUtil.findNoteTxtOfXML("Lable-syskuang-name"),
					code,
					"",
					CommonUtil.OPERTYPE_YK,
					userInfo.getSys_user_code(), 
					userInfo.getSys_user_name(),
					CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
					CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
					CommonUtil.findNoteTxtOfXML("addsystkuang"),
					"",
					"添加["+name+"]",
					logintype,
					userInfo.getSys_user_code(),
					time,
					userInfo.getSys_user_code(), 
					time);
			message="success";
		}
			
		} catch (Exception e) {
			logger.error("ManagementAction.kuangadd() 发生异常", e);
			e.printStackTrace();
		}
		logger.info("ManagementAction.kuangadd()方法结束");
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
			logger.error("ManagementAction.kuangadd() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("ManagementAction.kuangadd() 发生异常", e);
			e.printStackTrace();
		}
	}
	/**
	 * 跳转到添加筐页面
	 * @author zzg
	 * @date 2015-11-05
	 * @param request
	 * @param response
	 * @param logintype
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "admin/kuangadd")
	public String kuangadd(HttpServletRequest request,
			HttpServletResponse response,String logintype) throws Exception {
		logger.info("ManagementAction.kuangadd()方法Start");
		//所有权限list
				List<Map<String, String>> juilist=new ArrayList<Map<String,String>>();
				//不属于这个筐的用户
				List<SysUserInfo> outlist=null;
		try {
			juilist=SysUserWadService.juList("");
			outlist=SysUserInfoService.addwad_alluser(CommonUtil.findNoteTxtOfXML("ADMIN-CODE"));
		} catch (Exception e) {
			logger.error("ManagementAction.kuangadd()方法异常",e);
			e.printStackTrace();
		}
		logger.info("ManagementAction.kuangadd()方法end");
		request.setAttribute("juilist", juilist);
		request.setAttribute("outlist", outlist);
		String Url=ConstantUrl.kuangadd(logintype);
		return Url;
	}
	/**
	 * 添加筐及人员 权限
	 * @date 2015-11-05
	 * @author zzg
	 * @param request
	 * @param response
	 * @param logintype
	 * @param name
	 * @param quxlist
	 * @param newdata
	 * @param peolist
	 * @throws Exception
	 */
	@RequestMapping(value = "admin/kuangaddinfo")
	public void kuangaddinfo(HttpServletRequest request,
			HttpServletResponse response,String logintype,
			@RequestParam("name") String name,
			@RequestParam("quxlist") String quxlist,
			@RequestParam("newdata") String newdata,
			@RequestParam("peolist") String peolist) throws Exception {
		logger.info("ManagementAction.kuangaddinfo()方法开始");
		String message="error";	
		SysUserInfo userInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
	
		try {
			JSONArray quxiArray=JSONArray.fromObject(quxlist);
			JSONArray peoArray=JSONArray.fromObject(peolist);
			JSONArray newArray=JSONArray.fromObject(newdata);
			List<SysWadInfo> have=SysUserWadService.wadlistbyname(name);
			if (have.size()>0) {
				message="exist";
			}else {
				//筐信息
				SysWadInfo sysWadInfo=new SysWadInfo();
				String code=CommonUtil.USER_WAD+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.USERWAD_TYPE);//生成一个投资机构code
				Timestamp timestamp=	new Timestamp(new Date().getTime());
				sysWadInfo.setCreateid(userInfo.getSys_user_code());
				sysWadInfo.setSys_wad_name(name);
				sysWadInfo.setDeleteflag("0");
				sysWadInfo.setCreatetime(timestamp);
				sysWadInfo.setSys_wad_type("1");
				sysWadInfo.setSys_wad_code(code);
				//筐权限
				List<SysWadJuri> sysWadJuris=	kuangsubmitinfo_qx(quxiArray,code,request);
				//筐人员
				List<SysUserWad> sysUserWads=kuangsubmitinfo_peo(peoArray,code,request);
				Boolean b=SysUserWadService.tranSavewadinfo(sysWadInfo, sysWadJuris, sysUserWads);
				if (b) {
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-syskuang"),
							CommonUtil.findNoteTxtOfXML("Lable-syskuang-name"),
							code,
							"",
							CommonUtil.OPERTYPE_YK,
							userInfo.getSys_user_code(), 
							userInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
							CommonUtil.findNoteTxtOfXML("addsystkuang"),
							"",
							"添加筐（名称:"+newArray.get(0).toString()+";权限:"+newArray.get(1).toString()+";人员:"+newArray.get(2).toString()+")",
							logintype,
							userInfo.getSys_user_code(),
							timestamp,
							userInfo.getSys_user_code(), 
							timestamp);
					message="success";
				}
			}
			
		
		} catch (Exception e) {
			logger.error("ManagementAction.kuangaddinfo()方法异常",e);
		}
		logger.info("ManagementAction.kuangaddinfo()方法结束");
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
			logger.error("ManagementAction.kuangaddinfo() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("ManagementAction.kuangaddinfo() 发生异常", e);
			e.printStackTrace();
		}
	}
	/**
	 * 根据姓名模糊匹配所有员工 （不包含admin）
	 * @author zzg
	 * @date 2015-11-11
	 * @param request
	 * @param response
	 * @param logintype
	 * @param name
	 * @throws Exception 
	 */
	@RequestMapping(value = "userbyname")
	public void userbyname(HttpServletRequest request,
			HttpServletResponse response,String logintype,
			@RequestParam("name") String name,Page page) throws Exception{
		logger.info("ManagementAction.userbyname()方法开始");
		String message="error";
		int list_total=0;
		List<SysUserInfo> list=null;
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("name", name);
		map.put("admin", CommonUtil.findNoteTxtOfXML("ADMIN-CODE"));
		try {
			list_total=SysUserInfoService.userbyname_num(map);
			page.setTotalCount(list_total);
			map.put("start", page.getStartCount());
			map.put("size", page.getPageSize());
			list=SysUserInfoService.userbyname(map);
			if (list.size()>0) {
				message="success";
			}else {
				message="nomore";
			}
		} catch (Exception e) {
			logger.error("ManagementAction.userbynames() 发生异常", e);
		}
		logger.info("ManagementAction.userbyname()方法结束");
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("list", JSONArray.fromObject(list));
			resultJSON.put("page", new JSONObject(page));
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("ManagementAction.userbynames() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("ManagementAction.userbynames() 发生异常", e);
			e.printStackTrace();
		}
	}
	/**删除用户（逻辑删除）
	 * @author zzg
	 * @date 2015-11-11
	 * @param request
	 * @param response
	 * @param logintype
	 * @param code
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteuser")
	public void deleteuser(HttpServletRequest request,
			HttpServletResponse response,String logintype,
			@RequestParam("code") String code) throws Exception{
		logger.info("ManagementAction.deleteuser()方法开始");
		String message="error";
		SysUserInfo sInfo=(SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		Timestamp timestamp=	new Timestamp(new Date().getTime());
		if (sInfo.getSys_user_code().equals(code)) {
			message="loginuser";
		}else {
			try {
				SysUserInfo userInfo=SysUserInfoService.sysuserbycode(code);
				userInfo.setDeleteflag("1");
				userInfo.setUpdid(sInfo.getSys_user_code());
				userInfo.setUpdtime(timestamp);
				
				Boolean b=SysUserInfoService.tranDeleteuser(userInfo);
				if (b) {
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-sysuser"),
							CommonUtil.findNoteTxtOfXML("Lable-sysuser-name"),
							code,
							"",
							CommonUtil.OPERTYPE_YK,
							sInfo.getSys_user_code(), 
							sInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-DELE"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-DELE>"), 
							CommonUtil.findNoteTxtOfXML("deletesysuser"),
							"删除["+userInfo.getSys_user_name()+"]",
							"",
							logintype,
							sInfo.getSys_user_code(),
							timestamp,
							sInfo.getSys_user_code(), 
							timestamp);
					message="success";
				}
				
			} catch (Exception e) {
				logger.error("ManagementAction.deleteuser() 发生异常", e);
			}
		}
		logger.info("ManagementAction.deleteuser()方法结束");
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
			logger.error("ManagementAction.deleteuser() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("ManagementAction.deleteuser() 发生异常", e);
			e.printStackTrace();
		}
	}
	/**
	 * 添加员工
	 * @author zzg
	 * @date 2015-11-11
	 * @param request
	 * @param response
	 * @param logintype
	 * @param name
	 * @param en_name
	 * @param email
	 * @param weichat
	 * @throws Exception
	 */
	@RequestMapping(value = "useradd")
	public void useradd(HttpServletRequest request,
			HttpServletResponse response,String logintype,
			@RequestParam("name") String name,
			@RequestParam("en_name") String en_name,
			@RequestParam("email") String email,
			@RequestParam("phone") String phone,
			@RequestParam("weichat") String weichat) throws Exception{
		logger.info("ManagementAction.useradd()方法开始");
		String message="error";
		try {
			if (SysUserInfoService.sysuserbyemail(email).size()>0) {
				message="email_exist";
			}else {
				SysUserInfo sInfo=(SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
				SysUserInfo adduser=new SysUserInfo();
				Timestamp timestamp=	new Timestamp(new Date().getTime());
				String code=CommonUtil.USER+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.USER_TYPE);
				adduser.setCreateid(sInfo.getSys_user_code());
				adduser.setCreatetime(timestamp);
				adduser.setDeleteflag("0");
				adduser.setSys_user_code(code);
				adduser.setSys_user_name(name);
				adduser.setSys_user_ename(en_name);
				adduser.setSys_user_email(email);
				adduser.setSys_user_phone(phone);
				adduser.setSys_user_wechatnum(weichat);
				adduser.setUpdtime(timestamp);
				adduser.setUpdid(sInfo.getSys_user_code());
				// 生成随机字串
				String verifyCode = VerifyCodeUtils.generateVerifyCode(6);
				adduser.setSys_user_paw(verifyCode);
				
				Boolean b= SysUserInfoService.adduser(adduser);
				if (b) {
					message="success";
					List<String> mailList = new ArrayList<String>(); /* 存储邮箱地址 */
					List<EmailBean> mailBeanList = new ArrayList<EmailBean>();/* 邮箱中的变量 */
					EmailBean emailBean = new EmailBean();
					mailList.add(email);
					emailBean.setAccountName(name);
					emailBean.setNewpaw(verifyCode);
					emailBean.setUseremail(email);
					mailBeanList.add(emailBean);
					emailSenderService.sendEmail(mailList, mailBeanList,
							"Newuser.ftl");
					baseUpdlogInfoService.insertUpdlogInfo(
							CommonUtil.findNoteTxtOfXML("Lable-sysuser"),
							CommonUtil.findNoteTxtOfXML("Lable-sysuser-name"),
							code,
							"",
							CommonUtil.OPERTYPE_YK,
							sInfo.getSys_user_code(), 
							sInfo.getSys_user_name(),
							CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
							CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
							CommonUtil.findNoteTxtOfXML("addsysuser"),
							"",
							"添加["+name+"]",
							logintype,
							sInfo.getSys_user_code(),
							timestamp,
							sInfo.getSys_user_code(), 
							timestamp);	
				}
				
			}
		} catch (Exception e) {
			logger.error("ManagementAction.useradd() 发生异常", e);
			e.printStackTrace();
		}
		logger.info("ManagementAction.useradd()方法结束");
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
			logger.error("ManagementAction.useradd() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("ManagementAction.useradd() 发生异常", e);
			e.printStackTrace();
		}
	}
	/**
	 * 跳转到员工详情
	 * @param request
	 * @param response
	 * @param logintype
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "admin/employee_info")
	public String employee_info(HttpServletRequest request,
			HttpServletResponse response,String logintype,String code) throws Exception {
		logger.info("ManagementAction.employee_info()方法Start");
		SysUserInfo sysUserInfo=null;
		List<SysUserWad> wads=null;
		try {
			wads=SysUserWadService.sysuserwadbyuserid(code);
			 sysUserInfo=SysUserInfoService.sysuserbycode(code);
		} catch (Exception e) {
			logger.error("ManagementAction.employee_info()方法异常",e);
			e.printStackTrace();
		}
		logger.info("ManagementAction.employee_info()方法end");
		request.setAttribute("sysUserInfo", sysUserInfo);
		request.setAttribute("wads", wads);
		String Url=ConstantUrl.employee_info(logintype);
		return Url;
	}
	/**
	 * 修改员工基本信息
	 * @param request
	 * @param response
	 * @param logintype
	 * @param userid
	 * @param name
	 * @param en_name
	 * @param newdata
	 * @param olddata
	 * @param email
	 * @param phone
	 * @param weichat
	 * @throws Exception
	 */
	@RequestMapping(value = "admin/changeuerbaseinfo")
	public void changeuerbaseinfo(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam(value="code",required=false)String userid,
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="en_name",required=false)String en_name,
			@RequestParam(value="newdata",required=false)String newdata,
			@RequestParam(value="olddata",required=false)String olddata,
			@RequestParam(value="email",required=false)String email,
			@RequestParam(value="phone",required=false)String phone,
			@RequestParam(value="weichat",required=false)String weichat) throws Exception {
		logger.info("UserAction.changeuerbaseinfo()方法Start");
		String message="error";
		SysUserInfo sInfo=(SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		SysUserInfo sysUserInfo=null;
		Timestamp timestamp=	new Timestamp(new Date().getTime());
		System.out.println(olddata);
		try {
			sysUserInfo=SysUserInfoService.sysuserbycode(userid);
			if (sysUserInfo.getSys_user_code()==null) {
				message="nouser";
			}else {
				sysUserInfo.setSys_user_email(email==null?sysUserInfo.getSys_user_email():email);
				sysUserInfo.setSys_user_name(name==null?sysUserInfo.getSys_user_name():name);
				sysUserInfo.setSys_user_ename(en_name==null?sysUserInfo.getSys_user_ename():en_name);
				sysUserInfo.setSys_user_wechatnum(weichat==null?sysUserInfo.getSys_user_wechatnum():weichat);
				sysUserInfo.setSys_user_phone(phone==null?sysUserInfo.getSys_user_phone():phone);
				sysUserInfo.setUpdid(sInfo.getSys_user_code());
				sysUserInfo.setUpdtime(timestamp);
				SysUserInfoService.changeuserinfo(sysUserInfo);
				if (sInfo.getSys_user_code().equals(sysUserInfo.getSys_user_code())) {
					UserAction userAction=new UserAction();
					userAction.loginSession(request.getSession(),sysUserInfo);
				}
				baseUpdlogInfoService.insertUpdlogInfo(
						CommonUtil.findNoteTxtOfXML("Lable-sysuser"),
						CommonUtil.findNoteTxtOfXML("Lable-sysuser-name"),
						userid,
						"",
						CommonUtil.OPERTYPE_YK,
						sInfo.getSys_user_code(), 
						sInfo.getSys_user_name(),
						CommonUtil.findNoteTxtOfXML("CODE-YK-UPD"),
						CommonUtil.findNoteTxtOfXML("CONTENT-YK-UPD"), 
						CommonUtil.findNoteTxtOfXML("updatasysuser"),
						olddata,
						newdata,
						logintype,
						sInfo.getSys_user_code(),
						timestamp,
						sInfo.getSys_user_code(), 
						timestamp);	
			}
			
			message="success";
		} catch (Exception e) {
			logger.error("ManagementAction.changeuerbaseinfo() 发生异常", e);
			e.printStackTrace();
		}
		logger.info("ManagementAction.changeuerbaseinfo()方法结束");
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
			logger.error("ManagementAction.changeuerbaseinfo() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("ManagementAction.changeuerbaseinfo() 发生异常", e);
			e.printStackTrace();
		}
	}
	/**
	 * 跳转到标签管理
	 * @param request
	 * @param response
	 * @param logintype
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "admin/tag_manage")
	public String tag_manage(HttpServletRequest request,
			HttpServletResponse response,String logintype) throws Exception {
		HashMap<String, String> map=new LinkedHashMap<String, String>();
		map.put(CommonUtil.findNoteTxtOfXML("Lable-bask-name"), CommonUtil.findNoteTxtOfXML("Lable-bask"));
//		map.put(CommonUtil.findNoteTxtOfXML("Lable-indu-name"), CommonUtil.findNoteTxtOfXML("Lable-indu"));
		map.put(CommonUtil.findNoteTxtOfXML("Lable-comindu-name"), CommonUtil.findNoteTxtOfXML("Lable-comindu"));
		map.put(CommonUtil.findNoteTxtOfXML("Lable-orgindu-name"), CommonUtil.findNoteTxtOfXML("Lable-orgindu"));
		
//		map.put(CommonUtil.findNoteTxtOfXML("Lable-payatt-name"), CommonUtil.findNoteTxtOfXML("Lable-payatt"));
//		map.put(CommonUtil.findNoteTxtOfXML("Lable-stage-name"), CommonUtil.findNoteTxtOfXML("Lable-stage"));
		map.put(CommonUtil.findNoteTxtOfXML("Lable-comstage-name"), CommonUtil.findNoteTxtOfXML("Lable-comstage"));
		map.put(CommonUtil.findNoteTxtOfXML("Lable-trastage-name"), CommonUtil.findNoteTxtOfXML("Lable-trastage"));
		
		map.put(CommonUtil.findNoteTxtOfXML("Lable-currency-name"), CommonUtil.findNoteTxtOfXML("Lable-currency"));
		map.put(CommonUtil.findNoteTxtOfXML("Lable-feature-name"), CommonUtil.findNoteTxtOfXML("Lable-feature"));
		map.put(CommonUtil.findNoteTxtOfXML("Lable-type-name"), CommonUtil.findNoteTxtOfXML("Lable-type"));
		map.put(CommonUtil.findNoteTxtOfXML("Lable-bground-name"), CommonUtil.findNoteTxtOfXML("Lable-bground"));
		map.put(CommonUtil.findNoteTxtOfXML("Lable-currency-rnb-name"), CommonUtil.findNoteTxtOfXML("Lable-currency-rnb"));
		map.put(CommonUtil.findNoteTxtOfXML("Lable-currency-usd-name"), CommonUtil.findNoteTxtOfXML("Lable-currency-usd"));
		//date:2015-11-30 author:zzg option:add Task:070
		map.put(CommonUtil.findNoteTxtOfXML("Lable-investage-name"), CommonUtil.findNoteTxtOfXML("Lable-investage"));
		//date:2015-11-30 author:zzg option:addend Task:069
		
		map.put(CommonUtil.findNoteTxtOfXML("Lable-meetingtype-name"), CommonUtil.findNoteTxtOfXML("Lable-meetingtype"));
		
		String Url=ConstantUrl.tag_manage(logintype);
		request.setAttribute("labs", map);
		return Url;
	}
	/**
	 * 标签管理列表
	 * @param request
	 * @param response
	 * @param logintype
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "admin/tag_managelist")
	public void tag_managelist(HttpServletRequest request,
			HttpServletResponse response,String logintype,String code) throws Exception {
		logger.info("UserAction.tag_managelist()方法Start");
		String message="error";
		List<Map<String, String>> list=null;//字典 
		try {
			if (code.equals(CommonUtil.findNoteTxtOfXML("Lable-scale"))) {
				list=dic.findAllCurrencyChild();//规模
			}else {
				list=dic.findDIC(code);
			}
	
			message="success";
		} catch (Exception e) {
			logger.error("ManagementAction.tag_managelist() 发生异常", e);
		}
		logger.info("ManagementAction.tag_managelist()方法结束");
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("list", list);
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("ManagementAction.tag_managelist() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("ManagementAction.tag_managelist() 发生异常", e);
			e.printStackTrace();
		}
	}
	/**
	 * 更改标签信息
	 * @param request
	 * @param response
	 * @param logintype
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "admin/edittag_manage")
	public void edittag_manage(HttpServletRequest request,
			HttpServletResponse response,String logintype,String code,String name) throws Exception {
		logger.info("ManagementAction.edittag_manage()方法开始");
		String message="error";
		SysLabelelementInfo lable=null;
		try {
			lable=dic.lablebycode(code);
			if (lable.getSys_label_code()==null) {
				message="nolable";
			}else if(lable.getSys_labelelement_name().equals(name)) {
				message="nochange";
			}else {
				SysUserInfo sInfo=(SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
				lable.setSys_labelelement_name(name);
				lable.setUpdid(sInfo.getSys_user_code());
				lable.setUpdtime(new Timestamp(new Date().getTime()));
				dic.updatalable(lable);
				message="success";
			}
		} catch (Exception e) {
			logger.error("ManagementAction.edittag_manage() 发生异常", e);
			e.printStackTrace();
		}
		logger.info("ManagementAction.edittag_manage()方法结束");

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
			logger.error("ManagementAction.edittag_manage() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("ManagementAction.edittag_manage() 发生异常", e);
			e.printStackTrace();
		}
	}
	/**
	 * 添加标签信息
	 * @param request
	 * @param response
	 * @param logintype
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "admin/add_tag")
	public void add_tag(HttpServletRequest request,
			HttpServletResponse response,String logintype,String code,String name) throws Exception {
		logger.info("ManagementAction.add_tag()方法结束");
		String message="error";
		HashMap<String, String> map =new HashMap<String, String>();
		map.put("code", code);
		map.put("name", name);
		try {
			if (dic.lablebysys_lable_codeandname(map)!=null) {
				message="nameexist";
			}else {
				 // 取当前标签的最大值
			    String maxlablecode = iimportitdataservice
				    .querymaxlabelelementbycode(code);
			    int codeint;
				if (maxlablecode.lastIndexOf("0") >= 0) {
				    codeint = Integer.valueOf(maxlablecode.substring(maxlablecode.lastIndexOf("0"))) + 1;
				} else {
				    codeint = Integer.valueOf(maxlablecode.substring(maxlablecode.length() - 3)) + 1;
					}
				String labelelementcode = maxlablecode.substring(0,maxlablecode.length()
						- String.valueOf(codeint).length())
					+ String.valueOf(codeint);
				SysUserInfo sInfo=(SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
				SysLabelelementInfo lable=	new SysLabelelementInfo();
				/*赋值排序标识*/
				lable.setLabel_index(dic.findLabelCountByLabelcode(code));
				
				lable.setSys_labelelement_name(name);
				lable.setCreateid(sInfo.getSys_user_code());
				lable.setCreatetime(new Timestamp(new Date().getTime()));
				lable.setDeleteflag("0");
				lable.setSys_labelelement_state("0");
				lable.setSys_label_code(code);
				lable.setSys_labelelement_code(labelelementcode);
				lable.setUpdid(sInfo.getSys_user_code());
				lable.setUpdtime(new Timestamp(new Date().getTime()));
				dic.insertsyslabelementinfo(lable);
				message="success";
			}
			
		} catch (Exception e) {
			logger.error("ManagementAction.add_tag() 发生异常", e);
		}
		logger.info("ManagementAction.add_tag()方法结束");

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
			logger.error("ManagementAction.add_tag() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("ManagementAction.add_tag() 发生异常", e);
			e.printStackTrace();
		}
	   
	}
	
	/**
	 * 修改排序标签
	 * @param request
	 * @param response
	 * @param elcode 标签元素code
	 * @param labelcode 标签code
	 * @param logintype
	 */
	@RequestMapping(value="admin/updateLabelIndex")
	public void updateLabelIndex(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="elcode",required=true)String elcode,
			@RequestParam(value="labelcode",required=true)String labelcode,
			@RequestParam(value="logintype",required=true)String logintype){
		Map<String, String> infomap=null;
		Map<String, Object> addMap=null;
		Map<String, Object> minMap=null;
		SysLabelelementInfo info=null;
		String message="success";
		List<Map<String, String>> list=null;//字典 
		try {
			infomap=new HashMap<String, String>();
			infomap.put("labelcode", labelcode);
			infomap.put("elcode", elcode);
			info=dic.findLabelNextLabel(infomap);
			if(info!=null){
				addMap = new HashMap<String, Object>();
				addMap.put("elcode", info.getSys_labelelement_code());
				
				minMap = new HashMap<String, Object>();
				minMap.put("elcode", elcode);
				minMap.put("sign", info.getLabel_index());
				
				info=dic.lablebycode(elcode);
				addMap.put("sign", info.getLabel_index());
				
				int data = dic.tranModifyUpdateLabelIndex(addMap, minMap);
				
				if(data==0){
					message="fail";
				}else{
					if (labelcode.equals(CommonUtil.findNoteTxtOfXML("Lable-scale"))) {
						list=dic.findAllCurrencyChild();//规模
					}else {
						list=dic.findDIC(labelcode);
					}
				}
			}else {
				message="nodata";
			}
			
			
		} catch (Exception e) {
			logger.error("ManagementAtion updateLabelIndex 方法异常",e);
			e.printStackTrace();
			message="error";
		}
		
		
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("list", list);
			out.println(resultJSON.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			message = "error";
			logger.error("ManagementAction.add_tag() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("ManagementAction.add_tag() 发生异常", e);
			e.printStackTrace();
		}
	}
	
	
}

