package com.hgw.baseframe.ui.basemodule.refreshandloadmore.xrecyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hgw.baseframe.R;
import com.hgw.baseframe.adapter.refreshandloadmore.XRecyclerviewAdapter;
import com.hgw.baseframe.base.BaseActivity;
import com.hgw.baseframe.ui.basemodule.http.HttpActivity;
import com.hgw.baseframe.view.customxrecyclerview.CustomXRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：自定义XRecyclerView
 * @author hgw
 * */
public class CustomXRecyclerViewActivity extends BaseActivity implements View.OnClickListener{
    private CustomXRecyclerView mRecyclerView;
    private XRecyclerviewAdapter mAdapter;
    private List<String> listData = new ArrayList<>();
    private int pageIndex = 1;
    private boolean isFirstIn=true;

    /**
     * 入口
     * @param context
     */
    public static void toActivity(Context context) {
        Intent intent = new Intent(context, CustomXRecyclerViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xrecyclerview);

        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isFirstIn){
            pageIndex=1;
            //loadData(false);
            mRecyclerView.refresh(); //主动触发一次刷新
        }else {
            isFirstIn=false;
        }
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(this);

        mRecyclerView = (CustomXRecyclerView)this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this); //list模式
        //GridLayoutManager layoutManager = new GridLayoutManager(this,3); //grid模式，3列显示
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setPullRefreshEnabled(true); //是否允许刷新
        mRecyclerView.setLoadingMoreEnabled(true); //是否允许加载更多

        //添加item布局
        View header = LayoutInflater.from(this).inflate(R.layout.activity_xrecyclerview_item_view, (ViewGroup)findViewById(android.R.id.content),false);
        mRecyclerView.addHeaderView(header);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShortToast("点击了item view");
            }
        });

        //XRecyclerView刷新、加载更多监听
        mRecyclerView.setLoadingListener(new CustomXRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageIndex=1;
                loadData(false);
            }
            @Override
            public void onLoadMore() {
                pageIndex++;
                loadData(true);
            }
        });

        mAdapter = new XRecyclerviewAdapter(this,listData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.refresh(); //主动触发一次刷新

        mAdapter.setItemClickListener(new XRecyclerviewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //header view位置是0，加入图片view位置是1，所以列表从位置2开始
                showShortToast("点击="+(position-2));
            }

            @Override
            public void onItemLongClick(View view, int position) {
                //header是0，加入图片view是1，所以列表从位置2开始
                showShortToast("长按="+(position-2));
            }

            @Override
            public void onItemSubViewClick(View view, int position) {
                showShortToast("item中的子view="+(position));
            }
        });
    }

    private void loadData(final boolean isLoadMore) {
        new Handler().postDelayed(new Runnable(){
            public void run() {
                List<String> tempData = new ArrayList<>();
                if(!isLoadMore){
                    //模拟刷新数据
                    tempData.clear();
                    for(int i = 0; i < 10 ;i++){
                        tempData.add("刷新的数据" + i);
                    }
                }else{
                    //模拟加载更多数据
                    if(pageIndex<3) {
                        for (int i = 0; i < 10; i++) {
                            tempData.add("加载更多的数据" + i);
                        }
                    }
                }
                setData(isLoadMore,tempData);
            }
        }, 1000);
    }

    private void setData(boolean isLoadMore, List<String> data) {
        if(!isLoadMore){
            //刷新数据
            listData.clear();
            listData.addAll(data);

            mRecyclerView.refreshComplete(); //触发刷新完成
            mAdapter.notifyDataSetChanged();
        }else{
            //加载更多数据
            listData.addAll(data);

            if(data!=null && data.size()>0){
                mRecyclerView.loadMoreComplete(); //触发加载更多完成
                mAdapter.notifyDataSetChanged();
            }else{
                mRecyclerView.setNoMore(true); //触发“没有了”提示
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.back:
                /**返回*/
                this.finish();
                break;

            default:
                break;
        }
    }

}
