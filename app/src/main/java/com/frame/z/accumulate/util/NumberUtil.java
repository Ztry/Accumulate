package com.frame.z.accumulate.util;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/9/22.
 */

public class NumberUtil {


    /**
     * 将价格转换成万元单位
     */
    public static String switchPrice(String price) {
        double priceNum = Double.parseDouble(price);
        DecimalFormat df = new DecimalFormat("#.00");
        String format = df.format(priceNum / 10000);
        if (format.startsWith(".")) {
            format = 0 + format;
        }
        return format;
    }
}
