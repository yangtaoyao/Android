package online.icording.smartbulter2_01.entity;

import cn.bmob.v3.BmobUser;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.entity
 * 创建时间   2018/4/16 0016 15:40
 * 创建者    yangtaoyao
 * 描述      用户属性 拓展属性
 **/
public class Myuser extends BmobUser {

    private int age;
    private boolean sex;
    private String desc;


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
