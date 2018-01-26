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
import com.share.locker.dto.UserDTO;
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
    private final String KEY_OPERATION_SETTING = "KEY_OPERATION_SETTING";

    private View view;

    private OperationSettingDTO operationSettingDTO;

    @ViewInject(R.id.hot_item_list)
    private RecyclerView hotItemRclView;
    private HomeRecyclerViewAdapter homeRecyclerViewAdapter;

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadOperationData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //xutil 渲染fragment
        view = x.view().inject(this, inflater, container);

        if (operationSettingDTO == null && savedInstanceState != null
                && savedInstanceState.getSerializable(KEY_OPERATION_SETTING) != null) {
            //Fragment被回收
            operationSettingDTO = (OperationSettingDTO) savedInstanceState.getSerializable(KEY_OPERATION_SETTING);
        }

        showOperationDataOnUi();

        return view;
    }

    /**
     * 销毁前保存数据，避免下次显示时去请求服务器
     *
     * @param savedBundle
     */
    @Override
    public void onSaveInstanceState(Bundle savedBundle) {
        super.onSaveInstanceState(savedBundle);

        savedBundle.putSerializable(KEY_OPERATION_SETTING, operationSettingDTO);
    }


    //加载banner和operation数据，然后显示
    private void loadOperationData() {
        LockerHttpUtil.postJson(URL_GET_OPERATION_DATA, new HashMap<String, String>(),
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        //系统初始化完成后，执行界面操作
                        getActivity().runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        operationSettingDTO = (OperationSettingDTO) JsonUtil.json2Object(successData, OperationSettingDTO.class);

                                        showOperationDataOnUi();
                                    }
                                });
                    }

                    @Override
                    public void processFail(String failData) {
                        GlobalManager.dialogManager.showErrorDialog("服务端处理失败：" + failData);
                    }
                });
    }

    //运营数据加载完成后，显示到UI
    private void showOperationDataOnUi() {
        if(operationSettingDTO != null) {
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

}

