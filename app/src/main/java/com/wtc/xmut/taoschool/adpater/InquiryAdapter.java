package com.wtc.xmut.taoschool.adpater;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.InquiryExt;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

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
    protected void convert(ViewHolder holder, InquiryExt inquiryExt, int position) {

        holder.setText(R.id.tv_money, "￥" + inquiryExt.getIprice()+"");
        //holder.setText(R.id.tv_description, inquiryExt.getIshopname()+"");
        holder.setText(R.id.tv_user_name, inquiryExt.getName()+"");
        holder.setText(R.id.tv_fromwhere, "來自" + inquiryExt.getCollege()+"");
        holder.setText(R.id.tv_shoptime, inquiryExt.getItime()+"");
        ImageView view = holder.getView(R.id.sld_usericon);
        Glide.with(mContext).load(ServerApi.SHOWPIC+inquiryExt.getIconpath()).placeholder(R.drawable.loadding).into(view);
    }
}
