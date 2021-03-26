package com.polaris.pay.utils;

import com.polaris.pay.logic.model.Record;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Date 2021/2/25 21:06
 * @Author toPolaris
 * @Description 日期工具类
 */
public class DateUtils {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 返回当前日期的字符串类型，格式为"yyyy-MM-dd HH:mm:ss"
     * @return 格式化后的日期字符串
     */
    public static String getSimpleCurrentTime() {
        return FORMAT.format(new Date());
    }

    /**
     * 根据Record获取String类型的日期
     * @param record
     * @return String类型的日期
     */
    public static String getRecordTime(Record record) {
        return record.getYear() + "-" + record.getMonth() + "-" + record.getDay();

    }
}
