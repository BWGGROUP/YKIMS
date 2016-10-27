package cn.com.sims.common.commonUtil;

import java.net.URL;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 2009-5-11
 * Time: 13:34:10
 * 模拟Http访问的工具类
 */
public class HttpConnectionUtil {
    public static String getHttpContent(String url) {
        return getHttpContent(url, "GB2312");
    }

    public static String getHttpContent(String url, String charSet) {
        HttpURLConnection connection = null;
        String content = "";
        try {
            URL address_url = new URL(url);
            connection = (HttpURLConnection) address_url.openConnection();
//            connection.setRequestMethod("GET");
            //设置访问超时时间及读取网页流的超市时间,毫秒值
//            System.setProperty("sun.net.client.defaultConnectTimeout","30000");
//            System.setProperty("sun.net.client.defaultReadTimeout", "30000");

            //after JDK 1.5
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            //得到访问页面的返回值
            int response_code = connection.getResponseCode();
            if (response_code == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();
//                InputStreamReader reader = new InputStreamReader(in,charSet);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, charSet));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    content+=line;
                }
                return content;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection !=null){
                connection.disconnect();
            }
        }
        return "";
    }

    public static void main(String[] args) {
//        String content = HttpConnectionUtil.getHttpContent("http://192.168.1.61:8001/");
        String content = HttpConnectionUtil.getHttpContent("http://www.baidu.com");
        System.out.println("content = " + content);
    }
}
