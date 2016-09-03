package cn.com.sims.common.exceptionresolver;

/** 
  * @File: CustomSimpleMappingExceptionResolver.java
  * @Package cn.com.sims.common.exceptionresolver
  * @Description: CustomSimpleMappingExceptionResolver
  * @Copyright: 
  * @Company: shbs
  * @author skl
  * @date 2014-8-24下午14:49:03
  * @version V1.0
  */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import cn.com.sims.common.commonUtil.CommonUtil;

/**
 * @ClassName: CustomSimpleMappingExceptionResolver
 * @Description: 包装Request对象，通过结合过滤器，将包装后的HttpServletRequest对象传递到请求中
 * @author skl
 * @date 2014-8-24 下午14:49:03
 */
public class CustomSimpleMappingExceptionResolver extends
		SimpleMappingExceptionResolver {
	/* log */
	private static final Logger logger = Logger.getLogger(CustomSimpleMappingExceptionResolver.class);
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// Expose ModelAndView for chosen error view.
		String viewName = determineViewName(ex, request);
		if (viewName != null) {// JSP格式返回
			if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
					.getHeader("X-Requested-With") != null && request
					.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
				// 如果不是异步请求
				// Apply HTTP status code for error views, if specified.
				// Only apply it if we're processing a top-level request.
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				return getModelAndView(viewName, ex, request);
			} else {// JSON格式返回
				try {
					PrintWriter writer = response.getWriter();
					writer.write(CommonUtil.AJAX_EXCEPTION);
					writer.flush();
				} catch (IOException e) {
					logger.error("ajax 异常过滤器：",e);
				}
				return null;

			}
		} else {
			return null;
		}
	}
}