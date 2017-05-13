package com.wtc.xmut.taoschool.adpater;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
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
import com.wtc.xmut.taoschool.domain.ShopExt;
import com.wtc.xmut.taoschool.ui.activity.MyLikeActivity;
import com.wtc.xmut.taoschool.utils.XutilsUtils;

import java.util.List;

import static com.wtc.xmut.taoschool.myApplication.mContext;

/**
 * 作者 By lovec on 2017/2/23 0023.10:56
 * 邮箱 lovecanon0823@gmail.com
 */

public class MyLikeAdapter extends RecyclerView.Adapter<MyLikeAdapter.MyViewHolder> {


    private final XutilsUtils utils;
    private Context mcontext;
    private long parse;
    private Activity activity;
    private List<ShopExt> datas;

    public MyLikeAdapter(Context context, List<ShopExt> datas, MyLikeActivity myPublishActivity) {
        this.mcontext = context;
        this.activity = myPublishActivity;
        utils = XutilsUtils.getInstance();
        this.datas = datas;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mylike,
                parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Uri shopuri = Uri.parse(ServerApi.SHOWPIC + datas.get(position).getPicture());
        Uri UserIconUri = Uri.parse(ServerApi.SHOWPIC + datas.get(position).getIconpath());
        Glide.with(mcontext).load(shopuri).placeholder(R.drawable.loadding).into(holder.sld_shoppic);
        Glide.with(mcontext).load(UserIconUri).placeholder(R.drawable.usericon).into(holder.sld_usericon);
        holder.tv_description.setText(datas.get(position).getDescription());
        holder.tv_money.setText("￥:"+datas.get(position).getPrice());
        holder.tv_user_name.setText(datas.get(position).getUsername());
        holder.tv_fromwhere.setText(datas.get(position).getCollege());
        holder.tv_shoptime.setText(datas.get(position).getShoptime());
    }

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_money,tv_description,tv_user_name,tv_fromwhere,tv_shoptime,tv_del;
        ImageView sld_shoppic,sld_usericon;
        CardView card_view;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            tv_user_name = (TextView) itemView.findViewById(R.id.tv_user_name);
            tv_fromwhere = (TextView) itemView.findViewById(R.id.tv_fromwhere);
            tv_shoptime = (TextView) itemView.findViewById(R.id.tv_shoptime);
            sld_shoppic = (ImageView) itemView.findViewById(R.id.sld_shoppic);
            sld_usericon = (ImageView) itemView.findViewById(R.id.sld_usericon);
            tv_del = (TextView) itemView.findViewById(R.id.tv_del);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            card_view.setCardBackgroundColor(Color.WHITE);
        }
    }


}
