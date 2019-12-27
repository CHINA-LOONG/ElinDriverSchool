package com.elin.elindriverschool.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

import java.util.List;

/**
 * Created by imac on 17/1/3.
 */

public class WaitOptionTestStuBean {


    /**
     * rc : 0
     * des :
     * data_list : [{"stu_name":"刘媛","stu_cartype":"C1","stu_idnum":"371302199401203445","stu_phone":"15266688153","stu_reg_date":"2015-06-13","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371302199401203445.jpg","stu_exam_wait_flag":"203","stu_detarec_remind_flag":"2","has_premium":"0"},{"stu_name":"陈欣雪","stu_cartype":"C1","stu_idnum":"371302198505204624","stu_phone":"13515394397","stu_reg_date":"2016-07-27","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371302198505204624.jpg","stu_exam_wait_flag":"203","stu_detarec_remind_flag":"2","has_premium":"0"},{"stu_name":"高卫星","stu_cartype":"C1","stu_idnum":"37280119731226162X","stu_phone":"13697817735","stu_reg_date":"2015-10-13","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/37280119731226162x.jpg","stu_exam_wait_flag":"201","stu_detarec_remind_flag":"2","has_premium":"0"},{"stu_name":"张广玉","stu_cartype":"C1","stu_idnum":"371322199403308318","stu_phone":"15006499520","stu_reg_date":"2016-07-23","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371322199403308318.jpg","stu_exam_wait_flag":"201","stu_detarec_remind_flag":"2","has_premium":"0"}]
     */

    private String rc;
    private String des;
    private List<DataListBean> data_list;

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<DataListBean> getData_list() {
        return data_list;
    }

    public void setData_list(List<DataListBean> data_list) {
        this.data_list = data_list;
    }

    public static class DataListBean extends BaseIndexPinyinBean implements Parcelable {
        /**
         * stu_name : 刘媛
         * stu_cartype : C1
         * stu_idnum : 371302199401203445
         * stu_phone : 15266688153
         * stu_reg_date : 2015-06-13
         * stu_photo : http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/371302199401203445.jpg
         * stu_exam_wait_flag : 203
         * stu_detarec_remind_flag : 2
         * has_premium : 0
         */

        private String stu_name;
        private String stu_cartype;
        private String stu_idnum;
        private String stu_phone;
        private String stu_reg_date;
        private String stu_photo;
        private String stu_exam_wait_flag;
        private String stu_detarec_remind_flag;
        private String has_premium;

        public String getStu_name() {
            return stu_name;
        }

        public void setStu_name(String stu_name) {
            this.stu_name = stu_name;
        }

        public String getStu_cartype() {
            return stu_cartype;
        }

        public void setStu_cartype(String stu_cartype) {
            this.stu_cartype = stu_cartype;
        }

        public String getStu_idnum() {
            return stu_idnum;
        }

        public void setStu_idnum(String stu_idnum) {
            this.stu_idnum = stu_idnum;
        }

        public String getStu_phone() {
            return stu_phone;
        }

        public void setStu_phone(String stu_phone) {
            this.stu_phone = stu_phone;
        }

        public String getStu_reg_date() {
            return stu_reg_date;
        }

        public void setStu_reg_date(String stu_reg_date) {
            this.stu_reg_date = stu_reg_date;
        }

        public String getStu_photo() {
            return stu_photo;
        }

        public void setStu_photo(String stu_photo) {
            this.stu_photo = stu_photo;
        }

        public String getStu_exam_wait_flag() {
            return stu_exam_wait_flag;
        }

        public void setStu_exam_wait_flag(String stu_exam_wait_flag) {
            this.stu_exam_wait_flag = stu_exam_wait_flag;
        }

        public String getStu_detarec_remind_flag() {
            return stu_detarec_remind_flag;
        }

        public void setStu_detarec_remind_flag(String stu_detarec_remind_flag) {
            this.stu_detarec_remind_flag = stu_detarec_remind_flag;
        }

        public String getHas_premium() {
            return has_premium;
        }

        public void setHas_premium(String has_premium) {
            this.has_premium = has_premium;
        }

        @Override
        public String getTarget() {
            return stu_name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.stu_name);
            dest.writeString(this.stu_cartype);
            dest.writeString(this.stu_idnum);
            dest.writeString(this.stu_phone);
            dest.writeString(this.stu_reg_date);
            dest.writeString(this.stu_photo);
            dest.writeString(this.stu_exam_wait_flag);
            dest.writeString(this.stu_detarec_remind_flag);
            dest.writeString(this.has_premium);
        }

        public DataListBean() {
        }

        protected DataListBean(Parcel in) {
            this.stu_name = in.readString();
            this.stu_cartype = in.readString();
            this.stu_idnum = in.readString();
            this.stu_phone = in.readString();
            this.stu_reg_date = in.readString();
            this.stu_photo = in.readString();
            this.stu_exam_wait_flag = in.readString();
            this.stu_detarec_remind_flag = in.readString();
            this.has_premium = in.readString();
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
