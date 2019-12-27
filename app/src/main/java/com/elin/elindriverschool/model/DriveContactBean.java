package com.elin.elindriverschool.model;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

/**
 * Created by imac on 17/1/7.
 */

public class DriveContactBean extends BaseIndexPinyinBean{

    private String name;
    private String phone;
    private String photo;

    private boolean isTop;//是否是最上面的 不需要被转化成拼音的

    public DriveContactBean() {
    }

    public DriveContactBean(String name, String photo, String phone) {
        this.name = name;
        this.photo = photo;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public DriveContactBean setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isTop() {
        return isTop;
    }

    public DriveContactBean setTop(boolean top) {
        isTop = top;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public DriveContactBean setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getPhoto() {
        return photo;
    }

    public DriveContactBean setPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    @Override
    public String toString() {
        return "DriveContactBean{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

    @Override
    public String getTarget() {
        return name;
    }

    @Override
    public boolean isNeedToPinyin() {
        return !isTop;
    }

    @Override
    public boolean isShowSuspension() {
        return !isTop;
    }
}
