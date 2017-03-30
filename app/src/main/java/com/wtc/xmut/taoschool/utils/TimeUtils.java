package com.wtc.xmut.taoschool.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tianchaowang on 17-3-30.
 */

public class TimeUtils {

    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}
