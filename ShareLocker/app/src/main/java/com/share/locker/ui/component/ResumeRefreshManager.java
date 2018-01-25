package com.share.locker.ui.component;

import android.content.Intent;

import com.share.locker.vo.ResumeRefreshVO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jordan on 25/01/2018.
 */

public class ResumeRefreshManager {

    //存放各Frag在resume时需要的数据，如果为空，则不更新
    private Map<Integer,ResumeRefreshVO> resumeDataMap = new HashMap<>();

    public void updateResumeData(Integer fragIndex,Map<String,Object> data){
        ResumeRefreshVO vo = new ResumeRefreshVO();
        vo.setNeedRefresh(true);
        vo.setData(data);
        resumeDataMap.put(fragIndex,vo);
    }

    public boolean isNeedRefresh(Integer fragIndex){
        ResumeRefreshVO vo = resumeDataMap.get(fragIndex);
        return (vo != null && vo.isNeedRefresh());
    }

    public Map<String,Object> getResumeRefreshData(Integer fragIndex){
        ResumeRefreshVO vo = resumeDataMap.get(fragIndex);
        if(vo != null && vo.isNeedRefresh()){
            return vo.getData();
        }
        return null;
    }

    public void clearResumeData(Integer fragIndex){
        ResumeRefreshVO vo = new ResumeRefreshVO();
        vo.setNeedRefresh(false);
        resumeDataMap.put(fragIndex,vo);
    }
}
