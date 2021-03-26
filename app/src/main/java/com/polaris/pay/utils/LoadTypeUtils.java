package com.polaris.pay.utils;

import com.polaris.pay.R;
import com.polaris.pay.logic.model.RecordType;

import java.util.ArrayList;

/**
 * @Date 2021/2/25 14:39
 * @Author toPolaris
 * @Description 分类数据封装类，数据将在PayApplication类中进行初始化，后续直接取用即可
 */
public class LoadTypeUtils {

    private static final ArrayList<RecordType> typeListOut = new ArrayList<>();
    private static final ArrayList<RecordType> typeListIn = new ArrayList<>();

    static {
        // 数据初始化，分类初始化
        typeListIn.add(new RecordType(0, R.drawable.in_xz, R.drawable.in_xz_s, "薪资", false));
        typeListIn.add(new RecordType(1, R.drawable.in_jj, R.drawable.in_jj_s, "奖金", false));
        typeListIn.add(new RecordType(2, R.drawable.in_lc, R.drawable.in_lc_s, "理财", false));
        typeListIn.add(new RecordType(3, R.drawable.in_dk, R.drawable.in_dk_s, "贷款", false));
        typeListIn.add(new RecordType(4, R.drawable.in_sx, R.drawable.in_sx_s, "收息", false));
        typeListIn.add(new RecordType(5, R.drawable.in_es, R.drawable.in_es_s, "二手", false));
        typeListIn.add(new RecordType(6, R.drawable.in_ywsd, R.drawable.in_ywsd_s, "意外所得", false));
        typeListIn.add(new RecordType(7, R.drawable.in_qt, R.drawable.in_qt_s, "其他", false));
        typeListOut.add(new RecordType(0, R.drawable.out_cy, R.drawable.out_cy_s, "餐饮", true));
        typeListOut.add(new RecordType(1, R.drawable.out_jt, R.drawable.out_jt_s, "交通", true));
        typeListOut.add(new RecordType(2, R.drawable.out_yf, R.drawable.out_yf_s, "衣服", true));
        typeListOut.add(new RecordType(3, R.drawable.out_ryp, R.drawable.out_ryp_s, "日用品", true));
        typeListOut.add(new RecordType(4, R.drawable.out_yl, R.drawable.out_yl_s, "娱乐", true));
        typeListOut.add(new RecordType(5, R.drawable.out_ls, R.drawable.out_ls_s, "零食", true));
        typeListOut.add(new RecordType(6, R.drawable.out_zz, R.drawable.out_zz_s, "住宅", true));
        typeListOut.add(new RecordType(7, R.drawable.out_tx, R.drawable.out_tx_s, "通讯", true));
        typeListOut.add(new RecordType(8, R.drawable.out_fd, R.drawable.out_dk_s, "放贷", true));
        typeListOut.add(new RecordType(9, R.drawable.out_rqwl, R.drawable.out_rqwl_s, "人情往来", true));
        typeListOut.add(new RecordType(10, R.drawable.out_qt, R.drawable.out_qt_s, "其他", true));
    }

    public static ArrayList<RecordType> getTypeListOut() {
        return typeListOut;
    }

    public static ArrayList<RecordType> getTypeListIn() {
        return typeListIn;
    }

}
