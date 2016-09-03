package cn.com.sims.common.commonUtil;

/** 
 * @File: CommonUtil.java 
 * @Package cn.com.sims.common.commonUtil
 * @Description: TODO 
 * @Copyright: Copyright(c)2012 
 * @Company: shbs  
 * @author rqq
 * @date 2014/08/18
 * @version V1.0 
 **/

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException; 

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;







import cn.com.sims.action.investment.beanUtil.exportExcelThead;
import sun.misc.BASE64Encoder;


public class CommonUtil {
	
	public final static  int  QUEUE_SIZE = 100;
	
	/**
     * 创建队列，保存导出ｅｘｃｅｌ线程
     * */
     public static BlockingQueue<cn.com.sims.action.investment.beanUtil.exportExcelThead> bQueue = new LinkedBlockingQueue<cn.com.sims.action.investment.beanUtil.exportExcelThead>(QUEUE_SIZE);
    
 /**
  * 循环线程，遍历队列　bQueue，启动下载ＥＸＣＥＬ程序
  * */
     static {
         try{
             new Thread(){
                 public void run(){
                     while(true){
                         try {
                             if(bQueue !=  null){
                            exportExcelThead eh =  bQueue.take();
                            eh.run();
                             }
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                     }
                 }
             }.start();
         }catch(Exception e){
            
         }
     }
	


	/**
	 * 查询当前时间之前的数据，单位天
	 */
	public static final int DATE_DIFF= -1;
	/**
	 * 登录类型
	 */
	public static final String LOGINTYPE="logintype";
	/**
	 * 登录类型（微信）
	 */
	public static final String LOGINTYPEWX="WX";
	/**
	 * AJAX请求 如果登录超时返回参数
	 */
	public static final String SESSIONTIMEOUT="SESSIONTIMEOUT";
	/**
	 * 登录类型（手机）
	 */
	public static final String LOGINTYPEMB="MB";
	
	/**
	 * session中的登录者信息
	 * 杜文杰2015-10-9
	 */
	public  static final String LOGIN_INFO =  "LOGIN_INFO";

	public static final String USER_INFO= "USERINFO";


	
	
	/**
	 * 投资机构清单导出
	 */
	public static final String EXPORT_EXCEL_PRODUCT= "投资机构清单导出";
	


	/**
	 * 共同 ajax 异常返回值
	 */
	public static final String AJAX_EXCEPTION = "ajaxException";
/**
 * 微信授权地址
 */
public static final String WEICART_UID_URL="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
/**
 * 微信授权后回调地址
 */
public static final String WEICART_UID_CALL="http://www.ecapsmart.com/WeiXinExtended/bandServlet";
/**
 * 微信公众账号APPID
 */
public static final String WEICART_APPID="wx68295102be0cd28b";
/**
	 * @Title: getNowTime
	 * @Description:获取系统的当前时间
	 * @author rqq
	 * @return 字符串类型的XXXX-XX-XX的时间
	 */
	public static String getNowTime(){
		//获取当前时间
		Date now = new Date();
		Long tim = now.getTime();
		Timestamp time = new Timestamp(tim);
		return time.toString().substring(0, 10);
	}
	
	/**
	 * @Title: getNowTime_tamp
	 * @Description:获取系统的当前时间
	 * @author rqq
	 * @return 返回 Timestamp 类型的时间
	 */
	public static Timestamp getNowTime_tamp(){
		//获取当前时间
		Date now = new Date();
		Long tim = now.getTime();
		Timestamp time = new Timestamp(tim);
		return time;
	}
	/**
	 * @Title: getBeforeDayTime_tamp
	 * @Description:获取系统的前一天时间的0点
	 * @author rqq
	 * @return 返回 Timestamp 类型的时间
	 */
	public static Timestamp getBeforeDayTime_tamp(){
		//获取当前时间
		Date now = new Date();
        Calendar cal= Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DATE, CommonUtil.DATE_DIFF);
		Date dateLast = cal.getTime();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Timestamp time = Timestamp.valueOf(sdf.format(dateLast));
		String todayTime = time.toString().substring(0, 10)+ " 00:00:00";
		 time = Timestamp.valueOf(todayTime);
		return time;
	}
	/**
	 * @Title:getDayTime_tamp
	 * @Description:获取系统的当天时间的0点
	 * @author rqq
	 * @return 返回 Timestamp 类型的时间
	 */
	public static Timestamp getDayTime_tamp(){
		//获取当前时间
		Date now = new Date();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Timestamp time = Timestamp.valueOf(sdf.format(now));
		String todayTime = time.toString().substring(0, 10)+ " 00:00:00";
		 time = Timestamp.valueOf(todayTime);
		return time;
	}
	
	/**
	 * @Title:get6pwd
	 * @Description:产生六位密码
	 * @author rqq
	 * @return 返回 6为随机数字
	 */
	 public static String get6pwd(){
		  Random r = new Random();
		  int pwd = r.nextInt(1000000);
		  while(pwd<100000){
			  pwd = r.nextInt(1000000);
		  }
		  return String.valueOf(pwd);
	 }
	 
	 /**
	  * @Title:emailFormat
	  * @Description:邮箱格式验证
	  * @param email
	  * @return true or false
	  */
	public static boolean emailFormat(String email)  
    {  
        boolean tag = true;  
//        final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";  
        final String pattern1;
        if(null != email && email.indexOf("@") <= 1){
        	pattern1 = "[a-zA-Z0-9]@([a-zA-Z0-9]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z0-9]+";
        }
        else{
        	pattern1 = "[a-zA-Z0-9][a-zA-Z0-9._-]{0,16}[a-zA-Z0-9]@([a-zA-Z0-9]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z0-9]+";
        }
        final Pattern pattern = Pattern.compile(pattern1);  
        final Matcher mat = pattern.matcher(email);  
        if (!mat.find()) {  
            tag = false;  
        }  
        return tag;  
    }  
	
 	/**
 	 * @Title:EncoderByMd5
 	 * @Description:利用MD5进行加密      
 	 * @param str  待加密的字符串      
 	 * @return  加密后的字符串     
 	 * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法     
 	 * @throws UnsupportedEncodingException
 	 */ 
	public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
	if(str == null){
		return null;
	}
	//确定计算方法
	MessageDigest md5=MessageDigest.getInstance("MD5");
	BASE64Encoder base64en = new BASE64Encoder();
	//加密后的字符串
	String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
	return newstr;
	}
	
		
	/**
	 * @Title:findNoteTxtOfXML
	 * @Description:查询文件设定的属性值
	 * @param label
	 * @return
	 */
	public static String findNoteTxtOfXML(String label) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder db = factory.newDocumentBuilder();
			String filePath= CommonUtil.class.getClassLoader().getResource("/cn/com/sims/common/xml/common.xml").getPath();
			filePath = filePath.replace("%20"," ");
			Document doc = db.parse(new File(filePath));
			Element elmtInfo = doc.getDocumentElement();
			NodeList nodes = elmtInfo.getChildNodes();
			for (int i = 0; i < nodes.getLength();i++) {
				Node result = nodes.item(i);
				if (result.getNodeType() == Node.ELEMENT_NODE && result.getNodeName().equals(label)) {
					return result.getTextContent();
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}

		
	/**
	 * @Title:isUrl
	 * @Description:验证URL合法性
	 * @param pInput
	 * @return true or false
	 */
	public static boolean isUrl (String pInput) {
        if(pInput == null){
            return false;
        }
        String regEx = "^(http|https|ftp)//://([a-zA-Z0-9//.//-]+(//:[a-zA-"
            + "Z0-9//.&%//$//-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"
            + "2}|[1-9]{1}[0-9]{1}|[1-9])//.(25[0-5]|2[0-4][0-9]|[0-1]{1}"
            + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)//.(25[0-5]|2[0-4][0-9]|"
            + "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)//.(25[0-5]|2[0-"
            + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"
            + "-9//-]+//.)*[a-zA-Z0-9//-]+//.[a-zA-Z]{2,4})(//:[0-9]+)?(/"
            + "[^/][a-zA-Z0-9//.//,//?//'///////+&%//$//=~_//-@]*)*$";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(pInput);
        return matcher.matches();
    }
          
           
    /**
     * @Title:excelDownload
   	 * @Description:excel文件下载
   	 * @param pathnew：文件路径，request response
   	 * @return null
   	 * @throws IOException
   	 */
   	public static void excelDownload(String pathnew,HttpServletRequest request ,HttpServletResponse response) throws IOException{
   		
   		File fileExcelTemplate = new File(pathnew);
   		
   		InputStream is = new FileInputStream(fileExcelTemplate);
   		OutputStream os = response.getOutputStream();
   		BufferedInputStream bis = new BufferedInputStream(is);
   		
   		BufferedOutputStream bos = new BufferedOutputStream(os);

   		// response
   		response.reset();
   		response.setCharacterEncoding("UTF-8");
   		response.setContentType("application/vnd.ms-excel");
   		response.setHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode(fileExcelTemplate.getName(),"UTF-8"));
   		int bytesRead = 0;
   		byte[] buffer = new byte[1024 * 8];
   		while ((bytesRead = bis.read(buffer)) != -1) {
   		  bos.write(buffer, 0, bytesRead);
   		}
   	    bos.flush();
   	    bis.close();
   	    bos.close();
   	    is.close();    
   	    os.close();
   	    
   	}


	
	
	/**
	 * @Title:getDayForDate
	 * @Description:获取系统的当天
	 * @author 
	 * @return 返回  类型的时间
	 */
	public static String getDayForDate(){
		//获取当前时间
		Date now = new Date();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Timestamp time = Timestamp.valueOf(sdf.format(now));
		String todayTime = time.toString().substring(0, 10);
		return todayTime;
	}
	
	public static final int retryNo = 3;
	
	
	
	
	
	
	
	
	
	
	
	
	


	/**
	 * 获取系统的当前时间
	 * @author RQQ
	 * @return 字符串类型的XXXX-XX-XX XX:XX:XX的时间
	 */
	public static String getNowdateTime(){
		//获取当前时间
		Date now = new Date();
		Long tim = now.getTime();
		Timestamp time = new Timestamp(tim);
		return time.toString().substring(0, 19);
	}
	

	
	
   
 	/** 
	 * 删除单个文件 
	 * @author wsh
	 * @param   sPath    被删除文件的文件名 
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	public static boolean deleteFile(String sPath){  
	  boolean  flag = false;  
	   File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	} 
	/**
	 * 获取文件大小（字节为单位）
	 */
	public static long getFileSize(String spath){
		long num = 0;
		File file = new File(spath);
		if (file.isFile() && file.exists()) {
			long length = file.length();
			num = length;
		}else{
			num = 0;
		}
		return num;
	}
	
	/**
	 * 根据经纬度计算距离
	 * @param LonA 经度A
	 * @param LatA 纬度A
	 * @param LonB 经度B
	 * @param LatB 纬度B
	 * @return 距离
	 */
	public static double getDistance(double LonA, double LatA, double LonB, double LatB)
	{
		// 东西经，南北纬处理，只在国内可以不处理(假设都是北半球，南半球只有澳洲具有应用意义)
		double MLonA = LonA;
		double MLonB = LonB;
		// 地球半径（千米）
		double R = 6371.004;
		double C = Math.sin(rad(LatA)) * Math.sin(rad(LatB)) + Math.cos(rad(LatA)) * Math.cos(rad(LatB)) * Math.cos(rad(MLonA - MLonB));
		return (R * Math.acos(C));
	}

	private static double rad(double d)
	{
		return d * Math.PI / 180.0;
	}	
	
	//获取指定区间的随机整数
	public static int createrandomint(int max,int min){
		Random random=new Random();
		int randNumber = random.nextInt(max - min + 1) + min;
		return randNumber;
	}
	//获取指定日期的加减
	public static Timestamp datecalculation(Timestamp begintime,int dayint){
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(begintime);
	    cal.add(Calendar.DATE, dayint);
	    Date temp_date = cal.getTime(); 
	    Long tim = temp_date.getTime();
		Timestamp time = new Timestamp(tim);
		return time;
	}
	//-----------------------------------------------------------------------------------------------------------------------------------
	  private static final int[] prefix = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };   
	  
	    /**  
	     * 随机产生最大为18位的long型数据(long型数据的最大值是9223372036854775807,共有19位)  
	     *   
	     * @param digit  
	     *            用户指定随机数据的位数  
	     */  
	    public static long randomLong(int digit) {   
	        if (digit >= 19 || digit <= 0)   
	            throw new IllegalArgumentException("digit should between 1 and 18(1<=digit<=18)");   
	        String s = RandomStringUtils.randomNumeric(digit - 1);   
	        return Long.parseLong(getPrefix() + s);   
	    }   
	  
	    /**  
	     * 随机产生在指定位数之间的long型数据,位数包括两边的值,minDigit<=maxDigit  
	     *   
	     * @param minDigit  
	     *            用户指定随机数据的最小位数 minDigit>=1  
	     * @param maxDigit  
	     *            用户指定随机数据的最大位数 maxDigit<=18  
	     */  
	    public static long randomLong(int minDigit, int maxDigit)  {   
	        if (minDigit > maxDigit) {   
	            throw new IllegalArgumentException("minDigit > maxDigit");   
	        }   
	        if (minDigit <= 0 || maxDigit >= 19) {   
	            throw new IllegalArgumentException("minDigit <=0 || maxDigit>=19");   
	        }   
	        return randomLong(minDigit + getDigit(maxDigit - minDigit));   
	    }   
	  
	    private static int getDigit(int max) {   
	        return RandomUtils.nextInt(max + 1);   
	    }   
	  
	    /**  
	     * 保证第一位不是零  
	     *   
	     * @return  
	     */  
	    private static String getPrefix() {   
	        return prefix[RandomUtils.nextInt(9)] + "";   
	    }   
	  
		
		
		
		
/* 常量 杜文杰*/
		/**
		 * 操作者类型
		 * 系统
		 */
		public static final String OPERTYPE_SYS="1";
		
		/**
		 * 操作者类型
		 * 易凯用户
		 */
		public static final String OPERTYPE_YK="2";
		
		/**
		 * 投资机构业务类型
		 * investmentType
		 */
		public static int INVESTMENT_TYPE=Integer.parseInt(ConfigUtil.get("investmentType"));
		
		/**
		 * 投资人业务类型
		 * investorType
		 */
		public static int INVESTOR_TYPE=Integer.parseInt(ConfigUtil.get("investorType"));
		
		/**
		 * 公司业务类型
		 * companyType
		 */
		public static int COMPANY_TYPE=Integer.parseInt(ConfigUtil.get("companyType"));
		
		/**
		 * 交易业务类型
		 * tradeType
		 */
		public static int TRADE_TYPE=Integer.parseInt(ConfigUtil.get("tradeType"));
		
		/**
		 * 基金业务类型
		 * invesfundType
		 */
		public static int INVESFUND_TYPE=Integer.parseInt(ConfigUtil.get("invesfundType"));
		
		/**
		 * 会议业务类型
		 * meetingType
		 */
		public static int MEETING_TYPE=Integer.parseInt(ConfigUtil.get("meetingType"));
		
		/**
		 * 会议附件类型
		 * meetingFileType
		 */
		public static int MEETING_FILETYPE=Integer.parseInt(ConfigUtil.get("meetingFileType"));
		
		
		/**
		 * 创业者业务类型
		 * entrepreneurType
		 */
		public static int ENTREPRENEUR_TYPE=Integer.parseInt(ConfigUtil.get("entrepreneurType"));
		
		/**
		 * 机构Note业务类型
		 * investmentNoteType
		 */
		public static int INVESTMENTNOTE_TYPE=Integer.parseInt(ConfigUtil.get("investmentNoteType"));
		
		/**
		 * 投资人Note业务类型
		 * investorNoteType
		 */
		public static int INVESTORNOTE_TYPE=Integer.parseInt(ConfigUtil.get("investorNoteType"));
		
		/**
		 * 公司Note业务类型
		 * companyNoteType
		 */
		public static int COMPANYNOTE_TYPE=Integer.parseInt(ConfigUtil.get("companyNoteType"));
		
		/**
		 * 融资计划业务类型
		 * financplanType
		 */
		public static int FINANCPLAN_TYPE=Integer.parseInt(ConfigUtil.get("financplanType"));
		
		/**
		 * 交易Note业务类型
		 * tradeNoteType
		 */
		public static int TRADENOTE_TYPE=Integer.parseInt(ConfigUtil.get("tradeNoteType"));
		
		/**
		 * 会议Note业务类型
		 * meetingNoteType
		 */
		public static int MEETINGNOTE_TYPE=Integer.parseInt(ConfigUtil.get("meetingNoteType"));
		
		/**
		 * 用户业务类型
		 * userType
		 */
		public static int USER_TYPE=Integer.parseInt(ConfigUtil.get("userType"));
		
		/**
		 * 用户筐业务类型
		 * userWadType
		 */
		public static int USERWAD_TYPE=Integer.parseInt(ConfigUtil.get("userWadType"));
		
		
		/**
		 * 步长
		 * stepnum
		 */
		public static int STEPNUM=Integer.parseInt(ConfigUtil.get("stepnum"));
		
		/**
		 * 投资机构NOTE code前缀
		 * prefixInvestmentNote
		 */
		public static String PREFIX_INVESTMENTNOTE=ConfigUtil.get("prefixInvestmentNote");
		
		/**
		 * 投资机构基金code前缀
		 * prefixInvestmentNote
		 */
		public static String PREFIX_INVESFUND=ConfigUtil.get("prefixInvesfund");
		/**
		 * 会议Note code前缀
		 */
		public static String PREFIX_MEETINGNOTE=ConfigUtil.get("prefixMeetingNoteType");
		/**
		 *公司Note code前缀
		 */
		public static String PREFIX_COMPNOTE=ConfigUtil.get("prefixCompnote");
		
		/**
		 * 投资人noteid前缀
		 * prefixInvestmentNote
		 */
		public static String PREFIX_INVESTORNOTE=ConfigUtil.get("prefixInvestornote");
		
		/**
		 * 投资机构code前缀
		 * prefixInvestment
		 */
		public static String PREFIX_INVESTMENT=ConfigUtil.get("prefixInvestment");
		
		/**
		 * 交易备注code前缀
		 * prefixTradeNote
		 */
		public static String PREFIX_TRADENOTE=ConfigUtil.get("prefixTradeNote");
		
		/**
		 * 投资人code前缀
		 * prefixInvestor
		 */
		public static String PREFIX_INVESTOR=ConfigUtil.get("prefixInvestor");
		
		/**
		 * 公司code前缀
		 * prefixComp
		 */
		public static String PREFIX_COMP=ConfigUtil.get("prefixComp");
		
		/**
		 * 创业者code前缀
		 * prefixEntrepreneur
		 */
		public static String PREFIX_ENTREPRENEUR=ConfigUtil.get("prefixEntrepreneur");
		
		/**
		 * 融资计划code前缀
		 * prefixEntrepreneur
		 */
		public static String PREFIX_FINANCPLAN=ConfigUtil.get("prefixFinancplan");
		
		/**
		 * 交易code前缀
		 * prefixTrade
		 */
		public static String PREFIX_TRADE=ConfigUtil.get("prefixTrade");
		
		/**
		 * 会议code前缀
		 * prefixTrade
		 */
		public static String PREFIX_MEETING=ConfigUtil.get("prefixMeeting");
		
		/**
		 * 会议附件code前缀
		 * prefixMeetingFile
		 */
		public static String PREFIX_MEETINGFILE=ConfigUtil.get("prefixMeetingFile");
		
		/**
		 * 用户筐code前缀
		 * prefixTrade
		 */
		public static String USER_WAD=ConfigUtil.get("userwad");

		/**
		 * 用户code前缀
		 * prefixTrade
		 */
		public static String USER=ConfigUtil.get("user");
		
/* 常量 end*/
		
		  /**
		   * 中文转拼音
		   * 返回string[全拼,简拼]
		   */
		  public static String[] getPinYin(String strs) {
		        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
		        char[] ch = strs.trim().toCharArray();
		        StringBuffer buffer = new StringBuffer("");//全拼
		        StringBuffer buffer1 = new StringBuffer("");//简拼
		 
		        try {
		            for (int i = 0; i < ch.length; i++) {
		                // unicode，bytes应该也可以.
		                if (Character.toString(ch[i]).matches("[\u4e00-\u9fa5]+")) {
		                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(
		                            ch[i], format);
		                    buffer.append(temp[0]);// :结果"?"已经查出，但是音调是3声时不显示myeclipse8.6和eclipse
		                    buffer1.append(temp[0].substring(0, 1).toUpperCase());
		                } else {
		                    buffer.append(Character.toString(ch[i]));
		                }
		            }
		        } catch (BadHanyuPinyinOutputFormatCombination e) {
		            e.printStackTrace();
		        }
		        return   new String[]{buffer.toString(),buffer1.toString()};
		    }
		  /**
			 * 将请求里的参数转换为字符串
			 * @param request
			 * @return
			 */
			public static String getParamsUrl(HttpServletRequest request) {
				StringBuffer url = request.getRequestURL();
				Iterator iterator = request.getParameterMap().entrySet().iterator();
				StringBuffer param = new StringBuffer();
				String paramStr = null;
				int i = 0;
				while (iterator.hasNext()) {
					i++;
					Entry entry = (Entry) iterator.next();
					if (i == 1)
						param.append(entry.getKey()).append("=");
					else
						param.append("&").append(entry.getKey()).append("=");
					if (entry.getValue() instanceof String[]) {
						param.append(((String[]) entry.getValue())[0]);
					} else {
						param.append(entry.getValue());
					}

					try {
						paramStr = URLEncoder.encode(param.toString(), "utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				url.append("?");
				url.append(paramStr);
				return url.toString();
			}
			 /**
			 * 将请求里的参数转换为字符串，不进行中文转码
			 * @param request
			 * @return
			 */
			public static String getParamsUrlNonEncode(HttpServletRequest request) {
				StringBuffer url = request.getRequestURL();
				Iterator iterator = request.getParameterMap().entrySet().iterator();
				StringBuffer param = new StringBuffer();
				int i = 0;
				while (iterator.hasNext()) {
					i++;
					Entry entry = (Entry) iterator.next();
					if (i == 1)
						param.append(entry.getKey()).append("=");
					else
						param.append("&").append(entry.getKey()).append("=");
					if (entry.getValue() instanceof String[]) {
						param.append(((String[]) entry.getValue())[0]);
					} else {
						param.append(entry.getValue());
					}

				}
				
				url.append("?");
				url.append(param.toString());
				return url.toString();
			}
						
		/**
		* 日期处理，传入2014.5.6,2014.10.10或2014.10
		* 返回string
		 */
		public static String formatdatefromstr(String datestr) {
			String datastrformat=null;
		    if(datestr!=null && !"".equals(datestr)){
					//拆分字符串数组
			       String[] arrayStrings=datestr.split("\\.");
			       		//拼接年
			       datastrformat=arrayStrings[0].trim();
			       		//拼接月
			       if(arrayStrings[1].trim().length()==1){
				   		datastrformat=datastrformat+"-"+"0"+arrayStrings[1].trim();
			       		}
			       else{
				   		datastrformat=datastrformat+"-"+arrayStrings[1].trim();
			       		}
			       		//拼接日
			       if(arrayStrings.length==2){
			   		datastrformat=datastrformat+"-"+"01";
		       			}
			       else{
    				   if(arrayStrings[2].trim().length()==1){
    			   		datastrformat=datastrformat+"-"+"0"+arrayStrings[2].trim();
    		       				}
    				   else{
    			   		datastrformat=datastrformat+"-"+arrayStrings[2].trim();
    		       				}

		       			}
			       
		    		}
		    return datastrformat;
		 }
}
