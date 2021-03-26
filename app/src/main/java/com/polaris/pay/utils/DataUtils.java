package com.polaris.pay.utils;

import com.polaris.pay.logic.database.RecordDao;
import com.polaris.pay.logic.database.RecordDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Date 2021/2/25 9:10
 * @Author toPolaris
 * @Description 数据操作工具类
 */
public class DataUtils {
    /**
     * 数据库操作对象
     * */
    public static final RecordDatabase database = RecordDatabase.getInstance();
    public static final RecordDao dao = database.getRecordDao();

    /**
     * 单线程化的线程池，用于进行操作数据库
     * */
    public static final ExecutorService executorService = Executors.newSingleThreadExecutor();
}
