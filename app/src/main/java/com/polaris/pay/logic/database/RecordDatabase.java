package com.polaris.pay.logic.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.polaris.pay.logic.model.Record;
import com.polaris.pay.utils.PayApplication;

/**
 * @Date 2021/2/24 21:08
 * @Author toPolaris
 * @Description 数据库类
 */

@Database(entities = {Record.class}, version = 1, exportSchema = false)
public abstract class RecordDatabase extends RoomDatabase {
    /**
     * 得到操作数据库的对象
     * @return
     */
    public abstract RecordDao getRecordDao();

    private static RecordDatabase records;

    /**
     * 返回数据库单例
     * @return
     */
    public synchronized static RecordDatabase getInstance() {
        if (records == null) {
            records = Room.databaseBuilder(PayApplication.context, RecordDatabase.class, "records.db").build();
        }
        return records;
    }
}
