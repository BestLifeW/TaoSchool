package com.wtc.xmut.taoschool.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.OrdersExt;
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;
import com.wtc.xmut.taoschool.view.CircleImageView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by tianchaowang on 17-4-24.
 */

public class OrdersNewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String username;
    private final XutilsUtils utils;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<OrdersExt> list;
    private int STATE = 1; //状态，1为 拍下; 2为确认
    //建立枚举 2个item 类型
    public enum ORDERS {
        BURSER,
        SELLER
    }
    public OrdersNewAdapter(Context context,List<OrdersExt> list){
        this.list = list;
        username = PrefUtils.getString(context, PrefUtils.USER_NUMBER, "");
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        utils = XutilsUtils.getInstance();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载Item View的时候根据不同TYPE加载不同的布局
        if (viewType == ORDERS.BURSER.ordinal()) {
            return new BuyerItem(mLayoutInflater.inflate(R.layout.item_buyser, parent, false));
        } else {
            return new SellerItem(mLayoutInflater.inflate(R.layout.item_message, parent, false));
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BuyerItem) {
           ((BuyerItem) holder).tv_shopname.setText(list.get(position).getName());
           ((BuyerItem) holder).tv_isgreer.setText(list.get(position).getOrdersstate().contains("卖家确认")?"卖家已经同意":"卖家拒绝了");
           ((BuyerItem) holder).tv_money.setText(list.get(position).getPrice());
        } else if (holder instanceof SellerItem) {
           ((SellerItem) holder).tv_ordermsg.setText(list.get(position).getOrdersstate());
           ((SellerItem) holder).tv_ordertime.setText(list.get(position).getTime());
           ((SellerItem) holder).buyusername.setText(list.get(position).getBuyerusername());
           ((SellerItem) holder).buyusername.setText(list.get(position).getBuyerusername());
            String iconpath = list.get(position).getIconpath();
            String shopimg = list.get(position).getPicture();
            Glide.with(mContext).load(ServerApi.SHOWPIC+shopimg).placeholder(R.drawable.loadding).into(((SellerItem) holder).iv_shopimg);
            Glide.with(mContext).load(ServerApi.SHOWPIC+iconpath).placeholder(R.drawable.usericon).into(((SellerItem) holder).usericon);
            if (list.get(position).getOrdersstate().contains("拍下")&&STATE==1){
                ((SellerItem) holder).tv_ordermsg.setText("[订单]商品已被拍下");
                ((SellerItem) holder).ll_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert(list.get(position));
                    }
                });
            }else if (!list.get(position).getOrdersstate().contains("拍下")||STATE==2){
                ((SellerItem) holder).tv_ordermsg.setText( "[订单]同意买家请求");
                ((SellerItem) holder).ll_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertOld(list.get(position));
                    }
                });
            }
        }
    }
    //设置ITEM类型，可以自由发挥，这里设置item position单数显示item1 偶数显示item2
    @Override
    public int getItemViewType(int position) {

            return list.get(position).getBuyerusername().equals(username) ? ORDERS.BURSER.ordinal() : ORDERS.SELLER.ordinal();


    }
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
    //item1 的ViewHolder
     private static class BuyerItem extends RecyclerView.ViewHolder{
        TextView tv_shopname;
        TextView tv_money;
        TextView tv_seller;
        TextView tv_isgreer;
        BuyerItem(View itemView) {
            super(itemView);
                tv_shopname = (TextView) itemView.findViewById(R.id.tv_shopname);
                tv_money = (TextView)itemView.findViewById(R.id.tv_money);
                tv_seller = (TextView)itemView.findViewById(R.id.tv_seller);
                tv_isgreer = (TextView)itemView.findViewById(R.id.tv_isgreer);
        }
    }
    //item2 的ViewHolder
     private static class SellerItem extends RecyclerView.ViewHolder{
        TextView buyusername;
        TextView tv_ordermsg;
        TextView tv_ordertime;
        ImageView iv_shopimg;
        TextView tv_seller;
        CardView ll_order;
        CircleImageView usericon;
        public SellerItem(View itemView) {
            super(itemView);
            usericon = (CircleImageView)itemView.findViewById(R.id.clv_userhead);
            buyusername =(TextView)itemView.findViewById(R.id.buyusername);
            tv_ordermsg = (TextView)itemView.findViewById(R.id.tv_ordermsg);
            tv_ordertime = (TextView)itemView.findViewById(R.id.tv_ordertime);
            tv_seller = (TextView)itemView.findViewById(R.id.tv_seller);
            iv_shopimg = (ImageView)itemView.findViewById(R.id.iv_shopimg);
            ll_order = (CardView) itemView.findViewById(R.id.cv_order);
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

    private void alertOld(OrdersExt ordersExt){
        new MaterialStyledDialog.Builder(mContext)
                .setTitle("查看信息")
                .setIcon(R.drawable.smiley)
                .setDescription("用        户："+ordersExt.getName()+"\n\n"+"电话号码："+ordersExt.getTelephone()+"\n\n拍下了您的商品，并预订了在：\n\n"
                        +ordersExt.getTime()+"，\n\n" +
                        "在"+ordersExt.getCollege()+","+ordersExt.getFloor()+","+ordersExt.getDormitory()+"见面交易！")
                .setNegativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog,
                                        @NonNull DialogAction which) {
                    }
                })
                .show();
    }
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
}