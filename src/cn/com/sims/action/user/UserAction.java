package cn.com.sims.action.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import cn.com.sims.common.bean.EmailBean;
import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.common.commonUtil.ConstantUrl;
import cn.com.sims.common.commonUtil.VerifyCodeUtils;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.model.sysuserwad.SysUserWad;
import cn.com.sims.model.syswadjuri.SysWadJuri;
import cn.com.sims.service.sysuserinfo.SysUserInfoService;
import cn.com.sims.service.system.sysuserwad.SysUserWadService;
import cn.com.sims.util.email.IEmailSenderService;
/**
 * 
 * @author zzg
 *@date 20155-10-09
 */
@Controller
public class UserAction {
	private static final Logger logger = Logger
			.getLogger(UserAction.class);
	@Resource
	SysUserInfoService SysUserInfoService;
	@Resource
	SysUserWadService SysUserWadService;
	@Resource
	IEmailSenderService emailSenderService;//email 发送service
	/**跳转到登录
	 * @author zzg
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "loginhtml")
	public String loginhtml(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype) throws Exception {
		request.setAttribute("WUI", "");
		String Url=ConstantUrl.login(logintype);
		return Url;
	}
	/**登录验证（mobile）
	 * @author zzg
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "login")
	public void login(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("username")String username
			,@RequestParam("password")String password
			,@RequestParam("Verifycode")String Verifycode
			,@RequestParam(value="WCuserid",required=false)String WCuserid
			) throws Exception {
		logger.info("UserAction.login()方法Start");
		String message;
		if (!Verifycode.toLowerCase().equals(request.getSession().getAttribute("Verifycode"))) {
			message="codeerror";//验证码错误
		}else {
			List<SysUserInfo> list=null;
			try {
				
				list=SysUserInfoService.sysuserbyemail(username);
				if (list.size()==1) {//查到用户
					SysUserInfo s=list.get(0);
					if (s.getSys_user_paw().equals(password)) {//用户与密码匹配
						if (!"".equals(WCuserid)&&WCuserid!=null) {//存在微信授权后的userid
							s.setSys_user_wechatopen(WCuserid);
							int i=SysUserInfoService.bindweichat(s);
							if (i==1) {
								message="bind_success";//绑定成功
								//更改session中的值
								loginSession(request.getSession(),s);
							}else {
								message="binderror";//绑定失败
							}
						}else {
							message="success";//登录成功
							//更改session中的值
							loginSession(request.getSession(),s);
						}
					}else{
						message="pass_error";//密码错误
					}
				}else {
					message="name_erro";//用户名错误
				}
				logger.info("UserAction.login()方法end");
			} catch (Exception e) {
				message="error";
				logger.error("UserAction.login() 发生异常", e);
			}
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
			logger.error("UserAction.login() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("UserAction.login() 发生异常", e);
			e.printStackTrace();
		}
	}
	/**登录session处理
	 * @author zzg
	 * @return
	 * @date 2010-10-13
	 */
	public  HttpSession  loginSession(HttpSession session,SysUserInfo user) {
		logger.info("UserAction.loginSession()方法Start");
		List<SysUserWad> sysUserWadslisList;
try {
	sysUserWadslisList=SysUserWadService.sysuserwadbyuserid(user.getSys_user_code());
	for (int i = 0; i < sysUserWadslisList.size(); i++) {
		List<SysWadJuri> juris=SysUserWadService.wad_juri(sysUserWadslisList.get(i).getSys_wad_code());
		sysUserWadslisList.get(i).setSysWadJuri(juris);
	}
	user.setSysUserWadslisList(sysUserWadslisList);//添加用户筐
	session.setAttribute(CommonUtil.USER_INFO, user);
	logger.info("UserAction.loginSession()方法end");
} catch (Exception e) {
	logger.error("UserAction.loginSession() 发生异常", e);
	e.printStackTrace();
}
		return session;
	}

	/**
	 * 得到微信userid 进行绑定查询
	 * @author zzg
	 * @date 2010-10-21
	 */
	@RequestMapping(value = "check_userbind")
	public String check_userbind(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("userid")String userid,@RequestParam("state")String state
			,@RequestParam(value="logintype",required=false)String logintype) throws Exception {
		logger.info("UserAction.check_userbind()方法Start");
		List<SysUserInfo> list=null;
		String Url;
		try {
			list=SysUserInfoService.weixin_userid(userid);
			if (list.size()==1) {
				loginSession(request.getSession(),list.get(0));
				Url="redirect:"+state;
			}else {
				request.setAttribute("WUI", userid);
				 Url=ConstantUrl.login(logintype);
			}
			logger.info("UserAction.check_userbind()方法end");
		} catch (Exception e) {
			logger.error("UserAction.check_userbind() 发生异常", e);
			e.printStackTrace();
			request.setAttribute("WUI", userid);
			 Url=ConstantUrl.login(logintype);
		}
		return Url;
	}
	/**
	 * 生成验证码图片（返回图片流）
	 * @author zzg
	 * @date 2010-10-21
	 */
	@RequestMapping(value = "Verify_code")
	public void Verify_code(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
					response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/jpeg");
       //生成随机字串  
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);  
        //存入会话session  
        HttpSession session = request.getSession(true);  
        session.setAttribute("Verifycode", verifyCode.toLowerCase());  
        //生成图片  
        int w = 100, h = 40;  
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
	}
	/**
	 * 跳转修改用户信息页面
	 * @author zzg
	 * @date 2010-10-21
	 */
	@RequestMapping(value = "userinfo")
	public String userinfo(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype) throws Exception {
		logger.info("UserAction.userinfo()方法Start");
		SysUserInfo userInfo=(SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		request.setAttribute("userInfo", userInfo);
		String Url=ConstantUrl.userinfo(logintype);
		return Url;
	}
	/**
	 * 跳转修改用户信息页面
	 * @author zzg
	 * @date 2010-10-21
	 */
	@RequestMapping(value = "userpassword")
	public String userpassword(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype) throws Exception {
		logger.info("UserAction.userinfo()方法Start");
		SysUserInfo userInfo=(SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		request.setAttribute("userInfo", userInfo);
		String Url=ConstantUrl.userpasssword(logintype);
		return Url;
	}
	/**
	 * 用户退出登录（微信解绑）
	 * @author zzg
	 * @date 2010-10-29
	 */
	@RequestMapping(value = "logout")
	public String logout(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype) throws Exception {
		HttpSession session =request.getSession();
		SysUserInfo sInfo=(SysUserInfo) session.getAttribute(CommonUtil.USER_INFO);
		//如果是微信登录
		if (logintype.equals(CommonUtil.LOGINTYPEWX)) {
			sInfo.setSys_user_wechatopen(null);
		SysUserInfoService.bindweichat(sInfo);//解除绑定
		}
		session.removeAttribute(CommonUtil.USER_INFO);
		return "redirect:gotoSearchMain.html?logintype="+logintype;
	}
/**
 * 更改用户信息  适用个人信息修改和修改密码
* @author zzg
* @date 2015-11-06
 * @param request
 * @param response
 * @param logintype
 * @param userid
 * @param name
 * @param en_name
 * @param email
 * @param weichat
 * @throws Exception
 */
	@RequestMapping(value = "changeuerinfo")
	public void changeuerinfo(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="logintype",required=false)String logintype,
			@RequestParam(value="userid",required=false)String userid,@RequestParam(value="name",required=false)String name,
			@RequestParam(value="en_name",required=false)String en_name,
			@RequestParam(value="email",required=false)String email,
			@RequestParam(value="orpaw",required=false)String orpaw,
			@RequestParam(value="paw",required=false)String paw,
			@RequestParam(value="phone",required=false)String phone,
			@RequestParam(value="weichat",required=false)String weichat) throws Exception {
		logger.info("UserAction.changeuerinfo()方法Start");
		String message="error";
		SysUserInfo sInfo=(SysUserInfo) request.getSession().getAttribute(CommonUtil.USER_INFO);
		SysUserInfo sysUserInfo=SysUserInfoService.sysuserbycode(userid);
		if (sysUserInfo.getSys_user_code()==null) {
			message="nouser";
		}else if (orpaw!=null&&!sysUserInfo.getSys_user_paw().equals(orpaw)) {
			message="orpaw_error";
		}else {
			sysUserInfo.setSys_user_email(email==null?sysUserInfo.getSys_user_email():email);
			sysUserInfo.setSys_user_name(name==null?sysUserInfo.getSys_user_name():name);
			sysUserInfo.setSys_user_ename(en_name==null?sysUserInfo.getSys_user_ename():en_name);
			sysUserInfo.setSys_user_wechatnum(weichat==null?sysUserInfo.getSys_user_wechatnum():weichat);
			sysUserInfo.setSys_user_phone(phone==null?sysUserInfo.getSys_user_phone():phone);
			sysUserInfo.setSys_user_paw(paw==null?sysUserInfo.getSys_user_paw():paw);
			sysUserInfo.setUpdid(sInfo.getSys_user_code());
			sysUserInfo.setUpdtime(new Timestamp(new Date().getTime()));
			try {
				SysUserInfoService.changeuserinfo(sysUserInfo);
				if (sInfo.getSys_user_code().equals(sysUserInfo.getSys_user_code())) {
					loginSession(request.getSession(),sysUserInfo);
				}
				message="success";
			} catch (Exception e) {
				logger.error("UserAction.changeuerinfo()方法异常",e);
			}
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
			logger.error("UserAction.changeuerinfo() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("UserAction.changeuerinfo() 发生异常", e);
			e.printStackTrace();
		}
	}
	/**
	 * 找回密码
	 * @author zzg
	 * @date 2015-11-06
	 * @param request
	 * @param response
	 * @param logintype
	 * @param email
	 * @throws Exception
	 */
	@RequestMapping(value = "forgetpassword")
	public void forgetpassword(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "logintype", required = false) String logintype,
			@RequestParam(value = "emali", required = false) String email)
			throws Exception {
		logger.info("UserAction.forgetpassword()方法Start");
		String message = "error";
		try {
			List<SysUserInfo> userInfolist = SysUserInfoService
					.sysuserbyemail(email);
			if (userInfolist.size() == 0) {
				message = "nouser";
			} else {
				// 生成随机字串
				String verifyCode = VerifyCodeUtils.generateVerifyCode(6);
				userInfolist.get(0).setSys_user_paw(verifyCode);
				Boolean bo = SysUserInfoService.changeuserinfo(userInfolist
						.get(0));
				if (bo) {
					List<String> mailList = new ArrayList<String>(); /* 存储邮箱地址 */
					List<EmailBean> mailBeanList = new ArrayList<EmailBean>();/* 邮箱中的变量 */
					EmailBean emailBean = new EmailBean();
					if (userInfolist.get(0).getSys_user_code()
							.equals(CommonUtil.findNoteTxtOfXML("ADMIN-CODE"))) {
						emailBean.setUseremail(CommonUtil.findNoteTxtOfXML("MAIL_FROM"));
						mailList.add(CommonUtil.findNoteTxtOfXML("MAIL_FROM"));
					} else {
						emailBean.setUseremail(userInfolist.get(0)
								.getSys_user_email());
						mailList.add(userInfolist.get(0)
								.getSys_user_email());
					}
					emailBean.setAccountName(userInfolist.get(0)
							.getSys_user_name());
					emailBean.setNewpaw(verifyCode);
					mailBeanList.add(emailBean);
					emailSenderService.sendEmail(mailList, mailBeanList,
							"getCcnPass.ftl");
					message="success";
				}

			}
		} catch (Exception e) {
			logger.error("UserAction.forgetpassword()方法异常", e);
			e.printStackTrace();
		}
		logger.info("UserAction.forgetpassword()方法结束");
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
			logger.error("UserAction.forgetpassword() 发生异常", e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("UserAction.forgetpassword() 发生异常", e);
			e.printStackTrace();
		}
	}
	
}
