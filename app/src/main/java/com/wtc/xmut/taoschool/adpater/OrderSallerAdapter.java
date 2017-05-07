package com.wtc.xmut.taoschool.adpater;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.OrdersExt;
import com.wtc.xmut.taoschool.ui.activity.ConfirmActivity;
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;
import com.wtc.xmut.taoschool.view.CircleImageView;

import java.util.List;

/**
 * Created by tianchaowang on 17-4-24.
 */

public class OrderSallerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String username;
    private final XutilsUtils utils;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<OrdersExt> list;


    public OrderSallerAdapter(Context context, List<OrdersExt> list) {
        this.list = list;
        username = PrefUtils.getString(context, PrefUtils.USER_NUMBER, "");
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        utils = XutilsUtils.getInstance();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new SellerItem(mLayoutInflater.inflate(R.layout.item_message, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (list.get(position).getOrdersstate().equals("拍下")) {
            ((SellerItem) holder).tv_ordermsg.setText("卖家已经拍下");
        } else if (list.get(position).getOrdersstate().equals("卖家确认")) {
            ((SellerItem) holder).tv_ordermsg.setText("已确认信息");
        } else if (list.get(position).getOrdersstate().equals("卖家拒绝")) {
            ((SellerItem) holder).tv_ordermsg.setText("已取消信息");
        }
        ((SellerItem) holder).tv_ordertime.setText("日期:" + list.get(position).getTime().substring(5, 10));
        ((SellerItem) holder).buyusername.setText(list.get(position).getBuyerusername());
        String iconpath = list.get(position).getIconpath();
        String shopimg = list.get(position).getPicture();
        Glide.with(mContext).load(ServerApi.SHOWPIC + shopimg).placeholder(R.drawable.loadding).into(((SellerItem) holder).iv_shopimg);
        Glide.with(mContext).load(ServerApi.SHOWPIC + iconpath).placeholder(R.drawable.usericon).into(((SellerItem) holder).usericon);
        //拍下确认消息
        ((SellerItem) holder).ll_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ConfirmActivity.class);
                intent.putExtra("shopid", list.get(position).getShopid());
                mContext.startActivity(intent);
            }
        });

    }

    //设置ITEM类型，可以自由发挥，这里设置item position单数显示item1 偶数显示item2
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    //item2 的ViewHolder
    private static class SellerItem extends RecyclerView.ViewHolder {
        TextView buyusername;
        TextView tv_ordermsg;
        TextView tv_ordertime;
        ImageView iv_shopimg;
        TextView tv_seller;
        CardView ll_order;
        CircleImageView usericon;

        public SellerItem(View itemView) {
            super(itemView);
            usericon = (CircleImageView) itemView.findViewById(R.id.clv_userhead);
            buyusername = (TextView) itemView.findViewById(R.id.buyusername);
            tv_ordermsg = (TextView) itemView.findViewById(R.id.tv_ordermsg);
            tv_ordertime = (TextView) itemView.findViewById(R.id.tv_ordertime);
            //tv_seller = (TextView) itemView.findViewById(R.id.tv_seller);
            iv_shopimg = (ImageView) itemView.findViewById(R.id.iv_shopimg);
            ll_order = (CardView) itemView.findViewById(R.id.cv_order);
        }
    }


}