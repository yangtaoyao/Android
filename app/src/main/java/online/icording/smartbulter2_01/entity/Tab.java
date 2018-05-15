package online.icording.smartbulter2_01.entity;

/**
 * 项目名     FragmentTabHost
 * 包名      online.icording.fragmenttabhost
 * 创建时间   2018/4/24 0024 23:13
 * 创建者    yangtaoyao
 * 描述      TODO
 **/
public class Tab {
    private int title;
    private int image;
    private Class fragment;

    public Tab(int title, int image, Class fragment) {
        this.title = title;
        this.image = image;
        this.fragment = fragment;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
