package com.wtc.xmut.taoschool.adpater;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.OrdersExt;
import com.wtc.xmut.taoschool.ui.activity.ShopDetailActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by tianchaowang on 17-4-20.
 */

public class OrdersAdapter extends CommonAdapter<OrdersExt> {
    private static final String TAG = "OrdersAdapter";
    private Context mcontext;
    public OrdersAdapter(Context context, int layoutId, List<OrdersExt> datas) {
        super(context, layoutId, datas);
        this.mcontext = context;
    }

    @Override
    protected void convert(ViewHolder holder, final OrdersExt ordersExt, int position) {

        ImageView shopuri = holder.getView(R.id.iv_shopimg); //
        ImageView user_icon = holder.getView(R.id.clv_userhead);

        String iconpath = ordersExt.getIconpath();
        String shopimg = ordersExt.getPicture();
        Log.i(TAG, "convert: "+ServerApi.SHOWPIC+shopimg);
        Glide.with(mcontext).load(ServerApi.SHOWPIC+shopimg).placeholder(R.drawable.loadding).into(shopuri);
        Glide.with(mcontext).load(ServerApi.SHOWPIC+iconpath).placeholder(R.drawable.usericon).into(user_icon);

        holder.setText(R.id.buyusername, ordersExt.getBuyerusername());
        if (ordersExt.getShopstate().equals("0")){
            holder.setText(R.id.tv_ordermsg, "[订单]商品已被拍下");
        }

    }
}
