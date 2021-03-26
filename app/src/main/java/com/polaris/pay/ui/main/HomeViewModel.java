package com.polaris.pay.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polaris.pay.logic.model.Record;
import com.polaris.pay.utils.DataUtils;

import java.util.List;

/**
 * @Date 2021/2/25 15:20
 * @Author toPolaris
 * @Description 主界面的ViewModel
 */
public class HomeViewModel extends ViewModel {

    private LiveData<List<Record>> allRecords = new MutableLiveData<>();

    /**
     * 获取数据库中所有数据
     * @return 所有记录的LiveData
     */
    public LiveData<List<Record>> getAllRecords() {
        allRecords = DataUtils.dao.queryAllRecordsLive();
        return allRecords;
    }
}