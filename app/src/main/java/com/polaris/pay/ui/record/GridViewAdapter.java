package com.polaris.pay.ui.record;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.polaris.pay.R;
import com.polaris.pay.logic.model.RecordType;

import java.util.ArrayList;

/**
 * @Date 2021/2/25 15:19
 * @Author toPolaris
 * @Description 记录界面中GridView的适配器
 */
public class GridViewAdapter extends BaseAdapter {
    final Context context;
    /**
     * 需要加载的数据
     * */
    final ArrayList<RecordType> dataList;
    /**
     * 当前选中的位置，默认为0，即第一个
     * */
    private int positionSelected;

    public GridViewAdapter(Context context, ArrayList<RecordType> dataList, int positionSelected) {
        this.context = context;
        this.dataList = dataList;
        this.positionSelected = positionSelected;
    }

    public void setPositionSelected(int positionSelected) {
        this.positionSelected = positionSelected;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public RecordType getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dataList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.cell_record, parent, false);
        TextView textView = convertView.findViewById(R.id.record_gv_item_tv);
        ImageView imageView = convertView.findViewById(R.id.record_gv_item_iv);
        RecordType recordType = dataList.get(position);
        textView.setText(recordType.getTypeName());
        if (positionSelected == position) {
            imageView.setImageResource(recordType.getImgSelected());
        } else {
            imageView.setImageResource(recordType.getImg());
        }
        return convertView;
    }
}
