package com.share.locker.ui;

/**
 * 封装底部主tab的菜单数据
 * Created by ruolan on 2015/11/29.
 */
public class MainTabData {

    private int title;
    private int image;
    private Class fragment;

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
