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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;
import com.wtc.xmut.taoschool.R;
import com.wtc.xmut.taoschool.api.ServerApi;
import com.wtc.xmut.taoschool.domain.ShopExt;
import com.wtc.xmut.taoschool.ui.activity.MyPublishActivity;
import com.wtc.xmut.taoschool.utils.XutilsUtils;

import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.wtc.xmut.taoschool.myApplication.mContext;

/**
 * 作者 By lovec on 2017/2/23 0023.10:56
 * 邮箱 lovecanon0823@gmail.com
 */

public class MyPublishAdapter extends RecyclerView.Adapter<MyPublishAdapter.MyViewHolder> {


    private final XutilsUtils utils;
    private Context mcontext;
    private long parse;
    private Activity activity;
    private List<ShopExt> datas;

    public MyPublishAdapter(Context context,List<ShopExt> datas, MyPublishActivity myPublishActivity) {
        this.mcontext = context;
        this.activity = myPublishActivity;
        utils = XutilsUtils.getInstance();
        this.datas = datas;
    }
/*
    @Override
    protected void convert(ViewHolder holder, final ShopExt shopExt, final int position) {

        ImageView draweeView = holder.getView(R.id.sld_shoppic);
        ImageView user_icon = holder.getView(R.id.sld_usericon);
        CardView view = holder.getView(R.id.card_view);
        view.setCardBackgroundColor(Color.WHITE);
        Glide.with(mcontext).load(shopuri).placeholder(R.drawable.loadding).into(draweeView);
        Glide.with(mcontext).load(UserIconUri).placeholder(R.drawable.usericon).into(user_icon);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, PublishActivity.class);
                intent.putExtra("msg", "display");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        });

        holder.getView(R.id.tv_del).setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DialogUIUtils.showMdAlert(activity, "是否删除?", null, new DialogUIListener() {
                    @Override
                    public void onPositive() {
                        utils.get(ServerApi.DELSHOPBYID + shopExt.getId(), null, new XutilsUtils.XCallBack() {
                            @Override
                            public void onResponse(String result) {
                                if (result.contains("删除成功")) {

                                    notifyItemRemoved(position);
                                    notifyDataSetChanged();
                                    Toasty.success(mcontext, "删除成功", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onResponseFail() {
                                Toasty.error(mcontext, "删除失败", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onNegative() {

                    }
                }).show();


            }
        });
        holder.setText(R.id.tv_money, "￥" + shopExt.getPrice());
        holder.setText(R.id.tv_description, shopExt.getDescription());
        holder.setText(R.id.tv_user_name, shopExt.getName());
        holder.setText(R.id.tv_fromwhere, "來自" + shopExt.getCollege());
        holder.setText(R.id.tv_shoptime, shopExt.getShoptime());
    }*/

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mypublish,
                parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Uri shopuri = Uri.parse(ServerApi.SHOWPIC + datas.get(position).getPicture());
        Uri UserIconUri = Uri.parse(ServerApi.SHOWPIC + datas.get(position).getIconpath());
        Glide.with(mcontext).load(shopuri).placeholder(R.drawable.loadding).into(holder.sld_shoppic);
        Glide.with(mcontext).load(UserIconUri).placeholder(R.drawable.usericon).into(holder.sld_usericon);
        holder.tv_del.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ShowDelDialog(position);
            }
        });
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

    /*
  * 删除对话框
  * */
    private void ShowDelDialog(final int position) {
        DialogUIUtils.showMdAlert(activity, "是否删除?", null, new DialogUIListener() {
            @Override
            public void onPositive() {
                utils.get(ServerApi.DELSHOPBYID + datas.get(position).getId(), null, new XutilsUtils.XCallBack() {
                    @Override
                    public void onResponse(String result) {
                        if (result.contains("删除成功")) {
                            datas.remove(position);
                            notifyDataSetChanged();
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                            Toasty.success(mcontext, "删除成功", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onResponseFail() {
                        Toasty.error(mcontext, "删除失败", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onNegative() {

            }
        }).show();
    }
}
