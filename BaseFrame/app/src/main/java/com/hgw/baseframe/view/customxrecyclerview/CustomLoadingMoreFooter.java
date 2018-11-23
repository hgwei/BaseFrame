package com.hgw.baseframe.view.customxrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hgw.baseframe.R;

/**
 * 描述：自定义XRecyclerView的Footer（LoadingMoreFooter）
 * @author hgw
 */
public class CustomLoadingMoreFooter extends LinearLayout {
    private LinearLayout mContainer;

    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;
    private ProgressBar mProgressBar;
    private TextView mText;


    public CustomLoadingMoreFooter(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public CustomLoadingMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    public void initView(){
        mContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.xrecyclerview_custom_footer, null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);

        addView(mContainer,lp);

        mText = (TextView)findViewById(R.id.xrecyclerview_foot_more);
        mProgressBar= (ProgressBar) findViewById(R.id.xrecyclerview_foot_progress);
    }

    public void  setState(int state) {
        switch(state) {
            case STATE_LOADING:
                mProgressBar.setVisibility(View.VISIBLE);
                mText.setText("正在努力加载中...");
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                mText.setText("正在努力加载中...");
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                mText.setText("没有更多了");
                mProgressBar.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }
    }
}

