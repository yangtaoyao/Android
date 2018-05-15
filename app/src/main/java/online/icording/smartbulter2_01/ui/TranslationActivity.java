package online.icording.smartbulter2_01.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.utils.L;
import online.icording.smartbulter2_01.utils.Translation.TransApi;
import online.icording.smartbulter2_01.view.TitleBuilder;

import static android.provider.UserDictionary.Words.APP_ID;

/**
 * 项目名     SmartBulter2.01
 * 包名      online.icording.smartbulter2_01.ui
 * 创建时间   2018/5/4 0004 13:05
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class TranslationActivity extends AppCompatActivity implements View.OnClickListener {

    //
    private TextView explains;

    private TextView web_value01;
    private TextView web_key01;
    private TextView web_value02;
    private TextView web_key02;
    private TextView web_value03;
    private TextView web_key03;

    private String input_text;
    private EditText text_input_translate;
    private Button btn_translate_clear;
    private Button btn_translate_ok;
    private TextView text_translate_return;


    private static final String APP_ID = "20180504000153210";
    private static final String SECURITY_KEY = "BM6AaqmdDvTABgH4mPRY";


    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);

        new TitleBuilder(TranslationActivity.this).setTitleText("翻译")
                .setLeftImage(R.drawable.backicon)
                .setLightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        initView();
    }

    private void initView() {
        explains=findViewById(R.id.explains);
        text_input_translate=findViewById(R.id.text_input_translate);

        web_value01=findViewById(R.id.web_value01);
        web_key01=findViewById(R.id.web_key01);
        web_value02=findViewById(R.id.web_value02);
        web_key02=findViewById(R.id.web_key02);
        web_value03=findViewById(R.id.web_value03);
        web_key03=findViewById(R.id.web_key03);

        btn_translate_clear=findViewById(R.id.btn_translate_clear);
        btn_translate_ok=findViewById(R.id.btn_translate_ok);

        btn_translate_ok.setOnClickListener(this);
        btn_translate_clear.setOnClickListener(this);
    }

    private void parsingJson(String jsonp) {
        int startIndex = jsonp.indexOf("(");
        int endIndex = jsonp.lastIndexOf(")");
        String json = jsonp.substring(startIndex+1, endIndex);
        //Toast.makeText(TranslationActivity.this,json,Toast.LENGTH_SHORT).show();
        L.i(json);
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(json);

            JSONObject jsonBasic=jsonObject.getJSONObject("basic");
            JSONArray explainsArry=jsonBasic.getJSONArray("explains");
            explains.setText(explainsArry.get(0).toString());

            JSONArray webArry=jsonObject.getJSONArray("web");
            JSONArray web_valueArry;
            JSONObject webJson;
            String web_value="";
            for(int i=0;i<webArry.length();i++){
                webJson= (JSONObject) webArry.get(i);
                switch (i){
                    case 0:
                        L.i(webJson.optString("value"));
                        web_valueArry=webJson.getJSONArray("value");
                        for (int n=0;n<web_valueArry.length();n++){
                            if(n==0){
                                web_value="1. "+web_valueArry.get(n);
                            }else {
                                web_value = web_value +  " / " +web_valueArry.get(n);
                            }
                        }
                        web_value01.setText(web_value);
                        web_key01.setText(webJson.optString("key"));
                        break;
                    case 1:
                        L.i(webJson.optString("value"));
                        web_valueArry=webJson.getJSONArray("value");
                        for (int n=0;n<web_valueArry.length();n++){
                            if(n==0){
                                web_value="2. "+web_valueArry.get(n);
                            }else {
                                web_value = web_value +  " / " +web_valueArry.get(n);
                            }
                        }
                        web_value02.setText(web_value);
                        web_key02.setText(webJson.optString("key"));
                        break;
                    case 2:
                        L.i(webJson.optString("value"));
                        web_valueArry=webJson.getJSONArray("value");
                        for (int n=0;n<web_valueArry.length();n++){
                            if(n==0){
                                web_value="3. "+web_valueArry.get(n);
                            }else {
                                web_value = web_value + " / " +web_valueArry.get(n);
                            }
                        }
                        web_value03.setText(web_value);
                        web_key03.setText(webJson.optString("key"));
                        break;
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //java String 转utf-8编码
    public static String getUTF8XMLString(String xml) {
        // A StringBuffer Object
        StringBuffer sb = new StringBuffer();
        sb.append(xml);
        String xmString = "";
        String xmlUTF8="";
        try {
            xmString = new String(sb.toString().getBytes("UTF-8"));
            xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
            System.out.println("utf-8 编码：" + xmlUTF8) ;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // return to String Formed
        return xmlUTF8;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_translate_clear:
                text_input_translate.setText("");
                break;
            case R.id.btn_translate_ok:
                input_text= text_input_translate.getText().toString();
                String UTF8=getUTF8XMLString(input_text);
                if(!input_text.equals("")){
                    RxVolley.get("http://fanyi.youdao.com/openapi.do?keyfrom=neverland" +
                            "&key=969918857" +
                            "&type=data" +
                            "&doctype=jsonp&callback=show&version=1.1" +
                            "&q="+UTF8, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            parsingJson(t);
                            //Toast.makeText(TranslationActivity.this,input_text+t,Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(TranslationActivity.this,"输入框不能为空",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
