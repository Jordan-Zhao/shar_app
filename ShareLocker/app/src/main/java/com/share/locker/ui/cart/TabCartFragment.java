package com.share.locker.ui.cart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.share.locker.R;
import com.share.locker.ui.component.BaseFragment;


/**
 * Created by ruolan on 2015/11/29.
 */
public class TabCartFragment extends BaseFragment {

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container,false);
        return view;
    }
}
