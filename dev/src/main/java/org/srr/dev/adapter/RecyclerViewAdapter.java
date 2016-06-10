package org.srr.dev.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class RecyclerViewAdapter<T extends ViewHolder> extends
        RecyclerView.Adapter<ViewHolder> {
    private OnItemClickLitener l;

    public void setOnItemClickLitener(OnItemClickLitener l) {
        this.l = l;
    }

    public interface OnItemClickLitener {
        void onItemClick(View views, int position);

        boolean onItemLongClick(View views, int position);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        onBindHolder(viewHolder, i);
        if (l != null) {
            viewHolder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    l.onItemClick(viewHolder.itemView, i);
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    boolean isclick = l.onItemLongClick(viewHolder.itemView, i);
                    return isclick;
                }
            });

        }
    }

    public abstract void onBindHolder(ViewHolder viewHolder, int i);
}
