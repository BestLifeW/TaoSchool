package com.wtc.xmut.taoschool.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.wtc.xmut.taoschool.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * User:Shine
 * Date:2015-10-20
 * Description:
 */
public class CityFragment extends Fragment {

    private Context mContext;
    //定义图标数组
    private int[] imageRes = {
            R.drawable.yifu,
            R.drawable.caizhuang,
            R.drawable.baoshi,
            R.drawable.shuma,
            R.drawable.yundong,
            R.drawable.qiche,
            R.drawable.shenghuo,
            R.drawable.jiaju,
            R.drawable.qita};
    private String[] name = {
            "服饰",
            "彩妆",
            "珠宝",
            "数码",
            "运动",
            "汽车",
            "生活",
            "家具",
            "其他",

    };
    private View view;

    public CityFragment() {
        this.mContext = getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_city, container, false);
        //生成动态数组，并且转入数据
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<>();
        for (int i = 0; i < imageRes.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", imageRes[i]);//添加图像资源的ID
            map.put("ItemText", name[i]);//按序号做ItemText
            lstImageItem.add(map);
        }
        SimpleAdapter saImageItems = new SimpleAdapter(getActivity(),
                lstImageItem,//数据来源
                R.layout.item,//item的XML实现

                //动态数组与ImageItem对应的子项
                new String[]{"ItemImage", "ItemText"},

                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[]{R.id.img_tubiao, R.id.txt_wenzi});
        //添加并且显示
        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(saImageItems);
        //添加消息处理
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),name[position],Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    public static CityFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CityFragment fragment = new CityFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
