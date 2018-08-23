package com.hgw.baseframe.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hgw.baseframe.view.LoadingProgressDialog;

/**
 * 描述：Fragment基类
 * @author hgw
 * */
public class BaseFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
		
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	/**显示短时间的Toast*/
	public void showShortToast(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
	}

	/**显示长时间的Toast*/
	public void showLongToast(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
	}

	/**显示progressDialog*/
	public void showProgressDialog(String message) {
		LoadingProgressDialog.showProgressDialog(getActivity(),message);
	}

	/**关闭progressDialog*/
	public void hideProgressDialog() {
		LoadingProgressDialog.hideProgressDialog();
	}

}
