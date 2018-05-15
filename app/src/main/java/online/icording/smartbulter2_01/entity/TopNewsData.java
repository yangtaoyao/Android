package online.icording.smartbulter2_01.entity;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.entity
 * 创建时间   2018/4/22 0022 0:27
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class TopNewsData {
    public static final int TYPE_ONE=1;
    public static final int TYPE_TWO=2;
    public static final int TYPE_THREE=3;
    private int type;

    private String title;
    private String source;
    private String wechat_date;
    private String imgUrl="";
    private String imgUrl02="";
    private String imgUrl03="";
    private String newUrl;

    public String getImgUrl02() {
        return imgUrl02;
    }

    public void setImgUrl02(String imgUrl02) {
        this.imgUrl02 = imgUrl02;
    }

    public String getImgUrl03() {
        return imgUrl03;
    }

    public void setImgUrl03(String imgUrl03) {
        this.imgUrl03 = imgUrl03;
    }




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNewUrl() {
        return newUrl;
    }

    public void setNewUrl(String newUrl) {
        this.newUrl = newUrl;
    }

    public String getWechat_date() {
        return wechat_date;
    }

    public void setWechat_date(String wechat_date) {
        this.wechat_date = wechat_date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
