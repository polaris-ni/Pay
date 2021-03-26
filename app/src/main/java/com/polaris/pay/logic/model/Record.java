package com.polaris.pay.logic.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.polaris.pay.utils.LoadTypeUtils;

import java.util.Objects;

/**
 * @Date 2021/2/24 21:00
 * @Author toPolaris
 * @Description 存入数据库的纪录类，实现Parcelable接口，便于序列化
 */
@Entity(tableName = "records")
public class Record implements Parcelable {
    /**
     * 主键，记录id
     */
    @PrimaryKey(autoGenerate = true)
    private int id;
    /**
     * 图片id
     * */
    private int imgId;
    /**
     * 是否是支出，true为支出，否则为收入
     */
    private boolean type;
    /**
     * 产生记录的原因
     * */
    private String reason;
    /**
     * 记录的备注
     * */
    private String details;
    /**
     * 记录的年、月、日
     * */
    private int year;
    private int month;
    private int day;
    /**
     * 记录的具体金额
     * */
    private float money;

    protected Record(Parcel in) {
        id = in.readInt();
        imgId = in.readInt();
        type = in.readByte() != 0;
        reason = in.readString();
        details = in.readString();
        year = in.readInt();
        month = in.readInt();
        day = in.readInt();
        money = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(imgId);
        dest.writeByte((byte) (type ? 1 : 0));
        dest.writeString(reason);
        dest.writeString(details);
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeInt(day);
        dest.writeFloat(money);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Record> CREATOR = new Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return (type ? "支出" : "收入") + " - " + year + " - " + month + " - " + day + " - " + money + " - " + reason + " - " + details;
    }

    @Ignore
    public Record(int id, int imgId, boolean type, String reason, String details, int year, int month, int day, float money) {
        this.id = id;
        this.imgId = imgId;
        this.type = type;
        this.reason = reason;
        this.details = details;
        this.year = year;
        this.month = month;
        this.day = day;
        this.money = money;
    }

    public Record() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    /**
     * 得到record在GridView中对应的位置
     * @return 位置id
     */
    public int getReasonPosition() {
        if (type) {
            for (RecordType recordType : LoadTypeUtils.getTypeListOut()) {
                if (Objects.equals(recordType.getTypeName(), this.reason)) {
                    return recordType.getId();
                }
            }
        } else {
            for (RecordType recordType : LoadTypeUtils.getTypeListIn()) {
                if (Objects.equals(recordType.getTypeName(), this.reason)) {
                    return recordType.getId();
                }
            }
        }
        return 0;
    }
}
