package online.icording.smartbulter2_01.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.adapter.ChatListAdapter;
import online.icording.smartbulter2_01.entity.ChatListData;
import online.icording.smartbulter2_01.ui.YaoActivity;
import online.icording.smartbulter2_01.view.CustomPopWindow;
import online.icording.smartbulter2_01.view.TitleBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment implements View.OnClickListener {

    //
    private DrawerLayout mdrawerlayout;
    //

    private static final String url=" http://api.avatardata.cn/Tuling/Ask?key=accc236925af47c09eb5e4b3c4771ccf&info=";
    private String info="你好";

    private ListView mListView;
    private EditText bulter_edittext;
    private Button bulter_btn_send;
    private List<ChatListData> mList=new ArrayList<>();
    ChatListAdapter adapter;
    private View rootView;

    private CustomPopWindow mCustomPopWindow;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //避免重复加载UI
        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_chat,null);
            //初始化
            findView(rootView);
            //设置标题栏
            new TitleBuilder(rootView).setTitleText("智能聊天")
                    .setLeftImage(R.drawable.drawer)
                    .setLightOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //mdrawerlayout
                            mdrawerlayout.openDrawer(Gravity.LEFT);
                        }
                    });

        }
        ViewGroup parent= (ViewGroup) rootView.getParent();
        if (parent!=null){
            parent.removeView(rootView);
        }
        return rootView;
    }


    private void findView(View view) {
        bulter_edittext=view.findViewById(R.id.bulter_edittext);
        mListView=view.findViewById(R.id.mChatListView);
        bulter_btn_send=view.findViewById(R.id.bulter_btn_send);
        bulter_btn_send.setOnClickListener(this);
        mdrawerlayout=getActivity().findViewById(R.id.drawerlayout);

        //设置适配器
        adapter=new ChatListAdapter(getActivity(),mList);
        mListView.setAdapter(adapter);
        addLeftItem("您好，小主人，我是小图，很高兴为你服务。");
        addLeftItem("我已经具备了30多项聊天技能，赶快和我聊天试试吧！在这里，你可以查询你想要的生活服务，也可以尽情调侃小图哦。");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bulter_btn_send:
                String ed_text=bulter_edittext.getText().toString();
                //
                if(!ed_text.isEmpty()){
                    addRightText(ed_text);
                    bulter_edittext.setText("");
                    //post
//                    HttpParams params = new HttpParams();
//                    params.put("key","14cde7c10991450eb1b9bcfcfda07ba5");
//                    params.put("info",ed_text);
//                    params.put("userid","1213");
//
//                    RxVolley.post("http://www.tuling123.com/openapi/api", params, new HttpCallback() {
//                        @Override
//                        public void onSuccess(String t) {
//
//                            L.i("-----json:"+t);
//                            Toast.makeText(getContext(),"-----json:"+t,Toast.LENGTH_LONG).show();
//                            Loger.debug("请求到的数据:" + t);
//                        }
//                    });

                    RxVolley.get("http://www.tuling123.com/openapi/api?key=14cde7c10991450eb1b9bcfcfda07ba5"+"&info="+ed_text, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                           parsingJson(t);
                        }
                    });
                }else {
                    Toast.makeText(getActivity(),"输入框不能为空",Toast.LENGTH_LONG).show();
                }

                break;

        }
    }

    private void parsingJson(String t) {
// 100000文本类
// 200000链接类
// 302000新闻类
// 308000菜谱类
// 313000儿歌类
// 314000诗词类
        try {
            JSONObject jsonObject=new JSONObject(t);
            String code=jsonObject.getString("code");
            String text=jsonObject.getString("text");
            addLeftItem(text);
            switch (code){
                case "200000":
                    addLeftItem(jsonObject.getString("url"));
                    break;
                case "302000":
                    JSONArray jsonList1=jsonObject.getJSONArray("list");
                    for(int i=0;i<5;i++){
                        JSONObject json=(JSONObject)jsonList1.get(i);
//                        addLeftItem(json.getString("article")+json.getString("source")+json.getString("icon")+json.getString("detailurl"));
//
                        addLeftItem(i+". "+json.getString("article"));
                        addLeftItem(json.getString("source"));
                        addLeftItem(json.getString("icon"));
                        addLeftItem(json.getString("detailurl"));
                    }
                    break;
                case "308000":
                    JSONArray jsonList2=jsonObject.getJSONArray("list");
                    for(int i=0;i<5;i++){
                        JSONObject json=(JSONObject)jsonList2.get(i);
                        addLeftItem(json.getString("name"));
                        addLeftItem(json.getString("icon"));
                        addLeftItem(json.getString("info"));
                        addLeftItem(json.getString("detailurl"));
                    }
                    break;
                default:
                    break;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //添加左边
    private void addLeftItem(String t){
        ChatListData data=new ChatListData();
        data.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        data.setText(t);
        mList.add(data);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mListView.setSelection(mListView.getBottom());

    }
    //添加右边
    private void addRightText(String t){
        ChatListData data=new ChatListData();
        data.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        data.setText(t);
        mList.add(data);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mListView.setSelection(mListView.getBottom());
    }
}
