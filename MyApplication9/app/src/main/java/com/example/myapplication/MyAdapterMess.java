package com.example.myapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterMess extends RecyclerView.Adapter<MyAdapterMess.MyViewHolder> {
    private List<TestData> mDataset = new ArrayList<>();
    private IOnItemClickListener mItemClickListener;
    private Context mContext;




    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIndex;
        private TextView tvTitle;
        private TextView tvHot;
        private View contentView;
        private ImageView imHead;
        private TextView tvInfo;


        public MyViewHolder(View v) {
            super(v);
            contentView = v;
            imHead = v.findViewById(R.id.iv_head1);
            tvIndex = v.findViewById(R.id.tv_index1);
            tvTitle = v.findViewById(R.id.tv_title1);
            tvInfo = v.findViewById(R.id.tv_info1);
            tvHot = v.findViewById(R.id.tv_hot1);
        }

        public void onBind(int position, TestData data) {
            //tvIndex.setText(new StringBuilder().append(position).append(".  ").toString());
            tvTitle.setText(data.title);
            tvHot.setText(data.date);
            tvInfo.setText(data.info);
        }

        public void setOnClickListener(View.OnClickListener listener) {
            if (listener != null) {
                contentView.setOnClickListener(listener);
            }
        }

        public void setOnLongClickListener(View.OnLongClickListener listener) {
            if (listener != null) {
                contentView.setOnLongClickListener(listener);
            }
        }
    }



    public MyAdapterMess(List<TestData> myDataset) {
        mDataset.addAll(myDataset);
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        mItemClickListener = listener;
    }
    public MyAdapterMess(Context context) {
        this.mContext = context;
    }

    public void addData(int position, TestData data) {
        mDataset.add(position, data);
        notifyItemInserted(position);
        if (position != mDataset.size()) {
            //刷新改变位置item下方的所有Item的位置,避免索引错乱
            notifyItemRangeChanged(position, mDataset.size() - position);
        }
    }

    public void removeData(int position) {
        if (null != mDataset && mDataset.size() > position) {
            mDataset.remove(position);
            notifyItemRemoved(position);
            if (position != mDataset.size()) {
                //刷新改变位置item下方的所有Item的位置,避免索引错乱
                notifyItemRangeChanged(position, mDataset.size() - position);
            }
        }
    }

    @Override
    public MyAdapterMess.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item_mess, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //holder.imHead.setImageResource();
        holder.onBind(position, mDataset.get(position));
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemCLick(position, mDataset.get(position));
                }
            }
        });
        holder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemLongCLick(position, mDataset.get(position));
                }
                return false;
            }

        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface IOnItemClickListener {

        void onItemCLick(int position, TestData data);

        void onItemLongCLick(int position, TestData data);
    }
}
