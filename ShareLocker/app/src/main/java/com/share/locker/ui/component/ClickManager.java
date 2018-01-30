package com.share.locker.ui.component;

import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jordan on 27/01/2018.
 */

public class ClickManager {
    private static final long CLICK_SPARE_TIME = 3000;  //3秒
    public static Map<String,List<Long>> clickTimeMap = new HashMap<>();

    /**
     * 判断是否触发多次点击
     * @param clickCode
     * @param times
     * @return
     */
    public static boolean isTriggered(String clickCode,int times){
        List<Long> list = clickTimeMap.get(clickCode);
        if(list == null || list.size() < 1){
            clickTimeMap.put(clickCode,new ArrayList<Long>(10));
            if(times == 1){
                return true;
            }else {
                clickTimeMap.get(clickCode).add(System.currentTimeMillis());//记录当前时间
                return false;
            }
        }
        //小于间隔时间，则计数，否则清零
        Long lastClickTime = list.get(list.size()-1);
        Long currentClickTime = System.currentTimeMillis();
        if(currentClickTime - lastClickTime < CLICK_SPARE_TIME){
            list.add(currentClickTime);
        }else{
            clickTimeMap.put(clickCode,new ArrayList<Long>(10));
        }
        //判断是否触发
        boolean result = (list.size() == times);
        if(result){
            clickTimeMap.put(clickCode,new ArrayList<Long>(10));
            return true;
        }else{
            return false;
        }
    }

}
