package online.icording.smartbulter2_01.entity.movie;

import java.io.Serializable;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.entity
 * 创建时间   2018/4/22 0022 0:27
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class MovieData implements Serializable {
    public static final int TYPE_ONE=1;
    public static final int TYPE_TWO=2;
    public static final int TYPE_THREE=3;

    private int item_type;

    private String url="";//详情
    private String imgUrl="";//剧照
    private String tvTitle="";//电影名
    private String star="";//主演
    private String type="";//类型

    private String director="";//导演
    private String director_link="";

    private String grade="";//评分

    private String playDate="";//上映日期
    private String story="";//剧情



    public int getItem_type() {
        return item_type;
    }

    public void setItem_type(int item_type) {
        this.item_type = item_type;
    }
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
    public String getPlayDate() {
        return playDate;
    }

    public void setPlayDate(String playDate) {
        this.playDate = playDate;
    }

    public String getDirector_link() {
        return director_link;
    }

    public void setDirector_link(String director_link) {
        this.director_link = director_link;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
