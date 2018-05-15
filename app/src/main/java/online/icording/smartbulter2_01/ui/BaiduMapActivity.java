package online.icording.smartbulter2_01.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import online.icording.smartbulter2_01.R;
import online.icording.smartbulter2_01.utils.L;
import online.icording.smartbulter2_01.utils.MyLocationListener;
import online.icording.smartbulter2_01.view.CustomPopWindow;
import online.icording.smartbulter2_01.view.TitleBuilder;

/**
 * 项目名     SmartBulter2.01
 * 包名      online.icording.smartbulter2_01.ui
 * 创建时间   2018/5/11 0011 22:27
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class BaiduMapActivity extends BaseActivity {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private ImageView goto_my_poition;
    private TextView addr;
    private LinearLayout show_addr;
    //定位
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_map);
        initView();
        //设置标题栏
        new TitleBuilder(this).setBgRes(R.color.colorMapTitlebg)
                .setTitleText("地图")
                .setLeftImage(R.drawable.backicon)
                .setLightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    private void initView() {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        addr=findViewById(R.id.addr);
        show_addr=findViewById(R.id.show_addr);
        mBaiduMap=mMapView.getMap();
        goto_my_poition=findViewById(R.id.goto_my_poition);
        goto_my_poition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //移动
                MapStatusUpdate mapLatelng=MapStatusUpdateFactory.newLatLng(new LatLng(myListener.latitude,myListener.longitude));
                mBaiduMap.setMapStatus(mapLatelng);

                //位置显示
                show_addr.setVisibility(View.VISIBLE);
                addr.setText(myListener.addr);
            }
        });
        show_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_addr.setVisibility(View.GONE);
            }
        });
        //定位
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        initLocation();
        mLocationClient.start();
        //mLocationClient为第二步初始化过的LocationClient对象
        //调用LocationClient的start()方法，便可发起定位请求
        L.e("开始定位啦！");
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        private StringBuffer sb=new StringBuffer(256);
        public  double latitude;    //获取纬度信息
        public double longitude;    //获取经度信息
        public String addr="1";
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f
            this.latitude=latitude;
            this.longitude=longitude;

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

            String address = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息

            sb.append("定位结果:");
            sb.append("\nerrorCode:");
            sb.append(errorCode);
            sb.append("\n");
            sb.append(latitude);
            sb.append("\n");
            sb.append(longitude);
            sb.append("\n");
            sb.append(address);
            sb.append("\n");
            this.addr=address;

            L.e(sb.toString());
            L.e("定位结束！");


            //移动到我的位置
            //设置缩放
            MapStatusUpdate mapUpdate=MapStatusUpdateFactory.zoomTo(18);
            mBaiduMap.setMapStatus(mapUpdate);


            //移动
            MapStatusUpdate mapLatelng=MapStatusUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude()));
            mBaiduMap.setMapStatus(mapLatelng);

            //绘制图层
            LatLng point=new LatLng(location.getLatitude(),location.getLongitude());
            BitmapDescriptor bitmap=BitmapDescriptorFactory.fromResource(R.drawable.dingwei);
            OverlayOptions option=new MarkerOptions().position(point).icon(bitmap);
            mBaiduMap.addOverlay(option);
        }
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，设置定位模式，默认高精度
//LocationMode.Hight_Accuracy：高精度；
//LocationMode. Battery_Saving：低功耗；
//LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
//可选，设置返回经纬度坐标类型，默认gcj02
//gcj02：国测局坐标；
//bd09ll：百度经纬度坐标；
//bd09：百度墨卡托坐标；
//海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(0);
//可选，设置发起定位请求的间隔，int类型，单位ms
//如果设置为0，则代表单次定位，即仅定位一次，默认为0
//如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
//可选，设置是否使用gps，默认false
//使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
//可选，定位SDK内部是一个service，并放到了独立进程。
//设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
//可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5*60*1000);
//可选，7.2版本新增能力
//如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setEnableSimulateGps(false);
//可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        option.setIsNeedAddress(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true

        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }


}
