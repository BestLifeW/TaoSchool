package com.wtc.xmut.taoschool.adpater;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.InquiryExt;
import com.wtc.xmut.taoschool.ui.activity.InquiryDetailActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import static com.marshalchen.ultimaterecyclerview.expanx.Util.DataUtil.TAG;

/**
 * 作者 By lovec on 2017/5/10.23:12
 * 邮箱 lovecanon0823@gmail.com
 */

public class InquiryAdapter extends CommonAdapter<InquiryExt> {

    private Context mContext;

    public InquiryAdapter(Context context, int layoutId, List<InquiryExt> datas) {
        super(context, layoutId, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, final InquiryExt inquiryExt, int position) {
        Log.i(TAG, "convert: "+inquiryExt.toString());
        holder.setText(R.id.tv_money, "￥" + inquiryExt.getIprice()+"");
        holder.setText(R.id.tv_shopname, inquiryExt.getIshopname()+"");
        holder.setText(R.id.tv_user_name, inquiryExt.getName()+"");
        holder.setText(R.id.tv_fromwhere, "來自" + inquiryExt.getCollege()+"");
        holder.setText(R.id.tv_shoptime, inquiryExt.getItime()+"");
        final ImageView draweeView = holder.getView(R.id.sld_usericon);
        Uri UserIconUri = Uri.parse(ServerApi.SHOWPIC + inquiryExt.getIconpath());
        Log.i(TAG, "convert: "+UserIconUri);
        Glide.with(mContext).load(UserIconUri).placeholder(R.drawable.usericon) .into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource,
                                        GlideAnimation<? super GlideDrawable> glideAnimation) {
                draweeView.setImageDrawable(resource);
            }
        });

        CardView view = holder.getView(R.id.card_view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, InquiryDetailActivity.class);
                intent.putExtra("inquiryid", inquiryExt.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }
}
