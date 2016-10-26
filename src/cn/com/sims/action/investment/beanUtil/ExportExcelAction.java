/** 
 * @File: ExportExcelAction.java
 * @Package cn.com.canon.traveler.common.action
 * @Description: 导出Excel
 * @Copyright: 
 * @Company: Canon
 * @author rqq
 * @date 2014-8-18
 * @version V1.0
 */
package cn.com.sims.action.investment.beanUtil;

import java.io.File;
import java.io.IOException;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;



import jxl.SheetSettings;  
import jxl.Workbook;  
import jxl.format.Alignment;  
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;  
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;  
import jxl.write.DateFormat;
import jxl.write.Label;  
import jxl.write.WritableCellFormat;  
import jxl.write.WritableFont;  
import jxl.write.WritableSheet;  
import jxl.write.WritableWorkbook;

/**
 * @ClassName:ExportExcelAction
 * @Description: 导出Excel
 * @author rqq
 * @date 2014-8-18
 */
public class ExportExcelAction {
	//excel最大行数
	private final static int fullRowNo = 65535;
	private static final Logger logger = Logger.getLogger(ExportExcelAction.class);
	/**
	 * @Title: forgetPwdCcnUser
	 * @Description:向excel中打印数据并更改格式
     * @param list：结果集合
     * @param filePath：指定的路径名
     * @param out：输出流对象 通过response.getOutputStream()传入
     * @param mapFields：导出字段 key:对应实体类字段    value：对应导出表中显示的中文名
     * @param sheetName：工作表名称
     */
	private static void createSheetExcel(List list,WritableWorkbook wook,Map<String, String> mapFields,String sheetName,int sheetNo)throws Exception{
		Object objClass = null;
		logger.info("ExportExcelAction createSheetExcel 方法开始");
        try {
        	
            //设置头部字体格式
            WritableFont font = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, 
                    false, UnderlineStyle.NO_UNDERLINE, Colour.RED);
            //应用字体
            WritableCellFormat wcfh = new WritableCellFormat(font);
            //设置其他样式
            wcfh.setAlignment(Alignment.CENTRE);//水平对齐
            wcfh.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直对齐
            wcfh.setBorder(Border.ALL, BorderLineStyle.THIN);//边框
            wcfh.setBackground(Colour.YELLOW);//背景色
            wcfh.setWrap(false);//不自动换行
                                                                            
            //设置内容日期格式
            DateFormat df = new DateFormat("yyyy-MM-dd HH:mm:ss");
            //应用日期格式
            WritableCellFormat wcfc = new WritableCellFormat(df);
                                                                            
            wcfc.setAlignment(Alignment.CENTRE);
            wcfc.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直对齐
            wcfc.setBorder(Border.ALL, BorderLineStyle.THIN);//边框
            wcfc.setWrap(false);//不自动换行
            
            //基金＋金额格式
            WritableCellFormat wcfc_in = new WritableCellFormat(df);
                                                                            
            wcfc_in.setAlignment(Alignment.CENTRE);
            wcfc_in.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直对齐
            wcfc_in.setBorder(Border.ALL, BorderLineStyle.THIN);//边框
            wcfc_in.setWrap(true);//不自动换行
            
          //应用日期格式 （备注列）
            WritableCellFormat wcfc_ps = new WritableCellFormat(df);
                                                                            
            wcfc_ps.setAlignment(Alignment.CENTRE);
            wcfc_ps.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直对齐
            wcfc_ps.setBorder(Border.ALL, BorderLineStyle.THIN);//边框
            wcfc_ps.setWrap(true);//自动换行
                                                                            
            //创建工作表
            WritableSheet sheet = wook.createSheet(sheetName, sheetNo);
            //设置首行行高
            sheet.setRowView(0, 800);
            sheet.setColumnView(14, 40);
            sheet.setColumnView(0, 6);
            sheet.setColumnView(1, 20);
            sheet.setColumnView(2, 20);
            sheet.setColumnView(3, 20);
            sheet.setColumnView(4, 15);
            sheet.setColumnView(5, 15);
            //shouji
            sheet.setColumnView(6, 15);
            sheet.setColumnView(7, 30);
            sheet.setColumnView(8, 13);
            sheet.setColumnView(9, 13);
            sheet.setColumnView(10, 13);
            sheet.setColumnView(11, 9);
            sheet.setColumnView(12, 9);
            sheet.setColumnView(13, 20);
//            sheet.setColumnView(0, 6);
            SheetSettings setting = sheet.getSettings();
            setting.setVerticalFreeze(1);//冻结窗口头部
                                                                            
            int columnIndex = 0;  //列索引
            List<String> methodNameList = new ArrayList<String>();
            if(mapFields!=null){
                String key  = "";
                Map<String,Method> getMap = null;
                Method method = null;
                //开始导出表格头部
                for (Iterator<String> i = mapFields.keySet().iterator();i.hasNext();) {
                    key = i.next();
                    // 应用wcfh样式创建单元格
                    sheet.addCell(new Label(columnIndex, 0, mapFields.get(key), wcfh));
                    //记录字段的顺序，以便于导出的内容与字段不出现偏移
                    methodNameList.add(key);
                    columnIndex++;
                }
                if(list!=null && list.size()>0){
                    //导出表格内容
                    for (int i = 0,len = list.size(); i < len; i++) {
                        objClass = list.get(i);
                        getMap = getAllMethod(objClass);//获得对象所有的get方法
                        //按保存的字段顺序导出内容
                        for (int j = 0; j < methodNameList.size(); j++) {
                            //根据key获取对应方法
                            method = getMap.get("GET"+methodNameList.get(j).toString().toUpperCase());
																			if (method != null) {
																				// 从对应的get方法得到返回值
																				String value = "";
																				if (method.invoke(objClass, null) != null) {
																					value = method.invoke(objClass, null)
																							.toString();
																				}

                                //应用wcfc样式创建单元格
                                //备注使用wcfc_ps式样
                                if(methodNameList.get(j).toString().equals("countNo")){
                                	sheet.addCell(new Label(j, i+1, (i+1)+"", wcfc_ps));
                                }else if(methodNameList.get(j).toString().equals("invesfund")){
                                	sheet.addCell(new Label(j, i+1, value, wcfc_in));
                                }
                                else{
                                	sheet.addCell(new Label(j, i+1, value, wcfc));
                                }
                              }else{
                                   //如果没有对应的get方法，则默认将内容设为""
                                   sheet.addCell(new Label(j, i+1, (i+1)+"", wcfc));
                              }
                    
                        }
                    }
                }

            }else{
            	logger.error("ExportExcelAction createSheetExcel 传入参数不合法");
                throw new Exception("传入参数不合法");
            }
        } catch (Exception e) {
        	logger.error("ExportExcelAction createSheetExcel 方法异常"+e);
            e.printStackTrace();
            throw e;
        } 
        logger.info("ExportExcelAction createSheetExcel 方法结束");
	}
	 /** 
	  * @Title: createExcel
	  * @Description: 创建excel并写数据
	  * @param list：结果集合
      * @param pathnew：指定的路径名
      * @param out：输出流对象 通过response.getOutputStream()传入
      * @param mapFields：导出字段 key:对应实体类字段    value：对应导出表中显示的中文名
      * @param sheetName：工作表名称
	  * @return 返回页面地址
	  */
    public static void createExcel(List list,String pathnew,OutputStream out,Map<String, String> mapFields,String sheetName)throws Exception{
    	
    	logger.info("ExportExcelAction createExcel 方法开始");
        WritableWorkbook wook = null;//可写的工作薄对象
        
        //路径不存在，则创建
		File pathNewFile = new File(pathnew);
	    if(!pathNewFile.getParentFile().exists()){
	    	pathNewFile.getParentFile().mkdir();
	    }
        try {
			wook = Workbook.createWorkbook(new File(pathnew));//指定导出的目录和文件名 如：D:\\test.xls
		
        
	    	//分页
	    	int len = list.size();
	    	if(len <= 0){
	    		 sheetName = sheetName!=null && !sheetName.equals("")?sheetName:"sheet1";
	    		createSheetExcel(null,wook,mapFields,sheetName,0);
	    		
	    	}else{
	            sheetName = sheetName!=null && !sheetName.equals("")?sheetName:"sheet1";
	            String realSheetName = sheetName;
	        	int pages = 0;
	        	if(len%fullRowNo==0){//整除
	        		pages = len/fullRowNo;
	        		
	            	for(int ii = 0;ii<pages;ii++){
	            		if(pages > 1 && ii == 0){
	            			realSheetName = sheetName + "1";
	            		}
	            		createSheetExcel(list.subList(ii*fullRowNo, fullRowNo*(ii+1)),wook,mapFields,realSheetName,ii);
	            		realSheetName = sheetName+(ii+2);
	            	}
	        	}else{//非整除
	        		pages = len/fullRowNo+1;
	        		
	    			for(int ii = 0;ii<pages;ii++){
	            		
	    				if(pages > 1 && ii == 0){
	            			realSheetName = sheetName + 1;
	            		}
	    				
	    				if((ii+1) == pages){
	    					createSheetExcel(list.subList(ii*fullRowNo, len),wook,mapFields,realSheetName,ii);
	    				}else{
	    					createSheetExcel(list.subList(ii*fullRowNo, fullRowNo*(ii+1)),wook,mapFields,realSheetName,ii);
	    				}
	    				realSheetName = sheetName+(ii+2);
	            	}
	        	}
	    	}
                wook.write();
        } catch (IOException e) {
        	logger.error("ExportExcelAction createExcel 方法开始"+e);
			throw e;
		}catch (Exception e1) {
			logger.error("ExportExcelAction createExcel 方法开始"+e1);
			throw e1;
		}finally{
            try {
                if(wook!=null){
                    wook.close();
                }
            } catch (Exception e2) {
            	logger.error("ExportExcelAction createExcel 方法开始"+e2);
                throw e2;
            }
        }

		logger.info("ExportExcelAction createExcel 方法结束");
        
    }
    /**
     * @Title: getAllMethod
     * @Description:获取类的所有get方法
     * @param obj
     * @return
     */
    public static HashMap<String,Method> getAllMethod(Object obj) throws Exception{
    	logger.info("ExportExcelAction getAllMethod 方法开始");
        HashMap<String,Method> map = new HashMap<String,Method>();
        Method[] methods = obj.getClass().getMethods();//得到所有方法
        String methodName = "";
        for (int i = 0; i < methods.length; i++) {
            methodName = methods[i].getName().toUpperCase();//方法名
            if(methodName.startsWith("GET")){
                map.put(methodName, methods[i]);//添加get方法至map中
            }
        }
        logger.info("ExportExcelAction getAllMethod 方法结束");
        return map;
    }
    /**
     * @Title: ImportExcel
     * @Description:根据指定路径导出Excel
     * @param list
     * @param filePath
     * @param mapFields
     * @param sheetName
     */
    public static void ImportExcel(List list,String fileName,Map<String, String> mapFields,String sheetName)throws Exception{
        createExcel(list,fileName,null,mapFields,sheetName);
    }
                                                                    
    /**
     * @Title: ImportExcel
	 * @Description:从Jsp页面导出Excel
     * @param list
     * @param filePath
     * @param out
     * @param mapFields
     * @param sheetName
     */
    public static void ImportExcel(List list,String fileName,OutputStream out,Map<String, String> mapFields,String sheetName)throws Exception{
        createExcel(list,fileName,out,mapFields,sheetName);
    }
}
