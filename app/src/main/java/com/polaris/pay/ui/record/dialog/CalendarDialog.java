package com.polaris.pay.ui.record.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;

import com.google.android.material.card.MaterialCardView;
import com.polaris.pay.R;

/**
 * @Date 2021/2/26 15:29
 * @Author toPolaris
 * @Description 自定义日期选择对话框
 */
public class CalendarDialog extends Dialog implements View.OnClickListener {

    DatePicker datePicker;

    public interface OnCalendarListener {
        /**
         * 回调接口中的确定回调函数
         * @param datePicker
         */
        void onEnsure(DatePicker datePicker);
    }

    private OnCalendarListener onCalendarListener;

    public void setOnCalendarListener(OnCalendarListener onCalendarListener) {
        this.onCalendarListener = onCalendarListener;
    }

    public CalendarDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_calendar);
        datePicker = findViewById(R.id.dialog_calendar_datepicker);
        MaterialCardView ensure = findViewById(R.id.dialog_calendar_ensure);
        MaterialCardView cancel = findViewById(R.id.dialog_calendar_cancel);
        ensure.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_calendar_ensure:
                onCalendarListener.onEnsure(datePicker);
                break;
            case R.id.dialog_calendar_cancel:
                cancel();
                break;
            default:
                break;
        }
    }
}
