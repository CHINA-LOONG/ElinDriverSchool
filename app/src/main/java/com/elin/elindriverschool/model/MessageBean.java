package com.elin.elindriverschool.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by imac on 17/2/5.
 */

public class MessageBean {


    /**
     * des :
     * data_list : [{"reminder_content":"您有推送2017-02-20 16:19:21","reminder_date":"2017-02-20 16:19:21","reminder_id":"97","reminder_status":2,"reminder_title":"测试2017-02-20 16:19:21"},{"reminder_content":"您有推送2017-02-20 16:19:19","reminder_date":"2017-02-20 16:19:19","reminder_id":"96","reminder_status":2,"reminder_title":"测试2017-02-20 16:19:19"},{"reminder_content":"您有推送2017-02-20 16:19:11","reminder_date":"2017-02-20 16:19:11","reminder_id":"95","reminder_status":2,"reminder_title":"测试2017-02-20 16:19:11"},{"reminder_content":"您有推送2017-02-20 16:19:02","reminder_date":"2017-02-20 16:19:02","reminder_id":"94","reminder_status":2,"reminder_title":"测试2017-02-20 16:19:02"},{"reminder_content":"您有推送2017-02-20 16:06:29","reminder_date":"2017-02-20 16:06:29","reminder_id":"67","reminder_status":2,"reminder_title":"测试2017-02-20 16:06:29"},{"reminder_content":"学员丁泽明已超期","reminder_date":"2017-02-20 16:00:02","reminder_id":"53","reminder_status":2,"reminder_title":"学员丁泽明已超期"},{"reminder_content":"学员丁泽明,身份证号371322198411200716,已于2017-02-20 15:56:20调车成功，原教练员：教练员1,新教练员：张教练请及时沟通，学员联系电话:15651722162，新教练员联系电话:13256738828","reminder_date":"2017-02-20 15:56:20","reminder_id":"50","reminder_status":2,"reminder_title":"学员丁泽明已调车"},{"reminder_content":"您有推送2017-02-20 15:55:02","reminder_date":"2017-02-20 15:55:02","reminder_id":"49","reminder_status":2,"reminder_title":"测试2017-02-20 15:55:02"},{"reminder_content":"您有推送2017-02-20 15:52:30","reminder_date":"2017-02-20 15:52:30","reminder_id":"48","reminder_status":2,"reminder_title":"测试2017-02-20 15:52:30"},{"reminder_content":"您有推送2017-02-20 15:51:23","reminder_date":"2017-02-20 15:51:23","reminder_id":"47","reminder_status":2,"reminder_title":"测试2017-02-20 15:51:23"},{"reminder_content":"您有推送2017-02-20 15:51:17","reminder_date":"2017-02-20 15:51:17","reminder_id":"46","reminder_status":2,"reminder_title":"测试2017-02-20 15:51:17"},{"reminder_content":"您有推送2017-02-20 15:49:44","reminder_date":"2017-02-20 15:49:44","reminder_id":"45","reminder_status":2,"reminder_title":"测试2017-02-20 15:49:44"},{"reminder_content":"您有推送2017-02-20 15:49:38","reminder_date":"2017-02-20 15:49:38","reminder_id":"44","reminder_status":2,"reminder_title":"测试2017-02-20 15:49:38"},{"reminder_content":"您有推送2017-02-20 13:55:54","reminder_date":"2017-02-20 13:55:54","reminder_id":"43","reminder_status":2,"reminder_title":"测试2017-02-20 13:55:54"},{"reminder_content":"您有推送2017-02-20 13:41:13","reminder_date":"2017-02-20 13:41:13","reminder_id":"42","reminder_status":2,"reminder_title":"测试2017-02-20 13:41:13"},{"reminder_content":"您有推送2017-02-20 13:38:31","reminder_date":"2017-02-20 13:38:31","reminder_id":"40","reminder_status":2,"reminder_title":"测试2017-02-20 13:38:31"},{"reminder_content":"学员陈静,身份证号370101201701015579,已分于张教练教练员训练，请及时沟通，学员联系电话:15032145520,教练员联系电话:13256738828","reminder_date":"2017-02-17 17:20:25","reminder_id":"38","reminder_status":2,"reminder_title":"学员陈静已分配于张教练教练员"}]
     * rc : 0
     */

    private String des;
    private int rc;
    private List<DataListBean> data_list;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public List<DataListBean> getData_list() {
        return data_list;
    }

    public void setData_list(List<DataListBean> data_list) {
        this.data_list = data_list;
    }

    public static class DataListBean implements Parcelable {
        /**
         * reminder_content : 您有推送2017-02-20 16:19:21
         * reminder_date : 2017-02-20 16:19:21
         * reminder_id : 97
         * reminder_status : 2
         * reminder_title : 测试2017-02-20 16:19:21
         */

        private String reminder_content;
        private String reminder_date;
        private String reminder_id;
        private int reminder_status;
        private String reminder_title;

        public String getReminder_content() {
            return reminder_content;
        }

        public void setReminder_content(String reminder_content) {
            this.reminder_content = reminder_content;
        }

        public String getReminder_date() {
            return reminder_date;
        }

        public void setReminder_date(String reminder_date) {
            this.reminder_date = reminder_date;
        }

        public String getReminder_id() {
            return reminder_id;
        }

        public void setReminder_id(String reminder_id) {
            this.reminder_id = reminder_id;
        }

        public int getReminder_status() {
            return reminder_status;
        }

        public void setReminder_status(int reminder_status) {
            this.reminder_status = reminder_status;
        }

        public String getReminder_title() {
            return reminder_title;
        }

        public void setReminder_title(String reminder_title) {
            this.reminder_title = reminder_title;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.reminder_content);
            dest.writeString(this.reminder_date);
            dest.writeString(this.reminder_id);
            dest.writeInt(this.reminder_status);
            dest.writeString(this.reminder_title);
        }

        public DataListBean() {
        }

        protected DataListBean(Parcel in) {
            this.reminder_content = in.readString();
            this.reminder_date = in.readString();
            this.reminder_id = in.readString();
            this.reminder_status = in.readInt();
            this.reminder_title = in.readString();
        }

        public static final Parcelable.Creator<DataListBean> CREATOR = new Parcelable.Creator<DataListBean>() {
            @Override
            public DataListBean createFromParcel(Parcel source) {
                return new DataListBean(source);
            }

            @Override
            public DataListBean[] newArray(int size) {
                return new DataListBean[size];
            }
        };
    }
}
