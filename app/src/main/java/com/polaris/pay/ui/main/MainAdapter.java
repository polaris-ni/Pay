package com.polaris.pay.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.polaris.pay.R;
import com.polaris.pay.logic.model.Record;
import com.polaris.pay.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2021/2/24 20:41
 * @Author toPolaris
 * @Description 主界面RecyclerView的适配器
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    protected interface OnRemoveListener {
        /**
         * 删除接口中的回调方法
         * @param record 删除的数据
         */
        void onDeleteClick(Record record);
    }
    private OnRemoveListener onRemoveListener;

    public void setOnRemoveListener(OnRemoveListener onRemoveListener) {
        this.onRemoveListener = onRemoveListener;
    }

    /**
     * NavController，用于界面跳转
     */
    private final NavController navController;

    private final Context context;

    public MainAdapter(NavController navController, Context context) {
        this.navController = navController;
        this.context = context;
    }

    private List<Record> items = new ArrayList<>();

    public void setItems(List<Record> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_main, parent, false));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // 加载子项布局中的各个参数
        Record record = items.get(position);
        holder.reason.setText(record.getReason());
        if (TextUtils.isEmpty(record.getDetails()) || record.getDetails().length() <= 0) {
            holder.details.setVisibility(View.GONE);
        } else {
            holder.details.setVisibility(View.VISIBLE);
            holder.details.setText(record.getDetails());
        }

        holder.money.setText(String.valueOf(record.getMoney()));

        if (record.isType()) {
            holder.money.setTextColor(R.color.red);
        } else {
            holder.money.setTextColor(R.color.green);
        }

        holder.date.setText(DateUtils.getRecordTime(record));
        holder.infoImg.setImageResource(record.getImgId());

        holder.materialCardView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("record", record);
            // 标记此时为加载状态
            bundle.putBoolean("modify", true);
            bundle.putBoolean("type", record.isType());
            navController.navigate(R.id.action_homeFragment_to_recordDetailFragment, bundle);
        });

        // 设置长按事件
        holder.materialCardView.setOnLongClickListener(v -> {
            if (onRemoveListener != null) {
                onRemoveListener.onDeleteClick(record);
                return true;
            }
            return false;
        });
    }

    /**
     * ViewHolder类
     */
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView infoImg;
        private final TextView reason;
        private final TextView details;
        private final TextView money;
        private final TextView date;
        private final MaterialCardView materialCardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            materialCardView = itemView.findViewById(R.id.materialCardView);
            infoImg = itemView.findViewById(R.id.info_img);
            reason = itemView.findViewById(R.id.info_reason);
            details = itemView.findViewById(R.id.info_details);
            money = itemView.findViewById(R.id.info_money);
            date = itemView.findViewById(R.id.info_date);
        }
    }
}
