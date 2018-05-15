package online.icording.smartbulter2_01.entity;

/**
 * 项目名     SmartBulter
 * 包名      online.icording.smartbulter.entity
 * 创建时间   2018/4/17 0017 21:02
 * 创建者    yangtaoyao
 * 描述      对话列表的实体
 **/
public class ChatListData {
    private int type;
    //
    private String text;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
