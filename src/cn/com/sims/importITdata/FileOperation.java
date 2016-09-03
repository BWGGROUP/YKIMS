/** 
 * @File: LoggerIntercept.java
 * @Package cn.com.canon.traveler.common.interceptor
 * @Description: 随机验证码
 * @Copyright: 
 * @Company: Canon
 * @author skl
 * @date 2014-8-18
 * @version V1.0
 */
package cn.com.sims.importITdata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileOperation {
 private static String filesrc="/home/shbs-tp011/media/OutputFile/";
// private static String filetitle="text";
 /**
  * 创建文件
  * 
  * @return
  */
 public static String createFile(String filetitle)throws Exception{
     StringBuffer xlFileName = new StringBuffer();
     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     // 新的文件名
     xlFileName.append(filetitle);
     xlFileName.append("-");
     xlFileName.append(df.format(new Date()).replace(" ", "_").replace(":", "_"));
     xlFileName.append(".txt");
     File fileName = new File(filesrc + xlFileName);// 取得输出流
  try{
   if(!fileName.exists()){
    fileName.createNewFile();
   }
  }catch(Exception e){
   e.printStackTrace();
  }
  return fileName.toString();
 } 
 
 /**
  * 读TXT文件内容
  * @param fileName
  * @return
  */
 public static String readTxtFile(File fileName)throws Exception{
  String result=null;
  FileReader fileReader=null;
  BufferedReader bufferedReader=null;
  try{
   fileReader=new FileReader(fileName);
   bufferedReader=new BufferedReader(fileReader);
   try{
    String read=null;
    while((read=bufferedReader.readLine())!=null){
     result=result+read+"\r\n";
    }
   }catch(Exception e){
    e.printStackTrace();
   }
  }catch(Exception e){
   e.printStackTrace();
  }finally{
   if(bufferedReader!=null){
    bufferedReader.close();
   }
   if(fileReader!=null){
    fileReader.close();
   }
  }
  System.out.println("读取出来的文件内容是："+"\r\n"+result);
  return result;
 }
 
 
 public static boolean writeTxtFile(String content,File  fileName)throws Exception{
  RandomAccessFile mm=null;
  boolean flag=false;
  FileOutputStream o=null;
  try {
   o = new FileOutputStream(fileName);
      o.write(content.getBytes("GBK"));
      o.close();
//   mm=new RandomAccessFile(fileName,"rw");
//   mm.writeBytes(content);
   flag=true;
  } catch (Exception e) {
   // TODO: handle exception
   e.printStackTrace();
  }finally{
   if(mm!=null){
    mm.close();
   }
  }
  return flag;
 }



public static void contentToTxt(String filePath, String content) {
        String str = new String(); //原有txt内容
        String s1 = new String();//内容更新
        try {
            File f = new File(filePath);
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedReader input = new BufferedReader(new FileReader(f));

            while ((str = input.readLine()) != null) {
                s1 += str + "\n";
            }
            System.out.println(s1);
            input.close();
            s1 += content;

            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(s1);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}