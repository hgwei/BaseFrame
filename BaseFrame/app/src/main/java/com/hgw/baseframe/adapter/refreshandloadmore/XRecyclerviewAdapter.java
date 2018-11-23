package com.hgw.baseframe.adapter.refreshandloadmore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hgw.baseframe.R;

import java.util.List;

/**
 * 描述：RecyclerView-适配器
 * @author hgw
 * */
public class XRecyclerviewAdapter extends RecyclerView.Adapter<XRecyclerviewAdapter.MViewHolder> {
	private Context mContext;
	private List<String> listData;
	/**item点击回调接口*/
	private ItemClickListener mItemClickListener;

	public XRecyclerviewAdapter(Context context, List<String> listData) {
		super();
		this.mContext = context;
		this.listData = listData;
	}

	@Override
	public int getItemCount() {
		return listData == null ? 0 : listData.size();
	}

	@Override
	public MViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
		return new MViewHolder(LayoutInflater.from(mContext).inflate(R.layout.activity_xrecyclerview_list_item,viewGroup,false));
	}

	@Override
	public void onBindViewHolder(final MViewHolder mViewHolder, final int postion) {
		mViewHolder.name.setText(listData.get(postion));
		mViewHolder.image.setBackgroundResource(R.mipmap.ic_launcher);

		// Item 内部View添加监听回调
		mViewHolder.image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != mItemClickListener) {
					mItemClickListener.onItemSubViewClick(mViewHolder.image,postion);
				}
			}
		});
	}

	public class MViewHolder extends RecyclerView.ViewHolder {
		public TextView name;
		public ImageView image;

		public MViewHolder(final View view) {
			super(view);
			this.name = (TextView) view.findViewById(R.id.name);
			this.image = (ImageView) view.findViewById(R.id.image);

			//为item添加普通点击回调
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (null != mItemClickListener) {
						mItemClickListener.onItemClick(view, getPosition());
					}
				}
			});

			//为item添加长按回调
			view.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					if (null != mItemClickListener) {
						mItemClickListener.onItemLongClick(view, getPosition());
					}
					return true;
				}
			});
		}
	}

	/**注册item点击回调接口*/
	public void setItemClickListener(ItemClickListener mItemClickListener) {
		this.mItemClickListener = mItemClickListener;
	}

	/**定义item点击回调接口*/
	public interface ItemClickListener {
		//Item 普通点击
		void onItemClick(View view, int position);
		//Item 长按
		void onItemLongClick(View view, int position);
		//Item 内部View点击
		void onItemSubViewClick(View view, int position);
	}

}



