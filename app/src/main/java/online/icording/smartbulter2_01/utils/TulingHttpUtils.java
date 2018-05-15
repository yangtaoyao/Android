package online.icording.smartbulter2_01.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.utils
 * 创建时间   2018/4/17 0017 18:58
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class TulingHttpUtils {
    private static final String URL="http://www.tuling123.com/openapi/api";
    private static final String API_KEY ="14cde7c10991450eb1b9bcfcfda07ba5";

    public static String doGet(String msg){

//        new AsyncTask<Void Void String>(){
//
//        }

        String result="";
        String url=setParams(msg);
        ByteArrayOutputStream boas=null;
        InputStream is=null;
        try {
            java.net.URL urlNet=new java.net.URL(url);
            HttpURLConnection conn=(HttpURLConnection)urlNet.openConnection();
            conn.setReadTimeout(5*1000);
            conn.setConnectTimeout(5*1000);
            conn.setRequestMethod("GET");
            is=conn.getInputStream();
            int len=-1;
            byte[]buf=new byte[128];
            boas=new ByteArrayOutputStream();

            while((len=is.read(buf))!=-1){
                boas.write(buf,0,len);
            }
            boas.flush();
            result=new String(boas.toByteArray());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String setParams(String msg) {
        String url= "";
        try {
            url = URL+"?key="+API_KEY+"&info"+ URLEncoder.encode(msg,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

}
