package online.icording.smartbulter2_01.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.view.TitleBuilder;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.ui
 * 创建时间   2018/4/23 0023 21:34
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class OpenDoorActivity extends BaseActivity{
    private TextView mOpen_doorText;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_door);

        new TitleBuilder(OpenDoorActivity.this).setTitleText("")
                .setLeftImage(R.drawable.backicon)
                .setLightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        findView();

    }

    private void findView() {
        mOpen_doorText=findViewById(R.id.mOpen_doorText);
//        key	String	是	应用APPKEY
// 	time	String	是	时间戳（10位），如：1418816972
// 	sort	String	是	类型，desc:指定时间之前发布的，asc:指定时间之后发布的
// 	page	Int	否	当前页数,默认1
// 	rows	Int	否	返回记录条数，默认rows=20,最大50
        Random rand = new Random();
        int page = rand.nextInt(100);
        RxVolley.get("http://api.avatardata.cn/Joke/QueryJokeByTime?key=63af9f2fbb2a4edbb4f622adfc94450c&page="+page+"&rows=1&sort=dasc&time=1418745237", new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);
            }
        });
    }


    private void parsingJson(String t) {
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(t);
            JSONArray jsonList=jsonObject.getJSONArray("result");
            JSONObject text= (JSONObject) jsonList.get(0);
            mOpen_doorText.setText( text.getString("content"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
