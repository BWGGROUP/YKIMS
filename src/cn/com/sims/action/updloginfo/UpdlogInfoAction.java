package cn.com.sims.action.updloginfo;

import java.io.PrintWriter;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.com.sims.common.commonUtil.CommonUtil;
import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.model.baseupdloginfo.BaseUpdlogInfo;
import cn.com.sims.service.baseupdloginfo.IBaseUpdlogInfoService;

/**
 * 
 * @author duwenjie
 *
 */

@Controller
public class UpdlogInfoAction {
	
	@Resource
	IBaseUpdlogInfoService baseUpdlogInfoService;//系统更新记录
	
	private static final Logger logger = Logger
	.getLogger(UpdlogInfoAction.class);

	
	/**
	 * 根据操作模块类型,code分页查询更新记录
	 * @param request
	 * @param response
	 * @param page
	 * @param code业务code
	 * @param type操作类型
	 * @param logintype登录标识
	 * @throws Exception
	 */
	@RequestMapping(value = "findOrgUpdlogInfoByCode")
	public void findOrgUpdlogInfoByCode(HttpServletRequest request,
			HttpServletResponse response,Page page,String code,String type,String logintype) throws Exception {
		String message = "success";
		List<BaseUpdlogInfo> updlogInfo=null;//接收更新记录集合
		try {
			int count=baseUpdlogInfoService.findPageUpdlogCount(code, CommonUtil.findNoteTxtOfXML(type));
			page.setTotalCount(count);
			//查询更新记录
			updlogInfo=baseUpdlogInfoService.findPageUpdlogInfo(page, CommonUtil.findNoteTxtOfXML(type), code);
		} catch (Exception e) {
			message="faile";
			logger.error("UpdlogInfoAction.findOrgUpdlogInfoByCode() 发生异常", e);
			e.printStackTrace();
		}
		
		
		PrintWriter out = null;
		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			resultJSON.put("list", JSONArray.fromObject(updlogInfo));
			resultJSON.put("page", new JSONObject(page));
			out.println(resultJSON.toString());
			out.flush();
		}catch (Exception e) {
			logger.error("UpdlogInfoAction.findOrgUpdlogInfoByCode() 发生异常", e);
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}
	
}
