package cn.com.sims.util.interceptor;

/** 
 * @File: LoggerIntercept.java
 * @Package cn.com.sims.common.interceptor
 * @Description: 随机验证码
 * @Copyright: 
 * @Company: shbs
 * @author skl
 * @date 2014-8-18
 * @version V1.0
 */

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.aspectj.weaver.ast.Var;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.common.commonUtil.ConfigUtil;
import cn.com.sims.model.sysuserinfo.SysUserInfo;
import cn.com.sims.model.sysuserwad.SysUserWad;
import cn.com.sims.model.syswadjuri.SysWadJuri;

/**
 * @ClassName: LoggerIntercept
 * @Description: 日志拦截器
 * @author skl
 * @date 2014-8-18
 */
public class LoggerIntercept implements HandlerInterceptor {
	
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(LoggerIntercept.class);
	
	private String mappingURL;//利用正则映射到需要拦截的路径    
    public void setMappingURL(String mappingURL) {    
           this.mappingURL = mappingURL;    
   }  
	/**
	 * @Title: preHandle
	 * @Description://在业务处理器处理请求执行完成后,生成视图之前执行的动作 )
	 * @param request response handler 
	 * @Description:在业务处理器处理请求之前被调用
	 * 如果返回false
	 *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 
	 * 如果返回true
	 *    执行下一个拦截器,直到所有的拦截器都执行完毕
	 *    再执行被拦截的Controller
	 *    然后进入拦截器链,
	 *    从最后一个拦截器往回执行所有的postHandle()
	 *    接着再从最后一个拦截器往回执行所有的afterCompletion()
	 *  @return true false
	 */
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String url=request.getRequestURL().toString(); 
      if(mappingURL==null || url.matches(mappingURL)){
    	//不拦截URL
			return true; 
      }else{
		HttpSession session = request.getSession(); 
		String  logintype=request.getParameter(CommonUtil.LOGINTYPE);
		if (session.getAttribute(CommonUtil.USER_INFO)==null) {
    		if (request.getHeader("x-requested-with") != null  
    		           && request.getHeader("x-requested-with")  
    		               .equalsIgnoreCase("XMLHttpRequest")){//如果是ajax请求响应头会有，x-requested-with；
    			 					response.getWriter().print(CommonUtil.SESSIONTIMEOUT);//返回登录超时参数
    		         return false;  
    		       }else {
    		    	   //无此参数，则进入登录页面
    		    	   if(logintype == null ||  "".equals(logintype.trim())){
    		    		   response.sendRedirect("");
    		    		   return false;
    		    	   }
    		    	 //未登录状态
    					if(logintype.equals(CommonUtil.LOGINTYPEWX)){
    						//如果登录类型是微信
    			String URL=CommonUtil.WEICART_UID_URL
    					.replace("APPID", CommonUtil.WEICART_APPID)
    					.replace("REDIRECT_URI", CommonUtil.WEICART_UID_CALL)
    					.replace("STATE", CommonUtil.getParamsUrl(request));
    			response.sendRedirect(URL);
    						 return false;  
    					}else{
    						//未登录时，post请求转化为get请求
    						if (request.getMethod().equals("POST")){
    							response.sendRedirect(CommonUtil.getParamsUrlNonEncode(request));
    							return false;
    						}
    						//如果登录类型为手机或者PC 
    						request.setAttribute(CommonUtil.LOGINTYPE, logintype);
    						request.getRequestDispatcher("/loginhtml.html").forward(request, response);
    						return false;
    					}
				}
			
		}else{
			
			//无此参数，则进入登录页面
	    	   if(logintype == null ||  "".equals(logintype.trim())){
	    		   response.sendRedirect("");
	    		   return false;
	    	   }
	    	   
			//已登录用户进行判断
			SysUserInfo userinfo= (SysUserInfo) session.getAttribute(CommonUtil.USER_INFO);//登录用户
			Boolean JURI=JURI("JURI",userinfo);//拥有投资业务权限
			Boolean JURI_BACK=JURI("JURI_BACK",userinfo);//拥有后台管理权限
			if (url.matches(".*/userinfo.html*")||url.matches(".*/userpassword.html*")||url.matches(".*/logout.html*")||url.matches(".*/changeuerinfo.html*")) {
				return true;
			}
			//进入后台管理页面
			if(url.matches(".*/admin/.*")){
				if (JURI_BACK) {
					return true;
				}else{
					//如果是PC版
					if (logintype.equals(CommonUtil.findNoteTxtOfXML("COMPUTER"))) {
						response.sendRedirect("../without_permission.htm");
						return false;
					}else {
						response.sendRedirect("../without_permission.htm");
						return false;
					}
				}
			}else {
				if (JURI) {
					return true;
				}else if (JURI_BACK) {
					if (request.getHeader("x-requested-with") != null  
		    		           && request.getHeader("x-requested-with")  
		    		               .equalsIgnoreCase("XMLHttpRequest")){//如果是ajax请求响应头会有，x-requested-with；
		    			 					
		    		         return true;  
		    		       }
					//跳转到后台管理页面
					request.setAttribute(CommonUtil.LOGINTYPE, logintype);
					request.getRequestDispatcher("admin").forward(request, response);
					return false;
				}else{
					//如果是PC版
					if (logintype.equals(CommonUtil.findNoteTxtOfXML("COMPUTER"))) {
						response.sendRedirect("without_permission.htm");
						return false;
					}else {
						response.sendRedirect("without_permission.htm");
						return false;
					}
				}
			}
		}
	
      }
	        
	}
	/**判断是否拥有权限
	 * @author zzg
	 * @date 2015-10-29
	 * @return
	 */
	private static Boolean  JURI(String bs,SysUserInfo userinfo) {
		List<SysUserWad> userWads=userinfo.getSysUserWadslisList();
		if (bs.equals("JURI")) {
			for (int i = 0; i < userWads.size(); i++) {
				if(userWads.get(i).getSys_wad_code().equals(CommonUtil.findNoteTxtOfXML("JURI-ALL"))){
					return true;
				}
				List<SysWadJuri> juris=userWads.get(i).getSysWadJuri();
				for (int j = 0; j < juris.size(); j++) {
					if (juris.get(j).getSys_juri_code().equals(CommonUtil.findNoteTxtOfXML("JURI-BUES"))) {
						return true;
					}
				}
			}
		}else if (bs.equals("JURI_BACK")) {
			for (int i = 0; i < userWads.size(); i++) {
				if(userWads.get(i).getSys_wad_code().equals(CommonUtil.findNoteTxtOfXML("JURI-ALL"))){
					return true;
				}
				List<SysWadJuri> juris=userWads.get(i).getSysWadJuri();
				for (int j = 0; j < juris.size(); j++) {
					if (juris.get(j).getSys_juri_code().equals(CommonUtil.findNoteTxtOfXML("JURI-BUES-BACK"))) {
						return true;
					}
				}
			}
		} 
return false;
	}
	/**
	 * @Title: postHandle
	 * @Description://在业务处理器处理请求执行完成后,生成视图之前执行的动作 )
	 * @param request response handler modelAndView
	 * @return
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * @Title: afterCompletion
	 * @Description:在DispatcherServlet完全处理完请求后被调用 ,当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 * @param request response handler ex
	 * @return
	 */
	
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	     // 登录电脑的IP
			String pcIp = request.getHeader("x-forwarded-for");
			log.info("登录用户：id= name=  IP:"+pcIp+"    结束  ");
	}
}


