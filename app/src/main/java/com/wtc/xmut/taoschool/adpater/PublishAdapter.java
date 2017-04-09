package com.wtc.xmut.taoschool.adpater;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.ShopExt;
import com.wtc.xmut.taoschool.ui.activity.ShopDetailActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 作者 By lovec on 2017/2/23 0023.10:56
 * 邮箱 lovecanon0823@gmail.com
 */

public class PublishAdapter extends CommonAdapter<ShopExt> {

    private Context mcontext;
    private long parse;

    public PublishAdapter(Context context, int layoutId, List<ShopExt> datas) {
        super(context, layoutId, datas);
        this.mcontext = context;
    }

    @Override
    protected void convert(ViewHolder holder, final ShopExt shopExt, int position) {
        Uri shopuri = Uri.parse(ServerApi.SHOWPIC + shopExt.getPicture());
        Uri UserIconUri = Uri.parse(ServerApi.SHOWPIC + shopExt.getIconpath());
        ImageView draweeView = holder.getView(R.id.sld_shoppic);
        ImageView user_icon = holder.getView(R.id.sld_usericon);
        CardView view = holder.getView(R.id.card_view);
        Glide.with(mcontext).load(shopuri).placeholder(R.drawable.loadding).into(draweeView);
        Glide.with(mcontext).load(UserIconUri).placeholder(R.drawable.usericon).into(user_icon);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, ShopDetailActivity.class);
                intent.putExtra("ShopId", shopExt.getId());
                mcontext.startActivity(intent);
            }
        });
        holder.setText(R.id.tv_money, "￥" + shopExt.getPrice());
        holder.setText(R.id.tv_description, shopExt.getDescription());
        holder.setText(R.id.tv_user_name, shopExt.getName());
        holder.setText(R.id.tv_fromwhere, "來自" + shopExt.getCollege());
        holder.setText(R.id.tv_shoptime, shopExt.getShoptime());

    }
}
