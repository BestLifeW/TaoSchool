package com.wtc.xmut.taoschool.adpater;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.OrdersExt;
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

/**
 * Created by tianchaowang on 17-4-24.
 */

public class OrderBuyerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String username;
    private final XutilsUtils utils;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<OrdersExt> list;
    private Activity activity;
    private String ordersstate;

    public OrderBuyerAdapter(Context context, List<OrdersExt> list, Activity activity) {

        this.list = list;
        username = PrefUtils.getString(context, PrefUtils.USER_NUMBER, "");
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        utils = XutilsUtils.getInstance();
        this.activity = activity;
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
        @BindView(R.id.rl_buy)
        RelativeLayout rl_buy;


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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        if (list.get(position).getOrdersstate().equals("拍下")) {
            ((ViewHolder) holder).tvState.setText("等待卖家同意");
        } else if (list.get(position).getOrdersstate().equals("卖家确认")) {
            ((ViewHolder) holder).tvState.setText("卖家已同意");
        } else if (list.get(position).getOrdersstate().equals("卖家拒绝")) {
            ((ViewHolder) holder).tvState.setText("卖家已拒绝");
        } else if (list.get(position).getOrdersstate().equalsIgnoreCase("卖家确认交易")) {
            ((ViewHolder)holder).tvState.setText("卖家已确认");
        } else if (list.get(position).getOrdersstate().equalsIgnoreCase("买家确认交易")){
            ((ViewHolder)holder).tvState.setText("等待卖家确认");
        } else if (list.get(position).getOrdersstate().equalsIgnoreCase("交易完成")){
            ((ViewHolder)holder).tvState.setText("交易已完成");
        }
        ((ViewHolder) holder).rl_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String state = ((ViewHolder) holder).tvState.getText().toString();
                ordersstate = list.get(position).getOrdersstate();
                if (state.equalsIgnoreCase("卖家已同意") || state.equalsIgnoreCase("卖家已确认")) {
                    final HashMap<String, String> map = new HashMap<>();
                    if (ordersstate.equalsIgnoreCase("卖家确认交易")){
                        map.put("id", list.get(position).getOrderid() + "");
                        map.put("state", "交易完成");
                    }else {
                        map.put("id",list.get(position).getOrderid()+"");
                        map.put("state","买家确认交易");
                    }
                    //点击弹出 买家需要确认
                    DialogUIUtils.showMdAlert(activity, "是否确认？", "确认和卖家已交易成功?", new DialogUIListener() {
                        @Override
                        public void onPositive() {
                            utils.post(ServerApi.UPDATEORDERBYID, map, new XutilsUtils.XCallBack() {
                                @Override
                                public void onResponse(String result) {
                                    if (result.contains("成功")) {

                                    }
                                }

                                @Override
                                public void onResponseFail() {
                                    Toasty.error(mContext, "服务器链接失败", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onNegative() {

                        }
                    }).show();

                }
            }
        });


        ((ViewHolder) holder).tvSaller.setText(list.get(position).getSellerusername());
        ((ViewHolder) holder).tvMoney.setText("￥:" + list.get(position).getPrice());
        ((ViewHolder) holder).textView7.setText(list.get(position).getShopname());
        String iconpath = list.get(position).getIconpath();
        String shopimg = list.get(position).getPicture();
        Glide.with(mContext).load(ServerApi.SHOWPIC + shopimg).placeholder(R.drawable.loadding).into(((ViewHolder) holder).ivShoppic);
        //拍下确认消息
       /* */


    }

    //设置ITEM类型，可以自由发挥，这里设置item position单数显示item1 偶数显示item2
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


}