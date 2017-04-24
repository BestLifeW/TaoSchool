package com.wtc.xmut.taoschool.adpater;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.domain.OrdersExt;

import java.util.List;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;

/**
 * Created by tianchaowang on 17-4-24.
 */

public class OrdersNewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BUYER_TYPE = 0;
    private static final int SELLER_TYPE = 1;

    private List<OrdersExt> datas;

    public OrdersNewAdapter(List<String> datas) {

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       /* if (viewType == BUYER_TYPE) {
            return new BuyerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false));
        } else
            return new SallerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buyser, parent, false));*/
       //// TODO: 17-4-24  这边是写多种类型 
       return  null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    @Override
    public int getItemViewType(int position) {
        if (position % 4 == 0) {
            return BUYER_TYPE;
        }
        return SELLER_TYPE;
    }

 /*   private class SallerViewHolder extends RecyclerView.ViewHolder {
        public SallerViewHolder(Object inflate) {
            super();
        }
    }

    private class BuyerViewHolder extends RecyclerView.ViewHolder {
        public BuyerViewHolder(View inflate) {
            super();
        }
    }
    class SallerViewHolder extends RecyclerView.ViewHolder  {

        TextView tv_name;
        LinearLayout rootView;

        public NormalHolder(View itemView) {
            super(itemView);

        }

        class BuyerViewHolder extends RecyclerView.ViewHolder {
            public CheckHoldr(View itemView) {
                super(itemView);
            }
        }*/
}
