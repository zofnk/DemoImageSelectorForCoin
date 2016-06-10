package org.srr.dev.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import org.srr.dev.R;
import org.srr.dev.adapter.RecyclerViewAdapter;
import org.srr.dev.util.DimensionsUtil;

public class EmojiPopWindow {

    public interface OnItemClickLitener {
        void CheckEmoji(int emoji, int position);
    }


    public void showPopupWindow(View view, Context context, final OnItemClickLitener l) {
        final int emojis[] = new int[72];
        for (int i = 0; i < emojis.length; i++) {
            int img_resid = context.getResources().getIdentifier("emoji_" + (i + 1), "mipmap", context.getPackageName());
            emojis[i] = img_resid;
        }
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.pop_select_emoji, null);
        RecyclerView selectalbum_list = (RecyclerView) contentView.findViewById(R.id.pop_select_emoji_list);
        selectalbum_list.setLayoutManager(new GridLayoutManager(context, 8));

        EmojiAdapter emoji_adapter = new EmojiAdapter(emojis);
        selectalbum_list.setAdapter(emoji_adapter);

        final PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, DimensionsUtil.dip2px(180, context), true);

        popupWindow.setTouchable(true);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.bg_dialog));

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1] - popupWindow.getHeight());
        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

        emoji_adapter.setOnItemClickLitener(new RecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View views, int position) {
                l.CheckEmoji(emojis[position],position);
                popupWindow.dismiss();
            }

            @Override
            public boolean onItemLongClick(View views, int position) {
                return false;
            }
        });
    }


    /**
     * emoji列表适配器
     */
    public class EmojiAdapter extends RecyclerViewAdapter<EmojiAdapter.ViewHolder> {
        private final int[] emojis;

        public EmojiAdapter(int[] emojis) {
            this.emojis = emojis;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView emoji;

            public ViewHolder(View v) {
                super(v);
            }
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public EmojiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          final int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_emoji, parent, false);
            ViewHolder holder = new ViewHolder(v);
            holder.emoji = (ImageView) v.findViewById(R.id.item_emoji_img);
            return holder;
        }

        @Override
        public int getItemCount() {
            return emojis.length;
        }

        @Override
        public void onBindHolder(RecyclerView.ViewHolder viewHolder,
                                       int position) {
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.emoji.setImageResource(emojis[position]);
        }

    }
}
