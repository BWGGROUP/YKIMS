package cn.com.sims.action.briefing;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.types.Time;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.omg.CORBA.Current;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.com.sims.action.trade.TradeAction;
import cn.com.sims.common.commonUtil.ConstantUrl;
import cn.com.sims.common.commonUtil.Page;
import cn.com.sims.model.baseinvesfundinfo.BaseInvesfundInfo;
import cn.com.sims.model.baseinvestmentinfo.baseInvestmentInfo;
import cn.com.sims.model.viewInvestmentInfo.viewInvestmentInfo;
import cn.com.sims.service.baseinvestmentinfo.IBaseInvestmentInfoService;
import cn.com.sims.service.investment.investmentDetailInfoService.IInvestmentDetailInfoService;
import cn.com.sims.service.investment.organizationinvesfund.IOrganizationInvesfundService;
import cn.com.sims.service.tradeinfo.ITradeInfoService;
import freemarker.template.Template;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;

/**
 * 导出机构简介
 * 
 * @author zzg
 * @date 2016-10-26
 */
@Controller
public class BriefingAction {
	private static final Logger logger = Logger.getLogger(BriefingAction.class);
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Resource
	IBaseInvestmentInfoService baseInvestmentInfoService;// 投资机构信息基础层
	@Resource
	IInvestmentDetailInfoService iInvestmentDetailInfoService;//投资机构详情
	@Resource
	IOrganizationInvesfundService iOrganizationInvesfundService;// 投资机构基金
	@Resource
	ITradeInfoService iTradeInfoService;//交易信息
	private static final String TOPDFTOOL = "/opt/wkhtmltox/bin/wkhtmltopdf";
	/**
	 * 跳珠到机构简介页面
	 * 
	 * @author zzg
	 * @date 2016-10-26
	 */
	@RequestMapping(value = "Briefing")
	public String Briefing(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "logintype", required = false) String logintype,
			@RequestParam(value = "backtype", required = false) String backtype) {
		logger.info("TradeAction.Briefing()方法开始");
		String Url = ConstantUrl.Briefing(logintype);
		logger.info("TradeAction.Briefing()方法结束");
		return Url;
	}

	@RequestMapping(value = "exportexcel")
	public void exportexcel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "logintype", required = false) String logintype,
			@RequestParam(value = "code") String code
			) throws UnsupportedEncodingException {

		String fileName = new String(("机构简介" + System.currentTimeMillis() + ".xls").getBytes("utf-8"), "iso-8859-1");
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		// InputStream inputStream = new ByteArrayInputStream(html.getBytes());
		OutputStream outputStream;
		List<HashMap<String, Object>> mainlist=new ArrayList<>();
		
		 List<String> codelist = java.util.Arrays.asList(code.split(","));
		try {
			for (int i = 0; i < codelist.size(); i++) {
				String orgCode = codelist.get(i);
				baseInvestmentInfo baseInfo = null;// 定义投资机构信息(基础层)
				List<Map<String, String>> tradeList = null;// 定义投资机构交易集合
				List<BaseInvesfundInfo> fundList = null;// 定义投资机构基金集合
				viewInvestmentInfo info=null;//定义投资机构详情
				baseInfo = baseInvestmentInfoService.findBaseInvestmentByCode(orgCode);
				//投资机构详情(业务层)
				info=iInvestmentDetailInfoService.findInvestementDetailInfoByID(orgCode);
				// 分页查询投资机构基金
				Page page =new Page();
				page.setPageSize(3);
				Calendar calendar = Calendar.getInstance(); 
				calendar.add(Calendar.MONTH, -3);    //得到前一个月 
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String dateStr = sdf.format(calendar.getTime());
				tradeList=iTradeInfoService.findPageTradeInfoByDate(dateStr, orgCode);
				fundList = iOrganizationInvesfundService.findPageInvesfundByOrgId(orgCode, page);
				HashMap<String, Object> infomap=new HashMap<String, Object>();
				infomap.put("baseinfo", baseInfo);
				infomap.put("info",info);
				infomap.put("tradeList", tradeList);
				infomap.put("fundList", fundList);
				mainlist.add(infomap);
			}
		
			
			Map map=new HashMap();//Map传递数据
			map.put("mainlist", mainlist);
			Template tpl=freeMarkerConfigurer.getConfiguration().getTemplate("exportexcel.ftl");//对应文件名
			String html =FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);;

			outputStream = response.getOutputStream();
			byte[] bs = html.getBytes();
	
			outputStream.write(bs);
			outputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value = "exportpdf")
	public void exportpdf(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "logintype", required = false) String logintype,
			@RequestParam(value = "code") String code
			) throws UnsupportedEncodingException {
		String fileName = new String(("机构简介" + System.currentTimeMillis() + ".pdf").getBytes("utf-8"), "iso-8859-1");
		//String fileName = "机构简介" + System.currentTimeMillis() + ".pdf";
		String filehtmlName = new String(("机构简介" + System.currentTimeMillis() + ".html").getBytes("utf-8"), "iso-8859-1");
		//String filehtmlName = "机构简介" + System.currentTimeMillis() + ".html";

		OutputStream outputStream;
		List<HashMap<String, Object>> mainlist=new ArrayList<>();
		
		 List<String> codelist = java.util.Arrays.asList(code.split(","));
		try {
			for (int i = 0; i < codelist.size(); i++) {
				String orgCode = codelist.get(i);
				baseInvestmentInfo baseInfo = null;// 定义投资机构信息(基础层)
				List<Map<String, String>> tradeList = null;// 定义投资机构交易集合
				List<BaseInvesfundInfo> fundList = null;// 定义投资机构基金集合
				viewInvestmentInfo info=null;//定义投资机构详情
				baseInfo = baseInvestmentInfoService.findBaseInvestmentByCode(orgCode);
				//投资机构详情(业务层)
				info=iInvestmentDetailInfoService.findInvestementDetailInfoByID(orgCode);
				// 分页查询投资机构基金
				Page page =new Page();
				page.setPageSize(3);
				Calendar calendar = Calendar.getInstance(); 
				calendar.add(Calendar.MONTH, -3);    //得到前一个月 
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String dateStr = sdf.format(calendar.getTime());
				tradeList=iTradeInfoService.findPageTradeInfoByDate(dateStr, orgCode);
				fundList = iOrganizationInvesfundService.findPageInvesfundByOrgId(orgCode, page);
				HashMap<String, Object> infomap=new HashMap<String, Object>();
				infomap.put("baseinfo", baseInfo);
				infomap.put("info",info);
				infomap.put("tradeList", tradeList);
				infomap.put("fundList", fundList);
				mainlist.add(infomap);
			}
		
			
			Map map=new HashMap();//Map传递数据
			map.put("mainlist", mainlist);
			Template tpl=freeMarkerConfigurer.getConfiguration().getTemplate("exportexcel.ftl");//对应文件名
			String html =FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
			String pdfpath="/tmp/"+fileName;
			String htmlpath="/tmp/"+filehtmlName;
			
			 	File filepdf = new File(pdfpath);
		        File parent = filepdf.getParentFile();
		        File filehtml = new File(htmlpath);
		        FileOutputStream fos=new FileOutputStream(filehtml);
		        byte[] bs = html.getBytes();
		    	
		        fos.write(bs);
		        fos.close();
		        if (!parent.exists()) {
		            parent.mkdirs();
		        }

		        StringBuilder cmd = new StringBuilder();
		        cmd.append(TOPDFTOOL);
		        cmd.append(" ");
		        cmd.append("--page-size A2");// 
		        cmd.append(" ");
		        cmd.append(htmlpath);
		        cmd.append(" ");
		        cmd.append(pdfpath);

		        boolean result = true;
		        try {
		            Process proc = Runtime.getRuntime().exec(cmd.toString());
		            HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(
		                    proc.getErrorStream());
		            HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(
		                    proc.getInputStream());
		            error.start();
		            output.start();
		            proc.waitFor();
		            filepdf = new File(pdfpath);
		            
		            if (filepdf.exists()) {
		        		response.setCharacterEncoding("utf-8");
		        		response.setContentType("multipart/form-data");
		        		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		            	InputStream inputStream = new FileInputStream(filepdf);
		            	OutputStream os = response.getOutputStream();
		            	byte buffBytes[] = new byte[1024];
		            	   int read = 0;  
		            	   while ((read = inputStream.read(buffBytes)) != -1) {     
		            	    os.write(buffBytes, 0, read);  
		            	    }
		            	   os.flush();  
		            	   os.close();  
		            	   inputStream.close();  
					}else {
						
						PrintWriter out = response.getWriter();
						
						out.println("PDF导出异常");
						out.flush();
						out.close();
					}
		            
		          
		        } catch (Exception e) {
		            result = false;
		            PrintWriter out = response.getWriter();
					
					out.println("PDF导出异常");
					out.flush();
					out.close();
		        }
			
		} catch (Exception e) {
			PrintWriter out;
			try {
				out = response.getWriter();
				out.println("PDF导出异常");
				out.flush();
				out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			e.printStackTrace();
		}
	}
	
	public FreeMarkerConfigurer getFreeMarkerConfigurer() {
		return freeMarkerConfigurer;
	}
	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}
}
