package com.wtc.xmut.taoschool.adpater;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
        Glide.with(mcontext).load(UserIconUri).placeholder(R.drawable.usericon).into(user_icon);

        CardView view = holder.getView(R.id.card_view);
        view.setBackgroundColor(Color.WHITE);
        Glide.with(mcontext).load(shopuri).placeholder(R.drawable.loadding).into(draweeView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, ShopDetailActivity.class);
                intent.putExtra("ShopId", shopExt.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        });
        holder.setText(R.id.tv_money, "￥" + shopExt.getPrice());
        holder.setText(R.id.tv_description, shopExt.getShopname());
        holder.setText(R.id.tv_user_name, shopExt.getName());
        holder.setText(R.id.tv_fromwhere, "來自" + shopExt.getCollege());
        holder.setText(R.id.tv_shoptime, shopExt.getShoptime());

        holder.getView(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareText("我在淘学App上看到一个很棒的商品："+shopExt.getShopname()+",价格：￥"+shopExt.getPrice()+"，分享给你哦~");
            }
        });
    }
    //分享文字
    public void shareText(String shopinfo) {
        Intent shareIntent = new Intent();
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shopinfo);
        shareIntent.setType("text/plain");

        //设置分享列表的标题，并且每次都显示分享列表
        mcontext.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

}
