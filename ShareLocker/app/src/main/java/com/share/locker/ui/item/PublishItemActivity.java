package com.share.locker.ui.item;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.share.locker.R;
import com.share.locker.common.BizUtil;
import com.share.locker.common.Constants;
import com.share.locker.ui.component.BaseActivity;
import com.share.locker.ui.main.MainActivity;
import com.share.locker.vo.SelectableLockerVO;
import com.share.locker.common.MockUtil;
import com.share.locker.http.HttpCallback;
import com.share.locker.http.LockerHttpUtil;
import com.share.locker.ui.component.HorizontalListView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import com.share.locker.ui.component.GifSizeFilter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_publish_item)
public class PublishItemActivity extends BaseActivity {
    private static final String TAG_LOG = "PublishItemActivity";
    private static final int REQUEST_CODE_ADD_PHOTO = 21;//定义请求码常量
    private static final String URL_PUBLISH_ITEM = Constants.URL_BASE+"publishItem.json";

    @ViewInject(R.id.publish_back_btn)
    private Button backBtn;

    @ViewInject(R.id.publish_add_photo_layout)
    private LinearLayout addPhotoLayout;
    @ViewInject(R.id.publish_photo_list)
    private HorizontalListView photoListView;

    @ViewInject(R.id.publish_title)
    private EditText titleTxt;

    @ViewInject(R.id.publish_price_time_txt)
    private EditText priceTimeTxt;
    @ViewInject(R.id.publish_price_time_unit_selector)
    private Spinner priceTimeUnitSelector;
    @ViewInject(R.id.publish_price_txt)
    private EditText priceTxt;

    @ViewInject(R.id.publish_deposit_txt)
    private EditText depositTxt;

    @ViewInject(R.id.publish_locker_size_selector)
    private Spinner lockerSizeSelector;

    @ViewInject(R.id.publish_machine_selector)
    private Spinner machineSelector;

    @ViewInject(R.id.publish_description_txt)
    private EditText descriptionTxt;

    @ViewInject(R.id.publish_status_cbox)
    private CheckBox publishStatusCBox;

    @ViewInject(R.id.publish_submit_btn)
    private Button submitBtn;

    private List<Uri> photoUrlList; //选择的图片URI
    private SelectableLockerVO[] selectableLockerVOArr;   //绑定的箱柜selector的数据源

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //xutil注入
        x.view().inject(this);

        addEventListener();
    }

    private void addEventListener() {
        //柜门尺寸改变后，结合当前位置，重新加载可选择的箱柜列表
        lockerSizeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectableLockerVOArr = MockUtil.getMatchedMachine(i);  //TODO 应该去服务端取数据
                ArrayAdapter<String> machineSelectorAdapter = new ArrayAdapter<String>(PublishItemActivity.this,
                        android.R.layout.simple_spinner_item, getMachineNameArr(selectableLockerVOArr));
                machineSelector.setAdapter(machineSelectorAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    //返回按钮
    @Event(value = R.id.publish_back_btn, type = View.OnClickListener.class)
    private void onClickBackBtn(View view) {
        //返回到main activity
        finish();
    }

    //添加照片按钮
    @Event(value = R.id.publish_add_photo_layout, type = View.OnClickListener.class)
    private void onClickAddPhotoBtn(View view) {
        Matisse.from(PublishItemActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider"))
                .maxSelectable(9)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(
                        getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_ADD_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_PHOTO && resultCode == RESULT_OK) {
            //取到选中的图片path,显示到list中
            photoUrlList = Matisse.obtainResult(data);
            if (photoUrlList != null && photoUrlList.size() > 0) {
                PublishPhotoListItemData[] itemDataArr = new PublishPhotoListItemData[photoUrlList.size()];
                for (int i = 0; i < photoUrlList.size(); i++) {
                    PublishPhotoListItemData itemData = new PublishPhotoListItemData();
                    itemData.setImgUri(photoUrlList.get(i));
                    itemDataArr[i] = itemData;
                }
                PublishPhotoListAdapter listAdapter = new PublishPhotoListAdapter(this, itemDataArr);
                photoListView.setAdapter(listAdapter);
            }
        }
    }

    //提交表单 的按钮
    @Event(value = R.id.publish_submit_btn, type = View.OnClickListener.class)
    private void onClickSubmitBtn(View view) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("title",titleTxt.getText().toString());
        paramMap.put("priceTime",priceTimeTxt.getText().toString());
        paramMap.put("priceTimeUnit",getPriceTimeUnitCode(priceTimeUnitSelector.getSelectedItemPosition()));
        paramMap.put("price",priceTxt.getText().toString());
        paramMap.put("deposit",depositTxt.getText().toString());
        paramMap.put("lockerSize",getLockerSizeCode(lockerSizeSelector.getSelectedItemPosition()));
        paramMap.put("lockerId",String.valueOf(getSelectedLockerId(machineSelector.getSelectedItemPosition())));
        paramMap.put("description",descriptionTxt.getText().toString());
        paramMap.put("publishStatus",String.valueOf(publishStatusCBox.isChecked()));
        List<File> imgList = new ArrayList<>();
        for(Uri uri : photoUrlList){
            imgList.add(BizUtil.convertUriToFile(uri,this));
        }
        LockerHttpUtil.postFileJson(URL_PUBLISH_ITEM, paramMap, imgList,
                new HttpCallback() {
                    @Override
                    public void processSuccess(final String successData) {
                        runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  Intent successIntent = new Intent(PublishItemActivity.this, PublishItemSuccessActivity.class);
                                  successIntent.putExtra("itemId",successData);
                                  successIntent.putExtra("itemTitle",titleTxt.getText().toString());//返回给main，显示已发布成功
                                  startActivity(successIntent);
                                  finish(); //销毁activity
                              }
                          });
                    }

                    @Override
                    public void processFail(String failData) {

                    }
                });
    }

    private String[] getMachineNameArr(SelectableLockerVO[] machineVOArr){
        String[] resultArr = new String[machineVOArr.length];
        for(int i=0;i<machineVOArr.length;i++){
            resultArr[i] = machineVOArr[i].getMachineName();
        }
        return resultArr;
    }

    private String getPriceTimeUnitCode(int selectedPosition){
        Map<Integer,String> map = new HashMap<>();
        map.put(0,"DAY");
        map.put(1,"HALF_DAY");
        map.put(2,"HOUR");
        return map.get(selectedPosition);
    }

    private String getLockerSizeCode(int selectedPosition){
        Map<Integer,String> map = new HashMap<>();
        map.put(0,"MIN");
        map.put(1,"MID");
        map.put(2,"MAX");
        return map.get(selectedPosition);
    }
    private Integer getSelectedLockerId(int selectedPosition){
        return selectableLockerVOArr[selectedPosition].getLockerId();
    }

}
