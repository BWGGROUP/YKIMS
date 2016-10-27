package cn.com.sims.action.meeting;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.com.sims.common.commonUtil.CommonUtil;

/**
 * 添加会议 上传附件
 * @author dwj
 * @date 2016-5-4
 */

@SuppressWarnings("serial")
public class AddMeetingInfoUpload extends HttpServlet{
	
	private String srcpath="";  
	String geshiString="";
	private String fname="";
	
	private static final Logger logger = Logger
	.getLogger(AddMeetingInfoUpload.class);
	
	public AddMeetingInfoUpload(){
		super();
	}
	
	public void destroy() {
		super.destroy();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String message="success";
		Map<String, Object> map=null;
		String url=CommonUtil.findNoteTxtOfXML("MEETING_WORD_SRC");
		String meetingcode=URLDecoder.decode(request.getParameter("meetingcode"),"UTF-8");
		
		try {
			File uploadDir=new File(url);
		    if(!uploadDir.exists()){
		        uploadDir.mkdirs();
		    }
			
		    if (ServletFileUpload.isMultipartContent(request)) {
		    	
		    	DiskFileItemFactory dff = new DiskFileItemFactory();
				dff.setSizeThreshold(1024000);
				
				ServletFileUpload sfu = new ServletFileUpload(dff);
				FileItemIterator fii = sfu.getItemIterator(request);
				
				String fileName = "";
				FileItemStream fis =null;
				if(fii.hasNext()){
					fis = fii.next();
					
					if (!fis.isFormField() && fis.getName().length() > 0) {
						//读取action
						WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
						MeetingAction meetingAction=(MeetingAction) webApplicationContext.getBean("meetingAction");
						
						Map<String, Object> fileMap=meetingAction.meetingFileCode();
						if(fileMap!=null&&fileMap.get("message").toString().equals("success")){
							fileName = fis.getName();
							geshiString=fileName.substring(fileName.indexOf(".")+1, fileName.length());
							Pattern reg = Pattern.compile("[.]doc|docx|xls|txt|pdf$");
							Matcher matcher = reg.matcher(fileName);
							if (!matcher.find()) {
								//不是文档
								message="noword";
							}else {
								fname=fileMap.get("filecode").toString()+"."+geshiString;
								//原文件路径
								srcpath=url+"/"+fname;

								BufferedInputStream in = new BufferedInputStream(fis.openStream());// 获得文件输入流
								FileOutputStream a = new FileOutputStream(new File(srcpath));
								BufferedOutputStream output = new BufferedOutputStream(a);
								Streams.copy(in, output, true);// 开始把文件写到你指定的上传文件夹
								
								//存储路径
								srcpath=CommonUtil.findNoteTxtOfXML("MEETING_WORD_DOWNLOAD")+"/"+fname;
								
								if (meetingcode!=null&&!meetingcode.equals("")) {
									map=meetingAction.insertMeetingFile(request, response, srcpath,fileMap.get("filecode").toString(), meetingcode,fileName );
									if (map!=null) {
										message=map.get("message").toString();
									}
								}
							}
						}else {
							message=fileMap.get("message").toString();
						}
						
					}
				}
				
				
			
			}
		} catch (Exception e) {
			message="error";
			logger.error("AddMeetingInfoUpload 上传附件异常",e);
			e.printStackTrace();
		}
		
		PrintWriter out;
		response.setContentType("text/html; charset=UTF-8");
	    try {
			out=response.getWriter();
			JSONObject resultJSON = new JSONObject();
			resultJSON.put("message", message);
			if(message.equals("success")&&map!=null){
				resultJSON.put("info", JSONArray.fromObject(map.get("info")));
			}
			out.print(resultJSON.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	
	}
	
	
}
