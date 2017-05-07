package com.wtc.xmut.taoschool.adpater;

import android.content.Context;
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
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tianchaowang on 17-4-24.
 */

public class OrderBuyerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String username;
    private final XutilsUtils utils;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<OrdersExt> list;


    public OrderBuyerAdapter(Context context, List<OrdersExt> list) {
        this.list = list;
        username = PrefUtils.getString(context, PrefUtils.USER_NUMBER, "");
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        utils = XutilsUtils.getInstance();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_saller)
        TextView tvSaller;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.iv_shoppic)
        ImageView ivShoppic;
        @BindView(R.id.textView7)
         TextView textView7;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_buyser, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        if (list.get(position).getOrdersstate().equals("拍下")) {
            ((ViewHolder) holder).tvState.setText("等待卖家同意");
        } else if (list.get(position).getOrdersstate().equals("卖家确认")) {
            ((ViewHolder) holder).tvState.setText("卖家已同意");
        } else if (list.get(position).getOrdersstate().equals("卖家拒绝")) {
            ((ViewHolder) holder).tvState.setText("卖家已拒绝");
        }

        ((ViewHolder) holder).tvSaller.setText(list.get(position).getSellerusername());
        ((ViewHolder) holder).tvMoney.setText("￥："+list.get(position).getPrice());
        ((ViewHolder) holder).textView7.setText(list.get(position).getShopname());
        String iconpath = list.get(position).getIconpath();
        String shopimg = list.get(position).getPicture();
        Glide.with(mContext).load(ServerApi.SHOWPIC + shopimg).placeholder(R.drawable.loadding).into(((ViewHolder) holder).ivShoppic);
        //拍下确认消息
       /* ((ViewHolder) holder).ll_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ConfirmActivity.class);
                intent.putExtra("shopid", list.get(position).getShopid());
                mContext.startActivity(intent);
            }
        });*/


    }

    //设置ITEM类型，可以自由发挥，这里设置item position单数显示item1 偶数显示item2
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }





}