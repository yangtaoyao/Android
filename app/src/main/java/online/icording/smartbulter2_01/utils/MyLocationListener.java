package online.icording.smartbulter2_01.utils;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;

/**
 * 项目名     SmartBulter2.01
 * 包名      online.icording.smartbulter2_01.utils
 * 创建时间   2018/5/12 0012 11:50
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class MyLocationListener extends BDAbstractLocationListener {
    private StringBuffer sb=new StringBuffer(256);
    @Override
    public void onReceiveLocation(BDLocation location) {
        //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
        //以下只列举部分获取地址相关的结果信息
        //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

        double latitude = location.getLatitude();    //获取纬度信息
        double longitude = location.getLongitude();    //获取经度信息
        float radius = location.getRadius();    //获取定位精度，默认值为0.0f

        String coorType = location.getCoorType();
        //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

        int errorCode = location.getLocType();
        //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

        String addr = location.getAddrStr();    //获取详细地址信息
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
        sb.append(addr);
        sb.append("\n");

        L.e(sb.toString());
        L.e("定位结束！");

        //移动到我的位置
        //设置缩放
        MapStatusUpdateFactory.zoomTo(18);
    }
}