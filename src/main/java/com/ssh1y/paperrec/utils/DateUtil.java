package com.ssh1y.paperrec.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chenweihong
 */
public class DateUtil {

    /**
     * 获取当前时间，格式为yyyy-MM-dd
     * 
     * @return 当前时间
     */
    public static String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

}
