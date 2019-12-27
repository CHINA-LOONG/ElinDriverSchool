package com.elin.elindriverschool.model;

import java.util.List;

/**
 * Created by 17535 on 2017/9/16.
 */

public class NoticeStuListBean {

    /**
     * rc : "0"
     * des :
     * data : [{"stu_cartype":"C1","stu_idnum":"371322199709280020","stu_name":"徐慧","stu_phone":"13953970273","stu_homphone":"13953980273","stu_reg_date":"2017-05-08","stu_photo":"http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/徐慧371322199709280020.jpg","stu_status":"51"}]
     */

    private String rc;
    private String des;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * stu_cartype : C1
         * stu_idnum : 371322199709280020
         * stu_name : 徐慧
         * stu_phone : 13953970273
         * stu_homphone : 13953980273
         * stu_reg_date : 2017-05-08
         * stu_photo : http://elindriving.oss-cn-hangzhou.aliyuncs.com/student/徐慧371322199709280020.jpg
         * stu_status : 51
         */

        private String stu_cartype;
        private String stu_idnum;
        private String stu_name;
        private String stu_phone;
        private String stu_homphone;
        private String stu_reg_date;
        private String stu_photo;
        private String stu_status;

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

        public String getStu_name() {
            return stu_name;
        }

        public void setStu_name(String stu_name) {
            this.stu_name = stu_name;
        }

        public String getStu_phone() {
            return stu_phone;
        }

        public void setStu_phone(String stu_phone) {
            this.stu_phone = stu_phone;
        }

        public String getStu_homphone() {
            return stu_homphone;
        }

        public void setStu_homphone(String stu_homphone) {
            this.stu_homphone = stu_homphone;
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

        public String getStu_status() {
            return stu_status;
        }

        public void setStu_status(String stu_status) {
            this.stu_status = stu_status;
        }
    }
}
