package com.polaris.pay.ui.record;

import android.annotation.SuppressLint;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.polaris.pay.R;
import com.polaris.pay.logic.model.Record;
import com.polaris.pay.logic.model.RecordType;
import com.polaris.pay.ui.record.dialog.CalendarDialog;
import com.polaris.pay.ui.record.dialog.RecordDialog;
import com.polaris.pay.utils.DataUtils;
import com.polaris.pay.utils.DateUtils;
import com.polaris.pay.utils.KeyboardUtils;
import com.polaris.pay.utils.LoadTypeUtils;

import java.util.Calendar;

/**
 * @Date 2021/2/25 16:40
 * @Author toPolaris
 * @Description 记录界面
 */

public class RecordDetailFragment extends Fragment implements View.OnClickListener {
    private final String ZERO_STR = "0";
    KeyboardUtils keyboardUtils;
    Record record;
    private TextView date, details;
    private EditText money;
    private GridViewAdapter adapter;
    private boolean type;

    // 此处注意，Fragment使用有参构造时，需要提供一个无参构造，否则报could not find Fragment constructor错误
//    public RecordDetailFragment(boolean type, Record record, boolean modify) {
//        this.type = type;
//        this.record = record;
//        this.modify = modify;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 一定会传数据，断言不为空
        Bundle arguments = getArguments();
        assert arguments != null;
        /**
         * 是否是修改状态
         */
        boolean modify = arguments.getBoolean("modify");
        type = arguments.getBoolean("type");

        // 控件绑定
        View inflate = inflater.inflate(R.layout.fragment_record_detail, container, false);
        KeyboardView keyboard = inflate.findViewById(R.id.keyboard_view);
        date = inflate.findViewById(R.id.record_detail_date);
        details = inflate.findViewById(R.id.record_detail_details);
        money = inflate.findViewById(R.id.record_detail_money);
        GridView gridView = inflate.findViewById(R.id.record_detail_gridview);

        // 设置监听器
        details.setOnClickListener(this);
        date.setOnClickListener(this);

        if (modify) {
            // 如果是修改状态，设置初始状态为未修改之前的状态
            record = arguments.getParcelable("record");
            date.setText(DateUtils.getRecordTime(record));
            details.setText(record.getDetails());
            adapter = new GridViewAdapter(getContext(), type ? LoadTypeUtils.getTypeListOut() : LoadTypeUtils.getTypeListIn(), record.getReasonPosition());
            money.setText(String.valueOf(record.getMoney()));
        } else {
            // 如果是记账状态，为record设置初始值
            // 设置默认时间
            record = new Record();
            date.setText(DateUtils.getSimpleCurrentTime().substring(0, 16));
            Calendar instance = Calendar.getInstance();
            record.setYear(instance.get(Calendar.YEAR));
            record.setMonth(instance.get(Calendar.MONTH) + 1);
            record.setDay(instance.get(Calendar.DAY_OF_MONTH));
            // 设置默认原因、图片id
            record.setReason(type ? LoadTypeUtils.getTypeListOut().get(0).getTypeName() : LoadTypeUtils.getTypeListIn().get(0).getTypeName());
            record.setImgId(type ? LoadTypeUtils.getTypeListOut().get(0).getImgSelected() : LoadTypeUtils.getTypeListIn().get(0).getImgSelected());
            adapter = new GridViewAdapter(getContext(), type ? LoadTypeUtils.getTypeListOut() : LoadTypeUtils.getTypeListIn(), 0);
        }
        gridView.setAdapter(adapter);

        // 显示自定义软键盘，并设置回调
        keyboardUtils = new KeyboardUtils(keyboard, money);
        keyboardUtils.showKeyboard();
        keyboardUtils.setOnEnsureListener(() -> {
            String string = money.getText().toString();
            if (TextUtils.isEmpty(string) || ZERO_STR.equals(string)) {
                // 如果金额为0或者未输入，弹出toast提示输入不合法，要求重新输入
                Toast.makeText(getContext(), "输入不合法！", Toast.LENGTH_SHORT).show();
                return;
            } else {
                record.setMoney(Float.parseFloat(string));
            }
            DataUtils.executorService.execute(() -> DataUtils.dao.insertRecords(record));
            Toast.makeText(getContext(), "保存成功！" + record.toString(), Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        });

        // 子项点击事件
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            keyboardUtils.showKeyboard();
            adapter.setPositionSelected(position);
            adapter.notifyDataSetChanged();
            RecordType recordType;
            if (type) {
                recordType = LoadTypeUtils.getTypeListOut().get(position);
            } else {
                recordType = LoadTypeUtils.getTypeListIn().get(position);
            }
            // 设置图片Id、原因、类型
            record.setImgId(recordType.getImgSelected());
            record.setReason(recordType.getTypeName());
            record.setType(type);
            if (type) {
                record.setImgId(LoadTypeUtils.getTypeListOut().get(position).getImgSelected());
            } else {
                record.setImgId(LoadTypeUtils.getTypeListIn().get(position).getImgSelected());
            }
        });
        return inflate;
    }

    /**
     * 设置事件、备注点击事件，并弹出自定义dialog
     * @param v 点击的控件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_detail_details:
                RecordDialog dialog = new RecordDialog(requireContext());
                dialog.show();
                // 设置备注点击确定后的回调
                dialog.setOnEnsureListener(() -> {
                    EditText detailsEditor = dialog.findViewById(R.id.record_dialog_et);
                    if (TextUtils.isEmpty(detailsEditor.getText())) {
                        record.setDetails(null);
                    } else {
                        record.setDetails(detailsEditor.getText().toString());
                        details.setText(record.getDetails());
                    }
                    dialog.cancel();
                });
                break;
            case R.id.record_detail_date:
                CalendarDialog calendarDialog = new CalendarDialog(requireContext());
                calendarDialog.show();
                // 设置日期点击确定后的回调
                calendarDialog.setOnCalendarListener((datePicker) -> {
                    record.setYear(datePicker.getYear());
                    record.setMonth(datePicker.getMonth() + 1);
                    record.setDay(datePicker.getDayOfMonth());
                    date.setText(DateUtils.getRecordTime(record));
                    calendarDialog.cancel();
                });
                break;
            default:
                break;
        }
    }
}