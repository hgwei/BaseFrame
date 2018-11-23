package com.hgw.baseframe.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hgw.baseframe.R;
import com.hgw.baseframe.base.BaseFragment;

/**
 * 描述：功能模块 Fragment
 * @author hgw
 * */
public final class FunctionModuleFragment extends BaseFragment{
    
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_function_module, container,false);
		
		initView(view);
		return view;
	}

	private void initView(View view) {
		
	}

}
