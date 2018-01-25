package com.share.locker.ui.main;

/**
 * 封装底部主tab的菜单数据
 * Created by ruolan on 2015/11/29.
 */
public class MainTabData {

    private int title;
    private int image;
    private Class fragment;

    /**
     * @param title 标题
     * @param image 背景图标
     * @param fragment 对应的Fragment类
     */
    public MainTabData(int title, int image, Class fragment) {
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
