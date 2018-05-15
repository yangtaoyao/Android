package online.icording.smartbulter2_01.entity;

import java.io.Serializable;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.entity
 * 创建时间   2018/4/22 0022 0:27
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class BookData implements Serializable {
    private String online="";//详情url
    private String img="";//
    private String title="";//名
    private String catalog="";//类型 外国文学 小说 经典名著 ",
    private String tags="";//世界名著 好书推荐 文学经典 科幻小说 讽喻小说
    private String sub1="";//简介
    private String sub2="";//简介
    private String reading="";//782人阅读

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSub1() {
        return sub1;
    }

    public void setSub1(String sub1) {
        this.sub1 = sub1;
    }

    public String getSub2() {
        return sub2;
    }

    public void setSub2(String sub2) {
        this.sub2 = sub2;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }






}
