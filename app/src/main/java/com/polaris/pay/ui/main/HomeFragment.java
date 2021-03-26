package com.polaris.pay.ui.main;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.polaris.pay.MainActivity;
import com.polaris.pay.R;
import com.polaris.pay.logic.model.Record;
import com.polaris.pay.utils.DataUtils;

import org.json.JSONObject;

import java.util.Calendar;

/**
 * @Date 2021/2/25 14:55
 * @Author toPolaris
 * @Description 主界面
 */
public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HomeViewModel mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // 加载Home界面
        MainActivity activity = (MainActivity) getActivity();
        assert activity != null;
        NavController navController = activity.getNavController();

        // View绑定：历史记录的RecyclerView，今日支出、收入，本月支出收入，记录悬浮按钮
        RecyclerView recyclerView = requireView().findViewById(R.id.mainRecyclerView);
        TextView todayIn = requireView().findViewById(R.id.today_in);
        TextView todayOut = requireView().findViewById(R.id.today_out);
        TextView monthIn = requireView().findViewById(R.id.month_in);
        TextView monthOut = requireView().findViewById(R.id.month_out);
        FloatingActionButton recordIn = requireView().findViewById(R.id.main_fab_record_in);
        FloatingActionButton recordOut = requireView().findViewById(R.id.main_fab_record_out);

        // RecyclerView设定适配器，主界面RecyclerView设配器
        MainAdapter adapter = new MainAdapter(navController, requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        // 设置回调，弹出对话框，选择是否删除记录
        adapter.setOnRemoveListener(record -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
            alertDialog.setTitle("删除记录");
            alertDialog.setMessage("是否删除该条记录？");
            alertDialog.setPositiveButton("确定", (dialog, which) -> DataUtils.executorService.execute(() -> DataUtils.dao.deleteRecord(record)));
            alertDialog.setNegativeButton("取消", (dialog, which) -> {
            });
            alertDialog.show();
        });
        recyclerView.setAdapter(adapter);

        // 观察数据库中的所有记录返回的LiveData对象，实现实时更新主页recyclerview的功能
        mViewModel.getAllRecords().observe(getViewLifecycleOwner(), records -> {
            // 更新数据
            adapter.setItems(records);
            adapter.notifyDataSetChanged();
            Log.e("HomeFrag", "onActivityCreated: ");
            // 获取今日的具体年月日，月份注意加1
            Calendar instance = Calendar.getInstance();
            int year = instance.get(Calendar.YEAR);
            int month = instance.get(Calendar.MONTH) + 1;
            int day = instance.get(Calendar.DAY_OF_MONTH);
            // 变量分别记录本日支出收入、本月支出收入
            float dayIn = 0, dayOut = 0, monthInNum = 0, monthOutNum = 0;
            if (records != null) {
                for (Record record : records) {
                    if (record.getYear() == year && record.getMonth() == month) {
                        if (record.isType()) {
                            // 支出
                            monthOutNum += record.getMoney();
                            if (record.getDay() == day) {
                                dayOut += record.getMoney();
                            }
                        } else {
                            // 收入
                            monthInNum += record.getMoney();
                            if (record.getDay() == day) {
                                dayIn += record.getMoney();
                            }
                        }
                    }
                }
            }

            // 更新显示
            todayIn.setText(String.valueOf(dayIn));
            todayOut.setText(String.valueOf(dayOut));
            monthIn.setText(String.valueOf(monthInNum));
            monthOut.setText(String.valueOf(monthOutNum));
        });

        // 启动收入详细页面
        recordIn.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("modify", false);
            bundle.putBoolean("type", false);
            bundle.putParcelable("record", null);
            navController.navigate(R.id.action_homeFragment_to_recordDetailFragment, bundle);
        });

        // 启动支出详细页面
        recordOut.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("modify", false);
            bundle.putBoolean("type", true);
            bundle.putParcelable("record", null);
            navController.navigate(R.id.action_homeFragment_to_recordDetailFragment, bundle);
        });
    }
}