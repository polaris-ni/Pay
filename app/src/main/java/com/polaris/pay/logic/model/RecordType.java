package com.polaris.pay.logic.model;

/**
 * @Date 2021/2/25 14:40
 * @Author toPolaris
 * @Description 用于显示收入支出类型及详细信息的类
 */
public class RecordType {


    private int id;
    /**
     * 未被选中展示图片
     */
    private final int img;
    /**
     * 选中时展示图片
     */
    private final int imgSelected;
    /**
     * 分类名称
     */
    private final String typeName;
    /**
     * 收入还是支出
     */
    private final boolean type;

    public RecordType(int id, int img, int imgSelected, String typeName, boolean type) {
        this.id = id;
        this.img = img;
        this.imgSelected = imgSelected;
        this.typeName = typeName;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public int getImgSelected() {
        return imgSelected;
    }

    public String getTypeName() {
        return typeName;
    }
}
