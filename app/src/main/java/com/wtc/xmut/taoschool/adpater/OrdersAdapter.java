package com.wtc.xmut.taoschool.adpater;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.OrdersExt;
import com.wtc.xmut.taoschool.ui.activity.ShopDetailActivity;
import com.wtc.xmut.taoschool.utils.XutilsUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

/**
 * Created by tianchaowang on 17-4-20.
 */

public class OrdersAdapter extends CommonAdapter<OrdersExt> {
    private static final String TAG = "OrdersAdapter";
    private Context mcontext;
    private View ll_order;
    private XutilsUtils utils;
    private int STATE = 1; //状态，1为 拍下; 2为确认
    public OrdersAdapter(Context context, int layoutId, List<OrdersExt> datas) {
        super(context, layoutId, datas);
        this.mcontext = context;
        utils = XutilsUtils.getInstance();
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
        ll_order = holder.getView(R.id.ll_order);
        Log.i(TAG, "convert: "+ordersExt.toString());
        holder.setText(R.id.buyusername, ordersExt.getBuyerusername());
        if (ordersExt.getOrdersstate().contains("拍下")){
            holder.setText(R.id.tv_ordermsg, "[订单]商品已被拍下");
            ll_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert(ordersExt);
                }
            });
        }else if (!(ordersExt.getOrdersstate().contains("拍下"))||STATE==2){
            holder.setText(R.id.tv_ordermsg, "[订单]已经同意买家请求");
            ll_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertOld(ordersExt);
                }
            });
        }
    }
    private void alert(final OrdersExt ordersExt){
        new MaterialStyledDialog.Builder(mContext)
                .setTitle("恭喜!")
                .setIcon(R.drawable.smiley)
                .setDescription("用        户："+ordersExt.getBuyerusername()+"\n\n"+"电话号码："+ordersExt.getTelephone()+"\n\n拍下了您的商品，并预订了在：\n\n"
                        +ordersExt.getTime()+"，\n\n" +
                        "在"+ordersExt.getCollege()+","+ordersExt.getFloor()+","+ordersExt.getDormitory()+"见面交易,是否确认？")
                .setPositiveText("确认?")
                .setNegativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        UpdateOrder(ordersExt);

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog,
                                        @NonNull DialogAction which) {
                        Toasty.info(mContext,"已经取消,将会通知对方", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    //根据订单id修改信息
    private void UpdateOrder(final OrdersExt ordersExt) {
        HashMap<String,String> map = new HashMap<>();
        map.put("id",ordersExt.getOrderid()+"");
        map.put("state","卖家确认");
        utils.post(ServerApi.UPDATEORDERBYID, map, new XutilsUtils.XCallBack() {
            @Override
            public void onResponse(String result) {
                if (result.contains("成功")){
                Toasty.success(mContext,"确认成功", Toast.LENGTH_LONG).show();
                    STATE = 2;
                    alertOld(ordersExt);
                }
            }

            @Override
            public void onResponseFail() {
                Toasty.error(mContext,"服务器链接失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void alertOld(OrdersExt ordersExt){
        new MaterialStyledDialog.Builder(mContext)
                .setTitle("查看信息")
                .setIcon(R.drawable.smiley)
                .setDescription("用        户："+ordersExt.getName()+"\n\n"+"电话号码："+ordersExt.getTelephone()+"\n\n拍下了您的商品，并预订了在：\n\n"
                        +ordersExt.getTime()+"，\n\n" +
                        "在"+ordersExt.getCollege()+","+ordersExt.getFloor()+","+ordersExt.getDormitory()+"")
                .setNegativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog,
                                        @NonNull DialogAction which) {
                    }
                })
                .show();
    }
}
