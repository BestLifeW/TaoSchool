package com.wtc.xmut.taoschool.adpater;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.domain.InquiryComment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianchaowang on 17-4-3.
 */

public class InquiryCommentAdapter extends BaseAdapter {

    private static final String TAG = "CommentAdapter";
    private List<InquiryComment> comments = new ArrayList<>();
    private String username;
    private Context mContext;

    public InquiryCommentAdapter(Context context, List<InquiryComment> comment, String username) {
        this.mContext = context;
        this.comments = comment;
        this.username = username;
        Log.i(TAG, "CommentAdapter: " + comment.size());
    }


    @Override
    public int getCount() {

        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // 重用convertView
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_comment, null);
            holder.comment_name = (TextView) convertView.findViewById(R.id.comment_name);
            holder.comment_content = (TextView) convertView.findViewById(R.id.comment_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 适配数据
        if (comments.get(position).getUsername().equalsIgnoreCase(username)) {
            holder.comment_name.setText("主人:");
        } else {
            holder.comment_name.setText(comments.get(position).getUsername() + "：");
        }
        holder.comment_content.setText(comments.get(position).getContent());

        return convertView;
    }

    /**
     * 静态类，便于GC回收
     */
    static class ViewHolder {
        TextView comment_name;
        TextView comment_content;
    }

    public void addComment(InquiryComment comment) {
        comments.add(comment);
        notifyDataSetChanged();
    }
}
