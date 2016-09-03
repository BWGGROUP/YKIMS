package cn.com.sims.action.investorDeatil;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import cn.com.sims.model.baseinvestmentinvestor.BaseInvestmentInvestor;
import cn.com.sims.model.baseinvestorinfo.BaseInvestorInfo;
import cn.com.sims.model.baseinvestorlabelinfo.BaseInvestorlabelInfo;
import cn.com.sims.model.baseinvestornoteinfo.BaseInvestornoteInfo;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;
import cn.com.sims.service.code.SerialNumberGeneratorService;
import cn.com.sims.service.investor.InvestorService;
import cn.com.sims.service.system.syslabelelementinfo.ISysLabelelementInfoService;

/** 
 * @author  yl
 * @date ：2015年11月5日 
 * 添加投资人
 */
@Controller
public class investorAddAction {
	@Resource
	InvestorService investorService;
	@Resource
	ISysLabelelementInfoService dic;// 字典
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;//系统更新记录
	@Resource
	SerialNumberGeneratorService serialNumberGeneratorService;//生成id
	private static final Logger logger = Logger
			.getLogger(investorAddAction.class);
	/**
	 * 跳转到投资人添加页面
	 * @param request
	 * @param logintype
	 * @param response
	 * @return
	 * @throws Exception
	 * @author yl
	 * @date 2015年11月5日
	 *
	 *--start
	 *date:2015-12-07 Task:076 author:duwenjie option:add 
	 *添加传参backherf字段
	 *--end
	 */
		@RequestMapping(value = "investor_add")
		public String investor_add(
				HttpServletRequest request,
				@RequestParam(value = "logintype", required = false) String logintype,
				@RequestParam(value = "investmentCode", required = false) String investmentCode,
				@RequestParam(value = "backherf", required = false) String backherf,
				HttpServletResponse response) throws Exception {
			// 字典 行业
			List<Map<String, String>> induList = null;
            String investmentname="";
			// 查询行业
			induList = dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-orgindu"));
     if(investmentCode!=null&&investmentCode!=""){
    	 investmentname=investorService.findInvestment(investmentCode);
            }
//			// 查询筐
//			baskList = dic.findDIC(CommonUtil.findNoteTxtOfXML("Lable-bask"));
			request.setAttribute("induList", JSONArray.fromObject(induList));
			request.setAttribute("investmentCode", investmentCode);
			request.setAttribute("investmentname", investmentname);
			request.setAttribute("backherf", backherf);
//			request.setAttribute("baskList", JSONArray.fromObject(baskList));
			String Url = ConstantUrl.investorAdd(logintype);
			System.out.print(Url);
			return Url;
		}
		/**
		 * 添加投资人
		 * @param request
		 * @param response
		 * @param page
		 * @param logintype
		 * @param name
		 * @param investment
		 * @param phone
		 * @param email
		 * @param wechat
		 * @param pattylist
		 * @param indulist
		 * @param noteInfo
		 * @throws Exception
		*@author yl
		*@date 2015年11月6日
		 */
		@RequestMapping(value = "addInvestorInfo")
		public void addInvestorInfo(HttpServletRequest request,
				HttpServletResponse response, Page page,
				@RequestParam(value="logintype",required=false)String logintype,
				@RequestParam("name") String name,@RequestParam("investment") String investment,
				@RequestParam("phone") String phone,@RequestParam("email") String email,
				@RequestParam("wechat") String wechat,@RequestParam("pattylist") String pattylist,
				@RequestParam("indulist") String indulist,
				BaseInvestornoteInfo noteInfo) throws Exception {

			logger.info("investorAddAction.addInvestorInfo()方法Start");
			
			BaseInvestorInfo info = new BaseInvestorInfo();
//			SysStoredProcedure sysStoredProcedure = new SysStoredProcedure();
			
//			Map<String,Object> compMap = new HashMap<String,Object>();
			List<BaseInvestmentInvestor> investmentinvestorInfoList=new ArrayList<BaseInvestmentInvestor>();
			
			
			List<BaseInvestorlabelInfo> labelList=new ArrayList<BaseInvestorlabelInfo>();
			SysUserInfo sysUserInfo=  (SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
			String message = "success";
			String investorcode="";

			try {
					investorcode=CommonUtil.PREFIX_INVESTOR+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.INVESTOR_TYPE);//生成一个公司code
					//获取投资人的拼音全拼,简称拼音首字母
					String[] pinYin=CommonUtil.getPinYin(name);
					/**
					 * 添加投资人信息
					 */
					info.setBase_investor_code(investorcode);
					info.setBase_investor_name(name);
					info.setBase_investor_phone(phone);
			        info.setBase_investor_email(email);
			        info.setBase_investor_wechat(wechat);
			        info.setBase_investor_stem("2");
			        info.setBase_investor_estate("2");
			        info.setBase_investor_namep(pinYin[0]);
			        info.setBase_investor_namepf(pinYin[1]);
					info.setBase_datalock_viewtype(0);
					info.setBase_datalock_pltype("0");
					info.setDeleteflag("0");
					info.setCreateid(sysUserInfo.getSys_user_code());
					info.setCreatetime(new Timestamp(new Date().getTime()));
					info.setUpdtime(info.getCreatetime());
					info.setUpdid(info.getCreateid());

						/**
						 * 添加投资人近期关注信息
						 */
					JSONArray pattyList=JSONArray.fromObject(pattylist);
					if(pattyList!=null && pattyList.size()>0){
						for (int i = 0; i < pattyList.size(); i++) {	
							net.sf.json.JSONObject jsonObject=pattyList.getJSONObject(i);
							BaseInvestorlabelInfo labelinfo = new BaseInvestorlabelInfo();
							labelinfo.setBase_investor_code(investorcode);
							labelinfo.setSys_labelelement_code(jsonObject.getString("code"));
							labelinfo.setSys_label_code("Lable-payatt");
							labelinfo.setDeleteflag("0");
							labelinfo.setCreateid(sysUserInfo.getSys_user_code());
							labelinfo.setCreatetime(new Timestamp(new Date().getTime()));
							labelinfo.setUpdid(labelinfo.getCreateid());
							labelinfo.setUpdtime(labelinfo.getCreatetime());
							labelList.add(labelinfo);
						}
					}
						/**
						 * 添加投资人行业信息
						 */
					JSONArray induList=JSONArray.fromObject(indulist);
					if(induList!=null && induList.size()>0){
						for (int i = 0; i < induList.size(); i++) {	
							net.sf.json.JSONObject jsonObject=induList.getJSONObject(i);
							BaseInvestorlabelInfo labelinfo = new BaseInvestorlabelInfo();
							labelinfo.setBase_investor_code(investorcode);
							labelinfo.setSys_labelelement_code(jsonObject.getString("code"));
							labelinfo.setSys_label_code("Lable-orgindu");
							labelinfo.setDeleteflag("0");
							labelinfo.setCreateid(sysUserInfo.getSys_user_code());
							labelinfo.setCreatetime(new Timestamp(new Date().getTime()));
							labelinfo.setUpdtime(labelinfo.getCreatetime());
							labelinfo.setUpdid(labelinfo.getCreateid());
							labelList.add(labelinfo);
						}
					}
						/**
						 * 添加投资人所属机构信息
						 */
						JSONArray investmentList=JSONArray.fromObject(investment);
						if(investmentList!=null && investmentList.size()>0){
							for (int i = 0; i < investmentList.size(); i++) {	
								net.sf.json.JSONObject jsonObject=investmentList.getJSONObject(i);
								
								BaseInvestmentInvestor investmentinvestorInfo = new BaseInvestmentInvestor();
									

								investmentinvestorInfo.setBase_investor_code(investorcode);
								investmentinvestorInfo.setBase_investment_code(jsonObject.getString("code"));
								if("在职".equals(jsonObject.getString("state"))){
									investmentinvestorInfo.setBase_investor_state("0");
									
								}else{
									investmentinvestorInfo.setBase_investor_state("1");
								}
								investmentinvestorInfo.setBase_investor_posiname(jsonObject.getString("posi"));
								investmentinvestorInfo.setCreateid(sysUserInfo.getSys_user_code());
								investmentinvestorInfo.setCreatetime(new Timestamp(new Date().getTime()));
								investmentinvestorInfo.setUpdid(sysUserInfo.getSys_user_code());
								investmentinvestorInfo.setUpdtime(investmentinvestorInfo.getCreatetime());
								investmentinvestorInfo.setDeleteflag("0");
								investmentinvestorInfoList.add(investmentinvestorInfo);
						}
						}
						/**
						 * 添加投资人备注
						 */
						if(noteInfo.getBase_invesnote_content()!=null&&noteInfo.getBase_invesnote_content().trim()!=""){
							//生成一个公司note code
							String notecode=CommonUtil.PREFIX_INVESTORNOTE+serialNumberGeneratorService.getCodeGeneratorstr(CommonUtil.STEPNUM, CommonUtil.INVESTORNOTE_TYPE);
							//添加创建者用户名
							noteInfo.setSys_user_name(sysUserInfo.getSys_user_name());
							//创建者id
							noteInfo.setCreateid(sysUserInfo.getSys_user_code());
							//创建时间
							noteInfo.setCreatetime(new Timestamp(new Date().getTime()));
							//投资人note code
							noteInfo.setBase_investornote_code(notecode);
							noteInfo.setBase_investor_code(investorcode);
							noteInfo.setUpdtime(noteInfo.getCreatetime());
							noteInfo.setUpdid(noteInfo.getCreateid());
							noteInfo.setDeleteflag("0");
							//添加公司note到数据库
//							int i=companyDetailService.insertCompNote(noteInfo);
						}
						int count=investorService.tranModifyInvestorInfo(info,labelList,investmentinvestorInfoList,noteInfo);
						if(count!=0){
							Timestamp time = new Timestamp(new Date().getTime());
							/*添加系统更新记录*/
							baseUpdlogInfoService.insertUpdlogInfo(
									CommonUtil.findNoteTxtOfXML("Lable-people"),
									CommonUtil.findNoteTxtOfXML("Lable-people-name"),
									info.getBase_investor_code(), 
									info.getBase_investor_name(),
									CommonUtil.OPERTYPE_YK,
									sysUserInfo.getSys_user_code(), 
									sysUserInfo.getSys_user_name(),
									CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
									CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
									CommonUtil.findNoteTxtOfXML("addInvestor"),
									"",
									"添加["+info.getBase_investor_name()+"]投资人",
									logintype,
									sysUserInfo.getSys_user_code(),
									time,
									sysUserInfo.getSys_user_code(),
									time);
//							if(entreList!=null && entreList.size()>0){
//								for (int i = 0; i < entreList.size(); i++) {	
//									net.sf.json.JSONObject jsonObject=entreList.getJSONObject(i);
//									/*添加系统更新记录*/
//									baseUpdlogInfoService.insertUpdlogInfo(
//											CommonUtil.findNoteTxtOfXML("Lable-company"),
//											CommonUtil.findNoteTxtOfXML("Lable-company-name"),
//											info.getBase_comp_code(), 
//											info.getBase_comp_name(),
//											CommonUtil.OPERTYPE_YK,
//											sysUserInfo.getSys_user_code(), 
//											sysUserInfo.getSys_user_name(),
//											CommonUtil.findNoteTxtOfXML("CODE-YK-ADD"),
//											CommonUtil.findNoteTxtOfXML("CONTENT-YK-ADD"), 
//											CommonUtil.findNoteTxtOfXML("addCompPeople"),
//											"",
//											"添加公司联系人[姓名:"+jsonObject.getString("name")+";职位:"+jsonObject.getString("posi")+";手机:"+jsonObject.getString("phone")+";邮箱:"+jsonObject.getString("email")+";微信:"+jsonObject.getString("wechat")+"]",
//											logintype,
//											sysUserInfo.getSys_user_code(),
//											time,
//											"",
//											time);
//								}
//								}
							message="success";
				}

				
				logger.info("investorAddAction.addInvestorInfo()方法end");
			} catch (Exception e) {
				message="error";
				logger.error("investorAddAction.addInvestorInfo()) 发生异常", e);
				e.printStackTrace();
			}
			
			PrintWriter out;
			response.setContentType("text/html; charset=UTF-8");
			try {
				out = response.getWriter();
				JSONObject resultJSON = new JSONObject();
				resultJSON.put("message", message);
				resultJSON.put("code", info.getBase_investor_code());
	/*			resultJSON.put("viewCompInfo", new JSONObject(viewCompInfo));
				resultJSON.put("version", version);*/
				out.println(resultJSON.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				message = "error";
				logger.error("investorAddAction.addInvestorInfo() 发生异常", e);
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("investorAddAction.addInvestorInfo() 发生异常", e);
				e.printStackTrace();
			}
		}
}
