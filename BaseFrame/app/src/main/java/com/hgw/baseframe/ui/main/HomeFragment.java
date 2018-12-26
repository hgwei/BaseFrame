package com.hgw.baseframe.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hgw.baseframe.R;
import com.hgw.baseframe.base.BaseFragment;
import com.hgw.baseframe.ui.basemodule.http.HttpActivity;

/**
 * 描述：首页
 * @author hgw
 * */
public final class HomeFragment extends BaseFragment implements View.OnClickListener {
    
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_home, container,false);
		
		initView(view);
		return view;
	}

	private void initView(View view) {
    	//view.findViewById(R.id.http).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.http:
				/**http*/
				HttpActivity.toActivity(getActivity());
				break;
			default:
				break;
		}
	}

}
