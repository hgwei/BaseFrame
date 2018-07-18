package com.hgw.baseframe.ui.main.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hgw.baseframe.R;
import com.hgw.baseframe.base.ui.BaseFragment;
import com.hgw.baseframe.util.LogHelper;

/**
 * 描述：最新闻
 * @author hgw
 * */
public final class HomeFragment extends BaseFragment implements View.OnClickListener{
	private boolean param1;
	private String param2;

	public static HomeFragment getInstance(boolean param1, String param2) {
		HomeFragment fragment = new HomeFragment();
		Bundle args = new Bundle();
		args.putBoolean("param1", param1);
		args.putString("param2", param2);
		fragment.setArguments(args);
		return fragment;
    }
    
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_home, container,false);
		
		initView(view);
		LogHelper.showLog("param1="+param1);
		LogHelper.showLog("param2="+param2);
		return view;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		param1 = getArguments().getBoolean("param1");
		param2 = getArguments().getString("param2");
	}

	private void initView(View view) {
		view.findViewById(R.id.retrofit).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.retrofit:
				/**retrofit*/
				RetrofitActivity.toActivity(getActivity());
				break;
			default:
				break;
		}
	}

	@Override
	protected void initPresenter() {

	}
}
