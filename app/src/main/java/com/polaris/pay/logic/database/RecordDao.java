package com.polaris.pay.logic.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.polaris.pay.logic.model.Record;

import java.util.List;

/**
 * @Date 2021/2/24 21:10
 * @Author toPolaris
 * @Description 数据库操作接口
 */
@Dao
public interface RecordDao {

    /**
     * 向数据库中插入数据，存在则替换
     * @param records 被插入的数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecords(Record... records);

    /**
     * 以LiveData的形式返回所有记录
     * @return LiveData<List<Record>>类型的数据
     */
    @Query("select * from records order by id desc")
    LiveData<List<Record>> queryAllRecordsLive();

    /**
     * 删除数据
     * @param records 需要被删除的数据
     */
    @Delete
    void deleteRecord(Record... records);

}
