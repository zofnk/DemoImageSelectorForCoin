package org.srr.dev.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class RecyclerViewTypeAdapter<E, T extends ViewHolder> extends
        RecyclerView.Adapter<ViewHolder> {

    private ArrayList<E> mData;
    private OnItemClickLitener l;

    public void setOnItemClickLitener(OnItemClickLitener l) {
        this.l = l;
    }

    public interface OnItemClickLitener {
        void onItemClick(View views, int position);

        boolean onItemLongClick(View v, int position);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int i) {

        E e = mData.get(i);
        T t = (T) holder;
        onBindHolder(t, i, e);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        final T viewHolder;
        int layoutId = getLayoutId(viewType);
        v = LayoutInflater.from(parent.getContext()).inflate(
                layoutId, parent, false);
        viewHolder = getViewHolder(v, viewType);

        if (l != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    l.onItemClick(viewHolder.itemView, viewHolder.getAdapterPosition());
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    boolean isclick = l.onItemLongClick(viewHolder.itemView, viewHolder.getAdapterPosition());
                    return isclick;
                }
            });

        }
        return viewHolder;
    }

    public abstract void onBindHolder(T holder, int i, E e);

    public abstract T getViewHolder(View v, int viewType);

    public abstract int getLayoutId(int viewType);

    public ArrayList<E> getData() {
        return mData;
    }

    public void setData(ArrayList<E> mData) {
        this.mData = mData;
    }

    public class BaseViewHolder extends ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }
}
