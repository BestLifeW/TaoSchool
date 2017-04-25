package com.wtc.xmut.taoschool.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.domain.OrdersExt;
import com.wtc.xmut.taoschool.utils.PrefUtils;
import com.wtc.xmut.taoschool.utils.XutilsUtils;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by tianchaowang on 17-4-24.
 */

public class OrdersNewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String username;
    private final XutilsUtils utils;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<OrdersExt> list;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BuyerItem) {
            //((Item1ViewHolder) holder).mTextView.setText(titles[position]);
        } else if (holder instanceof SellerItem) {
           // ((Item2ViewHolder) holder).mTextView.setText(titles[position]);
        }
    }
    //设置ITEM类型，可以自由发挥，这里设置item position单数显示item1 偶数显示item2
    @Override
    public int getItemViewType(int position) {
        //Enum类提供了一个ordinal()方法，返回枚举类型的序数，这里ITEM_TYPE.ITEM1.ordinal()代表0， ITEM_TYPE.ITEM2.ordinal()代表1
        //return position % 2 == 0 ? ORDERS.BURSER.ordinal() : ORDERS.SELLER.ordinal();
        return list.get(position).getBuyerusername().equals(username) ?ORDERS.BURSER.ordinal():ORDERS.SELLER.ordinal();
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
        TextView mTextView;
        public SellerItem(View itemView) {
            super(itemView);
        }
    }
}