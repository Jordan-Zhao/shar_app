package com.share.locker.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.share.locker.R;
import com.share.locker.common.Constants;
import com.share.locker.common.GlobalManager;
import com.share.locker.common.JsonUtil;
import com.share.locker.dto.OperationSettingDTO;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;
import com.share.locker.ui.component.BaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;


/**
 * 首页Fragment
 * Created by ruolan on 2015/11/29.
 */
@ContentView(R.layout.fragment_home)
public class TabHomeFragment extends BaseFragment {
    private final String TAG_LOG = "TabHomeFragment";

    private final String URL_GET_OPERATION_DATA = Constants.URL_BASE + "/home/getOperationData.json";

    private View view;

    @ViewInject(R.id.hot_item_list)
    private RecyclerView hotItemRclView;
    private HomeRecyclerViewAdapter homeRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        //xutil 渲染fragment
        view = x.view().inject(this, inflater, container);

        loadOperationData();

        return view;
    }

    //加载banner和operation数据，然后显示
    private void loadOperationData() {
        LockerHttpUtil.postJson(URL_GET_OPERATION_DATA,new HashMap<String, String>(),
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        //系统初始化完成后，执行界面操作
                        getActivity().runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        showOperationDataOnUi(successData);
                                    }
                                });
                    }

                    @Override
                    public void processFail(String failData) {
                        GlobalManager.dialogManager.showErrorDialog("服务端处理失败："+failData);
                    }
                });
    }

    //运营数据加载完成后，显示到UI
    private void showOperationDataOnUi(String operationDataJson) {
        OperationSettingDTO operationSettingDTO = (OperationSettingDTO)JsonUtil.json2Object(operationDataJson,OperationSettingDTO.class);

        //layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        hotItemRclView.setLayoutManager(layoutManager);

        //list 数据 和 adapter
        homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(getActivity(), operationSettingDTO);
        hotItemRclView.setAdapter(homeRecyclerViewAdapter);

        //创建head view
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.home_list_head, hotItemRclView, false);
        homeRecyclerViewAdapter.setHeadView(headerView);
    }

}




































    /*
    private void testShowBanner(){
        bannerHome = view.findViewById(R.id.banner_home);

        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        bannerHome.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        bannerHome.setImageLoader(//自定义的图片加载器
                new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(context).load((String) path).into(imageView);
                    }
                } );
        //设置图片网址或地址的集合
        List<String> list_path = new ArrayList<>();
        //放标题的集合
        List<String> list_title = new ArrayList<>();

        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        list_title.add("好好学习");
        list_title.add("天天向上");
        list_title.add("热爱劳动");
        list_title.add("不搞对象");
        bannerHome.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        bannerHome.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        bannerHome.setBannerTitles(list_title);
        //设置轮播间隔时间
        bannerHome.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        bannerHome.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        bannerHome.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(new OnBannerListener() {
                    //轮播图的监听方法
                    @Override
                    public void OnBannerClick(int position) {
//                                    Log.i("tag", "你点了第"+position+"张轮播图");
                    }
                })
                //必须最后调用的方法，启动轮播图。
                .start();
    }*/
