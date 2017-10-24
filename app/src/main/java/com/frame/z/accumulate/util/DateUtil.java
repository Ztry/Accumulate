package com.frame.z.accumulate.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/22.
 */

public class DateUtil {


    /**
     * 时间戳转换为日期格式
     */
    public static String stampToDate(String stamp) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(stamp);
        Date date = new Date(lt * 1000);
        res = simpleDateFormat.format(date);
        return res;
    }
}
