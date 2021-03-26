package com.polaris.pay.ui.record.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.polaris.pay.R;

/**
 * @Date 2021/2/26 14:44
 * @Author toPolaris
 * @Description 自定义备注弹窗
 */
public class RecordDialog extends Dialog implements View.OnClickListener {
    EditText details;
    Button cancel, ensure;
    private OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public interface OnEnsureListener {
        /**
         * 回调接口中的确定事件
         */
        void onEnsure();
    }

    public RecordDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_record);
        details = findViewById(R.id.record_dialog_et);
        cancel = findViewById(R.id.record_dialog_cancel);
        ensure = findViewById(R.id.record_dialog_ensure);
        cancel.setOnClickListener(this);
        ensure.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.record_dialog_ensure:
                if (onEnsureListener != null) {
                    onEnsureListener.onEnsure();
                }
                break;
            case R.id.record_dialog_cancel:
            default:
                cancel();
                break;
        }

    }
}
