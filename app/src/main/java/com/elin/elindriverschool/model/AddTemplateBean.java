package com.elin.elindriverschool.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.StaticLayout;

import java.util.List;

/**
 * Created by 17535 on 2017/8/23.
 */

public class AddTemplateBean implements Parcelable {
    private String templateName;
    private List<AddTemplateTimeBean> data;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
    public List<AddTemplateTimeBean> getData() {
        return data;
    }

    public void setData(List<AddTemplateTimeBean> data) {
        this.data = data;
    }

    public static class AddTemplateTimeBean implements Parcelable {
        private String person_limit;
        private String train_sub;
        private String start_time;
        private String end_time;

        public String getPerson_limit() {
            return person_limit;
        }

        public void setPerson_limit(String person_limit) {
            this.person_limit = person_limit;
        }

        public String getTrain_sub() {
            return train_sub;
        }

        public void setTrain_sub(String train_sub) {
            this.train_sub = train_sub;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.person_limit);
            dest.writeString(this.train_sub);
            dest.writeString(this.start_time);
            dest.writeString(this.end_time);
        }

        public AddTemplateTimeBean() {
        }

        protected AddTemplateTimeBean(Parcel in) {
            this.person_limit = in.readString();
            this.train_sub = in.readString();
            this.start_time = in.readString();
            this.end_time = in.readString();
        }

        public static final Parcelable.Creator<AddTemplateTimeBean> CREATOR = new Parcelable.Creator<AddTemplateTimeBean>() {
            @Override
            public AddTemplateTimeBean createFromParcel(Parcel source) {
                return new AddTemplateTimeBean(source);
            }

            @Override
            public AddTemplateTimeBean[] newArray(int size) {
                return new AddTemplateTimeBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.templateName);
        dest.writeTypedList(this.data);
    }

    public AddTemplateBean() {
    }

    protected AddTemplateBean(Parcel in) {
        this.templateName = in.readString();
        this.data = in.createTypedArrayList(AddTemplateTimeBean.CREATOR);
    }

    public static final Parcelable.Creator<AddTemplateBean> CREATOR = new Parcelable.Creator<AddTemplateBean>() {
        @Override
        public AddTemplateBean createFromParcel(Parcel source) {
            return new AddTemplateBean(source);
        }

        @Override
        public AddTemplateBean[] newArray(int size) {
            return new AddTemplateBean[size];
        }
    };
}
