package cn.com.sims.util.requestFileter;

/** 
  * @File: RequestFileter.java
  * @Package cn.com.sims.common.filter
  * @Description: RequestFileter
  * @Copyright: 
  * @Company: shbs
  * @author skl
  * @date 2014-8-19下午14:49:03
  * @version V1.0
  */

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
/**
 * @ClassName:RequestFileter
 * @Description: RequestFileter
 * @author skl
 * @date 2014-8-19 下午14:49:03
 */
public class RequestFileter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		chain.doFilter(new RequestWrapper((HttpServletRequest) request), 
				response); 

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
