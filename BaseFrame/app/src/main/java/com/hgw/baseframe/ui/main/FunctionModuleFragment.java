package com.hgw.baseframe.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hgw.baseframe.R;
import com.hgw.baseframe.base.BaseFragment;
import com.hgw.baseframe.ui.fileopen.FileOpenActivity;
import com.hgw.baseframe.ui.tts.TTSActivity;

/**
 * 描述：功能模块 Fragment
 * @author hgw
 * */
public final class FunctionModuleFragment extends BaseFragment implements View.OnClickListener {
    
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_function_module, container,false);
		
		initView(view);
		return view;
	}

	private void initView(View view) {
		view.findViewById(R.id.fileOnlineOpen).setOnClickListener(this);
		view.findViewById(R.id.TTSOnline).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.fileOnlineOpen:
				/**文件在线预览*/
				FileOpenActivity.toActivity(getActivity());
				break;
			case R.id.TTSOnline:
				/**语音离线合成*/
				TTSActivity.toActivity(getActivity());
				break;
			default:
				break;
		}
	}

}
